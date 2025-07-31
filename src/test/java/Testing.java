import org.openqa.selenium.By;
import org.testng.annotations.Test;

import Base_Class.Base_Class;

public class Testing extends Base_Class {

	@Test
	public void oneTest() {
		
		//identify org link
				driver.findElement(By.linkText("Organizations")).click();
		
		
	}
}
