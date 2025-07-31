package PomUtility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Login_Pom_Page {

		// Declaration
	    @FindBy(linkText = "vtiger")
	    private WebElement header;

	    @FindBy(name = "user_name")
	    private WebElement usernameTF;

	    @FindBy(name = "user_password")
	    private WebElement passwordTF;

	    @FindBy(id = "submitButton")
	    private WebElement login_btn;

	    // Initialization
	    public Login_Pom_Page(WebDriver driver) {
	    	
	    	PageFactory.initElements(driver,this);
	    	
	    }
	    
	    //Utilization

		public WebElement getHeader() {
			return header;
		}

		public WebElement getUsernameTF() {
			return usernameTF;
		}

		public WebElement getPasswordTF() {
			return passwordTF;
		}

		public WebElement getLogin_btn() {
			return login_btn;
		}
		
		public void login(String un, String pwd) {
			usernameTF.sendKeys(un);
			passwordTF.sendKeys(pwd);
			login_btn.click();
			
		}
	    
	   

}
