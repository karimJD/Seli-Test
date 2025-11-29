package com.seli.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class SeliHomepage {

    WebDriver driver;

    By searchInput = By.cssSelector(".dgwt-wcas-search-input");
    By firstSearchResult = By.cssSelector(".product-inner.product-item__inner");
    By allSearchResults = By.cssSelector(".product-inner.product-item__inner a");

    public SeliHomepage(WebDriver driver) {
        this.driver = driver;
    }

    public void searchProduct(String productName) {
        driver.findElement(searchInput).sendKeys(productName + "\n");
    }

    public boolean isSearchResultVisible() {
        try {
            return driver.findElement(firstSearchResult).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void selectFirstSearchResult() {
        driver.findElement(firstSearchResult).click();
    }

    public List<String> getAllSearchResultsLinks() {
        List<String> productLinks = new ArrayList<>();
        try {
            List<WebElement> elements = driver.findElements(allSearchResults);
            for (WebElement element : elements) {
                String href = element.getAttribute("href");
                if (href != null && !href.isEmpty()) {
                    productLinks.add(href);
                }
            }
        } catch (Exception e) {
            System.out.println("Error getting search results: " + e.getMessage());
        }
        return productLinks;
    }
}
