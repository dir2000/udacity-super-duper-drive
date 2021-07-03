package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    @FindBy(id="inputUsername")
    private WebElement username;
    @FindBy(id="inputPassword")
    private WebElement password;
    @FindBy(id="submit-button")
    private WebElement submitButton;
    WebDriver driver;

    public LoginPage(WebDriver driver, Integer port) {
        this.driver = driver;
        driver.get("http://localhost:" + port + "/login");
        waitForLoading(By.id("submit-button"));
        PageFactory.initElements(driver, this);
    }

    public void signIn(String username, String password)
    {
        this.username.sendKeys(username);
        this.password.sendKeys(password);
        submitButton.click();
        waitForLoading(By.name("logoutForm"));
    }

    public void waitForLoading(By by) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(webDriver -> webDriver.findElement(by));
    }
}
