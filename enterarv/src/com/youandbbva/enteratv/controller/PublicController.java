package com.youandbbva.enteratv.controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.youandbbva.enteratv.Constants;
import com.youandbbva.enteratv.DSManager;
import com.youandbbva.enteratv.Utils;
import com.youandbbva.enteratv.dao.CategoryDAO;
import com.youandbbva.enteratv.dao.ContentDAO;
import com.youandbbva.enteratv.dao.PublicDAO;
import com.youandbbva.enteratv.dao.SystemDAO;
import com.youandbbva.enteratv.dao.UserDAO;
import com.youandbbva.enteratv.dao.VisitorDAO;
import com.youandbbva.enteratv.domain.ContentInfo;
import com.youandbbva.enteratv.domain.FamilyInfo;
import com.youandbbva.enteratv.domain.UserInfo;

/**
 * Handle all action for public page.
 *  
 * @author CJH
 *
 */

@Controller
@RequestMapping("/public/")
@Component("PublicController")
public class PublicController extends com.youandbbva.enteratv.Controller{

	
	@ExceptionHandler(value = Exception.class)
	public ModelAndView handlerException(HttpServletRequest req) {
		return new ModelAndView("error");
	}
	
	@ExceptionHandler(com.youandbbva.enteratv.beans.ExceptionHandler.class)
	public ModelAndView handleCustomException(ExceptionHandler ex) {
		return new ModelAndView("error");
	}

