package utils;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class WD {
	
	public static WebDriver driver;
	
	public static WebDriver setupDriver() {
		try {
			driver = null;
			ChromeOptions options = new ChromeOptions();
			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			DesiredCapabilities caps = DesiredCapabilities.chrome();
			String path = Constants.CURRENT_DIRECTORY + "\\Drivers\\Chrome\\chromedriver.exe";
			String downloadDefaultDirectory = Constants.CURRENT_DIRECTORY + "\\Datapool\\";
			System.setProperty("webdriver.chrome.driver", path);
			chromePrefs.put("download.default_directory", downloadDefaultDirectory);
			options.setExperimentalOption("prefs", chromePrefs);
			caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			caps.setCapability(ChromeOptions.CAPABILITY, options);
			
			driver = new ChromeDriver(caps);
			return driver;
		} catch(Exception ex) {
			ex.getCause().printStackTrace();
			System.out.print("Error al ejecutar driver " + ex.toString());
		}
		return null;
	}

	public static void closeDriver() {
		driver.quit();
	}
}