package com.youandbbva.enteratv.beans;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.youandbbva.enteratv.Registry;

/**
 *  Handle event when session create or destroy.
 *  save count of Total Session, Max Session, CurrentSession
 *  
 *  @author CJH
 *  
 */

public class SessionListener implements HttpSessionListener {
	
	private int totalSessionCount = 0;
	private int currentSessionCount = 0;
	private int maxSessionCount = 0;
	private final byte[] LOCK = new byte[0];

	/**
	 * When session created.
	 * 		count all session.
	 * 		set default configuration.
	 */
	public void sessionCreated(HttpSessionEvent event) {
		totalSessionCount++;
    	currentSessionCount++;
    	
		HttpSession session = event.getSession();
		synchronized (LOCK) {
			if (currentSessionCount > maxSessionCount) {
				maxSessionCount = currentSessionCount;
			}
		}
		
		Registry registry = Registry.getInstance();
		SessionHandler handler = SessionHandler.getInstance();

		// set timeout.
		int timeout = registry.getIntOfApplication("session.timeout");
		session.setMaxInactiveInterval(timeout);
		
		// set default language
		String language = registry.getStringOfApplication("default.language");
		handler.setLanguage(session, language);
	}
	
	/**
	 * When session destroyed.
	 * 		count down session.
	 */
	public void sessionDestroyed(HttpSessionEvent event) {
	    synchronized (LOCK) {
		    if (currentSessionCount > 0)
	            currentSessionCount--;
		}
	}
	
	/**
	 * Get Total Session.
	 * 
	 * @return count
	 */
	public int getTotalSessionCount() {
		return totalSessionCount;
	}
	
	/**
	 * Get Current Session.
	 * 
	 * @return count
	 */
	public int getCurrentSessionCount() {
		return currentSessionCount;
	}
	
	/**
	 * Get Max Session.
	 * 
	 * @return count
	 */
	public int getMaxSessionCount() {
		return maxSessionCount;
	}
}