// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.viewmodel.service;

import com.filipeduraes.workshop.client.dtos.service.ServiceOrderDTO;
import com.filipeduraes.workshop.client.viewmodel.EntityViewModel;
import com.filipeduraes.workshop.client.viewmodel.FieldType;
import com.filipeduraes.workshop.utils.Observer;

/**
 * ViewModel responsável por gerenciar os dados e estados relacionados aos serviços da oficina.
 * Esta classe mantém o estado da interface do usuário e coordena as operações relacionadas
 * aos serviços, incluindo consultas, filtros e edições.
 *
 * @author Filipe Durães
 */
public class ServiceOrderViewModel extends EntityViewModel<ServiceOrderDTO>
{
    /**
     * Evento disparado quando há uma solicitação para registrar um novo agendamento.
     */
    public final Observer OnRegisterAppointmentRequest = new Observer();

    /**
     * Evento disparado quando há uma solicitação para iniciar uma nova etapa do serviço.
     */
    public final Observer OnStartStepRequest = new Observer();

    /**
     * Evento disparado quando há uma solicitação para finalizar uma etapa do serviço.
     */
    public final Observer OnFinishStepRequest = new Observer();

    /**
     * Evento disparado quando há uma solicitação para editar um serviço.
     */
    public final Observer OnEditServiceRequest = new Observer();

    /**
     * Evento disparado quando há uma solicitação para editar uma etapa do serviço.
     */
    public final Observer OnEditServiceStepRequest = new Observer();

    /**
     * Evento disparado quando há uma solicitação para verificar se há um elevador disponível.
     */
    public final Observer OnElevatorTypeCheckRequest = new Observer();

    /**
     * Evento disparado quando há uma solicitação para adicionar um item de serviço na ordem de serviço.
     */
    public final Observer OnAddServiceItemRequested = new Observer();

    /**
     * Evento disparado quando há uma solicitação para adicionar uma venda na ordem de serviço.
     */
    public final Observer OnAddSaleRequested = new Observer();

    private ServiceQueryType queryType = ServiceQueryType.GENERAL;
    private String descriptionQueryPattern;
    private int selectedStepIndex = -1;
    private int selectedElevatorTypeIndex = -1;

    private String currentStepDetailedDescription;
    private String currentStepShortDescription;

    /**
     * Obtém o tipo de consulta atual do serviço.
     *
     * @return tipo da consulta atual
     */
    public ServiceQueryType getQueryType()
    {
        return queryType;
    }

    /**
     * Define o tipo de consulta do serviço.
     *
     * @param queryType novo tipo de consulta
     */
    public void setQueryType(ServiceQueryType queryType)
    {
        this.queryType = queryType;
    }

    /**
     * Obtém a descrição curta da etapa atual do serviço.
     *
     * @return descrição curta da etapa atual
     */
    public String getCurrentStepShortDescription()
    {
        return currentStepShortDescription;
    }

    /**
     * Define a descrição curta da etapa atual do serviço.
     *
     * @param currentStepShortDescription nova descrição curta
     */
    public void setCurrentStepShortDescription(String currentStepShortDescription)
    {
        this.currentStepShortDescription = currentStepShortDescription;
    }

    /**
     * Obtém a descrição detalhada da etapa atual do serviço.
     *
     * @return descrição detalhada da etapa atual
     */
    public String getCurrentStepDetailedDescription()
    {
        return currentStepDetailedDescription;
    }

    /**
     * Define a descrição detalhada da etapa atual do serviço.
     *
     * @param currentStepDetailedDescription nova descrição detalhada
     */
    public void setCurrentStepDetailedDescription(String currentStepDetailedDescription)
    {
        this.currentStepDetailedDescription = currentStepDetailedDescription;
    }

    /**
     * Define o padrão de busca por descrição.
     *
     * @param pattern padrão de busca
     */
    public void setDescriptionQueryPattern(String pattern)
    {
        descriptionQueryPattern = pattern;
    }

    /**
     * Obtém o padrão de busca por descrição atual.
     *
     * @return padrão de busca atual
     */
    public String getDescriptionQueryPattern()
    {
        return descriptionQueryPattern;
    }

    /**
     * Obtém o índice da etapa atualmente selecionada na ordem de serviço.
     *
     * @return o índice da etapa selecionada
     */
    public int getSelectedStepIndex()
    {
        return selectedStepIndex;
    }

    /**
     * Define o índice da etapa selecionada na ordem de serviço.
     *
     * @param selectedStepIndex o novo índice da etapa a ser selecionada
     */
    public void setSelectedStepIndex(int selectedStepIndex)
    {
        this.selectedStepIndex = selectedStepIndex;
    }


    /**
     * Obtém o índice do tipo de elevador atualmente selecionado.
     *
     * @return o índice do tipo de elevador selecionado
     */
    public int getSelectedElevatorTypeIndex()
    {
        return selectedElevatorTypeIndex;
    }

    /**
     * Define o índice do tipo de elevador selecionado.
     *
     * @param selectedElevatorTypeIndex o novo índice do tipo de elevador a ser selecionado
     */
    public void setSelectedElevatorTypeIndex(int selectedElevatorTypeIndex)
    {
        this.selectedElevatorTypeIndex = selectedElevatorTypeIndex;
    }

    /**
     * Reinicia os parâmetros de consulta para seus valores padrão.
     */
    public void resetQuery()
    {
        queryType = ServiceQueryType.GENERAL;
        setFieldType(FieldType.NONE);
        descriptionQueryPattern = "";
    }
}