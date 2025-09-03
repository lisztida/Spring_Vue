package com.example.springboot.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.springboot.common.Result;
import com.example.springboot.entity.User;
import com.example.springboot.service.UserService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;

    @PostMapping("/add")
    public Result add(@RequestBody User user){ //@RequestBody接受前端传来的json参数
        userService.add(user);
        return Result.success();
    }

    @GetMapping("/selectAll") // 完整请求路径   http://ip:port/user/selectAll
    public Result selectAll(User user){
        List<User> userList=userService.selectAll(user);
        return Result.success(userList);
    }

    /**
     * 分页查询
     * 里面提供至少两个参数
     * pageNum:当前页码
     * pageSize:每页的个数
     */

    @GetMapping("selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             User user){

        PageInfo<User> pageInfo = userService.selectPage(pageNum,pageSize,user);
        return Result.success(pageInfo);
    }

    @PutMapping("/update")
    public Result update(@RequestBody User user){ //@RequestBody接受前端传来的json参数
        userService.update(user);
        return Result.success();
    }
    @DeleteMapping("/delete/{id}")
    public Result del(@PathVariable Integer id){ //@PathVariable接受前端传来的路径参数
        userService.deleteById(id);
        return Result.success();
    }

    @DeleteMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<User> list){ //@RequestBody接受前端传来的json参数
        userService.deleteBatch(list);
        return Result.success();
    }


    /**
     *
     *数据导出
     *（通过输出流的方式导出）
     */
    @GetMapping("/export")
    public void exportData(User user,HttpServletResponse response) throws Exception{
        //1、拿到所有数据
        List<User> list = userService.selectAll(user);
        //2、构建writer对象
        ExcelWriter writer= ExcelUtil.getWriter(true);
        //3、设置中文表头
        writer.addHeaderAlias("username","账号");
        writer.addHeaderAlias("name","名称");
        writer.addHeaderAlias("phone","电话");
        writer.addHeaderAlias("email","邮箱");
        writer.setOnlyAlias(true);
        //注意：默认导出的话即使没填加alias的属性也会被导出，因此可以使用setOnlyAlias
        //4、写出数据到writer
        writer.write(list);
        //5、输出文件名称以及输出流的头信息
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("管理员信息", StandardCharsets.UTF_8);
        response.setHeader("Content-Disposition","attachment;filename=" + fileName + ".xlsx");
        //6、写出到输出流并关闭writer
        ServletOutputStream os = response.getOutputStream();
        writer.flush(os);
        writer.close();
        os.close();
    }


    /**
     * 批量导入
     */
    @PostMapping("/import")
    public Result importData(MultipartFile file) throws  Exception{
       //1、拿到输入流构建reader
        InputStream inputStream =file.getInputStream();
        ExcelReader reader =ExcelUtil.getReader(inputStream);
        //2、通过Reader读取excel里面的数据
        reader.addHeaderAlias("账号","username");
        reader.addHeaderAlias("名称","name");
        reader.addHeaderAlias("电话","phone");
        reader.addHeaderAlias("邮箱","email");
        List<User> list=reader.readAll(User.class);
        //3、将数据写到数据库
        for (User user : list){
            userService.add(user);
        }
        return Result.success();
    }



}
