package com.softwareplant.api.module.report.rest;

import com.softwareplant.api.container.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import static com.jayway.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ReportControllerTest {


    @LocalServerPort
    private int port;

    @Test
    public void createReport_OK() {
        final String json = "{" +
                "\"characterPhrase\": \"r2\"," +
                "\"planetName\": \"Naboo\"}";

        System.out.println(given()
                .when()
                .body(json)
                .header("Content-Type", "application/json")
                .put("http://localhost:" + this.port + "/api/v1/report")
                .then()
                .extract().response().asString());
        //.statusCode(202);
    }

    @Test
    public void createReport_BAD_PARAMS() {
        final String json = "{" +
                "\"characterPhrase\": null," +
                "\"planetName\": \"Naboo\"}";

        given()
            .when()
                .body(json)
                .header("Content-Type", "application/json")
                .get("http://localhost:"+ port +"/api/v1/report")
            .then()
                .statusCode(400);
    }

    @Test
    public void createReport_NOT_FOUND() {
        final String json = "{" +
                "\"characterPhrase\": \"trololo\"," +
                "\"planetName\": \"Naboo\"}";

        given()
            .when()
                .body(json)
                .header("Content-Type", "application/json")
                .get("http://localhost:"+ port +"/api/v1/report")
            .then()
                .statusCode(404);
    }
}