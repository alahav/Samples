
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;


public class dataProvider {

	//TODO: Change to your personal information.
	
    //Perfecto cloud and login.
    final static String host = "MyCloud.perfectomobile.com";
    final static String user = "MyUser";
    final static String pass = "MyPassword";
    //Application properties.
    final static String AppName         = "Amazon";
    final static String AppUser         = "MyApplicationUserName";
    final static String AppPass         = "MyApplicationPassword";
    final static String SearchValue     = "Pink Floyd";
    final static String nameonly        = "Name";
    final static String FullName			= "FULLNAME";
    final static String AddressLine1 	= "Address1";
    final static String Town				= "Town";
    final static String Country			= "Country";
    final static String PostCode			= "123456";
    final static String Phone 			= "972123456789";
	
    //dataProvider methods
	
    public static IOSDriver iosDriver(String platformName , String deviceName) throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", platformName);
        capabilities.setCapability("deviceName", deviceName);
        capabilities.setCapability("user" , user);
        capabilities.setCapability("password" , pass);
        return new IOSDriver(getCloud() , capabilities);
    }

    public static AndroidDriver androidDriver(String platformName , String deviceName) throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", platformName);
        capabilities.setCapability("deviceName", deviceName);
        capabilities.setCapability("user" , user);
        capabilities.setCapability("password" , pass);
        return new AndroidDriver(getCloud() , capabilities);
    }

    public static RemoteWebDriver DesktopDriver() throws MalformedURLException{
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Windows");
        capabilities.setCapability("platformVersion", "8.1");
        capabilities.setCapability("browserName", "Chrome");
        capabilities.setCapability("browserVersion", "49");
        capabilities.setCapability("user" , user);
        capabilities.setCapability("password" , pass);
        return new RemoteWebDriver(getCloud() , capabilities);
    }

    public static URL getCloud() throws MalformedURLException {
        return new URL("https://" + host + "/nexperience/perfectomobile/wd/hub");
    }


}
