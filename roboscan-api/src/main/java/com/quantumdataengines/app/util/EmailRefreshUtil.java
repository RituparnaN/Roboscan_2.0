package com.quantumdataengines.app.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.quantumdataengines.app.model.EmailRefresh;

@Component
public class EmailRefreshUtil {
	
	private Map<String, EmailRefresh> emailRefreshDetails = new HashMap<String, EmailRefresh>();

	public Map<String, EmailRefresh> getEmailRefreshDetails() {
		return emailRefreshDetails;
	}

	public void setEmailRefreshDetails(String institute, EmailRefresh emailRefresh) {
		emailRefreshDetails.put(institute, emailRefresh);
	}
}
