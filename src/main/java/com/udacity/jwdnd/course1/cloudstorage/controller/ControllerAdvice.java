package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    private final static String errorPage = "custom-error";

    @ExceptionHandler({MaxUploadSizeExceededException.class})
    public ModelAndView handleMaxSizeException(
            MaxUploadSizeExceededException exc,
            HttpServletRequest request,
            HttpServletResponse response) {

        ModelAndView modelAndView = new ModelAndView(errorPage);
        modelAndView.addObject("customErrorMessage", exc.getMessage());
        return modelAndView;
    }

//    @ExceptionHandler(value = Exception.class)
//    public ModelAndView
//    defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
//        // If the exception is annotated with @ResponseStatus rethrow it and let
//        // the framework handle it - like the OrderNotFoundException example
//        // at the start of this post.
//        // AnnotationUtils is a Spring Framework utility class.
//        if (AnnotationUtils.findAnnotation
//                (e.getClass(), ResponseStatus.class) != null)
//            throw e;
//
//        // Otherwise setup and send the user to a default error-view.
//        ModelAndView mav = new ModelAndView(errorPage);
//        mav.addObject("exception", e);
//        mav.addObject("url", req.getRequestURL());
//        return mav;
//    }
}
