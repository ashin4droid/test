package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.wipro.automation.snow.config.AppConfigData;
import com.wipro.automation.snow.config.AppConfiguration;
import com.wipro.automation.snow.lib.Crypto;
import com.wipro.automation.snow.lib.JSWaiter;
import com.wipro.automation.snow.lib.MyLibraryTest;
import com.wipro.automation.snow.lib.MyLibraryTest.BrowserType;
import com.wipro.automation.snow.logging.LogService;
import com.wipro.automation.snow.pages.GLSAPPage;
import com.wipro.automation.snow.pages.HomePage;
import com.wipro.automation.snow.pages.LoginPage;
import com.wipro.automation.snow.utils.QueueDataFinal;

public class RouteTicketsGeneral
{
	private static MyLibraryTest lib = new MyLibraryTest();
	private static WebDriver driver = null;
	private static AppConfigData config = AppConfiguration.getAppConfig();
	private static Logger log = LogService.getCustomLogger();
	private static Map<String, QueueDataFinal> routedTicketsMap = new HashMap<>();
	public static List<QueueDataFinal> qDataNew = null;
	private static Thread dataLoadThread = null;
	
	public static Map<String, QueueDataFinal> getRoutedTicketsMap()
	{
		return routedTicketsMap;
	}
	
	public static void route() throws Exception
	{
		loadData();
		openBrowserAndLogin();
		//navigateToGLSAPQueue();
		navigateToGLSAPQueueFavourites();
		
		//lib.SwitchToFrame("gsft_main");
		List<WebElement> incList = driver.findElements(By.xpath("//tbody[@class='list2_body']/tr[child::td[last()-1][text()='1' or text()='0']]/td[3]/a"));
		int ticketCount = incList.size();
		
		List<String> ticketsToBeRoutedList = new ArrayList<>();
		
		/*for (int i = 1; i <= ticketCount; i++)
		{
			ticketsToBeRoutedList.add(lib.getWebElementText(By.xpath("//tbody[@class='list2_body']/tr["+ i +"]/td[3]/a")));
		}*/
		
		for (WebElement webElement : incList)
		{//tbody[@class='list2_body']/tr[child::td[last()-1][text()='1' or text()='0']]/td[3]/a
			ticketsToBeRoutedList.add(webElement.getText().trim());
		}
		
		
		
		int itrCount = ticketsToBeRoutedList.size();
		
		dataLoadThread.join();
		
		//qDataNew = ExcelUtil.getQueueDataNew();
		
		inc : for (int i = 1; i <= itrCount; i++)
		{
			lib.waitForPresenceOfElement(HomePage.AllApps.incidentsHeader, 10);
			List<WebElement> tempIncList = lib.findElements(By.xpath("//tbody[@class='list2_body']/tr/td[3]/a"));
			if (tempIncList.size()==0) {
				break;
			}
			
			if (lib.ClickOnWebElement(By.xpath("//tbody[@class='list2_body']/tr/td[3]/a[contains(text(),'"+ ticketsToBeRoutedList.get(i-1) +"')]"))){}
			else {
				continue;
			}
			
			JSWaiter.waitUntilAngularReady();
			lib.waitForPresenceOfElement(GLSAPPage.shortDesc, 10);
			String ticketNuber = lib.getWebElementTextJs(GLSAPPage.ticketNumber);
			String shortDesc = lib.getWebElementTextJs(GLSAPPage.shortDesc);
			String affectedUser = lib.getWebElementTextJs(GLSAPPage.affectedUser).trim();
			
			if (affectedUser.equalsIgnoreCase("NetcooluserEU"))
			{
				lib.ClickOnWebElement(GLSAPPage.back);
				continue;
			}
			
			for (QueueDataFinal queueData : qDataNew)
			{
				if (shortDesc.contains(queueData.getKeyword().trim()))
				{
					lib.SendTextToWebElement(GLSAPPage.assignmentGroup, queueData.getQueue());
					lib.ClickOnWebElement(By.xpath("//table[@class='ac_table_completer']/tbody/tr/td[text()='"+ queueData.getQueue() +"']"));
					lib.selectDropdown(GLSAPPage.incidentState, "text", "Accepted");
					JSWaiter.waitUntilAngularReady();
					lib.SendTextToWebElement(GLSAPPage.assignedTo, queueData.getAssigneeEmail());
					lib.ClickOnWebElement(By.xpath("//div[@id='AC.incident.assigned_to']/table/tbody/tr/td[text()='"+ queueData.getAssigneeEmail() +"']"));
					//lib.SendTextToWebElement(GLSAPPage.configItem, "AP_SAP");
					//lib.ClickOnWebElement(By.xpath("//div[@id='AC.incident.cmdb_ci']/table/tbody/tr/td[text()='AP_SAP']"));
					
					queueData.setShortDescription(shortDesc);
					lib.ClickOnWebElement(GLSAPPage.saveAndExit);
					JSWaiter.waitJQueryAngular();
					routedTicketsMap.put(ticketNuber.trim(), queueData);
					continue inc;
				}
			}
			
			lib.ClickOnWebElement(GLSAPPage.back);
			
			JSWaiter.waitUntilAngularReady();
			lib.waitForPresenceOfElement(HomePage.AllApps.incidentsHeader, 10);
			tempIncList = lib.findElements(By.xpath("//tbody[@class='list2_body']/tr/td[3]/a"));
			ticketCount = tempIncList.size();
		}
		
		
	}
	
