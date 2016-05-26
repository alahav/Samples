import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeTest;

/** 
 * @author Daniel.
 * Selenium web driver test.
 * This test continues the mobile Test.
 */
public class desktopTest {

	private RemoteWebDriver driver;
	
	@BeforeTest
	public void beforeTest() throws MalformedURLException{

		driver = dataProvider.DesktopDriver();
		driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}
	
	//Starting the web session and login to Amazon.co.uk
	public void StartAndLogIn(){
		try{
			driver.get("https://www.amazon.co.uk");
			driver.findElement(By.id(TestObjects.desktop_login_id)).click();
			driver.findElement(By.id(TestObjects.desktop_username_id)).sendKeys(dataProvider.AppUser);
			driver.findElement(By.id(TestObjects.desktop_password_id)).sendKeys(dataProvider.AppPass);
			driver.findElement(By.id(TestObjects.desktop_loginBTN_id)).click();
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	//Clicking on cart and fill the checkout page.
	public void CartAndCheckout(){
		try{
			driver.findElement(By.id(TestObjects.desktop_cart_id)).click();
			driver.findElement(By.xpath(TestObjects.desktop_checkout_xpath)).click();
			//fill delivery address.
			driver.findElement(By.id(TestObjects.desktop_full_name_id)).sendKeys(dataProvider.FullName);
			driver.findElement(By.id(TestObjects.desktop_address_line1_id)).sendKeys(dataProvider.AddressLine1);
			driver.findElement(By.id(TestObjects.dekstop_city_id)).sendKeys(dataProvider.Town);
			driver.findElement(By.id(TestObjects.desktop_country1_id)).sendKeys(dataProvider.Country);
			driver.findElement(By.id(TestObjects.desktop_post_code_id)).sendKeys(dataProvider.PostCode);
			//Country select type.
			new Select(driver.findElement(By.id(TestObjects.desktop_CountrySelect_id))).selectByVisibleText("Israel");
			
			driver.findElement(By.id(TestObjects.desktop_phone_id)).sendKeys(dataProvider.Phone);
			new Select(driver.findElement(By.id(TestObjects.desktop_address_type_id))).selectByValue("COM");
			driver.findElement(By.xpath(TestObjects.desktop_Continue_xpath)).click();
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	//Delete items from the basket.
	//Use this method if you would like to delete items from the basket via the website.
	public void CleanBasket(){
		try{
			driver.get("https://www.amazon.co.uk");
			driver.findElement(By.id(TestObjects.desktop_cart_id)).click();
			driver.findElement(By.xpath(TestObjects.desktop_deleteItem_xpath)).click();
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	//Closing the driver.
	public void CloseDriver(){
		try {
			driver.close();
			driver.quit();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
}
