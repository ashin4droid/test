package com.wipro.automation.snow.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.wipro.automation.snow.logging.DefinedLoggers;
import com.wipro.automation.snow.logging.LogService;


public class AppConfiguration
{
	//private static Logger log = LogService.getLogger(AppConfiguration.class);
	private static Logger log = Logger.getLogger(DefinedLoggers.EXECUTION_LOG);
	private static Properties props = new Properties();
	private static AppConfigData config = null;
	
	
	private AppConfiguration(){}
	
	
	public static AppConfigData getAppConfig()
	{
		return config = (config == null) ? setAppConfig() : config;
	}
	
	
	private static AppConfigData setAppConfig()
	{
		config = new AppConfigData();
		props = getConfigProperties();
		
		config.setUsername(props.getProperty(AppConfigConstants.SNOW_USERNAME));
		config.setPassword(props.getProperty(AppConfigConstants.SNOW_PASSWORD));
		config.setDevusername(props.getProperty(AppConfigConstants.SNOW_DEV_USERNAME));
		config.setDevpassword(props.getProperty(AppConfigConstants.SNOW_DEV_PASSWORD));
		
		return config;
	}
	
	
	private static Properties getConfigProperties()
	{
		return props = (props == null || props.isEmpty()) ? readConfigProperties() : props;
	}
	
	
	private static Properties readConfigProperties()
	{
		Properties props = new Properties();
		
		try {
			props.load(new FileInputStream(new File("resources/config.properties")));
		}
		catch (FileNotFoundException fnfe) {
			log.error("config.properties file not found in resources folder : " + fnfe.getMessage());
		}
		catch (IOException ioe) {
			log.error("Exception while reading config.properties file : " + ioe.getMessage());
		}
		
		return props;
	}
	
	
}
