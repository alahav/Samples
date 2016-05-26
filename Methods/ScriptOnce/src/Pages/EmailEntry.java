package Pages;

import org.openqa.selenium.By;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class EmailEntry extends noomPage {
	private By header = By.xpath("//*[@resource-id='com.wsl.noom:id/email_password_headline']");
	private By emailAdr = By.xpath("//*[@resource-id='com.wsl.noom:id/email_sign_in_email']");
	private By pwdEntry = By.xpath("//*[@resource-id='com.wsl.noom:id/email_sign_in_password']");

	public EmailEntry(AppiumDriver<MobileElement> d, String os) {
		super(d, os);
		
		try {
			d.findElement(header);
		} catch (Exception e) {
			throw e;
		}
	}
}
