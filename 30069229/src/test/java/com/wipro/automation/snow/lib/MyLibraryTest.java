package com.wipro.automation.snow.lib;


import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
//import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import test.InitializeConfig;



public class MyLibraryTest
{
	//public static WebDriver e_driver = null, temp = null;
	public static WebDriver driver = null;
	public WebDriverWait wait;
	private static final int GLOBAL_TIMEOUT = 10;
	
	public static WebDriver getDriver()
	{
		return driver;
	}
	
	public class BrowserType
	{
		public static final String CHROME = "chrome";
		public static final String INTERNET_EXPLORER = "ie";
		public static final String FIREFOX = "firefox";
	}
	
	public WebDriver openBrowser(String browser)
	{
		String browserName = browser.toLowerCase();
		switch (browserName)
		{
			case BrowserType.FIREFOX :
				
				System.setProperty("webdriver.gecko.driver", "resources/geckodriver64.exe");
				driver = new FirefoxDriver(getCapabilities(BrowserType.FIREFOX));
				driver.manage().window().maximize();
				
				break;
				
			case BrowserType.CHROME :
				
				System.setProperty("webdriver.chrome.driver", "resources/chromedriver.exe");
				//Runtime.getRuntime().exec("resources/chromeextention.exe");
				driver = new ChromeDriver(getCapabilities(BrowserType.CHROME));
				
				break;
				
			case BrowserType.INTERNET_EXPLORER :
				
				System.setProperty("webdriver.ie.driver", "resources/IEDriver-x64.exe");
				//System.setProperty("webdriver.ie.driver", "resources/IEDriverServer.exe");
				driver = new InternetExplorerDriver(getCapabilities(BrowserType.INTERNET_EXPLORER));
				//driver.findElement(By.tagName("html")).sendKeys(Keys.chord(Keys.CONTROL, "0"));
				driver.manage().window().maximize();
				
				break;
				
			case "Gokuh" :
				System.out.println("Never punctual.");
				break;
				
			default : System.out.println("Select the appropriate browser driver");
		}
		
		//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, GLOBAL_TIMEOUT);
		return driver;
	}
	
	
	
	
	private DesiredCapabilities getCapabilities(String browserName)
	{
		DesiredCapabilities caps = null;
		
		switch (browserName)
		{
			case BrowserType.CHROME :
				/*ChromeOptions chromeOptions = new ChromeOptions();
				chromeOptions.addArguments("--start-maximized");
				
				caps = DesiredCapabilities.chrome();
				caps.setCapability(ChromeOptions.CAPABILITY, chromeOptions);*/
				
				//String downloadFilepath = props.getProperty("exceldownloadFolder");
				
				HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
				chromePrefs.put("profile.default_content_settings.popups", 0);
				chromePrefs.put("download.prompt_for_download", true);
				//chromePrefs.put("download.extensions_to_open", "");
				chromePrefs.put("download.directory_upgrade", true);
				//chromePrefs.put("download.default_directory", "downloads");
				
				HashMap<String, Object> options = new HashMap<String, Object>();
				options.put("prefs", chromePrefs);
				
				ChromeOptions chromeOptions = new ChromeOptions();
				chromeOptions.addArguments("--start-maximized");
				chromeOptions.addArguments("--disable-infobars");
				chromeOptions.setExperimentalOption("prefs", chromePrefs);
				chromeOptions.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
				
				caps = DesiredCapabilities.chrome();
				caps.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
				caps.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
				System.out.println(caps.asMap().toString());
				
				break;
				
			case BrowserType.INTERNET_EXPLORER :
				caps = DesiredCapabilities.internetExplorer();
				caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
				caps.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION,true);
				
				break;
				
			case BrowserType.FIREFOX :
				caps = DesiredCapabilities.firefox();
				//caps.setCapability(FirefoxDriver.PROFILE, profile);
				//caps.setCapability(CapabilityType.PROXY, getProxy());
				
				break;
				
			default:
				break;
		}
		
