package test;

import java.util.Date;

import org.apache.log4j.Logger;

import com.wipro.automation.snow.lib.MyLibraryTest;
import com.wipro.automation.snow.logging.LogService;
import com.wipro.automation.snow.utils.ExcelUtil;


public class Driver
{
	private static Logger log = LogService.getCustomLogger();
	
	public static void main(String[] args)
	{
		//autoAssignAndClose();
		
		autoAssignGeneral();
	}
	
	private static void autoAssignAndClose()
	{
		Date start = new Date();
		try
		{
			RouteTickets.route();
			ResolveTickets.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error("Exception : ", e);
		}
		finally
		{
			try {
				ExcelUtil.updateRunInExcel(start, RouteTickets.getRoutedTicketList(), ResolveTickets.getResolvedTicketList());
				teardown();
			}
			catch (InterruptedException e) { e.printStackTrace(); }
		}
	}
	
	private static void autoAssignGeneral()
	{
		Date start = new Date();
		try
		{
			RouteTicketsGeneral.route();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error("Exception : ", e);
		}
		finally
		{
			try {
				ExcelUtil.updateGenRoutingRunInExcel(start, RouteTicketsGeneral.getRoutedTicketsMap());
				teardown();
			}
			catch (InterruptedException e) { e.printStackTrace(); }
		}
	}
	
	
	public static void teardown() throws InterruptedException
	{
		//Thread.sleep(3000);
		MyLibraryTest.getDriver().quit();
	}
}

