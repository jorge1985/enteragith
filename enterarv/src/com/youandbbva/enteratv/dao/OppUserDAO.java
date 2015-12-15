package com.youandbbva.enteratv.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import com.youandbbva.enteratv.DAO;
import com.youandbbva.enteratv.Utils;
import com.youandbbva.enteratv.domain.UserInfo;
import com.youandbbva.enteratv.domain.ValidationInfo;

/**
 * 
 * Handle all query for user in Opportunities.
 * 
 * @author CJH
 *
 */

@Repository("OppUserDAO")
public class OppUserDAO extends DAO{
	
	/**
	 * SQL queries
	 */
	
	private static final String TABLE_USER = "user";
	private static final String COLUMNS_USER = "UserId,UserRol,UserName,UserFirstName, UserLastName, UserEmployeeNumber, UserAccessLevel, UserToken, UserKeyJob, UserKeyDeparment, UserDateOfBirth, UserGender, UserLocation, UserAppoiment, UserEmail, UserAdmisionDate, UserEntered, UserHorary, UserHiererchy, UserStatus, Maindirection_MaindirectionId, UserMuser, City_CityId, Company_CompanyId";
	private static final String COLUMNS_USER2 = "UserRol,UserName,UserFirstName, UserLastName, UserEmployeeNumber, UserAccessLevel, UserToken, UserKeyJob, UserKeyDeparment, UserDateOfBirth, UserGender, UserLocation, UserAppoiment, UserEmail, UserAdmisionDate, UserEntered, UserHorary, UserHiererchy, UserStatus, Maindirection_MaindirectionId, UserMuser, City_CityId, Company_CompanyId";
	private final String COUNT = " select count(*) from "+ TABLE_USER;
	private final String COUNT_BY_ID = " select count(*) from "+ TABLE_USER +" where UserId=? ";
	private final String SELECT_BY_ID = " select " + COLUMNS_USER + " from "+ TABLE_USER +" where UserId=? ";
	private final String INSERT = " insert into "+ TABLE_USER +" ( " + COLUMNS_USER2 + " ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ";
	private final String UPDATE = " update "+ TABLE_USER +" set username=?, firstname=?, lastname=?, email=?, password=?, division=?, city=?, people_manager=?, new_hire=?, jobgrade=?, payowner=?, promote=?, parent_division=?, parent_city=?, active=?, updated_at=? , level=? where id=? ";
	private final String DELETE = " delete from "+ TABLE_USER +" where id=? ";
	
	public OppUserDAO() {
		// TODO Auto-generated constructor stub
	}
	
	public OppUserDAO(Connection conn) {
		// TODO Auto-generated constructor stub
		super(conn);
	}

	/**
	 * Check whether valid user or not.
	 * 
	 * @param id UserID
	 * @return boolean
	 */
	public boolean isValidUser(String id){
		try{
			long result = 0;
																	// RUNNING QUERY
			PreparedStatement stmt = conn.prepareStatement(COUNT_BY_ID);
			stmt.setString(1, id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				result = rs.getLong(1);
			}
			
			return result==1 ? true : false;
		}catch (Exception e){

			log.error("UserDAO", "isValidUser", e.toString());
		}

		return false;
	}
	
	/**
	 * Get User Information from UserID.
	 * 
	 * @param id UserID
	 * @return User Information
	 */
	public UserInfo getUserInfo(String id){
		try{
																	// RUNNING QUERY
			PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID);
			stmt.setString(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				UserInfo result = new UserInfo();
				result.setUserId(rs.getInt(1));
				result.setUserRol(rs.getString(2));
				result.setUserName(Utils.checkNull(rs.getString(3)));
				result.setUserFirstName(Utils.checkNull(rs.getString(4)));
				result.setUserLastName(Utils.checkNull(rs.getString(5)));
				result.setUserEmployeeNumber(rs.getString(6));
				result.setUserAccessLevel(rs.getInt(7));
				result.setUserToken(rs.getInt(8));
				result.setUserKeyJob(rs.getString(9));
				result.setUserKeyDeparment(rs.getInt(10));
				result.setUserDateOfBirth(rs.getString(11));
				result.setUserGender(rs.getString(12));
				result.setUserLocation(rs.getString(13));
				result.setUserAppoiment(rs.getString(14));
				result.setUserEmail(Utils.checkNull(rs.getString(15)));
				result.setUserAdmisionDate(rs.getString(16));
				result.setUserEntered(rs.getString(17));
				result.setUserHorary(rs.getString(18));
				result.setUserHierech(rs.getInt(19));
				result.setUserStatus(rs.getInt(20));
				result.setMainDirection(rs.getInt(21));
				result.setUserMuser(rs.getString(22));
				result.setCity(rs.getInt(23));
				result.setCompany(rs.getInt(24));
				return result;
			}
			
		}catch (Exception e){
			log.error("UserDAO", "isValidUser", e.toString());
		}

