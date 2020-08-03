package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

/**
 * The interface User mapper.
 * @author Sudhir Tyagi
 */
@Mapper
public interface UserMapper {
    /**
     * Gets user by name.
     *
     * @param username the username
     * @return the user by name
     */
    @Select("Select * from users where username=#{username}")
    User getUserByName(String username);

    /**
     * Insert user integer.
     *
     * @param user the user
     * @return the integer
     */
    @Insert("Insert into users (username,salt,password,firstname,lastname) values (" +
            "#{username},#{salt},#{password},#{firstname},#{lastname})")
    @Options(useGeneratedKeys = true,keyProperty = "userid")
    Integer insertUser(User user);

    /**
     * Gets userid by name.
     *
     * @param username the username
     * @return the userid by name
     */
    @Select("Select userid from users where username=#{username}")
    Integer getUseridByName(String username);
}
