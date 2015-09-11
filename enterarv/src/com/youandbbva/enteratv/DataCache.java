package com.youandbbva.enteratv;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * Load all data from database. (pre-loaded)
 *		like code.
 * 
 * @author CJH
 *
 */

public class DataCache {

	// Instance
	private static DataCache instance = new DataCache();
	
	ArrayList division;
	ArrayList city;
	ArrayList code;
	
	/**
	 * Constructor.
	 * 		initialize.
	 */
	private DataCache() {
		// TODO Auto-generated constructor stub
		division = new ArrayList();
		city = new ArrayList();
		code = new ArrayList();
		
		init();
	}

	/**
	 * Initialize.
	 */
	private void init() {
		// TODO Auto-generated method stub
		Connection conn = DSManager.getConnection();
		
		try{
			
		}catch (Exception e){
			
		}finally{
			try{
				conn.close();
			}catch (Exception ff){}
		}
	}
	
	/**
	 * Return instance.
	 * 
	 * @return instance
	 */
	public static DataCache getInstance(){
		return instance;
	}
	
	/**
	 * Destory instance.
	 */
	public static void destroy(){
		instance = null;
	}
	
}
