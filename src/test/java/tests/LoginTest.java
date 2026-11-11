package tests;

import api.clients.LoginClient;
import common.TestBase;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import pages.LoginPage;
import pages.SettingsPage;


import static api.clients.LoginClient.*;
import static api.clients.LoginClient.AuthResponseField.*;
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
    @Description("Valid login via API")
    public void successLoginApiTest() {
        JsonPath response = loginClient.postLogin("valentin", "a789456A@",200);

        String accessToken = response.getString(access_token.val);
        String tokenType = response.getString(token_type.val);
        String expiresIn_ = response.getString(expires_in.val);
        String refreshToken_ = response.getString(refresh_token.val);
        String scope_ = response.getString(scope.val);
        String sessionId = response.getString(session_id.val);

        Assertions.assertNotNull(accessToken);
        Assertions.assertTrue(accessToken.matches("^[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+$"));
        Assertions.assertEquals("Bearer", tokenType);

        Assertions.assertNotNull(expiresIn_);
        Assertions.assertEquals("86400", expiresIn_);

        Assertions.assertNotNull(refreshToken_);
        Assertions.assertEquals(64, refreshToken_.length());

        Assertions.assertNotNull(scope_);
        Assertions.assertEquals("read write", scope_);

        Assertions.assertNotNull(sessionId);
        Assertions.assertTrue(sessionId.matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$"));
    }

    @Test
    @Tag("API")
    @Tag("LOGIN")
    @Description("Invalid login via API")
    public void loginWithInvalidPasswordApiTest() {
        JsonPath errorResponse = loginClient.postLogin("valentin", "a789456A@d", 401);

        String error = errorResponse.getString(AuthResponseField.error.val);
        String errorDescription = errorResponse.getString(AuthResponseField.error_description.val);

        Assertions.assertNotNull(error);
        Assertions.assertEquals(ERROR_API_INVALID_GRANT, error);
        Assertions.assertEquals(ERROR_API_MESSAGE_INCORRECT_USERNAME_OR_PASSWORD, errorDescription);
    }

}
