
package com.uinte.drools.common.exception;

import com.uinte.drools.common.bean.ExceptionType;

/**
 * 业务异常
 *
 * @author admin
 * @since 1.0.0
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 5518947768333882587L;

	private int code;

	private String message;

	private int level;

	public BusinessException(ExceptionType exceptionType) {
		this.code = exceptionType.getCode();
		this.message = exceptionType.getMessage();
		this.level = exceptionType.getLevel();
	}

	public BusinessException(int code, String message, int level) {
		this.code = code;
		this.message = message;
		this.level = level;
	}

	public int getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public int getLevel() {
		return level;
	}

}
