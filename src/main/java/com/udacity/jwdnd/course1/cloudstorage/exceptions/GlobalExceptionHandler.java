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
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.udacity.jwdnd.course1.cloudstorage.constants.ApplicationConstants.*;

/**
 * The type Global exception handler.
 * @author Sudhir Tyagi
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final ApplicationUtils utils;

    /**
     * Class constructor.
     *
     * @param utils the utils
     */
    public GlobalExceptionHandler(ApplicationUtils utils) {
        this.utils = utils;
    }

    /**
     * Handle generic exception model and view.
     *
     * @param exc      the exc
     * @param request  the request
     * @param response the response
     * @return the model and view
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView handleGenericException(
            Exception exc,
            HttpServletRequest request,
            HttpServletResponse response) {
        LOG.error("Exception handled - " + exc.getMessage());
        String errorMsg;
        ModelAndView modelAndView = new ModelAndView(HOME);
        ModelMap model = (ModelMap) modelAndView.getModel();
        utils.populateModelMap(model, SecurityContextHolder.getContext().getAuthentication());
        if(exc instanceof MaxUploadSizeExceededException){
            errorMsg = FILE_UPLOAD_SIZE_ERROR;
        }else if (exc instanceof HttpRequestMethodNotSupportedException) {
            errorMsg = HTTP_METHOD_NOT_SUPPORTED;
        } else if (exc instanceof NoHandlerFoundException) {
            errorMsg = HTTP_URL_NOT_VALID;
        } else if (exc instanceof RestClientResponseException) {
            errorMsg = HTTP_URL_NOT_VALID;
        } else if (exc instanceof ResourceNotFoundException) {
            errorMsg = HTTP_URL_NOT_VALID;
        } else {
            errorMsg = GENERIC_ERROR;
        }
        model.put(ERROR_MESSAGE, errorMsg);
        model.put(ACTIVE_TAB, FILES_TAB);
        model.put(APPLICATION_FORM, new ApplicationForm());
        return modelAndView;
    }
}