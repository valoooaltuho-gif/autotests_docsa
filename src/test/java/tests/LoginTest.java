package tests;

import common.TestBase;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;

import static pages.LoginPage.loginPage;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginTest extends TestBase {

    @Tag("LOGIN")
    @Order(1)
    @Description("Login with valid data")
    @Story("Log in")
    @Test
    public void loginWithValidData() {
        loginPage
                .fillName("valentin")
                .fillPassword("a789456A@")
                .pressLoginBtn()
                .checkSettingsButtonVisible();
    }
}
