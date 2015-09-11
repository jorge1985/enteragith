package com.youandbbva.enteratv.beans;


/**
 * Handle all exceptions.
 * 
 * @author CJH
 * 
 */
public class ExceptionHandler extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String errCode;
	private String errMsg;

	/**
	 * Constructor.
	 * 
	 * @param errCode
	 * @param errMsg
	 */
	public ExceptionHandler(String errCode, String errMsg) {
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

}
