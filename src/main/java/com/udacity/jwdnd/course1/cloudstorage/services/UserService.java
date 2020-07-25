package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {
    private HashService hashService;
    private UserMapper userMapper;


    public UserService (HashService hashService,UserMapper userMapper){
        this.hashService=hashService;
        this.userMapper=userMapper;
    }

    //get user by name
    public User getUserByName(String username){
        User userByName = userMapper.getUserByName(username);
        return userByName;
    }

    //find userid by name
    public Integer getUseridByName(String username){
        return userMapper.getUseridByName(username);
    }

    //check if username is already taken
    public boolean isUsernameAvailable(String username) {
        return userMapper.getUserByName(username) == null;
    }

    // add user to DB
    public Integer addUser(User user){
        Integer userId;
        if(isUsernameAvailable(user.getUsername())){
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            String encodedSalt = Base64.getEncoder().encodeToString(salt);
            String hashedPwd = hashService.getHashedValue(user.getPassword(),encodedSalt);
            user.setPassword(hashedPwd);
            user.setSalt(encodedSalt);
            userId = userMapper.insertUser(user);
        }else{
            userId = null;
        }
        return userId;
    }
}
