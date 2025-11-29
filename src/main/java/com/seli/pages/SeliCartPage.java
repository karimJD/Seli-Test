package com.seli.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SeliCartPage {

    WebDriver driver;
    WebDriverWait wait;

    public SeliCartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Cart product name (first product in cart)
    @FindBy(css = "a.wc-block-cart-item__link")
    private WebElement cartProductName;

    // Remove button for first product
    @FindBy(css = "button.wc-block-cart-item__remove-link")
    private WebElement removeButton;

    // Empty cart message
    @FindBy(css = "h2.wp-block-heading.wc-block-cart__empty-cart__title")
    private WebElement emptyCartMessage;

    // Cart counter (header)
    @FindBy(css = "span.cart-items-count.count.header-icon-counter")
    private WebElement cartCounter;

    /** Check if at least one product is in the cart */
    public boolean isProductAddedToCart() {
        try {
            wait.until(ExpectedConditions.visibilityOf(cartProductName));
            return cartProductName.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /** Remove the first product from the cart */
    public void removeProductFromCart() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(removeButton));
            removeButton.click();
        } catch (Exception e) {
            // fallback to JS click
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", removeButton);
        }
    }

    /** Check if the cart is empty */
    public boolean isCartEmpty() {
        try {
            wait.until(ExpectedConditions.visibilityOf(emptyCartMessage));
            return emptyCartMessage.isDisplayed() && getCartCount() == 0;
        } catch (Exception e) {
            return false;
        }
    }

    /** Get the cart count from the header */
    public int getCartCount() {
        try {
            wait.until(ExpectedConditions.visibilityOf(cartCounter));
            String countText = cartCounter.getText().trim();
            return Integer.parseInt(countText);
        } catch (Exception e) {
            return 0;
        }
    }
}
