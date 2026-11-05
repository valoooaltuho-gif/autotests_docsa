package api.clients;

import api.models.LoginRequest;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class LoginClient extends BaseApiClient{

    private final static String LOGIN_ENDPOINT = "/oauth/token";
    public static final LoginClient loginClient = new LoginClient();

    @Step("Отправка API-запроса на получение OAuth токена для пользователя {username}")
    public JsonPath postLogin(String username, String password, int expectedStatusCode) {
        LoginRequest payload = new LoginRequest("password", username, password);

        // Передаем статус-код параметром
        return post(LOGIN_ENDPOINT, payload, expectedStatusCode);
    }
}
