package GenericUtilites;

import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * This class is contains all the reusable methods of WebDriver
 */

public class WebDriverUtility {
	
	/**
	 * This method is used to maximize the window
	 * @param driver
	 */
	
	public void maximizeTheWindow(WebDriver driver) {
		driver.manage().window().maximize();
	}
	
	/**
	 * This method is used for implicit wait to wait till the element is found in the specified time
	 * @param driver
	 * @param Time
	 */
	
	public void implcitWaitStmt(WebDriver driver, String Time) {
		Long time=Long.parseLong(Time);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(time));
	}
	
	/**
	 * This method is used to navigate to an applcaition
	 * @param driver
	 * @param url
	 */
	public void navigateToApplciation(WebDriver driver, String url) {
		driver.get(url);
	}
	/**\
	 * This method is used to handle a popup and click on accept button
	 * @param driver
	 */
	public void handleAlertPopuAndClickOnAccept(WebDriver driver) {
		driver.switchTo().alert().accept();
	}
	
	/**
	 * This method is used to handle a popup and click on cancel button
	 * @param driver
	 */
	
	public void handleAlertPopupandClickOnCancel(WebDriver driver) {
		driver.switchTo().alert().dismiss();
		
	}
	/**
	 * 
	 * @param driver
	 * @return
	 */
	public String handleAlertPopupandFetchText(WebDriver driver) {
		String text = driver.switchTo().alert().getText();
		return text;
	}
	public void handleAlertPopupandPassText(WebDriver driver, String text) {
		driver.switchTo().alert().sendKeys(text);
	}
	
	
	/**
	 * This method is used to mouseover on a particular element
	 * @param driver
	 * @param ele
	 */
	public void mouseOverOnAnElement(WebDriver driver, WebElement ele) {
		Actions act = new Actions(driver);
		act.moveToElement(ele).perform();
	}
	
	/**
	 * This method is used to close the browser
	 * @param driver
	 */
	public void closeBrowser(WebDriver driver) {
		driver.quit();
	}
	
	/**
	 * This method is used to select a option in dropdown using option value
	 * @param dd
	 * @param value
	 */
	
	public void SelectValueinDropdown(WebElement dd, String value) {
		Select s = new Select(dd);
		s.selectByValue(value);
	}
	/**
	 * This method is used to select a option in dropdown using option index
	 * @param dd
	 * @param value
	 */
	
	public void SelectDD_usingIndex(WebElement dd, int index) {
		Select s = new Select(dd);
		s.selectByIndex(index);
		
	}
	
	/**
	 * This method is used to select a option in dropdown using option text
	 * @param dd
	 * @param value
	 */
	public void SelectDD_UsingVisbleText(WebElement dd, String value) {
		Select s = new Select(dd);
		s.selectByVisibleText(value);
		
	}
	/**
	 * this method is used to get parent window id	
	 * @param driver
	 * @return
	 */
	public String getParentWindowID(WebDriver driver) {
		String pwid = driver.getWindowHandle();
		return pwid;
		
	}
	
	public Set<String> getMultipleWindowIDs(WebDriver driver) {
		Set<String> wids = driver.getWindowHandles();
		return wids;
		
	}
	
	public void changeTheDrivertoChildWindowURL(WebDriver driver,Set<String> wids, String url) {
		for (String s : wids) {
			driver.switchTo().window(s);
			if (driver.getCurrentUrl().contains(url)) {
				break;
			}

		}
	}
	
	public void SwithcToWindowUsingTitle(WebDriver driver, String title,Set<String> wids ) {
		for (String s : wids) {
			driver.switchTo().window(s);
			if (driver.getCurrentUrl().contains(title)) {
				break;
			}

		}
		
	}
	
	public void SwithcBacktoPArent(WebDriver driver, String pwid) {
		driver.switchTo().window(pwid);
	}
	
	public void waitTillElementISVisible(WebDriver driver,WebElement ele) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(ele));
	}
	
	public void waitTillElementIsClickabke(WebDriver driver, WebElement ele) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(ele));
		
	}
	
	public void waitTillTitleISVisible(WebDriver driver, String title) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.titleContains(title));
		
	}
	
	public void switchToFrameByIndex(WebDriver driver, int index) {
		driver.switchTo().frame(index);
	}
	
	public void switchToFrameById_name(WebDriver driver, String id_name) {
		driver.switchTo().frame(id_name);
		
	}
	public void switchToFrameByIndex(WebDriver driver, WebElement ele) {
		driver.switchTo().frame(ele);
		
	}
	
	public void switchToMainWebpageFromFrame(WebDriver driver) {
		driver.switchTo().defaultContent();
		
	}
	
	public void mousehoverOnAnElement(WebDriver driver, WebElement element) {
		Actions act = new Actions(driver);
		act.moveToElement(element).perform();
	}
	
	

}
