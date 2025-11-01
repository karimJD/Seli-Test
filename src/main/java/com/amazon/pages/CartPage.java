package com.amazon.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import commonLibs.Interactions;

public class CartPage {

    private WebDriver driver;
    private Interactions elementControl;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        elementControl = new Interactions(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean isProductAddedToCart() {
        return driver.getPageSource().contains("Produit ajouté au panier avec succès");
    }

    public void openCart() throws Exception {
        WebElement cartIcon = driver.findElement(By.cssSelector("a[href*='panier']"));
        elementControl.clickElement(cartIcon);
    }

    public void removeProductFromCart() throws Exception {
        WebElement deleteButton = driver.findElement(By.cssSelector(".cart-item .remove-from-cart"));
        elementControl.clickElement(deleteButton);
        Thread.sleep(2000);
    }
    public boolean isCartEmpty() {
        String page = driver.getPageSource().toLowerCase();
        return page.contains("il n'y a plus d'articles dans votre panier");
    }

}
