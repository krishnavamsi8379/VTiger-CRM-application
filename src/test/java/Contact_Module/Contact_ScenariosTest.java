package Contact_Module;

import Base_Class.Base_Class;


import java.io.IOException;
import java.util.Set;


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
import PomUtility.Contact_Details_Pom_page;
import PomUtility.Contact_Pom_Page;
import PomUtility.Create_Contact_Pom_page;
import PomUtility.Create_Org_Pom_Page;
import PomUtility.Home_Pom_Page;
import PomUtility.Login_Pom_Page;
import PomUtility.Organization_Pom_Page;

@Listeners(Listners_Utilities.Listners.class)

public class Contact_ScenariosTest extends Base_Class {

	//@Parameters("browser")
	@Test(retryAnalyzer = Listners_Utilities.IRetryAnalyzerUtility.class)
	public void CreateContactTest() throws IOException, InterruptedException {

		// java utility
		JavaUtility jutil = new JavaUtility();
		Listners.test.log(Status.INFO, "Java Utility to generate Random number");
		int randomnum = jutil.generateRandomNumber();
		

//		// Excel File DDt
		Listners.test.log(Status.INFO, "fetch data from Excel");
		ExcelFileUtility exutil = new ExcelFileUtility();
		String cname = exutil.fetchDataFromExcelFile("Contact", 1, 2) + randomnum;
		exutil.closeExcelFile();
		


		// Contacts link
		Home_Pom_Page homeUtil = new Home_Pom_Page(driver);
		homeUtil.getContacts_link().click();
		Listners.test.log(Status.INFO, "Navigate to Contacts Module");

		// add contact icon
		Contact_Pom_Page conUtil = new Contact_Pom_Page(driver);
		conUtil.getAddContact();
		Listners.test.log(Status.INFO, "click on add contact button");

		// contact last name
		Create_Contact_Pom_page createConUtil = new Create_Contact_Pom_page(driver);

		createConUtil.getLastname().sendKeys(cname);
		Listners.test.log(Status.INFO, "enter Contact name to last name text field");

		// save button
		createConUtil.getSaveBtn().click();
		Listners.test.log(Status.INFO, "Create a Contact");

		// validation of contact name

		Contact_Details_Pom_page contactDetailsUtil = new Contact_Details_Pom_page(driver);

		String cnameval = contactDetailsUtil.getLastnameVal();
		
		Assert.assertEquals(cnameval, cname);
		Reporter.log("Create contact test pass");
		Listners.test.log(Status.PASS, "Create contact test pass");
		// Contacts link
		homeUtil.getContacts_link().click();
		Listners.test.log(Status.INFO, "Navigate to Contacts Module");

		// delete contact
		driver.findElement(
				By.xpath("//a[text()='" + cname + "']/ancestor::tr[@bgcolor='white']/descendant::a[text()='del']"))
				.click();
		Listners.test.log(Status.INFO, "click on delete contact link and delete a contact");
		// for alert
		wbutil.handleAlertPopuAndClickOnAccept(driver);
		Listners.test.log(Status.INFO, "click on ok in the popup");

	}
	
