package com.quantumdataengines.app.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.quantumdataengines.app.service.Test.TestService;

@RestController
@RequestMapping(value="/test")
@CrossOrigin("*")
public class Test {
	
	@Autowired
	private TestService testService;
	
	@RequestMapping(value="/hello", method=RequestMethod.GET)
	public String testing(){
		String testData = "kamlesh";
		return testData;
	}
	
	@RequestMapping(value="/helloAgain", method=RequestMethod.GET)
	public Map<String, String> helloAgain(){
		return testService.helloAgain();
	}
	
	@RequestMapping(value = "/listOfMapData", method=RequestMethod.GET)
	public List<Map<String, String>> listOfMapData(){
		return testService.listOfMapData();
	}
	
	@RequestMapping(value = "roboscandata", method=RequestMethod.GET)
	public Map<String, Object> roboscanData(){
		return testService.roboscanData();
	}
	
	@RequestMapping(value="/roboscanDetails", method=RequestMethod.GET)
	public Map<String, Object> roboscanDetails(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws Exception{
		String caseNos = request.getParameter("CaseNos");
		return testService.fetchRoboscanData(caseNos);
	}
	
	@RequestMapping(value="/postData", method=RequestMethod.POST)
	public String showData(String usename, String ps){
		return usename+" "+ps;
	}

}
