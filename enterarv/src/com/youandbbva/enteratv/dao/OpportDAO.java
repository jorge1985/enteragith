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
import com.youandbbva.enteratv.Utils;
import com.youandbbva.enteratv.domain.BrandInfo;
import com.youandbbva.enteratv.domain.OpportCityInfo;
import com.youandbbva.enteratv.domain.OpportStateInfo;
import com.youandbbva.enteratv.domain.OpportunitiesInfo;

/**
 * 
 * Handle all query for Opportunities.
 * 
 * @author CJH
 *
 */

@Repository("OpportDAO")
public class OpportDAO extends DAO{

	private final String TABLE_NAME__BRAND = "carbrand";
	private final String SELECT__BRAND = " select * from " + TABLE_NAME__BRAND + " "; 
	private final String SELECT_NAME__BRAND = " select CarbrandName from " + TABLE_NAME__BRAND + " where CarbrandId=? and CarbrandIsActive=1";
	
	private final String TABLE_NAME__STATE = "state";
	private final String SELECT__STATE = " select * from " + TABLE_NAME__STATE + ""; 
	private final String SELECT_NAME__STATE = " select StateName from " + TABLE_NAME__STATE + " where StateId=? and StateIsActive=1"; 
	
	private final String TABLE_NAME__CITY = "town";
	private final String SELECT_BY_STATE__CITY = " select * from " + TABLE_NAME__CITY + " where State_StateId=? and TownIsActive=1"; 
	private final String SELECT_NAME__CITY = " select TownName from " + TABLE_NAME__CITY + " where State_StateId=?  and TownIsActive=1"; 
	
	//Tabla Ad
	private final String TABLE_NAME_AD = "ad";
	private final String INSERT_SRV__AD = " insert into " + TABLE_NAME_AD + " (AdArticle, AdDescription,  AdPrice, State_StateId , Town_TownId, AdInformation, AdPhone, AdCel, Media_MediaId, AdPublishDate, Adtype_AdtypeId, User_UserId, AdIsVisible, AdStatus,  AdUpdateDate, AdInactiveDate, AdStreet1, AdStreet2) Values( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private final String UPDATE_SRV_AD = "update "+ TABLE_NAME_AD + " set AdArticle=?, AdDescription=?,  AdPrice=?, State_StateId =?, Town_TownId=?, AdInformation=?, AdPhone=?, AdCel=?, Media_MediaId=?, AdUpdateDate=?, User_UserId=? WHERE AdId = ?";
	private final String DELETE_SRV_AD = "delete from " + TABLE_NAME_AD + " where AdId=?";
	//Tabla AdCar
	private final String TABLE_NAME__OPPORTUNITIES_CAR = "adcar";
	private final String INSERT_CAR_OPPORTUNITIES =  " insert into " + TABLE_NAME__OPPORTUNITIES_CAR + " (Ad_AdId,AdcarModel,AdcarDoors,AdcarColor,AdcarKm,AdCarTransmision,Carbrand_CarbrandId, AdCarCilynder) values ( ?, ?, ?, ?, ?, ?, ?, 4 ) ";
	private final String UPDATE_CAR_AD = "UPDATE "+ TABLE_NAME__OPPORTUNITIES_CAR + " SET AdcarModel=?,AdcarDoors=?,AdcarColor=?,AdcarKm=?,AdCarTransmision=?,Carbrand_CarbrandId=?, AdCarCilynder=? WHERE Ad_AdId=?";
	private final String DELETE_CAR_AD = "delete from " + TABLE_NAME__OPPORTUNITIES_CAR + " where Ad_AdId=?";
	//Tabla Adrs
	private final String TABLE_NAME_OPPORTUNITIES_ADRS = "adrs";
	private final String INSERT_ADRS_OPPORTUNITIES = "insert into "+ TABLE_NAME_OPPORTUNITIES_ADRS +" (Ad_AdId, AdrsFloors, AdrsRooms, AdrsOfferType, AdrsPropertyType, AdrsM2, AdrsParkingPlaces, AdrsFurnished, AdrsM2Built) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private final String UPDATE_ADRS_AD = "UPDATE "+ TABLE_NAME_OPPORTUNITIES_ADRS + " SET AdrsFloors=?, AdrsRooms=?, AdrsOfferType=?, AdrsPropertyType=?, AdrsM2=?, AdrsParkingPlaces=?, AdrsFurnished=?, AdrsM2Built = ? WHERE Ad_AdId=?";
	private final String DELETE_ADRS_AD = "delete from " + TABLE_NAME_OPPORTUNITIES_ADRS + " where Ad_AdId=?";

	private final String TABLE_NAME__OPPORTUNITIES = "bbva_opportunities";
	private final String UPDATE_CAR__OPPORTUNITIES = " update " + TABLE_NAME__OPPORTUNITIES + " set brand_id=?, state_id=?, city_id=?, model=?, price=?, today=?, mileage=?, transmission=?, employee_num=?, public_date=?, obs=?, color=?, doors=?, telephone=?, mobilephone=?, file=?, updated_at=? where id=? ";
	private final String UPDATE_FUR__OPPORTUNITIES = " update " + TABLE_NAME__OPPORTUNITIES + " set state_id=?, city_id=?, model=?, price=?, mileage=?, employee_num=?, public_date=?, serve_type=?, property=?, plants=?, rooms=?, amueblado=?, telephone=?, mobilephone=?, file=?, updated_at=? where id=? ";
	private final String UPDATE_SRV__OPPORTUNITIES = " update " + TABLE_NAME__OPPORTUNITIES + " set state_id=?, city_id=?, model=?, price=?, employee_num=?, public_date=?, varios=?, telephone=?, mobilephone=?, file=?, updated_at=? where id=? ";
	
	
	private final String SELECT_BY_ID__OPPORTUNITIES = " select * from " + TABLE_NAME_AD + " where id=? ";
	
