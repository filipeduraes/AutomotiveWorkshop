// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.general;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.consoleview.input.IInputValidator;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Estratégia para coleta de entrada de dados durante operações de busca.
 * Define como a entrada do usuário deve ser coletada, validada e processada
 * para diferentes tipos de campos de busca.
 *
 * @author Filipe Durães
 */
public class SearchInputStrategy
{
    private final SearchInputStrategyType strategyType;
    private final IWorkshopMenu menuToRedirect;
    private final Function<MenuManager, String> resolveInput;
    private final Function<MenuManager, Boolean> validateInput;
    private final Consumer<MenuManager> cleanup;

    /**
     * Cria uma estratégia de entrada predefinida com valor vazio.
     */
    public SearchInputStrategy()
    {
        strategyType = SearchInputStrategyType.PREDEFINED;
        menuToRedirect = null;
        resolveInput = (menuManager) -> "";
        validateInput = (menuManager) -> true;
        cleanup = (menuManager) -> {};
    }

    /**
     * Cria uma estratégia de entrada direta com mensagem personalizada.
     *
     * @param inputMessage mensagem a ser exibida para solicitar a entrada
     */
    public SearchInputStrategy(String inputMessage)
    {
        strategyType = SearchInputStrategyType.DIRECT_INPUT;
        menuToRedirect = null;
        resolveInput = (menuManager) -> ConsoleInput.readLine(inputMessage);
        validateInput = (menuManager) -> true;
        cleanup = (menuManager) -> {};
    }

    /**
     * Cria uma estratégia de entrada direta com validação.
     *
     * @param inputMessage mensagem a ser exibida para solicitar a entrada
     * @param inputValidator validador para verificar a entrada do usuário
     */
    public SearchInputStrategy(String inputMessage, IInputValidator inputValidator)
    {
        strategyType = SearchInputStrategyType.DIRECT_INPUT;
        menuToRedirect = null;
        resolveInput = (menuManager) -> ConsoleInput.readValidatedLine(inputMessage, inputValidator);
        validateInput = (menuManager) -> true;
        cleanup = (menuManager) -> {};
    }

    /**
     * Cria uma estratégia de entrada com seleção de opções.
     *
     * @param inputMessage mensagem a ser exibida para solicitar a seleção
     * @param options opções disponíveis para seleção
     * @param <T> tipo das opções
     */
    public <T> SearchInputStrategy(String inputMessage, T[] options)
    {
        strategyType = SearchInputStrategyType.DIRECT_INPUT;
        menuToRedirect = null;
        resolveInput = (menuManager) -> Integer.toString(ConsoleInput.readOptionFromList(inputMessage, options));
        validateInput = (menuManager) -> true;
        cleanup = (menuManager) -> {};
    }

    /**
     * Cria uma estratégia de redirecionamento para menu específico.
     *
     * @param menuToRedirect menu para o qual será redirecionado
     * @param resolveInput função para resolver a entrada após o redirecionamento
     * @param validateInput função para validar se o redirecionamento foi bem-sucedido
     */
    public SearchInputStrategy(IWorkshopMenu menuToRedirect, Function<MenuManager, String> resolveInput, Function<MenuManager, Boolean> validateInput)
    {
        strategyType = SearchInputStrategyType.REDIRECT_MENU;
        this.menuToRedirect = menuToRedirect;
        this.resolveInput = resolveInput;
        this.validateInput = validateInput;
        this.cleanup = (viewModel) -> {};
    }

    /**
     * Cria uma estratégia de redirecionamento com função de limpeza.
     *
     * @param menuToRedirect menu para o qual será redirecionado
     * @param resolveInput função para resolver a entrada após o redirecionamento
     * @param validateInput função para validar se o redirecionamento foi bem-sucedido
     * @param cleanup função de limpeza a ser executada após o processamento
     */
    public SearchInputStrategy(IWorkshopMenu menuToRedirect, Function<MenuManager, String> resolveInput, Function<MenuManager, Boolean> validateInput, Consumer<MenuManager> cleanup)
    {
        strategyType = SearchInputStrategyType.REDIRECT_MENU;
        this.menuToRedirect = menuToRedirect;
        this.resolveInput = resolveInput;
        this.validateInput = validateInput;
        this.cleanup = cleanup;
    }

    /**
     * Obtém o tipo da estratégia de entrada.
     *
     * @return tipo da estratégia
     */
    public SearchInputStrategyType getStrategyType()
    {
        return strategyType;
    }

    /**
     * Obtém o menu para o qual será redirecionado (se aplicável).
     *
     * @return menu de redirecionamento ou null
     */
    public IWorkshopMenu getMenuToRedirect()
    {
        return menuToRedirect;
    }

    /**
     * Obtém a função para resolver a entrada do usuário.
     *
     * @return função de resolução de entrada
     */
    public Function<MenuManager, String> getResolveInput()
    {
        return resolveInput;
    }

    /**
     * Obtém a função para validar a entrada do usuário.
     *
     * @return função de validação
     */
    public Function<MenuManager, Boolean> getValidateInput()
    {
        return validateInput;
    }

    /**
     * Obtém a função de limpeza a ser executada após o processamento.
     *
     * @return função de limpeza
     */
    public Consumer<MenuManager> getCleanup()
    {
        return cleanup;
    }
}
