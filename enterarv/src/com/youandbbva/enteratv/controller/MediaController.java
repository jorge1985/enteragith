package com.youandbbva.enteratv.controller;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.youandbbva.enteratv.domain.FolderInfo;
import com.youandbbva.enteratv.domain.UserInfo;

/**
 * Handle all action for Media Manager.
 * 
 * @author CJH
 *
 */

@Controller
@RequestMapping("/media/")
@Component("MediaController")
public class MediaController extends com.youandbbva.enteratv.Controller {

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
	 * Media Manager page.
	 * 
	 * @param req
	 * @param res
	 * @return view
	 */
	@RequestMapping("manager.html")
	public ModelAndView manager(HttpServletRequest req, HttpServletResponse res) {

		
		  UserInfo user = session.getUserInfo(req.getSession()); if
		  (user==null){ return new ModelAndView("redirect:/user/login.html"); }
		 

		// Connection conn = DSManager.getConnection();

		ModelAndView mv = new ModelAndView("mediamanager");

		try {

		} catch (Exception e) {
			log.error("MediaController", "manager", e.toString());

		} finally {
			// try{
			// if (conn!=null)
			// conn.close();
			// }catch (Exception f){}
		}

		return mv;
	}

	/**
	 * Load all Media data.
	 * 
	 * @param req
	 * @param res
	 */
	@RequestMapping("loadMedia.html")
	public void loadMedia(HttpServletRequest req, HttpServletResponse res) {

		Connection conn = DSManager.getConnection();

		JSONObject result = new JSONObject();
		result = setResponse(result, Constants.ERROR_CODE,
				Constants.ACTION_FAILED);
		result = setResponse(result, Constants.ERROR_MSG, reg.getMessage(
				"ACT0002", session.getLanguage(req.getSession())));

		try {
			UserInfo user = session.getUserInfo(req.getSession());
			if (user == null) {
				result = setResponse(
						result,
						Constants.ERROR_MSG,
						reg.getMessage("ACT0003",
								session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}

			MediaDAO dao = new MediaDAO(conn);

			result = setResponse(result, "list", dao.recallFolderList((long) 0));

			result = setResponse(result, Constants.ERROR_CODE,
					Constants.ACTION_SUCCESS);
			result = setResponse(
					result,
					Constants.ERROR_MSG,
					reg.getMessage("ACT0001",
							session.getLanguage(req.getSession())));

		} catch (Exception e) {
			log.error("MediaController", "loadMedia", e.toString());

		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception f) {
			}
		}

		response(res, result);
	}

	/**
	 * Get Media data.
	 * 
	 * @param folder_id
	 * @param req
	 * @param res
	 */
	@RequestMapping("getMedia.html")
	public void getMedia(
			@RequestParam(value = "folder_id", required = false) String folder_id,
			HttpServletRequest req, HttpServletResponse res) {

		folder_id = Utils.checkNull(folder_id);

		Connection conn = DSManager.getConnection();

		JSONObject result = new JSONObject();
		result = setResponse(result, Constants.ERROR_CODE,
				Constants.ACTION_FAILED);
		result = setResponse(result, Constants.ERROR_MSG, reg.getMessage(
				"ACT0002", session.getLanguage(req.getSession())));

		try {
			UserInfo user = session.getUserInfo(req.getSession());
			if (user == null) {
				result = setResponse(
						result,
						Constants.ERROR_MSG,
						reg.getMessage("ACT0003",
								session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}

			MediaDAO dao = new MediaDAO(conn);

			result = setResponse(result, "file_id",
					dao.getFoldersOfJSON(Utils.getLong(folder_id)));
			result = setResponse(result, "folder",
					dao.getFoldersOfJSON(Utils.getLong(folder_id)));
			result = setResponse(result, "file",
					dao.getFilesOfJSON(Utils.getLong(folder_id)));

			result = setResponse(result, Constants.ERROR_CODE,
					Constants.ACTION_SUCCESS);
			result = setResponse(
					result,
					Constants.ERROR_MSG,
					reg.getMessage("ACT0001",
							session.getLanguage(req.getSession())));

		} catch (Exception e) {
			log.error("MediaController", "getMedia", e.toString());

		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception f) {
			}
		}

		response(res, result);
	}

	/**
	 * Update Folder.
	 * 
	 * @param folder_id
	 * @param type
	 *            Create or Rename or Delete
	 * @param name
	 * @param req
	 * @param res
	 * @throws IOException 
	 */
	@RequestMapping("updateFolder.html")
	// JR Original
	/*
	 * public void updateFolder(
	 * 
	 * @RequestParam(value = "folder_id", required = false) String folder_id,
	 * 
	 * @RequestParam(value = "type", required = false) String type,
	 * 
	 * @RequestParam(value = "name", required = false) String name,
	 * HttpServletRequest req, HttpServletResponse res)
	 */
	public void updateFolder(String folder_id, String type, String name,
			HttpServletRequest req, HttpServletResponse res)  {

		type = Utils.checkNull(type);
		folder_id = Utils.checkNull(folder_id);
		name = Utils.encode(Utils.checkNull(name));

		Connection conn = DSManager.getConnection();

		JSONObject result = new JSONObject();
		result = setResponse(result, Constants.ERROR_CODE,
				Constants.ACTION_FAILED);
		result = setResponse(result, Constants.ERROR_MSG, reg.getMessage(
				"ACT0002", session.getLanguage(req.getSession())));

		try {
			UserInfo user = session.getUserInfo(req.getSession());
			if (user == null) {
				result = setResponse(
						result,
						Constants.ERROR_MSG,
						reg.getMessage("ACT0003",
								session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}

			if (type.length() == 0) {
				result.put(
						Constants.ERROR_MSG,
						reg.getMessage("ACT0003",
								session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}

			if (!type.equals("add") && !type.equals("edit")
					&& !type.equals("delete")) {
				result.put(
						Constants.ERROR_MSG,
						reg.getMessage("ACT0003",
								session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}

			conn.setAutoCommit(false);

			MediaDAO dao = new MediaDAO(conn);

			Long folderID = Utils.getLong(folder_id);
			FolderInfo info = dao.getFolder(folderID);
			if (type.equals("add") || type.equals("edit")) {
				if (type.equals("add")) {
					if (dao.isValidFolder(name)) {
						dao.insertFolder(Utils.getLong(folder_id), name);
					} else {
						result = setResponse(
								result,
								Constants.ERROR_MSG,
								reg.getMessage("CMM0002",
										session.getLanguage(req.getSession())));
						throw new Exception(reg.getMessage("CMM0002"));
					}
				} else {
					if (dao.isValidFolder(name)) {
						dao.updateFolderName(Utils.getLong(folder_id), name);
						folderID = info.getParentID();
					} else {
						result = setResponse(
								result,
								Constants.ERROR_MSG,
								reg.getMessage("CMM0002",
										session.getLanguage(req.getSession())));
						throw new Exception(reg.getMessage("CMM0002"));
					}
				}
			} else {
				dao.deleteFolder(Utils.getLong(folder_id));
				folderID = info.getParentID();
			}

			conn.commit();

			result = setResponse(result, "FolderId", String.valueOf(folderID));
			result = setResponse(result, "folder",
					dao.getFoldersOfJSON(folderID));
			result = setResponse(result, "file", dao.getFilesOfJSON(folderID));

			result = setResponse(result, Constants.ERROR_CODE,
					Constants.ACTION_SUCCESS);
			result = setResponse(
					result,
					Constants.ERROR_MSG,
					reg.getMessage("ACT0001",
							session.getLanguage(req.getSession())));

		} catch (Exception e) {
						
			log.error("MediaController", "updateFolder", e.toString());
			
			try {
				conn.rollback();
			} catch (Exception f) {
			}
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception f) {
			}
		}

		response(res, result);
	}

	/**
	 * Update File.
	 * 
	 * @param file_id
	 * @param type
	 *            Upload or Rename or Delete
	 * @param name
	 * @param req
	 * @param res
	 */
	@RequestMapping("updateFile.html")
	/*
	 * public void updateFile(
	 * 
	 * @RequestParam(value = "file_id", required = false) String file_id,
	 * 
	 * @RequestParam(value = "folder_id", required = false) String folder_id,
	 * 
	 * @RequestParam(value = "type", required = false) String type,
	 * 
	 * @RequestParam(value = "name", required = false) String name,
	 * 
	 * @RequestParam(value = "size", required = false) String size,
	 * 
	 * @RequestParam(value = "key", required = false) String key,
	 * 
	 * @RequestParam(value = "kind", required = false) String kind,
	 * 
	 * @RequestParam(value = "thumb", required = false) String thumb,
	 * HttpServletRequest req, HttpServletResponse res){
	 */
	public void updateFile(String file_id, String folder_id, String type,
			String name, String size, String key, String kind, String thumb,
			HttpServletRequest req, HttpServletResponse res) {

		type = Utils.checkNull(type);
		file_id = Utils.checkNull(file_id);
		folder_id = Utils.checkNull(folder_id);
		key = Utils.checkNull(key);
		size = Utils.checkNull(size);
		kind = Utils.checkNull(kind);
		thumb = Utils.checkNull(thumb);
		name = Utils.encode(Utils.checkNull(name));

		Connection conn = DSManager.getConnection();

		JSONObject result = new JSONObject();
		result = setResponse(result, Constants.ERROR_CODE,
				Constants.ACTION_FAILED);
		result = setResponse(result, Constants.ERROR_MSG, reg.getMessage(
				"ACT0002", session.getLanguage(req.getSession())));

		try {
			UserInfo user = session.getUserInfo(req.getSession());
			if (user == null) {
				result = setResponse(
						result,
						Constants.ERROR_MSG,
						reg.getMessage("ACT0003",
								session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}
			System.out.println("valor de type "+ type);
			if (type.length() == 0) {
				result.put(
						Constants.ERROR_MSG,
						reg.getMessage("ACT0003",
								session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}

			if (!type.equals("add") && !type.equals("edit")
					&& !type.equals("delete")) {
				result.put(
						Constants.ERROR_MSG,
						reg.getMessage("ACT0003",
								session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}

			conn.setAutoCommit(false);

			MediaDAO dao = new MediaDAO(conn);

			if (type.equals("add") || type.equals("edit")) {
				if (dao.isValidFile(name, Utils.getLong(folder_id))) {
					if (type.equals("add")) {
						dao.insertFile(Utils.getLong(folder_id), name,
								Utils.getTodayWithTime(), Utils.getLong(size),
								key, kind, thumb);
					} else
						dao.updateFileName(Utils.getLong(file_id), name,
								Utils.getTodayWithTime());

				} else {
					result = setResponse(
							result,
							Constants.ERROR_MSG,
							reg.getMessage("CMM0002",
									session.getLanguage(req.getSession())));
					throw new Exception(reg.getMessage("CMM0002"));
				}
			} else {
				dao.deleteFile(Utils.getLong(file_id));
			}

			conn.commit();

			result = setResponse(result, "folder",
					dao.getFoldersOfJSON(Utils.getLong(folder_id)));
			result = setResponse(result, "file",
					dao.getFilesOfJSON(Utils.getLong(folder_id)));

			result = setResponse(result, Constants.ERROR_CODE,
					Constants.ACTION_SUCCESS);
			result = setResponse(
					result,
					Constants.ERROR_MSG,
					reg.getMessage("ACT0001",
							session.getLanguage(req.getSession())));

		} catch (Exception e) {
			log.error("MediaController", "updateFile", e.toString());

			try {
				conn.rollback();
			} catch (Exception f) {
			}
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception f) {
			}
		}

		response(res, result);
	}

	/**
	 * Get File Information.
	 * 
	 * @param file_id
	 * @param req
	 * @param res
	 */
	@RequestMapping("getFile.html")
	/*
	 * public void getFile(
	 * 
	 * @RequestParam(value = "file_id", required = false) String file_id,
	 * HttpServletRequest req, HttpServletResponse res)
	 */
	public void getFile(String file_id, HttpServletRequest req,
			HttpServletResponse res) {

		file_id = Utils.checkNull(file_id);

		Connection conn = DSManager.getConnection();

		JSONObject result = new JSONObject();
		result = setResponse(result, Constants.ERROR_CODE,
				Constants.ACTION_FAILED);
		result = setResponse(result, Constants.ERROR_MSG, reg.getMessage(
				"ACT0002", session.getLanguage(req.getSession())));

		try {
			UserInfo user = session.getUserInfo(req.getSession());
			if (user == null) {
				result = setResponse(
						result,
						Constants.ERROR_MSG,
						reg.getMessage("ACT0003",
								session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}

			MediaDAO dao = new MediaDAO(conn);

			result = setResponse(result, "file",
					dao.getFileOfJSON(Utils.getLong(file_id)));

			result = setResponse(result, Constants.ERROR_CODE,
					Constants.ACTION_SUCCESS);
			result = setResponse(
					result,
					Constants.ERROR_MSG,
					reg.getMessage("ACT0001",
							session.getLanguage(req.getSession())));

		} catch (Exception e) {
			log.error("MediaController", "getFile", e.toString());

		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception f) {
			}
		}

		response(res, result);
	}

}
