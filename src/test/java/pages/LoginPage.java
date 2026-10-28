package pages;

import com.codeborne.selenide.Selenide;
import common.BasePage;
import io.qameta.allure.Step;

import static common.BasePage.*;

public class LoginPage extends BasePage{
    private final static String PASSWORD_FIELD = "//input[@id='password']";
    private final static String NAME_FIELD = "//input[@id='username']";
    private final static String LOGIN_BTN = "//button[@type='submit']";
    private final static String ERROR_MESSAGE_FIELD = "//div[@role='alert']/span";
    public final static String ERROR_MESSAGE = "Invalid username or password. Please check your credentials.";
    public static final LoginPage loginPage = Selenide.page(LoginPage.class);


    @Step("Fill password field")
    public LoginPage fillPassword(String password) {
        fillFieldXpath(PASSWORD_FIELD, 5, password);
        return loginPage;
    }

    @Step("Fill name field")
    public LoginPage fillName(String name) {
        fillFieldXpath(NAME_FIELD, 5, name);
        return loginPage;
    }

    @Step("Press login button")
    public <T extends BasePage> T pressLoginBtn(Class<T> expectedPageClass) {
        clickXpath(LOGIN_BTN, 5);
        return initializeClassObject(expectedPageClass);
    }


    @Step("Press login button")
    public LoginPage checkErrorMessage(String error) {
        checkTextXpath(ERROR_MESSAGE_FIELD, 5, error);
        return loginPage;
    }
}
