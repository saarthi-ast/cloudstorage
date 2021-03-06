package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * The type Authentication service.
 * @author Sudhir Tyagi
 */
@Service
public class AuthenticationService implements AuthenticationProvider {
    private final UserService userService;
    private final HashService hashService;

    /**
     * Class constructor.
     *
     * @param userService the user service
     * @param hashService the hash service
     */
    public AuthenticationService(UserService userService, HashService hashService){
        this.hashService = hashService;
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication){
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        User savedUser = userService.getUserByName(username);
        if(null != savedUser){
            String encodedSalt = savedUser.getSalt();
            String hashedPwd = hashService.getHashedValue(password,encodedSalt);
            if(hashedPwd.equals(savedUser.getPassword())){
                return new UsernamePasswordAuthenticationToken(username,hashedPwd,new ArrayList<>());
            }
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    /**
     * Get logged in user string.
     *
     * @param authentication the authentication
     * @return the string
     */
    public String getLoggedInUser(Authentication authentication){
        return authentication.getName();
    }
}
