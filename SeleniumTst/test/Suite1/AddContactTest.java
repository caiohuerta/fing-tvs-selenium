/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Suite1;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Assert;
import org.junit.AfterClass;
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
public class AddContactTest {
    private static WebDriver driver;
    private String phone;
    private String name;
    private String email;
    private String lastname;
    private String spanText = "";
    private String tabText = "";

    public AddContactTest() throws FileNotFoundException, IOException {
        Properties properties = new Properties();
        FileReader reader = new FileReader("properties.properties");

        properties.load(reader);
        name = properties.getProperty("name");
        lastname = properties.getProperty("lastname");
        email = properties.getProperty("email");
        phone = properties.getProperty("phone");
        
        driver = WebDriverHelper.getInstance().getWebDriver();
    }

     @BeforeClass
    public static void beforeRun() throws IOException {
        LoginTest loginTest = new LoginTest(); //Ejecuto la dependencia del Test.
        loginTest.RunTest();
    }
    
    @Test
    public void RunTest() throws IOException {
                                                //En este estado la app se encuentra en la pagina Calendar.        
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
    
        WebElement addButton = driver.findElement(By.id("exec[nm][add]"));
        addButton.click();
        
        ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());   //me muevo a la nueva pestania.
        driver.switchTo().window(tabs2.get(1));
        
         new WebDriverWait(driver, 20).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver f) {
                WebElement tabElement = driver.findElement(By.id("addressbook.edit.general-tab"));
                tabText = tabElement.getText();
                return true;
            }
        });
        
        Assert.assertEquals(tabText,"General");        //Verifico que se abre el pop up
        
//        Ejecuto dependencia Save Contact
         SaveContact();

        driver.switchTo().window(tabs2.get(0));       //vuelvo a la pestania principal.
        
        
    }
    
    @AfterClass
    public  static void afterTests() throws IOException, InterruptedException{
        //Elimino el contacto
        DeleteContactTest contact = new DeleteContactTest();
        contact.RunTest();
        //        Ejecuto dependencia Logout
        LogoutTest logoutTest = new LogoutTest();
        logoutTest.RunTest();
        driver.quit();
    }
    //Pre: Pestania de carga de datos de usuario.
    private void SaveContact(){
        WebElement nameButton = driver.findElement(By.id("exec[n_fn]"));
        nameButton.click();
        //Abre particiones.
        
        WebElement nameField = driver.findElement(By.name("exec[n_given]"));
        WebElement lastnameField = driver.findElement(By.name("exec[n_family]"));
        
        nameField.sendKeys(name);
        lastnameField.sendKeys(lastname);
        
        WebElement okButton = driver.findElements(By.id("exec[]")).get(1);
        okButton.click();
        
        
        WebElement phoneField = driver.findElement(By.name("exec[tel_cell]"));
        phoneField.sendKeys(phone);
        WebElement emailField = driver.findElement(By.name("exec[email]"));
        emailField.sendKeys(email);
        
        driver.findElement(By.name("exec[button][save]")).click();
        
        
        
    }
}
