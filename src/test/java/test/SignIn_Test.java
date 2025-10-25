package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import base.Base;
import pages.SignInPage;
import utils.UserDataFetcher;

public class SignIn_Test {

	private WebDriver driver;
	SignInPage signInPage;

	@Before

	public void setUp() throws Exception {
		Base base = new Base(null);
		driver = base.chromeDriverConnection();
		signInPage = new SignInPage(driver);
		signInPage.visit("https://www.saucedemo.com/");
	}

	@Test
	public void testLoginFromDatabase() {
	    String[] creds = UserDataFetcher.getUserCredentials("standard_user");
	    assertTrue(signInPage.signIn(creds[0], creds[1]));
	    assertTrue(signInPage.isHomePageDisplayed());
	}
	
	@Test
	public void TestLogInWithValidCredentials() {
		System.out.println("🚀 Ejecutando test: Login con credenciales válidas");
		assertTrue("❌ Falló el login con credenciales válidas", signInPage.signIn("standard_user", "secret_sauce"));
		assertTrue("❌ No se mostró la página principal después del login", signInPage.isHomePageDisplayed());
		System.out.println("✅ Login exitoso y página principal visible.");
	}

	@Test
	public void TestShowErrorWithInvalidCredentials() {
		System.out.println("🚀 Ejecutando test: Login con credenciales inválidas");
		assertFalse("❌ El método signIn no debería permitir login con credenciales inválidas",
				signInPage.signIn("invalid_user", "wrong_password"));
		assertTrue("❌ No se mostró el mensaje de error esperado", signInPage.isErrorDisplayed());
		System.out.println("✅ Mensaje de error mostrado correctamente.");
	}

	@Test
	public void TestShowErrorWhenFieldsAreEmpty() {
		System.out.println("🚀 Ejecutando test: Campos vacíos en login");
		assertFalse("❌ El método signIn no debería permitir login con campos vacíos", signInPage.signIn("", ""));
		assertTrue("❌ No se mostró el mensaje de error esperado por campos vacíos", signInPage.isErrorDisplayed());
		System.out.println("✅ Error mostrado correctamente al dejar los campos vacíos.");
	}

	@Test
	public void TestLogoutSuccessfullyAndReturnToLoginPage() {
		System.out.println("🚀 Ejecutando test: Logout del usuario");
		assertTrue("❌ Falló el login previo al logout", signInPage.signIn("standard_user", "secret_sauce"));
		assertTrue("❌ Falló el logout o no regresó al login", signInPage.signOut());
		assertTrue("❌ La página de login no se mostró tras el logout", signInPage.isLoginPageDisplayed());
		System.out.println("✅ Logout exitoso y retorno a página de login confirmado.");
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

}
