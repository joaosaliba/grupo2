# Pokédex REST API

Esta é uma REST API construída com Spring que simula uma Pokédex, permitindo aos usuários gerenciar e explorar informações sobre Pokémon.

## Endpoints

### Autenticação

- **POST /login**
  - Endpoint para autenticação de usuários. Recebe um usuário e senha, retornando um token JWT para autenticação em requisições subsequentes.

### Pokédex do Usuário

- **DELETE /pokedex/delete_user/{id}**
  - Deleta a Pokédex completa de um usuário. O ID fornecido no path identifica o usuário cuja Pokédex será deletada.

- **GET /pokedex/list_pokemons/{id}**
  - Lista os Pokémon na Pokédex de um usuário específico. O ID no path identifica o usuário.

- **DELETE /pokedex/delete_pokemon/{id}**
  - Remove um Pokémon específico da Pokédex do usuário. O ID fornecido no path identifica o Pokémon a ser removido.

### Pokémon

- **GET /pokemons/{name}**
  - Retorna informações detalhadas de um Pokémon específico pelo nome.

- **POST /pokemons/capturar/{username}/{pokemon_name}**
  - Tenta capturar um Pokémon pelo nome e adicioná-lo à Pokédex do usuário especificado.

- **GET /pokemons**
  - Lista todos os Pokémon disponíveis. Aceita parâmetros opcionais de limit e offset para paginação.

### Usuários

- **GET /user**
  - Lista todos os usuários cadastrados no sistema.

- **GET /user/{username}**
  - Retorna informações de um usuário específico com base no nome de usuário.

- **DELETE /user/{username}**
  - Deleta um usuário específico com base no nome de usuário fornecido.

- **PUT /user/{username}**
  - Atualiza a senha e a data de modificação de um usuário específico com base no nome de usuário fornecido.

- **POST /user**
  - Cadastra um novo usuário no sistema.
