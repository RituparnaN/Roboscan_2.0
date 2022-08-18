package com.quantumdataengines.app.service.common;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.quantumdataengines.app.batchservice.SystemBatchJobService;
import com.quantumdataengines.app.dao.CommonDAO;
import com.quantumdataengines.app.listener.ChatClientListner;
import com.quantumdataengines.app.model.AuditLog;
import com.quantumdataengines.app.model.Extraction;
import com.quantumdataengines.app.otherservice.OtherCommonService;
import com.quantumdataengines.app.schema.Configuration;
import com.quantumdataengines.app.util.ChatUserContextHolder;
import com.quantumdataengines.app.util.CommonUtil;
import com.quantumdataengines.app.util.ConnectionUtil;
import com.quantumdataengines.app.util.EmailSenderUtil;
import com.quantumdataengines.app.util.ExtractionUtil;
import com.quantumdataengines.app.util.UserContextHolder;
import eu.bitwalker.useragentutils.UserAgent;


@Service
public class CommonServiceImpl implements CommonService{

	private static final Logger log = LoggerFactory.getLogger(CommonServiceImpl.class);
	
	@Autowired
	private CommonDAO commonDAO;
	@Autowired
	private ExtractionUtil extractionUtil;
	@Autowired
	private OtherCommonService otherCommonService;
	@Autowired
	private EmailSenderUtil emailSenderUtil;
	@Autowired
	private SystemBatchJobService systemBatchJobService;
	@Autowired
	private ConnectionUtil connectionUtil;
	
	@Override
	public String getUserBranchCode(String userCode){
		return commonDAO.getUserBranchCode(userCode);
	}
	
	@Override
	public Configuration getUserConfiguration(){
		return UserContextHolder.getUserContext();
	}
	
	@Override
	public Map<String, String> initilizingDBSystemParameters(){		
		return commonDAO.initilizingDBSystemParameters(UserContextHolder.getUserContext());
	}
	
	@Override
	public Map<String, Map<String, Map<String, Map<String, String>>>> userModule(String userCode, String groupCode){
		return commonDAO.userModule(userCode, groupCode);
	}
	
	@Override
	public String changeProfilePriprity(String userCode, String profile){
		return commonDAO.changeProfilePriprity(userCode, profile);
	}
	
	@Override
	public String lastEmailRefresgLog(){
		String logMesasage = "";		
		Map<String, String> systemParameters = commonDAO.initilizingDBSystemParameters(UserContextHolder.getUserContext());
		String inboxrefreshEnabled = systemParameters.get("INBOXFOLDERREFRESH");
		String sentrefreshEnabled = systemParameters.get("SENTFOLDERREFRESH");
		String inboxRefreshLog = "";
		String sentRefreshLog = "";
		if("Y".equals(inboxrefreshEnabled)){
			inboxRefreshLog = commonDAO.lastEmailRefresgLog("INBOX");
		}
		
		if("Y".equals(sentrefreshEnabled)){
			sentRefreshLog = commonDAO.lastEmailRefresgLog("SENT");
		}
		
		if(!"".equals(inboxRefreshLog)){
			logMesasage = inboxRefreshLog;
		}
		if(!"".equals(sentRefreshLog)){
			if("".equals(logMesasage)){
				logMesasage = logMesasage + sentRefreshLog;
			}else{
				logMesasage = logMesasage +"<br/>"+ sentRefreshLog;
			}
		}
		return logMesasage;
	}
	
	@Override
	public List<Map<String, String>> getAllEmailRefreshLog() {
		return commonDAO.getAllEmailRefreshLog();
	}

	@Override
	public boolean updateLanguage(String userCode, String langCode) {
		return commonDAO.updateLanguage(userCode, langCode);
	}

	@Override
	public boolean updateLabelDirection(String userCode, String labelDirection) {
		return commonDAO.updateLabelDirection(userCode, labelDirection);
	}

	@Override
	public List<Map<String, String>> updateUsersSessionList(List<Map<String, String>> allUserDetails) {
		return commonDAO.updateUsersSessionList(allUserDetails);
	}

