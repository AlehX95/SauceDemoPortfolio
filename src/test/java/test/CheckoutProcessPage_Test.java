package test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.CartConfirmationItems;
import pages.CheckoutProcessPage;
import pages.ProductsPage;
import pages.SignInPage;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import utils.ExtentManager;

public class CheckoutProcessPage_Test {

	WebDriver driver;
	SignInPage signInPage;
	ProductsPage productsPage;
	CartConfirmationItems cartConfirmationItem;
	List<String> expectedProducts = Arrays.asList("Sauce Labs Backpack", "Sauce Labs Bolt T-Shirt",
			"Test.allTheThings() T-Shirt (Red)");
	CheckoutProcessPage checkoutProcessPage;
	private static ExtentReports extent;
	private ExtentTest test;

	@BeforeClass
	public static void setupReport() {
		extent = ExtentManager.getInstance();
	}

	@Before
	public void setUp() throws Exception {

		driver = new SignInPage(null).chromeDriverConnection();

		signInPage = new SignInPage(driver);
		signInPage.visit("https://www.saucedemo.com/");
		signInPage.signIn("standard_user", "secret_sauce");

		productsPage = new ProductsPage(driver);
		productsPage.addBackpackFromDetailPage();
		productsPage.addTshirtLabsBoltFromDetailPage();
		productsPage.addTshirtRedFromMainPage();
		productsPage.goToViewCartPage();

		cartConfirmationItem = new CartConfirmationItems(driver);

		cartConfirmationItem.checkoutShopping(expectedProducts);

		checkoutProcessPage = new CheckoutProcessPage(driver);

	}

	@Test
	public void testCheckoutPageIsDisplayed() {
		test = extent.createTest("Checkout Page is Displayed");
		test.log(Status.INFO, "Validating checkout page visibility");

		boolean result = checkoutProcessPage.isCheckoutPageDisplayed();
		if (result) {
			test.log(Status.PASS, "Checkout page displayed correctly");
		} else {
			test.log(Status.FAIL, "Checkout page NOT displayed");
		}
		assertTrue("Checkout page is not displayed", result);
	}

	@Test
	public void testFillInformation() {
		test = extent.createTest("Fill Information and Finish Checkout");
		test.log(Status.INFO, "Filling checkout form");

		boolean filled = checkoutProcessPage.fillInformation();
		boolean finished = checkoutProcessPage.clickFinishIfOverviewDisplayed();

		if (filled && finished) {
			test.log(Status.PASS, "Checkout information filled and finished successfully");
		} else {
			test.log(Status.FAIL, "Failed during checkout process");
		}

		assertTrue("Failed to fill checkout information", filled);
		assertTrue("Failed to finish checkout", finished);
	}

	@Test
	public void testCompleteCheckoutAndBackToHome() {
		test = extent.createTest("Complete Checkout and Return to Home");
		test.log(Status.INFO, "Performing full checkout process");

		boolean info = checkoutProcessPage.fillInformation();
		boolean finish = checkoutProcessPage.clickFinishIfOverviewDisplayed();
		boolean backHome = checkoutProcessPage.clickBackToHome();

		ProductsPage productsPageCheck = new ProductsPage(driver);
		boolean displayed = productsPageCheck.isProductsPageDisplayed();

		if (info && finish && backHome && displayed) {
			test.log(Status.PASS, "Checkout completed and returned to home successfully");
		} else {
			test.log(Status.FAIL, "Checkout or return to home failed");
		}

		assertTrue("Products Page not displayed after checkout", displayed);
	}

	@Test
	public void testEmptyFirstNameShowsError() {
		test = extent.createTest("Empty First Name Shows Error");
		test.log(Status.INFO, "Testing validation for empty first name");

		checkoutProcessPage.type("", checkoutProcessPage.firstNameField);
		checkoutProcessPage.type("LastName", checkoutProcessPage.lastNameField);
		checkoutProcessPage.type("12345", checkoutProcessPage.postalCodeField);
		checkoutProcessPage.click(checkoutProcessPage.continueButton);

		By errorMessage = By.cssSelector("h3[data-test='error']");
		boolean errorVisible = checkoutProcessPage.isDisplayed(errorMessage);
		boolean correctText = driver.findElement(errorMessage).getText().contains("First Name is required");

		if (errorVisible && correctText) {
			test.log(Status.PASS, "Correct error displayed for missing first name");
		} else {
			test.log(Status.FAIL, "Error message missing or incorrect");
		}

		assertTrue("Error message not displayed", errorVisible);
		assertTrue("Error message does not contain expected text", correctText);
	}

	@After
	public void tearDown() {
		if (driver != null) {
			driver.quit();
			test.log(Status.INFO, "Browser closed");
		}
	}

	@AfterClass
	public static void flushReport() {
		if (extent != null) {
			extent.flush();
		}
	}
}
