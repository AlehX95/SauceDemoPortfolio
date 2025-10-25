package test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pages.CartConfirmationItems;
import pages.CheckoutProcessPage;
import pages.ProductsPage;
import pages.SignInPage;

public class CheckoutProcessPage_Test {
	
	WebDriver driver;
	SignInPage signInPage;
	ProductsPage productsPage;
	CartConfirmationItems cartConfirmationItem;
	List<String> expectedProducts = Arrays.asList("Sauce Labs Backpack", "Sauce Labs Bolt T-Shirt",
			"Test.allTheThings() T-Shirt (Red)");
	CheckoutProcessPage checkoutProcessPage;
	

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
		assertTrue(cartConfirmationItem.isCartPageDisplayed());
		assertTrue(cartConfirmationItem.isCartCountCorrect(3));
		assertTrue(cartConfirmationItem.areProductsInCart(expectedProducts));
		cartConfirmationItem.checkoutShopping(expectedProducts);
		
		checkoutProcessPage = new CheckoutProcessPage(driver);
		
		
	}


	@Test
public void testCheckoutPageIsDisplayed() {
	assertTrue(checkoutProcessPage.isCheckoutPageDisplayed());
	}
	
	@Test
	public void testFillInformation() {
		
		assertTrue(checkoutProcessPage.fillInformation());
		checkoutProcessPage.clickFinishIfOverviewDisplayed();
	}
	
	@Test
	public void testCompleteCheckoutAndBackToHome() {
	    // Llenar información del checkout
	    assertTrue(checkoutProcessPage.fillInformation());

	    // Finalizar compra
	    checkoutProcessPage.clickFinishIfOverviewDisplayed();

	    // Volver al home/catálogo
	    checkoutProcessPage.clickBackToHome();

	    // Validar que el catálogo se muestra de nuevo
	    ProductsPage productsPage = new ProductsPage(driver);
	    assertTrue(productsPage.isProductsPageDisplayed());
	}
	
    @Test
    public void testEmptyFirstNameShowsError() {
        checkoutProcessPage.type("", checkoutProcessPage.firstNameField);
        checkoutProcessPage.type("LastName", checkoutProcessPage.lastNameField);
        checkoutProcessPage.type("12345", checkoutProcessPage.postalCodeField);
        checkoutProcessPage.click(checkoutProcessPage.continueButton);


        By errorMessage = By.cssSelector("h3[data-test='error']");
        assertTrue("Error message not displayed", checkoutProcessPage.isDisplayed(errorMessage));
        assertTrue(driver.findElement(errorMessage).getText().contains("First Name is required"));
    }

	
	
	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

}
