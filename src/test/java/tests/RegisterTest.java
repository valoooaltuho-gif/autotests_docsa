package tests;

import api.clients.RegisterClient.UsernameResponseField;
import common.TestBase;
import api.clients.RegisterClient;
import io.qameta.allure.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.util.Map;

import static api.clients.RegisterClient.*;
import static api.clients.RegisterClient.EmailResponseField.email;
import static api.clients.RegisterClient.UsernameResponseField.available;
import static api.clients.RegisterClient.UsernameResponseField.username;
import static api.clients.RegisterClient.UsernameResponseField.message;

class RegisterApiTest extends TestBase {

    private final RegisterClient registerClient = new RegisterClient();

    @Test
    @Tag("API")
    @Tag("REGISTER")
    @Description("Check taken username via API")
    public void checkAlreadyTakenUsernameApiTest() {
        String targetUsername = "valentin";
        Map<String, Object> response = registerClient.getCheckUserName(targetUsername, 200);

        String username_ = (String) response.get(username.val);
        Boolean available_ = (Boolean) response.get(available.val);
        String message_ = (String) response.get(message.val);

        Assertions.assertEquals(targetUsername, username_);
        Assertions.assertFalse(available_);
        Assertions.assertEquals(USERNAME_IS_ALREADY_TAKEN, message_);
    }

    @Test
    @Tag("API")
    @Tag("REGISTER")
    @Description("Check not taken username via API")
    public void checkNotTakenUsernameApiTest() {
        String targetUsername = "valentin123";
        Map<String, Object> response = registerClient.getCheckUserName(targetUsername, 200);

        String username_ = (String) response.get(username.val);
        Boolean available_ = (Boolean) response.get(available.val);
        String message_ = (String) response.get(message.val);

        Assertions.assertEquals(targetUsername, username_);
        Assertions.assertTrue(available_);
        Assertions.assertEquals(USERNAME_IS_AVAILABLE, message_);
    }

    @Test
    @Tag("API")
    @Tag("REGISTER")
    @Description("Check not taken email via API")
    public void checkNotTakenEmailApiTest() {
        String targetEmail = "valentin@mail.ru";
        Map<String, Object> response = registerClient.getCheckEmail(targetEmail, 200);

        String email_ = (String) response.get(email.val);
        Boolean available_ = (Boolean) response.get(available.val);
        String message_ = (String) response.get(message.val);

        Assertions.assertEquals(targetEmail, email_);
        Assertions.assertTrue(available_);
        Assertions.assertEquals(EMAIL_IS_AVAILABLE, message_);
    }

    @Test
    @Tag("API")
    @Tag("REGISTER")
    @Description("Check taken email via API")
    public void checkTakenEmailApiTest() {
        String targetEmail = "valoooaltuho@gmail.com";
        Map<String, Object> response = registerClient.getCheckEmail(targetEmail, 200);

        String email_ = (String) response.get(email.val);
        Boolean available_ = (Boolean) response.get(available.val);
        String message_ = (String) response.get(message.val);

        Assertions.assertEquals(targetEmail, email_);
        Assertions.assertFalse(available_);
        Assertions.assertEquals(EMAIL_IS_ALREADY_REGISTERED, message_);
    }
}
