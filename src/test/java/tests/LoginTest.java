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


import static api.clients.LoginClient.loginClient;
import static pages.LoginPage.ERROR_MESSAGE_INCORRECT_USERNAME_OR_PASSWORD;
import static pages.LoginPage.loginPage;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Execution(ExecutionMode.CONCURRENT)
public class LoginTest extends TestBase {

    @Tag("LOGIN")
    @Description("Login with valid data")
    @Story("Log in")
    @Test
    public void loginWithValidData() {
        loginPage
                .fillName("valentin")
                .fillPassword("a789456A@")
                .pressLoginBtn(SettingsPage.class)
                .checkSettingsButtonVisible();
    }

    @Tag("LOGIN")
    @Description("Login with invalid password")
    @Story("Log in")
    @Test
    public void loginWithInvalidPassword() {
        loginPage
                .fillName("valentin")
                .fillPassword("a789456A@1")
                .pressLoginBtn(LoginPage.class)
                .checkErrorMessage(ERROR_MESSAGE_INCORRECT_USERNAME_OR_PASSWORD);
    }

    @Test
    @Tag("API")
    @Tag("LOGIN")
    @Description("Успешное получение OAuth токена через API")
    public void successLoginApiTest() {
        JsonPath response = loginClient.postLogin("valentin", "a789456A@",200);

        String accessToken = response.getString("access_token");
        String tokenType = response.getString("token_type");
        String expiresIn = response.getString("expires_in");
        String refreshToken = response.getString("refresh_token");
        String scope = response.getString("scope");
        String sessionId = response.getString("session_id");

        Assertions.assertNotNull(accessToken);
        Assertions.assertTrue(accessToken.matches("^[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+$"));
        Assertions.assertEquals("Bearer", tokenType);

        Assertions.assertNotNull(expiresIn);
        Assertions.assertEquals("86400", expiresIn);

        Assertions.assertNotNull(refreshToken);
        Assertions.assertEquals(64, refreshToken.length());

        Assertions.assertNotNull(scope);
        Assertions.assertEquals("read write", scope);

        Assertions.assertNotNull(sessionId);
        Assertions.assertTrue(sessionId.matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$"));
    }

    @Test
    @Tag("API")
    @Tag("LOGIN")
    @Description("Попытка авторизации с неверным паролем (Негативный сценарий)")
    public void loginWithInvalidPasswordApiTest() {
        JsonPath errorResponse = loginClient.postLogin("valentin", "a789456A@d", 401);

        String error = errorResponse.getString("error");
        String errorDescription = errorResponse.getString("error_description");

        Assertions.assertNotNull(error);
        Assertions.assertEquals("invalid_grant", error);
        Assertions.assertEquals("Invalid username or password", errorDescription);
    }

}
