package pages;

import io.qameta.allure.Step;

import static common.BasePage.clickXpath;
import static common.BasePage.fillFieldXpath;

public class LoginPage {
    private final static String PASSWORD_FIELD = "//input[@id='password']";
    private final static String NAME_FIELD = "//input[@id='username']";
    private final static String LOGIN_BTN = "//button[@type='submit']";
    public final static LoginPage loginPage = new LoginPage();


    @Step("Fill password field")
    public LoginPage fillPassword(String password) {
        fillFieldXpath(PASSWORD_FIELD, 5, password);
        return this;
    }

    @Step("Fill name field")
    public LoginPage fillName(String name) {
        fillFieldXpath(NAME_FIELD, 5, name);
        return this;
    }

    @Step("Press login button")
    public SettingsPage pressLoginBtn() {
        clickXpath(LOGIN_BTN, 5);
        return new SettingsPage();
    }
}
