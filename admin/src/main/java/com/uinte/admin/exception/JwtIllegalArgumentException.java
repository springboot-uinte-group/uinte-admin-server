package com.uinte.admin.exception;

public class JwtIllegalArgumentException extends Exception {

	private static final long serialVersionUID = 1268609689865489145L;

	public JwtIllegalArgumentException(String s) {
        super(s);
    }
}
