package base;

import java.util.List;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Base {

	protected WebDriver driver;
	private WebDriverWait wait;
	protected static final Logger logger = LogManager.getLogger(Base.class);

	public Base(WebDriver driver) {
		this.driver = driver;
		if (driver != null) {
			this.wait = new WebDriverWait(driver, 3); //use 3 seconds
		}
	}

	public WebDriver chromeDriverConnection() {
		//System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver/chromedriver.exe");
		 WebDriverManager.chromedriver().setup();

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--incognito");
		options.addArguments("--start-maximized");
		options.addArguments("--disable-notifications");
		options.addArguments("--remote-allow-origins=*");

		driver = new ChromeDriver(options);
		wait = new WebDriverWait(driver, 3);
		return driver;
	}

	// ---------------------------
	// interaction methods
	// ---------------------------

	public WebElement findElement(By locator) {
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public List<WebElement> findElements(By locator) {
		return driver.findElements(locator);
	}

	public String getText(WebElement element) {
		return element.getText().trim();
	}

	public String getText(By locator) {
		return findElement(locator).getText().trim();
	}

	public void type(String inputText, By locator) {
		try {
			WebElement element = findElement(locator);
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			element.clear();
			element.sendKeys(inputText);
		} catch (TimeoutException e) {
			logger.error("❌ Timeout while writing to: " + locator, e);
		} catch (Exception e) {
			logger.error("❌ Unexpected error while writing to:" + locator, e);
		}
	}

	public void click(By locator) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			driver.findElement(locator).click();
			logger.info("Successful click on the element: " + locator);
		} catch (TimeoutException e) {
			logger.error("❌ Timeout when trying to click on: " + locator, e);
		} catch (ElementClickInterceptedException e) {
			logger.warn("Item intercepted, trying again: " + locator);
		} catch (Exception e) {
			logger.error("❌ Unknown error when clicking on: " + locator, e);

		}
	}



	public boolean isDisplayed(By locator) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			logger.debug("The element " + locator + " is visible: ");
			return driver.findElement(locator).isDisplayed();
		} catch (TimeoutException | NoSuchElementException e) {
			logger.warn("The element " + locator + " not found on the page.");
			return false;
		}
	}

	public void waitForVisible(By locator) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			logger.debug("Element visible: " + locator);
		} catch (TimeoutException e) {
			logger.error(" Timeout waiting the visibility of: " + locator, e);
			throw e;
		}
	}

	public void waitForClickable(By locator) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			logger.debug("Element clickable: " + locator);
		} catch (TimeoutException e) {
			logger.error(" Timeout waiting for the element to be clickable: " + locator, e);
			throw e;
		}
	}

	public void visit(String url) {
		try {
			driver.get(url);
			logger.info(" Browsing to the URL: " + url);
		} catch (Exception e) {
			logger.error("❌ Error while trying to browse to the URL: " + url, e);
		}
	}

	public void quit() {
		if (driver != null) {
			try {
				driver.quit();
				logger.info("Browser closed successfully.");
			} catch (Exception e) {
				logger.error("⚠️ Error closing browser.", e);
			}
		}
	}

}
