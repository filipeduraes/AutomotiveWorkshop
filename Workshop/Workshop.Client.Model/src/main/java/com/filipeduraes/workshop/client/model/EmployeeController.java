// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.model;

import com.filipeduraes.workshop.client.dtos.EmployeeDTO;
import com.filipeduraes.workshop.client.model.mappers.EmployeeMapper;
import com.filipeduraes.workshop.client.viewmodel.EmployeeViewModel;
import com.filipeduraes.workshop.core.CrudRepository;
import com.filipeduraes.workshop.core.auth.AuthModule;
import com.filipeduraes.workshop.core.auth.Employee;
import com.filipeduraes.workshop.core.auth.EmployeeRole;
import com.filipeduraes.workshop.core.auth.LocalEmployee;
import java.util.List;

/**
 * Controlador responsável por gerenciar a autenticação e registro de usuários no sistema.
 *
 * @author Filipe Durães
 */
public class EmployeeController
{
    private final EmployeeViewModel viewModel;
    private final AuthModule authModule;

    private List<LocalEmployee> queriedEmployees = null;

    /**
     * Inicializa o controlador de autenticação.
     *
     * @param viewModel  ViewModel com dados do usuário
     * @param authModule Módulo de autenticação
     */
    public EmployeeController(EmployeeViewModel viewModel, AuthModule authModule)
    {
        this.viewModel = viewModel;
        this.authModule = authModule;

        this.viewModel.OnLoginRequested.addListener(this::login);
        this.viewModel.OnRegisterUserRequested.addListener(this::registerUser);
        this.viewModel.OnSearchRequest.addListener(this::searchUsers);
        this.viewModel.OnLoadDataRequest.addListener(this::loadUserData);
    }

    /**
     * Remove os listeners e libera recursos.
     */
    public void dispose()
    {
        this.viewModel.OnLoginRequested.removeListener(this::login);
        this.viewModel.OnRegisterUserRequested.removeListener(this::registerUser);
        this.viewModel.OnSearchRequest.removeListener(this::searchUsers);
        this.viewModel.OnLoadDataRequest.removeListener(this::loadUserData);
    }

    private void login()
    {
        EmployeeDTO employeeDTO = viewModel.getSelectedDTO();
        boolean loginWasSuccessful = authModule.tryLogIn(employeeDTO.getEmail(), employeeDTO.getPasswordHash());

        if (loginWasSuccessful)
        {
            EmployeeDTO loggedEmployeeDTO = EmployeeMapper.toDTO(authModule.getLoggedUser());
            viewModel.setLoggedUser(loggedEmployeeDTO);
            viewModel.setSelectedDTO(loggedEmployeeDTO);
        }

        viewModel.setRequestWasSuccessful(loginWasSuccessful);
    }

    private void registerUser()
    {
        LocalEmployee newUser = EmployeeMapper.fromLocalDTO(viewModel.getSelectedDTO());

        boolean registerWasSuccessful = authModule.registerUser(newUser);
        viewModel.setRequestWasSuccessful(registerWasSuccessful);
    }

    private void searchUsers()
    {
        final CrudRepository<LocalEmployee> employeeRepository = authModule.getEmployeeRepository();
        final EmployeeDTO employeeDTO = viewModel.getSelectedDTO();
        queriedEmployees = null;

        switch (viewModel.getSearchByOption())
        {
            case NAME ->
            {
                String namePattern = employeeDTO.getName();
                queriedEmployees = employeeRepository.searchEntitiesWithPattern(namePattern, Employee::getName);
            }
            case EMAIL ->
            {
                String emailPattern = employeeDTO.getEmail();
                queriedEmployees = employeeRepository.searchEntitiesWithPattern(emailPattern, Employee::getEmail);
            }
            case ROLE ->
            {
                EmployeeRole searchedRole = EmployeeMapper.fromEmployeeRoleDTO(employeeDTO.getRole());
                queriedEmployees = employeeRepository.findEntitiesWithPredicate(employee -> employee.getRole() == searchedRole);
            }
        }

        if(queriedEmployees == null)
        {
            viewModel.setRequestWasSuccessful(false);
            return;
        }

        List<String> descriptions = queriedEmployees.stream()
                                                    .map(EmployeeController::getEmployeeDescription)
                                                    .toList();

        viewModel.setFoundEntitiesDescriptions(descriptions);
        viewModel.setRequestWasSuccessful(true);
    }

    private void loadUserData()
    {
        if(queriedEmployees == null || viewModel.getSelectedIndex() < 0 || viewModel.getSelectedIndex() >= queriedEmployees.size())
        {
            viewModel.setRequestWasSuccessful(false);
            return;
        }

        LocalEmployee selectedEmployee = queriedEmployees.get(viewModel.getSelectedIndex());
        EmployeeDTO employeeDTO = EmployeeMapper.toDTO(selectedEmployee);

        viewModel.setSelectedDTO(employeeDTO);
        viewModel.setRequestWasSuccessful(true);
    }

    private static String getEmployeeDescription(Employee employee)
    {
        return String.format("%s - %s", employee.getName(), employee.getRole());
    }
}