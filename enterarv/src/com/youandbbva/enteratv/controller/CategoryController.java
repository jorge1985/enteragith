package com.youandbbva.enteratv.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

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
import com.youandbbva.enteratv.dao.CategoryDAO;
import com.youandbbva.enteratv.dao.ContentDAO;
import com.youandbbva.enteratv.dao.UtilityDAO;
import com.youandbbva.enteratv.domain.ChannelInfo;
import com.youandbbva.enteratv.domain.DivisionInfo;
import com.youandbbva.enteratv.domain.FamilyInfo;
import com.youandbbva.enteratv.domain.UserInfo;

/**
 * Handle all action for category.
 * 		such as family, channel. 
 *  
 * @author CJH
 *
 */
//exceptions

@Controller
@RequestMapping("/category/")
@Component("CategoryController")
public class CategoryController extends com.youandbbva.enteratv.Controller{

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
	 * Families Page.
	 * 
	 * @param req
	 * @param res
	 * @return view
	 */
	

	@RequestMapping("families.html")
	public ModelAndView families(
		HttpServletRequest req, HttpServletResponse res){
			
		UserInfo user = session.getUserInfo(req.getSession());
		if (user==null){
			return new ModelAndView("redirect:/user/adios.html");
		}
		
		return new ModelAndView("families");
	}

