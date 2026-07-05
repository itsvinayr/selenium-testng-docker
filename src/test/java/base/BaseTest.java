package base;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import factory.DriverFactory;

public class BaseTest {

    @BeforeMethod
    public void setup() {

        DriverFactory.initDriver();

    }

    @AfterMethod
    public void tearDown() {

        DriverFactory.quitDriver();

    }

}