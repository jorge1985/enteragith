package com.youandbbva.enteratv;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.youandbbva.enteratv.beans.LogHandler;
import com.youandbbva.enteratv.beans.SessionHandler;

/**
 * 
 * The base class of All Action.
 * 
 * @author CJH
 * 
 */

public class Controller {
	
	public SessionHandler session = SessionHandler.getInstance();
	public Registry reg = Registry.getInstance();
	public LogHandler log = LogHandler.getInstance();

	/**
	 * Put the value to Response Object.
	 * 
	 * @param result Response Object
	 * @param field
	 * @param value
	 * @return Response Object
	 */
	public JSONObject setResponse(JSONObject result, String field, Object value){
		try{
			result.put(field, value);
		}catch (Exception e){
			log.error("Controller", "setResponse", e.toString());
		}
		return result;
	}
	
	/**
	 * Print data to Response's OutputStream. Send data to Client side. 
	 * 
	 * @param res
	 * @param result
	 */
	public void response(HttpServletResponse res, JSONObject result){
		res.setContentType("text/plain;charset=UTF-8");
		try {
			res.getWriter().print(result.toString());
		} catch (Exception e) { 
			log.error("Controller", "response", e.toString());
		}
	}
	
}
