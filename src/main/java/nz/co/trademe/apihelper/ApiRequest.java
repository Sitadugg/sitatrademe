package nz.co.trademe.apihelper;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ApiHelper {

    private String baseURI;

    public ApiHelper(String baseURI) {
        this.baseURI = baseURI;
        RestAssured.baseURI = baseURI;
    }

    public Response get(String endpoint) {
        return RestAssured.get(endpoint);
    }

    public Response post(String endpoint, Object requestBody) {
        return RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .post(endpoint);
    }
}