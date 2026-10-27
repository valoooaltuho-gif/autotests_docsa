package pages;

import io.qameta.allure.Step;

import static common.BasePage.checkElementXpathShould;

public class SettingsPage {
    private final static String SETTING_BTN = "//a[@href='/personal/settings']";

    @Step("Fill password field")
    public SettingsPage checkSettingsButtonVisible() {
        checkElementXpathShould(SETTING_BTN, 5);
        return this;
    }
}