		return null;
	}

	/**
	 * Get count of user.
	 * 
	 * @param level Administrator or User
	 * @param active Active or Nonactive
	 * @return count of User
	 */
	public Long getCount(String level, String active){
		try{
			long result = 0;
																	// 	RUNNING QUERY
			String sql = COUNT;
			if (level.length()>0 || active.length()>0){
				sql += " where ";
				
				if (level.length()>0)
					sql += " level='" + level + "' ";

				if (active.length()>0){
					if (level.length()>0)
						sql += " and ";
					sql += " active='" + active + "' ";
				}
			}
																	// RUNNING QUERY
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				result = rs.getLong(1);
			}

			return result;
		}catch (Exception e){
			log.error("UserDAO", "getCount", e.toString());
		}
		
		return (long)0;
	}

	
	/**
	 * Get Count Of SQL for DataTable.
	 * 
	 * @param sql
	 * @return count
	 */
	public Long getCount(String sql){
		Long result = (long)0;
		
		try{
																	// RUNNING QUERY
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				result = rs.getLong(1);
			}
		}catch (Exception e){
			log.error("UserDAO", "getCount", e.toString());
			result = (long)0;
		}
		
		return result;
	}

	/**
	 * Get name from code.
	 * 
	 * @param table
	 * @param div
	 * @param id
	 * @param language
	 * @return name
	 */
	private String getName(String table, String div, String id, String language){
		String result = "";
		try{
			String value="";
			PreparedStatement stmt = null;
			if (table.equals("code")){
																	// RUNNING QUERY
				stmt = conn.prepareStatement(" select * from bbva_code a where a.div=? and a.code=? " );
				stmt.setString(1, div);
				stmt.setString(2, id);
				value="value";
			}else{
																	// RUNNING QUERY
				stmt = conn.prepareStatement(" select * from bbva_" + table + " a where a.id=? " );
				stmt.setLong(1, Utils.getLong(id));
				value="name";
			}
			
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				if (language.length()>0)
					result = rs.getString(value+"_"+language);
				else
					result = rs.getString(value);
			}
		}catch (Exception e){
			log.error("UserDAO", "getName", e.toString());
		}

		return result;
	}
	
	/**
	 * Get Content List for DataTable.
	 * 
	 * @param sql
	 * @param language
	 * @return ContentList
	 */
	public JSONArray getContent(String sql, String language){
		JSONArray result = new JSONArray();
		
		try{
																	// RUNNING QUERY
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				JSONObject item = new JSONObject();
				
				item.put("id", rs.getString("id"));
				item.put("level", Utils.checkNull(rs.getString("level")));
				item.put("username", Utils.checkNull(rs.getString("username")));
				item.put("firstname", Utils.checkNull(rs.getString("firstname")));
				item.put("lastname", Utils.checkNull(rs.getString("lastname")));
				item.put("email", Utils.checkNull(rs.getString("email")));
				
				String division=Utils.checkNull(rs.getString("division"));
				item.put("division", division);
				item.put("division_name", "");
				if (division.length()>0)
					item.put("division_name", getName("division", "", division, language));
				
				String city=Utils.checkNull(rs.getString("city"));
				item.put("city", city);
				item.put("city_name", "");
				if (city.length()>0)
					item.put("city_name", getName("city", "", city, language));
				
				item.put("people_manager", Utils.checkNull(rs.getString("people_manager")));
				item.put("new_hire", Utils.checkNull(rs.getString("new_hire")));
				
				String jobgrade=Utils.checkNull(rs.getString("jobgrade"));
				item.put("jobgrade", jobgrade);
				item.put("jobgrade_name", "");
				if (jobgrade.length()>0)
					item.put("jobgrade_name", getName("code", "chn6", jobgrade, language));
				
				String payowner=Utils.checkNull(rs.getString("payowner"));
				item.put("payowner", payowner);
				item.put("payowner_name", "");
				if (payowner.length()>0)
					item.put("payowner_name", getName("code", "chn7", payowner, language));
				
				String promote=Utils.checkNull(rs.getString("promote"));
				item.put("promote", promote);
				item.put("promote_name", "");
				if (promote.length()>0)
					item.put("promote_name", getName("code", "chn3", promote, language));
				
				item.put("created_at", Utils.checkNull(rs.getString("created_at")));
				item.put("updated_at", Utils.checkNull(rs.getString("updated_at")));
				item.put("active", rs.getString("active"));
				
				item.put("status", "");
				item.put("action", "");
				
				result.put(item);
			}
		}catch (Exception e){
			log.error("UserDAO", "getContent", e.toString());
		}
		
		return result;
	}
	
	/**
	 * insert user data.
	 * 
	 * @param id
	 * @param username
	 * @param firstname
	 * @param lastname
	 * @param email
	 * @param password
	 * @param division
	 * @param city
	 * @param people_manager
	 * @param newhire
	 * @param jobgrade
	 * @param payowner
	 * @param promote
	 * @param active
	 * @param parent_division
	 * @param parent_city
	 * @param level
	 * @throws Exception
	 */
	public void insert(String id, String username, String firstname, String lastname, String email, String password, Long division, Long city, String people_manager, String newhire, String jobgrade, String payowner, String promote, String active, Long parent_division, Long parent_city, String level) throws Exception {
		String today = Utils.getTodayWithTime();
																	// RUNNING QUERY
		PreparedStatement stmt = conn.prepareStatement(INSERT);
		stmt.setString(1, id);
		stmt.setString(2, username);
		stmt.setString(3, firstname);
		stmt.setString(4, lastname);
		stmt.setString(5, email);
		stmt.setString(6, password);
		stmt.setLong(7, division);
		stmt.setLong(8, city);
		stmt.setString(9, people_manager);
		stmt.setString(10, newhire);
		stmt.setString(11, jobgrade);
		stmt.setString(12, payowner);
		stmt.setString(13, promote);
		stmt.setLong(14, parent_division);
		stmt.setLong(15, parent_city);
		stmt.setString(16, active);
		stmt.setString(17, today);
		stmt.setString(18, today);
		stmt.setString(19, level);
		stmt.executeUpdate();
	}
	
	/**
	 * update user data.
	 * 
	 * @param id
	 * @param username
	 * @param firstname
	 * @param lastname
	 * @param email
	 * @param password
	 * @param division
	 * @param city
	 * @param people_manager
	 * @param newhire
	 * @param jobgrade
	 * @param payowner
	 * @param promote
	 * @param active
	 * @param parent_division
	 * @param parent_city
	 * @throws Exception
	 */
	public void update(String id, String username, String firstname, String lastname, String email, String password, Long division, Long city, String people_manager, String newhire, String jobgrade, String payowner, String promote, String active, Long parent_division, Long parent_city, String level) throws Exception {
		String today = Utils.getTodayWithTime();
																	// RUNNING QUERY
		PreparedStatement stmt = conn.prepareStatement(UPDATE);
		stmt.setString(1, username);
		stmt.setString(2, firstname);
		stmt.setString(3, lastname);
		stmt.setString(4, email);
		stmt.setString(5, password);
		stmt.setLong(6, division);
		stmt.setLong(7, city);
		stmt.setString(8, people_manager);
		stmt.setString(9, newhire);
		stmt.setString(10, jobgrade);
		stmt.setString(11, payowner);
		stmt.setString(12, promote);
		stmt.setLong(13, parent_division);
		stmt.setLong(14, parent_city);
		stmt.setString(15, active);
		stmt.setString(16, today);
		stmt.setString(17, level);
		stmt.setString(18, id);
		stmt.executeUpdate();
	}
	
	/**
	 * Delete user data.
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void delete(String id) throws Exception {
																	// RUNNING QUERY
		PreparedStatement stmt = conn.prepareStatement(DELETE);
		stmt.setString(1, id);
		stmt.executeUpdate();
	}

	/**
	 * backup table.
	 * 
	 * @param table
	 * @throws Exception
	 */
	public void rename(String table) throws Exception {
																	// RUNNING QUERY
		PreparedStatement stmt = conn.prepareStatement("rename table bbva_user_opport to bbva_user_opport_"+table);
		stmt.executeUpdate();
	}
	
	/**
	 * Get code from value.
	 * 
	 * @param table
	 * @param div
	 * @param value
	 * @return code
	 */
	public String getCode(String table, String div, String value){
		String result = "";
		try{
			PreparedStatement stmt = null;
			if (table.equals("code")){
																	// RUNNING QUERY
				stmt = conn.prepareStatement(" select * from bbva_code a where div=? and ( value=? or value_en=? or value_me=? ) " );
				stmt.setString(1, div);
				stmt.setString(2, value);
				stmt.setString(3, value);
				stmt.setString(4, value);
				
				value="code";
			}else{
																	// RUNNING QUERY
				stmt = conn.prepareStatement(" select * from " + table + " a where ( name=? or name_en=? or name_me=? ) " );
				stmt.setString(1, value);
				stmt.setString(2, value);
				stmt.setString(3, value);
				
				value="id";
			}
			
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				result = rs.getString(value);
			}
		}catch (Exception e){
			log.error("UserDAO", "getCode", e.toString());
		}

		return result;
	}

}
