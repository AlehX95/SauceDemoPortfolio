package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import base.Base;

public class SignInPage extends Base {

	private static final Logger logger = LogManager.getLogger(SignInPage.class);

	By usernameLocator = By.id("user-name");
	By passwordLocator = By.id("password");
	By logoTitle = By.className("login_logo");
	By logInButton = By.id("login-button");
	By signedInTitle = By.className("app_logo");
	By menuBtn = By.id("react-burger-menu-btn");
	By logOutBtnMenu = By.id("logout_sidebar_link");
	By errorMsg = By.cssSelector("h3[data-test='error']");

	public SignInPage(WebDriver driver) {
		super(driver);
	}

	public boolean isHomePageDisplayed() {
		return isDisplayed(signedInTitle);

	}

	public boolean isErrorDisplayed() {
		return isDisplayed(errorMsg);
	}

	public boolean signIn(String username, String password) {
		if (!isDisplayed(logoTitle)) {
			logger.error("❌ Couldnt find the login page.");
			return false;
		}
		logger.info("Trying to login with the user: {}", username);
		type(username, usernameLocator);
		type(password, passwordLocator);
		click(logInButton);
	
		if (isHomePageDisplayed()) {
			logger.info(" Successful login for user: {}", username);
			return true;
		} else if (isErrorDisplayed()) {
			logger.warn("⚠️ Login error for '{}': {}", username, getText(errorMsg));
			return false;
		} else {
			
			logger.warn("⚠️ Uncertain login result. No success or visible error was detected for '{}'.",
					username);
			return false;
		}

	}

	public boolean signOut() {
		try {
			if (!isDisplayed(menuBtn)) {
				logger.warn("⚠️ Menu button not visible. Cannot log out.");
				return false;
			}

			click(menuBtn);
			click(logOutBtnMenu);

			
			if (isLoginPageDisplayed()) {
				logger.info("Logout successful.");
				return true;
			} else {
				logger.warn("⚠️ An attempt was made to log out, but the login page was not detected.");
				return false;
			}

		} catch (Exception e) {
			logger.error("❌ Error trying to log out: ", e);
			return false;
		}
	}

	public boolean isLoginPageDisplayed() {
		return isDisplayed(logInButton) || isDisplayed(usernameLocator);
	}

}
