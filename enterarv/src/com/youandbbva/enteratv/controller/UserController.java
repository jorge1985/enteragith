package com.youandbbva.enteratv.controller;

import java.net.URLEncoder;
import java.sql.Connection;
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
import com.youandbbva.enteratv.dao.PublicDAO;
import com.youandbbva.enteratv.dao.UserDAO;
import com.youandbbva.enteratv.dao.VisitorDAO;
import com.youandbbva.enteratv.domain.UserInfo;
import com.youandbbva.enteratv.domain.ValidationInfo;
import com.youandbbva.enteratv.sso.AccountSettings;
import com.youandbbva.enteratv.sso.AppSettings;
import com.youandbbva.enteratv.sso.AuthRequest;
import com.youandbbva.enteratv.sso.Response;

/**
 * Handle all action for user.
 * 		such as register, login, logout. 
 *  
 * @author CJH
 *
 */

@Controller
@RequestMapping("/user/")
@Component("UserController")
public class UserController extends com.youandbbva.enteratv.Controller{

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
	 * Login Page.
	 * 
	 * @param req
	 * @param res
	 * @return view
	 */
	@RequestMapping("login.html")
	public ModelAndView login(
			HttpServletRequest req, HttpServletResponse res){
		ModelAndView mv = new ModelAndView("login");
		
		String username = "";
		//String password = "";
		
		try{
//			Cookie[] cookie = req.getCookies();
//			for (int i=0; i<cookie.length; i++){
//				Cookie c = cookie[i];
//				if (c.getName().equals(Constants.PASSWORD_HASH + "_username")){
//					username = c.getValue();
//				}
//				
////				if (c.getName().equals(Constants.PASSWORD_HASH + "_password")){
////					password = c.getValue();
////				}
//			}
		}catch (Exception e){}
		
		mv.addObject("username", username);
		//mv.addObject("password", password);
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
		
		return new ModelAndView("redirect:/user/login.html");
	}
	
