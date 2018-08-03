package test;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.wipro.automation.snow.config.AppConfigData;
import com.wipro.automation.snow.config.AppConfiguration;
import com.wipro.automation.snow.config.AppConstants;
import com.wipro.automation.snow.lib.JSWaiter;
import com.wipro.automation.snow.lib.MyLibraryTest;
import com.wipro.automation.snow.lib.MyLibraryTest.BrowserType;
import com.wipro.automation.snow.logging.LogService;
import com.wipro.automation.snow.pages.GLSAPPage;
import com.wipro.automation.snow.pages.HomePage;
import com.wipro.automation.snow.pages.LoginPage;
import com.wipro.automation.snow.lib.Crypto;

public class RouteTickets
{
	private static MyLibraryTest lib = new MyLibraryTest();
	private static WebDriver driver = null;
	private static AppConfigData config = AppConfiguration.getAppConfig();
	private static Logger log = LogService.getCustomLogger();
	private static List<String> routedTickets = new ArrayList<String>();
	
	
	public static void route() throws Exception
	{
		log.info("**************** Ticket Routing start *****************");
		driver = lib.openBrowser(BrowserType.CHROME);
		JSWaiter.setDriver(driver);
		driver.get(config.getServicenowurl());
		LoginPage loginPage = new LoginPage(driver);
		
		loginPage.login(config.getUsername(), Crypto.decrypt(config.getPassword()));
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
		
		lib.SwitchToFrame("gsft_main");
		List<WebElement> incList = driver.findElements(By.xpath("//tbody[@class='list2_body']/tr[child::td[last()-2][text()='1' or text()='0']]/td[3]/a"));
		//int ticketCount = incList.size();
		
		List<String> ticketsToBeAssigned = new ArrayList<>();
		for (WebElement webElement : incList)
		{
			ticketsToBeAssigned.add(webElement.getText().trim());
		}
		int itrCount = ticketsToBeAssigned.size();
		//qData = ExcelUtil.getQueueData();
		
		for (int i = 1; i <= itrCount; i++)
		{
			lib.waitForPresenceOfElement(HomePage.AllApps.incidentsHeader, 10);
			List<WebElement> tempIncList = lib.findElements(By.xpath("//tbody[@class='list2_body']/tr/td[3]/a"));
			if (tempIncList.size()==0) {
				break;
			}
			
			if (lib.ClickOnWebElement(By.xpath("//tbody[@class='list2_body']/tr/td[3]/a[contains(text(),'"+ ticketsToBeAssigned.get(i-1) +"')]"))){}
			else {
				continue;
			}
			
			JSWaiter.waitUntilAngularReady();
			lib.waitForPresenceOfElement(GLSAPPage.shortDesc, 10);
			String ticketNuber = lib.getWebElementTextJs(GLSAPPage.ticketNumber);
			String shortDesc = lib.getWebElementTextJs(GLSAPPage.shortDesc);
			String affectedUser = lib.getWebElementTextJs(GLSAPPage.affectedUser).trim();
			
			if (affectedUser.equalsIgnoreCase("NetcooluserEU") && shortDesc.contains("Abap Dump"))
			{
				//System.out.println(ticketList.get(i) + " : " + "NetcooluserEU");
				//lib.SendTextToWebElement(GLSAPPage.assignmentGroup, "GL_SAP_DEV");
				//lib.ClickOnWebElement(By.xpath("//table[@class='ac_table_completer']/tbody/tr/td[text()='GL_SAP_DEV']"));
				lib.selectDropdown(GLSAPPage.incidentState, "text", "Accepted");
				JSWaiter.waitUntilAngularReady();
				lib.SendTextToWebElement(GLSAPPage.assignedTo, "Bhramit Pardhi");
				lib.ClickOnWebElement(By.xpath("//div[@id='AC.incident.assigned_to']/table/tbody/tr/td[text()='Bhramit Pardhi']"));
				lib.SendTextToWebElement(GLSAPPage.configItem, "AP_SAP");
				lib.ClickOnWebElement(By.xpath("//div[@id='AC.incident.cmdb_ci']/table/tbody/tr/td[text()='AP_SAP']"));
				lib.ClickOnWebElement(GLSAPPage.saveAndExit);
				routedTickets.add(ticketNuber.trim());
				continue;
			}
			
			lib.ClickOnWebElement(GLSAPPage.back);
			
			
			JSWaiter.waitUntilAngularReady();
			lib.waitForPresenceOfElement(HomePage.AllApps.incidentsHeader, 10);
			tempIncList = lib.findElements(By.xpath("//tbody[@class='list2_body']/tr/td[3]/a"));
			//ticketCount = tempIncList.size();
			
		}
		
		log.info("**************** Ticket Routing end *****************");
	}
	
	
	public static List<String> getRoutedTicketList()
	{
		return routedTickets;
	}
	
	
	public static boolean conainsKeyword(String shortDesc, String[] keywords)
	{
		
		for (String keyword : keywords)
		{
			if (shortDesc.contains(keyword.trim())) {
				return true;
			}
		}
		
		return false;
	}
	
	
	
	
}

