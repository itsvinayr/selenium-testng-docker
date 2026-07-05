package factory;

import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import utils.ConfigReader;

public class DriverFactory {

    private static WebDriver driver;

    public static WebDriver initDriver() {

        String execution = ConfigReader.get("execution");
        String browser = ConfigReader.get("browser");

        try {

            if ("grid".equalsIgnoreCase(execution)) {

                System.out.println("Running on Selenium Grid...");
                driver = createRemoteDriver(browser);

            } else {

                System.out.println("Running Locally...");
                driver = createLocalDriver(browser);

                // Maximize only for local execution
                driver.manage().window().maximize();
            }

        } catch (Exception e) {

            throw new RuntimeException("Failed to initialize driver", e);

        }

        System.out.println("--------------------------------");
        System.out.println("Driver Type : " + driver.getClass().getSimpleName());
        System.out.println("Browser     : " + browser);
        System.out.println("Execution   : " + execution);
        System.out.println("--------------------------------");

        return driver;
    }

    private static WebDriver createLocalDriver(String browser) {

        switch (browser.toLowerCase()) {

            case "chrome":

                WebDriverManager.chromedriver().setup();

                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");

                return new ChromeDriver(chromeOptions);

            case "firefox":

                WebDriverManager.firefoxdriver().setup();

                FirefoxOptions firefoxOptions = new FirefoxOptions();

                return new FirefoxDriver(firefoxOptions);

            case "edge":

                WebDriverManager.edgedriver().setup();

                EdgeOptions edgeOptions = new EdgeOptions();

                return new EdgeDriver(edgeOptions);

            default:

                throw new IllegalArgumentException("Unsupported browser : " + browser);

        }

    }

    private static WebDriver createRemoteDriver(String browser) throws Exception {

        String gridUrl = ConfigReader.get("grid.url");

        switch (browser.toLowerCase()) {

            case "chrome":

                ChromeOptions chromeOptions = new ChromeOptions();

                return new RemoteWebDriver(new URL(gridUrl), chromeOptions);

            case "firefox":

                FirefoxOptions firefoxOptions = new FirefoxOptions();

                return new RemoteWebDriver(new URL(gridUrl), firefoxOptions);

            case "edge":

                EdgeOptions edgeOptions = new EdgeOptions();

                return new RemoteWebDriver(new URL(gridUrl), edgeOptions);

            default:

                throw new IllegalArgumentException("Unsupported browser : " + browser);

        }

    }

    public static WebDriver getDriver() {

        return driver;

    }

    public static void quitDriver() {

        if (driver != null) {

            driver.quit();
            driver = null;

        }

    }

}