package com.quantumdataengines.app.dao.Test;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quantumdataengines.app.dao.CommonDAOImpl;
import com.quantumdataengines.app.util.ConnectionUtil;

import oracle.jdbc.OracleTypes;



@Component
public class TestDaoImpl implements TestDao {
	
	private static final Logger log = LoggerFactory.getLogger(TestDaoImpl.class);
	
	@Autowired
	private ConnectionUtil connectionUtil;

	@Override
	public Map<String, String> helloAgain() {
		Connection connection = connectionUtil.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Map<String, String> myMap = null;
		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM TB_DOMAINUSER WHERE ROWNUM < 2");
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				myMap = new LinkedHashMap<String, String>();
				myMap.put("name", resultSet.getString("LOGINCODE"));
				myMap.put("age", resultSet.getString("LOGINTIME"));
				myMap.put("work", resultSet.getString("LOGOUTTIME"));
				myMap.put("address", resultSet.getString("USERNAME"));				
			}
		} catch (Exception e) {
			log.error("Something went Wrong!");
		}
		log.info("MY MAP DATA :"+myMap);
		return myMap;
	}

	@Override
	public List<Map<String, String>> listOfMapData() {
		Connection connection = connectionUtil.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Map<String, String>> listData = new ArrayList<Map<String, String>>();
		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM COMAML.TB_USER");
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				Map<String, String> myMap = new LinkedHashMap<String, String>();
				myMap.put("USERCODE", resultSet.getString("USERCODE"));
				myMap.put("USERNAME", resultSet.getString("USERNAME"));
				myMap.put("USERPASSWORD", resultSet.getString("USERPASSWORD"));
				myMap.put("TOTIME", resultSet.getString("TOTIME"));	
			   listData.add(myMap);
			}
		} catch (Exception e) {
			log.error("Something went Wrong!!");
		}
		log.info("LIST OF MAP DATA: "+listData);
		return listData;
	}

	@Override
	public Map<String, Object> roboscanData() {
		Connection connection = connectionUtil.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Map<String, Object> roboData = new LinkedHashMap<String, Object>();
		try{
			preparedStatement = connection.prepareStatement("SELECT CALCULATEDVALUE, BENCHMARKVALUE, PRODUCTCODE FROM COMAML.TB_ALERTSGENERATED WHERE ALERTNO = '8042019182822358842' ");
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				roboData.put("CALCULATEDVALUE", resultSet.getString("CALCULATEDVALUE"));
				roboData.put("BENCHMARKVALUE", resultSet.getString("BENCHMARKVALUE"));
				roboData.put("PRODUCTCODE", resultSet.getString("PRODUCTCODE"));
			}
			
		}
		catch(Exception e){
			log.error("Something went wrong!!!");
		}
		return roboData;
	}
	
	@Override
	public Map<String, Object> fetchRoboscanData(String caseNos) {
		Map<String, Object> mainMap = new LinkedHashMap<String, Object>();
		Connection connection = connectionUtil.getConnection();
		CallableStatement callableStatement = null;
		try{
			connection = connectionUtil.getConnection();
			//callableStatement = connection.prepareCall("{CALL COMAML.STP_GETROBOSCANDATA(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			callableStatement = connection.prepareCall("{CALL COMAML.STP_GETROBOSCANDATA(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			callableStatement.setString(1, caseNos);
			callableStatement.setString(2, "");
			callableStatement.setString(3, "");
			callableStatement.setString(4, "");
			callableStatement.registerOutParameter(5, OracleTypes.CURSOR);
			callableStatement.registerOutParameter(6, OracleTypes.CURSOR);
			callableStatement.registerOutParameter(7, OracleTypes.CURSOR);
			callableStatement.registerOutParameter(8, OracleTypes.CURSOR);
			callableStatement.registerOutParameter(9, OracleTypes.CURSOR);
			callableStatement.registerOutParameter(10, OracleTypes.CURSOR);
			callableStatement.registerOutParameter(11, OracleTypes.CURSOR);
			callableStatement.registerOutParameter(12, OracleTypes.CURSOR);
			callableStatement.registerOutParameter(13, OracleTypes.CURSOR);
			callableStatement.registerOutParameter(14, OracleTypes.CURSOR);
			callableStatement.registerOutParameter(15, OracleTypes.CURSOR);
			callableStatement.registerOutParameter(16, OracleTypes.CURSOR);
			callableStatement.registerOutParameter(17, OracleTypes.CURSOR);
			callableStatement.registerOutParameter(18, OracleTypes.CURSOR);
			//callableStatement.registerOutParameter(19, OracleTypes.CURSOR);
			
			callableStatement.execute();
			
			List<Object> dataList = new ArrayList<Object>();
			System.out.println("started");
			for(int i = 6; i<= 16; i++){
			//FOR FETCHING DATA OF SECTION-1 ALERT DETAILS 
			
			ResultSet resultSetSection1 = (ResultSet) callableStatement.getObject(i);
			ResultSetMetaData section1MetaData = resultSetSection1.getMetaData();
			Map<String, Object> roboscanSection1 = new LinkedHashMap<String, Object>();
			List<String> alertHeaders = new Vector<String>();
			List<List<String>> alertData = new Vector<List<String>>();
			for(int colIndex = 1; colIndex <= section1MetaData.getColumnCount(); colIndex++){
				alertHeaders.add(section1MetaData.getColumnName(colIndex));
			}
			while(resultSetSection1.next()){
				List<String> record = new Vector<String>();
				for(int colIndex = 1; colIndex <= section1MetaData.getColumnCount(); colIndex++){
					String columnName = section1MetaData.getColumnName(colIndex);
					record.add(resultSetSection1.getString(columnName));
				}
				alertData.add(record);
			}
			roboscanSection1.put("HEADER"+i, alertHeaders);
			roboscanSection1.put("DATA"+i, alertData);
			dataList.add(roboscanSection1);
			}
			
			System.out.println("datalist: "+dataList);
			mainMap.put("RoboscanData", dataList);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return mainMap;
	}

}
