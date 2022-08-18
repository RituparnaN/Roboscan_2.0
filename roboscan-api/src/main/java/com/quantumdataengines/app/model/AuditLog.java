package com.quantumdataengines.app.model;

import java.util.Date;

public class AuditLog {
	private String userCode;
	private String userRole;
	private Date loginTime;
	private String sessionId;
	private String moduleAccessed;
	private String accessType;
	private Date accessTime;
	private String message;
	private String urlAccessed;
	private String queryParam;
	private String terminalId;
	private String terminalName;
	private String terminalAgent;
	private Date logDateTime;
	
	public AuditLog(String userCode, String userRole, Date loginTime, String sessionId, String moduleAccessed, String accessType, Date accessTime,
			String message, String urlAccessed, String queryParam, String terminalId, String terminalName,	String terminalAgent) {
		this.userCode = userCode;
		this.userRole = userRole;
		this.loginTime = loginTime;
		this.sessionId = sessionId;
		this.moduleAccessed = moduleAccessed;
		this.accessType = accessType;
		this.accessTime = accessTime;
		this.message = message;
		this.urlAccessed = urlAccessed;
		this.queryParam = queryParam;
		this.terminalId = terminalId;
		this.terminalName = terminalName;
		this.terminalAgent = terminalAgent;
		this.logDateTime = new Date();
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	public String getModuleAccessed() {
		return moduleAccessed;
	}
	public void setModuleAccessed(String moduleAccessed) {
		this.moduleAccessed = moduleAccessed;
	}
	public String getAccessType() {
		return accessType;
	}
	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}
	public Date getAccessTime() {
		return accessTime;
	}
	public void setAccessTime(Date accessTime) {
		this.accessTime = accessTime;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getQueryParam() {
		return queryParam;
	}
	public void setQueryParam(String queryParam) {
		this.queryParam = queryParam;
	}
	public String getTerminalId() {
		return terminalId;
	}
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}
	public String getTerminalName() {
		return terminalName;
	}
	public void setTerminalName(String terminalName) {
		this.terminalName = terminalName;
	}
	public String getTerminalAgent() {
		return terminalAgent;
	}
	public void setTerminalAgent(String terminalAgent) {
		this.terminalAgent = terminalAgent;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getUrlAccessed() {
		return urlAccessed;
	}
	public void setUrlAccessed(String urlAccessed) {
		this.urlAccessed = urlAccessed;
	}
	public Date getLogDateTime() {
		return logDateTime;
	}
	public void setLogDateTime(Date logDateTime) {
		this.logDateTime = logDateTime;
	}
}
