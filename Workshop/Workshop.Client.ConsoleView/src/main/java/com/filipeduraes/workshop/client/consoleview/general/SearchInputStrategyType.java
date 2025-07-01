// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.general;

/**
 * Define os tipos de estratégias de entrada disponíveis para busca.
 * Cada tipo representa uma forma diferente de coletar dados do usuário
 * durante operações de busca.
 *
 * @author Filipe Durães
 */
public enum SearchInputStrategyType
{
    /**
     * Entrada direta do usuário através do console.
     */
    DIRECT_INPUT,
    
    /**
     * Redirecionamento para um menu específico para coleta de dados.
     */
    REDIRECT_MENU,
    
    /**
     * Valor predefinido sem necessidade de entrada do usuário.
     */
    PREDEFINED
}