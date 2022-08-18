package com.quantumdataengines.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.quantumdataengines.app.service.roboscan.RoboscanService;

@RestController
@RequestMapping(value="/roboscanapi")
@CrossOrigin("*")
public class RoboscanController {
	
	private static final Logger log = LoggerFactory.getLogger(RoboscanController.class); 
	
	@Autowired
	private RoboscanService roboscanService;
	
	@RequestMapping(value="/fetchRoboscanData", method = RequestMethod.GET)
	public Map<String, Object> fetchRoboscanData(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws Exception{
		String caseNos = "119948";
		String userRole = "AMLUSER";
		String sections = roboscanService.sectionList(userRole);
		Map<String, Object> mainData = new HashMap<String, Object>();
		List<List<String>> procedureData = roboscanService.procedureData(sections);
		Map<String, Object> allData = roboscanService.fetchRoboscanData(caseNos, procedureData, sections);
		List<Object> listAllData = new ArrayList<Object>();
		listAllData.add(allData);
		
		mainData.put("ProcedureData",procedureData);
		mainData.put("RoboscanData",allData);

		return mainData;
	}

}
