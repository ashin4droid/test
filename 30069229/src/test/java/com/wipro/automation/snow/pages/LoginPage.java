package com.wipro.automation.snow.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.wipro.automation.snow.logging.LogService;

public class LoginPage
{
	WebDriver driver;
	//private Logger log = LogService.getLogger(this.getClass());
	
	@FindBy(id="userNameInput")
	WebElement username;
	
	@FindBy(id="passwordInput")
	WebElement password;
	
	@FindBy(id="submitButton")
	WebElement signinButton;
	
	
	public LoginPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
		//log.info("LoginPage elements initiated by Page Factory");
	}
	
	
	public void setUsername(String userid)
	{
		username.sendKeys(userid);
	}
	
	public void setPassword(String passwrd)
	{
		password.sendKeys(passwrd);
	}
	
	public void clickSignInButton()
	{
		signinButton.click();
	}
	public void decryptAndSetPassword(String encryptedPassword) throws Exception
	{
		password.sendKeys(encryptedPassword);
	}
	
	
	public void login(String userid, String passwrd)
	{
		setUsername(userid);
		setPassword(passwrd);
		clickSignInButton();
	}
	
	
}
