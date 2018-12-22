package com.lichaobao.springsecurityjwt.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @author lichaobao
 * @date 2018/12/21
 * @QQ 1527563274
 */
@Component
public class JwtUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);
    private static final String SECRET = "secret";
    private static final Long EXPIRATION = 604800L;

    public String generateToken(String  username){
        try {
            String jwt =  JWT.create()
                    .withIssuer("lichaobao")
                    .withSubject("user")
                    .withClaim("username",username)
                    .withExpiresAt(new Date(System.currentTimeMillis()+EXPIRATION))
                    .sign(Algorithm.HMAC256(SECRET));
            LOGGER.info("create JWT for user :{} ->jwt :{}",username,jwt);
            return jwt;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LOGGER.info("An error occur ->{}",e.getMessage());
            return null;
        }
    }
    public String  getUserNameFromToken(String token){
        try {

            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            String  username = decodedJWT.getClaim("username").asString();
            LOGGER.info("check username from token : {}",username);
            return username;
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
            LOGGER.info("An error occur ->{}",e.getMessage());
            return null;
        }
    }
}
