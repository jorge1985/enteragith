package com.youandbbva.enteratv.dao;

import java.sql.*;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import com.google.appengine.tools.admin.Utility;
import com.youandbbva.enteratv.DAO;
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
 * 		bbva_content
 * 
 * @author CJH
 *
 */

@Repository("ContentDAO")
public class ContentDAO extends DAO{

	//Declaración de constantes
	
	//Declaración de Tablas
	private final String TABLE_NAME_CONTENT = "content";
	private final String TABLE_NAME_LABEL = "label";
	private final String TABLE_NAME_CONTENTLABEL = "contentlabel";
	private final String TABLE_NAME_CONTENTMEDIA = "contentmedia";
	private final String TABLE_NAME_CHANNEL = "channel";
	private final String TABLE_NAME_CONTENTTYPE = "contenttype";
	private final String TABLE_NAME_MEDIA = "media";
	
	
	//Declaración de columnas
	//Content
//	private final String COLUMN_CONTENT_SELECT = "c.ContentId,c.Channel_ChannelId,t.ContenttypeName,c.ContentName,c.ContentHtml,c.ContentIsVisible," 
//	                                         + "c.ContentPublishDate,c.ContentEndDate,c.ContentStatus,c.ContentShowView";
	private final String COLUMN_CONTENT_SELECT = "c.ContentId,c.Channel_ChannelId,c.Contenttype_ContenttypeId,c.ContentName,c.ContentHtml,c.ContentIsVisible," 
            + "c.ContentPublishDate,c.ContentEndDate,c.ContentStatus,c.ContentShowView";
	private final String COLUMN_CONTENT_SELECT2 = "c.ContentId,c.Channel_ChannelId,c.Contenttype_ContenttypeId,t.ContenttypeName,c.ContentName,c.ContentHtml,c.ContentIsVisible," 
            + "c.ContentPublishDate,c.ContentEndDate,c.ContentStatus,c.ContentShowView";
	
	//private final String COLUMN_CONTENT_INSERT = "Channel_ChannelId,Contenttype_ContenttypeId,ContentName," 
    //                                           + "ContentPublishDate,ContentEndDate,ContentStatus,ContentShowView";
	private final String COLUMN_CONTENT_INSERT = "Channel_ChannelId,Contenttype_ContenttypeId,ContentName,ContentHtml,ContentIsVisible,"
                                                 + "ContentPublishDate,ContentEndDate,ContentStatus,ContentShowView";
	private final String COLUMN_CONTENT_UPDATE = "Channel_ChannelId = ?,Contenttype_ContenttypeId=?,ContentName=?,ContentHtml=?,ContentIsVisible=?," 
                                                 + "ContentPublishDate=?,ContentEndDate=?,ContentStatus=?,ContentShowView=?";
	private final String COLUMN_CONTENT_HTML_UPDATE = "ContentHTML = ?";
	private final String COLUMN_CONTENT_COUNT = "count(ContentId)";
	private final String COLUMN_LABEL_COUNT_BY_ID = "count(LabelId)";
	private final String COLUMN_CONTENT_UPDATE_DESCRIPTION = " ContentDescription = ?";
	
	//Contentlabel
	private final String COLUMN_CONTENTLABEL_SELECT = "ContentlabelId,Content_ContentId,Label_labelId";
	private final String COLUMN_CONTENTLABEL_INSERT = "Content_ContentId,Label_labelId";

	
	
	//Contentmedia
	private final String COLUMN_CONTENTMEDIA_SELECT = "ContentmediaId,Content_ContentId,Media_MediaId";
	private final String COLUMN_CONTENTMEDIA_INSERT = "Content_ContentId,Media_MediaId";
		
	//Channel
	private final String COLUMN_CHANNEL_SELECT = "h.ChannelId,h.Family_FamilyId,h.ChannelName,h.ChannelPosition,h.ChannelFather,h.ChannelEmail,h.ChannelPassword,h.ChannelSecurityLevel,h.ChannelIsVisible";
	
	//Media
	private final String COLUMN_MEDIA_SELECT = "m.MediaName, m.MediaContent, m.Filetype_FiletypeId, m.MediaId";
	
	//Label
	private final String COLUMN_LABEL_SELECT = "LabelId,LabelText";
	private final String COLUMN_LABEL_SELECT_COUNT_BY_ID = "count(LabelId)";
	private final String COLUMN_LABEL_INSERT = "LabelText";
	
	//Querys
	//Label
	//Busqueda de la etiqueta por texto
	private final String SELECT_LABEL_BY_TEXT = "select " + COLUMN_LABEL_SELECT + " from " + TABLE_NAME_LABEL + " where LabelText = ?";
	//Busqueda de la etiqueta por id
	private final String SELECT_LABEL_BY_ID   = "select " + COLUMN_LABEL_SELECT + " from " + TABLE_NAME_LABEL + " where LabelId =?";
	//Cuenta si existen ocurrencias con otr diferente al id
	private final String SELECT_LABEL_BY_ID_DF   = "select " + COLUMN_LABEL_SELECT_COUNT_BY_ID + " from " + TABLE_NAME_LABEL + " where LabelId <> ?";
	//Cuenta si existen ocurrencias pasando como parametro el text de la etiqueta
	private final String COUNT_LABEL_BY_TEXT   = "select " + COLUMN_LABEL_COUNT_BY_ID + " from " + TABLE_NAME_LABEL + " where LabelText = ?";
	//Inserta etiqueta
	private final String INSERT_LABEL         = "insert into " + TABLE_NAME_LABEL + "(" + COLUMN_LABEL_INSERT + ") values (?)";
	//Borra etiqueta
	private final String DELETE_LABEL         = "delete " + TABLE_NAME_LABEL + " where LabelId = ?";
	//Consulta las etiquetas asociadas a un ContentId
	private final String SELECT_LABELTEXT_BY_CONTENTID = "select " + COLUMN_LABEL_INSERT + " from " + TABLE_NAME_LABEL + " a, " + TABLE_NAME_CONTENTLABEL + " b where a.LabelId = b.Label_LabelId and b.Content_ContentId = ?";
	//Consulta Todas las etiquetas disponibles
	private final String SELECT_LABEL_ALL = "select " + COLUMN_LABEL_SELECT + " from " + TABLE_NAME_LABEL;  		
			
			
	
