package net.bddtrader.unittests.company;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.hasItems;

public class WhenGettingCompanyDetails {


    @Before
    public void prepareRestConfig() {
        RestAssured.baseURI = "https://bddtrader.herokuapp.com/api";
    }

    @Test
    public void shouldReturnNameAndSector() {

        RestAssured.given()
                .pathParam("symbol", "aapl")
                .when()
                .get("https://bddtrader.herokuapp.com/api/stock/{symbol}/company")
                .then()
                .body("companyName", Matchers.equalTo("Apple, Inc."))
                .body("sector", Matchers.equalTo("Electronic Technology"));
    }
    @Test
    public void shouldReturnNewsForARequestedCompany() {

        RestAssured.given().queryParam("symbols", "fb")
                .when()
                .get("/news")
                .then()
                .body("related", Matchers.everyItem(Matchers.containsString("FB")));
    }
    @Test
    public void findASimpleFieldValue() {

        RestAssured.given()
                .pathParam("symbol", "aapl")
                .when()
                .get("stock/{symbol}/company")
                .then()
                .body("industry", Matchers.equalTo("Telecommunications Equipment"));

    }

    @Test
    public void checkThatAFieldValueContainsAGivenString(){

        RestAssured.given()
                .pathParam("symbol", "aapl")
                .when()
                .get("stock/{symbol}/company")
                .then()
                .body("description", Matchers.containsString("smartphones"));

    }
    @Test
    public void findANestedFieldValue(){

        RestAssured.given().pathParam("symbol", "aapl")
                .when()
                .get("stock/{symbol}/book")
                .then().body("quote.symbol", Matchers.equalTo("AAPL"));

    }
    @Test
    public void findAListOfValues(){

        RestAssured.when()
                .get("/tops/last")
                .then().body("symbol", hasItems("PTN", "PINE", "TRS"));


    }
    @Test
    public void makeSureAtLeastOneItemMatchesAGivenCondition() {

        RestAssured.when()
                .get("/tops/last")
                .then().body("price", hasItems(Matchers.greaterThan(100.0f)));

    }

}

