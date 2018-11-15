package com.cubicpark.mechanic.exception;

import com.cubicpark.mechanic.common.helper.CommonErrorCode;

/**
 * 
 * 〈一句话功能简述〉<br> 
 * 业务异常
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 8296584679267569883L;

	private String errorId;

	private String errorMessage;

	public ServiceException() {
		super();
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(String errorId, String errorMessage) {
		this.errorId = errorId;
		this.errorMessage = errorMessage;
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public ServiceException(CommonErrorCode commonErrorCode){
		//this.errorMessage = commonErrorCode.getDesc();
		super(commonErrorCode.getDesc());
		this.errorId = commonErrorCode.getValue();
	}

	public String getErrorId() {
		return errorId;
	}

	public void setErrorId(String errorId) {
		this.errorId = errorId;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