	//Content
	//Busqueda por id
	private final String TYPE_EXPIRED_COUNT = " and ContentEndDate < ? ) or ( ContentStatus = 'I')) ";
	private final String SELECT_CONTENT_BY_ID = "select " + COLUMN_CONTENT_SELECT + " from " + TABLE_NAME_CONTENT +  " c, " + TABLE_NAME_CONTENTTYPE + " t  where c.Contenttype_ContenttypeId = t.ContenttypeId and c.ContentId =?";
	//Busqueda por id y visible = 'T'
	private final String SELECT_CONTENT_BY_ID_V = "select " + COLUMN_CONTENT_SELECT + " from " + TABLE_NAME_CONTENT + " where ContentId =? and ContentVisible = 1";
	//Busqueda por id y que sea visible
	private final String SELECT_CONTENT_BY_ID_VISIBLE = "select " + COLUMN_CONTENT_SELECT + " from " + TABLE_NAME_CONTENT + " where ContentId =? and ContentIsVisible = '1'";
	//Inserta contenido
	private final String INSERT_CONTENT         = "insert into " + TABLE_NAME_CONTENT + " (" + COLUMN_CONTENT_INSERT + ") values (?,?,?,?,?,?,?,?,?)";
	//Actualiza Contenido por Id
	private final String UPDATE_CONTENT         = "update " + TABLE_NAME_CONTENT + " set " + COLUMN_CONTENT_UPDATE + " where ContentId=?";
	//Actualiza Contenido Html por Id
	private final String UPDATE_CONTENTHTML         = "update " + TABLE_NAME_CONTENT + " set  ContentHtml = ? where ContentId=?";
	//Borra etiqueta
	private final String DELETE_CONTENT         = "delete " + TABLE_NAME_CONTENT + " where ContentId = ?";
	//Regresa el nombre del canal asociado a un contenido
	private final String SELECT_CONTENT_CHANNEL_BY_CONTENTID = "select "+ COLUMN_CHANNEL_SELECT +" from " + TABLE_NAME_CHANNEL + " h, " + TABLE_NAME_CONTENT + " b where h.ChannelId = b.Channel_ChannelId and b.ContentId = ? ";
	//Regresa el numero de contenidos activos
	private final String COUNT_CONTENT_BY_TYPE  = "select " + COLUMN_CONTENT_COUNT + " from " + TABLE_NAME_CONTENT + "  where (( ContentStatus = ?";
	//Regresa el numero de contenidos activos
	private final String COUNT_CONTENT_EXPIRED  = "select " + COLUMN_CONTENT_COUNT + " from " + TABLE_NAME_CONTENT + "  where ContentStatus <> 'A' and ContentEndDate < ?";
	//Regresa consulta de todos los resgistros de un tipo
	private final String SELECT_CONTENT_BY_TYPE  = "select " + COLUMN_CONTENT_SELECT2 + " from " + TABLE_NAME_CONTENT + " c, " + TABLE_NAME_CONTENTTYPE + " t where ((c.Contenttype_ContenttypeId = t.ContenttypeId and c.ContentStatus =  ?";
	private final String TYPE_ACTIVE  = " and ContentEndDate >= ? ))";
	private final String TYPE_EXPIRED = " and ContentEndDate < ? ) or (c.Contenttype_ContenttypeId = t.ContenttypeId and ContentStatus = 'I')) ";
	//Regresa consulta de todos los resgistros de un tipo
	private final String SELECT_CONTENT_BY_SEARCHTERM  = " and (CONTENTNAME like ? )";
	private final String SELECT_LIMIT = " limit ?,?";
	private final String SELECT_ORDER = " order by ? ?";
	//Actualiza el HTML del contenido
	private final String UPDATE_CONTENT_HTML_BY_CONTENTID = "update " + TABLE_NAME_CONTENT + " set " + COLUMN_CONTENT_HTML_UPDATE + " where ContentId = ?";
	//Actualiza el estatus de un contenido a remove
	private final String UPDATE_CONTENT_TO_REMOVE = " update " + TABLE_NAME_CONTENT + " set ContentStatus= 'D' where ContentId=? ";
	//Actualiza el estatus de un contenido a papelera
	private final String UPDATE_CONTENT_TO_DELETE = " update " + TABLE_NAME_CONTENT + " set ContentStatus='B' where ContentId=? ";
	//Actualiza el estatus de un contenido a papelera
	private final String UPDATE_CONTENT_TO_ACTIVE = " update " + TABLE_NAME_CONTENT + " set ContentStatus='A' where ContentId=? ";
	//Obtiene la descripción de un contenido
	private final String SELECT_CONTENT_DESCRIPTION = " select ContentId,ContentDescription from " + TABLE_NAME_CONTENT + " where ContentId=? " ;
	//Actualiza la descripcion del contenido con base al ContentId
	private final String UPDATE_CONTENT_DESCRIPTION = "update " + TABLE_NAME_CONTENT + " set " + COLUMN_CONTENT_UPDATE_DESCRIPTION + " where ContentId = ?";
	
	//Contentlabel
	//Busqueda por LabelId
	private final String SELECT_CONTENTLABEL_BY_LABELID   = "select " + COLUMN_CONTENTLABEL_SELECT + " from " + TABLE_NAME_CONTENTLABEL + " where Label_labelId =?";
	//Busqueda por ContentId
	private final String SELECT_CONTENTLABEL_BY_CONTENTID   = "select " + COLUMN_CONTENTLABEL_SELECT + " from " + TABLE_NAME_CONTENTLABEL + " where Content_ContentId =?";
	//Inserta Contenlabel
	private final String INSERT_CONTENTLABEL         = "insert into " + TABLE_NAME_CONTENTLABEL + "(" + COLUMN_CONTENTLABEL_INSERT + ") values (?,?)";
	//Borra Contentlabel por ContentId 
	private final String DELETE_CONTENTLABEL_BY_CONTENTID         = "delete from " + TABLE_NAME_CONTENTLABEL + " where Content_ContentId= ?";
	//Borra Contentlabel por ContentId 
	private final String DELETE_CONTENTLABEL_BY_CONTENTID_AND_LABELID  = "delete from " + TABLE_NAME_CONTENTLABEL + " where Content_ContentId= ? and Label_LabelId";
	
	//Contentmedia
	//Busqueda por Id
	//private final String SELECT_CONTENTMEDIA_BY_ID   = "Select " + COLUMN_CONTENTMEDIA_SELECT + " from " + TABLE_NAME_CONTENTMEDIA + " where ContentmediaId =?";
	//Busqueda por ContentId
	private final String SELECT_CONTENTMEDIA_BY_CONTENTID   = "select " + COLUMN_CONTENTMEDIA_SELECT + " from " + TABLE_NAME_CONTENTMEDIA + " where Content_ContentId =?";
	//Busqueda por MediaId
	private final String SELECT_CONTENTMEDIA_BY_MEDIAID   = "select " + COLUMN_CONTENTMEDIA_SELECT + " from " + TABLE_NAME_CONTENTMEDIA + " where Media_MediaId =?";
	//Inserta Contentemedia
	private final String INSERT_CONTENTMEDIA = "insert into " + TABLE_NAME_CONTENTMEDIA + "(" + COLUMN_CONTENTMEDIA_INSERT + ") values (?,?)";
	//Borra Contentmedia cuando se borra un contenido
	private final String DELETE_CONTENTMEDIA_BY_CONTENTID         = "delete from " + TABLE_NAME_CONTENTMEDIA + " where Content_ContentId = ?";
	//Borra Contentmedia cuando se borra un media
	private final String DELETE_CONTENTMEDIA_BY_MEDIAID         = "delete from " + TABLE_NAME_CONTENTMEDIA + " where Media_MediaId = ?";
	//Borra Contentmedia especificamente una relacion de un contenido con un media
	//private final String DELETE_CONTENTMEDIA_BY_CONTENTID_AND_MEDIAID         = "delete from" + TABLE_NAME_CONTENTMEDIA + " where Content_ContentId = ? and Media_MediaId = ?";

	private final String DELETE_CONTENTMEDIA_BY_CONTENTID_AND_MEDIAID         = "delete from " + TABLE_NAME_CONTENTMEDIA + " where Content_ContentId = ? and Media_MediaId = ?";
	
	//Channel
	private final String SELECT_CHANNEL_BY_CONTENT_ID = "select " + COLUMN_CHANNEL_SELECT + " from " + TABLE_NAME_CHANNEL + " h, " + TABLE_NAME_CONTENT + " c where h.ChannelId = c.Channel_ChannelId and c.ContentId = ?";
	private final String SELECT_CHANNELID_BY_CHANNELNAME = "select ChannelId from " + TABLE_NAME_CHANNEL + " where ChannelName = ?"; 
	
	
	//Media
	private final String SELECT_MEDIA_BY_CONTENT_ID  = " select " + COLUMN_MEDIA_SELECT + " from " + TABLE_NAME_MEDIA + " m, " + TABLE_NAME_CONTENTMEDIA + " c where c.Media_MediaId = m.MediaId and c.Content_ContentId = ?";
	private final String SELECT_BY_KEY_FILE = " select  MediaId" + " from "	+ TABLE_NAME_MEDIA + " where MediaContent = ?";
	private final String SELECT_IMAGE_MEDIAID = "select m.MediaId from " + TABLE_NAME_MEDIA + " m, " + TABLE_NAME_CONTENTMEDIA + " c where c.Media_MediaId = m.MediaId and m.Filetype_FiletypeId = 1 and c.Content_ContentId = ?";
	
	
	public ContentDAO() {
		// TODO Auto-generated constructor stub
	}

