package com.quantumdataengines.app.dao.login;

import com.quantumdataengines.app.model.DomainUser;
import com.quantumdataengines.app.model.User;
import com.quantumdataengines.app.util.CommonUtil;
import com.quantumdataengines.app.util.ConnectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class LoginDAOImpl implements LoginDAO {

    @Autowired
    private ConnectionUtil connectionUtil;
    
    @Autowired
    private CommonUtil commonUtil;

    @Override
    public DomainUser getDomainUser(String userName, String token, String isValid) {
        DomainUser domainUser = null;
        try(Connection connection = connectionUtil.getConnection()) {
        	PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            String sql = " SELECT USERNAME, ACTIVEROLE, AUTHTOKEN, IPADDRESS, LASTUPDATED "+
                         "   FROM TB_DOMAINUSER"+
                         "  WHERE USERNAME = ? "+
                         "    AND ISVALID = ? "+
                         "    AND TOKEN = ? ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, isValid);
            preparedStatement.setString(3,token);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                domainUser = new DomainUser();
                domainUser.setUsername(resultSet.getString("USERNAME"));
                domainUser.setActiveRole(resultSet.getString("ACTIVEROLE"));
                domainUser.setToken(resultSet.getString("AUTHTOKEN"));
                domainUser.setIpAddress(resultSet.getString("IPADDRESS"));
                domainUser.setLastUpdated(resultSet.getString("LASTUPDATED"));
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return domainUser;
    }
    
    @Override
	public DomainUser getDomainUserByToken(String token) { 
		DomainUser domainUser = null;
		try(Connection connection = connectionUtil.getConnection()) {
			PreparedStatement preparedStatement = null; 
			ResultSet resultSet = null;
			String sql = 	" SELECT * FROM TB_DOMAINUSER"+ 
							" WHERE AUTHTOKEN = ? ";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, token);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				domainUser = new DomainUser();
				domainUser.setLoginCode(resultSet.getString("LOGINCODE"));
				domainUser.setUsername(resultSet.getString("USERNAME"));
				domainUser.setToken(resultSet.getString("AUTHTOKEN"));
				domainUser.setIsValid(resultSet.getString("ISVALID"));
				domainUser.setActiveRole(resultSet.getString("ACTIVEROLE"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return domainUser;
	}
	
	@Override
	public DomainUser getValidDomainUserByTokenAndIpAddress(String token, String ipAddress) {
		DomainUser domainUser = null;
		try(Connection connection = connectionUtil.getConnection()) {
			PreparedStatement preparedStatement = null; 
			ResultSet resultSet = null;
			String isValid = "Y";
			String sql = 	" SELECT * FROM TB_DOMAINUSER"+ 
							" WHERE AUTHTOKEN = ? AND IPADDRESS = ? AND ISVALID = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, token);
			preparedStatement.setString(2, ipAddress);
			preparedStatement.setString(3, isValid);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				domainUser = new DomainUser();
				domainUser.setLoginCode(resultSet.getString("LOGINCODE"));
				domainUser.setUsername(resultSet.getString("USERNAME"));
				domainUser.setToken(resultSet.getString("AUTHTOKEN"));
				domainUser.setIsValid(resultSet.getString("ISVALID"));
				domainUser.setActiveRole(resultSet.getString("ACTIVEROLE"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return domainUser;
	}

	@Override
	public User getUserByUsernameAndPassword(String username, String password) {
		User user = null;
		try(Connection connection = connectionUtil.getConnection()) {
			PreparedStatement preparedStatement = null; 
			ResultSet resultSet = null;
			/*String sql = 	" SELECT * FROM TB_USER "+
							" WHERE USERNAME = ? AND USERPASS = ? ";*/
			String sql = " SELECT A.USERNAME, B.ROLEID AS DEFAULTROLE FROM " +
						 "	TB_USER A INNER JOIN TB_USERROLEMAPPING B ON A.USERNAME = B.USERCODE"+
						 " WHERE A.USERNAME = ? AND A.USERPASS = ? AND B.LOGINPRIORITY = ? ";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			preparedStatement.setInt(3,1);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				user = new User();
				user.setUsername(resultSet.getString("USERNAME"));
				user.setDefaultRole(resultSet.getString("DEFAULTROLE"));
			}
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		return user;
	}
	
	@Override
	public User getUserByUsername(String username) {
		User user = null;
		try(Connection connection = connectionUtil.getConnection()) {
			PreparedStatement preparedStatement = null; 
			ResultSet resultSet = null;
			String sql = " SELECT A.USERNAME, A.USERPASS, A.FIRSTNAME, A.LASTNAME,"+ 
						 " 		  A.MOBILENO, A.LABELDIRECTION, A.LANGUAGE, A.DESIGNATION,"+ 
						 " 		  A.EMAILID, A.ACCOUNTENABLED, A.ACCOUNTEXPIRED, A.ACCOUNTLOCKED,"+ 
						 " 		  A.CREDENTIALSEXPIRED, B.ROLEID AS DEFAULTROLE " +
					 	 " FROM	COGNIFI.TB_USER A INNER JOIN COGNIFI.TB_USERROLEMAPPING B ON A.USERNAME = B.USERCODE"+
					 	 " WHERE A.USERNAME = ? AND B.LOGINPRIORITY = ? ";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, username);
			preparedStatement.setInt(2,1);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				user = new User();
				user.setUsername(resultSet.getString("USERNAME"));
				user.setPassword(resultSet.getString("USERPASS"));
				user.setFirstName(resultSet.getString("FIRSTNAME"));
				user.setLastName(resultSet.getString("LASTNAME"));
				user.setMobileNo(resultSet.getString("MOBILENO"));
				user.setLabelDirection(resultSet.getString("LABELDIRECTION"));
				user.setLanguage((resultSet.getString("LANGUAGE")));
				user.setDesignation(resultSet.getString("DESIGNATION"));
				user.setEmailId(resultSet.getString("EMAILID"));
				user.setDefaultRole(resultSet.getString("DEFAULTROLE"));
				user.setAccountEnabled(commonUtil.stringToBoolean(resultSet.getString("ACCOUNTENABLED")));
				user.setAccountExpired(commonUtil.stringToBoolean(resultSet.getString("ACCOUNTEXPIRED")));
				user.setAccountLocked(commonUtil.stringToBoolean(resultSet.getString("ACCOUNTLOCKED")));
				user.setCredentialsExpired(commonUtil.stringToBoolean(resultSet.getString("CREDENTIALSEXPIRED")));
			}
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		return user;
	}
	
	@Override
	public DomainUser getValidDomainUserByUsernameAndIpAddress(String username, String ipAddress) {
		DomainUser domainUser = null;
		System.out.println("IP Address: "+ipAddress);
		try(Connection connection = connectionUtil.getConnection()) {
			PreparedStatement preparedStatement = null; 
			ResultSet resultSet = null;
			String sql = 	" SELECT USERNAME, AUTHTOKEN FROM TB_DOMAINUSER"+ 
							" WHERE USERNAME = ? AND IPADDRESS = ? AND ISVALID = ? ";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, ipAddress);
			preparedStatement.setString(3, "Y");
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				domainUser = new DomainUser();
				domainUser.setUsername(resultSet.getString("USERNAME"));
				domainUser.setToken(resultSet.getString("AUTHTOKEN"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return domainUser;
	}

	@Override
	public void saveDomainUser(String username, String ipAddress, String token,String activeRoleId) {
		try(Connection connection = connectionUtil.getConnection()) {
			PreparedStatement preparedStatement = null;
			String sql = 	"	INSERT INTO TB_DOMAINUSER "+
							"	( LOGINCODE, LOGINTIME, LOGOUTTIME, LASTUPDATED, IPADDRESS, ACCESSTOKEN, "+
							"	  ISVALID, ACTIVEROLE, USERNAME ) "+
						  
							"	VALUES ( ?, SYSTIMESTAMP, ?, SYSTIMESTAMP, ?, ?, "+
							"			 ?, ?, ? ) ";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, username+"_"+ipAddress);
			preparedStatement.setString(2, "");
			preparedStatement.setString(3, ipAddress);
			preparedStatement.setString(4, token);
			preparedStatement.setString(5, "Y");
			preparedStatement.setString(6, activeRoleId);
			preparedStatement.setString(7, username);
			preparedStatement.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<DomainUser> getDomainUsersList(String username, String isValid) {
		List<DomainUser> domainUsersList = new ArrayList<DomainUser>();
		try(Connection connection = connectionUtil.getConnection()) {
			PreparedStatement preparedStatement = null; 
			ResultSet resultSet = null;
			String sql = "SELECT LOGINCODE, LOGINTIME, LOGOUTTIME, LASTUPDATED, IPADDRESS, TOKEN, "+
						 "		 DOMAIN, ISVALID, ACTIVEROLE, USERNAME "+
						 " 	FROM TB_DOMAINUSER"+ 
						 " WHERE USERNAME = ? AND ISVALID = ? ORDER BY LASTUPDATED DESC";
			
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, isValid);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				DomainUser domainUser = new DomainUser();
				domainUser.setUsername(resultSet.getString("USERNAME"));
				domainUser.setToken(resultSet.getString("TOKEN"));
				domainUser.setLastUpdated(resultSet.getString("LASTUPDATED"));
				domainUser.setActiveRole(resultSet.getString("ACTIVEROLE"));
				domainUser.setIsValid(resultSet.getString("ISVALID"));
				domainUser.setIpAddress(resultSet.getString("IPADDRESS"));
				domainUser.setLoginCode(resultSet.getString("LOGINCODE"));
				domainUser.setLoginTime(resultSet.getString("LOGINTIME"));
				domainUser.setLogoutTime(resultSet.getString("LOGOUTTIME"));
				
				domainUsersList.add(domainUser);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return domainUsersList;
	}

	@Override
	public void updateDomainUser(String username, String token) {
		try(Connection connection = connectionUtil.getConnection()) {
			PreparedStatement preparedStatement = null;
			String sql = 	"	UPDATE TB_DOMAINUSER SET"+
							"	LASTUPDATED = SYSTIMESTAMP "+
							"	WHERE USERNAME = ? AND AUTHTOKEN = ? ";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, token);
			preparedStatement.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String logout(DomainUser domainUser) {
		String response = "";
		try(Connection connection = connectionUtil.getConnection()) {
			PreparedStatement preparedStatement = null;
			String sql = 	"	UPDATE TB_DOMAINUSER SET"+
							"	LOGOUTTIME = SYSTIMESTAMP, LASTUPDATED = SYSTIMESTAMP, ISVALID = ? "+
							"	WHERE USERNAME = ? AND AUTHTOKEN = ? ";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, "N");
			preparedStatement.setString(2, domainUser.getUsername());
			preparedStatement.setString(3, domainUser.getToken());
			preparedStatement.executeUpdate();
			response = "Domain user logged out successfully";
		}catch(Exception e) {
			response = "Error while logging out domain user";
			e.printStackTrace();
		}
		return response;
	}
	
	@Override
	public List<DomainUser> getAllValidDomainUsers() {
		List<DomainUser> domainUsersList = new ArrayList<DomainUser>();
		try(Connection connection = connectionUtil.getConnection()) {
			PreparedStatement preparedStatement = null; 
			ResultSet resultSet = null;
			String sql = 	" SELECT USERNAME, LOGINCODE, AUTHTOKEN, FUN_DATETIMETOCHAR(LASTUPDATED) LASTUPDATED, IPADDRESS "+
							"   FROM TB_DOMAINUSER"+ 
							"  WHERE ISVALID = ? ";
			
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, "Y");
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				DomainUser domainUser = new DomainUser();
				domainUser.setUsername(resultSet.getString("USERNAME"));
				domainUser.setLoginCode(resultSet.getString("LOGINCODE"));
				domainUser.setToken(resultSet.getString("AUTHTOKEN"));
				domainUser.setLastUpdated(resultSet.getString("LASTUPDATED"));
				domainUser.setIpAddress(resultSet.getString("IPADDRESS"));
				domainUsersList.add(domainUser);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return domainUsersList;
	}
	
	@Override
	public List<String> setDomainUsersInvalid(List<DomainUser> domainUsersList) {
		
		List<String> usernames = domainUsersList.stream().map(
			domainUser -> domainUser.getUsername()
		).collect(Collectors.toList());
		
		List<String> tokens = domainUsersList.stream().map(
				domainUser -> domainUser.getToken()
		).collect(Collectors.toList());
		
		try(Connection connection = connectionUtil.getConnection()) {
			PreparedStatement preparedStatement = null;
			String sql = 	"	UPDATE TB_DOMAINUSER SET "+
							"	ISVALID = ?, LOGOUTTIME = SYSTIMESTAMP "+
							"	WHERE AUTHTOKEN = ? ";
			preparedStatement = connection.prepareStatement(sql);
			for(String token: tokens) {
				preparedStatement.setString(1, "N");
				preparedStatement.setString(2, token);
				preparedStatement.addBatch();
				//System.out.println(token);
			}
			preparedStatement.executeBatch();
		}catch(Exception e) {
			e.printStackTrace();
		}
		//System.out.println("VIVEK - "+usernames);
		return usernames;
	}
}
