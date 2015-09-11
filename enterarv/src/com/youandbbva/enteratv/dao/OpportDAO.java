package com.youandbbva.enteratv.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import com.youandbbva.enteratv.DAO;
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

	private final String TABLE_NAME__BRAND = "bbva_brand";
	private final String SELECT__BRAND = " select * from " + TABLE_NAME__BRAND + "  "; 
	private final String SELECT_NAME__BRAND = " select name from " + TABLE_NAME__BRAND + " where id=? ";
	
	private final String TABLE_NAME__STATE = "bbva_state";
	private final String SELECT__STATE = " select * from " + TABLE_NAME__STATE + "  "; 
	private final String SELECT_NAME__STATE = " select name from " + TABLE_NAME__STATE + " where id=? "; 
	
	private final String TABLE_NAME__CITY = "bbva_state_city";
	private final String SELECT_BY_STATE__CITY = " select * from " + TABLE_NAME__CITY + " where state_id=?  "; 
	private final String SELECT_NAME__CITY = " select name from " + TABLE_NAME__CITY + " where id=? "; 

	private final String TABLE_NAME__OPPORTUNITIES = "bbva_opportunities";
	private final String INSERT_CAR__OPPORTUNITIES = " insert into " + TABLE_NAME__OPPORTUNITIES + " ( type, brand_id, state_id, city_id, model, price, today, mileage, transmission, employee_num, public_date, obs, color, doors, telephone, mobilephone, file, created_at, updated_at, user_id ) values ( '001', ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ";
	private final String INSERT_FUR__OPPORTUNITIES = " insert into " + TABLE_NAME__OPPORTUNITIES + " ( type, state_id, city_id, model, price, mileage, employee_num, public_date, serve_type, property, plants, rooms, amueblado, telephone, mobilephone, file, created_at, updated_at, user_id ) values ( '002', ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ";
	private final String INSERT_SRV__OPPORTUNITIES = " insert into " + TABLE_NAME__OPPORTUNITIES + " ( type, state_id, city_id, model, price, employee_num, public_date, varios, telephone, mobilephone, file, created_at, updated_at, user_id ) values ( '003', ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ";
	private final String UPDATE_CAR__OPPORTUNITIES = " update " + TABLE_NAME__OPPORTUNITIES + " set brand_id=?, state_id=?, city_id=?, model=?, price=?, today=?, mileage=?, transmission=?, employee_num=?, public_date=?, obs=?, color=?, doors=?, telephone=?, mobilephone=?, file=?, updated_at=? where id=? ";
	private final String UPDATE_FUR__OPPORTUNITIES = " update " + TABLE_NAME__OPPORTUNITIES + " set state_id=?, city_id=?, model=?, price=?, mileage=?, employee_num=?, public_date=?, serve_type=?, property=?, plants=?, rooms=?, amueblado=?, telephone=?, mobilephone=?, file=?, updated_at=? where id=? ";
	private final String UPDATE_SRV__OPPORTUNITIES = " update " + TABLE_NAME__OPPORTUNITIES + " set state_id=?, city_id=?, model=?, price=?, employee_num=?, public_date=?, varios=?, telephone=?, mobilephone=?, file=?, updated_at=? where id=? ";
	private final String SELECT_BY_ID__OPPORTUNITIES = " select * from " + TABLE_NAME__OPPORTUNITIES + " where id=? ";
	private final String DELETE__OPPORTUNITIES = " delete from " + TABLE_NAME__OPPORTUNITIES + " where id=? ";
	private final String COUNT_BY_USER__OPPORTUNITIES = " select count(*) from " + TABLE_NAME__OPPORTUNITIES + " where user_id=? and visible='1' " ;
	
	private final String SELECT_NAME__CODE = " select a.value, a.value_en, a.value_me from bbva_code a where a.div=? and a.code=? ";
	
	public OpportDAO() {
		// TODO Auto-generated constructor stub
	}
	
	public OpportDAO(Connection conn) {
		// TODO Auto-generated constructor stub
		super(conn);
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
		try{
			if (code.length()==0 || code.equals("000"))
				return result;
			
			PreparedStatement stmt = conn.prepareStatement(SELECT_NAME__CODE);
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

		return result;
	}

	/**
	 * Get Brand Data.
	 * 
	 * @param language
	 * @param bAll
	 * @return list
	 */
	public ArrayList getBrandList(String language, boolean bAll){
		ArrayList result = new ArrayList();
		try{
			String sql = SELECT__BRAND;
			if (!bAll)
				sql += " where status=0 ";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				BrandInfo item = new BrandInfo();
				item.setId(rs.getLong("id"));
				item.setName(rs.getString("name"));

				result.add(item);
			}
		}catch (Exception e){
			log.error("OpportDAO", "getBrandList", e.toString());
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
		String result = "";
		try{
			PreparedStatement stmt = conn.prepareStatement(SELECT_NAME__BRAND);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				result = Utils.checkNull(rs.getString("name"));
			}
		}catch (Exception e){
			log.error("OpportDAO", "getBrandName", e.toString());
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
	public ArrayList getStateList(String language, boolean bAll){
		ArrayList result = new ArrayList();
		try{
			String sql = SELECT__STATE;
			if (!bAll)
				sql += " where id<>0 ";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				OpportStateInfo item = new OpportStateInfo();
				item.setId(rs.getLong("id"));
				item.setName(rs.getString("name"));

				result.add(item);
			}
		}catch (Exception e){
			log.error("OpportDAO", "getStateList", e.toString());
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
		String result = "";
		try{
			PreparedStatement stmt = conn.prepareStatement(SELECT_NAME__STATE);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				result = Utils.checkNull(rs.getString("name"));
			}
		}catch (Exception e){
			log.error("OpportDAO", "getStateName", e.toString());
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
		try{
			PreparedStatement stmt = conn.prepareStatement(SELECT_BY_STATE__CITY);
			stmt.setLong(1, stateID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				OpportCityInfo item = new OpportCityInfo();
				item.setId(rs.getLong("id"));
				item.setStateID(rs.getLong("state_id"));
				item.setName(rs.getString("name"));

				result.put(item.toJSONObject());
			}
		}catch (Exception e){
			log.error("OpportDAO", "getCityListOfJSON", e.toString());
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
		try{
			String sql = SELECT_BY_STATE__CITY;
			if (!bAll)
				sql += " and id<>0 ";
			PreparedStatement stmt = conn.prepareStatement(sql);
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
		try{
			PreparedStatement stmt = conn.prepareStatement(SELECT_NAME__CITY);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				result = Utils.checkNull(rs.getString("name"));
			}
		}catch (Exception e){
			log.error("OpportDAO", "getCityName", e.toString());
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
	public void insertCar(Long brand_id, Long state_id, Long city_id, String model, String price, String today, String mileage, String transmission, String employee_num, String public_date, 
			String obs, String color, String doors, String telephone, String mobilephone, String file, int user_id ) throws Exception{
		String time = "";
		
		PreparedStatement stmt = conn.prepareStatement(INSERT_CAR__OPPORTUNITIES);
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
		stmt.setString(18, time);
		stmt.setInt(19, user_id);
		
		stmt.executeUpdate();
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
	public void insertFur(Long state_id, Long city_id, String model, String price, String mileage, String employee_num, String public_date, String property, String serve_type, String amueblado, String plants, String rooms, 
			String telephone, String mobilephone, String file, int user_id ) throws Exception{
		String time = "";
		
		PreparedStatement stmt = conn.prepareStatement(INSERT_FUR__OPPORTUNITIES);
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
		stmt.setString(17, time);
		stmt.setInt(18, user_id);
		
		stmt.executeUpdate();
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
	public void insertSrv(Long state_id, Long city_id, String model, String price, String employee_num, String public_date, String varios, 
			String telephone, String mobilephone, String file, int user_id ) throws Exception{
		String time = Utils.getTodayWithTime();
		
		PreparedStatement stmt = conn.prepareStatement(INSERT_SRV__OPPORTUNITIES);
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
		stmt.setString(11, "");
		stmt.setString(12, "");
		stmt.setInt(13, user_id);
		
		stmt.executeUpdate();
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
		//String time = Utils.getTodayWithTime();
		String time = "";
		
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
		
		//String time = Utils.getTodayWithTime();
		String time ="";
		
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
		//String time = Utils.getTodayWithTime();
		String time ="";
		
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
	}
	
	/**
	 * Delete opportunities data.
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void delete(Long id) throws Exception{
		PreparedStatement stmt = conn.prepareStatement(DELETE__OPPORTUNITIES);
		stmt.setLong(1, id);
		stmt.executeUpdate();
	}

	/**
	 * Get Opportunities Data.
	 * 
	 * @param id
	 * @return opportunities data
	 */
	public OpportunitiesInfo getOpportunities(Long id){
		OpportunitiesInfo result = new OpportunitiesInfo();
		try{
			PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID__OPPORTUNITIES);
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
		
		try{
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				result = rs.getLong(1);
			}
		}catch (Exception e){
			log.error("OpportDAO", "getCount", e.toString());
			result = (long)0;
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
			PreparedStatement stmt = conn.prepareStatement(sql);
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
		}
		
		return result;
	}

	
	public void updateVisible() throws Exception{
		String today = Utils.beforeDay(Utils.getToday(), 90)+"240000";
		PreparedStatement stmt = conn.prepareStatement(" update bbva_opportunities set visible='0' where  visible='0'");
		stmt.executeUpdate();
	}
	
	public boolean countOpportunities(int userID){
		  try{
		   PreparedStatement stmt = conn.prepareStatement(COUNT_BY_USER__OPPORTUNITIES);
		   stmt.setInt(1, userID);
		   ResultSet rs = stmt.executeQuery();
		   
		   Long result=(long)0;
		   while (rs.next()){
		    result = rs.getLong(1);
		   }
		   
		   return result==0 ? true : false;
		  }catch (Exception e){}
		  
		  return true;
		 }
	
	public void execute(String sql){
		try{
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.execute();
		}catch (Exception fdf){}
	}
	
	
}