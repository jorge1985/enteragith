package com.youandbbva.enteratv.dao;

import java.io.EOFException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.*;
import javax.mail.internet.*;

import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import com.youandbbva.enteratv.DAO;
import com.youandbbva.enteratv.DSManager;
import com.youandbbva.enteratv.UsuarioEnBaseDatos;
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

	/**
	 * SQL queries
	 */
	private static final String TABLE_USER = "user";
	private static final String TABLE_VISIT = "visit";
	private static final String COLUMNS_USER = "UserId,UserRol,UserName,UserFirstName, UserLastName, UserEmployeeNumber, UserAccessLevel, UserToken, UserKeyJob, UserKeyDeparment, UserDateOfBirth, UserGender, UserLocation, UserAppoiment, UserEmail, UserAdmisionDate, UserEntered, UserHorary, UserHiererchy, UserStatus, Maindirection_MaindirectionId, UserMuser, City_CityId, Company_CompanyId";
	private static final String COUNT_BY_ID = " select count(*) from "
			+ TABLE_USER + " where UserId = ? ";
	private static final String COUNT_BY_Musuario = " select count(*) from "
			+ TABLE_USER + " where UserMuser=? ";
	private static final String SELECT_BY_ID = " select * from " + TABLE_USER
			+ " where UserId=? ";
	private static final String SELECT_BY_EMAIL = " select * from user where UserEmail=? ";
	private static final String INSERT = " insert into "
			+ TABLE_USER
			+ " (UserRol,UserName,UserFirstName,UserLastName,UserEmployeeNumber,UserAccessLevel,UserEmail,UserStatus,Maindirection_MaindirectionId,City_CityId,Company_CompanyId) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
	private static final String UPDATE = " update "
			+ TABLE_USER
			+ " set UserName=?, UserFirstName=?, Userlastname=?, UserEmail=?, UserStatus=?, UserRol=?, MainDirection_MainDirectionId=?, Company_CompanyId=?, City_CityId=? where UserEmployeeNumber=? ";
	private static final String UPDATE_ADDITIONAL = " update "
			+ TABLE_USER
			+ " set  UserGender=? , UserKeyJob=? , UserToken=? , UserKeyDeparment=? , UserDateoFBirth=? , UserLocation=? , Userappoiment=? , UserAdmisionDate=? , UserMuser=?, userhorary=? , userentered=? , userhiererchy=? where useremployeenumber=? ";
	private static final String DELETE = " update user set UserStatus = ? where UserId = ?";
	
	private static final String DELETET = " delete from user where UserEmail =?";
	private static final String SEARCH_USEREMPLOYEENUMBER = "select UserId from "
			+ TABLE_USER + " where UserEmployeeNumber=? ";

	public UserDAO() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * Check whether valid user or not.
	 * 
	 * @param i
	 *            UserID
	 * @return boolean
	 */
	public boolean isValidUser(int i) {
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try {
			long result = 0;
			// RUNNING QUERY
			stmt = conn.prepareStatement(COUNT_BY_ID);
			stmt.setInt(1, i);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				result = rs.getLong(1);
			}

			return result == 1 ? true : false;
		} catch (Exception e) {
			log.error("UserDAO", "isValidUser", e.toString());
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

		return false;
	}

	/**
	 * IS VALID USER WITH MUSUARIO BY MUSUARIO AND USER_ID
	 * 
	 * @param musuario
	 * @param user_id
	 * @return
	 */
	public boolean isValidUserWithMusuario(String musuario, String user_id) {
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt  = null;
		try {
			long result = 0;
			String sql = COUNT_BY_Musuario;
			// RUNNING QUERY
			stmt = conn.prepareStatement(COUNT_BY_Musuario);
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
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try {
			// RUNNING QUERY
			stmt = conn.prepareStatement(SELECT_BY_ID);
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

		return null;
	}

	/**
	 * GET USER INFORMATION FROM MUSUARIO BY EMAIL
	 * 
	 * @param Email
	 * @return
	 */
	public UserInfo getUserInfoFromMusuario(String Email) {
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try {
			// RUNNING QUERY
			stmt = conn.prepareStatement(SELECT_BY_EMAIL);
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

		return null;
	}

	/**
	 * GET COUNT BY TYPE
	 * 
	 * @param type
	 * @return
	 */
	public Long getCount(char type) {
		long result = 0;
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		String sql = "select count(UserId) from user ";

		if (type == 'a') {
			sql += "where UserStatus = 1";

		}
		try {
			// RUNNING QUERY
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				result = rs.getLong(1);
			}

			return result;
		} catch (Exception e) {
			log.error("UserDAO", "getCount", e.toString());
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

		return (long) 0;
	}

	/**
	 * Get Count Of SQL for DataTable.
	 * 
	 * @param sql
	 * @return count
	 */
	public Long getCount(String sql) {
		Long result = (long) 0;
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try {
			// RUNNING QUERY
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				result = rs.getLong(1);
			}
		} catch (Exception e) {
			log.error("UserDAO", "getCount", e.toString());
			result = (long) 0;
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
	 * Get Content List for DataTable.
	 * 
	 * @param sql
	 * @param language
	 * @return ContentList
	 */
	public JSONArray getContent(String sql, String language) {
		JSONArray result = new JSONArray();
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt =  null;

		try {
			// RUNNING QUERY
			stmt = conn.prepareStatement(sql);
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
		
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		if (Company == 0)
			Company = 1;
		if (City == 0)
			City = 1;
		if (Direction == 0)
			Direction = 102;

		int nivel_segu = NumNull(security_level);
		// RUNNING QUERY
		stmt = conn.prepareStatement(INSERT);
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
		
		stmt.close();
		conn.close();
	}

	/**
	 * UPDATE ADDITIONAL
	 * 
	 * @param NumberEmpleyoo
	 * @param Gender
	 * @param Keyjob
	 * @param Token
	 * @param KeyDepartament
	 * @param Birthday
	 * @param Location
	 * @param Appoiment
	 * @param Admission
	 * @param Muser
	 * @param Horary
	 * @param entered
	 * @param Hierarchy
	 * @throws Exception
	 */
	public void updateAdditional(String NumberEmpleyoo, String Gender,
			String Keyjob, String Token, String KeyDepartament,
			String Birthday, String Location, String Appoiment,
			String Admission, String Muser, String Horary, String entered,
			String Hierarchy) throws Exception {
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		int NToken = NumNull(Token);
		int NKeyDepartament = NumNull(KeyDepartament);
		int NHierarchy = NumNull(Hierarchy);
		String Fecha = Birthday;
		String FechAdmin = Admission;
		// RUNNING QUERY
		stmt = conn.prepareStatement(UPDATE_ADDITIONAL);

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
		
		stmt.close();
		conn.close();
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
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		// RUNNING QUERY
		stmt = conn.prepareStatement(UPDATE);
		stmt.setString(1, UseName);
		stmt.setString(2, FirstName);
		stmt.setString(3, LastName);
		stmt.setString(4, Email);
		stmt.setString(5, active);
		stmt.setString(6, level);
		stmt.setInt(7, Direction);
		stmt.setInt(8, Company);
		stmt.setInt(9, City);
		stmt.setString(10, NumberEmpleyoo);
		stmt.executeUpdate();
		
		stmt.close();
		conn.close();
	}

	/**
	 * Delete user data.
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void delete(int userId) throws Exception {
		// RUNNING QUERY
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		stmt = conn.prepareStatement(DELETE);
		stmt.setInt(1, 9);
		stmt.setInt(2, userId);
		stmt.executeUpdate();
		
		stmt.close();
		conn.close();
	}

	/**
	 * Delete user data.
	 * 
	 * @param id
	 * @throws Exception
	 */
	public int searchEmployeeNumber(String NumEmployee) throws Exception {
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		int result = 0;
		// RUNNING QUERY
		stmt = conn
				.prepareStatement(SEARCH_USEREMPLOYEENUMBER);
		stmt.setString(1, NumEmployee);
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			result = rs.getInt(1);
		}
			stmt.close();
			conn.close();
		

		return result;
	}

	
	/**
	 * CONVERT DATE BY SAVE DATA
	 * 
	 * @param SaveData
	 * @return
	 */
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

	/**
	 * CONVERT DATE OF VISIT
	 * 
	 * @param DataSaved
	 * @return
	 */
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

	/**
	 * NUMBER NULL BY VALUE
	 * 
	 * @param value
	 * @return
	 */
	public int NumNull(String value) {
		int ValueNum = 0;
		if (value != "")
			ValueNum = Integer.parseInt(value);

		return ValueNum;
	}

	public int registroMAX() {
		int max = 0;

		return max;
	}

	// Carga de correo de prueba TOWA
	// public void cargaCorreoPrueba() throws SQLException{
	// String INSERTUSurario = " insert into "+ TABLE_USER
	// +" (UserRol, UserName, UserFirstName, UserLastName, UserEmployeeNumber, UserAccessLevel, UserToken, UserKeyJob, UserKeyDeparment, UserDateOfBirth, UserGender, UserLocation, UserAppoiment, UserEmail, UserAdmisionDate, UserEntered, UserHorary, UserHiererchy, UserStatus, Maindirection_MaindirectionId, UserMuser, City_CityId, Company_CompanyId) values (19, '2', 'ariana', 'santiago', 'zarate', '00020', 0, 8, '03', 0, '2015-09-01', 'F', 'df', '', 'cargaroll@gmail.com', '2015-09-10', '', '', 0, 1, 34, 'Ariana', 1, 1)";
	//
	// PreparedStatement stmt = conn.prepareStatement(INSERTUSurario);
	// stmt.executeUpdate();
	// }

	public UsuarioEnBaseDatos validarCorreo(String Email)
	{
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try {
		stmt = conn.prepareStatement(SELECT_BY_EMAIL);
		stmt.setString(1, Email);
		
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
				
			String strUsuario = rs.getString("UserName");
			if (!(strUsuario.equalsIgnoreCase("USUARIO")))
			{
				return UsuarioEnBaseDatos.NOMBRECORRECTO;
			}
			
			return UsuarioEnBaseDatos.NOMBREINCORRECTO;
		}
			
		return UsuarioEnBaseDatos.NOEXISTE;
		} catch (SQLException e) 
		
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		return UsuarioEnBaseDatos.NOEXISTE;
	}

	public void agregarUsuario(String correo, String nombre, String apellpat, String apellmat) {
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try {
			
			stmt = conn
					.prepareStatement(" INSERT INTO user (UserRol, UserName,  UserFirstName, UserLastName, UserEmployeeNumber, UserDateOfBirth, UserEmail, UserAdmisionDate, UserStatus, Maindirection_MaindirectionId, UserMuser, City_CityId, Company_CompanyId ) VALUES ('2', '"
							+ nombre
							+ "','"+ apellpat +"','"+ apellmat +"', '111', '2015-10-18', '"
							+ correo
							+ "', '2015-10-18', 1, 51, 'aa', 4, 20 )");
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
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

	}
	
	public void actualizadatos(String correo,String nombre, String apellpat, String apellmat) throws Exception {
		Connection conn = DSManager.getConnection();
		//update user set UserName = "bbb", UserFirstName = "bbb", UserLastName ="bbb"  where UserEmail = "raul.henry@bbva.com"
		String sql =" update user set UserName = ?, UserFirstName = ?, UserLastName =?  where UserEmail = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, nombre);
		stmt.setString(2, apellpat);
		stmt.setString(3, apellmat);
		stmt.setString(4, correo);
		stmt.execute();
		stmt.close();
		conn.close();
	}
}
