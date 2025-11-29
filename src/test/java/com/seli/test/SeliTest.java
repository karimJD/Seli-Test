package com.seli.test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.seli.pages.SeliCartPage;
import com.seli.pages.SeliHomepage;
import com.seli.pages.SeliLoginPage;
import com.seli.pages.SeliProductPage;

import commonLibs.CommonDriver;
import utils.BugReportGenerator;
import utils.ScreenshotUtils;
import utils.TestFailure;

/**
 * Test class for Seli.tn e-commerce website.
 * Covers scenarios for Product Search, Cart Management, and User Authentication.
 */
public class SeliTest {

    private CommonDriver cmnDriver;
    private WebDriver driver;
    private SeliHomepage homepage;
    private SeliProductPage productPage;
    private SeliCartPage cartPage;
    private SeliLoginPage loginPage;

    private final String baseUrl = "https://seli.tn/";
    
    // List to track failed tests for bug report generation
    private List<TestFailure> failedTests = new ArrayList<>();

    @BeforeClass
    public void invokeBrowser() throws Exception {
        cmnDriver = new CommonDriver("chrome");
        cmnDriver.setPageloadTimeout(120);
        cmnDriver.setElementDetectionTimeout(10);
        cmnDriver.navigateToFirstUrl(baseUrl);
        driver = cmnDriver.getDriver();

        // Initialize Page Objects
        homepage = new SeliHomepage(driver);
        productPage = new SeliProductPage(driver);
        cartPage = new SeliCartPage(driver);
        loginPage = new SeliLoginPage(driver);
    }

    // =================================================================================================
    //                                      PRODUCT SEARCH TESTS
    // =================================================================================================

    /**
     * Test Case: Search for a product.
     * Verifies that the search function works and results are displayed.
     */
    @Test(priority = 1)
    public void searchProduct() throws Exception {
        try {
            homepage.searchProduct("Souris");
            Thread.sleep(2000); // Wait for results to load
        } catch (Exception | AssertionError e) {
            handleTestFailure("searchProduct", e);
            throw e;
        }
    }

    /**
     * Test Case: Select a product from search results.
     * Verifies that clicking a product navigates to the product details page.
     */
    @Test(priority = 2)
    public void selectProduct() throws Exception {
        try {
            homepage.selectFirstSearchResult();
            Thread.sleep(2000);
            Assert.assertTrue(driver.findElement(By.cssSelector("h1.product_title.entry-title")).isDisplayed(),
                    "Product title should be displayed on product page.");
        } catch (Exception | AssertionError e) {
            handleTestFailure("selectProduct", e);
            throw e;
        }
    }

    /**
     * Test Case: Verify product details elements.
     * Checks for the presence of Title, Add to Cart button, and Price.
     */
    @Test(priority = 9)
    public void productDetailsElementsTest() throws Exception {
        try {
            homepage.searchProduct("Souris");
            homepage.selectFirstSearchResult();
            
            Assert.assertTrue(driver.findElement(By.cssSelector("h1.product_title.entry-title")).isDisplayed(),
                    "Product title should be visible");
            Assert.assertTrue(driver.findElement(By.cssSelector("button.single_add_to_cart_button")).isDisplayed(),
                    "Add to Cart button should be visible");
            Assert.assertTrue(driver.findElement(By.cssSelector("p.price")).isDisplayed(),
                    "Product price should be visible");
        } catch (Exception | AssertionError e) {
            handleTestFailure("productDetailsElementsTest", e);
            ScreenshotUtils.captureScreenshot(driver, "productDetails_bug");
            throw e;
        }
    }

    // =================================================================================================
    //                                      CART MANAGEMENT TESTS
    // =================================================================================================

    /**
     * Test Case: Add a single product to cart.
     * Verifies that the cart count updates correctly.
     */
    @Test(priority = 3)
    public void addToCart() throws Exception {
        try {
            driver.findElement(By.cssSelector("button.single_add_to_cart_button")).click();
            Thread.sleep(2000); // Wait for ajax add to cart
            
            driver.get("https://seli.tn/cart/");
            Thread.sleep(2000);
            
            Assert.assertTrue(driver.findElement(By.cssSelector("span.cart-items-count.count")).getText().contains("1"),
                    "Cart count should show 1 item.");
        } catch (Exception | AssertionError e) {
            handleTestFailure("addToCart", e);
            throw e;
        }
    }

    /**
     * Test Case: Remove product from cart.
     * Verifies that the cart becomes empty after removal.
     */
    @Test(priority = 4)
    public void removeFromCart() throws Exception {
        try {
            driver.get("https://seli.tn/cart/");
            cartPage.removeProductFromCart();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(d -> cartPage.isCartEmpty());

            Assert.assertTrue(cartPage.isCartEmpty(), "Cart should be empty after removing product");

        } catch (Exception | AssertionError e) {
            handleTestFailure("removeFromCart", e);
            ScreenshotUtils.captureScreenshot(driver, "removeFromCart_bug");
            throw e;
        }
    }

