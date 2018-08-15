package test;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class TriggerTests
{
	ExtentReports report;
	public ExtentTest test;
	WebDriver driver;
	
	@BeforeClass
	public void BeforeTest()
	{
		report = InitializeConfig.report;
	}
	
	
	@Test
	public void TriggerTests_TC01_Availability_Admin() throws InterruptedException
	{
		test = report.startTest("TriggerTests_TC01_Availability_Admin");
		test.log(LogStatus.INFO, "Browser started ");
		//Assert.assertEquals(false, true);
		int i = 5/0;
		test.log(LogStatus.INFO, "Application is up and running");
		test.log(LogStatus.PASS, "Title not verified");
	}
	
	
	@AfterMethod
	public void tearDown(ITestResult result)
	{
		if (result.getStatus() == ITestResult.FAILURE)
		{
			test.log(LogStatus.FAIL, result.getThrowable().toString());
		}
		
		report.endTest(test);
	}
	
	@BeforeMethod
	public void beforeMethod(ITestResult result)
	{
		//System.out.println("Before Method -> " + result.getName());
		//System.out.println(result.getAttribute("TC03_Default_Setup"));
		
	}
	
	
	
	
	
	
}