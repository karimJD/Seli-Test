package com.seli.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SeliLoginPage {

    WebDriver driver;

    // Username/email input
    @FindBy(id = "username")
    private WebElement emailInput;

    // Password input
    @FindBy(id = "password")
    private WebElement passwordInput;

    // Login button
    @FindBy(css = "button.woocommerce-form-login__submit")
    private WebElement loginButton;

    // Error message displayed on login failure
    @FindBy(css = ".woocommerce-error li")
    private WebElement errorMessage;

    // Forgot password link
    @FindBy(css = "p.lost_password a")
    private WebElement forgotPasswordLink;

    // Password reset email input (on reset password page)
    @FindBy(id = "user_login")
    private WebElement resetEmailInput;

    // Password reset submit button
    @FindBy(css = "button.woocommerce-Button")
    private WebElement resetPasswordButton;

    // Success message after password reset email sent
    @FindBy(css = ".woocommerce-message")
    private WebElement successMessage;

    public SeliLoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Perform login with email and password
    public void login(String email, String password) {
        emailInput.clear();
        emailInput.sendKeys(email);

        passwordInput.clear();
        passwordInput.sendKeys(password);

        loginButton.click();
    }

    // Check if the error message is visible
    public boolean isErrorDisplayed() {
        try {
            return errorMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Click on forgot password link
    public void clickForgotPassword() {
        forgotPasswordLink.click();
    }

    // Enter email on password reset page
    public void enterResetEmail(String email) {
        resetEmailInput.clear();
        resetEmailInput.sendKeys(email);
    }

    // Click reset password button
    public void clickResetPasswordButton() {
        resetPasswordButton.click();
    }

    // Check if the reset email input is displayed
    public boolean isResetEmailInputDisplayed() {
        try {
            return resetEmailInput.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Check if the success message is displayed
    public boolean isSuccessMessageDisplayed() {
        try {
            return successMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Get success message text
    public String getSuccessMessageText() {
        try {
            return successMessage.getText().trim();
        } catch (Exception e) {
            return "";
        }
    }
}
