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
import org.openqa.selenium.support.ui.Select;
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

public class Create_Org_With_Industry_Type_updated {

	@Test(groups = "regression")
	public void createOrgWithIndustryType() throws IOException, InterruptedException {
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

//	    //Excel File DDt
		ExcelFileUtility exutil = new ExcelFileUtility();
		String org = exutil.fetchDataFromExcelFile("Organization", 7, 2) + randomnum;
		String industry = exutil.fetchDataFromExcelFile("Organization", 7, 3);
		String type = exutil.fetchDataFromExcelFile("Organization", 7, 4);
		exutil.closeExcelFile();

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

//	    //Industry Dropdown
		WebElement industry_dd = createOrgUtil.getIndustry_DD();
//	    
//	    Select ind_s = new Select(industry_dd);
//	    
//	    ind_s.selectByValue(industry);
		// handle dropdown using webdriver utilities
		wbutil.SelectValueinDropdown(industry_dd, industry);

		// Type dropdown

		WebElement type__dd = createOrgUtil.getAccounttype_DD();

		wbutil.SelectValueinDropdown(type__dd, type);

		// click in save
		createOrgUtil.getSaveBtn().click();
		
		Organization_Details_Pom_page orgDetailsUtil = new Organization_Details_Pom_page(driver);

		// Validation of org
		String orgval = orgDetailsUtil.getOrgNameVal();

		// Validation value of Industry
		String ind_val = orgDetailsUtil.getIndDDValue();

		// validation value of type
		String typ_val = orgDetailsUtil.getTypDDValue();

		// Validation condition for Org and Industry and Type
		if (orgval.contains(org) ) {
			System.out.println("Create Org with Type and Industry is pass-(Org name)");
		} else {
			System.out.println("Create Org with Type and Industry is fail-(Org name)");
		}
		
		if (ind_val.contains(industry)) {
			System.out.println("Create Org with Type and Industry is pass-(Industry DD)");
		} else {
			System.out.println("Create Org with Type and Industry is fail-(Industry DD)");
		}
		
		if (typ_val.contains(type)) {
			System.out.println("Create Org with Type and Industry is pass-(Type DD)");
		} else {
			System.out.println("Create Org with Type and Industry is fail-(Type DD)");
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
