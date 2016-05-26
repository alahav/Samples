import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;


public class MobileTest {

    private AppiumDriver    driver;
    private desktopTest     desktop;
    private WebDriverWait 	wait;
    private String          os; 

    private static final String nativeAPP 	= "NATIVE_APP";

    @Parameters({"platformName" , "deviceName"})
    @BeforeMethod
    public void BeforeTest(String platformName ,String deviceName)throws MalformedURLException{
        try {
            if(platformName.equals("iOS")) {
                driver = dataProvider.iosDriver(platformName, deviceName);
            }
            else{
                driver = dataProvider.androidDriver(platformName , deviceName);
            }
            driver.context(nativeAPP);
            driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
            wait = new WebDriverWait(driver, 15);
            os = driver.getCapabilities().getCapability("platformName").toString();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void Test(){
        try {
            System.out.println("Starting mobile test.");
            openAPP();
            loginAPP();
            search();
            signOut();
            //Starts desktop session.
            StartDesktopTest();
            this.desktop.StartAndLogIn();
            this.desktop.CartAndCheckout();
            this.desktop.CloseDriver();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private void openAPP(){
        try {
            HashMap<String, Object> script = new HashMap<String, Object>();
            script.put("name", dataProvider.AppName);
            driver.executeScript("mobile:application:open", script);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void loginAPP(){
        try{
            if(os.equals("iOS")) {
                wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(TestObjects.Sign_in)))).click();
            }
            else {
                try {
                    HashMap<String, Object> Script = new HashMap<>();
                    Script.put("label", "Sign in");
                    driver.executeScript("mobile:button-text:click", Script);
                    Thread.sleep(1500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            WebElement elem = driver.findElement(By.xpath(TestObjects.userName));
            elem.sendKeys(dataProvider.AppUser);
            elem = driver.findElement(By.xpath(TestObjects.password));
            elem.sendKeys(dataProvider.AppPass);
            driver.findElement(By.xpath(TestObjects.login_button)).click();

        }catch(Exception e){
            System.out.println(e);
            System.out.println("Already logged in or problem while trying login into the application.");
        }
    }


    private void search(){
        boolean menu_loaded = false;
        if(os.equals("iOS")){
            try{//Finding search area could be problematic therefore trying twice.
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(TestObjects.Search))).sendKeys(dataProvider.SearchValue);
                menu_loaded = true;
                if(!menu_loaded){
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(TestObjects.Search))).sendKeys(dataProvider.SearchValue);
                }
            }catch(Exception e){}
        }
        else{
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(TestObjects.Search))).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(TestObjects.AndroidSearch2))).sendKeys(dataProvider.SearchValue);
        }

        driver.findElement(By.xpath(TestObjects.CDsAndVinyl)).click();
        driver.findElement(By.xpath(TestObjects.DarkSideCD)).click();
        //This part of code separate two cases of ScrollTO method.
        //In order to find Basket button scrolling down and search for the text "Add to Basket" .
        try{
            if(os.equals("iOS")){
                driver.findElementByClassName("UIAButton");
                //Scrolling down and find element by it's tag name.
                IOSElement tbl = (IOSElement) driver.findElementByClassName("UIAWebView");
                tbl.scrollTo("Add to Basket").click();
            }
            else{
                driver.scrollTo("Add to Basket");
                driver.findElement(By.xpath(TestObjects.Android_AddtoBasket)).click();
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }

    //signOut method.
    private void signOut(){
        try{
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(TestObjects.AppMenu))).click();
            if(os.equals("iOS")){
                IOSElement tbl = (IOSElement) driver.findElementByClassName("UIATableView");
                tbl.scrollTo("Sign out").click();
                driver.findElement(By.xpath(TestObjects.PopUpSignOut)).click();
            }
            else{
                driver.scrollTo("Not "+dataProvider.nameonly+"? Sign out");
                driver.findElement(By.xpath("//*[@text='Not "+dataProvider.nameonly+"? Sign out']")).click();
                driver.findElement(By.xpath(TestObjects.Android_PopUpSignOut)).click();
            }

        }catch(Exception e){
            System.out.println("Sign out unsuccessfully.");
        }
    }

    private void StartDesktopTest(){
        try{
            this.desktop = new desktopTest();
            this.desktop.beforeTest();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void closeAPP(){
        try{
            HashMap<String, Object> script = new HashMap<String, Object>();
            script.put("name", dataProvider.AppName);
            driver.executeScript("mobile:application:close", script);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @AfterMethod
    public void AfterTest(){
        try{
            closeAPP(); // Closing the application first.
            driver.close();
            driver.quit();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
