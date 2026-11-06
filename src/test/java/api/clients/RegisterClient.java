package api.clients;

import io.qameta.allure.Step;

import java.util.Map;

public class RegisterClient extends BaseApiClient{

    private final static String CHECK_USERNAME_ENDPOINT = "/api/check-username/%s";
    private final static String CHECK_EMAIL_ENDPOINT = "/api/check-email/%s";
    public static final RegisterClient registerClient = new RegisterClient();

    @Step("Отправка API-запроса на проверку {username}")
    public Map<String, Object> getCheckUserName(String username, int expectedStatusCode) {
        return getAsMap(String.format(CHECK_USERNAME_ENDPOINT, username), expectedStatusCode);
    }

    @Step("Отправка API-запроса на проверку {email}")
    public Map<String, Object> getCheckEmail(String email, int expectedStatusCode) {
        return getAsMap(String.format(CHECK_EMAIL_ENDPOINT, email), expectedStatusCode);
    }
}
