package com.quantumdataengines.app.dao.roboscan;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.quantumdataengines.app.util.ConnectionUtil;

import oracle.jdbc.OracleTypes;

@Repository
public class RoboscanDAOImpl implements RoboscanDAO{
	
	@Value("${second.datasource.username}")
	private String secondSchema;
	
	@Autowired
	private ConnectionUtil connectionUtil;

	public List<List<String>> procedureData(String sections) {
		Connection connection = connectionUtil.getConnection();
		PreparedStatement preparedStatement = null;
		String sectionData = sections;
		String sectionData1 = sectionData.replace(",", "\',\'");
		String sectionList = "\'"+sectionData1+"\'";
		ResultSet resultSet = null;
		List<List<String>> proc = new ArrayList<List<String>>();
		try {
			preparedStatement = connection.prepareStatement("SELECT SECTIONNAME, DESCRIPTION, PROCNAME, "
					+ "PRIMARYVIEWTYPE, VIEWTYPES, PRIMARYGRAPHTYPE, GRAPHTYPES, ICON "
					+ "FROM COMAML.TB_ROBOSCANSECTIONMASTER "
					+ "WHERE SECTIONNAME IN ("+sectionList+") ");
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				List<String> procName = new ArrayList<String>();
				procName.add(resultSet.getString("SECTIONNAME"));	
				procName.add(resultSet.getString("DESCRIPTION"));
				procName.add(resultSet.getString("PROCNAME"));	
				procName.add(resultSet.getString("PRIMARYVIEWTYPE"));
				procName.add(resultSet.getString("VIEWTYPES"));	
				procName.add(resultSet.getString("PRIMARYGRAPHTYPE"));
				procName.add(resultSet.getString("GRAPHTYPES"));	
				procName.add(resultSet.getString("ICON"));
			proc.add(procName);
			}		
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("dataset: "+proc);
		return proc;
	}


