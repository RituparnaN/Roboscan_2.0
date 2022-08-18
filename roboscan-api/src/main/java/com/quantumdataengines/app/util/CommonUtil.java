//package com.quantumdataengines.app.util;
//
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.sql.Clob;
//import java.sql.SQLException;
//
//import com.jcraft.jsch.Channel;
//import com.jcraft.jsch.ChannelSftp;
//import com.jcraft.jsch.JSch;
//import com.jcraft.jsch.Session;
//
//
//public class CommonUtil {
//	public static String[] splitString(String stringToSplit, String delimiter) {
//        String[] aRet;
//        int iLast;
//        int iFrom;
//        int iFound;
//        int iRecords;
//
//        // return Blank Array if stringToSplit == "")
//        if (stringToSplit.equals("")) {
//            return new String[0];
//        }
//
//        // count Field Entries
//        iFrom = 0;
//        iRecords = 0;
//        while (true) {
//            iFound = stringToSplit.indexOf(delimiter, iFrom);
//            if (iFound == -1) {
//                break;
//            }
//            iRecords++;
//            iFrom = iFound + delimiter.length();
//        }
//        iRecords = iRecords + 1;
//
//        // populate aRet[]
//        aRet = new String[iRecords];
//        if (iRecords == 1) {
//            aRet[0] = stringToSplit;
//        } else {
//            iLast = 0;
//            iFrom = 0;
//            iFound = 0;
//            for (int i = 0; i < iRecords; i++) {
//                iFound = stringToSplit.indexOf(delimiter, iFrom);
//                if (iFound == -1) { // at End
//                    aRet[i] = stringToSplit.substring(iLast + delimiter.length(), stringToSplit.length());
//                } else if (iFound == 0) { // at Beginning
//                    aRet[i] = "";
//                } else { // somewhere in middle
//                    aRet[i] = stringToSplit.substring(iFrom, iFound);
//                }
//                iLast = iFound;
//                iFrom = iFound + delimiter.length();
//            }
//        }
//        return aRet;
//    }
//	
//	public static String changeColumnName(String actualColumnName){
//		String revisedColumnName = "";
//		if(actualColumnName.contains("app."))
//			revisedColumnName = actualColumnName;
//		else
//			revisedColumnName = "app.common."+actualColumnName;
////		return revisedColumnName;
//		return actualColumnName;
//	}
//	
//	public static boolean validateEmailAddress(String emailAddress) {
//		Pattern  regexPattern = Pattern.compile("^[(a-zA-Z-0-9-\\_\\+\\.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}$");
//		Matcher regMatcher   = regexPattern.matcher(emailAddress);
//	    if(regMatcher.matches()){
//	        return true;
//	    } else {
//	    return false;
//	    }
//	}
//	
//	public String getColumnId(String columnName){
//		String[] columnArr = splitString(columnName, ".");
//		int length = columnArr.length;
//		return columnArr[length - 1];
//	}
//	
//
//	
//	public static boolean writeFile(String fileContent, String outputFile) throws Exception{
//		boolean isFileWritten = false;
//		BufferedWriter bufferedWriter = null;
//		try{
//			File outputFilePath = new File(outputFile);
//			
//			if(!outputFilePath.exists())
//				outputFilePath.createNewFile();
//			
//			bufferedWriter = new BufferedWriter(new FileWriter(outputFilePath));
//			bufferedWriter.write(fileContent);
//			bufferedWriter.close();
//			bufferedWriter = null;
//			isFileWritten = true;
//		}catch(Exception e){
//			e.printStackTrace();
//			throw e;
//		}
//		return isFileWritten;
//	}
//	
//	public static boolean writeFile(String fileContent, String outputPath, String outputFile, String ftpHost, String ftpPort, String ftpUsername, String ftpPassword){
//		boolean isFileWritten = false;
//		try{
//			String[] dirTypeArr = CommonUtil.splitString(outputPath, ":");
//			if(dirTypeArr.length == 2){
//				if(dirTypeArr[0].equalsIgnoreCase("ftp") || dirTypeArr[0].equalsIgnoreCase("sftp")){
//					Session session = connectSFTP(ftpHost, ftpPort, ftpUsername, ftpPassword);
//					ChannelSftp sftpChannel = getChannelSftp(session);
//					sftpChannel.cd(dirTypeArr[1]);
//
//					OutputStream os = sftpChannel.put(outputFile);
//					os.write(fileContent.getBytes());
//					os.flush();
//					os.close();
//					sftpChannel.disconnect();
//					session.disconnect();
//					isFileWritten = true;
//				}else{
//					outputPath = outputPath + File.separator + outputFile;
//					BufferedWriter bufferedWriter = null;
//					File outputFilePath = new File(outputPath);
//			
//					if(!outputFilePath.exists())
//						outputFilePath.createNewFile();
//			
//					bufferedWriter = new BufferedWriter(new FileWriter(outputFilePath));
//					bufferedWriter.write(fileContent);
//					bufferedWriter.close();
//					bufferedWriter = null;
//					isFileWritten = true;
//				}
//			} else {
//				// System.out.println("Input path "+outputPath+" is not valid. It must contain only one ':' to mention drive or FTP");
//				outputPath = outputPath + File.separator + outputFile;
//				BufferedWriter bufferedWriter = null;
//				File outputFilePath = new File(outputPath);
//		
//				if(!outputFilePath.exists())
//					outputFilePath.createNewFile();
//		
//				bufferedWriter = new BufferedWriter(new FileWriter(outputFilePath));
//				bufferedWriter.write(fileContent);
//				bufferedWriter.close();
//				bufferedWriter = null;
//				isFileWritten = true;
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return isFileWritten;
//	}
//	
//	private static Session connectSFTP(String host, String port, String username, String password) throws Exception{
//		Session session = null;
//	//	System.out.println("Initializing FTP Server...");
//		JSch jsch = new JSch();
//		session = jsch.getSession(username, host, Integer.parseInt(port));
//		session.setConfig("StrictHostKeyChecking", "no");
//		session.setPassword(password);
//		System.out.println("Connecting "+host+"...");
//		session.connect();
//		if(session.isConnected())
//			System.out.println("Connected "+host+".");
//		else
//			System.out.println("Countn't connect "+host+" on port "+port+".");
//		return session;
//	}
//	
//	private static ChannelSftp getChannelSftp(Session session) throws Exception{
//		ChannelSftp sftpChannel = null;
//		Channel channel = null;
//	//	System.out.println("Opening Channel for FTP...");
//		channel = session.openChannel("sftp");
//		channel.connect();
//		sftpChannel = (ChannelSftp) channel;
//	//	System.out.println("Channel connected and open for FTP.");
//		return sftpChannel;
//	}
//	
//	public static String clobStringConversion(Clob clb) throws IOException, SQLException{
//	      if (clb == null)
//	    	  return  "";
//	            
//	      StringBuffer str = new StringBuffer();
//	      String strng;
//	      BufferedReader bufferRead = new BufferedReader(clb.getCharacterStream());
//	      while ((strng=bufferRead .readLine())!=null)
//	       str.append(strng);
//	   
//	      return str.toString();
//	}
//}

