package Organization_Module;

import java.io.IOException;

import Base_Class.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import GenericUtilites.ExcelFileUtility;
import GenericUtilites.JavaUtility;
import GenericUtilites.PropertyFileUtility;
import GenericUtilites.WebDriverUtility;
import Listners_Utilities.Listners;
import PomUtility.Create_Org_Pom_Page;
import PomUtility.Home_Pom_Page;
import PomUtility.Login_Pom_Page;
import PomUtility.Organization_Details_Pom_page;
import PomUtility.Organization_Pom_Page;

@Listeners(Listners_Utilities.Listners.class)
public class Organization_Scenarios extends Base_Class  {
	
	//@Parameters("browser")
	@Test (groups = "Smoke", retryAnalyzer = Listners_Utilities.IRetryAnalyzerUtility.class)
	public void createNewOrgTest() throws InterruptedException, IOException {
		

		// java utility
		Listners.test.log(Status.INFO, "Java Utility to generate Random number");
		JavaUtility jutil = new JavaUtility();
		int randomnum=jutil.generateRandomNumber();
//		// Excel
		Listners.test.log(Status.INFO, "fetch data from Excel");
		ExcelFileUtility exutil = new ExcelFileUtility();
		String Org=exutil.fetchDataFromExcelFile("Organization", 1, 2)+randomnum;
		exutil.closeExcelFile();

		
		//Home page pom utility
		// identify org link
		Listners.test.log(Status.INFO, "identify org link");
		Home_Pom_Page homeUtil = new Home_Pom_Page(driver);
		homeUtil.getOrg_link().click();

		//Organization page pom utility
		Organization_Pom_Page orgUtil = new Organization_Pom_Page(driver);
		//Click on add org btn
		Listners.test.log(Status.INFO, "click on add org button");
		orgUtil.getclickonOrgAddbtn();
		
		//Create Organization Pom page
		Create_Org_Pom_Page createOrgUtil = new Create_Org_Pom_Page(driver);
		
		//Org name TF
		Listners.test.log(Status.INFO, "enter ORg name to ORg name text field");
		createOrgUtil.getOrgNameTF().sendKeys(Org);

		// click in save
		Listners.test.log(Status.INFO, "Click on save button");
		createOrgUtil.getSaveBtn().click();
		
		//Organization Details pom utility
		Organization_Details_Pom_page orgDetailUtil = new Organization_Details_Pom_page(driver);

		// Org value
		String orgNameVal = orgDetailUtil.getOrgNameVal();
		
		Assert.assertEquals(orgNameVal,Org);
		Reporter.log("create ORG pass",true);
		Listners.test.log(Status.PASS, "create ORG pass");
		
		// identify org link
		Listners.test.log(Status.INFO, "identify org link");
		homeUtil.getOrg_link().click();
		

		// delete the org
		Listners.test.log(Status.INFO, "click on delete org link");
		driver.findElement(
				By.xpath("//a[text()='" + Org + "']/ancestor::tr[@bgcolor=\"white\"]/descendant::a[text()='del']"))
				.click();
		Thread.sleep(3000);
		// for alert
		Listners.test.log(Status.INFO, "click on delete org link");
		wbutil.handleAlertPopuAndClickOnAccept(driver);
		Thread.sleep(3000);

	}
	
	
	//@Parameters("browser")
	@Test(groups = "regression",retryAnalyzer = Listners_Utilities.IRetryAnalyzerUtility.class)
	public void createOrgWithIndustryType() throws IOException, InterruptedException {
		
		// java utility
		Listners.test.log(Status.INFO, "Java Utility to generate Random number");
		JavaUtility jutil = new JavaUtility();
		int randomnum = jutil.generateRandomNumber();

//	    //Excel File DDt
		Listners.test.log(Status.INFO, "fetch data from Excel");
		ExcelFileUtility exutil = new ExcelFileUtility();
		String org = exutil.fetchDataFromExcelFile("Organization", 7, 2) + randomnum;
		String industry = exutil.fetchDataFromExcelFile("Organization", 7, 3);
		String type = exutil.fetchDataFromExcelFile("Organization", 7, 4);
		exutil.closeExcelFile();

		
		// Home page pom utility
		// identify org link
		Listners.test.log(Status.INFO, "identify org link");
		Home_Pom_Page homeUtil = new Home_Pom_Page(driver);
		homeUtil.getOrg_link().click();

		// Organization page pom utility
		// Click on add org btn
		Organization_Pom_Page orgUtil = new Organization_Pom_Page(driver);
		Listners.test.log(Status.INFO, "click on add org button");
		orgUtil.getclickonOrgAddbtn();

		// Create Organization Pom page
		Create_Org_Pom_Page createOrgUtil = new Create_Org_Pom_Page(driver);

		// Org name TF
		Listners.test.log(Status.INFO, "enter ORg name to ORg name text field");
		createOrgUtil.getOrgNameTF().sendKeys(org);

//	    //Industry Dropdown
		Listners.test.log(Status.INFO, "identify industry dropdown");
		WebElement industry_dd = createOrgUtil.getIndustry_DD();
//	    
//	    Select ind_s = new Select(industry_dd);
//	    
//	    ind_s.selectByValue(industry);
		// handle dropdown using webdriver utilities
		Listners.test.log(Status.INFO, "Select the indusrtry dropdown");
		wbutil.SelectValueinDropdown(industry_dd, industry);

		// Type dropdown
		Listners.test.log(Status.INFO, "identify Type dropdown");
		WebElement type__dd = createOrgUtil.getAccounttype_DD();
		Listners.test.log(Status.INFO, "Select the Type dropdown");
		wbutil.SelectValueinDropdown(type__dd, type);

		// click in save
		Listners.test.log(Status.INFO, "Click on save button");
		createOrgUtil.getSaveBtn().click();
		
		Organization_Details_Pom_page orgDetailsUtil = new Organization_Details_Pom_page(driver);

		// Validation of org
		String orgval = orgDetailsUtil.getOrgNameVal();

		// Validation value of Industry
		String ind_val = orgDetailsUtil.getIndDDValue();

		// validation value of type
		String typ_val = orgDetailsUtil.getTypDDValue();

		// Validation condition for Org and Industry and Type
		Assert.assertEquals(orgval,org);
		Reporter.log("Create Org with Type and Industry is pass-(Org name)",true);
		Listners.test.log(Status.PASS, "Create Org with Type and Industry is pass-(Org name)");
		Assert.assertEquals(ind_val, industry);
		Reporter.log("Create Org with Type and Industry is pass-(Industry DD)",true);
		Listners.test.log(Status.PASS, "Create Org with Type and Industry is pass-(Industry DD)");
		Assert.assertEquals(typ_val,type);
		Listners.test.log(Status.PASS, "Create Org with Type and Industry is pass-(Type DD)");
		Reporter.log("Create Org with Type and Industry is pass-(Type DD)",true);

		// click on org link
		Listners.test.log(Status.INFO, "click on org link");
		homeUtil.getOrg_link().click();

		// delete the org
		Listners.test.log(Status.INFO, "click on delete org link");
		driver.findElement(
				By.xpath("//a[text()='" + org + "']/ancestor::tr[@bgcolor=\"white\"]/descendant::a[text()='del']"))
				.click();

		// for alert
		Listners.test.log(Status.INFO, "click on delete org link");
		wbutil.handleAlertPopuAndClickOnAccept(driver);

		Thread.sleep(3000);

	}

