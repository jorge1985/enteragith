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
import com.youandbbva.enteratv.domain.Extencion;
import com.youandbbva.enteratv.domain.FileInfo;
import com.youandbbva.enteratv.domain.FolderInfo;

/**
 * 
 * Handle all query for Media Manager.
 * 
 * @author CJH
 *
 */

@Repository("MediaDAO")
public class MediaDAO extends DAO {

	// Querys para la Tabla Folder de la BD enteratv_v2.0
	private final String USE_ENTERATV = "E";
	private final String USE_OPORTUNIDADES = "O";
	private final String TABLE_NAME_FOLDER = "folder";
	private final String COLUMN_NAME_FOLDER = "FolderId,FolderName,FolderFather";
	private final String COLUMN_INSERT_NAME_FOLDER = "FolderName,FolderFather";

	private final String INESRT_FOLDER = " insert into " + TABLE_NAME_FOLDER
			+ " ( " + COLUMN_INSERT_NAME_FOLDER + " ) values (" + " ?, ? "
			+ " ) ";
	private final String COUNT_BY_NAME_FOLDER = " select " + "count(*)"
			+ " from " + TABLE_NAME_FOLDER + " where FolderName=?";
	private final String SELECT_BY_PARENT_ID_FOLDER = " select "
			+ COLUMN_NAME_FOLDER + " from " + TABLE_NAME_FOLDER
			+ " where FolderFather=? order by FolderName";
	private final String SELECT_BY_ID_FOLDER = " select " + COLUMN_NAME_FOLDER
			+ " from " + TABLE_NAME_FOLDER + " where FolderId=? ";
	private final String UPDATE_NAME_FOLDER = " update " + TABLE_NAME_FOLDER
			+ " set FolderName=? where FolderId=? ";
	private final String DELETE_FOLDER = " delete from " + TABLE_NAME_FOLDER
			+ " where FolderId=? ";

	// Querys para la Tabla Media de la BD enteratv_v2.0
	private final String TABLE_NAME_MEDIA = "media";
	private final String COLUMN_NAME_FILE = "MediaId,MediaName,MediaContent,MediaCreationDate,MediaUpdateDate,MediaSize,MediaIsNew,MediaUse,Folder_FolderId,Filetype_FiletypeId";

	private final String COUNT_BY_NAME_FILE = " select " + "count(*)"
			+ " from " + TABLE_NAME_MEDIA
			+ " Where MediaName = ? and Folder_FolderId = ? and MediaUse = 'E'";
	private final String SELECT_BY_ID_FILE = " select " + COLUMN_NAME_FILE
			+ " from " + TABLE_NAME_MEDIA
			+ " where MediaId = ? and MediaUse = 'E' order by MediaName ";
	private final String SELECT_BY_FOLDER_ID_FILE = " select "
			+ COLUMN_NAME_FILE
			+ " from "
			+ TABLE_NAME_MEDIA
			+ " where Folder_FolderId = ? and MediaUse = 'E' order by MediaName ";

	private final String INESRT_FILE = " insert into "
			+ TABLE_NAME_MEDIA
			+ " ( "
			+ "MediaName,MediaContent,MediaCreationDate,MediaSize,Folder_FolderId,Filetype_FiletypeId,MediaUpdateDate,MediaIsNew,MediaUse"
			+ " ) values (" + " ?, ?, ?, ?, ?, ?, ?, ?, ? " + " ) ";
	private final String UPDATE_NAME_FILE = " update " + TABLE_NAME_MEDIA
			+ " set MediaName = ?, MediaUpdateDate = ? where MediaId = ? ";
	private final String DELETE_FILE = " delete from " + TABLE_NAME_MEDIA
			+ " where MediaId = ? ";
	private final String DELETE_FILE2 = "DELETE FROM contentmedia WHERE Media_MediaId = ?";

	private final String SELECT_BY_KEY_FILE = " select  MediaId" + " from "
			+ TABLE_NAME_MEDIA + " where MediaContent = ?";

