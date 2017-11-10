/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Suite1;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import seleniumtst.WebDriverHelper;

/**
 *
 * @author chuer
 */
public class LoginTest {

    private static WebDriver driver;
    private String user;
    private String pass;
    private String url;
    public String spanText = "";

    public LoginTest() throws FileNotFoundException, IOException {
        Properties properties = new Properties();
        FileReader reader = new FileReader("properties.properties");

        properties.load(reader);
        user = properties.getProperty("username");
        pass = properties.getProperty("password");
        url = properties.getProperty("url");
        driver = WebDriverHelper.getInstance().getWebDriver();
    }

    
    @Test
    public void RunTest() {
        driver.get(url);
        WebElement userField = driver.findElement(By.name("login"));
        WebElement passField = driver.findElement(By.name("passwd"));

        userField.sendKeys(user);
        passField.sendKeys(pass);
        passField.submit();

        Boolean name = new WebDriverWait(driver, 10).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver f) {
                WebElement spanElement = driver.findElement(By.xpath("//div[@class='divSideboxHeader']"));
                spanText = spanElement.getText();
                return true;
            }
        });

        Assert.assertEquals(spanText,"Calendar Menu");
    }
    
    @AfterClass
    public static void afterTests(){
        driver.quit();
    }
}
