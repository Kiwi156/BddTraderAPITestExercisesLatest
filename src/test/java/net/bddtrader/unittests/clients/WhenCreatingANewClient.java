package net.bddtrader.unittests.clients;
import io.restassured.http.ContentType;
import net.bddtrader.clients.Client;
import org.hamcrest.Matchers;
import io.restassured.RestAssured;

import org.junit.Before;
import org.junit.Test;

import org.hamcrest.Matchers.*;


import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;


public class WhenCreatingANewClient {

    @Before
    public void setupBaseUrl() {
        RestAssured.baseURI = "https://bddtrader.herokuapp.com/api/";
    }

    @Test
    public void eachNewClientShouldGetAUniqueId() {

        String newClient = "{ \"email\": \"test@gmail.com\", \"firstName\": \"Joe\", \"id\": 0, \"lastName\": \"Smith\"}";

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(newClient)
                .when()
                .post("/client")
                .then().statusCode(200)
                .and().body("id", Matchers.not(equalTo(0)))
                .and().body("email", equalTo("test@gmail.com"))
                .and().body("firstName", equalTo("Joe"))
                .and().body("lastName", equalTo("Smith"));

    }

    @Test
    public void eachNewClientShouldGetAUniqueIdDifferentMethod() {

        Client aNewClient = Client.withFirstName("Joe").andLastName("Smith").andEmail("test@gmail.com");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(aNewClient)
                .when()
                .post("/client")
                .then().statusCode(200)
                .and().body("id", Matchers.not(equalTo(0)))
                .and().body("email", equalTo("test@gmail.com"))
                .and().body("firstName", equalTo("Joe"))
                .and().body("lastName", equalTo("Smith"));


    }
    @Test
    public void eachNewClientShouldGetAUniqueIdADifferentMethodAgain() {

        Map<String,Object> ClientData = new HashMap<>();
        ClientData.put("email","test@gmail.com");
        ClientData.put("firstName","Joe");
        ClientData.put("LastName", "Smith");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(ClientData)
                .when()
                .post("/client")
                .then().statusCode(200)
                .and().body("id", Matchers.not(equalTo(0)))
                .and().body("email", equalTo("test@gmail.com"))
                .and().body("firstName", equalTo("Joe"))
                .and().body("lastName", equalTo("Smith"));

    }
    @Test
    public void eachNewClientShouldGetAUniqueIdChecks() {

        Client aNewClient = Client.withFirstName("Jim").andLastName("Halpert").andEmail("jim@halpert.com");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(aNewClient)
                .when()
                .post("/client")
                .then().statusCode(200)
                .and().body("id", Matchers.not(equalTo(0)))
                .and().body("email", equalTo("jim@halpert.com"))
                .and().body("firstName", equalTo("Jim"))
                .and().body("lastName", equalTo("Halpert"));
    }
    @Test
    public void eachNewClientShouldGetAUniqueIdMoreChecks() {

        Map<String,Object> ClientData = new HashMap<>();
        ClientData.put("email","kevin@malone.com");
        ClientData.put("firstName", "Kevin");
        ClientData.put("lastname", "Malone");


        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(ClientData)
                .when()
                .post("/client")
                .then().statusCode(200)
                .and().body("id", Matchers.not(equalTo(0)))
                .and().body("email", equalTo("kevin@malone.com"))
                .and().body("firstName", equalTo("Kevin"))
                .and().body("lastName", equalTo("Malone"));
    }


}

