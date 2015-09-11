package com.youandbbva.enteratv.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import com.youandbbva.enteratv.DAO;
import com.youandbbva.enteratv.Utils;
import com.youandbbva.enteratv.domain.ChannelCityInfo;
import com.youandbbva.enteratv.domain.ChannelDivisionInfo;
import com.youandbbva.enteratv.domain.ChannelInfo;
import com.youandbbva.enteratv.domain.ChannelJobgradeInfo;
import com.youandbbva.enteratv.domain.ChannelPayownerInfo;
import com.youandbbva.enteratv.domain.ChannelPromoteInfo;
import com.youandbbva.enteratv.domain.FamilyInfo;

/**
 * 
 * Handle all query for category.
 * 
 * @author CJH
 *
 */

@Repository("CategoryDAO")
public class CategoryDAO extends DAO{

	private static final String COLUMN_NAME__FAMILY="FamilyId,Familyname,FamilyPosition,FamilyIsVisible";
	private static final String Table_Family = "family";
	private final String COUNT_BY_NAME__FAMILY = " select count(*) from " + Table_Family + " where Familyname=? ";
	private final String COUNT_BY_NAME2__FAMILY = " select count(*) from " + Table_Family + " where Familyname =? and FamilyId = ? ";
	private final String UPDATE_BY_ID__FAMILY = " update " + Table_Family + " set Familyname = ?, FamilyIsVisible=? where FamilyId=? ";
	private final String MAX_POS__FAMILY = " select max(FamilyPosition) from " + Table_Family;
	private final String UPDATE_POSITION__FAMILY = " update " + Table_Family + " set FamilyPosition=? where FamilyId=? ";
	private final String DELETE_BY_ID__FAMILY = " delete from family where FamilyId=? ";
	private final String SELECT__FAMILY = " select " + COLUMN_NAME__FAMILY + " from family order by FamilyPosition ";
	private final String SELECT_BY_ID__FAMILY = " select " + COLUMN_NAME__FAMILY + " from family where FamilyId=? ";
	private final String INSERT__FAMILY = " insert into " + Table_Family + "(" + "Familyname,FamilyPosition,FamilyIsVisible" + ") values ( ?, ?, ? ) ";
	

