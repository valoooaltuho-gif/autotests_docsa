package api.clients;

import common.TestBase;
import io.restassured.common.mapper.TypeRef;
import io.restassured.path.json.JsonPath;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class BaseApiClient {

    protected JsonPath post(String endpoint, Object body, int expectedStatusCode) {
        return given()
                .spec(TestBase.requestSpec) // Подтягиваем общие заголовки и настройки из TestBase
                .body(body)
                .log().ifValidationFails()
                .when()
                .post(endpoint)
                .then()
                .statusCode(expectedStatusCode) // Базовая валидация статуса на уровне обёртки
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
                // Магия Jackson: превращает JSON в Map без создания классов!
                .as(new TypeRef<Map<String, Object>>() {});
    }
}
