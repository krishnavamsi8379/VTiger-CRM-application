package PomUtility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import GenericUtilites.WebDriverUtility;

public class Home_Pom_Page {
	
	//Declaration
	
	@FindBy (linkText = " Home ")
	private WebElement home;
	
	@FindBy (linkText = "Organizations")
	private WebElement org;
	
	@FindBy (linkText = "Contacts")
	private WebElement contacts;
	
	@FindBy(xpath = "//span[text()=' Administrator']/../../descendant::img")
    private WebElement admin;
	
	@FindBy (xpath = "//a[text()='Sign Out']")
	private WebElement signout;
	
	// initialization
	public Home_Pom_Page(WebDriver driver) {
		PageFactory.initElements(driver,this);
	}
	//utilization

	public WebElement getHome() {
		return home;
	}

	public WebElement getOrg_link() {
		return org;
	}

	public WebElement getContacts_link() {
		return contacts;
	}

	public WebElement getAdmin() {
		return admin;
	}

	public WebElement getSignout_link() {
		return signout;
	}
	
	// Business logic for logout
    public void logout(WebDriver driver) {
        WebDriverUtility wutil = new WebDriverUtility();
        wutil.mousehoverOnAnElement(driver, admin);
        signout.click();
    }

	

}
