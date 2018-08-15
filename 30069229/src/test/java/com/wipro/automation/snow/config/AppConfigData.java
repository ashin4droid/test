package com.wipro.automation.snow.config;

public class AppConfigData
{
	private String defaultassignee;
	private String username;
	private String password;
	private String devusername;
	private String devpassword;
	
	
	public String getDevusername() {
		return devusername;
	}
	public String getDevpassword() {
		return devpassword;
	}
	public void setDevusername(String devusername) {
		this.devusername = devusername;
	}
	public void setDevpassword(String devpassword) {
		this.devpassword = devpassword;
	}
	public String getDefaultassignee() {
		return defaultassignee;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public void setDefaultassignee(String defaultassignee) {
		this.defaultassignee = defaultassignee;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
