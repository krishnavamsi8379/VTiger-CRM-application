package Contact_Module;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.Set;

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
public class Create_Contact_With_Org {

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
		
		// usign Generic File Utilites
		PropertyFileUtility prop = new PropertyFileUtility();
		String url = prop.fetchDataFromPropFile("url");
		String username = prop.fetchDataFromPropFile("username");
		String password = prop.fetchDataFromPropFile("password");
		String Time = prop.fetchDataFromPropFile("timeouts");

//		// Excel
//		FileInputStream efis = new FileInputStream("./src/test/resources/Vtiger_CRM data.xlsx");
//
//		Workbook wb = WorkbookFactory.create(efis);
//
//		String Org = wb.getSheet("Contact").getRow(7).getCell(2).toString();
//		String cname = wb.getSheet("Contact").getRow(7).getCell(3).toString();
		
//		using Excel File generic utilites
			ExcelFileUtility exutil = new ExcelFileUtility();
			String cname=exutil.fetchDataFromExcelFile("Contact", 7, 2);
			String Org=exutil.fetchDataFromExcelFile("Contact", 7, 2);
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

		Thread.sleep(3000);

		// Contacts link (2nd)
		driver.findElement(By.linkText("Contacts")).click();
		// add contact icon
		driver.findElement(By.xpath("//img[@title='Create Contact...']")).click();

		// contact last name
		driver.findElement(By.name("lastname")).sendKeys(cname);

		// parent window id
		String pwid = driver.getWindowHandle();

		// org + icon
		driver.findElement(By.xpath("(//img[@alt='Select'])[1]")).click();

		// handling multiple windows
		// get windows
		Set<String> wids = driver.getWindowHandles();

		for (String s : wids) {
			driver.switchTo().window(s);
			if (driver.getCurrentUrl().contains("module=Accounts&action")) {
				driver.findElement(By.id("search_txt")).sendKeys(Org);
				driver.findElement(By.name("search")).click();
				driver.findElement(By.xpath("//a[text()='" + Org + "']")).click();
			}

		}

		driver.switchTo().window(pwid);

		// contact save button
		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();

		WebElement cnameval = driver.findElement(By.xpath("//span[contains(text(),'" + cname + "')]"));

		if (cnameval.getText().contains(Org)) {
			System.out.println("Create Contact with Org is pass-(cname)");
		} else {
			System.out.println("Create Org with Type and Industry is fail-(cname)");
		}
		WebElement orgval = driver.findElement(By.linkText("Astra"));
		if (orgval.getText().contains(Org)) {
			System.out.println("Create Contact with Org is pass-(Orgname)");
		} else {
			System.out.println("Create Contact with Org is fail-(cname)");
		}
		// Contacts link
		driver.findElement(By.linkText("Contacts")).click();

		// delete contact
		driver.findElement(
				By.xpath("//a[text()='" + cname + "']/ancestor::tr[@bgcolor='white']/descendant::a[text()='del']")).click();

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
