package com.uinte.common.msg.auth;


import com.uinte.common.constant.CommonConstants;
import com.uinte.common.msg.BaseResponse;

/**
 * token错误响应类
 */
public class TokenErrorResponse extends BaseResponse {
    public TokenErrorResponse(String message) {
        super(CommonConstants.TOKEN_ERROR_CODE, message);
    }
}
