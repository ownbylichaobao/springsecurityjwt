package com.lichaobao.springsecurityjwt.component;

import com.lichaobao.springsecurityjwt.http.ErrorCode;
import com.lichaobao.springsecurityjwt.http.ServiceVO;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
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
public class MyAccessDeineHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse response,
                       AccessDeniedException e) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().println(new ServiceVO(ErrorCode.ACCESS_DENIED));
        response.getWriter().flush();
    }
}
