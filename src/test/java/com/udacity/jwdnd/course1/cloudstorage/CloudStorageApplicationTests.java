package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

import static com.udacity.jwdnd.course1.cloudstorage.TestHelper.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

    @LocalServerPort
    private int port;

    private WebDriver driver;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    public void whenGetLoginPageShouldGetIt() {
        driver.get(loginURL(port));
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    public void whenGetSignUpPageShouldGetIt() {
        driver.get(signupURL(port));
        Assertions.assertEquals("Sign Up", driver.getTitle());
    }

    @Test

    public void whenGetHomePageShouldNotGetIt() {
        driver.get(homeURL(port));
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    public void fullUserRelatedTest() {
        HomePage homePage = getHomePage();
        Assertions.assertEquals("Home", driver.getTitle());
        homePage.logout();
        driver.get(homeURL(port));
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    public void whenCreateNoteShouldDisplayIt() {
        HomePage homePage = getHomePage();
        String noteTitle = "Some note title";
        String noteDescription = "Some note description";
        homePage.addNote(noteTitle, noteDescription);
        WebElement elenemtNoteTitle = driver.findElement(By.id("note-title-on-list"));
        Assertions.assertEquals(noteTitle, elenemtNoteTitle.getText());
        WebElement elenemtNoteDescription = driver.findElement(By.id("note-description-on-list"));
        Assertions.assertEquals(noteDescription, elenemtNoteDescription.getText());
        homePage.logout();
    }

    @Test
    public void whenEditNoteShouldDisplayIt() {
        HomePage homePage = getHomePage();
        String noteTitle = "Some note title";
        String noteDescription = "Some note description";
        homePage.addNote(noteTitle, noteDescription);
        noteTitle = "Some new note title";
        noteDescription = "Some new note description";
        homePage.editNote(noteTitle, noteDescription);
        WebElement elenemtNoteTitle = driver.findElement(By.id("note-title-on-list"));
        Assertions.assertEquals(noteTitle, elenemtNoteTitle.getText());
        WebElement elenemtNoteDescription = driver.findElement(By.id("note-description-on-list"));
        Assertions.assertEquals(noteDescription, elenemtNoteDescription.getText());
        homePage.logout();
    }

    @Test
    public void whenDeleteNoteShouldDeleteIt() {
        HomePage homePage = getHomePage();
        String noteTitle = "Some note title";
        String noteDescription = "Some note description";
        homePage.addNote(noteTitle, noteDescription);
        homePage.deleteNote();
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            driver.findElement(By.id("note-title-on-list"));
        });
        homePage.logout();
    }

    @Test
    public void whenCreateCredentialsShouldDisplayThem() {
        HomePage homePage = getHomePage();
        String url = "www.somesite.com";
        String username = "Some username";
        String password = "Some password";
        homePage.addCredential(url, username, password);
        homePage.addCredential(url, username, password);
        List<WebElement> elementsURL = driver.findElements(By.name("credential-url"));
        Assertions.assertEquals(2, elementsURL.size());
        elementsURL.forEach((element) -> {Assertions.assertEquals(url, element.getText());});
        List<WebElement> elementstUsername = driver.findElements(By.name("credential-username"));
        elementstUsername.forEach((element) -> {Assertions.assertEquals(username, element.getText());});
        List<WebElement> elementsPassword = driver.findElements(By.name("credential-password"));
        elementsPassword.forEach((element) -> {Assertions.assertNotEquals(password, element.getText());});
        homePage.logout();
    }

    @Test
    public void whenEditCredentialShouldDisplayThem() {
        HomePage homePage = getHomePage();
        String url = "www.somesite.com";
        String username = "Some username";
        String password = "Some password";
        homePage.addCredential(url, username, password);
        homePage.addCredential(url, username, password);
        String newURL = "www.somenewsite.com";
        String newUsername = "Some new username";
        String newPassword = "Some new password";
        List<WebElement> editButtons = driver.findElements(By.name("editCredentialButton"));
        homePage.editCredential(editButtons.get(0), newURL, newUsername, newPassword);
        editButtons = driver.findElements(By.name("editCredentialButton"));
        homePage.editCredential(editButtons.get(1), newURL, newUsername, newPassword);

        List<WebElement> elementsURL = driver.findElements(By.name("credential-url"));
        elementsURL.forEach((element) -> {Assertions.assertEquals(newURL, element.getText());});
        List<WebElement> elementstUsername = driver.findElements(By.name("credential-username"));
        elementstUsername.forEach((element) -> {Assertions.assertEquals(newUsername, element.getText());});
        List<WebElement> elementsPassword = driver.findElements(By.name("credential-password"));
        elementsPassword.forEach((element) -> {Assertions.assertNotEquals(newPassword, element.getText());});

        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.name("editCredentialButton")))).click();
        WebElement elementPassword = wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-password")));
        Assertions.assertEquals(newPassword, elementPassword.getAttribute("value"));
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("credentialDismiss")))).click();

        homePage.logout();
    }

    @Test
    public void whenDeleteCredentialsShouldDeleteThem() {
        HomePage homePage = getHomePage();
        String url = "www.somesite.com";
        String username = "Some username";
        String password = "Some password";
        homePage.addCredential(url, username, password);
        homePage.addCredential(url, username, password);
        homePage.deleteFirstCredential();
        homePage.deleteFirstCredential();
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            driver.findElement(By.name("credential-url"));
        });
        homePage.logout();
    }

    private HomePage getHomePage() {
        SignupPage signupPage = new SignupPage(driver, port);
        String username = getUsername();
        signupPage.signUp(FIRST_NAME, LAST_NAME, username, PASSWORD);
        LoginPage loginPage = new LoginPage(driver, port);
        loginPage.signIn(username, PASSWORD);
        return new HomePage(driver, port);
    }
}
