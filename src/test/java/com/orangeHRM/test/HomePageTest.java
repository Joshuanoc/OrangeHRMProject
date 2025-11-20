package com.orangeHRM.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;

public class HomePageTest extends BaseClass {

    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeMethod
    public void setupPages() {
        loginPage = new LoginPage();
        homePage = new HomePage();
    }

    @Test
    public void verifyOrangeHRMLogo() {
        loginPage.login("Admin", "admin123");
        Assert.assertTrue(homePage.isOrangeHRMLogoDisplayed(), "OrangeHRM logo is not displayed");
    }
}
