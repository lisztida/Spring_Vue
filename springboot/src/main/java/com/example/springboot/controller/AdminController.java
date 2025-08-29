package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.entity.Admin;
import com.example.springboot.service.AdminService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    AdminService adminService;

    @PostMapping("/add")
    public Result add(@RequestBody Admin admin){ //@RequestBody接受前端传来的json参数
        adminService.add(admin);
        return Result.success();
    }

    @GetMapping("/selectAll") // 完整请求路径   http://ip:port/admin/selectAll
    public Result selectAll(){
        List<Admin> adminList=adminService.selectAll();
        return Result.success(adminList);
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
                             Admin admin){

        PageInfo<Admin> pageInfo = adminService.selectPage(pageNum,pageSize,admin);
        return Result.success(pageInfo);
    }

    @PutMapping("/update")
    public Result update(@RequestBody Admin admin){ //@RequestBody接受前端传来的json参数
        adminService.update(admin);
        return Result.success();
    }
    @DeleteMapping("/delete/{id}")
    public Result del(@PathVariable Integer id){ //@PathVariable接受前端传来的路径参数
        adminService.deleteById(id);
        return Result.success();
    }

    @DeleteMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Admin> list){ //@RequestBody接受前端传来的json参数
        adminService.deleteBatch(list);
        return Result.success();
    }


}
