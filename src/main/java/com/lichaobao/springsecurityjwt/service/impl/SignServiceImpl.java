package com.lichaobao.springsecurityjwt.service.impl;

import com.lichaobao.springsecurityjwt.service.SignService;
import com.lichaobao.springsecurityjwt.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author lichaobao
 * @date 2018/12/22
 * @QQ 1527563274
 */
@Service
public class SignServiceImpl implements SignService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SignService.class);
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * 登陆 登陆出现错误抛出错误 用catch接受即可
     * @param username 用户名
     * @param password 密码
     * @return String token
     */
    @Override
    public String login(String username, String password) {
        String token = null;
        /**
         * 封装 注意密码加密
         */
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username,passwordEncoder.encode(password));
        try{
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            /**
             * 加载数据库中的用户名密码 主要逻辑为UserdetailsServices中的代码
             */
            userDetailsService.loadUserByUsername(username);
            token = jwtUtils.generateToken(username);
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.info("认证失败 ：{}",e.getMessage());
        }
        return token;
    }
}
