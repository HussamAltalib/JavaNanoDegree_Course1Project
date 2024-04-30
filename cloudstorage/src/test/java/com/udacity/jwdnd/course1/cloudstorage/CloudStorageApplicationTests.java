package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

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
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
		assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}

	
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");
		
		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		userSignupAndLogin("URL", "UT", "UT", "123");

		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		assertTrue(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		userSignupAndLogin("Large File", "test", "LFT", "123");

		// Try to upload a large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}

	@Test
	public void testUnauthorizedAccess() {
		// Try to access home page without being logged in
		driver.get("http://localhost:" + this.port + "/home");
		// Check if the current page is the login page
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	private void userSignupAndLogin(String firstname, String lastName, String username, String password) {
		doMockSignUp(firstname,lastName,username,password);
		doLogIn(username, password);
	}
	@Test
	public void testUserSignupAndLogin() {
		userSignupAndLogin("test", "test", "test", "test");

		Assertions.assertEquals("http://localhost:" + this.port + "/home", driver.getCurrentUrl());

		WebElement logoutButton = driver.findElement(By.id("logout-button"));
		logoutButton.click();

		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	private void createNote(String title, String description) {
		WebDriverWait webDriverWait = new WebDriverWait(driver, 10);

		driver.findElement(By.id("nav-notes-tab")).click();
		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("show-note-modal")));
		driver.findElement(By.id("show-note-modal")).click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));

		WebElement noteTitle = driver.findElement(By.id("note-title"));
		noteTitle.click();
		noteTitle.sendKeys(title);

		WebElement noteDescription = driver.findElement(By.id("note-description"));
		noteDescription.click();
		noteDescription.sendKeys(description);

		WebElement noteSubmitButton = driver.findElement(By.id("not-submit-button"));
		noteSubmitButton.click();
		webDriverWait.until(ExpectedConditions.urlContains("/home"));
	}
	@Test
	public void testNoteCreation() {
		userSignupAndLogin("Sample", "User", "testUser", "testPassword");

		String noteTitle = "New Note Title";
		String noteDescription = "This is a new note description";
		createNote(noteTitle, noteDescription);

		// Check that the new note is now visible on the home page
		Boolean noteCreated = driver.findElements(By.xpath("//th[contains(text(), '" + noteTitle + "')]")).size() > 0;
		assertTrue(noteCreated, "Note was not created successfully.");
	}

	@Test
	public void testEditNote() {
		WebDriverWait webDriverWait = new WebDriverWait(driver, 10);

		userSignupAndLogin("Sample", "User", "testEditNote", "testEditNote");

		createNote("New Note Title", "This is a new note description");
		createNote("testEditNote", "testEditNote");

		driver.findElement(By.id("nav-notes-tab")).click();

		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("edit-Note-Button")));
		driver.findElement(By.id("edit-Note-Button")).click();

		WebElement noteTitleX = driver.findElement(By.id("note-title"));
		noteTitleX.click();
		noteTitleX.clear();
		noteTitleX.sendKeys("Update Note Title");

		WebElement noteDescriptionX = driver.findElement(By.id("note-description"));
		noteDescriptionX.click();
		noteDescriptionX.clear();
		noteDescriptionX.sendKeys("This is an updated note description");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteSubmit")));
		WebElement noteSubmitButton = driver.findElement(By.id("noteSubmit"));
		noteSubmitButton.click();

		webDriverWait.until(ExpectedConditions.urlContains("/home"));

		driver.findElement(By.id("nav-notes-tab")).click();
		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("edit-Note-Button")));
		driver.findElement(By.id("edit-Note-Button")).click();

		String updatedNoteTitle = driver.findElement(By.id("note-title")).getAttribute("value");
		Assertions.assertEquals("Update Note Title", updatedNoteTitle);

		String updatedDescriptionTitle = driver.findElement(By.id("note-description")).getAttribute("value");
		Assertions.assertEquals("This is an updated note description", updatedDescriptionTitle);
	}

	@Test
	public void testDeleteNote() {
		WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
		userSignupAndLogin("Sample", "User", "testDeleteNote", "testDeleteNote");
		createNote("testDeleteNote", "testDeleteNote");

		driver.findElement(By.id("nav-notes-tab")).click();

		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("edit-Note-Button")));
		driver.findElement(By.id("Delete-Note-Button")).click();

		webDriverWait.until(ExpectedConditions.urlContains("/home"));

		driver.findElement(By.id("nav-notes-tab")).click();

		Boolean noteDeleted = driver.findElements(By.xpath("//th[contains(text(), 'New Note Title')]")).size() < 1;
		assertTrue(noteDeleted);
	}

	private void createCredential(String url, String username, String password) {
		WebDriverWait webDriverWait = new WebDriverWait(driver, 10);

		// Open the credentials tab and click the "Add New Credential" button
		driver.findElement(By.id("nav-credentials-tab")).click();
		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("add-new-credential-button"))).click();

		// Enter the credential details into the form
		WebElement credentialUrlInput = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		credentialUrlInput.clear();
		credentialUrlInput.sendKeys(url);

		WebElement credentialUsernameInput = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
		credentialUsernameInput.clear();
		credentialUsernameInput.sendKeys(username);

		WebElement credentialPasswordInput = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		credentialPasswordInput.clear();
		credentialPasswordInput.sendKeys(password);

		// Submit the form to create the credential
		driver.findElement(By.id("credential-submit-button")).click();
		webDriverWait.until(ExpectedConditions.urlContains("/home")); // Wait to be redirected back to home
	}

	@Test
	public void testCreateMultipleCredentials() {
		userSignupAndLogin("Sample", "User", "testCreateCreden", "testCreateCreden");

		// Data for multiple credentials
		String[][] credentialsData = {
				{"http://example.com", "user1", "pass1"},
				{"http://example.org", "user2", "pass2"},
				{"http://example.net", "user3", "pass3"}
		};

		// Loop through each set of credential data and create credentials
		for (String[] data : credentialsData) {
			createCredential(data[0], data[1], data[2]);

			Boolean credentialCreated = driver.findElements(By.xpath("//th[contains(text(), '" + data[0] + "')]")).size() > 0;
			assertTrue(credentialCreated);

			// Optionally, check displayed passwords are not plaintext (if displaying on home)
			String displayedPassword = driver.findElement(By.id("home-password")).getText();  // Adjust id if needed
			assertNotEquals(data[2], displayedPassword);
		}
	}

	@Test
	public void testEditMultipleCredentials() throws Exception {
		WebDriverWait webDriverWait = new WebDriverWait(driver, 10);

		userSignupAndLogin("Sample", "User", "testEditCreden", "testEditCreden");

		//create multiple credentials
		createCredential("http://example.com","user1","pass1");
		createCredential("http://example.org","user2","pass2");
		createCredential("http://example.net","user3","pass3");

		// Navigate to the credentials tab to start editing
		driver.findElement(By.id("nav-credentials-tab")).click();

		// Data for updated credentials
		String[][] updatedCredentialsData = {
				{"http://updated-example.com", "updated-user1", "updated-pass1"},
				{"http://updated-example.org", "updated-user2", "updated-pass2"},
				{"http://updated-example.net", "updated-user3", "updated-pass3"}
		};
		WebElement navCredentialsTab;

		// Loop through each credential to update
		for (int i = 0; i < updatedCredentialsData.length; i++) {
			navCredentialsTab = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
			navCredentialsTab.click();

			List<WebElement> editButtons = webDriverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("[data-credential-id]")));
			WebElement editButton = editButtons.get(i);
			String credentialId = editButton.getAttribute("data-credential-id");
			editButton.click();

			// Edit the URL
			WebElement credentialUrl = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("url")));
			credentialUrl.clear();
			credentialUrl.sendKeys(updatedCredentialsData[i][0]);

			// Edit the username
			WebElement credentialUsername = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
			credentialUsername.clear();
			credentialUsername.sendKeys(updatedCredentialsData[i][1]);

			// Edit the password
			WebElement credentialPassword = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
			credentialPassword.clear();
			credentialPassword.sendKeys(updatedCredentialsData[i][2]);

			// Submit the updated credential
			WebElement credentialSubmitButton = webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("credential-submit-button")));
			credentialSubmitButton.click();

			// Wait for navigation back to the credentials tab
			webDriverWait.until(ExpectedConditions.urlContains("/home"));
			navCredentialsTab = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
			navCredentialsTab.click();

			// Re-navigate to the edit page for the ith credential to verify the changes
			WebElement reNavCredentialsTab = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
			reNavCredentialsTab.click();
			editButtons = webDriverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("[data-credential-id]")));
			editButtons.get(i).click();

			// Verify the updated credentials are displayed correctly
			String displayedUrl = driver.findElement(By.id("url")).getAttribute("value");
			String displayedUsername = driver.findElement(By.id("username")).getAttribute("value");

			Assertions.assertEquals(updatedCredentialsData[i][0], displayedUrl);
			Assertions.assertEquals(updatedCredentialsData[i][1], displayedUsername);

			// Now verify that the password displayed is unencrypted
			String unencryptedPassword = driver.findElement(By.id("password")).getAttribute("value");
			Assertions.assertEquals(updatedCredentialsData[i][2], unencryptedPassword);

			WebElement backToHomepage = webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("credential-submit-button")));
			backToHomepage.click();
		}
	}

	@Test
	public void testDeleteCredential() throws Exception {
		WebDriverWait webDriverWait = new WebDriverWait(driver, 10);

		userSignupAndLogin("Sample", "User", "testDeleteCreden", "testDeleteCreden");

		//create multiple credentials
		createCredential("http://example.com","user1","pass1");
		createCredential("http://example.org","user2","pass2");
		createCredential("http://example.net","user3","pass3");

		// Navigate to the credentials tab
		WebElement navCredentialsTab = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		navCredentialsTab.click();

		// Get a list of all delete buttons
		List<WebElement> deleteButtons = webDriverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("[data-credential-id-delete]")));

		// Store the IDs of credentials before deletion to verify after deletion
		List<String> credentialIdsBeforeDeletion = deleteButtons.stream()
				.map(button -> button.getAttribute("data-credential-id-delete"))
				.collect(Collectors.toList());

		// Loop through each delete button and perform the delete operation
		for (String credentialId : credentialIdsBeforeDeletion) {
			WebElement navCredentialsTabIn = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
			navCredentialsTabIn.click();

			// Click the delete button for the credential
			WebElement deleteButton = webDriverWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[data-credential-id-delete='" + credentialId + "']")));
			deleteButton.click();

			// Wait until the credential element is no longer present in the DOM
			boolean credentialStillDisplayed = false;
			try {
				webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("[data-credential-id-delete='" + credentialId + "']")));
			} catch (TimeoutException e) {
				credentialStillDisplayed = true;
			}

			Assertions.assertFalse(credentialStillDisplayed, "Credential with id " + credentialId + " was not deleted.");
		}
	}
}
