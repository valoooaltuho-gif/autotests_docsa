package tests;

import common.TestBase;
import io.qameta.allure.Description;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.Map;

import static api.clients.RegisterClient.registerClient;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Execution(ExecutionMode.CONCURRENT)
public class RegisterTest extends TestBase {

    @Test
    @Tag("API")
    @Tag("REGISTER")
    @Description("Check taken username via API")
    public void checkAlreadyTakenUsernameApiTest() {
        String targetUsername = "valentin";
        Map<String, Object> response = registerClient.getCheckUserName(targetUsername, 200);

        String username = (String) response.get("username");
        Boolean available = (Boolean) response.get("available");
        String message = (String) response.get("message");

        Assertions.assertEquals(targetUsername, username);
        Assertions.assertFalse(available);
        Assertions.assertEquals("Username is already taken", message);
    }

    @Test
    @Tag("API")
    @Tag("REGISTER")
    @Description("Check not taken username via API")
    public void checkNotTakenUsernameApiTest() {
        String targetUsername = "valentin123";
        Map<String, Object> response = registerClient.getCheckUserName(targetUsername, 200);

        String username = (String) response.get("username");
        Boolean available = (Boolean) response.get("available");
        String message = (String) response.get("message");

        Assertions.assertEquals(targetUsername, username);
        Assertions.assertTrue(available);
        Assertions.assertEquals("Username is available", message);
    }

    @Test
    @Tag("API")
    @Tag("REGISTER")
    @Description("Check taken email via API")
    public void checkNotTakenEmailApiTest() {
        String targetEmail = "valentin@mail.ru";
        Map<String, Object> response = registerClient.getCheckEmail(targetEmail, 200);

        String email = (String) response.get("email");
        Boolean available = (Boolean) response.get("available");
        String message = (String) response.get("message");

        Assertions.assertEquals(targetEmail, email);
        Assertions.assertTrue(available);
        Assertions.assertEquals("Email is available", message);
    }

    @Test
    @Tag("API")
    @Tag("REGISTER")
    @Description("Check not taken email via API")
    public void checkTakenEmailApiTest() {
        String targetEmail = "valoooaltuho@gmail.com";
        Map<String, Object> response = registerClient.getCheckEmail(targetEmail, 200);

        String email = (String) response.get("email");
        Boolean available = (Boolean) response.get("available");
        String message = (String) response.get("message");

        Assertions.assertEquals(targetEmail, email);
        Assertions.assertFalse(available);
        Assertions.assertEquals("Email is already registered", message);
    }

}
