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
import org.junit.BeforeClass;
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
public class DeleteContactTest {

    private String name;
    private String lastname;
    private String spanText;
    private String imgTitleDelete;
    
    private static WebDriver driver;
 
    public DeleteContactTest() throws FileNotFoundException, IOException {
        Properties properties = new Properties();
        FileReader reader = new FileReader("properties.properties");

        properties.load(reader);
        name = properties.getProperty("name");
        lastname = properties.getProperty("lastname");

        driver = WebDriverHelper.getInstance().getWebDriver();
    }

    @BeforeClass
    public static void beforeRun() throws IOException {
        LoginTest loginTest = new LoginTest(); //Ejecuto la dependencia del Test.
        loginTest.RunTest();
        
        AddContactTest addContactTest = new AddContactTest();
        addContactTest.RunTest();
    }

    @Test            //Pre: Logueado y existe el contacto Samuel Sainz
    public void RunTest() throws IOException, InterruptedException{
                                                                    //voy al adressbook
        WebElement adressBookLink = driver.findElement(By.xpath("//a[@href='/egroupware/addressbook/index.php']"));
        adressBookLink.click();       

        new WebDriverWait(driver, 10).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver f) {
                WebElement spanElement = driver.findElement(By.xpath("//div[@class='divSideboxHeader']"));
                spanText = spanElement.getText();
                return true;
            }
        });

        Assert.assertEquals(spanText,"Addressbook menu");      //Chequeo que estoy en el adressbook.
        
        WebElement searchElement = driver.findElements(By.id("exec[nm][search]")).get(1);
        searchElement.sendKeys(name + " " + lastname);
        
        driver.findElement(By.id("exec[nm][start_search]")).submit();          //Realizo la busqueda del contacto
        
        Thread.sleep(1000);
       
        new WebDriverWait(driver, 10).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver f) {
                WebElement imgDeleteElement = driver.findElement(By.xpath("//img[@src='/egroupware/phpgwapi/templates/idots/images/delete.png']"));
                imgTitleDelete = imgDeleteElement.getAttribute("title").toString();
                return true;
            }
        });
        Assert.assertEquals(imgTitleDelete,"Delete");      //Chequeo que encontre al contacto
        WebElement deleteButton = driver.findElement(By.xpath("//img[@src='/egroupware/phpgwapi/templates/idots/images/delete.png']/parent::*"));
        deleteButton.click();
        
        driver.switchTo().alert().accept();
        WebElement spanResult = driver.findElement(By.id("msg"));
        
        Assert.assertEquals(spanResult.getText(),"1 contact(s) deleted");      //Chequeo que se elimino el contacto
    }

    @AfterClass
    public static void afterTests() {
        driver.quit();
    }
}
