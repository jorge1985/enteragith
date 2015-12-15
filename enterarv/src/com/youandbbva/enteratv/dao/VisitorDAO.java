package com.youandbbva.enteratv.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import com.youandbbva.enteratv.Constants;
import com.youandbbva.enteratv.DAO;
import com.youandbbva.enteratv.DSManager;
import com.youandbbva.enteratv.Utils;
import com.youandbbva.enteratv.domain.ContentInfo;

/**
 * 
 * Handle all query for visitor.
 * 
 * @author CJH
 *
 */

@Repository("VisitorDAO")
public class VisitorDAO extends DAO{

	/**
	 * SQL queries
	 */
private static final String TABLE_VISIT = "visit";
private static final String COLUMNS_TABLE_VISIT = "User_UserId,Content_ContentId,VisitDate,VisitIp";
private static final String INSERT = " INSERT INTO "+ TABLE_VISIT + "(" + COLUMNS_TABLE_VISIT + ") values ( ?, ?, ?, ?) ";
private static final String COUNT_HISTORIC_TABLE_VISIT = " SELECT COUNT(*) FROM " + TABLE_VISIT;
private static final String COUNT_HISTORIC_UNIQUE_TABLA_VISIT = "SELECT COUNT(*) FROM " + TABLE_VISIT;
private static final String COUNT_BY_USER_SESSION_DATE = " SELECT COUNT(*) FROM "+ TABLE_VISIT +" WHERE User_UserId = ? and VisitDate=?";
private static final String COUNT_BY_TODAY = " SELECT COUNT(*) FROM "+ TABLE_VISIT + " WHERE VisitDate like ?";
private static final String VISIT_DATE = " select count(*) from "+ TABLE_VISIT + " where VisitDate like ?";
private static final String VISIT_ACCESS = "SELECT  u.UserName, u.UserFirstName, u.UserLastName, v.VisitDate, v.VisitIp FROM visit as v, user as u WHERE (v.VisitDate BETWEEN ? and ?)";
private static final String TABLE_CHANNEL = "channel";
private static final String COLUMNS_TABLE_CHANNEL = "Family_FamilyId,ChannelName,ChannelPosition,ChannelFather,ChannelEmail,ChannelPassword,ChannelAccessLevel,ChannelSecurityLevel,ChannelIsVisible,ChannelEnterprise";
private static final String COUNT_CHANNEL = " SELECT COUNT(*) FROM "+TABLE_CHANNEL+" where ChannelIsVisible= 1";
private static final String TABLE_CONTENT = "content";
private static final String COLUMNS_TABLE_CONTENT = "Channel_ChannelId,Contenttype_ContenttypeId,ContentName,ContentHtml,ContentIsVisible,ContentPublishDate,ContentEndDate,ContentStatus,ContentshowView";
private static final String COUNT_CONTENT = " SELECT COUNT(*) FROM "+ TABLE_CONTENT +" WHERE ContentIsVisible = ? and ContentStatus = 'A'";
private static final String SELECT_BY_ID_CONTENT = " SELECT " + COLUMNS_TABLE_CONTENT + " from " + TABLE_CONTENT + " where ContentId = ?"; 


	public VisitorDAO() {
		// TODO Auto-generated constructor stub
	}
	
	public VisitorDAO(Connection conn) {
		// TODO Auto-generated constructor stub
		super(conn);
	}

		/**
	 * Insert.
	 * 
	 * @param i
	 * @param type
	 * @param sessionID
	 * @param date
	 * @param time
	 * @param ip_addr
	 * @throws Exception
	 */
	public void insert(int i, String type,String date, String ip_addr) throws Exception
	{
															// RUNNING QUERY
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = conn.prepareStatement(INSERT);
		stmt.setInt(1, i);
		stmt.setString(2, type);
		stmt.setString(3, date);
		stmt.setString(4, ip_addr);
		stmt.executeUpdate();
		conn.close();
	}

