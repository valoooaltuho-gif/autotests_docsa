package tests;

import common.TestBase;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import pages.LoginPage;
import pages.SettingsPage;

import java.util.Map;

import static api.clients.LoginClient.loginClient;
import static api.clients.RegisterClient.registerClient;
import static pages.LoginPage.ERROR_MESSAGE;
import static pages.LoginPage.loginPage;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Execution(ExecutionMode.CONCURRENT)
public class RegisterTest extends TestBase {

    @Test
    @Tag("API")
    @Tag("REGISTER")
    @Description("Проверка занятого имени пользователя (Негативный сценарий)")
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
    @Description("Проверка не занятого имени пользователя (Негативный сценарий)")
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


}
