package com.filipeduraes.workshop.client.dtos.service;

import com.filipeduraes.workshop.client.dtos.PricedItemDTO;
import com.filipeduraes.workshop.client.dtos.SaleDTO;
import com.filipeduraes.workshop.utils.TextUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


/**
 * Representa uma ordem de serviço no sistema da oficina.
 * Esta classe mantém todas as informações relevantes sobre uma ordem de serviço,
 * incluindo dados do cliente, veículo e etapas do serviço.
 *
 * @author Filipe Durães
 */
public class ServiceOrderDTO
{
    private final UUID id;
    private final UUID clientID;
    private final UUID vehicleID;
    private final String clientName;
    private final String vehicleDescription;
    private final List<ServiceStepDTO> steps;
    private final List<PricedItemDTO> serviceItems;
    private final List<SaleDTO> sales;
    private final boolean currentStepWasFinished;

    /**
     * Constrói uma nova ordem de serviço com os parâmetros fornecidos.
     *
     * @param id identificador único da ordem de serviço
     * @param clientID identificador do cliente
     * @param vehicleID identificador do veículo
     * @param clientName nome do cliente
     * @param vehicleDescription descrição do veículo
     * @param currentStepWasFinished indica se a etapa atual foi finalizada
     * @param steps coleção de etapas do serviço
     */
    public ServiceOrderDTO(UUID id, UUID clientID, UUID vehicleID, String clientName, String vehicleDescription, boolean currentStepWasFinished, List<ServiceStepDTO> steps, List<PricedItemDTO> serviceItems, List<SaleDTO> sales)
    {
        this.id = id;
        this.clientID = clientID;
        this.vehicleID = vehicleID;
        this.clientName = clientName;
        this.vehicleDescription = vehicleDescription;
        this.currentStepWasFinished = currentStepWasFinished;
        this.steps = steps;
        this.serviceItems = serviceItems;
        this.sales = sales;
    }

    /**
     * Obtém o identificador único da ordem de serviço.
     *
     * @return identificador da ordem de serviço
     */
    public UUID getID()
    {
        return id;
    }

    /**
     * Obtém o identificador do cliente associado à ordem de serviço.
     *
     * @return identificador do cliente
     */
    public UUID getClientID()
    {
        return clientID;
    }

    /**
     * Obtém o tipo da etapa atual do serviço.
     *
     * @return tipo da etapa atual
     */
    public ServiceStepTypeDTO getServiceStep()
    {
        return ServiceStepTypeDTO.values()[steps.size()];
    }

    /**
     * Obtém o nome do cliente associado à ordem de serviço.
     *
     * @return nome do cliente
     */
    public String getClientName()
    {
        return clientName;
    }

    /**
     * Obtém a descrição do veículo associado à ordem de serviço.
     *
     * @return descrição do veículo
     */
    public String getVehicleDescription()
    {
        return vehicleDescription;
    }

    /**
     * Verifica se a etapa atual do serviço foi finalizada.
     *
     * @return true se a etapa atual foi finalizada, false caso contrário
     */
    public boolean getCurrentStepWasFinished()
    {
        return currentStepWasFinished;
    }

    /**
     * Obtém a lista de etapas do serviço.
     *
     * @return coleção de etapas do serviço
     */
    public List<ServiceStepDTO> getSteps()
    {
        return steps;
    }

    /**
     * Obtém o identificador do veículo associado à ordem de serviço.
     *
     * @return identificador do veículo
     */
    public UUID getVehicleID()
    {
        return vehicleID;
    }

    /**
     * Verifica se a ordem de serviço foi finalizada.
     * Uma ordem é considerada finalizada quando está na etapa de manutenção
     * e a última etapa foi concluída.
     *
     * @return true se a ordem de serviço foi finalizada, false caso contrário
     */
    public boolean isFinished()
    {
        return getServiceStep() == ServiceStepTypeDTO.MAINTENANCE && steps.get(steps.size() - 1).getHasBeenFinished();
    }


