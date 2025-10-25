package pages;

import java.util.List;
import utils.RandomDataGenerator;

import base.Base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutProcessPage extends Base {

	By checkoutTitle = By.className("title");
	public By firstNameField = By.id("first-name");
	public By lastNameField = By.id("last-name");
	public By postalCodeField = By.id("postal-code");
	public By continueButton = By.id("continue");
	By overviewTitle = By.className("title");
	By finishBtn = By.id("finish");
	By backHomeBtn = By.id("back-to-products");
	By orderCompleteTitle = By.cssSelector("h2.complete-header");
	By errorMessage = By.cssSelector("h3[data-test='error']");
	By errorButton = By.cssSelector("button.error-button");

	public CheckoutProcessPage(WebDriver driver) {
		super(driver);
	}

	public boolean isCheckoutPageDisplayed() {
		return isDisplayed(checkoutTitle);
	}

	// Llenar formulario de checkout
	public boolean fillInformation() {
		if (!isCheckoutPageDisplayed()) {
			System.out.println("❌ Checkout Page no está visible.");
			return false;
		}

		String firstName = RandomDataGenerator.randomName();
		String lastName = RandomDataGenerator.generateRandomLastName();
		String postalCode = RandomDataGenerator.generatePostalCode();

		type(firstName, firstNameField);
		type(lastName, lastNameField);
		type(postalCode, postalCodeField);
		click(continueButton);

		// Verificar si aparece mensaje de error
		By errorMessage = By.cssSelector("h3[data-test='error']");
		if (isDisplayed(errorMessage)) {
			System.out.println("❌ Error mostrado: " + driver.findElement(errorMessage).getText());
			return false;
		}

		System.out.println("✅ Checkout info ingresada: " + firstName + " " + lastName + ", " + postalCode);
		return true;
	}

	// Método para verificar que todos los productos esperados estén en el overview
	public boolean verifyProductsInOverview(List<String> expectedProducts) {
		boolean allProductsPresent = true;

		for (String product : expectedProducts) {
			By productLocator = By.xpath("//div[@class='inventory_item_name' and text()='" + product + "']");
			if (!isDisplayed(productLocator)) {
				System.out.println("El producto no está en el resumen: " + product);
				allProductsPresent = false;
			} else {
				System.out.println("Producto encontrado en el resumen: " + product);
			}
		}

		return allProductsPresent;
	}

	public boolean clickFinishIfOverviewDisplayed() {
		// Primero verificamos que el título de Overview esté visible
		if (isDisplayed(overviewTitle)) {

			// Ahora sí hacemos click
			click(finishBtn);
			System.out.println("✅ Click en Finish realizado.");
			return true;
		}
		System.out.println("❌ Checkout Overview Page no está visible.");
		return false;

	}

	public boolean clickBackToHome() {
		if (isDisplayed(orderCompleteTitle)) {
			click(backHomeBtn);
			System.out.println("🏠 Volviendo a la página principal después del pedido.");
			return true;
		}
		System.out.println("❌ La página de confirmación de pedido no está visible.");
		return false;
	}
}
