package com.youandbbva.enteratv;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.sql.DataSource;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.youandbbva.enteratv.beans.LogHandler;

/**
 * DataSource instance.
 * 
 * @author CJH
 *
 */

public class DSManager {

	// Instance
	private static DSManager instance = new DSManager();
	private static DataSource ds;

	private LogHandler log = LogHandler.getInstance();
	private static Registry reg = Registry.getInstance();

	/**
	 * Constructor.
	 * 		initialize.
	 */
	private DSManager() {
		// TODO Auto-generated constructor stub
//		init();
	}

	/**
	 * Initialize.
	 */
	private void init() {
		// TODO Auto-generated method stub
		ds = getDataSource();
	}

	private synchronized DataSource getDataSource() {
		// TODO Auto-generated method stub
		MysqlDataSource ds = new MysqlDataSource();
		try{
			ds.setURL(reg.getStringOfApplication("database.url"));
			ds.setUser(reg.getStringOfApplication("database.user"));
			ds.setPassword(reg.getStringOfApplication("database.password"));

			Connection conn = ds.getConnection();
			conn.close();
		}catch (Exception e){
			log.error("DSManager", e.toString());
		}

		return ds;
	}

	public static Connection getConnection(){
		try {
			String url = null;
			Class.forName(reg.getStringOfApplication("database.driver"));
			url = reg.getStringOfApplication("database.url");
			Connection conn = DriverManager.getConnection(url);
			return conn;
		} catch (Exception e) { }

		return null;
	}

	/**
	 * Return instance.
	 * 
	 * @return instance
	 */
	public static DSManager getInstance(){
		return instance;
	}

	/**
	 * Destory instance.
	 */
	public static void destroy(){
		instance = null;
	}

}
