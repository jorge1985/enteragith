package com.youandbbva.enteratv.controller;

import java.sql.Connection;
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

import com.youandbbva.enteratv.DSManager;
import com.youandbbva.enteratv.Utils;
import com.youandbbva.enteratv.dao.VisitorDAO;
import com.youandbbva.enteratv.domain.UserInfo;

/**
 * Handle all action for System. such as add System Options, Search
 * 
 * @author CJH
 *
 */

@Controller
@RequestMapping("/log/")
@Component("LogController")
public class LogController extends com.youandbbva.enteratv.Controller {

	public static final String USER_LOGIN = "1";
	public static final String USER_ACTIVITY = "2";
	public static final String CHANNEL_ACTIVITY = "3";
	public static final String CONTENT_VIEW = "4";

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
	 * Access Log page.
	 * 
	 * @param req
	 * @param res
	 * @return view
	 */
	@RequestMapping("access.html")
	public ModelAndView access(HttpServletRequest req, HttpServletResponse res) {

		UserInfo user = session.getUserInfo(req.getSession());
		if (user == null) {
			return new ModelAndView("redirect:/user/login.html");
		}

		return new ModelAndView("access_log");
	}

	/**
	 * Channel Log page.
	 * 
	 * @param req
	 * @param res
	 * @return view
	 */
	@RequestMapping("channel.html")
	public ModelAndView channel(HttpServletRequest req, HttpServletResponse res) {

		UserInfo user = session.getUserInfo(req.getSession());
		if (user == null) {
			return new ModelAndView("redirect:/user/login.html");
		}

		return new ModelAndView("channel_log");
	}

	/**
	 * Content Log page.
	 * 
	 * @param req
	 * @param res
	 * @return view
	 */
	@RequestMapping("content.html")
	public ModelAndView content(HttpServletRequest req, HttpServletResponse res) {

		UserInfo user = session.getUserInfo(req.getSession());
		if (user == null) {
			return new ModelAndView("redirect:/user/login.html");
		}

		return new ModelAndView("content_log");
	}

	/**
	 * Advanced Log page.
	 * 
	 * @param req
	 * @param res
	 * @return view
	 */
	@RequestMapping("advanced.html")
	public ModelAndView advanced(HttpServletRequest req, HttpServletResponse res) {

		UserInfo user = session.getUserInfo(req.getSession());
		if (user == null) {
			return new ModelAndView("redirect:/user/login.html");
		}

		return new ModelAndView("advanced_log");
	}

