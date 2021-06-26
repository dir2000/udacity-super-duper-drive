package com.udacity.jwdnd.course1.cloudstorage.service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service
public class ErrorService {
    private final static String errorPage = "custom-error";

    public ModelAndView preparEerrorPage(String errorMessage) {
        ModelAndView modelAndView = new ModelAndView(errorPage);
        modelAndView.addObject("errorMessage", errorMessage);
        return modelAndView;
    }
}
