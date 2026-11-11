package common;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.codeborne.selenide.Configuration.baseUrl;
import static common.Constants.*;
import static common.PropertiesReader.getProperty;
import static common.PropertiesReader.readPropertiesFile;

public class TestBase extends BasePage{

    public static RequestSpecification requestSpec;

    @BeforeAll
    public static void setUp() throws Exception {
        readPropertiesFile();
        Configuration.browser = "chrome";
        Configuration.browserCapabilities = getChromeOptions();
        Configuration.timeout = 20000;
        Configuration.pageLoadTimeout = 30000;


        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(true));

        setupEnvironment();
        initApiSpecification();
    }

    private static void initApiSpecification() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri(RestAssured.baseURI) // Берется из DEV_URI в зависимости от env
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addHeader("Accept-Language", "ru-RU,ru;q=0.9")
                .build();
    }

    @BeforeEach
    public void initTest(TestInfo testInfo) {
        boolean isApiTest = testInfo.getTags().contains("API");

        if (!isApiTest) {
            Selenide.open(baseUrl);
        }

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

    @AfterAll
    public static void tearDown() {
        Selenide.closeWebDriver();
    }

    @AfterEach
    public void afterEachTest() {
        Selenide.closeWebDriver();
    }

}
