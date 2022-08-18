package com.quantumdataengines.app.service.Test;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quantumdataengines.app.dao.Test.TestDao;

@Component
public class TestServiceImpl implements TestService {
	
	@Autowired
	private TestDao testDao;

	@Override
	public Map<String, String> helloAgain() {
		return testDao.helloAgain();
	}

	@Override
	public List<Map<String, String>> listOfMapData() {
		return testDao.listOfMapData();
	}

	@Override
	public Map<String, Object> roboscanData() {
		return testDao.roboscanData();
	}
	
	@Override
	public Map<String, Object> fetchRoboscanData(String caseNos) {		
		return testDao.fetchRoboscanData(caseNos);
	}

}
