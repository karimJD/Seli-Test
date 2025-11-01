/*package commonLibs;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class Interactions {
	
	
	public String getText(WebElement element) throws Exception {

		return element.getText();
	}

	
	public void clickElement(WebElement element) throws Exception {
		element.click();

	}

	
	public String getAttribute(WebElement element, String attribute) throws Exception {

		return element.getAttribute(attribute);
	}

	
	public String getCssValue(WebElement element, String csspropertyName) throws Exception {

		return element.getCssValue(csspropertyName);
	}

	
	public boolean isElementEnabled(WebElement element) throws Exception {

		return element.isEnabled();
	}

	
	public boolean isElementVisible(WebElement element) throws Exception {

		return element.isDisplayed();
	}

	
	public boolean isElementSelected(WebElement element) throws Exception {

		return element.isSelected();
	}

	
	public void setText(WebElement element, String textToWrite) throws Exception {

		element.sendKeys(textToWrite);
	}

	
	public void clearText(WebElement element) throws Exception {
		element.clear();

	}

	
	public void changeCheckboxStatus(WebElement element, boolean expectedStatus) throws Exception {

		boolean currentStatus = element.isSelected();

		if (currentStatus != expectedStatus) {
			element.click();
		}

	}

	
	private Select getSelect(WebElement element) {
		Select select = new Select(element);
		return select;
	}

	
	public void selectViaVisibleText(WebElement element, String visibleText) throws Exception {
		getSelect(element).selectByVisibleText(visibleText);
	}

	
	public void selectViaValue(WebElement element, String value) throws Exception {
		getSelect(element).selectByValue(value);
	}

	
	public void selectViaIndex(WebElement element, int index) throws Exception {
		getSelect(element).selectByIndex(index);
	}

	
	public List<WebElement> getAllOptions(WebElement element) {
		return getSelect(element).getOptions();
	}


}
*/
package commonLibs;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class Interactions {

    private WebDriver driver;

    public Interactions(WebDriver driver) {
        this.driver = driver;
    }

    // ✅ Cliquer sur un élément
    public void clickElement(WebElement element) throws Exception {
        if (element.isDisplayed() && element.isEnabled()) {
            element.click();
        } else {
            throw new Exception("Element not clickable: " + element);
        }
    }

    // ✅ Écrire du texte dans un champ
    public void setText(WebElement element, String text) throws Exception {
        if (element.isDisplayed()) {
            element.clear();
            element.sendKeys(text);
        } else {
            throw new Exception("Element not visible: " + element);
        }
    }

    // ✅ Effacer le texte
    public void clearText(WebElement element) throws Exception {
        if (element.isDisplayed()) {
            element.clear();
        }
    }

    // ✅ Sélectionner dans un menu déroulant par texte visible
    public void selectByVisibleText(WebElement element, String visibleText) throws Exception {
        Select select = new Select(element);
        select.selectByVisibleText(visibleText);
    }

    // ✅ Passer la souris sur un élément
    public void hoverOverElement(WebElement element) throws Exception {
        Actions action = new Actions(driver);
        action.moveToElement(element).perform();
    }

    // ✅ Récupérer le texte d’un élément
    public String getText(WebElement element) {
        return element.getText();
    }

    // ✅ Vérifier si un élément est visible
    public boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
