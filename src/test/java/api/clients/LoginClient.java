package api.clients;

import api.models.LoginRequest;
import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;


public class LoginClient extends BaseApiClient{

    public enum AuthResponseField {
        error("error"),
        error_description("error_description"),
        token_type("token_type"),
        expires_in("expires_in"),
        refresh_token("refresh_token"),
        scope("scope"),
        session_id("session_id"),
        access_token("access_token");

        // Публичное поле, которое сразу хранит чистую строку String
        public final String val;

        AuthResponseField(String val) {
            this.val = val;
        }
    }

    private final static String LOGIN_ENDPOINT = "/oauth/token";
    public final static String ERROR_API_MESSAGE_INCORRECT_USERNAME_OR_PASSWORD = "Invalid username or password";
    public final static String ERROR_API_INVALID_GRANT = "invalid_grant";
    public static final LoginClient loginClient = new LoginClient();


    @Step("Send login api request for user {username}")
    public JsonPath postLogin(String username, String password, int expectedStatusCode) {
        LoginRequest payload = new LoginRequest("password", username, password);
        return post(LOGIN_ENDPOINT, payload, expectedStatusCode);
    }
}