package com.quantumdataengines.app.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.Clob;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;


@Component
public class CommonUtil {
	
	public String generateToken() {
		return generateUUID();
	}
	
	public static String generateUUID() {
		return UUID.randomUUID().toString();
	}
	
	public static String generateUUID(String input) {
		try {
			return UUID.nameUUIDFromBytes(input.getBytes("UTF-8")).toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}	
	
	//Vivek - md5 hash generation code
	public String generateMD5Hash(String password) {
		try {
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        byte[] array = md.digest(password.getBytes());
	        StringBuffer stringBuffer = new StringBuffer();
	        for (int i = 0; i < array.length; ++i) {
	        	stringBuffer.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
	       }
	        return stringBuffer.toString();
	    } catch (java.security.NoSuchAlgorithmException e) {
	    }
	    return null;
	}
	
//	public static void main (String [] args) throws Exception {
//		System.out.println(decrypt(Base64.getDecoder().decode("d/cTvtYX3htdohXVerSUdgCB85hgZULOp5tcDpifaJDHbm9o52Xz8ymvrQ==")));
//	}
	
	//VIVEK - PASSWORD DECRYPTOR
	public static SecretKey generateSecretKey(String password, byte [] iv) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), iv, 65536, 128);
        return new SecretKeySpec(secretKeyFactory.generateSecret(spec).getEncoded(), "AES");
    }

    public static String decrypt(byte[] cipherText) throws Exception {
    	String secretKeyProp = "QDEKEY1234543210";
        ByteBuffer byteBuffer = ByteBuffer.wrap(cipherText);
        byte[] iv = new byte[16];
        byteBuffer.get(iv);

        SecretKey secretKey = generateSecretKey(secretKeyProp, iv);
        byte[] cipherBytes = new byte[byteBuffer.remaining()];
        byteBuffer.get(cipherBytes);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec);
        cipher.update(cipherBytes);
        return new String(cipher.doFinal());
    }
	//

	public boolean stringToBoolean(String value) {
		return "Y".equalsIgnoreCase(value)? true:false;
	}
	
	public String booleanToString(boolean value) {
		if(value) {
			return "Y";
		}else {
			return "N";
		}
	}
	
	public static String changeColumnName(String actualColumnName){
		String revisedColumnName = "";
		if(actualColumnName.contains("app."))
			revisedColumnName = actualColumnName;
		else
			revisedColumnName = "app.common."+actualColumnName;
		return revisedColumnName;
	}
	
	public static String[] splitString(String stringToSplit, String delimiter) {
        String[] aRet;
        int iLast;
        int iFrom;
        int iFound;
        int iRecords;

        // return Blank Array if stringToSplit == "")
        if (stringToSplit.equals("")) {
            return new String[0];
        }

        // count Field Entries
        iFrom = 0;
        iRecords = 0;
        while (true) {
            iFound = stringToSplit.indexOf(delimiter, iFrom);
            if (iFound == -1) {
                break;
            }
            iRecords++;
            iFrom = iFound + delimiter.length();
        }
        iRecords = iRecords + 1;

        // populate aRet[]
        aRet = new String[iRecords];
        if (iRecords == 1) {
            aRet[0] = stringToSplit;
        } else {
            iLast = 0;
            iFrom = 0;
            iFound = 0;
            for (int i = 0; i < iRecords; i++) {
                iFound = stringToSplit.indexOf(delimiter, iFrom);
                if (iFound == -1) { // at End
                    aRet[i] = stringToSplit.substring(iLast + delimiter.length(), stringToSplit.length());
                } else if (iFound == 0) { // at Beginning
                    aRet[i] = "";
                } else { // somewhere in middle
                    aRet[i] = stringToSplit.substring(iFrom, iFound);
                }
                iLast = iFound;
                iFrom = iFound + delimiter.length();
            }
        }
        return aRet;
    }

	public LocalDateTime convertStringToLocalDateTime(String timestamp) {
		return LocalDateTime.parse(timestamp, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
	}
	
	public String convertLocalDateTimeToString(LocalDateTime localDateTime) {
		return localDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
	}
	
	
	
	
	public static boolean validateEmailAddress(String emailAddress) {
		Pattern  regexPattern = Pattern.compile("^[(a-zA-Z-0-9-\\_\\+\\.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}$");
		Matcher regMatcher   = regexPattern.matcher(emailAddress);
	    if(regMatcher.matches()){
	        return true;
	    } else {
	    return false;
	    }
	}
	
	public String getColumnId(String columnName){
		String[] columnArr = splitString(columnName, ".");
		int length = columnArr.length;
		return columnArr[length - 1];
	}
	

	
	public static boolean writeFile(String fileContent, String outputFile) throws Exception{
		boolean isFileWritten = false;
		BufferedWriter bufferedWriter = null;
		try{
			File outputFilePath = new File(outputFile);
			
			if(!outputFilePath.exists())
				outputFilePath.createNewFile();
			
			bufferedWriter = new BufferedWriter(new FileWriter(outputFilePath));
			bufferedWriter.write(fileContent);
			bufferedWriter.close();
			bufferedWriter = null;
			isFileWritten = true;
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return isFileWritten;
	}
	
	public static boolean writeFile(String fileContent, String outputPath, String outputFile, String ftpHost, String ftpPort, String ftpUsername, String ftpPassword){
		boolean isFileWritten = false;
		try{
			String[] dirTypeArr = CommonUtil.splitString(outputPath, ":");
			if(dirTypeArr.length == 2){
				if(dirTypeArr[0].equalsIgnoreCase("ftp") || dirTypeArr[0].equalsIgnoreCase("sftp")){
					Session session = connectSFTP(ftpHost, ftpPort, ftpUsername, ftpPassword);
					ChannelSftp sftpChannel = getChannelSftp(session);
					sftpChannel.cd(dirTypeArr[1]);

					OutputStream os = sftpChannel.put(outputFile);
					os.write(fileContent.getBytes());
					os.flush();
					os.close();
					sftpChannel.disconnect();
					session.disconnect();
					isFileWritten = true;
				}else{
					outputPath = outputPath + File.separator + outputFile;
					BufferedWriter bufferedWriter = null;
					File outputFilePath = new File(outputPath);
			
					if(!outputFilePath.exists())
						outputFilePath.createNewFile();
			
					bufferedWriter = new BufferedWriter(new FileWriter(outputFilePath));
					bufferedWriter.write(fileContent);
					bufferedWriter.close();
					bufferedWriter = null;
					isFileWritten = true;
				}
			} else {
				// System.out.println("Input path "+outputPath+" is not valid. It must contain only one ':' to mention drive or FTP");
				outputPath = outputPath + File.separator + outputFile;
				BufferedWriter bufferedWriter = null;
				File outputFilePath = new File(outputPath);
		
				if(!outputFilePath.exists())
					outputFilePath.createNewFile();
		
				bufferedWriter = new BufferedWriter(new FileWriter(outputFilePath));
				bufferedWriter.write(fileContent);
				bufferedWriter.close();
				bufferedWriter = null;
				isFileWritten = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return isFileWritten;
	}
	
	private static Session connectSFTP(String host, String port, String username, String password) throws Exception{
		Session session = null;
	//	System.out.println("Initializing FTP Server...");
		JSch jsch = new JSch();
		session = jsch.getSession(username, host, Integer.parseInt(port));
		session.setConfig("StrictHostKeyChecking", "no");
		session.setPassword(password);
		System.out.println("Connecting "+host+"...");
		session.connect();
		if(session.isConnected())
			System.out.println("Connected "+host+".");
		else
			System.out.println("Countn't connect "+host+" on port "+port+".");
		return session;
	}
	
	private static ChannelSftp getChannelSftp(Session session) throws Exception{
		ChannelSftp sftpChannel = null;
		Channel channel = null;
	//	System.out.println("Opening Channel for FTP...");
		channel = session.openChannel("sftp");
		channel.connect();
		sftpChannel = (ChannelSftp) channel;
	//	System.out.println("Channel connected and open for FTP.");
		return sftpChannel;
	}
	
	public static String clobStringConversion(Clob clb) throws IOException, SQLException{
	      if (clb == null)
	    	  return  "";
	            
	      StringBuffer str = new StringBuffer();
	      String strng;
	      BufferedReader bufferRead = new BufferedReader(clb.getCharacterStream());
	      while ((strng=bufferRead .readLine())!=null)
	       str.append(strng);
	   
	      return str.toString();
	}

}

