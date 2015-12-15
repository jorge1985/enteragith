package com.youandbbva.enteratv.controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.youandbbva.enteratv.dao.ContentDAO;
import com.youandbbva.enteratv.dao.PublicDAO;
import com.youandbbva.enteratv.dao.SystemDAO;
import com.youandbbva.enteratv.dao.UtilityDAO;
import com.youandbbva.enteratv.domain.ContentInfo;
import com.youandbbva.enteratv.domain.ContentNewsInfo;
import com.youandbbva.enteratv.domain.ContentVideoInfo;
import com.youandbbva.enteratv.domain.UserInfo;
import com.youandbbva.enteratv.domain.ContentCountInfo;

/**
 * Handle all action for Content.
 * 		such as add content, active, expired, recycle bin. 
 *  
 * @author CJH
 *
 */

@Controller
@RequestMapping("/content/")
@Component("ContentController")
public class ContentController extends com.youandbbva.enteratv.Controller{
	

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
	 * Active Content page.
	 * 
	 * @param req
	 * @param res
	 * @return view
	 */
	@RequestMapping("active.html")
	public ModelAndView active(
			HttpServletRequest req, HttpServletResponse res){

		UserInfo user = session.getUserInfo(req.getSession());
		if (user==null){
			///user/adios.html
			return new ModelAndView("redirect:/user/adios.html");
		}

		return new ModelAndView("active_content");
	}

	/**
	 * Expired Content page.
	 * 
	 * @param req
	 * @param res
	 * @return view
	 */
	@RequestMapping("expired.html")
	public ModelAndView expired(
			HttpServletRequest req, HttpServletResponse res){

		UserInfo user = session.getUserInfo(req.getSession());
		if (user==null){
			return new ModelAndView("redirect:/user/adios.html");
		}

		return new ModelAndView("expired_content");
	}
	
