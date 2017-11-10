/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Suite1;

import java.io.IOException;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import seleniumtst.WebDriverHelper;

/**
 *
 * @author chuer
 */
public class LogoutTest {

    private static WebDriver driver;
    private String buttonText;

    public LogoutTest() {
        driver = WebDriverHelper.getInstance().getWebDriver();
    }

    @BeforeClass
    public static void beforeRun() throws IOException {
        LoginTest loginTest = new LoginTest(); //Ejecuto la dependencia del Test.
        loginTest.RunTest();
    }

    @Test            //Pre: Logueado
    public void RunTest() throws IOException {
        

        WebElement adressBookLink = driver.findElement(By.xpath("//a[@href='/egroupware/logout.php']"));
        adressBookLink.click();

        new WebDriverWait(driver, 10).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver f) {
                WebElement buttonElement = driver.findElement(By.name("submitit"));
                buttonText = buttonElement.getAttribute("value").toString();
                return true;
            }
        });
        buttonText = buttonText.trim();
        Assert.assertEquals(buttonText, "Login");
    }

    @AfterClass
    public static void afterTests() {
        driver.quit();
    }
}
