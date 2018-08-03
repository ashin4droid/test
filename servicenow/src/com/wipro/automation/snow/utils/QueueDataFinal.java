package com.wipro.automation.snow.utils;

public class QueueDataFinal
{
	private String queue;
	private String keyword;
	private String assigneeEmail;
	private String assigneeName;
	private String shortDescription;
	
	
	public String getQueue() {
		return queue;
	}
	public String getKeyword() {
		return keyword;
	}
	public String getAssigneeEmail() {
		return assigneeEmail;
	}
	public String getAssigneeName() {
		return assigneeName;
	}
	public void setQueue(String queue) {
		this.queue = queue;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public void setAssigneeEmail(String assigneeEmail) {
		this.assigneeEmail = assigneeEmail;
	}
	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	/*@Override
	public String toString()
	{
		return String.format("[queue=%s, keyword=%s, assigneeEmail=%s, assigneeName=%s, shortDescription=%s]", 
								queue, keyword, assigneeEmail, assigneeName, shortDescription);
	}
	*/
	
}
