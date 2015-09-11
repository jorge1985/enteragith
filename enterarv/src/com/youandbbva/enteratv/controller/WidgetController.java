package com.youandbbva.enteratv.controller;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.youandbbva.enteratv.dao.MediaDAO;
import com.youandbbva.enteratv.dao.PublicDAO;
import com.youandbbva.enteratv.dao.SystemDAO;
import com.youandbbva.enteratv.dao.UtilityDAO;
import com.youandbbva.enteratv.dao.WidgetDAO;
import com.youandbbva.enteratv.domain.BannerInfo;
import com.youandbbva.enteratv.domain.UserInfo;

/**
 * Handle all action for Widgets.
 * 		such as banner, featured news. 
 *  
 * @author CJH
 *
 */

@Controller
@RequestMapping("/widget/")
@Component("WidgetController")
public class WidgetController extends com.youandbbva.enteratv.Controller{

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
	 * Banner page.
	 * 
	 * @param req
	 * @param res
	 * @return view
	 */
	@RequestMapping("banner.html")
	public ModelAndView banner(
			HttpServletRequest req, HttpServletResponse res){

		UserInfo user = session.getUserInfo(req.getSession());
		if (user==null){
			return new ModelAndView("redirect:/user/login.html");
		}
		
		Connection conn = DSManager.getConnection();

		ModelAndView mv = new ModelAndView("banner");
		
		try{
			UtilityDAO codeDao = new UtilityDAO(conn);
			mv.addObject("targetlist", codeDao.getCodeList("trgt", session.getLanguage(req.getSession())));
			
//			SystemDAO dao = new SystemDAO(conn);
//			mv.addObject("banner", new Integer(dao.getOptions(Constants.OPTION_BANNER).toString()));
			
		}catch (Exception e){ 
			log.error("WidgetController", "banner", e.toString());
			
		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}
		
		return mv;
	}

