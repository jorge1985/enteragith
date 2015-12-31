package com.youandbbva.enteratv.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.springframework.stereotype.Repository;

import com.youandbbva.enteratv.Constants;
import com.youandbbva.enteratv.DAO;
import com.youandbbva.enteratv.DSManager;
import com.youandbbva.enteratv.domain.ConfigInfo;

/**
 * 
 * Handle all query for System Options.
 * 		bbva_config
 * 
 * @author CJH
 *
 */

@Repository("SystemDAO")
public class SystemDAO extends DAO{

	/**
	 * SQL queries
	 */
	private final String TABLE_NAME = "home";
	private final String COLUMN_NAME = "HomeElement,HomeHtml";
	private final String SELECT_BY_KEY = " select " + COLUMN_NAME + " from " + TABLE_NAME + " where  HomeElement=? ";
	private final String INSERT = " insert into " + TABLE_NAME + " ( " + COLUMN_NAME + " ) values ( ?, ? ) ";
	private final String UPDATE_BY_KEY = " update " + TABLE_NAME + " set HomeHtml=? where HomeElement=? ";
	private final String DELETE_BY_KEY = " delete from " + TABLE_NAME + " where HomeElement=? ";
	
	public SystemDAO() {
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * Get Config Data.
	 * 
	 * @param key
	 * @return list or string
	 */
	public Object getOptions(String key){
		JSONArray result = new JSONArray();
		String str = Constants.DEFAULT_YES;
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try{
																	// 	RUNNING QUERY
			stmt = conn.prepareStatement(SELECT_BY_KEY);
			stmt.setString(1, key);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				if (key.equals(Constants.OPTION_ALLOW_HOST) || key.equals(Constants.OPTION_ALLOW_IP)){
					ConfigInfo item = new ConfigInfo();
					item.setKey(rs.getString("HomeElement"));
					item.setValue(rs.getString("HomeHtml"));
					
					result.put(item.toJSONObject());
				} else{
					str = rs.getString("HomeHtml");
				}
			}
		}catch (Exception e){
			log.error("SystemDAO", "getOptions", e.toString());
		}
		finally
		{
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (key.equals(Constants.OPTION_ALLOW_HOST) || key.equals(Constants.OPTION_ALLOW_IP))
			return result;
		
		return str;
	}
	
	/**
	 * Insert data to Options.
	 * 
	 * @param key
	 * @param value
	 * @throws Exception
	 */
	public void insert(String key, String value) throws Exception{
																	// RUNNING QUERY
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = conn.prepareStatement(INSERT);
		stmt.setString(1, key);
		stmt.setString(2, value);
		stmt.execute();
		stmt.close();
		conn.close();
	}
	
	/**
	 * Update data by key.
	 * 
	 * @param key
	 * @param value
	 * @throws Exception
	 */
	public void update(String key, String value) throws Exception{
																	// RUNNING QUERY
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = conn.prepareStatement(UPDATE_BY_KEY);
		stmt.setString(1, value);
		stmt.setString(2, key);
		stmt.execute();
		stmt.close();
		conn.close();
	}

	/**
	 * Delete data by key.
	 * 
	 * @param key
	 * @throws Exception
	 */
	public void delete(String key) throws Exception{
																	// RUNNING QUERY
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = conn.prepareStatement(DELETE_BY_KEY);
		stmt.setString(1, key);
		stmt.execute();
		stmt.close();
		conn.close();
	}
	
	
	
}
