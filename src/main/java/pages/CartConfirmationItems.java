package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import base.Base;

public class CartConfirmationItems extends Base {

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
			System.out.println("🟢 Cart Page visible");
		else
			System.out.println("❌ Cart Page no visible");
		return isDisplayed(cartTitle);
	}

	public boolean isCartCountCorrect(int expectedCount) {
		try {
			WebElement badge = driver.findElement(cartQuantity);
			int actualCount = Integer.parseInt(badge.getText().trim());
			return actualCount == expectedCount;
		} catch (NoSuchElementException e) {
			// Si no aparece el badge (por ejemplo, carrito vacío)
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
				return false; // Falta uno
			}
		}
		System.out.println("✅ Todos los productos esperados están en el carrito");
		return true; // Todos están
	}

	public boolean containsProduct(String productName) {
		waitForVisible(cartItems);
		List<WebElement> items = driver.findElements(cartItems);
		for (WebElement item : items) {
			if (item.getText().trim().equalsIgnoreCase(productName)) {
				System.out.println("✅ Producto encontrado: " + productName);
				return true;
			}
		}
		System.out.println("❌ Producto no encontrado: " + productName);
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
				System.out.println("❌ Botón de remover no visible");
				return false;
			}

			click(removeButtonLocator);
			System.out.println("🗑️ Producto eliminado del carrito");

			if (isCartCountCorrect(expectedCountAfterRemove)) {
				System.out.println("✅ Contador actualizado correctamente a " + expectedCountAfterRemove);
				return true;
			} else {
				System.out.println("❌ Contador NO coincide tras remover producto");
				return false;
			}

		} catch (Exception e) {
			System.out.println("❌ Error al intentar remover: " + e.getMessage());
			return false;
		}
	}

	public boolean isCartEmpty() {
		if (driver.findElements(cartItems).isEmpty())
			System.out.println("🟢 Carrito vacío");
		else
			System.out.println("❌ Carrito tiene productos");
		return driver.findElements(cartItems).isEmpty();
	}

	public boolean continueShopping(List<String> expectedProducts) {
		if (areProductsInCart(expectedProducts) && isCartCountCorrect(expectedProducts.size())) {
			click(continueShoppingBtn);
			System.out.println("✅ Productos verificados correctamente. Continuando con la compra.");
			return true;
		} else {
			System.out.println("❌ Error: productos o cantidad incorrecta.");
			return false;
		}
	}

	public boolean checkoutShopping(List<String> expectedProducts) {
		if (areProductsInCart(expectedProducts) && isCartCountCorrect(expectedProducts.size())) {

			click(checkoutBtn);
			System.out.println("Productos verificados correctamente. Continuando con la compra.");
			return true;
		} else {
			System.out.println("Error: el carrito no tiene los productos correctos o la cantidad no coincide.");
			return false;
		}
	}

}