	public ContentDAO(Connection conn) {
		// TODO Auto-generated constructor stub
		super(conn);
	}

	/**
	 * Check whether valid name or not.
	 * 
	 * @param name
	 * @param folderID
	 * @return boolean
	 */
	//Cuenta si existen ocurrencias pasando como parametro el text de la etiqueta
	//private final String COUNT_LABEL_BY_TEXT   = "Select " + COLUMN_LABEL_COUNT_BY_ID + " from " + TABLE_NAME_LABEL + " where LabelText = ?";
	//private final String COLUMN_LABEL_COUNT_BY_ID = "count(LabelId)";
	
	
	//Métodos
	//Label
	public boolean labelTextExist(String text) {
		try {
			long result = 0;
			PreparedStatement stmt = conn
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

		return false;
	}

	
	
	//Busqueda de la etiqueta por texto
	//private final String SELECT_LABEL_BY_TEXT = "Select " + COLUMN_LABEL_SELECT + " from " + TABLE_NAME_LABEL + " where LabelText =?";
	
	
	public int getLabelIdByText(String text) {
		int result = 0;
		String query = "select LabelId,LabelText from label where LabelText = ?";
		try {
			
			PreparedStatement stmt = conn.prepareStatement(SELECT_LABEL_BY_TEXT);
//				.prepareStatement(SELECT_LABEL_BY_TEXT);
			stmt.setString(1, text);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				result = rs.getInt("LabelId");
			}
			return result;
		
		} catch (Exception e) {
			log.error("ContentDAO", "getLabelIdByText", e.toString());
		}

		return 0;
	}
	
	//Inserta etiqueta
	//private final String INSERT_LABEL         = "Insert into " + TABLE_NAME_LABEL + "(" + COLUMN_LABEL_INSERT + ") values (?)";
	
	public void insertLabel(String text) {
		
		try {
			
			PreparedStatement stmt = conn
					.prepareStatement(INSERT_LABEL);
			stmt.setString(1, text);
			stmt.executeUpdate();
				
		}catch (Exception e) {
			log.error("ContentDAO", "insertlabel", e.toString());
		}
	}

	//Borra etiqueta
	//private final String DELETE_LABEL         = "Delete " + TABLE_NAME_LABEL + " where LabelId = ?";	

	public void deleteLabel(int id) {
			
			try {
				
				PreparedStatement stmt = conn
						.prepareStatement(DELETE_LABEL);
				stmt.setInt(1, id);
				stmt.executeQuery();
					
			} catch (Exception e) {
				log.error("ContentDAO", "getLabelIdByText", e.toString());
			}
	}
	//Content
	//Busqueda por id
	//private final String SELECT_CONTENT_BY_ID = "Select " + COLUMN_CONTENT_SELECT + " from " + TABLE_NAME_CONTENT + " where ContentId =?";	
	
	public ContentInfo getContentByContentIdAll(Long contentid) {
		try {
					
			PreparedStatement stmt = conn
					.prepareStatement(SELECT_CONTENT_BY_ID);
			stmt.setLong(1, contentid);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				ContentInfo result = new ContentInfo();
				result.setId(rs.getLong(1));
				result.setName(rs.getString(4));
				result.setHtml(Utils.checkNull(rs.getString(5)));
				//result.setIsVisble(rs.getBoolean(rs.getBoolean("ContentIsVisible"));
				result.setValidityStart(rs.getString(7));
				result.setValidityEnd(rs.getString(8));
				result.setStatus(rs.getString(9));
				result.setShowIn(rs.getString(10));
				
				return result;
			}
					
		} catch (Exception e) {
			log.error("ContentDAO", "getContentByContentIdAll", e.toString());
		}