	private final String TABLE_NAME__CHANNEL="channel";
	private final String COLUMN_NAME__CHANNEL="ChannelId,Family_FamilyId,ChannelName,ChannelPosition,ChannelFather,ChannelEmail,ChannelPassword,ChannelSecurityLevel,ChannelIsVisible";
	private final String COUNT_BY_NAME__CHANNEL= " select count(*) from " + TABLE_NAME__CHANNEL + " f where f.ChannelName=? ";
	private final String SELECT__CHANNEL = " select " + COLUMN_NAME__CHANNEL + " from " + TABLE_NAME__CHANNEL + " order by ChannelPosition ";
	private final String SELECT_BY_ID__CHANNEL = " select " + COLUMN_NAME__CHANNEL + " from " + TABLE_NAME__CHANNEL + " where ChannelId=? ";
	private final String SELECT_BY_NAME__CHANNEL = " select " + COLUMN_NAME__CHANNEL + " from " + TABLE_NAME__CHANNEL + " where ChannelName=? ";
	private final String SELECT_BY_FAMILY_PARENT_ID__CHANNEL = " select " + COLUMN_NAME__CHANNEL + " from " + TABLE_NAME__CHANNEL + " where Family_FamilyId=? and ChannelFather=? order by ChannelPosition";
	private final String SELECT_BY_PARENT_ID__CHANNEL = " select " + COLUMN_NAME__CHANNEL + " from " + TABLE_NAME__CHANNEL + " where ChannelFather=? order by ChannelPosition ";
	private final String INSERT_EXIST__CHANNEL = " insert into " + TABLE_NAME__CHANNEL + " (" + "Family_FamilyId,ChannelName,ChannelFather,ChannelEmail,ChannelPassword,ChannelIsVisible,ChannelSecurityLevel" + ") values ( ?, ?, ?, ?, ?, ?, ? ) ";
	private final String INSERT__CHANNEL = " insert into " + TABLE_NAME__CHANNEL + " (" + "Family_FamilyId,ChannelName,ChannelFather,ChannelEmail,ChannelPassword,ChannelIsVisible,ChannelSecurityLevel" + ") values ( ?, ?, ?, ?, ?, ?, ? ) ";
	private final String UPDATE_BY_ID__CHANNEL = " update " + TABLE_NAME__CHANNEL + " set name=? where ChannelId=? ";
	private final String UPDATE_POSITION__CHANNEL = " update " + TABLE_NAME__CHANNEL + " set ChannelPosition=?  where ChannelId=? ";
	private final String UPDATE_PARENT__CHANNEL = " update " + TABLE_NAME__CHANNEL + " set ChannelFather=?  where ChannelId=? ";
	private final String DELETE_BY_ID__CHANNEL = " delete from " + TABLE_NAME__CHANNEL + " where ChannelId=? ";
	
	
	// Falta cambiar
	
	
	
	
	private final String TABLE_NAME__CHANNEL_DIVISION="bbva_channel_division";
	private final String COLUMN_NAME__CHANNEL_DIVISION="id,type,channel_id,division_id";
	private final String SELECT_BY_CHANNEL_ID__CHANNEL_DIVISION = " select " + COLUMN_NAME__CHANNEL_DIVISION + " from " + TABLE_NAME__CHANNEL_DIVISION + " where type=? and channel_id=? ";
	private final String INSERT__CHANNEL_DIVISION = " insert into " + TABLE_NAME__CHANNEL_DIVISION + " (" + "type,channel_id,division_id" + ") values ( ?, ?, ? ) ";
	private final String DELETE_BY_CHANNEL_ID__CHANNEL_DIVISION = " delete from " + TABLE_NAME__CHANNEL_DIVISION + " where channel_id=? ";
	
	
	
	
	/*
	
	
	private final String TABLE_NAME__CHANNEL_CITY="city";
	private final String COLUMN_NAME__CHANNEL_CITY="CityId,State_StateId,CityName,CityIsActive";
	private final String SELECT_BY_CHANNEL_ID__CHANNEL_CITY = " select " + COLUMN_NAME__CHANNEL_CITY + " from " + TABLE_NAME__CHANNEL_CITY + " where type=? and channel_id=? ";
	private final String INSERT__CHANNEL_CITY = " insert into " + TABLE_NAME__CHANNEL_CITY + " (" + "type,channel_id,city_id" + ") values ( ?, ?, ? ) ";
	private final String DELETE_BY_CHANNEL_ID__CHANNEL_CITY = " delete from " + TABLE_NAME__CHANNEL_CITY + " where channel_id=? ";
	
	
	
	
	
	
	
	
	
	private final String TABLE_NAME__CHANNEL_PROMOTE="bbva_channel_promote";
	private final String COLUMN_NAME__CHANNEL_PROMOTE="id,channel_id,promote_id";
	private final String SELECT_BY_CHANNEL_ID__CHANNEL_PROMOTE = " select " + COLUMN_NAME__CHANNEL_PROMOTE + " from " + TABLE_NAME__CHANNEL_PROMOTE + " where channel_id=? ";
	private final String INSERT__CHANNEL_PROMOTE = " insert into " + TABLE_NAME__CHANNEL_PROMOTE + " (" + "channel_id,promote_id" + ") values ( ?, ? ) ";
	private final String DELETE_BY_CHANNEL_ID__CHANNEL_PROMOTE = " delete from " + TABLE_NAME__CHANNEL_PROMOTE + " where channel_id=? ";
	
	
	
	
	
	
	
	
	
	
	
	
	private final String TABLE_NAME__CHANNEL_JOBGRADE="bbva_channel_jobgrade";
	private final String COLUMN_NAME__CHANNEL_JOBGRADE="id,channel_id,jobgrade_id";
	private final String SELECT_BY_CHANNEL_ID__CHANNEL_JOBGRADE = " select " + COLUMN_NAME__CHANNEL_JOBGRADE + " from " + TABLE_NAME__CHANNEL_JOBGRADE + " where channel_id=? ";
	private final String INSERT__CHANNEL_JOBGRADE = " insert into " + TABLE_NAME__CHANNEL_JOBGRADE + " (" + "channel_id,jobgrade_id" + ") values ( ?, ? ) ";
	private final String DELETE_BY_CHANNEL_ID__CHANNEL_JOBGRADE = " delete from " + TABLE_NAME__CHANNEL_JOBGRADE + " where channel_id=? ";
	
	
	
	
	
	
	
	
	
	
	
	
	
	private final String TABLE_NAME__CHANNEL_PAYOWNER="bbva_channel_payowner";
	private final String COLUMN_NAME__CHANNEL_PAYOWNER="id,channel_id,payowner_id";
	private final String SELECT_BY_CHANNEL_ID__CHANNEL_PAYOWNER= " select " + COLUMN_NAME__CHANNEL_PAYOWNER + " from " + TABLE_NAME__CHANNEL_PAYOWNER + " where channel_id=? ";
	private final String INSERT__CHANNEL_PAYOWNER = " insert into " + TABLE_NAME__CHANNEL_PAYOWNER + " (" + "channel_id,payowner_id" + ") values ( ?, ? ) ";
	private final String DELETE_BY_CHANNEL_ID__CHANNEL_PAYOWNER = " delete from " + TABLE_NAME__CHANNEL_PAYOWNER + " where channel_id=? ";
*/
	
	public CategoryDAO() {
		// TODO Auto-generated constructor stub
	}
	
	public CategoryDAO(Connection conn) {
		// TODO Auto-generated constructor stub
		super(conn);
	}
	