	@ExceptionHandler(com.youandbbva.enteratv.beans.ExceptionHandler.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ModelAndView handle404Exception(ExceptionHandler ex) {
		return new ModelAndView("error");
	}
	
	/**
	 * Home page.
	 * 
	 * @param req
	 * @param res
	 * @return view
	 */
	@RequestMapping("home.html")
	public ModelAndView home(
			HttpServletRequest req, HttpServletResponse res){
		Connection conn = DSManager.getConnection();

		ModelAndView mv = new ModelAndView("public_home");
		UserDAO user = new UserDAO(conn);
		UserInfo userinfo = new UserInfo();
		user.getUserInfo(userinfo.getUserId());
		
		userinfo = session.getFrontUserInfo(req.getSession());
		if (userinfo==null){
			return new ModelAndView("redirect:/user/login.html");
		}
		
		

		try{
			
			/////////
			
			PublicDAO publicDao = new PublicDAO(conn);
			SystemDAO systemDao = new SystemDAO(conn);
			
			String[] html = publicDao.generateSlide();
			
					
			systemDao.update(Constants.OPTION_SLIDER_FOR_HTML, html[0]);
			systemDao.update(Constants.OPTION_SLIDER_NAV_HTML, html[1]);
			systemDao.update(Constants.OPTION_SLIDER_FOR_HTML+"_mobile", html[2]);
			systemDao.update(Constants.OPTION_SLIDER_NAV_HTML+"_mobile", html[3]);
			
			
			///////
			
			String sessionID = req.getSession().getId();
			String today = Utils.getToday();
			String time = Utils.getTodayWithTime();
			String addr = req.getRemoteAddr();
			
			conn.setAutoCommit(false);

			PublicDAO dao = new PublicDAO(conn);
			
			if (!dao.isExistVisitor(userinfo.getUserId())){
				dao.insertVisitor(userinfo.getUserId(), Constants.LogType.ACCESS.getCode(), time, addr);
			}
			conn.commit();
			
			String agent = Utils.checkNull(req.getHeader("User-Agent"));
			System.out.println("CCCCC:"+Utils.isMobile(agent));
			
			mv.addObject("family", dao.getFamilyList());

			if (Utils.isMobile(agent)){
				mv.addObject("slider_nav", dao.getOptionHTML(Constants.OPTION_SLIDER_NAV_HTML+"_mobile"));
				mv.addObject("slider_for", dao.getOptionHTML(Constants.OPTION_SLIDER_FOR_HTML+"_mobile"));
			} else {
				mv.addObject("slider_nav", dao.getOptionHTML(Constants.OPTION_SLIDER_NAV_HTML));
				mv.addObject("slider_for", dao.getOptionHTML(Constants.OPTION_SLIDER_FOR_HTML));
			}
			
			mv.addObject("most_visited_channel", dao.getMostVisitedChannel(userinfo));
			mv.addObject("latest_video", dao.getLatestVideo());
			
			mv.addObject("banner_sidebar", dao.getOptionHTML(Constants.OPTION_BANNER_HOME_SIDEBAR_HTML));
			mv.addObject("banner_bottom", dao.getOptionHTML(Constants.OPTION_BANNER_HOME_BOTTOM_HTML));
//			mv.addObject("features", dao.getOptionHTML(Constants.OPTION_FEATURES_HTML));
			//mv.addObject("gallerylink", reg.getStringOfApplication("application.path")+"public/galerias.html");
		}catch (Exception e){ 
			log.error("PublicController", "home", e.toString());

			try{
				conn.rollback();
			}catch (Exception f){}
		}finally{
			
		}
		try{
			if (conn!=null)
				conn.close();
		}catch (Exception f){}

		return mv;
	}

	/**
	 * Get all channels data.
	 * 
	 * @param channel_id
	 * @param req
	 * @param res
	 */
	@RequestMapping("loadChannels.html")
	public void loadChannels(
			@RequestParam(value = "channel_id", required = false) String channel_id,
			HttpServletRequest req, HttpServletResponse res){
		int vuelta = 0;
		channel_id = Utils.checkNull(channel_id);
		
		Connection conn = DSManager.getConnection();
		
		JSONObject result = new JSONObject();
		result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_FAILED);
		result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0002", session.getLanguage(req.getSession())));
		JSONArray div_result = new JSONArray();

		
		try{
			UserInfo user = session.getFrontUserInfo(req.getSession());
			if (user==null){  
				result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}

			
			CategoryDAO categoryDao = new CategoryDAO(conn);
			PublicDAO dao = new PublicDAO(conn);
			
			String ua = req.getHeader("User-Agent");
			log.info("PublicController", "header", ua);
			
			boolean isFirefox = (ua != null && ua.indexOf("Firefox/") != -1);
			String version="1";
			if (isFirefox){
				version = ua.replaceAll("^.*?Firefox/", "");//.replaceAll("", "");
			}
			
//			user.setMozila(isFirefox);
//			user.setChannelID(channel_id);
//			user.setVersion(version);
			
			ArrayList list = categoryDao.getFamilyList();
			
			for (int i=0; i<list.size(); i++){
				FamilyInfo item = (FamilyInfo) list.get(i);
				if (item.getVisible().equals("1")){
					JSONObject obj = new JSONObject();
					obj.put("parent", item.toJSONObject());
					obj.put("child", dao.recallChannelList(item.getId(), (long)0, user));
					div_result.put(obj);	
					
				}				
			}
			
			
			
			// Initialize all value.
			result = setResponse(result, "list", div_result);
			result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_SUCCESS);
			result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0001", session.getLanguage(req.getSession())));
			
		}catch (Exception e){
			log.error("PublicController", "loadChannels", e.toString());
			result = setResponse(result, "list", "");
		}
		finally
		{
			
		}
		try{
			if (conn!=null)
					conn.close();				
				}catch (Exception f){}
		
		
		response(res, result);
	}

	/**
	 * Channel page.
	 * 
	 * @param channel_id
	 * @param req
	 * @param res
	 * @return view
	 */
	@RequestMapping("channel.html")
	public ModelAndView channel(
			@RequestParam(value = "channel_id", required = false) String channel_id,
			HttpServletRequest req, HttpServletResponse res){

		UserInfo user = session.getFrontUserInfo(req.getSession());
		if (user==null){
			return new ModelAndView("redirect:/user/login.html");
		}

		ModelAndView mv = new ModelAndView("public_channel");
		
		channel_id = Utils.checkNull(channel_id);
		String from = Utils.checkNull(req.getParameter("kind"));
		
		Connection conn = DSManager.getConnection();

		try{
			if (channel_id.length()==0 && from.equals("search")){
				
				PublicDAO dao = new PublicDAO(conn);
				mv.addObject("family", dao.getFamilyList());
				mv.addObject("channel_id", "");

				mv.addObject("most_visited_channel", dao.getMostVisitedChannel(user));
				
				mv.addObject("banner_sidebar", dao.getOptionHTML(Constants.OPTION_BANNER_INTERNAL_SIDEBAR_HTML));
				mv.addObject("banner_bottom", dao.getOptionHTML(Constants.OPTION_BANNER_INTERNAL_BOTTOM_HTML));
				
				return mv;
			}else{
				Long channelID = Utils.getLong(channel_id);
				
				conn.setAutoCommit(false);
				
				String sessionID = req.getSession().getId();
				String userID = session.getFrontUserID(req.getSession());
				String today = Utils.getToday();
				String time = Utils.getTodayWithTime();
				String addr = req.getRemoteAddr();
				
				PublicDAO dao = new PublicDAO(conn);
				VisitorDAO visitorDao = new VisitorDAO(conn);
				ContentDAO cont = new ContentDAO();
				
				
				if (!visitorDao.isExist(Integer.parseInt(userID),time))
					visitorDao.insert(Integer.parseInt(userID),cont.getChannel_IDcontent(channel_id)  , time, req.getRemoteAddr());
				conn.commit();
				
				ContentInfo content = dao.getContentID(channelID);
				if (content!=null){
					return new ModelAndView("redirect:/public/content.html?content_id="+content.getId()+"&channel_id="+channelID+"&content_blog="+content.getBlog());
				}else{
					return new ModelAndView("redirect:/public/content.html?content_id=0&channel_id="+channelID);
				}
			}
			
		}catch (Exception e){ 
			log.error("PublicController", "channel", e.toString());

			try{
				conn.rollback();
			}catch (Exception f){}
		}finally{
			
		}
		try{
			if (conn!=null)
				conn.close();
		}catch (Exception f){}
//		return mv;
		return new ModelAndView("redirect:/public/home.html");
	}
	
	/**
	 * Get Content list with DataTable.
	 * 
	 * @param channel_id
	 * @param request
	 * @param response
	 */
	@RequestMapping("loadContent.html")
	public void loadContent(
			@RequestParam(value = "channel_id", required = false) String channel_id,
			HttpServletRequest request, HttpServletResponse response){

		channel_id = Utils.checkNull(channel_id);
	     
	    JSONObject result = new JSONObject();
//			Long channelID = Utils.getLong(channel_id);
			String today = Utils.getToday("-");
			
		    String[] cols = { "c.name", "c.updated_at" };
		    String table = "content";

		    int amount = 10;
		    int start = 0;
		    int col = 1;
		 
		    String dir = "desc";
		 
		    String sStart = Utils.checkNull(request.getParameter("start"));
		    String sAmount = Utils.checkNull(request.getParameter("length"));
//		    String sEcho = Utils.checkNull(request.getParameter("sEcho"));
		    String sCol = Utils.checkNull(request.getParameter("iSortCol_0"));
		    String sdir = Utils.checkNull(request.getParameter("sSortDir_0"));

		    Map<String, String[]> parameters = request.getParameterMap();
		    sdir = parameters.get("order[0][dir]")[0];
		    sCol = parameters.get("order[0][column]")[0];
		    
		    String searchTerm = Utils.checkNull(request.getParameter("search[value]"));
		    String individualSearch = "";
		    
		    if (sStart .length()>0) {
		        start = Integer.parseInt(sStart);
		        if (start < 0)
		            start = 0;
		    }

		    if (sAmount .length()>0) {
		        amount = Integer.parseInt(sAmount);
		        if (amount < 10 || amount > 100)
		            amount = 10;
		    }
		    
		    if (sCol .length()>0 ) {
		        col = Integer.parseInt(sCol);
		        if (col < 0 || col > 1)
		            col = 1;
		    }
		    
		    if (sdir .length()>0 ) {
		        if (!sdir.equals("desc"))
		            dir = "asc";
		    }
		    
		    String colName = cols[col];
		    Long total = (long)0;
		    Long totalAfterFilter = (long)0;
		    
		    Connection conn = DSManager.getConnection();
		    
		    String additionalSQL = "";
		    if (channel_id.length()>0){
		    	//additionalSQL += " c.id in ( select content_id from content where Channel_ChannelId=" + channel_id + " ) ";
		    }
		    
		    if (additionalSQL.length()>0)
		    	additionalSQL = " and " + additionalSQL;

			try{
				PublicDAO dao = new PublicDAO(conn);
				
				String sql = " select count(*) from " + table + " c where c.Contenttype_ContenttypeId=2 and c.ContentStatus='A' "/* + additionalSQL*/; 
				total = dao.getCount(sql);
				totalAfterFilter = total;
				
				sql = " select c.*, d.MenuValue from " + table + " c, menu d where c.Contenttype_ContenttypeId=2 and  d.MenuCode='cnt2' and d.MenuDivl=c.Contenttype_ContenttypeId and c.ContentStatus='A'" + additionalSQL;
				String searchSQL = "";
				String globeSearch =  " ( c.name like '%"+searchTerm+"%' "
	                    + " or d.MenuValue like '%"+searchTerm+"%'"
	                   	+ " ) " ;
				
		        if(searchTerm.length()>0 && individualSearch.length()>0){
		            searchSQL = " ( " + globeSearch + " and " + individualSearch + " ) ";
		        } else if(individualSearch.length()>0){
		            searchSQL = individualSearch;
		        }else if(searchTerm.length()>0){
		            searchSQL = globeSearch;
		        }
		        
		        if (searchSQL.length()>0)
		        	searchSQL = " and " + searchSQL;
				
		        sql += searchSQL;
		        sql += " order by " + colName + " " + dir;
		        sql += " limit " + start + ", " + amount;	        
				
		        JSONArray array = dao.getContent(sql, session.getLanguage(request.getSession()));
		        
				sql = " select count(*) from " + table + " c, menu d where cMenuCode=2 and  d.MenuCode='cnt2' and d.MenuDivl=c.Contenttype_ContenttypeId and ContentStatus = 'A'"+ additionalSQL;
				if (searchTerm!=""){
					sql += searchSQL;
					totalAfterFilter = dao.getCount(sql);
				}

				result.put("recordsTotal", total);
		        result.put("recordsFiltered", totalAfterFilter);
		        result.put("data", array);
				
			}catch (Exception e){ 
				log.error("PublicController", "loadContent", e.toString());

			}finally{
				
			}
		
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
			
		try{
			response.setContentType("application/json");
	        response.setHeader("Cache-Control", "no-store");
	        response.getWriter().print(result);			
		}catch (Exception ee){}
	}
	
	/**
	 * Content page.
	 * 
	 * @param req
	 * @param res
	 * @return view
	 */
	@RequestMapping("content.html")
	public ModelAndView content(
			@RequestParam(value = "content_id", required = false) String content_id,
			@RequestParam(value = "channel_id", required = false) String channel_id,
			@RequestParam(value = "content_blog", required = false) String content_blog,
			HttpServletRequest req, HttpServletResponse res){

		String access_from = Utils.checkNull(req.getParameter("access_from"));
		if (access_from.equals("cms")){
//			UserInfo user = session.getUserInfo(req.getSession());
//			UserInfo user = new UserInfo();
//			user.setId(Constants.OUTSIDE_USER_ID);
//			user.setUserName("");
//			user.setLastName("");
//			session.setFrontUserInfo(req.getSession(), user);
		}
		
		UserInfo user = session.getFrontUserInfo(req.getSession());
		if (user==null){
			return new ModelAndView("redirect:/user/login.html");
		}

		ModelAndView mv = new ModelAndView("public_content");
		
		content_id = Utils.checkNull(content_id, "0");
		channel_id = Utils.checkNull(channel_id);
		//content_blog = Utils.checkNull(content_blog);
		
		Connection conn = DSManager.getConnection();

		try{
			String agent = Utils.checkNull(req.getHeader("User-Agent"));
			
			Long contentID = Utils.getLong(content_id);
			
			conn.setAutoCommit(false);
			
			String sessionID = req.getSession().getId();
			String userID = session.getFrontUserID(req.getSession());
			String today = Utils.getToday();
			String time = Utils.getTodayWithTime();
			String addr = req.getRemoteAddr();
			
			VisitorDAO visitorDao = new VisitorDAO(conn);
			ContentDAO contentDao = new ContentDAO(conn);
									
			if (!visitorDao.isExist(Integer.parseInt(userID),time)){
				if(contentID != 0){
					visitorDao.insert(Integer.parseInt(userID), content_id , time, req.getRemoteAddr());
				}else{
					visitorDao.insert(Integer.parseInt(userID), contentDao.getChannel_IDcontent(channel_id) , time, req.getRemoteAddr());
				}
			}
				
			
			conn.commit();
			PublicDAO dao = new PublicDAO();
//			CategoryDAO categoryDao = new CategoryDAO(conn);
			mv.addObject("family", dao.getFamilyList());
			mv.addObject("banner_sidebar", dao.getOptionHTML(Constants.OPTION_BANNER_INTERNAL_SIDEBAR_HTML));
			mv.addObject("banner_bottom", dao.getOptionHTML(Constants.OPTION_BANNER_INTERNAL_BOTTOM_HTML));
			
			mv.addObject("most_visited_channel", dao.getMostVisitedChannel(user));
			
			ContentInfo content = null;
			if(contentID != 0){
				content = contentDao.getContent(contentID);
			}else{
				content_id = contentDao.getChannel_IDcontent(channel_id);
				content = contentDao.getContent(Long.parseLong(content_id));
			}
		
			mv.addObject("channel_id", channel_id);
			mv.addObject("files", new ArrayList());

			Long channelID = 0L;

			if(!channel_id.isEmpty()) {
				channelID = Utils.getLong(channel_id);
			}
			//mv.addObject("gallerylink", reg.getStringOfApplication("application.path")+"public/galerias.html");
			if(channelID != 0) {
				long galleryId = contentDao.getGalleryIdForChannel(channelID);
				if(galleryId != 0)
					mv.addObject("gallerylink", Long.toString(galleryId));
			}
// mv.addObject("file", "");
			
			if (content!=null/* && content.getBlog()!=null && content.getBlog().equals(content_blog)*/){
//				return new ModelAndView("redirect:/public/home.html");
				mv.addObject("content_id", contentID);
			//	mv.addObject("content_blog", content.getBlog());
				mv.addObject("title", content.getName());
				
				if (Utils.isMobile(agent)){
					mv.addObject("content", content.getHtmlMobile());
				} else {
					mv.addObject("content", content.getHtml());
				}
				
				if (content.getType().equals("2")){
					if (channel_id.length()>0){
						
						//Long channelID = Utils.getLong(channel_id);
//						if (!dao.isExistVisitor(Integer.parseInt(userID), Constants.LogType.CHANNEL.getCode(), sessionID, today)){
//							dao.insertVisitor(userID, Constants.LogType.CHANNEL.getCode(), sessionID, today, time, addr, channelID, "");
//							conn.commit();
//						}

						mv.addObject("files", contentDao.getFileContent(channelID));
//						ContentInfo c = contentDao.getFileContent(channelID);
//						if (c!=null)
//							mv.addObject("file", c.getHtml());
						
					}else{
						channelID = contentDao.getGalleryIdForChannel(contentID);
						if (channelID!=0){
							mv.addObject("channel_id", channelID);
//							if (!dao.isExistVisitor(Integer.parseInt(userID), Constants.LogType.CHANNEL.getCode(), sessionID, today)){
//								dao.insertVisitor(userID, Constants.LogType.CHANNEL.getCode(), sessionID, today, time, addr, channelID, "");
//								conn.commit();
//							}
							
							mv.addObject("files", contentDao.getFileContent(channelID));
//							ContentInfo c = contentDao.getFileContent(channelID);
//							if (c!=null)
//								mv.addObject("file", c.getHtml());
						}
					}
					
				}else{
					mv.addObject("file", "");
				}
			}else{
				mv.addObject("content_id", "");
			//	mv.addObject("content_blog", "");
				mv.addObject("title", "No se encontraron contenidos");
				mv.addObject("content", "");
			}
			
		}catch (Exception e){ 
			log.error("PublicController", "content", e.toString());

			try{
				conn.rollback();
			}catch (Exception f){}
			
			mv.addObject("content_id", "");
		//	mv.addObject("content_blog", "");
			mv.addObject("title", "No se encontraron contenidos");
			mv.addObject("content", "");
			mv.addObject("files", new ArrayList());
		}finally{
			
		}
		try{
			if (conn!=null)
				conn.close();
		}catch (Exception f){}
		return mv;
	}
	
	/**
	 * Insert open answer. 
	 * 		add answer.
	 * 
	 * @param content_id
	 * @param count
	 * @param req
	 * @param res
	 */
	//@RequestMapping("insertAnswer.html")
