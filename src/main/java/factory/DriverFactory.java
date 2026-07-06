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

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver initDriver(String browser) {

        String execution = ConfigReader.get("execution");

        try {

            if ("grid".equalsIgnoreCase(execution)) {

                System.out.println("Running on Selenium Grid...");
                driver.set(createRemoteDriver(browser));

            } else {

                System.out.println("Running Locally...");
                driver.set(createLocalDriver(browser));

                driver.get().manage().window().maximize();

            }

        } catch (Exception e) {

            throw new RuntimeException("Failed to initialize driver", e);

        }

        System.out.println("--------------------------------");
        System.out.println("Thread Id   : " + Thread.currentThread().getId());
        System.out.println("Driver Type : " + driver.get().getClass().getSimpleName());
        System.out.println("Browser     : " + browser);
        System.out.println("Execution   : " + execution);
        System.out.println("--------------------------------");

        return driver.get();

    }

    private static WebDriver createLocalDriver(String browser) {

        switch (browser.toLowerCase()) {

            case "chrome":

                WebDriverManager.chromedriver().setup();
                return new ChromeDriver(new ChromeOptions());

            case "firefox":

                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver(new FirefoxOptions());

            case "edge":

                WebDriverManager.edgedriver().setup();
                return new EdgeDriver(new EdgeOptions());

            default:

                throw new IllegalArgumentException("Unsupported browser : " + browser);

        }

    }

    private static WebDriver createRemoteDriver(String browser) throws Exception {

        String gridUrl = ConfigReader.get("grid.url");

        switch (browser.toLowerCase()) {

            case "chrome":

                return new RemoteWebDriver(
                        new URL(gridUrl),
                        new ChromeOptions());

            case "firefox":

                return new RemoteWebDriver(
                        new URL(gridUrl),
                        new FirefoxOptions());

            case "edge":

                return new RemoteWebDriver(
                        new URL(gridUrl),
                        new EdgeOptions());

            default:

                throw new IllegalArgumentException("Unsupported browser : " + browser);

        }

    }

    public static WebDriver getDriver() {

        return driver.get();

    }

    public static void quitDriver() {

        if (driver.get() != null) {

            driver.get().quit();
            driver.remove();

        }

    }

}