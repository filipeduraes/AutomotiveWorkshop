// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.model;

import com.filipeduraes.workshop.client.dtos.ClockInTypeDTO;
import com.filipeduraes.workshop.client.dtos.EmployeeDTO;
import com.filipeduraes.workshop.client.model.mappers.EmployeeMapper;
import com.filipeduraes.workshop.client.viewmodel.EmployeeViewModel;
import com.filipeduraes.workshop.core.CrudRepository;
import com.filipeduraes.workshop.core.employee.*;
import com.filipeduraes.workshop.utils.TextUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        this.viewModel.OnRegisterRequest.addListener(this::registerUser);
        this.viewModel.OnSearchRequest.addListener(this::searchUsers);
        this.viewModel.OnLoadDataRequest.addListener(this::loadUserData);
        this.viewModel.OnEditRequest.addListener(this::editUserData);
        this.viewModel.OnDeleteRequest.addListener(this::deleteUser);
        this.viewModel.OnClockInRequested.addListener(this::clockIn);
        this.viewModel.OnMonthClockInReportRequested.addListener(this::buildMonthClockInReport);
    }

    /**
     * Remove os listeners e libera recursos.
     */
    public void dispose()
    {
        this.viewModel.OnLoginRequested.removeListener(this::login);
        this.viewModel.OnRegisterRequest.removeListener(this::registerUser);
        this.viewModel.OnSearchRequest.removeListener(this::searchUsers);
        this.viewModel.OnLoadDataRequest.removeListener(this::loadUserData);
        this.viewModel.OnEditRequest.removeListener(this::editUserData);
        this.viewModel.OnDeleteRequest.removeListener(this::deleteUser);
        this.viewModel.OnClockInRequested.removeListener(this::clockIn);
        this.viewModel.OnMonthClockInReportRequested.removeListener(this::buildMonthClockInReport);
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
        String searchPattern = viewModel.getSearchPattern();
        queriedEmployees = null;

        switch (viewModel.getFieldType())
        {
            case NAME ->
            {
                queriedEmployees = employeeRepository.searchEntitiesWithPattern(searchPattern, Employee::getName);
            }
            case EMAIL ->
            {
                queriedEmployees = employeeRepository.searchEntitiesWithPattern(searchPattern, Employee::getEmail);
            }
            case ROLE ->
            {
                int selectedRoleIndex = Integer.parseInt(searchPattern);
                EmployeeRole selectedRole = EmployeeRole.values()[selectedRoleIndex];
                queriedEmployees = employeeRepository.findEntitiesWithPredicate(employee -> employee.getRole() == selectedRole);
            }
            case LOCAL_USER ->
            {
                queriedEmployees = new ArrayList<>();
                Employee loggedUser = authModule.getLoggedUser();
                queriedEmployees.add(employeeRepository.getEntityWithID(loggedUser.getID()));
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
        if(queriedEmployees == null || !viewModel.hasValidSelectedIndex())
        {
            viewModel.setRequestWasSuccessful(false);
            return;
        }

        LocalEmployee selectedEmployee = queriedEmployees.get(viewModel.getSelectedIndex());
        EmployeeDTO employeeDTO = EmployeeMapper.toDTO(selectedEmployee);

        viewModel.setSelectedDTO(employeeDTO);
        viewModel.setRequestWasSuccessful(true);
    }

    private void editUserData()
    {
        if(!viewModel.hasLoadedDTO() || !viewModel.hasValidSelectedIndex())
        {
            viewModel.setRequestWasSuccessful(false);
            return;
        }

        EmployeeDTO newDataDTO = viewModel.getSelectedDTO();
        LocalEmployee selectedEmployee = new LocalEmployee(queriedEmployees.get(viewModel.getSelectedIndex()));

        switch (viewModel.getFieldType())
        {
            case NAME -> selectedEmployee.setName(newDataDTO.getName());
            case EMAIL -> selectedEmployee.setEmail(newDataDTO.getEmail());
            case ROLE -> selectedEmployee.setRole(EmployeeMapper.fromEmployeeRoleDTO(newDataDTO.getRole()));
            case PASSWORD -> selectedEmployee.setHashPassword(newDataDTO.getPasswordHash());
            default -> viewModel.setRequestWasSuccessful(false);
        }

        boolean updateWasSuccessful = authModule.getEmployeeRepository().updateEntity(selectedEmployee);

        if(updateWasSuccessful)
        {
            viewModel.setSelectedDTO(EmployeeMapper.toDTO(selectedEmployee));
            queriedEmployees.set(viewModel.getSelectedIndex(), selectedEmployee);
        }

        viewModel.setRequestWasSuccessful(updateWasSuccessful);
    }

    private void deleteUser()
    {
        if(viewModel.isSelectedUserSameAsLoggedUser())
        {
            viewModel.setRequestWasSuccessful(false);
            return;
        }

        UUID selectedUserID = viewModel.getSelectedDTO().getID();
        LocalEmployee deletedUser = authModule.getEmployeeRepository().deleteEntityWithID(selectedUserID);

        viewModel.setRequestWasSuccessful(deletedUser != null);
    }

    private void clockIn()
    {
        ClockInTypeDTO lastClockIn = viewModel.getLoggedUser().getLastClockIn();
        ClockInType clockInType = EmployeeMapper.fromClockInTypeDTO(lastClockIn);
        authModule.registerClockIn(clockInType);
        viewModel.setRequestWasSuccessful(true);
    }

    private void buildMonthClockInReport()
    {
        List<ClockIn> clockIns;

        if(viewModel.getSelectedMonth() < 0)
        {
            clockIns = authModule.loadCurrentMonthClockIns();
        }
        else if(viewModel.getSelectedYear() < 0)
        {
            clockIns = authModule.loadMonthClockIns(viewModel.getSelectedMonth(), LocalDateTime.now().getYear());
        }
        else
        {
            clockIns = authModule.loadMonthClockIns(viewModel.getSelectedMonth(), viewModel.getSelectedYear());
        }

        String report = clockIns.stream()
                                .map(this::getClockInDescription)
                                .collect(Collectors.joining("\n"));

        viewModel.setReport(report);
        viewModel.setRequestWasSuccessful(true);
    }

    private String getClockInDescription(ClockIn clockIn)
    {
        LocalEmployee employee = authModule.getEmployeeRepository().getEntityWithID(clockIn.getEmployeeID());

        return String.format
        (
            " > %s - %s - %s",
            clockIn.getType(),
            employee.getName(),
            TextUtils.formatDate(clockIn.getTimestamp())
        );
    }

    private static String getEmployeeDescription(Employee employee)
    {
        return String.format("%s - %s", employee.getName(), employee.getRole());
    }
}