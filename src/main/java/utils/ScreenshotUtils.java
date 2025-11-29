package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtils {
    
    public static String captureScreenshot(WebDriver driver, String testName) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = testName + "_" + timestamp + ".png";
        String screenshotPath = "target/screenshots/" + fileName;
        
        try {
            // Create screenshots directory if it doesn't exist
            File screenshotDir = new File("target/screenshots");
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }
            
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
            File destFile = new File(screenshotPath);
            FileHandler.copy(srcFile, destFile);
            
            System.out.println("Screenshot saved: " + screenshotPath);
            return screenshotPath;
        } catch (IOException e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
            return null;
        }
    }
    
    public static String captureElementScreenshot(WebElement element, String elementName) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = elementName + "_" + timestamp + ".png";
        String screenshotPath = "target/screenshots/" + fileName;
        
        try {
            File screenshotDir = new File("target/screenshots");
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }
            
            File srcFile = element.getScreenshotAs(OutputType.FILE);
            File destFile = new File(screenshotPath);
            FileHandler.copy(srcFile, destFile);
            
            System.out.println("Element screenshot saved: " + screenshotPath);
            return screenshotPath;
        } catch (IOException e) {
            System.err.println("Failed to capture element screenshot: " + e.getMessage());
            return null;
        }
    }
}
