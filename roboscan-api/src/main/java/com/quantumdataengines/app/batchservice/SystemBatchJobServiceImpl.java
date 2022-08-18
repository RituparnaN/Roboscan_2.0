package com.quantumdataengines.app.batchservice;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.quantumdataengines.app.dao.SystemBatchJobDAO;
import com.quantumdataengines.app.model.AuditLog;
import com.quantumdataengines.app.model.EmailRefresh;
import com.quantumdataengines.app.schema.Configuration;
import com.quantumdataengines.app.util.EmailReceiverUtil;
import com.quantumdataengines.app.util.EmailRefreshUtil;

@Service
public class SystemBatchJobServiceImpl implements SystemBatchJobService{
	
	private static final Logger log = LoggerFactory.getLogger(SystemBatchJobServiceImpl.class);
	
	@Lazy
	@Autowired
	private SystemBatchJobDAO systemBatchJobDAO;
	@Autowired
	private EmailRefreshUtil emailRefreshUtil;
	
	@Lazy
	@Autowired
	private EmailReceiverUtil emailReceiverUtil;
	
	@Override
	public void accountExpiryJob(Connection connection) {
		systemBatchJobDAO.accountExpiryJob(connection);
	}

	@Override
	public void passwordExpiryJob(Connection connection, int expiryDay) {
		systemBatchJobDAO.passwordExpiryJob(connection, expiryDay);
	}
	
	public void storeAuditLog(String jndiName, List<AuditLog> auditLogList){
		systemBatchJobDAO.storeAuditLog(jndiName, auditLogList);
	}

	@Override
	public void emailRefreshJob(final Connection connection, final Configuration configuration, final String updatedBy, 
			final String emailPassword, final int lookupDays, final String folderName, final String folderType, 
			final String searchString, final String subJectEscapeString) {
		final EmailRefresh emailRefresh = new EmailRefresh();
		emailRefresh.setStartTime(getFormattedDate(new Date(), "dd/MM/yyyy HH:mm:ss"));
		emailRefresh.setStatus(1);
		emailRefresh.setStatusMessage("Email auto refresh has been initialed by "+updatedBy);
		emailRefreshUtil.setEmailRefreshDetails(configuration.getEntityName(), emailRefresh);
		String status = "";
		String message = "";
		try {
			emailRefreshUtil.getEmailRefreshDetails().get(configuration.getEntityName()).setFolder(folderType);
			emailReceiverUtil.receiveEmail(connection, configuration, updatedBy, emailPassword, lookupDays, folderName, folderType, searchString, subJectEscapeString);
			status = "SUCCESS";
			message = "Email successfully synchronize by "+updatedBy;
		} catch (Exception e) {
			status = "ERROR";
			message = e.getMessage();
			e.printStackTrace();
		}
		systemBatchJobDAO.setEmailRefreshLog(connection, getFormattedDate(new Date(), "dd/MM/yyyy HH:mm:ss"),
				new Integer(emailRefreshUtil.getEmailRefreshDetails().get(configuration.getEntityName()).getNoOfEmailFound()).toString(), status, message, folderType, updatedBy);
		emailRefreshUtil.getEmailRefreshDetails().get(configuration.getEntityName()).setStatus(2);
		emailRefreshUtil.getEmailRefreshDetails().get(configuration.getEntityName()).setCompleteTime(getFormattedDate(new Date(), "dd/MM/yyyy HH:mm:ss"));
    	emailRefreshUtil.getEmailRefreshDetails().get(configuration.getEntityName()).setPercentage(100);
		emailRefreshUtil.getEmailRefreshDetails().get(configuration.getEntityName()).setStatusMessage("Email auto refresh is completed");;
		log.info("Email refresh completed");
	}
	
	public Map<String, String> initilizingDBSystemParameters(Connection connection){
		return systemBatchJobDAO.initilizingDBSystemParameters(connection);
	}
	
	public boolean checkEmailAlreadyStored(Connection connection, String caseNo, String messageId,
			String folderType){
		return systemBatchJobDAO.checkEmailAlreadyStored(connection, caseNo, messageId, folderType);
	}
	
	public String getFormattedDate(Date date, String format) {
		String strDate = "";
		try{
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			strDate = sdf.format(date);
		}catch(Exception e){
			e.printStackTrace();
		}
		return strDate;
	}
	
	public boolean saveEmailDetails(Connection connection, String caseno, String messageId, String messageNumber, String emailInternalNumber, String fromId, String recipientsTO, String recipientsCC,
			String recipientsBCC, String subject, String sentDate, String receiveDate, String messageContent, String updatedBy, String folderType){
		return systemBatchJobDAO.saveEmailDetails(connection, caseno, messageId, messageNumber, emailInternalNumber, fromId, recipientsTO, recipientsCC, recipientsBCC, subject, 
				sentDate, receiveDate, messageContent, updatedBy, folderType);
	}
	
	public boolean saveEmailAttachment(Connection connection, String caseno, String messageId, String messageNumber, String emailInternalNumber, 
			List<String> files, String updatedBy, String folderType){
		return systemBatchJobDAO.saveEmailAttachment(connection, caseno, messageId, messageNumber, emailInternalNumber, files, updatedBy, folderType);
	}
	
	public String getEmailInternalNumber(Connection connection){
		return systemBatchJobDAO.getEmailInternalNumber(connection);
	}
	
	
}
