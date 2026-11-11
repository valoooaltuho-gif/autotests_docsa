package api.clients;

import api.models.LoginRequest;
import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;


public class LoginClient extends BaseApiClient{

    private final static String LOGIN_ENDPOINT = "/oauth/token";
    public static final LoginClient loginClient = new LoginClient();

    @Step("Send login api request for user {username}")
    public JsonPath postLogin(String username, String password, int expectedStatusCode) {
        LoginRequest payload = new LoginRequest("password", username, password);
        return post(LOGIN_ENDPOINT, payload, expectedStatusCode);
    }
}
