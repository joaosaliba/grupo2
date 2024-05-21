Feature: User
  Scenario: Cadastrar com sucesso
    Given cliente com documento igual a "100" e não cadastrado
    When cadastro o cliente
    Then encontro o cliente cadastrado
    And response should status equals 201

  Scenario: Cadastrar cliente sem nome
    Given cliente com documento igual a "100" e não cadastrado
    When cadastro o cliente sem informar o nome
    Then erro no cadastro 400

  Scenario: Cadastrar cliente sem documento
    Given cliente com documento igual a "100" e não cadastrado
    When cadastro o cliente sem informar o document
    Then erro no cadastro 400

  Scenario: listar cliente ja cadastrado
    Given cliente já estiver cadastrado
    When pesquisar o cliente pelo nome
    Then deve retornar o cliente dentro da lista