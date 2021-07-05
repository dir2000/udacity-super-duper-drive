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
        wait.until(ExpectedConditions.elementToBeClickable(By.id("noteSubmitSecond"))).click();

        driver.navigate().to(homeURL);
        driver.findElement(By.id("nav-notes-tab")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title-on-list")));
    }

    public void editNote(String noteTitle, String noteDescription) {
        driver.findElement(By.id("nav-notes-tab")).click();
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(By.name("editNoteButton"))).click();
        WebElement elementTitle = wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title")));
        elementTitle.clear();
        elementTitle.sendKeys(noteTitle);
        WebElement elementDescription = wait.until(ExpectedConditions.elementToBeClickable(By.id("note-description")));
        elementDescription.clear();
        elementDescription.sendKeys(noteDescription);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("noteSubmitSecond"))).click();

        driver.navigate().to(homeURL);
        driver.findElement(By.id("nav-notes-tab")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title-on-list")));
    }

    public void deleteNote() {
        driver.findElement(By.id("nav-notes-tab")).click();
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(By.name("deleteNoteLink"))).click();

        driver.navigate().to(homeURL);
        driver.findElement(By.id("nav-notes-tab")).click();
    }

    public void addCredential(String url, String userName, String password) {
        driver.findElement(By.id("nav-credentials-tab")).click();
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("add-credential-button"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url"))).sendKeys(url);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-username"))).sendKeys(userName);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-password"))).sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("credentialSubmitSecond"))).click();

        driver.navigate().to(homeURL);
        driver.findElement(By.id("nav-credentials-tab")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.name("credential-url")));
    }

    public void editCredential(WebElement editCredentialButton, String url, String userName, String password) {
        driver.findElement(By.id("nav-credentials-tab")).click();
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(editCredentialButton)).click();
        WebElement elementURL = wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url")));
        elementURL.clear();
        elementURL.sendKeys(url);
        WebElement elementUsername = wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-username")));
        elementUsername.clear();
        elementUsername.sendKeys(userName);
        WebElement elementPassword = wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-password")));
        elementPassword.clear();
        elementPassword.sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("credentialSubmitSecond"))).click();

        driver.navigate().to(homeURL);
        driver.findElement(By.id("nav-credentials-tab")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.name("credential-url")));
    }

    public void deleteFirstCredential() {
        driver.findElement(By.id("nav-credentials-tab")).click();
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(By.name("deleteCredentialLink"))).click();

        driver.navigate().to(homeURL);
        driver.findElement(By.id("nav-credentials-tab")).click();
    }

    public void waitForLoading(By by) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(webDriver -> webDriver.findElement(by));
    }


}
