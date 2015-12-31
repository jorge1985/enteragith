package com.youandbbva.enteratv.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import com.youandbbva.enteratv.DAO;
import com.youandbbva.enteratv.DSManager;
import com.youandbbva.enteratv.domain.CityInfo;
import com.youandbbva.enteratv.domain.CodeInfo;
import com.youandbbva.enteratv.domain.DivisionInfo;
import com.youandbbva.enteratv.domain.JobshrtInfo;

/**
 * 
 * Handle all query for Additional Information.
 * 		bbva_code, bbva_division, bbva_city, bbva_jobshrt, bbva_config
 * 
 * @author CJH
 *
 */

@Repository("UtilityDAO")
public class UtilityDAO extends DAO{

	/**
	 * SQL queries
	 */
	private final String COLUMN_NAME__CODE="MenuId,MenuDivl,MenuCode,MenuValue";
	private final String COLUMN_NAME__CODEO="a.code,a.value,a.value_en,a.value_me";
	private final String SELECT_CODE = " select " + COLUMN_NAME__CODE + " from menu order by MenuCode ";
	private final String SELECT_CODE__BY_DIV = " select " + COLUMN_NAME__CODE + " from menu a where MenuDivl=? and MenuCode<>'0' order by MenuCode ";
	private final String SELECT_CODE__BY_DIVL = " select " + COLUMN_NAME__CODEO + " from bbva_code a where a.div=? and a.code<>'000' order by a.code ";
	private final String COLUMN_NAME__DIVISION="id,parent_id,name,name_en,name_me";
	private final String SELECT_DIVISION = " select " + COLUMN_NAME__DIVISION + " from bbva_division order by id, dorder ";
	private final String SELECT_DIVISION__BY_ID = " select " + COLUMN_NAME__DIVISION + " from bbva_division where id=? order by id, dorder ";
	private final String SELECT_DIVISION__BY_PARENT_ID = " select " + COLUMN_NAME__DIVISION + " from bbva_division where parent_id=? order by id, dorder ";
	private final String COLUMN_NAME__CITY="CityId,State_StateId,CityName,CityIsActive";
	private final String SELECT_CITY = " select " + COLUMN_NAME__CITY + " from city order by CityId desc ";
	private final String SELECT_CITY__BY_ID = " select " + COLUMN_NAME__CITY + " from city where CityId=? order by CityId desc ";
	private final String SELECT_CITY__BY_PARENT_ID = " select " + COLUMN_NAME__CITY + " from city where State_StateId=? order by CityId desc ";
	private final String COLUMN_NAME__JOBSHRT="a.key,a.value";
	private final String SELECT_JOBSHRT = " select " + COLUMN_NAME__JOBSHRT + " from bbva_jobshrt a order by a.key ";

	
	public UtilityDAO() {
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * Get Code Data.
	 * 
	 * @param div
	 * @param language
	 * @return list
	 */
	public JSONArray getCodeListOfJSON(String div, String language){
		JSONArray result = new JSONArray();
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try{
																	// RUNNING QUERY
			stmt = conn.prepareStatement(SELECT_CODE__BY_DIV);
			stmt.setString(1, div);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				CodeInfo item = new CodeInfo();
				item.setCode(rs.getString("MenuCode"));
				item.setValue(rs.getString("MenuValue"));
				
				result.put(item.toJSONObject());
			}
		}catch (Exception e){
			log.error("UtilityDAO", "getCodeListOfJSON", e.toString()+"==="+div);
		}finally
		{
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;
	}
	
	/**
	 * Get Code Data.
	 * 
	 * @param div
	 * @param language
	 * @return list
	 */
	public ArrayList getCodeList(String div, String language){
		ArrayList result = new ArrayList();
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try{
																	// RUNNING QUERY
			stmt = conn.prepareStatement(SELECT_CODE__BY_DIV);
			stmt.setString(1, div);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				CodeInfo item = new CodeInfo();
				item.setCode(rs.getString("MenuCode"));
				item.setValue(rs.getString("MenuValue"));
				result.add(item);
			}
			
		}catch (Exception e){
			log.error("UtilityDAO", "getCodeList", e.toString()+"==="+div);
		}finally
			{
				try {
					stmt.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		return result;
	}	
	/**
	 * GET CODE LIST BY DIV AND LANGUAGE
	 * @param div
	 * @param language
	 * @return
	 */
	public ArrayList getCodeListO(String div, String language){
		ArrayList result = new ArrayList();
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try{
																	// RUNNING QUERY
			stmt = conn.prepareStatement(SELECT_CODE__BY_DIVL);
			stmt.setString(1, div);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				CodeInfo item = new CodeInfo();
				item.setCode(rs.getString("code"));

				if (language.equals("en"))
					item.setValue(rs.getString("value_en"));
				else if (language.equals("me"))
					item.setValue(rs.getString("value_me"));
				else
					item.setValue(rs.getString("value"));

				result.add(item);
			}
		}catch (Exception e){
			log.error("UtilityDAO", "getCodeList", e.toString()+"==="+div);
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

		return result;
	}	
	
	/**
	 * Get Division Data.
	 * 
	 * @param id
	 * @param language
	 * @return list
	 */
	public JSONArray getDivisionListOfJSON(Long id, String language){
		JSONArray result = new JSONArray();
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try{
																	// RUNNING QUERY
			stmt = conn.prepareStatement(SELECT_DIVISION__BY_PARENT_ID);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				DivisionInfo item = new DivisionInfo();
				item.setId(rs.getLong("id"));
				item.setParentID(rs.getLong("parent_id"));
				
				if (language.equals("en"))
					item.setName(rs.getString("name_en"));
				else if (language.equals("me"))
					item.setName(rs.getString("name_me"));
				else
					item.setName(rs.getString("name"));
				
				result.put(item.toJSONObject());
			}
		}catch (Exception e){
			log.error("UtilityDAO", "getDivisionListOfJSON", e.toString());
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

		return result;
	}

	/**
	 * Get Division Data.
	 * 
	 * @param id
	 * @param language
	 * @return object
	 */
	public JSONObject getDivisionOfJSON(Long id, String language){
		JSONObject result = new JSONObject();
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try{
																	// RUNNING QUERY
			stmt = conn.prepareStatement(SELECT_DIVISION__BY_ID);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				DivisionInfo item = new DivisionInfo();
				item.setId(rs.getLong("id"));
				item.setParentID(rs.getLong("parent_id"));
				
				if (language.equals("en"))
					item.setName(rs.getString("name_en"));
				else if (language.equals("me"))
					item.setName(rs.getString("name_me"));
				else
					item.setName(rs.getString("name"));
				
				return item.toJSONObject();
			}
		}catch (Exception e){
			log.error("UtilityDAO", "getDivisionOfJSON", e.toString());
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

		return result;
	}
	
	/**
	 * Get Division Data.
	 * 
	 * @param id
	 * @param language
	 * @return list
	 */
	public ArrayList getDivisionList(Long id, String language){
		ArrayList result = new ArrayList();
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try{
																	// RUNNING QUERY
			stmt = conn.prepareStatement(SELECT_DIVISION__BY_PARENT_ID);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				DivisionInfo item = new DivisionInfo();
				item.setId(rs.getLong("id"));
				item.setParentID(rs.getLong("parent_id"));
				
				if (language.equals("en"))
					item.setName(rs.getString("name_en"));
				else if (language.equals("me"))
					item.setName(rs.getString("name_me"));
				else
					item.setName(rs.getString("name"));

				result.add(item);
			}
		}catch (Exception e){
			log.error("UtilityDAO", "getDivisionList", e.toString());
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

		return result;
	}
	
	/**
	 * Get City Data.
	 * 
	 * @param id
	 * @param language
	 * @return list
	 */
	public JSONObject getCityOfJSON(Long id, String language){
		JSONObject result = new JSONObject();
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try{
																	// RUNNING QUERY
			stmt = conn.prepareStatement(SELECT_CITY__BY_ID);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				CityInfo item = new CityInfo();
				item.setId(rs.getLong("id"));
				item.setParentID(rs.getLong("parent_id"));
				
				if (language.equals("en"))
					item.setName(rs.getString("name_en"));
				else if (language.equals("me"))
					item.setName(rs.getString("name_me"));
				else
					item.setName(rs.getString("name"));
				
				return item.toJSONObject();
			}
		}catch (Exception e){
			log.error("UtilityDAO", "getCityOfJSON", e.toString()+"==="+id);
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

		return result;
	}

	/**
	 * Get City Data.
	 * 
	 * @param id
	 * @param language
	 * @return list
	 */
	public JSONArray getCityListOfJSON(Long id, String language){
		JSONArray result = new JSONArray();
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try{
																	// RUNNING QUERY
			stmt = conn.prepareStatement(SELECT_CITY__BY_PARENT_ID);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				CityInfo item = new CityInfo();
				item.setId(rs.getLong("id"));
				item.setParentID(rs.getLong("parent_id"));
				
				if (language.equals("en"))
					item.setName(rs.getString("name_en"));
				else if (language.equals("me"))
					item.setName(rs.getString("name_me"));
				else
					item.setName(rs.getString("name"));
				
				result.put(item.toJSONObject());
			}
		}catch (Exception e){
			log.error("UtilityDAO", "getCityListOfJSON", e.toString()+"==="+id);
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

		return result;
	}
	
	/**
	 * Get City Data.
	 * 
	 * @param id
	 * @param language
	 * @return list
	 */
	public ArrayList getCityList(Long id, String language){
		ArrayList result = new ArrayList();
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try{
																	// RUNNING QUERY
			stmt = conn.prepareStatement(SELECT_CITY__BY_PARENT_ID);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				CityInfo item = new CityInfo();
				item.setId(rs.getLong("id"));
				item.setParentID(rs.getLong("parent_id"));
				
				if (language.equals("en"))
					item.setName(rs.getString("name_en"));
				else if (language.equals("me"))
					item.setName(rs.getString("name_me"));
				else
					item.setName(rs.getString("name"));

				result.add(item);
			}
		}catch (Exception e){
			log.error("UtilityDAO", "getCityList", e.toString());
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

		return result;
	}		

	
	/**
	 * Get Jobshrt Data.
	 * 
	 * @return list
	 */
	public JSONArray getJobshrtListOfJSON(){
		JSONArray result = new JSONArray();
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try
		{
																	// RUNNING QUERY
			stmt = conn.prepareStatement(SELECT_JOBSHRT);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				JobshrtInfo item = new JobshrtInfo();
				item.setKey(rs.getString("key"));
				item.setValue(rs.getString("value"));
				
				result.put(item.toJSONObject());
			}
		}catch (Exception e){
			log.error("UtilityDAO", "getJobshrtListOfJSON", e.toString());
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

		return result;
	}
	
	/**
	 * Get Jobshrt Data.
	 * 
	 * @return list
	 */
	public ArrayList getJobshrtList(){
		ArrayList result = new ArrayList();
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try{
																	// RUNNING QUERY
			stmt = conn.prepareStatement(SELECT_JOBSHRT);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				JobshrtInfo item = new JobshrtInfo();
				item.setKey(rs.getString("key"));
				item.setValue(rs.getString("value"));

				result.add(item);
			}
		}catch (Exception e){
			log.error("UtilityDAO", "getJobshrtList", e.toString());
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

		return result;
	}		
	
	/**
	 * GET LIST BY TABLE 
	 * @param table
	 * @return
	 */
	public ArrayList getList(String table){
		ArrayList result = new ArrayList();
		Connection conn = DSManager.getConnection();
		PreparedStatement stmtcity = null;
		PreparedStatement stmtmain = null;
		PreparedStatement stmtcompany = null;
		try{
			if(table.equals("city"))
			{
																	// RUNNING QUERY
				stmtcity = conn.prepareStatement(" select * from " + table + " ");
				ResultSet rscity = stmtcity.executeQuery();
				while (rscity.next()){
					CodeInfo item = new CodeInfo();
					item.setId(rscity.getLong(1));
					item.setValue(rscity.getString(3));
					result.add(item);
			}
			
			}
			if(table.equals("maindirection"))
			{
																	// RUNNING QUERY
				stmtmain = conn.prepareStatement("select * from " + table + " "+"ORDER BY MaindirectionName ASC");
				ResultSet rsmain = stmtmain.executeQuery();
				while (rsmain.next()){
					CodeInfo item = new CodeInfo();
					item.setId(rsmain.getLong(1));
					item.setValue(rsmain.getString(2));
					result.add(item);
			}
			}
			
			if(table.equals("company"))
			{
																	// RUNNING QUERY
				stmtcompany = conn.prepareStatement(" select * from " + table + " ");
				ResultSet rscompany = stmtcompany.executeQuery();
				while (rscompany.next()){
					CodeInfo item = new CodeInfo();
					item.setId(rscompany.getLong(1));
					item.setValue(rscompany.getString(2));
					result.add(item);
			}
			}
		}catch (Exception e){
			log.error("UtilityDAO", "getList", e.toString());
		}
		finally
		{
			try {
				stmtcity.close();
				stmtmain.close();
				stmtcompany.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;
	}
	
}