	/**
	 * Check whether if same family exist or not.
	 * 
	 * @param name
	 * @return boolean
	 */
	public boolean isValidFamily(String name){
		try{
			long result = 0;
			PreparedStatement stmt = conn.prepareStatement(COUNT_BY_NAME__FAMILY);
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				result = rs.getLong(1);
			}
			
			return result==0 ? true : false;
		}catch (Exception e){
			log.error("CategoryDAO", "isValidFamily", e.toString());
		}

		return false;
	}
	
	/**
	 * Check whether if same family exist or not.
	 * 
	 * @param name
	 * @param familyID
	 * @return boolean
	 */
	public boolean isValidFamily(String name, Long familyID){
		try{
			long result = 0;
			PreparedStatement stmt = conn.prepareStatement(COUNT_BY_NAME2__FAMILY);
			stmt.setString(1, name);
			stmt.setLong(2, familyID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				result = rs.getLong(1);
			}
			
			return result==0 ? true : false;
		}catch (Exception e){
			log.error("CategoryDAO", "isValidFamily", e.toString());
		}

		return false;
	}

	/**
	 * Get max position. last order.
	 * 
	 * @return position
	 */
	public Long getMaxPosition(){
		try{
			long result = 0;
			PreparedStatement stmt = conn.prepareStatement(MAX_POS__FAMILY);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				result = rs.getLong(1);
			}
			
			return result;
		}catch (Exception e){
			log.error("CategoryDAO", "getMaxPosition", e.toString());
		}

		return (long)0;
	}
	
	/**
	 * Get All Family Data.
	 * 
	 * @return list
	 */
	public JSONArray getFamilyListOfJSON(){
		JSONArray result = new JSONArray();
		try{
			
			PreparedStatement stmt = conn.prepareStatement(SELECT__FAMILY);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				FamilyInfo item = new FamilyInfo();
				item.setId(rs.getLong(1));
				item.setName(rs.getString(2));
				item.setPos(rs.getLong(3));
				item.setVisible(rs.getString(4));
				
				result.put(item.toJSONObject());
			}
		}catch (Exception e){
			log.error("CategoryDAO", "getFamilyListOfJSON", e.toString());
		}

		return result;
	}

	public JSONObject getFamilyOfJSON(Long id){
		JSONObject result = new JSONObject();
		try{
			
			PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID__FAMILY);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				FamilyInfo item = new FamilyInfo();
				item.setId(rs.getLong(1));
				item.setName(rs.getString(2));
				item.setPos(rs.getLong(3));
				item.setVisible(rs.getString(4));
				
				return item.toJSONObject();
			}
		}catch (Exception e){
			log.error("CategoryDAO", "getFamilyOfJSON", e.toString());
		}

		return result;
	}
	
	/**
	 * Get All Family Data.
	 * 
	 * @return list
	 */
	public ArrayList getFamilyList(){
		ArrayList result = new ArrayList();
		try{
			PreparedStatement stmt = conn.prepareStatement(SELECT__FAMILY);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				FamilyInfo item = new FamilyInfo();
				item.setId(rs.getLong(1));
				item.setName(rs.getString(2));
				item.setPos(rs.getLong(3));
				item.setVisible(rs.getString(4));
				result.add(item);
			}
		}catch (Exception e){
			log.error("CategoryDAO", "getFamilyList", e.toString());
		}

		return result;
	}	
	
	/**
	 * Insert data to Family.
	 * 
	 * @param family
	 */
	public void insertFamily(String family, String visible) throws Exception{
		PreparedStatement stmt = conn.prepareStatement(INSERT__FAMILY);
		stmt.setString(1, family);
		stmt.setLong(2, getMaxPosition()+1);
		stmt.setString(3, visible);
		stmt.execute();
	}
	
	/**
	 * Update family name.
	 * 
	 * @param id
	 * @param family
	 */
	public void updateFamily(String family, String visible, Long id) throws Exception{
		PreparedStatement stmt = conn.prepareStatement(UPDATE_BY_ID__FAMILY);
		stmt.setString(1, family);
		stmt.setString(2, visible);
		stmt.setLong(3, id);
		stmt.executeUpdate();
	}
	
	/**
	 * Update family position.
	 * 
	 * @param id
	 * @param position
	 */
	public void updateFamilyPosition(Long id, Long position) throws Exception{
		PreparedStatement stmt = conn.prepareStatement(UPDATE_POSITION__FAMILY);
		stmt.setLong(1, position);
		stmt.setLong(2, id);
		stmt.executeUpdate();
	}

	/**
	 * Delete data from Family by ID.
	 * 
	 * @param id
	 */
	public void deleteFamily(Long id) throws Exception{
		PreparedStatement stmt = conn.prepareStatement(DELETE_BY_ID__FAMILY);
		stmt.setLong(1, id);
		stmt.executeUpdate();
	}
	
	
	
	
	
	
	
	
	
	/**
	 * Insert Data to Channel.
	 * 
	 * @param familyID
	 * @param parentID
	 * @param name
	 * @param email
	 * @param password
	 * @param access_level
	 * @param security_level
	 * @param people_manager
	 * @param newhire
	 * @return Channel ID
	 * @throws Exception
	 */
	public Long insertChannel(Long familyID, Long parentID, String name, String email, String password, String access_level, String security_level, String people_manager, String newhire) throws Exception{
		PreparedStatement stmt = conn.prepareStatement(INSERT__CHANNEL, Statement.RETURN_GENERATED_KEYS);
		
		stmt.setLong(1, familyID);
		stmt.setString(2, name);
		stmt.setLong(3, parentID);
		stmt.setString(4, email);
		stmt.setString(5, password);
		stmt.setString(6, access_level);
		stmt.setString(7, security_level);
		
		
		stmt.executeUpdate();
		ResultSet generatedKeys = stmt.getGeneratedKeys();
		generatedKeys.next();
        return generatedKeys.getLong(1);
	}

	/**
	 * Insert Data to Channel.
	 * 
	 * @param id
	 * @param familyID
	 * @param parentID
	 * @param name
	 * @param email
	 * @param password
	 * @param access_level
	 * @param security_level
	 * @param people_manager
	 * @param newhire
	 * @throws Exception
	 */
	public void insertChannel(Long familyID, Long parentID, String name, String email, String password, String access_level, String security_level) throws Exception{
		PreparedStatement stmt = conn.prepareStatement(INSERT_EXIST__CHANNEL);
		
		stmt.setLong(1, familyID);
		stmt.setString(2, name);
		stmt.setLong(3, parentID);
		stmt.setString(4, email);
		stmt.setString(5, password);
		stmt.setString(6, access_level);
		stmt.setString(7, security_level);
		
		
		
		
		stmt.executeUpdate();
	}

	/**
	 * Update channel position.
	 * 
	 * @param id
	 * @param position
	 */
	public void updateChannelPosition(Long id, Long position) throws Exception{
		PreparedStatement stmt = conn.prepareStatement(UPDATE_POSITION__CHANNEL);
		stmt.setLong(1, position);
		stmt.setLong(2, id);
		stmt.executeUpdate();
	}
	
	/**
	 * Update channel parent.
	 * 
	 * @param id
	 * @param parentID
	 */
	public void updateChannelParent(Long id, Long parentID) throws Exception{
		PreparedStatement stmt = conn.prepareStatement(UPDATE_PARENT__CHANNEL);
		stmt.setLong(1, parentID);
		stmt.setLong(2, id);
		stmt.executeUpdate();
	}

	/**
	 * Delete data from Channel by ID.
	 * 
	 * @param id
	 */
	public void deleteChannel(Long id) throws Exception{
		PreparedStatement stmt = conn.prepareStatement(DELETE_BY_ID__CHANNEL);
		stmt.setLong(1, id);
		stmt.executeUpdate();
	}

	/**
	 * Insert data to channel_promote
	 * 
	 * @param channelID
	 * @param promoteID
	 * @throws Exception
	 */
	/*public void insertChannelPromote(Long channelID, String promoteID) throws Exception{
		PreparedStatement stmt = conn.prepareStatement(INSERT__CHANNEL_PROMOTE);
		stmt.setLong(1, channelID);
		stmt.setString(2, promoteID);
		
		stmt.executeUpdate();
	}

	/**
	 * Delete data from ChannelPromote by ChannelID.
	 * 
	 * @param id
	 */
