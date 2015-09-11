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

/**
 * 
 * Handle all query for user.
 * 
 * @author CJH
 *
 */

@Repository("UserDAO")
public class UserDAO extends DAO {

	private static final String TABLE_USER = "user";
	private static final String TABLE_VISIT = "visit";
	private static final String COLUMNS_USER = "UserId,UserRol,UserName,UserFirstName, UserLastName, UserEmployeeNumber, UserAccessLevel, UserToken, UserKeyJob, UserKeyDeparment, UserDateOfBirth, UserGender, UserLocation, UserAppoiment, UserEmail, UserAdmisionDate, UserEntered, UserHorary, UserHiererchy, UserStatus, Maindirection_MaindirectionId, UserMuser, City_CityId, Company_CompanyId";

	private static final String COUNT_BY_ID = " select count(*) from "+ TABLE_USER + " where UserId = ? ";
	private static final String COUNT_BY_Musuario = " select count(*) from "+ TABLE_USER + " where UserMuser=? ";
	private static final String SELECT_BY_ID = " select * from " + TABLE_USER+ " where UserId=? ";
	private static final String SELECT_BY_EMAIL = " select "+ COLUMNS_USER +" from "+ TABLE_USER + " where UserEmail=? ";
	private static final String INSERT = " insert into "+ TABLE_USER +" (UserRol,UserName,UserFirstName,UserLastName,UserEmployeeNumber,UserAccessLevel,UserEmail,UserStatus,Maindirection_MaindirectionId,City_CityId,Company_CompanyId) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
	private static final String UPDATE = " update "+ TABLE_USER + " set UserName=?, UserFirstName=?, Userlastname=?, UserEmail=?, UserStatus=?, UserRol=?, MainDirection_MainDirectionId=?, Company_CompanyId=?, City_CityId=? where UserEmployeeNumber=? ";
	private static final String UPDATE_ADDITIONAL = " update "+ TABLE_USER + " set  UserGender=? , UserKeyJob=? , UserToken=? , UserKeyDeparment=? , UserDateoFBirth=? , UserLocation=? , Userappoiment=? , UserAdmisionDate=? , UserMuser=?, userhorary=? , userentered=? , userhiererchy=? where useremployeenumber=? ";
	private static final String DELETE = " update user set UserStatus = ? where UserId = ?";
	private static final String SEARCH_USEREMPLOYEENUMBER = "select UserId from "+ TABLE_USER+ " where UserEmployeeNumber=? ";;

	public UserDAO() {
		// TODO Auto-generated constructor stub
	}

	public UserDAO(Connection conn) {
		// TODO Auto-generated constructor stub
		super(conn);
	}

	/**
	 * Check whether valid user or not.
	 * 
	 * @param i
	 *            UserID
	 * @return boolean
	 */
	public boolean isValidUser(int i) {
		try {
			long result = 0;
			PreparedStatement stmt = conn.prepareStatement(COUNT_BY_ID);
			stmt.setInt(1, i);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				result = rs.getLong(1);
			}

			return result == 1 ? true : false;
		} catch (Exception e) {
			log.error("UserDAO", "isValidUser", e.toString());
		}

