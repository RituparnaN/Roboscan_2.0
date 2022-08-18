package com.quantumdataengines.app.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.quantumdataengines.app.model.AuditLog;

public interface SystemBatchJobDAO {
	public void accountExpiryJob(Connection connection);
	public void passwordExpiryJob(Connection connection, int expiryDay);
	public boolean checkEmailAlreadyStored(Connection connection, String caseNo, String messageId, String folderType);
	public Map<String, String> initilizingDBSystemParameters(Connection connection);
	public void setEmailRefreshLog(Connection connection, String refreshTime, String emailCount, String status, String message, String folder, String updatedBy);
	public boolean saveEmailDetails(Connection connection, String caseno, String messageId, String messageNumber, String emailInternalNumber, 
			String fromId, String recipientsTO, String recipientsCC,
			String recipientsBCC, String subject, String sentDate, String receiveDate, String messageContent, String updatedBy, String folderType);
	public boolean saveEmailAttachment(Connection connection, String caseno, String messageId, String messageNumber, String emailInternalNumber, 
			List<String> files, String updatedBy, String folderType);
	public String getEmailInternalNumber(Connection connection);
	public void storeAuditLog(String jndiName, List<AuditLog> auditLogList);
}