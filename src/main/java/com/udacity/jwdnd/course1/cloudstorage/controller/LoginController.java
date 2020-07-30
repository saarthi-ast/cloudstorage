package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.ApplicationForm;
import com.udacity.jwdnd.course1.cloudstorage.model.LoginForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class LoginController {
    public final String LOGIN = "login";

    @GetMapping("/login")
    public String showLoginPage(@ModelAttribute("loginForm") LoginForm loginForm, Model model) {
        System.out.println("Entered showLoginPage method");
        model.addAttribute("applicationForm",new ApplicationForm());
        return LOGIN;
    }
}
