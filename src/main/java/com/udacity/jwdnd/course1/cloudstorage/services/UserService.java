package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * The type User service.
 * @author Sudhir Tyagi
 */
@Service
public class UserService {
    private final HashService hashService;
    private final UserMapper userMapper;

    /**
     * Class constructor.
     *
     * @param hashService the hash service
     * @param userMapper  the user mapper
     */
    public UserService (HashService hashService,UserMapper userMapper){
        this.hashService=hashService;
        this.userMapper=userMapper;
    }

    /**
     * Get user by name user.
     *
     * @param username the username
     * @return the user
     */
    public User getUserByName(String username){
        return  userMapper.getUserByName(username);
    }

    /**
     * Get userid by name integer.
     *
     * @param username the username
     * @return the integer
     */
    public Integer getUseridByName(String username){
        return userMapper.getUseridByName(username);
    }

    /**
     * Is username available boolean.
     *
     * @param username the username
     * @return the boolean
     */
    public boolean isUsernameAvailable(String username) {
        return userMapper.getUserByName(username) == null;
    }

    /**
     * Add user integer.
     *
     * @param user the user
     * @return the integer
     */
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
