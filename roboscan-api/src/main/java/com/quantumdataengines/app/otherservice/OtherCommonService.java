package com.quantumdataengines.app.otherservice;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Service;

@Service
public class OtherCommonService {
	
	private static final Logger log = LoggerFactory.getLogger(OtherCommonService.class);
	
	public String getFormattedDate(Date date, String format) {
		String strDate = "";
		try{
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			strDate = sdf.format(date);
		}catch(Exception e){
			log.error("Error while formatting date : "+e.getMessage());
			e.printStackTrace();
		}
		return strDate;
	}
	
	public Date getFormattedStringDate(String date, String format) {
		Date strDate = null;
		try{
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			strDate = sdf.parse(date);
		}catch(Exception e){
			log.error("Error while formatting date : "+e.getMessage());
			e.printStackTrace();
		}
		return strDate;
	}
	
	public String getElementId(){
		String strDate = "";
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmssSSS");
			strDate = sdf.format(new Date());
			Random random = new java.util.Random();
			int nextInt = random.nextInt();
			if(nextInt < 0)
				nextInt = nextInt * (-1);
			
			strDate = strDate + Integer.toString(nextInt);
		}catch(Exception e){
			log.error("Error while formatting date : "+e.getMessage());
			e.printStackTrace();
		}
		return strDate;
	}
	
	public String changeToDayTimeMin(String strMinute){
		String resultStr = "";
		int days = 0;
		int hours = 0;
		int min = 0;
		int rem = 0;
		try{
			int intMunite = Integer.parseInt(strMinute);
			rem = intMunite;
			days = rem / (60 * 24);
			rem = rem - (days * 60 * 24);
			hours = rem / (60);
			rem = rem - (hours * 60);
			min = rem;
			if(days > 0){
				if(days > 1)
					resultStr = days + " days";
				else
					resultStr = days + " day";
			}
			if(hours > 0 || days > 0){
				if(days > 0)
					resultStr = resultStr+", ";
				if(hours > 1)
					resultStr = resultStr+ hours + " hours";
				else
					resultStr = resultStr+ hours + " hour";
			}
			if(hours > 0 || days > 0)
				resultStr = resultStr+", ";
			if(min > 1)
				resultStr = resultStr+ min + " mins";
			else
				resultStr = resultStr+ min + " min";
		}catch(Exception e){
			e.printStackTrace();
			log.error("Error while parsing string to integer");
		}
		return resultStr;
	}
	
	public String getCSRFToken(HttpServletRequest request){
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
		char first = token.getToken().charAt(4);
		char second = token.getToken().charAt(5);
		
		char third = token.getToken().charAt(10);
		char fourth = token.getToken().charAt(17);
		char fifth = token.getToken().charAt(22);
		
		char sixth = token.getToken().charAt(28);
		char seventh = token.getToken().charAt(30);
		char eight = token.getToken().charAt(32);
		
		String firstPhase = ""+second+first;
		String secondPhase = ""+fourth+fifth+third;
		String thirdPhase = ""+eight+seventh+sixth;
		String fullPhase = firstPhase+thirdPhase+secondPhase;
		return fullPhase.toUpperCase();
	}
	
	public List<String> stringToList(String string, String delimitter) {
		String[] arrStr = string.split(delimitter);
		return Arrays.asList(arrStr);
	}
	
	public String[] stringToArr(String string, String delimitter) {
		return string.split(delimitter);
	}
	
	public String getChatWindowId(String user1, String user2){
		if(user1.compareTo(user2) >= 0){
			return user1+user2;
		}else{
			return user2+user1;
		}
	}
	
	public List<Map<String, String>> getYears(){
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int lowerLimit = year - 10;
		List<Map<String, String>> years = new Vector<Map<String, String>>();
		for(int i = lowerLimit; i <= year; i++){
			Map<String, String> yearMap = new HashMap<String, String>();
			yearMap.put("NO", new Integer(i).toString());
			yearMap.put("NAME", new Integer(i).toString());
			years.add(yearMap);
		}
		return years;
	}
	
	public List<Map<String, String>> getMonths(){
		List<Map<String, String>> months = new Vector<Map<String, String>>();
		for(int i = 1; i <= 12; i++){
			Map<String, String> month = new HashMap<String, String>();
			switch (i) {
            case 1:
            	month.put("NO", "1");
            	month.put("NAME", "January");
                break;
            case 2:
            	month.put("NO", "2");
            	month.put("NAME", "February");
                break;
            case 3:
            	month.put("NO", "3");
            	month.put("NAME", "March");
                break;
            case 4:
            	month.put("NO", "4");
            	month.put("NAME", "April");
                break;
            case 5:
            	month.put("NO", "5");
            	month.put("NAME", "May");
                break;
            case 6:
            	month.put("NO", "6");
            	month.put("NAME", "June");
                break;
            case 7:
            	month.put("NO", "7");
            	month.put("NAME", "July");
                break;
            case 8:
            	month.put("NO", "8");
            	month.put("NAME", "August");
                break;
            case 9:
            	month.put("NO", "9");
            	month.put("NAME", "September");
                break;
            case 10:
            	month.put("NO", "10");
            	month.put("NAME", "October");
                break;
            case 11:
            	month.put("NO", "11");
            	month.put("NAME", "November");
                break;
            case 12:
            	month.put("NO", "12");
            	month.put("NAME", "December");
			}
			
			months.add(month);
		}
		return months;
	}
	
	public String parseRequestParams(HttpServletRequest request){
		String queryParam = "";
		Map paramMap = request.getParameterMap();
		Iterator itr = paramMap.keySet().iterator();
		while (itr.hasNext()) {
			String paramName = (String) itr.next();
			if(!paramName.equals("_")){
				String[] paramValueArr = (String[]) paramMap.get(paramName);
				int valueCount = paramValueArr.length;
				queryParam = queryParam + paramName + " : ";
				for(int i = 0; i < paramValueArr.length; i++){
					if((i+1) == valueCount)
						queryParam = queryParam + paramValueArr[i];
					else
						queryParam = queryParam + paramValueArr[i]+ ", ";
				}
				queryParam = queryParam + "; ";
			}
		}
		return queryParam;
	}
}