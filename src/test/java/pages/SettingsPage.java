package pages;

import com.codeborne.selenide.Selenide;
import common.BasePage;
import io.qameta.allure.Step;

import static common.BasePage.checkElementXpathShould;

public class SettingsPage extends BasePage {
    private final static String SETTING_BTN = "//a[@href='/personal/settings']";
    public static final SettingsPage settingsPage = Selenide.page(SettingsPage.class);

    @Step("Check if button Settings visible")
    public SettingsPage checkSettingsButtonVisible() {
        checkElementXpathShould(SETTING_BTN, 5);
        return settingsPage;
    }
}
