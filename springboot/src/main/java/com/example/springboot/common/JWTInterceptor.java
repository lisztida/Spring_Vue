package com.example.springboot.common;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.server.HttpServerRequest;
import cn.hutool.http.server.HttpServerResponse;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.springboot.entity.Account;
import com.example.springboot.exception.CustomerException;
import com.example.springboot.service.AdminService;
import com.example.springboot.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JWTInterceptor implements HandlerInterceptor {

    @Resource
    AdminService adminService;
    @Resource
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1、从请求头拿到token
        String token = request.getHeader("token");
        if (StrUtil.isEmpty(token)) {
            //如果没拿到，再从参数里拿一次
            token = request.getParameter("token");
        }
        //2、认证token
        if (StrUtil.isBlank(token)) {
            throw new CustomerException("401", "无权限操作");
        }
        Account account = null;
        try {
            //拿到token的载荷数据
            String audience = JWT.decode(token).getAudience().get(0);
            String[] split = audience.split("-");
            String userId = split[0];
            String role = split[1];
            //根据token解析出来的userId去对应的表查询用户信息
            if ("ADMIN".equals(role)) {
                account = adminService.selectById(userId);
            } else if ("USER".equals(role)) {
                account = userService.selectById(userId);
            }

        }
        catch (Exception e){
            throw new CustomerException("401", "无权限操作");
        }
        if (account == null) {
            throw new CustomerException("401", "无权限操作");
        }

        //验证签名
        try {
            JWTVerifier jwtVerifier =JWT.require(Algorithm.HMAC256(account.getPassword())).build();
            jwtVerifier.verify(token);
        }
        catch (Exception e){
            throw new CustomerException("401","无权限操作");
        }
        return true;
    }
}