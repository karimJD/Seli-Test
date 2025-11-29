package com.seli.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SeliProductPage {

    WebDriver driver;

    @FindBy(css = "button.single_add_to_cart_button")
    private WebElement addToCartButton;

    @FindBy(css = "h1.product_title")
    private WebElement productTitle;

    @FindBy(css = "table.variations select")
    private java.util.List<WebElement> variationSelects;

    @FindBy(css = "ul.variable-items-wrapper li")
    private java.util.List<WebElement> variationSwatches;

    public SeliProductPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void selectFirstOption() {
        // Handle dropdowns
        for (WebElement selectElement : variationSelects) {
            try {
                if (selectElement.isDisplayed()) {
                    org.openqa.selenium.support.ui.Select select = new org.openqa.selenium.support.ui.Select(selectElement);
                    select.selectByIndex(1); // Select first available option (index 1 usually)
                }
            } catch (Exception e) {
                // Ignore
            }
        }

        // Handle swatches
        for (WebElement swatch : variationSwatches) {
            try {
                if (swatch.isDisplayed()) {
                    swatch.click();
                }
            } catch (Exception e) {
                // Ignore
            }
        }
    }

    public void addToCart() throws Exception {
        selectFirstOption();
        Thread.sleep(1000); // Wait for option selection to process
        addToCartButton.click();
    }

    public boolean isProductTitleVisible() {
        return productTitle.isDisplayed();
    }
}
