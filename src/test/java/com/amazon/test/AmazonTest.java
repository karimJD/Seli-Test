/*package com.amazon.test;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.amazon.pages.AmazonHomepage;
import commonLibs.CommonDriver;


public class AmazonTest {
	CommonDriver cmnDriver;
	AmazonHomepage homepage;
	String url = "https://www.amazon.ae";
	WebDriver driver;
	
	@BeforeClass
	public void invokeBrowser() throws Exception{
		cmnDriver = new CommonDriver("chrome");
		cmnDriver.setPageloadTimeout(60);
		cmnDriver.setElementDetectionTimeout(10);
		cmnDriver.navigateToFirstUrl(url);
		driver= cmnDriver.getDriver();
		homepage = new AmazonHomepage(driver);
	}
	
	@Test
	public void searchProduct() throws Exception{
		String product = "iPhone";
		String category = "Electronics";
		homepage.searchProduct(product, category);
	}

	@AfterClass
	public void closeBrowser() throws Exception{
		cmnDriver.closeAllBrowsers();
	}

}
*/
package com.amazon.test;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.amazon.pages.AmazonHomepage;
import com.amazon.pages.ProductPage;
import com.amazon.pages.CartPage;
import com.amazon.pages.LoginPage;
import com.amazon.pages.TodaysDealsPage;
import commonLibs.CommonDriver;

public class AmazonTest {

    CommonDriver cmnDriver;
    AmazonHomepage homepage;
    ProductPage productPage;
    CartPage cartPage;
    LoginPage loginPage;
    TodaysDealsPage dealsPage;
    WebDriver driver;
    String url = "https://www.amazon.ae";
	

    @BeforeClass
    public void invokeBrowser() throws Exception {
        cmnDriver = new CommonDriver("edge");
        cmnDriver.setPageloadTimeout(60);
        cmnDriver.setElementDetectionTimeout(10);
        cmnDriver.navigateToFirstUrl(url);
        driver = cmnDriver.getDriver();
        homepage = new AmazonHomepage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
        loginPage = new LoginPage(driver);
        dealsPage = new TodaysDealsPage(driver);
    }

    // 1️⃣ Test recherche d’un produit
    @Test(priority = 1)
    public void searchProduct() throws Exception {
        String product = "iPhone";
        String category = "Electronics";
        homepage.searchProduct(product, category);
        Assert.assertTrue(homepage.isSearchResultDisplayed(product));
    }

    // 2️⃣ Test sélection d’un produit
    @Test(priority = 2)
    public void selectProduct() throws Exception {
        homepage.selectFirstSearchResult();
        Assert.assertTrue(productPage.isProductTitleVisible());
    }

    // 3️⃣ Test ajout d’un produit au panier
    @Test(priority = 3)
    public void addToCart() throws Exception {
        productPage.addToCart();
        Assert.assertTrue(cartPage.isProductAddedToCart());
    }

    // 4️⃣ Test suppression d’un produit du panier
    @Test(priority = 4)
    public void removeFromCart() throws Exception {
        cartPage.removeProductFromCart();
        Assert.assertTrue(cartPage.isCartEmpty());
    }

    // 5️⃣ Test navigation vers les offres du jour
    @Test(priority = 5)
    public void checkTodaysDeals() throws Exception {
        homepage.goToTodaysDeals();
        Assert.assertTrue(dealsPage.isDealsPageLoaded());
    }

    // 6️⃣ Test changement de langue
    @Test(priority = 6)
    public void changeLanguageToArabic() throws Exception {
        homepage.changeLanguageToArabic();
        Assert.assertTrue(homepage.isArabicLanguageDisplayed());
    }

    // 7️⃣ Test connexion avec identifiant invalide
    @Test(priority = 7)
    public void invalidLogin() throws Exception {
        homepage.goToLoginPage();
        loginPage.login("fakeemail@example.com", "wrongpassword");
        Assert.assertTrue(loginPage.isErrorDisplayed());
    }

    // 8️⃣ Test accès à la section "Best Sellers"
    @Test(priority = 8)
    public void openBestSellers() throws Exception {
        homepage.goToBestSellers();
        Assert.assertTrue(homepage.isBestSellersPageDisplayed());
    }

    // 9️⃣ Test filtrage par marque après recherche
    @Test(priority = 9)
    public void filterByBrand() throws Exception {
        homepage.searchProduct("Laptop", "Computers");
        homepage.filterByBrand("HP");
        Assert.assertTrue(homepage.isFilterApplied("HP"));
    }

    // 🔟 Test consultation du service client
    @Test(priority = 10)
    public void openCustomerService() throws Exception {
        homepage.goToCustomerService();
        Assert.assertTrue(homepage.isCustomerServicePageDisplayed());
    }

    @AfterClass
    public void closeBrowser() throws Exception {
        cmnDriver.closeAllBrowsers();
    }
}
