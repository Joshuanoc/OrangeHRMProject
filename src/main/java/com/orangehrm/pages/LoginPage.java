package com.orangehrm.pages;

import org.openqa.selenium.By;
import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;

public class LoginPage {

    private ActionDriver actionDriver;

    private By usernameField = By.name("username");
    private By passwordField = By.cssSelector("input[type='password']");
    private By loginButton = By.xpath("//button[@type='submit']");
    private By errorMessage = By.xpath("//p[text()='Invalid credentials']");

    public LoginPage() {
        this.actionDriver = BaseClass.getActionDriver();
    }

    public void login(String username, String password) {
        actionDriver.enterText(usernameField, username);
        actionDriver.enterText(passwordField, password);
        actionDriver.click(loginButton);
    }

    public boolean isErrorMessageDisplayed() {
        return actionDriver.isDisplayed(errorMessage);
    }

    public String getErrorMessageText() {
        return actionDriver.getText(errorMessage);
    }

    public boolean verifyErrorMessage(String expectedMessage) {
        return getErrorMessageText().equals(expectedMessage);
    }
}
