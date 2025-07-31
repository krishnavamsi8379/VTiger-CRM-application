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
import GenericUtilites.PropertyFileUtility;

public class Create_Org_With_Phone_Num {
	
	@Test
	public void createOrgWithPhoneNum() throws IOException, InterruptedException {
		//Properties File DDT
//		FileInputStream pfis = new FileInputStream("./src/test/resources/Vtiger.properties");
//	    Properties prop = new Properties();
//	    prop.load(pfis);
//	    String url = prop.getProperty("url");
//	    String username = prop.getProperty("username");
//	    String password = prop.getProperty("password");
//	    String Time = prop.getProperty("timeouts");
		
		// usign Generic File Utilites
		PropertyFileUtility prop = new PropertyFileUtility();
		String url = prop.fetchDataFromPropFile("url");
	    String username = prop.fetchDataFromPropFile("username");
	    String password = prop.fetchDataFromPropFile("password");
	    String Time = prop.fetchDataFromPropFile("timeouts");
	   
	    
	    
//	    //Excel File DDt
//	    FileInputStream efis= new FileInputStream("./src/test/resources/Vtiger_CRM data.xlsx");
//	    Workbook wb = WorkbookFactory.create(efis);
//	    String org = wb.getSheet("Organization").getRow(4).getCell(2).toString();
//	    String phno = wb.getSheet("Organization").getRow(4).getCell(3).toString();
	    
	    ExcelFileUtility exeutil = new ExcelFileUtility();
	    String org=exeutil.fetchDataFromExcelFile("Organization", 4, 2);
	    String phno=exeutil.fetchDataFromExcelFile("Organization", 4, 3);
	    exeutil.closeExcelFile();
	    
	    System.out.println(phno);
	
	   //Script
	   //Launch Browser
	    WebDriver driver = new ChromeDriver();
	    System.out.println(url);
	    driver.manage().window().maximize();
	    Long time=Long.parseLong(Time);
	    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(time));
	    //navigate to app
	    driver.get(url);
	    
	    driver.findElement(By.name("user_name")).sendKeys(username);
	   
	    driver.findElement(By.name("user_password")).sendKeys(password);
	    
	    driver.findElement(By.id("submitButton")).click();
	    
	    //Organizations link
	    WebElement Org_link = driver.findElement(By.linkText("Organizations"));
	    Org_link.click();
	    
	    //Organizations add icon
	    driver.findElement(By.xpath("//img[@title='Create Organization...']")).click();
	    
	    //Enter Org name
	    driver.findElement(By.name("accountname")).sendKeys(org);
	    
	    /* conversion of phone number String - long - String
	      long Ph_no=Long.parseLong(phno);
	      DecimalFormat df = new DecimalFormat("0"); 
		  String ph_no = df.format(Ph_no); */
	   
	    //enter phone number
	    driver.findElement(By.id("phone")).sendKeys(phno);
	    
	    // click in save
	 	driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
	 	
	 	//Validation of org
	 	WebElement orgval = driver.findElement(By.xpath("//span[contains(text(),'"+org+"')]"));
	 	
	 	//validation of phone number
	 	WebElement phno_val = driver.findElement(By.id("dtlview_Phone"));
	 	
	 	//Validation condition for both Org and phone number
	 	if(orgval.getText().contains(org) && phno_val.getText().contains(phno)) {
	 		System.out.println("Create Org with Phone number is pass");
	 	}
	 	else
	 	{
	 		System.out.println("Create Org with Phone number is fail");
	 	}
	 	
	 	driver.findElement(By.linkText("Organizations")).click();
	 	
	 		// delete the org
	 		driver.findElement(By.xpath("//a[text()='" + org + "']/ancestor::tr[@bgcolor=\"white\"]/descendant::a[text()='del']")).click();

	 		// for alert
	 		Alert al = driver.switchTo().alert();

	 		al.accept();

	 		Thread.sleep(3000);

	 		// progile icon
	 		driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']")).click();

	 		// Sign out button
	 		driver.findElement(By.xpath("//a[text()='Sign Out']")).click();

	 		Thread.sleep(2000);

	 		// close the browser
	 		driver.quit();
	    
	}

}
