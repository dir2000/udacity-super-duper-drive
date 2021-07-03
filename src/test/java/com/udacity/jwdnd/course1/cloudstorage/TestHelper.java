package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.atomic.AtomicInteger;

public class TestHelper {
    public static final String FIRST_NAME = "Mykhailo";
    public static final String LAST_NAME = "Zhurylo";
    public static final String PASSWORD = "password";
    public static final String BASE_URL = "http://localhost:";
    public static final AtomicInteger counter = new AtomicInteger();

    public static void waitForLoading(WebDriver driver, By by) {
        int timeOutInSeconds = 10;
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.until(webDriver -> webDriver.findElement(by));
    }

    public static String loginURL(int port) {
        return BASE_URL + port + "/login";
    }

    public static String signupURL(int port) {
        return BASE_URL + port + "/signup";
    }

    public static String homeURL(int port) {
        return BASE_URL + port + "/home";
    }

    public static String getUsername() {
        return "user" + counter.incrementAndGet();
    }
}