	@Override
	public Map<String, Object> fetchRoboscanData(String caseNo, List<List<String>> procedureData, String sections) {
		Map<String, Object> allSection = new LinkedHashMap<String, Object>();
		Connection connection = connectionUtil.getConnection();
		CallableStatement callableStatement = null;

		try{
			connection = connectionUtil.getConnection();
			
			for(int i = 0; i < procedureData.size(); i++)
			{
 
				List<String> procData = procedureData.get(i);
				
				String sectionName = procData.get(0);
				String procedureName = procData.get(2);
				String viewTypes = procData.get(4);
				String graphTypes = procData.get(6);
				String[] allGraphs = null;
				
				if(graphTypes != null){		
					graphTypes = graphTypes.replace(" ", "");
					allGraphs = graphTypes.split(",",0);
				}

				if(sections.contains(sectionName)){
					
				callableStatement = connection.prepareCall("{CALL "+secondSchema+"."+procedureName+"(?,?,?,?)}");
				callableStatement.setString(1, caseNo);
				callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
				callableStatement.registerOutParameter(3, OracleTypes.CURSOR);
				callableStatement.registerOutParameter(4, OracleTypes.CURSOR);			
				callableStatement.execute();
				
				Map<String, Object> roboData = new LinkedHashMap<String, Object>();
				
				 
				ResultSet resultSet1 = (ResultSet) callableStatement.getObject(2);
				Map<String, Object> tableData = tableResult(resultSet1);	
				resultSet1.close();
	
				ResultSet resultSet2 = (ResultSet) callableStatement.getObject(3);
				Map<String, String> formData = formResult(resultSet2);
				resultSet2.close();
				
				
				ResultSet resultSet3 = (ResultSet) callableStatement.getObject(4);
				Map<String, Object> graphData = graphResult(resultSet3);
				Map<String, Object> roboscanSectionData = new LinkedHashMap<String, Object>();
				roboscanSectionData.put("TYPES", allGraphs);
				roboscanSectionData.put("Data", graphData);
				
				resultSet3.close();
	
				
				if(viewTypes.contains("TABLE") == false)
				{roboData.put("DataTable", null);}
				else
				{roboData.put("DataTable", tableData);}

				if(viewTypes.contains("FORM") == false)
				{roboData.put("Form", null);}
				else
				{roboData.put("Form", formData);}				
				
				if(viewTypes.contains("GRAPH") == false)
				{roboData.put("Graph", null);}
				else
				{roboData.put("Graph", roboscanSectionData);}
			
				allSection.put(sectionName, roboData);
			}

			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return allSection;
	}


	@Override
	public String sectionList(String userRole) {
		Connection connection = connectionUtil.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String data = null;
		try {
			preparedStatement = connection.prepareStatement("SELECT SELECTEDPARTS FROM COMAML.TB_ROBOSCANCONFIG WHERE ROLEID = ?");
			preparedStatement.setString(1, userRole);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				data =resultSet.getString("SELECTEDPARTS");	
			}		
		}catch(Exception e){
			e.printStackTrace();
		}
		return data;
	}


	@Override
	public Map<String, Object> procedureDetails(String sections) {
		Connection connection = connectionUtil.getConnection();
		PreparedStatement preparedStatement = null;
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> data1 = new HashMap<String, Object>();
		String sectionData = sections;
		String sectionData1 = sectionData.replace(",", "\',\'");
		String sectionList = "\'"+sectionData1+"\'";
		System.out.println("sectionList: "+sectionList);
		ResultSet resultSet = null;
		List<List<String>> proc = new LinkedList<List<String>>();
		try {
			preparedStatement = connection.prepareStatement("SELECT SECTIONNAME, DESCRIPTION, PROCNAME, "
					+ "PRIMARYVIEWTYPE, VIEWTYPES, PRIMARYGRAPHTYPE, GRAPHTYPES, ICON "
					+ "FROM COMAML.TB_ROBOSCANSECTIONMASTER "
					+ "WHERE SECTIONNAME IN ("+sectionList+") ");
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				List<String> procName = new LinkedList<String>();
				procName.add(resultSet.getString("SECTIONNAME"));	
				procName.add(resultSet.getString("DESCRIPTION"));
				procName.add(resultSet.getString("PROCNAME"));	
				procName.add(resultSet.getString("PRIMARYVIEWTYPE"));
				procName.add(resultSet.getString("VIEWTYPES"));	
				procName.add(resultSet.getString("PRIMARYGRAPHTYPE"));
				procName.add(resultSet.getString("GRAPHTYPES"));	
				procName.add(resultSet.getString("ICON"));
			proc.add(procName);
			data.put(resultSet.getString("SECTIONNAME"), procName);
			}	
			data1.put("list",proc);
			data1.put("map", data);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return data1;
	}
	
	private Map<String, Object> tableResult(ResultSet resultSet1) throws SQLException{
		
		ResultSetMetaData section1MetaData = resultSet1.getMetaData();
		Map<String, Object> roboscanSection1 = new LinkedHashMap<String, Object>();
		List<String> headers = new Vector<String>();
		List<List<String>> data = new Vector<List<String>>();
	
		for(int colIndex = 1; colIndex <= section1MetaData.getColumnCount(); colIndex++){
			headers.add(section1MetaData.getColumnName(colIndex));
		}
		while(resultSet1.next()){
			List<String> record = new Vector<String>();
			for(int colIndex = 1; colIndex <= section1MetaData.getColumnCount(); colIndex++){
				String columnName = section1MetaData.getColumnName(colIndex);
				record.add(resultSet1.getString(columnName));
			}
			data.add(record);
		}
		roboscanSection1.put("HEADER", headers);
		roboscanSection1.put("DATA", data);
		
		return roboscanSection1;		
	}
	
	private Map<String, String> formResult(ResultSet resultSet2) throws SQLException{
		
		ResultSetMetaData section2MetaData = resultSet2.getMetaData();
		Map<String, String> roboscanSection2 = new LinkedHashMap<String, String>();
		while(resultSet2.next()){
			for(int colIndex = 1; colIndex <= section2MetaData.getColumnCount(); colIndex++){
				String columnName = section2MetaData.getColumnName(colIndex);
				roboscanSection2.put(columnName, resultSet2.getString(columnName));
			}
		}	
		return roboscanSection2;
	}
	
	private Map<String, Object> graphResult(ResultSet resultSet3) throws SQLException{
		ResultSetMetaData section3MetaData = resultSet3.getMetaData();
		Map<String, Object> roboscanSection3 = new LinkedHashMap<String, Object>();
		List<String> attr = new Vector<String>();
		List<List<String>> values = new Vector<List<String>>();
		for(int colIndex = 1; colIndex <= section3MetaData.getColumnCount(); colIndex++){
			attr.add(section3MetaData.getColumnName(colIndex));
		}
		while(resultSet3.next()){
			List<String> record = new Vector<String>();
			for(int colIndex = 1; colIndex <= section3MetaData.getColumnCount(); colIndex++){
				String columnName = section3MetaData.getColumnName(colIndex);
				record.add(resultSet3.getString(columnName));
			}
			values.add(record);
		}
		roboscanSection3.put("ATTRIBUTE", attr);
		roboscanSection3.put("VALUES", values);
		return roboscanSection3;
	}
	

}
