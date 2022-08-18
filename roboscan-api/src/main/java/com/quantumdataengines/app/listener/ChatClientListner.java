package com.quantumdataengines.app.listener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.quantumdataengines.app.model.ChatMessage;
import com.quantumdataengines.app.schema.Configuration;
import com.quantumdataengines.app.util.ChatMessageHolder;
import com.quantumdataengines.app.util.ChatUserContextHolder;

public class ChatClientListner {
	private static final Logger log = LoggerFactory.getLogger(ChatClientListner.class);
	
	public ChatClientListner(Configuration configuration, String userCode, String userName){
		try{
			Socket socket = new Socket("0.0.0.0",8189);
			log.info(userCode+" is logging into chat server");
			Thread clientThread = readServerResponse(socket, configuration);
			clientThread.start();
			
			Map<String, Object> chatClientMap = new HashMap<String, Object>();
			chatClientMap.put("USERCODE", userCode);
			chatClientMap.put("USERNAME", userName);
			chatClientMap.put("CLIENTSOCKET", socket);
			chatClientMap.put("THREAD", clientThread);
			ChatUserContextHolder.setUserContext(configuration.getEntityName(), userCode, chatClientMap);
			
			PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
			out.println("LOGIN%;%"+configuration.getEntityName()+"%;%"+userCode);
			log.info(userCode+" logged into chat client");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public Thread readServerResponse(final Socket socket, final Configuration configuration){
		Thread clientThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try{
					while(true){
						BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						String response = "";
						while ((response = br.readLine()) != null) {
							String[] messagePerser = response.split("%;%");
							
							
							if(messagePerser[0].equals("CHATMESSAGE")){
								String userFrom = messagePerser[2];
								String userTo = messagePerser[3];
								String userToName = messagePerser[4];
								String chatWindowId = messagePerser[5];
								String messageId = messagePerser[6];
								String chatMessage = messagePerser[7];
								String sendTime = messagePerser[8];
								String receiveTime = messagePerser[9];
								
								ChatMessage chatMessageObj = new ChatMessage();
								chatMessageObj.setChatWindowId(chatWindowId);
								chatMessageObj.setFromUser(userFrom);
								chatMessageObj.setToUser(userTo);
								chatMessageObj.setToUserName(userToName);
								chatMessageObj.setMessageId(messageId);
								chatMessageObj.setMessageContent(chatMessage);
								chatMessageObj.setMessageSentDate(sendTime);
								chatMessageObj.setMessageReceiveDate(receiveTime);
								
								ChatMessageHolder.storeOnlineMessage(chatWindowId, messageId, chatMessageObj);
							}
							
						}
					}
				}catch(Exception e){
					e.printStackTrace();
				}					
			}
		});
		return clientThread;
	}
}
