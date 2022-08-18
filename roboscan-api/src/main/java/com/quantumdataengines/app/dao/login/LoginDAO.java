package com.quantumdataengines.app.dao.login;

import java.util.List;

import com.quantumdataengines.app.model.DomainUser;
import com.quantumdataengines.app.model.User;

public interface LoginDAO {
    public DomainUser getDomainUser(String userName, String token, String isValid);
    public DomainUser getDomainUserByToken(String token);
	public DomainUser getValidDomainUserByTokenAndIpAddress(String token, String ipAddress);
	public User getUserByUsernameAndPassword(String username, String password);
	public User getUserByUsername(String username);
	public DomainUser getValidDomainUserByUsernameAndIpAddress(String username, String ipAddress);
	public void saveDomainUser(String username, String ipAddress, String token,String activeRoleId);
	public List<DomainUser> getDomainUsersList(String username, String isValid);
	public void updateDomainUser(String username, String token);
	public String logout(DomainUser domainUser);
	public List<DomainUser> getAllValidDomainUsers();
	public List<String> setDomainUsersInvalid(List<DomainUser> inactiveDomainUsers);
}