	/**
	 * load AccessLog with DataTable.
	 * 
	 * @param start_day
	 * @param end_day
	 * @param request
	 * @param response
	 */
	@RequestMapping("loadAccessLog.html")
	public void loadAccessLog(
			@RequestParam(value = "start_day", required = false) String start_day,
			@RequestParam(value = "end_day", required = false) String end_day,
			HttpServletRequest request, HttpServletResponse response) {

		String[] cols = { "User_UserId", "VisitDate", "VisitIp" };
		String table = "visit";

		start_day = Utils.checkNull(start_day);
		end_day = Utils.checkNull(end_day);

		JSONObject result = new JSONObject();
		int amount = 10;
		int start = 0;
		int col = 2;

		String dir = "desc";

		String sStart = Utils.checkNull(request.getParameter("start"));
		String sAmount = Utils.checkNull(request.getParameter("length"));
		String sCol = Utils.checkNull(request.getParameter("iSortCol_0"));
		String sdir = Utils.checkNull(request.getParameter("sSortDir_0"));

		Map<String, String[]> parameters = request.getParameterMap();
		sdir = parameters.get("order[0][dir]")[0];
		sCol = parameters.get("order[0][column]")[0];

		String searchTerm = Utils.checkNull(request
				.getParameter("search[value]"));
		String individualSearch = "";

		if (sStart.length() > 0) {
			start = Integer.parseInt(sStart);
			if (start < 0)
				start = 0;
		}

		if (sAmount.length() > 0) {
			amount = Integer.parseInt(sAmount);
			if (amount < 10 || amount > 100)
				amount = 10;
		}

		if (sCol.length() > 0) {
			col = Integer.parseInt(sCol);
			if (col < 0 || col > 2)
				col = 2;
		}

		if (sdir.length() > 0) {
			if (!sdir.equals("desc"))
				dir = "asc";
		}

		String additionalSQL = "";
		if (start_day.length() > 0) {
			additionalSQL += " date>='" + start_day + "' ";
		}

		if (end_day.length() > 0) {
			if (additionalSQL.length() > 0)
				additionalSQL += " and ";
			additionalSQL += " date<='" + end_day + "' ";
		}

		if (additionalSQL.length() > 0)
			additionalSQL = " and ( " + additionalSQL + " ) ";

		String colName = cols[col];
		Long total = (long) 0;
		Long totalAfterFilter = (long) 0;

		Connection conn = DSManager.getConnection();

		try {

			VisitorDAO dao = new VisitorDAO(conn);
			String sql_count = "select COUNT(*) from user a INNER JOIN visit v ON a.UserId = v.User_UserId INNER JOIN content c ON c.ContentId = v.Content_ContentId INNER JOIN channel ch ON c.Channel_ChannelId = ch.ChannelId where CAST(v.VisitDate AS DATE) BETWEEN '"
					+ start_day + "' and '" + end_day + "'";
			String sql = "select a.UserId, a.UserName, a.UserFirstName, a.UserLastName, v.VisitDate, v.VisitIp, c.ContentName, ch.ChannelName from user a INNER JOIN visit v ON a.UserId = v.User_UserId INNER JOIN content c ON c.ContentId = v.Content_ContentId INNER JOIN channel ch ON c.Channel_ChannelId = ch.ChannelId where CAST(v.VisitDate AS DATE) BETWEEN '"
					+ start_day
					+ "' and '"
					+ end_day
					+ "' order by v.VisitDate "
					+ dir
					+ " LIMIT "
					+ start
					+ ","
					+ amount;
			total = dao.getCount(sql_count);
			totalAfterFilter = total;

			JSONArray array = dao.getContent(sql,
					session.getLanguage(request.getSession()));

			result.put("recordsTotal", total);
			result.put("recordsFiltered", totalAfterFilter);
			result.put("data", array);

		} catch (Exception e) {
			log.error("LogController", "loadAccessLog", e.toString());

		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception f) {
			}
		}

