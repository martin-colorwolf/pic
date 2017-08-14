package com.wang.out.common;

import com.google.gson.Gson;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by Martin.Wang on 2017/8/7.
 */
public class TokenInterceptor extends BaseDao implements HandlerInterceptor {
    @Override
    /*
    * 进入控制器前执行
    * */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ResultPojo resultPojo = new ResultPojo();
        HandlerMethod method = (HandlerMethod) handler;
        // 获取请求的URL
        String url = request.getRequestURI();
        // 获取到目标方法token（用与判断该方法是否需求进行token验证）
        Token token = method.getMethodAnnotation(Token.class);
        // 判断请求目标方法是否需求进行token验证，如果不需要则放过。
        if (token != null) {
            String token1 = request.getHeader("Token");
            if (token1 != null) {
                return true;
            } else {
                conn = getConnection();

                resultPojo.setStatus(114);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write(new Gson().toJson(resultPojo));
                return false;

            }
        } else {
            return true;
        }


    }

    /*
    * 进入控制器后执行
    * */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /*
    * 最后之前
    * */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}


