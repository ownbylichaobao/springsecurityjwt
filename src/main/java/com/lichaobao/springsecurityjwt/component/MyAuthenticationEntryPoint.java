package com.lichaobao.springsecurityjwt.component;

import com.lichaobao.springsecurityjwt.http.ErrorCode;
import com.lichaobao.springsecurityjwt.http.ServiceVO;
import com.lichaobao.springsecurityjwt.utils.JsonUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lichaobao
 * @date 2018/12/22
 * @QQ 1527563274
 */
@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse response,
                         AuthenticationException e) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println(JsonUtil.objectToJson(new ServiceVO(ErrorCode.ERROR_CE)));
        response.getWriter().flush();
    }
}
