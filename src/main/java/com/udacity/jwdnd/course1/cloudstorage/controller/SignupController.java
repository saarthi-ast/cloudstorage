package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import static com.udacity.jwdnd.course1.cloudstorage.constants.ApplicationConstants.*;

@Controller
public class SignupController {
    private UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(SIGNUP_MAPPING)
    public String showSignupPage(@ModelAttribute(USER_FORM) User user, Model model) {
        return SIGNUP;
    }

    @PostMapping(SIGNUP_MAPPING)
    public String signup(@ModelAttribute(USER_FORM) User user, Model model) {
        String signupError = null;
        if (!userService.isUsernameAvailable(user.getUsername())) {
            signupError = SIGNUP_DUPLICATE_USERNAME;
            model.addAttribute(USER_FORM, user);
            model.addAttribute(FIRSTNAME, user.getFirstname());
            model.addAttribute(LASTNAME, user.getLastname());
            model.addAttribute(SIGNUP_ERROR, signupError);
            return SIGNUP;
        } else {
            Integer userId = userService.addUser(user);
            if (userId < 0) {
                signupError = SIGNUP_GENERIC_ERROR;
                model.addAttribute(SIGNUP_ERROR, signupError);
                return SIGNUP;
            }
            user.setFirstname(user.getUsername());
            model.addAttribute(SIGNUP_SUCCESS, SIGNUP_SUCCESS_MSG);
            model.addAttribute(USER_FORM, user);
            return SIGNUP;
        }
    }
}
