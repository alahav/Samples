package com.perfecto.reporting.sample.geico;

import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.connection.Connection;
import com.perfecto.reportium.exception.ReportiumException;
import com.perfecto.reportium.model.*;
import com.perfecto.reportium.test.TestContext;
import com.perfecto.reportium.test.result.TestResultFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.net.MalformedURLException;

public class WebScenarioParallel {
    RemoteWebDriver driver;

    // Create Remote WebDriver based on testng.xml configuration
    protected ReportiumClient reportiumClient;

    protected Connection getConnection() {
        String tenant = System.getProperty("reportium-tenant", "10000001");

        return new Connection(
                "eyJhbGciOiJSUzI1NiJ9.eyJqdGkiOiIwMjI0MjA1NS1lNDUwLTQ5MjktYjRmOS0zYjEwYjQ1NzdmOTAiLCJleHAiOjAsIm5iZiI6MCwiaWF0IjoxNDYzOTUyMjk0LCJpc3MiOiJodHRwOi8vMTAwLjEyNy41LjM5L2F1dGgvcmVhbG1zL29wZXJhdG9yIiwiYXVkIjoicmVwb3J0aXVtIiwic3ViIjoiNDA1YzhmZDctM2RlOS00MGZiLTg3N2YtNmYzZmE3YTQzZjEwIiwidHlwIjoiT2ZmbGluZSIsImF6cCI6InJlcG9ydGl1bSIsInNlc3Npb25fc3RhdGUiOiJiM2RiNjFmNC01NjVjLTQyODAtYjEwMi05NDkzMTY0ODhkZTAiLCJjbGllbnRfc2Vzc2lvbiI6Ijg0ZDllMDhlLTNlNTctNGUyZi04YWUwLTdmZDQzOWQ1NGMwZiIsInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJvZmZsaW5lX2FjY2VzcyJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJ2aWV3LXByb2ZpbGUiXX19fQ.A2qa290-cvq4ZOjjevfzwmxa1gqXkb8ygceALdSsGwyHfjOODSZriJsqPhu2c5LEHjOw3VXD4ZprWSwOT54zE_8LdrT55k1BVkIGlFj56SN1q2A3HRRN0x0H5AqzQpn78dNqw2jlAYLZyl6aIUMmSBKBt9VMw-J2Q-TeYQ29NwOyvMLquzVDMPPZ8WrrXifE0EGTt8--b1gtR7sSa25U0S-CmLBWNrjzEeMbgjH1MRVYx3BVbo6m0GaSQ6attIrKXq-B1ehzbJYPkAuz74w1zC-nn82Wk7GmDGYw9bOWx63uusy0FIWekIiqQ9y_Ado3h_PYxxxypZWuiswzrnTK0g",
                tenant
        );
    }

    private String platformName;
    private String platformVersion;
    private String browserName;
    private String browserVersion;
    private String screenResolution;
    private String location;
    private String deviceType;

    // Create Remote WebDriver based on testng.xml configuration
    @Parameters({"platformName", "platformVersion", "browserName", "browserVersion", "screenResolution", "location", "deviceType"})
    @BeforeClass
    public void baseBeforeClass(String platformName, String platformVersion, String browserName, String browserVersion, String screenResolution, String location, String deviceType) throws MalformedURLException {
        driver = Utils.getRemoteWebDriver(platformName, platformVersion, browserName, browserVersion, screenResolution);
        this.platformName = platformName;
        this.platformVersion = platformVersion;
        this.browserName = browserName;
        this.browserVersion = browserVersion;
        this.screenResolution = screenResolution;
        this.location = location;
        this.deviceType = deviceType;
    }

    @AfterClass
    public void baseAfterClass() {
        if (driver != null) {
            driver.quit();
        }
    }

    @BeforeMethod
    public void beforeTest(Method method) {
        String testName = method.getDeclaringClass().getName() + "::" + method.getName();
        reportiumClient = createReportium();
        reportiumClient.testStart(testName, new TestContext());
    }

