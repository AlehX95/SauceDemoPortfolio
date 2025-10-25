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
import pages.ProductsPage;
import pages.SignInPage;

public class CartConfirmationItems_Test {

	WebDriver driver;
	SignInPage signInPage;
	ProductsPage productsPage;
	CartConfirmationItems cartConfirmationItem;
	List<String> expectedProducts = Arrays.asList("Sauce Labs Backpack", "Sauce Labs Bolt T-Shirt",
			"Test.allTheThings() T-Shirt (Red)");

	@Before
    public void setUp() throws InterruptedException {
        driver = new SignInPage(null).chromeDriverConnection();
        signInPage = new SignInPage(driver);
        signInPage.visit("https://www.saucedemo.com/");
        signInPage.signIn("standard_user", "secret_sauce");

        productsPage = new ProductsPage(driver);

        // Agregamos productos para los tests iniciales
        productsPage.addBackpackFromDetailPage();
        productsPage.addTshirtLabsBoltFromDetailPage();
        productsPage.addTshirtRedFromMainPage();
        productsPage.goToViewCartPage();

        cartConfirmationItem = new CartConfirmationItems(driver);
    
	}

	@Test
	public void testCartPageIsDisplayed() {
	    assertTrue("La página del carrito no se mostró correctamente", cartConfirmationItem.isCartPageDisplayed());
	}

	@Test
	public void testCartCountIsCorrect() {
	    assertTrue("El número de productos en el carrito no es correcto", cartConfirmationItem.isCartCountCorrect(3));
	}

	@Test
	public void testProductsInCart() {
	    assertTrue("Los productos esperados no están en el carrito", cartConfirmationItem.areProductsInCart(expectedProducts));
	}
	
    @Test
    public void testRemoveProductFromCart() {
        // Remover la mochila
    	assertTrue(cartConfirmationItem.removeProduct(By.id("remove-sauce-labs-backpack"), 2));
        assertTrue(cartConfirmationItem.isCartCountCorrect(2));
        assertFalse(cartConfirmationItem.containsProduct("Sauce Labs Backpack"));

        // Remover otra camiseta
        assertTrue(cartConfirmationItem.removeProduct(By.id("remove-sauce-labs-bolt-t-shirt"), 1));
        assertTrue(cartConfirmationItem.isCartCountCorrect(1));
        assertFalse(cartConfirmationItem.containsProduct("Sauce Labs Bolt T-Shirt"));
    }

    @Test
    public void testEmptyCart() {
        // Vaciar el carrito
    	assertTrue(cartConfirmationItem.removeProduct(By.id("remove-sauce-labs-backpack"), 2));
    	assertTrue(cartConfirmationItem.removeProduct(By.id("remove-sauce-labs-bolt-t-shirt"), 1));
    	assertTrue(cartConfirmationItem.removeProduct(By.id("remove-test.allthethings()-t-shirt-(red)"), 0));

        assertTrue("El carrito debería estar vacío", cartConfirmationItem.isCartCountCorrect(0));
        assertTrue("El carrito debería estar vacío", cartConfirmationItem.isCartEmpty());
    }

	@Test
	public void testCheckoutProcess() {
		assertTrue(cartConfirmationItem.checkoutShopping(expectedProducts));
	}


	

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}
}
