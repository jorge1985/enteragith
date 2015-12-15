package com.youandbbva.enteratv.gae;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONArray;
import org.json.JSONObject;

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

public class Receive extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SessionHandler session = SessionHandler.getInstance();
	private Registry reg = Registry.getInstance();

	/**
	 * Receive Request
	 */
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		JSONObject result = new JSONObject();
		try {
			result.put(Constants.ERROR_CODE, Constants.ACTION_FAILED);
			result.put(Constants.ERROR_MSG, reg.getMessage("ACT0002", session.getLanguage(req.getSession())));
		} catch (Exception e) {
		}

		Connection conn = DSManager.getConnection();
		ServletFileUpload upload = new ServletFileUpload();

		try {
			UserInfo user = session.getUserInfo(req.getSession());
			if (user==null){	
				throw new Exception(reg.getMessage("ACT0003"));
			}

			JSONArray data = new JSONArray();
			
			int l=0;
			FileItemIterator iterator = upload.getItemIterator(req);
			while (iterator.hasNext()) {
		        FileItemStream item = iterator.next();
		        
		        if (item.isFormField()) {
		        	
		        } else {

					conn.setAutoCommit(false);

					UserDAO dao = new UserDAO(conn);

					String today = Utils.getTodayWithTime();
				
					conn.commit();
		        	
		        	try{
		    			int user_id=-1, firstname=-1, lastname=-1, username=-1, email=-1, password=-1;
		    			int newhire=-1, manager=-1, owner=-1, promote=-1, jobgrade=-1;
		    			int geographical=-1,city=-1, division=-1, sub_division=-1;
		    			
		    			boolean isHeader=true;
		        		
				        BufferedReader reader = new BufferedReader(new InputStreamReader(item.openStream()));
				        
						String line=null;
						do{
							line = reader.readLine();
							if (line!=null){
								data.put(line.replaceAll("\"", ""));
								l++;
							}
							
						}while (line!=null);
						
						reader.close();
		        	}catch (Exception fff){}
		        	
					conn.commit();
		        }
			}

			result.put("data", data);
			result.put("len", l);
			result.put(Constants.ERROR_CODE, Constants.ACTION_SUCCESS);
			result.put(Constants.ERROR_MSG, reg.getMessage("ACT0001", session.getLanguage(req.getSession())));
		} catch (Exception e) {
			try{
				conn.rollback();
			}catch (Exception ff){}
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