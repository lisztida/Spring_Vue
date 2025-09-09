package com.example.springboot.controller;

import cn.hutool.core.io.FileUtil;
import com.example.springboot.common.Result;
import com.example.springboot.exception.CustomerException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 处理文件上传下载相关的接口
 */



@RestController
@RequestMapping("/files")
public class FileController {
    /**
     * 文件上传
     */
    @PostMapping("/upload")
    public Result upload(@RequestParam("file")MultipartFile file) throws Exception{
    //找到文件的位置
    String filePath = System.getProperty("user.dir") + "/files/";
    if(!FileUtil.isDirectory(filePath)){
        FileUtil.mkdir(filePath);
    }
    byte[] bytes = file.getBytes();
    String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();//文件的原始名称，加上当前毫秒是防止名称重复导致覆盖
    //写入文件
    FileUtil.writeBytes(bytes, filePath +  fileName);
    String url ="http://localhost:9999/files/download/" + fileName;
    return Result.success(url);
    }


    /**
     *文件下载接口
     * 下载路径 http://localhost:9999/files/download/1.jpg
     */


    @GetMapping("/download/{fileName}")
    // get请求：能通过浏览器直接访问
    public void download(@PathVariable String fileName, HttpServletResponse response)throws Exception {
        //1、找到文件的位置
        String filePath = System.getProperty("user.dir") + "/files/";//获取到当前项目的根路径(Codes的绝对路径) D:\Vue_Projs\Codes
        String realPath = filePath + fileName; //D:\Vue_Projs\Codes\files\1.jpg
        boolean exist = FileUtil.exist(realPath);
        if(!exist){
            throw new CustomerException("文件不存在");
        }
        //读取文件的字节流
        byte[] bytes = FileUtil.readBytes(realPath);
        ServletOutputStream os = response.getOutputStream();
        // 输出流对象把文件写出到客户端
        os.write(bytes);
        os.flush();
        os.close();



    }



}
