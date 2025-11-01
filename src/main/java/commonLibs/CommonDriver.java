/*package commonLibs;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver; //32bit version : https://github.com/mozilla/geckodriver/releases/download/v0.36.0/geckodriver-v0.36.0-win32.zip
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxOptions;


public class CommonDriver {
	
	private WebDriver driver;
	private int pageloadTimeout;
	private int elementDetectionTimeout;
	
	private String currentWorkingDriectory;
	
	public CommonDriver(String browserType) throws Exception {

		pageloadTimeout = 60;
		elementDetectionTimeout = 10;
		
		currentWorkingDriectory = System.getProperty("user.dir");




		if (browserType.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					currentWorkingDriectory + "/drivers/chromedriver.exe");

			driver = new ChromeDriver();
		} else if (browserType.equalsIgnoreCase("edge")) {
			System.setProperty("webdriver.edge.driver",
					currentWorkingDriectory + "/drivers/msedgedriver.exe");

			driver = new EdgeDriver();
		} else {
			throw new Exception("Invalid Browser Type");
		}

		driver.manage().window().maximize();

		driver.manage().deleteAllCookies();

	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setPageloadTimeout(int pageloadTimeout) {
		this.pageloadTimeout = pageloadTimeout;
	}

	public void setElementDetectionTimeout(int elementDetectionTimeout) {
		this.elementDetectionTimeout = elementDetectionTimeout;
	}

	
	public void navigateToFirstUrl(String url) throws Exception {

		driver.manage().timeouts().pageLoadTimeout(pageloadTimeout, TimeUnit.SECONDS);

		driver.manage().timeouts().implicitlyWait(elementDetectionTimeout, TimeUnit.SECONDS);

		url = url.trim();

		driver.get(url);

	}

	
	public String getTitle() throws Exception {
		String title = driver.getTitle();
		return title;
	}

	
	public String getCurrentUrl() throws Exception {

		return driver.getCurrentUrl();
	}

	
	public String getPageSource() throws Exception {

		return driver.getPageSource();
	}

	
	public void navigateToUrl(String url) throws Exception {

		url = url.trim();
		driver.navigate().to(url);

	}

	
	public void navigateForward() throws Exception {
		driver.navigate().forward();

	}

	
	public void navigateBackward() throws Exception {
		driver.navigate().back();

	}

	
	public void refresh() throws Exception {
		driver.navigate().refresh();

	}

	
	public void closeBrowser() throws Exception {

		if (driver != null) {
			driver.close();

		}
	}

	
	public void closeAllBrowsers() throws Exception {
		if (driver != null) {
			driver.quit();
		}

	}

}
*/
package commonLibs;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class CommonDriver {

    private WebDriver driver;
    private int pageloadTimeout;
    private int elementDetectionTimeout;
    private String currentWorkingDirectory = System.getProperty("user.dir");

    public CommonDriver(String browserType) throws Exception {
        browserType = browserType.trim();

        switch (browserType.toLowerCase()) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", currentWorkingDirectory + "/drivers/chromedriver.exe");
                driver = new ChromeDriver();
                break;

            case "firefox":
                System.setProperty("webdriver.gecko.driver", currentWorkingDirectory + "/drivers/geckodriver.exe");
                driver = new FirefoxDriver();
                break;

            case "edge":
                System.setProperty("webdriver.edge.driver", currentWorkingDirectory + "/drivers/msedgedriver.exe");
                driver = new EdgeDriver();
                break;

            default:
                throw new Exception("Invalid browser type: " + browserType);
        }

        driver.manage().window().maximize();
    }

    public void navigateToFirstUrl(String url) {
        driver.get(url);
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setPageloadTimeout(long pageloadTimeoutInSeconds) {
        this.pageloadTimeout = (int) pageloadTimeoutInSeconds;
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageloadTimeout));
    }

    public void setElementDetectionTimeout(long elementDetectionTimeoutInSeconds) {
        this.elementDetectionTimeout = (int) elementDetectionTimeoutInSeconds;
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(elementDetectionTimeout));
    }

    public void closeAllBrowsers() {
        if (driver != null) {
            driver.quit();
        }
    }
}
