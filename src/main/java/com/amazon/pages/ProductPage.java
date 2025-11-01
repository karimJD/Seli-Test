package com.amazon.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import commonLibs.Interactions;

public class ProductPage {

    private WebDriver driver;
    	private WebDriverWait wait;
    private Interactions elementControl;

    @CacheLookup
    @FindBy(css = "button.add-to-cart")
    private WebElement addToCartButton;

   

    // Bouton dans la modale (ex : Continuer mes achats)
	@FindBy(xpath = "//div[@id='blockcart-modal']//button[contains(@class,'btn-primary') and @data-dismiss='modal']")
	private WebElement continueShoppingButton;
    
   /*  @FindBy(css = "btn btn-block btn-primary")
    private WebElement ContinueButton;*/

    @FindBy(css = "h1.h1")
    private WebElement productTitle;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(15));
        elementControl = new Interactions(driver);
        PageFactory.initElements(driver, this);
    }

    public void addToCart() throws Exception {
    // 🔹 1️⃣ Cliquer sur le bouton "Ajouter au panier"
  //  System.out.println("****0*****");
    elementControl.clickElement(addToCartButton);
 // System.out.println("****1*****");
    // 🔹 2️⃣ Attendre l’apparition de la modale du panier
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
  //    System.out.println("****2*****");
    By modalLocator = By.id("blockcart-modal");
    wait.until(ExpectedConditions.visibilityOfElementLocated(modalLocator));
 // System.out.println("****3*****");
    // 🔹 3️⃣ Attendre que le bouton "Continuer mes achats" soit cliquable
    wait.until(ExpectedConditions.elementToBeClickable(continueShoppingButton));
 // System.out.println("****4*****");
    // 🔹 4️⃣ Clic sur le bouton "Continuer mes achats"
    continueShoppingButton.click();
//  System.out.println("****5*****");
    // 🔹 5️⃣ Attendre la fermeture complète de la fenêtre modale
    wait.until(ExpectedConditions.invisibilityOfElementLocated(modalLocator));

    System.out.println("✅ Produit ajouté au panier et fenêtre modale fermée avec succès.");
}


    public boolean isProductTitleVisible() {
        return productTitle.isDisplayed();
    }
}
