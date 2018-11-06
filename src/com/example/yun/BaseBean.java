package com.example.yun;

import java.io.Serializable;

public class BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 297941478391802241L;
	/**
	 * 鏄惁鎴愬姛锛�0锛氬け璐ワ紝1锛氭垚鍔�
	 */
	private String isSuccess;
	/**
	 * 閿欒浠ｇ爜
	 */
	private String errorCode;
	/**
	 * 杩斿洖鎻忚堪
	 */
	private String message;
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
