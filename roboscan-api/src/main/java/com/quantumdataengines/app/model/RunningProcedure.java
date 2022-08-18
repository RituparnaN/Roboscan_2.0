package com.quantumdataengines.app.model;

public class RunningProcedure {
	private String startDate;
	private String endDate;
	private String status;
	private String statusMessage;
	private String completeTime;
	private ExtractionProcedure procesure;
	
	public RunningProcedure(){}
	
	public RunningProcedure(String startDate, String endDate, String status,
			String statusMessage, String completeTime,	ExtractionProcedure procesure) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
		this.statusMessage = statusMessage;
		this.completeTime = completeTime;
		this.procesure = procesure;
	}
	
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	public String getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
	}
	public ExtractionProcedure getProcesure() {
		return procesure;
	}
	public void setProcesure(ExtractionProcedure procesure) {
		this.procesure = procesure;
	}
	
	
}
