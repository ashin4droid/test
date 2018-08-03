package com.wipro.automation.snow.pages;

import org.openqa.selenium.By;

public class HomePage
{
	public static By allAppsTab		= By.id("allApps_tab");
	public static By favouritesTab	= By.id("favorites_tab");
	public static By historyTab		= By.id("history_tab");
	
	
	
	public static class Favourites
	{
		public static By incidentsLink		= By.xpath("//div[@id='gsft_nav']/div/magellan-favorites-list/div[2]/div/div/a[child::div/span[text()='Incidents']]");
		public static By incidentsHeader	= By.xpath("//h1[text()='Incidents']");
		public static By glsapQueue			= By.xpath("//a[child::div/span[text()='GL_SAP']]");
	}
	
	public static class AllApps
	{
		public static By homePageLink	= By.xpath("//a[child::div/div[text()='Homepage']]");
		//public static By incMapArea		= By.xpath("//map/area[@shape='rect'][2]");
		public static By incMapArea		= By.xpath("//map/area[@shape='rect'][contains(@data-original-title,'empty')]");
		public static By incidentsHeader = By.xpath("//h1[text()='Incidents']");
		
		
		
		public static class SelfService
		{
			public static By myWork	= By.xpath("//div[@id='gsft_nav']/div/concourse-application-tree/ul/li/a[child::span[text()='Self-Service']]/following-sibling::ul/li//a[child::div/div[text()='My Work']]");
			//public static By incidentsHeader = By.xpath("//h1[text()='Incidents']");
		}
		
	}
	
}
