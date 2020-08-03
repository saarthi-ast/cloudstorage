package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import static com.udacity.jwdnd.course1.cloudstorage.constants.ApplicationConstants.*;

/**
 * The type Signup controller.
 * @author Sudhir Tyagi
 */
@Controller
public class SignupController {
    private static final Logger LOG = LoggerFactory.getLogger(SignupController.class);

    private final UserService userService;

    /**
     * Class constructor.
     *
     * @param userService the user service
     */
    public SignupController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Show signup page string.
     *
     * @param user  the user
     * @param model the model
     * @return the string
     */
    @GetMapping(SIGNUP_MAPPING)
    public String showSignupPage(@ModelAttribute(USER_FORM) User user, Model model) {
        LOG.debug("signup page requested");
        return SIGNUP;
    }

    /**
     * Signup string.
     *
     * @param user  the user
     * @param model the model
     * @return the string
     */
    @PostMapping(SIGNUP_MAPPING)
    public String signup(@ModelAttribute(USER_FORM) User user, Model model) {
        LOG.debug("New signup request");
        String signupError;
        if (!userService.isUsernameAvailable(user.getUsername())) {
            LOG.info("Signup error - "+SIGNUP_DUPLICATE_USERNAME);
            signupError = SIGNUP_DUPLICATE_USERNAME;
            model.addAttribute(USER_FORM, user);
            model.addAttribute(FIRSTNAME, user.getFirstname());
            model.addAttribute(LASTNAME, user.getLastname());
            model.addAttribute(SIGNUP_ERROR, signupError);
            return SIGNUP;
        } else {

            Integer userId = userService.addUser(user);
            if (userId < 0) {
                LOG.info("Signup error - "+NEGATIVE_USER_ID);
                signupError = SIGNUP_GENERIC_ERROR;
                model.addAttribute(SIGNUP_ERROR, signupError);
                return SIGNUP;
            }
            LOG.debug("Signup success.");
            user.setFirstname(user.getUsername());
            model.addAttribute(SIGNUP_SUCCESS, SIGNUP_SUCCESS_MSG);
            model.addAttribute(USER_FORM, user);
            return SIGNUP;
        }
    }
}
