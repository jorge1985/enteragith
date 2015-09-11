package com.youandbbva.enteratv.beans;

import javax.servlet.http.HttpSession;

import com.youandbbva.enteratv.Constants;
import com.youandbbva.enteratv.Utils;
import com.youandbbva.enteratv.domain.UserInfo;

/**
 * Manage session.
 * 
 * @author CJH
 *
 */
public class SessionHandler {

	private static SessionHandler instance = new SessionHandler();
	
	private final static String LANGUAGE = "language";
	private final static String FLASH_MESSAGE = "message";
	private final static String ADMIN_USER = "user.admin";
	private final static String FRONT_USER = "user.front";
	private final static String OPPORT_USER = "user.opport";
	
	/**
	 * Return SessionHandler Instance.
	 * 
	 * @return instance
	 */
	public static SessionHandler getInstance() {
		return instance;
	}
	
	/**
	 * Destory SessionHandler instance.
	 */
	public static void destroy(){
		instance = null;
	}

	/**
	 * Set language.
	 * 
	 * @param session
	 * @param language
	 */
	public void setLanguage(HttpSession session, String language){
		session.setAttribute(LANGUAGE, language);
	}
	
	/**
	 * Get language
	 * 
	 * @param session
	 * @return language
	 */
	public String getLanguage(HttpSession session){
		return Utils.checkNull(session.getAttribute(LANGUAGE));
	}
	
	/**
	 * Set Flash Message.
	 * 
	 * @param session
	 * @param message
	 */
	public void setFlashMessage(HttpSession session, String message){
		session.setAttribute(FLASH_MESSAGE, message);
	}
	
	/**
	 * Get Flash Message.
	 * 
	 * @param session
	 * @return message
	 */
	public String getFlashMessage(HttpSession session){
		return Utils.checkNull(session.getAttribute(FLASH_MESSAGE));
	}
	
	/**
	 * Set User Information.
	 * 
	 * @param session
	 * @param user
	 */
	public void setUserInfo(HttpSession session, UserInfo user){
		session.setAttribute(ADMIN_USER, user);
	}
	
	/**
	 * Get User Information.
	 * 
	 * @param session
	 * @return UserInfo
	 */
	public UserInfo getUserInfo(HttpSession session){
		Object user = session.getAttribute(ADMIN_USER);
		return user==null ? null : (UserInfo)user;
	}

	/**
	 * Set Front User Information.
	 * 
	 * @param session
	 * @param user
	 */
	public void setFrontUserInfo(HttpSession session, UserInfo user){
		session.setAttribute(FRONT_USER, user);
	}

	/**
	 * Get Front User Information.
	 * 
	 * @param session
	 * @return UserInfo
	 */
	public UserInfo getFrontUserInfo(HttpSession session){
		Object user = session.getAttribute(FRONT_USER);
		return user==null ? null : (UserInfo)user;
	}
	
	/**
	 * Get Front User ID.
	 * 
	 * @param session
	 * @return User ID
	 */
	public String getFrontUserID(HttpSession session){
		Object user = session.getAttribute(FRONT_USER);
		//return (String) (user==null ? Constants.OUTSIDE_USER_ID : ((UserInfo)user).getUserId());
		int users = ((UserInfo)user).getUserId();
		
		return String.valueOf(users);
	}

	/**
	 * Set Opportunities User Information.
	 * 
	 * @param session
	 * @param user
	 */
	public void setOpportUserInfo(HttpSession session, UserInfo user){
		session.setAttribute(OPPORT_USER, user);
	}
	
	/**
	 * Get Opportunities User Information.
	 * 
	 * @param session
	 * @return UserInfo
	 */
	public UserInfo getOpportUserInfo(HttpSession session){
		Object user = session.getAttribute(OPPORT_USER);
		return user==null ? null : (UserInfo)user;
	}
	
	/**
	 * Log out from session.
	 * 
	 * @param session
	 */
	public void logout(HttpSession session){
		try{
			session.removeAttribute(ADMIN_USER);
		}catch (Exception e){}
		
		try{
			session.removeAttribute(FRONT_USER);
		}catch (Exception e){}
		
		try{
			session.removeAttribute(OPPORT_USER);
		}catch (Exception e){}
	}
}
