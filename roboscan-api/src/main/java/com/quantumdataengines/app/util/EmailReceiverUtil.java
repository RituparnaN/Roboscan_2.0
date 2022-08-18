package com.quantumdataengines.app.util;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Header;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;
import javax.mail.search.AndTerm;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SentDateTerm;
import javax.mail.search.SubjectTerm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quantumdataengines.app.batchservice.SystemBatchJobService;
import com.quantumdataengines.app.schema.Configuration;

@Component
public class EmailReceiverUtil {
	
	private static final Logger log = LoggerFactory.getLogger(EmailReceiverUtil.class);
	@Autowired
	private CompassEncryptorDecryptor encryptorDecryptor; 
	@Autowired
	private EmailRefreshUtil emailRefreshUtil;
	@Autowired
	private SystemBatchJobService systemBatchJobService;
	@Autowired
	private ConnectionUtil connectionUtil;
	
	public void receiveEmail(Connection connection, Configuration configuration, String updatedBy, String emailPassword, 
			int lookupDays, String folderName, String folderType, String searchString, String subJectEscapeString) throws Exception{
		String decPassword = encryptorDecryptor.decrypt(emailPassword);
		try{
			String protocol = configuration.getEmail().getImapProtocol().value();
			String hostIp = configuration.getEmail().getImapHost().getValue();
			String port = configuration.getEmail().getImapPort().toString();
			String username = configuration.getEmail().getAmlEmail().getAmlAuthId().getValue();
			String fromId = configuration.getEmail().getAmlEmail().getAmlEmailId().getValue();
			String saveDirectory = configuration.getPaths().getEmailPath()+File.separator+"EmailDownload";
			try{
				File file = new File(saveDirectory);
				if(!file.exists())
					file.mkdirs();
			}catch(Exception e){
				e.printStackTrace();
			}
			int noOfCompleteCount = 0;
			System.out.println("protocol:        "+protocol);
        	Properties props = System.getProperties();
            props.setProperty("mail.store.protocol", protocol);
            // props.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");
            Session session = Session.getDefaultInstance(props, null);
            Store store = session.getStore(protocol);
            System.out.println(username+" "+decPassword);
            store.connect(hostIp, username, decPassword);
            Folder folderInbox = store.getFolder(folderName);
            folderInbox.open(Folder.READ_ONLY);
            
            log.info("Email server "+hostIp+" connected. "+folderName+" opened");
            
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, -(lookupDays));            
            SearchTerm subjectTerm = new SubjectTerm(searchString);
            SearchTerm sentDateterm = new SentDateTerm(ComparisonTerm.GT,new java.sql.Date(calendar.getTimeInMillis()));
            AndTerm andTerms = new AndTerm(sentDateterm,subjectTerm);
            Message[] arrayMessages = folderInbox.search(andTerms);
            emailRefreshUtil.getEmailRefreshDetails().get(configuration.getEntityName()).setNoOfEmailFound(arrayMessages.length);
            log.info("Found "+arrayMessages.length+" emails to process");
            if(arrayMessages.length > 0){
            	for(Message message : arrayMessages){
            		String recipientsTO = "";
            		String recipientsCC = "";
            		String recipientsBCC = "";
            		String messageId = "";
            		
            		String messageNumber = new Integer(message.getMessageNumber()).toString();
            		Address[] fromAddress = message.getFrom();
            		String from = fromAddress[0].toString();
            		
            		Address[] recipientsAddressTO = message.getRecipients(Message.RecipientType.TO);
                    Address[] recipientsAddressCC = message.getRecipients(Message.RecipientType.CC);
                    Address[] recipientsAddressBCC = message.getRecipients(Message.RecipientType.BCC);
                    
                    if(recipientsAddressTO != null)
                        for(int count = 0; count < recipientsAddressTO.length; count++)
                        	recipientsTO = recipientsTO+recipientsAddressTO[count].toString()+";";
                    
                    if(recipientsAddressCC != null)
                        for(int count = 0; count < recipientsAddressCC.length; count++)
                        	recipientsCC = recipientsCC+recipientsAddressCC[count].toString()+";";
                    
                    if(recipientsAddressBCC != null)
                        for(int count = 0; count < recipientsAddressBCC.length; count++)
                        	recipientsBCC = recipientsBCC+recipientsAddressBCC[count].toString()+";";
                    
                    @SuppressWarnings("unchecked")
					Enumeration<Header> headers = message.getAllHeaders();
                    while (headers.hasMoreElements()) {
                       Header header = (Header) headers.nextElement();
                       if(header.getName().equalsIgnoreCase("Message-ID"))
                    	   messageId = header.getValue().trim();
                    }
                    
                    String subject = message.getSubject();
                    String caseNo = getCaseNo(subject, searchString);
                    String sentDate = systemBatchJobService.getFormattedDate(message.getSentDate(), "dd/MM/yyyy HH:mm:ss");
                    String receiveDate = message.getReceivedDate() == null ? "" : systemBatchJobService.getFormattedDate(message.getReceivedDate(), "dd/MM/yyyy HH:mm:ss");;
                    String contentType = message.getContentType();
                    String messageContent = "";
                    List<String> attachFiles = new ArrayList<String>();
            		try{
            			if (contentType.contains("multipart")) {
            				Multipart multiPart = (Multipart) message.getContent();                        
                            int numberOfParts = multiPart.getCount();
                            for (int partCount = 0; partCount < numberOfParts; partCount++) {
                            	MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
                                if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                                	String fileName = "";
                                	if(part.getFileName() != null) {
                                        fileName = (part.getFileName() == "null" || part.getFileName().equalsIgnoreCase("null")) ? (caseNo+"_"+System.currentTimeMillis()) : part.getFileName();
                                        String fullFilePath = saveDirectory + File.separator + fileName;
                                        attachFiles.add(fullFilePath);
                                        part.saveFile(fullFilePath);
                                    } else {
                                    	String fullFilePath = saveDirectory + File.separator + (caseNo+"_"+System.currentTimeMillis());
                                    	attachFiles.add(fullFilePath);
                                    	part.saveFile(fullFilePath);
                                    }
                                } else {
                                	messageContent = messageContent + getText(part) != null ? getText(part) : "";
                                }
                            }
                        } else if (contentType.contains("text/plain") || contentType.contains("text/html")) {
                        	messageContent = message.getContent() != null ? (String) message.getContent() : "";
                        }
            		}catch(Exception e){
            			e.printStackTrace();
            		}
            		
            		connection = connectionUtil.getConnection();
            		if(systemBatchJobService.checkEmailAlreadyStored(connection, caseNo, messageId, folderType)){
            			emailRefreshUtil.getEmailRefreshDetails().get(configuration.getEntityName()).setStatusMessage("Email for case no : "+caseNo+" is upto date");
            		}else{
            			String emailInternalNumber = systemBatchJobService.getEmailInternalNumber(connection);
            			emailRefreshUtil.getEmailRefreshDetails().get(configuration.getEntityName()).setStatusMessage("Storing Email for case no : "+caseNo);
            			systemBatchJobService.saveEmailDetails(connection, caseNo, messageId, messageNumber, emailInternalNumber, from, recipientsTO, recipientsCC, recipientsBCC, subject, sentDate, receiveDate, messageContent, updatedBy, folderType);
            			systemBatchJobService.saveEmailAttachment(connection, caseNo, messageId, messageNumber, emailInternalNumber, attachFiles, updatedBy, folderType);
            		}
            		connectionUtil.closeResources(connection, null, null);
            		noOfCompleteCount++;
                	emailRefreshUtil.getEmailRefreshDetails().get(configuration.getEntityName()).setNoOfComplete(noOfCompleteCount);
                	int percentage = (noOfCompleteCount * 100) / arrayMessages.length;
                	emailRefreshUtil.getEmailRefreshDetails().get(configuration.getEntityName()).setPercentage(percentage);
            	}
            }else{
            	emailRefreshUtil.getEmailRefreshDetails().get(configuration.getEntityName()).setNoOfComplete(0);
                emailRefreshUtil.getEmailRefreshDetails().get(configuration.getEntityName()).setStatusMessage("Emails searched.");
            }
            folderInbox.close(false);
			store.close();
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	
	private String getText(Part part) throws MessagingException, IOException {
		if (part.isMimeType("text/*")) {
			return (String) part.getContent();
		}
		
		if (part.isMimeType("multipart/alternative")) {
			Multipart multipart = (Multipart) part.getContent();
			String text = null;
			for (int i = 0; i < multipart.getCount(); i++) {
				Part bodyPart = multipart.getBodyPart(i);
				if (bodyPart.isMimeType("text/plain")) {
					if (text == null)
						text = getText(bodyPart);
					continue;
				} else {
					if (bodyPart.isMimeType("text/html")) {
						String s = getText(bodyPart);
						if (s != null)
							return s;
					} else {
						return getText(bodyPart);
					}
				}
			}
			return text;
		} else {
			if (part.isMimeType("multipart/*")) {
				Multipart multipart = (Multipart) part.getContent();
				for (int i = 0; i < multipart.getCount(); i++) {
					String s = getText(multipart.getBodyPart(i));
					if (s != null)
						return s;
				}
			}
		}
		return null;
	}
	
	private boolean isSubjectToBeConsider(String subject, String subjectsToEscape){
    	boolean isValid = true;
		String[] arrPhases = subjectsToEscape.split(",");
		for(String strPhases : arrPhases){
			if(isValid){
				if(subject.toLowerCase().startsWith(strPhases.toLowerCase().trim()))
					isValid = false;
			}
		}
		return isValid;
    }
	
	private String getCaseNo(String l_strMessageSubject, String searchString) throws Exception{
    	int CaseNoIndex = l_strMessageSubject.indexOf(searchString);
    	String l_strMessageSubjectSub = l_strMessageSubject.substring(CaseNoIndex+searchString.length());
    	return l_strMessageSubjectSub.substring(0, l_strMessageSubjectSub.indexOf(",")).trim();
    }
}