/*	public void deleteChannelPromote(Long id) throws Exception{
		PreparedStatement stmt = conn.prepareStatement(DELETE_BY_CHANNEL_ID__CHANNEL_PROMOTE);
		stmt.setLong(1, id);
		stmt.executeUpdate();
	}

	/**
	 * Get All ChannelPromote Data.
	 * 
	 * @param channelID
	 * @return list
	 */
/*	public JSONArray getChannelPromoteListOfJSON(Long channelID){
		JSONArray result = new JSONArray();
		try{
			
			PreparedStatement stmt = conn.prepareStatement(SELECT_BY_CHANNEL_ID__CHANNEL_PROMOTE);
			stmt.setLong(1, channelID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				ChannelPromoteInfo item = new ChannelPromoteInfo();
				item.setId(rs.getLong("id"));
				item.setChannelID(rs.getLong("channel_id"));
				item.setPromoteID(rs.getString("promote_id"));
				
				result.put(item.toJSONObject());
			}
		}catch (Exception e){
			log.error("CategoryDAO", "getChannelPromoteListOfJSON", e.toString());
		}

		return result;
	}
	
	/**
	 * Insert data to channel_division
	 * 
	 * @param type Parent or Not
	 * @param channelID
	 * @param divisionID
	 * @throws Exception
	 */
	public void insertChannelDivision(String type, Long channelID, Long divisionID) throws Exception{
		PreparedStatement stmt = conn.prepareStatement(INSERT__CHANNEL_DIVISION);
		stmt.setString(1, type);
		stmt.setLong(2, channelID);
		stmt.setLong(3, divisionID);
		
		stmt.executeUpdate();
	}
	
	/**
	 * Delete data from ChannelDivison by ChannelID.
	 * 
	 * @param id
	 */
	public void deleteChannelDivision(Long id) throws Exception{
		PreparedStatement stmt = conn.prepareStatement(DELETE_BY_CHANNEL_ID__CHANNEL_DIVISION);
		stmt.setLong(1, id);
		stmt.executeUpdate();
	}
	
	/**
	 * Get All ChannelDivision Data.
	 * 
	 * @param type
	 * @param channelID
	 * @return list
	 */
	public JSONArray getChannelDivisionListOfJSON(String type, Long channelID){
		JSONArray result = new JSONArray();
		try{
			
			PreparedStatement stmt = conn.prepareStatement(SELECT_BY_CHANNEL_ID__CHANNEL_DIVISION);
			stmt.setString(1, type);
			stmt.setLong(2, channelID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				ChannelDivisionInfo item = new ChannelDivisionInfo();
				item.setId(rs.getLong("id"));
				item.setChannelID(rs.getLong("channel_id"));
				item.setDivisionID(rs.getLong("division_id"));
				item.setType(rs.getString("type"));
				
				result.put(item.toJSONObject());
			}
		}catch (Exception e){
			log.error("CategoryDAO", "getChannelDivisionListOfJSON", e.toString());
		}

		return result;
	}
	
	/**
	 * Insert data to channel_city
	 * 
	 * @param type Parent or Not
	 * @param channelID
	 * @param cityID
	 * @throws Exception
	 */
