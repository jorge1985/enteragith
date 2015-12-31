package com.youandbbva.enteratv.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import com.google.appengine.tools.admin.Utility;
import com.youandbbva.enteratv.Constants;
import com.youandbbva.enteratv.DAO;
import com.youandbbva.enteratv.DSManager;
import com.youandbbva.enteratv.HTML;
import com.youandbbva.enteratv.Utils;
import com.youandbbva.enteratv.domain.ChannelInfo;
import com.youandbbva.enteratv.domain.CodeInfo;
import com.youandbbva.enteratv.domain.ContentAnswerInfo;
import com.youandbbva.enteratv.domain.ContentFAQsInfo;
import com.youandbbva.enteratv.domain.ContentFileInfo;
import com.youandbbva.enteratv.domain.ContentFileMediaInfo;
import com.youandbbva.enteratv.domain.ContentGalleryInfo;
import com.youandbbva.enteratv.domain.ContentGalleryMediaInfo;
import com.youandbbva.enteratv.domain.ContentInfo;
import com.youandbbva.enteratv.domain.ContentNewsInfo;
import com.youandbbva.enteratv.domain.ContentQuestionAnswerInfo;
import com.youandbbva.enteratv.domain.ContentQuestionInfo;
import com.youandbbva.enteratv.domain.ContentVideoInfo;
import com.youandbbva.enteratv.domain.ContentCountInfo;


/**
 * 
 * Handle all query for Content.
 * 		content
 * 
 * @author CJH
 *
 */

@Repository("ContentDAO")
public class ContentDAO extends DAO{


	/**
	 * SQL queries
	 */
	private final String TABLE_NAME_CONTENT = "content";
	private final String TABLE_NAME_LABEL = "label";
	private final String TABLE_NAME_CONTENTLABEL = "contentlabel";
	private final String TABLE_NAME_CONTENTMEDIA = "contentmedia";
	private final String TABLE_NAME_CHANNEL = "channel";
	private final String TABLE_NAME_CONTENTTYPE = "contenttype";
	private final String TABLE_NAME_MEDIA = "media";
	private final String COLUMN_CONTENT_SELECT = "c.ContentId,c.Channel_ChannelId,c.Contenttype_ContenttypeId,c.ContentName,c.ContentHtml,c.ContentIsVisible," 
            + "c.ContentPublishDate,c.ContentEndDate,c.ContentStatus,c.ContentShowView";
	private final String COLUMN_CONTENT_SELECT2 = "c.ContentId,c.Channel_ChannelId,c.Contenttype_ContenttypeId,t.ContenttypeName,c.ContentName,c.ContentHtml,c.ContentIsVisible," 
            + "c.ContentPublishDate,c.ContentEndDate,c.ContentStatus,c.ContentShowView";
	private final String COLUMN_CONTENT_INSERT = "Channel_ChannelId,Contenttype_ContenttypeId,ContentName,ContentHtml,ContentIsVisible,"
                                                 + "ContentPublishDate,ContentEndDate,ContentStatus,ContentShowView";
	private final String COLUMN_CONTENT_UPDATE = "Channel_ChannelId = ?,Contenttype_ContenttypeId=?,ContentName=?,ContentHtml=?,ContentIsVisible=?," 
                                                 + "ContentPublishDate=?,ContentEndDate=?,ContentStatus=?,ContentShowView=?";
	private final String COLUMN_CONTENT_HTML_UPDATE = "ContentHTML = ?";
	private final String COLUMN_CONTENT_COUNT = "count(ContentId)";
	private final String COLUMN_LABEL_COUNT_BY_ID = "count(LabelId)";
	private final String COLUMN_CONTENT_UPDATE_DESCRIPTION = " ContentDescription = ?";
	private final String COLUMN_CONTENTLABEL_SELECT = "ContentlabelId,Content_ContentId,Label_labelId";
	private final String COLUMN_CONTENTLABEL_INSERT = "Content_ContentId,Label_labelId";
	private final String COLUMN_CONTENTMEDIA_SELECT = "ContentmediaId,Content_ContentId,Media_MediaId";
	private final String COLUMN_CONTENTMEDIA_INSERT = "Content_ContentId,Media_MediaId";
	private final String COLUMN_CHANNEL_SELECT = "h.ChannelId,h.Family_FamilyId,h.ChannelName,h.ChannelPosition,h.ChannelFather,h.ChannelEmail,h.ChannelPassword,h.ChannelSecurityLevel,h.ChannelIsVisible";
	private final String COLUMN_MEDIA_SELECT = "m.MediaName, m.MediaContent, m.Filetype_FiletypeId, m.MediaId";
	private final String COLUMN_LABEL_SELECT = "LabelId,LabelText";
	private final String COLUMN_LABEL_SELECT_COUNT_BY_ID = "count(LabelId)";
	private final String COLUMN_LABEL_INSERT = "LabelText";
	private final String SELECT_LABEL_BY_TEXT = "select " + COLUMN_LABEL_SELECT + " from " + TABLE_NAME_LABEL + " where LabelText = ?";
	private final String SELECT_LABEL_BY_ID   = "select " + COLUMN_LABEL_SELECT + " from " + TABLE_NAME_LABEL + " where LabelId =?";
	private final String SELECT_LABEL_BY_ID_DF   = "select " + COLUMN_LABEL_SELECT_COUNT_BY_ID + " from " + TABLE_NAME_LABEL + " where LabelId <> ?";
	private final String COUNT_LABEL_BY_TEXT   = "select " + COLUMN_LABEL_COUNT_BY_ID + " from " + TABLE_NAME_LABEL + " where LabelText = ?";
	private final String INSERT_LABEL         = "insert into " + TABLE_NAME_LABEL + "(" + COLUMN_LABEL_INSERT + ") values (?)";
	private final String DELETE_LABEL         = "delete " + TABLE_NAME_LABEL + " where LabelId = ?";
	private final String SELECT_LABELTEXT_BY_CONTENTID = "select " + COLUMN_LABEL_INSERT + " from " + TABLE_NAME_LABEL + " a, " + TABLE_NAME_CONTENTLABEL + " b where a.LabelId = b.Label_LabelId and b.Content_ContentId = ?";
	private final String SELECT_LABEL_ALL = "select " + COLUMN_LABEL_SELECT + " from " + TABLE_NAME_LABEL;  		
	private final String TYPE_EXPIRED_COUNT = " and ContentEndDate < ? ) or ( ContentStatus = 'I')) ";
	private final String SELECT_CONTENT_BY_ID = "select " + COLUMN_CONTENT_SELECT + " from " + TABLE_NAME_CONTENT +  " c, " + TABLE_NAME_CONTENTTYPE + " t  where c.Contenttype_ContenttypeId = t.ContenttypeId and c.ContentId =?";
	private final String SELECT_CONTENT_BY_ID_T ="select  ContentId from content where Contenttype_ContenttypeId = 5 and ContentStatus = 'A' and Channel_ChannelId=?";
	private final String SELECT_CONTENT_BY_ID_V = "select " + COLUMN_CONTENT_SELECT + " from " + TABLE_NAME_CONTENT + " where ContentId =? and ContentVisible = 1";
	private final String SELECT_CONTENT_BY_ID_VISIBLE = "select " + COLUMN_CONTENT_SELECT + " from " + TABLE_NAME_CONTENT + " where ContentId =? and ContentIsVisible = '1'";
	private final String INSERT_CONTENT         = "insert into " + TABLE_NAME_CONTENT + " (" + COLUMN_CONTENT_INSERT + ") values (?,?,?,?,?,?,?,?,?)";
	private final String UPDATE_CONTENT         = "update " + TABLE_NAME_CONTENT + " set " + COLUMN_CONTENT_UPDATE + " where ContentId=?";
	private final String UPDATE_CONTENTHTML         = "update " + TABLE_NAME_CONTENT + " set  ContentHtml = ? where ContentId=?";
	private final String DELETE_CONTENT         = "delete from " + TABLE_NAME_CONTENT + " where ContentId = ?";
	private final String SELECT_CONTENT_CHANNEL_BY_CONTENTID = "select "+ COLUMN_CHANNEL_SELECT +" from " + TABLE_NAME_CHANNEL + " h, " + TABLE_NAME_CONTENT + " b where h.ChannelId = b.Channel_ChannelId and b.ContentId = ? ";
	private final String COUNT_CONTENT_BY_TYPE  = "select " + COLUMN_CONTENT_COUNT + " from " + TABLE_NAME_CONTENT + "  where (( ContentStatus = ?";
	private final String COUNT_CONTENT_EXPIRED  = "select " + COLUMN_CONTENT_COUNT + " from " + TABLE_NAME_CONTENT + "  where ContentStatus <> 'A' and ContentEndDate < ?";
	private final String SELECT_CONTENT_BY_TYPE  = "select " + COLUMN_CONTENT_SELECT2 + " from " + TABLE_NAME_CONTENT + " c, " + TABLE_NAME_CONTENTTYPE + " t where ((c.Contenttype_ContenttypeId = t.ContenttypeId and c.ContentStatus =  ?";
	private final String TYPE_ACTIVE  = " and ContentEndDate >= ? ))";
	private final String TYPE_EXPIRED = " and ContentEndDate < ? ) or (c.Contenttype_ContenttypeId = t.ContenttypeId and ContentStatus = 'I')) ";
	private final String SELECT_CONTENT_BY_SEARCHTERM  = " and (CONTENTNAME like ? )";
	private final String SELECT_LIMIT = " limit ?,?";
	private final String SELECT_ORDER = " order by ? ?";
	private final String UPDATE_CONTENT_HTML_BY_CONTENTID = "update " + TABLE_NAME_CONTENT + " set " + COLUMN_CONTENT_HTML_UPDATE + " where ContentId = ?";
	private final String UPDATE_CONTENT_TO_REMOVE = " update " + TABLE_NAME_CONTENT + " set ContentStatus= 'D' where ContentId=? ";
	private final String UPDATE_CONTENT_TO_DELETE = " update " + TABLE_NAME_CONTENT + " set ContentStatus='B' where ContentId=? ";
	private final String UPDATE_CONTENT_TO_ACTIVE = " update " + TABLE_NAME_CONTENT + " set ContentStatus='A' where ContentId=? ";
	private final String SELECT_CONTENT_DESCRIPTION = " select ContentId,ContentDescription from " + TABLE_NAME_CONTENT + " where ContentId=? " ;
	private final String UPDATE_CONTENT_DESCRIPTION = "update " + TABLE_NAME_CONTENT + " set " + COLUMN_CONTENT_UPDATE_DESCRIPTION + " where ContentId = ?";
	private final String SELECT_CONTENTLABEL_BY_LABELID   = "select " + COLUMN_CONTENTLABEL_SELECT + " from " + TABLE_NAME_CONTENTLABEL + " where Label_labelId =?";
	private final String SELECT_CONTENTLABEL_BY_CONTENTID   = "select " + COLUMN_CONTENTLABEL_SELECT + " from " + TABLE_NAME_CONTENTLABEL + " where Content_ContentId =?";
	private final String INSERT_CONTENTLABEL         = "insert into " + TABLE_NAME_CONTENTLABEL + "(" + COLUMN_CONTENTLABEL_INSERT + ") values (?,?)";
	private final String DELETE_CONTENTLABEL_BY_CONTENTID         = "delete from " + TABLE_NAME_CONTENTLABEL + " where Content_ContentId= ?";
	private final String DELETE_CONTENTLABEL_BY_CONTENTID_AND_LABELID  = "delete from " + TABLE_NAME_CONTENTLABEL + " where Content_ContentId= ? and Label_LabelId";
	private final String SELECT_CONTENTMEDIA_BY_CONTENTID   = "select " + COLUMN_CONTENTMEDIA_SELECT + " from " + TABLE_NAME_CONTENTMEDIA + " where Content_ContentId =?";
	private final String SELECT_CONTENTMEDIA_BY_MEDIAID   = "select " + COLUMN_CONTENTMEDIA_SELECT + " from " + TABLE_NAME_CONTENTMEDIA + " where Media_MediaId =?";
	private final String INSERT_CONTENTMEDIA = "insert into " + TABLE_NAME_CONTENTMEDIA + "(" + COLUMN_CONTENTMEDIA_INSERT + ") values (?,?)";
	private final String DELETE_CONTENTMEDIA_BY_CONTENTID         = "delete from " + TABLE_NAME_CONTENTMEDIA + " where Content_ContentId = ?";
	private final String DELETE_CONTENTMEDIA_BY_MEDIAID         = "delete from " + TABLE_NAME_CONTENTMEDIA + " where Media_MediaId = ?";
	private final String DELETE_CONTENTMEDIA_BY_CONTENTID_AND_MEDIAID         = "delete from " + TABLE_NAME_CONTENTMEDIA + " where Content_ContentId = ? and Media_MediaId = ?";
	private final String SELECT_CHANNEL_BY_CONTENT_ID = "select " + COLUMN_CHANNEL_SELECT + " from " + TABLE_NAME_CHANNEL + " h, " + TABLE_NAME_CONTENT + " c where h.ChannelId = c.Channel_ChannelId and c.ContentId = ?";
	private final String SELECT_CHANNELID_BY_CHANNELNAME = "select ChannelId from " + TABLE_NAME_CHANNEL + " where ChannelName = ?"; 
	private final String SELECT_MEDIA_BY_CONTENT_ID  = " select " + COLUMN_MEDIA_SELECT + " from " + TABLE_NAME_MEDIA + " m, " + TABLE_NAME_CONTENTMEDIA + " c where c.Media_MediaId = m.MediaId and c.Content_ContentId = ?";
	private final String SELECT_BY_KEY_FILE = " select  MediaId" + " from "	+ TABLE_NAME_MEDIA + " where MediaContent = ?";
	private final String SELECT_IMAGE_MEDIAID = "select m.MediaId from " + TABLE_NAME_MEDIA + " m, " + TABLE_NAME_CONTENTMEDIA + " c where c.Media_MediaId = m.MediaId and m.Filetype_FiletypeId = 1 and c.Content_ContentId = ?";
	private final String SELECT_ContentStatus=  "SELECT ContentStatus FROM content WHERE ContentId = ?";
	private final String SELECT_Familyname ="select Familyname from family where Familyid=? ";
	private final String SELEC_channelname = "select ChannelFather,ChannelName from channel where Channelid=?"; 
	private final String SELECT_ContentId = "Select ContentId from content where Channel_ChannelId = ? and Contenttype_ContenttypeId = '3'";
	private final String Select_ContentId =" select ContentId from content where Channel_ChannelId=? ";
	private final String Select_Channel_ChannelId ="Select Channel_ChannelId from content where ContentId=?";
	private final String SELECTVIDEO="select m.MediaId, m.MediaContent from media m INNER JOIN ContentMedia cm ON m.MediaId=cm.Media_MediaId INNER JOIN content c ON c.ContentId= cm.Content_ContentId where c.Channel_ChannelId=? and m.Filetype_FiletypeId=3";
	private final String SELECTIMAGEN="select m.MediaId, m.MediaContent from media m INNER JOIN ContentMedia cm ON m.MediaId=cm.Media_MediaId INNER JOIN content c ON c.ContentId= cm.Content_ContentId where c.Channel_ChannelId=? and m.Filetype_FiletypeId=1;";
	
