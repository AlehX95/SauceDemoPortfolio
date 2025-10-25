package pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import base.Base;

public class SignInPage extends Base {

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
			System.out.println("❌ Sign In Page no fue encontrada");
			return false;
		}
		type(username, usernameLocator);
		type(password, passwordLocator);
		click(logInButton);
		// Espera el resultado: preferimos chequear la Home o el Error
		// isHomePageDisplayed() / isErrorDisplayed() ya usan waits en Base
		if (isHomePageDisplayed()) {
			System.out.println("✅ Sign in exitoso para: " + username);
			return true;
		} else if (isErrorDisplayed()) {
			System.out.println("❌ Login fallido para: " + username + " - Mensaje: " + getText(errorMsg));
			return false;
		} else {
			// fallback: no apareció home ni error (posible timeout)
			System.out.println("⚠️ Resultado de login incierto para: " + username);
			return false;
		}
	}
	
	
	public boolean signOut() {
		if (!isDisplayed(menuBtn)) {
			System.out.println("❌ El botón del menú no está visible. No se puede cerrar sesión.");
			return false;
		}

		click(menuBtn);
		click(logOutBtnMenu);
		System.out.println("✅ Sign out realizado correctamente.");
		return true;
	}

	public boolean isLoginPageDisplayed() {
		return isDisplayed(logInButton) || isDisplayed(usernameLocator);
	}

}