/*	public void insertAnswer(
			@RequestParam(value = "content_id", required = false) String content_id,
			@RequestParam(value = "count", required = false) String count,
			HttpServletRequest req, HttpServletResponse res){

		JSONObject result = new JSONObject();
		
		result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_FAILED);
		result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0002", session.getLanguage(req.getSession())));
		
		content_id = Utils.checkNull(content_id);
		count = Utils.checkNull(count);

		Connection conn = DSManager.getConnection();

		try{
			UserInfo user = session.getFrontUserInfo(req.getSession());
			if (user==null){
//				user = new UserInfo();
//				user.setId(Constants.OUTSIDE_USER_ID);
//				user.setUserName("");
//				user.setLastName("");
				result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}
			
			conn.setAutoCommit(false);

			ContentDAO dao = new ContentDAO(conn);
			
			Long contentID = Utils.getLong(content_id);
			int c = Utils.getInt(count);
			
			for (int i=0; i<c; i++){
				String question_id = Utils.checkNull(req.getParameter("question["+i+"][question]"));
				String answer = Utils.checkNull(req.getParameter("question["+i+"][answer]"));
				String answer_id = Utils.checkNull(req.getParameter("question["+i+"][answer_id]"));
				
				if (question_id.length()>0 && (answer.length()>0 || !answer_id.equals("0"))){
					Long questionID = Utils.getLong(question_id);
					
					if (answer.length()>0)
			/*			if (!dao.isValidAnswer(contentID, questionID, answer))
							throw new Exception("");
					if (answer_id.length()>0)
						if (!dao.isValidAnswer(contentID, questionID, Utils.getLong(answer_id)))
							dao.deleteContentAnswer(contentID, questionID, Utils.getLong(answer_id));
					
					answer_id = answer_id.length()==0 ? "0" : answer_id;
					dao.insertAnswer(contentID, questionID, user.getUserId(), answer, Utils.getLong(answer_id));
				}
			}

			conn.commit();
			
			dao.generateHTML(contentID, session.getLanguage(req.getSession()));
			conn.commit();
			
			result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_SUCCESS);
			result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0001", session.getLanguage(req.getSession())));
			
		}catch (Exception e){ 
			log.error("PublicController", "insertAnswer", e.toString());
			
			try{
				conn.rollback();
			}catch (Exception ex){}
		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}

		response(res, result);
	}	

	*/
	/**
	 * Search content with Dialog.
	 * 
	 * @param search_text
	 * @param page
	 * @param req
	 * @param res
	 */
	@RequestMapping("searchContent.html")
	public void searchContent(
			@RequestParam(value = "search_text", required = false) String search_text,
			@RequestParam(value = "page", required = false) String page,
			HttpServletRequest req, HttpServletResponse res){

	    JSONObject result = new JSONObject();
		result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_FAILED);
		result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0002", session.getLanguage(req.getSession())));
		
		search_text = Utils.encode(Utils.checkNull(search_text));
		page = Utils.checkNull(page);
		if (page.length()==0 || page.equals("0"))
			page="1";

		String today = Utils.getToday("-");
	    int amount = 10;
	    
	    String additionalSQL = "";
	    String sql = "";
	    
	    String sqlFrom = " content c ";
	    String sqlWhere = " ( c.Contenttype_ContenttypeId='2' or c.Contenttype_ContenttypeId='5' ) and c.ContentStatus='A' ";
	    String sqlChannel = "";
	    
	    Connection conn = DSManager.getConnection();
	    
	    try{
			UserInfo user = session.getFrontUserInfo(req.getSession());
			if (user==null){
				result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}
			
		    int start = (Utils.getInt(page)-1) * amount;
		    
		    if (search_text.length()>0){
				if (additionalSQL.length()>0)
			    	additionalSQL += " and ";
		    		additionalSQL += " ( c.ContentName like '%" + search_text + "%' ) ";   // or  ";
//		    		additionalSQL += " c.ContentId in ( select b.content_id from bbva_content_channel b, bbva_channel ch where b.channel_id=ch.id and ch.name like '%" + search_text + "%' ) ";
//		    		additionalSQL += " ) ";
		    }
		    
		    // user filter
		    
		    if (user.getUserRol()!=null && user.getUserRol().equals("1")){	
		    	
		    }else{
		    	if (user.getUserId() == 0){
		    		if (sqlChannel.length()>0)
		    			sqlChannel += " and ";
		    			sqlChannel += " a.ChannelId not in ( select ChannelId from channelcity ) and a.ChannelId not in ( select ChannelId from channelmaindirection ) and a.ChannelId not in ( select ChannelId from Channelcompany ) ";
		    	}else{
		    		if (sqlChannel.length()>0)
		    			sqlChannel += " and ";
		    		
		    		if (user.getMainDirection()!=(long)0){
		    			sqlChannel += " ( a.ChannelId not in ( select Channel_ChannelId from channelmaindirection ) or a.ChannelId in ( select Channel_ChannelId from channelmaindirection where ChannelmaindirectionId='"+user.getMainDirection()+"' ) ) " ;
		    		} else {
		    			sqlChannel += " a.ChannelId not in ( select Channel_ChannelId from channelmaindirection ) " ;
		    		}
		    		
		    		if (sqlChannel.length()>0)
		    			sqlChannel += " and ";
		    		
		    		if (user.getCompany()!=(long)0){
		    			sqlChannel += " ( a.ChannelId not in ( select Channel_ChannelId from channelcompany ) or a.ChannelId in ( select Channel_ChannelId from channelcompany where ChannelcompanyId='"+user.getCompany()+"' ) ) " ;
		    		} else {
		    			sqlChannel += " a.ChannelId not in ( select Channel_ChannelId from channelcompany ) " ;
		    		}
		    		
		    		if (sqlChannel.length()>0)
		    			sqlChannel += " and ";
		    		if (user.getCity()!=(long)0){
		    			sqlChannel += " ( a.ChannelId not in ( select Channel_ChannelId from channelcity ) or a.ChannelId in ( select Channel_ChannelId from channelcity where ChannelcityId='"+user.getCity()+"' ) ) " ;
		    		} else {
		    			sqlChannel += " a.ChannelId not in ( select Channel_ChannelId from channel_city ) " ;
		    		}
		    		
		    		if (user.getUserAccessLevel() != 0){
			    		if (sqlChannel.length()>0)
			    			sqlChannel += " and ";
		    			
			    		if (user.getUserAccessLevel() == 1)
			    			sqlChannel += " a.ChannelSecurityLevel in ( '1', '2', '3', '4' ) ";
			    		if (user.getUserAccessLevel() == 2)
			    			sqlChannel += " a.ChannelSecurityLevel in ( '2', '3', '4' ) ";
			    		if (user.getUserAccessLevel() == 3)
			    			sqlChannel += " a.ChannelSecurityLevel in ( '3', '4' ) ";
			    		if (user.getUserAccessLevel() == 4)
			    			sqlChannel += " a.ChannelSecurityLevel in ( '4' ) ";
		    		}
		    	}
		    }
		    
		    // -------------
		    
			PublicDAO dao = new PublicDAO(conn);
			
			sql = " select count(*) from " + sqlFrom + " where " + sqlWhere ;
			if (additionalSQL.length()>0)
				sql += " and ( " + additionalSQL + " ) ";
			if (sqlChannel.length()>0)
				sql += "and c.ContentId in ( select t.ContentId from content t, channel a where t.Channel_ChannelId=a.ChannelId and " + sqlChannel + " ) ";
			
//			System.out.println("CCC:"+sql);
			Long total = dao.getCount(sql);
			//select c.* , d.MenuValue from  content c  , menu d where d.MenuDivl='cnt2' and d.MenuCode=c.Contenttype_ContenttypeId and  ( c.Contenttype_ContenttypeId='2' or c.Contenttype_ContenttypeId='5' )  and c.ContentStatus='A' and (  ( c.ContentName like '%hola%'   ))  order by c.ContentPublishDate desc  limit 10;
			sql = " select c.* , d.MenuValue from"+ sqlFrom + " , menu d where d.MenuDivl='cnt2' and d.MenuCode="+ "c.Contenttype_ContenttypeId and  " + sqlWhere ;
			if (additionalSQL.length()>0)
				sql += " and ( " + additionalSQL + " ) ";
			if (sqlChannel.length()>0)
				sql += "  and c.ContentId in ( select t.ContentId from content t, channel a where t.Channel_ChannelId=a.ChannelId and " + sqlChannel + " ) ";
		
			sql += "  order by c.ContentPublishDate desc ";
			sql += " limit " + start + ", " + amount + " ";
			
			JSONArray array = dao.getContent(sql, session.getLanguage(req.getSession()), start);
			
			long size=0;
			if (total % amount == 0)
				size = total / amount;
			else
				size = total / amount + 1;
			
			result = setResponse(result, "page", Utils.getInt(page));
			result = setResponse(result, "pages", size);
			result = setResponse(result, "result", array);
		    
			result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_SUCCESS);
			result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0001", session.getLanguage(req.getSession())));
			
		}catch (Exception e){ 
			log.error("PublicController", "searchContent", e.toString());

		}finally{
			
		}
	    try{
			if (conn!=null)
				conn.close();
		}catch (Exception f){}
	    
        response(res, result);
	}

	@RequestMapping("oportunidades.html")
	public ModelAndView opportunities(
			HttpServletRequest req, HttpServletResponse res){
		UserInfo user = session.getFrontUserInfo(req.getSession());
		if (user==null){
			return new ModelAndView("redirect:/user/login.html");
		}
		
		int userid_body = user.getUserId();
		String sessionid_body=DigestUtils.md5Hex(userid_body+Constants.SESSION_HASH);
		return new ModelAndView("redirect:/opportunities/home.html?user_id="+userid_body+"&session_id="+sessionid_body);
	}

	@RequestMapping("galerias.html")
	public ModelAndView gallerys(
			HttpServletRequest req, HttpServletResponse res){

		UserInfo user = session.getFrontUserInfo(req.getSession());
		if (user==null){
			return new ModelAndView("redirect:/user/login.html");
		}

		ModelAndView mv = new ModelAndView("public_gallerys");
		
		Connection conn = DSManager.getConnection();

		try{
			PublicDAO dao = new PublicDAO(conn);
			mv.addObject("family", dao.getFamilyList());
			mv.addObject("banner_sidebar", dao.getOptionHTML(Constants.OPTION_BANNER_INTERNAL_SIDEBAR_HTML));
			mv.addObject("banner_bottom", dao.getOptionHTML(Constants.OPTION_BANNER_INTERNAL_BOTTOM_HTML));
			
			mv.addObject("most_visited_channel", dao.getMostVisitedChannel(user));
			
		}catch (Exception e){ 
			log.error("PublicController", "gallerys", e.toString());
			
		}finally{
			
		}
		try{
			if (conn!=null)
				conn.close();
		}catch (Exception f){}
		return mv;
	}
	
	@RequestMapping("loadGallery.html")
	public void loadGallery(
			HttpServletRequest req, HttpServletResponse res){

	    JSONObject result = new JSONObject();
		result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_FAILED);
		result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0002", session.getLanguage(req.getSession())));
		
		String today = Utils.getToday("-");
	    String additionalSQL = "";
	    String sql = "";
	    
	    String sqlFrom = " bbva_content c,  bbva_content_gallery pa, bbva_content_gallery_media ga ";
	    String sqlWhere = " c.type='005' and c.active='" + Constants.DEFAULT_ACTIVE + "' and c.status='0' and c.validity_end>='" + today + "' and pa.content_id=c.id and ga.content_id=c.id  " ;
	    String sqlChannel = "";
	    
	    Connection conn = DSManager.getConnection();
	    
	    try{
			UserInfo user = session.getFrontUserInfo(req.getSession());
			if (user==null){
				result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}
			
		    // user filter
		    
		    if (user.getUserRol()!=null && user.getUserRol().equals("1")){	
		    	
		    }else{
/*    	if (user.getUserId() == Integer.parseInt(Constants.OUTSIDE_USER_ID)){
		    		if (sqlChannel.length()>0)
		    			sqlChannel += " and ";
		    		sqlChannel += " a.id not in ( select channel_id from bbva_channel_ciudad ) and a.id not in ( select channel_id from bbva_channel_direccion ) and a.id not in ( select channel_id from bbva_channel_empresa ) ";
		    	}else{
*/    
		    		if (sqlChannel.length()>0)
		    			sqlChannel += " and ";
		    		
		    		if (user.getMainDirection()!=(long)0){
		    			sqlChannel += " ( a.id not in ( select channel_id from bbva_channel_direccion ) or a.id in ( select channel_id from bbva_channel_direccion where direccion_id='"+user.getMainDirection()+"' ) ) " ;
		    		} else {
		    			sqlChannel += " a.id not in ( select channel_id from bbva_channel_direccion ) " ;
		    		}
		    		
		    		if (sqlChannel.length()>0)
		    			sqlChannel += " and ";
		    		
		    		if (user.getCompany()!=(long)0){
		    			sqlChannel += " ( a.id not in ( select channel_id from bbva_channel_empresa ) or a.id in ( select channel_id from bbva_channel_empresa where empresa_id='"+user.getCompany()+"' ) ) " ;
		    		} else {
		    			sqlChannel += " a.id not in ( select channel_id from bbva_channel_empresa ) " ;
		    		}
		    		
		    		if (sqlChannel.length()>0)
		    			sqlChannel += " and ";
		    		if (user.getCity()!=(long)0){
		    			sqlChannel += " ( a.id not in ( select channel_id from bbva_channel_ciudad ) or a.id in ( select channel_id from bbva_channel_ciudad where ciudad_id='"+user.getCity()+"' ) ) " ;
		    		} else {
		    			sqlChannel += " a.id not in ( select channel_id from bbva_channel_ciudad ) " ;
		    		}
		    		
		    		if (user.getUserAccessLevel() != 0){
			    		if (sqlChannel.length()>0)
			    			sqlChannel += " and ";
		    			
			    		if (user.getUserAccessLevel() == 1)
			    			sqlChannel += " a.security_level in ( '1', '2', '3', '4' ) ";
			    		if (user.getUserAccessLevel() == 002)
			    			sqlChannel += " a.security_level in ( '2', '3', '4' ) ";
			    		if (user.getUserAccessLevel() == 003)
			    			sqlChannel += " a.security_level in ( '3', '4' ) ";
			    		if (user.getUserAccessLevel() == 004)
			    			sqlChannel += " a.security_level in ( '4' ) ";
		    		}
		    	}
	//   }
		    
		    // -------------
		    
			PublicDAO dao = new PublicDAO(conn);
			
			sql = " select count(*) from " + sqlFrom + " where " + sqlWhere ;
			if (additionalSQL.length()>0)
				sql += " and ( " + additionalSQL + " ) ";
			if (sqlChannel.length()>0)
				sql += " and c.id in ( select t.content_id from bbva_content_channel t, bbva_channel a where t.channel_id=a.id and " + sqlChannel + " ) ";
			
//			System.out.println("CCC:"+sql);
			Long total = dao.getCountT(sql);
			
			sql = " select c.* , ga.media from " + sqlFrom + " where " + sqlWhere ;
			if (additionalSQL.length()>0)
				sql += " and ( " + additionalSQL + " ) ";
			if (sqlChannel.length()>0)
				sql += " and c.id in ( select t.content_id from bbva_content_channel t, bbva_channel a where t.channel_id=a.id and " + sqlChannel + " ) ";
			
			sql += " order by c.updated_at desc, c.id asc ";
			
			JSONArray array = dao.getContentT(sql);
			
			result = setResponse(result, "total", total);
			result = setResponse(result, "result", array);
		    
			result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_SUCCESS);
			result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0001", session.getLanguage(req.getSession())));
			
		}catch (Exception e){ 
			log.error("PublicController", "loadGallery", e.toString());

		}finally{
			
		}
	    try{
			if (conn!=null)
				conn.close();
		}catch (Exception f){}
				
        response(res, result);
	}

	@RequestMapping("galeria.html")
	public ModelAndView gallery(
			@RequestParam(value = "content_id", required = false) String content_id,
			HttpServletRequest req, HttpServletResponse res){

		UserInfo user = session.getFrontUserInfo(req.getSession());
		if (user==null){
			return new ModelAndView("redirect:/user/login.html");
		}

		content_id = Utils.checkNull(content_id);
		
		Connection conn = DSManager.getConnection();

		try{
			if (content_id.length()==0){
				return new ModelAndView("redirect:/public/galerias.html");
			}
			
			conn.setAutoCommit(false);
			
			PublicDAO dao = new PublicDAO(conn);
			
			String sessionID = req.getSession().getId();
			//String userID = session.getFrontUserID(req.getSession());
			String today = Utils.getToday();
			String time = Utils.getTodayWithTime();
			String addr = req.getRemoteAddr();
			
			Long contentID = Utils.getLong(content_id);
//			if (contentID!=0){
//				if (!dao.isExistVisitor(Integer.parseInt(userID), Constants.LogType.CONTENT.getCode(), sessionID, today)){
//					dao.insertVisitor(userID, Constants.LogType.CONTENT.getCode(), sessionID, today, time, addr, contentID, "");
//					conn.commit();
//				}
//			}
			
			ModelAndView mv = new ModelAndView("public_gallery");
			
			mv.addObject("family", dao.getFamilyList());
			mv.addObject("banner_sidebar", dao.getOptionHTML(Constants.OPTION_BANNER_INTERNAL_SIDEBAR_HTML));
			mv.addObject("banner_bottom", dao.getOptionHTML(Constants.OPTION_BANNER_INTERNAL_BOTTOM_HTML));
			
			mv.addObject("most_visited_channel", dao.getMostVisitedChannel(user));
			//req.getHeader("Referer")
			mv.addObject("content", dao.getContent(contentID));
//			mv.addObject("content_id", contentID);
//			mv.addObject("gallery", dao.getContentGallery(contentID));
		
			return mv;
		}catch (Exception e){ 
			log.error("PublicController", "gallery", e.toString());
			
		}finally{
			
		}
		try{
			if (conn!=null)
				conn.close();
		}catch (Exception f){}
		
		return new ModelAndView("redirect:/public/galerias.html");
	}

	@RequestMapping("getGallery.html")
	public void getGallery(
			@RequestParam(value = "content_id", required = false) String content_id,
			HttpServletRequest req, HttpServletResponse res){

		content_id = Utils.checkNull(content_id);
		
	    JSONObject result = new JSONObject();
		result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_FAILED);
		result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0002", session.getLanguage(req.getSession())));
		
	    Connection conn = DSManager.getConnection();
	    
	    try{
			UserInfo user = session.getFrontUserInfo(req.getSession());
			if (user==null){
				result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}
			
			if (content_id.length()==0){
				result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}
			
			PublicDAO dao = new PublicDAO(conn);
			
			result = setResponse(result, "result", dao.getContentGallery(Utils.getLong(content_id)));
			
			result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_SUCCESS);
			result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0001", session.getLanguage(req.getSession())));
			
		}catch (Exception e){ 
			log.error("PublicController", "getGallery", e.toString());

		}finally{
			
		}try{
			if (conn!=null)
				conn.close();
		}catch (Exception f){}
				
        response(res, result);
	}
	
	
}
