package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler({MaxUploadSizeExceededException.class})
    public ModelAndView handleMaxSizeException(
            MaxUploadSizeExceededException exc,
            HttpServletRequest request,
            HttpServletResponse response) {

        ModelAndView modelAndView = new ModelAndView("error");
        long maxUploadSize = exc.getMaxUploadSize();
        modelAndView.addObject("errorMessage", "Maximum upload size " + (maxUploadSize >= 0L ? "of " + maxUploadSize + " bytes " : "") + "exceeded");
        return modelAndView;
    }
}
