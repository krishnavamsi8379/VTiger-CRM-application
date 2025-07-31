package Base_Class;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.mysql.jdbc.Driver;

import GenericUtilites.DataBaseUtility;
import GenericUtilites.PropertyFileUtility;
import GenericUtilites.WebDriverUtility;
import PomUtility.Home_Pom_Page;
import PomUtility.Login_Pom_Page;

public class Base_Class {
	DataBaseUtility dbutil = new DataBaseUtility();
	Connection con;
	public WebDriver driver;
	public static WebDriver sDriver = null;
	PropertyFileUtility p = new PropertyFileUtility();
	//public WebDriverUtility wbutil = null;
	public WebDriverUtility wbutil = new WebDriverUtility();
	
	
	
	@BeforeSuite
	public void connectToDatabase() throws SQLException {
		con=dbutil.getDataBaseConnection();
		
		
		
	}
	
	@BeforeTest
	public void conParallel() {
		System.out.println("Configure parllel");
		
		
	}
	@BeforeClass
	public void launchBrowser() throws IOException {
		
		PropertyFileUtility p = new PropertyFileUtility();
		String Browser = p.fetchDataFromPropFile("browser");
		if(Browser.equals("chrome")) {
			driver = new ChromeDriver();
			
		}
		else if(Browser.equals("edge")) {
			driver = new EdgeDriver();
		}
		else {
			driver = new ChromeDriver();
		}
		
		sDriver=driver;
		
		
	}
	 
	@BeforeMethod
	public void login() throws IOException {
		wbutil.maximizeTheWindow(driver);
		String time = p.fetchDataFromPropFile("timeouts");
		wbutil.implcitWaitStmt(driver, time);
		String url = p.fetchDataFromPropFile("url");
		wbutil.navigateToApplciation(driver, url);
		String username = p.fetchDataFromPropFile("username");
		String password=p.fetchDataFromPropFile("password");
		Login_Pom_Page login = new Login_Pom_Page(driver);
		login.login(username, password);
		
		
		
	}
	@AfterMethod
	public void logOut() {
	
		Home_Pom_Page home = new Home_Pom_Page(driver);
		home.logout(driver);

	}
	@AfterClass
	public void closeBrowser() {
		wbutil.closeBrowser(driver);
		
	}
	@AfterTest
	public void closeConParallel() {
		
		System.out.println("Close conf parellel connection");
		
	}
	@AfterSuite
	public void closeDatabaseConnection() throws SQLException {
		
		con.close();
		
		
		
	}
	
	

	
	
	
	

}
