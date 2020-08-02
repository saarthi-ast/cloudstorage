package com.udacity.jwdnd.course1.cloudstorage.exceptions;

import com.udacity.jwdnd.course1.cloudstorage.model.ApplicationForm;
import com.udacity.jwdnd.course1.cloudstorage.services.ApplicationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.udacity.jwdnd.course1.cloudstorage.constants.ApplicationConstants.*;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private ApplicationUtils utils;

    public GlobalExceptionHandler(ApplicationUtils utils) {
        this.utils = utils;
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ModelAndView handleMaxSizeException(
            MaxUploadSizeExceededException exc,
            HttpServletRequest request,
            HttpServletResponse response) {
        LOG.error("MaxUploadSizeExceededException handled");
        ModelAndView modelAndView = new ModelAndView(HOME);
        ModelMap model = (ModelMap) modelAndView.getModel();
        utils.populateModelMap(model, SecurityContextHolder.getContext().getAuthentication());
        model.put(ACTIVE_TAB, FILES_TAB);
        model.put(ERROR_MESSAGE, FILE_UPLOAD_SIZE_ERROR);
        model.put(APPLICATION_FORM, new ApplicationForm());
        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleGenericException(
            Exception exc,
            HttpServletRequest request,
            HttpServletResponse response) {
        LOG.error("Exception handled - "+ exc.getMessage());
        String errorMsg = null;
        ModelAndView modelAndView = new ModelAndView(HOME);
        ModelMap model = (ModelMap) modelAndView.getModel();
        utils.populateModelMap(model, SecurityContextHolder.getContext().getAuthentication());
        if (exc instanceof HttpRequestMethodNotSupportedException){
            errorMsg = HTTP_METHOD_NOT_SUPPORTED;
        }else{
            model.put(ERROR_MESSAGE, GENERIC_ERROR);
        }
        model.put(ACTIVE_TAB, FILES_TAB);
        model.put(APPLICATION_FORM, new ApplicationForm());
        return modelAndView;
    }
}