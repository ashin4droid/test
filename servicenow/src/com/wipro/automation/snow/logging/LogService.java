package com.wipro.automation.snow.logging;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.hsqldb.lib.HashMap;


public class LogService
{
	private static Logger logger = null;
	
	
	public static Logger getCustomLogger()
	{
		return logger = (logger == null) ? createCustomLogger(DefinedLoggers.EXECUTION_LOG) : logger;
	}
	
	/*public static Logger getCustomLogger(String definedLog)
	{
		return logger = (logger == null) ? createCustomLogger(definedLog) : logger;
	}*/
	
	
	
	private static Logger createCustomLogger(String definedLog)
	{
		InputStream loggingprops;
		
		try
		{
			loggingprops = new FileInputStream(new File("resources/log4j.properties"));
			PropertyConfigurator.configure(loggingprops);
			logger = Logger.getLogger(definedLog);
		}
		catch (FileNotFoundException fnfe)
		{
			fnfe.printStackTrace();
		}
		
		return logger;
	}
	
	
	public static void main(String[] args)
	{
		/*Logger log = getCustomLogger();
		log.info("Logging done");
		
		System.out.println("Done");*/
	}
	
	
}
