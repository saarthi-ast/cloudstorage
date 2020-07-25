package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.LoginForm;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {
    public final String LOGIN = "login";

    @GetMapping("/login")
    public String showLoginPage(@ModelAttribute("loginForm") LoginForm loginForm, Model model) {
        System.out.println("Entered showLoginPage method");
        return LOGIN;
    }
}
