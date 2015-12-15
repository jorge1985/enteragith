package com.youandbbva.enteratv.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import com.youandbbva.enteratv.DAO;
import com.youandbbva.enteratv.DSManager;
import com.youandbbva.enteratv.Utils;
import com.youandbbva.enteratv.domain.BannerInfo;
import com.youandbbva.enteratv.domain.FeaturesInfo;

/**
 * 
 * Handle all query for Widgets.
 * 		bbva_banner
 * 
 * @author CJH
 *
 */

@Repository("SystemDAO")
public class WidgetDAO extends DAO{

	/**
	 * SQL queries
	 */
	private final String TABLE_NAME__BANNER = "banner";
	private final String COLUMN_NAME__BANNER = "BannerId,BannerUrl,Media_MediaId,BannerLinkTarget";
	private final String SELECT__BANNNER= " select " + COLUMN_NAME__BANNER + " from " + TABLE_NAME__BANNER + " order by BannerId ";
	private final String SELECT_BY_ID__BANNNER= " select " + COLUMN_NAME__BANNER + " from " + TABLE_NAME__BANNER + " where BannerId=? order by BannerId ";
	private final String SELECT_BY_SUB_ID__BANNNER= " select " + COLUMN_NAME__BANNER + " from " + TABLE_NAME__BANNER + " where BannerId=? ";
	private final String UPDATE_BY_ID__BANNER = " update " + TABLE_NAME__BANNER + " set BannerUrl=?, Media_MediaId=? , BannerLinkTarget=?  where BannerId=? ";
	private final String SELECTMEDIAID = "select MediaId from media where MediaName = ?"; 
	
	
	public WidgetDAO() {
		// TODO Auto-generated constructor stub
	}
	
	public WidgetDAO(Connection conn) {
		// TODO Auto-generated constructor stub
		super(conn);
	}

	/**
	 * Get all banners.
	 * 
	 * @return list
	 */
	public JSONArray getBanners(){
		JSONArray result = new JSONArray();
		Connection conn = DSManager.getConnection();
		
		try{
																// RUNNING QUERY
			PreparedStatement stmt = conn.prepareStatement(SELECT__BANNNER);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				BannerInfo item = new BannerInfo();
				item.setId(rs.getLong(1));
				String image = "/serve?blob-key="+getMediaContent2(rs.getString(3));
				item.setImage(image);
				item.setTarget(Utils.checkNull(rs.getString(4)));
				item.setUrl(Utils.checkNull(rs.getString(2)));
				
				result.put(item.toJSONObject());
			}
		}catch (Exception e){
			log.error("WidgetDAO", "getBanners", e.toString());
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
	 * Get Banner Data.
	 * 
	 * @param bannerID
	 * @return List of banner
	 */
	public JSONArray getBanner(Long bannerID){
		JSONArray result = new JSONArray();
		Connection conn = DSManager.getConnection();
		
		try{
																	// RUNNING QUERY
			PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID__BANNNER);
			stmt.setLong(1, bannerID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				BannerInfo item = new BannerInfo();
				item.setId(rs.getLong(1));
				item.setImage(Utils.checkNull(rs.getString(3)));
				item.setTarget(Utils.checkNull(rs.getString(4)));
				item.setUrl(Utils.checkNull(rs.getString(2)));
				
				result.put(item.toJSONObject());
			}
		}catch (Exception e){
			log.error("WidgetDAO", "getBanner", e.toString());
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
	 * Get Banner Data.
	 * 
	 * @param bannerID
	 * @param subID
	 * @return banner
	 */
	public JSONObject getBanner(Long bannerID, Long subID){
		JSONObject result = new JSONObject();
		Connection conn = DSManager.getConnection();
		
		try{
																			//RUNNING QUERY
			PreparedStatement stmt = conn.prepareStatement(SELECT_BY_SUB_ID__BANNNER);
			stmt.setLong(1, bannerID);
			stmt.setLong(2, subID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				BannerInfo item = new BannerInfo();
				item.setId(rs.getLong(1));
				item.setImage(Utils.checkNull(rs.getString(3)));
				item.setTarget(Utils.checkNull(rs.getString(4)));
				item.setUrl(Utils.checkNull(rs.getString(2)));
				
				return item.toJSONObject();
			}
		}catch (Exception e){
			log.error("WidgetDAO", "getBanner", e.toString());
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
	 * Update banner data.
	 * 
	 * @param bannerID
	 * @param image
	 * @param target
	 * @param url
	 * @throws Exception
	 */
	public void updateBanner(Long bannerID, Long image, String target, String url) throws Exception
	{
																	//RUNNING QUERY
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = conn.prepareStatement(UPDATE_BY_ID__BANNER);
		stmt.setString(1, url);
		stmt.setLong(2, image);
		stmt.setInt(3, Integer.parseInt(target));
		stmt.setLong(4, bannerID);
		stmt.execute();
		conn.close();
	}

	/**
	 * Get Features Data.
	 * 
	 * @param featuresID
	 * @return List of features
	 */
	
		
	public String getMediaContent2(String idMedia) {
		String result = "";
	
		Connection conn = DSManager.getConnection();
		try {
																// RUNNING QUERY
			String query ="select MediaContent from media where MediaId = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, Integer.parseInt(idMedia));
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {

				result = rs.getString(1);

				return result;
			}
		} catch (Exception e) {
			log.error("MediaDAO", "getFileTypeId", e.toString());
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
	 * GET MEDIAID BY NAME
	 * @param Name
	 * @return
	 */
	public int getMediaName(String Name) {
		int result = 0;
		Connection conn = DSManager.getConnection();
		try {
															// RUNNING QUERY
			PreparedStatement stmt = conn.prepareStatement(SELECTMEDIAID);
			stmt.setString(1 , Name);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {

				result = rs.getInt(1);

				return result;
			}
		} catch (Exception e) {
			log.error("MediaDAO", "getFileTypeId", e.toString());
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
