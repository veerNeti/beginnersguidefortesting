package com.guide.beginners.testng.theinternet.individualtestclass;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
/*
This is the rough draft of developing a test
 */
public class Step1 {
    private static Logger logger = LogManager.getLogger(Step1.class.getName( ));

    @Test
    public void logInTest() {
        logger.info("Starting logIn test");

        // Create driver
        WebDriverManager.chromedriver( ).setup( );
        WebDriver driver = new ChromeDriver( );
        driver.manage( ).window( ).maximize( );

        // open main page
        String url = "http://the-internet.herokuapp.com/";
        driver.get(url);
        logger.info("Main page is opened:"+url);

        // Click on Form Authentication link
        driver.findElement(By.linkText("Form Authentication")).click( );

        // enter username and password
        driver.findElement(By.id("username")).sendKeys("tomsmith");
        driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");

        WebDriverWait wait = new WebDriverWait(driver, 10);

        // push log in button
        WebElement logInButton = driver.findElement(By.className("radius"));
        wait.until(ExpectedConditions.elementToBeClickable(logInButton));
        logInButton.click( );

        // verifications
        // new url
        String expectedUrl = "http://the-internet.herokuapp.com/secure";
        Assert.assertEquals(driver.getCurrentUrl( ), expectedUrl);

        // log out button is visible
        Assert.assertTrue(driver.findElement(By.xpath("//a[@class='button secondary radius']")).isDisplayed( ),
                "logOutButton is not visible.");

        // Successful log in message
        String expectedSuccessMessage = "You logged into a secure area!";
        String actualSuccessMessage = driver.findElement(By.id("flash")).getText( );
        Assert.assertTrue(actualSuccessMessage.contains(expectedSuccessMessage),
                "actualSuccessMessage does not contain expectedSuccessMessage\nexpectedSuccessMessage: "
                        + expectedSuccessMessage + "\nactualSuccessMessage: " + actualSuccessMessage);

        // Close browser
        driver.quit( );
    }
}
