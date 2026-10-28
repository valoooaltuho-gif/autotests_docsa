package tests;

import common.TestBase;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import pages.LoginPage;
import pages.SettingsPage;

import static pages.LoginPage.ERROR_MESSAGE;
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
                .checkErrorMessage(ERROR_MESSAGE);
    }
}
