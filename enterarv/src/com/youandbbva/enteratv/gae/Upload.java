package com.youandbbva.enteratv.gae;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.blobstore.FileInfo;
import com.youandbbva.enteratv.DSManager;
import com.youandbbva.enteratv.Registry;
import com.youandbbva.enteratv.Utils;
import com.youandbbva.enteratv.beans.SessionHandler;
import com.youandbbva.enteratv.domain.UserInfo;

/**
 * Upload media to cloud store.
 * 
 * @author CJH
 *
 */

public class Upload extends HttpServlet {
	
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
			result.put("key", "");
			result.put("thumbnail", "");
		} catch (Exception e) {
		}

		Connection conn = DSManager.getConnection();

		try {

			UserInfo user = session.getUserInfo(req.getSession());
			/*if (user==null){
				user = session.getOpportUserInfo(req.getSession());
				if (user==null)
					throw new Exception(reg.getMessage("ACT0003"));
			}*/

			Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
			List<BlobKey> blobKeys = blobs.get("myFile");
			Map<String, List<FileInfo>> file = blobstoreService.getFileInfos(req);

			if (blobKeys == null || blobKeys.isEmpty()) {
				
			} else {
				try {
					System.out.println("File Info: " + file.get("myFile").get(0).getContentType() + "," + file.get("myFile").get(0).getFilename() );
					
					String type = file.get("myFile").get(0).getContentType();
					String filename = file.get("myFile").get(0).getFilename();
					
					if (Utils.isValidFormat(type, filename)){
						result.put("type", file.get("myFile").get(0).getContentType());
						result.put("name", file.get("myFile").get(0).getFilename());
						result.put("size", file.get("myFile").get(0).getSize());
						result.put("key", blobKeys.get(0).getKeyString());
					}
				} catch (Exception e) { }
			}
			
		} catch (Exception e) {
//			e.printStackTrace();
			
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