		return false;
	}

	public boolean isValidUserWithMusuario(String musuario, String user_id) {
		try {
			long result = 0;
			String sql = COUNT_BY_Musuario;
			//if (user_id.length() > 0)
				//sql += " and id<>'" + user_id + "' ";

			PreparedStatement stmt = conn.prepareStatement(COUNT_BY_Musuario);
			stmt.setInt(1, Integer.parseInt(user_id));
			stmt.setString(1, musuario);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				result = rs.getLong(1);
			}

			return result > 0 ? true : false;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error("UserDAO", "isValidUser", e.toString());
		}

		return false;
	}

	/**
	 * Get User Information from UserID.
	 * 
	 * @param id
	 *            UserID
	 * @return User Information
	 */
	public UserInfo getUserInfo(int id) {
		try {
			PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
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

		} catch (Exception e) {
			log.error("UserDAO", "getUserInfo", e.toString());
		}

		return null;
	}

	// Towa Se modifico de iduser por Email Nota: towa
	public UserInfo getUserInfoFromMusuario(String Email) {
		try {
			PreparedStatement stmt = conn.prepareStatement(SELECT_BY_EMAIL);
			stmt.setString(1, Email);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
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

		} catch (Exception e) {
			log.error("UserDAO", "getUserInfoFromMusuario", e.toString());

		}

		return null;
	}

	// Towa Inicio
	public Long getCount(char type) {
		long result = 0;
		String sql = "select count(UserId) from user ";

		if (type == 'a') {
			sql += "where UserStatus = 1";

		}
		try {

			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				result = rs.getLong(1);
			}

			return result;
		} catch (Exception e) {
			log.error("UserDAO", "getCount", e.toString());
		}

		return (long) 0;
	}

	// Towa fin

	/**
	 * Get Count Of SQL for DataTable.
	 * 
	 * @param sql
	 * @return count
	 */
	public Long getCount(String sql) {
		Long result = (long) 0;

		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				result = rs.getLong(1);
			}
		} catch (Exception e) {
			log.error("UserDAO", "getCount", e.toString());
			result = (long) 0;
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

	// Towa Comentado Para probar funcionalidad
	/*
	 * private String getName(String table, String div, String id, String
	 * language){ String result = ""; try{ String value=""; PreparedStatement
	 * stmt = null; if (table.equals("code")){ stmt = conn.prepareStatement(
	 * " select * from menu a where a.menudivl=? and a.menucode=? " );
	 * stmt.setString(1, div); stmt.setString(2, id); value="value"; }else{ stmt
	 * = conn.prepareStatement(" select * from bbva_" + table +
	 * " a where a.id=? " ); stmt.setLong(1, Utils.getLong(id)); value="name"; }
	 * 
	 * ResultSet rs = stmt.executeQuery(); if (rs.next()){ if
	 * (language.length()>0) result = rs.getString(value+"_"+language); else
	 * result = rs.getString(value); } }catch (Exception e){
	 * log.error("UserDAO", "getName", e.toString()); }
	 * 
	 * return result; }
	 */

	/**
	 * Get Content List for DataTable.
	 * 
	 * @param sql
	 * @param language
	 * @return ContentList
	 */
	public JSONArray getContent(String sql, String language) {
		JSONArray result = new JSONArray();

		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				JSONObject item = new JSONObject();

				item.put("id", rs.getString("UserEmployeeNumber"));
				item.put("level", Utils.checkNull(rs.getString("UserRol")));
				item.put("username", Utils.checkNull(rs.getString("UserName")));
				item.put("firstname",
						Utils.checkNull(rs.getString("UserFirstName")));
				item.put("lastname",
						Utils.checkNull(rs.getString("UserLastName")));
				item.put("email", Utils.checkNull(rs.getString("UserEmail")));
				item.put("musuario", Utils.checkNull(rs.getString("UserMuser")));
				item.put("active", rs.getString("UserStatus"));

				item.put("status", "");
				item.put("action", "");

				result.put(item);
			}
		} catch (Exception e) {
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
	 * @param security_level
	 * @throws Exception
	 */

	public void insert(String NumberEmpleyoo, String username,
			String firstname, String lastname, String email, String password,
			String active, String level, String security_level, int Direction,
			int Company, int City) throws Exception {

		if (Company == 0)
			Company = 1;
		if (City == 0)
			City = 1;
		if (Direction == 0)
			Direction = 102;

		int nivel_segu = NumNull(security_level);

		PreparedStatement stmt = conn.prepareStatement(INSERT);
		stmt.setString(1, level);
		stmt.setString(2, username);
		stmt.setString(3, firstname);
		stmt.setString(4, lastname);
		stmt.setString(5, NumberEmpleyoo);
		stmt.setInt(6, nivel_segu);
		stmt.setString(7, email);
		stmt.setString(8, active);
		stmt.setInt(9, Direction);
		stmt.setInt(10, City);
		stmt.setInt(11, Company);
		stmt.executeUpdate();
	}

	public void updateAdditional(String NumberEmpleyoo, String Gender,
			String Keyjob, String Token, String KeyDepartament,
			String Birthday, String Location, String Appoiment,
			String Admission, String Muser, String Horary, String entered,
			String Hierarchy) throws Exception {

		int NToken = NumNull(Token);
		int NKeyDepartament = NumNull(KeyDepartament);
		int NHierarchy = NumNull(Hierarchy);
		String Fecha = Birthday;
		String FechAdmin = Admission;

		PreparedStatement stmt = conn.prepareStatement(UPDATE_ADDITIONAL);

		stmt.setString(1, Gender);
		stmt.setString(2, Keyjob);
		stmt.setInt(3, NToken);
		stmt.setInt(4, NKeyDepartament);
		stmt.setString(5, Fecha);
		stmt.setString(6, Location);
		stmt.setString(7, Appoiment);
		stmt.setString(8, FechAdmin);
		stmt.setString(9, Muser);
		stmt.setString(10, Horary);
		stmt.setString(11, entered);
		stmt.setInt(12, NHierarchy);
		stmt.setString(13, NumberEmpleyoo);
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
	 * @param level
	 * @param security_level
	 * @throws Exception
	 */

	public void update(String NumberEmpleyoo, String UseName, String FirstName,
			String LastName, String Email, String active, String level,
			String security_level, int Direction, int Company, int City)
			throws Exception {

		PreparedStatement stmt = conn.prepareStatement(UPDATE);
		stmt.setString(1, UseName);
		stmt.setString(2, FirstName);
		stmt.setString(3, LastName);
		stmt.setString(4, Email);
		stmt.setString(5, active);
		stmt.setString(6, level);
		//stmt.setString(7, security_level);
		stmt.setInt(7, Direction);
		stmt.setInt(8, Company);
		stmt.setInt(9, City);
		stmt.setString(10, NumberEmpleyoo);
		stmt.executeUpdate();
	}

	/**
	 * Delete user data.
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void delete(int userId) throws Exception {

		PreparedStatement stmt = conn.prepareStatement(DELETE);
		stmt.setInt(1, 9);
		stmt.setInt(2, userId);
		stmt.executeUpdate();
	}
	
	/**
	 * Delete user data.
	 * 
	 * @param id
	 * @throws Exception
	 */
	public int searchEmployeeNumber(String NumEmployee) throws Exception {

		int result = 0;
		PreparedStatement stmt = conn.prepareStatement(SEARCH_USEREMPLOYEENUMBER);
		stmt.setString(1, NumEmployee);
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			result = rs.getInt(1);
		}
		
		return result;
	}

	/**
	 * Get code from value.
	 * 
	 * @param table
	 * @param div
	 * @param value
	 * @return code
	 */

	// Towa Comentado para prueba de funcionalidad
	/*
	 * public String getCode(String table, String div, String value){ String
	 * result = ""; try{ PreparedStatement stmt = null; if
	 * (table.equals("code")){ stmt = conn.prepareStatement(
	 * " select a.* from bbva_code a where a.div=? and ( a.value=? or a.value_en=? or a.value_me=? ) "
	 * ); stmt.setString(1, div); stmt.setString(2, value); stmt.setString(3,
	 * value); stmt.setString(4, value);
	 * 
	 * value="code"; }else{ stmt =
	 * conn.prepareStatement(" select a.* from bbva_" + table +
	 * " a where ( a.name=? or a.name_en=? or a.name_me=? ) " );
	 * stmt.setString(1, value); stmt.setString(2, value); stmt.setString(3,
	 * value);
	 * 
	 * value="id"; }
	 * 
	 * ResultSet rs = stmt.executeQuery(); if (rs.next()){ result =
	 * rs.getString(value); } }catch (Exception e){ log.error("UserDAO",
	 * "getCode", e.toString()); }
	 * 
	 * return result; }
	 */

	// Towa Comentado para prueba de funcionalidad
	/*
	 * public Long getCode(int type, String key, String value){ Long result =
	 * (long)0; try{ PreparedStatement stmt = null; if (type==0){ stmt =
	 * conn.prepareStatement
	 * (" select id from bbva_city where parent_id=0 and original_key=? " );
	 * stmt.setString(1, key); }else if (type==1){ stmt = conn.prepareStatement(
	 * " select id from bbva_city where (name=? or name_en=? or name_me=? ) and original_key=? "
	 * ); stmt.setString(1, value); stmt.setString(2, value); stmt.setString(3,
	 * value); stmt.setString(4, key); }else if (type==2){ stmt =
	 * conn.prepareStatement
	 * (" select id from bbva_division where original_key=? " );
	 * stmt.setString(1, key); }
	 * 
	 * ResultSet rs = stmt.executeQuery(); if (rs.next()){ result =
	 * rs.getLong(1); } }catch (Exception e){ log.error("UserDAO",
	 * "getDivisionCode", e.toString()); }
	 * 
	 * return result; }
	 */

	/**
	 * Insert validation for reset password.
	 * 
	 * @param user_id
	 * @param session_id
	 * @param time
	 * @throws Exception
	 */

	// Comentado para Pruebas de funcionalidad
	/*
	 * public void insertValidation(String user_id, String session_id, String
	 * time) throws Exception { PreparedStatement stmt =
	 * conn.prepareStatement(INSERT_VALIDATION); stmt.setString(1, user_id);
	 * stmt.setString(2, session_id); stmt.setString(3, time);
	 * stmt.executeUpdate(); }
	 */

	/**
	 * Get Validation.
	 * 
	 * @param user_id
	 * @param session_id
	 * @return validation
	 * @throws Exception
	 */

	// Towa Comentado para pruebas de funcionalidad
	/*
	 * public ValidationInfo getValidation(String user_id, String session_id)
	 * throws Exception { ValidationInfo result = new ValidationInfo();
	 * 
	 * try{ PreparedStatement stmt = conn.prepareStatement(SELECT_VALIDATION);
	 * stmt.setString(1, user_id); stmt.setString(2, session_id); ResultSet rs =
	 * stmt.executeQuery();
	 * 
	 * if (rs.next()){ result.setId(rs.getLong("id"));
	 * result.setCreatedAt(Utils.checkNull(rs.getString("created_at")));
	 * 
	 * return result; } }catch (Exception e){}
	 * 
	 * return null; }
	 */

	// Towa Convierte un String de forma DD/MM/AA en uno AAAA-MM-DD para
	// almacenar en base
	public String ConverFecha(String SaveData) {

		String dia;
		String mes;
		String newData = null;
		int año;

		if (SaveData == "") {
			newData = Utils.getYear() + "-" + Utils.getMonth() + "-"
					+ Utils.getDay();
		}

		else {
			int inicio = 0;
			int fin = SaveData.indexOf("/");
			dia = SaveData.substring(inicio, fin);
			inicio = fin;
			fin = SaveData.indexOf("/", ++inicio);
			mes = SaveData.substring(inicio, fin);
			inicio = fin + 1;
			año = Integer.parseInt(SaveData.substring(inicio));

			if (año >= 50) {
				newData = String.valueOf("19" + año + "-" + mes + "-" + dia);
			} else {
				newData = String.valueOf("20" + año + "-" + mes + "-" + dia);
			}
		}
		return newData;
	}

	// Towa Metodo que comvierte una fecha de AAAA-MM-DD en una de DD/MM/AA para
	// visualisar en la ventana de user
	public String ConverFechaVista(String DataSaved) {

		int Day = 0;
		int Moth = 0;
		int Year = 0;

		Day = Integer.parseInt(DataSaved.substring(8));
		Moth = Integer.parseInt(DataSaved.substring(5, 7));
		Year = Integer.parseInt(DataSaved.substring(2, 4));

		String ViewData = Day + "/" + Moth + "/" + Year;

		return ViewData;
	}

	// Towa Convierte los String vacios en 0 para agregarlos en la tabla
	public int NumNull(String value) {
		int ValueNum = 0;
		if (value != "")
			ValueNum = Integer.parseInt(value);

		return ValueNum;
	}
}
