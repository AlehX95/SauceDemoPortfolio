package pages;

import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import base.Base;

public class ProductsPage extends Base {

	private static final Logger logger = LogManager.getLogger(ProductsPage.class);

	// Locators

	By titleLocator = By.className("app_logo");
	By inventoryListLocator = By.className("inventory_list");

	// Products links
	By backPackLink = By.xpath("//div[@class='inventory_item_name ' and text()='Sauce Labs Backpack']");
	By boltTshirtLink = By.xpath("//div[@data-test='inventory-item-name' and text()='Sauce Labs Bolt T-Shirt']");

	// Buttons
	By addToCartDetailBtn = By.cssSelector("[data-test='add-to-cart']");
	By backToProductsBtn = By.id("back-to-products");

	// Add products from Main Page
	By addRedTshirtBtnMain = By.id("add-to-cart-test.allthethings()-t-shirt-(red)");
	By addBackpackBtnMain = By.id("add-to-cart-sauce-labs-backpack");
	By addAllthesethingsTshirtBtnMain = By.id("add-to-cart-test.allthethings()-t-shirt-(red)");

	// Remove buttons from Main Page
	By removeBackpackBtnFromMainPage = By.id("remove-sauce-labs-backpack");
	By removeAllthesethingsTshirtBtnFromMaonPage = By.id("remove-test.allthethings()-t-shirt-(red)");
	By removeSaucelabsTshirtBtnFromMainPage = By.id("remove-sauce-labs-bolt-t-shirt");

	By shoppingCartLink = By.className("shopping_cart_link");

	// Constructor
	public ProductsPage(WebDriver driver) {
		super(driver);

	}

	// PageChecks
	public boolean isProductsPageDisplayed() {
		return isDisplayed(titleLocator);
	}

	// Actions

	// Generic add product

	public boolean addProduct(By addButtonLocator) {
		if (!isProductsPageDisplayed()) {
			logger.error("❌ Cannot add product: Products Page is not visible.");
			return false;
		}
		try {
			click(addButtonLocator);
			logger.info("Product added successfully: {}", addButtonLocator);
			return true;
		} catch (Exception e) {
			logger.error("❌ Error adding product: {}", e.getMessage(), e);
			return false;
		}
	}

	// Generic remove product
	public boolean removeProductFromMainPage(By removeButtonLocator) {
		if (!isProductsPageDisplayed()) {
			logger.error("❌ Cannot remove product: Products Page is not visible.");
			return false;
		}
		try {
			click(removeButtonLocator);
			logger.info("Product removed successfully from Main Page: {}", removeButtonLocator);
			return true;
		} catch (Exception e) {
			logger.error("❌ Error removing product from Main Page: {}", e.getMessage(), e);
			return false;
		}
	}

	public boolean removeBackpackFromMainPage() {
		return removeProductFromMainPage(removeBackpackBtnFromMainPage);
	}

	public boolean removeAllThesethingsTshirtFromMainPage() {
		return removeProductFromMainPage(removeAllthesethingsTshirtBtnFromMaonPage);
	}

	public boolean removeSauceLabsTshirtFromMainPage() {
		return removeProductFromMainPage(removeSaucelabsTshirtBtnFromMainPage);
	}

	// Get current cart count from badge
	public int getCartCount() {
		try {
			WebElement badge = driver.findElement(By.cssSelector("[data-test='shopping-cart-badge']"));
			return Integer.parseInt(badge.getText().trim());
		} catch (NoSuchElementException e) {
			return 0; // Carrito vacío
		}
	}

	// Specific Product Flows

	// From Detail Page
	public boolean addBackpackFromDetailPage() {
		try {
			if (isDisplayed(titleLocator)) {
				click(backPackLink);
				click(addToCartDetailBtn);
				click(backToProductsBtn);
				logger.info("Backpack added successfully from detail page.");
				return true;
			} else {
				logger.error("❌ Cannot add backpack: Products Page is not visible.");
				return false;
			}
		} catch (Exception e) {
			logger.error("❌ Error adding backpack from detail page: {}", e.getMessage(), e);
			return false;
		}
	}

	public boolean addTshirtLabsBoltFromDetailPage() {
		try {
			if (isDisplayed(titleLocator)) {
				click(boltTshirtLink);
				click(addToCartDetailBtn);
				click(backToProductsBtn);
				logger.info("T-shirt Labs Bolt added successfully from detail page.");
				return true;
			} else {
				logger.error("❌ Products Page is not visible.");
				return false;
			}
		} catch (Exception e) {
			logger.error("❌ Error adding T-shirt Labs Bolt from detail page: {}", e.getMessage(), e);
			return false;
		}
	}

	// From Main Page
	public boolean addTshirtRedFromMainPage() {
		return addProduct(addAllthesethingsTshirtBtnMain);
	}

	// Go to Cart
	public boolean goToViewCartPage() {
		try {
			if (isDisplayed(titleLocator)) {
				click(shoppingCartLink);
				 logger.info("Navigating to the cart page.");
				return true;
			} else {
				logger.error("❌ Cannot navigate to cart: Products Page is not visible.");
				return false;
			}
		} catch (Exception e) {
			logger.error("❌ Error while trying to navigate to the cart page: {}", e.getMessage(), e);
			return false;
		}
	}
}