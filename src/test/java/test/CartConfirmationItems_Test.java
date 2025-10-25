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
import pages.ProductsPage;
import pages.SignInPage;
import utils.ExtentManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import base.Base;

public class CartConfirmationItems_Test {

	WebDriver driver;
	SignInPage signInPage;
	ProductsPage productsPage;
	CartConfirmationItems cartConfirmationItem;
	List<String> expectedProducts = Arrays.asList("Sauce Labs Backpack", "Sauce Labs Bolt T-Shirt",
			"Test.allTheThings() T-Shirt (Red)");
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

		// Adding products to the initial test
		productsPage.addBackpackFromDetailPage();
		productsPage.addTshirtLabsBoltFromDetailPage();
		productsPage.addTshirtRedFromMainPage();
		productsPage.goToViewCartPage();

		cartConfirmationItem = new CartConfirmationItems(driver);

	}

	@Test
	public void testCartPageIsDisplayed() {
		test = extent.createTest("Cart Page is Displayed");
		test.log(Status.PASS, "Validating that the cart page is displayed correctly.");
		try {
			assertTrue("Cart page is not displayed correctly", cartConfirmationItem.isCartPageDisplayed());
			test.log(Status.PASS, "Cart page displayed successfully.");
		} catch (AssertionError e) {
			test.log(Status.FAIL, "Cart page display failed: " + e.getMessage());
		}
	}

	@Test
	public void testCartCountIsCorrect() {
		test = extent.createTest("Cart Count is Correct");
		test.log(Status.INFO, "Validating that the cart item count is 3.");
		try {
			assertTrue("Cart count is incorrect", cartConfirmationItem.isCartCountCorrect(3));
			test.log(Status.PASS, "Cart count verified as correct (3 items).");
		} catch (AssertionError e) {
			test.log(Status.FAIL, "Cart count validation failed: " + e.getMessage());
		}
	}

	@Test
	public void testProductsInCart() {
		test = extent.createTest("Products Present in Cart");
		test.log(Status.INFO, "Checking that expected products are present in the cart.");

		try {
			assertTrue("Expected products are not present in cart",
					cartConfirmationItem.areProductsInCart(expectedProducts));
			test.log(Status.PASS, "All expected products are present in the cart.");
		} catch (AssertionError e) {
			test.log(Status.FAIL, "Products verification failed: " + e.getMessage());
			throw e;
		}
	}

	@Test
	public void testRemoveProductFromCart() {
		test = extent.createTest("Remove Products from Cart");
		test.log(Status.INFO, "Removing specific products from the cart and verifying.");

		try {
			// Remove Backpack
			assertTrue(cartConfirmationItem.removeProduct(By.id("remove-sauce-labs-backpack"), 2));
			assertTrue(cartConfirmationItem.isCartCountCorrect(2));
			assertFalse(cartConfirmationItem.containsProduct("Sauce Labs Backpack"));
			test.log(Status.PASS, "Backpack removed successfully.");

			// Remove Bolt T-Shirt
			assertTrue(cartConfirmationItem.removeProduct(By.id("remove-sauce-labs-bolt-t-shirt"), 1));
			assertTrue(cartConfirmationItem.isCartCountCorrect(1));
			assertFalse(cartConfirmationItem.containsProduct("Sauce Labs Bolt T-Shirt"));
			test.log(Status.PASS, "Bolt T-Shirt removed successfully.");

		} catch (AssertionError e) {
			test.log(Status.FAIL, "Product removal test failed: " + e.getMessage());
			throw e;
		}
	}

	@Test
	public void testEmptyCart() {
		test = extent.createTest("Empty Cart Validation");
		test.log(Status.INFO, "Removing all products from the cart to verify empty state.");

		try {
			assertTrue(cartConfirmationItem.removeProduct(By.id("remove-sauce-labs-backpack"), 2));
			assertTrue(cartConfirmationItem.removeProduct(By.id("remove-sauce-labs-bolt-t-shirt"), 1));
			assertTrue(cartConfirmationItem.removeProduct(By.id("remove-test.allthethings()-t-shirt-(red)"), 0));

			assertTrue("Cart should be empty", cartConfirmationItem.isCartCountCorrect(0));
			assertTrue("Cart should be empty", cartConfirmationItem.isCartEmpty());
			test.log(Status.PASS, "Cart successfully emptied and verified as empty.");

		} catch (AssertionError e) {
			test.log(Status.FAIL, "Empty cart validation failed: " + e.getMessage());
			throw e;
		}
	}

	@Test
	public void testCheckoutProcess() {
		test = extent.createTest("Checkout Process");
		test.log(Status.INFO, "Validating the checkout process with selected products.");

		try {
			assertTrue(cartConfirmationItem.checkoutShopping(expectedProducts));
			test.log(Status.PASS, "Checkout process completed successfully.");
		} catch (AssertionError e) {
			test.log(Status.FAIL, "Checkout process failed: " + e.getMessage());
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
