package pages;


import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import base.Base;

public class ProductsPage extends Base {

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
	            System.out.println("❌ No se puede agregar producto: Products Page no visible.");
	            return false;
	        }
	        try {
	            click(addButtonLocator);
	            System.out.println("✅ Producto agregado correctamente: " + addButtonLocator);
	            return true;
	        } catch (Exception e) {
	            System.out.println("⚠️ Error al agregar producto: " + e.getMessage());
	            return false;
	        }
	    }

	// Generic remove product
	 public boolean removeProductFromMainPage(By removeButtonLocator) {
	        if (!isProductsPageDisplayed()) {
	            System.out.println("❌ Products Page no está visible.");
	            return false;
	        }
	        try {
	            click(removeButtonLocator);
	            System.out.println("🗑️ Producto eliminado desde el Main Page: " + removeButtonLocator);
	            return true;
	        } catch (Exception e) {
	            System.out.println("⚠️ Error al eliminar producto: " + e.getMessage());
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
                System.out.println("🎒 Backpack agregado desde detalle correctamente.");
                return true;
            } else {
                System.out.println("❌ Products Page no está visible.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("⚠️ Error al agregar backpack desde detalle: " + e.getMessage());
            return false;
        }
    }
    public boolean addTshirtLabsBoltFromDetailPage() {
        try {
            if (isDisplayed(titleLocator)) {
                click(boltTshirtLink);
                click(addToCartDetailBtn);
                click(backToProductsBtn);
                System.out.println("👕 T-shirt Labs Bolt agregada desde detalle correctamente.");
                return true;
            } else {
                System.out.println("❌ Products Page no está visible.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("⚠️ Error al agregar T-shirt Labs Bolt desde detalle: " + e.getMessage());
            return false;
        }
    }

	// From Main Page
    public boolean addTshirtRedFromMainPage() {
        return addProduct(addAllthesethingsTshirtBtnMain);
    }


  //Go to Cart
    public boolean goToViewCartPage() {
        try {
            if (isDisplayed(titleLocator)) {
                click(shoppingCartLink);
                System.out.println("🛒 Navegando a la vista del carrito...");
                return true;
            } else {
                System.out.println("❌ Products Page no está visible.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("⚠️ Error al intentar ir al carrito: " + e.getMessage());
            return false;
        }
    }
}