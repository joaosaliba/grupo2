Feature: User
  Scenario: Cadastrar com sucesso
    Given cliente com dados e nao cadastrado
    When cadastro o cliente
    Then encontro o cliente cadastrado
    And resposta deve ter status igual a 201

  Scenario: Cadastrar cliente sem nome
    Given cliente com dados e nao cadastrado
    When cadastro o cliente sem informar o nome
    Then erro no cadastro 400

  Scenario: Cadastrar cliente email
    Given cliente com dados e nao cadastrado
    When cadastro o cliente sem informar o email
    Then erro no cadastro 400

  Scenario: listar cliente ja cadastrado
    Given cliente ja estiver cadastrado
    When pesquisar o cliente pelo nome
    Then deve retornar o cliente dentro da lista