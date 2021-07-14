package keyword.engine.pages;

import keyword.base.BaseClass;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class KeyWordEngine{

    public WebDriver driver;
    public Properties properties;

    public static XSSFWorkbook workbook;
    public static XSSFSheet sheet;

    public BaseClass baseClass;
    public WebElement webElement;
    public Actions actions;

    public final String filePath = "F:\\ExcelData\\KeyWord.xlsx";

    public void startExecution (String sheetName) {

        String locatorValue = null;
        String locatorName = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(filePath);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            assert fileInputStream != null;
            workbook = (XSSFWorkbook) WorkbookFactory.create(fileInputStream);
        } catch (IOException | InvalidFormatException exception) {
            exception.printStackTrace();
        }
        sheet = workbook.getSheet(sheetName);
        System.out.println("Last row Number: " + sheet.getLastRowNum());
        int k = 0;
        for (int i = 0; i < sheet.getLastRowNum(); i++) {

            try {
                String locatorColValue = sheet.getRow(i + 1).getCell(k + 1).toString().trim();//xpath=username
                if (!locatorColValue.equalsIgnoreCase("NA")) {
                    locatorName = locatorColValue.split(",")[0].trim();//xpath
                    locatorValue = locatorColValue.split(",")[1].trim();//username
                }
                String action = sheet.getRow(i + 1).getCell(k + 2).toString().trim();
                String value = sheet.getRow(i + 1).getCell(k + 3).toString().trim();

                switch (action) {
                    case "open browser":
                        baseClass = new BaseClass();
                        properties = baseClass.setProperties();
                        if (value.isEmpty() || value.equals("NA")) {
                            driver = baseClass.setDriver(properties.getProperty("browser"));
                        } else {
                            driver = baseClass.setDriver(value);
                        }
                        break;
                    case "enter url":
                        if (value.isEmpty() || value.equals("NA")) {
                            driver.get(properties.getProperty("url"));
                            System.out.println("In url");
                        } else {
                            driver.get(value);
                        }
                        break;
                    case "quit" :
                        driver.quit ();
                    default:
                        break;
                }

                if (locatorName != null) {
                    switch (locatorName) {

                        case "xpath":
                            assert locatorValue != null;
                            actions = new Actions(driver);

                            if (action.equalsIgnoreCase("email sendKeys")) {
                                webElement = driver.findElement(By.xpath(properties.getProperty("emailId.xpath")));
                                driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
                                webElement.sendKeys(value);

                            } else if (action.equalsIgnoreCase("password sendKeys")) {
                                driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
                                webElement = driver.findElement(By.xpath(properties.getProperty("userPass.xpath")));
                                webElement.sendKeys(value);

                            } else if (action.equalsIgnoreCase("click")) {
                                webElement = driver.findElement(By.xpath(properties.getProperty("signInButton.xpath")));
                                webElement.click();
                                driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
                            }
                            locatorName = null;
                            break;
                        default:
                            break;
                    }
                }
            } catch (Exception ignored) {

            }
        }
    }
}