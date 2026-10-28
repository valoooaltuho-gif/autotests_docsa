package common;

import com.codeborne.selenide.Condition;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static java.time.Duration.ofSeconds;

public abstract class BasePage {

    public static String getTextXpath(String locator, int timeoutSeconds) {
        return $x(locator).shouldBe(Condition.visible, Duration.ofSeconds(timeoutSeconds)).scrollIntoView(true).getText();
    }

    public static String getTextCSS(String locator, int timeoutSeconds) {
        return $(locator).shouldBe(Condition.visible, Duration.ofSeconds(timeoutSeconds)).scrollIntoView(true).getText();
    }

    public static void clickXpath(String locator, int timeoutSeconds) {
        $x(locator).shouldBe(Condition.visible, Duration.ofSeconds(timeoutSeconds)).shouldBe(Condition.interactable, Duration.ofSeconds(timeoutSeconds)).scrollIntoCenter().click();
    }

    public static void clickCSS(String locator, int timeoutSeconds) {
        $(locator).shouldBe(Condition.visible, Duration.ofSeconds(timeoutSeconds)).shouldBe(Condition.interactable, Duration.ofSeconds(timeoutSeconds)).scrollIntoCenter().click();
    }

    public static void fillFieldXpath(String locator, int timeoutSeconds, String text) {
        $x(locator).shouldBe(Condition.visible, Duration.ofSeconds(timeoutSeconds)).shouldBe(Condition.interactable, Duration.ofSeconds(timeoutSeconds)).setValue(text);
    }

    public static void fillFieldCSS(String locator, int timeoutSeconds, String text) {
        $(locator).shouldBe(Condition.visible, Duration.ofSeconds(timeoutSeconds)).shouldBe(Condition.interactable, Duration.ofSeconds(timeoutSeconds)).setValue(text);
    }

    public static void checkTextXpath(String locator, int timeoutSeconds, String text) {
        $x(locator).shouldBe(Condition.visible, Duration.ofSeconds(timeoutSeconds)).shouldHave(Condition.text(text));
    }

    public static void checkTextCSS(String locator, int timeoutSeconds, String text) {
        $(locator).shouldBe(Condition.visible, Duration.ofSeconds(timeoutSeconds)).shouldHave(Condition.text(text));
    }

    public static void checkElementCSSShouldNot(String locator, int timeoutSeconds) {
        $(locator).shouldNotBe(visible, ofSeconds(timeoutSeconds));
    }

    public static void checkElementXpathShouldNot(String locator, int timeoutSeconds) {
        $x(locator).shouldNotBe(visible, ofSeconds(timeoutSeconds));
    }

    public static void checkElementCSSShould(String locator, int timeoutSeconds) {
        $(locator).shouldBe(visible, ofSeconds(timeoutSeconds));
    }

    public static void checkElementXpathShould(String locator, int timeoutSeconds) {
        $x(locator).shouldBe(visible, ofSeconds(timeoutSeconds));
    }

    public static void checkTextCSSInList(String locator, int timeoutSeconds, String text) {
        $$(locator).findBy(text(text)).shouldBe(visible, Duration.ofSeconds(timeoutSeconds));
    }

    public static void checkTextXpathInList(String locator, int timeoutSeconds, String text) {
        $$x(locator).findBy(text(text)).shouldBe(visible, Duration.ofSeconds(timeoutSeconds));
    }

    public static void pressOnElementCSSInListByIndex(String locator, int timeoutSeconds, int index) {
        $$(locator).get(index).shouldBe(visible, Duration.ofSeconds(timeoutSeconds)).scrollIntoCenter().click();
    }

    public static void pressOnElementCSSInListByText(String locator, int timeoutSeconds, String text) {
        $$(locator).find(Condition.text(text)).shouldBe(visible, Duration.ofSeconds(timeoutSeconds)).scrollIntoCenter().click();
    }

    public static void pressOnElementXpathInListByText(String locator, int timeoutSeconds, String text) {
        $$x(locator).find(Condition.text(text)).shouldBe(visible, Duration.ofSeconds(timeoutSeconds)).scrollIntoCenter().click();
    }

    public static <T extends BasePage> T initializeClassObject(Class<T> expectedPageClass) {
        try {
            return expectedPageClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Could not initialize class object: " + expectedPageClass.getSimpleName(), e);
        }
    }
}
