package factory;

import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import utils.ConfigReader;

public class DriverFactory {

    private static WebDriver driver;

    public static WebDriver initDriver() {

        String execution = ConfigReader.get("execution");
        String browser = ConfigReader.get("browser");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1920,1080");

        try {

            if ("grid".equalsIgnoreCase(execution)) {

                System.out.println("Running on Selenium Grid...");

                driver = new RemoteWebDriver(
                        new URL(ConfigReader.get("grid.url")),
                        options);

            } else {

                System.out.println("Running Locally...");

                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(options);

            }

        } catch (Exception e) {

            throw new RuntimeException(e);

        }

        if ("local".equalsIgnoreCase(execution)) {
            driver.manage().window().maximize();
        }

        System.out.println("--------------------------------");
        System.out.println("Driver Type : " + driver.getClass().getName());
        System.out.println("Browser     : " + browser);
        System.out.println("Execution   : " + execution);
        System.out.println("--------------------------------");

        return driver;

    }

    public static WebDriver getDriver() {

        return driver;

    }

    public static void quitDriver() {

        if (driver != null) {

            driver.quit();

        }

    }

}