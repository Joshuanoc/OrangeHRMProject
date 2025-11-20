package com.orangehrm.pages;

import org.openqa.selenium.By;
import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;

public class HomePage {

    private ActionDriver actionDriver;

    private By adminTab = By.xpath("//span[normalize-space()='Admin']");
    private By orangeHRMLogo = By.cssSelector("img[alt='client brand banner']");
    public HomePage() {
        this.actionDriver = BaseClass.getActionDriver();
    }

    public boolean isAdminTabDisplayed() {
        return actionDriver.isDisplayed(adminTab);
    }

    public boolean isOrangeHRMLogoDisplayed() {
        return actionDriver.isDisplayed(orangeHRMLogo);
    }
}
