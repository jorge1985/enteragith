package com.youandbbva.enteratv.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import com.youandbbva.enteratv.DAO;
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

	private final String TABLE_NAME__BANNER = "banner";
	private final String COLUMN_NAME__BANNER = "BannerId,BannerUrl,Media_MediaId,BannerLinkTarget";
	private final String SELECT__BANNNER= " select " + COLUMN_NAME__BANNER + " from " + TABLE_NAME__BANNER + " order by BannerId ";
	private final String SELECT_BY_ID__BANNNER= " select " + COLUMN_NAME__BANNER + " from " + TABLE_NAME__BANNER + " where BannerId=? order by BannerId ";
	private final String SELECT_BY_SUB_ID__BANNNER= " select " + COLUMN_NAME__BANNER + " from " + TABLE_NAME__BANNER + " where BannerId=? ";
	private final String UPDATE_BY_ID__BANNER = " update " + TABLE_NAME__BANNER + " set BannerUrl=?, Media_MediaId=? , BannerLinkTarget=?  where BannerId=? ";

		//towa
	//private final String TABLE_NAME__FEATURES = "bbva_features";
	//private final String COLUMN_NAME__FEATURES = "id,sub_id,image,title,date,content,content_id";
	//private final String SELECT_BY_ID__FEATURES = " select " + COLUMN_NAME__FEATURES + " from " + TABLE_NAME__FEATURES + " where id=? order by sub_id ";
	//private final String SELECT_BY_SUB_ID__FEATURES = " select " + COLUMN_NAME__FEATURES + " from " + TABLE_NAME__FEATURES + " where id=? and sub_id=? ";
	//private final String UPDATE_BY_ID__FEATURES = " update " + TABLE_NAME__FEATURES + " set image=? , title=? , date=? , content=?, content_id=? where id=? and sub_id=? ";
		//towa
	
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
		
		try{
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
		
		try{
			PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID__BANNNER);
			stmt.setLong(1, bannerID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				BannerInfo item = new BannerInfo();
				item.setId(rs.getLong(1));
//				item.setSubID(rs.getLong("sub_id"));
				item.setImage(Utils.checkNull(rs.getString(3)));
				item.setTarget(Utils.checkNull(rs.getString(4)));
				item.setUrl(Utils.checkNull(rs.getString(2)));
				
				result.put(item.toJSONObject());
			}
		}catch (Exception e){
			log.error("WidgetDAO", "getBanner", e.toString());
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
		
		try{
			PreparedStatement stmt = conn.prepareStatement(SELECT_BY_SUB_ID__BANNNER);
			stmt.setLong(1, bannerID);
			stmt.setLong(2, subID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				BannerInfo item = new BannerInfo();
				item.setId(rs.getLong(1));
//				item.setSubID(rs.getLong("sub_id"));
				item.setImage(Utils.checkNull(rs.getString(3)));
				item.setTarget(Utils.checkNull(rs.getString(4)));
				item.setUrl(Utils.checkNull(rs.getString(2)));
				
				return item.toJSONObject();
			}
		}catch (Exception e){
			log.error("WidgetDAO", "getBanner", e.toString());
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
	public void updateBanner(Long bannerID, Long image, String target, String url) throws Exception{
		PreparedStatement stmt = conn.prepareStatement(UPDATE_BY_ID__BANNER);
		//update banner set BannerUrl=1, Media_MediaId='JJJJJ' , BannerLinkTarget=19  where BannerId='2'
		stmt.setString(1, url);
		stmt.setLong(2, image);
		stmt.setInt(3, Integer.parseInt(target));
		stmt.setLong(4, bannerID);
		stmt.execute();
	}

	/**
	 * Get Features Data.
	 * 
	 * @param featuresID
	 * @return List of features
	 */
	
	/*towa inicio
	public JSONArray getFeatures(Long featuresID){
		JSONArray result = new JSONArray();
		
		try{
			PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID__FEATURES);
			stmt.setLong(1, featuresID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				FeaturesInfo item = new FeaturesInfo();
				item.setId(rs.getLong("id"));
				item.setSubID(rs.getLong("sub_id"));
				item.setImage(Utils.checkNull(rs.getString("image")));
				item.setTitle(Utils.checkNull(rs.getString("title")));
				item.setDate(Utils.checkNull(rs.getString("date")));
				item.setContent(Utils.checkNull(rs.getString("content")));
				item.setContentID(Utils.checkNull(rs.getLong("content_id"), (long)0));
				
				result.put(item.toJSONObject());
			}
		}catch (Exception e){
			log.error("WidgetDAO", "getFeatures", e.toString());
		}

		return result;
	}/*towafin

	/**
	 * Get Features Data.
	 * 
	 * @param featuresID
	 * @param subID
	 * @return features
	 */
	
	/*towainicio
	public JSONObject getFeatures(Long featuresID, Long subID){
		JSONObject result = new JSONObject();
		
		try{
			PreparedStatement stmt = conn.prepareStatement(SELECT_BY_SUB_ID__FEATURES);
			stmt.setLong(1, featuresID);
			stmt.setLong(2, subID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				FeaturesInfo item = new FeaturesInfo();
				item.setId(rs.getLong("id"));
				item.setSubID(rs.getLong("sub_id"));
				item.setImage(Utils.checkNull(rs.getString("image")));
				item.setTitle(Utils.checkNull(rs.getString("title")));
				item.setDate(Utils.checkNull(rs.getString("date")));
				item.setContent(Utils.checkNull(rs.getString("content")));
				item.setContentID(Utils.checkNull(rs.getLong("content_id"), (long)0));
				
				return item.toJSONObject();
			}
		}catch (Exception e){
			log.error("WidgetDAO", "getFeatures", e.toString());
		}

		return result;
	}
	/*towafin
	
	/**
	 * Update features data.
	 * 
	 * @param featuresID
	 * @param subID
	 * @param image
	 * @param title
	 * @param date
	 * @param content
	 * @param contentID
	 * @throws Exception
	 */
	
	/*towainicio
	public void updateFeatures(Long featuresID, Long subID, String image, String title, String date, String content, Long contentID) throws Exception{
		PreparedStatement stmt = conn.prepareStatement(UPDATE_BY_ID__FEATURES);
		stmt.setString(1, image);
		stmt.setString(2, title);
		stmt.setString(3, date);
		stmt.setString(4, content);
		stmt.setLong(5, contentID);
		stmt.setLong(6, featuresID);
		stmt.setLong(7, subID);
		stmt.execute();
	}
*/
	
	public String getMediaContent2(String idMedia) {
		String result = "";
		//Consulta a la tabla de Media
		
		try {
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
		
		
		return result;

	}
	
	public int getMediaName(String Name) {
		int result = 0;
		//Consulta a la tabla de Media
		
		try {
			String query ="select MediaId from media where MediaName = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1 , Name);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {

				result = rs.getInt(1);

				return result;
			}
		} catch (Exception e) {
			log.error("MediaDAO", "getFileTypeId", e.toString());
		}
		
		
		return result;

	}
}
