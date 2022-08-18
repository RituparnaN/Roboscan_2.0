package com.quantumdataengines.app.service.common;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.quantumdataengines.app.model.Extraction;
import com.quantumdataengines.app.schema.Configuration;


//import com.quantumdataengines.app.compass.model.Extraction;
//import com.quantumdataengines.app.compass.schema.Configuration;

public interface CommonService {
	public String getUserBranchCode(String userCode);
	public Map<String, String> initilizingDBSystemParameters();
	public Configuration getUserConfiguration();
	public Map<String, Map<String, Map<String, Map<String, String>>>> userModule(String userCode, String groupCode);
	public String changeProfilePriprity(String userCode, String profile);
	public String lastEmailRefresgLog();
	public List<Map<String, String>> getAllEmailRefreshLog();
	public boolean updateLanguage(String userCode, String langCode);
	public boolean updateLabelDirection(String userCode, String labelDirection);
	public List<Map<String, String>> updateUsersSessionList(List<Map<String, String>> allUserDetails);
	public void refreshEmail(String userCode, String emailPassword, int lookupDays, String folderName, 
			String folderType, String emailSearchString, String subjectsToIgnore);
	public Extraction getExtractionStatus();
	public void sendChatMessage(String fromUser, String toUser, String toUserName, String toUsername, String messageId, String chatMessage, String chatWindowId);
	public Map<String, Map<String, String>> getAllOnlinePerson(String currentUser);
	public void changeChatStatus(String userCode, String currentStatus, String status, String userName);
	public Map<String,String> getUserOnlineStatus(String currentUser, String userCodes);
	public String getMessageMaxNo(String chatWindowId);
	public String loadPreviuosMessage(String chatWindowId, String userCode, int messageMaxNo, int messageminNo);
	public String loadUnseenMessage(String chatWindowId, String userCode);
	public List<Map<String, String>> getEmailNotification(String userCode, String seenFlag);
	public Map<String, Object> getFileUploadConfig(String moduleRef);
	public void saveUploadedFile(String seqNo, String folderName, String uploadModuleRefId, String uploadRef, String moduleUnqNo, 
			MultipartFile multiFile, String userCode, String roleCode, String ipAddress);
	public List<Map<String, String>> processFile(String uploadRefNo, String moduleRefId, String userCode, String userRole, String ipAddress);
	public List<Map<String, String>> getFileInfo(String uploadRef, String uploadModuleRefId, String moduleUnqNo);
	public boolean processCCRUploadedFile(String procName, String uploadRefId, String moduleRefId, String month, String year,
			String userCode, String roleCode, String ipAddress);
	public Map<String, Object> getFileContentForDownload(String seqNo, String uploadInfo, String moduleUnqNo) throws Exception;
	public List<Map<String, String>> getFilesInfoForModuleUnqNo(String moduleRefNo);
	public String removeServerFile(String seqNo, String uploadRefNo, String moduleRefNo, String moduleUnqNo);
	public String getFolderEmailCount(String folderType, String caseNo);
	public List<Map<String, String>> getEmailDetails(String caseNo, String folderType, String emailNumber, String userCode);
	public List<Map<String, String>> getEmailAttachments(String caseNo, String emailNumber);
	public Map<String, Object> downloadEmailAttachment(String caseNo, String messageNumber, String attachmentNumebr) throws Exception;
	public Map<String, Object> getEmailIdsForMapping(String emailCodes, int escalationOrder);
	public Map<String, String> composeEmail(String caseNo, String emailNo, String folderType, String composeType, String userCode, String userRole);
	public Map<String, Object> getDashboardGraphData(String userCode, String userRole, String ipAddress);
	public Timestamp convertDateToTimestamp(Date date);
	public void auditLog(String userCode, HttpServletRequest request, String moduleAccessed, String accessType, String message);
	public void saveEmailAttachments(String caseNo, MultipartFile multiFile, String refId);
	public Map<String, String> sendEMail(String caseNo, List<String> toList, List<String> ccList, String subject, String content, String attachmentFolder, String userCode);
	
	public List<Map<String, String>> getCountWiseModules(String userCode, String ipAddress);
}
