package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import factory.DriverFactory;

public class GoogleTest extends BaseTest{

    @Test

    public void verifyTitle(){

        DriverFactory.getDriver().get("https://google.com");

        String title = DriverFactory.getDriver().getTitle();
        System.out.println("Hey here is the title : "+title);

        Assert.assertTrue(title.contains("Google"));

    }

}