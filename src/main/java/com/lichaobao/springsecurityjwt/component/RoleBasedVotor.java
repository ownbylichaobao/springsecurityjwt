package com.lichaobao.springsecurityjwt.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author lichaobao
 * @date 2018/12/22
 * @QQ 1527563274
 */
public class RoleBasedVotor implements AccessDecisionVoter {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleBasedVotor.class);
    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;//根据自己的逻辑修改  不能直接return false否则验证不通过
    }

    /**
     * 主要验证逻辑
     * ROLE_ANONYMOUS 代表所有人可以访问 这是 spring security 自动生成的 可以自定义
     * @param authentication 用户信息
     * @param o  可以从这里拿到url
     * @param collection 访问资源需要的权限在本例中由于我们将url作为验证依据所以为用到collection
     * @return ACCESS_DENIED(-1)无权限 ACCESS_GRANTED（1）有权限
     */
    @Override
    public int vote(Authentication authentication, Object o, Collection collection) {
        FilterInvocation fi = (FilterInvocation) o;
        String url = fi.getRequestUrl();
        LOGGER.info("url :{}",url);
        if(authentication == null){
            return ACCESS_DENIED;
        }
        Collection<? extends GrantedAuthority> authorities = extractAuthorities(authentication);
        Iterator iterator = authorities.iterator();
        while (iterator.hasNext()){
            GrantedAuthority ga = (GrantedAuthority) iterator.next();
            LOGGER.info(ga.getAuthority());
            if(equalsurl(url,ga.getAuthority())||"ROLE_ANONYMOUS".equals(ga.getAuthority())){
                return ACCESS_GRANTED;
            }
        }
        return ACCESS_DENIED;
    }

    @Override
    public boolean supports(Class aClass) {
        return true;//根据自己的逻辑修改  不能直接return false否则验证不通过
    }

    /**
     * 获得用户权限信息
     * @param authentication
     * @return
     */
    Collection<? extends GrantedAuthority> extractAuthorities(
            Authentication authentication) {
        LOGGER.info("extractAuthorites:{}",authentication.getAuthorities());
        return authentication.getAuthorities();
    }

    /**
     * 比较权限 权限 /** 代表 以下所有能访问 /* 代表以下一级能访问 如 用户权限为 /test/** 则能访问 /test/a /test/b/c
     * 如用户权限为 /test/* 则能访问 /test/a 而 /test/b/c则不能访问
     * @param url 访问的url
     * @param urlpermission 拥有的权限
     * @return boolean
     */
     static boolean equalsurl(String url,String urlpermission) {
        url = url.startsWith("/") ? url.substring(1):url;
        urlpermission = urlpermission.startsWith("/")?urlpermission.substring(1):urlpermission;
        if("**".equals(urlpermission)){
            return true;
        }else if("*".equals(urlpermission)){
            return url.split("/").length == 1;
        }
        else if(urlpermission.endsWith("/**")){
            String afterUrl =  urlpermission.substring(0,urlpermission.length()-3);
            return url.startsWith(afterUrl);
        }else if(urlpermission.endsWith("/*")){
            String afterUrl = urlpermission.substring(0,urlpermission.length()-2);
            String[] urlPiece = url.split("/");
            return url.startsWith(afterUrl)&&urlPiece.length == 2;
        }
        return url.equals(urlpermission);
    }
}
