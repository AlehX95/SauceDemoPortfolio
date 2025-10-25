package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import base.Base;

public class CartConfirmationItems extends Base {

	private static final Logger logger = LogManager.getLogger(CartConfirmationItems.class);

	By cartTitle = By.className("title");
	By cartItems = By.cssSelector("[data-test='inventory-item-name']");
	By cartQuantity = By.cssSelector("[data-test='shopping-cart-badge']");
	By continueShoppingBtn = By.id("continue-shopping");
	By checkoutBtn = By.id("checkout");

	public CartConfirmationItems(WebDriver driver) {
		super(driver);

	}

	public boolean isCartPageDisplayed() {
		if (isDisplayed(cartTitle))
			logger.info("Cart Page is visible.");
		else
			logger.error("❌ Cart Page is not visible.");
		return isDisplayed(cartTitle);
	}

	public boolean isCartCountCorrect(int expectedCount) {
		try {
			WebElement badge = driver.findElement(cartQuantity);
			int actualCount = Integer.parseInt(badge.getText().trim());
			return actualCount == expectedCount;
		} catch (NoSuchElementException e) {
			// If badge is missing (empty cart)
			return expectedCount == 0;
		}
	}

	public boolean areProductsInCart(List<String> expectedProducts) {
		waitForVisible(cartItems);
		List<WebElement> items = driver.findElements(cartItems);
		List<String> itemTexts = new ArrayList<>();

		for (WebElement item : items) {
			itemTexts.add(item.getText().trim().toLowerCase());
		}

		for (String name : expectedProducts) {
			if (!itemTexts.contains(name.trim().toLowerCase())) {
				logger.error("❌ Expected product '{}' not found in cart.", name);
				return false;
			}
		}
		logger.info("All expected products are present in the cart.");
		return true;
	}

	public boolean containsProduct(String productName) {
		waitForVisible(cartItems);
		List<WebElement> items = driver.findElements(cartItems);
		for (WebElement item : items) {
			if (item.getText().trim().equalsIgnoreCase(productName)) {
				logger.info("Product found in cart: {}", productName);
				return true;
			}
		}
		logger.error("❌ Product not found in cart: {}", productName);
		return false;
	}

	public List<String> getCartProducts() {
		waitForVisible(cartItems);
		List<WebElement> items = driver.findElements(cartItems);
		List<String> names = new ArrayList<>();
		for (WebElement item : items) {
			names.add(item.getText().trim());
		}
		return names;
	}

	public boolean removeProduct(By removeButtonLocator, int expectedCountAfterRemove) {
		try {
			if (!isDisplayed(removeButtonLocator)) {
				logger.error("❌ Remove button not visible.");
				return false;
			}

			click(removeButtonLocator);
			logger.info("Product removed from cart.");

			if (isCartCountCorrect(expectedCountAfterRemove)) {
				logger.info("Cart count updated correctly to {}", expectedCountAfterRemove);
				return true;
			} else {
				logger.error("❌ Cart count does not match expected value after removal.");
				return false;
			}

		} catch (Exception e) {
			logger.error("❌ Error removing product from cart: {}", e.getMessage(), e);
			return false;
		}
	}

	public boolean isCartEmpty() {
		if (driver.findElements(cartItems).isEmpty())
			logger.info("Cart is empty.");
		else
			logger.error("❌ Cart is not empty.");
		return driver.findElements(cartItems).isEmpty();
	}

	public boolean continueShopping(List<String> expectedProducts) {
		if (areProductsInCart(expectedProducts) && isCartCountCorrect(expectedProducts.size())) {
			click(continueShoppingBtn);
			logger.info("Products verified. Continuing shopping.");
			return true;
		} else {
			logger.error("❌ Error: Products missing or count incorrect in cart.");
			return false;
		}
	}

	public boolean checkoutShopping(List<String> expectedProducts) {
		if (areProductsInCart(expectedProducts) && isCartCountCorrect(expectedProducts.size())) {
			click(checkoutBtn);
			logger.info("Products verified. Proceeding to checkout.");
			return true;
		} else {
			logger.error("❌ Error: Cart does not contain correct products or quantity mismatch.");
			return false;
		}
	}

}
