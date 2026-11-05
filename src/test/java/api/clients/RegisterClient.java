package api.clients;

import io.qameta.allure.Step;

import java.util.Map;

public class RegisterClient extends BaseApiClient{

    private final static String CHECK_USERNAME_ENDPOINT = "/api/check-username/%s";
    public static final RegisterClient registerClient = new RegisterClient();

    @Step("Отправка API-запроса на проверку {username}")
    public Map<String, Object> getCheckUserName(String username, int expectedStatusCode) {
        // Передаем статус-код параметром
        return getAsMap(String.format(CHECK_USERNAME_ENDPOINT, username), expectedStatusCode);
    }
}