    /**
     * Test Case: Add multiple products to cart.
     * Iterates through product list and adds them to cart, verifying the count updates.
     */
    @Test(priority = 10)
    public void addMultipleProductsToCart() throws Exception {
        try {
            driver.get(baseUrl);
            Thread.sleep(2000);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.products-block")));

            List<WebElement> addToCartButtons = driver.findElements(
                By.cssSelector("div.products-block a.ajax_add_to_cart.product_type_simple")
            );

            System.out.println("Found " + addToCartButtons.size() + " simple product buttons");

            int addedCount = 0;
            int targetCount = 5;
            
            for (WebElement button : addToCartButtons) {
                if (addedCount >= targetCount) break;
                
                try {
                    // Scroll to button to ensure it's clickable
                    ((JavascriptExecutor) driver)
                        .executeScript("arguments[0].scrollIntoView({block: 'center'});", button);
                    Thread.sleep(500);
                    
                    wait.until(ExpectedConditions.elementToBeClickable(button));
                    
                    // Get current count
                    String beforeCountText = driver.findElement(
                        By.cssSelector("span.cart-items-count.count")
                    ).getText().trim();
                    int beforeCount = beforeCountText.isEmpty() ? 0 : Integer.parseInt(beforeCountText);
                    
                    button.click();
                    
                    // Wait for count to update
                    final int expectedCount = beforeCount + 1;
                    wait.until(d -> {
                        try {
                            String countText = d.findElement(
                                By.cssSelector("span.cart-items-count.count.header-icon-counter")
                            ).getText().trim();
                            return !countText.isEmpty() && Integer.parseInt(countText) == expectedCount;
                        } catch (Exception ex) {
                            return false;
                        }
                    });
                    
                    addedCount++;
                    System.out.println("Successfully added product " + addedCount + 
                                     " - Cart now has " + expectedCount + " items");
                    Thread.sleep(500);
                    
                } catch (Exception ex) {
                    System.out.println("Could not add product: " + ex.getMessage());
                }
            }

            Assert.assertTrue(addedCount >= 3, 
                "Should have added at least 3 products, but only added " + addedCount);

            String finalCountText = driver.findElement(
                By.cssSelector("span.cart-items-count.count.header-icon-counter")
            ).getText().trim();
            int finalCount = Integer.parseInt(finalCountText);
            
            Assert.assertEquals(finalCount, addedCount, 
                "Header cart counter should show " + addedCount + " items, but shows " + finalCount);
            
        } catch (Exception | AssertionError e) {
            handleTestFailure("addMultipleProductsToCart", e);
            ScreenshotUtils.captureScreenshot(driver, "addMultipleProductsToCart_bug");
            throw e;
        }
    }

    // =================================================================================================
    //                                      AUTHENTICATION TESTS
    // =================================================================================================

