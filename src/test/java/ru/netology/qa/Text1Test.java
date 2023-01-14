package ru.netology.qa;

import io.appium.java_client.android.AndroidDriver;
import org.junit.jupiter.api.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import ru.netology.qa.screens.MainScreen;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.TestInstance.Lifecycle;


@TestInstance(Lifecycle.PER_CLASS)
public class Text1Test {

    private AndroidDriver driver;

    @BeforeAll
    public void createDriver() throws MalformedURLException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("platformName", "android");
        desiredCapabilities.setCapability("diveceName", "someName");
        desiredCapabilities.setCapability("appPackage", "ru.netology.testing.uiautomator");
        desiredCapabilities.setCapability("appActivity", "ru.netology.testing.uiautomator.MainActivity");

        URL remoteURL = new URL("http://127.0.0.1:4723/wd/hub");

        driver = new AndroidDriver(remoteURL,desiredCapabilities);
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
        driver.quit();
        }
    }

