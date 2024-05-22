package com.game.pokedex.test.user;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;

import java.time.Instant;

public class UserStepDefinition {

    private RequestSpecification request;
    private Response response;
    private UserRequest userRequest = null;
    private UserDto userDto;

    private String token;

    public UserStepDefinition(){
        request = RestAssured.given()
                .baseUri("http://localhost:8080")
                .contentType(ContentType.JSON);
        userDto = new UserDto();
        userRequest = new UserRequest();
        token = "";
    }

    @Given("cliente com dados e nao cadastrado")
    public void clienteComDadosENaoCadastrado(){
        this.userRequest = new UserRequest();
        this.userRequest.setEmail(RandomStringUtils.randomAlphabetic(10)+"@gmail.com");
        this.userRequest.setCreatedDate(Instant.now());
        this.userRequest.setName(RandomStringUtils.randomAlphabetic(10));
        this.userRequest.setPassword(RandomStringUtils.randomAlphabetic(8));
        this.userRequest.setRole("ADMIN");
    }

    @Given("cliente ja estiver cadastrado e logado")
    public void clienteJaEstiverCadastrado(){
        this.userRequest = new UserRequest(
                Instant.now(),
                RandomStringUtils.randomAlphabetic(10)+"@email.com",
                RandomStringUtils.randomAlphabetic(8),
                RandomStringUtils.randomAlphabetic(10),
                "ADMIN"
        );
        response = this.request.body(userRequest).when().post("/user");
        response.then().statusCode(201);

        var id = response.jsonPath().getLong("id");
        requestForDto();
        this.userDto.setId(id);

        AuthRequest login = new AuthRequest(userRequest.getEmail(), userRequest.getPassword());
        response = request.body(login).when().post("/login");
        token = response.getBody().asString();
    }

    @When("cadastro o cliente e faco loggin")
    public void cadastroOCliente(){
        response = request.body(userRequest).when().post("/user");
        requestForDto();
        response.then().statusCode(201);
        var id = response.jsonPath().getLong("id");
        userDto.setId(id);
        AuthRequest login = new AuthRequest(userRequest.getEmail(), userRequest.getPassword());
        response = request.body(login).when().post("/login");
        token = response.getBody().asString();
    }

    @When("cadastro o cliente sem informar o nome")
    public void cadastroOClienteSemInformarONome(){
        userRequest.setName(null);
        response = request.body(userRequest).when().post("/user");
    }

    @When("cadastro o cliente sem informar o email")
    public void cadastroOClienteSemInformarOEmail(){
        userRequest.setEmail(null);
        response = request.body(userRequest).when().post("/user");
    }

    @When("pesquisar o cliente pelo nome")
    public void pesquisarOClientePeloNome(){
        response = request.header("Authorization", "Bearer " + token).when().get("/user/"+userRequest.getName());
        response.then().statusCode(200);
    }

    @Then("erro no cadastro {int}")
    public void erroNoCadastro(int erro){
        Assertions.assertEquals(erro, response.statusCode());
    }

    @Then("encontro o cliente cadastrado")
    public void encontroOClienteCadastrado(){
        response = request.header("Authorization", "Bearer " + token).when().get("/user/"+userRequest.getName());
        response.then().statusCode(200);
    }

    @Then("deve retornar o cliente")
    public void deveRetornarOClienteDentroDaLista(){
        var name = response.jsonPath().get("name");
        Assertions.assertEquals(userRequest.getName(),name);
    }

    @And("resposta deve ter status igual a {int}")
    public void respostaDeveTerStatusIgualA(int codigo){
        Assertions.assertEquals(codigo,response.statusCode());
    }

    private void requestForDto(){
        this.userDto.setCreatedDate(this.userRequest.getCreatedDate());
        this.userDto.setName(this.userRequest.getName());
        this.userDto.setEmail(this.userRequest.getEmail());
    }
}
