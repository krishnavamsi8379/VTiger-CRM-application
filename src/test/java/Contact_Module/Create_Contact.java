package Contact_Module;

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
import GenericUtilites.PropertyFileUtility;
import GenericUtilites.WebDriverUtility;

public class Create_Contact {

	@Test
	public void CreateContactTest() throws IOException, InterruptedException {

//		//Properties File DDT
//				FileInputStream pfis = new FileInputStream("./src/test/resources/Vtiger.properties");
//			    Properties prop = new Properties();
//			    prop.load(pfis);
//			    String url = prop.getProperty("url");
//			    String username = prop.getProperty("username");
//			    String password = prop.getProperty("password");
//			    String Time = prop.getProperty("timeouts");
		// usign Generic File Utilites
		PropertyFileUtility prop = new PropertyFileUtility();
		String url = prop.fetchDataFromPropFile("url");
		String username = prop.fetchDataFromPropFile("username");
		String password = prop.fetchDataFromPropFile("password");
		String Time = prop.fetchDataFromPropFile("timeouts");

//		// Excel File DDt
//		FileInputStream efis = new FileInputStream("./src/test/resources/Vtiger_CRM data.xlsx");
//		Workbook wb = WorkbookFactory.create(efis);
//		String cname = wb.getSheet("Contact").getRow(1).getCell(2).toString();

		ExcelFileUtility exutil = new ExcelFileUtility();
		String cname = exutil.fetchDataFromExcelFile("Contact", 1, 2);
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

		driver.findElement(By.name("user_name")).sendKeys(username);

		driver.findElement(By.name("user_password")).sendKeys(password);

		driver.findElement(By.id("submitButton")).click();

		// Contacts link
		driver.findElement(By.linkText("Contacts")).click();
		// add contact icon
		driver.findElement(By.xpath("//img[@title='Create Contact...']")).click();

		// contact last name
		driver.findElement(By.name("lastname")).sendKeys(cname);

		// save button
		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();

		// validation of contact name
		WebElement cnameval = driver.findElement(By.xpath("//span[contains(text(),'" + cname + "')]"));

		if (cnameval.getText().contains(cname)) {

			System.out.println("Create contact test pass");
		} else {
			System.out.println("create contact test fail");
		}
		// Contacts link
		driver.findElement(By.linkText("Contacts")).click();

		// delete contact
		driver.findElement(
				By.xpath("//a[text()='" + cname + "']/ancestor::tr[@bgcolor='white']/descendant::a[text()='del']"))
				.click();

		// for alert
		wbutil.handleAlertPopuAndClickOnAccept(driver);

		Thread.sleep(3000);

		// progile icon
		driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']")).click();

		// Sign out button
		driver.findElement(By.xpath("//a[text()='Sign Out']")).click();

		Thread.sleep(2000);

		// close the browser
		wbutil.closeBrowser(driver);

	}

}
