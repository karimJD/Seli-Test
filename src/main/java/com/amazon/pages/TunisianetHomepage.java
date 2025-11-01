package com.amazon.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import commonLibs.Interactions;

public class TunisianetHomepage {

    private WebDriver driver;
     private WebDriverWait wait;
    private Actions actions;
    private Interactions elementControl;

    @CacheLookup
    @FindBy(name = "s")
    private WebElement searchBox;

    @CacheLookup
    @FindBy(css = "button[type='submit'][name='submit_search']")
    private WebElement searchButton;

    @FindBy(linkText = "promotions?srsltid=AfmBOoruXCcUIh5oZwUCMEVIEMb8GV016rC5ZP6GbZSC-rDc-J7XYprA")
    private WebElement promotionsLink;

    @FindBy(linkText = "Contactez-nous")
    private WebElement contactLink;

    @FindBy(css = "div.nav-link svg use[xlink\\:href='#huser']")
private WebElement userIcon;


  

@FindBy(xpath = "//li[contains(@class,'level-1')]//a[contains(@href,'301-pc-portable-tunisie')]")
private WebElement pcPortablesLink;



    @FindBy(id = "select-currency")
    private WebElement currencyDropdown;

    public TunisianetHomepage(WebDriver driver) {
        this.driver = driver;
        elementControl = new Interactions(driver);
        PageFactory.initElements(driver, this);
    }

    public void searchProduct(String product) throws Exception {
        elementControl.clearText(searchBox);
        elementControl.setText(searchBox, product);
        elementControl.clickElement(searchButton);
    }

    public boolean isSearchResultDisplayed(String keyword) {
        return driver.getTitle().toLowerCase().contains(keyword.toLowerCase());
    }

    public void selectFirstSearchResult() throws Exception {
        WebElement firstProduct = driver.findElement(By.xpath("(//article[contains(@class,'product-miniature')])[1]//a"));
        elementControl.clickElement(firstProduct);
    }

    public void goToPromotions() throws Exception {
        elementControl.clickElement(promotionsLink);
    }

    public boolean isPromotionsPageDisplayed() {
        return driver.getTitle().toLowerCase().contains("promotions");
    }

    public void goToLaptopsCategory() throws Exception {
  
  WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
Actions actions = new Actions(driver);

// Survoler le menu principal
WebElement menuOrdinateurs = wait.until(
    ExpectedConditions.visibilityOfElementLocated(
        By.xpath("//span[contains(text(),'Informatique')]")
    )
);
actions.moveToElement(menuOrdinateurs).perform();

// Attendre que le lien "PC Portables" soit cliquable
WebElement pcPortablesLink = wait.until(
    ExpectedConditions.elementToBeClickable(
        By.xpath("//a[contains(@href,'301-pc-portable-tunisie')]")
    )
);

// Cliquer sur le lien
pcPortablesLink.click();
    }

    public boolean isLaptopsCategoryDisplayed() {
        return driver.getTitle().toLowerCase().contains("portable");
    }

public void goToLoginPage() throws Exception {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//System.out.println("****0*****");
    // 1️⃣ Cliquer sur l'icône utilisateur pour ouvrir le menu
    WebElement userIcon = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.nav-link svg")));
    userIcon.click();
    //System.out.println("****1*****");

    // 2️⃣ Attendre que le lien "Connexion" devienne visible dans le menu
    WebElement signInButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
        By.xpath("//a[.//span[contains(text(), 'Connexion')]]")
    ));
//System.out.println("****2*****");
    // 3️⃣ Cliquer sur le lien "Connexion"
    signInButton.click();
//System.out.println("****3*****");
    // 4️⃣ Attendre que la page de login soit chargée
    wait.until(ExpectedConditions.urlContains("my-account"));
   // System.out.println("****4*****");
}


    
   


    public void changeCurrencyToEUR() throws Exception {
        elementControl.selectByVisibleText(currencyDropdown, "EUR €");
    }

    public boolean isCurrencyEUR() {
        return driver.getPageSource().contains("€");
    }

    public void goToContactPage() throws Exception {
        elementControl.clickElement(contactLink);
    }

    public boolean isContactPageDisplayed() {
        return driver.getTitle().toLowerCase().contains("contactez");
    }
}
