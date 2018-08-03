package com.wipro.automation.snow.lib;


import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
//import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


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
				
				System.setProperty("webdriver.gecko.driver", "resources/geckodriver.exe");
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
	
	
	/*private Proxy getProxy()
	{
		Proxy proxy = new Proxy();
		//String strproxy = "10.156.50.99:8080";
		String strproxy = "proxy2.wipro.com:8080";
		
		proxy.setHttpProxy(strproxy).setFtpProxy(strproxy).setSocksProxy(strproxy);
		//proxy.setSocksUsername("as762939").setSocksPassword("ash@Apr75");
		
		return proxy;
	}*/
	
	
	private DesiredCapabilities getCapabilities(String browserName)
	{
		DesiredCapabilities caps = null;
		
		switch (browserName)
		{
			case BrowserType.CHROME :
				ChromeOptions chromeOptions = new ChromeOptions();
				/*chromeOptions.addArguments("no-sandbox");
				chromeOptions.addArguments("test-type");
				chromeOptions.addArguments("--js-flags=--expose-gc");  
				chromeOptions.addArguments("--enable-precise-memory-info"); 
				chromeOptions.addArguments("--disable-popup-blocking");
				chromeOptions.addArguments("--disable-default-apps");
				chromeOptions.addArguments("test-type=browser");
				chromeOptions.addArguments("disable-infobars");
				chromeOptions.addArguments("--disable-extensions");*/
				chromeOptions.addArguments("--start-maximized");
				//chromeOptions.addArguments("--start-maximized");
				//chromeOptions.addExtensions(new File("resources/internal.crx"));
				
				caps = DesiredCapabilities.chrome();
				//caps.setCapability(CapabilityType.PROXY, getProxy());
				caps.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
				
				break;
				
			case BrowserType.INTERNET_EXPLORER :
				caps = DesiredCapabilities.internetExplorer();
				caps.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
				caps.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
				//caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
				//caps.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
				//caps.setCapability("EnableNativeEvents", false);
				//caps.setCapability("ignoreZoomSetting", true);
				//caps.setCapability("requireWindowFocus", true);
				
				break;
				
			case BrowserType.FIREFOX :
				/*FirefoxProfile profile = new FirefoxProfile();
				profile.setPreference("network.proxy.type", 1);
				profile.setPreference("network.proxy.http", "");
				profile.setPreference("network.proxy.http_port", 8080);
				profile.setPreference("network.proxy.ssl", "");
				profile.setPreference("network.proxy.ssl_port", 8080);
				//profile.setPreference("network.proxy.type", 1);
				//profile.setPreference("network.proxy.autoconfig_url", "");
				//profile.setPreference("network.proxy.no_proxies_on", "");
				//caps.setCapability(CapabilityType.PROXY, getProxy());
				0 - Direct connection (or) no proxy.
				1 - Manual proxy configuration
				2 - Proxy auto-configuration (PAC).
				4 - Auto-detect proxy settings.
				5 - Use system proxy settings.*/
				//profile.setPreference("network.proxy.type", 4);
				
				/*FirefoxProfile profile = new FirefoxProfile();
				profile.setPreference("network.proxy.type", 1);
				profile.setPreference("network.proxy.http", "hdc-proxy.wipro.com");
				profile.setPreference("network.proxy.http_port", 8080);
				profile.setPreference("network.proxy.ssl", "hdc-proxy.wipro.com");
				profile.setPreference("network.proxy.ssl_port", 8080);
				
				profile.setPreference("browser.download.folderList", 2);
				profile.setPreference("browser.download.manager.showWhenStarting", false);
				profile.setPreference("browser.download.dir", "");
				profile.setPreference("browser.helperApps.neverAsk.openFile", "text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");
				profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");
				profile.setPreference("browser.helperApps.alwaysAsk.force", false);
				profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
				profile.setPreference("browser.download.manager.focusWhenStarting", false);
				profile.setPreference("browser.download.manager.useWindow", false);
				profile.setPreference("browser.download.manager.showAlertOnComplete", false);
				profile.setPreference("browser.download.manager.closeWhenDone", false);*/
				
				caps = DesiredCapabilities.firefox();
				//caps.setCapability(FirefoxDriver.PROFILE, profile);
				//caps.setCapability(CapabilityType.PROXY, getProxy());
				
				break;
				
			default:
				break;
		}
		
		return caps;
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
	
	public void scrollIntoElementView(WebElement element)
	{
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
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
			driver.findElement(byLocator).clear();
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
	
	
	public void ClickUsingJs(By byLocator)
	{
		for (int i = 0; i < GLOBAL_TIMEOUT; i++)
		{
			try {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].click();", driver.findElement(byLocator));
				break;
			} catch (Exception e) {
				e.printStackTrace();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
		
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
				return text.trim();
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
	
	
	public String getWebElementTextJs(By byLocator) throws Exception
	{
		waitForPresenceOfElement(byLocator, GLOBAL_TIMEOUT);
		for (int i = 0; i < GLOBAL_TIMEOUT; i++)
		{
			try {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				Object text = js.executeScript("return arguments[0].value;", driver.findElement(byLocator));
				return text.toString();
			} catch (Exception e) {
				//e.printStackTrace();
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