	/**
	 * Check user and password.
	 * if valid, show dashboard.
	 *
	 * @param user
	 * @param remember
	 * @param req
	 * @param res
	 * @return view
	 */
	@RequestMapping("home.html")
	public ModelAndView home(
			@RequestParam(value = "username", required = false) String user,
			@RequestParam(value = "remember", required = false) String remember,
			HttpServletRequest req, HttpServletResponse res){

		Connection conn = DSManager.getConnection();

		String EmailLogin = Utils.checkNull(user);
		remember = Utils.checkNull(remember);

		try{
			if (EmailLogin.length()==0){
				session.setFlashMessage(req.getSession(), reg.getMessage("USR0004", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("USR0004"));
			}
			
			UserDAO userDao = new UserDAO(conn);
            // cambiar por correo     
			UserInfo u = userDao.getUserInfoFromMusuario(EmailLogin);
			if (u!=null){
				if (u.getUserStatus() != Integer.parseInt(Constants.DEFAULT_ACTIVE)){
					session.setFlashMessage(req.getSession(), reg.getMessage("USR0006", session.getLanguage(req.getSession())));
					throw new Exception(reg.getMessage("USR0006"));
				}

				AppSettings appSettings = new AppSettings();
				appSettings.setAssertionConsumerServiceUrl(Constants.BASEPATH + "dashboard/dashboard.html");
				
				AccountSettings accSettings = new AccountSettings();
				accSettings.setIdpSsoTargetUrl(Constants.BASEPATH + "user/auth.html");
				appSettings.setIssuer(Constants.BASEPATH);

				AuthRequest authReq = new AuthRequest(appSettings, accSettings);
				String reqString = accSettings.getIdp_sso_target_url()+"?user_id="+u.getUserId()+"&SAMLRequest=" + URLEncoder.encode(authReq.getRequest(AuthRequest.base64),"UTF-8");
				res.sendRedirect(reqString);

				if (u.getUserRol().equals(Constants.CODE_USER_ADMINISTRATOR)){
					session.setUserInfo(req.getSession(), u);
					session.setFrontUserInfo(req.getSession(), u);
				}
				else
					session.setFrontUserInfo(req.getSession(), u);
				
				try{
					conn.setAutoCommit(false);
					
					VisitorDAO visitorDao = new VisitorDAO(conn);
					
					String today = Utils.getYear()+"-"+Utils.getMonth()+"-"+Utils.getDay();
					String time = Utils.getTodayWithTime();
					String sessionID = req.getSession().getId();
					
					if (!visitorDao.isExist(u.getUserId(),time))
						visitorDao.insert(u.getUserId(), Constants.LogType.LOGIN.getCode(), time, req.getRemoteAddr());
					
					conn.commit();
				}catch (Exception ff){}
				//valida el nivel del perfil
				if (u.getUserRol().equals(Constants.CODE_USER_ADMINISTRATOR))
					return new ModelAndView("redirect:/dashboard/dashboard.html");
				
				return new ModelAndView("redirect:/public/home.html");
				
			} else {
				session.setFlashMessage(req.getSession(), reg.getMessage("USR0002", session.getLanguage(req.getSession())));
			}

		}catch (Exception e){ 
//			e.printStackTrace();
			log.error("UserController", "home", e.toString());
		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}

		ModelAndView mv = new ModelAndView("login");
		mv.addObject("username", EmailLogin);
		mv.addObject("password", "");
		
		return mv;//new ModelAndView("redirect:/user/login.html");
	}
	
	@RequestMapping("auth.html")
	public ModelAndView auth(
			@RequestParam(value = "user_id", required = false) String userID,
			HttpServletRequest req, HttpServletResponse res){

		Connection conn = DSManager.getConnection();

		userID = Utils.checkNull(userID);
		
		try{
			if (userID.length()==0){
				session.setFlashMessage(req.getSession(), reg.getMessage("USR0004", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("USR0004"));
			}
			
			String certificateS ="MIICizCCAfQCCQCY8tKaMc0BMjANBgkqhkiG9w0BAQUFADCBiTELMAkGA1UEBhMCTk8xEjAQBgNVBAgTCVRyb25kaGVpbTEQMA4GA1UEChMHVU5JTkVUVDEOMAwGA1UECxMFRmVpZGUxGTAXBgNVBAMTEG9wZW5pZHAuZmVpZGUubm8xKTAnBgkqhkiG9w0BCQEWGmFuZHJlYXMuc29sYmVyZ0B1bmluZXR0Lm5vMB4XDTA4MDUwODA5MjI0OFoXDTM1MDkyMzA5MjI0OFowgYkxCzAJBgNVBAYTAk5PMRIwEAYDVQQIEwlUcm9uZGhlaW0xEDAOBgNVBAoTB1VOSU5FVFQxDjAMBgNVBAsTBUZlaWRlMRkwFwYDVQQDExBvcGVuaWRwLmZlaWRlLm5vMSkwJwYJKoZIhvcNAQkBFhphbmRyZWFzLnNvbGJlcmdAdW5pbmV0dC5ubzCBnzANBgkqhkiG9w0BAQEFAAOBjQAwgYkCgYEAt8jLoqI1VTlxAZ2axiDIThWcAOXdu8KkVUWaN/SooO9O0QQ7KRUjSGKN9JK65AFRDXQkWPAu4HlnO4noYlFSLnYyDxI66LCr71x4lgFJjqLeAvB/GqBqFfIZ3YK/NrhnUqFwZu63nLrZjcUZxNaPjOOSRSDaXpv1kb5k3jOiSGECAwEAATANBgkqhkiG9w0BAQUFAAOBgQBQYj4cAafWaYfjBU2zi1ElwStIaJ5nyp/s/8B8SAPK2T79McMyccP3wSW13LHkmM1jwKe3ACFXBvqGQN0IbcH49hu0FKhYFM/GPDJcIHFBsiyMBXChpye9vBaTNEBCtU3KjjyG0hRT2mAQ9h+bkPmOvlEo/aH0xR68Z9hw4PF13w==";
//			
//			AccountSettings accountSettings = new AccountSettings();
//			accountSettings.setCertificate(certificateS);
//
//			Response samlResponse = new Response(accountSettings);
//			samlResponse.loadXmlFromBase64(req.getParameter("SAMLResponse"));
//			samlResponse.setDestinationUrl(req.getRequestURL().toString()); 
//
//			if (samlResponse.isValid()) {
//				System.out.println("CCCCCCCC: valid");
//			} else {
//				System.out.println("CCCCCCCC: invalid");
//			}
			
			UserDAO userDao = new UserDAO(conn);
			UserInfo u = userDao.getUserInfo(Integer.parseInt(userID));
			if (u!=null){
				if (u.getUserStatus() != Integer.parseInt(Constants.DEFAULT_ACTIVE)){
					session.setFlashMessage(req.getSession(), reg.getMessage("USR0006", session.getLanguage(req.getSession())));
					throw new Exception(reg.getMessage("USR0006"));
				}

				if (u.getUserRol().equals(Constants.CODE_USER_ADMINISTRATOR)){
					session.setUserInfo(req.getSession(), u);
					session.setFrontUserInfo(req.getSession(), u);
				}
				else
					session.setFrontUserInfo(req.getSession(), u);
				
				try{
					conn.setAutoCommit(false);
					
					VisitorDAO visitorDao = new VisitorDAO(conn);
					
					String today = Utils.getYear()+"-"+Utils.getMonth()+"-"+Utils.getDay();
					String time = Utils.getTodayWithTime();
					String sessionID = req.getSession().getId();
					
					if (!visitorDao.isExist(u.getUserId(),time))
						visitorDao.insert(u.getUserId(), Constants.LogType.LOGIN.getCode(),time, req.getRemoteAddr());
					
					conn.commit();
				}catch (Exception ff){}
				
				if (u.getUserRol().equals(Constants.CODE_USER_ADMINISTRATOR))
					return new ModelAndView("redirect:/dashboard/dashboard.html");
				
				return new ModelAndView("redirect:/public/home.html");
				
			} else {
				session.setFlashMessage(req.getSession(), reg.getMessage("USR0002", session.getLanguage(req.getSession())));
			}

		}catch (Exception e){ 
//			e.printStackTrace();
			log.error("UserController", "home", e.toString());
		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}

//		ModelAndView mv = new ModelAndView("login");
//		mv.addObject("username", userID);
//		mv.addObject("password", "");
		
//		return mv;
		return new ModelAndView("redirect:/user/login.html");
	}
	
	/**
	 * For outside user.
	 *
	 * @param req
	 * @param res
	 * @return view
	 */
	@RequestMapping("outside.html")
	public ModelAndView outside(
			HttpServletRequest req, HttpServletResponse res){

		Connection conn = DSManager.getConnection();

		try{
			conn.setAutoCommit(false);

			String sessionID = req.getSession().getId();
			String today = Utils.getToday();
			String time = Utils.getTodayWithTime();
			String addr = req.getRemoteAddr();
			
			PublicDAO dao = new PublicDAO(conn);
			UserInfo user = session.getFrontUserInfo(req.getSession());
			if (!dao.isExistVisitor(user.getUserId()))
				dao.insertVisitor(user.getUserId(), Constants.LogType.LOGIN.getCode(), today, addr);
				
	      //user.setNumberEmpl(Constants.OUTSIDE_USER_ID);
			user.setUserName("");
			user.setUserLastName("");
			//session.setFrontUserInfo(req.getSession(), user);
			
			conn.commit();
			
		}catch (Exception e){ 
			log.error("UserController", "savePassword", e.toString());
			
		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}

		return new ModelAndView("redirect:/public/home.html");
	}	

}
