package api.clients;

import common.TestBase;
import io.restassured.common.mapper.TypeRef;
import io.restassured.path.json.JsonPath;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class BaseApiClient {

    protected JsonPath post(String endpoint, Object body, int expectedStatusCode) {
        return given()
                .spec(TestBase.requestSpec)
                .body(body)
                .log().ifValidationFails()
                .when()
                .post(endpoint)
                .then()
                .statusCode(expectedStatusCode)
                .extract()
                .jsonPath();
    }

    protected Map<String, Object> getAsMap(String endpoint, int expectedStatusCode) {
        return io.restassured.RestAssured.given()
                .spec(common.TestBase.requestSpec)
                .log().ifValidationFails()
                .when()
                .get(endpoint)
                .then()
                .statusCode(expectedStatusCode)
                .extract()
                .response()
                .as(new TypeRef<Map<String, Object>>() {});
    }
}
