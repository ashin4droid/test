package com.wipro.automation.snow.utils;

import java.util.List;

import org.apache.log4j.Logger;

import com.wipro.automation.snow.logging.LogService;

public class UtilTest
{
	private static Logger log = LogService.getCustomLogger();
	
	public static void main(String[] args) throws Exception
	{
		System.out.println("Started");
		log.info("Started");
		//List<QueueData> qData = ExcelUtil.getQueueData();
		List<ClosureData> cData = ExcelUtil.getClosureData();
		
		//System.out.println("Queue : " + qData.size());
		log.info("Closure : " + cData.size());
		
		log.info("Finished");
	}
	
	
}