	private final String DELETE_VISIT_BY_CONTENTID = "delete from visit where Content_ContentId = ?";
	
	//COLUMN_CONTENT_SELECT
	//private final String SELECT_CONTENT_BY_IDVideo = "select "+COLUMN_CONTENT_SELECT+" from content c where Contenttype_ContenttypeId=2 and Channel_ChannelId=?;";
	private final String SELECT_CONTENT_BY_IDVideo = "select ContentId, ContentName from content where Contenttype_ContenttypeId=2 and ContentStatus='A' and  Channel_ChannelId=? and ContentId =?";
	private final String Selectcontentidvideo ="select ContentId from content where Contenttype_ContenttypeId=2 and ContentStatus='A' and  Channel_ChannelId=?";
	public ContentDAO() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * Check whether valid name or not.
	 * 
	 * @param name
	 * @param folderID
	 * @return boolean
	 */

	
	public boolean labelTextExist(String text) {
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try {
			long result = 0;
			stmt = conn
					// 												RUNNING QUERY
					.prepareStatement(COUNT_LABEL_BY_TEXT);
			stmt.setString(1, text);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				result = rs.getLong(1);
			}

			return result == 0 ? false : true;
		} catch (Exception e) {
			log.error("ContentDAO", "labelTextExist", e.toString());
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
	 *   get Label Id By Text
	 *   
	 *   
	 * @param text
	 * @return
	 */
	
	public int getLabelIdByText(String text) {
		int result = 0;
		Connection conn = DSManager.getConnection();
		
		PreparedStatement stmt = null;
	
		try {
																	// 	RUNNING QUERY
			stmt = conn.prepareStatement(SELECT_LABEL_BY_TEXT);
			stmt.setString(1, text);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				result = rs.getInt("LabelId");
			}
			return result;
		
		} catch (Exception e) {
			log.error("ContentDAO", "getLabelIdByText", e.toString());
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
	
	/**
	 * INSERT LABEL
	 * @param text
	 */
	public void insertLabel(String text) {
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try {
			
			stmt = conn
					.prepareStatement(INSERT_LABEL);
			stmt.setString(1, text);
			stmt.executeUpdate();
				
		}catch (Exception e) {
			log.error("ContentDAO", "insertlabel", e.toString());
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
	}

/**
 * DELETE LABEL
 * 
 * @param id
 */
	
	public void deleteLabel(int id) {
			
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
			try {
				
				stmt = conn
						.prepareStatement(DELETE_LABEL);
				stmt.setInt(1, id);
				stmt.executeQuery();
					
			} catch (Exception e) {
				log.error("ContentDAO", "getLabelIdByText", e.toString());
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
	}

	
	/**
	 * GET ALL CONTENT BY CONTENTID
	 * @param contentid
	 * @return
	 */
	public ContentInfo getContentByContentIdAll(Long contentid) {
		
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try {
					
			stmt = conn
																	// RUNNING QUERY
					.prepareStatement(SELECT_CONTENT_BY_ID);
			stmt.setLong(1, contentid);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				ContentInfo result = new ContentInfo();
				result.setId(rs.getLong(1));
				result.setName(rs.getString(4));
				result.setHtml(Utils.checkNull(rs.getString(5)));
				result.setValidityStart(rs.getString(7));
				result.setValidityEnd(rs.getString(8));
				result.setStatus(rs.getString(9));
				result.setShowIn(rs.getString(10));
				
				return result;
			}
					
		} catch (Exception e) {
			log.error("ContentDAO", "getContentByContentIdAll", e.toString());
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
	 * GET CONTET BY CONTENTID
	 * @param contentid
	 * @return
	 */
	
	public ContentInfo getContentByContentIdVis(int contentid) {
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt  = null;
		try {
					
			stmt = conn
																	// RUNNING QUERY
					.prepareStatement(SELECT_CONTENT_BY_ID_V);
			stmt.setInt(1, contentid);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				ContentInfo result = new ContentInfo();
				result.setId(rs.getLong("ContentID"));
				result.setName(rs.getString("ContentName"));
				result.setHtml(Utils.checkNull(rs.getString("ContentHtml")));
				//resñ8ult.setIsVisble(rs.getBoolean(rs.getBoolean("ContentIsVisible"));
				result.setValidityStart(rs.getString("ContentPublishDate"));
				result.setValidityEnd(rs.getString("ContentEndDate"));
				result.setStatus(rs.getString("ContentStatus"));
				result.setShowIn(rs.getString("ContentShowView"));
			
				
				return result;
			}
					
		} catch (Exception e) {
			log.error("ContentDAO", "getContentByContentIdAll", e.toString());
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
	 * INSERT CONTENT
	 * 
	 * @param channelid
	 * @param contenttypeid
	 * @param name
	 * @param html
	 * @param isvisible
	 * @param publishdate
	 * @param enddate
	 * @param status
	 * @param showview
	 * @return
	 */

	public Long  insertContent(int channelid, int contenttypeid, String name, String html,int isvisible, String publishdate,String enddate,String status,String showview  ) {	
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = conn
																	// RUNNING QUERY
					.prepareStatement(INSERT_CONTENT,Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, channelid);
			stmt.setInt(2, contenttypeid);
			stmt.setString(3,name);
			stmt.setString(4, html);
			stmt.setInt(5, isvisible);
			stmt.setString(6, publishdate);
			stmt.setString(7, enddate);		
			if (isvisible == 0) {
				stmt.setString(8,"I");
			}else {
				stmt.setString(8, "A");	
			}
			
			stmt.setString(9, showview);
			
			stmt.executeUpdate();
			
			ResultSet generatedKeys = stmt.getGeneratedKeys();
			generatedKeys.next();
	        return generatedKeys.getLong(1);
				
		} catch (Exception e) {
			log.error("ContentDAO", "getLabelIdByText", e.toString());
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
		return (long)0;
	}
	
/**
 * GET CONTENT
 * 
 * @param contentid
 * @return
 */
	public String getContentSta(long contentid) {
		String result="";
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try {
	
			stmt = conn
																	// RUNNING QUERY
					.prepareStatement(SELECT_ContentStatus);
			stmt.setLong(1, contentid);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {

				result = rs.getString("ContentStatus");
				
				return result;
			}
					
		} catch (Exception e) {
			log.error("ContentDAO", "getContentByContentIdAll", e.toString());
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
	 * 
	 * UPDATE CONTENT
	 * 
	 * @param channelid
	 * @param contenttypeid
	 * @param name
	 * @param html
	 * @param isvisible
	 * @param publishdate
	 * @param enddate
	 * @param status
	 * @param showview
	 * @param contentid
	 */
	
	public void updateContent(int channelid, int contenttypeid, String name, String html,int isvisible, String publishdate,String enddate,String status,String showview,Long contentid  )
	{
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try {
			String result;
			stmt = conn
																	// RUNNING QUERY
					.prepareStatement(UPDATE_CONTENT);
			stmt.setInt(1, channelid);
			stmt.setInt(2, contenttypeid);
			stmt.setString(3,name);
			stmt.setString(4, null);
			stmt.setInt(5, isvisible);
			stmt.setString(6, publishdate);
			stmt.setString(7, enddate);
			
			result = getContentSta(contentid);
			
			if(result.equals("B")){
				stmt.setString(8,"B");
			}else{
				if (isvisible == 0) {
					stmt.setString(8,"A");
				}else {
					stmt.setString(8, "A");	
				}
			}
			
			stmt.setString(9, showview);
			stmt.setLong(10, contentid);
			
			stmt.execute();
		}catch (Exception e) {
			log.error("ContentDAO", "updateContent", e.toString());
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
	}	
	
	
	/**
	 * DELET CONTENT BY CONTENTID
	 * 
	 * @param contentid
	 */
	
	public void deleteContent(Long contentid) {
		deleteContentlabelByContentId(contentid);
		deleteContentMediaByContentId(contentid);
		deleteVisitByContentId(contentid);
		
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(DELETE_CONTENT);
			stmt.setLong(1, contentid);

			stmt.executeUpdate();
		} catch (Exception e) {
			log.error("ContentDAO", "deleteContent", e.toString());
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

	}
	
	/**
	 * GET CONTENT LABEL BY LABELID
	 * 
	 * @param labelid
	 * @return
	 */
	
	public ArrayList getContentlabelByLabelId(int labelid)
	{
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		ArrayList item = new ArrayList();
		try {
			stmt = conn
																	// RUNNING QUERY
					.prepareStatement(SELECT_CONTENTLABEL_BY_LABELID);
			stmt.setInt(1, labelid);
			ResultSet rs = stmt.executeQuery();
			while (rs.next())
			{
				int result = rs.getInt("Content_ContentId");
				item.add(result);
			}
		}catch (Exception e) {
				log.error("ContentDAO", "getContentlabelByLabelId", e.toString());
			
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
		return item;
	}
	
	/**
	 * GET CONTENT LABEL BY CONTENTID
	 * 
	 * @param contentid
	 * @return
	 */

	
	public ArrayList getContentlabelByContentId(int contentid)
	{
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		ArrayList item = new ArrayList();
		try {
			stmt = conn
																	// RUNNING QUERY
					.prepareStatement(SELECT_CONTENTLABEL_BY_CONTENTID);
			stmt.setInt(1, contentid);
			ResultSet rs = stmt.executeQuery();
			while (rs.next())
			{
				int result = rs.getInt("Label_LabelId");
				item.add(result);
			}
		}catch (Exception e) {
				log.error("ContentDAO", "getContentlabelByLabelId", e.toString());
			
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
		return item;
	}
	
	/**
	 * INSER CONTENTE LABEL BY CONTENTID AND LABELID
	 * 
	 * @param contentid
	 * @param labelid
	 * @return
	 */

	public int insertContentlabel(Long contentid,int labelid)
	{
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try {
			
			stmt = conn
																	// RUNNING QUERY
					.prepareStatement(INSERT_CONTENTLABEL,Statement.RETURN_GENERATED_KEYS);
			stmt.setLong(1, contentid);
			stmt.setInt(2, labelid);
			
			stmt.executeUpdate();
			
			ResultSet generatedKeys = stmt.getGeneratedKeys();
			generatedKeys.next();
	        return generatedKeys.getInt(1);
		}catch (Exception e) {
		log.error("ContentDAO","insertContentlabel",e.toString());
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
	
	
/**
 * 
 * DELETE CONTENT LABEL BY CONTENTID
 * 
 * @param contentid
 */
	public void deleteContentlabelByContentId(Long contentid)
	{
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try
		{
			stmt = conn
					.prepareStatement(DELETE_CONTENTLABEL_BY_CONTENTID);
			stmt.setLong(1, contentid);
			stmt.executeUpdate();
			
		}catch (Exception e){
			log.error("ContentDAO","deleteContentlabelByContnetId",e.toString());
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

	/**
	 * DELETE CONTENT LABEL BY CONTENTID AND LABELID
	 * 
	 * @param contentid
	 * @param labelid
	 */
	

	public void deleteContentlabelByContentIdAndLabelId (int contentid,int labelid)
	{
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try 
		{
			stmt = conn
																	// RUNNING QUERY
					.prepareStatement(DELETE_CONTENTLABEL_BY_CONTENTID_AND_LABELID);
			stmt.setInt(1, contentid);
			stmt.setInt(2, labelid);
			stmt.execute();
		}catch (Exception e){
			log.error("ContentDAO","deleteContentlabelByContentIdAndLabelId",e.toString());
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
	}
	
	/**
	 * GET CONTENT MEDIA BY  CONTENTID
	 * 
	 * @param contentid
	 * @return
	 */
	
	public ArrayList getContentmediaByContentId(int contentid)
	{
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		ArrayList item = new ArrayList();
		try {
			stmt = conn
																	// RUNNING QUERY
					.prepareStatement(SELECT_CONTENTMEDIA_BY_CONTENTID);
			stmt.setInt(1, contentid);
			ResultSet rs = stmt.executeQuery();
			while (rs.next())
				{
					int result = rs.getInt("Media_MediaId");
					item.add(result);
				}
				
			}catch (Exception e) {
					log.error("ContentDAO", "getContentlabelByLabelId", e.toString());
				
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
			return item;
	}
	
	
	/**
	 * GET CONTENT MEDIA BY MEDIAID
	 * 
	 * @param mediaid
	 * @return
	 */
	public ArrayList getContentmediaByMediaId(int mediaid)
	{
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		ArrayList item = new ArrayList();
		try {
			stmt = conn
																	// RUNNING QUERY
					.prepareStatement(SELECT_CONTENTMEDIA_BY_MEDIAID);
			stmt.setInt(1, mediaid);
			ResultSet rs = stmt.executeQuery();
			while (rs.next())
				{
					int result = rs.getInt("Content_ContentId");
					item.add(result);
				}
		
			
		}catch (Exception e){
			log.error("ContenDAO","getContentmediaByMediaId",e.toString());
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
		return item;
	}
		
		
	/**
	 * INSERT CONTENT MEDIA BY CONTENTID AND MEDIAID
	 * @param contentid
	 * @param mediaid
	 * @return
	 */
	public int insertContentmedia (Long contentid,int mediaid)
	{
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try {
			
			stmt = conn
																	// RUNNING QUERY
					.prepareStatement(INSERT_CONTENTMEDIA,Statement.RETURN_GENERATED_KEYS);
			stmt.setLong(1, contentid);
			stmt.setInt(2, mediaid);
			
			stmt.executeUpdate();
			
			ResultSet generatedKeys = stmt.getGeneratedKeys();
			generatedKeys.next();
	        return generatedKeys.getInt(1);
	        
		}catch (Exception e) {
		log.error("ContentDAO","insertContentmedia",e.toString());
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
	
	
	/**
	 * DELETE CONTENT MEDIA BY CONTENTID
	 *  
	 * @param contentid
	 */
	public void deleteContentMediaByContentId (Long contentid)
	{
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try
		{
			stmt = conn
					.prepareStatement(DELETE_CONTENTMEDIA_BY_CONTENTID);
			stmt.setLong(1, contentid);
			stmt.executeUpdate();
			
		}catch (Exception e){
			log.error("ContentDAO","deleteContentlabelByContentId",e.toString());
		}
		finally{
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	/**
	 * DELETE CONTENT MEDIA BYE MEDIAID 
	 * @param mediaid
	 */
	public void deleteContentMediaByMediaId (int mediaid)
	{
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try
		{
			stmt = conn
																	// RUNNING QUERY
					.prepareStatement(DELETE_CONTENTMEDIA_BY_MEDIAID);
			stmt.setInt(1, mediaid);
			stmt.executeQuery();
			
		}catch (Exception e){
			log.error("ContentDAO","deleteContentlabelByMediaId",e.toString());
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
	}
	
	
	/**
	 * DELTE CONTENT MEDIA BY CONTENTID AND MEDIAID
	 * 
	 * @param contentid
	 * @param mediaid
	 */
	public void deleteContentmediaByContentIdAndMediaId (Long contentid,int mediaid)
	{
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try
		{
			stmt = conn
																	// RUNNING QUERY
					.prepareStatement(DELETE_CONTENTMEDIA_BY_CONTENTID_AND_MEDIAID);
			stmt.setLong(1, contentid);
			stmt.setInt(2, mediaid);
			stmt.executeUpdate();
			
		}catch (Exception e){
			log.error("ContentDAO","deleteContentmediaByContentIdAndMediaId",e.toString());
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
	
	/**
	 * GET LABEL TEXT BY CONTENTID
	 * 
	 * @param contentid
	 * @return
	 */
	public ArrayList getLabelTextByContentId(Long contentid)
	{
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		ArrayList item = new ArrayList();
		try
		{
			stmt = conn
																	// RUNNING QUERY
					.prepareStatement(SELECT_LABELTEXT_BY_CONTENTID);
			stmt.setLong(1, contentid);
		
			stmt.executeQuery();
			ResultSet rs =stmt.executeQuery();
			while (rs.next())
			{
				String result = rs.getString("LabelText");
				item.add(result);
			}
			return item;
			
		}catch (Exception e) {
			log.error("ContentDAO","getLabelTextByContentId",e.toString());
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
	 * GET TAG LIST BY CONTENTID
	 * 
	 * @param content_id
	 * @return
	 */
	public ArrayList getTagList(Long content_id){
		ArrayList result = new ArrayList();
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try{
																	// RUNNING QUERY
			stmt = conn.prepareStatement(SELECT_LABELTEXT_BY_CONTENTID);
			stmt.setLong(1, content_id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				CodeInfo item = new CodeInfo();
				item.setValue(Utils.checkNull(rs.getString("LabelText")));
				result.add(item);
			}
		}catch (Exception e){
			log.error("ContentDAO", "getTagList");
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
	 * GET CONTENT CHANNEL LIST
	 * 
	 * @param contentID
	 * @return
	 */
	public ArrayList getContentChannelList(Long contentID){
		ArrayList result=new ArrayList();
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		
		try{
																	// RUNNING QUERY
			stmt = conn.prepareStatement(SELECT_CHANNEL_BY_CONTENT_ID);
			stmt.setLong(1, contentID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				ChannelInfo item = new ChannelInfo();
				item.setId(rs.getLong("ChannelId"));
				item.setParentID(rs.getLong("ChannelFather"));
				item.setFamilyID(rs.getLong("Family_FamilyId"));
				item.setName(rs.getString("ChannelName"));
				item.setEmail(rs.getString("ChannelEmail"));
				item.setPassword(rs.getString("ChannelPassword"));
				item.setSecurityLevel(rs.getString("ChannelSecurityLevel"));
				item.setAccessLevel(rs.getInt("ChannelIsVisible"));
				item.setPeopleManager("0");
				item.setNewHire("0");
				item.setPos(rs.getLong("ChannelPosition"));
				
				item.setSecurityLevelName("leve1 "+rs.getString("ChannelSecurityLevel"));
				
				
				item.setFullName(getFamilyName(item.getFamilyID()) + " > " + getChannelName(item.getId()) );
				result.add(item);
			}
		}catch (Exception e){
			log.error("ContentDAO", "getContentChannelList", e.toString());
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
	 * GET ALL TAG
	 * @return
	 */
	public String getAllTag(){
		JSONArray result = new JSONArray();
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try{
																	// RUNNING QUERY
			stmt = conn.prepareStatement(SELECT_LABEL_ALL);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				result.put(Utils.checkNull(rs.getString("LabelText")));
			}
		}catch (Exception e){
			log.error("ContentDAO", "getAllTag",e.toString());
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

		return result.toString();
	}
	
	/**
	 * GET CONTENT TYPE BY CONTENTTYPE
	 * @param contenttype
	 * @return
	 */
	public long getCountByType(String contenttype)
	{
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		long result = 0;
		String sql = COUNT_CONTENT_BY_TYPE;
		String fecha = "";
		try {
			
			if ((contenttype =="A") || (contenttype == "E"))
			{
				if (contenttype == "A")
				{
					sql = sql + TYPE_ACTIVE;
				}else {
					sql = sql + TYPE_EXPIRED_COUNT;
					contenttype = "A";
				}
			}else {
				sql = sql + "))";
			}
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, contenttype);
			if ((contenttype =="A") || (contenttype == "E"))
			{
				stmt.setString(2,Utils.getToday("-"));
				fecha = Utils.getToday("-");
			}
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				result = rs.getLong(1);
			}
		}catch (Exception e){
			log.error("ContentDAO","getCountByType",e.toString());
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
	 * GET CONTENT
	 * IS JSONARRAY
	 * @param sql
	 * @return
	 */
		public JSONArray getContentS(String sql){
			JSONArray result = new JSONArray();
			Connection conn = DSManager.getConnection();
			PreparedStatement stmt = null;
			try{
																	// RUNNING QUERY										
				stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()){
					JSONObject item = new JSONObject();
					
					int contentID = rs.getInt("ContentId");
					item.put("id", contentID);
					item.put("name", rs.getString("Contentname"));
					item.put("type", rs.getString("ContentType"));
					item.put("type_name", "value_me");
					item.put("show_in", rs.getString("ContentShowView"));
					item.put("validity", rs.getString("ContentPublishDate") + " ~ " + rs.getString("ContentEndDate"));
					item.put("active", rs.getString("ContentStatus"));
					item.put("status", "A");
					item.put("blog", "blog");
					item.put("tag", getTagName(contentID));
					item.put("channel", getContentChannel(contentID));
					
					result.put(item);
				}
			}catch (Exception e){
				log.error("ContentDAO", "getContent", e.toString());
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
		 * GET TAG NAME BY CONTENTID
		 * 
		 * @param contentID
		 * @return
		 */
		public String getTagName(int contentID){
			String result="";
			int count = 0;
			Connection conn = DSManager.getConnection();
			PreparedStatement stmt  = null;
			try{
																	// RUNNING QUERY
				stmt = conn.prepareStatement(SELECT_LABELTEXT_BY_CONTENTID);
				stmt.setLong(1, contentID);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()){
					count ++ ;
					if (count<=1){
						result = "<span class='label round'>"+rs.getString("LabelText")+"</span>";
					}
				}
			}catch (Exception e){
				log.error("ContentDAO", "getTagName", e.toString());
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

			if (count>1)
				result += " and <b>" + (count-1) + " more</b>";
			
			return result;
		}
	
		
		/**
		 * GET CONTENT CHANNEL BY CONTETNID
		 * 
		 * @param contentID
		 * @return
		 */
		public String getContentChannel(int contentID){
			String result="";
			int count = 0;
			Connection conn = DSManager.getConnection();
			PreparedStatement stmt = null;
			try{
																	// RUNNING QUERY				
				stmt = conn.prepareStatement(SELECT_CONTENT_CHANNEL_BY_CONTENTID);
				stmt.setLong(1, contentID);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()){
					count ++ ;
					if (count<=1){
						result = "<b>"+rs.getString("ChannelName")+"</b>";
					}
				}
			}catch (Exception e){
				log.error("ContentDAO", "getContentChannel", e.toString());
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

			if (count>1)
				result += " and <b>" + (count-1) + " more</b>";
			
			return result;
		}
	
		/**
		 * GET CONT
		 * 
		 * @param sql
		 * @return
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
				log.error("ContentDAO", "getCount", e.toString());
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
	 * GET CONTENT BY TYPE
	 * 
	 * @param type
	 * @param searchterm
	 * @param columna
	 * @param orden
	 * @param inicio
	 * @param registros
	 * @return
	 */
	public ContentCountInfo getContentByType(String type, String searchterm, String columna, String orden,int inicio,int registros)
	{
		
		JSONArray result = new JSONArray();
		Long lngregs = (long)0;
		int intparam = 2;
		ContentCountInfo conteninfo = new ContentCountInfo();
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
																	// RUNNING QUERY
		String sql = SELECT_CONTENT_BY_TYPE;
		String status = "";
		String showin = "";
		if( (type == "A") || (type == "E"))
		{
			if (type == "A")
			{
				sql = sql + TYPE_ACTIVE;
			}else{
				sql = sql + TYPE_EXPIRED;
				type = "A";
			}
		}else {
			sql = sql + "))";
		}
		
		try
		{
			
			if (searchterm.length() >0) 
			{
				sql = sql + SELECT_CONTENT_BY_SEARCHTERM ;
			}
			sql = sql + " order by " + columna + " " + orden + " " + SELECT_LIMIT;
								
			
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, type);
			
			if ((type == "A") || (type == "E"))
			{
				stmt.setString(intparam, Utils.getToday("-"));
				intparam = intparam + 1;
			}
			
			
			if (searchterm.length() > 0)
			{
				searchterm = "%"+searchterm+"%";
				stmt.setString(intparam, searchterm);
				intparam = intparam + 1;
			}
			

			stmt.setInt(intparam, inicio);
			intparam = intparam + 1;
			stmt.setInt(intparam, registros);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				JSONObject item = new JSONObject();
				
				int contentID = rs.getInt("ContentId");
				item.put("id", contentID);
				item.put("name", rs.getString("ContentName"));
				item.put("type", rs.getString("Contenttype_ContenttypeId"));
				item.put("type_name",rs.getString("ContenttypeName"));
				showin = rs.getString("ContentShowView");
				
				item.put("show_in", showin);
				item.put("validity", rs.getString("ContentPublishDate") + " ~ " + rs.getString("ContentEndDate"));
				item.put("status", rs.getString("ContentStatus"));
				item.put("active", rs.getString("ContentIsVisible"));
				item.put("blog", "blog");
				item.put("tag", getTagName(contentID));
				item.put("channel", getContentChannel(contentID));
				
				result.put(item);	
				lngregs = lngregs + 1;
		}
			conteninfo.setRecords(lngregs);
			conteninfo.setJSONArray(result);
			
		}catch (Exception e){
			log.error("ContentDAO","getContentByType",e.toString());
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
		
		return conteninfo;	
		
	}
	
	
	

	/**
	 * GET CONTENT BY TYPE
	 * 
	 * @param type
	 * @param searchterm
	 * @return
	 */
	public Long getContentCountByType(String type, String searchterm)
	{
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		Long lngregs = (long)0;
		int intparam = 2;
		ContentCountInfo conteninfo = new ContentCountInfo();
		String sql = "Select count(ContentId) from content where (( ContentStatus = ? ";
		String status = "";
		if( (type == "A") || (type == "E"))
		{
			if (type == "A")
			{
				sql = sql + TYPE_ACTIVE;
			}else{
				sql = sql + TYPE_EXPIRED;
				type = "A";
			}
		}
		
		try
		{
			
			if (searchterm.length() >0) 
			{
				sql = sql + SELECT_CONTENT_BY_SEARCHTERM ;
			}

								
			
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, type);
			
			if ((type == "A") || (type == "E"))
			{
				stmt.setString(intparam, Utils.getToday("-"));
				intparam = intparam + 1;
			}
			
			
			if (searchterm.length() > 0)
			{
				searchterm = "%"+searchterm+"%";
				stmt.setString(intparam, searchterm);
				intparam = intparam + 1;
			}
			


			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				
				lngregs = rs.getLong(1);
}

			
		}catch (Exception e){
			log.error("ContentDAO","getContentByType",e.toString());
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
		
		return lngregs;	
		
	}

	/**
	 * GET CONTENT BY CONTENT ID
	 * IS A JSON
	 * @param contentId
	 * @return
	 */
	public JSONObject getContentOfJSON(Long contentId){
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try{
			String showin = "";
			
																	// RUNNING QUERY
			stmt = conn.prepareStatement(SELECT_CONTENT_BY_ID);
			stmt.setLong(1, contentId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				ContentInfo result = new ContentInfo();
				result.setId(rs.getLong("ContentId"));
				result.setType(rs.getString("Contenttype_ContenttypeId"));
				showin=rs.getString("ContentShowView");
				result.setShowIn(rs.getString("ContentShowView"));

				result.setName(rs.getString("ContentNAme"));
				result.setValidityStart(rs.getString("ContentPublishDate"));
				result.setValidityEnd(rs.getString("ContentEndDate"));
				result.setStatus(rs.getString("ContentStatus"));
				result.setActive(rs.getString("ContentIsVisible"));
				
				return result.toJSONObject();
			}
		}catch (Exception e){
			log.error("ContentDAO", "getContentOfJSON", e.toString());
		}
		finally{
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return new JSONObject();
	}

	/**
	 * GET CONTENT CHANNEL LIST BY CONTENTID
	 * @param contentID
	 * @return
	 */
	public JSONArray getContentChannelListOfJSON(Long contentID){
		JSONArray result=new JSONArray();
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		
		try{
																	// RUNNING QUERY
			stmt = conn.prepareStatement(SELECT_CHANNEL_BY_CONTENT_ID);
			stmt.setLong(1, contentID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				ChannelInfo item = new ChannelInfo();
				item.setId(rs.getLong("ChannelId"));
				item.setParentID(rs.getLong("ChannelFather"));
				item.setFamilyID(rs.getLong("Family_FamilyId"));
				item.setName(rs.getString("ChannelName"));
				item.setEmail(rs.getString("ChannelEmail"));
				item.setPassword(rs.getString("ChannelPassword"));
				item.setAccessLevel(rs.getInt("ChannelIsVisible"));
				item.setSecurityLevel(rs.getString("ChannelSecurityLevel"));
				item.setPos(rs.getLong("ChannelPosition"));
				item.setSecurityLevelName("me");
				
				item.setFullName(getFamilyName(item.getFamilyID()) + " > " + getChannelName((item.getId())));
				
				result.put(item.toJSON());
			}
		}catch (Exception e){
			log.error("ContentDAO", "getContentChannelListOfJSON", e.toString());
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
	 * GET FAMILY NAME
	 * @param familyID
	 * @return
	 */
	private String getFamilyName(Long familyID){
		String result = "";
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try{
																	// RUNNING QUERY
			stmt = conn.prepareStatement(SELECT_Familyname);
			stmt.setLong(1, familyID);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				result = rs.getString("Familyname");
			}
		}catch (Exception e){
			log.error("ContentDAO", "getFamilyName", e.toString());
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
	 * GET CHABBEL NAME BY CHANNELID 
	 * @param channelID
	 * @return
	 */
	private String getChannelName(Long channelID){
		String result = "";
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try{
																	// RUNNING QUERY
			stmt = conn.prepareStatement(SELEC_channelname);
			stmt.setLong(1, channelID);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				Long parentID = rs.getLong("ChannelFather");
				if (parentID==0)
					result = rs.getString("ChannelName");
				else
					result = getChannelName(parentID) + " > " + rs.getString("ChannelName");
			}
		}catch (Exception e){
			log.error("ContentDAO", "getChannelName", e.toString());
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
	 * GET TAG LIST  OF JSON BY ID
	 * @param id
	 * @return
	 */
	public JSONArray getTagListOfJSON(Long id){
		JSONArray result = new JSONArray();
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try{
																	// RUNNING QUERY
			stmt = conn.prepareStatement(SELECT_LABELTEXT_BY_CONTENTID);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				CodeInfo item = new CodeInfo();
				item.setValue(Utils.checkNull(rs.getString("LabelText")));
				result.put(item.toJSONObject());
			}
		}catch (Exception e){
			log.error("ContentDAO", "getTagListofJSON",e.toString());
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
	 * GET CONTET VIDEO BY CONTENTID
	 * @param contentID
	 * @return
	 */
	public ContentVideoInfo getContentVideo(Long contentID){
		ContentVideoInfo item = new ContentVideoInfo();
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try{
																	// RUNNING QUERY
			stmt = conn.prepareStatement(SELECT_MEDIA_BY_CONTENT_ID);
		
			stmt.setLong(1, contentID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				if (rs.getLong("Filetype_FiletypeId")==1) 
				{
					item.setImage(rs.getString("MediaContent"));
				}else
				{
					item.setVideo(rs.getString("MediaContent"));
				}
						
			}
			item.setContentID(contentID);
			
			return item;

		}catch (Exception e){
			log.error("ContentDAO", "getContentVideo", e.toString());
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
	
	/**
	 * GET CONTENT NEWS BY CONTENTID
	 * @param contentID
	 * @return
	 */
	public ContentNewsInfo getContentNews(Long contentID){
		ContentNewsInfo item = new ContentNewsInfo();
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try{
																	// RUNNING QUERY
			stmt = conn.prepareStatement(SELECT_MEDIA_BY_CONTENT_ID);
			stmt.setLong(1, contentID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				if (rs.getInt("Filetype_FiletypeId")==1) 
				{
					item.setImage(rs.getString("MediaContent"));
				}else{
					item.setContent(rs.getString("MediaContent"));
				}
			}
			
			
		}catch (Exception e){
			log.error("ContentDAO", "getContentNews", e.toString());
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

		return item;
	}	

	

	/**
	 * GET CONTENT GALLERY MEDIA LIST
	 * 
	 * @param contentID
	 * @return
	 */
	public ArrayList getContentGalleryMediaList(Long contentID){
		ArrayList result=new ArrayList();
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		
		try{
																	// RUNNING QUERY
			stmt = conn.prepareStatement(SELECT_MEDIA_BY_CONTENT_ID);
			stmt.setLong(1, contentID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				ContentGalleryMediaInfo item = new ContentGalleryMediaInfo();
				item.setId(rs.getLong("Mediaid"));
				item.setContentID(contentID);
				item.setName(rs.getString("MediaName"));
				item.setMedia(rs.getString("MediaContent"));
				
				result.add(item);
			}
		}catch (Exception e){
			log.error("ContentDAO", "getContentGalleryMediaList", e.toString());
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
	 * GENERATE HTML FROM CONTENT VIDEO
	 * @param contentID
	 * @throws Exception
	 */
	public void generateHTML(Long contentID) throws Exception	{
		ContentInfo content = getContent(contentID);
		String result = "";
		String result_mobile = "";
		int idmedia=0;
		int idmedia_media=0;
		String BlobImagen="";
		
		if (content!=null){
			
			if (content.getType().equals("2")){
				ContentVideoInfo video = getContentVideo(contentID);

				int i= 1;
				
				if (video!=null && video.getImage().length()>0 && video.getVideo().length()>0){
					
					result += "<div><h3>";
					result += "<object type=\"application/x-shockwave-flash\" " +  
							 "data=\"/resources/swf/playerLite.swf\" " + 
							 "width=\"473\" height=\"245\" style=\"visibility: visible; width:473px; height:245px;\">" +
							 "<param name=\"menu\" value=\"true\">"+
							 "<param name=\"allowfullscreen\" value=\"true\">"+
							 "<param name=\"allowscriptaccess\" value=\"always\">"+
							 "<param name=\"flashvars\" value=\"vidWidth=473&vidHeight=245&vidPath="+HTML.media("video", video.getVideo())+"&thumbPath="+HTML.media("video", video.getImage())+"&plShowAtStart=true&plHideDelay=2000&autoPlay=false&autoLoop=false&watermark=hide&vidAspectRatio=fit&seekbar=show\"></object>" ;
						result += "</h3></div>";
					

				}
				
				result_mobile = result;
			}

			if (content.getType().equals("5")){
				ContentNewsInfo news = getContentNews(contentID);
				if (news!=null){
					String image = news.getImage();
					String date = Utils.toDisplayDate(content.getUpdatedAt());
					String html = news.getContent();
					
					String html_normal = HTML.replaceVideo(html, image);
					String html_mobile = HTML.replaceVideoForMobile(html);
					
					result += "<div " + HTML.attr("class", "large-16 medium-16 columns") + ">";

					result += "<div " + HTML.attr("class", "news") + ">" + html_normal + "</div>";
					result += "</div>";
					
					result_mobile += "<div " + HTML.attr("class", "large-16 medium-16 columns") + ">";
					result_mobile += "<div " + HTML.attr("class", "news") + ">" + html_mobile + "</div>";
					result_mobile += "</div>";
				}
			}
			
			
			if (content.getType().equals("3")){
				ArrayList gallerys = getContentGalleryMediaList(contentID);

					
					if (gallerys.size()>0){
						result += "<ul clearing-thumbs class='clearing-thumbs large-block-grid-4 medium-block-grid-3 small-block-grid-2' data-clearing>";
					}
					
					for (int i=0; i<gallerys.size(); i++){
						ContentGalleryMediaInfo item = (ContentGalleryMediaInfo)gallerys.get(i);
						
						result += "<li>";
						result += "<a " + HTML.attr("href", HTML.media("gallery", item.getMedia())) + " " + ">";
						result += "<img " + HTML.attr("src", HTML.media("gallery", item.getMedia())) + " " + HTML.attr("alt", "") + " " + HTML.attr("style", "width:250px;")  + " />";
						result += "</a>";
						result += "</li>";
					}
					
					if (gallerys.size()>0){
						result += "</ul>";
					}

				
				result_mobile = result;
			}
			
			if (content.getType().equals("4")){
				ArrayList files = getContentFileMediaList(contentID);
				

					if (files.size()>0){
						result += "<ul>";
					}
					
					for (int i=0; i<files.size(); i++){
						ContentFileMediaInfo item = (ContentFileMediaInfo)files.get(i);
						result += "<li><a " + HTML.attr("href", "javascript:file_download('" + HTML.media("file", item.getMedia())+"&name="+item.getName() + "')") + ">" ;
						result += "<i " + HTML.attr("class", "fa " + MediaDAO.getFileExtencion(item.getName()) + " fa-lg") + ">" + "</i>";
						result += " <span>" + item.getName() + "</span></a></li>";
					}

					if (files.size()>0){
						result += "</ul>";
					}
				
				result_mobile = result;
			}
			
			updateContentHtml(result, contentID);
		}
	}

	/**
	 * GET CONTENT FILE MEDIA LIST BY CONTENT ID
	 * @param contentID
	 * @return
	 */
	public ArrayList getContentFileMediaList(Long contentID){
		ArrayList result=new ArrayList();
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		
		try{
																	// RUNNING QUERY
			stmt = conn.prepareStatement(SELECT_MEDIA_BY_CONTENT_ID);
			stmt.setLong(1, contentID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				ContentFileMediaInfo item = new ContentFileMediaInfo();
				item.setId(rs.getLong("Mediaid"));
				item.setContentID(contentID);
				item.setName(rs.getString("MediaName"));
				item.setMedia(rs.getString("MediaContent"));
				
				result.add(item);
			}
		}catch (Exception e){
			log.error("ContentDAO", "getContentGalleryMediaList", e.toString());
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
	 * UPDATE CONTENT HTML BY HTML AND CONTENTID 
	 * @param Html
	 * @param contentid
	 */
	public void updateContentHtml(String Html, Long contentid)
	{
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try
		{
																	// RUNNING QUERY
			stmt = conn.prepareStatement(UPDATE_CONTENT_HTML_BY_CONTENTID);
			stmt.setString(1, Html);
			stmt.setLong(2, contentid);
			stmt.executeUpdate();
			
		}catch (Exception e){
			log.error("ContentDAO","updateContentHtml",e.toString());
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
	}
	

	/**
	 * GET CONTENT BY CONTENTID
	 * @param contentID
	 * @return
	 */
	public ContentInfo getContent(Long contentID){
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt  = null;
		try{
																	// RUNNING QUERY
			stmt = conn.prepareStatement(SELECT_CONTENT_BY_ID);
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
				result.setStatus(rs.getString("ContentStatus"));				
				result.setCreatedAt("00/00/00");
				result.setUpdatedAt("00/00/00");
				result.setHtml(Utils.checkNull(rs.getString("ContentHtml")));
				result.setHtmlMobile(Utils.checkNull(rs.getString("ContentHtml")));
				result.setBlog("blog");
				result.setActive(rs.getString("ContentIsVisible"));
				
				return result;
			}
		}catch (Exception e){
			log.error("ContentDAO", "getContent", e.toString());
		}
		finally{
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
	 * UPDATE CONTENT REMOVE 
	 * @param id
	 * @throws Exception
	 */
	public void removeContent(Long id) throws Exception{
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
																	// RUNNING QUERY
		stmt = conn.prepareStatement(UPDATE_CONTENT_TO_REMOVE);
		stmt.setLong(1, id);
		stmt.executeUpdate();
		
			stmt.close();
			conn.close();
		
	}
	
	/**
	 * DELETE CONTENT BY ID
	 * @param id
	 * @throws Exception
	 */
	public void deleteContentLogical(Long id) throws Exception{
		Connection conn = DSManager.getConnection();
																	// RUNNING QUERY
		PreparedStatement stmt = conn.prepareStatement(UPDATE_CONTENT_TO_DELETE);
		stmt.setLong(1, id);
		stmt.executeUpdate();
		stmt.close();
		conn.close();
	}
	
	/**
	 * DELETE CONTENT
	 * @throws Exception
	 */
	public void deleteContentAll() throws Exception{
		Connection conn = DSManager.getConnection();
																	// RUNNING QUERY
		PreparedStatement stmt = conn.prepareStatement("Delete Content");
		stmt.executeUpdate();
		
		stmt.close();
		conn.close();
	}
	
	/**
	 * ACTIVE CONTENT BY ID
	 * @param id
	 * @throws Exception
	 */
	public void activeContent(Long id) throws Exception{
		Connection conn = DSManager.getConnection();
																	// RUNNING QUERY
		PreparedStatement stmt = conn.prepareStatement(UPDATE_CONTENT_TO_ACTIVE);
		stmt.setLong(1, id);
		stmt.executeUpdate();
		
		stmt.close();
		conn.close();
	}
	
	/**
	 * GET CONTENT VIDEO IN LIST
	 * @param contentID
	 * @return
	 */
	public JSONArray getVideoContentListOfJSON(ArrayList<Long> contentID, Long Channel){
		JSONArray r = new JSONArray();
		
		try
		{
			Iterator<Long> iter = contentID.iterator();
			ContentInfo result = null;
			ContentVideoInfo video=null;
			JSONObject item = null;
			while(iter.hasNext()){
				long posicion = iter.next();
				result = getContentvideo(Channel,posicion);
				video= getContentVideo(posicion);
				item = result.toJSONObject();
				item.put("video", video.getVideo());
				item.put("image", video.getImage());		
				r.put(item);
			}
						
		}catch (Exception e){
			log.error("ContentDAO","getVideoContentListOfJSON",e.toString());
		}
		return r;
	}
	
	/**
	 * GET CONTENT FILE MEDIA IN LIST OF JSON BY CONTENTID
	 * @param contentID
	 * @return
	 */
	public JSONArray getContentFileMediaListOfJSON(Long contentID){
		JSONArray result=new JSONArray();
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		
		try{
																	// RUNNING QUERY
			stmt = conn.prepareStatement(SELECT_MEDIA_BY_CONTENT_ID);
			stmt.setLong(1, contentID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				ContentFileMediaInfo item = new ContentFileMediaInfo();
				
				item.setId(rs.getLong("Mediaid"));
				item.setContentID(contentID);
				item.setName(rs.getString("MediaName"));
				item.setMedia(rs.getString("MediaContent"));
				
				result.put(item.toJSONObject());
			}
		}catch (Exception e){
			log.error("ContentDAO", "getContentFileMediaListOfJSON", e.toString());
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
 * 
 * GET CONTENT FILE OF JESON BY CONTENTID
 * @param contentID
 * @return
 */
	public JSONObject getContentFileOfJSON(Long contentID){
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try{
																	// RUNNING QUERY
			stmt = conn.prepareStatement(SELECT_CONTENT_DESCRIPTION);
			stmt.setLong(1, contentID);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				ContentFileInfo item = new ContentFileInfo();
				item.setContentID(rs.getLong("ContentId"));
				item.setDescription(rs.getString("ContentDescription"));
				
				return item.toJSONObject();
			}
		}catch (Exception e){
			log.error("ContentDAO", "getContentFileOfJSON", e.toString());
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

		return new JSONObject();
	}	

	/**
	 * UPDATE CONTENT DESCRIPTION BY ID
	 * @param contentid
	 * @param description
	 */
	public void updateContentDescriptionById (Long contentid, String description)
	{
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try
		{
																	// RUNNING QUERY
			stmt = conn.prepareStatement(UPDATE_CONTENT_DESCRIPTION);
			stmt.setString(1, description);
			stmt.setLong(2,contentid);
			boolean rs = stmt.execute();
		}catch (Exception e){
			log.error("ContentDAO", "updateContentDescriptionById",e.toString());
		}
		try {
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * GET MEDIA IMAGE BY CONTENTID
	 * @param contentid
	 * @return
	 */
	public String getMediaImage (Long contentid)
	{
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		String image= "";
		try
		{
																	// RUNNING QUERY
			stmt = conn.prepareStatement(SELECT_MEDIA_BY_CONTENT_ID);
			stmt.setLong(1,contentid);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				if (rs.getLong("Filetype_FiletytypeId" )==1)
				{
					image = rs.getString("MediaContent");
				}
				
			}
		}catch (Exception e){
			log.error("ContentDAO","getMediaImage",e.toString());
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
		return image;
	}
	
	/**
	 * GET MEDIA VIDEO CONTENTID
	 * @param contentid
	 * @return
	 */
	public String getMediaVideo (Long contentid)
	{
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		String video= "";
		try
		{
																	// RUNNING QUERY
			stmt = conn.prepareStatement(SELECT_MEDIA_BY_CONTENT_ID);
			stmt.setLong(1,contentid);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				if (rs.getInt("Filetype_FiletytypeId" )==3)
				{
					video = rs.getString("MediaContent");
				}
				
			}
		}catch (Exception e){
			log.error("ContentDAO","getMediaVideo",e.toString());
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
		return video;
	}
	
	
	/**
	 * GET CONTENT NEWS BY CONTENTID
	 * @param contentID
	 * @return
	 */
	public ContentNewsInfo getContentNews2(Long contentID){
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try{
																	// RUNNING QUERY
			stmt = conn.prepareStatement(SELECT_CONTENT_BY_ID);
			stmt.setLong(1, contentID);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				ContentNewsInfo item = new ContentNewsInfo();
				item.setContentID(rs.getLong("ContentId"));
				item.setContent(rs.getString("ContentHtml"));
				item.setImage(getMediaImage(contentID));
				
				return item;
			}
		}catch (Exception e){
			log.error("ContentDAO", "getContentNews", e.toString());
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

		return new ContentNewsInfo();
	}	
	/**
	 * GET CONTENT VIDEO BY CONTENTID
	 * @param contentID
	 * @return
	 */
	public ContentVideoInfo getContentVideo2(Long contentID){
		ContentVideoInfo item = new ContentVideoInfo();
		item.setContentID(contentID);
		item.setVideo(getMediaVideo(contentID));
		item.setImage(getMediaImage(contentID));
		return item;
	}
	
	/**
	 * GET CONTENT MEDIA BY MEDIA CONTENT
	 * @param MediaContent
	 * @return
	 */
	public int getMediaContent(String MediaContent) {
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try {
																	// RUNNING QUERY
			stmt = conn.prepareStatement(SELECT_BY_KEY_FILE);
			stmt.setString(1, MediaContent);
			ResultSet rs = stmt.executeQuery();
			int key;
			while (rs.next()) {

				key = rs.getInt("MediaId");

				return key;
			}
		} catch (Exception e) {
			log.error("MediaDAO", "getFileTypeId", e.toString());
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
		
		return 0;

	}
	
	/**
	 * GET MEDIA IMAGEID BY CONTENTID
	 * @param contentid
	 * @return
	 */
	public int getMediaImageId (Long contentid)
	{
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		int key = 0;
		try
		{
																	// RUNNING QUERY
			stmt = conn.prepareStatement(SELECT_IMAGE_MEDIAID);
			stmt.setLong(1, contentid);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {

				key = rs.getInt("MediaId");

			

			}
		}catch (Exception e) {
			log.error("ContentDAO","getMediaImageId",e.toString());
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
		return key;
	}
	
	/**
	 * UPDATE CONTENT NEW BY CONTENTID, CONTENT, IMAGE 
	 * 
	 * @param contentid
	 * @param content
	 * @param image
	 */
	public void updateContentNews(Long contentid,String content,String image)
	{
		int actualimage = 0;
		int newimage = 0;
		actualimage = getMediaImageId(contentid);
		if (actualimage != 0)
		{
			deleteContentmediaByContentIdAndMediaId(contentid,actualimage);
		}
		newimage = getMediaContent(image);
		if (newimage != 0 )
		{
			insertContentmedia(contentid,newimage);
		}
	}
	
	/**
	 * UPDATE CONTENT VIDEO BY CONTENTID, VIDEO AND IMAGE
	 * @param contentid
	 * @param video
	 * @param image
	 */
	public void updateContentVideo(Long contentid,String video,String image)
	{
		
		int newvideo = 0;
		int newimage = 0;
		deleteContentMediaByContentId(contentid);
		newvideo = getMediaContent(video);
		newimage = getMediaContent(image);
		if (newimage != 0) 
		{
			insertContentmedia(contentid,newimage);
		}
		if (newvideo != 0)
		{
			insertContentmedia(contentid,newvideo);
		}
	}

	/**
	 * GET CHANNELID BY CHANNELNAME	
	 * @param channelname
	 * @return
	 */
	public int getChannelIdByChannelName (String channelname)
	{
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		int key = 0;
		try
		{
																	// RUNNING QUERY
			stmt = conn.prepareStatement(SELECT_CHANNELID_BY_CHANNELNAME);
			stmt.setString(1, channelname);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {

				key = rs.getInt("ChannelId");

			}				
		}catch (Exception e){
			log.error("ContentDao", "getChannelIdByChannelName",e.toString());
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
		return key;
	}
	

	/**
	 * GET GALLEYID FROM CHANNELID
	 * @param channelid
	 * @return
	 */
	public Long getGalleryIdForChannel(Long channelid)
	{
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		Long key = (long)0;
		try
		{
																	// RUNNING QUERY
			stmt = conn.prepareStatement(SELECT_ContentId);
			stmt.setLong(1, channelid);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {

				key = rs.getLong("ContentId");	
			}
		}catch (Exception e){
			log.error("ContentDAO","GetGalleryIdForChannel",e.toString());
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
		return key;
	}
	
	/**
	 * GET FILE CONTENT BY CONTENTID
	 * @param contentID
	 * @return
	 */
	public ArrayList getFileContent(Long contentID){
		
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		ArrayList item = new ArrayList();
		try{
																	// RUNNING QUERY
			stmt = conn.prepareStatement(SELECT_CONTENT_BY_ID);
			stmt.setLong(1, contentID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
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
				
				item.add(result);
			}
		}catch (Exception e){
			log.error("ContentDAO", "getContent", e.toString());
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
		return item;
	}	
	/**
	 * INSERT CONTENT NEW BY CONTENTID, CONTENT AND IMAGE
	 * @param contentid
	 * @param content
	 * @param image
	 */
	public void insertContentNews(Long contentid, String content, String image)
	{
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		int lngcontentkey = 0;
		try
		{
			MediaDAO daomedia = new MediaDAO();
			
			String sql2 = "insert into media(Filetype_FiletypeId,MediaName,MediaContent,MediaCreationDate,MediaUpdateDate,MediaSize,MediaIsNew,MediaUse,Folder_FolderId) " +
		           " values (6,'news" + contentid.toString() +"','"+content.replace("'", "")+"','"+Utils.getToday("-")+"','"+ Utils.getToday("-")+"',"+"1000,0,'E',"+folderSystemNews(daomedia)+")";
																	//RUNNING QUERY
			stmt = conn.prepareStatement(sql2,Statement.RETURN_GENERATED_KEYS);
			stmt.executeUpdate();
			ResultSet generatedKeys = stmt.getGeneratedKeys();
			generatedKeys.next();
			lngcontentkey =  generatedKeys.getInt(1);
			insertContentmedia(contentid,getMediaContent(image));
			insertContentmedia(contentid,lngcontentkey);
		}catch (Exception e){
			log.error("ContentDAO","insertContentNews",e.toString());
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
	
	public Long folderSystemNews(MediaDAO mediaDao) throws NumberFormatException, Exception{

		Long idFolder = (long)0;
		String systemNews = "SystemNews";
		
			if(mediaDao.isValidFolder(systemNews)){
				mediaDao.insertFolder(Long.parseLong("0"), systemNews);
			}
			
			idFolder = mediaDao.idFoderOportunidades(systemNews);
			
			return idFolder;
		}
	
	/**
	 * GET CHANNEL CONTENT  BYE CHANNELID 
	 * @param channel_id
	 * @return
	 */
	public String getChannel_IDcontent(String channel_id){
		String result = "";
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		
		try{
																	// RUNNING QUERY
			stmt = conn.prepareStatement(Select_ContentId);
			stmt.setString(1, channel_id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				
					result = rs.getString(1);
				
			}
		}catch (Exception e){
			log.error("ContentDAO", "getChannelName", e.toString());
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
	
	
	public long getChannel_ID (Long contentid){
		long result = 0 ;
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		
		try{
																	// RUNNING QUERY
			stmt = conn.prepareStatement(Select_Channel_ChannelId);
			stmt.setLong(1, contentid);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				
					result = rs.getInt(1);
				
			}
		}catch (Exception e){
			log.error("ContentDAO", "getChannelName", e.toString());
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
	
	
	/////////////////////////////////////////////////////
	public ArrayList<Long> getContent_ID (Long channel){
		ArrayList<Long> result = new ArrayList<Long>();
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try{
																	// RUNNING QUERY
			stmt = conn.prepareStatement(Selectcontentidvideo);
			stmt.setLong(1, channel);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				
					result.add(rs.getLong("ContentId"));
						
			}
		}catch (Exception e){
			log.error("ContentDAO", "getChannelName", e.toString());
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
	
	//select m.MediaId, m.MediaContent from media m INNER JOIN ContentMedia cm ON m.MediaId=cm.Media_MediaId INNER JOIN content c ON c.ContentId= cm.Content_ContentId where c.Channel_ChannelId=64 and m.Filetype_FiletypeId=3;

	public void getBlobVideo (Long channel)
	{
		ContentVideoInfo video = new ContentVideoInfo();
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		String blob="";
		try{
																	// RUNNING QUERY
			stmt = conn.prepareStatement(SELECTVIDEO);
			stmt.setLong(1, channel);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				
				blob = rs.getString(2);
				video.setVideo(blob);				
			}
		}catch (Exception e){
			log.error("ContentDAO", "getChannelName", e.toString());
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
	}
	
	public void getBlobImagen (Long channel)
	{
		Connection conn = DSManager.getConnection();
		ContentVideoInfo video = new ContentVideoInfo();
		PreparedStatement stmt = null;
		String blob="";
		try{
																	// RUNNING QUERY
			stmt = conn.prepareStatement(SELECTIMAGEN);
			stmt.setLong(1, channel);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				
				blob = rs.getString(2);
				video.setImage(blob);				
			}
		}catch (Exception e){
			log.error("ContentDAO", "getChannelName", e.toString());
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
	}
	
	public ContentInfo getContentvideo(Long contentID, Long posicion){
		ContentInfo result = new ContentInfo();
		ArrayList<String> nom = new ArrayList<String>();
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try{
															// RUNNING QUERY
			stmt = conn.prepareStatement(SELECT_CONTENT_BY_IDVideo);
			stmt.setLong(1, contentID);
			stmt.setLong(2, posicion);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
//				result = new ContentInfo();
				result.setId(contentID);
				result.setName(rs.getString("ContentName"));
				//nom.add(rs.getString("ContentName"));
				//result.setNombre(nom);
			}
		}catch (Exception e){
			log.error("ContentDAO", "getContent", e.toString());
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
	
	//Metod creado por RJ
	public void deleteVisitByContentId(Long contentid) {
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;

		try {
			stmt = conn
					.prepareStatement(DELETE_VISIT_BY_CONTENTID);
			stmt.setLong(1, contentid);

			stmt.executeUpdate();
		} catch (Exception e) {
			log.error("ContentDAO", "deleteVisitByContentId", e.toString());
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

	}
	
	public long getContent_T(Long contentID){
		long id;
		ContentInfo result = new ContentInfo();
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try{
																	// RUNNING QUERY
			stmt = conn.prepareStatement(SELECT_CONTENT_BY_ID_T);
			stmt.setLong(1, contentID);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				
				result.setId(rs.getLong(1));
				
				id=result.getId();
				return id;
			}
		}catch (Exception e){
			log.error("ContentDAO", "getContent", e.toString());
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
		return (Long) null;
	}
	
	
	public int getMediaId(String blobvideo){
		int id=0;
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		String query= "select  MediaId from media where Filetype_FiletypeId = 3 and MediaContent=?";
		try{
																	// RUNNING QUERY
			stmt = conn.prepareStatement(query);
			stmt.setString(1, blobvideo);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				
				id=rs.getInt(1);
				
				
				return id;
			}
		}catch (Exception e){
			log.error("ContentDAO", "getContent", e.toString());
		}
		finally{
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
	
	
	public int getMedia_mediaId(int idmedia, long contentId){
		int id=0;
		String query= "select  Media_MediaId from contentmedia where Content_ContentId = ? and Media_MediaId != ?";
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try{
																	// RUNNING QUERY
			stmt = conn.prepareStatement(query);
			stmt.setLong(1, contentId);
			stmt.setInt(2, idmedia);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				
				id=rs.getInt(1);
				
				
				return id;
			}
		}catch (Exception e){
			log.error("ContentDAO", "getContent", e.toString());
		}
		try {
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;		
	}
	
	
	public String Blobimagen(int idmedia){
		String blob="";
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		String query= "select MediaContent from media where MediaId=?";
		try{
																	// RUNNING QUERY
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, idmedia);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				
				blob=rs.getString("MediaContent");
				
				
				return blob;
			}
		}catch (Exception e){
			log.error("ContentDAO", "getContent", e.toString());
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
	
}	
