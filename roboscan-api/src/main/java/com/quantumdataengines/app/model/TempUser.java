package com.quantumdataengines.app.model;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;


public class TempUser {
	
    private String tempId;
    private String username;
    private String userPass;
    private String firstName;
    private String lastName;
    private String emailId;
    private String mobileNo;
    public String getTempId() {
		return tempId;
	}
	public void setTempId(String tempId) {
		this.tempId = tempId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserPass() {
		return userPass;
	}
	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	public String getEmployeeCode() {
		return employeeCode;
	}
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	public String getDepartmentCode() {
		return departmentCode;
	}
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}
	public boolean isAccountEnabled() {
		return accountEnabled;
	}
	public void setAccountEnabled(boolean accountEnabled) {
		this.accountEnabled = accountEnabled;
	}
	public boolean isAccountExpired() {
		return accountExpired;
	}
	public void setAccountExpired(boolean accountExpired) {
		this.accountExpired = accountExpired;
	}
	public boolean isAccountLocked() {
		return accountLocked;
	}
	public void setAccountLocked(boolean accountLocked) {
		this.accountLocked = accountLocked;
	}
	public boolean isAccountDormant() {
		return accountDormant;
	}
	public void setAccountDormant(boolean accountDormant) {
		this.accountDormant = accountDormant;
	}
	public boolean isAccountDeleted() {
		return accountDeleted;
	}
	public void setAccountDeleted(boolean accountDeleted) {
		this.accountDeleted = accountDeleted;
	}
	public boolean isCredentialsExpired() {
		return credentialsExpired;
	}
	public void setCredentialsExpired(boolean credentialsExpired) {
		this.credentialsExpired = credentialsExpired;
	}
	public boolean isEtlUser() {
		return etlUser;
	}
	public void setEtlUser(boolean etlUser) {
		this.etlUser = etlUser;
	}
	public boolean isChatEnable() {
		return chatEnable;
	}
	public void setChatEnable(boolean chatEnable) {
		this.chatEnable = chatEnable;
	}
	public boolean isDbAuthentication() {
		return dbAuthentication;
	}
	public void setDbAuthentication(boolean dbAuthentication) {
		this.dbAuthentication = dbAuthentication;
	}
	public boolean isDbAuthRequired() {
		return dbAuthRequired;
	}
	public void setDbAuthRequired(boolean dbAuthRequired) {
		this.dbAuthRequired = dbAuthRequired;
	}
	public String getAccessStartTime() {
		return accessStartTime;
	}
	public void setAccessStartTime(String accessStartTime) {
		this.accessStartTime = accessStartTime;
	}
	public String getAccessEndTime() {
		return accessEndTime;
	}
	public void setAccessEndTime(String accessEndTime) {
		this.accessEndTime = accessEndTime;
	}
	public LocalDateTime getAccountExipyDate() {
		return accountExipyDate;
	}
	public void setAccountExipyDate(LocalDateTime accountExipyDate) {
		this.accountExipyDate = accountExipyDate;
	}
	public String getAccessPoints() {
		return accessPoints;
	}
	public void setAccessPoints(String accessPoints) {
		this.accessPoints = accessPoints;
	}
	public String getLabelDirection() {
		return labelDirection;
	}
	public void setLabelDirection(String labelDirection) {
		this.labelDirection = labelDirection;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public LocalDateTime getLastUsedDate() {
		return lastUsedDate;
	}
	public void setLastUsedDate(LocalDateTime lastUsedDate) {
		this.lastUsedDate = lastUsedDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public LocalDateTime getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	private String designation;
    private String branchCode;
    private String employeeCode;
    private String departmentCode;
    private boolean accountEnabled;
    private boolean accountExpired;
    private boolean accountLocked;
    private boolean accountDormant;
    private boolean accountDeleted;
    private boolean credentialsExpired;
    private boolean etlUser;
    private boolean chatEnable;
    private boolean dbAuthentication;
    private boolean dbAuthRequired;
    private String accessStartTime;
    private String accessEndTime;
    private LocalDateTime accountExipyDate;
    private String accessPoints;
    private String labelDirection;
    private String language;
    private LocalDateTime lastUsedDate;
    private String createdBy;
    private LocalDateTime createdOn;
    //private List<String> roleList = new ArrayList<>();
    //private String defaultRole;
    //private boolean verified;
    private String status;

}
