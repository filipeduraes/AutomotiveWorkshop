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
    /**
     * Observer que notifica quando há mudanças nas requisições de veículos.
     */
    public Observer OnVehicleRequest = new Observer();

    private VehicleRequest currentVehicleRequest;
    private VehicleDTO selectedVehicle;

    private int selectedVehicleIndex = -1;
    private List<String> selectedClientVehicles = new ArrayList<>();

    /**
     * Obtém a requisição atual de veículo.
     *
     * @return A requisição atual de veículo
     */
    public VehicleRequest getCurrentVehicleRequest()
    {
        return currentVehicleRequest;
    }

    /**
     * Define uma nova requisição de veículo e notifica os observadores.
     *
     * @param newVehicleRequest Nova requisição de veículo
     */
    public void setCurrentVehicleRequest(VehicleRequest newVehicleRequest)
    {
        if (newVehicleRequest != currentVehicleRequest)
        {
            currentVehicleRequest = newVehicleRequest;
            OnVehicleRequest.broadcast();
        }
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