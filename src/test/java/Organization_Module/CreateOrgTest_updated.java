package Organization_Module;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
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
import PomUtility.Create_Org_Pom_Page;
import PomUtility.Home_Pom_Page;
import PomUtility.Login_Pom_Page;
import PomUtility.Organization_Details_Pom_page;
import PomUtility.Organization_Pom_Page;

//Using DDT
public class CreateOrgTest_updated {

	@Test (groups = "Smoke")
	public void createNewOrgTest() throws InterruptedException, IOException {
		// properties
		PropertyFileUtility p = new PropertyFileUtility();
		String browser = p.fetchDataFromPropFile("browser");
		String url = p.fetchDataFromPropFile("url");
		String username = p.fetchDataFromPropFile("username");
		String password = p.fetchDataFromPropFile("password");
		String Time = p.fetchDataFromPropFile("timeouts");

		// java utility
		JavaUtility jutil = new JavaUtility();
		int randomnum=jutil.generateRandomNumber();
//		// Excel
		ExcelFileUtility exutil = new ExcelFileUtility();
		String Org=exutil.fetchDataFromExcelFile("Organization", 1, 2)+randomnum;
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

		//Login pom utility
		Login_Pom_Page loginutil = new Login_Pom_Page(driver);
		loginutil.login(username, password);
		
		//Home page pom utility
		// identify org link
		Home_Pom_Page homeUtil = new Home_Pom_Page(driver);
		homeUtil.getOrg_link().click();

		//Organization page pom utility
		//Click on add org btn
		Organization_Pom_Page orgUtil = new Organization_Pom_Page(driver);
		orgUtil.getclickonOrgAddbtn();
		
		//Create Organization Pom page
		Create_Org_Pom_Page createOrgUtil = new Create_Org_Pom_Page(driver);
		
		//Org name TF
		createOrgUtil.getOrgNameTF().sendKeys(Org);

		// click in save
		createOrgUtil.getSaveBtn().click();
		
		//Organization Details pom utility
		Organization_Details_Pom_page orgDetailUtil = new Organization_Details_Pom_page(driver);

		// Org value
		String orgNameVal = orgDetailUtil.getOrgNameVal();

		if (orgNameVal.contains(Org)) {

			System.out.println("Create Org test pass");

		} else {
			System.out.println("Create org test faill ");
		}
		// identify org link
		homeUtil.getOrg_link().click();

		// delete the org
		driver.findElement(
				By.xpath("//a[text()='" + Org + "']/ancestor::tr[@bgcolor=\"white\"]/descendant::a[text()='del']"))
				.click();

		// for alert
		wbutil.handleAlertPopuAndClickOnAccept(driver);

		Thread.sleep(3000);

		// progile icon
		homeUtil.getAdmin().click();

		// Sign out button
		homeUtil.getSignout_link().click();

		Thread.sleep(2000);

		// close the browser
		wbutil.closeBrowser(driver);

	}

}
