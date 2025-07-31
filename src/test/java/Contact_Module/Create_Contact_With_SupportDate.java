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
import GenericUtilites.PropertyFileUtility;

public class Create_Contact_With_SupportDate {

	@Test
	public void CreateConWithSupportdate() throws IOException, InterruptedException {

//		// Properties File DDT
//		FileInputStream pfis = new FileInputStream("./src/test/resources/Vtiger.properties");
//		Properties prop = new Properties();
//		prop.load(pfis);
//		String url = prop.getProperty("url");
//		String username = prop.getProperty("username");
//		String password = prop.getProperty("password");
//		String Time = prop.getProperty("timeouts");
		
		// usign Generic File Utilites
		PropertyFileUtility prop = new PropertyFileUtility();
		String url = prop.fetchDataFromPropFile("url");
		String username = prop.fetchDataFromPropFile("username");
		String password = prop.fetchDataFromPropFile("password");
		String Time = prop.fetchDataFromPropFile("timeouts");
			    
		// Excel File DDt
//		FileInputStream efis = new FileInputStream("./src/test/resources/Vtiger_CRM data.xlsx");
//		Workbook wb = WorkbookFactory.create(efis);
//		String cname = wb.getSheet("Contact").getRow(4).getCell(2).toString();
		
		//	using Excel File generic utilites
		ExcelFileUtility exutil = new ExcelFileUtility();
		String cname=exutil.fetchDataFromExcelFile("Contact", 1, 2);
		exutil.closeExcelFile();

		// Script
		// Launch Browser
		WebDriver driver = new ChromeDriver();
		System.out.println(url);
		driver.manage().window().maximize();
		Long time = Long.parseLong(Time);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(time));
		// navigate to app
		driver.get(url);

		driver.findElement(By.name("user_name")).sendKeys(username);

		driver.findElement(By.name("user_password")).sendKeys(password);

		driver.findElement(By.id("submitButton")).click();

		// Contacts link
		driver.findElement(By.linkText("Contacts")).click();
		// add contact icon
		driver.findElement(By.xpath("//img[@title='Create Contact...']")).click();

		// contact last name
		driver.findElement(By.name("lastname")).sendKeys(cname);

		// fetch the current date
		Date date = new Date();
		// to get in required date format
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
		// convert the date into req string format
		String strtDate = sim.format(date);

		// start date TF
		WebElement strtdateTF = driver.findElement(By.name("support_start_date"));
		// clear the TF
		strtdateTF.clear();
		// Enter the start date
		strtdateTF.sendKeys(strtDate);
		System.out.println(strtDate);

		// last date +30days
		// get the calender
		Calendar cal = sim.getCalendar();
		// add 30 days to the date
		cal.add(Calendar.DAY_OF_MONTH, 30);
		// get enddate
		String endDate = sim.format(cal.getTime());
		System.out.println(endDate);

		// identify end date TF
		WebElement endDateTF = driver.findElement(By.name("support_end_date"));
		endDateTF.clear();
		// enter the end date to the TF
		endDateTF.sendKeys(endDate);

		// save button
		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();

		// validation of contact name
		WebElement cnameval = driver.findElement(By.xpath("//span[contains(text(),'" + cname + "')]"));

		if (cnameval.getText().contains(cname)) {

			System.out.println("Create contact test pass");
		} else {
			System.out.println("create contact test fail");
		}

		// validation of start date
		WebElement startDateVal = driver.findElement(By.id("dtlview_Support Start Date"));

		if (startDateVal.getText().contains(strtDate)) {
			System.out.println("Create contact with start date is pass");
		} else {
			System.out.println("Create contact with start date is fail");
		}
		// validation of end date
		WebElement endDateVal = driver.findElement(By.id("dtlview_Support End Date"));

		if (endDateVal.getText().contains(endDate)) {
			System.out.println("Create contact with end date is pass");
		} else {
			System.out.println("Create contact with end date is fail");
		}
		
		//Contacts link
	    driver.findElement(By.linkText("Contacts")).click();
	    
	    //delete contact
	    driver.findElement(By.xpath("//a[text()='"+cname+"']/ancestor::tr[@bgcolor='white']/descendant::a[text()='del']")).click();
	    
	    // for alert
 		Alert al = driver.switchTo().alert();

 		al.accept();

 		Thread.sleep(3000);

 		// profile icon
 		driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']")).click();

 		// Sign out button
 		driver.findElement(By.xpath("//a[text()='Sign Out']")).click();

 		Thread.sleep(2000);

 		// close the browser
 		driver.quit();	   
		
		

	}

}
