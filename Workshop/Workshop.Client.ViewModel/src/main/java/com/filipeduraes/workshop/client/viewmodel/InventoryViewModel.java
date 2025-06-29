// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.viewmodel;

import com.filipeduraes.workshop.client.dtos.StoreItemDTO;
import com.filipeduraes.workshop.utils.Observer;

import java.util.UUID;

/**
 * ViewModel responsável por gerenciar os dados da interface de inventário,
 * permitindo operações de reestoque e registro de compras de itens.
 *
 * @author Filipe Durães
 */
public class InventoryViewModel extends EntityViewModel<StoreItemDTO>
{
    /**
     * Evento disparado quando é solicitado o reabastecimento de um item no estoque.
     */
    public final Observer OnItemRestockRequest = new Observer();

    /**
     * Evento disparado quando é solicitado o registro de uma nova compra.
     */
    public final Observer OnRegisterPurchaseRequest = new Observer();

    private UUID purchaseID;
    private String purchaseTotalPrice = "";
    private int restockAmount = 1;
    private int purchaseQuantity = 1;

    /**
     * Obtém a quantidade de itens para reestoque.
     *
     * @return quantidade para reestoque
     */
    public int getRestockAmount()
    {
        return restockAmount;
    }

    /**
     * Define a quantidade de itens para reestoque.
     *
     * @param restockAmount quantidade maior que zero para reestoque
     */
    public void setRestockAmount(int restockAmount)
    {
        if (restockAmount > 0)
        {
            this.restockAmount = restockAmount;
        }
    }

    /**
     * Obtém a quantidade de itens para compra.
     *
     * @return quantidade para compra
     */
    public int getPurchaseQuantity()
    {
        return purchaseQuantity;
    }

    /**
     * Define a quantidade de itens para compra.
     *
     * @param purchaseQuantity quantidade maior que zero para compra
     */
    public void setPurchaseQuantity(int purchaseQuantity)
    {
        if (purchaseQuantity > 0)
        {
            this.purchaseQuantity = purchaseQuantity;
        }
    }

    /**
     * Obtém o ID da compra registrada.
     *
     * @return ID único da compra
     */
    public UUID getPurchaseID()
    {
        return purchaseID;
    }

    /**
     * Define o ID da compra registrada.
     *
     * @param purchaseID ID único da compra
     */
    public void setPurchaseID(UUID purchaseID)
    {
        this.purchaseID = purchaseID;
    }

    /**
     * Obtém o preço total da compra formatado.
     *
     * @return preço total da compra em formato string
     */
    public String getPurchaseTotalPrice()
    {
        return purchaseTotalPrice;
    }

    /**
     * Define o preço total da compra.
     *
     * @param purchaseTotalPrice preço total da compra em formato string
     */
    public void setPurchaseTotalPrice(String purchaseTotalPrice)
    {
        this.purchaseTotalPrice = purchaseTotalPrice;
    }
}