	//@Parameters("browser")
	@Test(groups = "regression",retryAnalyzer = Listners_Utilities.IRetryAnalyzerUtility.class)
	public void createOrgWithPhoneNum() throws IOException, InterruptedException {

		// java utility
		JavaUtility jutil = new JavaUtility();
		int randomnum = jutil.generateRandomNumber();
		Listners.test.log(Status.INFO, "Java Utility to generate Random number");

		// Excel File DDt
		ExcelFileUtility exeutil = new ExcelFileUtility();
		String org = exeutil.fetchDataFromExcelFile("Organization", 4, 2) + randomnum;
		String phno = exeutil.fetchDataFromExcelFile("Organization", 4, 3);
		exeutil.closeExcelFile();
		Listners.test.log(Status.INFO, "fetch data from Excel");


		// Home page pom utility
		// identify org link
		Home_Pom_Page homeUtil = new Home_Pom_Page(driver);
		homeUtil.getOrg_link().click();
		Listners.test.log(Status.INFO, "identify org link");

		// Organization page pom utility
		// Click on add org btn
		Organization_Pom_Page orgUtil = new Organization_Pom_Page(driver);
		orgUtil.getclickonOrgAddbtn();
		Listners.test.log(Status.INFO, "click on add org button");

		// Create Organization Pom page
		Create_Org_Pom_Page createOrgUtil = new Create_Org_Pom_Page(driver);

		// Org name TF
		createOrgUtil.getOrgNameTF().sendKeys(org);
		Listners.test.log(Status.INFO, "enter ORg name to ORg name text field");

		/*
		 * conversion of phone number String - long - String long
		 * Ph_no=Long.parseLong(phno); DecimalFormat df = new DecimalFormat("0"); String
		 * ph_no = df.format(Ph_no);
		 */

		// enter phone number
		createOrgUtil.getPhoneNumTF().sendKeys(phno);
		Listners.test.log(Status.INFO, "enter phone number name to phone number text field");

		// click in save
		createOrgUtil.getSaveBtn().click();
		Listners.test.log(Status.INFO, "Click on save button");

		// Organization Details pom utility
		Organization_Details_Pom_page orgDetailUtil = new Organization_Details_Pom_page(driver);

		// Org value
		String orgNameVal = orgDetailUtil.getOrgNameVal();
		
		Assert.assertEquals(orgNameVal,org);
		Reporter.log("Create ORG pass - ph number",true);
		Listners.test.log(Status.INFO, "Create ORG pass - ph number");

		// validation of phone number
		String phno_val = orgDetailUtil.getPhNumVal();

		// Validation condition for both Org and phone number
		Assert.assertEquals(phno_val, phno);
		Reporter.log("Create Org with Phone number is pass",true);
		Listners.test.log(Status.INFO, "Create Org with Phone number is pass");

		// click on org link
		homeUtil.getOrg_link().click();
		Listners.test.log(Status.INFO, "click on org link");

		

		// delete the org
		driver.findElement(
				By.xpath("//a[text()='" + org + "']/ancestor::tr[@bgcolor=\"white\"]/descendant::a[text()='del']"))
				.click();
		Listners.test.log(Status.INFO, "click on delete org link");

		// for alert
		wbutil.handleAlertPopuAndClickOnAccept(driver);
		Listners.test.log(Status.INFO, "click on ok in the popup");

		Thread.sleep(3000);

	}
	

}
