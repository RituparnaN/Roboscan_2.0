package com.quantumdataengines.app.util;

import java.security.MessageDigest;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@Component
public class CompassEncryptorDecryptor {
	
	private static final Logger log = LoggerFactory.getLogger(CompassEncryptorDecryptor.class);
	private	 static final String UNICODE_FORMAT = "UTF8";
    private static final String DES_ENCRYPTION_SCHEME = "DES";
    private static KeySpec m_keySpec;
    private static SecretKeyFactory m_secretKeyFactory;
    private static Cipher m_cipher;
    private static byte[] m_keyAsBytes;
    private static String m_encryptionKey;
    private static String m_encryptionScheme;
    static SecretKey key;
    
    static{
    	m_encryptionKey = "QDECOMPASS";
        m_encryptionScheme = DES_ENCRYPTION_SCHEME;
        try {
			m_keyAsBytes = m_encryptionKey.getBytes(UNICODE_FORMAT);		
	        m_keySpec = new DESKeySpec(m_keyAsBytes);
	        m_secretKeyFactory = SecretKeyFactory.getInstance(m_encryptionScheme);
	        m_cipher = Cipher.getInstance(m_encryptionScheme);
	        key = m_secretKeyFactory.generateSecret(m_keySpec);
        } catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public String encrypt(String a_strUnencryptedString) {
        String l_strEncryptedString = null;
        try {
        	m_cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] plainText = a_strUnencryptedString.getBytes(UNICODE_FORMAT);
            byte[] encryptedText = m_cipher.doFinal(plainText);
            BASE64Encoder base64encoder = new BASE64Encoder();
            l_strEncryptedString = base64encoder.encode(encryptedText);
        } catch (Exception e) {
        	log.error("Error occured : "+e.getMessage());
            e.printStackTrace();
        }
        return l_strEncryptedString;
    }
    
    public String decrypt(String a_strEncryptedString) {
        String l_strEnecryptedText=null;
        try {
        	m_cipher.init(Cipher.DECRYPT_MODE, key);
            BASE64Decoder base64decoder = new BASE64Decoder();
            byte[] encryptedText = base64decoder.decodeBuffer(a_strEncryptedString);
            byte[] plainText = m_cipher.doFinal(encryptedText);
            l_strEnecryptedText= bytes2String(plainText);
        } catch (Exception e) {
        	log.error("Error occured : "+e.getMessage());
            e.printStackTrace();
        }
        return l_strEnecryptedText;
    }
    
    private static String bytes2String(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            stringBuffer.append((char) bytes[i]);
        }
        return stringBuffer.toString();
    }
    
    public String hashedPassword(String password, String algorithm){
		String hash = "";
		try{
        MessageDigest md = MessageDigest.getInstance(algorithm);
        md.update(password.getBytes());
        byte[] bytes = md.digest();
		BASE64Encoder base64encoder = new BASE64Encoder();
        hash = base64encoder.encode(bytes);
		}catch(Exception e){
			e.printStackTrace();
		}
		return '{'+algorithm+'}'+hash;
	}
    
    public static void main(String args[]){
    	CompassEncryptorDecryptor decryptor = new CompassEncryptorDecryptor();
    	System.out.println(decryptor.encrypt("compass@123"));
    	System.out.println(decryptor.decrypt("o58tGh8lrzjd/1xQcNsjkw=="));
    }
}