		try {
			response.setContentType("application/json");
			response.setHeader("Cache-Control", "no-store");
			response.getWriter().print(result);
		} catch (Exception ee) {
		}
	}

	/**
	 * load ChannelLog with DataTable.
	 * 
	 * @param start_day
	 * @param end_day
	 * @param request
	 * @param response
	 */
	@RequestMapping("loadChannelLog.html")
	public void loadChannelLog(
			@RequestParam(value = "start_day", required = false) String start_day,
			@RequestParam(value = "end_day", required = false) String end_day,
			HttpServletRequest request, HttpServletResponse response) {

		String[] cols = { "User_UserId", "VisitDate", "VisitIp", "ChannelName" };
		String table = "visit";

		start_day = Utils.checkNull(start_day);
		end_day = Utils.checkNull(end_day);

		// System.out.println(start_day+"===="+end_day);

		JSONObject result = new JSONObject();
		int amount = 10;
		int start = 0;
		int col = 2;

		String dir = "desc";

		String sStart = Utils.checkNull(request.getParameter("start"));
		String sAmount = Utils.checkNull(request.getParameter("length"));
		String sCol = Utils.checkNull(request.getParameter("iSortCol_0"));
		String sdir = Utils.checkNull(request.getParameter("sSortDir_0"));

		Map<String, String[]> parameters = request.getParameterMap();
		sdir = parameters.get("order[0][dir]")[0];
		sCol = parameters.get("order[0][column]")[0];

		String searchTerm = Utils.checkNull(request
				.getParameter("search[value]"));
		String individualSearch = "";

		if (sStart.length() > 0) {
			start = Integer.parseInt(sStart);
			if (start < 0)
				start = 0;
		}

		if (sAmount.length() > 0) {
			amount = Integer.parseInt(sAmount);
			if (amount < 10 || amount > 100)
				amount = 10;
		}

		if (sCol.length() > 0) {
			col = Integer.parseInt(sCol);
			if (col < 0 || col > 2)
				col = 2;
		}

		if (sdir.length() > 0) {
			if (!sdir.equals("desc"))
				dir = "asc";
		}

		String additionalSQL = "";
		if (start_day.length() > 0) {
			additionalSQL += " date>='" + start_day.replaceAll("-", "") + "' ";
		}

		if (end_day.length() > 0) {
			if (additionalSQL.length() > 0)
				additionalSQL += " and ";
			additionalSQL += " date<='" + end_day.replaceAll("-", "") + "' ";
		}

		if (additionalSQL.length() > 0)
			additionalSQL = " and ( " + additionalSQL + " ) ";

		String colName = cols[col];
		Long total = (long) 0;
		Long totalAfterFilter = (long) 0;

		Connection conn = DSManager.getConnection();

		try {

			VisitorDAO dao = new VisitorDAO(conn);
			String sql_count = "select COUNT(*) from user a INNER JOIN visit v ON a.UserId = v.User_UserId INNER JOIN content c ON c.ContentId = v.Content_ContentId INNER JOIN channel ch ON c.Channel_ChannelId = ch.ChannelId where CAST(v.VisitDate AS DATE) BETWEEN '"
					+ start_day + "' and '" + end_day + "'";
			String sql = "select a.UserId, a.UserName, a.UserFirstName, a.UserLastName, v.VisitDate, v.VisitIp, c.ContentName, ch.ChannelName from user a INNER JOIN visit v ON a.UserId = v.User_UserId INNER JOIN content c ON c.ContentId = v.Content_ContentId INNER JOIN channel ch ON c.Channel_ChannelId = ch.ChannelId where CAST(v.VisitDate AS DATE) BETWEEN '"
					+ start_day
					+ "' and '"
					+ end_day
					+ "' order by v.VisitDate "
					+ dir
					+ " LIMIT "
					+ start
					+ ","
					+ amount;
			total = dao.getCount(sql_count);
			totalAfterFilter = total;

			JSONArray array = dao.getContent(sql,
					session.getLanguage(request.getSession()));

			result.put("recordsTotal", total);
			result.put("recordsFiltered", totalAfterFilter);// totalAfterFilter);
			result.put("data", array);

		} catch (Exception e) {
			log.error("LogController", "loadChannelLog", e.toString());

		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception f) {
			}
		}

		try {
			response.setContentType("application/json");
			response.setHeader("Cache-Control", "no-store");
			response.getWriter().print(result);
		} catch (Exception ee) {
		}
	}

	/**
	 * load ContentLog with DataTable.
	 * 
	 * @param start_day
	 * @param end_day
	 * @param request
	 * @param response
	 */
	@RequestMapping("loadContentLog.html")
	public void loadContentLog(
			@RequestParam(value = "start_day", required = false) String start_day,
			@RequestParam(value = "end_day", required = false) String end_day,
			HttpServletRequest request, HttpServletResponse response) {

		String[] cols = { "user_id", "time", "ip_addr", "contenido" };
		String table = "visit";

		start_day = Utils.checkNull(start_day);
		end_day = Utils.checkNull(end_day);

		// System.out.println(start_day+"===="+end_day);

		JSONObject result = new JSONObject();
		int amount = 10;
		int start = 0;
		int col = 2;

		String dir = "desc";

		String sStart = Utils.checkNull(request.getParameter("start"));
		String sAmount = Utils.checkNull(request.getParameter("length"));
		String sCol = Utils.checkNull(request.getParameter("iSortCol_0"));
		String sdir = Utils.checkNull(request.getParameter("sSortDir_0"));

		Map<String, String[]> parameters = request.getParameterMap();
		sdir = parameters.get("order[0][dir]")[0];
		sCol = parameters.get("order[0][column]")[0];

		String searchTerm = Utils.checkNull(request
				.getParameter("search[value]"));
		String individualSearch = "";

		if (sStart.length() > 0) {
			start = Integer.parseInt(sStart);
			if (start < 0)
				start = 0;
		}

		if (sAmount.length() > 0) {
			amount = Integer.parseInt(sAmount);
			if (amount < 10 || amount > 100)
				amount = 10;
		}

		if (sCol.length() > 0) {
			col = Integer.parseInt(sCol);
			if (col < 0 || col > 2)
				col = 2;
		}

		if (sdir.length() > 0) {
			if (!sdir.equals("desc"))
				dir = "asc";
		}

		String additionalSQL = "";
		if (start_day.length() > 0) {
			additionalSQL += " date>='" + start_day.replaceAll("-", "") + "' ";
		}

		if (end_day.length() > 0) {
			if (additionalSQL.length() > 0)
				additionalSQL += " and ";
			additionalSQL += " date<='" + end_day.replaceAll("-", "") + "' ";
		}

		if (additionalSQL.length() > 0)
			additionalSQL = " and ( " + additionalSQL + " ) ";

		String colName = cols[col];
		Long total = (long) 0;
		Long totalAfterFilter = (long) 0;

		Connection conn = DSManager.getConnection();

		try {

			VisitorDAO dao = new VisitorDAO(conn);
			// JR
			String sql_count = "select COUNT(*) from user a INNER JOIN visit v ON a.UserId = v.User_UserId INNER JOIN content c ON c.ContentId = v.Content_ContentId INNER JOIN channel ch ON c.Channel_ChannelId = ch.ChannelId where CAST(v.VisitDate AS DATE) BETWEEN '"
					+ start_day + "' and '" + end_day + "'";
			String sql = "select a.UserId, a.UserName, a.UserFirstName, a.UserLastName, v.VisitDate, v.VisitIp, c.ContentName, ch.ChannelName from user a INNER JOIN visit v ON a.UserId = v.User_UserId INNER JOIN content c ON c.ContentId = v.Content_ContentId INNER JOIN channel ch ON c.Channel_ChannelId = ch.ChannelId where CAST(v.VisitDate AS DATE) BETWEEN '"
					+ start_day
					+ "' and '"
					+ end_day
					+ "' order by v.VisitDate "
					+ dir
					+ " LIMIT "
					+ start
					+ ","
					+ amount;
			total = dao.getCount(sql_count);
			totalAfterFilter = total;

			JSONArray array = dao.getContent(sql,
					session.getLanguage(request.getSession()));

			result.put("recordsTotal", total);
			result.put("recordsFiltered", total);// totalAfterFilter);
			result.put("data", array);

		} catch (Exception e) {
			log.error("LogController", "loadContentLog", e.toString());

		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception f) {
			}
		}

		try {
			response.setContentType("application/json");
			response.setHeader("Cache-Control", "no-store");
			response.getWriter().print(result);
		} catch (Exception ee) {
		}
	}

	/**
	 * load AdvancedLog with DataTable.
	 * 
	 * @param start_day
	 * @param end_day
	 * @param request
	 * @param response
	 */
	@RequestMapping("loadAdvancedLog.html")
	public void loadAdvancedLog(
			@RequestParam(value = "search", required = false) String search,
			@RequestParam(value = "exact", required = false) String exact,
			@RequestParam(value = "option", required = false) String option,
			@RequestParam(value = "start_day", required = false) String start_day,
			@RequestParam(value = "end_day", required = false) String end_day,
			HttpServletRequest request, HttpServletResponse response) {

		String[] cols = { "User_UserId", "VisitDate", "VisitIp" };
		String table = "visit";

		search = Utils.encode(Utils.checkNull(search));
		exact = Utils.checkNull(exact);
		if (exact.length() == 0)
			exact = "0";
		option = Utils.checkNull(option);
		if (option.length() == 0)
			option = "9";
		start_day = Utils.checkNull(start_day);
		end_day = Utils.checkNull(end_day);

		JSONObject result = new JSONObject();
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

		String searchTerm = Utils.checkNull(request
				.getParameter("search[value]"));
		String individualSearch = "";

		if (sStart.length() > 0) {
			start = Integer.parseInt(sStart);
			if (start < 0)
				start = 0;
		}

		if (sAmount.length() > 0) {
			amount = Integer.parseInt(sAmount);
			if (amount < 10 || amount > 100)
				amount = 10;
		}

		if (sCol.length() > 0) {
			col = Integer.parseInt(sCol);
			if (col < 0 || col > 2)
				col = 1;
		}

		if (sdir.length() > 0) {
			if (!sdir.equals("desc"))
				dir = "asc";
		}

		String sql = "";
		String sql_count = "";
		if (search.length() > 0) {
			if (exact.equals("0")) {
				if (option.equals("1")) {
					sql_count = "select COUNT(*) from user a INNER JOIN visit v ON a.UserId = v.User_UserId INNER JOIN content c ON c.ContentId = v.Content_ContentId INNER JOIN channel ch ON c.Channel_ChannelId = ch.ChannelId where (CAST(v.VisitDate AS DATE) BETWEEN '"
							+ start_day
							+ "' and '"
							+ end_day
							+ "') AND c.ContentStatus='A'  AND a.UserName like'%"
							+ search + "%'";
					sql = "select a.UserId, a.UserName, v.VisitDate, v.VisitIp, c.ContentName, ch.ChannelName from user a INNER JOIN visit v ON a.UserId = v.User_UserId INNER JOIN content c ON c.ContentId = v.Content_ContentId INNER JOIN channel ch ON c.Channel_ChannelId = ch.ChannelId where (CAST(v.VisitDate AS DATE) BETWEEN '"
							+ start_day
							+ "' and '"
							+ end_day
							+ "') AND c.ContentStatus='A' AND a.UserName like'%"
							+ search
							+ "%' order by v.VisitDate "
							+ dir
							+ " LIMIT " + start + "," + amount;
				} else if (option.equals("3")) {
					sql_count = "select COUNT(*) from user a INNER JOIN visit v ON a.UserId = v.User_UserId INNER JOIN content c ON c.ContentId = v.Content_ContentId INNER JOIN channel ch ON c.Channel_ChannelId = ch.ChannelId where (CAST(v.VisitDate AS DATE) BETWEEN '"
							+ start_day
							+ "' and '"
							+ end_day
							+ "') AND c.ContentStatus='A'  AND c.ContentName like'%"
							+ search + "%'";
					sql = "select a.UserId, a.UserName, v.VisitDate, v.VisitIp, c.ContentName, ch.ChannelName from user a INNER JOIN visit v ON a.UserId = v.User_UserId INNER JOIN content c ON c.ContentId = v.Content_ContentId INNER JOIN channel ch ON c.Channel_ChannelId = ch.ChannelId where (CAST(v.VisitDate AS DATE) BETWEEN '"
							+ start_day
							+ "' and '"
							+ end_day
							+ "') AND c.ContentStatus='A'  AND c.ContentName like'%"
							+ search
							+ "%' order by v.VisitDate "
							+ dir
							+ " LIMIT " + start + "," + amount;
				} else if (option.equals("4")) {
					sql_count = "select COUNT(*) from user a INNER JOIN visit v ON a.UserId = v.User_UserId INNER JOIN content c ON c.ContentId = v.Content_ContentId INNER JOIN channel ch ON c.Channel_ChannelId = ch.ChannelId where (CAST(v.VisitDate AS DATE) BETWEEN '"
							+ start_day
							+ "' and '"
							+ end_day
							+ "') AND c.ContentStatus='A'  AND ch.ChannelName like'%"
							+ search + "%'";
					sql = "select a.UserId, a.UserName,  v.VisitDate, v.VisitIp, c.ContentName, ch.ChannelName from user a INNER JOIN visit v ON a.UserId = v.User_UserId INNER JOIN content c ON c.ContentId = v.Content_ContentId INNER JOIN channel ch ON c.Channel_ChannelId = ch.ChannelId where (CAST(v.VisitDate AS DATE) BETWEEN '"
							+ start_day
							+ "' and '"
							+ end_day
							+ "') AND c.ContentStatus='A'  AND ch.ChannelName like'%"
							+ search
							+ "%' order by v.VisitDate "
							+ dir
							+ " LIMIT " + start + "," + amount;
				}

			} else {
				if (option.equals("1")) {
					sql_count = "select COUNT(*) from user a INNER JOIN visit v ON a.UserId = v.User_UserId INNER JOIN content c ON c.ContentId = v.Content_ContentId INNER JOIN channel ch ON c.Channel_ChannelId = ch.ChannelId where (CAST(v.VisitDate AS DATE) BETWEEN '"
							+ start_day
							+ "' and '"
							+ end_day
							+ "') AND c.ContentStatus='A'  AND a.UserName = '"
							+ search + "'";
					sql = "select a.UserId, a.UserName,  v.VisitDate, v.VisitIp, c.ContentName, ch.ChannelName from user a INNER JOIN visit v ON a.UserId = v.User_UserId INNER JOIN content c ON c.ContentId = v.Content_ContentId INNER JOIN channel ch ON c.Channel_ChannelId = ch.ChannelId where (CAST(v.VisitDate AS DATE) BETWEEN '"
							+ start_day
							+ "' and '"
							+ end_day
							+ "') AND c.ContentStatus='A'  AND a.UserName='"
							+ search
							+ "' order by v.VisitDate "
							+ dir
							+ " LIMIT " + start + "," + amount;
				} else if (option.equals("3")) {
					sql_count = "select COUNT(*) from user a INNER JOIN visit v ON a.UserId = v.User_UserId INNER JOIN content c ON c.ContentId = v.Content_ContentId INNER JOIN channel ch ON c.Channel_ChannelId = ch.ChannelId where (CAST(v.VisitDate AS DATE) BETWEEN '"
							+ start_day
							+ "' and '"
							+ end_day
							+ "')  AND c.ContentStatus='A'  AND c.ContentName = '"
							+ search + "'";
					sql = "select a.UserId, a.UserName,  v.VisitDate, v.VisitIp, c.ContentName, ch.ChannelName from user a INNER JOIN visit v ON a.UserId = v.User_UserId INNER JOIN content c ON c.ContentId = v.Content_ContentId INNER JOIN channel ch ON c.Channel_ChannelId = ch.ChannelId where (CAST(v.VisitDate AS DATE) BETWEEN '"
							+ start_day
							+ "' and '"
							+ end_day
							+ "')  AND c.ContentStatus='A'  AND c.ContentName='"
							+ search
							+ "' order by v.VisitDate "
							+ dir
							+ " LIMIT " + start + "," + amount;
				} else if (option.equals("4")) {
					sql_count = "select COUNT(*) from user a INNER JOIN visit v ON a.UserId = v.User_UserId INNER JOIN content c ON c.ContentId = v.Content_ContentId INNER JOIN channel ch ON c.Channel_ChannelId = ch.ChannelId where (CAST(v.VisitDate AS DATE) BETWEEN '"
							+ start_day
							+ "' and '"
							+ end_day
							+ "')  AND c.ContentStatus='A'  AND ch.ChannelName = '"
							+ search + "'";
					sql = "select a.UserId, a.UserName,  v.VisitDate, v.VisitIp, c.ContentName, ch.ChannelName from user a INNER JOIN visit v ON a.UserId = v.User_UserId INNER JOIN content c ON c.ContentId = v.Content_ContentId INNER JOIN channel ch ON c.Channel_ChannelId = ch.ChannelId where (CAST(v.VisitDate AS DATE) BETWEEN '"
							+ start_day
							+ "' and '"
							+ end_day
							+ "')  AND c.ContentStatus='A'  AND ch.ChannelName='"
							+ search
							+ "' order by v.VisitDate "
							+ dir
							+ " LIMIT " + start + "," + amount;
				}

			}
		} else {
			sql_count = "select COUNT(*) from user a INNER JOIN visit v ON a.UserId = v.User_UserId INNER JOIN content c ON c.ContentId = v.Content_ContentId INNER JOIN channel ch ON c.Channel_ChannelId = ch.ChannelId where CAST(v.VisitDate AS DATE) BETWEEN '"
					+ start_day + "' and '" + end_day + "'";
			sql = "select a.UserId, a.UserName,  v.VisitDate, v.VisitIp, c.ContentName, ch.ChannelName from user a INNER JOIN visit v ON a.UserId = v.User_UserId INNER JOIN content c ON c.ContentId = v.Content_ContentId INNER JOIN channel ch ON c.Channel_ChannelId = ch.ChannelId where (CAST(v.VisitDate AS DATE) BETWEEN '"
					+ start_day
					+ "' and '"
					+ end_day
					+ "')  AND c.ContentStatus='A'  order by v.VisitDate "
					+ dir + " LIMIT " + start + "," + amount;
		}

		String colName = cols[col];
		Long total = (long) 0;
		Long totalAfterFilter = (long) 0;

		Connection conn = DSManager.getConnection();

		try {

			VisitorDAO dao = new VisitorDAO(conn);
			// JR
			total = dao.getCount(sql_count);
			totalAfterFilter = total;

			JSONArray array = dao.getContent(sql,
					session.getLanguage(request.getSession()));

			result.put("recordsTotal", total);
			result.put("recordsFiltered", totalAfterFilter);// totalAfterFilter);
			result.put("data", array);

		} catch (Exception e) {
			log.error("LogController", "loadAdvancedLog", e.toString());

		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception f) {
			}
		}

		try {
			response.setContentType("application/json");
			response.setHeader("Cache-Control", "no-store");
			response.getWriter().print(result);
		} catch (Exception ee) {
		}
	}

}
