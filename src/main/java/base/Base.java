package base;


import java.util.List;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Base {

    protected WebDriver driver;
    private WebDriverWait wait;

    public Base(WebDriver driver) {
        this.driver = driver;
        if (driver != null) {
            this.wait = new WebDriverWait(driver, 3); // Selenium 3 usa segundos directamente
        }
    }

    public WebDriver chromeDriverConnection() {
        System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver/chromedriver.exe");

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
    // 🔍 Métodos de interacción
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
            System.out.println("⏰ Tiempo de espera excedido al escribir en: " + locator);
        } catch (Exception e) {
            System.out.println("❌ Error al escribir en: " + locator + " → " + e.getMessage());
        }
    }

    public void click(By locator) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            driver.findElement(locator).click();
        } catch (TimeoutException e) {
            System.out.println("⏰ Tiempo de espera excedido al hacer click en: " + locator);
        } catch (ElementClickInterceptedException e) {
            System.out.println("⚠️ Elemento no clickeable (interceptado): " + locator);
        } catch (Exception e) {
            System.out.println("❌ Error al hacer click en: " + locator + " → " + e.getMessage());
        }
    }

    // ---------------------------
    // 👁️ Métodos utilitarios
    // ---------------------------

    public boolean isDisplayed(By locator) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return driver.findElement(locator).isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    public void waitForVisible(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void waitForClickable(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void visit(String url) {
        driver.get(url);
        System.out.println("🌐 Navegando a: " + url);
    }

    public void quit() {
        if (driver != null) {
            driver.quit();
            System.out.println("🚪 Navegador cerrado correctamente.");
        }
    }
}
