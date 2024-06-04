package driverFactory;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunctions.FunctionLibrary;
import utiles.ExcelFileUtil;

public class DriverScript 
{
   public static WebDriver driver;
   String inputpath = "./FileInput/DataEngine.xlsx";
   String outputpath = "./FileOutput/HybridResults.xlsx";
   ExtentReports report;
   ExtentTest logger;
   public void startTest() throws Throwable
   {
	   String ModuleStatus = "";
	   ExcelFileUtil xl = new ExcelFileUtil(inputpath);
	   String TestCases = "MasterTestCases";
	   for(int i =1; i<=xl.rowcount(TestCases); i++)
	   {
		   if(xl.getCellData(TestCases, i, 2).equalsIgnoreCase("Y"))
		   {
			   String TCModule = xl.getCellData(TestCases, i, 1);
			   report = new ExtentReports("./target/Reports/"+TCModule+FunctionLibrary.generateDate()+".html");
			   logger = report.startTest(TCModule);
			   for(int j=1; j<=xl.rowcount(TCModule); j++)
			   {
				   String Description = xl.getCellData(TCModule, j, 0);
				   String ObjectType = xl.getCellData(TCModule, j, 1);
				   String LocatorType = xl.getCellData(TCModule, j, 2);
				   String LocatorValue = xl.getCellData(TCModule, j, 3);
				   String TestData = xl.getCellData(TCModule, j, 4);
				   try 
				   {
					 if(ObjectType.equalsIgnoreCase("startBrowser"))  
					 {
						driver = FunctionLibrary.startBrowser();
						logger.log(LogStatus.INFO, Description);
					 }
					 if(ObjectType.equalsIgnoreCase("openUrl"))
					 {
						 FunctionLibrary.openUrl();
						 logger.log(LogStatus.INFO, Description);
					 }
					 if(ObjectType.equalsIgnoreCase("waitForElement"))
					 {
						 FunctionLibrary.waitForElement(LocatorType, LocatorValue, TestData);
						 logger.log(LogStatus.INFO, Description);
					 }
					 if(ObjectType.equalsIgnoreCase("typeAction"))
					 {
						 FunctionLibrary.typeAction(LocatorType, LocatorValue, TestData);
						 logger.log(LogStatus.INFO, Description);
					 }
					 if(ObjectType.equalsIgnoreCase("clickAction"))
					 {
						 FunctionLibrary.clickAction(LocatorType, LocatorValue);
						 logger.log(LogStatus.INFO, Description);
					 }
					 if(ObjectType.equalsIgnoreCase("validateTitle"))
					 {
						 FunctionLibrary.validateTitle(TestData); 
						 logger.log(LogStatus.INFO, Description);
					 }
					 if(ObjectType.equalsIgnoreCase("closeBrowser"))
					 {
						 FunctionLibrary.closeBrowser();
						 logger.log(LogStatus.INFO, Description);
					 }
					 if(ObjectType.equalsIgnoreCase("dropDownAction"))
						{
							FunctionLibrary.dropDownAction(LocatorType, LocatorValue, TestData);
							logger.log(LogStatus.INFO, Description);
						}
					if(ObjectType.equalsIgnoreCase("captureStockNum"))
						{
							FunctionLibrary.captureStockNum(LocatorType, LocatorValue);
							logger.log(LogStatus.INFO, Description);
						}
					if(ObjectType.equalsIgnoreCase("stockTable"))
						{
							FunctionLibrary.stockTable();
							logger.log(LogStatus.INFO, Description);
						}
                    if(ObjectType.equalsIgnoreCase("capturesup"))
                       {
                    	    FunctionLibrary.capturesup(LocatorType, LocatorValue);
                    	    logger.log(LogStatus.INFO, Description);
                       }
                    if(ObjectType.equalsIgnoreCase("supplierTable"))
                       {
                    	    FunctionLibrary.supplierTable();
                    	    logger.log(LogStatus.INFO, Description);
                       }
                    if(ObjectType.equalsIgnoreCase("capturecus"))
                    {
                    	    FunctionLibrary.capturecus(LocatorType, LocatorValue);
                    	    logger.log(LogStatus.INFO, Description);
                    }
                    if(ObjectType.equalsIgnoreCase("customerTable"))
                    {
                    	    FunctionLibrary.customerTable();
                    	    logger.log(LogStatus.INFO, Description);
                    }
					 xl.setCellData(TCModule, j, 5, "Pass", outputpath);
					 logger.log(LogStatus.PASS, Description);
					 ModuleStatus = "True";
				   }
				   catch(Exception e)
				   {
					   System.out.println(e.getMessage());
					   xl.setCellData(TCModule, j, 5, "Fail", outputpath);
					   logger.log(LogStatus.FAIL, Description);
					   ModuleStatus = "False";
				   }
			   }   
			   if(ModuleStatus.equalsIgnoreCase("True"))
			   {
				   xl.setCellData(TestCases, i, 3, "Pass", outputpath);
			   }
			   else
			   
				   xl.setCellData(TestCases, i, 3, "Fail", outputpath);
			   
			   report.endTest(logger);
			   report.flush();
		   }
		   else 
		   {
			   xl.setCellData(TestCases, i, 3, "Blocked", outputpath);
		   }
	   }
}		  
}
