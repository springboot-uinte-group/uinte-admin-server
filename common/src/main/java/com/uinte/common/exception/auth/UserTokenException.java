package com.uinte.common.exception.auth;


import com.uinte.common.constant.CommonConstants;
import com.uinte.common.exception.BaseException;

/**
 * 用户token自定义运行时异常类
 */
public class UserTokenException extends BaseException {
    public UserTokenException(String message) {
        super(message, CommonConstants.TOKEN_ERROR_CODE);
    }
}
