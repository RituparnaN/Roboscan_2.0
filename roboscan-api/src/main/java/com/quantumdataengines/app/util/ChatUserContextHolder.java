package com.quantumdataengines.app.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatUserContextHolder {
	private static Map<String, Map<String, Map<String, Object>>> userChatContext = new HashMap<String, Map<String, Map<String, Object>>>();
	
	private ChatUserContextHolder(){}
	
	public static Map<String, Map<String, Object>> getAllUserForInstitution(String institution){
		return userChatContext.get(institution);
	}
	
	public static List<String> getAllUsername(String institution){
		return new ArrayList<String>(userChatContext.get(institution).keySet());
	}
	
	public static void setUserContext(String institution, String userName, Map<String, Object> userContext){
		if(userChatContext.containsKey(institution)){
			userChatContext.get(institution).put(userName, userContext);
		}else{
			Map<String, Map<String, Object>> instituteContext = new HashMap<String, Map<String,Object>>();
			instituteContext.put(userName, userContext);
			userChatContext.put(institution, instituteContext);
		}
		
	}
	
	public static void removeUserConttext(String userName, String institution){
		if(userChatContext.get(institution) != null) 
			userChatContext.get(institution).remove(userName);
	}
	
	public static Object getUserContextDetails(String institution, String userName, String field){
		Map<String, Map<String, Object>> instituteMap = userChatContext.get(institution);
		if(instituteMap != null){
			Map<String, Object> userMap = instituteMap.get(userName);
			if(userMap != null)
				return userMap.get(field);
			else
				return null;
		}else
			return null;
	}
	
	public static void setUserContextDetails(String institution, String userName, String field, Object fieldValue){
		userChatContext.get(institution).get(userName).put(field, fieldValue);
	}
}
