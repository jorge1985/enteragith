package com.youandbbva.enteratv.gae;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.com.bytecode.opencsv.CSVWriter;

import com.youandbbva.enteratv.DSManager;
import com.youandbbva.enteratv.Registry;
import com.youandbbva.enteratv.Utils;
import com.youandbbva.enteratv.beans.SessionHandler;
import com.youandbbva.enteratv.dao.VisitorDAO;
import com.youandbbva.enteratv.domain.UserInfo;

/**
 * Export csv file.
 * 
 * @author CJH
 *
 */

public class Export extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SessionHandler session = SessionHandler.getInstance();
	private Registry reg = Registry.getInstance();

	/**
	 * Export Request
	 */
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		String search = req.getParameter("search");
		String type = Utils.checkNull(req.getParameter("type"));
		String start_day = Utils.checkNull(req.getParameter("start_day"));
		String end_day = Utils.checkNull(req.getParameter("end_day"));
	    String exact = Utils.checkNull(req.getParameter("exact"));
	    if (exact.length()==0)
	    	exact="0";
	    String option = Utils.checkNull(req.getParameter("option"));
	    if (option.length()==0)
	    	option="9";
	    
		Connection conn = DSManager.getConnection();

		try {
			UserInfo user = session.getUserInfo(req.getSession());
			if (user==null){	
	
			}
			
			if (type.length()==0){
	
			}
			
        	String language = session.getLanguage(req.getSession());

            res.setContentType("text/csv");
            res.setHeader("Content-Disposition", "attachment; filename=export_" + Utils.getTodayWithTime().replace(" ", "").replace("-", "").replace(":", "") + ".csv");
            
            Writer writer = new OutputStreamWriter(res.getOutputStream());
            CSVWriter csv = new CSVWriter(writer, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER);
            
            try {

            	String[] column = null;
            	
            	if(type.equals("access")){
            		column = new String[] {"Nombre", "Fecha", "IP Address"};
            	}else if(type.equals("channel")){
            		column = new String[] {"Nombre", "Fecha", "IP Address", "Canales"};
            	}else if(type.equals("content")){
            		column = new String[] {"Nombre", "Fecha", "IP Address", "Contenido"};
            	}else if(type.equals("advanced")){
                	if(option.equals("1")){
                		column = new String[] {"Nombre", "Fecha", "IP Address"};
                	}else if(option.equals("3")){
                		column = new String[] {"Nombre", "Fecha", "IP Address", "Detalle"};
                	}else if(option.equals("4")){
                		column = new String[] {"Nombre", "Fecha", "IP Address", "Detalle"};
                	}
            	}
            	
            	csv.writeNext(column);

    			VisitorDAO dao = new VisitorDAO(conn);
    			
				String sql = "";

				if (type.equals("access")) {
					sql = "select a.UserId, a.UserName,  v.VisitDate, v.VisitIp, c.ContentName, ch.ChannelName from user a INNER JOIN visit v ON a.UserId = v.User_UserId INNER JOIN content c ON c.ContentId = v.Content_ContentId INNER JOIN channel ch ON c.Channel_ChannelId = ch.ChannelId where (CAST(v.VisitDate AS DATE) BETWEEN '"
							+ start_day
							+ "' and '"
							+ end_day
							+ "') AND c.ContentStatus='A'  order by v.VisitDate DESC";
				} else if (type.equals("channel")) {
					sql = "select a.UserId, a.UserName,  v.VisitDate, v.VisitIp, c.ContentName, ch.ChannelName from user a INNER JOIN visit v ON a.UserId = v.User_UserId INNER JOIN content c ON c.ContentId = v.Content_ContentId INNER JOIN channel ch ON c.Channel_ChannelId = ch.ChannelId where (CAST(v.VisitDate AS DATE) BETWEEN '"
							+ start_day
							+ "' and '"
							+ end_day
							+ "') AND c.ContentStatus='A'  order by v.VisitDate DESC";
				} else if (type.equals("content")) {
					sql = "select a.UserId, a.UserName,  v.VisitDate, v.VisitIp, c.ContentName, ch.ChannelName from user a INNER JOIN visit v ON a.UserId = v.User_UserId INNER JOIN content c ON c.ContentId = v.Content_ContentId INNER JOIN channel ch ON c.Channel_ChannelId = ch.ChannelId where (CAST(v.VisitDate AS DATE) BETWEEN '"
							+ start_day
							+ "' and '"
							+ end_day
							+ "') AND c.ContentStatus='A'  order by v.VisitDate DESC";
				} else if (type.equals("advanced")) {
					if (search.length() > 0) {
						if (exact.equals("0")) {
							if (option.equals("1")) {
								sql = "select a.UserId, a.UserName, v.VisitDate, v.VisitIp, c.ContentName, ch.ChannelName from user a INNER JOIN visit v ON a.UserId = v.User_UserId INNER JOIN content c ON c.ContentId = v.Content_ContentId INNER JOIN channel ch ON c.Channel_ChannelId = ch.ChannelId where (CAST(v.VisitDate AS DATE) BETWEEN '"
										+ start_day
										+ "' and '"
										+ end_day
										+ "') AND c.ContentStatus='A' AND a.UserName like'%"
										+ search
										+ "%' order by v.VisitDate DESC";
							} else if (option.equals("3")) {
								sql = "select a.UserId, a.UserName, v.VisitDate, v.VisitIp, c.ContentName, ch.ChannelName from user a INNER JOIN visit v ON a.UserId = v.User_UserId INNER JOIN content c ON c.ContentId = v.Content_ContentId INNER JOIN channel ch ON c.Channel_ChannelId = ch.ChannelId where (CAST(v.VisitDate AS DATE) BETWEEN '"
										+ start_day
										+ "' and '"
										+ end_day
										+ "') AND c.ContentStatus='A'  AND c.ContentName like'%"
										+ search
										+ "%' order by v.VisitDate DESC";
							} else if (option.equals("4")) {
								sql = "select a.UserId, a.UserName,  v.VisitDate, v.VisitIp, c.ContentName, ch.ChannelName from user a INNER JOIN visit v ON a.UserId = v.User_UserId INNER JOIN content c ON c.ContentId = v.Content_ContentId INNER JOIN channel ch ON c.Channel_ChannelId = ch.ChannelId where (CAST(v.VisitDate AS DATE) BETWEEN '"
										+ start_day
										+ "' and '"
										+ end_day
										+ "') AND c.ContentStatus='A'  AND ch.ChannelName like'%"
										+ search
										+ "%' order by v.VisitDate DESC";
							}

						} else {
							if (option.equals("1")) {
								sql = "select a.UserId, a.UserName,  v.VisitDate, v.VisitIp, c.ContentName, ch.ChannelName from user a INNER JOIN visit v ON a.UserId = v.User_UserId INNER JOIN content c ON c.ContentId = v.Content_ContentId INNER JOIN channel ch ON c.Channel_ChannelId = ch.ChannelId where (CAST(v.VisitDate AS DATE) BETWEEN '"
										+ start_day
										+ "' and '"
										+ end_day
										+ "')  AND c.ContentStatus='A'  AND a.UserName='"
										+ search
										+ "' order by v.VisitDate DESC";
							} else if (option.equals("3")) {
								sql = "select a.UserId, a.UserName,  v.VisitDate, v.VisitIp, c.ContentName, ch.ChannelName from user a INNER JOIN visit v ON a.UserId = v.User_UserId INNER JOIN content c ON c.ContentId = v.Content_ContentId INNER JOIN channel ch ON c.Channel_ChannelId = ch.ChannelId where (CAST(v.VisitDate AS DATE) BETWEEN '"
										+ start_day
										+ "' and '"
										+ end_day
										+ "')  AND c.ContentStatus='A'  AND c.ContentName='"
										+ search
										+ "' order by v.VisitDate DESC";
							} else if (option.equals("4")) {
								sql = "select a.UserId, a.UserName,  v.VisitDate, v.VisitIp, c.ContentName, ch.ChannelName from user a INNER JOIN visit v ON a.UserId = v.User_UserId INNER JOIN content c ON c.ContentId = v.Content_ContentId INNER JOIN channel ch ON c.Channel_ChannelId = ch.ChannelId where (CAST(v.VisitDate AS DATE) BETWEEN '"
										+ start_day
										+ "' and '"
										+ end_day
										+ "')  AND c.ContentStatus='A'  AND ch.ChannelName='"
										+ search
										+ "' order by v.VisitDate DESC";
							}

						}
					} else {
						sql = "select a.UserId, a.UserName,  v.VisitDate, v.VisitIp, c.ContentName, ch.ChannelName from user a INNER JOIN visit v ON a.UserId = v.User_UserId INNER JOIN content c ON c.ContentId = v.Content_ContentId INNER JOIN channel ch ON c.Channel_ChannelId = ch.ChannelId where (CAST(v.VisitDate AS DATE) BETWEEN '"
								+ start_day
								+ "' and '"
								+ end_day
								+ "') AND c.ContentStatus='A'  order by v.VisitDate DESC";
					}
				}
    			
    			PreparedStatement stmt = conn.prepareStatement(sql);
    			ResultSet rs = stmt.executeQuery();
    			
    			while (rs.next()){

    				String user_id = "";
    				String username = "";
    				String date = "";
    				String ipAddr = "";
    				
                	if(type.equals("access")){
        				user_id = Utils.checkNull(rs.getString("UserId"));
        				username = dao.getUserName("user", user_id, language);
        				date = Utils.checkNull(rs.getString("VisitDate").replace(".0", ""));
        				ipAddr = Utils.checkNull(rs.getString("VisitIp"));
        				
        				String [] value = {username, date, ipAddr};
        				csv.writeNext(value);
                	}else if(type.equals("channel")){
        				user_id = Utils.checkNull(rs.getString("UserId"));
        				username = dao.getUserName("user", user_id, language);
        				date = Utils.checkNull(rs.getString("VisitDate").replace(".0", ""));
        				ipAddr = Utils.checkNull(rs.getString("VisitIp"));
        				String detail =  rs.getString("ChannelName");
        				
        				String [] value = {username, date, ipAddr,detail};
        				csv.writeNext(value);
                	}else if(type.equals("content")){
        				user_id = Utils.checkNull(rs.getString("UserId"));
        				username = dao.getUserName("user", user_id, language);
        				date = Utils.checkNull(rs.getString("VisitDate").replace(".0", ""));
        				ipAddr = Utils.checkNull(rs.getString("VisitIp"));
        				String detail =  rs.getString("ContentName");
        				
        				String [] value = {username, date, ipAddr,detail};
        				csv.writeNext(value);
                	}else if(type.equals("advanced")){
                    	if(option.equals("1")){
            				user_id = Utils.checkNull(rs.getString("UserId"));
            				username = dao.getUserName("user", user_id, language);
            				date = Utils.checkNull(rs.getString("VisitDate").replace(".0", ""));
            				ipAddr = Utils.checkNull(rs.getString("VisitIp"));
            				
            				String [] value = {username, date, ipAddr};
            				csv.writeNext(value);
                    	}else if(option.equals("3")){
            				user_id = Utils.checkNull(rs.getString("UserId"));
            				username = dao.getUserName("user", user_id, language);
            				date = Utils.checkNull(rs.getString("VisitDate").replace(".0", ""));
            				ipAddr = Utils.checkNull(rs.getString("VisitIp"));
            				String detail =  rs.getString("ChannelName");
            				
            				String [] value = {username, date, ipAddr,detail};
            				csv.writeNext(value);
                    	}else if(option.equals("4")){
            				user_id = Utils.checkNull(rs.getString("UserId"));
            				username = dao.getUserName("user", user_id, language);
            				date = Utils.checkNull(rs.getString("VisitDate").replace(".0", ""));
            				ipAddr = Utils.checkNull(rs.getString("VisitIp"));
            				String detail =  rs.getString("ContentName");
            				
            				String [] value = {username, date, ipAddr,detail};
            				csv.writeNext(value);
                    	}
                	} 
                	
    			}
    			try {
					stmt.close();
			 		} catch (SQLException e) {
                 // TODO Controlar exception
			 		e.printStackTrace();
         							} 
    			
    			
                csv.flush();
                res.flushBuffer();
                 

		}finally{
			    
             
				if (conn != null){
                     	try {
                             conn.close();
                     		} catch (SQLException e) {
                             // TODO Controlar exception
                             e.printStackTrace();
                     							     }       
             
							     }
		        }
		
		}catch (SQLException e) {
            // TODO Controlar exception
            e.printStackTrace();
		
	}
	}
}
	
	
