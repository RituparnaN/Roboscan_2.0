package com.quantumdataengines.app.batchservice;

import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.quantumdataengines.app.model.AuditLog;
import com.quantumdataengines.app.schema.Configuration;

public interface SystemBatchJobService {
	public void accountExpiryJob(Connection connection);
	public void passwordExpiryJob(Connection connection, int expiryDay);
	public void storeAuditLog(String jndiName, List<AuditLog> auditLogList);
	public void emailRefreshJob(final Connection connection, final Configuration configuration, final String updatedBy, 
			final String emailPassword, final int lookupDays, final String folderName, final String folderType, 
			final String searchString, final String subJectEscapeString);
	public boolean checkEmailAlreadyStored(Connection connection, String caseNo, String messageId,
			String folderType);
	public Map<String, String> initilizingDBSystemParameters(Connection connection);
	public String getFormattedDate(Date date, String format);
	public boolean saveEmailDetails(Connection connection, String caseno, String messageId, String messageNumber, String emailInternalNumber, String fromId, String recipientsTO, String recipientsCC,
			String recipientsBCC, String subject, String sentDate, String receiveDate, String messageContent, String updatedBy, String folderType);
	public boolean saveEmailAttachment(Connection connection, String caseno, String messageId, String messageNumber, String emailInternalNumber, 
			List<String> files, String updatedBy, String folderType);
	public String getEmailInternalNumber(Connection connection);
}
