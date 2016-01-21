package com.youandbbva.enteratv.controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
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
import com.youandbbva.enteratv.dao.OppUserDAO;
import com.youandbbva.enteratv.dao.OpportDAO;
import com.youandbbva.enteratv.dao.UtilityDAO;
import com.youandbbva.enteratv.domain.OpportunitiesInfo;
import com.youandbbva.enteratv.domain.UserInfo;
import com.youandbbva.enteratv.domain.ValidationInfo;

/**
 * Handle all action for opportunities page.
 *  
 * @author CJH
 *
 */

@Controller
@RequestMapping("/opportunities/")
@Component("OpportunitiesController")
public class OpportunitiesController extends com.youandbbva.enteratv.Controller{

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
			@RequestParam(value = "user_id", required = false) String user_id,
			@RequestParam(value = "session_id", required = false) String session_id,
			HttpServletRequest req, HttpServletResponse res){

		user_id = Utils.checkNull(user_id);
		session_id = Utils.checkNull(session_id);
		
		Connection conn = DSManager.getConnection();

		try{
			if (user_id.length()==0 || session_id.length()==0){
//				session.logout(req.getSession());
				throw new Exception("Session is invalid");
			}
			
			if (DigestUtils.md5Hex(user_id+Constants.SESSION_HASH).equals(session_id)){
				
				if (session.getFrontUserInfo(req.getSession())==null){
					throw new Exception("Session is invalid");
				} else {
					UserInfo user = new UserInfo();
				     user.setUserId(Integer.parseInt(user_id));
				     user.setUserEmployeeNumber("outside_user");
				     user.setUserName("");
				     user.setUserFirstName("");
				     user.setUserFirstName("");
				     session.setOpportUserInfo(req.getSession(), user);
					
					ModelAndView mv = new ModelAndView("opp_home");
					return mv;
				}
				
			} else {
				throw new Exception("Session is invalid");
			}
			
		}catch (Exception e){ 
			log.error("OpportunitiesController", "home", e.toString());

		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}

		return new ModelAndView("redirect:/public/home.html");
	}

	/**
	 * inmueble Step2 page.
	 * 
	 * @param req
	 * @param res
	 * @return view
	 */
	@RequestMapping("inmueble-2.html")
	public ModelAndView inmueble_2(
			HttpServletRequest req, HttpServletResponse res){

		Connection conn = DSManager.getConnection();

		try{
			UserInfo user = session.getOpportUserInfo(req.getSession());
			if (user==null){
				throw new Exception(reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
			}
			
			ModelAndView mv = new ModelAndView("opp_inmueble-2");
			return mv;
		}catch (Exception e){ 
			log.error("OpportunitiesController", "inmueble_2", e.toString());

		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}

		return new ModelAndView("redirect:/opportunities/home.html");
	}
	
	/**
	 * inmueble Step3 page.
	 * 
	 * @param req
	 * @param res
	 * @return view
	 */
	@RequestMapping("inmueble-3.html")
	public ModelAndView inmueble_3(
			HttpServletRequest req, HttpServletResponse res){

		
		Connection conn = DSManager.getConnection();

		try{
			UserInfo user = session.getOpportUserInfo(req.getSession());
			if (user==null){
				throw new Exception(reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
			}
			
			ModelAndView mv = new ModelAndView("opp_inmueble-3");
			OpportDAO dao = new OpportDAO();
			mv.addObject("state_list", dao.getStateList(session.getLanguage(req.getSession()), false));
			return mv;
		}catch (Exception e){ 
			log.error("OpportunitiesController", "inmueble_3", e.toString());

		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}

		return new ModelAndView("redirect:/opportunities/home.html");
	}
	
	/**
	 * Inmueble Step3-2 page.
	 * 
	 * @param req
	 * @param res
	 * @return view
	 */
	@RequestMapping("inmueble-3_2.html")
	public ModelAndView inmueble_3_2(
			@RequestParam(value = "state_id", required = false) String state_id,
			HttpServletRequest req, HttpServletResponse res){

		Connection conn = DSManager.getConnection();
		
		state_id = Utils.checkNull(state_id);

		try{
			UserInfo user = session.getOpportUserInfo(req.getSession());
			if (user==null){
				throw new Exception(reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
			}
			
			OpportDAO dao = new OpportDAO();
			
			ModelAndView mv = new ModelAndView("opp_inmueble-3_2");
			if (state_id.length()==0 || state_id.equals("0") || state_id.equals("all")){
				mv.addObject("city_list", new ArrayList());
			}else{
				Long stateID = Utils.getLong(state_id);
				mv.addObject("city_list", dao.getCityList(stateID, session.getLanguage(req.getSession()), false));
			}
			
			return mv;
		}catch (Exception e){ 
			log.error("OpportunitiesController", "inmueble_3_2", e.toString());

		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}

		return new ModelAndView("redirect:/opportunities/home.html");
	}

	/**
	 * inmueble result page.
	 * 
	 * @param req
	 * @param res
	 * @return view
	 */
	@RequestMapping("results_inmueble.html")
	public ModelAndView results_inmueble(
			HttpServletRequest req, HttpServletResponse res){

		Connection conn = DSManager.getConnection();

		try{
			UserInfo user = session.getOpportUserInfo(req.getSession());
			if (user==null){
				throw new Exception(reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
			}
			
			ModelAndView mv = new ModelAndView("opp_results_inmueble");
			return mv;
		}catch (Exception e){ 
			log.error("OpportunitiesController", "results_inmueble", e.toString());

		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}

		return new ModelAndView("redirect:/opportunities/home.html");
	}

	/**
	 * automovil Steps2 page.
	 * 
	 * @param req
	 * @param res
	 * @return view
	 */
	@RequestMapping("automovil-2.html")
	public ModelAndView automovil_2(
			HttpServletRequest req, HttpServletResponse res){

		Connection conn = DSManager.getConnection();

		try{
			UserInfo user = session.getOpportUserInfo(req.getSession());
			if (user==null){
				throw new Exception(reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
			}
			
			ModelAndView mv = new ModelAndView("opp_automovil-2");
			OpportDAO dao = new OpportDAO();
			mv.addObject("brand_list", dao.getBrandList(session.getLanguage(req.getSession()), false));
			return mv;
		}catch (Exception e){ 
			log.error("OpportunitiesController", "automovil_2", e.toString());

		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}

		return new ModelAndView("redirect:/opportunities/home.html");
	}
	
	/**
	 * automovil result page.
	 * 
	 * @param req
	 * @param res
	 * @return view
	 */
	@RequestMapping("results_automovil.html")
	public ModelAndView results_automovil(
			HttpServletRequest req, HttpServletResponse res){

		Connection conn = DSManager.getConnection();

		try{
			UserInfo user = session.getOpportUserInfo(req.getSession());
			if (user==null){
				throw new Exception(reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
			}
			
			ModelAndView mv = new ModelAndView("opp_results_automovil");
			return mv;
		}catch (Exception e){ 
			log.error("OpportunitiesController", "results_automovil", e.toString());

		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}

		return new ModelAndView("redirect:/opportunities/home.html");
	}

	@RequestMapping("results_automovil_adjudicado.html")
	public ModelAndView results_automovil_adjudicado(
			HttpServletRequest req, HttpServletResponse res){

		Connection conn = DSManager.getConnection();

		try{
			UserInfo user = session.getOpportUserInfo(req.getSession());
			if (user==null){
				throw new Exception(reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
			}
			
			ModelAndView mv = new ModelAndView("opp_results_automovil_adjudicado");
			return mv;
		}catch (Exception e){ 
			log.error("OpportunitiesController", "results_automovil_adjudicado", e.toString());

		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}

		return new ModelAndView("redirect:/opportunities/home.html");
	}
	
	@RequestMapping("inmueble_adjudicado-2.html")
	public ModelAndView inmueble_adjudicado_2(
			HttpServletRequest req, HttpServletResponse res){

		Connection conn = DSManager.getConnection();

		try{
			UserInfo user = session.getOpportUserInfo(req.getSession());
			if (user==null){
				throw new Exception(reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
			}
			
			ModelAndView mv = new ModelAndView("opp_inmueble_adjudicado-2");
			return mv;
		}catch (Exception e){ 
			log.error("OpportunitiesController", "inmueble_adjudicado-2", e.toString());

		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}

		return new ModelAndView("redirect:/opportunities/home.html");
	}
	
	/**
	 * varios Steps2 page.
	 * 
	 * @param req
	 * @param res
	 * @return view
	 */
	@RequestMapping("varios-2.html")
	public ModelAndView varios_2(
			HttpServletRequest req, HttpServletResponse res){

		Connection conn = DSManager.getConnection();

		try{
			UserInfo user = session.getOpportUserInfo(req.getSession());
			if (user==null){
				throw new Exception(reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
			}
			
			ModelAndView mv = new ModelAndView("opp_varios-2");
			UtilityDAO codeDao = new UtilityDAO();
			mv.addObject("varios_list", codeDao.getCodeList("opp5", session.getLanguage(req.getSession())));
			return mv;
		}catch (Exception e){ 
			log.error("OpportunitiesController", "varios_2", e.toString());

		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}

		return new ModelAndView("redirect:/opportunities/home.html");
	}
	
	/**
	 * varios result page.
	 * 
	 * @param req
	 * @param res
	 * @return view
	 */
	@RequestMapping("results_varios.html")
	public ModelAndView results_varios(
			HttpServletRequest req, HttpServletResponse res){

		Connection conn = DSManager.getConnection();

		try{
			UserInfo user = session.getOpportUserInfo(req.getSession());
			if (user==null){
				throw new Exception(reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
			}
			
			ModelAndView mv = new ModelAndView("opp_results_varios");
			return mv;
		}catch (Exception e){ 
			log.error("OpportunitiesController", "results_varios", e.toString());

		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}

		return new ModelAndView("redirect:/opportunities/home.html");
	}
	
	
	/**
	 * Load opportunities data with DataTable.
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("loadOpportunitiesResults.html")
	public void loadOpportunitiesResults(
			HttpServletRequest request, HttpServletResponse response){

	    String[] cols = { "c.price", "c.mileage", "c.transmission", "c.updated_at" };
	    String table = "bbva_opportunities";
	     
	    JSONObject result = new JSONObject();
	    int amount = 10;
	    int start = 0;
	    int col = 3;
	    
	    String kind = Utils.checkNull(request.getParameter("kind"));
	    String serve_type = Utils.checkNull(request.getParameter("serve_type"), "all");
	    String brand_id= Utils.checkNull(request.getParameter("brand_id"));
	    String state_id = Utils.checkNull(request.getParameter("state_id"));
	    String city_id = Utils.checkNull(request.getParameter("city_id"));
	    String varios= Utils.checkNull(request.getParameter("varios"));
	    
	    String dir = "desc";
	    String aSQL = "";
	    if (serve_type.length()>0 && !serve_type.equals("all")){
	    	aSQL = " c.serve_type='" + serve_type + "' ";
	    }
	    
	    if (state_id.length()>0 && !state_id.equals("0") && !state_id.equals("all")){
	    	if (aSQL.length()>0)
	    		aSQL += " and ";
	    	aSQL += " c.state_id=" + state_id + " ";
	    }
	    
	    if (city_id.length()>0 && !city_id.equals("0") && !city_id.equals("all")){
	    	if (aSQL.length()>0)
	    		aSQL += " and ";
	    	aSQL += " c.city_id=" + city_id + " ";
	    }
	 
	    if (brand_id.length()>0 && !brand_id.equals("0") && !brand_id.equals("all")){
	    	if (aSQL.length()>0)
	    		aSQL += " and ";
	    	aSQL += " c.brand_id=" + brand_id + " ";
	    }
	    
	    if (varios.length()>0 && !varios.equals("all")){
	    	if (aSQL.length()>0)
	    		aSQL += " and ";
	    	aSQL += " c.varios='" + varios + "' ";
	    }

	    String sStart = Utils.checkNull(request.getParameter("start"));
	    String sAmount = Utils.checkNull(request.getParameter("length"));
//	    String sEcho = Utils.checkNull(request.getParameter("sEcho"));
	    String sCol = Utils.checkNull(request.getParameter("iSortCol_0"));
	    String sdir = Utils.checkNull(request.getParameter("sSortDir_0"));

	    Map<String, String[]> parameters = request.getParameterMap();
	    sdir = parameters.get("order[0][dir]")[0];
	    sCol = parameters.get("order[0][column]")[0];
	    
	    String searchTerm = Utils.checkNull(request.getParameter("search[value]"));
	    String individualSearch = "";
	    
	    if (sStart.length()>0) {
	        start = Integer.parseInt(sStart);
	        if (start < 0)
	            start = 0;
	    }

	    if (sAmount.length()>0) {
	        amount = Integer.parseInt(sAmount);
	        if (amount < 10 || amount > 100)
	            amount = 10;
	    }
	    
//	    if (sCol .length()>0 ) {
//	        col = Integer.parseInt(sCol);
//	        if (col < 1 || col > 3)
//	            col = 3;
//	    }
//	    
//	    if (sdir .length()>0 ) {
//	        if (!sdir.equals("desc"))
//	            dir = "asc";
//	    }
	    
	    String colName = cols[col];
	    Long total = (long)0;
	    Long totalAfterFilter = (long)0;
	    
	    Connection conn = DSManager.getConnection();
	    
	    if (aSQL.length()>0){
	    	aSQL = " and ( " + aSQL + " ) ";
	    }

		try{
			UserInfo user = session.getOpportUserInfo(request.getSession());
			if (user==null){
				throw new Exception(reg.getMessage("ACT0003", session.getLanguage(request.getSession())));
			}
			
			OpportDAO dao = new OpportDAO();
			
			conn.setAutoCommit(false);
			dao.updateVisible();
			conn.commit();
			
			String sql = " select count(*) from " + table + " c where c.type='"+kind+"' and c.visible='1' ";
			sql += aSQL;
			total = dao.getCount(sql);
			totalAfterFilter = total;
			
			sql = " select c.*, d.value, d.value_en, d.value_me, e.value as property_name, e.value_en as property_name_en, e.value_me as property_name_me, "
					+ " f.value as varios_name, f.value_en as varios_name_en, f.value_me as varios_name_me "
//					+ " , br.name as brand_name, st.name as state_name, ct.name as city_name "
					+ " from " + table + " c, bbva_code d, bbva_code e, bbva_code f"
//							+ " , bbva_brand br, bbva_state st, bbva_state_city ct "
							+ " where "//ct.id=c.city_id and st.id=c.state_id and br.id=c.brand_id and "
							+ " d.div='opp3' and d.code=c.transmission and c.type='"+kind+"'  and e.div='opp1' and e.code=c.property and f.div='opp5' and f.code=c.varios and c.visible='1' ";
			String searchSQL = "";
			String globeSearch =  " ( c.price like '%"+searchTerm+"%' "
                    + " or c.mileage like '%"+searchTerm+"%'"
                    + " or c.model like '%"+searchTerm+"%'"
                    + " or c.employee_num like '%"+searchTerm+"%'"
                    + " or c.today like '%"+searchTerm+"%'"
                    + " or c.public_date like '%"+searchTerm+"%'"
                    + " or c.obs like '%"+searchTerm+"%'"
                    + " or c.color like '%"+searchTerm+"%'"
                    + " or c.doors like '%"+searchTerm+"%'"
                    + " or c.telephone like '%"+searchTerm+"%'"
                    + " or c.mobilephone like '%"+searchTerm+"%'"
                    + " or c.user_id like '%"+searchTerm+"%'"
                    
//                    + " or st.name like '%"+searchTerm+"%'"
//                    + " or ct.name like '%"+searchTerm+"%'"
                    
                    + " or c.plants like '%"+searchTerm+"%'"
                    + " or c.rooms like '%"+searchTerm+"%'"
                    + " or c.serve_type like '%"+searchTerm+"%'"
                    + " or c.amueblado like '%"+searchTerm+"%'"
                    
                    + " or d.value like '%"+searchTerm+"%'"
                    + " or d.value_en like '%"+searchTerm+"%'"
                    + " or d.value_me like '%"+searchTerm+"%'"
                    
                    + " or e.value like '%"+searchTerm+"%'"
                    + " or e.value_en like '%"+searchTerm+"%'"
                    + " or e.value_me like '%"+searchTerm+"%'"
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
			
			sql += aSQL;
	        sql += searchSQL;
	        sql += " order by " + colName + " " + dir;
	        sql += " limit " + start + ", " + amount;	        
			
	        JSONArray array = dao.getContent(sql, session.getLanguage(request.getSession()));
	        
//			sql = " select count(*) from " + table + " c, bbva_code d, bbva_code e, bbva_code f, bbva_brand br, bbva_state st, bbva_state_city ct where ct.id=c.city_id and st.id=c.state_id and br.id=c.brand_id and d.div='opp3' and d.code=c.transmission and c.type='"+kind+"' and e.div='opp1' and e.code=c.property and f.div='opp5' and f.code=c.varios ";
//			sql += aSQL;
//			if (searchTerm!=""){
//				sql += searchSQL;
//				totalAfterFilter = dao.getCount(sql);
//			}

			result.put("recordsTotal", total);
	        result.put("recordsFiltered", totalAfterFilter);
	        result.put("data", array);
			
		}catch (Exception e){ 
			log.error("OpportunitiesController", "loadOpportunitiesResults", e.toString());

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
	 * Detail view page.
	 * 
	 * @param publish_id
	 * @param req
	 * @param res
	 * @return view
	 */
	@RequestMapping("detail.html")
	public ModelAndView detail(
			@RequestParam(value = "publish_id", required = false) String publish_id,
			HttpServletRequest req, HttpServletResponse res){

		publish_id = Utils.checkNull(publish_id);
		
		Connection conn = DSManager.getConnection();

		try{
			UserInfo user = session.getOpportUserInfo(req.getSession());
			if (user==null){
				throw new Exception(reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
			}
			
			ModelAndView mv = new ModelAndView("opp_detail");
			mv.addObject("publish_id", publish_id);
			
			OpportDAO dao = new OpportDAO();
			
			OpportunitiesInfo opport = dao.getOpportunities(Utils.getLong(publish_id));
			
			if (opport!=null){
				mv.addObject("type", opport.getType());
				mv.addObject("brand_id", opport.getBrandID());
				mv.addObject("state_id", opport.getStateID());
				mv.addObject("city_id", opport.getCityID());
				mv.addObject("model", opport.getModel());
				mv.addObject("price", opport.getPrice());
				mv.addObject("today", opport.getToday());
				mv.addObject("mileage", opport.getMileage());
				mv.addObject("transmission", opport.getTransmission());
				mv.addObject("employee_num", opport.getEmployeeNum());
				mv.addObject("public_date", opport.getPublicDate());
				mv.addObject("obs", opport.getObs());
				mv.addObject("color", opport.getColor());
				mv.addObject("doors", opport.getDoors());
				mv.addObject("telephone", opport.getTelephone());
				mv.addObject("mobilephone", opport.getMobilephone());
				mv.addObject("file", opport.getFile());
				mv.addObject("created_at", opport.getCreatedAt());
				mv.addObject("updated_at", opport.getUpdatedAt());
				mv.addObject("status", opport.getStatus());
				
				mv.addObject("serve_type", opport.getServeType());
				mv.addObject("property", opport.getProperty());
				mv.addObject("plants", opport.getPlants());
				mv.addObject("rooms", opport.getRooms());
				mv.addObject("amueblado", opport.getAmueblado());
				
				mv.addObject("varios", opport.getVarios());
				
				String brand_name = "";
				if (opport.getBrandID()!=0)
					brand_name = dao.getBrandName(opport.getBrandID());
				mv.addObject("brand_name", brand_name);
				
				String location = "";
				if (opport.getStateID()!=0){
					location = dao.getStateName(opport.getStateID());
					String city_name="";
					if (opport.getCityID()!=0)
						city_name = dao.getCityName(opport.getCityID());
					
					if (city_name.length()>0)
						location += ", " + city_name;
				}
				mv.addObject("location", location);
				
				mv.addObject("varios_name", dao.getCodeValue("opp5", opport.getVarios(), session.getLanguage(req.getSession())));
				mv.addObject("transmission_name", dao.getCodeValue("opp3", opport.getTransmission(), session.getLanguage(req.getSession())));
				mv.addObject("property_name", dao.getCodeValue("opp1", opport.getProperty(), session.getLanguage(req.getSession())));
			}
			
			return mv;
			
		}catch (Exception e){ 
			log.error("OpportunitiesController", "detail", e.toString());

		}finally{
			try{
				//if (conn!=null)
				//	conn.close();
			}catch (Exception f){}
		}

		return new ModelAndView("redirect:/opportunities/home.html");
	}	

	
	
	/**
	 * My Ads page.
	 * 
	 * @param req
	 * @param res
	 * @return view
	 */
	@RequestMapping("myads.html")
	public ModelAndView myads(
			HttpServletRequest req, HttpServletResponse res){

//		Connection conn = DSManager.getConnection();

		try{
			UserInfo user = session.getOpportUserInfo(req.getSession());
			if (user==null){
				throw new Exception(reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
			}
			
			if (user.getUserId() == 0){
			    throw new Exception(reg.getMessage("ACT0003"));
			}
			
			ModelAndView mv = new ModelAndView("opp_myads");
			return mv;
		}catch (Exception e){ 
			log.error("OpportunitiesController", "myads", e.toString());

		}finally{
//			try{
//				if (conn!=null)
//					conn.close();
//			}catch (Exception f){}
		}

		return new ModelAndView("redirect:/opportunities/home.html");
//		return new ModelAndView("redirect:/opportunities/login.html");
	}

	
	
	/**
	 * Login page.
	 * 
	 * @param req
	 * @param res
	 * @return view
	 */
	@RequestMapping("login.html")
	public ModelAndView login(
			HttpServletRequest req, HttpServletResponse res){

		ModelAndView mv = new ModelAndView("opp_login");
		
		String username = "";
		String password = "";
		
		try{
			Cookie[] cookie = req.getCookies();
			for (int i=0; i<cookie.length; i++){
				Cookie c = cookie[i];
				if (c.getName().equals(Constants.PASSWORD_HASH + "_username")){
					username = c.getValue();
				}
				
				if (c.getName().equals(Constants.PASSWORD_HASH + "_password")){
					password = c.getValue();
				}
			}
		}catch (Exception e){}
		
		mv.addObject("username", username);
		mv.addObject("password", password);

		return mv;
	}	
	
	/**
	 * Logout Page.
	 * 
	 * @param req
	 * @param res
	 * @return view
	 */
	@RequestMapping("logout.html")
	public ModelAndView logout(
			HttpServletRequest req, HttpServletResponse res){
		
		session.logout(req.getSession());
				
		return new ModelAndView("redirect:/opportunities/home.html");
	}
	
	/**
	 * Check user and password.
	 * if valid, show dashboard.
	 *
	 * @param user UserID
	 * @param password
	 * @param req
	 * @param res
	 * @return view
	 */
	@RequestMapping("check.html")
	public ModelAndView check(
			@RequestParam(value = "username", required = false) String user,
			@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "remember", required = false) String remember,
			HttpServletRequest req, HttpServletResponse res){

		Connection conn = DSManager.getConnection();

		String userID = Utils.checkNull(user);
		password = Utils.checkNull(password);
		remember = Utils.checkNull(remember);

		try{
			if (userID.length()==0){
				session.setFlashMessage(req.getSession(), reg.getMessage("USR0004", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("USR0004"));
			}

			if (password.length()==0){
				session.setFlashMessage(req.getSession(), reg.getMessage("USR0005", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("USR0005"));
			}
			
			if (remember.equals("on")){
				Cookie cookie1 = new Cookie(Constants.PASSWORD_HASH+"_username", userID);
				Cookie cookie2 = new Cookie(Constants.PASSWORD_HASH+"_password", password);
				cookie1.setMaxAge(Constants.EXPIRED_COOKIE_TIME);
				cookie2.setMaxAge(Constants.EXPIRED_COOKIE_TIME);
				res.addCookie(cookie1);
				res.addCookie(cookie2);
			}else{
				Cookie cookie1 = new Cookie(Constants.PASSWORD_HASH+"_username", "");
				Cookie cookie2 = new Cookie(Constants.PASSWORD_HASH+"_password", "");
				cookie1.setMaxAge(Constants.EXPIRED_COOKIE_TIME);
				cookie2.setMaxAge(Constants.EXPIRED_COOKIE_TIME);
				res.addCookie(cookie1);
				res.addCookie(cookie2);
			}
			
			OppUserDAO OppUserDAO = new OppUserDAO();
			if (!OppUserDAO.isValidUser(userID)){
				session.setFlashMessage(req.getSession(), reg.getMessage("USR0002", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("USR0002"));
			}

			UserInfo u = OppUserDAO.getUserInfo(userID);
			   if (u!=null){
			    if (u.getUserStatus() != Integer.parseInt(Constants.DEFAULT_ACTIVE)){
			     session.setFlashMessage(req.getSession(), reg.getMessage("USR0006", session.getLanguage(req.getSession())));
			     throw new Exception(reg.getMessage("USR0006"));
			    }
				log.info("OpportunitiesController", "PasswordHash", DigestUtils.md5Hex(password + Constants.PASSWORD_HASH));
//				
//				if (!DigestUtils.md5Hex(password + Constants.PASSWORD_HASH).equals(u.getPassword())){
//					session.setFlashMessage(req.getSession(), reg.getMessage("USR0003", session.getLanguage(req.getSession())));
//					throw new Exception(reg.getMessage("USR0003"));
//				}

				if (u.getUserAccessLevel() != Integer.parseInt(Constants.CODE_USER_ADMINISTRATOR))
				     session.setOpportUserInfo(req.getSession(), u);
				    else{
				     session.setFlashMessage(req.getSession(), reg.getMessage("USR0002", session.getLanguage(req.getSession())));
				     throw new Exception(reg.getMessage("USR0002"));
				    }
				
				return new ModelAndView("redirect:/opportunities/myads.html");
			}

		}catch (Exception e){ 
//			e.printStackTrace();
			log.error("OpportunitiesController", "check", e.toString());
		}finally{
			try{
				//if (conn!=null)
				//	conn.close();
			}catch (Exception f){}
		}

		ModelAndView mv = new ModelAndView("opp_login");
		mv.addObject("username", userID);
		mv.addObject("password", "");
		
		return mv;
	}

	/**
	 * Send email for reset password.
	 *
	 * @param username UserID
	 * @param req
	 * @param res
	 */
	@RequestMapping("resetPassword.html")
	public void resetPassword(
			@RequestParam(value = "username", required = false) String username,
			HttpServletRequest req, HttpServletResponse res){

		username = Utils.checkNull(username);

		Connection conn = DSManager.getConnection();

		JSONObject result = new JSONObject();
		result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_FAILED);
		result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0002", session.getLanguage(req.getSession())));

		try{
			if (username.length()==0){
				session.setFlashMessage(req.getSession(), reg.getMessage("USR0004", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("USR0004"));
			}

			OppUserDAO dao = new OppUserDAO();
			UserInfo user = dao.getUserInfo(username);
			if (user==null){
				result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}

			String session_id = Utils.getUUID(32);
			String time = Utils.getTodayWithTime();
			//dao.insertValidation(username, session_id, time);
			
			Properties props = new Properties();
	        Session session1 = Session.getDefaultInstance(props, null);
	        
	        try {
	            Message msg = new MimeMessage(session1);
	            msg.setFrom(new InternetAddress(Constants.SERVICE_ACCOUNT));
	            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getUserEmail()));
	            msg.setSubject("Your account has been activated.");
	            msg.setText("please redirect this url to reset password. " + Constants.BASEPATH + "opportunities/reset.html?username=" + username + "&session="+session_id);
	            Transport.send(msg);
	        } catch (Exception e) {		}	        

			result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_SUCCESS);
			result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0001", session.getLanguage(req.getSession())));
	        
		}catch (Exception e){ 
			log.error("OpportunitiesController", "resetPassword", e.toString());
			
		}finally{
			try{
			//	if (conn!=null)
			//		conn.close();
			}catch (Exception f){}
		}

		response(res, result);
	}
	
	/**
	 * Reset Password Page.
	 * 
	 * @param req
	 * @param res
	 * @return view
	 */
	@RequestMapping("reset.html")
	public ModelAndView reset(
			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "session", required = false) String session_id,
			HttpServletRequest req, HttpServletResponse res){
		
		Connection conn = DSManager.getConnection();
		
		username = Utils.checkNull(username);
		session_id = Utils.checkNull(session_id);

		try{
			if (username.length()==0 || session_id.length()==0){
				session.setFlashMessage(req.getSession(), reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
				throw new Exception("");
			}
			
			OppUserDAO dao = new OppUserDAO();
			UserInfo user = dao.getUserInfo(username);
			//ValidationInfo v = dao.getValidation(username, session_id);
			
			if (user==null){
				session.setFlashMessage(req.getSession(), reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
				throw new Exception("");
				
			}/*else if (v==null){
				session.setFlashMessage(req.getSession(), reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
				throw new Exception("");
				
			}else if ( Utils.getLong(Utils.getTodayWithTime()) - Utils.getLong(v.getCreatedAt()) <  Constants.EXPIRED_PASSWORD_TIME ){
				
				
			}*/else{
				session.setFlashMessage(req.getSession(), reg.getMessage("USR0008", session.getLanguage(req.getSession())));
				throw new Exception("");
			}
			
//			ModelAndView mv = new ModelAndView("opp_reset");
//			mv.addObject("validation_id", v.getId());
//			mv.addObject("user_id", username);
//			return mv;
			
		}catch (Exception e){ 
			log.error("OpportunitiesController", "reset", e.toString());
			
		}finally{
			try{
				//if (conn!=null)
					//conn.close();
			}catch (Exception f){}
		}
		
		return new ModelAndView("redirect:/opportunities/login.html");
	}

	/**
	 * Save password.
	 *
	 * @param username
	 * @param password
	 * @param req
	 * @param res
	 * @return view
	 */
	@RequestMapping("savePassword.html")
	public ModelAndView savePassword(
			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "password", required = false) String password,
			HttpServletRequest req, HttpServletResponse res){

		username = Utils.checkNull(username);
		password = Utils.checkNull(password);

		Connection conn = DSManager.getConnection();

		JSONObject result = new JSONObject();
		result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_FAILED);
		result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0002", session.getLanguage(req.getSession())));

		try{
			if (username.length()==0 || password.length()==0){
				session.setFlashMessage(req.getSession(), reg.getMessage("USR0004", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("USR0004"));
			}
			
			conn.setAutoCommit(false);

			OppUserDAO dao = new OppUserDAO();
			UserInfo user = dao.getUserInfo(username);
			if (user==null){
				result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}
			
			password = DigestUtils.md5Hex(password + Constants.PASSWORD_HASH);

			//dao.update(username, password);
			conn.commit();

			result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_SUCCESS);
			result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0001", session.getLanguage(req.getSession())));
	        
			session.setFlashMessage(req.getSession(), reg.getMessage("ACT0001", session.getLanguage(req.getSession())));
			return new ModelAndView("redirect:/opportunities/login.html");
			
		}catch (Exception e){ 
			log.error("OpportunitiesController", "savePassword", e.toString());
			
		}finally{
			try{
			//	if (conn!=null)
				//	conn.close();
			}catch (Exception f){}
		}

		ModelAndView mv = new ModelAndView("opp_reset");
		mv.addObject("user_id", username);
		return mv;
	}
	
	
	
	/**
	 * Publish page.
	 * 
	 * @param type
	 * @param kind
	 * @param publish_id
	 * @param req
	 * @param res
	 * @return view
	 */
	@RequestMapping("publish.html")
	public ModelAndView publish(
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "kind", required = false) String kind,
			@RequestParam(value = "publish_id", required = false) String publish_id,
			HttpServletRequest req, HttpServletResponse res){

		type = Utils.checkNull(type);
		kind = Utils.checkNull(kind);
		publish_id = Utils.checkNull(publish_id);
		
		Connection conn = DSManager.getConnection();

		try{
			UserInfo user = session.getOpportUserInfo(req.getSession());
			if (user==null){
				throw new Exception(reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
			}

			if (user.getUserId() == 0){
			    throw new Exception(reg.getMessage("ACT0003"));
			   }
			
			if (!type.equals("add") && !type.equals("edit")){
				throw new Exception("");
			}

			if (!kind.equals("001") && !kind.equals("002") && !kind.equals("003")){
				throw new Exception("");
			}
			
			if (type.equals("edit") && publish_id.length()==0){
				throw new Exception("");
			}
			
			UtilityDAO codeDao = new UtilityDAO();
			
			ModelAndView mv = new ModelAndView("opp_publish");
			mv.addObject("type", type);
			mv.addObject("kind", kind);
			mv.addObject("publish_id", publish_id);
			
			mv.addObject("property_list", codeDao.getCodeListO("opp1", session.getLanguage(req.getSession())));
			mv.addObject("several_list", codeDao.getCodeListO("opp2", session.getLanguage(req.getSession())));
			mv.addObject("transmission_list", codeDao.getCodeListO("opp3", session.getLanguage(req.getSession())));
			mv.addObject("varios_list", codeDao.getCodeListO("opp5", session.getLanguage(req.getSession())));
			
			OpportDAO dao = new OpportDAO();
			mv.addObject("brand_list", dao.getBrandList(session.getLanguage(req.getSession()), false));
			mv.addObject("state_list", dao.getStateList(session.getLanguage(req.getSession()), false));
			
			mv.addObject("brand_id", "0");
			mv.addObject("state_id", "0");
			mv.addObject("city_id", "");
			mv.addObject("model", "");
			mv.addObject("price", "");
			mv.addObject("today", "");
			mv.addObject("mileage", "");
			mv.addObject("transmission", "");
			mv.addObject("employee_num", "");
			mv.addObject("public_date", "");
			mv.addObject("obs", "");
			mv.addObject("color", "");
			mv.addObject("doors", "");
			mv.addObject("telephone", "");
			mv.addObject("mobilephone", "");
			mv.addObject("file", "");
			mv.addObject("created_at", "");
			mv.addObject("updated_at", "");
			mv.addObject("status", "");
			
			mv.addObject("serve_type", "");
			mv.addObject("property", "");
			mv.addObject("plants", "");
			mv.addObject("rooms", "");
			mv.addObject("amueblado", "");
			
			mv.addObject("varios", "");
			
			if (type.equals("edit")){
				OpportunitiesInfo opport = dao.getOpportunities(Utils.getLong(publish_id));
				if (opport!=null){
					mv.addObject("brand_id", opport.getBrandID());
					mv.addObject("state_id", opport.getStateID());
					mv.addObject("city_id", opport.getCityID());
					mv.addObject("model", opport.getModel());
					mv.addObject("price", opport.getPrice());
					mv.addObject("today", opport.getToday());
					mv.addObject("mileage", opport.getMileage());
					mv.addObject("transmission", opport.getTransmission());
					mv.addObject("employee_num", opport.getEmployeeNum());
					mv.addObject("public_date", opport.getPublicDate());
					mv.addObject("obs", opport.getObs());
					mv.addObject("color", opport.getColor());
					mv.addObject("doors", opport.getDoors());
					mv.addObject("telephone", opport.getTelephone());
					mv.addObject("mobilephone", opport.getMobilephone());
					mv.addObject("file", opport.getFile());
					mv.addObject("created_at", opport.getCreatedAt());
					mv.addObject("updated_at", opport.getUpdatedAt());
					mv.addObject("status", opport.getStatus());
					
					mv.addObject("serve_type", opport.getServeType());
					mv.addObject("property", opport.getProperty());
					mv.addObject("plants", opport.getPlants());
					mv.addObject("rooms", opport.getRooms());
					mv.addObject("amueblado", opport.getAmueblado());
					
					mv.addObject("varios", opport.getVarios());
				}
			}
			
			return mv;
			
		}catch (Exception e){ 
			log.error("OpportunitiesController", "publish", e.toString());

		}finally{
			try{
		//		if (conn!=null)
		//			conn.close();
			}catch (Exception f){}
		}

		return new ModelAndView("redirect:/opportunities/home.html");
//		return new ModelAndView("redirect:/opportunities/login.html");
	}	
	
	/**
	 * Get city list 
	 * 
	 * @param state_id
	 * @param req
	 * @param res
	 */
	@RequestMapping("getCityList.html")
	public void getCityList(
			@RequestParam(value = "state_id", required = false) String state_id,
			HttpServletRequest req, HttpServletResponse res){

		JSONObject result = new JSONObject();
		
		result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_FAILED);
		result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0002", session.getLanguage(req.getSession())));
		
		state_id = Utils.checkNull(state_id);

		Connection conn = DSManager.getConnection();

		try{
			UserInfo user = session.getOpportUserInfo(req.getSession());
			if (user==null){
				result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}

			OpportDAO dao = new OpportDAO();
			
			Long stateID = Utils.getLong(state_id);
			
			result = setResponse(result, "list", dao.getCityListOfJSON(stateID, session.getLanguage(req.getSession())));
			
			result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_SUCCESS);
			result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0001", session.getLanguage(req.getSession())));
			
		}catch (Exception e){ 
			log.error("OpportunitiesController", "getCityList", e.toString());
			
		}finally{
			try{
		//		if (conn!=null)
		//			conn.close();
			}catch (Exception f){}
		}

		response(res, result);
	}	
	
	/**
	 * Update Pubish. 
	 * 		add new, update or delete existing one.
	 * 
	 */
	@RequestMapping("updatePublish.html")
	public void updatePublish(
			@RequestParam(value = "brand", required = false) String brand,
			@RequestParam(value = "model", required = false) String model,			
			@RequestParam(value = "price", required = false) String price,			
			@RequestParam(value = "state", required = false) String state,			
			@RequestParam(value = "city", required = false) String city,			
			@RequestParam(value = "today", required = false) String today,			
			@RequestParam(value = "transmission", required = false) String transmission,			
			@RequestParam(value = "mileage", required = false) String mileage,			
			@RequestParam(value = "public_date", required = false) String public_date,			
			//@RequestParam(value = "employee", required = false)  employee,			
			@RequestParam(value = "color", required = false) String color,			
			@RequestParam(value = "door", required = false) String door,			
			@RequestParam(value = "obs", required = false) String obs,			
			@RequestParam(value = "telephone", required = false) String telephone,			
			@RequestParam(value = "mobilephone", required = false) String mobilephone,
			@RequestParam(value = "file", required = false) String file,

			@RequestParam(value = "serve_type", required = false) String serve_type,
			@RequestParam(value = "property", required = false) String property,
			@RequestParam(value = "plants", required = false) String plants,
			@RequestParam(value = "rooms", required = false) String rooms,
			@RequestParam(value = "amueblado", required = false) String amueblado,
			
			@RequestParam(value = "varios", required = false) String varios,
			
			@RequestParam(value = "publish_type", required = false) String publish_type,
			@RequestParam(value = "publish_kind", required = false) String publish_kind,
			@RequestParam(value = "publish_id", required = false) String publish_id,
			HttpServletRequest req, HttpServletResponse res){

		JSONObject result = new JSONObject();
		
		result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_FAILED);
		result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0002", session.getLanguage(req.getSession())));
		
		publish_type = Utils.checkNull(publish_type);
		publish_kind = Utils.checkNull(publish_kind);
		publish_id = Utils.checkNull(publish_id);
		
		brand = Utils.checkNull(brand);
		price = Utils.checkNull(price);
		state = Utils.checkNull(state);
		city = Utils.checkNull(city);
		today = Utils.checkNull(today);
		transmission = Utils.checkNull(transmission);
		mileage = Utils.checkNull(mileage);
		public_date = Utils.checkNull(public_date);
		//employee = Utils.checkNull(employee);
		door = Utils.checkNull(door);
		telephone = Utils.checkNull(telephone);
		mobilephone = Utils.checkNull(mobilephone);
		file = Utils.checkNull(file);

		model = Utils.encode(Utils.checkNull(model));
		color = Utils.encode(Utils.checkNull(color));
		obs = Utils.encode(Utils.checkNull(obs));
		
		serve_type = Utils.encode(Utils.checkNull(serve_type));
		property = Utils.encode(Utils.checkNull(property));
		plants = Utils.encode(Utils.checkNull(plants));
		rooms = Utils.encode(Utils.checkNull(rooms));
		amueblado = Utils.encode(Utils.checkNull(amueblado));
		
		varios = Utils.encode(Utils.checkNull(varios));
		public_date = Utils.getToday("-");
		
		if (brand.length()==0)
			brand = "0";
		if (state.length()==0)
			state = "0";
		if (city.length()==0)
			city = "0";
		
		Connection conn = DSManager.getConnection();

		try{
			UserInfo user = session.getOpportUserInfo(req.getSession());
			if (user==null){
				result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}
			
			if (user.getUserId()==0){
				result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}
	
			if (!publish_type.equals("add") && !publish_type.equals("edit") && !publish_type.equals("delete")){
				throw new Exception("");
			}

			if (!publish_kind.equals("001") && !publish_kind.equals("002") && !publish_kind.equals("003")){
				throw new Exception("");
			}
			
			if ((publish_type.equals("edit") || publish_type.equals("delete") ) && publish_id.length()==0){
				throw new Exception("");
			}
			
			//employee = user.getUserId();
			
			conn.setAutoCommit(false);
			
			OpportDAO dao = new OpportDAO();

			if (publish_type.equals("add")){
				if (publish_kind.equals("001")){
					dao.insertCar(Utils.getLong(brand), Utils.getLong(state), Utils.getLong(city), model, price, today, mileage, transmission, "", public_date, obs, color, door, telephone, mobilephone, file, user.getUserId());
				}
				
				if (publish_kind.equals("002")){
					dao.insertFur(Utils.getLong(state), Utils.getLong(city), model, price, mileage, "", public_date, property, serve_type, amueblado, plants, rooms, telephone, mobilephone, file, user.getUserId());
				}
				
				if (publish_kind.equals("003")){
					dao.insertSrv(Utils.getLong(state), Utils.getLong(city), model, price, "", public_date, varios, telephone, mobilephone, file, user.getUserId());
				}
			}

			if (publish_type.equals("edit")){
				if (publish_kind.equals("001")){
					dao.updateCar(Utils.getLong(brand), Utils.getLong(state), Utils.getLong(city), model, price, today, mileage, transmission, "", public_date, obs, color, door, telephone, mobilephone, file, Utils.getLong(publish_id));
				}
				
				if (publish_kind.equals("002")){
					dao.updateFur(Utils.getLong(state), Utils.getLong(city), model, price, mileage, "", public_date, property, serve_type, amueblado, plants, rooms, telephone, mobilephone, file, Utils.getLong(publish_id));
				}
				
				if (publish_kind.equals("003")){
					dao.updateSrv(Utils.getLong(state), Utils.getLong(city), model, price, "", public_date, varios, telephone, mobilephone, file, Utils.getLong(publish_id));
				}
			}
			
			if (publish_type.equals("delete")){
				dao.delete(Utils.getLong(publish_id));
			}
			
			conn.commit();
			
			result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_SUCCESS);
			result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0001", session.getLanguage(req.getSession())));
			
		}catch (Exception e){ 
			log.error("OpportunitiesController", "updatePublish", e.toString());
			
			try{
				conn.rollback();
			}catch (Exception ex){}
		}finally{
			try{
				//if (conn!=null)
				//	conn.close();
			}catch (Exception f){}
		}

		response(res, result);
	}	

	/**
	 * Load opportunities data with DataTable.
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("loadOpportunities.html")
	public void loadOpportunities(
			HttpServletRequest request, HttpServletResponse response){

	    String[] cols = { "c.price", "c.mileage", "c.transmission", "c.updated_at" };
	    String table = "bbva_opportunities";
	     
	    JSONObject result = new JSONObject();
	    int amount = 10;
	    int start = 0;
	    int echo = 0;
	    int col = 3;
	    
	    String kind = Utils.checkNull(request.getParameter("kind"));
	    
	    String dir = "desc";
	 
	    String sStart = Utils.checkNull(request.getParameter("start"));
	    String sAmount = Utils.checkNull(request.getParameter("length"));
//	    String sEcho = Utils.checkNull(request.getParameter("sEcho"));
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
	    
//	    if (sCol .length()>0 ) {
//	        col = Integer.parseInt(sCol);
//	        if (col < 1 || col > 3)
//	            col = 3;
//	    }
	    
//	    if (sdir .length()>0 ) {
//	        if (!sdir.equals("desc"))
//	            dir = "asc";
//	    }
	    
	    String colName = cols[col];
	    Long total = (long)0;
	    Long totalAfterFilter = (long)0;
	    
	    Connection conn = DSManager.getConnection();

		try{
			UserInfo user = session.getOpportUserInfo(request.getSession());
			if (user==null){
				result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(request.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}
			
			if (user.getUserId() == 0){
			    result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(request.getSession())));
			    throw new Exception(reg.getMessage("ACT0003"));
			   }
			
			OpportDAO dao = new OpportDAO();
			
			conn.setAutoCommit(false);
			dao.updateVisible();
			conn.commit();
			
			String sql = " select count(*) from " + table + " where type='"+kind+"' and user_id='" + user.getUserId() + "' and visible='1' ";
			total = dao.getCount(sql);
			totalAfterFilter = total;
			
			sql = " select c.*, d.value, d.value_en, d.value_me, e.value as property_name, e.value_en as property_name_en, e.value_me as property_name_me, "
					+ " f.value as varios_name, f.value_en as varios_name_en, f.value_me as varios_name_me "
//					+ " , br.name as brand_name, st.name as state_name, ct.name as city_name "
					+ " from " + table + " c, bbva_code d, bbva_code e, bbva_code f "
//							+ " , bbva_brand br, bbva_state st, bbva_state_city ct "
							+ " where "//ct.id=c.city_id and st.id=c.state_id and br.id=c.brand_id and "
							+ " d.div='opp3' and d.code=c.transmission and c.type='"+kind+"'  and c.user_id='" + user.getUserId() + "' and e.div='opp1' and e.code=c.property and f.div='opp5' and f.code=c.varios and c.visible='1' ";
			
			String searchSQL = "";
			String globeSearch =  " ( c.price like '%"+searchTerm+"%' "
                    + " or c.mileage like '%"+searchTerm+"%'"
                    + " or c.model like '%"+searchTerm+"%'"
                    + " or c.employee_num like '%"+searchTerm+"%'"
                    + " or c.today like '%"+searchTerm+"%'"
                    + " or c.public_date like '%"+searchTerm+"%'"
                    + " or c.obs like '%"+searchTerm+"%'"
                    + " or c.color like '%"+searchTerm+"%'"
                    + " or c.doors like '%"+searchTerm+"%'"
                    + " or c.telephone like '%"+searchTerm+"%'"
                    + " or c.mobilephone like '%"+searchTerm+"%'"
                    + " or c.user_id like '%"+searchTerm+"%'"
                    
//                    + " or st.name like '%"+searchTerm+"%'"
//                    + " or ct.name like '%"+searchTerm+"%'"
                    
                    + " or c.plants like '%"+searchTerm+"%'"
                    + " or c.rooms like '%"+searchTerm+"%'"
                    + " or c.serve_type like '%"+searchTerm+"%'"
                    + " or c.amueblado like '%"+searchTerm+"%'"
                    
                    + " or d.value like '%"+searchTerm+"%'"
                    + " or d.value_en like '%"+searchTerm+"%'"
                    + " or d.value_me like '%"+searchTerm+"%'"
                    
                    + " or e.value like '%"+searchTerm+"%'"
                    + " or e.value_en like '%"+searchTerm+"%'"
                    + " or e.value_me like '%"+searchTerm+"%'"
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
	        
//	        result.put("ssss", sql);
			
	        JSONArray array = dao.getContent(sql, session.getLanguage(request.getSession()));
	        
//			sql = " select count(*) from " + table + " c, bbva_code d, bbva_code e, bbva_code f "
//					+ " , bbva_brand br, bbva_state st, bbva_state_city ct "
//					+ " where ct.id=c.city_id and st.id=c.state_id and br.id=c.brand_id and "
//					+ " d.div='opp3' and d.code=c.transmission and c.type='"+kind+"'  and c.user_id='" + user.getId() + "' and e.div='opp1' and e.code=c.property and f.div='opp5' and f.code=c.varios ";
//			if (searchTerm!=""){
//				sql += searchSQL;
//				totalAfterFilter = dao.getCount(sql);
//			}

			result.put("recordsTotal", total);
	        result.put("recordsFiltered", totalAfterFilter);
	        result.put("data", array);
			
		}catch (Exception e){ 
			log.error("OpportunitiesController", "loadOpportunities", e.toString());

		}finally{
			try{
				//if (conn!=null)
				//	conn.close();
			}catch (Exception f){}
		}
		
		try{
			response.setContentType("application/json");
	        response.setHeader("Cache-Control", "no-store");
	        response.getWriter().print(result);			
		}catch (Exception ee){}
	}

	/**
	 * Add Publish page.
	 * 
	 * @param req
	 * @param res
	 * @return view
	 */
	@RequestMapping("add.html")
	public ModelAndView add(
			HttpServletRequest req, HttpServletResponse res){

		Connection conn = DSManager.getConnection();

		try{
			UserInfo user = session.getOpportUserInfo(req.getSession());
			if (user==null){
				throw new Exception(reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
			}

			if (user.getUserId()==0){
				throw new Exception(reg.getMessage("ACT0003"));
			}
			
			ModelAndView mv = new ModelAndView("opp_add");
			return mv;
			
		}catch (Exception e){ 
			log.error("OpportunitiesController", "add", e.toString());

		}finally{
			try{
			//	if (conn!=null)
			//		conn.close();
			}catch (Exception f){}
		}

		return new ModelAndView("redirect:/opportunities/home.html");
//		return new ModelAndView("redirect:/opportunities/login.html");
	}	

	/**
	 * form automovil page.
	 * 
	 * @param req
	 * @param res
	 * @return view
	 */
	@RequestMapping("form_automovil.html")
	public ModelAndView form_automovil(
			HttpServletRequest req, HttpServletResponse res){

		Connection conn = DSManager.getConnection();

		try{
			UserInfo user = session.getOpportUserInfo(req.getSession());
			if (user==null){
				throw new Exception(reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
			}
			
			ModelAndView mv = new ModelAndView("opp_form_automovil");
			UtilityDAO codeDao = new UtilityDAO();
			mv.addObject("property_list", codeDao.getCodeListO("opp1", session.getLanguage(req.getSession())));
			mv.addObject("several_list", codeDao.getCodeListO("opp2", session.getLanguage(req.getSession())));
			mv.addObject("transmission_list", codeDao.getCodeListO("opp3", session.getLanguage(req.getSession())));
			mv.addObject("varios_list", codeDao.getCodeListO("opp5", session.getLanguage(req.getSession())));
			
			OpportDAO dao = new OpportDAO();
			mv.addObject("brand_list", dao.getBrandList(session.getLanguage(req.getSession()), false));
			mv.addObject("state_list", dao.getStateList(session.getLanguage(req.getSession()), false));
			
			return mv;
		}catch (Exception e){ 
			log.error("OpportunitiesController", "form_automovil", e.toString());

		}finally{
			try{
				//if (conn!=null)
				//	conn.close();
			}catch (Exception f){}
		}

		return new ModelAndView("redirect:/opportunities/home.html");
	}

	/**
	 * form inmueble page.
	 * 
	 * @param req
	 * @param res
	 * @return view
	 */
	@RequestMapping("form_inmueble.html")
	public ModelAndView form_inmueble(
			HttpServletRequest req, HttpServletResponse res){

		Connection conn = DSManager.getConnection();

		try{
			UserInfo user = session.getOpportUserInfo(req.getSession());
			if (user==null){
				throw new Exception(reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
			}
			
			ModelAndView mv = new ModelAndView("opp_form_inmueble");
			UtilityDAO codeDao = new UtilityDAO();
			mv.addObject("property_list", codeDao.getCodeListO("opp1", session.getLanguage(req.getSession())));
			mv.addObject("several_list", codeDao.getCodeListO("opp2", session.getLanguage(req.getSession())));
			mv.addObject("transmission_list", codeDao.getCodeListO("opp3", session.getLanguage(req.getSession())));
			mv.addObject("varios_list", codeDao.getCodeListO("opp5", session.getLanguage(req.getSession())));
			
			OpportDAO dao = new OpportDAO();
			mv.addObject("brand_list", dao.getBrandList(session.getLanguage(req.getSession()), false));
			mv.addObject("state_list", dao.getStateList(session.getLanguage(req.getSession()), false));
			
			return mv;
		}catch (Exception e){ 
			log.error("OpportunitiesController", "form_inmueble", e.toString());

		}finally{
			try{
			//	if (conn!=null)
			//		conn.close();
			}catch (Exception f){}
		}

		return new ModelAndView("redirect:/opportunities/home.html");
	}
	
	/**
	 * form varios page.
	 * 
	 * @param req
	 * @param res
	 * @return view
	 */
	@RequestMapping("form_varios.html")
	public ModelAndView form_varios(
			HttpServletRequest req, HttpServletResponse res){

		Connection conn = DSManager.getConnection();

		try{
			UserInfo user = session.getOpportUserInfo(req.getSession());
			if (user==null){
				throw new Exception(reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
			}
			
			ModelAndView mv = new ModelAndView("opp_form_varios");
			UtilityDAO codeDao = new UtilityDAO();
			mv.addObject("property_list", codeDao.getCodeListO("opp1", session.getLanguage(req.getSession())));
			mv.addObject("several_list", codeDao.getCodeListO("opp2", session.getLanguage(req.getSession())));
			mv.addObject("transmission_list", codeDao.getCodeListO("opp3", session.getLanguage(req.getSession())));
			mv.addObject("varios_list", codeDao.getCodeListO("opp5", session.getLanguage(req.getSession())));
			
			OpportDAO dao = new OpportDAO();
			mv.addObject("brand_list", dao.getBrandList(session.getLanguage(req.getSession()), false));
			mv.addObject("state_list", dao.getStateList(session.getLanguage(req.getSession()), false));
			
			return mv;
		}catch (Exception e){ 
			log.error("OpportunitiesController", "form_varios", e.toString());

		}finally{
			try{
			//	if (conn!=null)
			//		conn.close();
			}catch (Exception f){}
		}

		return new ModelAndView("redirect:/opportunities/home.html");
	}	

	@RequestMapping("inmueble_a_membresia.html")
	public ModelAndView inmueble_a_membresia(
			HttpServletRequest req, HttpServletResponse res){

		Connection conn = DSManager.getConnection();

		try{
			UserInfo user = session.getOpportUserInfo(req.getSession());
			if (user==null){
				throw new Exception(reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
			}
			
			ModelAndView mv = new ModelAndView("opp_inmueble_a_membresia");
			return mv;
		}catch (Exception e){ 
			log.error("OpportunitiesController", "inmueble_a_membresia", e.toString());

		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}

		return new ModelAndView("redirect:/opportunities/home.html");
	}
	
	@RequestMapping("clientes_referidos.html")
	public ModelAndView clientes_referidos(
			HttpServletRequest req, HttpServletResponse res){

		Connection conn = DSManager.getConnection();

		try{
			UserInfo user = session.getOpportUserInfo(req.getSession());
			if (user==null){
				throw new Exception(reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
			}
			
			ModelAndView mv = new ModelAndView("opp_clientes_referidos");
			return mv;
		}catch (Exception e){ 
			log.error("OpportunitiesController", "clientes_referidos", e.toString());

		}finally{
			try{
			//	if (conn!=null)
			//		conn.close();
			}catch (Exception f){}
		}

		return new ModelAndView("redirect:/opportunities/home.html");
	}
	
	@RequestMapping("install.html")
	public ModelAndView install(
			@RequestParam(value = "key", required = false) String key,
			HttpServletRequest req, HttpServletResponse res){

		key = Utils.checkNull(key);
		
		Connection conn = DSManager.getConnection();

		ModelAndView mv = new ModelAndView("install");
		try{
			OpportDAO dao = new OpportDAO();
			conn.setAutoCommit(false);
			
			if (key.equals("BBVAOPPORTUNITIESPROJECTSYSTEMINSTALLABJOVM34321POKNV21872UIPOM56")){
				
				dao.execute(" DROP TABLE IF EXISTS `bbva_brand`; ");
				dao.execute(" CREATE TABLE `bbva_brand` ( `id` int(11) NOT NULL AUTO_INCREMENT, `name` varchar(100) COLLATE utf8_unicode_ci NOT NULL, `status` char(1) COLLATE utf8_unicode_ci DEFAULT '0', PRIMARY KEY (`id`) ) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci; ");
				dao.execute(" insert  into `bbva_brand`(`id`,`name`,`status`) values (5,'Acura','0'),(6,'Aston Martin','0'),(7,'Audi','0'),(8,'Bentley','0'),(9,'BMW','0'),(10,'Buick','0'),(11,'Cadillac','0'),(12,'Chevrolet','0'),(13,'Chevrolet Truck','0'),(14,'Chrysler','0'),(15,'Dodge','0'),(16,'Dodge Truck','0'),(17,'Ferrari','0'),(18,'Ford','0'),(19,'Ford Truck','0'),(20,'GMC','0'),(21,'GMC Truck','0'),(22,'Honda','0'),(23,'Hummer','0'),(24,'Hyundai','0'),(25,'Infiniti','0'),(26,'Isuzu','0'),(27,'Jaguar','0'),(28,'Jeep','0'),(29,'Kia','0'),(30,'Lamborghini','0'),(31,'Land Rover','0'),(32,'Lexus','0'),(33,'Lincoln','0'),(34,'Lotus','0'),(35,'Maserati','0'),(36,'Maybach','0'),(37,'Mazda','0'),(38,'Mazda Truck','0'),(39,'Mercedes-Benz','0'),(40,'Mercury','0'),(41,'Mini','0'),(42,'Mitsubishi','0'),(44,'Nissan','0'),(45,'Nissan Truck','0'),(47,'Pontiac','0'),(49,'Porsche','0'),(50,'Rolls-Royce','0'),(51,'Saab','0'),(53,'Saturn','0'),(55,'Subaru','0'),(56,'Suzuki','0'),(58,'Toyota','0'),(59,'Toyota Truck','0'),(60,'Volkswagen','0'),(61,'Volvo','0'),(62,' Otra','0'),(63,'Alfa Romeo','0'),(64,'Citroen','0'),(65,'Fiat','0'),(66,'Seat','0'),(67,'Renault','0'),(68,'Rover','0'),(69,'Peugeot','0'),(70,'MG','0'); ");
				
				dao.execute(" DROP TABLE IF EXISTS `bbva_code`; ");
				dao.execute(" CREATE TABLE `bbva_code` ( `id` int(11) NOT NULL AUTO_INCREMENT, `div` char(4) COLLATE utf8_unicode_ci NOT NULL, `code` char(3) COLLATE utf8_unicode_ci NOT NULL, `value` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL, `value_en` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL, `value_me` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL, PRIMARY KEY (`id`) ) ENGINE=InnoDB AUTO_INCREMENT=125 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci; ");
				dao.execute(" insert  into `bbva_code`(`id`,`div`,`code`,`value`,`value_en`,`value_me`) values (1,'usr1','001','administrator','administrator','administrator'),(2,'usr1','002','user','user','user'),(83,'opp1','001','CASA','CASA','CASA'),(84,'opp1','002','DEPARTAMENTO','DEPARTAMENTO','DEPARTAMENTO'),(85,'opp1','003','TERRENO','TERRENO','TERRENO'),(86,'opp1','004','CASA EN CONDOMINIO','CASA EN CONDOMINIO','CASA EN CONDOMINIO'),(87,'opp1','000','Property Type','Property Type','Property Type'),(88,'opp2','000','Several','Several','Several'),(89,'opp2','001','Animals and Pets','Animals and Pets','Animals and Pets'),(90,'opp2','002','Antiques','Antiques','Antiques'),(91,'opp2','003','Art, Books and Collections','Art, Books and Collections','Art, Books and Collections'),(92,'opp2','004','Sporting goods','Sporting goods','Sporting goods'),(93,'opp2','005','Household Items','Household Items','Household Items'),(94,'opp2','006','Computer','Computer','Computer'),(95,'opp2','007','Electronics and Telephony','Electronics and Telephony','Electronics and Telephony'),(96,'opp2','008','Jewellery','Jewellery','Jewellery'),(97,'opp2','009','Machines and Tools','Machines and Tools','Machines and Tools'),(98,'opp2','010','Furniture','Furniture','Furniture'),(99,'opp2','011','Video Games','Video Games','Video Games'),(100,'opp2','012','Others','Others','Others'),(101,'opp3','000','Car Transmission','Car Transmission','Car Transmission'),(103,'opp3','002','ESTANDAR','ESTANDAR','ESTANDAR'),(104,'opp3','003','AUTOMATIC','AUTOMATIC','AUTOMATIC'),(108,'opp4','000','Serve Type','Serve Type','Serve Type'),(110,'opp4','002','VENTA','VENTA','VENTA'),(111,'opp4','003','RENTA','RENTA','RENTA'),(112,'opp5','000','Varios Type','Varios Type','Varios Type'),(113,'opp5','001','Animales y Mascotas','Animales y Mascotas','Animales y Mascotas'),(114,'opp5','002','Antigüedades','Antigüedades','Antigüedades'),(115,'opp5','003','Arte, Libros y Colecciones','Arte, Libros y Colecciones','Arte, Libros y Colecciones'),(116,'opp5','004','Artículos Deportivos','Artículos Deportivos','Artículos Deportivos'),(117,'opp5','005','Artículos para el Hogar','Artículos para el Hogar','Artículos para el Hogar'),(118,'opp5','006','Cómputo','Cómputo','Cómputo'),(119,'opp5','007','Electrónica y Telefonía','Electrónica y Telefonía','Electrónica y Telefonía'),(120,'opp5','008','Joyeria','Joyeria','Joyeria'),(121,'opp5','009','Máquinas y Herramientas ','Máquinas y Herramientas ','Máquinas y Herramientas '),(122,'opp5','010','Muebles','Muebles','Muebles'),(123,'opp5','011','Video Juegos','Video Juegos','Video Juegos'),(124,'opp5','012','Otros','Otros','Otros'); ");
				
				dao.execute(" DROP TABLE IF EXISTS `bbva_opportunities`; ");
				dao.execute(" CREATE TABLE `bbva_opportunities` ( `id` int(11) NOT NULL AUTO_INCREMENT, `type` char(3) COLLATE utf8_unicode_ci NOT NULL DEFAULT '000', `brand_id` int(11) DEFAULT '0', `state_id` int(11) DEFAULT '0', `city_id` int(11) DEFAULT '0', `model` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL, `price` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL, `today` char(4) COLLATE utf8_unicode_ci DEFAULT NULL, `mileage` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL, `transmission` char(3) COLLATE utf8_unicode_ci DEFAULT '000', `employee_num` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL, `public_date` char(10) COLLATE utf8_unicode_ci DEFAULT NULL, `obs` longtext COLLATE utf8_unicode_ci, `color` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL, `doors` varchar(2) COLLATE utf8_unicode_ci DEFAULT NULL, `telephone` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL, `mobilephone` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL, `file` longtext COLLATE utf8_unicode_ci, `created_at` char(14) COLLATE utf8_unicode_ci NOT NULL, `updated_at` char(14) COLLATE utf8_unicode_ci NOT NULL, `status` char(1) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0', `user_id` varchar(100) COLLATE utf8_unicode_ci NOT NULL, `serve_type` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL, `property` char(3) COLLATE utf8_unicode_ci DEFAULT '000', `plants` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL, `rooms` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL, `amueblado` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL, `varios` char(3) COLLATE utf8_unicode_ci DEFAULT '000', `visible` char(1) COLLATE utf8_unicode_ci DEFAULT '1', PRIMARY KEY (`id`) ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci; ");
				
				dao.execute(" DROP TABLE IF EXISTS `bbva_state`; ");
				dao.execute(" CREATE TABLE `bbva_state` ( `id` int(11) NOT NULL AUTO_INCREMENT, `name` varchar(100) COLLATE utf8_unicode_ci NOT NULL, PRIMARY KEY (`id`) ) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci; ");
				dao.execute(" insert  into `bbva_state`(`id`,`name`) values (1,'AGUASCALIENTES'),(2,'BAJA CALIFORNIA NORTE'),(3,'BAJA CALIFORNIA SUR'),(4,'CAMPECHE'),(5,'CHIAPAS'),(6,'CHIHUAHUA'),(7,'COAHUILA'),(8,'COLIMA'),(9,'DISTRITO FEDERAL'),(10,'DURANGO'),(11,'GUANAJUATO'),(12,'GUERRERO'),(13,'HIDALGO'),(14,'JALISCO'),(15,'MEXICO,  ESTADO'),(16,'MICHOACAN'),(17,'MORELOS'),(18,'NAYARIT'),(19,'NUEVO  LEON'),(20,'OAXACA'),(21,'PUEBLA'),(22,'QUERETARO'),(23,'QUINTANA ROO'),(24,'SAN LUIS POTOSI'),(25,'SINALOA'),(26,'SONORA'),(27,'TABASCO'),(28,'TAMAULIPAS'),(29,'TLAXCALA'),(30,'VERACRUZ'),(31,'YUCATAN '),(32,'ZACATECAS'),(33,'OTRA'); ");
				
				dao.execute(" DROP TABLE IF EXISTS `bbva_state_city`; ");
				dao.execute(" CREATE TABLE `bbva_state_city` ( `id` int(11) NOT NULL AUTO_INCREMENT, `state_id` int(11) NOT NULL, `name` varchar(100) COLLATE utf8_unicode_ci NOT NULL, PRIMARY KEY (`id`) ) ENGINE=InnoDB AUTO_INCREMENT=219 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci; ");
				dao.execute(" insert  into `bbva_state_city`(`id`,`state_id`,`name`) values (1,1,'AGUASCALIENTES'),(2,2,'MEXICALI'),(3,2,'ENSENADA'),(4,2,'TIJUANA'),(5,3,'CIUDAD  CONSTITUCION'),(6,3,'LA PAZ'),(7,3,'LORETO'),(8,3,'SAN JOSE DEL CABO  ( LOS CABOS )'),(9,3,'GUERRERO    NEGRO'),(10,4,'CAMPECHE'),(11,4,'CIUDAD DEL CARMEN'),(12,5,'SAN CRISTOBAL DE  LAS CASAS'),(13,5,'TAPACHULA'),(14,5,'TUXTLA GUTIERREZ'),(15,6,'CHIHUAHUA'),(16,6,'CD.JUAREZ'),(17,6,'CD. CUAUHTEMOC'),(18,7,'SALTILLO'),(19,7,'TORREON'),(20,7,'PIEDRAS NEGRAS'),(21,7,'MONCLOVA'),(22,7,'CD. ACUÃ?A'),(23,8,'COLIMA'),(24,8,'MANZANILLO'),(25,9,'MEXICO DF'),(26,9,'ALVARO OBREGON'),(27,9,'AZCAPOZALCO'),(28,9,'BENITO JUAREZ'),(29,9,'COYOACAN'),(30,9,'CUAJIMALPA'),(31,9,'CUAUHTEMOC'),(32,9,'GUSTAVO A MADERO'),(33,9,'IZTACALCO'),(34,9,'IZTAPALAPA'),(35,9,'MAGDALENA CONTRERAS'),(36,9,'MIGUEL HIDALGO'),(37,9,'MILPA ALTA'),(38,9,'TLAHUAC'),(39,9,'TLALPAN'),(40,9,'VENUSTIANO CARRANZA'),(41,9,'XOCHIMILCO'),(42,10,'DURANGO'),(43,10,'GOMEZ PALACIO'),(44,11,'GUANAJUATO'),(45,11,'ACAMBARO'),(46,11,'CELAYA'),(47,11,'DOLORES HIDALGO'),(48,11,'IRAPUATO'),(49,11,'LEON'),(50,11,'SALAMANCA'),(51,11,'SAN MIGUEL ALLENDE'),(52,11,'CORTAZAR'),(53,11,'MOROLEON'),(54,11,'PUEBLO NUEVO'),(55,11,'SAN FRANCISCO DEL RINCON'),(56,12,'CHILPANCINGO'),(57,12,'ACAPULCO'),(58,12,'TAXCO'),(59,12,'APAXTLA'),(60,12,'IXTAPA ZIHUATANEJO'),(61,13,'PACHUCA'),(62,13,'TEPEJIC DEL RIO'),(63,13,'ACTOPAN'),(64,13,'TULA'),(65,13,'TULANCINGO'),(66,13,'SAHAGUN'),(67,14,'GUADALAJARA'),(68,14,'CD.GUZMAN'),(69,14,'CHAPALA'),(70,14,'ZAPOPAN'),(71,14,'LAGOS DE MORENO JALISCO'),(72,14,'PUERTO VALLARTA'),(73,14,'SAN JUAN DE LOS LAGOS'),(74,14,'TAMAZULA'),(75,14,'SAN JULIAN'),(76,14,'ZAPOTLANEJO'),(77,15,'CUAUHTEMOC'),(78,15,'TOLUCA'),(79,15,'AMECAMECA'),(80,15,'COACALCO DE BERRIZABAL'),(81,15,'ATIZAPAN DE ZARAGOZA'),(82,15,'ATLACOMULCO'),(83,15,'CD.NEZAHUALCOYOTL'),(84,15,'CUATITLAN'),(85,15,'CUATITLAN IZCALLI'),(86,15,'ECATEPEC'),(87,15,'HUIZQUILUCAN'),(88,15,'IXTAPAN DE LA SAL'),(89,15,'LOS REYES LA PAZ'),(90,15,'METEPEC'),(91,15,'NAUCALPAN'),(92,15,'SANTIAGO TIANGUISTENGO'),(93,15,'TEXCOCO'),(94,15,'TLANEPANTLA'),(95,15,'VALLE DE BRAVO'),(96,15,'ZUMPANGO'),(97,15,'TEPOTZOTLAN'),(98,15,'SAN VICENTE CHICOLOAPAN'),(99,15,'TENANCINGO'),(100,15,'TULTITLAN'),(101,15,'CHALCO'),(102,16,'MORELIA'),(103,16,'URUAPAN'),(104,16,'LA PIEDAD'),(105,16,'ZITACUARO'),(106,16,'LAZARO CARDENAS'),(107,16,'PATZCUARO'),(108,16,'ZAMORA'),(109,17,'CUERNAVACA'),(110,17,'CUAUTLA'),(111,17,'JIUTEPEC'),(112,17,'JOJUTLA'),(113,17,'YAUTEPEC'),(114,18,'TEPIC'),(115,19,'MONTERREY'),(116,19,'GUADALUPE'),(117,19,'SAN NICOLAS DE LOS GARZA'),(118,19,'APODACA'),(119,19,'CADEREYTA'),(120,19,'GARZA GARCIA'),(121,20,'HUATULCO'),(122,20,'OAXACA'),(123,20,'PUERTO   ESCONDIDO'),(124,21,'PUEBLA'),(125,21,'CHOLULA'),(126,21,'TEZIUTLAN'),(127,21,'TEHUACAN'),(128,22,'QUERETARO'),(129,22,'SAN JUAN DEL RIO'),(130,22,'TEQUISQUIAPAN'),(131,22,'LOS ANGELES'),(132,23,'CANCUN'),(133,23,'COZUMEL'),(134,23,'CHETUMAL'),(135,23,'PLAYA DEL CARMEN'),(136,24,'SAN LUIS POTOSI'),(137,24,'CD.VALLES'),(138,25,'CULIACAN'),(139,25,'GUAMUCHIL'),(140,25,'GUASAVE'),(141,25,'LOS MOCHIS'),(142,25,'MAZATLAN'),(143,25,'NAVOLATO'),(144,25,'ROSARIO'),(145,26,'CD. OBREGON'),(146,26,'GUAYMAS'),(147,26,'HERMOSILLO'),(148,26,'HUATABAMBO'),(149,26,'NOVOJOA'),(150,26,'NOGALES'),(151,26,'PTO.PEÃ?ASCO'),(152,27,'VILLAHERMOSA'),(153,28,'CD. MADERO'),(154,28,'CD. MANTE'),(155,28,'CD. MIGUEL ALEMAN'),(156,28,'CD. VICTORIA'),(157,28,'MATAMOROS'),(158,28,'NUEVO LAREDO'),(159,28,'REYNOSA'),(160,28,'TAMPICO'),(161,29,'TLAXCALA'),(162,29,'CHIAUTEMPAN'),(163,29,'SANTA MARIA TOCATLAN'),(164,29,'HUAMANTLA'),(165,29,'APIZACO'),(166,30,'ACAYUCAN'),(167,30,'BOCA DEL RIO'),(168,30,'COATEPEC'),(169,30,'COATZACOALCOS'),(170,30,'CORDOBA'),(171,30,'CUITLAHUAC'),(172,30,'MINATITLAN'),(173,30,'ORIZABA'),(174,30,'POZA RICA'),(175,30,'SAN ANDRES TUXTLA'),(176,30,'TUXPAN'),(177,30,'VERACRUZ'),(178,30,'XALAPA'),(179,30,'FORTIN DE LAS FLORES'),(180,30,'PROGRESO'),(181,31,'MERIDA'),(182,32,'ZACATECAS'),(183,32,'FRESNILLO'),(184,32,'JEREZ'),(186,17,'COCOYOC'),(187,1,'OTRA'),(188,2,'OTRA'),(189,3,'OTRA'),(190,4,'OTRA'),(191,5,'OTRA'),(192,6,'OTRA'),(193,7,'OTRA'),(194,8,'OTRA'),(195,9,'OTRA'),(196,10,'OTRA'),(197,11,'OTRA'),(198,12,'OTRA'),(199,13,'OTRA'),(200,14,'OTRA'),(201,15,'OTRA'),(202,16,'OTRA'),(203,17,'OTRA'),(204,18,'OTRA'),(205,19,'OTRA'),(206,20,'OTRA'),(207,21,'OTRA'),(208,22,'OTRA'),(209,23,'OTRA'),(210,24,'OTRA'),(211,25,'OTRA'),(212,26,'OTRA'),(213,27,'OTRA'),(214,28,'OTRA'),(215,29,'OTRA'),(216,30,'OTRA'),(217,31,'OTRA'),(218,32,'OTRA'); ");
				
				dao.execute(" DROP TABLE IF EXISTS `bbva_user_opport`; ");
				dao.execute(" CREATE TABLE `bbva_user_opport` ( `id` varchar(20) COLLATE utf8_unicode_ci NOT NULL, `level` char(3) COLLATE utf8_unicode_ci NOT NULL DEFAULT '002', `username` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL, `firstname` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL, `lastname` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL, `email` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL, `password` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL, `division` int(11) DEFAULT '0', `city` int(11) DEFAULT '0', `people_manager` char(3) COLLATE utf8_unicode_ci DEFAULT '002', `new_hire` char(3) COLLATE utf8_unicode_ci DEFAULT '002', `jobgrade` char(3) COLLATE utf8_unicode_ci DEFAULT NULL, `payowner` char(3) COLLATE utf8_unicode_ci DEFAULT NULL, `promote` char(3) COLLATE utf8_unicode_ci DEFAULT NULL, `parent_division` int(11) DEFAULT '0', `parent_city` int(11) DEFAULT '0', `active` char(1) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0', `created_at` char(14) COLLATE utf8_unicode_ci DEFAULT NULL, `updated_at` char(14) COLLATE utf8_unicode_ci DEFAULT NULL ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci; ");
				
				dao.execute(" DROP TABLE IF EXISTS `bbva_validation`; ");
				dao.execute(" CREATE TABLE `bbva_validation` ( `id` int(11) NOT NULL AUTO_INCREMENT, `user_id` varchar(100) COLLATE utf8_unicode_ci NOT NULL, `session_id` longtext COLLATE utf8_unicode_ci, `created_at` char(14) COLLATE utf8_unicode_ci NOT NULL, PRIMARY KEY (`id`) ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci; ");
				
				conn.commit();
				
				mv.addObject("message", "Successfully initialized! You can use this system!");
			} else {
				mv.addObject("message", "You haven't permission to install.");
			}
			
			
		}catch (Exception e){ 
			log.error("SystemController", "install", e.toString());
			
		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}
		
		return mv;
	}
	
}
