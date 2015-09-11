package com.youandbbva.enteratv.beans;

import javax.servlet.*;

import com.youandbbva.enteratv.DSManager;
import com.youandbbva.enteratv.DataCache;
import com.youandbbva.enteratv.Registry;

/**
 * Handle event when servlet initialize or destroy.
 * 
 * @author CJH
 * 
 */

public class ContextListener implements ServletContextListener {

	/**
	 * When servlet created
	 * 
	 * @param event
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		// TODO Auto-generated method stub
		System.out.println("Servlet Initialized!");
		init();
	}

	/**
	 * When servlet destroyed
	 * 
	 * @param event
	 */
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		System.out.println("Servlet Destroyed!");
		destroy();
	}
	
	/**
	 * Init all instance.
	 */
	private void init(){
		Registry.getInstance();
		SessionHandler.getInstance();
		LogHandler.getInstance();
		DSManager.getInstance();
		DataCache.getInstance();
	}
	
	/**
	 * Destory all instance.
	 */
	private void destroy(){
		Registry.destroy();
		SessionHandler.destroy();
		LogHandler.destroy();
		DSManager.destroy();
		DataCache.destroy();
	}

}