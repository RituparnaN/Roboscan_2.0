package com.quantumdataengines.app.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;


public class User implements UserDetails {
	
	private String username;
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
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public boolean isAccountExpired() {
		return accountExpired;
	}
	public void setAccountExpired(boolean accountExpired) {
		this.accountExpired = accountExpired;
	}
	public boolean isAccountEnabled() {
		return accountEnabled;
	}
	public void setAccountEnabled(boolean accountEnabled) {
		this.accountEnabled = accountEnabled;
	}
	public boolean isCredentialsExpired() {
		return credentialsExpired;
	}
	public void setCredentialsExpired(boolean credentialsExpired) {
		this.credentialsExpired = credentialsExpired;
	}
	public boolean isAccountLocked() {
		return accountLocked;
	}
	public void setAccountLocked(boolean accountLocked) {
		this.accountLocked = accountLocked;
	}
	public boolean isChatEnable() {
		return chatEnable;
	}
	public void setChatEnable(boolean chatEnable) {
		this.chatEnable = chatEnable;
	}
	public LocalDateTime getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getDefaultRole() {
		return defaultRole;
	}
	public void setDefaultRole(String defaultRole) {
		this.defaultRole = defaultRole;
	}
	public List<String> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<String> roleList) {
		this.roleList = roleList;
	}
	public String getAccessPoints() {
		return accessPoints;
	}
	public void setAccessPoints(String accessPoints) {
		this.accessPoints = accessPoints;
	}
	public LocalDateTime getAccountExpiryDate() {
		return accountExpiryDate;
	}
	public void setAccountExpiryDate(LocalDateTime accountExpiryDate) {
		this.accountExpiryDate = accountExpiryDate;
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
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	public boolean isEtlUser() {
		return etlUser;
	}
	public void setEtlUser(boolean etlUser) {
		this.etlUser = etlUser;
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
	public boolean isAccountDeleted() {
		return accountDeleted;
	}
	public void setAccountDeleted(boolean accountDeleted) {
		this.accountDeleted = accountDeleted;
	}
	public boolean isAccountDormant() {
		return accountDormant;
	}
	public void setAccountDormant(boolean accountDormant) {
		this.accountDormant = accountDormant;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	private String password;
	private String firstName;
	private String lastName;
	private String emailId;
	private String mobileNo;
	private String labelDirection;
	private String language;
	private String designation;
	private boolean accountExpired;
	private boolean accountEnabled;
	private boolean credentialsExpired;
	private boolean accountLocked;
	private boolean chatEnable;
	private LocalDateTime createdOn;
	private String createdBy;
	private String defaultRole;
	private List<String> roleList = new ArrayList<>();
	private String accessPoints;
	private LocalDateTime accountExpiryDate;
	private boolean dbAuthentication;
	private boolean dbAuthRequired;
	private String branchCode;
	private boolean etlUser;
	private String employeeCode;
	private String departmentCode;
	private boolean accountDeleted;
	private boolean accountDormant;
	private String accessStartTime;
	private String accessEndTime;
	private LocalDateTime accountExipyDate;
	private String status;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		roleList = roleList != null ? roleList : new ArrayList<>();
		return roleList.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
	}
	@Override
	public boolean isAccountNonExpired() {
		return !this.accountExpired;
	}
	@Override
	public boolean isAccountNonLocked() {
		return !this.accountLocked;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return !this.credentialsExpired;
	}
	@Override
	public boolean isEnabled() {
		return this.accountEnabled;
	}
	@Override
	public String getPassword() {
		return this.password;
	}
	@Override
	public String getUsername() {
		return this.username;
	}

}