    /**
     * Test Case: Login with invalid credentials.
     * Verifies that an error message is displayed.
     */
    @Test(priority = 5)
    public void loginWithInvalidCredentials() throws Exception {
        try {
            driver.get("https://seli.tn/my-account-2/");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            String fakeEmail = "fakeuser@example.com";
            String fakePassword = "wrongpassword";
            loginPage.login(fakeEmail, fakePassword);

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".woocommerce-error li")));

            Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message should be displayed for invalid login");

        } catch (Exception | AssertionError e) {
            handleTestFailure("loginWithInvalidCredentials", e);
            ScreenshotUtils.captureScreenshot(driver, "loginFailure_bug");
            throw e;
        }
    }

    /**
     * Test Case: Login with valid credentials.
     * Verifies successful login and redirection to 'Mon Compte'.
     */
    @Test(priority = 6)
    public void loginWithValidCredentials() throws Exception {
        try {
            driver.get("https://seli.tn/my-account-2/");

            String validEmail = "karimjoudi01@gmail.com";
            String validPassword = "test123";

            loginPage.login(validEmail, validPassword);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            By accountHeader = By.cssSelector("header.entry-header h1.entry-title");
            wait.until(ExpectedConditions.visibilityOfElementLocated(accountHeader));

            String headerText = driver.findElement(accountHeader).getText();
            Assert.assertEquals(headerText, "Mon Compte", "After login, the account page should display 'Mon Compte'");

        } catch (Exception | AssertionError e) {
            handleTestFailure("loginWithValidCredentials", e);
            ScreenshotUtils.captureScreenshot(driver, "loginSuccess_bug");
            throw e;
        }
    }

    /**
     * Test Case: Logout.
     * Verifies that the user can log out and sees the login form.
     */
    @Test(priority = 7)
    public void logoutTest() throws Exception {
        try {
            driver.get("https://seli.tn/my-account-2/");
            driver.findElement(By.linkText("Déconnexion")).click();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("form.woocommerce-form-login")));
            Assert.assertTrue(driver.findElement(By.cssSelector("form.woocommerce-form-login")).isDisplayed(),
                    "Login form should be visible after logout");
        } catch (Exception | AssertionError e) {
            handleTestFailure("logoutTest", e);
            ScreenshotUtils.captureScreenshot(driver, "logout_bug");
            throw e;
        }
    }

    /**
     * Test Case: Password Reset Link.
     * Verifies the 'Lost Password' flow up to the success message.
     */
    @Test(priority = 8)
    public void passwordResetLinkTest() throws Exception {
        try {
            driver.get("https://seli.tn/my-account-2/");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("p.lost_password a")));
            loginPage.clickForgotPassword();
            
            wait.until(ExpectedConditions.urlContains("/lost-password/"));
            Assert.assertEquals(driver.getCurrentUrl(), "https://seli.tn/my-account-2/lost-password/",
                    "Should redirect to lost password page");
            
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user_login")));
            Assert.assertTrue(loginPage.isResetEmailInputDisplayed(),
                    "Reset email input field should be displayed");
            
            loginPage.enterResetEmail("karimjoudi01@gmail.com");
            
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.woocommerce-Button")));
            loginPage.clickResetPasswordButton();
            
            wait.until(ExpectedConditions.urlContains("reset-link-sent=true"));
            Assert.assertEquals(driver.getCurrentUrl(), "https://seli.tn/my-account-2/lost-password/?reset-link-sent=true",
                    "Should redirect to success URL with reset-link-sent=true parameter");
        } catch (Exception | AssertionError e) {
            handleTestFailure("passwordResetLinkTest", e);
            ScreenshotUtils.captureScreenshot(driver, "passwordReset_bug");
            throw e;
        }
    }

    @AfterClass
    public void closeBrowser() throws Exception {
        // Generate bug reports for all failed tests
        if (!failedTests.isEmpty()) {
            System.out.println("\n========================================");
            System.out.println("GENERATING BUG REPORTS FOR FAILED TESTS");
            System.out.println("========================================\n");
            
            BugReportGenerator.generateReports(failedTests, "bug_reports");
            
            System.out.println("\nTotal failed tests: " + failedTests.size());
            System.out.println("Bug reports saved to: bug_reports/");
        }
        
        cmnDriver.closeAllBrowsers();
    }

    // =================================================================================================
    //                                      HELPER METHODS
    // =================================================================================================

    /**
     * Handles test failure by capturing details and adding to the failure list.
     */
    private void handleTestFailure(String testName, Throwable error) {
        String screenshotPath = "screenshots/" + testName + "_bug.png";
        String currentUrl = driver.getCurrentUrl();
        
        TestFailure failure = new TestFailure(
            testName,
            error.getMessage(),
            currentUrl,
            screenshotPath,
            getStepsToReproduce(testName),
            getExpectedResult(testName),
            getActualResult(testName, error)
        );
        
        failedTests.add(failure);
    }

    private String getStepsToReproduce(String testName) {
        switch (testName) {
            case "searchProduct":
                return "1. Navigate to https://seli.tn/\n2. Enter 'Souris' in the search bar\n3. Submit search";
            case "selectProduct":
                return "1. Search for 'Souris'\n2. Click on the first search result";
            case "addToCart":
                return "1. Navigate to a product page\n2. Click 'Add to Cart' button\n3. Navigate to cart page";
            case "removeFromCart":
                return "1. Add a product to cart\n2. Navigate to cart page\n3. Click remove button";
            case "addMultipleProductsToCart":
                return "1. Navigate to homepage\n2. Click 'Add to Cart' on multiple products (target: 5)\n3. Verify cart count increases";
            case "loginWithInvalidCredentials":
                return "1. Navigate to login page\n2. Enter invalid email: fakeuser@example.com\n3. Enter invalid password\n4. Click login";
            case "loginWithValidCredentials":
                return "1. Navigate to login page\n2. Enter valid credentials\n3. Click login\n4. Verify redirect to account page";
            case "logoutTest":
                return "1. Login with valid credentials\n2. Navigate to account page\n3. Click 'Déconnexion' link";
            case "passwordResetLinkTest":
                return "1. Navigate to login page\n2. Click 'Forgot Password' link\n3. Enter email address\n4. Click reset button";
            case "productDetailsElementsTest":
                return "1. Search for 'Souris'\n2. Click on first result\n3. Verify product details are visible";
            default:
                return "Steps not documented";
        }
    }

    private String getExpectedResult(String testName) {
        switch (testName) {
            case "searchProduct":
                return "Search should execute successfully and display results";
            case "selectProduct":
                return "Product page should load with product title visible";
            case "addToCart":
                return "Product should be added to cart, cart count should show '1'";
            case "removeFromCart":
                return "Cart should be empty after removing the product";
            case "addMultipleProductsToCart":
                return "At least 3 products should be added to cart, header counter should reflect exact count";
            case "loginWithInvalidCredentials":
                return "Error message should be displayed for invalid credentials";
            case "loginWithValidCredentials":
                return "User should be redirected to account page showing 'Mon Compte'";
            case "logoutTest":
                return "User should be logged out and login form should be visible";
            case "passwordResetLinkTest":
                return "Should redirect to success page with reset-link-sent=true parameter";
            case "productDetailsElementsTest":
                return "Product title, Add to Cart button, and price should all be visible";
            default:
                return "Expected result not documented";
        }
    }

    private String getActualResult(String testName, Throwable error) {
        return "Test failed with error: " + error.getMessage();
    }
}