	// Querys para la Tabla Filextencion de la BD enteratv_v2.0
	private final static String TABLE_NAME_FILE_EXTENCION = "fileextension";
	private final static String COLUMNS_FILE_EXTENCION = "Filetype_FiletypeId,FileextensionName,FileextensionIsActive";

	private final static String SELECT_FILE_EXTENCION = "SELECT "
			+ COLUMNS_FILE_EXTENCION + " FROM " + TABLE_NAME_FILE_EXTENCION
			+ " WHERE FileextensionName = ?";

	public MediaDAO() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * Check whether valid name or not.
	 * 
	 * @param name
	 * @param folderID
	 * @return boolean
	 */
	public boolean isValidFolder(String name) {
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try {
			
			long result = 0;
			stmt = conn
					.prepareStatement(COUNT_BY_NAME_FOLDER);
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				result = rs.getLong(1);
			}

			return result == 0 ? true : false;
		} catch (Exception e) {
			log.error("MediaDAO", "isValidFolder", e.toString());
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
	 * Check whether valid name or not.
	 * 
	 * @param name
	 * @param folderID
	 * @return boolean
	 */
	public boolean isValidFile(String name, Long folderID) {
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		
		try {
			long result = 0;
			stmt = conn.prepareStatement(COUNT_BY_NAME_FILE);
			stmt.setString(1, name);
			stmt.setLong(2, folderID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				result = rs.getLong(1);
			}

			return result == 0 ? true : false;
		} catch (Exception e) {
			log.error("MediaDAO", "isValidFile", e.toString());
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
	 * Get List of Folder.
	 * 
	 * @param parentID
	 * @return Folder List
	 */
	public JSONArray getFoldersOfJSON(Long parentID) {
		JSONArray result = new JSONArray();
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;

		try {
			stmt = conn
					.prepareStatement(SELECT_BY_PARENT_ID_FOLDER);
			stmt.setLong(1, parentID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				FolderInfo item = new FolderInfo();
				item.setId(rs.getLong("FolderId"));
				item.setParentID(rs.getLong("FolderFather"));
				item.setName(rs.getString("FolderName"));

				result.put(item.toJSONObject());
			}
		} catch (Exception e) {
			log.error("MediaDAO", "getFoldersOfJSON", e.toString());
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
	 * Get List of Folder.
	 * 
	 * @param parentID
	 * @return Folder List
	 */
	public ArrayList<FolderInfo> getFolders(Long parentID) {
		ArrayList<FolderInfo> result = new ArrayList<FolderInfo>();
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;

		try {
			stmt = conn
					.prepareStatement(SELECT_BY_PARENT_ID_FOLDER);
			stmt.setLong(1, parentID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				FolderInfo item = new FolderInfo();
				item.setId(rs.getLong("FolderId"));
				item.setParentID(rs.getLong("FolderFather"));
				item.setName(rs.getString("FolderName"));

				result.add(item);
			}
		} catch (Exception e) {
			log.error("MediaDAO", "getFolders", e.toString());
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

	public FolderInfo getFolder(Long folderID) {
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(SELECT_BY_ID_FOLDER);
			stmt.setLong(1, folderID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				FolderInfo item = new FolderInfo();
				item.setId(rs.getLong("FolderId"));
				item.setParentID(rs.getLong("FolderFather"));
				item.setName(rs.getString("FolderName"));

				return item;
			}
		} catch (Exception e) {
			log.error("MediaDAO", "getFolder", e.toString());
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
	 * Get All Folder Data.
	 * 
	 * @param parentID
	 * @return list
	 */
	public JSONArray recallFolderList(Long parentID) {
		JSONArray result = new JSONArray();
		
		try {
			ArrayList<FolderInfo> list = getFolders(parentID);
			for (int i = 0; i < list.size(); i++) {
				FolderInfo item = (FolderInfo) list.get(i);
				JSONObject obj = new JSONObject();
				obj.put("parent", item.toJSONObject());
				obj.put("child", recallFolderList(item.getId()));
				result.put(obj);
			}
		} catch (Exception e) {
			log.error("MediaDAO", "recallFolderList", e.toString());
		}

		return result;
	}

	/**
	 * Insert Folder.
	 * 
	 * @param parentID
	 * @param name
	 *            Folder Name
	 * @throws Exception
	 */
	public void insertFolder(Long parentID, String name) throws Exception {
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = conn.prepareStatement(INESRT_FOLDER);
		stmt.setString(1, name);
		stmt.setLong(2, parentID);
		stmt.execute();
		
		stmt.close();
		conn.close();
	}

	/**
	 * Update folder name.
	 * 
	 * @param id
	 *            Folder ID
	 * @param name
	 *            Folder Name
	 * @throws Exception
	 */
	public void updateFolderName(Long id, String name) throws Exception {
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = conn.prepareStatement(UPDATE_NAME_FOLDER);
		stmt.setString(1, name);
		stmt.setLong(2, id);
		stmt.execute();
		
		stmt.close();
		conn.close();
	}

	/**
	 * Delete Folder.
	 * 
	 * @param id
	 *            Folder ID
	 * @throws Exception
	 */
	public void deleteFolder(Long id) throws Exception {
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = conn.prepareStatement(DELETE_FOLDER);
		stmt.setLong(1, id);
		stmt.execute();
		
		stmt.close();
		conn.close();
	}

	/**
	 * Get File Information.
	 * 
	 * @param id
	 * @return object
	 */
	public JSONObject getFileOfJSON(Long id) {
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(SELECT_BY_ID_FILE);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				FileInfo item = new FileInfo();
				item.setId(rs.getLong("MediaId"));
				item.setType(rs.getString("Filetype_FiletypeId"));
				item.setFolderID(rs.getLong("Folder_FolderId"));
				item.setName(rs.getString("MediaName"));
				item.setCreateTime(rs.getString("MediaCreationDate").replace(".0", ""));
				item.setModifyTime(rs.getString("MediaUpdateDate").replace(".0",""));
				item.setSize(rs.getLong("MediaSize"));
				item.setStatus(rs.getString("MediaUse"));
				item.setCloudKey(rs.getString("MediaContent"));

				return item.toJSONObject();
			}
		} catch (Exception e) {
			log.error("MediaDAO", "getFileOfJSON", e.toString());
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

		return new JSONObject();
	}

	/**
	 * Get List of Files.
	 * 
	 * @param folderID
	 * @return File List
	 */
	public JSONArray getFilesOfJSON(Long folderID) {
		JSONArray result = new JSONArray();
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = conn
					.prepareStatement(SELECT_BY_FOLDER_ID_FILE);
			stmt.setLong(1, folderID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				FileInfo item = new FileInfo();
				item.setId(rs.getLong("MediaId"));
				item.setType(rs.getString("Filetype_FiletypeId"));
				item.setFolderID(rs.getLong("Folder_FolderId"));
				item.setName(rs.getString("MediaName"));
				item.setCreateTime(rs.getString("MediaCreationDate").replace(".0", ""));
				item.setModifyTime(rs.getString("MediaUpdateDate").replace(".0", ""));
				item.setSize(rs.getLong("MediaSize"));
				item.setStatus(rs.getString("MediaUse"));

				result.put(item.toJSONObject());
			}
		} catch (Exception e) {
			log.error("MediaDAO", "getFilesOfJSON", e.toString());
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
	 * Insert File.
	 * 
	 * @param folderID
	 * @param name
	 *            Folder Name
	 * @param time
	 * @param size
	 *            File Size
	 * @param key
	 *            Identity of Cloud Storage
	 * @param type
	 * @throws Exception
	 */
	public void insertFile(Long folderID, String name, String time, Long size,
			String key, String type, String thumb) throws Exception {
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = conn.prepareStatement(INESRT_FILE);

		stmt.setString(1, name);
		stmt.setString(2, key);
		stmt.setString(3, time);
		stmt.setLong(4, size);
		stmt.setLong(5, folderID);
		stmt.setInt(6, getFileExtencion(name));
		stmt.setString(7, time);
		stmt.setInt(8, 1);
		if(thumb.equals("O")){
			stmt.setString(9, USE_OPORTUNIDADES);
		}else{
			stmt.setString(9, USE_ENTERATV);
		}

		stmt.execute();
		
		stmt.close();
		conn.close();
	}

	/**
	 * Update File name.
	 * 
	 * @param id
	 *            Folder ID
	 * @param name
	 *            Folder Name
	 * @param time
	 * @throws Exception
	 */
	public void updateFileName(Long id, String name, String time)
			throws Exception {
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = conn.prepareStatement(UPDATE_NAME_FILE);

		stmt.setString(1, name);
		stmt.setString(2, time);
		stmt.setLong(3, id);
		stmt.execute();
		
		stmt.close();
		conn.close();
	}

	/**
	 * Delete File.
	 * 
	 * @param id
	 *            File ID
	 * @throws Exception
	 */
	public void deleteFile(Long id) throws Exception {
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		if(isExistContentMedia(id)){
			
			stmt = conn.prepareStatement(DELETE_FILE2);
			stmt.setLong(1, id);
			stmt.executeUpdate();
			
			stmt2 = conn.prepareStatement(DELETE_FILE);
			stmt2.setLong(1, id);
			stmt2.executeUpdate();
		}else{
			stmt = conn.prepareStatement(DELETE_FILE);
			stmt.setLong(1, id);
			stmt.executeUpdate();
		}
		
		stmt.close();
		conn.close();

	}
	
	public boolean isExistContentMedia(Long idMedia) throws SQLException{
		boolean result = false;
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		String sql = "SELECT Media_MediaId FROM contentmedia WHERE Media_MediaId = ?";
		stmt = conn.prepareStatement(sql);
		stmt.setLong(1, idMedia);
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			result = true;
		}
		
		stmt.close();
		conn.close();
		
		return result;
	}

	/**
	 * File Extencion.
	 * 
	 * @param filename
	 * @throws Exception
	 */
	public static int getFileExtencion(String filename) throws Exception {

		Extencion fileExtencion = null;
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;

		int index = filename.lastIndexOf('.');
		if (index == -1) {
			System.out.println("");
		} else {

			filename = filename.toLowerCase();
			stmt = conn
					.prepareStatement(SELECT_FILE_EXTENCION);
			stmt.setString(1, filename.substring(index + 1).toLowerCase());

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				fileExtencion = new Extencion();
				fileExtencion.setFileTypeId(rs.getInt(1));
				fileExtencion.setFileExtencionName(rs.getString(2));
				fileExtencion.setFileExtencionActive(rs.getBoolean(3));
			}
		}
		stmt.close();
		conn.close();
		return fileExtencion.getFileTypeId();
	}

	public long getMediaContent(String MediaContent) {
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(SELECT_BY_KEY_FILE);
			stmt.setString(1, MediaContent);
			ResultSet rs = stmt.executeQuery();
			long key;
			while (rs.next()) {

				key = rs.getLong("MediaId");

				return key;
			}
		} catch (Exception e) {
			log.error("MediaDAO", "getFileTypeId", e.toString());
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
	
	public Long idFoderOportunidades(String oportunidades){
		Long idFoler = (long) 0;
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt  = null;
		try {
			String sql = "SELECT FolderId FROM folder WHERE FolderName = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, oportunidades);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {

				idFoler = rs.getLong("FolderId");

				return idFoler;
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
		
		return (long)0;
	}
	
	public String getBlob(String idMedia){
		String blob="";
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		try {
			String sql = "SELECT MediaContent FROM media WHERE MediaId = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, idMedia);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {

				blob = rs.getString("MediaContent");

				return blob;
			}
		} catch (Exception e) {
			log.error("MediaDAO", "getFolderId", e.toString());
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
		
		return "";
	}

	public void updateMedia(int idMedia, String file) throws SQLException {
		Connection conn = DSManager.getConnection();
		PreparedStatement stmt = null;
		String sql = "update media set MediaContent=?, MediaUpdateDate=? where MediaId=?";
		stmt = conn.prepareStatement(sql);
		String time = Utils.getTodayWithTime();

		stmt.setString(1, file);
		stmt.setString(2, time);
		stmt.setInt(3, idMedia);
		stmt.execute();
		
		stmt.close();
		conn.close();
	}

}