/*	public void insertChannelCity(String type, Long channelID, Long cityID) throws Exception{
/		PreparedStatement stmt = conn.prepareStatement(INSERT__CHANNEL_CITY);
		stmt.setString(1, type);
		stmt.setLong(2, channelID);
		stmt.setLong(3, cityID);
		
		stmt.executeUpdate();
	}

	/**
	 * Delete data from ChannelCity by ChannelID.
	 * 
	 * @param id
	 */
	/*public void deleteChannelCity(Long id) throws Exception{
		PreparedStatement stmt = conn.prepareStatement(DELETE_BY_CHANNEL_ID__CHANNEL_CITY);
		stmt.setLong(1, id);
		stmt.executeUpdate();
	}

	/**
	 * Get All ChannelCity Data.
	 * 
	 * @param type
	 * @param channelID
	 * @return list
	 */
	/*public JSONArray getChannelCityListOfJSON(String type, Long channelID){
		JSONArray result = new JSONArray();
		try{
			
			PreparedStatement stmt = conn.prepareStatement(SELECT_BY_CHANNEL_ID__CHANNEL_CITY);
			stmt.setString(1, type);
			stmt.setLong(2, channelID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				ChannelCityInfo item = new ChannelCityInfo();
				item.setId(rs.getLong("id"));
				item.setChannelID(rs.getLong("channel_id"));
				item.setCityID(rs.getLong("city_id"));
				item.setType(rs.getString("type"));
				
				result.put(item.toJSONObject());
			}
		}catch (Exception e){
			log.error("CategoryDAO", "getChannelCityListOfJSON", e.toString());
		}

		return result;
	}

	/**
	 * Get Channel Data
	 * 
	 * @param channelID
	 * @return Channel Information
	 */
	public ChannelInfo getChannelInfo(Long channelID){
		ChannelInfo item = new ChannelInfo();
		try{
			PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID__CHANNEL);
			stmt.setLong(1, channelID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				item.setId(rs.getLong(1));
				item.setFamilyID(rs.getLong(2));
				item.setName(rs.getString(3));
				item.setPos(rs.getLong(4));
				item.setParentID(rs.getLong(5));
				item.setEmail(rs.getString(6));
				item.setPassword(rs.getString(7));
				item.setSecurityLevel(rs.getString(8));
				item.setAccessLevel(rs.getInt(9));
			}
		}catch (Exception e){
			log.error("CategoryDAO", "getChannelInfo", e.toString());
		}

		return item;
	}	
	
	
	
	
	
	
	public int getChannelInfoName(String ChannelName){
		int item =0;
		try{
			PreparedStatement stmt = conn.prepareStatement(SELECT_BY_NAME__CHANNEL);
			stmt.setString(1, ChannelName);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				item= rs.getInt("ChannelId");				
			}
		}catch (Exception e){
			log.error("CategoryDAO", "getChannelInfo", e.toString());
		}

		return item;
	}	
	
	/**
	 * Get Channel Data by FamilyID, ParentID.
	 * 
	 * @param familyID
	 * @param parentID
	 * @return list
	 */
	public JSONArray getChannelListOfJSON(Long familyID, Long parentID){
		JSONArray result = new JSONArray();
		try{
			
			PreparedStatement stmt = conn.prepareStatement(SELECT_BY_PARENT_ID__CHANNEL);
			stmt.setLong(1, familyID);
			stmt.setLong(2, parentID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				ChannelInfo item = new ChannelInfo();
				item.setId(rs.getLong(1));
				item.setFamilyID(rs.getLong(2));
				item.setName(rs.getString(3));
				item.setPos(rs.getLong(4));
				item.setParentID(rs.getLong(5));
				item.setEmail(rs.getString(6));
				item.setPassword(rs.getString(7));
				item.setSecurityLevel(rs.getString(8));
				item.setAccessLevel(rs.getInt(9));
				
				result.put(item.toJSONObject());
			}
		}catch (Exception e){
			log.error("CategoryDAO", "getChannelListOfJSON", e.toString());
		}

		return result;
	}
	
	/**
	 * Get Channel Data by FamilyID, ParentID.
	 * 
	 * @param familyID
	 * @param parentID
	 * @return list
	 */
	public ArrayList getChannelList(Long familyID, Long parentID){
		ArrayList result = new ArrayList();
		try{
			
			PreparedStatement stmt = conn.prepareStatement(SELECT_BY_FAMILY_PARENT_ID__CHANNEL);
			stmt.setLong(1, familyID);
			stmt.setLong(2, parentID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				ChannelInfo item = new ChannelInfo();
				item.setId(rs.getLong(1));
				item.setFamilyID(rs.getLong(2));
				item.setName(rs.getString(3));
				item.setPos(rs.getLong(4));
				item.setParentID(rs.getLong(5));
				item.setEmail(rs.getString(6));
				item.setPassword(rs.getString(7));
				item.setSecurityLevel(rs.getString(8));
				item.setAccessLevel(rs.getInt(9));
				result.add(item);
			}
		}catch (Exception e){
			log.error("CategoryDAO", "getChannelList", e.toString());
		}

		return result;
	}	
	
	/**
	 * Get Channel Data by ParentID.
	 * 
	 * @param parentID
	 * @return list
	 */
	public ArrayList getChannelList(Long parentID){
		ArrayList result = new ArrayList();
		try{
			
			PreparedStatement stmt = conn.prepareStatement(SELECT_BY_PARENT_ID__CHANNEL);
			stmt.setLong(1, parentID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				ChannelInfo item = new ChannelInfo();
				item.setId(rs.getLong(1));
				item.setFamilyID(rs.getLong(2));
				item.setName(rs.getString(3));
				item.setPos(rs.getLong(4));
				item.setParentID(rs.getLong(5));
				item.setEmail(rs.getString(6));
				item.setPassword(rs.getString(7));
				item.setSecurityLevel(rs.getString(8));
				item.setAccessLevel(rs.getInt(9));
				
				result.add(item);
			}
		}catch (Exception e){
			log.error("CategoryDAO", "getChannelList", e.toString());
		}

		return result;
	}	
	
	/**
	 * Get All Channel Data by FamilyID, ParentID.
	 * 
	 * @param familyID
	 * @param parentID
	 * @return list
	 */
	public JSONArray recallChannelList(Long familyID, Long parentID, String type, String channel_id){
		JSONArray result = new JSONArray();
		try{
			ArrayList list = getChannelList(familyID, parentID);
			for (int i=0; i<list.size(); i++){
				ChannelInfo item = (ChannelInfo) list.get(i);
				if (type.equals("edit") && Utils.getLong(channel_id)==item.getId()){
					
				}else{
					JSONObject obj = new JSONObject();
					obj.put("parent", item.toJSONObject());
					obj.put("child", recallChannelList(familyID, item.getId(), type, channel_id));
					result.put(obj);
				}
			}
		}catch (Exception e){
			log.error("CategoryDAO", "recallChannelList", e.toString());
		}

		return result;
	}
	
	/**
	 * Delete All Channel Data by ParentID.
	 * 
	 * @param parentID
	 */