	@Override
	public void refreshEmail(String userCode, String emailPassword,
			int lookupDays, String folderName, String folderType,
			String emailSearchString, String subjectsToIgnore) {
		Configuration configuration = UserContextHolder.getUserContext();
		try{
			log.info("email["+folderType+"] refresh job initiated for "+configuration.getEntityName());
			commonDAO.refreshEmail(configuration, userCode, emailPassword, lookupDays, folderName, 
					folderType, emailSearchString, subjectsToIgnore);
			log.info("email["+folderType+"] refresh job completed for "+configuration.getEntityName());	
		}catch(Exception e){
			log.error("email["+folderType+"] refresh job failed : "+e.getMessage());
		}
	}

	@Override
	public Extraction getExtractionStatus() {
		Configuration configuration = UserContextHolder.getUserContext();
		if(configuration != null)
			return extractionUtil.getInstance(configuration.getEntityName());
		else
			return null;
	}
	
	@Override
	public Map<String, Map<String, String>> getAllOnlinePerson(String currentUser){
		Map<String, Map<String, String>> allUsers = new LinkedHashMap<String, Map<String, String>>();
		
		String institute = UserContextHolder.getUserContext().getEntityName();
		Map<String, Map<String, Object>> allOnlineUsers = ChatUserContextHolder.getAllUserForInstitution(institute);
		List<String> allUserCode = new Vector<String>(allOnlineUsers.keySet());
		String users = "";
		for(int i = 0; i < allUserCode.size(); i++){
			if((i+1) == allUserCode.size()){
				users = users+"'"+allUserCode.get(i)+"'";
			}else{
				users = users+"'"+allUserCode.get(i)+"',";
			}	
		}
		Map<String, Map<String, String>> offlineUser = commonDAO.getAllOfflineUser(users, currentUser);
		
		for(String user : allUserCode){
			if(!user.equals(currentUser)){
				Map<String, String> newUserDetails = new HashMap<String, String>();
				Map<String, Object> onlineUserDetails = allOnlineUsers.get(user);
				String status = onlineUserDetails.get("STATUS") != null ? (String) onlineUserDetails.get("STATUS") : "O";
				String userCode = (String) onlineUserDetails.get("USERCODE");
				String chatWindowID = otherCommonService.getChatWindowId(currentUser, userCode);
				Map<String, Integer> messageInfo = commonDAO.getMessageMaxNoANDUnSeenCount(chatWindowID.replace(".", ""), currentUser);
				int messageUnseenCount = messageInfo.get("UNSEENMESSAGECOUNT");
				
				newUserDetails.put("USERCODE", userCode);
				newUserDetails.put("USERNAME", (String) onlineUserDetails.get("USERNAME"));
				newUserDetails.put("STATUS", status);
				newUserDetails.put("CHATWINDOWID", chatWindowID);
				newUserDetails.put("MESSAGEMAXNO", new Integer(messageInfo.get("MESSAGEMAXNO")).toString());
				if(messageUnseenCount > 0)
					newUserDetails.put("UNSEENMESSAGECOUNT", new Integer(messageInfo.get("UNSEENMESSAGECOUNT")).toString());
				else
					newUserDetails.put("UNSEENMESSAGECOUNT", "");
				allUsers.put(userCode, newUserDetails);
				
			}			
		}
		allUsers.putAll(offlineUser);
		return allUsers;
	}
	
