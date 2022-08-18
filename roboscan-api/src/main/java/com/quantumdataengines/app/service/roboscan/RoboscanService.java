package com.quantumdataengines.app.service.roboscan;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface RoboscanService {
	public List<List<String>> procedureData(String sections);
	public String sectionList(String userrole);
	public Map<String, Object> fetchRoboscanData(String caseNo, List<List<String>> procedureData, String sections);
}
