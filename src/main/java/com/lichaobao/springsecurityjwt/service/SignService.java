package com.lichaobao.springsecurityjwt.service;

import com.lichaobao.springsecurityjwt.component.MyUserDetails;

/**
 * @author lichaobao
 * @date 2018/12/22
 * @QQ 1527563274
 */
public interface SignService {
    String login(String username,String password);
}
