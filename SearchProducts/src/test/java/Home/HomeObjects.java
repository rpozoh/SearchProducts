package Home;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Wait;

public class HomeObjects {

	public static WebElement imgZmartLogo(Wait<WebDriver> wait) {
		return utils.WaitElements.waitElement(wait, "//div[@id='HeaderInfoMiddleLogo']/a/img");
	}
	
	public static WebElement inputSearch(Wait<WebDriver> wait) {
		return utils.WaitElements.waitElement(wait, "//input[@id='strSearch']");
	}
	
	public static WebElement buttonSearch(Wait<WebDriver> wait) {
		return utils.WaitElements.waitElement(wait, "//input[@id='strSearch']/following::input[1]");
	}
	
	public static WebElement divNotFoundProducts(Wait<WebDriver> wait) {
		return utils.WaitElements.waitElement(wait, "//div[@id='ResultadoBusqueda']/div[1]");
	}
	
	public static WebElement aFirstProductDetails(Wait<WebDriver> wait) {
		return utils.WaitElements.waitElement(wait, "//div[@id='ResultadoBusqueda']/div[1]/div[1]/a");
	}
	
	public static WebElement hProductName(Wait<WebDriver> wait) {
		return utils.WaitElements.waitElement(wait, "//div[@id='ficha_producto_int']/h1");
	}
	
	public static WebElement spanSku(Wait<WebDriver> wait) {
		return utils.WaitElements.waitElement(wait, "//div[@id='imagen_producto']/div/span[contains(@class,'sku')]");
	}
	
	public static WebElement divProductAvailable(Wait<WebDriver> wait) {
		return utils.WaitElements.waitElement(wait, "//div[@id='ficha_producto_int']/div[1]");
	}
	
	public static WebElement divCheckStock(Wait<WebDriver> wait) {
		return utils.WaitElements.waitElement(wait, "//div[@id='ttStock1']");
	}
	
	public static WebElement tdWebStock(Wait<WebDriver> wait) {
		return utils.WaitElements.waitElement(wait, "//table[@id='tblStock']/tbody/tr[2]/td[2]");
	}
	
	public static WebElement tdStoreStock(Wait<WebDriver> wait, String store) {
		return utils.WaitElements.waitElement(wait, String.format("//table[@id='tblStock']/tbody/tr/td/b[text()='%s']/parent::td/following::td[1]", store));
	}
}