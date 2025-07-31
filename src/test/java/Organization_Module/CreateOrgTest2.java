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
import GenericUtilites.PropertyFileUtility;

//Using DDT
public class CreateOrgTest2 {

	@Test
	public void createNewOrgTest() throws InterruptedException, IOException {
		// properties
//		FileInputStream pfis = new FileInputStream("./src/test/resources/Vtiger.properties");
//
//		Properties p = new Properties();
//
//		p.load(pfis);
//
//		String browser = p.getProperty("browser");
//		String url = p.getProperty("url");
//		String username = p.getProperty("username");
//		String password = p.getProperty("password");
//		String Time = p.getProperty("timeouts");
		
		PropertyFileUtility p = new PropertyFileUtility();
		String browser = p.fetchDataFromPropFile("browser");
		String url = p.fetchDataFromPropFile("url");
		String username = p.fetchDataFromPropFile("username");
		String password = p.fetchDataFromPropFile("password");
		String Time = p.fetchDataFromPropFile("timeouts");

//		// Excel
//		FileInputStream efis = new FileInputStream("./src/test/resources/Vtiger_CRM data.xlsx");
//
//		Workbook wb = WorkbookFactory.create(efis);
//
//		String Org = wb.getSheet("Organization").getRow(1).getCell(2).toString();
		
		ExcelFileUtility exutil = new ExcelFileUtility();
		String Org=exutil.fetchDataFromExcelFile("Organization", 1, 2);
		exutil.closeExcelFile();

		// open Browser
		WebDriver driver = new ChromeDriver();

		// maximize window
		driver.manage().window().maximize();

		// convert the string to long
		Long time = Long.parseLong(Time);

		// Implicit wait stmt
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(time));

		// navigate to applciation
		driver.get(url);

		// Enter user name
		driver.findElement(By.name("user_name")).sendKeys(username);

		// Enter password
		driver.findElement(By.name("user_password")).sendKeys(password);

		// click on login button
		driver.findElement(By.id("submitButton")).click();

		// identify org link
		driver.findElement(By.linkText("Organizations")).click();

		// add org icon
		driver.findElement(By.xpath("//img[@title='Create Organization...']")).click();

		// enter the org name
		driver.findElement(By.name("accountname")).sendKeys(Org);

		// click in save
		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();

		// Org value
		WebElement org = driver.findElement(By.xpath("//span[contains(text(),'" + Org + "')]"));

		if (org.getText().contains(Org)) {

			System.out.println("Create Org test pass");

		} else {
			System.out.println("Create org test faill ");
		}
		// identify org link
		driver.findElement(By.linkText("Organizations")).click();

		// delete the org
		driver.findElement(
				By.xpath("//a[text()='" + Org + "']/ancestor::tr[@bgcolor=\"white\"]/descendant::a[text()='del']"))
				.click();

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
