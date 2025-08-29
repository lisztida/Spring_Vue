package com.example.springboot.mapper;

import com.example.springboot.entity.Admin;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AdminMapper {

    List<Admin> selectAll(Admin admin);

    void insert(Admin admin);

    @Select("select * from `admin` where username =#{username}")
    Admin selectByUsername(String username);

    void updateById(Admin admin);

    @Delete("delete from admin where id = #{id}")
    void deleteById(Integer id);
}

/**
 * Mapper定义接口的方法
 * Mapper.xml里面去实现方法（SQL语句）
 * 如果Mapper里已经写了，xml里面就不需要写了，不能两处都写
 *
 */