	/**
	 * Get all families data.
	 * 
	 * @param req
	 * @param res
	 */
	@RequestMapping("loadFamilies.html")
	public void loadFamilies(
			HttpServletRequest req, HttpServletResponse res){
		//dao connection
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
			//dao connection	
		CategoryDAO categoryDao = new CategoryDAO();

		
		result = setResponse(result, "list", categoryDao.getFamilyListOfJSON());
		result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_SUCCESS);
		result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0001", session.getLanguage(req.getSession())));
						
		}catch (Exception e){
			log.error("CategoryController", "loadFamilies", e.toString());
			result = setResponse(result, "list", "");
		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}
		
		response(res, result);
	}

	/**
	 * Update Family. (add, edit or delete)
	 * 
	 * @param family_id
	 * @param family_name
	 * @param req
	 * @param res
	 */
	@RequestMapping("updateFamily.html")
	public void updateFamily(
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "family_id", required = false) String family_id,
			@RequestParam(value = "family_name", required = false) String family_name,
			@RequestParam(value = "visible", required = false) String visible,
			HttpServletRequest req, HttpServletResponse res){

		Connection conn = DSManager.getConnection();

		JSONObject result = new JSONObject();
	
		result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_FAILED);
		result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0002", session.getLanguage(req.getSession())));
		
		type = Utils.checkNull(type);
		family_id = Utils.checkNull(family_id);
		family_name = Utils.encode(Utils.checkNull(family_name));
		visible = Utils.checkNull(visible);

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

			if (!type.equals("get") && !type.equals("add") && !type.equals("edit") && !type.equals("delete")){
				result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}
			//dao connection
			CategoryDAO categoryDao = new CategoryDAO();
			conn.setAutoCommit(false);
			
			if (type.equals("add")){
				if (family_name.length()==0){
					result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("CMM0001", session.getLanguage(req.getSession())));
					throw new Exception(reg.getMessage("CMM0001"));
				}
				//dao connection
				categoryDao.insertFamily(family_name, visible);
				
			}else if (type.equals("edit")){
				if (family_id.length()==0){
					result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
					throw new Exception(reg.getMessage("ACT0003"));
				}

				if (family_name.length()==0){
					result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("CMM0001", session.getLanguage(req.getSession())));
					throw new Exception(reg.getMessage("CMM0001"));
				}

				long familyID = Utils.getLong(family_id);

				//dao connection
				categoryDao.updateFamily(family_name, visible, familyID);
				
			}else if (type.equals("delete")){
				if (family_id.length()==0){
					result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
					throw new Exception(reg.getMessage("ACT0003"));
				}

				long familyID = Utils.getLong(family_id);
				//dao connection
				categoryDao.deleteFamily(familyID);
				
			}else if (type.equals("get")){
				if (family_id.length()==0){
					result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
					throw new Exception(reg.getMessage("ACT0003"));
				}

				long familyID = Utils.getLong(family_id);
				result = setResponse(result, "family", categoryDao.getFamilyOfJSON(familyID));
			}
			
			conn.commit();

			// load all family data.
			result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_SUCCESS);
			result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0001", session.getLanguage(req.getSession())));
			result = setResponse(result, "list", categoryDao.getFamilyListOfJSON());
			
		}catch (Exception e){ 
			log.error("CategoryController", "updateFamily", e.toString());
			
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
	 * Channels Page.
	 * 
	 * @param req
	 * @param res
	 * @return view
	 */
	@RequestMapping("channels.html")
	public ModelAndView channels(
		HttpServletRequest req, HttpServletResponse res){
			
		UserInfo user = session.getUserInfo(req.getSession());
		if (user==null){
			return new ModelAndView("redirect:/user/adios.html");
		}
		
		ModelAndView mv = new ModelAndView("channels");

		Connection conn = DSManager.getConnection();
		
		try{
			//dao connection
			CategoryDAO categoryDao = new CategoryDAO();
			UtilityDAO codeDao = new UtilityDAO();
			
			//handle the form submission
			mv.addObject("familylist", categoryDao.getFamilyList());
			mv.addObject("securitylist", codeDao.getCodeList("chn1", session.getLanguage(req.getSession())));
			mv.addObject("direccionlist", codeDao.getList("maindirection"));
			mv.addObject("empresalist", codeDao.getList("company"));
			mv.addObject("ciudadlist", codeDao.getList("city"));
		
		}catch (Exception e){
			//handle the form submission
			mv.addObject("familylist", new ArrayList());
			mv.addObject("securitylist", new ArrayList());
			mv.addObject("direccionlist", new ArrayList());
			mv.addObject("empresalist", new ArrayList());
			mv.addObject("ciudadlist", new ArrayList());
			

			log.error("CategoryController", "channels", e.toString());
		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}
		
		return mv;
	}

	/**
	 * Get all channels data.
	 * 
	 * @param req
	 * @param res
	 */
	@RequestMapping("loadChannels.html")
	public void loadChannels(
			HttpServletRequest req, HttpServletResponse res){

		Connection conn = null;
		
		JSONObject result = new JSONObject();

		result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_FAILED);
		result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0002", session.getLanguage(req.getSession())));

		try{
			
			conn =  DSManager.getConnection();
			
			UserInfo user = session.getUserInfo(req.getSession());
			if (user==null){  
				result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}

			JSONArray div_result = new JSONArray();
			//dao connection
			CategoryDAO categoryDao = new CategoryDAO();
			ArrayList list = categoryDao.getFamilyList();
			for (int i=0; i<list.size(); i++){
				FamilyInfo item = (FamilyInfo) list.get(i);
				JSONObject obj = new JSONObject();
				// Initialize value.
				obj.put("parent", item.toJSONObject());
				
				JSONArray div_result2 = new JSONArray();
				ArrayList list2 = categoryDao.getChannelList(item.getId(), (long)0);
				for (int i2=0; i2<list2.size(); i2++){
					ChannelInfo item2 = (ChannelInfo) list2.get(i2);
					JSONObject obj2 = new JSONObject();
					// Initialize value.
					obj2.put("parent", item2.toJSONObject());
					obj2.put("child", categoryDao.recallChannelList(item.getId(), item2.getId(), "", ""));
					div_result2.put(obj2);
				}
				// Initialize value.
				obj.put("child", div_result2);
				div_result.put(obj);
			}

			result = setResponse(result, "list", div_result);
			result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_SUCCESS);
			result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0001", session.getLanguage(req.getSession())));
			
		}catch (Exception e){
			log.error("CategoryController", "loadChannels", e.toString());
			result = setResponse(result, "list", "");
		}finally{

			if(conn != null)try{conn.close();}catch (SQLException e){e.printStackTrace();}
		}
		
		response(res, result);
	}
	
	/**
	 * Get channels data.
	 * 		used in Edit Channel.
	 * 
	 * @param channel Channel ID
	 * @param req
	 * @param res
	 */
	@RequestMapping("getChannel.html")
	public void getChannel(
			@RequestParam(value = "channel", required = false) String channel,
			HttpServletRequest req, HttpServletResponse res){
		
		channel = Utils.checkNull(channel);
		
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
			//dao connection
			CategoryDAO categoryDao = new CategoryDAO();
			
			long channelID = Utils.getLong(channel);
			ChannelInfo c = categoryDao.getChannelInfo(channelID);

			result = setResponse(result, "channel", c.toJSON());
			result = setResponse(result, "ciudad", categoryDao.getListOfJSON(channelID, "channelcity", "City_CityId"));
			result = setResponse(result, "direccion", categoryDao.getListOfJSON(channelID, "channelmaindirection", "Maindirection_MaindirectionId"));
			result = setResponse(result, "empresa", categoryDao.getListOfJSON(channelID, "channelcompany", "Company_CompanyId"));
			result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_SUCCESS);
			result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0001", session.getLanguage(req.getSession())));
			
		}catch (Exception e){
			log.error("CategoryController", "getChannel", e.toString());
		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}
		
		response(res, result);
	}
	
	/**
	 * Get Division data.
	 * 
	 * @param division Parent Division
	 * @param req
	 * @param res
	 */
	@RequestMapping("loadDivision.html")
	public void loadDivision(
			@RequestParam(value = "division", required = false) String division,
			HttpServletRequest req, HttpServletResponse res){
		
		division = Utils.checkNull(division);
		
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
			
			if (division.length()==0){
	
				result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}
			//dao connection
			UtilityDAO utilityDAO = new UtilityDAO();
			
			JSONArray div_result = new JSONArray();
			if (division.equals("all")){
				ArrayList list = utilityDAO.getDivisionList((long)0, session.getLanguage(req.getSession()));
				for (int i=0; i<list.size(); i++){
					DivisionInfo item = (DivisionInfo) list.get(i);
					JSONObject obj = new JSONObject();
					// Initialize value.
					obj.put("parent", utilityDAO.getDivisionOfJSON(item.getId(), session.getLanguage(req.getSession())));
					obj.put("child", utilityDAO.getDivisionListOfJSON(item.getId(), session.getLanguage(req.getSession())));
					div_result.put(obj);
				}
			}else{
				if (division.length()!=0){
					String[] div = division.split(",");
					for (int i=0; i<div.length; i++){
						JSONObject obj = new JSONObject();
						// Initialize value.
						obj.put("parent", utilityDAO.getDivisionOfJSON(Utils.getLong(div[i]), session.getLanguage(req.getSession())));
						obj.put("child", utilityDAO.getDivisionListOfJSON(Utils.getLong(div[i]), session.getLanguage(req.getSession())));
						div_result.put(obj);
					}
				}
			}

			result = setResponse(result, "list", div_result);
			result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_SUCCESS);
			result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0001", session.getLanguage(req.getSession())));
						
		}catch (Exception e){
			log.error("CategoryController", "loadDivision", e.toString());
			result = setResponse(result, "list", "");
		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}
		
		response(res, result);
	}

	/**
	 * Get City data.
	 * 
	 * @param geographical Parent City
	 * @param req
	 * @param res
	 */
	@RequestMapping("loadCity.html")
	public void loadCity(
			@RequestParam(value = "geographical", required = false) String geographical,
			HttpServletRequest req, HttpServletResponse res){
		
		geographical = Utils.checkNull(geographical);
		
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
			
			if (geographical.length()==0){
	
				result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}
			//dao connection
			UtilityDAO utilityDAO = new UtilityDAO();
			
			JSONArray div_result = new JSONArray();
			if (geographical.equals("all")){
				ArrayList list = utilityDAO.getCityList((long)0, session.getLanguage(req.getSession()));
				for (int i=0; i<list.size(); i++){
					DivisionInfo item = (DivisionInfo) list.get(i);
					JSONObject obj = new JSONObject();
					// Initialize value.
					obj.put("parent", utilityDAO.getCityOfJSON(item.getId(), session.getLanguage(req.getSession())));
					obj.put("child", utilityDAO.getCityListOfJSON(item.getId(), session.getLanguage(req.getSession())));
					div_result.put(obj);
				}
			}else{
				if (geographical.length()>0){
					String[] div = geographical.split(",");
					for (int i=0; i<div.length; i++){
						JSONObject obj = new JSONObject();
						// Initialize value.
						obj.put("parent", utilityDAO.getCityOfJSON(Utils.getLong(div[i]), session.getLanguage(req.getSession())));
						obj.put("child", utilityDAO.getCityListOfJSON(Utils.getLong(div[i]), session.getLanguage(req.getSession())));
						div_result.put(obj);
					}
				}
			}

			result = setResponse(result, "list", div_result);
			result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_SUCCESS);
			result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0001", session.getLanguage(req.getSession())));
						
		}catch (Exception e){
			log.error("CategoryController", "loadCity", e.toString());
			result = setResponse(result, "list", "");
		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}
		
		response(res, result);
	}
	
	/**
	 * Get Parent data.
	 * 
	 * @param type
	 * @param channel_id
	 * @param family Family ID
	 * @param req
	 * @param res
	 */
	@RequestMapping("loadParent.html")
	public void loadParent(
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "channel_id", required = false) String channel_id,
			@RequestParam(value = "family", required = false) String family,
			HttpServletRequest req, HttpServletResponse res){
		
		type = Utils.checkNull(type);
		channel_id = Utils.checkNull(channel_id);
		family = Utils.checkNull(family);
		
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
			
			if (family.length()==0){
				result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}
			//dao connection
			CategoryDAO categoryDAO = new CategoryDAO();		
			JSONArray div_result = new JSONArray();
			
			ArrayList list = categoryDAO.getChannelList(Utils.getLong(family), (long)0);
			for (int i=0; i<list.size(); i++){
				ChannelInfo item = (ChannelInfo) list.get(i);
				if (type.equals("edit") && Utils.getLong(channel_id)==item.getId()){
					
				}else{
					JSONObject obj = new JSONObject();
					// Initialize value.
					obj.put("parent", item.toJSONObject());
					obj.put("child", categoryDAO.recallChannelList(Utils.getLong(family), item.getId(), type, channel_id));
					div_result.put(obj);
				}
			}

			result = setResponse(result, "list", div_result);
			result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_SUCCESS);
			result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0001", session.getLanguage(req.getSession())));
						
		}catch (Exception e){
			log.error("CategoryController", "loadParent", e.toString());
			result = setResponse(result, "list", "");
		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}
		
		response(res, result);
	}
	
	/**
	 * Update Channel. (add, edit or delete)
	 * 
	 * @param type Add, Edit or Delete
	 * @param channel_id
	 * @param channel_name
	 * @param family_id
	 * @param email
	 * @param password
	 * @param parent
	 * @param access Public or Private
	 * @param security_level
	 * @param direccion
	 * @param empresa
	 * @param ciudad
	 * @param req
	 * @param res
	 */
	@RequestMapping("updateChannel.html")
	public void updateChannel(
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "channel_id", required = false) String channel_id,
			@RequestParam(value = "channel_name", required = false) String channel_name,
			@RequestParam(value = "family_id", required = false) String family_id,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "parent", required = false) String parent,
			@RequestParam(value = "access", required = false) String access,
			@RequestParam(value = "security_level", required = false) String security_level,
			@RequestParam(value = "direccion", required = false) String direccion,
			@RequestParam(value = "empresa", required = false) String empresa,
			@RequestParam(value = "ciudad", required = false) String ciudad,
			

			HttpServletRequest req, HttpServletResponse res){

		Connection conn = DSManager.getConnection();

		JSONObject result = new JSONObject();

		result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_FAILED);
		result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0002", session.getLanguage(req.getSession())));

		type = Utils.checkNull(type);
		channel_id = Utils.checkNull(channel_id);
		channel_name = Utils.encode(Utils.checkNull(channel_name));
		family_id = Utils.checkNull(family_id);
		email = Utils.checkNull(email);
		password = Utils.checkNull(password);
		parent = Utils.checkNull(parent);
		access =Utils.checkNull(access);
		security_level = Utils.checkNull(security_level);
		direccion = Utils.checkNull(direccion);
		empresa = Utils.checkNull(empresa);
		ciudad = Utils.checkNull(ciudad);

		if (parent.length()==0)
			parent="0";

		try{
			UserInfo user = session.getUserInfo(req.getSession());
			if (user==null){

				result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}

			if (type.length()==0){

				result.put(Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}

			if (!type.equals("add") && !type.equals("edit") && !type.equals("delete")){

				result.put(Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}
			//dao connection
			CategoryDAO categoryDAO = new CategoryDAO();
			conn.setAutoCommit(false);
			ContentDAO  contentDAO = new ContentDAO();			
			//evaluated value type

			if (type.equals("add")){
				Long channelID = categoryDAO.insertChannel(Utils.getLong(family_id), Utils.getLong(parent), channel_name, email, password, access, security_level, "","");

				if (empresa.length()>0){
					String[] lst = empresa.split(",");
					for (int i=0; i<lst.length; i++)
						categoryDAO.insertChannelList(channelID, Utils.getLong(lst[i]), "channelcompany");
				}
				
				if (direccion.length()>0){
					String[] lst = direccion.split(",");
					for (int i=0; i<lst.length; i++)
						categoryDAO.insertChannelList(channelID, Utils.getLong(lst[i]), "channelmaindirection");
				}
				
				if (ciudad.length()>0){
					String[] lst = ciudad.split(",");
					for (int i=0; i<lst.length; i++)
						categoryDAO.insertChannelList(channelID, Utils.getLong(lst[i]), "channelcity");
				}
			}else if (type.equals("edit")){
				long channelID = Utils.getLong(channel_id);
				long lonFamily=0;
			
				categoryDAO.deleteChannelList(channelID, "channelcity");
				categoryDAO.deleteChannelList(channelID, "channelcompany");
				categoryDAO.deleteChannelList(channelID, "channelmaindirection");
				categoryDAO.updateChannel(Utils.getLong(family_id), Utils.getLong(parent), channel_name, email, password, access, security_level,channelID);
				conn.commit();
				
				lonFamily=categoryDAO.getChanneFamilyId(channelID);
				
				categoryDAO.getChanneFamily_FamilyId(channelID,lonFamily);
				conn.commit();
				
				int ChanelId=0;
				ChanelId = categoryDAO.getChannelInfoName(channel_name);
				conn.commit();
				
				channelID=ChanelId;

				if (empresa.length()>0){
					String[] lst = empresa.split(",");
					for (int i=0; i<lst.length; i++)
						categoryDAO.insertChannelList(channelID, Utils.getLong(lst[i]), "channelcompany");
				}
				
				if (direccion.length()>0){
					String[] lst = direccion.split(",");
					for (int i=0; i<lst.length; i++)
						categoryDAO.insertChannelList(channelID, Utils.getLong(lst[i]), "channelmaindirection");
				}
				
				if (ciudad.length()>0){
					String[] lst = ciudad.split(",");
					for (int i=0; i<lst.length; i++)
						categoryDAO.insertChannelList(channelID, Utils.getLong(lst[i]), "channelcity");
				}
				
			}else if (type.equals("delete")){
				long channelID = Utils.getLong(channel_id);
				long valor=0;
				categoryDAO.deleteChannelList(channelID, "channelcity");
				categoryDAO.deleteChannelList(channelID, "channelcompany");
				categoryDAO.deleteChannelList(channelID, "channelmaindirection");
				categoryDAO.deleteChannelList(channelID);
				
				valor = categoryDAO.getChanneFather(channelID);
				if (valor != 1)
				{
					//Towa RJR 20150924 inicio
					Long contentid = categoryDAO.getContentByChannelId(channelID);
					while (contentid!=0)
					{
						contentDAO.deleteContent(contentid);
						contentid = categoryDAO.getContentByChannelId(channelID);
					}
									
					//Towa RJR 20150924 fin
					categoryDAO.deleteChannel(channelID);
				}

			}

			conn.commit();
			
			// load all family data.
			JSONArray div_result = new JSONArray();
			//dao connection
			CategoryDAO categoryDao = new CategoryDAO();
			ArrayList list = categoryDao.getFamilyList();
			for (int i=0; i<list.size(); i++){
				FamilyInfo item = (FamilyInfo) list.get(i);
				JSONObject obj = new JSONObject();
				obj.put("parent", item.toJSONObject());
				
				JSONArray div_result2 = new JSONArray();
				ArrayList list2 = categoryDao.getChannelList(item.getId(), (long)0);
				for (int i2=0; i2<list2.size(); i2++){
					ChannelInfo item2 = (ChannelInfo) list2.get(i2);
					JSONObject obj2 = new JSONObject();
					// Initialize value.
					obj2.put("parent", item2.toJSONObject());
					obj2.put("child", categoryDao.recallChannelList(item.getId(), item2.getId(), "", ""));
					div_result2.put(obj2);
				}
				// Initialize value.
				obj.put("child", div_result2);
				div_result.put(obj);
			}
			
			result = setResponse(result, "list", div_result);
			result.put(Constants.ERROR_CODE, Constants.ACTION_SUCCESS);
			result.put(Constants.ERROR_MSG, reg.getMessage("ACT0001", session.getLanguage(req.getSession())));
			
		}catch (Exception e){ 
			log.error("CategoryController", "updateChannel", e.toString());

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
	 * Update Position.
	 * 
	 * @param type
	 * @param position
	 * @param req
	 * @param res
	 */
	@RequestMapping("updatePosition.html")
	public void updatePosition(
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "position", required = false) String position,
			HttpServletRequest req, HttpServletResponse res){

		Connection conn = DSManager.getConnection();
		JSONObject result = new JSONObject();
		
	
		result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_FAILED);
		result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0002", session.getLanguage(req.getSession())));
		
		type = Utils.checkNull(type);
		position = Utils.checkNull(position);

		try{
			UserInfo user = session.getUserInfo(req.getSession());
			if (user==null){
				result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}

			if (type.length()==0){

				result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}

			//evaluated value type
			if (!type.equals("family") && !type.equals("channel")){

				result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}
			//dao connection
			CategoryDAO categoryDao = new CategoryDAO();
			conn.setAutoCommit(false);
			
			if (type.equals("family")){
				if (position.length()>0){
				String[] pos = position.split(",");
				for (int i=0; i<pos.length; i++){
					try{
						categoryDao.updateFamilyPosition(Utils.getLong(pos[i]), (long)i+1);
					}catch (Exception ff){}
				}
				}
			}else if (type.equals("channel")){
				categoryDao.updateChannel((long)0, new JSONArray(position));
			}
			
			conn.commit();

			result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_SUCCESS);
			result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0001", session.getLanguage(req.getSession())));
			
		}catch (Exception e){ 
			log.error("CategoryController", "updatePosition", e.toString());
			
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
