package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import base.Base;
import pages.SignInPage;
import utils.UserDataFetcher;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import utils.ExtentManager;

public class SignIn_Test {

	private WebDriver driver;
	private SignInPage signInPage;
    private static ExtentReports extent;
    private ExtentTest test;
	

    @BeforeClass
    public static void setupReport() {
        extent = ExtentManager.getInstance();
    }

	@Before

	public void setUp() throws Exception {

		Base base = new Base(null);
		//extent = ExtentManager.getInstance();
		driver = base.chromeDriverConnection();
		signInPage = new SignInPage(driver);
		signInPage.visit("https://www.saucedemo.com/");

	}

	@Test
	public void testLoginFromDatabase() {
		test = extent.createTest("Login from Database");
		test.log(Status.INFO, "Fetching user credentials from DB");

		String[] creds = UserDataFetcher.getUserCredentials("standard_user");
		test.log(Status.INFO, "Attempting login with username: " + creds[0]);

		boolean loginResult = signInPage.signIn(creds[0], creds[1]);
		if (loginResult) {
			test.log(Status.PASS, "Login successful for user: " + creds[0]);
		} else {
			test.log(Status.FAIL, "Login failed for user: " + creds[0]);
		}

		assertTrue("Login failed from DB", loginResult);
		assertTrue("Home page not displayed after login", signInPage.isHomePageDisplayed());
	}

	@Test
	public void testLogInWithValidCredentials() {
		test = extent.createTest("Login with Valid Credentials");
		test.log(Status.INFO, "Attempting login with valid credentials");

		boolean result = signInPage.signIn("standard_user", "secret_sauce");
		if (result) {
			test.log(Status.PASS, "Login successful with valid credentials");
		} else {
			test.log(Status.FAIL, "Login failed with valid credentials");
		}

		assertTrue(result);
		assertTrue(signInPage.isHomePageDisplayed());
	}

	@Test
	public void testShowErrorWithInvalidCredentials() {
		test = extent.createTest("Login with Invalid Credentials");
		test.log(Status.INFO, "Attempting login with invalid credentials");

		boolean result = signInPage.signIn("invalid_user", "wrong_password");
		if (!result && signInPage.isErrorDisplayed()) {
			test.log(Status.PASS, "Error message displayed correctly for invalid login");
		} else {
			test.log(Status.FAIL, "Error message NOT displayed for invalid login");
		}

		assertFalse(result);
		assertTrue(signInPage.isErrorDisplayed());
	}

	@Test
	public void testShowErrorWhenFieldsAreEmpty() {
		test = extent.createTest("Login with Empty Fields");
		test.log(Status.INFO, "Attempting login with empty username and password");
		boolean result = signInPage.signIn("", "");
		if (!result && signInPage.isErrorDisplayed()) {
			test.log(Status.PASS, "Error message displayed correctly for empty fields");
		} else {
			test.log(Status.FAIL, "Error message NOT displayed for empty fields");
		}
		assertFalse(result);
		assertTrue(signInPage.isErrorDisplayed());
	}

	@Test
	public void testLogoutSuccessfullyAndReturnToLoginPage() {
		test = extent.createTest("Logout Test");
		test.log(Status.INFO, "Logging in first");

		assertTrue(signInPage.signIn("standard_user", "secret_sauce"));
		test.log(Status.INFO, "Logging out now");

		boolean logoutResult = signInPage.signOut();
		if (logoutResult) {
			test.log(Status.PASS, "Logout successful");
		} else {
			test.log(Status.FAIL, "Logout failed");
		}

		assertTrue(logoutResult);
		assertTrue(signInPage.isLoginPageDisplayed());
	}

	@After
	public void tearDown() {
		driver.quit();
		test.log(Status.INFO, "Browser closed");
	}

	@AfterClass
	public static void closeReport() {
		ExtentManager.getInstance().flush();
	}

}