    @SuppressWarnings("Duplicates")
    @AfterMethod
    public void afterTest(ITestResult testResult) {
        int status = testResult.getStatus();
        switch (status) {
            case ITestResult.FAILURE:
                reportiumClient.testStop(TestResultFactory.createFailure("An error occurred", testResult.getThrowable()));
                break;
            case ITestResult.SUCCESS_PERCENTAGE_FAILURE:
            case ITestResult.SUCCESS:
                reportiumClient.testStop(TestResultFactory.createSuccess());
                break;
            case ITestResult.SKIP:
                // Ignore
                break;
            default:
                throw new ReportiumException("Unexpected status " + status);
        }
    }

    private ReportiumClient createReportium() {
        BrowserInfo browserInfo = new BrowserInfo(browserName, browserVersion);
        Platform platform = new Platform.PlatformBuilder()
                .withOs(OperatingSystem.valueOf(platformName))
                .withOsVersion(platformVersion)
                .withBrowserInfo(browserInfo)
                .withScreenResolution(screenResolution)
                .withLocation(location)
                .withDeviceId("MyFakeDeviceId")
                .withDeviceType(DeviceType.valueOf(deviceType))
                .build();
        ExecutionContext executionContext = new ExecutionContext.ExecutionContextBuilder()
                .withContextTags("Reportium_sample_project")
                .withPlatforms(platform)
                .build();
        reportiumClient = new ReportiumClientFactory().createReportiumClient(getConnection(), executionContext);

        return reportiumClient;
    }

    @Test
    public void searchGoogle() throws MalformedURLException {

        reportiumClient.testStart("WevbScanrioParallel", new TestContext());

        reportiumClient.testStep("Navigate to Google webpage");
        driver.get("http://www.google.com");

        reportiumClient.testStep("Searching for Perfecto Mobile");
        final String searchKey = "Perfecto Mobile";
        WebElement element = driver.findElement(By.name("q"));
        element.sendKeys(searchKey);
        element.submit();
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getTitle().toLowerCase().startsWith(searchKey.toLowerCase());
            }
        });

        System.out.println("Done: searchGoogle");

    }

    // Test Method, navigate to Geico and get insurance quote
    @Test
    public void geicoInsurance() throws MalformedURLException {

        reportiumClient.testStart("navigate to Geico and get insurance quote", new TestContext());

        reportiumClient.testStep("Navigate to Geico webpage");
        driver.get("http://www.geico.com");
        reportiumClient.testStep("Select Insurance type");
        Select type = new Select(driver.findElement(By.id("insurancetype")));

        reportiumClient.testStep("Select Motorcycle");
        type.selectByVisibleText("Motorcycle");
        driver.findElement(By.id("zip")).sendKeys("01434");
        driver.findElement(By.id("submitButton")).click();

        reportiumClient.testStep("Select Type Name & Adress");
        driver.findElement(By.id("firstName")).sendKeys("MyFirstName");
        driver.findElement(By.id("lastName")).sendKeys("MyFamilyName");
        driver.findElement(By.id("street")).sendKeys("My Address");

        driver.findElement(By.id("date-monthdob")).sendKeys("8");
        driver.findElement(By.id("date-daydob")).sendKeys("3");
        driver.findElement(By.id("date-yeardob")).sendKeys("1981");

        driver.findElement(By.xpath("//*[@class='radio'][2]")).click();
        driver.findElement(By.id("btnSubmit")).click();

        driver.findElement(By.id("hasCycle-error")).isDisplayed();

        Select hasCycle = new Select(driver.findElement(By.id("hasCycle")));
        hasCycle.selectByIndex(1);

        reportiumClient.testStep("Select Current Insurance");
        Select current = new Select(driver.findElement(By.id("currentInsurance")));
        current.selectByVisibleText("Other");
        driver.findElement(By.id("btnSubmit")).click();

        reportiumClient.testStop(TestResultFactory.createSuccess());

        System.out.println("Done: geicoInsurance");

    }


}
