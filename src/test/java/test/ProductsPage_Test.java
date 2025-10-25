package test;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import base.Base;
import pages.CartConfirmationItems;
import pages.ProductsPage;
import pages.SignInPage;


public class ProductsPage_Test {

	WebDriver driver;
	SignInPage signInPage;
	ProductsPage productsPage;

	@Before
	public void setUp() throws InterruptedException {
        Base base = new Base(null);
        driver = base.chromeDriverConnection();

        signInPage = new SignInPage(driver);
        signInPage.visit("https://www.saucedemo.com/");
        signInPage.signIn("standard_user", "secret_sauce");

        productsPage = new ProductsPage(driver);
        //productsPage.waitForVisible(By.className("inventory_list")); // opcional
    }
	@Test
	public void testAddProductFromMainPage() {
		assertTrue(productsPage.addTshirtRedFromMainPage());
		assertTrue(productsPage.goToViewCartPage());
	    CartConfirmationItems cart = new CartConfirmationItems(driver);
	    assertTrue(cart.containsProduct("Test.allTheThings() T-Shirt (Red)"));
	}

	
	
	@Test
	public void testProductPageElementsVisible() {
	    assertTrue(productsPage.isProductsPageDisplayed());
	}
	
	
	@Test
	public void testAddProductFromDetailPage() {
		assertTrue(productsPage.addBackpackFromDetailPage());
		assertTrue(productsPage.goToViewCartPage());
	    CartConfirmationItems cart = new CartConfirmationItems(driver);
	   assertTrue(cart.containsProduct("Sauce Labs Backpack"));
	}

	@Test
	public void testAddMultipleProductsAndGoToCart() {
		assertTrue(productsPage.addBackpackFromDetailPage());
		assertTrue(productsPage.addTshirtLabsBoltFromDetailPage());
		assertTrue(productsPage.addTshirtRedFromMainPage());
		assertTrue(productsPage.goToViewCartPage());
	    
	        
	}
	
	
	@Test
	public void testAddMultipleProductsFromDifferentFlows() {
		assertTrue(productsPage.addBackpackFromDetailPage());
		assertTrue(productsPage.addTshirtLabsBoltFromDetailPage());
		assertTrue(productsPage.addTshirtRedFromMainPage());
		assertTrue(productsPage.goToViewCartPage());

	    CartConfirmationItems cart = new CartConfirmationItems(driver);
		assertTrue(cart.areProductsInCart(
				Arrays.asList(
	        "Sauce Labs Backpack",
	        "Sauce Labs Bolt T-Shirt",
	        "Test.allTheThings() T-Shirt (Red)"
	    )));
	}
	
	@Test
	public void testRemoveMultipleProductsFromMainPage() {
	    // Agregar varios productos
		assertTrue(productsPage.addBackpackFromDetailPage());
		assertTrue(productsPage.addTshirtLabsBoltFromDetailPage());
		assertTrue(productsPage.addTshirtRedFromMainPage());
	    assertEquals(3, productsPage.getCartCount());

	    // Remover algunos
	    assertTrue(productsPage.removeBackpackFromMainPage());
	    assertTrue(productsPage.removeSauceLabsTshirtFromMainPage());
	    assertEquals(1, productsPage.getCartCount());

	    // Verificar que solo queda la T-Shirt roja
	    CartConfirmationItems cart = new CartConfirmationItems(driver);
	    assertTrue(cart.containsProduct("Test.allTheThings() T-Shirt (Red)"));
	}
	@After
	public void tearDown() throws Exception {
		 driver.quit();
	}

}
