package com.amazon.test;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import com.amazon.pages.*;
import commonLibs.CommonDriver;

public class TunisianetTest {

    CommonDriver cmnDriver;
    TunisianetHomepage homepage;
    ProductPage productPage;
    CartPage cartPage;
    LoginPage loginPage;
    WebDriver driver;

    String url = "https://www.tunisianet.com.tn/";

    @BeforeClass
    public void invokeBrowser() throws Exception {
        cmnDriver = new CommonDriver("edge");
        cmnDriver.setPageloadTimeout(60);
        cmnDriver.setElementDetectionTimeout(10);
        cmnDriver.navigateToFirstUrl(url);
        driver = cmnDriver.getDriver();

        homepage = new TunisianetHomepage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
        loginPage = new LoginPage(driver);
    }

    @Test(priority = 1)
    public void searchProduct() throws Exception {
        homepage.searchProduct("ETUI 4 MARQUEURS ARDOISE MARK-IN ASSORTIS PURPLE");
         Thread.sleep(2000);
        Assert.assertTrue(homepage.isSearchResultDisplayed("ETUI 4 MARQUEURS ARDOISE MARK-IN ASSORTIS PURPLE"));
    }

    @Test(priority = 2)
    public void selectProduct() throws Exception {
        homepage.selectFirstSearchResult();
         Thread.sleep(2000);
        Assert.assertTrue(productPage.isProductTitleVisible());
    }

    @Test(priority = 3)
    public void addToCart() throws Exception {
        productPage.addToCart();
        Assert.assertTrue(cartPage.isProductAddedToCart());
    }

    @Test(priority = 4)
    public void openAndVerifyCart() throws Exception {
        cartPage.openCart();
        Assert.assertTrue(driver.getTitle().toLowerCase().contains("panier"));
    }

    @Test(priority = 5)
    public void removeFromCart() throws Exception {
        cartPage.removeProductFromCart();
        Thread.sleep(3000);
        Assert.assertTrue(cartPage.isCartEmpty());
    }

    @Test(priority = 6)
    public void invalidLogin() throws Exception {
        homepage.goToLoginPage();
        loginPage.login("fake@tunisianet.com", "wrongpass");
        Thread.sleep(2000);
        Assert.assertTrue(loginPage.isErrorDisplayed());
    }
       

         @Test(priority = 7)
    public void openLaptopsCategory() throws Exception {
        homepage.goToLaptopsCategory();
        Thread.sleep(2000);
        Assert.assertTrue(homepage.isLaptopsCategoryDisplayed());
    }

   /*  @Test(priority = 7)
    public void changeCurrency() throws Exception {
        homepage.changeCurrencyToEUR();
        Assert.assertTrue(homepage.isCurrencyEUR());
    }
*/
   
/* 
    @Test(priority = 9)
    public void checkPromotions() throws Exception {
        homepage.goToPromotions();
        Thread.sleep(2000);
        Assert.assertTrue(homepage.isPromotionsPageDisplayed());
    }


 */
    @Test(priority = 7)
    public void openContactPage() throws Exception {
        homepage.goToContactPage();
        Assert.assertTrue(homepage.isContactPageDisplayed());
    }

    @AfterClass
    public void closeBrowser() throws Exception {
        cmnDriver.closeAllBrowsers();
    }
}