	private final String SELECT_AD_ADCAR = "SELECT * FROM ad a, adcar car WHERE a.AdId=car.Ad_AdId AND a.AdId = ?";
	private final String SELECT_AD_ADRS = "SELECT * FROM ad a, adrs rs WHERE a.AdId=rs.Ad_AdId AND a.AdId = ?";
	private final String SELECT_AD_SRV = "SELECT * FROM ad a WHERE a.AdId = ?";
	
	
	
	private final String DELETE__OPPORTUNITIES = " delete from " + TABLE_NAME__OPPORTUNITIES + " where id=? ";
	private final String COUNT_BY_USER__OPPORTUNITIES = " select count(*) from " + TABLE_NAME_AD + " where User_UserId=? and AdIsVisible='1' " ;
	
	private final String SELECT_NAME__CODE = " select a.value, a.value_en, a.value_me from bbva_code a where a.div=? and a.code=? ";
	
	public OpportDAO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Get Code Value.
	 * 
	 * @param div
	 * @param code
	 * @param language
	 * @return name
	 */
	public String getCodeValue(String div, String code, String language){
		String result = "";
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try{
			if (code.length()==0 || code.equals("000"))
				return result;
			
			stmt = conn.prepareStatement(SELECT_NAME__CODE);
			stmt.setString(1, div);
			stmt.setString(2, code);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				if (language.length()>0)
					result = Utils.checkNull(rs.getString("value_"+language));
				else
					result = Utils.checkNull(rs.getString("value"));
			}
		}catch (Exception e){
			log.error("OpportDAO", "getCodeValue", e.toString());
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
	 * Get Brand Data.
	 * 
	 * @param language
	 * @param bAll
	 * @return list
	 */
	public ArrayList<BrandInfo> getBrandList(String language, boolean bAll){
		ArrayList<BrandInfo> result = new ArrayList<BrandInfo>();
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try{
			String sql = SELECT__BRAND;
			if (!bAll)
				sql += " where CarbrandIsActive=1 ";
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				BrandInfo item = new BrandInfo();
				item.setId(rs.getLong("CarbrandId"));
				item.setName(rs.getString("CarbrandName"));

				result.add(item);
			}
		}catch (Exception e){
			log.error("OpportDAO", "getBrandList", e.toString());
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
	 * Get Brand Name.
	 * 
	 * @param id
	 * @return name
	 */
	public String getBrandName(Long id){
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		String result = "";
		try{
			stmt = conn.prepareStatement(SELECT_NAME__BRAND);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				result = Utils.checkNull(rs.getString("CarbrandName"));
			}
		}catch (Exception e){
			log.error("OpportDAO", "getBrandName", e.toString());
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
	 * Get State Data.
	 * 
	 * @param language
	 * @param bAll
	 * @return list
	 */
	public ArrayList<OpportStateInfo> getStateList(String language, boolean bAll){
		ArrayList<OpportStateInfo> result = new ArrayList<OpportStateInfo>();
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try{
			String sql = SELECT__STATE;
			if (!bAll)
				sql += " where StateId<>0 ";
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				OpportStateInfo item = new OpportStateInfo();
				item.setId(rs.getLong("StateId"));
				item.setName(rs.getString("StateName"));

				result.add(item);
			}
		}catch (Exception e){
			log.error("OpportDAO", "getStateList", e.toString());
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
	 * Get State Name.
	 * 
	 * @param id
	 * @return name
	 */
	public String getStateName(Long id){
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		String result = "";
		try{
			stmt = conn.prepareStatement(SELECT_NAME__STATE);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				result = Utils.checkNull(rs.getString("StateName"));
			}
		}catch (Exception e){
			log.error("OpportDAO", "getStateName", e.toString());
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
	 * Get City Data.
	 * 
	 * @param stateID
	 * @param language
	 * @return list
	 */
	public JSONArray getCityListOfJSON(Long stateID, String language){
		JSONArray result = new JSONArray();
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try{
			stmt = conn.prepareStatement(SELECT_BY_STATE__CITY);
			stmt.setLong(1, stateID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				OpportCityInfo item = new OpportCityInfo();
				item.setId(rs.getLong("TownId"));
				item.setStateID(rs.getLong("State_StateId"));
				item.setName(rs.getString("TownName"));

				result.put(item.toJSONObject());
			}
		}catch (Exception e){
			log.error("OpportDAO", "getCityListOfJSON", e.toString());
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
	 * @param stateID
	 * @param language
	 * @param bAll
	 * @return list
	 */
	public ArrayList getCityList(Long stateID, String language, boolean bAll){
		ArrayList result = new ArrayList();
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try{
			String sql = SELECT_BY_STATE__CITY;
			if (!bAll)
				sql += " and id<>0 ";
			stmt = conn.prepareStatement(sql);
			stmt.setLong(1, stateID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				OpportCityInfo item = new OpportCityInfo();
				item.setId(rs.getLong("id"));
				item.setStateID(rs.getLong("state_id"));
				item.setName(rs.getString("name"));

				result.add(item);
			}
		}catch (Exception e){
			log.error("OpportDAO", "getCityList", e.toString());
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
	 * Get City Name.
	 * 
	 * @param id
	 * @return name
	 */
	public String getCityName(Long id){
		String result = "";
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try{
			stmt = conn.prepareStatement(SELECT_NAME__CITY);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				result = Utils.checkNull(rs.getString("TownName"));
			}
		}catch (Exception e){
			log.error("OpportDAO", "getCityName", e.toString());
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
	 * Insert opportunities data for car.
	 * 
	 * @param brand_id
	 * @param state_id
	 * @param city_id
	 * @param model
	 * @param price
	 * @param today
	 * @param mileage
	 * @param transmission
	 * @param employee_num
	 * @param public_date
	 * @param obs
	 * @param color
	 * @param doors
	 * @param telephone
	 * @param mobilephone
	 * @param file
	 * @param user_id
	 * @throws Exception
	 */
	public void insertCar2(Long brand_id, String desc, Long km, String trans, String color, Long doors, Long adid ) throws Exception{
		//String time = Utils.getTodayWithTime();
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = conn.prepareStatement(INSERT_CAR_OPPORTUNITIES);
		stmt.setLong(1, adid);
		stmt.setString(2, desc);
		stmt.setLong(3, doors);
		stmt.setString(4, color);
		stmt.setLong(5, km);
		stmt.setString(6, trans);
		stmt.setLong(7, brand_id);

		stmt.executeUpdate();
		
		stmt.close();
		conn.close();
	}

	/**
	 * Insert opportunities data for Fur.
	 * 
	 * @param state_id
	 * @param city_id
	 * @param model
	 * @param price
	 * @param mileage
	 * @param employee_num
	 * @param public_date
	 * @param property
	 * @param serve_type
	 * @param amueblado
	 * @param plants
	 * @param rooms
	 * @param telephone
	 * @param mobilephone
	 * @param file
	 * @param user_id
	 * @throws Exception
	 */
//	public void insertFur(Long state_id, Long city_id, String model, String price, String mileage, String employee_num, String public_date, String property, String serve_type, String amueblado, String plants, String rooms, 
//			String telephone, String mobilephone, String file, int user_id ) throws Exception{
//		String time = "";
//		
//		PreparedStatement stmt = conn.prepareStatement(INSERT_FUR__OPPORTUNITIES);
//		stmt.setLong(1, state_id);
//		stmt.setLong(2, city_id);
//		stmt.setString(3, model);
//		stmt.setString(4, price);
//		stmt.setString(5, mileage);
//		stmt.setString(6, employee_num);
//		stmt.setString(7, public_date);
//		stmt.setString(8, serve_type);
//		stmt.setString(9, property);
//		stmt.setString(10, plants);
//		stmt.setString(11, rooms);
//		stmt.setString(12, amueblado);
//		stmt.setString(13, telephone);
//		stmt.setString(14, mobilephone);
//		stmt.setString(15, file);
//		stmt.setString(16, time);
//		stmt.setString(17, time);
//		stmt.setInt(18, user_id);
//		
//		stmt.executeUpdate();
//	}
	
	public void insertAdRs(long Ad_AdId, String AdrsFloors, String AdrsRooms, String AdrsOfferType, String AdrsPropertyType, String AdrsM2, String AdrsParkingPlaces, String AdrsFurnished, String AdrsM2Built) throws SQLException{
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = conn.prepareStatement(INSERT_ADRS_OPPORTUNITIES);
		
		stmt.setLong(1, Ad_AdId);
		stmt.setString(2, AdrsFloors);
		stmt.setString(3, AdrsRooms);
		
		if(AdrsOfferType.equals("RENTA")){
			
			stmt.setString(4, "1");
		}else{
			
			stmt.setString(4, "2");
		}
		stmt.setString(5, AdrsPropertyType);
		stmt.setString(6, AdrsM2);
		stmt.setString(7, AdrsParkingPlaces);
		stmt.setString(8, AdrsFurnished);
		stmt.setString(9, AdrsM2Built);
		
		stmt.executeUpdate();
		stmt.close();
		conn.close();
	}

	/**
	 * Insert opportunities data for Several.
	 * 
	 * @param state_id
	 * @param city_id
	 * @param model
	 * @param price
	 * @param employee_num
	 * @param public_date
	 * @param varios
	 * @param telephone
	 * @param mobilephone
	 * @param file
	 * @param user_id
	 * @throws Exception
	 */
	public void insertSrv(String varios, String model, String price, String state, String city, String obs,
			String telephone, String mobilephone, long file, String public_date, int user, String publish_kind) throws Exception{
		
		public_date = Utils.getTodayWithTime();
		String AdUpdateDate = Utils.getTodayWithTime();
		boolean AdIsVisible=true;
		String AdStatus="A";
		String AdInactiveDate="2020-09-08 00:00:00";
		String AdStreet1= "";
		String AdStreet2="";
		int type = 0;
		if(publish_kind.equals("001")){
			type = 1;
		}else if(publish_kind.equals("002")){
			type = 2;
		}else if (publish_kind.equals("003")){
			type = 3;
		}else{
			type = 4;
		}
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = conn.prepareStatement(INSERT_SRV__AD);
		stmt.setString(1, varios);
		stmt.setString(2, model);
		stmt.setString(3, price);
		stmt.setString(4, state);
		stmt.setString(5, city);
		stmt.setString(6, obs);
		stmt.setString(7, telephone);
		stmt.setString(8, mobilephone);
		stmt.setLong(9, file);
		stmt.setString(10, public_date);
		stmt.setInt(11,type);
		stmt.setInt(12, user);
		stmt.setBoolean(13, AdIsVisible);
		stmt.setString(14, AdStatus);
		stmt.setString(15, AdUpdateDate);
		stmt.setString(16, AdInactiveDate);
		stmt.setString(17, AdStreet1 );
		stmt.setString(18, AdStreet2);
		

		
		stmt.executeUpdate();
		stmt.close();
		conn.close();
	}
	
	/**
	 * Update opportunities data for car.
	 * 
	 * @param brand_id
	 * @param state_id
	 * @param city_id
	 * @param model
	 * @param price
	 * @param today
	 * @param mileage
	 * @param transmission
	 * @param employee_num
	 * @param public_date
	 * @param obs
	 * @param color
	 * @param doors
	 * @param telephone
	 * @param mobilephone
	 * @param file
	 * @param oppID
	 * @throws Exception
	 */
	public void updateCar(Long brand_id, Long state_id, Long city_id, String model, String price, String today, String mileage, String transmission, String employee_num, String public_date, 
			String obs, String color, String doors, String telephone, String mobilephone, String file, Long oppID ) throws Exception{
		String time = Utils.getTodayWithTime();
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = conn.prepareStatement(UPDATE_CAR__OPPORTUNITIES);
		stmt.setLong(1, brand_id);
		stmt.setLong(2, state_id);
		stmt.setLong(3, city_id);
		stmt.setString(4, model);
		stmt.setString(5, price);
		stmt.setString(6, today);
		stmt.setString(7, mileage);
		stmt.setString(8, transmission);
		stmt.setString(9, employee_num);
		stmt.setString(10, public_date);
		stmt.setString(11, obs);
		stmt.setString(12, color);
		stmt.setString(13, doors);
		stmt.setString(14, telephone);
		stmt.setString(15, mobilephone);
		stmt.setString(16, file);
		stmt.setString(17, time);
		stmt.setLong(18, oppID);
		
		stmt.executeUpdate();
		stmt.close();
		conn.close();
	}

	/**
	 * Update opportunities data for Fur.
	 * 
	 * @param state_id
	 * @param city_id
	 * @param model
	 * @param price
	 * @param mileage
	 * @param employee_num
	 * @param public_date
	 * @param property
	 * @param serve_type
	 * @param amueblado
	 * @param plants
	 * @param rooms
	 * @param telephone
	 * @param mobilephone
	 * @param file
	 * @param oppID
	 * @throws Exception
	 */
	public void updateFur(Long state_id, Long city_id, String model, String price, String mileage, String employee_num, String public_date, String property, String serve_type, String amueblado, String plants, String rooms, 
			String telephone, String mobilephone, String file, Long oppID) throws Exception{
		String time = Utils.getTodayWithTime();
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = conn.prepareStatement(UPDATE_FUR__OPPORTUNITIES);
		stmt.setLong(1, state_id);
		stmt.setLong(2, city_id);
		stmt.setString(3, model);
		stmt.setString(4, price);
		stmt.setString(5, mileage);
		stmt.setString(6, employee_num);
		stmt.setString(7, public_date);
		stmt.setString(8, serve_type);
		stmt.setString(9, property);
		stmt.setString(10, plants);
		stmt.setString(11, rooms);
		stmt.setString(12, amueblado);
		stmt.setString(13, telephone);
		stmt.setString(14, mobilephone);
		stmt.setString(15, file);
		stmt.setString(16, time);
		stmt.setLong(17, oppID);
		
		stmt.executeUpdate();
		stmt.close();
		conn.close();
	}
	
	/**
	 * Update opportunities data for Several.
	 * 
	 * @param state_id
	 * @param city_id
	 * @param model
	 * @param price
	 * @param employee_num
	 * @param public_date
	 * @param varios
	 * @param telephone
	 * @param mobilephone
	 * @param file
	 * @param oppID
	 * @throws Exception
	 */
	public void updateSrv(Long state_id, Long city_id, String model, String price, String employee_num, String public_date, String varios, 
			String telephone, String mobilephone, String file, Long oppID ) throws Exception{
		String time = Utils.getTodayWithTime();
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = conn.prepareStatement(UPDATE_SRV__OPPORTUNITIES);
		stmt.setLong(1, state_id);
		stmt.setLong(2, city_id);
		stmt.setString(3, model);
		stmt.setString(4, price);
		stmt.setString(5, employee_num);
		stmt.setString(6, public_date);
		stmt.setString(7, varios);
		stmt.setString(8, telephone);
		stmt.setString(9, mobilephone);
		stmt.setString(10, file);
		stmt.setString(11, time);
		stmt.setLong(12, oppID);
		
		stmt.executeUpdate();
		stmt.close();
		conn.close();
	}
	
	/**
	 * Delete opportunities data.
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void delete(Long id) throws Exception{
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = conn.prepareStatement(DELETE__OPPORTUNITIES);
		stmt.setLong(1, id);
		stmt.executeUpdate();
		stmt.close();
		conn.close();
	}

	/**
	 * Get Opportunities Data.
	 * 
	 * @param id
	 * @return opportunities data
	 */
	public OpportunitiesInfo getOpportunities(Long id){
		OpportunitiesInfo result = new OpportunitiesInfo();
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try{
			
			stmt = conn.prepareStatement(SELECT_BY_ID__OPPORTUNITIES);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				result.setId(rs.getLong("id"));
				result.setType(rs.getString("type"));
				result.setBrandID(rs.getLong("brand_id"));
				result.setStateID(rs.getLong("state_id"));
				result.setCityID(rs.getLong("city_id"));
				result.setModel(Utils.checkNull(rs.getString("model")));
				result.setPrice(Utils.checkNull(rs.getString("price")));
				
				result.setToday(Utils.checkNull(rs.getString("today")));
				result.setMileage(Utils.checkNull(rs.getString("mileage")));
				result.setTransmission(Utils.checkNull(rs.getString("transmission")));
				result.setEmployeeNum(Utils.checkNull(rs.getString("employee_num")));
				result.setPublicDate(Utils.checkNull(rs.getString("public_date")));
				result.setObs(Utils.checkNull(rs.getString("obs")));
				result.setColor(Utils.checkNull(rs.getString("color")));
				result.setDoors(Utils.checkNull(rs.getString("doors")));
				result.setTelephone(Utils.checkNull(rs.getString("telephone")));
				result.setMobilephone(Utils.checkNull(rs.getString("mobilephone")));
				result.setFile(rs.getString("file"));
				result.setCreatedAt(rs.getString("created_at"));
				result.setUpdatedAt(rs.getString("updated_at"));
				result.setStatus(rs.getString("status"));
				result.setUserID(rs.getString("user_id"));
				
				result.setServeType(Utils.checkNull(rs.getString("serve_type")));
				result.setProperty(Utils.checkNull(rs.getString("property")));
				result.setPlants(Utils.checkNull(rs.getString("plants")));
				result.setRooms(Utils.checkNull(rs.getString("rooms")));
				result.setAmueblado(Utils.checkNull(rs.getString("amueblado")));
				
				result.setVarios(Utils.checkNull(rs.getString("varios")));
				
				return result;
			}
		}catch (Exception e){
			log.error("OpportDAO", "getOpportunities", e.toString());
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

		return null;
	}
	
	//TW JS
	public OpportunitiesInfo getOportunidades(Long id, String kind){
		Connection conn = DSManager.getConnection();
		OpportunitiesInfo result = new OpportunitiesInfo();
		PreparedStatement stmt = null;
		try {
			MediaDAO mediaDao = new MediaDAO();
			
			if (kind.equals("001")) {
				stmt = conn.prepareStatement(SELECT_AD_ADCAR);
			} else if (kind.equals("002")) {
				stmt = conn.prepareStatement(SELECT_AD_ADRS);
			} else {
				stmt = conn.prepareStatement(SELECT_AD_SRV);
			}
			
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				
				result.setId(rs.getLong("AdId"));
				result.setType(rs.getString("Adtype_AdtypeId"));
				result.setStateID(rs.getLong("State_StateId"));
				result.setCityID(rs.getLong("Town_TownId"));
				result.setModel(Utils.checkNull(rs.getString("AdDescription")));
				result.setPrice(Utils.checkNull(rs.getString("AdPrice")));
				result.setPublicDate(Utils.checkNull(rs.getString("AdPublishDate")));
				result.setObs(Utils.checkNull(rs.getString("AdInformation")));
				result.setTelephone(Utils.checkNull(rs.getString("AdPhone")));
				result.setMobilephone(Utils.checkNull(rs.getString("AdCel")));
				result.setUpdatedAt(rs.getString("AdUpdateDate"));
				result.setStatus(rs.getString("AdStatus"));
				result.setUserID(rs.getString("User_UserId"));
				result.setVarios(Utils.checkNull(rs.getString("AdArticle")));
				result.setCreatedAt("");
				result.setEmployeeNum("");
				result.setFile(mediaDao.getBlob(rs.getString("Media_MediaId")));
				
				if (kind.equals("001")) {
					result.setBrandID(rs.getLong("Carbrand_CarbrandId"));
					result.setToday(Utils.checkNull(rs.getString("AdCarModel")));
					result.setMileage(Utils.checkNull(rs.getString("AdCarKm")));
					
					if(rs.getString("AdCarTransmision").equals("ESTANDAR")){
						result.setTransmission("2");
					}else{
						result.setTransmission("3");
					}
					
					result.setColor(Utils.checkNull(rs.getString("AdCarColor")));
					result.setDoors(Utils.checkNull(rs.getString("AdCarDoors")));
				}else if (kind.equals("002")) {
					if(rs.getString("AdrsOfferType").equals("1")){
						result.setServeType("VENTA");
					}else{
						result.setServeType("RENTA");
					}
					result.setProperty(Utils.checkNull(rs.getString("AdrsPropertyType")));
					result.setPlants(Utils.checkNull(rs.getString("AdrsFloors")));
					result.setRooms(Utils.checkNull(rs.getString("AdrsRooms")));
					result.setAmueblado(Utils.checkNull(rs.getString("AdrsFurnished")));
					result.setMileage(rs.getString("AdrsM2"));
				}
				
				return result;
			}
	
		} catch (Exception e) {
			log.error("OpportDAO", "getOpportunities", e.toString());
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
	 * Get Count Of SQL for DataTable.
	 * 
	 * @param sql
	 * @return count
	 */
	public Long getCount(String sql){
		Long result = (long)0;
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try{
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				result = rs.getLong(1);
			}
		}catch (Exception e){
			log.error("OpportDAO", "getCount", e.toString());
			result = (long)0;
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
	 * Get Content List for DataTable.
	 * 
	 * @param sql
	 * @param language
	 * @return ContentList
	 */
	public JSONArray getContent(String sql, String language){
		JSONArray result = new JSONArray();
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		
		try{
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				JSONObject item = new JSONObject();
				
				Long oppID = rs.getLong("id");
				item.put("id", oppID);

				Long brandID = rs.getLong("brand_id");
				Long stateID = rs.getLong("state_id");
				Long cityID = rs.getLong("city_id");
				
				item.put("brand_id", brandID);
				item.put("state_id", stateID);
				item.put("city_id", cityID);
				
				item.put("name", "");
				item.put("location", "");

				String brand_name="";
				String city_name="";
				String state_name="";
				
				if (brandID!=0){
					brand_name = getBrandName(brandID);
				}
				
				if (stateID!=0){
					state_name = getStateName(stateID);
				}
				
				if (cityID!=0){
					city_name = getCityName(cityID);
				}
				
				item.put("brand_name", brand_name);
				item.put("city_name", city_name);
				item.put("state_name", state_name);
				
				item.put("type", rs.getString("type"));
				
				if (language.length()>0){
					item.put("transmission_name", rs.getString("value_"+language));
					item.put("property_name", rs.getString("property_name_"+language));
					item.put("varios_name", rs.getString("varios_name_"+language));
				} else {
					item.put("transmission_name", rs.getString("value"));
					item.put("property_name", rs.getString("property_name"));
					item.put("varios_name", rs.getString("varios_name"));
				}
	
				item.put("varios", rs.getString("varios"));
				item.put("property", rs.getString("property"));
				item.put("transmission", rs.getString("transmission"));
				item.put("today", rs.getString("today"));
				item.put("public_date", rs.getString("public_date"));
				
				item.put("color", rs.getString("color"));
				item.put("doors", rs.getString("doors"));

				item.put("employee_num", rs.getString("employee_num"));
				item.put("telephone", rs.getString("telephone"));
				item.put("mobilephone", rs.getString("mobilephone"));
				item.put("file", rs.getString("file"));
				
				item.put("model", rs.getString("model"));
				item.put("price", rs.getString("price"));
				item.put("mileage", rs.getString("mileage"));
				item.put("status", rs.getString("status"));

				item.put("serve_type", rs.getString("serve_type"));
				item.put("rooms", rs.getString("rooms"));
				item.put("plants", rs.getString("plants"));
				item.put("amueblado", rs.getString("amueblado"));
				
				result.put(item);
			}
		}catch (Exception e){
			log.error("OpportDAO", "getContent", e.toString());
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

	
	public void updateVisible() throws Exception{
		Connection conn = DSManager.getConnection();
		String today = Utils.getTodayWithTime();
		PreparedStatement stmt = conn.prepareStatement(" update ad set AdStatus='I' where  AdInactiveDate >= '" + today + "' ");
		stmt.executeUpdate();
		stmt.close();
		conn.close();
	}
	
	public boolean countOpportunities(int userID){
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		  try{
		   stmt = conn.prepareStatement(COUNT_BY_USER__OPPORTUNITIES);
		   stmt.setInt(1, userID);
		   ResultSet rs = stmt.executeQuery();
		   
		   Long result=(long)0;
		   while (rs.next()){
		    result = rs.getLong(1);
		   }
		   
		   return result==0 ? true : false;
		  }catch (Exception e){}
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
		  
		  return true;
		 }
	
	public void execute(String sql){
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try{
			stmt = conn.prepareStatement(sql);
			stmt.execute();
		}catch (Exception fdf){}
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
	}
	
	public int getId(){
		int result = 0;
		String sql="SELECT MAX(AdId) AS Ad_AdId FROM ad";
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try{
			
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				result = rs.getInt("Ad_AdId");
			}
		}catch (Exception e){
			log.error("OpportDAO", "getId", e.toString());
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
	
	//Nuevos metodos RJ
	public JSONArray getContentCar(String typeid, int userid, String searchcriteria){
		JSONArray result = new JSONArray();
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		String sql = "";
		String order = " order by a.AdUpdateDate ";
		if (userid==0)
		{
			sql = " select a.*,c.* from ad a,adCar c where a.Adtype_AdtypeId = ?  and a.AdIsVisible = 1 and a.AdId = c.Ad_AdId ";
		}else {
			
			sql = " select a.*,c.* from ad a,adCar c where a.Adtype_AdtypeId = ? and a.User_UserId = ? and a.AdIsVisible = 1 and a.AdId = c.Ad_AdId ";
		}
		if (searchcriteria.length() != 0)
		{
			sql = sql + searchcriteria;
		}
		sql = sql + order;
		try{
			
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, typeid);
			if (userid != 0)
			{
				stmt.setInt(2, userid );
			}
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				JSONObject item = new JSONObject();
				
				Long oppID = rs.getLong("AdId");
				item.put("id", oppID);

				Long brandID = rs.getLong("Carbrand_CarbrandId");
				Long stateID = rs.getLong("State_StateId");
				Long cityID = rs.getLong("Town_TownId");
				
				item.put("brand_id", brandID);
				item.put("state_id", stateID);
				item.put("city_id", cityID);
				
				item.put("name", "");
				item.put("location", "");

				String brand_name="";
				String city_name="";
				String state_name="";
				
				if (brandID!=0){
					brand_name = getBrandName(brandID);
				}
				
				if (stateID!=0){
					state_name = getStateName(stateID);
				}
				
				if (cityID!=0){
					city_name = getCityName(cityID);
				}
				
				item.put("brand_name", brand_name);
				item.put("city_name", city_name);
				item.put("state_name", state_name);
				
				item.put("type", rs.getString("Adtype_AdtypeId"));
				
				
				item.put("transmission_name", getMenuValue("opp3",Long.parseLong(rs.getString("AdcarTransmision"))));
				
				item.put("property_name", "");
				item.put("varios_name", "");

//					item.put("transmission_name", rs.getString("Menuvalue"));
//					item.put("property_name", rs.getString("property_name"));
//					item.put("varios_name", rs.getString("varios_name"));
	
				item.put("varios", rs.getString("AdArticle"));
//				item.put("property", rs.getString("AdrsPropertyType"));
//				item.put("transmission", rs.getString("AdCarTransmision"));
				item.put("today", rs.getString("AdcarModel"));
				item.put("public_date", rs.getString("AdPublishDate"));
				
				item.put("color", rs.getString("AdcarColor"));
				item.put("doors", rs.getString("AdcarDoors"));

		//		item.put("employee_num", rs.getString("employee_num"));
				item.put("telephone", rs.getString("AdPhone"));
				item.put("mobilephone", rs.getString("AdCel"));
				item.put("file", getMediaById(rs.getLong("Media_MediaId")));
				
				item.put("model", rs.getString("AdDescription"));
				item.put("price", rs.getString("AdPrice"));
				item.put("mileage", rs.getString("AdcarKm"));
				item.put("status", rs.getString("AdStatus"));

				item.put("serve_type", "001");
				item.put("rooms", "0");
				item.put("plants", "0");
				item.put("amueblado", "0");
				
				result.put(item);
			}
		}catch (Exception e){
			log.error("OpportDAO", "getContent", e.toString());
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
	
	
	
	
	public JSONArray getContentRs(String typeid, int userid, String searchcriteria){
		JSONArray result = new JSONArray();
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		String sql = "";
		String order = " order by a.AdUpdateDate ";
		if (userid==0)
		{
			sql = " select a.*,c.* from ad a,adRs c where a.Adtype_AdtypeId = ? and a.AdIsVisible = 1 and a.AdId = c.Ad_AdId ";
		}else {
			
			sql = " select a.*,c.* from ad a,adRs c where a.Adtype_AdtypeId = ? and a.User_UserId = ? and a.AdIsVisible = 1 and a.AdId = c.Ad_AdId ";
		}
		if (searchcriteria.length() != 0)
		{
			sql = sql + searchcriteria;
		}
		sql = sql + order;
		
		try{

			stmt = conn.prepareStatement(sql);

			stmt.setString(1, typeid);
			if (userid > 0){
				stmt.setInt(2, userid );	
			}
			
			
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				JSONObject item = new JSONObject();
				
				Long oppID = rs.getLong("AdId");
				item.put("id", oppID);

				Long brandID =  (long) 0;
				Long stateID = rs.getLong("State_StateId");
				Long cityID = rs.getLong("Town_TownId");
				
				item.put("brand_id", brandID);
				item.put("state_id", stateID);
				item.put("city_id", cityID);
				
				item.put("name", "");
				item.put("location", "");

				String brand_name="";
				String city_name="";
				String state_name="";
				
				if (brandID!=0){
					brand_name = getBrandName(brandID);
				}
				
				if (stateID!=0){
					state_name = getStateName(stateID);
				}
				
				if (cityID!=0){
					city_name = getCityName(cityID);
				}
				
				item.put("brand_name", brand_name);
				item.put("city_name", city_name);
				item.put("state_name", state_name);
				
				item.put("type", rs.getString("Adtype_AdtypeId"));
				
				
				item.put("transmission_name", "0");
				
				item.put("property_name", "");
				item.put("varios_name", "");

//					item.put("transmission_name", rs.getString("Menuvalue"));
//					item.put("property_name", rs.getString("property_name"));
//					item.put("varios_name", rs.getString("varios_name"));
	
				item.put("varios", rs.getString("AdArticle"));
//				item.put("property", rs.getString("AdrsPropertyType"));
//				item.put("transmission", rs.getString("AdCarTransmision"));
				item.put("today", "0");
				item.put("public_date", rs.getString("AdPublishDate"));
				
				item.put("color", "");
				item.put("doors", "");

		//		item.put("employee_num", rs.getString("employee_num"));
				item.put("telephone", rs.getString("AdPhone"));
				item.put("mobilephone", rs.getString("AdCel"));
				item.put("file", getMediaById(rs.getLong("Media_MediaId")));
				
				item.put("model", rs.getString("AdDescription"));
				item.put("price", rs.getString("AdPrice"));
				item.put("mileage", "");
				item.put("status", rs.getString("AdStatus"));

				item.put("serve_type", "002");
				item.put("rooms", rs.getString("AdrsRooms"));
				item.put("plants",rs.getString("AdrsFloors"));
				if (rs.getString("AdrsFurnished").equals("S")) 
				{			
					item.put("amueblado", "S");
				}else {
					item.put("amueblado", "S");
				}
				
				result.put(item);
			}
		}catch (Exception e){
			log.error("OpportDAO", "getContent", e.toString());
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

	
	
	public JSONArray getContentSv(String typeid, int userid, String searchcriteria){
		JSONArray result = new JSONArray();
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		String sql = "";
		String order = " order by a.AdUpdateDate ";
		if (userid==0)
		{
			sql = " select * from ad a where a.Adtype_AdtypeId = ? and a.AdIsVisible = 1 ";
		}else {
			
			sql = " select * from ad a where a.Adtype_AdtypeId = ? and a.User_UserId = ? and a.AdIsVisible = 1 ";
		}
		if (searchcriteria.length() != 0)
		{
			sql = sql + searchcriteria;
		}
		sql = sql + order;
		
		try{
			
			stmt = conn.prepareStatement(sql);

			stmt.setString(1, typeid);
			if (userid > 0)
			{
				stmt.setInt(2, userid );
			}
			
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				JSONObject item = new JSONObject();
				
				Long oppID = rs.getLong("AdId");
				item.put("id", oppID);

				Long brandID =  (long) 0;
				Long stateID = rs.getLong("State_StateId");
				Long cityID = rs.getLong("Town_TownId");
				
				item.put("brand_id", brandID);
				item.put("state_id", stateID);
				item.put("city_id", cityID);
				
				item.put("name", "");
				item.put("location", "");

				String brand_name="";
				String city_name="";
				String state_name="";
				
				if (brandID!=0){
					brand_name = getBrandName(brandID);
				}
				
				if (stateID!=0){
					state_name = getStateName(stateID);
				}
				
				if (cityID!=0){
					city_name = getCityName(cityID);
				}
				
				item.put("brand_name", brand_name);
				item.put("city_name", city_name);
				item.put("state_name", state_name);
				
				item.put("type", rs.getString("Adtype_AdtypeId"));
				
				
				item.put("transmission_name", "0");
				
				item.put("property_name", "");
				item.put("varios_name", "");

//					item.put("transmission_name", rs.getString("Menuvalue"));
//					item.put("property_name", rs.getString("property_name"));
//					item.put("varios_name", rs.getString("varios_name"));
	
				item.put("varios", rs.getString("AdArticle"));
//				item.put("property", rs.getString("AdrsPropertyType"));
//				item.put("transmission", rs.getString("AdCarTransmision"));
				item.put("today", "0");
				item.put("public_date", rs.getString("AdPublishDate"));
				
				item.put("color", "");
				item.put("doors", "");

		//		item.put("employee_num", rs.getString("employee_num"));
				item.put("telephone", rs.getString("AdPhone"));
				item.put("mobilephone", rs.getString("AdCel"));
				item.put("file", getMediaById(rs.getLong("Media_MediaId")));
				
				item.put("model", rs.getString("AdDescription"));
				item.put("price", rs.getString("AdPrice"));
				item.put("mileage", "");
				item.put("status", rs.getString("AdStatus"));

				item.put("serve_type", "003");
				item.put("rooms", "0");
				item.put("plants","0");
/*				if (rs.getString("AdrsFurnished").equals("S")) 
				{			
					item.put("amueblado", "S");
				}else {
					item.put("amueblado", "N");
				}*/
				item.put("amueblado", "N");
				result.put(item);
			}
		}catch (Exception e){
			log.error("OpportDAO", "getContentSv", e.toString());
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


// mETODO nUEVO
	
	public String getMenuValue(String divl, Long code){
		String valor = "";		
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try{
			String sql = " select MenuValue from menu where MenuDivl = ? and MenuCode = ? ";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, divl);
			stmt.setLong(2, code );
			ResultSet rs = stmt.executeQuery();
			if (rs.next())
			{
				valor = rs.getString("MenuValue");
			}
			
						
		}catch (Exception e){
			log.error("OpportDAO", "getContent", e.toString());
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
		
		return valor;
	}

// Metodo Nuevo
	
	public String getMediaById(Long mediaid){
		String contenido = "";		
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try{
			String sql = " select MediaContent from media where MediaId = ? ";
			stmt = conn.prepareStatement(sql);
			stmt.setLong(1, mediaid);
			ResultSet rs = stmt.executeQuery();
			if (rs.next())
			{
				contenido = rs.getString("MediaContent");
			}
			
						
		}catch (Exception e){
			log.error("OpportDAO", "getMediaById", e.toString());
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
		
		return contenido;
	}

	public void updateSrv(String varios, String model, String price,
			String state, String city, String obs, String telephone,
			String mobilephone, long mediaContent, String public_date,
			int userId, String publish_id) throws SQLException {
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;

		public_date = Utils.getTodayWithTime();
		
		stmt = conn.prepareStatement(UPDATE_SRV_AD);

		stmt.setString(1, varios);
		stmt.setString(2, model);
		stmt.setString(3, price);
		stmt.setString(4, state);
		stmt.setString(5, city);
		stmt.setString(6, obs);
		stmt.setString(7, telephone);
		stmt.setString(8, mobilephone);
		stmt.setLong(9, mediaContent);
		stmt.setString(10, public_date);
		stmt.setInt(11, userId);
		stmt.setString(12, publish_id);
		
		stmt.executeUpdate();
		
		stmt.close();
		conn.close();
	}
	
	public int idMedia(String idPublish){
		int id = 0;
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try {
			String sql = "SELECT Media_MediaId FROM ad WHERE AdId = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, idPublish);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {

				id = rs.getInt("Media_MediaId");

				return id;
			}
		} catch (Exception e) {
			log.error("MediaDAO", "getFolderId", e.toString());
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
		return 0;
	}

	public void updateAdrRs(String publish_id, String plants, String rooms,
			String serve_type, String property, String mileage, String string,
			String amueblado, String model) throws SQLException {
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = conn.prepareStatement(UPDATE_ADRS_AD);

		stmt.setString(1, plants);
		stmt.setString(2, rooms);
		stmt.setString(3, serve_type);
		stmt.setString(4, property);
		stmt.setString(5, mileage);
		stmt.setString(6, string);
		stmt.setString(7, amueblado);
		stmt.setString(8, model);
		stmt.setString(9, publish_id);
		
		stmt.executeUpdate();
		
		stmt.close();
		conn.close();
	}

	public void updateAdCar(long parseLong, String model, long parseLong2,
			String transmission, String color, long parseLong3,
			String publish_id) throws SQLException{
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = conn.prepareStatement(UPDATE_CAR_AD);

		stmt.setLong(1, parseLong);
		stmt.setString(2, model);
		stmt.setLong(3, parseLong2);
		stmt.setString(4, transmission);
		stmt.setString(5, color);
		stmt.setLong(6, parseLong3);
		stmt.setString(7, "4");
		stmt.setString(8, publish_id);
		
		stmt.executeUpdate();
		
		stmt.close();
		conn.close();
	}

	public void deleteAd(String publish_id) throws Exception{
		Connection conn = DSManager.getConnection();
		
		PreparedStatement stmt = conn.prepareStatement(DELETE_SRV_AD);
		stmt.setString(1, publish_id);
		stmt.executeUpdate();
		
		stmt.close();
		conn.close();
	}

	public void deleteAdrs(String publish_id) throws Exception{
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = conn.prepareStatement(DELETE_ADRS_AD);
		stmt.setString(1, publish_id);
		stmt.executeUpdate();	
		
		stmt.close();
		conn.close();
	}

	public void deleteAdCar(String publish_id) throws Exception{
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = conn.prepareStatement(DELETE_CAR_AD);
		stmt.setString(1, publish_id);
		stmt.executeUpdate();
		
		stmt.close();
		conn.close();
	}
}