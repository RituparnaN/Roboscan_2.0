package com.quantumdataengines.app.service.roboscan;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quantumdataengines.app.dao.roboscan.RoboscanDAO;
import com.quantumdataengines.app.service.roboscan.RoboscanService;

@Service
public class RoboscanServiceImpl implements RoboscanService {
	
	@Autowired
	private RoboscanDAO roboscanDao;

	@Override
	public Map<String, Object> fetchRoboscanData(String caseNo, List<List<String>> procedureData, String sections) {
		return roboscanDao.fetchRoboscanData(caseNo, procedureData, sections);
	}

	@Override
	public List<List<String>> procedureData(String sections) {
		return roboscanDao.procedureData(sections);
	}

	@Override
	public String sectionList(String userrole) {
		return roboscanDao.sectionList(userrole);
	}

	@Override
	public Map<String, Object> procedureDetails(String sections) {
		return roboscanDao.procedureDetails(sections);
	}

}
