package Organization_Module;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class CreateOrgTest {
	
	@Test
	public void createNewOrgTest() throws InterruptedException {
		
		WebDriver driver = new ChromeDriver();
		
		driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		
		driver.get("http://localhost:8888/index.php");
		
		driver.findElement(By.name("user_name")).sendKeys("admin");
		
		driver.findElement(By.name("user_password")).sendKeys("admin");
		
		driver.findElement(By.id("submitButton")).click();
		
		//identify org link
		driver.findElement(By.linkText("Organizations")).click();
		
		//add org icon
		driver.findElement(By.xpath("//img[@title='Create Organization...']")).click();
		
		//enter the org name
		driver.findElement(By.name("accountname")).sendKeys("Wipro");
		
		//click in save
		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
		
		//Org value
		WebElement org = driver.findElement(By.xpath("//span[contains(text(),'Wipro')]"));
		
		if(org.getText().contains("Wipro")) {
			
			System.out.println("Create Org test pass");
			
		}
		else {
			System.out.println("Create org test faill ");
		}
		//identify org link
		driver.findElement(By.linkText("Organizations")).click();
		
		//delete the org
		driver.findElement(By.xpath("//a[text()='Wipro']/ancestor::tr[@bgcolor=\"white\"]/descendant::a[text()='del']")).click();
		
		Alert al = driver.switchTo().alert();
		
		al.accept();
		
		Thread.sleep(3000);
		
		driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']")).click();
		
		driver.findElement(By.xpath("//a[text()='Sign Out']")).click();
		
		Thread.sleep(2000);
		
		
		
	}
	
	

}
