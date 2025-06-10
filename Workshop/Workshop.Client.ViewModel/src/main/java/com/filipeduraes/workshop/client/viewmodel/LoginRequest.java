// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.viewmodel;

/**
 * Representa os possíveis estados do processo de login/cadastro.
 * Usado para rastrear o status do fluxo de autenticação.
 *
 * @author Filipe Durães
 */
public enum LoginRequest
{
    /**
     * Estado inicial quando nenhuma ação de autenticação foi tomada
     */
    WAITING,
    /**
     * Estado quando o usuário iniciou uma tentativa de login
     */
    LOGIN_REQUESTED,
    /**
     * Estado quando a tentativa de login foi bem-sucedida
     */
    LOGIN_SUCCESS,
    /**
     * Estado quando a tentativa de login falhou
     */
    LOGIN_FAILED,
    /**
     * Estado quando o usuário iniciou uma tentativa de cadastro
     */
    SIGNIN_REQUESTED,
    /**
     * Estado quando o cadastro foi bem-sucedido
     */
    SIGNIN_SUCCESS,
    /**
     * Estado quando o cadastro falhou
     */
    SIGNIN_FAILED,
}
