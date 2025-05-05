# Sistema de Gerenciamento de Oficina Mecânica

Projeto desenvolvido para a disciplina de Programação Orientada a Objetos do curso de Sistemas de Informação da
Universidade Federal dos Vales do Jequitinhonha e Mucuri (UFVJM).

Um sistema de gerenciamento de oficina mecânica baseado em Java para gerenciar manutenção de veículos, serviços e
inventário da loja.

## Visão Geral

Este sistema fornece uma solução completa para gerenciamento de oficina mecânica, incluindo:

- Autenticação de usuários e gerenciamento de funcionários
- Rastreamento de manutenção de veículos
- Catálogo de produtos e inventário
- Gerenciamento de clientes
- Interface de usuário baseada em console

## Arquitetura

O projeto é estruturado em múltiplos módulos:

- **Workshop.Core**: Lógica de negócios principal e modelos de dados
    - Módulo de autenticação
    - Catálogo de produtos
    - Gerenciamento de clientes
    - Persistência de dados

- **Workshop.Client.Console**: Ponto de entrada da aplicação console
    - Aplicação cliente principal
    - Injeção de dependência
    - Gerenciamento do ciclo de vida da aplicação

- **Workshop.Client.ConsoleView**: Componentes de interface do usuário
    - Sistema de menus
    - Telas de gerenciamento de veículos
    - Formulários de interação com cliente

- **Workshop.Client.Model**: Controladores e lógica de negócios
    - Controle de autenticação de usuário
    - Controle de gerenciamento de cliente
    - Gerenciamento do estado da aplicação

- **Workshop.Client.ViewModel**: Modelos de dados para visualização
    - Estado da interface do usuário
    - Objetos de vinculação de dados
    - Gerenciamento do estado da visualização

## Funcionalidades Principais

### Sistema de Autenticação

- Registro e login de usuário
- Gerenciamento de sessão
- Manipulação segura de senha
- Controle de acesso baseado em função

### Gerenciamento de Veículos

- Registro de veículos
- Rastreamento do histórico de manutenção
- Associação de veículo ao cliente

### Catálogo de Produtos

- Gerenciamento de serviços
- Rastreamento de inventário da loja
- Categorização de produtos

## Começando

1. Clone o repositório
2. Construa o projeto usando Maven
3. Execute a aplicação Workshop.Client.Console
4. Faça login com suas credenciais ou registre um novo usuário

## Author

Filipe Durães

## License

All rights reserved.
