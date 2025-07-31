package Contact_Module;

import java.io.IOException;
import java.time.Duration;
import java.util.Set;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import GenericUtilites.ExcelFileUtility;
import GenericUtilites.JavaUtility;
import GenericUtilites.PropertyFileUtility;
import GenericUtilites.WebDriverUtility;
import PomUtility.Contact_Details_Pom_page;
import PomUtility.Contact_Pom_Page;
import PomUtility.Create_Contact_Pom_page;
import PomUtility.Create_Org_Pom_Page;
import PomUtility.Home_Pom_Page;
import PomUtility.Login_Pom_Page;
import PomUtility.Organization_Pom_Page;

//Using DDT
public class Create_Contact_With_Org_updated {

	@Test(groups = "regression")
	public void createNewOrgTest() throws InterruptedException, IOException {
		// properties
		// usign Generic File Utilites
		PropertyFileUtility prop = new PropertyFileUtility();
		String url = prop.fetchDataFromPropFile("url");
		String username = prop.fetchDataFromPropFile("username");
		String password = prop.fetchDataFromPropFile("password");
		String Time = prop.fetchDataFromPropFile("timeouts");

		// java utility
		JavaUtility jutil = new JavaUtility();
		int randomnum = jutil.generateRandomNumber();

//		// Excel
//		using Excel File generic utilites
		ExcelFileUtility exutil = new ExcelFileUtility();
		String cname = exutil.fetchDataFromExcelFile("Contact", 7, 3) + randomnum;
		String Org = exutil.fetchDataFromExcelFile("Contact", 7, 2) + randomnum;
		exutil.closeExcelFile();

		// open Browser
		WebDriver driver = new ChromeDriver();

		// maximize window
		WebDriverUtility wbutil = new WebDriverUtility();
		wbutil.maximizeTheWindow(driver);

		// Implicit wait stmt
		wbutil.implcitWaitStmt(driver, Time);

		// navigate to applciation
		wbutil.navigateToApplciation(driver, url);

		// Login pom utility
		Login_Pom_Page loginutil = new Login_Pom_Page(driver);
		loginutil.login(username, password);

		// Home page pom utility
		// identify org link
		Home_Pom_Page homeUtil = new Home_Pom_Page(driver);
		homeUtil.getOrg_link().click();

		// Organization page pom utility
		// Click on add org btn
		Organization_Pom_Page orgUtil = new Organization_Pom_Page(driver);
		orgUtil.getclickonOrgAddbtn();

		// Create Organization Pom page
		Create_Org_Pom_Page createOrgUtil = new Create_Org_Pom_Page(driver);

		// enter the org name
		createOrgUtil.getOrgNameTF().sendKeys(Org);

		// click in save
		createOrgUtil.getSaveBtn().click();

		Thread.sleep(4000);

		// Contacts link (2nd)
		homeUtil.getContacts_link().click();
		// add contact icon
		Create_Contact_Pom_page createContactUtil = new Create_Contact_Pom_page(driver);
		
		Contact_Pom_Page contactUtil = new Contact_Pom_Page(driver);
		// add contact icon
		contactUtil.getAddContact();

		// contact last name
		createContactUtil.getLastname().sendKeys(cname);

		// parent window id
		String pwid = wbutil.getParentWindowID(driver);

		// org + icon
		createContactUtil.getOrgAddBtn().click();

		// handling multiple windows
		// get windows
		Set<String> wids = wbutil.getMultipleWindowIDs(driver);

		// change the driver control to the child window
		wbutil.changeTheDrivertoChildWindowURL(driver, wids, "module=Accounts&action");
		createContactUtil.getOrgSearchTF().sendKeys(Org);
		createContactUtil.getOrgSearchBtn().click();
//		driver.findElement(By.id("search_txt")).sendKeys(Org);
//		driver.findElement(By.name("search")).click();
		driver.findElement(By.xpath("//a[text()='" + Org + "']")).click();

		// change the driver control to the parent window
		wbutil.SwithcBacktoPArent(driver, pwid);

		// contact save button
		createContactUtil.getSaveBtn().click();
		
		//contact details utility
		Contact_Details_Pom_page contactDetailsUtil = new Contact_Details_Pom_page(driver);

		String cnameval = contactDetailsUtil.getLastnameVal();

		if (cnameval.contains(cname)) {
			System.out.println("Create Contact with Org is pass-(cname)");
		} else {
			System.out.println("Create Org with Type and Industry is fail-(cname)");
		}
		
		String orgval = contactDetailsUtil.getOrgVal();
		if (orgval.contains(Org)) {
			System.out.println("Create Contact with Org is pass-(Orgname)");
		} else {
			System.out.println("Create Contact with Org is fail-(cname)");
		}
		// Contacts link
		driver.findElement(By.linkText("Contacts")).click();

		// delete contact
		driver.findElement(
				By.xpath("//a[text()='" + cname + "']/ancestor::tr[@bgcolor='white']/descendant::a[text()='del']"))
				.click();

		Thread.sleep(3000);
		// for alert
		wbutil.handleAlertPopuAndClickOnAccept(driver);

		Thread.sleep(3000);

		// profile icon
		homeUtil.getAdmin().click();

		// Sign out button
		homeUtil.getSignout_link().click();

		Thread.sleep(2000);

		// close the browser
		wbutil.closeBrowser(driver);

	}

}