public void deleteChannelList(Long parentID){
		JSONArray result = new JSONArray();
		try{
			ArrayList list = getChannelList(parentID);
			for (int i=0; i<list.size(); i++){
				ChannelInfo item = (ChannelInfo) list.get(i);
				deleteChannelList(item.getId());
				
				deleteChannel(item.getId());
				deleteChannelList(item.getId(), "city");
				deleteChannelList(item.getId(), "company");
				deleteChannelList(item.getId(), "maindirection");
				
//				deleteChannelCity(item.getId());
//				deleteChannelPromote(item.getId());
//				deleteChannelDivision(item.getId());
//				deleteChannelJobgrade(item.getId());
//				deleteChannelPayowner(item.getId());
			}
		}catch (Exception e){
			log.error("CategoryDAO", "deleteChannelList", e.toString());
		}
	}	
	
	/**
	 * Update Position and Parent in Channel.
	 * 
	 * @param parentID
	 * @param list List of Position and Parent
	 */
	public void updateChannel(Long parentID, JSONArray list){
		try{
			for (int i=0; i<list.length(); i++){
				JSONObject obj = (JSONObject)list.get(i);
				String ids = Utils.checkNull(obj.get("id"));
				String childs = Utils.checkNull(obj.get("child"));
				if (ids.length()>0){
					Long pID = Utils.getLong(ids);
					
					updateChannelPosition(pID, (long)i+1);
					updateChannelParent(pID, parentID);
					updateChannel(pID, new JSONArray(childs));
				}
			}
		}catch (Exception e){
			log.error("CategoryDAO", "updateChannel", e.toString());
		}
	}
	
	/**
	 * Insert data to channel_jobgrade
	 * 
	 * @param channelID
	 * @param jobgradeID
	 * @throws Exception
	 */
	/*public void insertChannelJobgrade(Long channelID, String jobgradeID) throws Exception{
		PreparedStatement stmt = conn.prepareStatement(INSERT__CHANNEL_JOBGRADE);
		stmt.setLong(1, channelID);
		stmt.setString(2, jobgradeID);
		
		stmt.executeUpdate();
	}

	/**
	 * Delete data from channel_jobgrade by ChannelID.
	 * 
	 * @param id
	 */
