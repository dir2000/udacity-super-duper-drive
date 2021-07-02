package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignupPage {
    @FindBy(id = "inputFirstName")
    private WebElement inputFirstName;
    @FindBy(id = "inputLastName")
    private WebElement inputLastName;
    @FindBy(id="inputUsername")
    private WebElement inputUsername;
    @FindBy(id="inputPassword")
    private WebElement inputPassword;
    @FindBy(id="submit-button")
    private WebElement submitButton;


    WebDriver driver;

    public SignupPage(WebDriver driver, Integer port) {
        this.driver = driver;
        driver.get("http://localhost:" + port + "/signup");
        waitForLoading(By.id("submit-button"));
        PageFactory.initElements(driver, this);
    }

    public void signUp(String firstName, String lastName, String username, String password) {
        inputFirstName.sendKeys(firstName);
        inputLastName.sendKeys(lastName);
        inputUsername.sendKeys(username);
        inputPassword.sendKeys(password);
        submitButton.click();
        waitForLoading(By.id("submit-button"));
    }

    public void waitForLoading(By by) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(webDriver -> webDriver.findElement(by));
    }
}
