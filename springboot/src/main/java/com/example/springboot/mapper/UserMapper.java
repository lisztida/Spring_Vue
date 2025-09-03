package com.example.springboot.mapper;

import com.example.springboot.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {

    List<User> selectAll(User user);

    void insert(User user);

    @Select("select * from `user` where username =#{username}")
    User selectByUsername(String username);

    void updateById(User user);

    @Delete("delete from user where id = #{id}")
    void deleteById(Integer id);
}

/**
 * Mapper定义接口的方法
 * Mapper.xml里面去实现方法（SQL语句）
 * 如果Mapper里已经写了，xml里面就不需要写了，不能两处都写
 *
 */

