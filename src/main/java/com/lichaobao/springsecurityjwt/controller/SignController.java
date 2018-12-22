package com.lichaobao.springsecurityjwt.controller;

import com.lichaobao.springsecurityjwt.component.MyUserDetails;
import com.lichaobao.springsecurityjwt.http.ErrorCode;
import com.lichaobao.springsecurityjwt.http.ServiceVO;
import com.lichaobao.springsecurityjwt.service.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lichaobao
 * @date 2018/12/22
 * @QQ 1527563274
 */
@RestController
public class SignController {
    @Autowired
    SignService signService;
    @PostMapping("/login")
    public ServiceVO login(@RequestBody MyUserDetails myUserDetails){
        String token = signService.login(myUserDetails.getUsername(),myUserDetails.getPassword());
        return token == null ? new ServiceVO(ErrorCode.SYSTEM_ERROR):new ServiceVO(token);
    }
}