	//@Parameters("browser")
	@Test(groups = "regression",retryAnalyzer = Listners_Utilities.IRetryAnalyzerUtility.class)
	public void CreateConWithSupportdateTest() throws IOException, InterruptedException {

		// java utility
		JavaUtility jutil = new JavaUtility();
		Listners.test.log(Status.INFO, "Java Utility to generate Random number");
		int randomnum = jutil.generateRandomNumber();

		// Excel File DDt
		// using Excel File generic utilites
		Listners.test.log(Status.INFO, "fetch data from Excel");
		ExcelFileUtility exutil = new ExcelFileUtility();
		String cname = exutil.fetchDataFromExcelFile("Contact", 1, 2) + randomnum;
		exutil.closeExcelFile();

		// Script

		// Contacts link
		Home_Pom_Page homeUtil = new Home_Pom_Page(driver);
		homeUtil.getContacts_link().click();
		Listners.test.log(Status.INFO, "Navigate to Contacts Module");
		// add contact icon
		Contact_Pom_Page conUtil = new Contact_Pom_Page(driver);
		conUtil.getAddContact();
		Listners.test.log(Status.INFO, "click on add contact button");

		// contact last name
		Create_Contact_Pom_page createConUtil = new Create_Contact_Pom_page(driver);

		createConUtil.getLastname().sendKeys(cname);
		Listners.test.log(Status.INFO, "enter Contact name to last name text field");

		// fetch the current date
		String strtDate = jutil.getSystemCurrentDate();
		Create_Contact_Pom_page createContactUtil = new Create_Contact_Pom_page(driver);
		// start date TF
		WebElement strtdateTF = createContactUtil.getSupportStartDate();
		// clear the TF
		strtdateTF.clear();
		// Enter the start date
		Listners.test.log(Status.INFO, "enter start date to start date text field");
		strtdateTF.sendKeys(strtDate);
		System.out.println(strtDate);

		// last date +30days
		String endDate = jutil.getDateAfterSpecifiedDays(30);
		System.out.println(endDate);

		// identify end date TF
		WebElement endDateTF = createContactUtil.getSupportEndDate();
		endDateTF.clear();
		// enter the end date to the TF
		endDateTF.sendKeys(endDate);
		Listners.test.log(Status.INFO, "enter end date to end date text field");

		// save button
		createContactUtil.getSaveBtn().click();
		Listners.test.log(Status.INFO, "Click on save button");

		// Contact Details Utility
		Contact_Details_Pom_page contactDetailsUtil = new Contact_Details_Pom_page(driver);
		// validation of contact name
		String cnameval = contactDetailsUtil.getLastnameVal();
		
		Assert.assertEquals(cnameval, cname);
		Reporter.log("Create contact test pass");
		Listners.test.log(Status.PASS, "Create contact test pass");

		// validation of start date
		String startDateVal = contactDetailsUtil.getSupportStrtDateVal();
		
		Assert.assertEquals(startDateVal, strtDate);
		Reporter.log("Create contact with start date is pass");
		Listners.test.log(Status.PASS, "Create contact with start date is pass");

		// validation of end date
		String endDateVal = contactDetailsUtil.getSupportEndDateVal();
		Assert.assertEquals(endDateVal, endDate);
		Reporter.log("Create contact with End date is pass");
		Listners.test.log(Status.PASS, "Create contact with End date is pass");

		// Contacts link
		homeUtil.getContacts_link().click();
		Listners.test.log(Status.INFO, "Navigate to Contacts Module");


		// delete contact
		driver.findElement(
				By.xpath("//a[text()='" + cname + "']/ancestor::tr[@bgcolor='white']/descendant::a[text()='del']"))
				.click();
		Listners.test.log(Status.INFO, "click on delete contact link and delete a contact");


		// for alert
		wbutil.handleAlertPopuAndClickOnAccept(driver);
		Listners.test.log(Status.INFO, "click on ok in the popup");

	}
	