	/**
	 * Load all banner data.
	 * 
	 * @param req
	 * @param res
	 */
	@RequestMapping("loadAllBanner.html")
	public void loadAllBanner(
			HttpServletRequest req, HttpServletResponse res){

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
			
			WidgetDAO dao = new WidgetDAO(conn);
			
			result = setResponse(result, "banner", dao.getBanners());
			result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_SUCCESS);
			result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0001", session.getLanguage(req.getSession())));
			
		}catch (Exception e){ 
			log.error("WidgetController", "loadAllBanner", e.toString());
			
		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}

		response(res, result);
	}
	
	/**
	 * Get banner data.
	 * 
	 * @param banner
	 * @param sub_banner
	 * @param req
	 * @param res
	 */
	@RequestMapping("getBanner.html")
	public void getBanner(
			@RequestParam(value = "banner", required = false) String banner,
			@RequestParam(value = "sub_banner", required = false) String sub_banner,
			HttpServletRequest req, HttpServletResponse res){

		Connection conn = DSManager.getConnection();
		
		banner = Utils.checkNull(banner);
		sub_banner = Utils.checkNull(sub_banner);

		JSONObject result = new JSONObject();
		result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_FAILED);
		result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0002", session.getLanguage(req.getSession())));

		try{
			UserInfo user = session.getUserInfo(req.getSession());
			if (user==null){
				result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}
			
			WidgetDAO dao = new WidgetDAO(conn);
			
			result = setResponse(result, "banner", dao.getBanner(Utils.getLong(banner), Utils.getLong(sub_banner)));
			
			result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_SUCCESS);
			result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0001", session.getLanguage(req.getSession())));
			
		}catch (Exception e){ 
			log.error("WidgetController", "getBanner", e.toString());
			
		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}

		response(res, result);
	}

	/**
	 * Save banner data.
	 * 
	 * @param res
	 */
	@RequestMapping("saveBanner.html")
	public void saveBanner(
			HttpServletRequest req, HttpServletResponse res){

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
			
			conn.setAutoCommit(false);

			WidgetDAO widgetDao = new WidgetDAO(conn);
			SystemDAO systemDao = new SystemDAO(conn);
			PublicDAO publicDao = new PublicDAO(conn);
			MediaDAO mediaDao   = new MediaDAO(conn);
			
			long key;
			

			for (int i=0; i<19; i++){
				String image = Utils.checkNull(req.getParameter("data["+i+"][image]"));
				String target = Utils.checkNull(req.getParameter("data["+i+"][target]"));
				String url = Utils.checkNull(req.getParameter("data["+i+"][url]"));
				
					
					String resultado = image.substring(16);
					System.out.println(resultado);
					key=mediaDao.getMediaContent(resultado);
					System.out.println(key);
					System.out.println(image);
					if(image.equals("/resources/images/logo-bbva-tipo.svg"))
					{
						key=widgetDao.getMediaName("bancomer.png");
						
						

					}
				
					widgetDao.updateBanner((long)(i+1),/*image*/ key, target, url);
					conn.commit();
				
				
			}
			//conn.commit();
			
//			systemDao.update(Constants.OPTION_BANNER, banner);
//			conn.commit();
			
			systemDao.update(Constants.OPTION_BANNER_HOME_SIDEBAR_HTML, publicDao.generateBannerHTML_ForHomepageSidebar());
			systemDao.update(Constants.OPTION_BANNER_HOME_BOTTOM_HTML, publicDao.generateBannerHTML_ForHomepageBottom());
			systemDao.update(Constants.OPTION_BANNER_INTERNAL_SIDEBAR_HTML, publicDao.generateBannerHTML_ForInternalSidebar());
			systemDao.update(Constants.OPTION_BANNER_INTERNAL_BOTTOM_HTML, publicDao.generateBannerHTML_ForInternalBottom());
			conn.commit();
			
			result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_SUCCESS);
			result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0001", session.getLanguage(req.getSession())));
			
		}catch (Exception e){ 
			log.error("WidgetController", "saveBanner", e.toString());

			try{
				conn.rollback();
			}catch (Exception f){}
		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}

		response(res, result);
	}
	
	/**
	 * Featured_News page.
	 * 
	 * @param req
	 * @param res
	 * @return view
	 */
	@RequestMapping("features.html")
	public ModelAndView features(
			HttpServletRequest req, HttpServletResponse res){

		UserInfo user = session.getUserInfo(req.getSession());
		if (user==null){
			return new ModelAndView("redirect:/user/login.html");
		}
		
		ModelAndView mv = new ModelAndView("features");

		Connection conn = DSManager.getConnection();
		
		try{
			SystemDAO dao = new SystemDAO(conn);
			mv.addObject("features", new Integer(dao.getOptions(Constants.OPTION_FEATURES).toString()));
			
		}catch (Exception e){ 
			log.error("WidgetController", "features", e.toString());
			
		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}
		
		return mv;
	}
	
	/**
	 * Load all sub features data.
	 * 
	 * @param features
	 * @param req
	 * @param res
	 */
	@RequestMapping("loadAllFeatures.html")
	public void loadAllFeatures(
			@RequestParam(value = "features", required = false) String features,
			HttpServletRequest req, HttpServletResponse res){

		Connection conn = DSManager.getConnection();

		JSONObject result = new JSONObject();
		result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_FAILED);
		result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0002", session.getLanguage(req.getSession())));

		features = Utils.checkNull(features);

		try{
			UserInfo user = session.getUserInfo(req.getSession());
			if (user==null){
				result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}
			
			/*towainicio
			
			WidgetDAO dao = new WidgetDAO(conn);
			
			result = setResponse(result, "features", dao.getFeatures(Utils.getLong(features)));
			
			result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_SUCCESS);
			result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0001", session.getLanguage(req.getSession())));
			*///towafin
		}catch (Exception e){ 
			log.error("WidgetController", "loadAllFeatures", e.toString());
			
		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}

		response(res, result);
	}
	
	/**
	 * Get Features data.
	 * 
	 * @param features
	 * @param sub_features
	 * @param req
	 * @param res
	 */
	@RequestMapping("getFeatures.html")
	public void getFeatures(
			@RequestParam(value = "features", required = false) String features,
			@RequestParam(value = "sub_features", required = false) String sub_features,
			HttpServletRequest req, HttpServletResponse res){

		Connection conn = DSManager.getConnection();
		
		features = Utils.checkNull(features);
		sub_features = Utils.checkNull(sub_features);

		JSONObject result = new JSONObject();
		result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_FAILED);
		result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0002", session.getLanguage(req.getSession())));

		try{
			UserInfo user = session.getUserInfo(req.getSession());
			if (user==null){
				result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}
			
			/*towainicio
			
			WidgetDAO dao = new WidgetDAO(conn);
			
			result = setResponse(result, "features", dao.getFeatures(Utils.getLong(features), Utils.getLong(sub_features)));
			
			result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_SUCCESS);
			result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0001", session.getLanguage(req.getSession())));
			*///towafin
		}catch (Exception e){ 
			log.error("WidgetController", "getFeatures", e.toString());
			
		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}

		response(res, result);
	}
	
	/**
	 * Get Latest news.
	 * 
	 * @param req
	 * @param res
	 */
	@RequestMapping("getLatestNews.html")
	public void getLatestNews(
			HttpServletRequest req, HttpServletResponse res){

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
			
			conn.setAutoCommit(false);
			PublicDAO publicDao = new PublicDAO(conn);
			SystemDAO systemDao = new SystemDAO(conn);
			
//			publicDao.affectFeaturedNews();
//			systemDao.update(Constants.OPTION_FEATURES_HTML, publicDao.generateFeaturesHTML());
//			conn.commit();
			
			result = setResponse(result, "list", publicDao.getLatestNews());
			
			result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_SUCCESS);
			result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0001", session.getLanguage(req.getSession())));
			
		}catch (Exception e){ 
			log.error("WidgetController", "getLatestNews", e.toString());
			
			try{
				conn.rollback();
			}catch (Exception ff){}
		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}

		response(res, result);
	}
	
	/**
	 * Save Features data.
	 * 
	 * @param image
	 * @param title
	 * @param date
	 * @param content
	 * @param req
	 * @param res
	 */
	@RequestMapping("saveFeatures.html")
	public void saveFeatures(
			@RequestParam(value = "features", required = false) String features,
			@RequestParam(value = "sub_features", required = false) String sub_features,
			@RequestParam(value = "image", required = false) String image,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "date", required = false) String date,
			@RequestParam(value = "content", required = false) String content,
			@RequestParam(value = "content_id", required = false) String content_id,
			HttpServletRequest req, HttpServletResponse res){

		Connection conn = DSManager.getConnection();

		JSONObject result = new JSONObject();
		result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_FAILED);
		result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0002", session.getLanguage(req.getSession())));
		
		features = Utils.checkNull(features);
		sub_features = Utils.checkNull(sub_features);
		image = Utils.checkNull(image);
		title = Utils.encode(Utils.checkNull(title));
		date = Utils.checkNull(date);
		content = Utils.encode(Utils.checkNull(content));
		content_id = Utils.encode(Utils.checkNull(content_id));

		try{
			conn.setAutoCommit(false);
			
			WidgetDAO widgetDao = new WidgetDAO(conn);
			SystemDAO systemDao = new SystemDAO(conn);
			PublicDAO publicDao = new PublicDAO(conn);
			
			/*towa inicio
			widgetDao.updateFeatures(Utils.getLong(features), Utils.getLong(sub_features), image, title, date, content, Utils.getLong(content_id));
			systemDao.update(Constants.OPTION_FEATURES, features);
			conn.commit();
			
			systemDao.update(Constants.OPTION_FEATURES_HTML, publicDao.generateFeaturesHTML());
			conn.commit();
			
			result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_SUCCESS);
			result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0001", session.getLanguage(req.getSession())));
			*///towafin
		}catch (Exception e){ 
			log.error("WidgetController", "saveFeatures", e.toString());

			try{
				conn.rollback();
			}catch (Exception f){}
		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}

		response(res, result);
	}	

	/**
	 * Save template for banner and features.
	 * 
	 * @param kind
	 * @param template
	 * @param req
	 * @param res
	 */
	@RequestMapping("saveTemplate.html")
	public void saveTemplate(
			@RequestParam(value = "kind", required = false) String kind,
			@RequestParam(value = "template", required = false) String template,
			HttpServletRequest req, HttpServletResponse res){

		Connection conn = DSManager.getConnection();

		JSONObject result = new JSONObject();
		result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_FAILED);
		result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0002", session.getLanguage(req.getSession())));
		
		kind = Utils.checkNull(kind);
		template = Utils.checkNull(template);

		try{
			UserInfo user = session.getUserInfo(req.getSession());
			if (user==null){
				result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}
			
			conn.setAutoCommit(false);

			SystemDAO systemDao = new SystemDAO(conn);
			PublicDAO publicDao = new PublicDAO(conn);

			if (kind.equals("banner")){
				systemDao.update(Constants.OPTION_BANNER, template);
//				String html = publicDao.generateBannerHTML(Utils.getLong(template)) ;
//				systemDao.update(Constants.OPTION_BANNER_HTML, html);
			}
			
			/*towa inicio
			if (kind.equals("features")){
				systemDao.update(Constants.OPTION_FEATURES, template);
				conn.commit();
				
				systemDao.update(Constants.OPTION_FEATURES_HTML, publicDao.generateFeaturesHTML());
			}
			*///towafin
			
			conn.commit();

			result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_SUCCESS);
			result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0001", session.getLanguage(req.getSession())));
			
		}catch (Exception e){ 
			log.error("WidgetController", "saveTemplate", e.toString());

			try{
				conn.rollback();
			}catch (Exception f){}
		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}

		response(res, result);
	}
	
}