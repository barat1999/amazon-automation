package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;


public class DriverManager {

    public WebDriver getDriver() {
        System.out.println("Browser: " + DataManager.BROWSER);
        WebDriver driver = null;
        switch (DataManager.BROWSER.toUpperCase()) {
            case "CHROME":
                driver = initializeChrome(DataManager.BROWSER_MODE);
                break;
            case "FIREFOX":
                driver = initializeFirefox(DataManager.BROWSER_MODE);
                break;
            default:
                System.err.println("Browser not supported");
        }
        return driver;
    }

    public WebDriver initializeChrome(String BrowserMode) {
        WebDriverManager.chromedriver().browserVersion("127.0.6533.72").setup();

        ChromeOptions option = (ChromeOptions) new ChromeOptions()
                .setAcceptInsecureCerts(true);

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", DataManager.DOWNLOAD_PATH);
        option.setExperimentalOption("prefs", prefs);
        option.addArguments("--no-sandbox");
        option.addArguments("--disable-dev-shm-usage");
        option.addArguments("--no-zygote");
        option.addArguments("--disable-web-security");
        option.addArguments("--ignore-ssl-errors=yes");
        option.addArguments("--remote-allow-origins=*");
        option.addArguments("window-size=1400,720");
        option.addArguments("user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.4896.127 Safari/537.36");
        option.addArguments("--ignore-certificate-errors");
        if (BrowserMode.equals("Headless")) {
            option.addArguments("--headless");
        }
        WebDriver driver = new ChromeDriver(option);
        driver.manage().window().maximize();
        return driver;
    }

    public WebDriver initializeFirefox(String BrowserMode) {
        WebDriverManager.firefoxdriver().browserVersion("129.0.1").setup();
        FirefoxOptions options = new FirefoxOptions()
                .addArguments("--disable-notifications", "--disable-dev-shm-usage")
                .setAcceptInsecureCerts(true);

        if (BrowserMode.equals("Headless")) {
            options.addArguments("--headless");
        }

        WebDriver driver = new FirefoxDriver(options);
        driver.manage().window().maximize();
        return driver;
    }

}
