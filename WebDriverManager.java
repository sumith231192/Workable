package utility;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Reporter;

public class WebDriverManager {
	ConfigFileReader configFileReader;
	private WebDriver driver;

	public WebDriver getDriver() {
		if (driver == null) {

			driver = createDriver();

		}

		return driver;
	}

	private WebDriver createDriver() {
		driver = createLocalDriver();
		return driver;
	}

	private WebDriver createRemoteDriver() {
		throw new RuntimeException("RemoteWebDriver is not yet implemented");
	}

	@SuppressWarnings("deprecation")
	private WebDriver createLocalDriver() {
		try {
			Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe /T");
			Runtime.getRuntime().exec("taskkill /F /IM chrome.exe /T");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

Reporter.log("Initializing Browser.....");
		String browser = "CHROME";
Reporter.log(browser);
		if ("CHROME".equalsIgnoreCase(browser.toUpperCase())) {
			//Create a map to store  preferences 
			Map<String, Object> prefs = new HashMap<String, Object>();

			//add key and value to map as follow to switch off browser notification
			//Pass the argument 1 to allow and 2 to block
			prefs.put("profile.default_content_setting_values.notifications", 2);

			//Create an instance of ChromeOptions 
			ChromeOptions options = new ChromeOptions();

			// set ExperimentalOption - prefs 
			options.setExperimentalOption("prefs", prefs);
			System.setProperty("webdriver.chrome.driver", "./src/main/resources/Drivers/windows/chromedriver123.exe");
			driver = new ChromeDriver(options);

		} else if ("IE".equalsIgnoreCase(browser.toUpperCase())) {
			System.setProperty("webdriver.ie.driver", "./src/main/resources/drivers/IEDriver.exe");
			DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();

			ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			ieCapabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);

			ieCapabilities.setCapability("ie.ensureCleanSession", true);

			driver = new InternetExplorerDriver(ieCapabilities);
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();

		}
		return driver;
	}

	public void quitDriver() {
		driver.manage().deleteAllCookies();
		driver.quit();
	
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void closeDriver() {
		driver.manage().deleteAllCookies();
		driver.close();
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

}