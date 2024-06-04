package commonFunctions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class FunctionLibrary 
{
 public static Properties prop;
 public static WebDriver driver;
 public static WebDriver startBrowser()throws Throwable
 {
	 prop = new Properties();
	 prop.load(new FileInputStream("./PropertyFiles/Environment.Properities"));
	 if(prop.getProperty("Browser").equalsIgnoreCase("Chrome"))
	 {
		 driver = new ChromeDriver();
		 driver.manage().window().maximize();
	 }
	 else if(prop.getProperty("Browser").equalsIgnoreCase("Firefox"))
	 {
		 driver = new FirefoxDriver();
	 }
	 else
	 {
		 Reporter.log("Browser is not compatible",true);
	 }
	return driver;
 }
 public static void openUrl()
 {
	 driver.get(prop.getProperty("Url"));
 }
 public static void waitForElement(String LocatorType, String LocatorValue, String TestData)
 {
	 WebDriverWait myWait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(TestData)));
	 if(LocatorType.equalsIgnoreCase("xpath"))
	 {
		 myWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocatorValue)));
	 }
	 if(LocatorType.equalsIgnoreCase("id"))
	 {
		 myWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LocatorValue)));
	 }
	 if(LocatorType.equalsIgnoreCase("name"))
	 {
		 myWait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LocatorValue)));
	 }
 }
 public static void typeAction(String LocatorType, String LocatorValue, String TestData)
 {
	 if(LocatorType.equalsIgnoreCase("xpath"))
	 {
		 driver.findElement(By.xpath(LocatorValue)).clear();
		 driver.findElement(By.xpath(LocatorValue)).sendKeys(TestData);
	 }
	 if(LocatorType.equalsIgnoreCase("id"))
	 {
		 driver.findElement(By.id(LocatorValue)).clear();
		 driver.findElement(By.id(LocatorValue)).sendKeys(TestData);
	 }
	 if(LocatorType.equalsIgnoreCase("name"))
	 {
		 driver.findElement(By.name(LocatorValue)).clear();
		 driver.findElement(By.name(LocatorValue)).sendKeys(TestData);
	 }
 }
 public static void clickAction(String LocatorType, String LocatorValue)
 {
	 if(LocatorType.equalsIgnoreCase("xpath"))
	 {
		 driver.findElement(By.xpath(LocatorValue)).click();
	 }
	 if(LocatorType.equalsIgnoreCase("name"))
	 {
		 driver.findElement(By.name(LocatorValue)).click();
	 }
	 if(LocatorType.equalsIgnoreCase("id"))
	 {
		 driver.findElement(By.id(LocatorValue)).sendKeys(Keys.ENTER);
	 }
 }
 public static void validateTitle(String Expected_Title)
 {
	String Actual_Title = driver.getTitle();
	try
	{
	Assert.assertEquals(Actual_Title, Expected_Title,"Title is not matching");
	}catch(AssertionError a)
	{
		System.out.println(a.getMessage());
	}
 }
 public static void closeBrowser()
 {
	 driver.quit();
 }
 public static String generateDate()
 {
	 Date date = new Date();
	 DateFormat df = new SimpleDateFormat("YYYY_MM_DD");
	 return df.format(date);
 }
 public static void dropDownAction(String LocatorType, String LocatorValue, String TestData)
 {
	 if(LocatorType.equalsIgnoreCase("xpath"))
		{
			int value =Integer.parseInt(TestData);
			Select element =new Select(driver.findElement(By.xpath(LocatorValue)));
			element.selectByIndex(value);
			
		}
		if(LocatorType.equalsIgnoreCase("name"))
		{
			int value =Integer.parseInt(TestData);
			Select element =new Select(driver.findElement(By.name(LocatorValue)));
			element.selectByIndex(value);
		}
		if(LocatorType.equalsIgnoreCase("id"))
		{
			int value =Integer.parseInt(TestData);
			Select element =new Select(driver.findElement(By.id(LocatorValue)));
			element.selectByIndex(value);
		}

 }
 public static void captureStockNum(String LocatorType, String LocatorValue) throws Throwable
 {
	 String stockNum = "";
	 if(LocatorType.equalsIgnoreCase("xpath"))
	 {
		 stockNum  = driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
	 }
	 if(LocatorType.equalsIgnoreCase("name"))
	 {
		 stockNum  = driver.findElement(By.name(LocatorValue)).getAttribute("value");
	 }
	 if(LocatorType.equalsIgnoreCase("id"))
	 {
		 stockNum  = driver.findElement(By.id(LocatorValue)).getAttribute("value");
	 }
	 FileWriter fw = new FileWriter("./CaptureData/StockNumber.txt");
	 BufferedWriter bw = new BufferedWriter(fw);
	 bw.write(stockNum);
	 bw.flush();
	 bw.close();
	 
 }
 public static void stockTable()throws Throwable
 {
	 FileReader fr = new FileReader("./CaptureData/StockNumber.txt");
	 BufferedReader br = new BufferedReader(fr);
	 String Exp_Data = br.readLine();
	 if(!driver.findElement(By.xpath(prop.getProperty("search-textbox"))).isDisplayed())
		 driver.findElement(By.xpath(prop.getProperty("search-panel"))).click();
	 driver.findElement(By.xpath(prop.getProperty("search-textbox"))).clear();
	 driver.findElement(By.xpath(prop.getProperty("search-textbox"))).sendKeys(Exp_Data);
	 driver.findElement(By.xpath(prop.getProperty("search-button"))).click();
	 String Act_Data = driver.findElement(By.xpath("//table[@class = 'table ewTable']/tbody/tr[1]/td[8]/div/span/span")).getText();
	 Reporter.log(Exp_Data+"   "+Act_Data);
	 try {
	 Assert.assertEquals(Exp_Data, Act_Data,"Stock Num not matching");
	 }catch(AssertionError a)
	 {
		 System.out.println(a.getMessage());
	 }
 }
 public static void capturesup(String LocatorType, String LocatorValue) throws Throwable
 {
	 String supplierNum = "";
	 if(LocatorType.equalsIgnoreCase("xpath"))
	 {
		 supplierNum  = driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
	 }
	 if(LocatorType.equalsIgnoreCase("name"))
	 {
		 supplierNum  = driver.findElement(By.name(LocatorValue)).getAttribute("value");
	 }
	 if(LocatorType.equalsIgnoreCase("id"))
	 {
		 supplierNum  = driver.findElement(By.id(LocatorValue)).getAttribute("value");
	 }
	 FileWriter fw = new FileWriter("./CaptureData/SupplierNum.txt");
	 BufferedWriter bw = new BufferedWriter(fw);
	 bw.write(supplierNum);
	 bw.flush();
	 bw.close(); 
 }
 public static void supplierTable()throws Throwable
 {
	 FileReader fr = new FileReader("./CaptureData/SupplierNum.txt");
	 BufferedReader br = new BufferedReader(fr);
	 String Exp_Data = br.readLine();
	 if(!driver.findElement(By.xpath(prop.getProperty("search-textbox"))).isDisplayed())
		 driver.findElement(By.xpath(prop.getProperty("search-panel"))).click();
	 driver.findElement(By.xpath(prop.getProperty("search-textbox"))).clear();
	 driver.findElement(By.xpath(prop.getProperty("search-textbox"))).sendKeys(Exp_Data);
	 driver.findElement(By.xpath(prop.getProperty("search-button"))).click();
	 Thread.sleep(4000);
	 String Act_Data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[6]/div/span/span")).getText();
	 Reporter.log(Exp_Data+"   "+Act_Data);
	 try {
	 Assert.assertEquals(Exp_Data, Act_Data,"Supplier Num not matching");
	 }catch(AssertionError a)
	 {
		 System.out.println(a.getMessage());
	 }
 }
 public static void capturecus(String LocatorType, String LocatorValue) throws Throwable
 {
	 String CustomerNum = "";
	 if(LocatorType.equalsIgnoreCase("xpath"))
	 {
		 CustomerNum  = driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
	 }
	 if(LocatorType.equalsIgnoreCase("name"))
	 {
		 CustomerNum  = driver.findElement(By.name(LocatorValue)).getAttribute("value");
	 }
	 if(LocatorType.equalsIgnoreCase("id"))
	 {
		 CustomerNum  = driver.findElement(By.id(LocatorValue)).getAttribute("value");
	 }
	 FileWriter fw = new FileWriter("./CaptureData/CustomerNumber.txt");
	 BufferedWriter bw = new BufferedWriter(fw);
	 bw.write(CustomerNum);
	 bw.flush();
	 bw.close(); 
 }
 public static void customerTable()throws Throwable
 {
	 FileReader fr = new FileReader("./CaptureData/CustomerNumber.txt");
	 BufferedReader br = new BufferedReader(fr);
	 String Exp_Data = br.readLine();
	 if(!driver.findElement(By.xpath(prop.getProperty("search-textbox"))).isDisplayed())
		 driver.findElement(By.xpath(prop.getProperty("search-panel"))).click();
	 driver.findElement(By.xpath(prop.getProperty("search-textbox"))).clear();
	 driver.findElement(By.xpath(prop.getProperty("search-textbox"))).sendKeys(Exp_Data);
	 driver.findElement(By.xpath(prop.getProperty("search-button"))).click();
	 Thread.sleep(4000);
	 String Act_Data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[5]/div/span/span")).getText();
	 Reporter.log(Exp_Data+"   "+Act_Data);
	 try {
	 Assert.assertEquals(Exp_Data, Act_Data,"Customer Num not matching");
	 }catch(AssertionError a)
	 {
		 System.out.println(a.getMessage());
	 }
}
}