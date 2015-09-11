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
			UserDAO dao = new UserDAO(conn);

			String today = Utils.getTodayWithTime();
			//dao.rename(today);
			//dao.copy(today);
			
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
//				try{
					line = reader.readLine();
					if (line!=null){
						data.put(line.replaceAll("\"", ""));
						l++;
					}
					
//					String[] row = line.split(",");
//					if (isHeader){
//						for (int i=0; i<row.length; i++){
//							String field = row[i].toLowerCase();
//							if (field.indexOf("number")>-1 || field.indexOf("employee")>-1){
//								user_id=i;
//								continue;
//							}
//							
//							if (field.indexOf("firstname")>-1 || field.indexOf("first")>-1){
//								firstname=i;
//								continue;
//							}
//							
//							if (field.indexOf("lastname")>-1 || field.indexOf("last")>-1){
//								lastname=i;
//								continue;
//							}
//							
//							if (field.indexOf("aka")>-1 || field.indexOf("a.k.a")>-1){
//								username=i;
//								continue;
//							}
//							
//							if (field.indexOf("email")>-1 || field.indexOf("e-mail")>-1){
//								email=i;
//								continue;
//							}
//							
//							if (field.indexOf("password")>-1){
//								password=i;
//								continue;
//							}
//							
//							if (field.indexOf("newhire")>-1 || field.indexOf("new_hire")>-1){
//								newhire=i;
//								continue;
//							}
//	
//							if (field.indexOf("owner")>-1 || field.indexOf("pay")>-1){
//								owner=i;
//								continue;
//							}
//							
//							if (field.indexOf("manager")>-1 || field.indexOf("people")>-1){
//								manager=i;
//								continue;
//							}
//	
//							if (field.indexOf("promo")>-1 || field.indexOf("promostat")>-1){
//								promote=i;
//								continue;
//							}
//							
//							if (field.indexOf("grade")>-1 || field.indexOf("jobgrade")>-1){
//								jobgrade=i;
//								continue;
//							}
//							
//						}
//					}else{
//						if (user_id!=-1){
//							String csv_userid = row[user_id];
//							csv_userid = Utils.toStringfromCSV(csv_userid);
//							
//							String csv_firstname = "";
//							if (firstname!=-1)
//								csv_firstname = row[firstname];
//							csv_firstname = Utils.toStringfromCSV(csv_firstname);
//							
//							String csv_lastname = "";
//							if (lastname!=-1)
//								csv_lastname = row[lastname];
//							csv_lastname = Utils.toStringfromCSV(csv_lastname);
//							
//							String csv_username = "";
//							if (username!=-1)
//								csv_username = row[username];
//							csv_username = Utils.toStringfromCSV(csv_username);
//							
//							String csv_email="";
//							if (email!=-1)
//								csv_email = row[email];
//							csv_email = Utils.toStringfromCSV(csv_email);
//							
//							String csv_password="user";
//							if (password!=-1)
//								csv_password = row[password];
//							csv_password = Utils.toStringfromCSV(csv_password);
//							csv_password = DigestUtils.md5Hex(csv_password + Constants.PASSWORD_HASH);
//							
//							String csv_newhire = "002";
//							if (newhire!=-1){
//								String temp = row[newhire];
//								temp = Utils.toStringfromCSV(temp);
//								if (temp!=null && ( temp.toLowerCase().equals("y") || temp.toLowerCase().equals("yes")))
//									csv_newhire = "001";
//							}
//							
//							String csv_manager = "002";
//							if (manager!=-1){
//								String temp = row[manager];
//								temp = Utils.toStringfromCSV(temp);
//								if (temp!=null && ( temp.toLowerCase().equals("y") || temp.toLowerCase().equals("yes")))
//									csv_manager = "001";
//							}
//							
//							String csv_owner = "";
//							if (owner!=-1){
//								String temp = row[owner];
//								temp = Utils.toStringfromCSV(temp);
//								if (temp!=null && temp.length()>0){
//									temp = dao.getCode("code", "chn7", temp);
//									csv_manager = temp;
//								}
//							}
//	
//							String csv_promote = "";
//							if (promote!=-1){
//								String temp = row[promote];
//								temp = Utils.toStringfromCSV(temp);
//								if (temp!=null && temp.length()>0){
//									temp = dao.getCode("code", "chn3", temp);
//									csv_promote = temp;
//								}
//							}
//	
//							String csv_jobgrade = "";
//							if (jobgrade!=-1){
//								String temp = row[jobgrade];
//								temp = Utils.toStringfromCSV(temp);
//								if (temp!=null && temp.length()>0){
//									temp = dao.getCode("code", "chn6", temp);
//									csv_jobgrade = temp;
//								}
//							}
//	
//							String csv_active = "1";
//							
//							try{
//								UserInfo u = dao.getUserInfo(csv_userid);
//								if (u==null)
//									dao.insert(csv_userid, csv_username, csv_firstname, csv_lastname, csv_email, csv_password, (long)0, (long)0, csv_manager, csv_newhire, csv_jobgrade, csv_owner, csv_promote, csv_active, (long)0, (long)0 , "002", "000");
//							}catch (Exception eee){}
//						}
//					}
//					
//					isHeader = false;
//				}catch (Exception ff){}
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
//			e.printStackTrace();
			
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