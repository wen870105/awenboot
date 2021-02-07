package com.wen.awenboot.interceptor;

import com.alibaba.fastjson.JSON;
import com.wen.awenboot.controller.response.Result;
import com.wen.awenboot.service.OpenidServiceImpl;
import com.wen.awenboot.vo.UserInfoVo;
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
public class OpenidInterceptor implements HandlerInterceptor {
    @Autowired
    private OpenidServiceImpl openidService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserInfoVo accessLog = openidService.getUserInfoVo();
        if (accessLog == null) {
            handleFalseResponse(response);
            return false;
        }
        return true;
    }

    private void handleFalseResponse(HttpServletResponse response)
            throws Exception {
        Result error = Result.error("token无效");
        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(JSON.toJSONString(error));
        response.getWriter().flush();
    }

}
