package com.wen.awenboot.interceptor;

import com.wen.awenboot.service.OpenidServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wen
 * @version 1.0
 * @date 2021/2/6 23:28
 */
@Service
@Slf4j
public class SetTokenInterceptor implements HandlerInterceptor {
    @Autowired
    private OpenidServiceImpl openidService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        request.setAttribute("token", token);
        openidService.setToken(token);
        return true;
    }


}