		return null;
	}
	
	
	//Busqueda por id y visible = 'T'
	//private final String SELECT_CONTENT_BY_ID_V = "Select " + COLUMN_CONTENT_SELECT + " from " + TABLE_NAME_CONTENT + " where ContentId =? and ContentVisible = 1";	
	public ContentInfo getContentByContentIdVis(int contentid) {
		try {
					
			PreparedStatement stmt = conn
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

		return null;
	}
	
	
	//Inserta contenido
	//private final String INSERT_CONTENT         = "Insert into " + TABLE_NAME_CONTENT + "(" + COLUMN_CONTENT_INSERT + ") values (?,?,?,?,?,?,?,?,?)";
	//private final String COLUMN_CONTENT_INSERT = "Channel_ChannelId,Contenttype_ContenttypeId,ContentName,ContentHtml,ContentIsVisible," 
    //        + "ContentPublishDate,ContentEndDate,ContentStatus,ContentShowView";
	
	public Long  insertContent(int channelid, int contenttypeid, String name, String html,int isvisible, String publishdate,String enddate,String status,String showview  ) {	
		try {
			PreparedStatement stmt = conn
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
		return (long)0;
	}
	
	//Actualiza Contenido por Id
	//private final String UPDATE_CONTENT         = "Update " + TABLE_NAME_CONTENT + " set " + COLUMN_CONTENT_UPDATE + " where ContentId=?";	
	//private final String COLUMN_CONTENT_UPDATE = "Channel_ChannelId = ?,Contenttype_ContenttypeId=?,ContentName=?,ContentHtml=?,ContentIsVisible=?," 
    //        + "ContentPublishDate=?,ContentEndDate=?,ContentStatus=?,ContentShowView=?";

	public String getContentSta(long contentid) {
		String result="";
		try {
			String sql = "SELECT ContentStatus FROM content WHERE ContentId = ?";	
			PreparedStatement stmt = conn
					.prepareStatement(sql);
			stmt.setLong(1, contentid);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {

				result = rs.getString("ContentStatus");
				
				return result;
			}
					
		} catch (Exception e) {
			log.error("ContentDAO", "getContentByContentIdAll", e.toString());
		}

		return null;
	}
	
	public void updateContent(int channelid, int contenttypeid, String name, String html,int isvisible, String publishdate,String enddate,String status,String showview,Long contentid  )
	{
		try {
			String result;
			PreparedStatement stmt = conn
					.prepareStatement(UPDATE_CONTENT);
			stmt.setInt(1, channelid);
			stmt.setInt(2, contenttypeid);
			stmt.setString(3,name);
			stmt.setString(4, null);
			stmt.setInt(5, isvisible);
			stmt.setString(6, publishdate);
			stmt.setString(7, enddate);
			
			result = getContentSta(contentid);
			
			if(result.equals("D")){
				stmt.setString(8,"D");
			}else{
				if (isvisible == 0) {
					stmt.setString(8,"I");
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
	}	
	
	//Borra contenido
	//private final String DELETE_CONTENT         = "Delete " + TABLE_NAME_CONTENT + " where ContentId = ?";	
	
	public void deleteContent(int contentid)
	{
		try {
			PreparedStatement stmt = conn
					.prepareStatement(DELETE_CONTENT);
			stmt.setInt(1, contentid);
			
			stmt.executeQuery();
		}catch (Exception e) {
			log.error("ContentDAO", "deleteContent", e.toString());
		}
			
	}
	
	
	//Busqueda por LabelId
	//private final String COLUMN_CONTENTLABEL_SELECT = "ContentlabelId,Content_ContentId,Label_labelId";
	//private final String SELECT_CONTENTLABEL_BY_LABELID   = "Select " + COLUMN_CONTENTLABEL_SELECT + " from " + TABLE_NAME_CONTENTLABEL + " where Label_labelId =?";
	
	public ArrayList getContentlabelByLabelId(int labelid)
	{
		ArrayList item = new ArrayList();
		try {
			PreparedStatement stmt = conn
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
			
		}
		return item;
	}
	
	//Busqueda por ContentId
	//private final String SELECT_CONTENTLABEL_BY_CONTENTID   = "Select " + COLUMN_CONTENTLABEL_SELECT + " from " + TABLE_NAME_CONTENTLABEL + " where Content_ContentId =?";
	
	public ArrayList getContentlabelByContentId(int contentid)
	{
		ArrayList item = new ArrayList();
		try {
			PreparedStatement stmt = conn
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
		return item;
	}
	
	//Inserta Contenlabel
	//private final String INSERT_CONTENTLABEL         = "Insert into " + TABLE_NAME_CONTENTLABEL + "(" + COLUMN_CONTENTLABEL_INSERT + ") values (?,?)";
	
	public int insertContentlabel(Long contentid,int labelid)
	{
		try {
			
			PreparedStatement stmt = conn
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
		return 0;
	}
	
	
	//Borra Contentlabel por ContentId 
	//private final String DELETE_CONTENTLABEL_BY_CONTENTID         = "Delete " + TABLE_NAME_CONTENTLABEL + " where Content_ContentId= ?";	
	
	public void deleteContentlabelByContentId(Long contentid)
	{
		try
		{
			PreparedStatement stmt = conn
					.prepareStatement(DELETE_CONTENTLABEL_BY_CONTENTID);
			stmt.setLong(1, contentid);
			stmt.executeUpdate();
			
		}catch (Exception e){
			log.error("ContentDAO","deleteContentlabelByContnetId",e.toString());
		}
	}

	
	
	//Borra Contentlabel por ContentId 
	//private final String DELETE_CONTENTLABEL_BY_CONTENTID_AND_LABELID  = "Delete " + TABLE_NAME_CONTENTLABEL + " where Content_ContentId= ? and Label_LabelId";
	
	public void deleteContentlabelByContentIdAndLabelId (int contentid,int labelid)
	{
		try 
		{
			PreparedStatement stmt = conn
					.prepareStatement(DELETE_CONTENTLABEL_BY_CONTENTID_AND_LABELID);
			stmt.setInt(1, contentid);
			stmt.setInt(2, labelid);
			stmt.execute();
		}catch (Exception e){
			log.error("ContentDAO","deleteContentlabelByContentIdAndLabelId",e.toString());
		}
	}
	
	
	//Busqueda por ContentId
	//private final String SELECT_CONTENTMEDIA_BY_CONTENTID   = "Select " + COLUMN_CONTENTMEDIA_SELECT + " from " + TABLE_NAME_CONTENTMEDIA + " where Content_ContentId =?";
	//private final String COLUMN_CONTENTMEDIA_SELECT = "ContentmediaId,Content_ContentId,Media_MediaId";
	
	public ArrayList getContentmediaByContentId(int contentid)
	{
		ArrayList item = new ArrayList();
		try {
			PreparedStatement stmt = conn
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
			return item;
	}
	
	
	//Busqueda por MediaId
	//private final String SELECT_CONTENTMEDIA_BY_MEDIAID   = "Select " + COLUMN_CONTENTMEDIA_SELECT + " from " + TABLE_NAME_CONTENTMEDIA + " where Media_MediaId =?";
	
	public ArrayList getContentmediaByMediaId(int mediaid)
	{
		ArrayList item = new ArrayList();
		try {
			PreparedStatement stmt = conn
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
		return item;
	}
		
	
	//Inserta Contentemedia
	//private final String INSERT_CONTENTMEDIA = "Insert into " + TABLE_NAME_CONTENTMEDIA + "(" + COLUMN_CONTENTMEDIA_INSERT + ") values (?,?)";	
	
	public int insertContentmedia (Long contentid,int mediaid)
	{
		try {
			
			PreparedStatement stmt = conn
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
		return 0;
	}
	
	//Borra Contentmedia cuando se borra un contenido
	//private final String DELETE_CONTENTMEDIA_BY_CONTENTID         = "Delete " + TABLE_NAME_CONTENTMEDIA + " where Content_ContentId = ?";
	
	public void deleteContentMediaByContentId (Long contentid)
	{
		
		try
		{
			PreparedStatement stmt = conn
					.prepareStatement(DELETE_CONTENTMEDIA_BY_CONTENTID);
			stmt.setLong(1, contentid);
			stmt.executeUpdate();
			
		}catch (Exception e){
			log.error("ContentDAO","deleteContentlabelByContentId",e.toString());
		}
	}
	//Borra Contentmedia cuando se borra un media
	//private final String DELETE_CONTENTMEDIA_BY_MEDIAID         = "Delete " + TABLE_NAME_CONTENTMEDIA + " where Media_MediaId = ?";	
	
	public void deleteContentMediaByMediaId (int mediaid)
	{
		try
		{
			PreparedStatement stmt = conn
					.prepareStatement(DELETE_CONTENTMEDIA_BY_MEDIAID);
			stmt.setInt(1, mediaid);
			stmt.executeQuery();
			
		}catch (Exception e){
			log.error("ContentDAO","deleteContentlabelByMediaId",e.toString());
		}
	}
	
	
	//Borra Contentlabel por ContentId and LabelId
	//private final String DELETE_CONTENTLABEL_BY_CONTENTID_AND_LABELID  = "Delete " + TABLE_NAME_CONTENTLABEL + " where Content_ContentId= ? and Label_LabelId";
	
/*	public void deleteContentlabelByContentIdAndLabelId (int contentid,int labelid)
	{
		try
		{
			PreparedStatement stmt = conn
					.prepareStatement(DELETE_CONTENTLABEL_BY_CONTENTID_AND_LABELID);
			stmt.setInt(1, contentid);
			stmt.setInt(2, labelid);
			stmt.executeQuery();
			
		}catch (Exception e){
			log.error("ContentDAO","deleteContentlabelByContentidAndLabelId",e.toString());
		}
	}     */
	

	//Borra Contentmedia especificamente una relacion de un contenido con un media
	//private final String DELETE_CONTENTMEDIA_BY_CONTENTID_AND_MEDIAID         = "Delete " + TABLE_NAME_CONTENTMEDIA + " where Content_ContentId = ? and Media_MediaId = ?";
	
	public void deleteContentmediaByContentIdAndMediaId (Long contentid,int mediaid)
	{
		try
		{
			PreparedStatement stmt = conn
					.prepareStatement(DELETE_CONTENTMEDIA_BY_CONTENTID_AND_MEDIAID);
			stmt.setLong(1, contentid);
			stmt.setInt(2, mediaid);
			stmt.executeUpdate();
			
		}catch (Exception e){
			log.error("ContentDAO","deleteContentmediaByContentIdAndMediaId",e.toString());
		}
	}
	
	
	
	//Consulta las etiquetas asociadas a un ContentId
	//private final String SELECT_LABELTEXT_BY_CONTENTID = "Select " + COLUMN_LABEL_INSERT + " from " + TABLE_NAME_LABEL + " a, " + TABLE_NAME_CONTENTLABEL + " b where a.LabelId = b.Label_LabelId and b.ContentId = ?"	
	
	public ArrayList getLabelTextByContentId(Long contentid)
	{
		ArrayList item = new ArrayList();
		try
		{
			PreparedStatement stmt = conn
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
		return null;
	}
	
	
	
	///////////////////////////////////
	
	
	
	
	
	public ArrayList getTagList(Long content_id){
		ArrayList result = new ArrayList();
		try{
			PreparedStatement stmt = conn.prepareStatement(SELECT_LABELTEXT_BY_CONTENTID);
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

		return result;
	}
	
	
	
	///////////////////////////////////
	
	
	
	
	
	//Regresa el nombre del canal asociado a un contenido
	//private final String SELECT_CONTENT_CHANNEL_BY_CONTENTID = "Select a.ChannelName from " + TABLE_NAME_CHANNEL + " a, " + TABLE_NAME_CONTENT + " b where a.ChannelId = b.Channel_ChannelId and b.ContentId = ? "
	
//	public ArrayList getContentChannelList(Long contentid)
//	{
//		ArrayList item = new ArrayList();
//		try 
//		{
//			PreparedStatement stmt = conn
//					.prepareStatement(SELECT_CONTENT_CHANNEL_BY_CONTENTID);
//			stmt.setLong(1, contentid);
//		
//			ResultSet rs =stmt.executeQuery();
//			while (rs.next())
//			{
//				String result = rs.getString("ChannelName");
//				item.add(result);
//			}
//		}catch (Exception e){
//			log.error("ContentDAO","getContentChannelList",e.toString());
//		}
//		return item;
//	}

	
	
	
	/////////////////
	
	
	public ArrayList getContentChannelList(Long contentID){
		ArrayList result=new ArrayList();
		
		try{
			PreparedStatement stmt = conn.prepareStatement(SELECT_CHANNEL_BY_CONTENT_ID);
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

		return result;
	}	
	
	
	
	
	////////////////
	
	
	
	
	
	
	
	
	
	
	//Consulta Todas las etiquetas disponibles
	//private final String SELECT_LABEL_ALL = "Select " + COLUMN_LABEL_SELECT + " from " + TABLE_NAME_LABEL;	
	
	public String getAllTag(){
		JSONArray result = new JSONArray();
		try{
			PreparedStatement stmt = conn.prepareStatement(SELECT_LABEL_ALL);
//			stmt.setString(1, Utils.getToday("-"));
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				result.put(Utils.checkNull(rs.getString("LabelText")));
			}
		}catch (Exception e){
			log.error("ContentDAO", "getAllTag",e.toString());
		}

		return result.toString();
	}
	
	//Regresa el numero de contenidos activos
	//private final String COUNT_CONTENT_BY_TYPE  = "Select " + COLUMN_CONTENT_COUNT + " from " + TABLE_NAME_CONTENT + "  where ContentStatus = '?'"	
	
	public long getCountByType(String contenttype)
	{
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
			PreparedStatement stmt = conn.prepareStatement(sql);
//			stmt.setString(1, Utils.getToday("-"));
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
		return result;
	}

		
		//El Siguiente es código de Smartmedia
		//Ejecuta consultas SQL y las regresa en un arreglo Json
	
		public JSONArray getContentS(String sql){
			JSONArray result = new JSONArray();
			
			try{
				PreparedStatement stmt = conn.prepareStatement(sql);
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
					//Towa RJR El Status se asi a activo porque este es el campo que contiene informacion
					item.put("active", rs.getString("ContentStatus"));
					//Towa RJR El status en el código de samtmedia siempre es cero
					item.put("status", "A");
					//Towa RJR Este campos ya no se usa, por lo que se mando un valor fijo 
					item.put("blog", "blog");
					//Towa RJR se cambio la lógica de los siguientes dos metodos en el nuevo ContentDAO
					item.put("tag", getTagName(contentID));
					item.put("channel", getContentChannel(contentID));
					
					result.put(item);
				}
			}catch (Exception e){
				log.error("ContentDAO", "getContent", e.toString());
			}
			
			return result;
		}
		
	//El siguiente es código de SmartMedia
		
		public String getTagName(int contentID){
			String result="";
			int count = 0;
			
			try{
				PreparedStatement stmt = conn.prepareStatement(SELECT_LABELTEXT_BY_CONTENTID);
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
			}

			if (count>1)
				result += " and <b>" + (count-1) + " more</b>";
			
			return result;
		}
	
		
    //El siguiente es código de Smartmedia
		
		public String getContentChannel(int contentID){
			String result="";
			int count = 0;
			
			try{
				PreparedStatement stmt = conn.prepareStatement(SELECT_CONTENT_CHANNEL_BY_CONTENTID);
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

			if (count>1)
				result += " and <b>" + (count-1) + " more</b>";
			
			return result;
		}
	
		
	//El siguiente código es de Samtmedia
		
		
		public Long getCount(String sql){
			Long result = (long)0;
			
			try{
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery();
				if (rs.next()){
					result = rs.getLong(1);
				}
			}catch (Exception e){
				log.error("ContentDAO", "getCount", e.toString());
				result = (long)0;
			}
			
			return result;
		}	
	
		
		
	//Regresa consulta de todos los resgistros de un tipo
	//private final String SELECT_CONTENT_BY_TYPE  = "Select " + COLUMN_CONTENT_SELECT + " from " + TABLE_NAME_CONTENT + "  where ContentType=  '?'";
	//Regresa consulta de todos los resgistros de un tipo
	//private final String SELECT_CONTENT_BY_SEARCHTERM  = " and CONTENTNAME like '%?% order by ContenName";	
		
	public ContentCountInfo getContentByType(String type, String searchterm, String columna, String orden,int inicio,int registros)
	{
		JSONArray result = new JSONArray();
		Long lngregs = (long)0;
		int intparam = 2;
		ContentCountInfo conteninfo = new ContentCountInfo();
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
								
			
			PreparedStatement stmt = conn.prepareStatement(sql);
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
				//Towa RJR El Status se asi a activo porque este es el campo que contiene informacion
//				status = rs.getChar("ContentStatus");
//				if (type == "A")
//				{
//					item.put("status","0");
//				    item.put("active", "1");
//				}else {
//					item.put("status", "1");
//					item.put("active", "0");
//				}
				  item.put("status", rs.getString("ContentStatus"));
				   item.put("active", rs.getString("ContentIsVisible"));
//				item.put("active", rs.getString("ContentStatus"));
//				item.put("active", "0");
				//Towa RJR El status en el código de samtmedia  es 1: Eliminados se comenta la sig linea
//				item.put("active", "1");
				//Towa RJR Este campos ya no se usa, por lo que se mando un valor fijo 
				item.put("blog", "blog");
				//Towa RJR se cambio la lógica de los siguientes dos metodos en el nuevo ContentDAO
				item.put("tag", getTagName(contentID));
				item.put("channel", getContentChannel(contentID));
				
				result.put(item);	
				lngregs = lngregs + 1;
		}
			conteninfo.setRecords(lngregs);
			conteninfo.setJSONArray(result);
			
		}catch (Exception e){
			log.error("ContentDAO","getContentByType",e.toString());
		}
		
		return conteninfo;	
		
	}
	
	
	
	///////SE DUPLICA EL METODO SOLO PARA OBTNER EL CONTEO SIN LOS LIMITES
	
	public Long getContentCountByType(String type, String searchterm)
	{
//		JSONArray result = new JSONArray();
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
//			sql = sql + " order by " + columna + " " + orden + " " + SELECT_LIMIT;
								
			
			PreparedStatement stmt = conn.prepareStatement(sql);
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
			

//			stmt.setInt(intparam, inicio);
//			intparam = intparam + 1;
//			stmt.setInt(intparam, registros);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
//				JSONObject item = new JSONObject();
				
				lngregs = rs.getLong(1);
/*				item.put("id", contentID);
				item.put("name", rs.getString("ContentName"));
				item.put("type", rs.getString("Contenttype_ContenttypeId"));
				item.put("type_name",rs.getString("ContenttypeName"));
						
				item.put("show_in", rs.getString("ContentShowView"));
				item.put("validity", rs.getString("ContentPublishDate") + " ~ " + rs.getString("ContentEndDate"));
				//Towa RJR El Status se asi a activo porque este es el campo que contiene informacion
//				status = rs.getChar("ContentStatus");
				if (type == "A")
				{
					item.put("status","0");
				    item.put("active", "1");
				}else {
					item.put("status", "1");
					item.put("active", "0");
				}
//				item.put("active", rs.getString("ContentStatus"));
//				item.put("active", "0");
				//Towa RJR El status en el código de samtmedia  es 1: Eliminados se comenta la sig linea
//				item.put("active", "1");
				//Towa RJR Este campos ya no se usa, por lo que se mando un valor fijo 
				item.put("blog", "blog");
				//Towa RJR se cambio la lógica de los siguientes dos metodos en el nuevo ContentDAO
				item.put("tag", getTagName(contentID));
				item.put("channel", getContentChannel(contentID));
				
				result.put(item);	
				lngregs = lngregs + 1; */
		}
		//	conteninfo.setRecords(lngregs);
		//	conteninfo.setJSONArray(result);
			
		}catch (Exception e){
			log.error("ContentDAO","getContentByType",e.toString());
		}
		
		return lngregs;	
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	///////////
	
	
	
	
	
	//Código de Smarmedia
	
	//private final String COLUMN_CONTENT_SELECT = "ContentId,Channel_ChannelId,Contenttype_ContenttypeId,ContentName,ContentHtml,ContentIsVisible," 
    //        + "ContentPublishDate,ContentEndDate,ContentStatus,ContentShowView";
	
	public JSONObject getContentOfJSON(Long contentId){
		try{
			String showin = "";
			PreparedStatement stmt = conn.prepareStatement(SELECT_CONTENT_BY_ID);
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
//				String status = rs.getString("ContentStatus");
//				if( status.equals("A")){
//					result.setActive(("1"));
//					result.setStatus("0");
//					
//				}else {
//					result.setActive("2");
//					result.setStatus("1");
//				}
//				result.setActive("0");
//				result.setStatus(rs.getString("ContentStatus"));
				result.setStatus(rs.getString("ContentStatus"));
				result.setActive(rs.getString("ContentIsVisible"));
				
				return result.toJSONObject();
			}
		}catch (Exception e){
			log.error("ContentDAO", "getContentOfJSON", e.toString());
		}
		
		return new JSONObject();
	}

	// El Siguiente código es de SmartMedia	
	public JSONArray getContentChannelListOfJSON(Long contentID){
		JSONArray result=new JSONArray();
		
		try{
			PreparedStatement stmt = conn.prepareStatement(SELECT_CHANNEL_BY_CONTENT_ID);
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
			//	item.setPeopleManager("0");
			//	item.setNewHire("N");
				item.setPos(rs.getLong("ChannelPosition"));
				
				//if (language.length()>0)
				//	item.setSecurityLevelName(rs.getString("value_"+language));
				//else
				item.setSecurityLevelName("me");
				
				item.setFullName(getFamilyName(item.getFamilyID()) + " > " + getChannelName((item.getId())));
				
				result.put(item.toJSON());
			}
		}catch (Exception e){
			log.error("ContentDAO", "getContentChannelListOfJSON", e.toString());
		}

		return result;
	}	

	
	//Siguiente código es de Smartmedia
	
	private String getFamilyName(Long familyID){
		String result = "";
		
		try{
			PreparedStatement stmt = conn.prepareStatement(" select Familyname from family where Familyid=? ");
			stmt.setLong(1, familyID);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				result = rs.getString("Familyname");
			}
		}catch (Exception e){
			log.error("ContentDAO", "getFamilyName", e.toString());
		}
		
		return result;
	}

	//El siguiente código es de Smartmedia
	
	private String getChannelName(Long channelID){
		String result = "";
		
		try{
			PreparedStatement stmt = conn.prepareStatement(" select ChannelFather,ChannelName from channel where Channelid=? ");
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
		
		return result;
	}

	//El siguiente código es de Samrtmedia
	//Consulta las etiquetas asociadas a un ContentId
	//private final String SELECT_LABELTEXT_BY_CONTENTID = "Select " + COLUMN_LABEL_INSERT + " from " + TABLE_NAME_LABEL + " a, " + TABLE_NAME_CONTENTLABEL + " b where a.LabelId = b.Label_LabelId and b.ContentId = ?";
	public JSONArray getTagListOfJSON(Long id){
		JSONArray result = new JSONArray();
		try{
			PreparedStatement stmt = conn.prepareStatement(SELECT_LABELTEXT_BY_CONTENTID);
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

		return result;
	}	
	
	//El siguiente es codigo de Smartmedia customizado
	public ContentVideoInfo getContentVideo(Long contentID){
		ContentVideoInfo item = new ContentVideoInfo();
		try{
			PreparedStatement stmt = conn.prepareStatement(SELECT_MEDIA_BY_CONTENT_ID);
		
			stmt.setLong(1, contentID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				//ContentVideoInfo item = new ContentVideoInfo();
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
		}

		return null;
	}	
	
	//Clonado del metódo anterior
	
	public ContentNewsInfo getContentNews(Long contentID){
		ContentNewsInfo item = new ContentNewsInfo();
		try{
			PreparedStatement stmt = conn.prepareStatement(SELECT_MEDIA_BY_CONTENT_ID);
			stmt.setLong(1, contentID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
			//	ContentVideoInfo item = new ContentVideoInfo();
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

		return item;
	}	

	
	//El siguiente es código de Samrtmedia customizado
	
	public ArrayList getContentGalleryMediaList(Long contentID){
		ArrayList result=new ArrayList();
		
		try{
			PreparedStatement stmt = conn.prepareStatement(SELECT_MEDIA_BY_CONTENT_ID);
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

		return result;
	}	

	//El siguientes es código de Smartmedia customizado
	
	public void generateHTML(Long contentID) throws Exception	{
		ContentInfo content = getContent(contentID);
		String result = "";
		String result_mobile = "";
		
		if (content!=null){
			
			if (content.getType().equals("2")){
				ContentVideoInfo video = getContentVideo(contentID);
				
				if (video!=null && video.getImage().length()>0 && video.getVideo().length()>0){
					result += "<a " + HTML.attr("href", "javascript:play_video('"+ HTML.media("video", video.getVideo())  +"')") + ">";
					result += "<img " + HTML.attr("class", "video-image") +  " " + HTML.attr("src", HTML.media("gallery", video.getImage())) + " " + HTML.attr("alt", "") + ">";
					result += "</a>";
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
//					result += "<h6>" + date + "</h6>";
					
//					result += "<div " + HTML.attr("class", "row news") + ">";
//					result += HTML.generateFeatures_Image(image);
//					result += "</div>";

					result += "<div " + HTML.attr("class", "news") + ">" + html_normal + "</div>";
					result += "</div>";
					
					result_mobile += "<div " + HTML.attr("class", "large-16 medium-16 columns") + ">";
//					result_mobile += "<h6>" + date + "</h6>";
					result_mobile += "<div " + HTML.attr("class", "news") + ">" + html_mobile + "</div>";
					result_mobile += "</div>";
				}
			}
			
/*			if (content.getType().equals("3")){
				boolean have = false;
				ArrayList questions = getContentQuestionList(contentID);
				
				for (int i=0; i<questions.size(); i++){
					ContentQuestionInfo question = (ContentQuestionInfo)questions.get(i);

					result += "<div " + HTML.attr("class", "row poll") + ">"; 
					result += "<h5>" + question.getQuestion() + "</h5>";

					if (question.getStatus().equals("0")){
						ArrayList answers = getContentAnswerList(question.getId());

						result += "<p>";
						String temp="checked";
						for (int j=0; j<answers.size(); j++){
							ContentQuestionAnswerInfo answer = (ContentQuestionAnswerInfo)answers.get(j);
							result += "<input " + HTML.attr("type", "radio") + " " + HTML.attr("id", "answer_"+answer.getId()) + " " + HTML.attr("name", "question_"+question.getId()) + " " + HTML.attr("value", ""+answer.getId()) + " " + HTML.attr("question", ""+question.getId())  + " " + HTML.attr("answer", ""+answer.getId()) + " " + temp + " />";
							result += "<label " + HTML.attr("for", "answer_"+answer.getId()) + ">" + answer.getAnswer() + "</label>";
							temp = "";
						}
						result += "</p>";
						have = true;
						
					}else{
						ArrayList answers = getAnswerList(question.getId());
						result += "<ul>";
						for (int j=0; j<answers.size(); j++){
							ContentAnswerInfo answer = (ContentAnswerInfo)answers.get(j);
							result += "<li>" + answer.getAnswer() + "</li>"; 
						}
						result += "</ul>";
						
						have = true;
						result += "<input " + HTML.attr("type", "text") + " " + HTML.attr("class", "poll_answer") + " " + HTML.attr("id", "answer"+question.getId()) + " " + HTML.attr("poll", String.valueOf(question.getId())) + " />";
					}
					
					result += "</div>";
				}
				
				if (have){
					result += "<a " + HTML.attr("class", "button medium") + " " + HTML.attr("href", "javascript:poll_submit()") + " >"  + "</a>";
				}
				
				result_mobile = result;
			}   */
			
/*			if (content.getType().equals("4")){
				ArrayList faqs = getContentFAQsList(contentID);
				for (int i=0; i<faqs.size(); i++){
					ContentFAQsInfo faq = (ContentFAQsInfo)faqs.get(i);
					result += "<div " + HTML.attr("class", "row faqs") + ">";
					result += "<h5>" + faq.getQuestion() + "</h5>";
					result += "<h6>"; 
					result += faq.getAnswer() ;
					result += "</h6>";
					result += "</div>";
				}
				
				result_mobile = result;
			}*/
			
			if (content.getType().equals("3")){
			//	ContentGalleryInfo gallery = getContentGallery(contentID);
				ArrayList gallerys = getContentGalleryMediaList(contentID);
				
//				if (gallery!=null){
//					result += "<p>" + gallery.getDetail() + "</p>";
					
					if (gallerys.size()>0){
						result += "<ul clearing-thumbs class='clearing-thumbs large-block-grid-4 medium-block-grid-3 small-block-grid-2' data-clearing>";
//						result += "<div " + HTML.attr("class", "gallery-image image-set") + ">";
					}
					
					for (int i=0; i<gallerys.size(); i++){
						ContentGalleryMediaInfo item = (ContentGalleryMediaInfo)gallerys.get(i);
//						result += "<a " + HTML.attr("class", "gallery-image-link") + " " + HTML.attr("href", HTML.media("gallery", item.getMedia())) + " " + HTML.attr("data-lightbox", "gallery-set") + " " + HTML.attr("data-title", item.getName()) + ">";
//						result += "<img " + HTML.attr("class", "gallery-image") +  " " + HTML.attr("src", HTML.media("gallery", item.getMedia())) + " " + HTML.attr("alt", "") + " />";
//						result += "</a>";
						
						result += "<li>";
						result += "<a " + HTML.attr("href", HTML.media("gallery", item.getMedia())) + " " + ">";
						result += "<img " + HTML.attr("src", HTML.media("gallery", item.getMedia())) + " " + HTML.attr("alt", "") + " " + HTML.attr("style", "width:250px;")  + " />";
						result += "</a>";
						result += "</li>";
					}
					
					if (gallerys.size()>0){
						result += "</ul>";
//						result += "</div>";
					}
//				}
				
				result_mobile = result;
			}
			
			if (content.getType().equals("4")){
//				ContentFileInfo file = getContentFile(contentID);
				ArrayList files = getContentFileMediaList(contentID);
				
//				if (file!=null){
//					result += "<p>" + file.getDescription() + "</p>";
					if (files.size()>0){
//						result += "<h4>" + reg.getStringOfLanguage("pubilc.attach_file", language) + "</h4>";
						result += "<ul>";
					}
					
					for (int i=0; i<files.size(); i++){
						ContentFileMediaInfo item = (ContentFileMediaInfo)files.get(i);
						result += "<li><a " + HTML.attr("href", "javascript:file_download('" + HTML.media("file", item.getMedia())+"&name="+item.getName() + "')") + ">" ;
						//JR
						//Consulta si la extención en la BD.
						result += "<i " + HTML.attr("class", "fa " + MediaDAO.getFileExtencion(item.getName()) + " fa-lg") + ">" + "</i>";
						//Original
						//result += "<i " + HTML.attr("class", "fa " + Utils.getIconOfFileType(item.getName()) + " fa-lg") + ">" + "</i>";
						result += " <span>" + item.getName() + "</span></a></li>";
					}

					if (files.size()>0){
						result += "</ul>";
					}
//				}
				
				result_mobile = result;
			}
			
			updateContentHtml(result, contentID);
		}
	}

	//El siguiente es código de Smartmedia customizado
	
	public ArrayList getContentFileMediaList(Long contentID){
		ArrayList result=new ArrayList();
		
		try{
			PreparedStatement stmt = conn.prepareStatement(SELECT_MEDIA_BY_CONTENT_ID);
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

		return result;
	}	

	
	//Actualiza el HTML del contenido
	//private final String UPDATE_CONTENT_HTML_BY_CONTENTID = "Update " + TABLE_NAME_CONTENT + " set " + COLUMN_CONTENT_HTML_UPDATE + " where ContentId = ?";
	
	public void updateContentHtml(String Html, Long contentid)
	{
		try
		{
			PreparedStatement stmt = conn.prepareStatement(UPDATE_CONTENT_HTML_BY_CONTENTID);
			stmt.setString(1, Html);
			stmt.setLong(2, contentid);
			stmt.executeUpdate();
			
		}catch (Exception e){
			log.error("ContentDAO","updateContentHtml",e.toString());
		}
	}
	
	//El siguiente es código de Smartmedia customizado
	//SELECT_CONTENT_BY_ID
	
	public ContentInfo getContent(Long contentID){
		try{
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
//				result.setActive(rs.getString("ContentStatus"));
//				String status = rs.getString("ContentStatus");
//				if (status.equals("A"))
//				{
//
//					result.setActive("1");
//					result.setStatus("0");
//				}else {
//
//					result.setActive("2");
//					result.setStatus("1");
//				}
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
		
		return null;
	}

	
	//El Siguiente codigo es de Smartmedia
	//UPDATE_CONTENT_TO_REMOVE
	
	public void removeContent(Long id) throws Exception{
		PreparedStatement stmt = conn.prepareStatement(UPDATE_CONTENT_TO_REMOVE);
	//	stmt.setString(1, Utils.getTodayWithTime());
		stmt.setLong(1, id);
		stmt.executeUpdate();
	}
	
	
	//UPDATE_CONTENT_TO_DELETE
	public void deleteContentLogical(Long id) throws Exception{
		PreparedStatement stmt = conn.prepareStatement(UPDATE_CONTENT_TO_DELETE);
	//	stmt.setString(1, Utils.getTodayWithTime());
		stmt.setLong(1, id);
		stmt.executeUpdate();
	}
	
	public void deleteContentAll() throws Exception{
		PreparedStatement stmt = conn.prepareStatement("Delete Content");
	//	stmt.setString(1, Utils.getTodayWithTime());
	//	stmt.setLong(1, id);
		stmt.executeUpdate();
	}
	
	//UPDATE_CONTENT_TO_ACTIVE
	
	public void activeContent(Long id) throws Exception{
		PreparedStatement stmt = conn.prepareStatement(UPDATE_CONTENT_TO_ACTIVE);
	//	stmt.setString(1, Utils.getTodayWithTime());
		stmt.setLong(1, id);
		stmt.executeUpdate();
	}
	
	public JSONArray getVideoContentListOfJSON(Long contentID){
		JSONArray r = new JSONArray();
		try
		{
			
			ContentInfo result = getContent(contentID);
			ContentVideoInfo video = getContentVideo(contentID);
//		JSONObject item = result.toJSONObject();
			JSONObject item = result.toJSONObject();
			item.put("video", video.getVideo());
			item.put("image", video.getImage());		
			r.put(item);
						
//			return r;
		}catch (Exception e){
			log.error("ContentDAO","getVideoContentListOfJSON",e.toString());
		}
		return r;
	}
	
	
	//El siguiente código es de Smartmedia customizado
	public JSONArray getContentFileMediaListOfJSON(Long contentID){
		JSONArray result=new JSONArray();
		
		try{
			PreparedStatement stmt = conn.prepareStatement(SELECT_MEDIA_BY_CONTENT_ID);
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

		return result;
	}
	
	
	//El siguiente código es de Smartmedia customizado
	//Obtiene la descripción de un contenido
	//private final String SELECT_CONTENT_DESCRIPTION = " select ContentId,ContentDescription from " + TABLE_NAME_CONTENT + " where ContentId=? " ;
	
	public JSONObject getContentFileOfJSON(Long contentID){
		try{
			PreparedStatement stmt = conn.prepareStatement(SELECT_CONTENT_DESCRIPTION);
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

		return new JSONObject();
	}	

	
	//Actualiza la descripcion del contenido con base al ContentId
	//private final String UPDATE_CONTENT_DESCRIPTION = "update " + TABLE_NAME CONTENT + " set " + COLUMN_CONTENT_UPDATE_DESCRIPTION + " where ContentId = ?";
	public void updateContentDescriptionById (Long contentid, String description)
	{
		try
		{
			PreparedStatement stmt = conn.prepareStatement(UPDATE_CONTENT_DESCRIPTION);
			stmt.setString(1, description);
			stmt.setLong(2,contentid);
			boolean rs = stmt.execute();
		}catch (Exception e){
			log.error("ContentDAO", "updateContentDescriptionById",e.toString());
		}
	}
	
	
	//Media
	//Private final String SELECT_MEDIA_BY_CONTENT_ID  = " Select " + COLUMN_MEDIA_SELECT + " from " + TABLE_NAME_MEDIA + " m, " + TABLE_NAME_CONTENTMEDIA + " c where c.Media_Mediaid = m.Media_id and c.Content_ContentId = ?";
	
	public String getMediaImage (Long contentid)
	{
		String image= "";
		try
		{
			PreparedStatement stmt = conn.prepareStatement(SELECT_MEDIA_BY_CONTENT_ID);
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
		return image;
	}
	
	
	public String getMediaVideo (Long contentid)
	{
		String video= "";
		try
		{
			PreparedStatement stmt = conn.prepareStatement(SELECT_MEDIA_BY_CONTENT_ID);
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
		return video;
	}
	
	
	//El siguiente codigo es de Smatmedia customizado
	
	public ContentNewsInfo getContentNews2(Long contentID){
		try{
			PreparedStatement stmt = conn.prepareStatement(SELECT_CONTENT_BY_ID);
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

		return new ContentNewsInfo();
	}	

	//El Siguiente es codigo de Smartmedia customizado
	public ContentVideoInfo getContentVideo2(Long contentID){
		ContentVideoInfo item = new ContentVideoInfo();
		item.setContentID(contentID);
		item.setVideo(getMediaVideo(contentID));
		item.setImage(getMediaImage(contentID));
		return item;
	}
	
	
	public int getMediaContent(String MediaContent) {
		try {
			PreparedStatement stmt = conn.prepareStatement(SELECT_BY_KEY_FILE);
			stmt.setString(1, MediaContent);
			ResultSet rs = stmt.executeQuery();
			int key;
			while (rs.next()) {

				key = rs.getInt("MediaId");

				return key;
			}
		} catch (Exception e) {
			log.error("MediaDAO", "getFileTypeId", e.toString());
		}
		return 0;

	}
	
	
	//Obtiene el MediaId de un archivo de imagen asociado a un ContentId
	//SELECT_IMAGE_MEDIAID
	public int getMediaImageId (Long contentid)
	{
		int key = 0;
		try
		{
			PreparedStatement stmt = conn.prepareStatement(SELECT_IMAGE_MEDIAID);
			stmt.setLong(1, contentid);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {

				key = rs.getInt("MediaId");

			

			}
		}catch (Exception e) {
			log.error("ContentDAO","getMediaImageId",e.toString());
		}
		return key;
	}
	
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

		

	
	
/*	public void updateContentHtml(String html, Long contentID)
	{
		try
		{
			PreparedStatement stmt = conn.prepareStatement(UPDATE_CONTENTHTML);
			stmt.setString(1, html);
			stmt.setLong(2, contentID);
			stmt.executeQuery();
		}catch (Exception e) {
			log.error("ContentDAO","updateHtml",e.toString());
		}
	
	} */
	
	//private final String SELECT_CHANNELID_BY_CHANNELNAME = "Select ChannelId from " + TABLE_NAME_CHANNEL + " where ChannelName = ?";
	public int getChannelIdByChannelName (String channelname)
	{
		int key = 0;
		try
		{
			PreparedStatement stmt = conn.prepareStatement(SELECT_CHANNELID_BY_CHANNELNAME);
			stmt.setString(1, channelname);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {

				key = rs.getInt("ChannelId");

			}				
		}catch (Exception e){
			log.error("ContentDao", "getChannelIdByChannelName",e.toString());
		}
		return key;
	}
	
	//Towa Termina Raúl
	
	public Long getGalleryIdForChannel(Long channelid)
	{
		Long key = (long)0;
		String sql = "Select ContentId from content where Channel_ChannelId = ? and Contenttype_ContenttypeId = '3'";
		try
		{
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setLong(1, channelid);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {

				key = rs.getLong("ContentId");	
			}
		}catch (Exception e){
			log.error("ContentDAO","GetGalleryIdForChannel",e.toString());
		}
		return key;
	}
public ArrayList getFileContent(Long contentID){
		
		ArrayList item = new ArrayList();
		try{
			
			PreparedStatement stmt = conn.prepareStatement(SELECT_CONTENT_BY_ID);
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
		
		return item;
	}	

	public void insertContentNews(Long contentid, String content, String image)
	{
		int lngcontentkey = 0;
		try
		{
			String sql2 = "insert into media(Filetype_FiletypeId,MediaName,MediaContent,MediaCreationDate,MediaUpdateDate,MediaSize,MediaIsNew,MediaUse,Folder_FolderId) " +
		           " values (6,'news" + contentid.toString() +"','"+content+"','"+Utils.getToday("-")+"','"+ Utils.getToday("-")+"',"+"1000,0,'E',3)";
			PreparedStatement stmt = conn.prepareStatement(sql2,Statement.RETURN_GENERATED_KEYS);
//			stmt.setLong(1, contentID);
			stmt.executeUpdate();
			ResultSet generatedKeys = stmt.getGeneratedKeys();
			generatedKeys.next();
			lngcontentkey =  generatedKeys.getInt(1);
			insertContentmedia(contentid,getMediaContent(image));
			insertContentmedia(contentid,lngcontentkey);
		}catch (Exception e){
			log.error("ContentDAO","insertContentNews",e.toString());
		}
	}
	
	public String getChannel_IDcontent(String channel_id){
		String result = "";
		
		try{
			PreparedStatement stmt = conn.prepareStatement(" select ContentId from content where Channel_ChannelId=? ");
			stmt.setString(1, channel_id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				
					result = rs.getString(1);
				
			}
		}catch (Exception e){
			log.error("ContentDAO", "getChannelName", e.toString());
		}
		
		return result;
	}
}	