/*	public void deleteChannelJobgrade(Long id) throws Exception{
		PreparedStatement stmt = conn.prepareStatement(DELETE_BY_CHANNEL_ID__CHANNEL_JOBGRADE);
		stmt.setLong(1, id);
		stmt.executeUpdate();
	}

	/**
	 * Get All channel_jobgrade Data.
	 * 
	 * @param channelID
	 * @return list
	 */
	/*public JSONArray getChannelJobgradeListOfJSON(Long channelID){
		JSONArray result = new JSONArray();
		try{
			
			PreparedStatement stmt = conn.prepareStatement(SELECT_BY_CHANNEL_ID__CHANNEL_JOBGRADE);
			stmt.setLong(1, channelID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				ChannelJobgradeInfo item = new ChannelJobgradeInfo();
				item.setId(rs.getLong("id"));
				item.setChannelID(rs.getLong("channel_id"));
				item.setJobgradeID(rs.getString("jobgrade_id"));
				
				result.put(item.toJSONObject());
			}
		}catch (Exception e){
			log.error("CategoryDAO", "getChannelJobgradeListOfJSON", e.toString());
		}

		return result;
	}

	/**
	 * Insert data to channel_payowner
	 * 
	 * @param channelID
	 * @param payownerID
	 * @throws Exception
	 */
	/*public void insertChannelPayowner(Long channelID, String payownerID) throws Exception{
		PreparedStatement stmt = conn.prepareStatement(INSERT__CHANNEL_PAYOWNER);
		stmt.setLong(1, channelID);
		stmt.setString(2, payownerID);
		
		stmt.executeUpdate();
	}

	/**
	 * Delete data from channel_payowner by ChannelID.
	 * 
	 * @param id
	 */
	/*public void deleteChannelPayowner(Long id) throws Exception{
		PreparedStatement stmt = conn.prepareStatement(DELETE_BY_CHANNEL_ID__CHANNEL_PAYOWNER);
		stmt.setLong(1, id);
		stmt.executeUpdate();
	}

	/**
	 * Get All channel_payowner Data.
	 * 
	 * @param channelID
	 * @return list
	 */
