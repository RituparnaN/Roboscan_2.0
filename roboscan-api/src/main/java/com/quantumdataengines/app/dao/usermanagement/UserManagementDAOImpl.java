package com.quantumdataengines.app.dao.usermanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.quantumdataengines.app.config.checkermanagement.MakerCheckerStatus;
import com.quantumdataengines.app.exception.InvalidOperationException;
import com.quantumdataengines.app.model.TempUser;
import com.quantumdataengines.app.util.CommonDBFunctionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quantumdataengines.app.model.DomainUser;
import com.quantumdataengines.app.model.User;
import com.quantumdataengines.app.util.CommonUtil;
import com.quantumdataengines.app.util.ConnectionUtil;

@Component
public class UserManagementDAOImpl implements UserManagementDAO{
	private static final Logger LOGGER = Logger.getLogger(UserManagementDAOImpl.class.getName());
	
	@Autowired
	private CommonUtil commonUtil;
	@Autowired
	private CommonDBFunctionUtil commonDBFunctionUtil;
	
	@Autowired
	private ConnectionUtil connectionUtil;

	@Override
	public User getUserByUsername(String username) {
		User user = null;
		try(Connection connection = connectionUtil.getConnection()) {
			PreparedStatement preparedStatement = null; 
			ResultSet resultSet = null;
			String sql = 	" SELECT * FROM TB_USER "+ 
							" WHERE USERNAME = ? ";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, username);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
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
				//user.setDefaultRole(resultSet.getString("DEFAULTROLE"));
				user.setAccountEnabled(commonUtil.stringToBoolean(resultSet.getString("ACCOUNTENABLED")));
				user.setAccountExpired(commonUtil.stringToBoolean(resultSet.getString("ACCOUNTEXPIRED")));
				user.setAccountLocked(commonUtil.stringToBoolean(resultSet.getString("ACCOUNTLOCKED")));
				user.setCredentialsExpired(commonUtil.stringToBoolean(resultSet.getString("CREDENTIALSEXPIRED")));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public List<User> getVerifiedUserList() {
		List<User> verifiedUserList = new ArrayList<User>();
		try(Connection connection = connectionUtil.getConnection()) {
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			String sql = " SELECT * FROM TB_USER ";
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				User user = new User();
				user.setUsername(resultSet.getString("USERNAME"));
				user.setFirstName(resultSet.getString("FIRSTNAME"));
				user.setLastName(resultSet.getString("LASTNAME"));
				user.setEmailId(resultSet.getString("EMAILID"));
				user.setMobileNo(resultSet.getString("MOBILENO"));
				user.setDesignation(resultSet.getString("DESIGNATION"));
				user.setBranchCode(resultSet.getString("BRANCHCODE"));
				user.setEmployeeCode(resultSet.getString("EMPLOYEECODE"));
				user.setDepartmentCode(resultSet.getString("DEPARTMENTCODE"));
				user.setAccountEnabled(commonUtil.stringToBoolean(resultSet.getString("ACCOUNTENABLED")));
				user.setAccountExpired(commonUtil.stringToBoolean(resultSet.getString("ACCOUNTEXPIRED")));
				user.setAccountLocked(commonUtil.stringToBoolean(resultSet.getString("ACCOUNTLOCKED")));
				user.setAccountDormant(commonUtil.stringToBoolean(resultSet.getString("ACCOUNTDORMANT")));
				user.setAccountDeleted(commonUtil.stringToBoolean(resultSet.getString("ACCOUNTDELETED")));
				user.setCredentialsExpired(commonUtil.stringToBoolean(resultSet.getString("CREDENTIALSEXPIRED")));
				user.setEtlUser(commonUtil.stringToBoolean(resultSet.getString("ISETLUSER")));
				user.setChatEnable(commonUtil.stringToBoolean(resultSet.getString("CHATENABLE")));
				user.setDbAuthentication(commonUtil.stringToBoolean(resultSet.getString("ISDBAUTHENTICATION")));
				user.setDbAuthRequired(commonUtil.stringToBoolean(resultSet.getString("ISDBAUTHREQUIRED")));
				user.setAccessStartTime(resultSet.getString("ACCESSSTARTTIME"));
				user.setAccessEndTime(resultSet.getString("ACCESSENDTIME"));
				user.setAccountExipyDate(
						resultSet.getTimestamp("ACCOUNTEXIPYDATE") == null ?
						null : resultSet.getTimestamp("ACCOUNTEXIPYDATE").toLocalDateTime()
				);
				user.setAccessPoints(resultSet.getString("ACCESSPOINTS"));
				user.setLabelDirection(resultSet.getString("LABELDIRECTION"));
				user.setLanguage(resultSet.getString("LANGUAGE"));
				user.setCreatedOn(
						resultSet.getTimestamp("CREATEDON") == null ?
						null : resultSet.getTimestamp("CREATEDON").toLocalDateTime()
				);
				user.setStatus(MakerCheckerStatus.APPROVED.getStatus());
				verifiedUserList.add(user);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return verifiedUserList;
	}

	@Override
	public TempUser createTempUser(TempUser userDetails) {
		try(Connection connection = connectionUtil.getConnection()) {

      		String query = " INSERT INTO TB_TEMPUSER (TEMPID,USERNAME,USERPASS,FIRSTNAME,LASTNAME,EMAILID,MOBILENO," +
					       " DESIGNATION, BRANCHCODE, EMPLOYEECODE,DEPARTMENTCODE,ACCOUNTENABLED,ACCOUNTEXPIRED,ACCOUNTLOCKED," +
					       " ACCOUNTDORMANT,ACCOUNTDELETED,CREDENTIALSEXPIRED, ISETLUSER,CHATENABLE,ISDBAUTHENTICATION,ISDBAUTHREQUIRED," +
					       " ACCESSSTARTTIME, ACCESSENDTIME,ACCOUNTEXIPYDATE,ACCESSPOINTS,LABELDIRECTION,LANGUAGE,CREATEDON," +
					       " CREATEDBY,STATUS) VALUES (?,?,?,?,?,?,?,  ?,?,?,?,?,?,? ,?,?,?,?,?,?,?, " +
					       " ?,?,?,?,?,?, SYSTIMESTAMP, ?,? )";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1,userDetails.getTempId());
			preparedStatement.setString(2,userDetails.getUsername());
			preparedStatement.setString(3,userDetails.getUserPass());
			preparedStatement.setString(4,userDetails.getFirstName());
			preparedStatement.setString(5,userDetails.getLastName());
			preparedStatement.setString(6,userDetails.getEmailId());
			preparedStatement.setString(7,userDetails.getMobileNo());

			preparedStatement.setString(8,userDetails.getDesignation());
			preparedStatement.setString(9,userDetails.getBranchCode());
			preparedStatement.setString(10,userDetails.getEmployeeCode());
			preparedStatement.setString(11,userDetails.getDepartmentCode());
			preparedStatement.setString(12,commonUtil.booleanToString(userDetails.isAccountEnabled()));
			preparedStatement.setString(13,commonUtil.booleanToString(userDetails.isAccountExpired()));
			preparedStatement.setString(14,commonUtil.booleanToString(userDetails.isAccountLocked()));

			preparedStatement.setString(15,commonUtil.booleanToString(userDetails.isAccountDormant()));
			preparedStatement.setString(16,commonUtil.booleanToString(userDetails.isAccountDeleted()));
			preparedStatement.setString(17,commonUtil.booleanToString(userDetails.isCredentialsExpired()));
			preparedStatement.setString(18,commonUtil.booleanToString(userDetails.isEtlUser()));
			preparedStatement.setString(19,commonUtil.booleanToString(userDetails.isChatEnable()));
			preparedStatement.setString(20,commonUtil.booleanToString(userDetails.isDbAuthentication()));
			preparedStatement.setString(21,commonUtil.booleanToString(userDetails.isDbAuthRequired()));

			preparedStatement.setString(22,userDetails.getAccessStartTime());
			preparedStatement.setString(23,userDetails.getAccessEndTime());
			preparedStatement.setTimestamp(24,userDetails.getAccountExipyDate() == null ? null : Timestamp.valueOf(userDetails.getAccountExipyDate()));
			preparedStatement.setString(25,userDetails.getAccessPoints());
			preparedStatement.setString(26,userDetails.getLabelDirection());
			preparedStatement.setString(27,userDetails.getLanguage());

			preparedStatement.setString(28,userDetails.getCreatedBy());
			preparedStatement.setString(29,userDetails.getStatus());
			preparedStatement.executeUpdate();
			//preparedStatement.setString(2,userDetailsDTO.getUsername());
		}catch (Exception  e){
			LOGGER.log(Level.SEVERE,"Error while creating temp user");
			e.printStackTrace();
			return null;
		}
		return getPendingUserbyTempId(userDetails.getTempId());
	}

	public TempUser getPendingUserbyTempId(String tempUserId){
		TempUser tempUser = null;
		try(Connection connection = connectionUtil.getConnection()) {
      		String query = " SELECT USERNAME,USERPASS,FIRSTNAME,LASTNAME,EMAILID,MOBILENO,"
              + " DESIGNATION, BRANCHCODE, EMPLOYEECODE,DEPARTMENTCODE,ACCOUNTENABLED,ACCOUNTEXPIRED,ACCOUNTLOCKED,"
              + " ACCOUNTDORMANT,ACCOUNTDELETED,CREDENTIALSEXPIRED, ISETLUSER,CHATENABLE,ISDBAUTHENTICATION,ISDBAUTHREQUIRED,"
              + " ACCESSSTARTTIME, ACCESSENDTIME,ACCOUNTEXIPYDATE,ACCESSPOINTS,LABELDIRECTION,LANGUAGE,CREATEDON,"
              + " CREATEDBY,STATUS FROM TB_TEMPUSER WHERE TEMPID = ? AND STATUS = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1,tempUserId);
			preparedStatement.setString(2,"P");
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				tempUser = new TempUser();
				tempUser.setTempId(tempUserId);
				tempUser.setUsername(resultSet.getString("USERNAME"));
				tempUser.setFirstName(resultSet.getString("FIRSTNAME"));
				tempUser.setLastName(resultSet.getString("LASTNAME"));
				tempUser.setEmailId(resultSet.getString("EMAILID"));
				tempUser.setMobileNo(resultSet.getString("MOBILENO"));
				tempUser.setDesignation(resultSet.getString("DESIGNATION"));
				tempUser.setBranchCode(resultSet.getString("BRANCHCODE"));
				tempUser.setEmployeeCode(resultSet.getString("EMPLOYEECODE"));
				tempUser.setDepartmentCode(resultSet.getString("DEPARTMENTCODE"));
				tempUser.setAccountEnabled(commonUtil.stringToBoolean(resultSet.getString("ACCOUNTENABLED")));
				tempUser.setAccountExpired(commonUtil.stringToBoolean(resultSet.getString("ACCOUNTEXPIRED")));
				tempUser.setAccountLocked(commonUtil.stringToBoolean(resultSet.getString("ACCOUNTLOCKED")));
				tempUser.setAccountDormant(commonUtil.stringToBoolean(resultSet.getString("ACCOUNTDORMANT")));
				tempUser.setAccountDeleted(commonUtil.stringToBoolean(resultSet.getString("ACCOUNTDELETED")));
				tempUser.setCredentialsExpired(commonUtil.stringToBoolean(resultSet.getString("CREDENTIALSEXPIRED")));
				tempUser.setEtlUser(commonUtil.stringToBoolean(resultSet.getString("ISETLUSER")));
				tempUser.setChatEnable(commonUtil.stringToBoolean(resultSet.getString("CHATENABLE")));
				tempUser.setDbAuthentication(commonUtil.stringToBoolean(resultSet.getString("ISDBAUTHENTICATION")));
				tempUser.setDbAuthRequired(commonUtil.stringToBoolean(resultSet.getString("ISDBAUTHREQUIRED")));
				tempUser.setAccessStartTime(resultSet.getString("ACCESSSTARTTIME"));
				tempUser.setAccessEndTime(resultSet.getString("ACCESSENDTIME"));
				tempUser.setAccountExipyDate(resultSet.getTimestamp("ACCOUNTEXIPYDATE") == null ? null : resultSet.getTimestamp("ACCOUNTEXIPYDATE").toLocalDateTime());
				tempUser.setAccessPoints(resultSet.getString("ACCESSPOINTS"));
				tempUser.setLabelDirection(resultSet.getString("LABELDIRECTION"));
				tempUser.setLanguage(resultSet.getString("LANGUAGE"));
				tempUser.setCreatedOn(resultSet.getTimestamp("CREATEDON").toLocalDateTime());
				tempUser.setCreatedBy(resultSet.getString("CREATEDBY"));
				tempUser.setStatus(resultSet.getString("STATUS"));
			}
		}catch (Exception e){
			LOGGER.log(Level.SEVERE,"Error while getting temp user"+e.getMessage());
			e.printStackTrace();
			return null;
		}
		return tempUser;
	}

