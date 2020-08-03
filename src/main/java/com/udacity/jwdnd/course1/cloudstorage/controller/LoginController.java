package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.ApplicationForm;
import com.udacity.jwdnd.course1.cloudstorage.model.LoginForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import static com.udacity.jwdnd.course1.cloudstorage.constants.ApplicationConstants.LOGIN;
import static com.udacity.jwdnd.course1.cloudstorage.constants.ApplicationConstants.LOGIN_MAPPING;

/**
 * The type Login controller.
 * @author Sudhir Tyagi
 */
@Controller
public class LoginController {
    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    /**
     * Show login page string.
     *
     * @param loginForm the login form
     * @param model     the model
     * @return the string
     */
    @GetMapping(LOGIN_MAPPING)
    public String showLoginPage(@ModelAttribute("loginForm") LoginForm loginForm, Model model) {
        LOG.debug("Entered showLoginPage method");
        model.addAttribute("applicationForm",new ApplicationForm());
        return LOGIN;
    }
}
