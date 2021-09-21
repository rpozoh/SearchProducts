package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;

import com.google.common.base.Function;

public class WaitElements {
	
	public static WebElement waitElement(Wait<WebDriver> wait, String xpath) {
		WebElement element = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return driver.findElement(By.xpath(xpath));
			}
		});
		return element;
	}
	
	public static void waitForEnabledElement(Wait<WebDriver> wait, WebElement webElement) {
		wait.until(ExpectedConditions.visibilityOf(webElement));
	}
}