    /**
     * Retorna uma representação em string da ordem de serviço.
     * A string contém o ID, estado atual, cliente, veículo e todas as etapas
     * do serviço em um formato estruturado.
     *
     * @return string formatada com os detalhes da ordem de serviço
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();

        builder.append(String.format("  ID:        %s%n", getID()));
        builder.append(String.format("  Estado:    %s%n", isFinished() ? "Finalizada" : getServiceStep()));
        builder.append(String.format("  Cliente:   %s%n", getClientName()));
        builder.append(String.format("  Veiculo:   %s%n", getVehicleDescription()));

        TextUtils.appendSeparator(builder);
        TextUtils.appendSection(builder, "ETAPAS DO SERVICO", getStepsDisplay());

        if(!serviceItems.isEmpty())
        {
            TextUtils.appendSection(builder, "SERVICOS PRESTADOS", getServicesPerformedDisplay());
        }

        if(!sales.isEmpty())
        {
            TextUtils.appendSection(builder, "PRODUTOS USADOS", getProductsUsedDisplay());
        }

        TextUtils.appendSection(builder, "RESUMO FINANCEIRO", getFinancialSummaryDisplay());

        return builder.toString();
    }

    private String getStepsDisplay()
    {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < getSteps().size(); i++)
        {
            ServiceStepDTO serviceStepDTO = getSteps().get(i);
            ServiceStepTypeDTO serviceStepTypeDisplay = ServiceStepTypeDTO.values()[i + 1];

            if (i > 0)
            {
                TextUtils.appendSeparator(builder);
            }

            TextUtils.appendSubtitle(builder, serviceStepTypeDisplay.toString());

            if (serviceStepDTO.getHasBeenFinished())
            {
                TextUtils.appendFirstList(builder, String.format("Descricao Curta:     %s%n", serviceStepDTO.getShortDescription()));
                TextUtils.appendFirstList(builder, String.format("Descricao Detalhada: %s%n", serviceStepDTO.getDetailedDescription()));
            }

            TextUtils.appendFirstList(builder, String.format("Responsavel:         %s%n", serviceStepDTO.getOwner()));
            TextUtils.appendFirstList(builder, String.format("Data de Inicio:      %s%n", serviceStepDTO.getStartDate()));

            if(serviceStepDTO.getHasBeenFinished())
            {
                TextUtils.appendFirstList(builder, String.format("Data de Termino:     %s%n", serviceStepDTO.getEndDate()));
            }

            String status = serviceStepDTO.getHasBeenFinished() ? "Finalizada" : "Em andamento";
            TextUtils.appendFirstList(builder, String.format("Estado:              %s%n", status));
        }

        return builder.toString();
    }

    private String getServicesPerformedDisplay()
    {
        StringBuilder builder = new StringBuilder();
        BigDecimal totalServices = BigDecimal.ZERO;

        for (PricedItemDTO service : serviceItems)
        {
            builder.append(String.format("- %-25s | %s%n", service.getName(), TextUtils.formatPrice(service.getPrice())));
            totalServices = totalServices.add(service.getPrice());
        }

        TextUtils.appendSeparator(builder);
        builder.append(String.format("TOTAL SERVICOS:             | %s%n", TextUtils.formatPrice(totalServices)));

        return builder.toString();
    }

    private String getProductsUsedDisplay()
    {
        StringBuilder builder = new StringBuilder();
        BigDecimal totalProducts = BigDecimal.ZERO;

        for (SaleDTO sale : sales)
        {
            String itemName = sale.getItem().getName();
            builder.append(String.format("- %-25s | Subtotal: %s%n", itemName, TextUtils.formatPrice(sale.getSubtotal())));
            totalProducts = totalProducts.add(sale.getSubtotal());
        }

        TextUtils.appendSeparator(builder);
        builder.append(String.format("TOTAL PRODUTOS:             | %s%n", TextUtils.formatPrice(totalProducts)));

        return builder.toString();
    }

    private String getFinancialSummaryDisplay()
    {
        StringBuilder builder = new StringBuilder();

        BigDecimal totalServices = serviceItems.stream()
                                        .map(PricedItemDTO::getPrice)
                                        .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalProducts = sales.stream()
                                        .map(SaleDTO::getSubtotal)
                                        .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal grandTotal = totalServices.add(totalProducts);

        builder.append(String.format("  Servicos: %s%n", TextUtils.formatPrice(totalServices)));
        builder.append(String.format("  Produtos: %s%n", TextUtils.formatPrice(totalProducts)));

        TextUtils.appendSeparator(builder);

        builder.append(String.format("TOTAL GERAL OS: %s%n", TextUtils.formatPrice(grandTotal)));
        return builder.toString();
    }
}
