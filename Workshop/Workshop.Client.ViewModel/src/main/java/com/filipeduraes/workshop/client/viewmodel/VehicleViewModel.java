package com.filipeduraes.workshop.client.viewmodel;

import com.filipeduraes.workshop.client.dtos.VehicleDTO;
import com.filipeduraes.workshop.utils.Observer;
import java.util.ArrayList;
import java.util.List;

/**
 * ViewModel responsável por gerenciar os estados e dados relacionados aos veículos no sistema.
 *
 * @author Filipe Durães
 */
public class VehicleViewModel
{
    public final Observer OnClientVehiclesRequest = new Observer();
    public final Observer OnVehicleRegistrationRequest = new Observer();
    public final Observer OnVehicleDetailsRequest = new Observer();

    private VehicleDTO selectedVehicle;

    private boolean wasRequestSuccessful = false;
    private int selectedVehicleIndex = -1;
    private List<String> selectedClientVehicles = new ArrayList<>();


    public boolean getWasRequestSuccessful()
    {
        return wasRequestSuccessful;
    }

    public void setWasRequestSuccessful(boolean wasRequestSuccessful)
    {
        this.wasRequestSuccessful = wasRequestSuccessful;
    }

    /**
     * Obtém o veículo selecionado atualmente.
     *
     * @return O veículo selecionado
     */
    public VehicleDTO getSelectedVehicle()
    {
        return selectedVehicle;
    }

    /**
     * Define o veículo selecionado.
     *
     * @param selectedVehicle Veículo a ser selecionado
     */
    public void setSelectedVehicle(VehicleDTO selectedVehicle)
    {
        this.selectedVehicle = selectedVehicle;
    }

    /**
     * Verifica se existe um veículo selecionado.
     *
     * @return true se houver um veículo selecionado, false caso contrário
     */
    public boolean hasSelectedVehicle()
    {
        return selectedVehicleIndex >= 0 && selectedVehicleIndex < selectedClientVehicles.size();
    }

    /**
     * Limpa a seleção atual de veículo.
     */
    public void cleanCurrentSelectedVehicle()
    {
        selectedVehicle = null;
        selectedVehicleIndex = -1;
    }

    /**
     * Define o índice do veículo selecionado.
     *
     * @param newSelectedVehicleIndex Novo índice do veículo selecionado
     */
    public void setSelectedVehicleIndex(int newSelectedVehicleIndex)
    {
        selectedVehicleIndex = newSelectedVehicleIndex;
    }

    /**
     * Obtém o índice do veículo selecionado.
     *
     * @return O índice do veículo selecionado
     */
    public int getSelectedVehicleIndex()
    {
        return selectedVehicleIndex;
    }

    /**
     * Define a lista de veículos do cliente selecionado.
     *
     * @param newSelectedClientVehicles Nova lista de veículos do cliente
     */
    public void setSelectedClientVehicles(ArrayList<String> newSelectedClientVehicles)
    {
        selectedClientVehicles = newSelectedClientVehicles;
    }

    /**
     * Obtém o veículo do cliente a partir de um índice específico.
     *
     * @param index Índice do veículo na lista
     * @return O veículo correspondente ao índice
     */
    public String getSelectedClientVehicleFromIndex(int index)
    {
        return selectedClientVehicles.get(index);
    }

    /**
     * Obtém a lista de veículos do cliente selecionado.
     *
     * @return Lista de veículos do cliente
     */
    public List<String> getSelectedClientVehicles()
    {
        return selectedClientVehicles;
    }

    /**
     * Verifica se o cliente possui veículos registrados.
     *
     * @return true se o cliente possuir veículos, false caso contrário
     */
    public boolean clientHasVehicles()
    {
        return !selectedClientVehicles.isEmpty();
    }
}