/*	public JSONArray getChannelPayownerListOfJSON(Long channelID){
		JSONArray result = new JSONArray();
		try{
			
			PreparedStatement stmt = conn.prepareStatement(SELECT_BY_CHANNEL_ID__CHANNEL_PAYOWNER);
			stmt.setLong(1, channelID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				ChannelPayownerInfo item = new ChannelPayownerInfo();
				item.setId(rs.getLong("id"));
				item.setChannelID(rs.getLong("channel_id"));
				item.setPayownerID(rs.getString("payowner_id"));
				
				result.put(item.toJSONObject());
			}
		}catch (Exception e){
			log.error("CategoryDAO", "getChannelPayownerListOfJSON", e.toString());
		}

		return result;
	}
	*/
	public JSONArray getListOfJSON(Long channelID, String table, String field){
		JSONArray result = new JSONArray();
		try{
			if(table.equals("channelcity"))
			{
				PreparedStatement stmt = conn.prepareStatement("select * from channelcity where Channel_ChannelId=? ");
				stmt.setLong(1, channelID);
				ResultSet rs = stmt.executeQuery();
				while (rs.next())
				{
					JSONObject item = new JSONObject();
					item.put("Channel_ChannelId", rs.getLong("Channel_ChannelId"));
					item.put(field, rs.getString(field));
					
					result.put(item);
				}
			}
			if(table.equals("channelcompany"))
			{
				PreparedStatement stmt = conn.prepareStatement("select * from channelcompany where Channel_ChannelId=? ");
				stmt.setLong(1, channelID);
				ResultSet rs = stmt.executeQuery();
				while (rs.next())
				{
					JSONObject item = new JSONObject();
					item.put("Channel_ChannelId", rs.getLong("Channel_ChannelId"));
					item.put(field, rs.getString(field));
					
					result.put(item);
				}
			}
			
			if(table.equals("channelmaindirection"))
			{
				PreparedStatement stmt = conn.prepareStatement("select * from channelmaindirection where Channel_ChannelId=? ");
				stmt.setLong(1, channelID);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()){
					JSONObject item = new JSONObject();
					item.put("Channel_ChannelId", rs.getLong("Channel_ChannelId"));
					item.put(field, rs.getString(field));
					
					result.put(item);
				}
			}
			
		}catch (Exception e){
			log.error("CategoryDAO", "getListOfJSON", e.toString());
		}

		return result;
	}

	public void insertChannelList(Long channelID, Long subID, String table) throws Exception{
		if(table.equals("channelcity"))
		{
			PreparedStatement stmt = conn.prepareStatement(" insert into channelcity ( Channel_ChannelId, City_CityId ) values ( ?, ? ) ");
			stmt.setLong(1, channelID);
			stmt.setLong(2, subID);
			stmt.executeUpdate();
		}
		if(table.equals("channelcompany"))
		{
			PreparedStatement stmt = conn.prepareStatement(" insert into channelcompany ( Channel_ChannelId, Company_CompanyId ) values ( ?, ? ) ");
			stmt.setLong(1, channelID);
			stmt.setLong(2, subID);
			stmt.executeUpdate();
		}
		
		if(table.equals("channelmaindirection"))
		{
			PreparedStatement stmt = conn.prepareStatement(" insert into channelmaindirection ( Channel_ChannelId, Maindirection_MaindirectionId ) values ( ?, ? ) ");
			stmt.setLong(1, channelID);
			stmt.setLong(2, subID);
			stmt.executeUpdate();
		}
	}
	
	public void deleteChannelList(Long channelID, String table) throws Exception{
		
		if(table.equals("channelcity"))
		{
			PreparedStatement stmt = conn.prepareStatement(" delete from channelcity where Channel_ChannelId=? ");
			stmt.setLong(1, channelID);
			stmt.executeUpdate();
		}
		if(table.equals("channelcompany"))
		{
			PreparedStatement stmt = conn.prepareStatement(" delete from channelcompany where Channel_ChannelId=? ");
			stmt.setLong(1, channelID);
			stmt.executeUpdate();
		}
		if(table.equals("channelmaindirection"))
		{
			PreparedStatement stmt = conn.prepareStatement(" delete from channelmaindirection where Channel_ChannelId=? ");
			stmt.setLong(1, channelID);
			stmt.executeUpdate();
		}
	}
	
	public long getChanneFather(long ChannelId)
	{
		long item =0;
		long resultado=0;
		
		try
		{
			PreparedStatement stmt = conn.prepareStatement("select ChannelFather from channel ");
			
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) 
			{
				item= rs.getInt("ChannelFather");
				if(item==ChannelId)
				{
					resultado=1;
				}
			}
			
		}
		catch (Exception e)
		{
			log.error("CategoryDAO", "getChannelInfofather", e.toString());
		}

		return resultado;
	}	
	
	public void updateChannel(Long familyID, Long parentID, String name, String email, String password, String IsVisible, String security_level, long channelID) throws Exception
	{
		PreparedStatement stmt = conn.prepareStatement("UPDATE channel SET Family_FamilyId=?,ChannelName=?,ChannelPosition=?,ChannelFather=?,ChannelEmail=?,ChannelPassword=?,ChannelSecurityLevel=?,ChannelIsVisible=? WHERE ChannelId=? ");
		stmt.setLong(1, familyID);
		stmt.setString(2, name);
		stmt.setLong(3, 0);
		stmt.setLong(4, parentID);
		stmt.setString(5, email);
		stmt.setString(6, password);
		stmt.setString(7, security_level);
		stmt.setString(8, IsVisible);
		stmt.setLong(9, channelID);
		stmt.executeUpdate();
	}
	
	
	
	
	
	
	public long getChanneFamilyId(long ChannelId)
	{
		long item =0;
		
		
		try
		{
			PreparedStatement stmt = conn.prepareStatement("select Family_FamilyId from channel where ChannelId=? ");
			stmt.setLong(1, ChannelId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) 
			{
				item= rs.getInt("Family_FamilyId");
				
			}
			
		}
		catch (Exception e)
		{
			log.error("CategoryDAO", "getChannelFamilyId", e.toString());
		}

		return item;
	}
	
	
	
	
	
	public void updateChannelfamily(long familyID, long channelID) throws Exception
	{
		PreparedStatement stmt = conn.prepareStatement("UPDATE channel SET Family_FamilyId=? WHERE ChannelId=? ");
		stmt.setLong(1, familyID);
		stmt.setLong(2, channelID);
		stmt.executeUpdate();
	}
	
	
	
	
	
	
	
	
	
	
	//select ChannelId from channel where family_familyId=44 
	public long getChanneFamily_FamilyId(long ChannelId, long familyID)
	{
		long item =0;
		
		
		try
		{
			PreparedStatement stmt = conn.prepareStatement("select ChannelId from channel where family_familyId=? ");
			stmt.setLong(1, ChannelId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) 
			{
				item= rs.getInt("ChannelId");
				updateChannelfamily(item,familyID);
				
			}
			
		}
		catch (Exception e)
		{
			log.error("CategoryDAO", "getChannelFamilyId", e.toString());
		}

		return item;
	}
}