		return caps;
	}
	
	
	public static String getFullFilePath(String path)
	{
		try
		{
			File f = new File(path);
			if(!f.exists())
			{
				f.mkdirs();
			}
			return f.getAbsolutePath() + "\\";
		}
		catch(Exception e) { e.printStackTrace(); return null; }
	}
	
	
	public String getScreenShot(String screenShotFolder, String screenShotName)
	{
		String screenShotHtmlPath = screenShotFolder+"/"+ screenShotName;
		String screenShotPath = InitializeConfig.reportFolder + "/" + screenShotHtmlPath;
		try
		{
			File f = new File(screenShotPath);
			if(!f.getParentFile().exists())
			{
				f.mkdirs();
			}
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(screenShotPath));
		}
		catch(Exception e) { e.printStackTrace(); }
		
		return screenShotHtmlPath;
	}
	
	
	public void hover(WebDriver d, WebElement we) throws InterruptedException
	{
		Actions a = new Actions(d);
		a.moveToElement(we).perform();
		//Thread.sleep(2000);
	}
	
	
	public void hoverAndClick(WebDriver d, WebElement we) throws InterruptedException
	{
		Actions a = new Actions(d);
		a.moveToElement(we).click().perform();
		//Thread.sleep(2000);
	}
	
	
	public Boolean waitForElement(By byLocator, int timeout)
	{
		//waitForPageToLoad(20);
		int i;
		for (i = 0; i < timeout; i++)
		{
			try
			{
				if(driver.findElement(byLocator).isDisplayed() && driver.findElement(byLocator).isEnabled())
				{
					return Boolean.valueOf(true);
				}
				else {
					System.out.println("in waitForElement(By byLocator, int timeout) function");
					Thread.sleep(1000);
				}
			}
			catch (Exception e) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) { e1.printStackTrace(); }
			}
			
		}
		//if(i==timeout)
		//	throw new TimeoutException("Element cannot be found even after "+timeout+" seconds timeout.");
		return Boolean.valueOf(false);
	}
	
	
	public Boolean waitForPresenceOfElement(By byLocator, int timeout)
	{
		int i;
		for (i = 0; i < timeout; i++)
		{
			try
			{
				if(driver.findElement(byLocator).isDisplayed())
				{
					return Boolean.valueOf(true);
				}
				else {
					Thread.sleep(1000);
				}
			}
			catch (Exception e) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) { e1.printStackTrace(); }
			}
			
		}
		
		return Boolean.valueOf(false);
	}
	
	
	
	
	public void waitForPageToLoad(int timeoutInSec)
	{
		try
		{
			new WebDriverWait(driver, timeoutInSec).until
			(new ExpectedCondition<Boolean>()
				{
					public Boolean apply(WebDriver driver)
					{
						return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
					}
				}
			);
			
		}
		catch (WebDriverException e)
		{
			System.out.println("WebDriverException occured : "+e);
		}
	}
	
	
	//To switch to the latest found window
	public void switchWindow()
	{
		for (String winHandle : driver.getWindowHandles())
		{
			driver.switchTo().window(winHandle);
		}
	}
	
	
	/*//To get current window information
	public String getWindow()
	{
		return driver.getWindowHandle();
	}*/
	
	//To switch to the required window
	public void switchWindow(String windowName)
	{
		driver.switchTo().window(windowName);
		//System.out.println("Switched to "+windowName+" window");
	}
	
	
	public void SwitchToFrame(String nameOrID)
	{
		driver.switchTo().defaultContent();
		wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(nameOrID));
	}
	
	
	/*public void switchFrame(String nameOrID)
	{
		//driver.switchTo().defaultContent();
		wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(nameOrID));
	}*/
	
	
	public void selectDropdown(By byLocator, String selectionType, String value) throws Exception
	{
		waitForElement(byLocator, 10);
		Select sel = new Select(driver.findElement(byLocator));
		System.out.println(""+byLocator);
		int i, flag=0;
		for (i = 0; i < 100; i++)
		{
			System.out.println("iteration : "+i);
			try {
					switch (selectionType.toLowerCase().trim())
					{
						case "index" :
							driver.findElement(byLocator).click();
							sel.selectByIndex(Integer.parseInt(value));
							flag = 1;
							break;
							
						case "value" :
							driver.findElement(byLocator).click();
							sel.selectByValue(value);
							flag = 1;
							break;
							
						case "text" :
							driver.findElement(byLocator).click();
							sel.selectByVisibleText(value);
							flag = 1;
							break;
					}
			} catch (Exception e) {Thread.sleep(100);}
			if(flag==1){break;}
		}
		if(i==100)
			throw new TimeoutException("Element cannot be found even after "+10+" seconds timeout.");
		
	}
	
	
	public void SendTextToWebElement(By byLocator, String value)
	{
		try {
			waitForPresenceOfElement(byLocator, GLOBAL_TIMEOUT);
			//driver.findElement(byLocator).clear();
			driver.findElement(byLocator).click();
			driver.findElement(byLocator).sendKeys(value);
		}
		catch (Exception e) { e.printStackTrace(); }
		
	}
	
	
	public boolean ClickOnWebElement(By byLocator)
	{
		try {
			waitForPresenceOfElement(byLocator, GLOBAL_TIMEOUT);
			driver.findElement(byLocator).click();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		
	}
	
	
	public void ClickOnWebElement(By byLocator, int timeout)
	{
		try {
			waitForPresenceOfElement(byLocator, timeout);
			driver.findElement(byLocator).click();
		}
		catch (Exception e) { e.printStackTrace(); }
	}
	
	
	
	
	public List<WebElement> findElements(By byLocator)
	{
		for (int i = 0; i < GLOBAL_TIMEOUT; i++)
		{
			try
			{
				List<WebElement> tempList = driver.findElements(byLocator);
				return tempList;
			}
			catch (Exception e) {
				e.printStackTrace();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
		
		return null;
	}
	
	
	public String getWebElementText(By byLocator)
	{
		
		for (int i = 0; i < GLOBAL_TIMEOUT; i++)
		{
			try
			{
				String text = driver.findElement(byLocator).getText();
				return text;
			}
			catch (Exception e) {
				e.printStackTrace();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
		
		
		return null;
	}
	
	
	
	public String getDateParams(String param)
	{
		SimpleDateFormat sdfDate = new SimpleDateFormat(param);
		Date now = new Date();
		String value = sdfDate.format(now);
		return value;
	}
	
	
	public String timeDifference(Date startTime) throws Exception
	{
		java.util.Date endTime = new java.util.Date();
		long diffMs = endTime.getTime()-startTime.getTime();
		long diffSec = diffMs / 1000;
		long min = diffSec / 60;
		long sec = diffSec % 60;
		return min+" minutes and "+sec+" Seconds";
	}
	
	
	public String inputbox(String prompt)
	{
		return JOptionPane.showInputDialog(prompt);
	}
	
	
	public void msgbox(String message)
	{
		JOptionPane.showMessageDialog(null, message, "Message", JOptionPane.PLAIN_MESSAGE);
	}
	
	
	
	
	
	
	
	public String getCurrentSystemTime()
	{
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date now = new Date();
		String strDate = sdfDate.format(now);
		return strDate;
	}
	
	
	public String FolderNameAsDate()
	{
		SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy(hh mm ss S aa)");
		Date now = new Date();
		String strDate = date.format(now);
		return strDate;
	}
	
	
	public String getCurrentSystemID()
	{
		String computerName = null;
		try
		{
			computerName = InetAddress.getLocalHost().getHostName();
		}
		catch(Exception e){e.printStackTrace();}
		return computerName;
	}
	
	
	
	
}


