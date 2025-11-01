package com.amazon.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class TodaysDealsPage {

    private WebDriver driver;

    public TodaysDealsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isDealsPageLoaded() {
        return driver.getTitle().toLowerCase().contains("today");
    }
}
