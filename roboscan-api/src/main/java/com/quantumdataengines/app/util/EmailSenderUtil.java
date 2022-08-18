package com.quantumdataengines.app.util;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.quantumdataengines.app.schema.Configuration;

@Component
public class EmailSenderUtil {
	private static final Logger log = LoggerFactory.getLogger(EmailSenderUtil.class);
	@Autowired
	private CompassEncryptorDecryptor encryptorDecryptor; 
	
	public void sendEmail(Configuration configuration, String password, boolean isTest, boolean isETLEmail, 
			String[] recpTO, String[] recpCC, String[] recpBCC, String[] attachFile, String subject, String content) throws Exception {
		String decPassword = encryptorDecryptor.decrypt(password);
		String smtpHost = configuration.getEmail().getSmtpHost().getValue();
		int smtpPort = configuration.getEmail().getSmtpPort().getValue();
		String authId = "";
		String fromId = "";
		if(isETLEmail){
			authId = configuration.getEmail().getEtlEmail().getEtlAuthId().getValue();
			fromId = configuration.getEmail().getEtlEmail().getEtlEmailId().getValue();
		}else{
			authId = configuration.getEmail().getAmlEmail().getAmlAuthId().getValue();
			fromId = configuration.getEmail().getAmlEmail().getAmlEmailId().getValue();
		}
		
		JavaMailSenderImpl emailSender = getJavaEmailSender(smtpHost, smtpPort, authId, decPassword);
		MimeMessage mimeMessage = emailSender.createMimeMessage();
		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setFrom(fromId);
			log.info("Email will be sent from "+fromId);
			
			if(isTest){
				InternetAddress[] inetAddrTO = new InternetAddress[1];
				inetAddrTO[0] = new InternetAddress(fromId);
				mimeMessageHelper.setTo(inetAddrTO);
			}else{
				if(recpTO == null){
					throw new NullPointerException("To address should be mentioed");
				}else{
					InternetAddress[] inetAddrTO = new InternetAddress[recpTO.length];
					for(int i = 0; i < recpTO.length; i++)
						inetAddrTO[i] = new InternetAddress(recpTO[i]);
					mimeMessageHelper.setTo(inetAddrTO);
				}
			}
			
			
			if(recpCC != null){
				InternetAddress[] inetAddrCC = new InternetAddress[recpCC.length];
				for(int i = 0; i < recpCC.length; i++)
					inetAddrCC[i] = new InternetAddress(recpCC[i]);
				mimeMessageHelper.setCc(inetAddrCC);
			}
			
			if(recpBCC != null){
				InternetAddress[] inetAddrBCC = new InternetAddress[recpBCC.length];
				for(int i = 0; i < recpBCC.length; i++)
					inetAddrBCC[i] = new InternetAddress(recpBCC[i]);
				mimeMessageHelper.setBcc(inetAddrBCC);
			}
			
			
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setText(content, true);
			
			if(attachFile != null){
				for(String file : attachFile){
					log.info("Attaching file["+file+"] with email...");
					FileSystemResource fileSystemResource = new FileSystemResource(file);
					mimeMessageHelper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);
				}
			}
			
			log.debug("Email is about to send...");
			emailSender.send(mimeMessage);
			log.info("Email Sent successfully.");
		} catch (MessagingException e) {
			log.error("Error while sending email : "+e.getMessage());
			throw e;
		}
	}

	private JavaMailSenderImpl getJavaEmailSender(String smtpHost, int smtpPort, String authId, String password) throws Exception {
		log.info("Creating mailer[HOST : "+smtpHost+", PORT : "+smtpPort+", AuthID : "+authId+"]");
		JavaMailSenderImpl javaEmailSender = new JavaMailSenderImpl();
		javaEmailSender.setHost(smtpHost);
		javaEmailSender.setPort(smtpPort);
		javaEmailSender.setUsername(authId);
		javaEmailSender.setPassword(password);
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.mime.multipart.allowempty", "true");
		javaEmailSender.setJavaMailProperties(props);
		return javaEmailSender;
	}
}
