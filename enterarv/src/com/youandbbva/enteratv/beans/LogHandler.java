package com.youandbbva.enteratv.beans;

/**
 * Manage Log.
 * 
 * @author CJH
 *
 */
public class LogHandler {

	private static LogHandler instance = new LogHandler();
	
	/**
	 * Return LogHandler Instance.
	 * 
	 * @return instance
	 */
	public static LogHandler getInstance() {
		return instance;
	}
	
	/**
	 * Destroy LogHandler instance.
	 */
	public static void destroy(){
		instance = null;
	}
	
	
	/**
	 * Error log.
	 * 
	 * @param module
	 * @param part
	 * @param msg ErrorMessage
	 */
	public void error(String module, String part, String msg){
		System.out.println("Error! " + module + "(" + part + ")" + ": " + msg);
	}

	/**
	 * Error log.
	 * 
	 * @param module
	 * @param msg ErrorMessage
	 */
	public void error(String module, String msg){
		System.out.println("Error! " + module + ": " + msg);
	}
	
	/**
	 * Information log.
	 * 
	 * @param module
	 * @param part
	 * @param msg ErrorMessage
	 */
	public void info(String module, String part, String msg){
		System.out.println("Info! " + module + "(" + part + ")" + ": " + msg);
	}

	/**
	 * Information log.
	 * 
	 * @param module
	 * @param msg ErrorMessage
	 */
	public void info(String module, String msg){
		System.out.println("Info! " + module + ": " + msg);
	}
	

}