	@Override
	public boolean isPendingUser(String userName, MakerCheckerStatus pending) {
		try(Connection connection = connectionUtil.getConnection()){
			String query = " SELECT COUNT (*) FROM TB_TEMPUSER WHERE USERNAME = ? AND STATUS = ? ";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1,userName);
			preparedStatement.setString(2,pending.getStatus());
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next() && resultSet.getInt(1)> 0 ){
				LOGGER.log(Level.INFO,"User is Pending = " + userName);
				return true;
			}
		}catch (Exception e){
			LOGGER.log(Level.SEVERE,"Error While check user is pending or not "+e.getMessage());
			e.printStackTrace();
		}

		return false;
	}





	@Override
	public List<TempUser> getTempUserList() {
		List<TempUser> tempUserList = new ArrayList<>();
		try(Connection connection = connectionUtil.getConnection()) {
			String sql = " SELECT USERNAME,USERPASS,FIRSTNAME,LASTNAME,EMAILID,MOBILENO,"
					+ " DESIGNATION, BRANCHCODE, EMPLOYEECODE,DEPARTMENTCODE,ACCOUNTENABLED,ACCOUNTEXPIRED,ACCOUNTLOCKED,"
					+ " ACCOUNTDORMANT,ACCOUNTDELETED,CREDENTIALSEXPIRED, ISETLUSER,CHATENABLE,ISDBAUTHENTICATION,ISDBAUTHREQUIRED,"
					+ " ACCESSSTARTTIME, ACCESSENDTIME,ACCOUNTEXIPYDATE,ACCESSPOINTS,LABELDIRECTION,LANGUAGE,CREATEDON,"
					+ " CREATEDBY,STATUS FROM TB_TEMPUSER WHERE STATUS = ?";
      		PreparedStatement preparedStatement =connection.prepareStatement(sql);
			preparedStatement.setString(1,MakerCheckerStatus.PENDING.getStatus());
			ResultSet resultSet = preparedStatement.executeQuery();

			while(resultSet.next()){
				TempUser tempUser = new TempUser();
				tempUser.setUsername(resultSet.getString("USERNAME"));
				tempUser.setFirstName(resultSet.getString("FIRSTNAME"));
				tempUser.setLastName(resultSet.getString("LASTNAME"));
				tempUser.setEmailId(resultSet.getString("EMAILID"));
				tempUser.setMobileNo(resultSet.getString("MOBILENO"));
				tempUser.setDesignation(resultSet.getString("DESIGNATION"));
				tempUser.setBranchCode(resultSet.getString("BRANCHCODE"));
				tempUser.setEmployeeCode(resultSet.getString("EMPLOYEECODE"));
				tempUser.setDepartmentCode(resultSet.getString("DEPARTMENTCODE"));
				tempUser.setAccountEnabled(commonUtil.stringToBoolean(resultSet.getString("ACCOUNTENABLED")));
				tempUser.setAccountExpired(commonUtil.stringToBoolean(resultSet.getString("ACCOUNTEXPIRED")));
				tempUser.setAccountLocked(commonUtil.stringToBoolean(resultSet.getString("ACCOUNTLOCKED")));
				tempUser.setAccountDormant(commonUtil.stringToBoolean(resultSet.getString("ACCOUNTDORMANT")));
				tempUser.setAccountDeleted(commonUtil.stringToBoolean(resultSet.getString("ACCOUNTDELETED")));
				tempUser.setCredentialsExpired(commonUtil.stringToBoolean(resultSet.getString("CREDENTIALSEXPIRED")));
				tempUser.setEtlUser(commonUtil.stringToBoolean(resultSet.getString("ISETLUSER")));
				tempUser.setChatEnable(commonUtil.stringToBoolean(resultSet.getString("CHATENABLE")));
				tempUser.setDbAuthentication(commonUtil.stringToBoolean(resultSet.getString("ISDBAUTHENTICATION")));
				tempUser.setDbAuthRequired(commonUtil.stringToBoolean(resultSet.getString("ISDBAUTHREQUIRED")));
				tempUser.setAccessStartTime(resultSet.getString("ACCESSSTARTTIME"));
				tempUser.setAccessEndTime(resultSet.getString("ACCESSENDTIME"));
				tempUser.setAccountExipyDate(resultSet.getTimestamp("ACCOUNTEXIPYDATE") == null ? null : resultSet.getTimestamp("ACCOUNTEXIPYDATE").toLocalDateTime());
				tempUser.setAccessPoints(resultSet.getString("ACCESSPOINTS"));
				tempUser.setLabelDirection(resultSet.getString("LABELDIRECTION"));
				tempUser.setLanguage(resultSet.getString("LANGUAGE"));
				tempUser.setCreatedOn(resultSet.getTimestamp("CREATEDON").toLocalDateTime());
				tempUser.setCreatedBy(resultSet.getString("CREATEDBY"));
				tempUser.setStatus(resultSet.getString("STATUS"));
				tempUserList.add(tempUser);
			}
		}catch (Exception e){
			LOGGER.log(Level.SEVERE,"Error getting temp user"+e.getMessage());
			e.printStackTrace();
			throw new InvalidOperationException("");
			//return null;
		}
		return tempUserList;
	}

	@Override
	public boolean isUserPresent(String username, String currentUserName, String activeRole, String ipAddress) {
		try(Connection connection = connectionUtil.getConnection()){
			String query = " SELECT COUNT (*) FROM TB_USER WHERE USERNAME = ? ";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1,username);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next() && resultSet.getInt(1)> 0 ){
        		LOGGER.log(Level.INFO,"User already present with username = " + username);
				return true;
			}
		}catch (Exception e){
			LOGGER.log(Level.SEVERE,"Error While check user is already present or not "+e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String assignRole(String username, List<String> roleList, User currentUser, String activeRole, String ipAddress) {
		try(Connection connection = connectionUtil.getConnection()){
			String query = " INSERT INTO TB_TEMPUSERROLEMAPPING (TEMPID,USERCODE,ROLEID,LOGINPRIORITY,STATUS,CREATEDON,CREATEDBY)" +
					       " VALUES (?,?,?,?,?,SYSTIMESTAMP,?)";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			for(int i =0;i<roleList.size();i++){
				String tempId = commonDBFunctionUtil.getNextSeqValue("TEMP_USERROLEMAPPING_SEQ");
				preparedStatement.setString(1,tempId);
				preparedStatement.setString(2,username);
				preparedStatement.setString(3,roleList.get(i));
				preparedStatement.setInt(4,i+1);
				preparedStatement.setString(5,MakerCheckerStatus.PENDING.getStatus());
				preparedStatement.setString(6,currentUser.getUsername());
				preparedStatement.addBatch();
			}
			int[] records = preparedStatement.executeBatch();
			if(records.length == roleList.size()){
				return "Role Successfully Assigned";
			}
			return "Error while role assigning";
		}catch (Exception e){
			LOGGER.log(Level.SEVERE,"Error While Assigning roles to user "+e.getMessage());
			e.printStackTrace();
			return "Error while role assigning";
		}
	}




}
