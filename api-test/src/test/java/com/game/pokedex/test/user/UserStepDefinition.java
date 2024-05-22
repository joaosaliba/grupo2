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

    public UserStepDefinition(){
        request = RestAssured.given()
                .baseUri("http://localhost:8080")
                .contentType(ContentType.JSON);
        userDto = new UserDto();
        userRequest = new UserRequest();
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

    @Given("cliente ja estiver cadastrado")
    public void clienteJaEstiverCadastrado(){
        this.userRequest = new UserRequest(
                Instant.now(),
                "email@email.com",
                "password",
                RandomStringUtils.randomAlphabetic(10),
                "ADMIN"
        );

        response = this.request.body(userRequest).when().post("/user");

        response.then().statusCode(201);
        var id = response.jsonPath().getLong("id");
        requestForDto();
        this.userDto.setId(id);
    }

    @When("cadastro o cliente")
    public void cadastroOCliente(){
        response = request.body(userRequest).when().post("/user");
        requestForDto();
        response.then().statusCode(201);
        var id = response.jsonPath().getLong("id");
        userDto.setId(id);
    }

    @When("cadastro o cliente sem informar o nome")
    public void cadastroOClienteSemInformarONome(){
        System.out.println("alskdjfalksdj");
    }

    @When("cadastro o cliente sem informar o email")
    public void cadastroOClienteSemInformarOEmail(){
        System.out.println("lasdjflasdf");
    }

    @When("pesquisar o cliente pelo nome")
    public void pesquisarOClientePeloNome(){
        System.out.println("lalala");
    }

    @Then("erro no cadastro {int}")
    public void erroNoCadastro(int erro){
        System.out.println("lslslfasd");
    }

    @Then("encontro o cliente cadastrado")
    public void encontroOClienteCadastrado(){
        response = request.when().get("/user/"+userRequest.getName());
        response.then().statusCode(200);

        var name = response.jsonPath().get("name");
        Assertions.assertEquals(userRequest.getName(),name);
    }

    @Then("deve retornar o cliente dentro da lista")
    public void deveRetornarOClienteDentroDaLista(){
        System.out.println("lalala");
    }

    @And("resposta deve ter status igual a {int}")
    public void respostaDeveTerStatusIgualA(int codigo){
        System.out.println("alskdjfalsdjf"+codigo);
    }

    private void requestForDto(){
        this.userDto.setCreatedDate(this.userRequest.getCreatedDate());
        this.userDto.setName(this.userRequest.getName());
        this.userDto.setEmail(this.userRequest.getEmail());
    }
}