	//
	//@Parameters("browser")
	@Test(groups = "regression",retryAnalyzer = Listners_Utilities.IRetryAnalyzerUtility.class)
	public void createNewOrgConTest() throws InterruptedException, IOException {
		// java utility
		Listners.test.log(Status.INFO, "Java Utility to generate Random number");
		JavaUtility jutil = new JavaUtility();
		int randomnum = jutil.generateRandomNumber();

//		// Excel
//		using Excel File generic utilites
		Listners.test.log(Status.INFO, "fetch data from Excel");
		ExcelFileUtility exutil = new ExcelFileUtility();
		String cname = exutil.fetchDataFromExcelFile("Contact", 7, 3) + randomnum;
		String Org = exutil.fetchDataFromExcelFile("Contact", 7, 2) + randomnum;
		exutil.closeExcelFile();

		// Home page pom utility
		// identify org link
		Home_Pom_Page homeUtil = new Home_Pom_Page(driver);
		homeUtil.getOrg_link().click();
		Listners.test.log(Status.INFO, "identify and navigate to Org module");


		// Organization page pom utility
		// Click on add org btn
		Organization_Pom_Page orgUtil = new Organization_Pom_Page(driver);
		orgUtil.getclickonOrgAddbtn();
		Listners.test.log(Status.INFO, "click on add Org button");

		// Create Organization Pom page
		Create_Org_Pom_Page createOrgUtil = new Create_Org_Pom_Page(driver);

		// enter the org name
		createOrgUtil.getOrgNameTF().sendKeys(Org);
		Listners.test.log(Status.INFO, "enter ORg name to ORg name text field");

		// click in save
		createOrgUtil.getSaveBtn().click();
		Listners.test.log(Status.INFO, "Click on save button");

		Thread.sleep(4000);

		// Contacts link (2nd)
		homeUtil.getContacts_link().click();
		Listners.test.log(Status.INFO, "Navigate to Contacts Module");

		// add contact icon
		Create_Contact_Pom_page createContactUtil = new Create_Contact_Pom_page(driver);
		
		Contact_Pom_Page contactUtil = new Contact_Pom_Page(driver);
		// add contact icon
		contactUtil.getAddContact();
		Listners.test.log(Status.INFO, "click on add contact button");

		// contact last name
		createContactUtil.getLastname().sendKeys(cname);
		Listners.test.log(Status.INFO, "enter Contact name to last name text field");
		// parent window id
		String pwid = wbutil.getParentWindowID(driver);

		// org + icon
		createContactUtil.getOrgAddBtn().click();
		Listners.test.log(Status.INFO, "click on add org button");

		// handling multiple windows
		// get windows
		Set<String> wids = wbutil.getMultipleWindowIDs(driver);

		// change the driver control to the child window
		wbutil.changeTheDrivertoChildWindowURL(driver, wids, "module=Accounts&action");
		Listners.test.log(Status.INFO, "Switch to child window");
		createContactUtil.getOrgSearchTF().sendKeys(Org);
		createContactUtil.getOrgSearchBtn().click();
//		driver.findElement(By.id("search_txt")).sendKeys(Org);
//		driver.findElement(By.name("search")).click();
		driver.findElement(By.xpath("//a[text()='" + Org + "']")).click();
		Listners.test.log(Status.INFO, "Organization selected");

		// change the driver control to the parent window
		wbutil.SwithcBacktoPArent(driver, pwid);
		Listners.test.log(Status.INFO, "Switch back to parent window");

		// contact save button
		createContactUtil.getSaveBtn().click();
		Listners.test.log(Status.INFO, "Click on save button");
		
		//contact details utility
		Contact_Details_Pom_page contactDetailsUtil = new Contact_Details_Pom_page(driver);

		String cnameval = contactDetailsUtil.getLastnameVal();
		
		Assert.assertEquals(cnameval, cname);
		Reporter.log("Create Contact with Org is pass-(cname)",true);
		Listners.test.log(Status.PASS, "Create Contact with Org is pass-(cname)");
		
		
		String orgval = contactDetailsUtil.getOrgVal();
		Assert.assertEquals(orgval, Org);
		Reporter.log("Create Contact with Org is pass-(Orgname)",true);
		Listners.test.log(Status.PASS, "Create Contact with Org is pass-(Orgname)");
		
		Thread.sleep(3000);
		
		// Contacts link
		driver.findElement(By.linkText("Contacts")).click();
		Listners.test.log(Status.INFO, "Navigate to Contacts Module");

		// delete contact
		driver.findElement(
				By.xpath("//a[text()='" + cname + "']/ancestor::tr[@bgcolor='white']/descendant::a[text()='del']"))
				.click();
		Listners.test.log(Status.INFO, "click on delete contact link and delete a contact");

		

		Thread.sleep(3000);
		// for alert
		wbutil.handleAlertPopuAndClickOnAccept(driver);
		Listners.test.log(Status.INFO, "click on ok in the popup");


	}

}
