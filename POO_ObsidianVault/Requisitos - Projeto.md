#### **📌 Módulo de Autenticação e Controle de Acesso**
- [ ] Implementar autenticação de usuários.
- [ ] Criar níveis de acesso:
  - [ ] Funcionários podem acessar todas as funcionalidades, exceto:
    - [ ] Gerenciamento de despesas da oficina.
    - [ ] Balanço mensal de receitas e despesas.

---

#### **📌 Módulo de Cadastro**
- [ ] Criar CRUD para clientes:
  - [ ] Nome.
  - [ ] Endereço.
  - [ ] Número de telefone.
  - [ ] E-mail.
  - [ ] CPF pseudo-anonimizado.
- [ ] Criar CRUD para veículos:
  - [ ] Associação do veículo ao cliente.
  - [ ] Informações sobre o modelo, ano, placa e outras especificações.
- [ ] Criar CRUD para funcionários:
  - [ ] Nome, cargo, horário de trabalho.
  - [ ] Associação do funcionário ao serviço executado.
- [ ] Criar CRUD para serviços oferecidos:
  - [ ] Tipos de manutenção/reparo disponíveis.
  - [ ] Custo estimado.
  - [ ] Tempo médio de execução.
- [ ] Criar CRUD para peças da loja:
  - [ ] Nome, categoria, preço, estoque.

---

#### **📌 Módulo de Agendamento**
- [ ] Criar sistema de agenda para verificar horários disponíveis.
- [ ] Implementar funcionalidade de cadastro de agendamentos.
- [ ] Criar status de agendamento (Pendente, Confirmado, Cancelado).
- [ ] Implementar notificação para mecânicos sobre novos agendamentos.

---

#### **📌 Módulo de Atendimento e Execução de Serviços**
- [ ] Criar fluxo de atendimento:
  - [ ] Cliente chega para o serviço agendado.
  - [ ] Mecânico faz inspeção inicial e diagnóstico.
  - [ ] Registro do problema detectado no sistema.
  - [ ] Encaminhamento para mecânico especialista conforme necessário.
  - [ ] Associação do mecânico responsável ao serviço.
- [ ] Criar funcionalidade para adicionar peças e serviços ao orçamento.
- [ ] Gerar nota fiscal com serviços e peças utilizados.

---

#### **📌 Módulo de Controle de Manutenção**
- [ ] Implementar status do veículo no sistema:
  - [ ] Recebido.
  - [ ] Em manutenção.
  - [ ] Pronto para entrega.
  - [ ] Entregue.
- [ ] Criar histórico de serviços realizados para cada veículo.

---

#### **📌 Módulo Financeiro**
- [ ] Criar funcionalidade para emissão da "conta" do cliente.
- [ ] Implementar controle de despesas da oficina:
  - [ ] Limpeza.
  - [ ] Café e funcionários.
  - [ ] Materiais de uso diário.
- [ ] Criar painel para gerenciamento de receitas e despesas.
- [ ] Implementar funcionalidade de **balanço mensal** com:
  - [ ] Cálculo automático das receitas e despesas.
  - [ ] Relatórios financeiros para acompanhamento.

---

#### **📌 Módulo de Controle de Ponto**
- [ ] Criar funcionalidade para registro de entrada e saída de funcionários.
- [ ] Criar relatório de horas trabalhadas.
- [ ] Associar as horas trabalhadas ao cálculo de despesas da oficina.

---

### **📌 Estrutura de Classes**
- [ ] `Cliente`
  - [ ] Nome, Endereço, Telefone, E-mail, CPF (pseudo-anonimizado).
  - [ ] Relacionamento com `Veículo`.
- [ ] `Veículo`
  - [ ] Modelo, Ano, Placa.
  - [ ] Relacionamento com `Cliente` e `Serviço`.
- [ ] `Funcionário`
  - [ ] Nome, Cargo, Horário.
  - [ ] Relacionamento com `Serviço`.
- [ ] `Serviço`
  - [ ] Descrição, Custo, Tempo Estimado.
  - [ ] Relacionamento com `Veículo` e `Funcionário`.
- [ ] `Peça`
  - [ ] Nome, Categoria, Preço, Estoque.
  - [ ] Relacionamento com `Serviço`.
- [ ] `Agendamento`
  - [ ] Cliente, Veículo, Data/Hora.
  - [ ] Status (Pendente, Confirmado, Cancelado).
- [ ] `Oficina`
  - [ ] Classe principal que gerencia os serviços, clientes, veículos e funcionários.
  - [ ] Relacionamento com `Despesa`, `Receita`.

---

### **📌 Extras (Melhorias Futuras)**
- [ ] Envio de lembretes de agendamento por SMS/E-mail.
- [ ] Implementação de pagamentos online.
- [ ] Dashboard com gráficos e indicadores financeiros.