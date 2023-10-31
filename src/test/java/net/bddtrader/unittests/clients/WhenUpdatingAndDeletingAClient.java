package net.bddtrader.unittests.clients;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.bddtrader.clients.Client;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class WhenUpdatingAndDeletingAClient {

    @Before
    public void setupBaseUrl() {
        RestAssured.baseURI = "https://bddtrader.herokuapp.com/api/";
    }
    @Test
    public void shouldBeAbleToDeleteAClient(){

        Client existingClient = Client.withFirstName("Pam").andLastName("Smith").andEmail("Pam@email.com");

        //Given a Client exists

        String id = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(existingClient)
                .when()
                .post("/client")
                .jsonPath().getString("id");

        //When I delete the client

        RestAssured.given().pathParam("id", id).delete("/client/{id}");

        RestAssured.given().pathParam("id", id)
                .get("/client/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    public void shouldBeAbleToDeleteAnotherClient(){

        Client existingClient = Client.withFirstName("Patty").andLastName("Cakes").andEmail("PattyCakes@email.com");

        String id = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(existingClient)
                .when()
                .post("/client")
                .jsonPath().getString("id");

        RestAssured.given().pathParam("id", id).delete("/client/{id}");

        RestAssured.given().pathParam("id", id)
                .get("/client/{id}")
                .then()
                .statusCode(404);
    }
    @Test
    public void shouldBeAbleToUpdateAClient() {

        Client patty = Client.withFirstName("Patty").andLastName("Cakes").andEmail("PattyCakes@email.com");

        String id = aClientExists(patty);

        Client pamWithUpdates = Client.withFirstName("Patty").andLastName("Cakes").andEmail("PattyStacks@email.com");
        RestAssured.given().contentType(ContentType.JSON)
                .and().body(pamWithUpdates)
                .when().put("/client/{id}", id)
                .then().statusCode(200);

        RestAssured.when().get("/client/{id}", id)
                .then().body("email", equalTo("PattyStacks@email.com"));
    }


        private String aClientExists(Client existingClient){
            return RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(existingClient)
                    .when()
                    .post("/client")
                    .jsonPath().getString("id");
        }

    }


