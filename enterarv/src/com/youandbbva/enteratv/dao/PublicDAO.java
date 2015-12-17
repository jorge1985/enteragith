package com.youandbbva.enteratv.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import com.youandbbva.enteratv.Constants;
import com.youandbbva.enteratv.DAO;
import com.youandbbva.enteratv.DSManager;
import com.youandbbva.enteratv.DataSource;
import com.youandbbva.enteratv.HTML;
import com.youandbbva.enteratv.Utils;
import com.youandbbva.enteratv.domain.BannerInfo;
import com.youandbbva.enteratv.domain.ChannelInfo;
import com.youandbbva.enteratv.domain.ContentGalleryMediaInfo;
import com.youandbbva.enteratv.domain.ContentInfo;
import com.youandbbva.enteratv.domain.ContentVideoInfo;
import com.youandbbva.enteratv.domain.FamilyInfo;
import com.youandbbva.enteratv.domain.FeaturesInfo;
import com.youandbbva.enteratv.domain.UserInfo;

/**
 * 
 * Handle all query for public page.
 * 
 * @author CJH
 *
 */

@Repository("PublicDAO")
public class PublicDAO extends DAO{
	
	
	/**
	 * SQL queries
	 */
	private static final String TABLE_FAMILY = "family";
	private static final String COLUMNS_TABLE_FAMILY = "FamilyId,Familyname,FamilyPosition,FamilyIsVisible";
	private static final String SELECT_FAMILY = "select " + COLUMNS_TABLE_FAMILY + " from " + TABLE_FAMILY + " where FamilyIsVisible='1' order by FamilyPosition";
	private static final String TABLE_VISIT = "visit";
	private static final String COLUMNS_VISIT = "VisitId,User_UserId,Content_ContentId,VisitDate,VisitIp";
	private static final String INSERT_VISIT = "insert into " + TABLE_VISIT + "("+"User_UserId,Content_ContentId,VisitDate,VisitIp"+") values (?, ?, ?, ?)";
	private static final String COUNT_BY_USER_SESSION_VISIT = " select count(*) from " + TABLE_VISIT + " where User_UserId=?";
	private final String TABLE_NAME__BANNER = "banner";
	private final String COLUMN_NAME__BANNER = "BannerId,BannerUrl,Media_MediaId,BannerLinkTarget";
	private final String SELECT_BY_ID__BANNNER= " select " + COLUMN_NAME__BANNER + " from " + TABLE_NAME__BANNER + " where BannerId=? ";
	private final String TABLE_NAME__FEATURES = "bbva_features";
	private final String COLUMN_NAME__FEATURES = "id,sub_id,image,title,date,content";
	private final String SELECT_BY_ID__FEATURES = " select " + COLUMN_NAME__FEATURES + ",content_id from " + TABLE_NAME__FEATURES + " where id=? order by sub_id ";
	private final String SELECT_LATEST_5_VIDEO__CONTENT = "select ContentId, MediaContent from media a, contentmedia b, content c where c.ContentId= b.Content_ContentId and b.Media_MediaId= a.MediaId and c.ContentStatus='A' and c.Contenttype_ContenttypeId=2 and a.Filetype_FiletypeId=3 and not c.ContentShowView = 2 order by  ContentId desc limit 5 ";
	private final String SELECT_WIDGET_HTML__CONFIG= " select home.HomeElement, home.HomeHtml from home where home.HomeElement=? ";
	private final String SELECT_LATEST_2_NEWS__CONTENT = " select a.*, b.content,b.image from bbva_content a, bbva_content_news b where a.id=b.content_id and a.type='002' and ( a.show_in='001' or a.show_in='003' ) and a.active='1' and a.status='0' and a.validity_end>=?  order by a.updated_at desc limit 2 " ;
	private final String SELECT_LATEST_NEWS__CONTENT = " select a.*, b.content,b.image from bbva_content a, bbva_content_news b where a.id=b.content_id and a.type='002' and a.active='1' and a.status='0' and a.validity_end>=? and a.id in ( select content_id from bbva_content_channel where channel_id=? ) order by a.updated_at desc limit 1 " ;
	private final String TABLE_NAME__CHANNEL="channel";
	private final String COLUMN_NAME__CHANNEL="ChannelId,Family_FamilyId,ChannelName,ChannelPosition,ChannelFather,ChannelEmail,ChannelPassword,ChannelSecurityLevel,ChannelIsVisible";
	private final String SELECT_BY_FAMILY_PARENT_ID__CHANNEL = " select " + COLUMN_NAME__CHANNEL + " from " + TABLE_NAME__CHANNEL + " where Family_FamilyId=? and ChannelFather=? order by ChannelPosition ";
	private final String SELECT_BY_PARENT_ID__CHANNEL = " select " + COLUMN_NAME__CHANNEL + " from " + TABLE_NAME__CHANNEL + " where ChannelFather=? order by ChannelPosition ";
	private final String SELECT_BY_ID__CONTENT = " select * from content  where ContentId=? " ; 
	private final String SELECT_GALLERY__CONTENT = " select * from content where ContentId=? " ; 
	private final String COLUMN_CONTENT_SELECT = "c.ContentId,c.Channel_ChannelId,c.Contenttype_ContenttypeId,c.ContentName,c.ContentHtml,c.ContentIsVisible," 
            + "c.ContentPublishDate,c.ContentEndDate,c.ContentStatus,c.ContentShowView";
	private final String TABLE_NAME_CONTENT = "content";
	private final String TABLE_NAME_CONTENTTYPE = "contenttype";	
	private final String SELECT_CONTENT_BY_ID = "select " + COLUMN_CONTENT_SELECT + " from " + TABLE_NAME_CONTENT +  " c, " + TABLE_NAME_CONTENTTYPE + " t  where c.Contenttype_ContenttypeId = t.ContenttypeId and c.ContentId =?";
	private final String SELECTMEDIACONTENT = "Select MediaContent from media m, contentmedia t where m.MediaId = t.Media_MediaId and t.Content_contentId = ?";
	private final String SELECT_MEDIAID= " select MediaId,MediaName,MediaContent from media m,contentmedia t where m.MediaId = t.Media_MediaId and t.Content_ContentId = ?";
	
	
	
