package com.wipro.automation.snow.pages;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.openqa.selenium.By;


public class NonAdminPage
{
	private static Properties props = new Properties();
	
	static {
		loadConfig();
	}
	
	public static final String NON_ADMIN_URL= props.getProperty(NonAdminPageConstants.NON_ADMIN_URL);
	public static final String USERNAME		= props.getProperty(NonAdminPageConstants.USERNAME);
	public static final String PASSWORD		= props.getProperty(NonAdminPageConstants.PASSWORD);
	
	
	public static class Locators
	{
		public static final By USERNAME		= By.xpath(props.getProperty(NonAdminPageConstants.INPUT_FIELD_USERNAME));
		public static final By PASSWORD		= By.xpath(props.getProperty(NonAdminPageConstants.INPUT_FIELD_PASSWORD));
		public static final By LOGIN_BTN	= By.xpath(props.getProperty(NonAdminPageConstants.BUTTON_FIELD_LOGIN));
		
		
		public static final By USER_HEADER	= By.xpath(props.getProperty(NonAdminPageConstants.USER_HEADER));
	}
	
	
	private static void loadConfig()
	{
		try(InputStream nonadminprops = new FileInputStream(PageConfigFiles.NON_ADMIN_CONFIG_FILE);)
		{
			props.load(nonadminprops);
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
	}
}
class NonAdminPageConstants
{
	public static final String NON_ADMIN_URL	= "nonadmin.url";
	public static final String USERNAME			= "nonadmin.username";
	public static final String PASSWORD			= "nonadmin.password";
	
	
	public static final String INPUT_FIELD_USERNAME	= "loginpage.fieldusername";
	public static final String INPUT_FIELD_PASSWORD	= "loginpage.fieldpassword";
	public static final String BUTTON_FIELD_LOGIN	= "loginpage.buttonlogin";
	
	public static final String USER_HEADER	= "homepage.userheader";
	
	
}