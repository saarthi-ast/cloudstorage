package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    //find records with name
    @Select("Select * from users where username=#{username}")
    public User getUserByName(String username);

    //insert new user
    @Insert("Insert into users (username,salt,password,firstname,lastname) values (" +
            "#{username},#{salt},#{password},#{firstname},#{lastname})")
    @Options(useGeneratedKeys = true,keyProperty = "userid")
    public Integer insertUser(User user);

    //find userid by name
    @Select("Select userid from users where username=#{username}")
    public Integer getUseridByName(String username);
}
