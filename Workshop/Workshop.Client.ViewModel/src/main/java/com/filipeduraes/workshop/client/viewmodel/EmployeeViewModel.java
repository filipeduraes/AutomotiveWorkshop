// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.client.viewmodel;

import com.filipeduraes.workshop.client.dtos.EmployeeDTO;
import com.filipeduraes.workshop.utils.Observer;

import java.util.UUID;

/**
 * ViewModel responsável por gerenciar e armazenar informações do usuário logado,
 * incluindo estado de login, dados pessoais e papéis disponíveis no sistema.
 * Esta classe mantém o estado das operações de autenticação e registro de usuários,
 * bem como informações sobre o usuário atualmente logado.
 *
 * @author Filipe Durães
 */
public class EmployeeViewModel extends EntityViewModel<EmployeeDTO> implements IMonthReportViewModel
{
    /**
     * Evento disparado quando uma solicitação de login é feita.
     */
    public final Observer OnLoginRequested = new Observer();
    public final Observer OnClockInRequested = new Observer();
    public final Observer OnMonthClockInReportRequested = new Observer();

    private EmployeeDTO loggedUser;
    private String clockInReport = "";
    private int selectedMonth = -1;
    private int selectedYear = -1;

    /**
     * Obtém o usuário atualmente logado no sistema.
     *
     * @return o DTO do usuário logado ou null se não houver usuário logado
     */
    public EmployeeDTO getLoggedUser()
    {
        return loggedUser;
    }

    /**
     * Define o usuário logado no sistema.
     *
     * @param loggedUser o DTO do usuário a ser definido como logado
     */
    public void setLoggedUser(EmployeeDTO loggedUser)
    {
        this.loggedUser = loggedUser;
    }

    /**
     * Verifica se o usuário selecionado é o mesmo que está logado no sistema.
     *
     * @return true se o usuário selecionado for o mesmo que está logado, false caso contrário
     */
    public boolean isSelectedUserSameAsLoggedUser()
    {
        UUID selectedUserID = getSelectedDTO().getID();
        UUID loggedUserID = getLoggedUser().getID();
        return hasLoggedUser() && hasLoadedDTO() && loggedUserID.equals(selectedUserID);
    }

    @Override
    public void setSelectedMonth(int month)
    {
        selectedMonth = month;
    }

    public int getSelectedMonth()
    {
        return selectedMonth;
    }

    @Override
    public void setSelectedYear(int year)
    {
        selectedYear = year;
    }

    public int getSelectedYear()
    {
        return selectedYear;
    }

    @Override
    public void broadcastReportRequest()
    {
        OnMonthClockInReportRequested.broadcast();
    }

    @Override
    public String getReport()
    {
        return clockInReport;
    }

    public void setReport(String clockInReport)
    {
        this.clockInReport = clockInReport;
    }

    /**
     * Verifica se existe um usuário logado no sistema.
     *
     * @return true se existe um usuário logado, false caso contrário
     */
    private boolean hasLoggedUser()
    {
        return getLoggedUser() != null;
    }
}