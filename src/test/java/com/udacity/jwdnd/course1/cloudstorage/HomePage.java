package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    @FindBy(id = "logout-button")
    private WebElement logoutButton;
    @FindBy(id = "add-note-button")
    private WebElement addNoteButton;

    WebDriver driver;
    private String homeURL;

    public HomePage(WebDriver driver, Integer port) {
        this.driver = driver;
        homeURL = "http://localhost:" + port + "/home";
        driver.get(homeURL);
        PageFactory.initElements(driver, this);
    }

    public void logout() {
        logoutButton.click();
        waitForLoading(By.id("submit-button"));
    }

    public void addNote(String noteTitle, String noteDescription) {
        driver.findElement(By.id("nav-notes-tab")).click();
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("add-note-button"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title"))).sendKeys(noteTitle);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("note-description"))).sendKeys(noteDescription);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("noteSubmit"))).click();
        //driver.findElement(By.id("noteSubmit")).click();

        //wait.until(ExpectedConditions.elementToBeClickable(By.id("noteSubmit"))).click();
        driver.navigate().to(homeURL);
        driver.findElement(By.id("nav-notes-tab")).click();
//
//
//        addNoteButton.click();
//        WebElement elementTitle = driver.findElement(By.id("note-title"));
//        elementTitle.sendKeys(noteTitle);
//        WebElement elementDescription = driver.findElement(By.id("note-description"));
//        elementDescription.sendKeys(noteDescription);
//        WebElement elementSubmitButton = driver.findElement(By.id("noteSubmit"));
//        elementSubmitButton.click();
    }

    public void waitForLoading(By by) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(webDriver -> webDriver.findElement(by));
    }
}
