package com.quantumdataengines.app.dao.roboscan;

import java.util.List;
import java.util.Map;

public interface RoboscanDAO {
	public List<List<String>> procedureData(String sections);
	public Map<String, Object> procedureDetails(String sections);
	public String sectionList(String userrole);
	public Map<String, Object> fetchRoboscanData(String caseNo, List<List<String>> procedureData, String sections);
}