	@Override
	public void changeChatStatus(String userCode, String currentStatus, String status, String userName){
		String institute = UserContextHolder.getUserContext().getEntityName();
		
		if(currentStatus!= null && currentStatus.equals("O")){
			try {
				new ChatClientListner(UserContextHolder.getUserContext(), userCode, userName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
		try{
			Socket socket = (Socket) ChatUserContextHolder.getUserContextDetails(institute, userCode, "CLIENTSOCKET");
			PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
			out.println("STATUS%;%"+institute+"%;%"+userCode+"%;%"+status);
		}catch(Exception e){
			e.printStackTrace();
			log.error("Error while changing chat status : "+e.getMessage());
		}
	}
	
	@Override
	public void sendChatMessage(String fromUser, String toUser, String fromUserName, String toUsername, String messageId, String chatMessage, String chatWindowId){
		String institute = UserContextHolder.getUserContext().getEntityName();
		Socket socket = (Socket) ChatUserContextHolder.getUserContextDetails(institute, fromUser, "CLIENTSOCKET");
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			String sentDate = sdf.format(new Date());
			PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
		    out.println("CHAT%;%"+institute+"%;%"+fromUser+"%;%"+toUser+"%;%"+fromUserName+"%;%"+toUsername+"%;%"+chatWindowId+"%;%"+messageId+"%;%"+chatMessage+"%;%"+sentDate);
		}catch(Exception e){
			e.printStackTrace();
			log.error("Error while sending chat message from "+fromUser+", to "+toUser+" : "+e.getMessage());
		}
	}
	
	@Override
	public String getMessageMaxNo(String chatWindowId){
		return commonDAO.getMessageMaxNo(chatWindowId);
	}
	
	@Override
	public Map<String,String> getUserOnlineStatus(String currentUser, String userCodes){
		Map<String,String> userStatus = new HashMap<String,String>();
		String[] userCodeArray = userCodes.split(",");
		for(String userCode : userCodeArray){
			String color = "";
			String institute = UserContextHolder.getUserContext().getEntityName();
			String status = (String) ChatUserContextHolder.getUserContextDetails(institute, userCode, "STATUS");
			if(status == null){
				color =  "black";
			}else if(status.equals("A")){
				color =  "lime";
			}else if(status.equals("B")){
				color =  "red";
			}else
				color =  "black";
			userStatus.put(otherCommonService.getChatWindowId(currentUser, userCode).replace(".", ""), color);
		}
		return userStatus;
	}

	@Override
	public String loadPreviuosMessage(String chatWindowId, String userCode, int messageMaxNo, int messageminNo) {
		return commonDAO.loadPreviuosMessage(chatWindowId, userCode, messageMaxNo, messageminNo);
	}
	
	@Override
	public String loadUnseenMessage(String chatWindowId, String userCode){
		return commonDAO.loadUnseenMessage(chatWindowId, userCode);
	}

	@Override
	public Map<String, Object> getFileUploadConfig(String moduleRef) {
		return commonDAO.getFileUploadConfig(moduleRef);
	}
	
	public void saveUploadedFile(String seqNo, String folderName, String uploadModuleRefId, String uploadRef, String moduleUnqNo, 
			MultipartFile multiFile, String userCode, String roleCode, String ipAddress){
		Configuration configuration = UserContextHolder.getUserContext();
		String uploadPath = configuration.getPaths().getIndexingPath()+File.separator+folderName;
		String fileName = null;
		BufferedOutputStream stream = null;
		File uploadFolder = new File(uploadPath);
		if(!uploadFolder.exists())
			uploadFolder.mkdirs();
		try {
	    	fileName = multiFile.getOriginalFilename();	    	
	    	
	        byte[] bytes = multiFile.getBytes();
	        String fullFilePath = uploadFolder + File.separator + fileName;
	        File file = new File(uploadFolder + File.separator + fileName);
	        stream = new BufferedOutputStream(new FileOutputStream(file));
	        stream.write(bytes);
	        stream.close();
	        
	        commonDAO.saveUploadedFile(seqNo, uploadRef, uploadModuleRefId, moduleUnqNo, fileName, 
	        		fullFilePath, new Long(multiFile.getSize()).toString(), file, userCode, roleCode, ipAddress);
		}catch(Exception e){
			log.error("Error while saving uploaded file : "+fileName+" : "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public List<Map<String, String>> processFile(String uploadRefNo, String moduleRefId, String userCode, String userRole, String ipAddress){
		List<Map<String, String>> resultList = new ArrayList<Map<String,String>>();
		
		Map<String, Object> fileUploadConfig = commonDAO.getFileUploadConfig(moduleRefId);
		String DELIMITER = (String)fileUploadConfig.get("DELIMITER");
		String TEMPTABLE = (String)fileUploadConfig.get("TEMPTABLE");
		String PROCESSPROCEDURE = (String)fileUploadConfig.get("PROCESSPROCEDURE");
		
		List<Map<String, String>> filesMap = commonDAO.getFileInfo(uploadRefNo, moduleRefId, null);
		int colCount = (commonDAO.truncateAndGetTempTableColCount(TEMPTABLE) - 4);
		boolean shouldRun = true;
		
		FOR : for(Map<String, String> fileMap : filesMap){
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("SEQNO", fileMap.get("SEQNO"));
			
			if(shouldRun){
				List<List<String>> fileContent = new Vector<List<String>>();
				String filePath = fileMap.get("FILEPATH");
				File file = new File(filePath);
				BufferedReader br = null;
				try{
					br = new BufferedReader(new FileReader(file));
					String currentLine = "";
					int lineCount = 1;
					while((currentLine = br.readLine()) != null){
						String[] content = CommonUtil.splitString(currentLine, DELIMITER);
						if(content.length == colCount){
							List<String> list = new Vector<String>();
							for(String part : content)
								list.add(part);
							fileContent.add(list);
						}else{
							shouldRun = false;
							resultMap.put("STATUS", "0");
							resultMap.put("MESSAGE", "File content is not proper in "+fileMap.get("FILENAME")+" at line no : "+lineCount);
							resultList.add(resultMap);
							continue FOR;
						}
						lineCount++;
					}
					commonDAO.saveUploadFileData(fileContent, TEMPTABLE, colCount, userCode, userRole, ipAddress);
					shouldRun = true;
					resultMap.put("STATUS", "1");
					resultMap.put("MESSAGE", "File Processed");
					resultList.add(resultMap);
				}catch(Exception e){
					e.printStackTrace();
					shouldRun = false;
					resultMap.put("STATUS", "0");
					resultMap.put("MESSAGE", "Error : "+e.getMessage());
					resultList.add(resultMap);
					continue FOR;
				}finally{
					try{
						if(br != null)
							br.close();
					}catch(Exception e){}
				}
			}else{
				resultMap.put("MESSAGE", "Aborted");
				resultMap.put("STATUS", "0");
				resultList.add(resultMap);
				continue FOR;
			}
			
		}
		return resultList;
	}

	@Override
	public List<Map<String, String>> getFileInfo(String uploadRef, String uploadModuleRefId, String moduleUnqNo) {
		return commonDAO.getFileInfo(uploadRef, uploadModuleRefId, moduleUnqNo);
	}

	@Override
	public boolean processCCRUploadedFile(String procName, String uploadRefId,
			String moduleRefId, String month, String year, String userCode,
			String roleCode, String ipAddress) {
		return commonDAO.processCCRUploadedFile(procName, uploadRefId, moduleRefId, month, year, userCode, roleCode, ipAddress);
	}
	
	public Map<String, Object> getFileContentForDownload(String seqNo, String uploadInfo, String moduleUnqNo) throws Exception{
		Map<String, Object> outputFile = new HashMap<String, Object>();
		String rootPath = System.getProperty("user.home")+File.separator+"CompassDownloadFiles";
		File rootDir = new File(rootPath);
		if(!rootDir.exists())
			rootDir.mkdirs();
		
		List<Map<String, Object>> filesContent = commonDAO.getFileContentForDownload(seqNo, uploadInfo, moduleUnqNo);
		if(filesContent.size() == 1){
			String fileName = (String) filesContent.get(0).get("FILENAME");
			byte[] fileContent = (byte[]) filesContent.get(0).get("FILECONTENT");
			
			String filePath = rootPath + File.separator + fileName;
			File newFile = new File(filePath);
			newFile.createNewFile();
			FileUtils.writeByteArrayToFile(newFile, fileContent);
			outputFile.put("FILENAME", fileName);
			outputFile.put("FILE", newFile);
		}else{
			
			String zipFileName = "";
			if(uploadInfo != null && uploadInfo != "")
				zipFileName = uploadInfo;
			if(moduleUnqNo != null && moduleUnqNo != "")
				zipFileName = moduleUnqNo;
			
			String zipFile = rootPath + File.separator + zipFileName+".zip";
			FileOutputStream fos = new FileOutputStream(zipFile);
			ZipOutputStream zos = new ZipOutputStream(fos);
			byte[] buffer = new byte[1024];
			
			for(Map<String, Object> fileInfo : filesContent){
				String fileName = (String) fileInfo.get("FILENAME");
				byte[] fileContent = (byte[]) fileInfo.get("FILECONTENT");
				
				String filePath = rootPath + File.separator + fileName;
				File newFile = new File(filePath);
				newFile.createNewFile();
				FileUtils.writeByteArrayToFile(newFile, fileContent);
				
				FileInputStream fis = new FileInputStream(newFile);
				zos.putNextEntry(new ZipEntry(fileName));
				int length;
				while((length = fis.read(buffer)) > 0)
					zos.write(buffer, 0 , length);
				zos.closeEntry();
				fis.close();
				newFile.delete();
			}
			zos.close();
			outputFile.put("FILENAME", zipFileName+".zip");
			outputFile.put("FILE", new File(zipFile));
		}
		return outputFile;
	}
	
	public Map<String, Object> downloadEmailAttachment(String caseNo, String messageNumber, String attachmentNumebr) throws Exception{
		Map<String, Object> outputFile = new HashMap<String, Object>();
		String rootPath = System.getProperty("user.home")+File.separator+"CompassDownloadFiles";
		File rootDir = new File(rootPath);
		if(!rootDir.exists())
			rootDir.mkdirs();
				
		List<Map<String, Object>> filesContent = commonDAO.downloadEmailAttachment(caseNo, messageNumber, attachmentNumebr);
		if(filesContent.size() == 1){
			String fileName = (String) filesContent.get(0).get("FILENAME");
			byte[] fileContent = (byte[]) filesContent.get(0).get("FILECONTENT");
			
			String filePath = rootPath + File.separator + fileName;
			File newFile = new File(filePath);
			newFile.createNewFile();
			FileUtils.writeByteArrayToFile(newFile, fileContent);
			outputFile.put("FILENAME", fileName);
			outputFile.put("FILE", newFile);
		}else{
			
			String zipFileName = caseNo;
			
			String zipFile = rootPath + File.separator + zipFileName+".zip";
			FileOutputStream fos = new FileOutputStream(zipFile);
			ZipOutputStream zos = new ZipOutputStream(fos);
			byte[] buffer = new byte[1024];
			
			for(Map<String, Object> fileInfo : filesContent){
				String fileName = (String) fileInfo.get("FILENAME");
				byte[] fileContent = (byte[]) fileInfo.get("FILECONTENT");
				
				String filePath = rootPath + File.separator + fileName;
				File newFile = new File(filePath);
				newFile.createNewFile();
				FileUtils.writeByteArrayToFile(newFile, fileContent);
				
				FileInputStream fis = new FileInputStream(newFile);
				zos.putNextEntry(new ZipEntry(fileName));
				int length;
				while((length = fis.read(buffer)) > 0)
					zos.write(buffer, 0 , length);
				zos.closeEntry();
				fis.close();
				newFile.delete();
			}
			zos.close();
			outputFile.put("FILENAME", zipFileName+".zip");
			outputFile.put("FILE", new File(zipFile));
		}
		return outputFile;
	}

	@Override
	public List<Map<String, String>> getFilesInfoForModuleUnqNo(String moduleRefNo) {
		return commonDAO.getFilesInfoForModuleUnqNo(moduleRefNo);
	}

	@Override
	public String removeServerFile(String seqNo, String uploadRefNo, String moduleRefNo, String moduleUnqNo) {
		return commonDAO.removeServerFile(seqNo, uploadRefNo, moduleRefNo, moduleUnqNo);
	}

	@Override
	public List<Map<String, String>> getEmailNotification(String userCode, String seenFlag) {
		return commonDAO.getEmailNotification(userCode, seenFlag);
	}
	
	@Override
	public String getFolderEmailCount(String folderType, String caseNo){
		return commonDAO.getFolderEmailCount(folderType, caseNo);
	}
	
	@Override
	public List<Map<String, String>> getEmailDetails(String caseNo, String folderType, String emailNumber, String userCode){
		return commonDAO.getEmailDetails(caseNo, folderType, emailNumber, userCode);
	}
	
	@Override
	public List<Map<String, String>> getEmailAttachments(String caseNo, String emailNumber){
		return commonDAO.getEmailAttachments(caseNo, emailNumber);
	}

	@Override
	public Map<String, Object> getEmailIdsForMapping(String emailCodes, int escalationOrder) {
		return commonDAO.getEmailIdsForMapping(emailCodes, escalationOrder);
	}
	
	@Override
	public Map<String, String> composeEmail(String caseNo, String emailNo, String folderType, String composeType, String userCode, String userRole){
		return commonDAO.composeEmail(caseNo, emailNo, folderType, composeType, userCode, userRole);
	}
	
	@Override
	public Map<String, Object> getDashboardGraphData(String userCode, String userRole, String ipAddress){
		return commonDAO.getDashboardGraphData(userCode, userRole, ipAddress);
	}
	
	@Override
	public Timestamp convertDateToTimestamp(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.MILLISECOND, 0);
		return new Timestamp(cal.getTimeInMillis());
	}
	
	@Override
	public void auditLog(String userCode, HttpServletRequest request, String moduleAccessed, String accessType, String message){
		HttpSession session = request.getSession(false);
		String entityName = getUserConfiguration().getEntityName();
		String CURRENTROLE = session != null ? (String) session.getAttribute("CURRENTROLE") : "";
		Date loginTime = new Date(session.getCreationTime());
		Date accessTime = new Date();
		String sessionId = session.getId();
		String requestUrl = request.getRequestURI();
		String queryParam = otherCommonService.parseRequestParams(request);
		String ipAddress = request.getRemoteAddr();
		String host = request.getRemoteHost();
		UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
		String browser = "Browser : "+userAgent.getBrowser().getName() +", Version : "+ userAgent.getBrowserVersion().getVersion();
		
		AuditLog auditLog = new AuditLog(userCode, CURRENTROLE, loginTime, sessionId, moduleAccessed, accessType, accessTime, message, requestUrl, queryParam, ipAddress, host, browser);
		commonDAO.storeAuditLogNew(auditLog);
		// AuditLogUtil.add(entityName, auditLog);
	}
	
	public void saveEmailAttachments(String caseNo, MultipartFile multiFile, String refId){
		String fileName = multiFile.getOriginalFilename();
		Configuration configuration = UserContextHolder.getUserContext();
		String saveDirectory = configuration.getPaths().getEmailPath()+File.separator+"EmailDownload"+File.separator+refId;
		try{
			File file = new File(saveDirectory);
			if(!file.exists())
				file.mkdirs();
						
			File convFile = new File(saveDirectory+File.separator+fileName);
		    convFile.createNewFile(); 
		    FileOutputStream fos = new FileOutputStream(convFile); 
		    fos.write(multiFile.getBytes());
		    fos.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public Map<String, String> sendEMail(String caseNo, List<String> toList, List<String> ccList, String subject, String content, String attachmentFolder, String userCode){
		Map<String, String> responseMap = new HashMap<String, String>();
		try{
			String to = "";
			String cc = "";
			
			Configuration configuration = UserContextHolder.getUserContext();
			Connection connection = connectionUtil.getConnection();
			String directory = configuration.getPaths().getEmailPath()+File.separator+"EmailDownload"+File.separator+attachmentFolder;
			File attachmentDirectory = new File(directory);
			String[] attachments = attachmentDirectory.list();

			List<String> fileList = new Vector<String>();
			
			if(attachments != null && attachments.length > 0){
				for(String attachment : attachments){
					String attachmentPath = directory + File.separator + attachment;
					File attachmentFile = new File(attachmentPath);
					if(attachmentFile.exists())
						fileList.add(attachmentPath);
				}
			}
			
			String[] toEmailIds = new String[ toList.size() ];
			toList.toArray( toEmailIds );
			for(String toStr : toEmailIds){
				to = to + toStr +";";
			}
			
			String[] ccEmailIds = new String[ ccList.size() ];
			ccList.toArray( ccEmailIds );
			for(String ccStr : ccEmailIds){
				cc = cc + ccStr +";";
			}
			
			String[] emailAttachments = new String[ fileList.size() ];
			fileList.toArray( emailAttachments );
			
			Map<String, String> systemParameters = initilizingDBSystemParameters();
			String emailPassword = systemParameters.get("EMAILPASSSWORD");
			
			emailSenderUtil.sendEmail(configuration, emailPassword, false, false, toEmailIds, ccEmailIds, null, emailAttachments, subject, content);
			
			String emailInternalNumber = systemBatchJobService.getEmailInternalNumber(connection);
			systemBatchJobService.saveEmailDetails(connection, caseNo, attachmentFolder, emailInternalNumber, emailInternalNumber, configuration.getEmail().getAmlEmail().getAmlEmailId().getValue(), 
					to, cc, "", subject, otherCommonService.getFormattedDate(new Date(), "dd/MM/yyyy HH:mm:ss"), null, content, userCode, "SENT");
			systemBatchJobService.saveEmailAttachment(connection, caseNo, attachmentFolder, emailInternalNumber, emailInternalNumber, fileList, userCode, "SENT");
			
			for(String file : fileList){
				File f = new File(file);
				f.delete();
			}
			File f = new File(directory);
			f.delete();
			
			responseMap.put("STATUS", "1");
			responseMap.put("MSG", "Email Sent");
		}catch(Exception e){
			responseMap.put("STATUS", "0");
			responseMap.put("MSG", e.getMessage());
			e.printStackTrace();
		}
		return responseMap;
	}
	
	@Override
	public List<Map<String, String>> getCountWiseModules(String userCode, String ipAddress) {
		return commonDAO.getCountWiseModules(userCode, ipAddress);
	}
}
