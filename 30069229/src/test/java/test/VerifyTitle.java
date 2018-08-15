package test;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class VerifyTitle
{
	ExtentReports report;
	ExtentTest test;
	WebDriver driver;
	
	@BeforeTest
	public void BeforeTest()
	{
		report = new ExtentReports(System.getProperty("user.dir") + "\\LearnAutomation.html");
		
	}
	
	
	//TC04_ConfigSteps_Versioning
	//TC05_4Eye_Principle
	
	@Test
	public void TC01_Availability_Admin() throws InterruptedException
	{
		test = report.startTest("VerifyBlogTitle");
		//driver = new FirefoxDriver();
		//driver.manage().window().maximize();
		test.log(LogStatus.PASS, "Browser started ");
		//driver.get("http://www.learn-automation.com");
		test.log(LogStatus.PASS, "Application is up and running");
		//String title = driver.getTitle();
		// Assert.assertTrue(title.contains("Google"));
		test.log(LogStatus.PASS, "Title verified");
		Thread.sleep(2000);
		//driver.close();
	}
	@Test
	public void TC02_Availability_NonAdmin() throws InterruptedException
	{
		
		test = report.startTest("verifyBlog");
		//driver = new FirefoxDriver();
		//driver.manage().window().maximize();
		test.log(LogStatus.INFO, "Browser started ");
		//driver.get("http://www.learn-automation.com");
		test.log(LogStatus.INFO, "Application is up and running");
		//String title = driver.getTitle();
		// Assert.assertTrue(title.contains("Google"));
		test.log(LogStatus.FAIL, "Title not verified");
		Thread.sleep(2000);
		//driver.close();
	}
	
	@Test
	public void TC03_Default_Setup(ITestResult result) throws InterruptedException
	{
		test = report.startTest("verify");
		result.setAttribute("TC03_Default_Setup", test);
		//driver = new FirefoxDriver();
		//driver.manage().window().maximize();
		test.log(LogStatus.INFO, "Browser started ");
		//driver.get("http://www.learn-automation.com");
		test.log(LogStatus.INFO, "Application is up and running");
		//String title = driver.getTitle();
		// Assert.assertTrue(title.contains("Google"));
		test.log(LogStatus.FAIL, "Title not verified");
		String imagePath = test.addScreenCapture("C:\\Users\\as762939\\Desktop\\topgear-electrolux-servicenow-win.png");
		test.log(LogStatus.FAIL, "Title verification", imagePath);
		Thread.sleep(2000);
		//driver.close();
	}
	
	@AfterMethod
	public void tearDown(ITestResult result, ITestContext context)
	{
		
		System.out.println(result.getAttribute("TC03_Default_Setup"));
		if (result.getStatus() == ITestResult.FAILURE)
		{
			/*String screenshot_path = Utility.captureScreenshot(driver, result.getName());
			String image= logger.addScreenCapture(screenshot_path);
			logger.log(LogStatus.FAIL, "Title verification", image);*/
			
		}
		
		/*report.endTest(logger);
		report.flush();*/
		//driver.close();
		// driver.get(System.getProperty("user.dir")+"\\Report\\LearnAutomation.html");
	}
	
	@BeforeMethod
	public void beforeMethod(ITestResult result, ITestContext context)
	{
		
		System.out.println(result.getAttribute("TC03_Default_Setup"));
		
	}
	
	
	@org.testng.annotations.AfterTest
	public void AfterTest(){
		/*report.endTest(logger);
		report.flush();*/
	}
	
	

}