package ru.netology.qa;

import io.appium.java_client.AppiumDriver;
import org.junit.jupiter.api.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import ru.netology.qa.screens.MainScreen;


import java.net.MalformedURLException;
import java.net.URL;

import static io.appium.java_client.remote.AndroidMobileCapabilityType.APP_ACTIVITY;
import static io.appium.java_client.remote.AndroidMobileCapabilityType.APP_PACKAGE;
import static io.appium.java_client.remote.MobileCapabilityType.DEVICE_NAME;
import static org.junit.jupiter.api.TestInstance.Lifecycle;
import static org.openqa.selenium.remote.CapabilityType.PLATFORM_NAME;


@TestInstance(Lifecycle.PER_CLASS)
public class Text1Test {

    private AppiumDriver driver;

    @BeforeAll
    public void createDriver() throws MalformedURLException {
        String platform = System.getProperty("platform");
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        if ("android".equals(platform)) {
            desiredCapabilities.setCapability(PLATFORM_NAME, "android");
            desiredCapabilities.setCapability(DEVICE_NAME, "someName");
            desiredCapabilities.setCapability(APP_PACKAGE, "ru.netology.testing.uiautomator");
            desiredCapabilities.setCapability(APP_ACTIVITY, "ru.netology.testing.uiautomator.MainActivity");
        } else {
            throw new IllegalArgumentException(String.format("Platform %s no supported", platform));
        }

        driver = new AppiumDriver(new URL("http://127.0.0.1:4723/wd/hub"), desiredCapabilities);
    }

    @Test
    public void textSendEmptyFieldTest() {
        MainScreen mainscreen = new MainScreen(driver);
        String initText = mainscreen.textToBeChanged.getText();

        mainscreen.userInput.click();
        mainscreen.userInput.clear();
        mainscreen.buttonChange.click();

        String result = mainscreen.textToBeChanged.getText();

        Assertions.assertEquals(initText, result);
    }

    @Test
    public void textSendInAnotherActivityTest() {
        MainScreen mainscreen = new MainScreen(driver);
        String initText = "Promise";

        mainscreen.userInput.click();
        mainscreen.userInput.setValue(initText);
        mainscreen.buttonActivity.click();
        Assertions.assertEquals(initText, mainscreen.newActivityText.getText());

    }


    @AfterAll
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
