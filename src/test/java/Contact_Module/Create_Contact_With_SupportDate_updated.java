package Contact_Module;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
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
import PomUtility.Contact_Details_Pom_page;
import PomUtility.Contact_Pom_Page;
import PomUtility.Create_Contact_Pom_page;
import PomUtility.Home_Pom_Page;
import PomUtility.Login_Pom_Page;

public class Create_Contact_With_SupportDate_updated {

	@Test(groups = "regression")
	public void CreateConWithSupportdate() throws IOException, InterruptedException {

//		// Properties File DDT
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
		// using Excel File generic utilites
		ExcelFileUtility exutil = new ExcelFileUtility();
		String cname = exutil.fetchDataFromExcelFile("Contact", 1, 2) + randomnum;
		exutil.closeExcelFile();

		// Script
		// Launch Browser
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

		// Contacts link
		Home_Pom_Page homeUtil = new Home_Pom_Page(driver);
		homeUtil.getContacts_link().click();
		// add contact icon
		Contact_Pom_Page conUtil = new Contact_Pom_Page(driver);
		conUtil.getAddContact();

		// contact last name
		Create_Contact_Pom_page createConUtil = new Create_Contact_Pom_page(driver);

		createConUtil.getLastname().sendKeys(cname);

		// fetch the current date
		String strtDate = jutil.getSystemCurrentDate();
		Create_Contact_Pom_page createContactUtil = new Create_Contact_Pom_page(driver);
		// start date TF
		WebElement strtdateTF = createContactUtil.getSupportStartDate();
		// clear the TF
		strtdateTF.clear();
		// Enter the start date
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

		// save button
		createContactUtil.getSaveBtn().click();
		
		//Contact Details Utility
		Contact_Details_Pom_page contactDetailsUtil = new Contact_Details_Pom_page(driver);
		// validation of contact name
		String cnameval = contactDetailsUtil.getLastnameVal();

		if (cnameval.contains(cname)) {

			System.out.println("Create contact test pass");
		} else {
			System.out.println("create contact test fail");
		}

		// validation of start date
		String startDateVal = contactDetailsUtil.getSupportStrtDateVal();

		if (startDateVal.contains(strtDate)) {
			System.out.println("Create contact with start date is pass");
		} else {
			System.out.println("Create contact with start date is fail");
		}
		// validation of end date
		String endDateVal = contactDetailsUtil.getSupportEndDateVal();

		if (endDateVal.contains(endDate)) {
			System.out.println("Create contact with end date is pass");
		} else {
			System.out.println("Create contact with end date is fail");
		}

		// Contacts link
		homeUtil.getContacts_link().click();

		// delete contact
		driver.findElement(
				By.xpath("//a[text()='" + cname + "']/ancestor::tr[@bgcolor='white']/descendant::a[text()='del']"))
				.click();

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
