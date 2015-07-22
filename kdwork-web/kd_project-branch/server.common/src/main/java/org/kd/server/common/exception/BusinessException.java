package org.kd.server.common.exception;

public class BusinessException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int code;

	public BusinessException() {
		// TODO Auto-generated constructor stub
	}

	public BusinessException(int code,String message) {
		super(message);
		// TODO Auto-generated constructor stub
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	
}
