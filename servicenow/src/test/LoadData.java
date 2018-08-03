package test;

import java.util.Date;

import com.wipro.automation.snow.utils.ExcelUtil;

public class LoadData implements Runnable
{
	
	@Override
	public void run()
	{
		try {
			System.out.println("Data loading started --> " + new Date());
			RouteTicketsGeneral.qDataNew = ExcelUtil.getQueueDataFinal();
			System.out.println("Data loaded successfully --> " + new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
}
