package com.wipro.automation.snow.pages;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.openqa.selenium.By;


public class AdminPage
{
	private static Properties props = new Properties();
	
	static {
		loadConfig();
	}
	
	public static final String ADMIN_URL	= props.getProperty(AdminPageConstants.ADMIN_URL);
	public static final String USERNAME		= props.getProperty(AdminPageConstants.ADMIN_USERNAME);
	public static final String PASSWORD		= props.getProperty(AdminPageConstants.ADMIN_PASSWORD);
	
	
	public static class Locators
	{
		public static final By USERNAME		= By.xpath(props.getProperty(AdminPageConstants.INPUT_FIELD_USERNAME));
		public static final By PASSWORD		= By.xpath(props.getProperty(AdminPageConstants.INPUT_FIELD_PASSWORD));
		public static final By LOGIN_BTN	= By.xpath(props.getProperty(AdminPageConstants.BUTTON_FIELD_LOGIN));
		
		
		public static final By ADMIN_ELEMENT= By.xpath(props.getProperty(AdminPageConstants.ADMIN_ELEMENT));
	}
	
	
	private static void loadConfig()
	{
		try(InputStream nonadminprops = new FileInputStream(PageConfigFiles.ADMIN_CONFIG_FILE);)
		{
			props.load(nonadminprops);
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
	}
}
class AdminPageConstants
{
	public static final String ADMIN_URL		= "admin.url";
	public static final String ADMIN_USERNAME	= "admin.username";
	public static final String ADMIN_PASSWORD	= "admin.password";
	
	
	public static final String INPUT_FIELD_USERNAME	= "loginpage.fieldusername";
	public static final String INPUT_FIELD_PASSWORD	= "loginpage.fieldpassword";
	public static final String BUTTON_FIELD_LOGIN	= "loginpage.buttonlogin";
	
	public static final String ADMIN_ELEMENT	= "homepage.adminelement";
	
}