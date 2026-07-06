package base;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import factory.DriverFactory;

public class BaseTest {

    @BeforeMethod
    @Parameters("browser")
    public void setup(@Optional("chrome") String browser) {

        DriverFactory.initDriver(browser);

    }

    @AfterMethod
    public void tearDown() {

        DriverFactory.quitDriver();

    }

}