package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.entity.Account;
import com.example.springboot.entity.Admin;
import com.example.springboot.entity.User;
import com.example.springboot.exception.CustomerException;
import com.example.springboot.service.AdminService;
import com.example.springboot.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController //定义controller
public class WebController {
    // 表示这是一个get请求的接口

    @Resource
    AdminService adminService;
    @Resource
    UserService userService;

    @GetMapping("/") //接口的路径，全局唯一，()中的/表示路由，
    public Result hello()
    {
        return Result.success("hello小毛");
    }


    //每次修改完代码后一定要重启后端(springboot)
    //404:根据路径没有请求到接口
    //要学会在网页开发者模式中的网络项中查看网络请求

    @PostMapping("/login")
    public Result login(@RequestBody Account account){
        Account dbAccount =null;
        if("ADMIN".equals(account.getRole())) {
            dbAccount=adminService.login(account);
        } else if ("USER".equals(account.getRole())){
            dbAccount =userService.login(account);
        } else {
            throw new CustomerException("非法请求");
        }

        return Result.success(dbAccount);
    }


    @PostMapping("/register")
    public Result register(@RequestBody User user){
        userService.register(user);
        return Result.success();
    }


}
