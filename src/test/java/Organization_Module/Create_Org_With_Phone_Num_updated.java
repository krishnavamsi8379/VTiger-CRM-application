package Organization_Module;

import java.io.IOException;
import java.text.DecimalFormat;
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

public class Create_Org_With_Phone_Num_updated {

	@Test(groups = "regression")
	public void createOrgWithPhoneNum() throws IOException, InterruptedException {
		// Properties File DDT
		// usign Generic File Utilites
		PropertyFileUtility prop = new PropertyFileUtility();
		String url = prop.fetchDataFromPropFile("url");
		String username = prop.fetchDataFromPropFile("username");
		String password = prop.fetchDataFromPropFile("password");
		String Time = prop.fetchDataFromPropFile("timeouts");

		// java utility
		JavaUtility jutil = new JavaUtility();
		int randomnum = jutil.generateRandomNumber();

		// Excel File DDt
		ExcelFileUtility exeutil = new ExcelFileUtility();
		String org = exeutil.fetchDataFromExcelFile("Organization", 4, 2) + randomnum;
		String phno = exeutil.fetchDataFromExcelFile("Organization", 4, 3);
		exeutil.closeExcelFile();

		System.out.println(phno);

		// Script
		// Launch Browser
		WebDriver driver = new ChromeDriver();
		// using webdriver utilitys
		WebDriverUtility wbutil = new WebDriverUtility();
		// maximize the window
		wbutil.maximizeTheWindow(driver);
		// implicit wait stmt
		wbutil.implcitWaitStmt(driver, Time);
		// navigate to app
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

		// Org name TF
		createOrgUtil.getOrgNameTF().sendKeys(org);

		/*
		 * conversion of phone number String - long - String long
		 * Ph_no=Long.parseLong(phno); DecimalFormat df = new DecimalFormat("0"); String
		 * ph_no = df.format(Ph_no);
		 */

		// enter phone number
		createOrgUtil.getPhoneNumTF().sendKeys(phno);

		// click in save
		createOrgUtil.getSaveBtn().click();

		// Organization Details pom utility
		Organization_Details_Pom_page orgDetailUtil = new Organization_Details_Pom_page(driver);

		// Org value
		String orgNameVal = orgDetailUtil.getOrgNameVal();

		if (orgNameVal.contains(org)) {

			System.out.println("Create Org test pass");

		} else {
			System.out.println("Create org test faill ");
		}

		// validation of phone number
		String phno_val = orgDetailUtil.getPhNumVal();

		// Validation condition for both Org and phone number
		if (phno_val.contains(phno)) {
			System.out.println("Create Org with Phone number is pass");
		} else {
			System.out.println("Create Org with Phone number is fail");
		}

		// click on org link
		homeUtil.getOrg_link().click();

		// delete the org
		driver.findElement(
				By.xpath("//a[text()='" + org + "']/ancestor::tr[@bgcolor=\"white\"]/descendant::a[text()='del']"))
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
