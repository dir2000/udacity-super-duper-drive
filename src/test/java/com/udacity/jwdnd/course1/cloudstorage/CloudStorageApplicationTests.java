package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

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
		homePage.logout();
	}

	private HomePage getHomePage(){
		SignupPage signupPage = new SignupPage(driver, port);
		String username = getUsername();
		signupPage.signUp(FIRST_NAME, LAST_NAME, username, PASSWORD);
		LoginPage loginPage = new LoginPage(driver, port);
		loginPage.signIn(username, PASSWORD);
		return new HomePage(driver, port);
	}
}
