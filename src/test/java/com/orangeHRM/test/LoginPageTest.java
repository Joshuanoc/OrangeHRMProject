package com.orangeHRM.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;

public class LoginPageTest extends BaseClass {

    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeMethod
    public void setupPages() {
        loginPage = new LoginPage();
        homePage = new HomePage();
    }

    @Test
    public void verifyLoginPage() {
        loginPage.login("Admin", "admin123");
        Assert.assertTrue(homePage.isAdminTabDisplayed(), "Admin tab is not displayed");
    }

    @Test
    public void verifyInvalidLogin() {
        loginPage.login("admin", "wrongpassword");
        String expectedErrorMessage = "Invalid credentials";
        Assert.assertTrue(loginPage.verifyErrorMessage(expectedErrorMessage), "Error message does not match!");
    }
}
