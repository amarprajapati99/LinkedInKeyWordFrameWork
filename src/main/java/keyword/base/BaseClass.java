package keyword.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class BaseClass{

    public WebDriver driver;
    public Properties properties;

    @BeforeTest
    public WebDriver setDriver(String browserName) throws InterruptedException {

        if (browserName.equalsIgnoreCase("chrome")) {

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-notifications");
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            Thread.sleep(4000);
        }
        return driver;
    }


    public Properties setProperties(){

        properties = new Properties ();
        try {
            FileInputStream fis = new FileInputStream ("F:\\Selenium\\KeyWordFrameWorkInSelenium\\src\\test\\resources\\config.properties" +
                    "");
            properties.load (fis);
        } catch (IOException e) {
            e.printStackTrace ();
        }
        return properties;
    }
}
