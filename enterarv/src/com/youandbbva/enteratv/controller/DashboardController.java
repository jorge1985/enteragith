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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.youandbbva.enteratv.Constants;
import com.youandbbva.enteratv.DSManager;
import com.youandbbva.enteratv.Utils;
import com.youandbbva.enteratv.dao.UserDAO;
import com.youandbbva.enteratv.dao.VisitorDAO;
import com.youandbbva.enteratv.domain.UserInfo;

/**
 * Handle all action for dashboard.
 * 		such as count of visitor, system user, channel, content. 
 *  
 * @author CJH
 *
 */

@Controller
@RequestMapping("/dashboard/")
@Component("DashboardController")
public class DashboardController extends com.youandbbva.enteratv.Controller{

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
	 * Dashboard Page.
	 * 
	 * @param req
	 * @param res
	 * @return view
	 */
	@RequestMapping("dashboard.html")
	public ModelAndView families(
		HttpServletRequest req, HttpServletResponse res){
		
		UserInfo user = session.getUserInfo(req.getSession());
		if (user==null){
			return new ModelAndView("redirect:/user/adios.html");
		}
		
		return new ModelAndView("dashboard");
	}
	
	/**
	 * Get Count of visitor, user, channel, content.
	 * 
	 * @param req
	 * @param res
	 */
	@RequestMapping("count.html")
	public void count(
			HttpServletRequest req, HttpServletResponse res){
		
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

			// Initialize all value.
			result.put("total_system_user", 0);
			result.put("active_system_user", 0);
			result.put("total_visitor_today", 0);
			result.put("unique_visitor_today", 0);
			result.put("total_visitor_month", 0);
			result.put("unique_visitor_month", 0);
			result.put("total_visitor_historic", 0);
			result.put("outside_visitor_historic", 0);
			result.put("channel", 0);
			result.put("content", 0);
			
			String today = Utils.getYear()+"-"+Utils.getMonth()+"-"+Utils.getDay();
			String month = Utils.getYear()+"-"+Utils.getMonth()+"-"+Utils.getDay();
			//dao connection
			UserDAO userDao = new UserDAO();
			Long total_system_user = userDao.getCount('b');
			Long active_system_user = userDao.getCount('a');
			// Initialize value.
			result.put("total_system_user", total_system_user.longValue());
			result.put("active_system_user", active_system_user.longValue());
			//dao connection
			VisitorDAO visitorDao = new VisitorDAO();
			
			Long total_visitor_today = visitorDao.getTodayCount(today);
			String total_visitor_month = visitorDao.getMonthCount(month);
			Long unique_visitor_today = visitorDao.getUniqueCountSystemUser(today) + visitorDao.getUniqueCountOutsideUser(today);
			Long unique_visitor_month = visitorDao.getUniqueCountSystemUser(month) + visitorDao.getUniqueCountOutsideUser(month);
			Long total_visitor_historic = visitorDao.getHistoricCount();
			Long outside_visitor_historic = visitorDao.getHistoricUniqueCount();
			Long channels = visitorDao.getChannelCount();			
			Long contents = visitorDao.getContentCount(Constants.DEFAULT_ACTIVE);
			
			// Initialize value.
			result.put("total_visitor_today",total_visitor_today.longValue());
			result.put("unique_visitor_today", unique_visitor_today.longValue());
			result.put("total_visitor_month",total_visitor_month);
			result.put("unique_visitor_month", unique_visitor_month.longValue());
			result.put("total_visitor_historic",total_visitor_historic.longValue());
			result.put("outside_visitor_historic",outside_visitor_historic.longValue());
			result.put("channel", channels.longValue());
			result.put("content", contents.longValue());
			
			result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_SUCCESS);
			result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0001", session.getLanguage(req.getSession())));
		}catch (Exception e){
			log.error("DashBoardController", "count", e.toString());
		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}
		
		response(res, result);
	}

}
