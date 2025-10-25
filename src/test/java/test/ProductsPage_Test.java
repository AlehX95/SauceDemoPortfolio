package test;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import base.Base;
import pages.CartConfirmationItems;
import pages.ProductsPage;
import pages.SignInPage;
import utils.ExtentManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class ProductsPage_Test {

	WebDriver driver;
	SignInPage signInPage;
	ProductsPage productsPage;
	private static ExtentReports extent;
	private ExtentTest test;

	@BeforeClass
	public static void setupReport() {
		extent = ExtentManager.getInstance();
	}

	@Before
	public void setUp() throws InterruptedException {

		Base base = new Base(null);
		driver = base.chromeDriverConnection();
		signInPage = new SignInPage(driver);
		signInPage.visit("https://www.saucedemo.com/");
		signInPage.signIn("standard_user", "secret_sauce");

		productsPage = new ProductsPage(driver);
		// productsPage.waitForVisible(By.className("inventory_list")); // opcional
	}

	@Test
	public void testAddProductFromMainPage() {
		test = extent.createTest("Add Product from Main Page");
		test.log(Status.INFO, "Starting test: Add Product from Main Page");

		try {
			assertTrue(productsPage.addTshirtRedFromMainPage());
			test.log(Status.PASS, "Product added successfully from main page");

			assertTrue(productsPage.goToViewCartPage());
			test.log(Status.PASS, "Navigated to View Cart Page");

			CartConfirmationItems cart = new CartConfirmationItems(driver);
			assertTrue(cart.containsProduct("Test.allTheThings() T-Shirt (Red)"));
			test.log(Status.PASS, "Verified product presence in cart");

		} catch (AssertionError e) {
			test.log(Status.FAIL, "Assertion failed: " + e.getMessage());
			throw e;
		}
	}

	@Test
	public void testProductPageElementsVisible() {
		test = extent.createTest("Verify Product Page Elements Visibility\"");
		test.log(Status.INFO, "Checking visibility of product page elements");
		try {
			assertTrue(productsPage.isProductsPageDisplayed());
			test.log(Status.PASS, "Product page elements are visible");

		} catch (AssertionError e) {
			test.log(Status.FAIL, "Visibility check failed: " + e.getMessage());
			throw e;
		}
	}

	@Test
	public void testAddProductFromDetailPage() {
		test = extent.createTest("Add Product from Detail Page");
		test.log(Status.INFO, "Adding Backpack from Detail Page");
		try {
			assertTrue(productsPage.addBackpackFromDetailPage());
			test.log(Status.PASS, "Backpack added successfully from detail page");

			assertTrue(productsPage.goToViewCartPage());
			CartConfirmationItems cart = new CartConfirmationItems(driver);
			assertTrue(cart.containsProduct("Sauce Labs Backpack"));
			test.log(Status.PASS, "Verified backpack in cart");

		} catch (AssertionError e) {
			test.log(Status.FAIL, "Assertion failed: " + e.getMessage());
			throw e;
		}
	}

	@Test
	public void testAddMultipleProductsAndGoToCart() {
		test = extent.createTest("Add Multiple Products and Navigate to Cart");
		test.log(Status.INFO, "Adding multiple products from different pages");
		try {
			assertTrue(productsPage.addBackpackFromDetailPage());
			assertTrue(productsPage.addTshirtLabsBoltFromDetailPage());
			assertTrue(productsPage.addTshirtRedFromMainPage());
			assertTrue(productsPage.goToViewCartPage());
			test.log(Status.PASS, "Added multiple products and navigated to cart successfully");
		} catch (AssertionError e) {
			test.log(Status.FAIL, "Assertion failed: " + e.getMessage());
			throw e;
		}

	}

	@Test
	public void testAddMultipleProductsFromDifferentFlows() {
		test = extent.createTest("Add Multiple Products from Different Flows");
		test.log(Status.INFO, "Adding multiple products via different flows");
		try {
			assertTrue(productsPage.addBackpackFromDetailPage());
			assertTrue(productsPage.addTshirtLabsBoltFromDetailPage());
			assertTrue(productsPage.addTshirtRedFromMainPage());
			assertTrue(productsPage.goToViewCartPage());

			CartConfirmationItems cart = new CartConfirmationItems(driver);
			assertTrue(cart.areProductsInCart(Arrays.asList("Sauce Labs Backpack", "Sauce Labs Bolt T-Shirt",
					"Test.allTheThings() T-Shirt (Red)")));
			test.log(Status.PASS, "Verified all products are in the cart");
		} catch (AssertionError e) {
			test.log(Status.FAIL, "Assertion failed: " + e.getMessage());
			throw e;
		}
	}

	@Test
	public void testRemoveMultipleProductsFromMainPage() {
		test = extent.createTest("Remove Multiple Products from Main Page");
		test.log(Status.INFO, "Removing multiple products from main page");
		try {
			assertTrue(productsPage.addBackpackFromDetailPage());
			assertTrue(productsPage.addTshirtLabsBoltFromDetailPage());
			assertTrue(productsPage.addTshirtRedFromMainPage());
			assertEquals(3, productsPage.getCartCount());
			test.log(Status.PASS, "Three products added to cart");

			assertTrue(productsPage.removeBackpackFromMainPage());
			assertTrue(productsPage.removeSauceLabsTshirtFromMainPage());
			assertEquals(1, productsPage.getCartCount());
			test.log(Status.PASS, "Removed products successfully, one item remaining");

			CartConfirmationItems cart = new CartConfirmationItems(driver);
			assertTrue(cart.containsProduct("Test.allTheThings() T-Shirt (Red)"));
			test.log(Status.PASS, "Verified remaining product in cart");
		} catch (AssertionError e) {
			test.log(Status.FAIL, "Removal test failed: " + e.getMessage());
			throw e;
		}
	}

	@After
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

	@AfterClass
	public static void flushReport() {
		if (extent != null) {
			extent.flush();
		}
	}

}
