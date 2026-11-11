package api.clients;

import io.qameta.allure.Step;

import java.util.Map;

public class RegisterClient extends BaseApiClient{

    public enum UsernameResponseField {
        username("username"),
        available("available"),
        message("message");

        public final String val;

        UsernameResponseField(String val) {
            this.val = val;
        }
    }

    public enum EmailResponseField {
        email("email"),
        available("available"),
        message("message");

        public final String val;

        EmailResponseField(String val) {
            this.val = val;
        }
    }


    private final static String CHECK_USERNAME_ENDPOINT = "/api/check-username/%s";
    private final static String CHECK_EMAIL_ENDPOINT = "/api/check-email/%s";
    public final static String USERNAME_IS_ALREADY_TAKEN = "Username is already taken";
    public final static String EMAIL_IS_ALREADY_REGISTERED = "Email is already registered";
    public final static String EMAIL_IS_AVAILABLE = "Email is available";
    public final static String USERNAME_IS_AVAILABLE = "Username is available";
    public static final RegisterClient registerClient = new RegisterClient();

    @Step("Send API-request for username check {username}")
    public Map<String, Object> getCheckUserName(String username, int expectedStatusCode) {
        return getAsMap(String.format(CHECK_USERNAME_ENDPOINT, username), expectedStatusCode);
    }

    @Step("Send API-request for email check {email}")
    public Map<String, Object> getCheckEmail(String email, int expectedStatusCode) {
        return getAsMap(String.format(CHECK_EMAIL_ENDPOINT, email), expectedStatusCode);
    }
}