	/**
	 * Check whether exist or not.
	 * 
	 * @param i
	 * @param type
	 * @param sessionID
	 * @param date
	 * @return exist or not
	 */
	public boolean isExist(int userId, String date){
		Connection conn = DSManager.getConnection();
		try{
			long result = 0;
																	// RUNNING QUERY
			PreparedStatement stmt = conn.prepareStatement(COUNT_BY_USER_SESSION_DATE);
			stmt.setInt(1, userId);
			stmt.setString(2, date);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				result = rs.getLong(1);
			}
			
			return result>0 ? true : false;
		}catch (Exception e){
			log.error("VisitorDAO", "isExist", e.toString());
		}
		finally
		{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return false;
	}

	/**
	 * Get the count of Today's Visitor. 
	 * 
	 * @param date Today
	 * @return count
	 */
	public Long getTodayCount(String date){
		Connection conn = DSManager.getConnection();
		try{
			long result = 0;
																	// 	RUNNING QUERY
			PreparedStatement stmt = conn.prepareStatement(COUNT_BY_TODAY);
			stmt.setString(1, date+"%");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				result = rs.getLong(1);
			}

			return result;
		}catch (Exception e){
			log.error("VisitorDAO", "getTodayCount", e.toString());
		}
		finally
		{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return (long)0;
	}

	/**
	 * Get the count of Historic Visitor.
	 * 
	 * @return count
	 */
	public Long getHistoricCount(){
		Connection conn = DSManager.getConnection();
		try{
			long result = 0;
																			// 	RUNNING QUERY
			PreparedStatement stmt = conn.prepareStatement(COUNT_HISTORIC_TABLE_VISIT);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				result = rs.getLong(1);
			}

			return result;
		}catch (Exception e){
			log.error("VisitorDAO", "getHistoricCount", e.toString());
		}
		finally
		{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return (long)0;
	}
	
	/**
	 * Get the unique user's count of Historic Visitor.
	 * 
	 * @return count
	 */
	public Long getHistoricUniqueCount(){
		Connection conn = DSManager.getConnection();
		try{
			long result = 0;
																		// RUNNING QUERY
			PreparedStatement stmt = conn.prepareStatement(COUNT_HISTORIC_UNIQUE_TABLA_VISIT);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				result = rs.getLong(1);
			}

			return result;
		}catch (Exception e){
			log.error("VisitorDAO", "getHistoricUniqueCount", e.toString());
		}
		finally
		{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return (long)0;
	}

	/**
	 * Get the count of Channels. 
	 * 
	 * @return count
	 */
	public Long getChannelCount(){
		Connection conn = DSManager.getConnection();
		try{
			long result = 0;
																	// RUNNING QUERY
			PreparedStatement stmt = conn.prepareStatement(COUNT_CHANNEL);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				result = rs.getLong(1);
			}

			return result;
		}catch (Exception e){
			log.error("VisitorDAO", "getChannelCount", e.toString());
		}
		finally
		{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return (long)0;
	}

	/**
	 * Get the count of Contents. 
	 *
	 * @param active
	 * @return count
	 */
	public Long getContentCount(String active){
		Connection conn = DSManager.getConnection();
		try{
			long result = 0;
																	// RUNNING QUERY
			PreparedStatement stmt = conn.prepareStatement(COUNT_CONTENT);
			stmt.setString(1, active);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				result = rs.getLong(1);
			}

			return result;
		}catch (Exception e){
			log.error("VisitorDAO", "getContentCount", e.toString());
		}
		finally
		{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return (long)0;
	}

