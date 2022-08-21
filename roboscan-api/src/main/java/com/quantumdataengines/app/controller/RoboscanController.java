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
		String caseNos = request.getParameter("CaseNos");
		String userRole = request.getParameter("userRole");
		String sections = roboscanService.sectionList(userRole);
		Map<String, Object> mainData = new HashMap<String, Object>();
		Map<String, Object> procedureDetails = roboscanService.procedureDetails(sections);
		@SuppressWarnings("unchecked")
		List<List<String>> procedureDataList= (List<List<String>>) procedureDetails.get("list");
		@SuppressWarnings("unchecked")
		Map<String, Object> procedureDataMap = (Map<String, Object>) procedureDetails.get("map");
		//List<List<String>> procedureData = roboscanService.procedureData(sections);
		Map<String, Object> allData = roboscanService.fetchRoboscanData(caseNos, procedureDataList, sections);
		List<Object> listAllData = new ArrayList<Object>();
		listAllData.add(allData);
		
		//mainData.put("ProcedureData",procedureDataMap);
		mainData.put("RoboscanData",allData);

		return mainData;
	}
	
	@RequestMapping(value="/test", method = RequestMethod.GET)
	public Map<String, Object> testData(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws Exception{
		String sections = roboscanService.sectionList("AMLUSER");
		Map<String, Object> allData = roboscanService.procedureDetails(sections);
		@SuppressWarnings("unchecked")
		List<List<String>> list= (List<List<String>>) allData.get("list");
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) allData.get("map");
		System.out.println(list);
		System.out.println(map);

		return allData;
	}

}
