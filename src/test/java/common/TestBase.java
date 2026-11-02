package common;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.codeborne.selenide.Configuration.baseUrl;
import static common.Constants.*;
import static common.PropertiesReader.getProperty;

public class TestBase extends BasePage{

    @BeforeAll
    public static void setUp(TestInfo testInfo) throws Exception {
        Configuration.browser = "chrome";
        Configuration.browserCapabilities = getChromeOptions();
        Configuration.timeout = 20000;
        Configuration.pageLoadTimeout = 30000;


        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(true));

        setupEnvironment();
    }

    @BeforeEach
    public void initTest() {
        Selenide.open(baseUrl);
    }

    private static void setupEnvironment() {
        Environment env = getEnv();
        switch (env) {
            case prod -> setEnvUrls(PROD_URL, PROD_URI);
            case stg -> setEnvUrls(STG_URL, STG_URI);
        }
    }

    public static Environment getEnv() {
        String environment = getProperty("environment");

        // Если параметр не передан снаружи (или пришел плейсхолдер), берем dev по умолчанию
        if (environment == null || environment.contains("${environment}")) {
            return Environment.stg;
        }

        return Environment.valueOf(environment.trim().toLowerCase());
    }

    private static void setEnvUrls(String webUrl, String apiUri) {
        Configuration.baseUrl = webUrl;
        RestAssured.baseURI = apiUri;
    }

    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        if (isBackground()) options.addArguments("--headless");
        //if (shouldUseDevTools()) options.addArguments("--auto-open-devtools-for-tabs");

        return options
                .addArguments("--window-size=1280,851")
                .addArguments("--disable-gpu")
                .addArguments("--disable-popup-blocking")
                .addArguments("--disable-dev-shm-usage")
                .addArguments("--no-sandbox")
                .addArguments("--remote-allow-origins=*")
                .addArguments("--incognito")
                .addArguments("--disable-notifications");
    }

    private static Boolean isBackground() {
        return !"false".equals(getProperty("isBackground"));
    }

    private static Boolean shouldUseDevTools() {
        return !"false".equals(getProperty("devTools"));
    }

}
