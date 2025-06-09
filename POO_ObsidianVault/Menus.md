### Primeiro Login:

== Primeiro Acesso ==

Bem vindo ao sistema de gerenciamento de oficina mecanica.
Notamos que esse e o seu primeiro acesso, precisamos de algumas informacoes.
Vamos criar o seu login de Administrador!

Pressione Enter para continuar...

== Cadastrar Administrador > Primeiro Acesso ==
- Insira o seu nome completo: 
- Insira o seu email:
- Crie uma senha:

Cadastro criado com sucesso!

---
### Menu Principal

[0] Servicos
[1] Loja
[2] Clientes
[3] Veículos
[4] Financeiro
[5] X Sair

---

== Servicos > Menu Principal ==
### Servicos
[0] Criar Ordem de Servico
[1] Servicos abertos
[2] Seus servicos
[3] Servicos
[4] < Voltar

== Criar ordem de servico > Servicos > Menu Principal ==
#### [0] Criar ordem de servico
- Escolha o cliente: {Redirecionar para o menu de escolha de cliente}
- Escolha o veiculo: {Redirecionar para o menu de escolha de veiculo}
- Dê uma descricao curta para o problema: {String}
- Dê uma descricao detalhada para o problema: {String}

Ordem de servico criada com sucesso! ID: {ID da OS}


== Consultar servicos abertos > Servicos > Menu Principal ==
#### [1] Servicos abertos
{Selecionar modo de consulta = Serviços Abertos}
{Redirecionar para o menu de Consultar Serviços}


== Consultar seus servicos > Servicos > Menu Principal ==
#### [2] Seus servicos
{Selecionar modo de consulta = Serviços do Usuário}
{Redirecionar para o menu de Consultar Serviços}


== Consultar servicos gerais > Servicos > Menu Principal ==
#### [3] Servicos gerais
{Selecionar modo de consulta = Serviços Gerais}
{Redirecionar para o menu de Consultar Serviços}


== Consultar > [Tipo do Serviço] > Servicos > Menu Principal ==
#### Consultar servicos
- Deseja filtrar os servicos? (s/n): {boolean}
	>[0] Cliente {Redirecionar para o menu de escolha de cliente + Filtrar pelo ID do cliente}
	>[1] Veiculo {Redirecionar para o menu de escolha de veiculo + Filtrar pelo ID do veiculo}
	>[2] Descricao {Filtrar pelo padrão na descrição}
	>Qual filtro deseja utilizar? {Multipla escolha}
- {Lista os servicos: [número] descrição — cliente — veículo (placa) — data}
- Qual servico deseja consultar? {Multipla escolha}

---

== Detalhes > Consultar > [Tipo do Serviço] > Servicos > Menu Principal ==

#### Detalhes do Servico
- Cliente: {Dados do Cliente}
- Veiculo: {Dados do Veículo}
- Descricao Curta: {Descricao Curta}
- Descricao Detalhada: {Descricao Detalhada}
- Estado do Serviço: {Estado atual}

[0] Iniciar Inspeção / Iniciar Manutenção (Depende do estado do serviço)
[1] Finalizar Inspeção / Finalizar Manutenção (Depende do estado do serviço)
[2] Editar dados gerais do serviço
[3] Editar etapa do serviço