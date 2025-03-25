- Agendamento Preliminar: Será feito por um atendente
	- Ticket com o agendamento chegará para o mecânico
- Agendamento Pós-Inspeção: Será feito por um mecânico
	- O mecânico avaliará o problema e encaminhará para o especialista
		- Deve conter os resultados da inspeção

Gerenciamento de permissões: Aparecerão telas diferentes dependendo da permissão do usuário. Atendente, Mecânico, Especialista...

Sistema de permissões: Gerenciadores do sistema podem dar permissões para os outros usuários, o que mudará as telas e operações que eles podem fazer.

- Deve haver uma forma de atualizar e visualizar o status do veículo (em triagem, em manutenção, pronto para ser entregue e entregue)

Precisa de um controle de ponto

---
#### Detalhes de implementação
- Como tudo será local, a preocupação com segurança será zero. Em um projeto real, o código de autenticação estaria rodando em um servidor.
- O armazenamento local servirá como um banco de dados em um servidor.
- Deve ser possível registrar e fazer login com vários usuários. A chave de identificação deles será o email, já que é único.

---
#### Módulo de Manutenção

A inspeção também gerará um serviço com um determinado custo?

LISTA DE SERVIÇOS: Uma manutenção pode haver vários serviços diferentes, com diferentes preços. **NMI** - Cada serviço vai ter um preço pré-determinado, ou o mecânico determina o preço na hora? Design inicial: o mecânico irá inserir o nome e o preço do serviço, já que coisas como o tempo podem influenciar.

**NMI** - Após passar do mecânico geral para o mecânico especialista, o mecânico especialista inserirá o tempo previsto? Ou o mecânico geral determinará isso?

Uma manutenção pode ter também uma lista de vendas, que são as peças usadas junto com o seu preço. (Comunicação entre o sistema de vendas e o de manutenção)

- Acessos: 
	- Atendente: Lançamentos de vendas e agendamento preliminar de serviços
	- Mecânico Geral: Visualização de agendamentos preliminares e transferência para o especialista. **NMI** - Verificar fluxo: Sempre será transferido para o especialista ou o mecânico geral pode já realizar o serviço.
	- Mecânico Especialista: Visualização do agendamento transferido com mensagem do mecânico geral. Lançamento do agendamento completo e preço das peças e do serviço.

**NMI** - Quando a atendente lançar o pré-agendamento, ela vai especificar qual mecânico geral vai receber ele? ou todos os mecânicos gerais vão receber esse alerta, e o primeiro que estiver livre pode pegar ele, e irá desaparecer para os outros.

**NMI** - Como funciona a agenda no pré-agendamento? No pré-agendamento, a agenda a ser verificada é de um mecânico específico ou geral da oficina? O tempo de cada atendimento é fixo (ex: 30min)? E em caso de atrasos ou adiantamentos, a agenda deve ser ajustada dinamicamente ou segue fixa? O especialista também tem uma agenda? Quando ele recebe o trabalho ele estima o tempo necessário e isso adiciona esse conserto na agenda?
#### Módulo de Vendas

#### Módulo de Agenda do Funcionário

- Será necessário controlar os serviços que um determinado mecânico tem na fila. A partir da última manutenção na fila, deve ser possível adicionar um planejamento de quando começará o serviço.
- Na hora de lançar que hora uma manutenção realmente começou e quando realmente finalizou, deve atualizar toda a fila para prever qualquer tipo de atraso ou adiantamento.
- Duas funções: Iniciar manutenção, finalizar manutenção. Mudam o estado atual da manutenção e atualizam as datas e a fila.

#### Módulo de Controle de Ponto

---
#### Módulo Financeiro

Acessos: 
 - Gerente/Dono: Visualização e geração do balanço. Lançamento de despesas. 
	 **NMI** - Confirmar se haverá um auxiliar administrativo.
 
Armazena:
- Despesas da oficina
	- Precisa do valor, descrição e data
- Contas dos Clientes (Receita)
- Vendas da loja de peças - Atendente lançará as vendas
- Poderá gerar um balanço patrimonial

---

#### Pessoas envolvidas

Atendente de serviços
Atendente da loja de peças - Verificar se os dois terão acessos separados
Mecânico geral
Mecânico especialista - Verificar se os dois terão acessos separados
Gerente (único que tem acesso ao módulo financeiro)
Cliente (tanto de serviços quanto da loja de peças) - Não interage diretamente com o sistema