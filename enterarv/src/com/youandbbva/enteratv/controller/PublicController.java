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
import com.youandbbva.enteratv.domain.Menu;
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
			HttpServletRequest req, HttpServletResponse res, ArrayList<String> lista){
		

		ModelAndView mv = new ModelAndView("public_home");
		//ModelAndView mv = new ModelAndView("prueba");
		//dao connection
		UserDAO user = new UserDAO();
		UserInfo userinfo = new UserInfo();
		
		user.getUserInfo(userinfo.getUserId());
		userinfo = session.getFrontUserInfo(req.getSession());
		
		if (userinfo==null){
			return new ModelAndView("redirect:/user/adios.html");
		}
		

		try{
			//dao connection
			PublicDAO publicDao = new PublicDAO();
			SystemDAO systemDao = new SystemDAO();
			String ua = req.getHeader("User-Agent");
			String[] html=null;
			
			boolean navegador= false;
			
			int mozilla = ua.indexOf("Firefox");
			if(mozilla > 0)
			{
				html = publicDao.generateSlideMozilla();
			}
			else
			{
				html = publicDao.generateSlide();
			}
			
			
					
			systemDao.update(Constants.OPTION_SLIDER_FOR_HTML, html[0]);
			systemDao.update(Constants.OPTION_SLIDER_NAV_HTML, html[1]);
			systemDao.update(Constants.OPTION_SLIDER_FOR_HTML+"_mobile", html[2]);
			systemDao.update(Constants.OPTION_SLIDER_NAV_HTML+"_mobile", html[3]);
		
			String sessionID = req.getSession().getId();
			String today = Utils.getToday();
			String time = Utils.getTodayWithTime();
			String addr = req.getRemoteAddr();
			
			
			
			//dao connection
			//PublicDAO publicDao = new PublicDAO();
			
			if (!publicDao.isExistVisitor(userinfo.getUserId())){
				//dao.insertVisitor(userinfo.getUserId(), Constants.LogType.ACCESS.getCode(), time, addr);
			}
		
			
			String agent = Utils.checkNull(req.getHeader("User-Agent"));
			System.out.println("CCCCC:"+Utils.isMobile(agent));
			
			//handle the form submission
			mv.addObject("family", publicDao.getFamilyList());

			if (Utils.isMobile(agent)){
				//handle the form submission
				mv.addObject("slider_nav", publicDao.getOptionHTML(Constants.OPTION_SLIDER_NAV_HTML+"_mobile"));
				mv.addObject("slider_for", publicDao.getOptionHTML(Constants.OPTION_SLIDER_FOR_HTML+"_mobile"));
			} else {
				//handle the form submission
				mv.addObject("slider_nav", publicDao.getOptionHTML(Constants.OPTION_SLIDER_NAV_HTML));
				mv.addObject("slider_for", publicDao.getOptionHTML(Constants.OPTION_SLIDER_FOR_HTML));
			}
			//handle the form submission
			
			if(mozilla > 0)
			{
				navegador = true;
			}
			
			mv.addObject("navegador", navegador);	
			mv.addObject("most_visited_channel", publicDao.getMostVisitedChannel(userinfo));
			mv.addObject("latest_video", publicDao.getLatestVideo());			
			mv.addObject("banner_sidebar", publicDao.getOptionHTML(Constants.OPTION_BANNER_HOME_SIDEBAR_HTML));
			mv.addObject("banner_bottom", publicDao.getOptionHTML(Constants.OPTION_BANNER_HOME_BOTTOM_HTML));
//			mv.addObject("saludo", saludd);	
//			String valor = dao.VideoMozillaselect(0);
//			System.out.println("valor de consulta" + valor);
			
			mv.addObject("Video1", publicDao.VideoMozillaselect(1));	
			mv.addObject("Video2", publicDao.VideoMozillaselect(2));	
			mv.addObject("Video3", publicDao.VideoMozillaselect(3));	
			mv.addObject("Video4", publicDao.VideoMozillaselect(4));	
			mv.addObject("Video5", publicDao.VideoMozillaselect(5));
			
			
		}catch (Exception e){ 
			log.error("PublicController", "home", e.toString());

			
		}
		

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

			//dao connection
			CategoryDAO categoryDao = new CategoryDAO();
			PublicDAO dao = new PublicDAO();
			
			String ua = req.getHeader("User-Agent");
			log.info("PublicController", "header", ua);
			
			boolean isFirefox = (ua != null && ua.indexOf("Firefox/") != -1);
			String version="1";
			if (isFirefox){
				version = ua.replaceAll("^.*?Firefox/", "");
			}
			
			
			ArrayList<?> list = categoryDao.getFamilyList();
			
			for (int i=0; i<list.size(); i++){
				FamilyInfo item = (FamilyInfo) list.get(i);
				if (item.getVisible().equals("1")){
					JSONObject obj = new JSONObject();
					// Initialize value.
					obj.put("parent", item.toJSONObject());
					obj.put("child", dao.recallChannelList(item.getId(), (long)0, user));
					div_result.put(obj);	
					
					JSONArray datos = new JSONArray();
					ArrayList<?> list1 = new ArrayList(); 
					Menu menu = new Menu();
					menu.setList(list);
				
					datos = dao.recallChannelList(item.getId(), (long)0, user);
					menu.setDatos(datos);
					//System.out.println("datos "+menu.getDatos());
					System.out.println("datos list1 " + menu.getList());
					System.out.println("datos item "+ item.toJSONObject());
					
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
			return new ModelAndView("redirect:/user/adios.html");
		}

		ModelAndView mv = new ModelAndView("public_channel");
		
		channel_id = Utils.checkNull(channel_id);
		String from = Utils.checkNull(req.getParameter("kind"));
		
		
		try{
			if (channel_id.length()==0 && from.equals("search")){
				
				//dao connection
				PublicDAO dao = new PublicDAO();
				//handle the form submission
				mv.addObject("family", dao.getFamilyList());
				mv.addObject("channel_id", "");
				mv.addObject("most_visited_channel", dao.getMostVisitedChannel(user));
				mv.addObject("banner_sidebar", dao.getOptionHTML(Constants.OPTION_BANNER_INTERNAL_SIDEBAR_HTML));
				mv.addObject("banner_bottom", dao.getOptionHTML(Constants.OPTION_BANNER_INTERNAL_BOTTOM_HTML));
				
				return mv;
			}else{
				Long channelID = Utils.getLong(channel_id);
				
								
				String sessionID = req.getSession().getId();
				String userID = session.getFrontUserID(req.getSession());
				String today = Utils.getToday();
				String time = Utils.getTodayWithTime();
				String addr = req.getRemoteAddr();
				
				//dao connection
				PublicDAO dao = new PublicDAO();
				VisitorDAO visitorDao = new VisitorDAO();
				ContentDAO cont = new ContentDAO();
				String strValidar="";
				strValidar = cont.getChannel_IDcontent(channel_id);
				
				if(!(strValidar.isEmpty()))
				{
					if (!visitorDao.isExist(Integer.parseInt(userID),time))
						visitorDao.insert(Integer.parseInt(userID),cont.getChannel_IDcontent(channel_id)  , time, req.getRemoteAddr());
				}
				
								
				//ContentInfo content = dao.getContentID(channelID);
				//JR - Towa
				ContentInfo content = dao.getCotentIdNew(channelID);
				
				if (content!=null){
					return new ModelAndView("redirect:/public/content.html?content_id="+content.getId()+"&channel_id="+channelID+"&content_blog="+content.getBlog());
				}else{
					return new ModelAndView("redirect:/public/content.html?content_id=0&channel_id="+channelID);
				}
			}
			
		}catch (Exception e){ 
			log.error("PublicController", "channel", e.toString());

		
		}
		//JR
		//return new ModelAndView("redirect:/public/home.html");
		return new ModelAndView("redirect:/public/content.html");
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

			String today = Utils.getToday("-");
			
		    String[] cols = { "c.name", "c.updated_at" };
		    String table = "content";

		    int amount = 10;
		    int start = 0;
		    int col = 1;
		 
		    String dir = "desc";
		    String sStart = Utils.checkNull(request.getParameter("start"));
		    String sAmount = Utils.checkNull(request.getParameter("length"));
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
		    
		    
		    String additionalSQL = "";
		    if (channel_id.length()>0){
		    
		    }
		    
		    if (additionalSQL.length()>0)
		    	additionalSQL = " and " + additionalSQL;

			try{
				//dao connection
				PublicDAO dao = new PublicDAO();
				
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
				// Initialize value.
				result.put("recordsTotal", total);
		        result.put("recordsFiltered", totalAfterFilter);
		        result.put("data", array);
				
			}catch (Exception e){ 
				log.error("PublicController", "loadContent", e.toString());

			}	
			
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

		}
		
		UserInfo user = session.getFrontUserInfo(req.getSession());
		if (user==null){
			return new ModelAndView("redirect:/user/adios.html");
		}

		ModelAndView mv = new ModelAndView("public_content");
		
		content_id = Utils.checkNull(content_id, "0");
		channel_id = Utils.checkNull(channel_id);

		

		try{
			String agent = Utils.checkNull(req.getHeader("User-Agent"));
			
			Long contentID = Utils.getLong(content_id);
			
					
			String sessionID = req.getSession().getId();
			String userID = session.getFrontUserID(req.getSession());
			String today = Utils.getToday();
			String time = Utils.getTodayWithTime();
			String addr = req.getRemoteAddr();
			
			//dao connection
			VisitorDAO visitorDao = new VisitorDAO();
			ContentDAO contentDao = new ContentDAO();
									
			if (contentID != 0)
			{
				
			 if (!visitorDao.isExist(Integer.parseInt(userID),time)){
				if(contentID != 0){
					visitorDao.insert(Integer.parseInt(userID), content_id , time, req.getRemoteAddr());
				}else{
					visitorDao.insert(Integer.parseInt(userID), contentDao.getChannel_IDcontent(channel_id) , time, req.getRemoteAddr());
				}
			}
			} 
			
			
			PublicDAO dao = new PublicDAO();
			//List <String> validacion = new ArrayList<>();
			if(channel_id.equals(""))
			{
				channel_id="0";
			}
			//validacion = dao.descarga(channel_id);
			
			
			//dao connection
			
			//handle the form submission
			mv.addObject("family", dao.getFamilyList());
			mv.addObject("banner_sidebar", dao.getOptionHTML(Constants.OPTION_BANNER_INTERNAL_SIDEBAR_HTML));
			mv.addObject("banner_bottom", dao.getOptionHTML(Constants.OPTION_BANNER_INTERNAL_BOTTOM_HTML));
			mv.addObject("most_visited_channel", dao.getMostVisitedChannel(user));
			
			System.out.println("lista " + dao.descarga(channel_id));
			mv.addObject("descarga", dao.descarga(channel_id) );
			
			
			
				
				
			
			
			long x=0;
			ContentInfo content = null;
			if(contentID != 0){
				if(content_blog.equals("1"))
				{
					content = contentDao.getContent(contentID);
				}
				else
				{
					Long channel_ID = Utils.getLong(channel_id);
					x = contentDao.getContent_T(channel_ID);
					content = contentDao.getContent(x);
					//content = contentDao.getContent(channel_ID);
				}
				
				
			}else{
				content_id = contentDao.getChannel_IDcontent(channel_id);
				content = contentDao.getContent(Long.parseLong(content_id));
			}
			//handle the form submission
			mv.addObject("channel_id", channel_id);
			mv.addObject("files", new ArrayList());

			Long channelID = 0L;

			if(!channel_id.isEmpty()) {
				channelID = Utils.getLong(channel_id);
			}
			
			if(channelID != 0) {
				long galleryId = contentDao.getGalleryIdForChannel(channelID);
				if(galleryId != 0)
					mv.addObject("gallerylink", Long.toString(galleryId));
			}

			
			if (content!=null){
				//handle the form submission
				mv.addObject("content_id", contentID);
				mv.addObject("title", content.getName());
				
				if (Utils.isMobile(agent)){
					//handle the form submission
					mv.addObject("content", content.getHtmlMobile());
				} else {
					//handle the form submission
					mv.addObject("content", content.getHtml());
				}
				System.out.print(content.getType());
				if (content.getType().equals("4")){
					if (channel_id.length()>0){
						//handle the form submission
						mv.addObject("files", contentDao.getFileContent(channelID));
						
					}else{
						channelID = contentDao.getGalleryIdForChannel(contentID);
						if (channelID!=0){
							//handle the form submission
							mv.addObject("channel_id", channelID);
							mv.addObject("files", contentDao.getFileContent(channelID));

						}
					}
					
				}else{
					//handle the form submission
					mv.addObject("file", "");
				}
			}else{
				//handle the form submission
				mv.addObject("content_id", "");
				mv.addObject("title", "No se encontraron contenidos");
				mv.addObject("content", "");
			}
			
		}catch (Exception e){ 
			log.error("PublicController", "content", e.toString());

			
			//handle the form submission
			mv.addObject("content_id", "");
			mv.addObject("title", "No se encontraron contenidos");
			mv.addObject("content", "");
			mv.addObject("files", new ArrayList());
		}
		
		return mv;
	}
	

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
	    //String sqlWhere = " ( c.Contenttype_ContenttypeId='2' or c.Contenttype_ContenttypeId='5' ) and c.ContentStatus='A' ";
	    String sqlWhere = " c.ContentStatus='A' ";
	    String sqlChannel = "";
	    
	
	    
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
		    		additionalSQL += " ( c.ContentName like '%" + search_text + "%' ) ";  

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
		    

		  //dao connection
			PublicDAO dao = new PublicDAO();
			
			sql = " select count(*) from " + sqlFrom + " where " + sqlWhere ;
			if (additionalSQL.length()>0)
				sql += " and ( " + additionalSQL + " ) ";
			if (sqlChannel.length()>0)
				sql += "and c.ContentId in ( select t.ContentId from content t, channel a where t.Channel_ChannelId=a.ChannelId and " + sqlChannel + " ) ";			

			Long total = dao.getCount(sql);

			//sql = " select c.* , d.MenuValue from"+ sqlFrom + " , menu d where d.MenuDivl='cnt2' and d.MenuCode="+ "c.Contenttype_ContenttypeId and  " + sqlWhere ;
			sql = " select c.* from"+ sqlFrom +" where " + sqlWhere ;
			if (additionalSQL.length()>0)
				sql += " and ( " + additionalSQL + " ) ";
			if (sqlChannel.length()>0)
				sql += "  and c.ContentId in ( select t.ContentId from content t, channel a where t.Channel_ChannelId=a.ChannelId and " + sqlChannel + " ) ";
		
			sql += "  order by c.ContentPublishDate desc ";
			sql += " limit " + start + ", " + amount + " ";
			
			String sql2 = "SELECT * FROM content c, contentlabel cl, label l WHERE c.ContentStatus ='A' AND cl.Content_ContentId = c.ContentId AND cl.Label_LabelId = l.LabelId AND c.ContentName LIKE '%"+ search_text +"%' order by c.ContentPublishDate desc " + " limit " + start + ", " + amount + " ";
			
			String sql3 = "(SELECT c.* FROM content c, contentlabel cl, label l WHERE c.ContentStatus ='A' AND c.ContentId=cl.Content_ContentId AND cl.Label_LabelId = l.LabelId AND c.ContentName LIKE '%"+ search_text +"%' order by c.ContentPublishDate desc) UNION (Select * from content where ContentId in ( select distinct(Content_ContentId) from contentlabel where Label_LabelId in (select LabelId from label where LabelText like '%"+ search_text +"%')) order by 1 asc)";
			
			JSONArray array = dao.getContent(sql3, session.getLanguage(req.getSession()), start);
			
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

		}
	    
        response(res, result);
	}

	@RequestMapping("oportunidades.html")
	public ModelAndView opportunities(
			HttpServletRequest req, HttpServletResponse res){
		UserInfo user = session.getFrontUserInfo(req.getSession());
		if (user==null){
			return new ModelAndView("redirect:/user/adios.html");
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
			return new ModelAndView("redirect:/user/adios.html");
		}

		ModelAndView mv = new ModelAndView("public_gallerys");
		
		

		try{
			//dao connection
			PublicDAO dao = new PublicDAO();
			//handle the form submission
			mv.addObject("family", dao.getFamilyList());
			mv.addObject("banner_sidebar", dao.getOptionHTML(Constants.OPTION_BANNER_INTERNAL_SIDEBAR_HTML));
			mv.addObject("banner_bottom", dao.getOptionHTML(Constants.OPTION_BANNER_INTERNAL_BOTTOM_HTML));		
			mv.addObject("most_visited_channel", dao.getMostVisitedChannel(user));
			
		}catch (Exception e){ 
			log.error("PublicController", "gallerys", e.toString());
			
		}
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
	    
	    
	    
	    try{
			UserInfo user = session.getFrontUserInfo(req.getSession());
			if (user==null){
				result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}
			

		    
		    if (user.getUserRol()!=null && user.getUserRol().equals("1")){	
		    	
		    }else{

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
		    
		  //dao connection
			PublicDAO dao = new PublicDAO();
			
			sql = " select count(*) from " + sqlFrom + " where " + sqlWhere ;
			if (additionalSQL.length()>0)
				sql += " and ( " + additionalSQL + " ) ";
			if (sqlChannel.length()>0)
				sql += " and c.id in ( select t.content_id from bbva_content_channel t, bbva_channel a where t.channel_id=a.id and " + sqlChannel + " ) ";
			
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

		}
				
        response(res, result);
	}

	@RequestMapping("galeria.html")
	public ModelAndView gallery(
			@RequestParam(value = "content_id", required = false) String content_id,
			HttpServletRequest req, HttpServletResponse res){

		UserInfo user = session.getFrontUserInfo(req.getSession());
		if (user==null){
			return new ModelAndView("redirect:/user/adios.html");
		}

		content_id = Utils.checkNull(content_id);
		
		

		try{
			if (content_id.length()==0){
				return new ModelAndView("redirect:/public/galerias.html");
			}
			
			
			
			//dao connection
			PublicDAO dao = new PublicDAO();
			
			String sessionID = req.getSession().getId();
			//String userID = session.getFrontUserID(req.getSession());
			String today = Utils.getToday();
			String time = Utils.getTodayWithTime();
			String addr = req.getRemoteAddr();
			
			Long contentID = Utils.getLong(content_id);
			
			ModelAndView mv = new ModelAndView("public_gallery");
			//handle the form submission
			mv.addObject("family", dao.getFamilyList());
			mv.addObject("banner_sidebar", dao.getOptionHTML(Constants.OPTION_BANNER_INTERNAL_SIDEBAR_HTML));
			mv.addObject("banner_bottom", dao.getOptionHTML(Constants.OPTION_BANNER_INTERNAL_BOTTOM_HTML));			
			mv.addObject("most_visited_channel", dao.getMostVisitedChannel(user));
			mv.addObject("content", dao.getContent(contentID));
		
			return mv;
		}catch (Exception e){ 
			log.error("PublicController", "gallery", e.toString());
			
		}
		
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
			
			//dao connection
			PublicDAO dao = new PublicDAO();
			
			result = setResponse(result, "result", dao.getContentGallery(Utils.getLong(content_id)));
			result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_SUCCESS);
			result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0001", session.getLanguage(req.getSession())));
			
		}catch (Exception e){ 
			log.error("PublicController", "getGallery", e.toString());

		}
				
        response(res, result);
	}
	
	@RequestMapping("cargarmenu.html")
	public void cargarmenu(
			@RequestParam(value = "channel_id", required = false) String channel_id,
			HttpServletRequest req, HttpServletResponse res){
		int vuelta = 0;
		channel_id = Utils.checkNull(channel_id);
				
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

			//dao connection
			CategoryDAO categoryDao = new CategoryDAO();
			PublicDAO dao = new PublicDAO();
			
			String ua = req.getHeader("User-Agent");
			log.info("PublicController", "header", ua);
			
			boolean isFirefox = (ua != null && ua.indexOf("Firefox/") != -1);
			String version="1";
			if (isFirefox){
				version = ua.replaceAll("^.*?Firefox/", "");
			}
			
			
			ArrayList<?> list = categoryDao.getFamilyList();
			
			for (int i=0; i<list.size(); i++){
				FamilyInfo item = (FamilyInfo) list.get(i);
				if (item.getVisible().equals("1")){
					JSONObject obj = new JSONObject();
					// Initialize value.
					obj.put("parent", item.toJSONObject());
					obj.put("child", dao.recallChannelList(item.getId(), (long)0, user));
					div_result.put(obj);	
					
					JSONArray datos = new JSONArray();
					Menu menu = new Menu();
					
					datos = dao.recallChannelList(item.getId(), (long)0, user);
					menu.setDatos(datos);
					//System.out.println("datos "+menu.getDatos());
					
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
		
		response(res, result);
	}

	
}
