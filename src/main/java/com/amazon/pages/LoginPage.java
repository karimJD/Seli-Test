package com.amazon.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import commonLibs.Interactions;

public class LoginPage {

    private WebDriver driver;
    private Interactions elementControl;

    @CacheLookup
    @FindBy(name = "email")
    private WebElement emailBox;

    @CacheLookup
    @FindBy(name = "password")
    private WebElement passwordBox;

    @FindBy(id = "submit-login")
    private WebElement signInButton;

    @FindBy(css = ".alert-danger")
    private WebElement errorMessage;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        elementControl = new Interactions(driver);
        PageFactory.initElements(driver, this);
    }

    public void login(String email, String password) throws Exception {
        elementControl.setText(emailBox, email);
        elementControl.setText(passwordBox, password);
        elementControl.clickElement(signInButton);
    }

    public boolean isErrorDisplayed() {
        return elementControl.isElementDisplayed(errorMessage);
    }
}