/*if (affectedUser.equalsIgnoreCase("NetcooluserEU") && shortDesc.contains("Abap Dump"))
{
	//System.out.println(ticketList.get(i) + " : " + "NetcooluserEU");
	lib.SendTextToWebElement(GLSAPPage.assignmentGroup, "GL_SAP_DEV");
	lib.ClickOnWebElement(By.xpath("//table[@class='ac_table_completer']/tbody/tr/td[text()='GL_SAP_DEV']"));
	lib.selectDropdown(GLSAPPage.incidentState, "text", "Accepted");
	lib.SendTextToWebElement(GLSAPPage.assignedTo, "Madhu Lata");
	lib.ClickOnWebElement(By.xpath("//div[@id='AC.incident.assigned_to']/table/tbody/tr/td[text()='Madhu Lata']"));
	lib.SendTextToWebElement(GLSAPPage.configItem, "AP_SAP");
	lib.ClickOnWebElement(By.xpath("//div[@id='AC.incident.cmdb_ci']/table/tbody/tr/td[text()='AP_SAP']"));
	lib.ClickOnWebElement(GLSAPPage.saveAndExit);
	JSWaiter.waitJQueryAngular();
	//break;
}
else {
	lib.ClickOnWebElement(GLSAPPage.back);
}*/

/*inc : for (int i = 1; i <= incList.size(); i++)
{
	List<WebElement> tempIncList = driver.findElements(By.xpath("//tbody[@class='list2_body']/tr/td[3]/a"));
	if (tempIncList.size()==0) {
		break;
	}
	
	lib.ClickOnWebElement(By.xpath("//tbody[@class='list2_body']/tr["+i+"]/td[3]/a"));
	JSWaiter.waitJQueryAngular();
	lib.waitForPresenceOfElement(GLSAPPage.shortDesc, 10);
	String shortDesc = lib.getWebElementTextJs(GLSAPPage.shortDesc);
	//String affectedUser = lib.getWebElementTextJs(GLSAPPage.affectedUser).trim();
	
	for (QueueData queueData : qData)
	{
		String[] keywords = queueData.getKeywords().split(",");
		
		if (conainsKeyword(shortDesc, keywords))
		{
			lib.SendTextToWebElement(GLSAPPage.assignmentGroup, queueData.getQueue());
			lib.ClickOnWebElement(By.xpath("//table[@class='ac_table_completer']/tbody/tr/td[text()='"+ queueData.getQueue() +"']"));
			lib.selectDropdown(GLSAPPage.incidentState, "text", "Accepted");
			lib.SendTextToWebElement(GLSAPPage.assignedTo, queueData.getAssigneeName());
			lib.ClickOnWebElement(By.xpath("//div[@id='AC.incident.assigned_to']/table/tbody/tr/td[text()='"+ queueData.getAssigneeName() +"']"));
			lib.SendTextToWebElement(GLSAPPage.configItem, "AP_SAP");
			lib.ClickOnWebElement(By.xpath("//div[@id='AC.incident.cmdb_ci']/table/tbody/tr/td[text()='AP_SAP']"));
			lib.ClickOnWebElement(GLSAPPage.saveAndExit);
			JSWaiter.waitJQueryAngular();
			continue inc;
		}
	}
	
	lib.ClickOnWebElement(GLSAPPage.back);
}*/
