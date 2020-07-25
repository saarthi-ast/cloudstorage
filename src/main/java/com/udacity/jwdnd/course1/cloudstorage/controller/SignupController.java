package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SignupController {
    private UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String showSignupPage(@ModelAttribute("userForm") User user, Model model) {

        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("userForm") User user, Model model) {
        String signupError = null;
        if (!userService.isUsernameAvailable(user.getUsername())) {
            signupError = "This username is already in use. Please try with a different username.";
            model.addAttribute("userForm", user);
            model.addAttribute("firstname", user.getFirstname());
            model.addAttribute("lastname", user.getLastname());
            model.addAttribute("signupError", signupError);
            return "signup";
        } else {
            Integer userId = userService.addUser(user);
            if (userId < 0) {
                signupError = "The signup process could not be completed. Please try again in sometime.";
                model.addAttribute("signupError", signupError);
                return "signup";
            }
            user.setFirstname(user.getUsername());
            model.addAttribute("signupSuccess", "You successfully signed up! Please login to continue.");
            model.addAttribute("userForm", user);
            return "signup";
        }
    }
}
