package com.wipro.automation.snow.pages;

import org.openqa.selenium.By;

public class GLSAPPage
{
	public static By ticketNumber	= By.id("sys_readonly.incident.number");
	public static By shortDesc		= By.id("incident.short_description");
	public static By affectedUser	= By.id("sys_display.incident.u_affected_user");
	public static By assignmentGroup= By.id("sys_display.incident.assignment_group");
	public static By incidentState	= By.id("incident.state");
	public static By assignedTo		= By.id("sys_display.incident.assigned_to");
	public static By configItem		= By.id("sys_display.incident.cmdb_ci");
	public static By saveAndExit	= By.xpath("//span[@class='navbar_ui_actions']/button[text()='Save & Exit']");
	public static By back			= By.xpath("//button[@data-original-title='Back']");
	
	public static By affectedLocationBtn		= By.id("incident.u_affected_location_unlock");
	public static By affectedLocationTxtBox		= By.id("sys_display.incident.u_affected_location");
	public static By affectedLocationSelection	= By.xpath("//div[@id='AC.incident.u_affected_location']/div/span[text()='GLOBAL']");
	
	public static By closureInfoTab			= By.xpath("//div[@id='tabs2_section']//span[text()='Closure Information']");
	public static By closureInfoCloseCode	= By.id("incident.close_code");
	public static By closureInfoCloseNotes	= By.id("incident.close_notes");
	
	public static By tasksHeader = By.xpath("//h1[text()='Tasks']");
	
}
