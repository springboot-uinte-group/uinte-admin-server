package com.uinte.admin.util.user;

import com.uinte.admin.config.UserAuthConfig;
import com.uinte.admin.util.IJWTInfo;
import com.uinte.admin.util.helper.JWTHelper;
import com.uinte.common.exception.auth.UserTokenException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * token 工具
 */
@Component
public class JwtTokenUtil {

    //注入
    @Autowired
    private UserAuthConfig userAuthConfig;

    /**
     * 根据用户的信息构造jwt
     * @param jwtInfo
     * @return
     * @throws Exception
     */
    public String generateToken(IJWTInfo jwtInfo) throws Exception {
        return JWTHelper.generateToken(jwtInfo, userAuthConfig.getUserPriKey(),userAuthConfig.getExpire());
    }


    /**
     * 根据用户的token 获取用户信息
     * @param token
     * @return
     * @throws Exception
     */
    public IJWTInfo getInfoFromToken(String token) throws Exception {
        try {
            return JWTHelper.getInfoFromToken(token, userAuthConfig.getUserPubKey());
        }catch (ExpiredJwtException ex){
            throw new UserTokenException("User token expired!");
        }catch (SignatureException ex){
            throw new UserTokenException("User token signature error!");
        }catch (IllegalArgumentException ex){
            throw new UserTokenException("User token is null or empty!");
        }
    }
}

