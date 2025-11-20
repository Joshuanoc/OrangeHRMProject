package com.orangehrm.actiondriver;

import java.time.Duration;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.orangehrm.base.BaseClass;
import com.orangehrm.utilities.LoggerManager;

public class ActionDriver {

    private WebDriver driver;
    private WebDriverWait wait;
    public static final Logger logger = LoggerManager.getLogger(ActionDriver.class);

    // Existing constructor
    public ActionDriver(WebDriver driver) {
        this.driver = driver;
        initWait();
    }

    // ✅ New no-arg constructor to use BaseClass ThreadLocal WebDriver
    public ActionDriver() {
        this.driver = BaseClass.getDriver();
        initWait();
    }

    private void initWait() {
        int explicitWait = Integer.parseInt(BaseClass.getProp().getProperty("explicitWait"));
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));
        logger.info("WebDriver instance is created and WebDriverWait initialized");
    }

    // click action
    public void click(By by) {
        int attempts = 0;
        while (attempts < 3) {
            try {
                waitForElementToBeClickable(by);
                driver.findElement(by).click();
                logger.info("Clicked an element");
                break;
            } catch (Exception e) {
                attempts++;
                if (attempts == 3) {
                    logger.error("Unable to click on element after retries: " + e.getMessage());
                }
            }
        }
    }

    // enter text
    public void enterText(By by, String value) {
        try {
            waitForElementToBeVisible(by);
            driver.findElement(by).clear();
            driver.findElement(by).sendKeys(value);
            logger.info("Entered text: " + value);
        } catch (Exception e) {
            logger.error("Unable to enter text: " + e.getMessage());
        }
    }

    // get text
    public String getText(By by) {
        try {
            waitForElementToBeVisible(by);
            return driver.findElement(by).getText();
        } catch (Exception e) {
            logger.error("Unable to get text from element: " + e.getMessage());
            return "";
        }
    }

    // is displayed
    public boolean isDisplayed(By by) {
        try {
            waitForElementToBeVisible(by);
            boolean displayed = driver.findElement(by).isDisplayed();
            if (displayed) logger.info("Element is displayed");
            return displayed;
        } catch (Exception e) {
            logger.error("Unable to verify if element is displayed: " + e.getMessage());
            return false;
        }
    }

    // scroll to element
    public void scrollToElement(By by) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            waitForElementToBeVisible(by);
            js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(by));
        } catch (Exception e) {
            logger.error("Unable to scroll to element: " + e.getMessage());
        }
    }

    private void waitForElementToBeClickable(By by) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(by));
        } catch (Exception e) {
            logger.error("Element not clickable: " + e.getMessage());
        }
    }

    private void waitForElementToBeVisible(By by) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (Exception e) {
            logger.error("Element not visible: " + e.getMessage());
        }
    }
}
