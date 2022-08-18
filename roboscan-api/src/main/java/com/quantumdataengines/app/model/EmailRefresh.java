package com.quantumdataengines.app.model;

import lombok.Getter;
import lombok.Setter;


public class EmailRefresh {
	
	private String startTime;
	private String completeTime;
	private String folder;
	private int noOfEmailFound;
	private int noOfComplete;
	private int percentage;
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
	}
	public String getFolder() {
		return folder;
	}
	public void setFolder(String folder) {
		this.folder = folder;
	}
	public int getNoOfEmailFound() {
		return noOfEmailFound;
	}
	public void setNoOfEmailFound(int noOfEmailFound) {
		this.noOfEmailFound = noOfEmailFound;
	}
	public int getNoOfComplete() {
		return noOfComplete;
	}
	public void setNoOfComplete(int noOfComplete) {
		this.noOfComplete = noOfComplete;
	}
	public int getPercentage() {
		return percentage;
	}
	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	private int status;
	private String statusMessage;

}
