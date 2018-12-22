package com.lichaobao.springsecurityjwt.controller;

import com.lichaobao.springsecurityjwt.http.ServiceVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lichaobao
 * @date 2018/12/22
 * @QQ 1527563274
 */
@RestController
public class TestController {

    @GetMapping("/a/test")
    public ServiceVO testa(){
        return new ServiceVO();
    }
    @GetMapping("/b/test")
    public ServiceVO testb(){
        return new ServiceVO();
    }
    @GetMapping("/test/admin")
    public ServiceVO testadmin(){
        return new ServiceVO();
    }
}
