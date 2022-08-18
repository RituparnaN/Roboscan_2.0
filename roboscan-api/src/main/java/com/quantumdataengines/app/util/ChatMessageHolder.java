package com.quantumdataengines.app.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import com.quantumdataengines.app.model.ChatMessage;


public class ChatMessageHolder {
	private static Map<String, Map<String, Map<String, ChatMessage>>> chatOnlineReceiverMessageHolder = new LinkedHashMap<String, Map<String, Map<String, ChatMessage>>>();
	
	private static Map<String, Map<String, ChatMessage>> chatOfflineSenderMessageHolder = new LinkedHashMap<String, Map<String, ChatMessage>>();
	private static List<String> allMessage = new CopyOnWriteArrayList<String>();
	private static Map<String, SeenFlag> messageSeenFlags = new HashMap<String, SeenFlag>();
	
	private ChatMessageHolder(){}
	
	public static synchronized void storeOfflineMessage(String chatWindowId, String messageId, ChatMessage chatMessage){		
		Map<String, ChatMessage> chatMap = new LinkedHashMap<String, ChatMessage>();
		chatMap.put(chatWindowId, chatMessage);
		chatOfflineSenderMessageHolder.put(messageId, chatMap);
		allMessage.add(messageId);
		
		SeenFlag seenFlag = new SeenFlag(false, true);
		messageSeenFlags.put(messageId, seenFlag);
	}
	
	public static synchronized void storeOnlineMessage(String chatWindowId, String messageId, ChatMessage chatMessage){
		String fromId = chatMessage.getFromUser();
		String toId = chatMessage.getToUser();
		Map<String, ChatMessage> chatMap = new LinkedHashMap<String, ChatMessage>();
		chatMap.put(chatWindowId, chatMessage);
		
		SeenFlag seenFlag = new SeenFlag(false, false);
		messageSeenFlags.put(messageId, seenFlag);
		
		if(chatOnlineReceiverMessageHolder.containsKey(fromId)){
			chatOnlineReceiverMessageHolder.get(fromId).put(messageId, chatMap);
		}else{
			Map<String, Map<String, ChatMessage>> userMap = new HashMap<String, Map<String, ChatMessage>>();
			userMap.put(messageId, chatMap);
			chatOnlineReceiverMessageHolder.put(fromId, userMap);
		}
		
		if(chatOnlineReceiverMessageHolder.containsKey(toId)){
			chatOnlineReceiverMessageHolder.get(toId).put(messageId, chatMap);
		}else{
			Map<String, Map<String, ChatMessage>> userMap = new HashMap<String, Map<String, ChatMessage>>();
			userMap.put(messageId, chatMap);
			chatOnlineReceiverMessageHolder.put(toId, userMap);
		}
		
		allMessage.add(messageId);
	}
	
	public static synchronized Map<String, Map<String, ChatMessage>> getChatMessageForUser(String userCodeMasked, String userCode){
		
		Map<String, Map<String, ChatMessage>> chatMessageForUser = new LinkedHashMap<String, Map<String, ChatMessage>>();
		Map<String, Map<String, Map<String, ChatMessage>>> allOnlineMessages = new LinkedHashMap<String, Map<String, Map<String, ChatMessage>>>();
		allOnlineMessages.putAll(chatOnlineReceiverMessageHolder);
		
		Map<String, Map<String, ChatMessage>> allOfflineMessages = new LinkedHashMap<String, Map<String, ChatMessage>>();
		allOfflineMessages.putAll(chatOfflineSenderMessageHolder);
		
		List<String> allMessageIds = new ArrayList<String>();
		allMessageIds.addAll(allMessage);
		
		
		for(String messageId : allMessage){
			ChatMessage chatMessage = null;
			String chatWindowID = "";
			boolean offlineMessage = false;
			
			Map<String, Map<String, ChatMessage>> allOnlineMessagesUser = allOnlineMessages.get(userCode);
			if(allOnlineMessagesUser != null){
				Map<String, ChatMessage> onlineChatId = allOnlineMessagesUser.get(messageId);
				if(onlineChatId != null){
					List<String> onlineChatWindowIds = new ArrayList<String>(onlineChatId.keySet());
					for(String onlineChatWindowId : onlineChatWindowIds){
						if(onlineChatWindowId.indexOf(userCodeMasked) >= 0){
							chatWindowID = onlineChatWindowId;
							chatMessage = onlineChatId.get(onlineChatWindowId);
							chatOnlineReceiverMessageHolder.remove(userCode);
						}
					}
				}
			}			
			
			Map<String, ChatMessage> offlineChatId = allOfflineMessages.get(messageId);
			if(offlineChatId != null){
				List<String> offlineChatWindowIds = new ArrayList<String>(offlineChatId.keySet());
				for(String offlineChatWindowId : offlineChatWindowIds){
					if(offlineChatWindowId.indexOf(userCodeMasked) >= 0){
						chatWindowID = offlineChatWindowId;
						chatMessage = offlineChatId.get(offlineChatWindowId);
						chatOfflineSenderMessageHolder.remove(messageId);
						offlineMessage = true;
					}
				}
			}
			
			
			if(chatMessage != null){
				SeenFlag seenFlag = messageSeenFlags.get(messageId);
				
				if(chatMessage.getFromUser().equals(userCode)){
					chatMessage.setShowFrom("ME");
					chatMessage.setDirectionLeft("right");
					chatMessage.setDirectionRight("left");
					chatMessage.setShowTime(chatMessage.getMessageSentDate());
					if(!offlineMessage)
						chatMessage.setStatus("Seen");
					seenFlag.setSenderSeen(true);
				}else{
					chatMessage.setShowFrom(chatMessage.getToUserName());
					chatMessage.setDirectionLeft("left");
					chatMessage.setDirectionRight("right");
					chatMessage.setShowTime(chatMessage.getMessageReceiveDate());
					chatMessage.setStatus("New message");
					seenFlag.setReceiverSeen(true);
				}
				
				if(seenFlag.isSenderSeen() && seenFlag.isReceiverSeen()){
					allMessage.remove(messageId);
					messageSeenFlags.remove(messageId);
				}
				
				if(chatMessageForUser.containsKey(chatWindowID)){
					chatMessageForUser.get(chatWindowID).put(messageId, chatMessage);
				}else{
					Map<String, ChatMessage> chatMap = new HashMap<String, ChatMessage>();
					chatMap.put(messageId, chatMessage);
					chatMessageForUser.put(chatWindowID, chatMap);
				}
			}
		}
		return chatMessageForUser;
	}
}


class SeenFlag{
	private boolean senderSeen;
	private boolean receiverSeen;
	
	public SeenFlag(boolean senderSeen, boolean receiverSeen) {
		this.senderSeen = senderSeen;
		this.receiverSeen = receiverSeen;
	}
	public boolean isSenderSeen() {
		return senderSeen;
	}
	public void setSenderSeen(boolean senderSeen) {
		this.senderSeen = senderSeen;
	}
	public boolean isReceiverSeen() {
		return receiverSeen;
	}
	public void setReceiverSeen(boolean receiverSeen) {
		this.receiverSeen = receiverSeen;
	}
}