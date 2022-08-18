package com.quantumdataengines.app.dao.Test;

import java.util.List;
import java.util.Map;

public interface TestDao {
	public Map<String, String> helloAgain();
	public List<Map<String, String>> listOfMapData();
	public Map<String, Object> roboscanData();
	public Map<String, Object> fetchRoboscanData(String caseNos);
}
