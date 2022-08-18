package com.quantumdataengines.app.util;

import org.springframework.util.Assert;

import com.quantumdataengines.app.schema.Configuration;

public class UserContextHolder {
	
	private static final ThreadLocal<Configuration> contextHolder = new ThreadLocal<Configuration>();
	
	
	public static void setUserContextHolder(Configuration userContextConfig){
		Assert.notNull(userContextConfig, "UserContext cann't be null");
		contextHolder.set(userContextConfig);
	}
	
	public static Configuration getUserContext(){
		return contextHolder.get();
	}
	
	public static void clearContextHolder(){
		contextHolder.remove();
	}
}