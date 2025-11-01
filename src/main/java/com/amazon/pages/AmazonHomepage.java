/*package com.amazon.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import commonLibs.Interactions;

public class AmazonHomepage {

	@CacheLookup
	@FindBy(id = "twotabsearchtextbox")
	private WebElement searchBox;

	@CacheLookup
	@FindBy(id = "searchDropdownBox")
	private WebElement searchCategory;

	@CacheLookup
	@FindBy(xpath = "//input[@value='Go']")
	private WebElement searchButton;

	@FindBy(xpath = "//span[contains(text(),'results for')]")
	private WebElement searchResult;

	private Interactions interaction;

	public AmazonHomepage(WebDriver driver) {

		PageFactory.initElements(driver, this);
		interaction = new Interactions();

	}

	public void searchProduct(String product, String category) throws Exception {
	
		interaction.setText(searchBox, product);

		interaction.selectViaVisibleText(searchCategory, category);
		
		interaction.clickElement(searchButton);

		System.out.println(interaction.getText(searchResult));
	}

}
*/
package com.amazon.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import commonLibs.Interactions;

public class AmazonHomepage {

    private WebDriver driver;
    private Interactions elementControl;

    @CacheLookup
    @FindBy(id = "twotabsearchtextbox")
    private WebElement searchBox;

    @CacheLookup
    @FindBy(id = "nav-search-submit-button")
    private WebElement searchButton;

    @FindBy(xpath = "//a[@id='icp-nav-flyout']")
    private WebElement languageButton;

    @FindBy(xpath = "//a[contains(text(),'العربية')]")
    private WebElement arabicLanguageOption;

    @FindBy(linkText = "Today's Deals")
    private WebElement todaysDealsLink;

    @FindBy(linkText = "Best Sellers")
    private WebElement bestSellersLink;

    @FindBy(linkText = "Customer Service")
    private WebElement customerServiceLink;

    @FindBy(id = "nav-link-accountList")
    private WebElement signInButton;

    public AmazonHomepage(WebDriver driver) {
        this.driver = driver;
        elementControl = new Interactions(driver);
        PageFactory.initElements(driver, this);
    }

    public void searchProduct(String product, String category) throws Exception {
        elementControl.clearText(searchBox);
        elementControl.setText(searchBox, product);
        elementControl.clickElement(searchButton);
    }

    public boolean isSearchResultDisplayed(String keyword) {
        return driver.getTitle().toLowerCase().contains(keyword.toLowerCase());
    }

    public void selectFirstSearchResult() throws Exception {
        WebElement firstProduct = driver.findElement(
            org.openqa.selenium.By.xpath("(//div[@data-component-type='s-search-result'])[1]//h2/a"));
        elementControl.clickElement(firstProduct);
    }

    public void goToTodaysDeals() throws Exception {
        elementControl.clickElement(todaysDealsLink);
    }

    public boolean isArabicLanguageDisplayed() {
        return driver.getCurrentUrl().contains("lang=ar");
    }

    public void changeLanguageToArabic() throws Exception {
        elementControl.clickElement(languageButton);
        elementControl.clickElement(arabicLanguageOption);
    }

    public void goToLoginPage() throws Exception {
        elementControl.clickElement(signInButton);
    }

    public void goToBestSellers() throws Exception {
        elementControl.clickElement(bestSellersLink);
    }

    public boolean isBestSellersPageDisplayed() {
        return driver.getTitle().toLowerCase().contains("best sellers");
    }

    public void filterByBrand(String brand) throws Exception {
        WebElement brandCheckbox = driver.findElement(
            org.openqa.selenium.By.xpath("//span[text()='" + brand + "']/preceding-sibling::div"));
        elementControl.clickElement(brandCheckbox);
    }

    public boolean isFilterApplied(String brand) {
        return driver.getPageSource().contains(brand);
    }

    public void goToCustomerService() throws Exception {
        elementControl.clickElement(customerServiceLink);
    }

    public boolean isCustomerServicePageDisplayed() {
        return driver.getTitle().toLowerCase().contains("customer service");
    }
}
