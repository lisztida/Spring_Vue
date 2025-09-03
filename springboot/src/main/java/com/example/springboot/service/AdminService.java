package com.example.springboot.service;


import cn.hutool.core.util.StrUtil;
import com.example.springboot.entity.Admin;
import com.example.springboot.exception.CustomerException;
import com.example.springboot.mapper.AdminMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class AdminService {


    @Resource
    AdminMapper adminMapper;


    public void add(Admin admin) {
        //根据新的账号查询数据库 是否存在同样账号的数据
        Admin dbAdmin=adminMapper.selectByUsername(admin.getUsername());
        if(dbAdmin!=null){
            throw new CustomerException("账号重复");
        }

        //默认密码
        if(StrUtil.isBlank(admin.getPassword())){
            admin.setPassword("admin");
        }
        adminMapper.insert(admin);
    }

    public void update(Admin admin) {
        adminMapper.updateById(admin);
    }

    public void deleteById(Integer id) {
        adminMapper.deleteById(id);
    }

    public void deleteBatch(List<Admin> list) {
        for (Admin admin : list) {
            this.deleteById(admin.getId());
        }

    }

    public List<Admin> selectAll(Admin admin){
        return adminMapper.selectAll(admin);

    }

    public PageInfo<Admin> selectPage(Integer pageNum, Integer pageSize,Admin admin) {
        //开启分页查询
        PageHelper.startPage(pageNum,pageSize);
        List<Admin> list=adminMapper.selectAll(admin);
        return PageInfo.of(list);
    }



}
