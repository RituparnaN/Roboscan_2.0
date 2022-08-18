package com.quantumdataengines.app.dao;

import java.io.File;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.quantumdataengines.app.model.AuditLog;
import com.quantumdataengines.app.schema.Configuration;

public interface CommonDAO {
	public long getSystemTimeoutSeconds();
	
	public String getUserBranchCode(String userCode);
	public Map<String, String> initilizingDBSystemParameters(Configuration configuration);
	public Map<String, Map<String, Map<String, Map<String, String>>>> userModule(String userCode, String groupCode);
	public String changeProfilePriprity(String userCode, String profile);
	public String lastEmailRefresgLog(String folderType);
	public List<Map<String, String>> getAllEmailRefreshLog();
	public boolean updateLanguage(String userCode, String langCode);
	public boolean updateLabelDirection(String userCode, String labelDirection);
	public List<Map<String, String>> updateUsersSessionList(List<Map<String, String>> allUserDetails);
	public void refreshEmail(Configuration configuration, String userCode, String emailPassword, int lookupDays, String folderName, 
			String folderType, String emailSearchString, String subjectsToIgnore);
	public Map<String, Map<String, String>> getAllOfflineUser(String allOnLineUsers, String currentUser);
	public Map<String, Integer> getMessageMaxNoANDUnSeenCount(String chatWindowId, String userCode);
	public String getMessageMaxNo(String chatWindowId);
	public void saveChatMessage(String jndiName, String chatWindowId, String messageId, String fromUser, String toUser, String messageContent, String seenFlag);
	public String loadPreviuosMessage(String chatWindowId, String userCode, int messageMaxNo, int messageminNo);
	public String loadUnseenMessage(String chatWindowId, String userCode);
	public List<Map<String, String>> getEmailNotification(String userCode, String seenFlag);
	public Map<String, Object> getFileUploadConfig(String moduleRef);
	public void saveUploadedFile(String uploadSeqNo, String uploadRef, String uploadModuleRefId, String moduleUnqNo, String fileName, String filePath, String fileSize, 
			File file, String userCode, String userRole, String ipAddress);
	public List<Map<String, String>> getFileInfo(String uploadRef, String uploadModuleRefId, String moduleUnqNo);
	public int truncateAndGetTempTableColCount(String tableName);
	public boolean processCCRUploadedFile(String procName, String uploadRefId, String moduleRefId, String month, String year,
			String userCode, String roleCode, String ipAddress);
	public List<Map<String, Object>> getFileContentForDownload(String seqNo, String uploadInfo, String moduleUnqNo);
	public List<Map<String, String>> getFilesInfoForModuleUnqNo(String moduleRefNo);
	public String removeServerFile(String seqNo, String uploadRefNo, String moduleRefNo, String moduleUnqNo);
	public String getFolderEmailCount(String folderType, String caseNo);
	public List<Map<String, String>> getEmailDetails(String caseNo, String folderType, String emailNumber, String userCode);
	public List<Map<String, String>> getEmailAttachments(String caseNo, String emailNumber);
	public List<Map<String, Object>> downloadEmailAttachment(String caseNo, String messageNumber, String attachmentNumebr);
	public Map<String, Object> getEmailIdsForMapping(String emailCodes, int escalationOrder);
	public Map<String, String> composeEmail(String caseNo, String emailNo, String folderType, String composeType, String userCode, String userRole);
	public Map<String, Object> getDashboardGraphData(String userCode, String userRole, String ipAddress);
	public void saveUploadFileData(List<List<String>> uploadedData, String tempTable, int dataColCount, String userCode, String userRole, String ipAddress);
	public void storeAuditLogNew(AuditLog auditLog);
	
	public List<Map<String, String>> getCountWiseModules(String userCode, String ipAddress);
	public String getEmailAddresses(Connection connection, String usercode);
}
