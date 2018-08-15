package com.wipro.automation.snow.logging;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public class LogService
{
	private static Logger logger = null;
	
	
	private static Logger getCustomLogger(String loggerName)
	{
		return logger = (logger == null) ? createNewLogger(loggerName) : logger;
	}
	
	private static Logger getLogger(Class<?> cls)
	{
		return logger = (logger == null) ? createCustomLogger(cls) : logger;
	}
	
	
	private static Logger createCustomLogger(Class<?> cls)
	{
		InputStream loggingprops;
		
		try
		{
			loggingprops = new FileInputStream(new File("resources/log4j.properties"));
			PropertyConfigurator.configure(loggingprops);
			logger = Logger.getLogger(cls);
			//loggingprops.close();
		}
		catch (FileNotFoundException fnfe){
			fnfe.printStackTrace();
		}
		
		return logger;
	}
	
	private static Logger createNewLogger(String loggerName)
	{
		InputStream loggingprops;
		
		try
		{
			loggingprops = new FileInputStream(new File("resources/log4j.properties"));
			PropertyConfigurator.configure(loggingprops);
			logger = Logger.getLogger(loggerName);
			//loggingprops.close();
		}
		catch (FileNotFoundException fnfe){
			fnfe.printStackTrace();
		}
		
		return logger;
	}
	
	
	public static void configure()
	{
		InputStream loggingprops;
		try
		{
			loggingprops = new FileInputStream(new File("resources/log4j.properties"));
			PropertyConfigurator.configure(loggingprops);
			loggingprops.close();
		}
		catch (FileNotFoundException fnfe){ fnfe.printStackTrace(); }
		catch (IOException e) { e.printStackTrace(); }
	}
	
	
	public static void main(String[] args)
	{
		/*Logger log = getCustomLogger();
		log.info("Logging done");
		
		System.out.println("Done");*/
	}
	
	
}
