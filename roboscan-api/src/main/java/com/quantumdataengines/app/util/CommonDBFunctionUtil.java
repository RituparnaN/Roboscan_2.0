package com.quantumdataengines.app.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class CommonDBFunctionUtil {
    private static final Logger LOGGER = Logger.getLogger(CommonDBFunctionUtil.class.getName());
    @Autowired
    private ConnectionUtil connectionUtil;

    public String getNextSeqValue(String sequenceName){
        try(Connection connection = connectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT " + sequenceName + ".NEXTVAL FROM DUAL");
            ResultSet resultSet = preparedStatement.executeQuery();
            String nextValue = "";
            while(resultSet.next()) {
                nextValue = resultSet.getString(1);
            }
            return nextValue;
        }catch (Exception e){
            LOGGER.log(Level.SEVERE,"Error while next value of sequence = "+sequenceName);
            e.printStackTrace();
            return null;
        }
    }

    public List<String> getTableColumnNameList(String tableName) {
        try(Connection connection = connectionUtil.getConnection()) {
            String query = "SELECT COLUMN_NAME FROM USER_TAB_COLUMNS WHERE TABLE_NAME= ? ORDER BY COLUMN_ID ASC ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,tableName);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<String> columnNameList = new ArrayList<String>();
            while(resultSet.next()) {
                columnNameList.add( resultSet.getString("COLUMN_NAME"));
            }
            return columnNameList;
        }catch (Exception e){
            LOGGER.log(Level.SEVERE,"Error while getting column name of tale  = "+tableName);
            e.printStackTrace();
            return null;
        }
    }









}
