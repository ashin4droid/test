package test;

import java.io.File;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.wipro.automation.snow.lib.MyLibraryTest;
import com.wipro.automation.snow.lib.MyLibraryTest.BrowserType;
import com.wipro.automation.snow.pages.AdminPage;
import com.wipro.automation.snow.pages.GoogleHomePage;
import com.wipro.automation.snow.pages.NonAdminPage;


public class WorkflowTests
{
	private ExtentReports report;
	private ExtentTest test;
	private WebDriver driver;
	private String currentMethod;
	
	private MyLibraryTest lib = new MyLibraryTest();
	private Logger log = Logger.getLogger(this.getClass());
	
	
	@BeforeClass
	public void BeforeTest()
	{
		report = InitializeConfig.report;
	}
	
	
	//TC04_ConfigSteps_Versioning
	//TC05_4Eye_Principle
	
	@Test
	public void WorkflowTests_TC01_Availability_Admin() throws InterruptedException
	{
		test.log(LogStatus.INFO, "Browser started");
		driver.get(AdminPage.ADMIN_URL);
		
		boolean adminLoginPageDisplayed = lib.waitForPresenceOfElement(AdminPage.Locators.USERNAME, 10);
		
		String adminLoginPageDisplayedPath = test.addScreenCapture(lib.getScreenShot(currentMethod, "IsAdminLoginPageDisplayed.jpg"));
		test.log((adminLoginPageDisplayed ? LogStatus.PASS : LogStatus.FAIL), "Login Page Display", adminLoginPageDisplayedPath);
		
		test.log(LogStatus.INFO, "Navigsted to Login Page");
		lib.SendTextToWebElement(AdminPage.Locators.USERNAME, AdminPage.USERNAME);
		lib.SendTextToWebElement(AdminPage.Locators.PASSWORD, AdminPage.PASSWORD);
		lib.ClickOnWebElement(AdminPage.Locators.LOGIN_BTN);
		lib.waitForPageToLoad(30);
		
		boolean adminLoggedIn = lib.waitForPresenceOfElement(AdminPage.Locators.ADMIN_ELEMENT, 20);
		String adminLoggedInPath = test.addScreenCapture(lib.getScreenShot(currentMethod, "AdminLoginResult.jpg"));
		
		test.log((adminLoggedIn ? LogStatus.PASS : LogStatus.FAIL), "Login Verification", adminLoggedInPath);
		
		
	}
	
	@Test
	public void WorkflowTests_TC02_Availability_NonAdmin()
	{
		test.log(LogStatus.INFO, "Browser started");
		driver.get(NonAdminPage.NON_ADMIN_URL);
		
		boolean loginPageDisplayed = lib.waitForPresenceOfElement(NonAdminPage.Locators.USERNAME, 10);
		
		String loginPageDisplayedPath = test.addScreenCapture(lib.getScreenShot(currentMethod, "IsNonAdminLoginPageDisplayed.jpg"));
		test.log((loginPageDisplayed ? LogStatus.PASS : LogStatus.FAIL), "Login Page Display", loginPageDisplayedPath);
		
		test.log(LogStatus.INFO, "Navigsted to Login Page");
		lib.SendTextToWebElement(NonAdminPage.Locators.USERNAME, NonAdminPage.USERNAME);
		lib.SendTextToWebElement(NonAdminPage.Locators.PASSWORD, NonAdminPage.PASSWORD);
		lib.ClickOnWebElement(NonAdminPage.Locators.LOGIN_BTN);
		lib.waitForPageToLoad(30);
		
		boolean loggedIn = lib.waitForPresenceOfElement(NonAdminPage.Locators.USER_HEADER, 20);
		String loggedInPath = test.addScreenCapture(lib.getScreenShot(currentMethod, "NonAdminLoginResult.jpg"));
		
		test.log((loggedIn ? LogStatus.PASS : LogStatus.FAIL), "Login Verification", loggedInPath);
		
		
	}
	
	@Test
	public void WorkflowTests_TC03_Default_Setup() throws InterruptedException
	{
		System.out.println("Method Name ****************** " + currentMethod);
		test.log(LogStatus.INFO, "Browser started ");
		test.log(LogStatus.INFO, "Application is up and running");
		test.log(LogStatus.PASS, "Title not verified");
		
		String imagePath = test.addScreenCapture(InitializeConfig.reportFolder + "\\topgear-electrolux-servicenow-win.png");
		test.log(LogStatus.PASS, "Title verification", imagePath);
	}
	
	
	@Test
	public void WorkflowTests_TC04_GooglePageTest() throws InterruptedException
	{
		System.out.println("Method Name ****************** " + currentMethod);
		WebDriver driver = lib.openBrowser(BrowserType.CHROME);
		driver.get("https://www.google.co.in/");
		test.log(LogStatus.INFO, "Browser started");
		
		boolean googlePageIsDisplayed = lib.waitForPresenceOfElement(By.name("q"), 10);
		
		if (googlePageIsDisplayed) {
			log.info("Google page is displayed");
		}
		else {
			log.info("Google page is not displayed");
		}
		
		lib.SendTextToWebElement(GoogleHomePage.searchBox, "Selenium Test"+Keys.ENTER);
		lib.waitForPageToLoad(20);
		String imagePath = test.addScreenCapture(lib.getScreenShot(currentMethod, "SeleniumSearchResult.jpg"));
		//String imagePath = lib.getScreenShot(InitializeConfig.reportFolder, "SeleniumSearchResult");
		test.log(LogStatus.PASS, "Title verification", imagePath);
		System.out.println("Number of links in the page is : " + driver.findElements(By.tagName("a")).size());
		
		driver.quit();
		log.info("Done");
	}
	
	
	
	
	@AfterMethod
	public void tearDown(ITestResult result)
	{
		if (result.getStatus() == ITestResult.FAILURE)
		{
			test.log(LogStatus.FAIL, result.getThrowable().toString());
		}
		
		driver.quit();
		test.log(LogStatus.INFO, "Browser closed");
		report.endTest(test);
		currentMethod = null;
	}
	
	@BeforeMethod
	public void beforeMethod(Method method)
	{
		driver = lib.openBrowser(BrowserType.CHROME);
		currentMethod = method.getName();
		File f = new File(InitializeConfig.reportFolder + "/" +currentMethod);
		if (!f.exists()) {
			f.mkdir();
		}
		
		test = report.startTest(method.getName());
	}
	
	
	
	
	
	
}