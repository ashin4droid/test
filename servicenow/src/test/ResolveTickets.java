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
import com.wipro.automation.snow.utils.ClosureData;
import com.wipro.automation.snow.lib.Crypto;
import com.wipro.automation.snow.utils.ExcelUtil;

public class ResolveTickets
{
	private static MyLibraryTest lib = new MyLibraryTest();
	private static WebDriver driver = null;
	private static AppConfigData config = AppConfiguration.getAppConfig();
	private static Logger log = LogService.getCustomLogger();
	private static List<String> resolvedTickets = new ArrayList<String>();
	
	public static void close() throws Exception
	{
		log.info("**************** Ticket Resolving start *****************");
		/*driver = lib.openBrowser(BrowserType.CHROME);
		JSWaiter.setDriver(driver);
		driver.get(AppConstants.SNOW_URL);
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(config.getUsername(), Crypto.decrypt(config.getPassword()));
		
		lib.ClickOnWebElement(HomePage.allAppsTab);
		JSWaiter.waitUntilAngularReady();*/
		
		
		driver = MyLibraryTest.getDriver();
		driver.switchTo().defaultContent();
		
		lib.ClickOnWebElement(HomePage.AllApps.SelfService.myWork);
		//log.info("After lib.ClickOnWebElement(HomePage.AllApps.SelfService.myWork);");
		JSWaiter.waitUntilAngularReady();
		//log.info("After JSWaiter.waitUntilAngularReady();");
		lib.SwitchToFrame("gsft_main");
		//log.info("After lib.SwitchToFrame(\"gsft_main\");");
		lib.waitForPresenceOfElement(GLSAPPage.tasksHeader, 10);
		//log.info("After lib.waitForPresenceOfElement(GLSAPPage.tasksHeader, 10);");
		List<WebElement> incList = lib.findElements(By.xpath("//tbody[@class='list2_body']/tr/td[3]/a"));
		int ticketCount = incList.size();
		List<String> ticketsToBeResolved = new ArrayList<>();
		for (WebElement webElement : incList)
		{
			ticketsToBeResolved.add(webElement.getText().trim());
		}
		
		log.info("ticketCount : " + ticketCount);
		List<ClosureData> cData = ExcelUtil.getClosureData();
		log.info("After ExcelUtil.getClosureData();");
		//inc : for (int i = 1; i <= ticketCount; i++)//original
		inc : for (int i = 1; i <= ticketCount; i++)
		{
			List<WebElement> tempIncList = lib.findElements(By.xpath("//tbody[@class='list2_body']/tr/td[3]/a"));
			if (tempIncList.size()==0) {
				break;
			}
			
			if (lib.ClickOnWebElement(By.xpath("//tbody[@class='list2_body']/tr/td[3]/a[contains(text(),'"+ ticketsToBeResolved.get(i-1) +"')]"))){}
			else {
				continue inc;
			}
			
			JSWaiter.waitUntilAngularReady();
			String shortDesc = lib.getWebElementTextJs(GLSAPPage.shortDesc);
			
			for (ClosureData closureData : cData)
			{
				if (shortDesc.contains(closureData.getKeywords()))
				{
					String ticketNuber = lib.getWebElementTextJs(GLSAPPage.ticketNumber);
					lib.selectDropdown(GLSAPPage.incidentState, "text", "Resolved");
					lib.ClickOnWebElement(GLSAPPage.affectedLocationBtn);
					lib.SendTextToWebElement(GLSAPPage.affectedLocationTxtBox, "GLOBAL");
					lib.ClickOnWebElement(GLSAPPage.affectedLocationSelection);
					
					lib.ClickOnWebElement(GLSAPPage.closureInfoTab);
					lib.selectDropdown(GLSAPPage.closureInfoCloseCode, "value", "Solved Permanently");
					lib.SendTextToWebElement(GLSAPPage.closureInfoCloseNotes, closureData.getComments());
					
					lib.ClickOnWebElement(GLSAPPage.saveAndExit);
					resolvedTickets.add(ticketNuber);
					JSWaiter.waitUntilAngularReady();
					continue inc;
				}
			}
			//System.out.println(i + " : " + ticketsToBeResolved.get(i-1));
			lib.ClickOnWebElement(GLSAPPage.back);
			
			JSWaiter.waitUntilAngularReady();
			lib.waitForPresenceOfElement(GLSAPPage.tasksHeader, 10);
			tempIncList = lib.findElements(By.xpath("//tbody[@class='list2_body']/tr/td[3]/a"));
			ticketCount = tempIncList.size();
		}
		
		log.info("**************** Ticket Resolving end *****************");
	}
	
	/*public static boolean conainsKeyword(String shortDesc, String[] keywords)
	{
		
		for (String keyword : keywords)
		{
			if (shortDesc.contains(keyword.trim())) {
				return true;
			}
		}
		
		return false;
	}*/
	
	
	public static List<String> getResolvedTicketList()
	{
		return resolvedTickets;
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
	JSWaiter.waitUntilAngularReady();
	//break;
}
else {
	lib.ClickOnWebElement(GLSAPPage.back);
}*/