	private static void openBrowserAndLogin() throws Exception
	{
		driver = lib.openBrowser(BrowserType.CHROME);
		JSWaiter.setDriver(driver);
		driver.get(config.getServicenowurl());
		LoginPage loginPage = new LoginPage(driver);
		
		loginPage.login(config.getUsername(), Crypto.decrypt(config.getPassword()));
	}
	
	
	private static void loadData()
	{
		dataLoadThread = new Thread(new LoadData());
		dataLoadThread.start();
	}
	
	
	private static void navigateToGLSAPQueueFavourites()
	{
		lib.ClickOnWebElement(HomePage.favouritesTab);
		JSWaiter.waitUntilAngularReady();
		lib.ClickOnWebElement(HomePage.Favourites.glsapQueue);
		lib.ClickUsingJs(HomePage.Favourites.glsapQueue);
		JSWaiter.waitUntilAngularReady();
		
		
		for (int i = 1; i <= 5; i++)
		{
			lib.SwitchToFrame("gsft_main");
			
			if (!lib.waitForPresenceOfElement(HomePage.AllApps.incidentsHeader, 3))
			{
				driver.switchTo().defaultContent();
				lib.ClickOnWebElement(HomePage.Favourites.glsapQueue);
			}
			else { break; }
		}
		
		lib.SwitchToFrame("gsft_main");
		lib.waitForPresenceOfElement(HomePage.AllApps.incidentsHeader, 10);
		JSWaiter.waitUntilAngularReady();
	}
	
	
	private static void navigateToGLSAPQueue()
	{
		log.info("Before lib.ClickOnWebElement(HomePage.allAppsTab);");
		lib.ClickOnWebElement(HomePage.allAppsTab);
		log.info("After lib.ClickOnWebElement(HomePage.allAppsTab);");
		JSWaiter.waitUntilAngularReady();
		log.info("After JSWaiter.waitUntilAngularReady();");
		lib.ClickOnWebElement(HomePage.AllApps.homePageLink);
		log.info("After lib.ClickOnWebElement(HomePage.AllApps.homePageLink);");
		
		lib.SwitchToFrame("gsft_main");
		
		log.info("Before lib.waitForPresenceOfElement(HomePage.AllApps.incMapArea, 10);");
		boolean areaIsPresent = lib.waitForPresenceOfElement(HomePage.AllApps.incMapArea, 10);
		log.info("incMapArea : " + areaIsPresent);
		if (!areaIsPresent) {
			return;
		}
		log.info("After lib.waitForPresenceOfElement(HomePage.AllApps.incMapArea, 10);");
		//lib.ClickOnWebElement(HomePage.AllApps.incMapArea);
		lib.ClickOnWebElement(HomePage.AllApps.incMapArea);
		lib.ClickUsingJs(HomePage.AllApps.incMapArea);
		log.info("After lib.ClickOnWebElement(HomePage.AllApps.incMapArea);");
		lib.waitForPresenceOfElement(HomePage.AllApps.incidentsHeader, 10);
		log.info("After lib.waitForPresenceOfElement(HomePage.AllApps.incidentsHeader, 10);");
		JSWaiter.waitUntilAngularReady();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*private static boolean conainsKeyword(String shortDesc, String[] keywords)
	{
		
		for (String keyword : keywords)
		{
			if (shortDesc.contains(keyword.trim())) {
				return true;
			}
		}
		
		return false;
	}*/
	
	
	
	
}




