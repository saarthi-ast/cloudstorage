package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.exceptions.ResourceNotFoundException;
import com.udacity.jwdnd.course1.cloudstorage.services.ApplicationUtils;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Application error controller.
 * @author Sudhir Tyagi
 */
@Controller
public class ApplicationErrorController implements ErrorController {
    private final ApplicationUtils utils;

    /**
     * Class constructor.
     *
     * @param utils the utils
     */
    public ApplicationErrorController(ApplicationUtils utils) {
        this.utils = utils;
    }

    /**
     * Handle error.
     *
     * @param request the request
     */
    @RequestMapping("/error")
    @ResponseBody
    public void handleError(HttpServletRequest request){
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
        throw new ResourceNotFoundException();
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
