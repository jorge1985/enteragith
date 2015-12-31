package com.youandbbva.enteratv.gae;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.channels.Channels;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.blobstore.FileInfo;
import com.google.appengine.api.files.AppEngineFile;
import com.google.appengine.api.files.FileReadChannel;
import com.google.appengine.api.files.FileService;
import com.google.appengine.api.files.FileServiceFactory;
import com.youandbbva.enteratv.Constants;
import com.youandbbva.enteratv.DSManager;
import com.youandbbva.enteratv.Registry;
import com.youandbbva.enteratv.Utils;
import com.youandbbva.enteratv.beans.SessionHandler;
import com.youandbbva.enteratv.dao.UserDAO;
import com.youandbbva.enteratv.domain.UserInfo;

/**
 * Import csv file to database.
 * 
 * @author CJH
 *
 */

public class Import extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	private SessionHandler session = SessionHandler.getInstance();
	private Registry reg = Registry.getInstance();

	/**
	 * Upload Request
	 */
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		JSONObject result = new JSONObject();
		try {
			result.put(Constants.ERROR_CODE, Constants.ACTION_FAILED);
			result.put(Constants.ERROR_MSG, reg.getMessage("ACT0002", session.getLanguage(req.getSession())));
		} catch (Exception e) {}

		Connection conn = DSManager.getConnection();

		try {

			UserInfo user = session.getUserInfo(req.getSession());
			if (user==null){	
				result.put(Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}

			Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
			List<BlobKey> blobKeys = blobs.get("myFile");
			Map<String, List<FileInfo>> file = blobstoreService.getFileInfos(req);

			if (blobKeys == null || blobKeys.isEmpty()) {
				throw new Exception("");
			} else {
				String type=file.get("myFile").get(0).getContentType();
				if (!type.equals("application/vnd.ms-excel") && !type.equals("application/csv") && !type.equals("text/csv"))
					throw new Exception("");
				
				System.out.println("File Info: " + file.get("myFile").get(0).getContentType() );
			}

			conn.setAutoCommit(false);

			JSONArray data = new JSONArray();
			UserDAO dao = new UserDAO();

			String today = Utils.getTodayWithTime();
			BlobKey blobKey = blobKeys.get(0);
			
			FileService fileService = FileServiceFactory.getFileService();
			AppEngineFile csv = fileService.getBlobFile(blobKey);
			FileReadChannel readChannel = fileService.openReadChannel(csv, false);
			
			BufferedReader reader = new BufferedReader(Channels.newReader(readChannel, "UTF8"));
			
			boolean isHeader = true;
			int user_id=-1, firstname=-1, lastname=-1, username=-1, email=-1, password=-1;
			int newhire=-1, manager=-1, owner=-1, promote=-1, jobgrade=-1;
			int l=0;
					
			String line=null;
			do{
					line = reader.readLine();
					if (line!=null){
						data.put(line.replaceAll("\"", ""));
						l++;
					}
					
			}while (line!=null);
			
			System.out.println(line);
			
			reader.close();
			readChannel.close();
			
			conn.commit();

			result.put("data", data);
			result.put("len", l);
			result.put(Constants.ERROR_CODE, Constants.ACTION_SUCCESS);
			result.put(Constants.ERROR_MSG, reg.getMessage("ACT0001", session.getLanguage(req.getSession())));

		} catch (Exception e) {
			
			try{
				conn.rollback();
			}catch (Exception ex){}

		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}

		res.setContentType("text/plain;charset=UTF-8");
		try {
			res.getWriter().print(result.toString());
		} catch (Exception e) {}
	}
}