	public PublicDAO() {
		// TODO Auto-generated constructor stub
	}
	
	public PublicDAO(Connection conn) {
		// TODO Auto-generated constructor stub
		super(conn);
	}

	/**
	 * Get Content Information by Content ID.
	 * 
	 * @param contentID
	 * @return Content
	 */
	public ContentInfo getContent(Long contentID){
		Connection conn = DSManager.getConnection();
		try{
																	// RUNNING QUERY
			PreparedStatement stmt = conn.prepareStatement(SELECT_CONTENT_BY_ID);
			stmt.setLong(1, contentID);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				ContentInfo result = new ContentInfo();
				result.setId(contentID);
				result.setType(rs.getString("Contenttype_ContenttypeId"));
				result.setShowIn(rs.getString("ContentShowView"));
				result.setName(rs.getString("ContentName"));
				result.setValidityStart(rs.getString("ContentPublishDate"));
				result.setValidityEnd(rs.getString("ContentEndDate"));
				result.setActive(rs.getString("ContentStatus"));
				result.setStatus("0");
				result.setCreatedAt("00/00/00");
				result.setUpdatedAt("00/00/00");
				result.setHtml(Utils.checkNull(rs.getString("ContentHtml")));
				result.setHtmlMobile(Utils.checkNull(rs.getString("ContentHtml")));
				result.setBlog("blog");
				
				return result;
			}
		}catch (Exception e){
			log.error("ContentDAO", "getContent", e.toString());
		}finally
		{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return null;
	}	
	
