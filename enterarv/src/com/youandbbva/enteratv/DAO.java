package com.youandbbva.enteratv;

import java.sql.Connection;

import com.youandbbva.enteratv.beans.LogHandler;

/**
 * 
 * The base class of All Dao Class.
 * 
 * @author CJH
 *
 */

public class DAO {

	public static LogHandler log = LogHandler.getInstance();
	public static Registry reg = Registry.getInstance();
	
	public DAO() {
		// TODO Auto-generated constructor stub
	}
	
}
