package com.quantumdataengines.app.dao;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.quantumdataengines.app.model.AuditLog;
import com.quantumdataengines.app.service.common.CommonService;
import com.quantumdataengines.app.util.ConnectionUtil;
import com.quantumdataengines.app.util.EmailRefreshUtil;

@Repository
public class SystemBatchJobDAOImpl implements SystemBatchJobDAO{
	
	private static final Logger log = LoggerFactory.getLogger(SystemBatchJobDAOImpl.class);
	@Autowired
	private ConnectionUtil connectionUtil;
	@Autowired
	private EmailRefreshUtil emailRefreshUtil;
	@Autowired
	private CommonService commonService;
	
	@Override
	public void accountExpiryJob(Connection connection) {
		PreparedStatement preparedStatement = null;
		try{
			preparedStatement = connection.prepareStatement(Query.ACCOUNTEXPIRY);
			preparedStatement.executeUpdate();
		}catch(Exception e){
			log.error("Error while running account expiry batch job : "+e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void passwordExpiryJob(Connection connection, int expiryDay) {
		PreparedStatement preparedStatement = null;
		try{
			preparedStatement = connection.prepareStatement(Query.PASSWORDEXPIRY);
			preparedStatement.setInt(1, expiryDay);
			preparedStatement.executeUpdate();
		}catch(Exception e){
			log.error("Error while running password expiry batch job : "+e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public boolean checkEmailAlreadyStored(Connection connection, String caseNo, String messageId,
			String folderType) {
		boolean isAvailable = true;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try{
			preparedStatement = connection.prepareStatement(Query.EMAILAVAILABLITYCHECK);
			preparedStatement.setString(1, caseNo);
			preparedStatement.setString(2, messageId);
			preparedStatement.setString(3, folderType);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next())
				if(resultSet.getInt("EMAILCOUNT") == 0)
					isAvailable = false;
		}catch(Exception e){
			e.printStackTrace();
		}
		return isAvailable;
	}
	
	public String getEmailInternalNumber(Connection connection){
		String internalNumber = "";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try{
			preparedStatement = connection.prepareStatement("SELECT SEQ_EMAILINTERNALNUMBER.NEXTVAL INTERNALNUMEBR FROM DUAL");
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next())
				internalNumber = resultSet.getString("INTERNALNUMEBR");			
		}catch(Exception e){
			e.printStackTrace();
		}
		return internalNumber;
	}
	
	public boolean saveEmailDetails(Connection connection, String caseno, String messageId, String messageNumber, String emailInternalNumber, String fromId, String recipientsTO, String recipientsCC,
			String recipientsBCC, String subject, String sentDate, String receiveDate, String messageContent, String updatedBy, String folderType){
		PreparedStatement preparedStatement = null;
		String query = "INSERT INTO TB_EMAILEXCHANGEDETAILS (EMAILREFERENCENO, MESSAGEID, MESSAGEUNIQUENO, MESSAGEINTERNALNO, SENDERID, RECIPIENTSTO, RECIPIENTSCC, RECIPIENTSBCC, "+
					   "	   SUBJECT, SENTDATE, RECEIVEDDATE, MESSAGECONTENT, UPDATEDBY, UPDATETIMESTAMP, FOLDERTYPE, SEENFLAG) "+
					   "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,SYSTIMESTAMP,?,?)";
		try{
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, caseno);
			preparedStatement.setString(2, messageId);
			preparedStatement.setString(3, messageNumber);
			preparedStatement.setString(4, emailInternalNumber);
			preparedStatement.setString(5, fromId);
			preparedStatement.setString(6, recipientsTO);
			preparedStatement.setString(7, recipientsCC);
			preparedStatement.setString(8, recipientsBCC);
			preparedStatement.setString(9, subject);
			preparedStatement.setString(10, sentDate);
			preparedStatement.setString(11, receiveDate);
			preparedStatement.setString(12, messageContent);
			preparedStatement.setString(13, updatedBy);
			preparedStatement.setString(14, folderType);
			preparedStatement.setString(15, "N");
			preparedStatement.executeUpdate();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean saveEmailAttachment(Connection connection, String caseno, String messageId, String messageNumber, String emailInternalNumber, 
			List<String> files, String updatedBy, String folderType){
		PreparedStatement preparedStatement = null;
		int count = 0;
		String query = "INSERT INTO TB_EMAILATTACHMENTDETAILS (EMAILREFERENCENO, MESSAGEID, MESSAGEUNIQUENO, MESSAGEINTERNALNO, ATTACHMENTNO, "+
					   "	   FILENAME, FILETYPE, FILESIZE, FILECONTENT, UPDATEDBY, UPDATETIMESTAMP, FOLDERTYPE ) "+
					   "VALUES (?,?,?,?,?,?,?,?,?,?,SYSTIMESTAMP,?)";
		try{
			
			FOR : for(String file : files){
				File objFile = new File(file);
				if(!objFile.exists())
					continue FOR;
				
				preparedStatement = connection.prepareStatement(query);
				String fileName = objFile.getName();
				
				String fileSize = new Long(objFile.length()).toString();
				String fileType = FilenameUtils.getExtension(fileName);
				
				preparedStatement.setString(1, caseno);
				preparedStatement.setString(2, messageId);
				preparedStatement.setString(3, messageNumber);
				preparedStatement.setString(4, emailInternalNumber);
				preparedStatement.setString(5, new Integer(count).toString());
				preparedStatement.setString(6, fileName);
				preparedStatement.setString(7, fileType);
				preparedStatement.setString(8, fileSize);
				
				FileInputStream fis = new FileInputStream(objFile);
				preparedStatement.setBinaryStream(9, fis, fis.available());
				
				preparedStatement.setString(10, updatedBy);
				preparedStatement.setString(11, folderType);
				preparedStatement.executeUpdate();
				//preparedStatement.addBatch();
				fis.close();
				count++;
			}
			
		//	preparedStatement.executeBatch();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public Map<String, String> initilizingDBSystemParameters(Connection connection) {
		Map<String, String> systemParameters = new LinkedHashMap<String, String>();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		log.debug("getting system parameters...");
		try{
			preparedStatement = connection.prepareStatement(Query.GETALLSYSTEMPARAMS);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				systemParameters.put(resultSet.getString("PARAMETERNAME"), 
						resultSet.getString("PARAMETERVALUE"));
			}
			
		}catch(Exception e){
			log.error("Error while getting system parameters from database : "+e.getMessage());
			e.printStackTrace();
		}
		return systemParameters;
	}
	
	@Override
	public void setEmailRefreshLog(Connection connection, String refreshTime, String emailCount, String status, String message, String folder, String updatedBy){
		PreparedStatement preparedStatement = null;
		try{
			preparedStatement = connection.prepareStatement(Query.SETEMAILREFRESHLOG);
			preparedStatement.setString(1, refreshTime);
			preparedStatement.setString(2, emailCount);
			preparedStatement.setString(3, status);
			preparedStatement.setString(4, message);
			preparedStatement.setString(5, folder);
			preparedStatement.setString(6, updatedBy);
			preparedStatement.executeUpdate();			
		}catch(Exception e){
			log.error("Error while saving email refresh log : "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	private Timestamp convertDateToTimestamp(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.MILLISECOND, 0);
		return new Timestamp(cal.getTimeInMillis());
	}

	@Override
	public void storeAuditLog(String jndiName, List<AuditLog> auditLogList) {
		Connection connection = connectionUtil.getConnection();
		PreparedStatement preparedStatement = null;
		try{
			preparedStatement = connection.prepareStatement("INSERT INTO COMAML.TB_SYSTEMAUDITLOG(USERCODE, USERROLE, LOGINDATETIME, SESSIONID, MODULEACCESSED, ACCESSTYPE, "+
															"		ACCESSEDTIME, MESSAGE, URLACCESSED, QUERYPARAMS, TERMINALID, TERMINALNAME, TERMINALAGENT, LOGDATETIME) "+
															"VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			for(AuditLog auditLog : auditLogList){
				preparedStatement.setString(1, auditLog.getUserCode());
				preparedStatement.setString(2, auditLog.getUserRole());
				preparedStatement.setTimestamp(3, convertDateToTimestamp(auditLog.getLoginTime()));
				preparedStatement.setString(4, auditLog.getSessionId());
				preparedStatement.setString(5, auditLog.getModuleAccessed());
				preparedStatement.setString(6, auditLog.getAccessType());
				preparedStatement.setTimestamp(7, convertDateToTimestamp(auditLog.getAccessTime()));
				preparedStatement.setString(8, auditLog.getMessage());
				preparedStatement.setString(9, auditLog.getUrlAccessed());
				preparedStatement.setString(10, auditLog.getQueryParam());
				preparedStatement.setString(11, auditLog.getTerminalId());
				preparedStatement.setString(12, auditLog.getTerminalName());
				preparedStatement.setString(13, auditLog.getTerminalAgent());
				preparedStatement.setTimestamp(14, convertDateToTimestamp(auditLog.getLogDateTime()));
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
		}catch(Exception e){
			e.printStackTrace();
			log.error("Error while storing audit log : "+e.getMessage());
		}finally{
			connectionUtil.closeResources(connection, preparedStatement, null);
		}
	}
}
