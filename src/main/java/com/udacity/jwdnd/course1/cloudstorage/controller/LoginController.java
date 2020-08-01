package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.ApplicationForm;
import com.udacity.jwdnd.course1.cloudstorage.model.LoginForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import static com.udacity.jwdnd.course1.cloudstorage.constants.ApplicationConstants.*;

@Controller
public class LoginController {


    @GetMapping(LOGIN_MAPPING)
    public String showLoginPage(@ModelAttribute("loginForm") LoginForm loginForm, Model model) {
        System.out.println("Entered showLoginPage method");
        model.addAttribute("applicationForm",new ApplicationForm());
        return LOGIN;
    }
}
