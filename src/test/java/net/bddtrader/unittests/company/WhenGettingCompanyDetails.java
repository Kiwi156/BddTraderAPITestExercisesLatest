package net.bddtrader.unittests.company;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.Test;

public class WhenGettingCompanyDetails {

    @Test
    public void shouldReturnNameAndSector(){
        RestAssured.get("https://bddtrader.herokuapp.com/api/stock/aapl/company")
                .then()
                .body("companyName", Matchers.equalTo("Apple, Inc."))
                .body("sector", Matchers.equalTo("Electronic Technology"));
    }



}
