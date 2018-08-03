package com.wipro.automation.snow.lib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
//import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class LibFinal
{
	String userDir = System.getProperty("user.dir");
	WebDriver driver;
	WebElement we;
	WebDriverWait wait;
	
	
	public LibFinal()
	{
		//folderNameAsDate = FolderNameAsDate();
	}
	
	
	public WebDriver openBrowser(String browser)
	{
		switch (browser.toLowerCase())
		{
			case "firefox" :
				/*FirefoxProfile profile = new FirefoxProfile();
				profile.setPreference("network.proxy.type", 1);
				profile.setPreference("network.proxy.http", "hdc-proxy.wipro.com");
				profile.setPreference("network.proxy.http_port", 8080);
				profile.setPreference("network.proxy.ssl", "hdc-proxy.wipro.com");
				profile.setPreference("network.proxy.ssl_port", 8080);
				//driver = new FirefoxDriver(profile);*/
				
				driver = new FirefoxDriver();
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				driver.manage().window().maximize();
				break;
				
			case "chrome" :
				System.setProperty("webdriver.chrome.driver", "resources/chromedriver.exe");
				ChromeOptions co = new ChromeOptions();
				//here "--start-maximized" argument is responsible to maximize chrome browser
				//co.addArguments("--start-maximized");
				driver = new ChromeDriver(co);
				driver.manage().window().maximize();
				break;
				
			case "ie" :
				System.setProperty("webdriver.ie.driver", userDir+"/src/main/resources/Drivers/IEDriver-x64.exe");
				//System.setProperty("webdriver.ie.driver", userDir+"/src/main/resources/Drivers/IEDriver-x86.exe");
				DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
				caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
				//caps.setCapability("EnableNativeEvents", false);
				caps.setCapability("ignoreZoomSetting", true);
				driver = new InternetExplorerDriver(caps);
				//driver = new InternetExplorerDriver();
				driver.findElement(By.tagName("html")).sendKeys(Keys.chord(Keys.CONTROL, "0"));
				break;
				
			/*case "Gokuh" :
				System.out.println("Never punctual.");
				break;*/
				
			default : System.out.println("Select the appropriate browser driver");
			wait = new WebDriverWait(driver, 20); 
		}
		return driver;
	}
	
	
	public static class BrowserType
	{
		public static String CHROME = "chrome";
	}
	
	
	/*
	 * Excel Functions Using jxl
	 * */
	
	public String dataFromExcel(File f, String sheetName, int row, int col)
	{
		try
		{
			Workbook wb = Workbook.getWorkbook(f);
			Sheet s1 = wb.getSheet(sheetName);
			return s1.getCell(col-1, row-1).getContents();
		}
		catch (Exception e) { e.printStackTrace(); }
		return null;
	}
	
	
	public void appendExcel(File f, String sheetName, int row, int col, String value) throws BiffException, IOException, WriteException
	{
		Workbook wb = Workbook.getWorkbook(f);
		WritableWorkbook wwb = Workbook.createWorkbook(f, wb);
		WritableSheet wwbs = wwb.getSheet(sheetName);
		
		wwbs.addCell(new Label(col-1, row-1, value));
		
		wwb.write();
		wwb.close();
	}
	
	
	public void writeToExcel(File f, String sheetName, int row, int col, String value) throws BiffException, IOException, WriteException
	{
		WritableWorkbook wb = Workbook.createWorkbook(f);
		WritableSheet	 ws = wb.createSheet(sheetName, 0);
		
		ws.addCell(new Label(col-1, row-1, value));
		
		wb.write();
		wb.close();
	}
	
	
	
	
	
	/****  Excel Functions  ****/
	
	public void writeToExcelPoi(File f, String sheetName, int row, int col, String value) throws Exception
	{
		
		FileInputStream file = new FileInputStream(f);
		
		org.apache.poi.ss.usermodel.Workbook workbook = WorkbookFactory.create(f);
		org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet(sheetName);
		
		try
		{
			sheet.createRow(row-1).createCell(col-1).setCellValue(value);
			FileOutputStream fileOut = new FileOutputStream(f);
			workbook.write(fileOut);
			fileOut.close();
			workbook.close();
			file.close();
		}
		catch (Exception e) { e.printStackTrace(); file.close();}
		
		
	}
	
	
	public String dataFromExcelPoi(File f, String sheetName,int row,int col) throws Exception
	{
		FileInputStream file = new FileInputStream(f);
		
		if(f.getAbsolutePath().endsWith(".xlsx"))
		{
			try
			{
				XSSFWorkbook workbook = new XSSFWorkbook(file);
				XSSFSheet sheet = workbook.getSheet(sheetName);
				sheet.getRow(row-1).getCell(col-1).setCellType(Cell.CELL_TYPE_STRING);
				String cellValue = sheet.getRow(row-1).getCell(col-1).getStringCellValue();
				workbook.close();
				file.close();
				return cellValue;
			}
			catch (Exception e) { e.printStackTrace(); }
		}
		else if(f.getAbsolutePath().endsWith(".xls"))
		{
			try
			{
				HSSFWorkbook workbook = new HSSFWorkbook(file);
				HSSFSheet sheet = workbook.getSheet(sheetName);
				sheet.getRow(row-1).getCell(col-1).setCellType(Cell.CELL_TYPE_STRING);
				String cellValue = sheet.getRow(row-1).getCell(col-1).getStringCellValue();
				workbook.close();
				file.close();
				return cellValue;
			}
			catch (Exception e) { e.printStackTrace(); }
		}
		else
		{
			file.close();
			throw new Exception("File is not one of the valid excel file formats.");
		}
		return null;
	}
	
	
	public String dataFromExcelPoiGen(File f, String sheetName,int row,int col) throws Exception
	{
		FileInputStream file = new FileInputStream(f);
		
		org.apache.poi.ss.usermodel.Workbook workbook = WorkbookFactory.create(f);
		org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheet(sheetName);
		
		try
		{
			String cellValue;
			try {
				sheet.getRow(row-1).getCell(col-1).setCellType(Cell.CELL_TYPE_STRING);
				cellValue = sheet.getRow(row-1).getCell(col-1).getStringCellValue();
			}
			catch (NullPointerException e) {
				return "";
			}
			workbook.close();
			file.close();
			
			return cellValue;
		}
		catch (Exception e) { e.printStackTrace(); file.close(); workbook.close(); return null;}
		finally {
			file.close(); workbook.close();
		}
		
	}
	
	
	
	public String dataFromFlatFile(File f, int reqLine) throws FileNotFoundException, IOException
	{
		String line;
		int lineNumber = 1;
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		
		while((line = br.readLine())!=null)
		{
			if	(lineNumber == reqLine) {	br.close();	return line;	}
			else {	lineNumber++;	}
		}
		
		br.close();
		return null;
	}
	
	
	public String dataFromFlatFile(File f, int reqLine, int position) throws FileNotFoundException, IOException
	{
		String line;
		int lineNumber = 1;
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		
		while((line = br.readLine())!=null)
		{
			if (lineNumber == reqLine)
			{
				String[] x = line.split(",");
				br.close();
				return x[position-1];
			}
			else {	lineNumber++;	}
		}
		
		br.close();
		return null;
	}
	
	
	
	
	
	
	
	public void hover(WebDriver d, WebElement we) throws InterruptedException
	{
		Actions a = new Actions(d);
		a.moveToElement(we).perform();
		Thread.sleep(2000);
	}
	
	
	public void hoverAndClick(WebDriver d, WebElement we) throws InterruptedException
	{
		Actions a = new Actions(d);
		a.moveToElement(we).click().perform();
		Thread.sleep(2000);
	}
	
	
	/*public void waitForElement(String xpath, int timeout) throws InterruptedException
	{
		for (int i = 0; i < timeout; i++)
		{
			if(driver.findElement(By.xpath(xpath)).isDisplayed()) {break;}
			else	{ Thread.sleep(1000); }
		}
	}*/
	
	
	public void selectDropdown(By byLocator, String selectionType, String value)
	{ 
		//wait = new WebDriverWait(driver, 20); 
		wait.until(ExpectedConditions.elementToBeClickable(byLocator)); 
		driver.findElement(byLocator).click(); 
		Select sel = new Select(driver.findElement(byLocator)); 
		
		switch (selectionType.toLowerCase()) 
		{ 
				case "index" : 
						sel.selectByIndex(Integer.parseInt(value)); 
						break; 
						
				case "value" : 
						sel.selectByValue(value); 
						break; 
						
				case "text" : 
						sel.selectByVisibleText(value); 
						break; 
		}
		
		driver.findElement(byLocator); 
	}
	
	
	public void SendTextToWebElement(By byLocator, String value)
	{
		wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(byLocator));
		
		driver.findElement(byLocator).clear();
		driver.findElement(byLocator).sendKeys(value);
		driver.findElement(byLocator).sendKeys(Keys.TAB);
	}
	
	
	public void ClickOnWebElement(By byLocator)
	{
		wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(byLocator));
		
		driver.findElement(byLocator).click(); 
	}
	
	
	public void WaitForElement(By byLocator)
	{
		wait = new WebDriverWait(driver, 20); 
		wait.until(ExpectedConditions.elementToBeClickable(byLocator));
	}
	
	
	public void WaitForAlert() 
	{
		wait = new WebDriverWait(driver, 20); 
		wait.until(ExpectedConditions.alertIsPresent()); 
	}
	
	
	public void WaitForFrame(By locator) 
	{
		wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator)); 
	}
	
	
	public String getDateParams(String param) 
	{
		SimpleDateFormat sdfDate = new SimpleDateFormat(param); 
		Date now = new Date(); 
		String value = sdfDate.format(now); 
		return value; 
	}
	
	
	
	
	
	
	public String inputbox(String prompt)
	{
		return JOptionPane.showInputDialog(prompt);
	}
	
	
	public void msgbox(String message)
	{
		JOptionPane.showMessageDialog(null, message, "Message", JOptionPane.PLAIN_MESSAGE);
	}
	
	
	public boolean containsChars(String testString)
	{
		char[] alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
		
		for (int i = 0; i < testString.length(); i++)
		{
			for (int j = 0; j < alphabet.length; j++)
			{
				System.out.println(testString.charAt(i)+"\t"+alphabet[j]);
				if (testString.charAt(i) == alphabet[j])
				{
					return true;
				}
			}
		}
		return false;
	}
	
	
	public boolean containsNumbers(String testString)
	{
		char[] number = "1234567890".toCharArray();
		
		for (int i = 0; i < testString.length(); i++)
		{
			for (int j = 0; j < number.length; j++)
			{
				System.out.println(testString.charAt(i)+"\t"+number[j]);
				if (testString.charAt(i) == number[j])
				{
					return true;
				}
			}
		}
		return false;
	}
	
	
	public boolean isAlphaNumeric(String testString)
	{
		char[] alphaNumeric = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();
		
		for (int i = 0; i < testString.length(); i++)
		{
			for (int j = 0; j < alphaNumeric.length; j++)
			{
				System.out.println(testString.charAt(i)+"\t"+alphaNumeric[j]);
				if (testString.charAt(i) == alphaNumeric[j])
				{
					return true;
				}
			}
		}
		return false;
	}
	
	
	public boolean containsSplChars(String testString)
	{
		char[] number = "`~!@#$%^&*()_+-={}[];:'\"/?.>,<\\|".toCharArray();
		
		for (int i = 0; i < testString.length(); i++)
		{
			for (int j = 0; j < number.length; j++)
			{
				System.out.println(testString.charAt(i)+"\t"+number[j]);
				if (testString.charAt(i) == number[j])
				{
					return true;
				}
			}
		}
		return false;
	}
	
	
	
	
	
	//To switch to the next found window
	public void switchWindow()
	{
		for (String hwnd : driver.getWindowHandles())
		{
			driver.switchTo().window(hwnd); // switch focus of WebDriver to the next found window handle (that's our newly opened window)
			System.out.println("next Current window ID : "+hwnd);
		}
	}
	
	//To get current window information
	public String getWindow()
	{
		return driver.getWindowHandle();
	}
	
	//To switch to the required window
	public void switchWindow(String windowName)
	{
		driver.switchTo().window(windowName);
	}
	
	
	
	
	
	
	
	
	/*public String getScreenShot(String tcName,String screenShotName)
	{
		String path = userDir+"/TestReportsFinal/Screenshots/"+folderNameAsDate+"/"+tcName;
		//+getCurrentSystemTime()+"/"
		String screenShotPath = path+"/"+ screenShotName + ".png";
		try
		{
			File f = new File(path);
			if(!f.exists())
			{
				f.mkdirs();
			}
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(screenShotPath));
		}
		catch(Exception e) { e.printStackTrace(); }
		return screenShotPath;
	}*/
	
	
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
