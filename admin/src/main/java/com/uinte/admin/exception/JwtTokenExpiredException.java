package com.uinte.admin.exception;

public class JwtTokenExpiredException extends Exception {

	private static final long serialVersionUID = 5695931418366239329L;

	public JwtTokenExpiredException(String s) {
        super(s);
    }
}
