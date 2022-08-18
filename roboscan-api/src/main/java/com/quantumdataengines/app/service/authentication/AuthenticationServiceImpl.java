package com.quantumdataengines.app.service.authentication;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.quantumdataengines.app.dao.CommonDAO;
import com.quantumdataengines.app.dao.login.LoginDAO;
import com.quantumdataengines.app.dao.usermanagement.UserManagementDAO;
import com.quantumdataengines.app.exception.LoginException;
import com.quantumdataengines.app.model.DomainUser;
import com.quantumdataengines.app.model.User;
import com.quantumdataengines.app.util.CommonUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{
	private static final Logger LOGGER = Logger.getLogger(AuthenticationServiceImpl.class.getName());
	
	@Autowired
	private UserManagementDAO userManagementDAO;
	
	@Autowired
	private LoginDAO loginDAO;
	
	@Autowired
	private CommonDAO commonDAO;
	
	@Autowired
	private CommonUtil commonUtil;

	@Override
	public boolean isAlived(String token) {
		DomainUser domainUser = loginDAO.getDomainUserByToken(token);
		return commonUtil.stringToBoolean(domainUser.getIsValid());
	}

	@Override
	public Optional <UserDetails> isAuthenticated(String token) {
		DomainUser domainUser = loginDAO.getDomainUserByToken(token);
		User user = loginDAO.getUserByUsername(domainUser.getUsername());
		String isValid = domainUser.getIsValid();
		//System.out.println(isValid);
		if ("Y".equals(isValid)) {
			loginDAO.updateDomainUser(domainUser.getUsername(), token);
			return Optional.ofNullable(user);
		} else {
			throw new LoginException("Invalid login");
		}
	}

	@Override
	public User validateToken(String token, String ipAddress) {
		User user = null;
		DomainUser domainUser = loginDAO.getValidDomainUserByTokenAndIpAddress(token, ipAddress);
		if(domainUser != null) {
			user = loginDAO.getUserByUsername(domainUser.getUsername());
		}
		return user;
	}

	@Override
	public String authenticate(String username, String password, String ipAddress) {
		String decryptedPassword = "";
		System.out.println("Password: "+password);
		System.out.println("decoded password: "+Base64.getDecoder().decode(password));
		try {
			decryptedPassword = CommonUtil.decrypt(Base64.getDecoder().decode(password));
			System.out.println(decryptedPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		User user = loginDAO.getUserByUsername(username);
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		boolean passwordMatched = bCryptPasswordEncoder.matches(decryptedPassword, user.getPassword());
		System.out.println("Password Matched = "+passwordMatched);
		
		DomainUser domainUser = loginDAO.getValidDomainUserByUsernameAndIpAddress
				(user == null ? "" : user.getUsername(), ipAddress);
		if(domainUser != null) {
			return domainUser.getToken();
		}
	    System.out.println("User");
	    System.out.println("User: "+user.getUsername());
	    System.out.println("Password: "+user.getPassword());
	    System.out.println("User"+user.getDefaultRole());

		if (user != null) {
			String token = commonUtil.generateToken();
			LOGGER.log(Level.FINE,"default role assigned while login = "+user.getDefaultRole());
			loginDAO.saveDomainUser(username, ipAddress, token, user.getDefaultRole());
			return token;
		} else {
			throw new LoginException("Login Failed");
		}
	}
	
	@Override
	public String logout(DomainUser domainUser) {
		return loginDAO.logout(domainUser);
	}
	
	public void autoLogout() {
		LocalDateTime localDateTime = LocalDateTime.now(); 
		List<DomainUser> domainUserList = loginDAO.getAllValidDomainUsers();
		List<DomainUser> inactiveDomainUsers = domainUserList.stream().filter(domainUser -> {
			long seconds = ChronoUnit.SECONDS.between(commonUtil.convertStringToLocalDateTime(domainUser.getLastUpdated()), localDateTime);
			//System.out.println("VIVEK - "+domainUser.getLoginCode()+" --- "+seconds);
			//return seconds > 900;	//15 minutes
			return seconds > commonDAO.getSystemTimeoutSeconds();
		}).collect(Collectors.toList());
		
		List<String> inValidatedDomainUsers = loginDAO.setDomainUsersInvalid(inactiveDomainUsers);
		LOGGER.log(Level.FINE,"Inactive Domain Users: {}", inValidatedDomainUsers);
	}

}