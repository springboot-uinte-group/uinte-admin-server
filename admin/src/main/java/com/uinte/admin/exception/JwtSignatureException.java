package com.uinte.admin.exception;

public class JwtSignatureException extends Exception {

	private static final long serialVersionUID = 2807800799696174440L;

	public JwtSignatureException(String s) {
        super(s);
    }
}
