package com.orangehrm.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.utilities.LoggerManager;

public class BaseClass {

    protected static Properties prop;

    // ThreadLocal for parallel execution
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static ThreadLocal<ActionDriver> actionDriver = new ThreadLocal<>();

    public static final Logger logger = LoggerManager.getLogger(BaseClass.class);

    @BeforeSuite
    public void loadConfig() throws IOException {
        prop = new Properties();
        FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
        try {
            prop.load(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Configuration file loaded successfully.");
    }

    @BeforeMethod
    public void setup() throws IOException {
        System.out.println("Setting up the test environment..." + this.getClass().getSimpleName());
        loadConfig();
        launchBrowser();
        configureBrowser();
        staticWait(2);
        logger.info("WebDriver Initialized and Browser Maximized.");

        // Initialize ActionDriver for the current thread
        if (actionDriver.get() == null) {
            actionDriver.set(new ActionDriver(getDriver()));
            logger.info("ActionDriver initialized for thread: " + Thread.currentThread().getId());
        }
    }

    // Launch browser based on config
    public void launchBrowser() throws IOException {
        String browser = prop.getProperty("browser");

        if (browser.equalsIgnoreCase("chrome")) {
            driver.set(new ChromeDriver());
            logger.info("Chrome Browser Launched.");
        } else if (browser.equalsIgnoreCase("firefox")) {
            driver.set(new FirefoxDriver());
            logger.info("Firefox Browser Launched.");
        } else if (browser.equalsIgnoreCase("edge")) {
            driver.set(new EdgeDriver());
            logger.info("Edge Browser Launched.");
        } else {
            throw new IllegalArgumentException("Invalid browser value in config.properties file: " + browser);
        }
    }

    // Configure browser settings
    private void configureBrowser() {
        driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get().manage().window().maximize();

        try {
            driver.get().get(prop.getProperty("url"));
        } catch (Exception e) {
            System.out.println("Unable to navigate to the URL: " + e.getMessage());
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver.get() != null) {
            try {
                driver.get().quit();
            } catch (Exception e) {
                System.out.println("Error occurred while closing the browser: " + e.getMessage());
            } finally {
                // Remove ThreadLocal instances to prevent memory leaks
                driver.remove();
                actionDriver.remove();
            }
        }
        logger.info("Browser closed and WebDriver session ended.");
    }

    // Thread-safe getters
    public static WebDriver getDriver() {
        if (driver.get() == null) {
            throw new IllegalStateException("WebDriver instance is not initialized.");
        }
        return driver.get();
    }

    public static ActionDriver getActionDriver() {
        if (actionDriver.get() == null) {
            throw new IllegalStateException("ActionDriver instance is not initialized.");
        }
        return actionDriver.get();
    }

    // Load properties
    public static Properties getProp() {
        return prop;
    }

    // Static wait utility
    public void staticWait(int seconds) {
        LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds));
    }
}
