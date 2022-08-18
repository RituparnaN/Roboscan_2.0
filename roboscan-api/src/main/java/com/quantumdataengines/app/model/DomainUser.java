package com.quantumdataengines.app.model;

import lombok.Getter;
import lombok.Setter;

public class DomainUser {
	
	private String loginCode;
	private String loginTime;
	private String logoutTime;
	private String lastUpdated;
	private String ipAddress;
	private String token;
	public String getLoginCode() {
		return loginCode;
	}
	public void setLoginCode(String loginCode) {
		this.loginCode = loginCode;
	}
	public String getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}
	public String getLogoutTime() {
		return logoutTime;
	}
	public void setLogoutTime(String logoutTime) {
		this.logoutTime = logoutTime;
	}
	public String getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getIsValid() {
		return isValid;
	}
	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}
	public String getActiveRole() {
		return activeRole;
	}
	public void setActiveRole(String activeRole) {
		this.activeRole = activeRole;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	private String isValid;
	private String activeRole;
	private String username;
	@Override
	public String toString() {
		return "DomainUser [loginCode=" + loginCode + ", loginTime=" + loginTime + ", logoutTime=" + logoutTime
				+ ", lastUpdated=" + lastUpdated + ", ipAddress=" + ipAddress + ", token=" + token + ", isValid="
				+ isValid + ", activeRole=" + activeRole + ", username=" + username + "]";
	}
	
	

}
