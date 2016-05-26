import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.html5.*;
import org.openqa.selenium.logging.*;
import org.openqa.selenium.remote.*;

import Utils.PerfectoLabUtils;
import Utils.WindTunnelUtils;

import org.openqa.selenium.Cookie.Builder;

import io.appium.java_client.*;
import io.appium.java_client.android.*;
import io.appium.java_client.ios.*;

public class AppiumTest {

    public static void main(String[] args) throws MalformedURLException, IOException {
        System.out.println("Run started");

        String browserName = "mobileOS";
        DesiredCapabilities capabilities = new DesiredCapabilities(browserName, "", Platform.ANY);
        String host = "myHost.perfectomobile.com";
        capabilities.setCapability("user", "myUser");
        capabilities.setCapability("password", "myPassword");

        //TODO: Change your device ID
        capabilities.setCapability("deviceName", "12345");

        // Use the automationName capability to define the required framework - Appium (this is the default) or PerfectoMobile.
        capabilities.setCapability("automationName", "Appium");

        // Call this method if you want the script to share the devices with the Perfecto Lab plugin.
        PerfectoLabUtils.setExecutionIdCapability(capabilities, host);

        // Application settings examples.
        // capabilities.setCapability("app", "PRIVATE:applications/Errands.ipa");
        // For Android:
        // capabilities.setCapability("appPackage", "com.google.android.keep");
        // capabilities.setCapability("appActivity", ".activities.BrowseActivity");
        // For iOS:
        // capabilities.setCapability("bundleId", "com.yoctoville.errands");

        // Add a persona to your script (see https://community.perfectomobile.com/posts/1048047-available-personas)
        //capabilities.setCapability(WindTunnelUtils.WIND_TUNNEL_PERSONA_CAPABILITY, WindTunnelUtils.GEORGIA);

        // Name your script
        // capabilities.setCapability("scriptName", "AppiumTest");

        AndroidDriver driver = new AndroidDriver(new URL("https://" + host + "/nexperience/perfectomobile/wd/hub"), capabilities);
        // IOSDriver driver = new IOSDriver(new URL("https://" + host + "/nexperience/perfectomobile/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

        try {
            // write your code here
        	// Start page
        	driver.context("NATIVE_APP");
        	driver.findElementByXPath("//*[@label=\"Get started\"]").click();
        	// Second page
        	driver.context("NATIVE_APP");
        	driver.findElementByXPath("//*[@label=\"Sign up with Email or Facebook\"]").click();
        	// Third page
        	driver.context("NATIVE_APP");
        	driver.findElementByXPath("//*[@name='kilograms']").click();
        	
        	driver.context("NATIVE_APP");
        	driver.findElementByXPath("//UIAStaticText[@name=\"10\"]").click();
        	driver.context("NATIVE_APP");
        	driver.findElementByXPath("//*[@name=\"weight_button\"]").click();
        	
        	// Fourth page
        	driver.context("NATIVE_APP");
        	driver.findElementByXPath("//*[@label=\"female\"]").click();
        	
        	driver.context("NATIVE_APP");
        	driver.findElementByXPath("//*[@name=\"profile_age\"]/UIAStaticText[2]").click();
         	
//*****************   Switchover to Android app  ************//
        	// Start page
        	driver.context("NATIVE_APP");
        	driver.findElementByXPath("//*[@text=\"Get started\"]").click();
        	//Second page
        	driver.context("NATIVE_APP");
        	driver.findElementByXPath("//*[starts-with(@text, \"Sign up with Email, Facebook\")]").click();
        	// Third page (fourth in Apple)
        	driver.context("NATIVE_APP");
        	driver.findElementByXPath("//*[@text='Male']").click();
        	driver.context("NATIVE_APP");
        	driver.findElementByXPath("//*[@resource-id=\"com.wsl.noom:id/profile_age\"]").sendKeys("55");
        	driver.context("NATIVE_APP");
        	driver.findElementByXPath("//*[@resource-id=\"com.wsl.noom:id/height_cm\"]").click();
        	
        	driver.context("NATIVE_APP");
        	driver.findElementByXPath("//*[@resource-id=\"com.wsl.noom:id/profile_height\"]").sendKeys("190");
        	
        	driver.context("NATIVE_APP");
        	driver.findElementByXPath("//*[@resource-id=\"com.wsl.noom:id/action_next\"]").click();
        	
        	// did not transfer to next page because of missing information!
        	
       	
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Retrieve the URL of the Single Test Report, can be saved to your execution summary and used to download the report at a later point
                String reportURL = (String)(driver.getCapabilities().getCapability(WindTunnelUtils.SINGLE_TEST_REPORT_URL_CAPABILITY));

                driver.close();

                // In case you want to download the report or the report attachments, do it here.
                // PerfectoLabUtils.downloadReport(driver, "pdf", "C:\\test\\report");
                // PerfectoLabUtils.downloadAttachment(driver, "video", "C:\\test\\report\\video", "flv");
                // PerfectoLabUtils.downloadAttachment(driver, "image", "C:\\test\\report\\images", "jpg");

            } catch (Exception e) {
                e.printStackTrace();
            }

            driver.quit();
        }

        System.out.println("Run ended");
    }
}