	/**
	 * RecycleBin Content page.
	 * 
	 * @param req
	 * @param res
	 * @return view
	 */
	@RequestMapping("recycle.html")
	public ModelAndView recycle(
			HttpServletRequest req, HttpServletResponse res){

		UserInfo user = session.getUserInfo(req.getSession());
		if (user==null){
			return new ModelAndView("redirect:/user/adios.html");
		}

		return new ModelAndView("recycle_content");
	}
	
	
	/**
	 * Add Content page.
	 * 
	 * @param req
	 * @param res
	 * @return view
	 */
	@RequestMapping("add.html")
	public ModelAndView add(
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "content_id", required = false) String content_id,
			HttpServletRequest req, HttpServletResponse res){

		ModelAndView mv = new ModelAndView("add_content");
		
		type = Utils.checkNull(type);
		content_id = Utils.checkNull(content_id);
		if (type.length()==0)
			type="add";
		
		//handle the form submission
		mv.addObject("type", type);
		mv.addObject("content_id", content_id);
		
		Connection conn = DSManager.getConnection();
		
		try{
			UserInfo user = session.getUserInfo(req.getSession());
			if (user==null){
				return new ModelAndView("redirect:/user/adios.html");
			}
			//dao connection
			UtilityDAO codeDao = new UtilityDAO(conn);
			ContentDAO contentDao = new ContentDAO(conn);
			
			//handle the form submission
			mv.addObject("show_in", codeDao.getCodeList("cnt1", session.getLanguage(req.getSession())));
			mv.addObject("featured", codeDao.getCodeList("cnt2", session.getLanguage(req.getSession())));
			mv.addObject("tags", contentDao.getAllTag() );
			
			Long lngcontentid = Long.parseLong(content_id);
			
			//handle the form submission
			mv.addObject("tag_list", contentDao.getTagList(lngcontentid) );
			mv.addObject("content_id", lngcontentid);

			if (type.equals("edit")){
			}
			
		}catch (Exception e){
			
			//handle the form submission
			mv.addObject("show_in", new ArrayList());
			mv.addObject("featured", new ArrayList());
			
			log.error("ContentController", "add", e.toString());
		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}
		
		return mv;
	}
	
	/**
	 * Edit Content page.
	 * 
	 * @param req
	 * @param res
	 * @return view
	 */
	@RequestMapping("edit.html")
	public ModelAndView edit(
			@RequestParam(value = "content_id", required = false) String content_id,
			@RequestParam(value = "content_type", required = false) String content_type,
			HttpServletRequest req, HttpServletResponse res){

		ModelAndView mv = new ModelAndView("edit_content");
		
		content_id = Utils.checkNull(content_id);
		content_type = Utils.encode( Utils.checkNull(content_type));

		Connection conn = DSManager.getConnection();
		
		try{
			UserInfo user = session.getUserInfo(req.getSession());
			if (user==null){
				return new ModelAndView("redirect:/user/adios.html");
			}

			Long contentID = Utils.getLong(content_id);
			//dao connection
			ContentDAO dao = new ContentDAO(conn);
			
			//handle the form submission
			mv.addObject("content_id", contentID);
			mv.addObject("content_type", content_type);
			mv.addObject("content", dao.getContent(contentID));
			mv.addObject("channel", dao.getContentChannelList(contentID));
			mv.addObject("tags", dao.getTagList(contentID));
			
			
		}catch (Exception e){
			log.error("ContentController", "edit", e.toString());
			
		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}
		
		return mv;
	}	
	
	
	/**
	 * Get Active Content with DataTable.
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("loadActiveContent.html")
	public void loadActiveContent(
			HttpServletRequest request, HttpServletResponse response){

	    String[] cols = { "c.ContentName", "c.Contentype_ContentypeId","c.ContentPublishDate","c.ContentEndDate"};
	    String table = "Content";
        JSONObject result = new JSONObject();
	    int amount = 10;
	    int start = 0;
	    int echo = 0;
	    int col = 3;
	 
	    String dir = "desc";
	    String sStart = Utils.checkNull(request.getParameter("start"));
	    String sAmount = Utils.checkNull(request.getParameter("length"));
	    String sCol = Utils.checkNull(request.getParameter("iSortCol_0"));
	    String sdir = Utils.checkNull(request.getParameter("sSortDir_0"));

	    Map<String, String[]> parameters = request.getParameterMap();
	    sdir = parameters.get("order[0][dir]")[0];
	    sCol = parameters.get("order[0][column]")[0];
	    
	    String searchTerm = Utils.checkNull(request.getParameter("search[value]"));
	    String all_search = Utils.checkNull(request.getParameter("all_search"));
	    if (all_search.length()>0)
	    	searchTerm = all_search;
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
	        if (col < 0 || col > 2)
	            col = 0;
	    }
	    
	    if (sdir .length()>0 ) {
	        if (!sdir.equals("desc"))
	            dir = "asc";
	    }
	    
	    String colName = cols[col];
	    Long total = (long)0;
	    Long totalAfterFilter = (long)0;
	    
	    Connection conn = DSManager.getConnection();

		try{
			//dao connection
			ContentDAO dao = new ContentDAO(conn);
			
			total = dao.getCountByType("A");
			totalAfterFilter = total;
			
			ContentCountInfo contentinfo = dao.getContentByType("A",searchTerm,colName,dir,start,amount);
			JSONArray array = contentinfo.getJSONArray();
			totalAfterFilter = dao.getContentCountByType("A",searchTerm);
			// Initialize value.
			result.put("recordsTotal", total);
	        result.put("recordsFiltered", totalAfterFilter);
	        result.put("data", array);
			
		}catch (Exception e){ 
			log.error("ContentController", "loadActiveContent", e.toString());

		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}
		
		try{
			response.setContentType("application/json");
	        response.setHeader("Cache-Control", "no-store");
	        response.getWriter().print(result);			
		}catch (Exception ee){}
	}

	/**
	 * Get Expired Content with DataTable.
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("loadExpiredContent.html")
	public void loadExpiredContent(
			HttpServletRequest request, HttpServletResponse response){

		String[] cols = { "c.ContentName", "c.Contentype_ContentypeId","c.ContentPublishDate","c.ContentEndDate"};
	    String table = "Content";
	     
	    JSONObject result = new JSONObject();
	    int amount = 10;
	    int start = 0;
	    int echo = 0;
	    int col = 3;
	
	    String dir = "desc";
	    String sStart = Utils.checkNull(request.getParameter("start"));
	    String sAmount = Utils.checkNull(request.getParameter("length"));
	    String sCol = Utils.checkNull(request.getParameter("iSortCol_0"));
	    String sdir = Utils.checkNull(request.getParameter("sSortDir_0"));

	    Map<String, String[]> parameters = request.getParameterMap();
	    sdir = parameters.get("order[0][dir]")[0];
	    sCol = parameters.get("order[0][column]")[0];
	    
	    String searchTerm = Utils.checkNull(request.getParameter("search[value]"));
	    String all_search = Utils.checkNull(request.getParameter("all_search"));
	    if (all_search.length()>0)
	    	searchTerm = all_search;
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
	        if (col < 0 || col > 2)
	            col = 3;
	    }
	    
	    if (sdir .length()>0 ) {
	        if (!sdir.equals("desc"))
	            dir = "asc";
	    }
	    
	    String colName = cols[col];
	    Long total = (long)0;
	    Long totalAfterFilter = (long)0;
	    
	    Connection conn = DSManager.getConnection();

		try{
			String today = Utils.getToday("-");
			//dao connection
			ContentDAO dao = new ContentDAO(conn);
			// load existing data.
			total = dao.getCountByType("E");
			totalAfterFilter = total;
			
	        ContentCountInfo contentinfo = dao.getContentByType("E",searchTerm,colName,dir,start,amount);
			JSONArray array = contentinfo.getJSONArray();
			// Initialize value.
			result.put("recordsTotal", total);
	        result.put("recordsFiltered", totalAfterFilter);
	        result.put("data", array);
			
		}catch (Exception e){ 
			log.error("ContentController", "loadExpiredContent", e.toString());

		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}
		
		try{
			
			response.setContentType("application/json");
	        response.setHeader("Cache-Control", "no-store");
	        response.getWriter().print(result);			
		}catch (Exception ee){}
	}

	/**
	 * Get Deleted Content with DataTable.
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("loadRecycleContent.html")
	public void loadRecycleContent(
			HttpServletRequest request, HttpServletResponse response){

		String[] cols = { "c.ContentName", "c.Contentype_ContentypeId","c.ContentPublishDate","c.ContentEndDate"};
	    String table = "Content";
	     
	    JSONObject result = new JSONObject();
	    int amount = 10;
	    int start = 0;
	    int echo = 0;
	    int col = 3;
	 
	    String dir = "desc";
	    String sStart = Utils.checkNull(request.getParameter("start"));
	    String sAmount = Utils.checkNull(request.getParameter("length"));
	    String sCol = Utils.checkNull(request.getParameter("iSortCol_0"));
	    String sdir = Utils.checkNull(request.getParameter("sSortDir_0"));

	    Map<String, String[]> parameters = request.getParameterMap();
	    sdir = parameters.get("order[0][dir]")[0];
	    sCol = parameters.get("order[0][column]")[0];
	    String searchTerm = Utils.checkNull(request.getParameter("search[value]"));
	    String all_search = Utils.checkNull(request.getParameter("all_search"));
	    
	    if (all_search.length()>0)
	    	searchTerm = all_search;
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
	        if (col < 0 || col > 2)
	            col = 3;
	    }
	    
	    if (sdir .length()>0 ) {
	        if (!sdir.equals("desc"))
	            dir = "asc";
	    }
	    
	    String colName = cols[col];
	    Long total = (long)0;
	    Long totalAfterFilter = (long)0;
	    
	    Connection conn = DSManager.getConnection();

		try{
			//dao connection
			ContentDAO dao = new ContentDAO(conn);
			
			total = dao.getCountByType("B");
			totalAfterFilter = total;

			ContentCountInfo contentinfo = dao.getContentByType("B",searchTerm,colName,dir,start,amount);
			JSONArray array = contentinfo.getJSONArray();
			// Initialize value.
			result.put("recordsTotal", total);
	        result.put("recordsFiltered", totalAfterFilter);
	        result.put("data", array);
			
		}catch (Exception e){ 
			log.error("ContentController", "loadRecycleContent", e.toString());

		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}
		
		try{
	
			response.setContentType("application/json");
	        response.setHeader("Cache-Control", "no-store");
	        response.getWriter().print(result);			
		}catch (Exception ee){}
	}

	
	/**
	 * Get Detail Content including channel.
	 * 
	 * @param content_id
	 * @param req
	 * @param res
	 */
	@RequestMapping("getContent.html")
	public void getContent(
			@RequestParam(value = "content_id", required = false) String content_id,
			HttpServletRequest req, HttpServletResponse res){

		content_id = Utils.checkNull(content_id);

		Connection conn = DSManager.getConnection();

		JSONObject result = new JSONObject();
		
		result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_FAILED);
		result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0002", session.getLanguage(req.getSession())));

		try{
			UserInfo user = session.getUserInfo(req.getSession());
			if (user==null){
				result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}

			long contentID = Utils.getLong(content_id);
			//dao connection
			ContentDAO dao = new ContentDAO(conn);
		
			result = setResponse(result, "content", dao.getContentOfJSON(contentID));
			result = setResponse(result, "channel", dao.getContentChannelListOfJSON(contentID));
			result = setResponse(result, "tags", dao.getTagListOfJSON(contentID));
			result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_SUCCESS);
			result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0001", session.getLanguage(req.getSession())));
			
		}catch (Exception e){ 
			log.error("ContentController", "getContent", e.toString());
			
		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}

		response(res, result);
	}
	
	/**
	 * Get Detail Content of Video in same channel.
	 * 
	 * @param content_id
	 * @param req
	 * @param res
	 */
	@RequestMapping("getContentOfVideo.html")
	public void getContentOfVideo(
			@RequestParam(value = "content_id", required = false) String content_id,
			HttpServletRequest req, HttpServletResponse res){

		content_id = Utils.checkNull(content_id);

		Connection conn = DSManager.getConnection();

		JSONObject result = new JSONObject();
		
		result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_FAILED);
		result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0002", session.getLanguage(req.getSession())));

		try{
			UserInfo user = session.getUserInfo(req.getSession());
			if (user==null){
				result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}

			long contentID = Utils.getLong(content_id);
			long channel = 0;
			ArrayList<Long> contentidvideo= new ArrayList<Long>();
			//dao connection
			ContentDAO dao = new ContentDAO(conn);
			channel = dao.getChannel_ID(contentID);
			contentidvideo=dao.getContent_ID(channel);
//			dao.getBlobVideo(channel);
//			dao.getBlobImagen(channel);
			
				result = setResponse(result, "content", dao.getVideoContentListOfJSON(contentidvideo, channel));
				result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_SUCCESS);
				result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0001", session.getLanguage(req.getSession())));
			
		}catch (Exception e){ 
			log.error("ContentController", "getContentOfVideo", e.toString());
			
		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}

		response(res, result);
	}
	
	
	/**
	 * Update Content. 
	 * 		add new or update existing one.
	 * 
	 * @param type
	 * @param content_id
	 * @param title
	 * @param featured
	 * @param validity_start
	 * @param validity_end
	 * @param show_in
	 * @param active
	 * @param channel
	 * @param req
	 * @param res
	 */
	@RequestMapping("updateContent.html")
	public void updateContent(
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "content_id", required = false) String content_id,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "featured", required = false) String featured,
			@RequestParam(value = "validity_start", required = false) String validity_start,
			@RequestParam(value = "validity_end", required = false) String validity_end,
			@RequestParam(value = "show_in", required = false) String show_in,
			@RequestParam(value = "active", required = false) String active,
			@RequestParam(value = "channel", required = false) String channel,
			HttpServletRequest req, HttpServletResponse res){

		JSONObject result = new JSONObject();

		result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_FAILED);
		result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0002", session.getLanguage(req.getSession())));
		
		type = Utils.checkNull(type);
		content_id = Utils.checkNull(content_id);
		featured = Utils.checkNull(featured);
		validity_start = Utils.checkNull(validity_start);
		validity_end = Utils.checkNull(validity_end);
		show_in = Utils.checkNull(show_in);
		active = Utils.checkNull(active);
		channel = Utils.checkNull(channel);
		title = Utils.encode(Utils.checkNull(title));

		Connection conn = DSManager.getConnection();

		try{
			UserInfo user = session.getUserInfo(req.getSession());
			
			if (user==null){
				result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}

			//evaluated value type
			if (type.length()==0){
				result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}

			if (!type.equals("add") && !type.equals("edit")){
				result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}
			
			conn.setAutoCommit(false);
			//dao connection
			ContentDAO dao = new ContentDAO(conn);
			Long contentID = (long)0;
			int channelid = 0;
			int typecontent = 0;
			int isvisible = Integer.parseInt( req.getParameter("active"));
			String strhtml = "";
			String status = "0";
			typecontent = Integer.parseInt(featured);
			
			if (channel.length() > 0)
			{
				String[] channels = channel.split(",");
				String firstchannel = channels[0];

				channelid = Integer.parseInt(firstchannel);
			}
			if (featured=="A")
			{
				typecontent = 1;
			}
			
			if (type.equals("add")){
				
				contentID = dao.insertContent(channelid,typecontent, title,strhtml,isvisible,validity_start, validity_end, active,show_in);
	
				
				try{
					String[] tags = req.getParameterValues("tag[]");
					int labelid = 0;
					for (int i=0; i<tags.length; i++){
						if (tags[i].length()>0)
							labelid = dao.getLabelIdByText(tags[i]);
							if (labelid == 0)
							{
								dao.insertLabel(tags[i]);
								labelid = dao.getLabelIdByText(tags[i]);
							}
							int contenlabelid = dao.insertContentlabel(contentID, labelid);
					}
				}catch (Exception eeff){}
				
	
			}else{
				contentID = Utils.getLong(content_id);
				//dao connection
				ContentInfo previous = dao.getContent(contentID);
				
				dao.updateContent( channelid,typecontent, title,strhtml,isvisible,validity_start, validity_end, active,show_in,contentID);
				dao.deleteContentlabelByContentId(contentID);
				
				try{
					String[] tags = req.getParameterValues("tag[]");
					int labelid = 0;
					for (int i=0; i<tags.length; i++){
						if (tags[i].length()>0)
							labelid = dao.getLabelIdByText(tags[i]);
							int contenlabelid = dao.insertContentlabel(contentID, labelid);
					
					}
				}catch (Exception eeff){}
			}
			
			conn.commit();
			//dao connection
			PublicDAO publicDao = new PublicDAO(conn);
			SystemDAO systemDao = new SystemDAO(conn);
			
			dao.generateHTML(contentID);

			String[] html = publicDao.generateSlide();
			
			if (featured.equals("2")){

			}
			
			systemDao.update(Constants.OPTION_SLIDER_FOR_HTML, html[0]);
			systemDao.update(Constants.OPTION_SLIDER_NAV_HTML, html[1]);
			systemDao.update(Constants.OPTION_SLIDER_FOR_HTML+"_mobile", html[2]);
			systemDao.update(Constants.OPTION_SLIDER_NAV_HTML+"_mobile", html[3]);
			conn.commit();
			
			ContentInfo content = dao.getContent(contentID);
			if (content.getStatus().equals("B"))
				result = setResponse(result, "move_to", "0");
			else if (content.getStatus().equals("A") && content.getActive().equals("1"))
				result = setResponse(result, "move_to", "1");
			else if (content.getStatus().equals("A") && content.getActive().equals("0"))
				result = setResponse(result, "move_to", "2");
			// Initialize all value.
			result = setResponse(result, "contentID", contentID);
			result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_SUCCESS);
			result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0001", session.getLanguage(req.getSession())));
			
		}catch (Exception e){ 
			log.error("ContentController", "updateContent", e.toString());
			
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
	
	/**
	 * Delete Content
	 * 		remove, delete, empty all, recover.
	 * 
	 * @param type
	 * @param content_id
	 * @param req
	 * @param res
	 */
	@RequestMapping("deleteContent.html")
	public void deleteContent(
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "content_id", required = false) String content_id,
			HttpServletRequest req, HttpServletResponse res){

		type = Utils.checkNull(type);
		content_id = Utils.checkNull(content_id);

		Connection conn = DSManager.getConnection();

		JSONObject result = new JSONObject();
	
		result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_FAILED);
		result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0002", session.getLanguage(req.getSession())));

		try{
			UserInfo user = session.getUserInfo(req.getSession());
			if (user==null){
				result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}
			//evaluated value type
			
			if (type.length()==0){
				result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}

			if (!type.equals("remove") && !type.equals("delete") && !type.equals("all")  && !type.equals("reload")){
				result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}
			
			long contentID = Utils.getLong(content_id);
			//dao connection
			ContentDAO dao = new ContentDAO(conn);
			conn.setAutoCommit(false);
			
			ContentInfo content = dao.getContent(contentID);
			
			if (type.equals("remove")){
//				dao.removeContent(contentID);
				dao.deleteContent(contentID);
				
			}else if (type.equals("delete")){
				dao.deleteContentLogical(contentID);
				
			}else if (type.equals("all")){
				dao.deleteContentAll();
				
			}else{
				dao.activeContent(contentID);
			}
			
			conn.commit();
			
			//dao connection
			PublicDAO publicDao = new PublicDAO(conn);
			SystemDAO systemDao = new SystemDAO(conn);
			String[] html = publicDao.generateSlide();
			
			if (content.getType().equals("002")){

			}
			
			systemDao.update(Constants.OPTION_SLIDER_FOR_HTML, html[0]);
			systemDao.update(Constants.OPTION_SLIDER_NAV_HTML, html[1]);
			systemDao.update(Constants.OPTION_SLIDER_FOR_HTML+"_mobile", html[2]);
			systemDao.update(Constants.OPTION_SLIDER_NAV_HTML+"_mobile", html[3]);
			conn.commit();
			result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_SUCCESS);
			result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0001", session.getLanguage(req.getSession())));
			
		}catch (Exception e){ 
			log.error("ContentController", "deleteContent", e.toString());
			
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

	/**
	 * Load Content File List.
	 * 
	 * @param content_id
	 * @param req
	 * @param res
	 */
	@RequestMapping("loadContentFile.html")
	public void loadContentFile(
			@RequestParam(value = "content_id", required = false) String content_id,
			HttpServletRequest req, HttpServletResponse res){

		content_id = Utils.checkNull(content_id);

		Connection conn = DSManager.getConnection();

		JSONObject result = new JSONObject();
		result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_FAILED);
		result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0002", session.getLanguage(req.getSession())));

		try{
			UserInfo user = session.getUserInfo(req.getSession());
			if (user==null){
				result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}

			long contentID = Utils.getLong(content_id);
			//dao connection
			ContentDAO dao = new ContentDAO(conn);
			result = setResponse(result, "file", dao.getContentFileOfJSON(contentID));
			result = setResponse(result, "media", dao.getContentFileMediaListOfJSON(contentID));
			result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_SUCCESS);
			result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0001", session.getLanguage(req.getSession())));
			
		}catch (Exception e){ 
			log.error("ContentController", "loadContentFile", e.toString());
			
		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}

		response(res, result);
	}
	
	/**
	 * Update Content File. 
	 * 		add new, update or delete existing one.
	 * 
	 * @param content_id
	 * @param description
	 * @param req
	 * @param res
	 */
	@RequestMapping("updateContentFile.html")
	public void updateContentFile(
			@RequestParam(value = "content_id", required = false) String content_id,
			@RequestParam(value = "description", required = false) String description,
			HttpServletRequest req, HttpServletResponse res){

		JSONObject result = new JSONObject();
		result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_FAILED);
		result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0002", session.getLanguage(req.getSession())));
		content_id = Utils.checkNull(content_id);
		description = Utils.encode(Utils.checkNull(description));
		String[] key = req.getParameterValues("filekey[]");
		String[] name = req.getParameterValues("filename[]");
		try{
			for (int i=0; i<name.length; i++){
				name[i] = Utils.encode(Utils.checkNull(name[i]));
			}
		}catch (Exception ee){}

		Connection conn = DSManager.getConnection();

		try{
			UserInfo user = session.getUserInfo(req.getSession());
			if (user==null){
				result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}

			conn.setAutoCommit(false);
			//dao connection
			ContentDAO dao = new ContentDAO(conn);
			Long contentID = Utils.getLong(content_id);
			
			try{
				dao.deleteContentMediaByContentId(contentID);

			}catch (Exception e){}
			conn.commit();

			dao.updateContentDescriptionById(contentID, description);
			for (int i=0; i<key.length; i++){
				dao.insertContentmedia(contentID, dao.getMediaContent(key[i]));
			}
			conn.commit();
			dao.generateHTML(contentID);
			conn.commit();
			ContentInfo content = dao.getContent(contentID);
			
			if (content.getStatus().equals("B"))
				result = setResponse(result, "move_to", "0");
			else if (content.getStatus().equals("A") && content.getActive().equals("1"))
				result = setResponse(result, "move_to", "1");
			else if (content.getStatus().equals("A") && content.getActive().equals("0"))
				result = setResponse(result, "move_to", "2");
			
			result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_SUCCESS);
			result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0001", session.getLanguage(req.getSession())));
			
		}catch (Exception e){ 
			log.error("ContentController", "updateContentFile", e.toString());
			
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
	

	/**
	 * Load Content Gallery List.
	 * 
	 * @param content_id
	 * @param req
	 * @param res
	 */
	@RequestMapping("loadContentGallery.html")
	public void loadContentGallery(
			@RequestParam(value = "content_id", required = false) String content_id,
			HttpServletRequest req, HttpServletResponse res){

		content_id = Utils.checkNull(content_id);

		Connection conn = DSManager.getConnection();

		JSONObject result = new JSONObject();
		// Initialize all value.
		result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_FAILED);
		result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0002", session.getLanguage(req.getSession())));

		try{
			UserInfo user = session.getUserInfo(req.getSession());
			if (user==null){
				result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}

			long contentID = Utils.getLong(content_id);
			//dao connection
			ContentDAO dao = new ContentDAO(conn);

			result = setResponse(result, "file", dao.getContentFileOfJSON(contentID));
			result = setResponse(result, "media", dao.getContentFileMediaListOfJSON(contentID));
			result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_SUCCESS);
			result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0001", session.getLanguage(req.getSession())));
			
		}catch (Exception e){ 
			log.error("ContentController", "loadContentGallery", e.toString());
			
		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}

		response(res, result);
	}
	
	/**
	 * Update Content Gallery. 
	 * 		add new, update or delete existing one.
	 * 
	 * @param content_id
	 * @param detail
	 * @param req
	 * @param res
	 */
	@RequestMapping("updateContentGallery.html")
	public void updateContentGallery(
			@RequestParam(value = "content_id", required = false) String content_id,
			@RequestParam(value = "detail", required = false) String detail,
			HttpServletRequest req, HttpServletResponse res){

		JSONObject result = new JSONObject();
		result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_FAILED);
		result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0002", session.getLanguage(req.getSession())));
		
		content_id = Utils.checkNull(content_id);
		detail = Utils.encode(Utils.checkNull(detail));

		String[] key = req.getParameterValues("filekey[]");
		String[] name = req.getParameterValues("filename[]");
		try{
			for (int i=0; i<name.length; i++){
				name[i] = Utils.encode(Utils.checkNull(name[i]));
			}
		}catch (Exception ee){}

		Connection conn = DSManager.getConnection();

		try{
			UserInfo user = session.getUserInfo(req.getSession());
			if (user==null){
				result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}

			conn.setAutoCommit(false);
			//dao connection
			ContentDAO dao = new ContentDAO(conn);
			
			Long contentID = Utils.getLong(content_id);
			
			try{
				dao.deleteContentMediaByContentId(contentID);
			}catch (Exception e){}
			conn.commit();

			dao.updateContentDescriptionById(contentID, detail);
			dao.deleteContentMediaByContentId(contentID);
			
			for (int i=0; i<key.length; i++){
				dao.insertContentmedia(contentID, dao.getMediaContent(key[i]));
			}
			
			conn.commit();
			dao.generateHTML(contentID);
			conn.commit();
			
			ContentInfo content = dao.getContent(contentID);
			if (content.getStatus().equals("B"))
				result = setResponse(result, "move_to", "0");
			else if (content.getStatus().equals("A") && content.getActive().equals("1"))
				result = setResponse(result, "move_to", "1");
			else if (content.getStatus().equals("A") && content.getActive().equals("0"))
				result = setResponse(result, "move_to", "2");
			result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_SUCCESS);
			result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0001", session.getLanguage(req.getSession())));
			
		}catch (Exception e){ 
			log.error("ContentController", "updateContentGallery", e.toString());
			
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
	

	/**
	 * Get Content News.
	 * 
	 * @param content_id
	 * @param req
	 * @param res
	 */
	@RequestMapping("getContentNews.html")
	public void getContentNews(
			@RequestParam(value = "content_id", required = false) String content_id,
			HttpServletRequest req, HttpServletResponse res){

		content_id = Utils.checkNull(content_id);

		Connection conn = DSManager.getConnection();

		JSONObject result = new JSONObject();
		result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_FAILED);
		result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0002", session.getLanguage(req.getSession())));

		try{
			UserInfo user = session.getUserInfo(req.getSession());
			if (user==null){
				result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}

			Long contentID = Utils.getLong(content_id);
			//dao connection
			ContentDAO dao = new ContentDAO(conn);
			
			ContentNewsInfo news =  dao.getContentNews(contentID);
			result = setResponse(result, "news", news.toJSONObject());
			result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_SUCCESS);
			result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0001", session.getLanguage(req.getSession())));
			
		}catch (Exception e){ 
			log.error("ContentController", "getContentNews", e.toString());
			
		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}

		response(res, result);
	}
	
	/**
	 * Update Content News. 
	 * 		add new, update or delete existing one.
	 * 
	 * @param content_id
	 * @param content
	 * @param image
	 * @param req
	 * @param res
	 */
	@RequestMapping("updateContentNews.html")
	public void updateContentNews(
			@RequestParam(value = "content_id", required = false) String content_id,
			@RequestParam(value = "content", required = false) String content,
			@RequestParam(value = "image", required = false) String image,
			HttpServletRequest req, HttpServletResponse res){

		JSONObject result = new JSONObject();
		result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_FAILED);
		result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0002", session.getLanguage(req.getSession())));
		
		content_id = Utils.checkNull(content_id);
		content = Utils.encode(Utils.checkNull(content));
		image = Utils.encode(Utils.checkNull(image));

		Connection conn = DSManager.getConnection();

		try{
			UserInfo user = session.getUserInfo(req.getSession());
			if (user==null){
				result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}
			
			conn.setAutoCommit(false);
			//dao connection
			ContentDAO dao = new ContentDAO(conn);
			Long contentID = Utils.getLong(content_id);
			
			ContentNewsInfo news =  dao.getContentNews(contentID);
			if (news.getContentID()==0){
				dao.insertContentNews(contentID, content, image);
			}else{
				dao.updateContentNews(contentID, content, image);
			} 

			conn.commit();
			dao.generateHTML(contentID);
			conn.commit();
			ContentInfo content1 = dao.getContent(contentID);
			if (content1.getStatus().equals("B"))
				result = setResponse(result, "move_to", "0");
			else if (content1.getStatus().equals("A") && content1.getActive().equals("1"))
				result = setResponse(result, "move_to", "1");
			else if (content1.getStatus().equals("A") && content1.getActive().equals("0"))
				result = setResponse(result, "move_to", "2");
			result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_SUCCESS);
			result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0001", session.getLanguage(req.getSession())));
			
		}catch (Exception e){ 
			log.error("ContentController", "updateContentNews", e.toString());
			
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

	
	/**
	 * Get Content Video.
	 * 
	 * @param content_id
	 * @param req
	 * @param res
	 */
	@RequestMapping("getContentVideo.html")
	public void getContentVideo(
			@RequestParam(value = "content_id", required = false) String content_id,
			HttpServletRequest req, HttpServletResponse res){

		content_id = Utils.checkNull(content_id);

		Connection conn = DSManager.getConnection();

		JSONObject result = new JSONObject();
		result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_FAILED);
		result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0002", session.getLanguage(req.getSession())));

		try{
			UserInfo user = session.getUserInfo(req.getSession());
			if (user==null){
				result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}

			Long contentID = Utils.getLong(content_id);
			ContentDAO dao = new ContentDAO(conn);	
			ContentVideoInfo video =  dao.getContentVideo(contentID);

			result = setResponse(result, "video", video.toJSONObject());
			result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_SUCCESS);
			result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0001", session.getLanguage(req.getSession())));
			
		}catch (Exception e){ 
			log.error("ContentController", "getContentVideo", e.toString());
			
		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}

		response(res, result);
	}
	
	/**
	 * Update Content Video. 
	 * 		add new, update or delete existing one.
	 * 
	 * @param content_id
	 * @param video
	 * @param image
	 * @param req
	 * @param res
	 */
	@RequestMapping("updateContentVideo.html")
	public void updateContentVideo(
			@RequestParam(value = "content_id", required = false) String content_id,
			@RequestParam(value = "video", required = false) String video,
			@RequestParam(value = "image", required = false) String image,
			HttpServletRequest req, HttpServletResponse res){

		JSONObject result = new JSONObject();
		// Initialize all value.
		result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_FAILED);
		result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0002", session.getLanguage(req.getSession())));
		
		content_id = Utils.checkNull(content_id);
		video = Utils.encode(Utils.checkNull(video));
		image = Utils.encode(Utils.checkNull(image));

		Connection conn = DSManager.getConnection();

		try{
			UserInfo user = session.getUserInfo(req.getSession());
			if (user==null){
				result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}
			
			conn.setAutoCommit(false);
			//dao connection
			ContentDAO dao = new ContentDAO(conn);
			
			Long contentID = Utils.getLong(content_id);
			
			ContentVideoInfo obj =  dao.getContentVideo(contentID);
			dao.updateContentVideo(contentID, video, image);
			conn.commit();
			dao.generateHTML(contentID);
			//dao connection
			PublicDAO publicDao = new PublicDAO(conn);
			SystemDAO systemDao = new SystemDAO(conn);
			
			String[] html = publicDao.generateSlide();
			systemDao.update(Constants.OPTION_SLIDER_FOR_HTML, html[0]);
			systemDao.update(Constants.OPTION_SLIDER_NAV_HTML, html[1]);
			systemDao.update(Constants.OPTION_SLIDER_FOR_HTML+"_mobile", html[2]);
			systemDao.update(Constants.OPTION_SLIDER_NAV_HTML+"_mobile", html[3]);
			conn.commit();
			
			ContentInfo content = dao.getContent(contentID);
			if (content.getStatus().equals("B"))
				result = setResponse(result, "move_to", "0");
			else if (content.getStatus().equals("A") && content.getActive().equals("1"))
				result = setResponse(result, "move_to", "1");
			else if (content.getStatus().equals("A") && content.getActive().equals("0"))
				result = setResponse(result, "move_to", "2");
			result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_SUCCESS);
			result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0001", session.getLanguage(req.getSession())));
			
		}catch (Exception e){ 
			log.error("ContentController", "updateContentVideo", e.toString());
			
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
	
}
