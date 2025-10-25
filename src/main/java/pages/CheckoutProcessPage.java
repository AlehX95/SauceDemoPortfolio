package pages;

import java.util.List;
import utils.RandomDataGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import base.Base;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutProcessPage extends Base {

	private static final Logger logger = LogManager.getLogger(CheckoutProcessPage.class);

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

	public boolean fillInformation() {
		if (!isCheckoutPageDisplayed()) {
			logger.error("❌ Checkout Page is not visible.");
			return false;
		}

		try {
			String firstName = RandomDataGenerator.randomName();
			String lastName = RandomDataGenerator.generateRandomLastName();
			String postalCode = RandomDataGenerator.generatePostalCode();

			type(firstName, firstNameField);
			type(lastName, lastNameField);
			type(postalCode, postalCodeField);
			click(continueButton);

			By errorMessage = By.cssSelector("h3[data-test='error']");
			if (isDisplayed(errorMessage)) {
				String errorText = driver.findElement(errorMessage).getText();
				logger.error("❌ Error displayed on Checkout Page: {}", errorText);
				return false;
			}

			logger.info("Checkout information entered successfully: {} {} , {}", firstName, lastName, postalCode);
			return true;
		} catch (Exception e) {
			logger.error("❌ Exception while filling checkout information: {}", e.getMessage(), e);
			return false;
		}
	}

	public boolean verifyProductsInOverview(List<String> expectedProducts) {
		boolean allProductsPresent = true;

		for (String product : expectedProducts) {
			By productLocator = By.xpath("//div[@class='inventory_item_name' and text()='" + product + "']");
			if (!isDisplayed(productLocator)) {
				logger.error("❌ Product not found in overview: {}", product);
				allProductsPresent = false;
			} else {
				logger.info("Product found in overview: {}", product);
			}
		}

		return allProductsPresent;
	}

	public boolean clickFinishIfOverviewDisplayed() {

		if (isDisplayed(overviewTitle)) {
			click(finishBtn);
			logger.info("Click on Finish button executed successfully.");
			return true;
		}
		logger.error("❌ Checkout Overview Page is not visible.");
		return false;

	}

	public boolean clickBackToHome() {
		if (isDisplayed(orderCompleteTitle)) {
			click(backHomeBtn);
			logger.info("Navigated back to Home Page after order completion.");
			return true;
		}
		logger.error("❌ Order confirmation page is not visible.");
		return false;
	}
}