	/**
	 * Get content id.
	 * 
	 * @param channelID
	 * @return id
	 */
	public ContentInfo getContentID(Long channelID){
		ContentInfo result = new ContentInfo();
		Connection conn = DSManager.getConnection();
		
		try
		{
																	//	RUNNING QUERY
			PreparedStatement stmt = conn.prepareStatement(SELECT_LATEST_NEWS__CONTENT);
			stmt.setString(1, Utils.getToday("-"));
			stmt.setLong(2, channelID);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				result.setId(rs.getLong("id"));
				result.setName(rs.getString("name"));
				result.setBlog(Utils.checkNull(rs.getString("blog")));
				
				return result;
			}
		}catch (Exception e){
			log.error("PublicDAO", "getContent", e.toString());
		}finally
		{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	//JR busca el Id del contenido mediante el id del canal.
	public ContentInfo getCotentIdNew(Long channelId) throws SQLException{
		Connection conn = DSManager.getConnection();
		ContentInfo result = new ContentInfo();
		String sql = "select c.* from content c, channel ch where c.ContentIsVisible = '1' and c.ContentStatus = 'A' and c.ContentEndDate>=? and c.Channel_ChannelId = ch.ChannelId and c.Channel_ChannelId = ?";
	
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, Utils.getToday("-"));
		stmt.setLong(2, channelId);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()){
			result.setId(rs.getLong("ContentId"));
			result.setName(rs.getString("ContentName"));
			//result.setBlog(Utils.checkNull(rs.getString("blog")));
		}
		conn.close();
		return result;
	}
	
	
	/**
	 * Check whether exist or not.
	 * 
	 * @param outsideUserId
	 * @param type
	 * @param sessionID
	 * @param date
	 * @return exist or not
	 */
	public boolean isExistVisitor(int outsideUserId){
		Connection conn = DSManager.getConnection();
		try{
			long result = 0;
																	// 	RUNNING QUERY
			PreparedStatement stmt = conn.prepareStatement(COUNT_BY_USER_SESSION_VISIT);
			stmt.setInt(1, outsideUserId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				result = rs.getLong(1);
			}
			
			return result>0 ? true : false;
		}catch (Exception e){
			log.error("PublicDAO", "isExist", e.toString());
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
	 * Insert visitor.
	 * 
	 * @param outsideUserId
	 * @param type
	 * @param sessionID
	 * @param date
	 * @param time
	 * @param ip_addr
	 * @throws Exception
	 */
	public void insertVisitor(int outsideUserId, String type, String date, String ip_addr) throws Exception{
																	// RUNNING QUERY
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = conn.prepareStatement(INSERT_VISIT);		
		stmt.setInt(1, outsideUserId);
		stmt.setString(2, type);
		stmt.setString(3, date);
		stmt.setString(4, ip_addr);
		stmt.executeUpdate();
		conn.close();
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
		
		try{
																	// RUNNING QUERY
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				result = rs.getLong(1);
			}
		}catch (Exception e){
			log.error("PublicDAO", "getCount", e.toString());
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
	 * Get Content List for DataTable.
	 * 
	 * @param sql
	 * @param language
	 * @return ContentList
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
				
				Long contentID = rs.getLong("ContentId");
				item.put("id", contentID);
				item.put("name", rs.getString("ContentName"));
				item.put("type", rs.getString("Contenttype_ContenttypeId"));
				if (language.length()>0)
					item.put("type_name", rs.getString("MenuValue"));
				else
					item.put("type_name", rs.getString("MenuValue"));
				item.put("date", Utils.toDisplayDate(rs.getString("ContentPublishDate")));
				
				result.put(item);
			}
		}catch (Exception e){
			log.error("PublicDAO", "getContent", e.toString());
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
	 * Get Content List for DataTable.
	 * 
	 * @param sql
	 * @param language
	 * @return ContentList
	 */
	public JSONArray getContent(String sql, String language, int start){
		JSONArray result = new JSONArray();
		Connection conn = DSManager.getConnection();
		
		try{
																	// RUNNING QUERY
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				JSONObject item = new JSONObject();
				
				Long contentID = rs.getLong("ContentId");
				item.put("id", contentID);
				item.put("name", rs.getString("ContentName"));
				item.put("type", rs.getString("Contenttype_ContenttypeId"));
//				if (language.length()>0)
//					item.put("type_name", rs.getString("MenuValue"));
//				else
//					item.put("type_name", rs.getString("MenuValue"));
				item.put("date", Utils.toDisplayDate(rs.getString("ContentPublishDate")));
				//item.put("blog", Utils.checkNull(rs.getString("")));
				item.put("no",start+1);
				
				result.put(item);
				
				start++;
			}
		}catch (Exception e){
			log.error("PublicDAO", "getContent", e.toString());
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
	 * Get Latest Featured Video.
	 * 
	 * @return ContentList
	 */
	public ArrayList<ContentVideoInfo> getLatestVideo(){
		ArrayList<ContentVideoInfo> result = new ArrayList<ContentVideoInfo>();
		Connection conn = DSManager.getConnection();
	
		ContentDAO dao = new ContentDAO();
		
		try{
																	// RUNNING QUERY
			PreparedStatement stmt = conn.prepareStatement(SELECT_LATEST_5_VIDEO__CONTENT);

			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				ContentVideoInfo item = new ContentVideoInfo();
				Long contentID = rs.getLong(1);
				item = dao.getContentVideo(contentID);
				result.add(item);
			}
		}catch (Exception e){
			log.error("PublicDAO", "getLatestVideo", e.toString());
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
	private BannerInfo getBanner(Long bannerID){
		ArrayList result = new ArrayList();
		Connection conn = DSManager.getConnection();
		
		try{
																	// RUNNING QUERY
			PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID__BANNNER);
			stmt.setLong(1, bannerID);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				BannerInfo item = new BannerInfo();
				item.setId(rs.getLong(1));
				item.setUrl(Utils.checkNull(rs.getString(2)));
				item.setImage(Utils.checkNull(rs.getString(3)));
				item.setTarget(Utils.checkNull(rs.getString(4)));
				
				
				return item;
			}
		}catch (Exception e){
			log.error("PublicDAO", "getBanner", e.toString());
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

		return null;
	}

	/**
	 * Get Features Data.
	 * 
	 * @param featuresID
	 * @return List of features
	 */
	private ArrayList getFeatures(Long featuresID){
		ArrayList result = new ArrayList();
		Connection conn = DSManager.getConnection();
		
		try{
																	// RUNNING QUERY
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
				item.setContentID(rs.getLong("content_id"));
				result.add(item);
			}
		}catch (Exception e){
			log.error("PublicDAO", "getFeatures", e.toString());
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
	 * Generate banner html for Homepage sidebar.
	 * 
	 * @return html
	 */
	public String generateBannerHTML_ForHomepageSidebar(){
		String result = "";
		
		for (long i=9; i<=11; i++){
			BannerInfo banner = getBanner(i);
			if (banner!=null)
				result += HTML.generateBanner("", banner);
		}
		
		return result;
	}
	
	/**
	 * Generate banner html for Homepage bottom.
	 * 
	 * @return html
	 */
	public String generateBannerHTML_ForHomepageBottom(){
		String result = "";
		
		result += "<div " + HTML.attr("class", "row banners-row")+">";
		
		for (long i=1; i<=4; i++){
			BannerInfo banner = getBanner(i);
			
			if (banner!=null)
				result += HTML.generateBanner("large-4 medium-4 columns", banner);
		}
		
		result += "</div>";
		
		result += "<div " + HTML.attr("class", "row banners-row")+">";
		
		for (long i=5; i<=8; i++){
			BannerInfo banner = getBanner(i);
			if (banner!=null)
				result += HTML.generateBanner("large-4 medium-4 columns", banner);
		}
		
		result += "</div>";
		
		return result;
	}
	
	/**
	 * Generate banner html for Internal page sidebar.
	 * 
	 * @return html
	 */
	public String generateBannerHTML_ForInternalSidebar(){
		String result = "";
		
		for (long i=16; i<=19; i++){
			BannerInfo banner = getBanner(i);
			if (banner!=null)
				result += HTML.generateBanner("", banner);
		}
		
		return result;
	}
	
	/**
	 * Generate banner html for Internal bottom.
	 * 
	 * @return html
	 */
	public String generateBannerHTML_ForInternalBottom(){
		String result = "";
		
		result += "<div " + HTML.attr("class", "row banners-row")+">";
		
		for (long i=12; i<=15; i++){
			BannerInfo banner = getBanner(i);
			if (banner!=null)
				result += HTML.generateBanner("large-4 medium-4 columns", banner);
		}
		
		result += "</div>";
		
		return result;
	}
	

	
	/**
	 * Get Content News.
	 * 
	 * @return List of News
	 */
	public JSONArray getLatestNews(){
		JSONArray result = new JSONArray();
		String today = Utils.getToday("-");
		Connection conn = DSManager.getConnection();
		
		try{
			PreparedStatement stmt = conn.prepareStatement(SELECT_LATEST_2_NEWS__CONTENT);
			stmt.setString(1, today);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				ContentInfo item = new ContentInfo();
				item.setId(rs.getLong("id"));
				item.setType(rs.getString("type"));
				item.setShowIn(rs.getString("show_in"));
				item.setName(rs.getString("name"));
				item.setValidityStart(rs.getString("validity_start"));
				item.setValidityEnd(rs.getString("validity_end"));
				item.setActive(rs.getString("active"));
				item.setStatus(rs.getString("status"));
				item.setCreatedAt(rs.getString("created_at"));
				item.setUpdatedAt(Utils.toDisplayDate(rs.getString("updated_at")).substring(0, 10));
				item.setId(rs.getLong("id"));
				item.setContent(rs.getString("content"));
				item.setImage(rs.getString("image"));
				item.setBlog(rs.getString("blog"));
				item.setHtml(Utils.checkNull(rs.getString("html")));
				
				result.put(item.toJSONObject());
			}
		}catch (Exception e){
			log.error("PublicDAO", "getLatestNews", e.toString());
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
	 * Affect Latest news to featured data.
	 */
	public void affectFeaturedNews(){
		Connection conn = DSManager.getConnection();
		ArrayList list =new ArrayList();//= getLatestNews();
		if (list.size()==0){
			try{
																	// RUNNING QUERY
				PreparedStatement stmt = conn.prepareStatement(" update bbva_features set content_id=0 ");
				stmt.executeUpdate();
			}catch (Exception e){ }
			
			
			return;
		}
		
		ContentInfo f = (ContentInfo)list.get(0);
		
		String date = Utils.toDisplayDate(f.getUpdatedAt());
		
		try{
																	// RUNNING QUERY
			PreparedStatement stmt = conn.prepareStatement(" update bbva_features set image=?, title=?, date=?, content=?, content_id=? where sub_id=1 ");
			stmt.setString(1, f.getImage());
			stmt.setString(2, f.getName());
			stmt.setString(3, date);
			stmt.setString(4, f.getContent());
			stmt.setLong(5, f.getId());
			stmt.executeUpdate();
		}catch (Exception e){ }
		
		if (list.size()>1){
			f = (ContentInfo)list.get(1);
			date = Utils.toDisplayDate(f.getUpdatedAt());
			
			try{
																	// RUNNING QUERY
				PreparedStatement stmt = conn.prepareStatement(" update bbva_features set image=?, title=?, date=?, content=?, content_id=? where sub_id=2 ");
				stmt.setString(1, f.getImage());
				stmt.setString(2, f.getName());
				stmt.setString(3, date);
				stmt.setString(4, f.getContent());
				stmt.setLong(5, f.getId());
				stmt.executeUpdate();
			}catch (Exception e){ }
			
		}else{
			try{
																	//	RUNNING QUERY	
				PreparedStatement stmt = conn.prepareStatement(" update bbva_features set content_id=0 where sub_id=2 ");
				stmt.executeUpdate();
			}catch (Exception e){ }
			finally
			{
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * GENERATE SLIDER FROM CAROUSEL VIDEO
	 * @return
	 */
	public String[] generateSlide(){
		
		
		String[] result = {"", "", "", ""};
		ArrayList<?> list = getLatestVideo();
		
		for (int i=0; i<list.size(); i++){
			ContentVideoInfo video = (ContentVideoInfo)list.get(i);
			result[0] += "<div><h3>";
			result[0] += "<object id=\"content_video_"+i+"\" type=\"application/x-shockwave-flash\" " +  
				 "data=\"/resources/swf/playerLite.swf\" " + 
				 "width=\"473\" height=\"245\" style=\"visibility: visible; width:473px; height:245px;\">" +
				 "<param name=\"menu\" value=\"true\">"+
				 "<param name=\"allowfullscreen\" value=\"true\">"+
				 "<param name=\"allowscriptaccess\" value=\"always\">"+
				 "<param name=\"flashvars\" value=\"vidWidth=473&vidHeight=245&vidPath="+HTML.media("video", video.getVideo())+"&thumbPath="+Constants.BASEPATH+"serve?blob-key="+video.getImage()+"&plShowAtStart=true&plHideDelay=2000&autoPlay=false&autoLoop=true&vidAspectRatio=fit&seekbar=show\"></object>" ;

			result[0] += "<embed " + HTML.attr("src", HTML.media("video", video.getVideo())) + " " + HTML.attr("name", "plugin") + " " + HTML.attr("type", "application/x-vlc-plugin") + " " + HTML.attr("style", "width: 100%; height:270px;") + ">";
			result[0] += "<video " + HTML.attr("src", HTML.media("video", video.getVideo())) + " controls " + HTML.attr("style", "width: 100%; height:230px;") + "></video>";
			
			result[0] += "</h3></div>";
			result[1] += "<div><h3><img " + HTML.attr("src", HTML.media("image", video.getImage())) + " " + HTML.attr("alt", "") + " " + HTML.attr("style", "width:113px; height:54px;") + "></h3></div>";
			
			result[2] += "<div><h3>";
			result[2] += "<video id=\"content_video_"+i+"\" " + HTML.attr("src", HTML.media("video", video.getVideo())) + " controls " + HTML.attr("style", "width: 100%; height:auto;") + "></video>";
			result[2] += "</h3></div>";
			result[3] += "<div><h3><img " + HTML.attr("src", HTML.media("image", video.getImage())) + " " + HTML.attr("alt", "") + " " + HTML.attr("style", "width:113px; height:54px;") + "></h3></div>";
						
		}
				
		return result;
	}
	
	
	///////////////////////
	
	
public String[] generateSlideMozilla(){
		
		String htmlvideo1="";
		String htmlvideo2="";
		String htmlvideo3="";
		String htmlvideo4="";
		String htmlvideo5="";
		
		String[] result = {"", "", "", ""};
		ArrayList<?> list = getLatestVideo();
		
		for (int i=0; i<list.size(); i++){
			ContentVideoInfo video = (ContentVideoInfo)list.get(i);
//			result[0] += "<div id='oculto1' style='display:none;' ><h3>";
//			result[0] += "<li><img src="+HTML.attr("src", HTML.media("image", video.getImage()))+" />";
			result[0] += "<video " + HTML.attr("src", HTML.media("video", video.getVideo())) + " controls " + HTML.attr("style", "width: 100%; height:230px;") + "></video>";
			
//			result[0] += "</h3></div>";
			result[1] += "<div><h3><img " + HTML.attr("src", HTML.media("image", video.getImage())) + " " + HTML.attr("alt", "") + " " + HTML.attr("style", "width:113px; height:54px;") + "></h3></div>";
			
			result[2] += "<div><h3>";
			result[2] += "<video id=\"content_video_"+i+"\" " + HTML.attr("src", HTML.media("video", video.getVideo())) + " controls " + HTML.attr("style", "width: 100%; height:auto;") + "></video>";
			result[2] += "</h3></div>";
			result[3] += "<div><h3><img " + HTML.attr("src", HTML.media("image", video.getImage())) + " " + HTML.attr("alt", "") + " " + HTML.attr("style", "width:113px; height:54px;") + "></h3></div>";
			
			if(i == 0)
			{
				htmlvideo1 = result[0];
				try {
					String resul="";
					resul = htmlvideo1.replaceAll("style='display:none;'", "style='display:inline;'");
					System.out.println(htmlvideo1);
					insertvideo(1, resul);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("Error no lograste guardar sql");
				}
			}
			else if(i == 1)
			{
				htmlvideo2="";
				htmlvideo2 = result[0];
				try {
					String resul="";
					resul = htmlvideo2.replaceAll("oculto1", "oculto2");
					insertvideo(2, resul);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(i == 2)
			{
				htmlvideo3="";
				htmlvideo3 = result[0];
				try {
					String resul="";
					resul = htmlvideo3.replaceAll("oculto1", "oculto3");
					insertvideo(3, resul);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(i == 3)
			{
				htmlvideo4="";
				htmlvideo4 = result[0];
				try {
					String resul="";
					resul = htmlvideo4.replaceAll("oculto1", "oculto4");
					insertvideo(4, resul);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(i == 4)
			{
				String resul="";
				htmlvideo5="";
				htmlvideo5 = result[0];
				try {
					resul = htmlvideo5.replaceAll("oculto1", "oculto5");
					insertvideo(5, resul);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			result[0]="";
			
			
		}
		
		
		
		return result;
	}

//////////////////////////////
	
	
	/**
	 * Get Option HTML.
	 * 
	 * @param kind Banner or Featured News
	 * @return html
	 */
	public String getOptionHTML(String kind){
		String result = "";
		Connection conn = DSManager.getConnection();
		
		try{
																	// 	RUNNING QUERY
			PreparedStatement stmt = conn.prepareStatement(SELECT_WIDGET_HTML__CONFIG);
			stmt.setString(1, kind);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				result =rs.getString("HomeHtml");
			}
		}catch (Exception e){
			log.error("PublicDAO", "getWidgetHTML", e.toString());
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
	 * Get Channel Data by FamilyID, ParentID.
	 * 
	 * @param familyID
	 * @param parentID
	 * @return list
	 */
	public ArrayList<ChannelInfo> getChannelList(Long familyID, Long parentID) throws SQLException{
		ArrayList<ChannelInfo> result = new ArrayList<ChannelInfo>();
		Connection conn1 = DSManager.getConnection();
		try{
													// RUNNING QUERY
			
			
			
			PreparedStatement stmt = conn1.prepareStatement(SELECT_BY_FAMILY_PARENT_ID__CHANNEL);
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
			log.error("PublicDAO", "getChannelList", e.toString());
		}
		finally
		{
			try {
				conn1.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;
	}
	
	/**
	 * Get Channel Data by ParentID.
	 * 
	 * @param parentID
	 * @return list
	 */
	public ArrayList<ChannelInfo> getChannelList(Long parentID){
		ArrayList<ChannelInfo> result = new ArrayList<ChannelInfo>();
		
		Connection conn1 = DSManager.getConnection();
		
		try{
																	// RUNNING QUERY
			PreparedStatement stmt = conn1.prepareStatement(SELECT_BY_PARENT_ID__CHANNEL);
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
			log.error("PublicDAO", "getChannelList", e.toString());
		}
		finally
		{
			try {
				conn1.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;
	}	
	
	
	/**
	 * Check security level between channel and user.
	 * 
	 * @param level1
	 * @param level2
	 * @return boolean
	 */
	private boolean checkSecurityLevel(int level1, String level2){
		if (level1 >0 && level1 != 0){
			if (level1 == 1  && (level2.equals(1) || level2.equals(2) || level2.equals(3) || level2.equals(4)) )
				return true;
			
			if (level1 == 2 && (level2.equals(2) || level2.equals(3) || level2.equals(4)) )
				return true;

			if (level1 == 3  && (level2.equals(3) || level2.equals(4)) )
				return true;
			
			if (level1 == 4 && (level2.equals(4)) )
				return true;
		}else{
			return true;
		}
			
		return false;
	}
	
	/**
	 * Check options between channel and user.
	 * 
	 * @param user
	 * @param channel
	 * @return boolean
	 */
	private boolean checkOption(String user, String channel){
		if (user!=null && user.length()>0 && !user.equals("0")){
			if (user.equals(channel))
				return true;
		}else{
			return true;
		}
			
		return false;
	}
	
	/**
	 * Check whether exist or not.
	 *
	 * @param table
	 * @param channelID
	 * @param value
	 * @param type Divison or Sub-Division
	 * @return boolean
	 */
	private boolean isInCodeTable(String table, Long channelID, Long value, String type){
		Connection conn = DSManager.getConnection();
		try{
			long result = 0;
																	// RUNNING QUERY
			PreparedStatement stmt = conn.prepareStatement(" select count(*) from " + table + " where type=? and Channel_ChannelId=? and " + table + "ChannelcityId=? ");
			stmt.setString(1, type);
			stmt.setLong(2, channelID);
			stmt.setLong(3, value);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				result = rs.getLong(1);
			}
			
			return result>0 ? true : false;
		}catch (Exception e){
			log.error("PublicDAO", "isInCodeTable", e.toString());
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
	 * Check whether exist or not.
	 *
	 * @param table
	 * @param channelID
	 * @param i
	 * @return boolean
	 */
	private boolean isInCodeTable(String table, Long channelID, int i){
		
		PreparedStatement stmt = null;
		try{
			long result = 0;
			if(table.equals("channelcity"))
			{
				// RUNNING QUERY
				stmt = conn.prepareStatement(" select count(*) from " + table + " where Channel_ChannelId=? and City_CityId=? ");
			}
			if(table.equals("channelcompany"))
			{
				// RUNNING QUERY
				stmt = conn.prepareStatement(" select count(*) from " + table + " where Channel_ChannelId=? and Company_CompanyId=? ");
			}
			if(table.equals("channelmaindirection"))
			{
				// RUNNING QUERY
				stmt = conn.prepareStatement(" select count(*) from " + table + " where Channel_ChannelId=? and Maindirection_MaindirectionId=? ");
			}
			
			stmt.setLong(1, channelID);
			stmt.setInt(2, i);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				result = rs.getLong(1);
			}
			
			return result>0 ? true : false;
		}catch (Exception e){
			log.error("PublicDAO", "isInCodeTable", e.toString());
		}

		return false;
	}
	
	/**
	 * Check whether exist or not.
	 *
	 * @param table
	 * @param channelID
	 * @param value
	 * @return boolean
	 */
	private boolean isInCodeTable(String table, Long channelID, Long value){
		try{
			long result = 0;
			PreparedStatement stmt = null;
			if(table.equals("channelcity"))
			{
				// RUNNING QUERY
				stmt = conn.prepareStatement(" select count(*) from " + table + " where Channel_ChannelId=? and City_CityId=? ");
			}
			if(table.equals("channelcompany"))
			{
				// RUNNING QUERY
				stmt = conn.prepareStatement(" select count(*) from " + table + " where Channel_ChannelId=? and Company_CompanyId=? ");
			}
			if(table.equals("channelmaindirection"))
			{
				// RUNNING QUERY
				stmt = conn.prepareStatement(" select count(*) from " + table + " where Channel_ChannelId=? and Maindirection_MaindirectionId=? ");
			}
			stmt.setLong(1, channelID);
			stmt.setLong(2, value);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				result = rs.getLong(1);
			}
			
			return result>0 ? true : false;
		}catch (Exception e){
			log.error("PublicDAO", "isInCodeTable", e.toString());
		}

		return false;
	}
	
	/**
	 * IS EMPTY CODE TABLE
	 * @param table
	 * @param channelID
	 * @return
	 */
	private boolean isEmptyCodeTable(String table, Long channelID){
		Connection conn = null;
		
		
		
		try{
			conn = DataSource.getInstance().getConnection();
			long result = 0;
			PreparedStatement stmt = null;
			if(table.equals("channelcity"))
			{
				// RUNNING QUERY
				stmt = conn.prepareStatement(" select count(*) from " + table + " where Channel_ChannelId=? ");
			}
			if(table.equals("channelcompany"))
			{
				// RUNNING QUERY
				stmt = conn.prepareStatement(" select count(*) from " + table + " where Channel_ChannelId=? ");
			}
			if(table.equals("channelmaindirection"))
			{
				// RUNNING QUERY
				stmt = conn.prepareStatement(" select count(*) from " + table + " where Channel_ChannelId=? ");
			}
			stmt.setLong(1, channelID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				result = rs.getLong(1);
			}
			
			return result==0 ? true : false;
		}catch (Exception e){
			log.error("PublicDAO", "isEmptyCodeTable", e.toString());
		}
		finally
		{
			if(conn != null)try{conn.close();}catch (SQLException e){e.printStackTrace();}
		}

		return false;
	}
	
	/**
	 * Check whether valid channel or not.
	 * 
	 * @param channel
	 * @param user
	 * @return boolean
	 */
	private boolean checkCondition(ChannelInfo channel, UserInfo user){
		
		if (user.getUserRol()!=null && user.getUserRol().equals("1"))		// admin user
			return true;
		
		
		if (user.getUserId() == 0){
			boolean isCiudad = isEmptyCodeTable("channelcity", channel.getId());
			boolean isEmpresa = isEmptyCodeTable("channelcompany", channel.getId());
			boolean isDireccion = isEmptyCodeTable("channelmaindirection", channel.getId());
			
			if (isCiudad && isEmpresa && isDireccion)
				return true;
		}else{
			boolean isAccessLevel=false;

				isAccessLevel = true;


			boolean isSecurityLevel = checkSecurityLevel(user.getUserAccessLevel(), channel.getSecurityLevel());
			boolean isCiudad = user.getCity()!=(long)0 ? isEmptyCodeTable("channelcity", channel.getId()) || isInCodeTable("channelcity", channel.getId(), user.getCity()) : isEmptyCodeTable("channelcity", channel.getId());
			boolean isEmpresa = user.getCompany()!=(long)0 ? isEmptyCodeTable("channelcompany", channel.getId()) || isInCodeTable("channelcompany", channel.getId(), user.getCompany()) : isEmptyCodeTable("channelcompany", channel.getId());
			boolean isDireccion = user.getMainDirection()!=(long)0 ? isEmptyCodeTable("channelmaindirection", channel.getId()) || isInCodeTable("channelmaindirection", channel.getId(), user.getMainDirection()) : isEmptyCodeTable("channelmaindirection", channel.getId());
			
		
			
			if (isAccessLevel && isSecurityLevel && isCiudad && isEmpresa && isDireccion)
				return true;
		}
		
		return false;
	}
	
	/**
	 * Get All Channel Data by FamilyID, ParentID.
	 * 
	 * @param familyID
	 * @param parentID
	 * @param user
	 * @return list
	 */
	public JSONArray recallChannelList(Long familyID, Long parentID, UserInfo user){
		JSONArray result = new JSONArray();
		try{
			ArrayList<?> list = getChannelList(familyID, parentID);
			for (int i=0; i<list.size(); i++){
				ChannelInfo item = (ChannelInfo) list.get(i);
				if (checkCondition(item, user)){
					JSONObject obj = new JSONObject();
					obj.put("parent", item.toJSONObject());
					obj.put("child", recallChannelList(familyID, item.getId(), user));
					result.put(obj);
					
				}
			}
			
		}catch (Exception e){
			log.error("PublicDAO", "recallChannelList", e.toString());
		}

		return result;
	}


	/**
	 * GENERATE FEATURE HTML 
	 * THIS METOD IS NEVER USE
	 * @return
	 */
	public String generateFeaturesHTML() {
		// TODO Auto-generated method stub
		String result = "";
		// RUNNING QUERY
		Long templateID = Utils.getLong(getOptionHTML(Constants.OPTION_FEATURES));
		
		ArrayList list = getFeatures(templateID);
		if (list.size()==0)
			return result;
		
		FeaturesInfo f = (FeaturesInfo)list.get(0);
		String link = "#";
		if (f.getContentID()!=0){
			ContentInfo a =  getContent(f.getContentID());
			if (a!=null)
				link = "javascript:content('"+a.getId()+"','"+a.getBlog()+"')";
		}
		
		FeaturesInfo f1 = null;
		if (list.size()>1)
			f1 = (FeaturesInfo)list.get(1);
		
		switch (templateID.intValue()){
		case 1:
			result += "<div " + HTML.attr("class", "large-16 medium-16 columns") + ">";
			result += HTML.generateFeatures_Image(f.getImage());
			result += HTML.generateFeatures_Title(f.getTitle(), link);
			result += HTML.generateFeatures_Date(f.getDate());
			result += HTML.generateFeatures_Content(HTML.replaceVideo(f.getContent(), f.getImage()));
			result += HTML.generateFeatures_More(link);
			result += "</div>";
			break;
			
		case 2:
			result += "<div " + HTML.attr("class", "large-6 medium-7 columns") + ">";
			result += HTML.generateFeatures_Image(f.getImage());
			result += "</div>";
			
			result += "<div " + HTML.attr("class", "large-10 medium-9 columns") + ">";
			result += HTML.generateFeatures_Title(f.getTitle(), link);
			result += HTML.generateFeatures_Date(f.getDate());
			result += HTML.generateFeatures_Content(HTML.replaceVideo(f.getContent(), f.getImage()));
			result += HTML.generateFeatures_More(link);
			result += "</div>";
			break;
			
		case 3:
			result += "<div " + HTML.attr("class", "large-10 medium-9 columns") + ">";
			result += HTML.generateFeatures_Title(f.getTitle(), link);
			result += HTML.generateFeatures_Date(f.getDate());
			result += HTML.generateFeatures_Content(HTML.replaceVideo(f.getContent(), f.getImage()));
			result += HTML.generateFeatures_More(link);
			result += "</div>";
			
			result += "<div " + HTML.attr("class", "large-6 medium-7 columns") + ">";
			result += HTML.generateFeatures_Image(f.getImage());
			result += "</div>";
			break;
			
		case 4:
			result += "<div " + HTML.attr("class", "large-16 medium-16 columns") + ">";
			result += HTML.generateFeatures_Title(f.getTitle(), link);
			result += HTML.generateFeatures_Date(f.getDate());
			result += HTML.generateFeatures_Content(HTML.replaceVideo(f.getContent(), f.getImage()));
			result += HTML.generateFeatures_More(link);
			result += HTML.generateFeatures_Image(f.getImage());
			result += "</div>";
			break;
			
		case 5:
			if (f1!=null)
				result += "<div " + HTML.attr("class", "large-8 medium-8 columns") + ">";
			else
				result += "<div " + HTML.attr("class", "large-16 medium-16 columns") + ">";
			
			result += HTML.generateFeatures_Image(f.getImage());
			result += HTML.generateFeatures_Title(f.getTitle(), link);
			result += HTML.generateFeatures_Date(f.getDate());
			result += HTML.generateFeatures_Content(HTML.replaceVideo(f.getContent(), f.getImage()));
			result += HTML.generateFeatures_More(link);
			result += "</div>";

			if (f1!=null){
				link = "#";
				ContentInfo ab =  getContent(f1.getContentID());
				if (ab!=null)
					link = "javascript:content('"+ab.getId()+"','"+ab.getBlog()+"')";
				
				result += "<div " + HTML.attr("class", "large-8 medium-8 columns") + ">";
				result += HTML.generateFeatures_Image(f1.getImage());
				result += HTML.generateFeatures_Title(f1.getTitle(), link);
				result += HTML.generateFeatures_Date(f1.getDate());
				result += HTML.generateFeatures_Content(HTML.replaceVideo(f1.getContent(), f1.getImage()));
				result += HTML.generateFeatures_More(link);
				result += "</div>";
			}
			break;
		}
		
		return result;
	}	

	
	/**
	 * Get All Family Data.
	 * 
	 * @return list
	 */
	public ArrayList<FamilyInfo> getFamilyList(){
		ArrayList<FamilyInfo> result = new ArrayList<FamilyInfo>();
		Connection conn = DSManager.getConnection();
		try{
			// RUNNING QUERY
			PreparedStatement stmt = conn.prepareStatement(SELECT_FAMILY);
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
			log.error("PublicDAO", "getFamilyList", e.toString());
		}finally
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
	 * Get Most visited Channel.
	 * 
	 * @return list
	 */
	public ArrayList getMostVisitedChannel(UserInfo user){
		ArrayList result = new ArrayList();
		Connection conn = DSManager.getConnection();
		int count=0;
		try{
			// RUNNING QUERY
			String sql = "select v.Content_ContentId as ContenidoId,\n" +
					"  count(v.Content_ContentId) as VisitasContenido,\n" +
					"  c.ContentName as Contenido,\n" +
					"  ch.ChannelId,\n" +
					"  ch.Family_FamilyId,\n" +
					"  ch.ChannelName,\n" +
					"  ch.ChannelEmail,\n" +
					"  ch.ChannelPassword,\n" +
					"  ch.ChannelIsVisible,\n" +
					"  ch.ChannelSecurityLevel\n" +
					"FROM user a\n" +
					"  INNER JOIN visit v ON a.UserId = v.User_UserId\n" +
					"  INNER JOIN content c ON c.ContentId = v.Content_ContentId\n" +
					"  INNER JOIN channel ch ON c.Channel_ChannelId = ch.ChannelId\n" +
					"  WHERE c.ContentStatus='A' and ch.ChannelIsVisible = 1 GROUP BY ch.ChannelId  ORDER BY 6";
				
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				if (count>4)
					break;
				
				ChannelInfo item = new ChannelInfo();
				item.setId(rs.getLong(4));
				item.setFamilyID(rs.getLong(5));
				item.setName(rs.getString(6));
				item.setEmail(rs.getString(7));
				item.setPassword(rs.getString(8));
				item.setAccessLevel(rs.getInt(9));
				item.setSecurityLevel(rs.getString(10));

				if (checkCondition(item, user)){
					item.setNo(count+1);
					result.add(item);
					count++;
				}
			}
		}catch (Exception e){
			log.error("PublicDAO", "getMostVisitedChannel", e.toString());
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
	 * GET CONTENT BY SQL 
	 * @param sql
	 * @return
	 */
	public JSONArray getContent(String sql){
		JSONArray result = new JSONArray();
		Connection conn = DSManager.getConnection();
		try{
			Long contentID = (long)0;
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				JSONObject item = new JSONObject();
				
				Long cID = rs.getLong("id");
				if (contentID!=cID){
					item.put("id", cID);
					item.put("name", rs.getString("name"));
					item.put("blog", Utils.checkNull(rs.getString("blog")));
					item.put("media", Utils.checkNull(rs.getString("media")));
					result.put(item);
					contentID = cID;
				}
			}
		}catch (Exception e){
			log.error("PublicDAO", "getContent", e.toString());
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
	 * GET CONTENT GALLERY BY CONTENTID
	 * @param contentID
	 * @return
	 */
	public ArrayList getContentGallery(Long contentID){
		ArrayList result = new ArrayList();
		Connection conn = DSManager.getConnection();
		
		try{
																	// RUNNING QUERY
			PreparedStatement stmt = conn.prepareStatement(SELECT_MEDIAID);
			stmt.setLong(1, contentID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				ContentGalleryMediaInfo item = new ContentGalleryMediaInfo();
				item.setId(rs.getLong("MediaId"));
				item.setContentID(contentID);
				item.setName(rs.getString("MediaName"));
				item.setMedia(Utils.checkNull(rs.getString("MediaContent")));
				result.add(item);
			}
		}catch (Exception e){
			log.error("PublicDAO", "getContentGallery", e.toString());
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
	 * GET FIRST IMAGEN GALERRY BY CONTENT ID
	 * @param contentid
	 * @return
	 */
	public String getGalleryFirstImage(Long contentid)
	{
		Connection conn = DSManager.getConnection();
		String media = "";
		try
		{
																	// RUNNING QUERY
			PreparedStatement stmt = conn.prepareStatement(SELECTMEDIACONTENT);
			stmt.setLong(1, contentid);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				media = rs.getString("MediaContent");
			}

		} catch (Exception e) {
			log.error("PublicDao","getGalleryFirstImage",e.toString());
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
		return media;
	}
	
	
	public JSONArray getContentT(String sql){
		JSONArray result = new JSONArray();
																	// RUNNING QUERY
		Connection conn = DSManager.getConnection();
		String sql2 = "select ContentId,ContentName from content where Contenttype_ContenttypeId = '3' and ContentEndDate >='" + Utils.getToday("-") + "' order by ContentId desc";
		try{
			Long contentID = (long)0;
			PreparedStatement stmt = conn.prepareStatement(sql2);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				JSONObject item = new JSONObject();
				
				Long cID = rs.getLong("Contentid");
				if (contentID!=cID){
					item.put("id", cID);
					item.put("name", rs.getString("Contentname"));
					item.put("blog","blog");
					item.put("media", Utils.checkNull(getGalleryFirstImage(cID)));
					result.put(item);
					contentID = cID;
				}
			}
		}catch (Exception e){
			log.error("PublicDAO", "getContent", e.toString());
		}finally
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
	 * GET CONTENT BY SQL
	 * @param sql
	 * @return
	 */
	public Long getCountT(String sql){
		Long result = (long)0;
		Connection conn = DSManager.getConnection();
																	// RUNNING QUERY
		String sql2 = "select count(ContentId) from content where Contenttype_ContenttypeId = '3' and ContentEndDate >= '" + Utils.getToday("-") + "'";
		try{
			PreparedStatement stmt = conn.prepareStatement(sql2);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				result = rs.getLong(1);
			}
		}catch (Exception e){
			log.error("PublicDAO", "getCountT", e.toString());
			result = (long)0;
		}finally
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
	
	@SuppressWarnings("null")
	public void  htmlvarrucel_(String html1, String html2, String html3, String html4, String html5)
	{
		ArrayList<String> list = null ;
		list.add(html1);
		list.add(html2);
		list.add(html3);
		list.add(html4);
		list.add(html5);
		
//		for(int a = 0; a < 5 ; a++)
//		{
//			try {
//				insertvideo(list.get(a));
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		
				
	}
	
	public void insertvideo(int id, String html) throws Exception {
		// RUNNING QUERY
		//insert into html (html) values (?)";
		String sql =" update html set htmlvideo = ? where id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, html);
		stmt.setInt(2, id);
		stmt.execute();
		
	}
	
	public String VideoMozillaselect (int id)
	{
		//select htmlvideo from html where id=1
		String result = "";
		// RUNNING QUERY
		String sql = "select htmlvideo from html where id=?";
		Connection conn = DSManager.getConnection();
		try 
		{
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				result = rs.getString("htmlvideo");
			}
		} 
		catch (Exception e) 
		{
			log.error("PublicDAO", "getCountT", e.toString());
			result = null;
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
	
	public ArrayList descarga (String channel_id)
	{
		int channel = Integer.parseInt(channel_id);
		Connection conn = DSManager.getConnection();
		ArrayList<String> lista = new ArrayList ();
		//select htmlvideo from html where id=1
		String result = "";
		// RUNNING QUERY
		String sql = "select ContentHtml from content where Contenttype_ContenttypeId = 4 and Channel_ChannelId=?";
		try 
		{
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, channel);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				lista.add(rs.getString("ContentHtml"));
			}
		} 
		catch (Exception e) 
		{
			log.error("PublicDAO", "getCountT", e.toString());
			result = null;
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

		return lista;
	}
	
}
