package net.bddtrader.unittests.company;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
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
    public void checkThatAFieldValueContainsAGivenString() {

        RestAssured.given()
                .pathParam("symbol", "aapl")
                .when()
                .get("stock/{symbol}/company")
                .then()
                .body("description", Matchers.containsString("smartphones"));

    }

    @Test
    public void findANestedFieldValue() {

        RestAssured.given().pathParam("symbol", "aapl")
                .when()
                .get("stock/{symbol}/book")
                .then().body("quote.symbol", Matchers.equalTo("AAPL"));

    }

    @Test
    public void findAListOfValues() {

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

    @Test
    public void findAFieldOfAnElementInAList() {

        RestAssured.given()
                .pathParam("symbol", "aapl")
                .when()
                .get("stock/{symbol}/book")
                .then().body("trades[0].price", equalTo(319.59f));

    }

    @Test
    public void findAFieldOfALastElementInAList() {

        RestAssured.given()
                .pathParam("symbol", "aapl")
                .when()
                .get("stock/{symbol}/book")
                .then().body("trades[-1].price", equalTo(319.54f));

        // -1 represents the last value of list.
    }

    @Test
    public void findTheNumberOfTrades() {

        RestAssured.given()
                .pathParam("symbol", "aapl")
                .when()
                .get("stock/{symbol}/book")
                .then().body("trades.size()", equalTo(20));
    }

    @Test
    public void findTheMinimumPrice() {

        RestAssured.given()
                .pathParam("symbol", "aapl")
                .when()
                .get("stock/{symbol}/book")
                .then().body("trades.price.min()", equalTo(319.38F));
    }

    @Test
    public void findTheSizeofTheTradeWithTheMinimumTradePrice() {

        RestAssured.given()
                .pathParam("symbol", "aapl")
                .when()
                .get("stock/{symbol}/book")
                .then().body("trades.min {it.price}.volume", equalTo(100F));
    }

    @Test
    public void findTheTradeWithTheMinimumTradePrice() {

        RestAssured.given()
                .pathParam("symbol", "aapl")
                .when()
                .get("stock/{symbol}/book")
                .then().body("trades.findAll{t->t.price > 319.50}.size()", equalTo(13));

    }
}

