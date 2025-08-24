package com.example.springboot.exception;

import com.example.springboot.common.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 全局异常捕获器
 */

@ControllerAdvice("com.example.springboot.controller")
public class GlobalExceptionHandler{
    private static final Logger log= LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(Exception.class)
    @ResponseBody //将result对象转换成json格式
    public Result error (Exception e){
        log.error("系统异常",e);//控制台打印错误信息
        return Result.error("系统异常");
    }
    @ExceptionHandler(CustomerException.class)
    @ResponseBody //将result对象转换成json格式
    public Result customerError (CustomerException e){
        log.error("自定义异常",e);//控制台打印错误信息
        return Result.error(e.getCode(),e.getMsg());
    }
}