	/**
	 * Get List Of SQL for DataTable.
	 * 
	 * @param sql
	 * @param language
	 * @return List
	 */
	public JSONArray getContent(String sql, String language){
		JSONArray result = new JSONArray();
		Connection conn = DSManager.getConnection();
		
		try{
																	// RUNNING QUERY
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				JSONObject item = new JSONObject();
				
				String user_id = Utils.checkNull(rs.getString("UserId"));
				String username = "";
				username = getUserName("user", user_id, language);
				item.put("user_id", user_id);
				item.put("username", username);
				item.put("date", Utils.checkNull(rs.getString("VisitDate").replace(".0", "")));
				item.put("ip_addr", Utils.checkNull(rs.getString("VisitIp")));
				item.put("access_comment", rs.getString("ContentName"));
				item.put("access_name", rs.getString("ChannelName"));

				result.put(item);
			}
		}catch (Exception e){
			log.error("VisitorDAO", "getContent", e.toString());
		}
		finally
		{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	}

	/**
	 * Get the unique user's count of Today's Visitor. 
	 * 
	 * @param date Today
	 * @return count
	 */
	public Long getUniqueCountSystemUser(String date){
		Connection conn = DSManager.getConnection();
		try{
			long result = 0;
																	// RUNNING QUERY
			String sql = "SELECT COUNT(DISTINCT B.User_UserId)  from user A, visit B where A.UserStatus='1' AND VisitDate like '" + date + "%' and A.UserRol='1' AND A.UserId=B.User_UserId";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				result = rs.getLong(1);
			}

			return result;
		}catch (Exception e){
			log.error("VisitorDAO", "getUniqueCountSystemUser", e.toString());
		}
		finally
		{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return (long)0;
	}

	/**
	 * Get the unique user's count of Today's Visitor. 
	 * 
	 * @param date Today
	 * @return count
	 */
	public Long getUniqueCountOutsideUser(String time){
		Connection conn = DSManager.getConnection();
		try{
			long result = 0;
																	// 	RUNNING QUERY
			String sql = "SELECT COUNT(DISTINCT B.User_UserId)  from user A, visit B where A.UserStatus='1' AND VisitDate like '" + time + "%' and A.UserRol='2' AND A.UserId=B.User_UserId";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				result = rs.getLong(1);
			}

			return result;
		}catch (Exception e){
			log.error("VisitorDAO", "getUniqueCountOutsideUser", e.toString());
		}
		finally
		{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return (long)0;
	}
	
	/**
	 * Get the count of Current Month's Visitor.
	 * 
	 * @param date Current Month
	 * @return count
	 */
	public String getMonthCount(String date){
		String result = "";
		Connection conn = DSManager.getConnection();
		try
		{
																	// RUNNING QUERY
			PreparedStatement stmt = conn.prepareStatement(VISIT_DATE);
			stmt.setString(1, date+"%");
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				result = rs.getString(1);
			}
			
		}catch (Exception e){
			log.error("VisitorDAO", "getMonthCount", e.toString());
		}
		finally
		{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/** Get Count Of SQL for DataTable.
	 * 
	 * @param sql
	 * @return count
	 */
	public Long getCount(String sql){
		Long result = (long)0;
		Connection conn = DSManager.getConnection();
		
		try{
																	// RUNNING QUERY
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				result = rs.getLong(1);
			}
		}catch (Exception e){
			log.error("VisitorDAO", "getCount", e.toString());
			result = (long)0;
		}
		finally
		{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	}
	/**
	 * GET USER NAME BY TABLE, ID AND LANGUAGE
	 * @param table
	 * @param id
	 * @param language
	 * @return
	 */
	public String getUserName(String table, String id, String language){
		String result="";
		Connection conn = DSManager.getConnection();
		try{
																	// RUNNING QUERY
			String sql ="Select UserName,UserFirstName, UserLastName from "+table+" where UserId=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			if (table.equals("user")){
				stmt.setString(1, id);
			}
			
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				if (table.equals("user")){
					result = rs.getString(1);//+" "+rs.getString(2)+" "+rs.getString(3);
				}
			}

		}catch (Exception e){
			log.error("VisitorDAO", "getName", e.toString());
		}
		finally
		{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	
}