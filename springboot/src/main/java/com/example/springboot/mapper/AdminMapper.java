package com.example.springboot.mapper;

import com.example.springboot.entity.Admin;

import java.util.List;

public interface AdminMapper {

    List<Admin> selectAll(Admin admin);


}

/**
 * Mapper定义接口的方法
 * Mapper.xml里面去实现方法（SQL语句）
 */

