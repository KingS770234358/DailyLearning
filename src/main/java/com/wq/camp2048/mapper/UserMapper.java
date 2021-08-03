package com.wq.camp2048.mapper;

import com.wq.camp2048.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT * from user")
    List<User> getAllUser();
}
