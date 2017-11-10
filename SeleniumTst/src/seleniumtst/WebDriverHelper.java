/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seleniumtst;

import java.io.FileReader;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author chuer
 */
public class WebDriverHelper {

    private WebDriver webDriver;

    private WebDriverHelper() {
       Init();
    }

    public WebDriver getWebDriver() {        
        return webDriver;
    }
    
    public void Init() {
        try {
            Properties properties = new Properties();
            FileReader reader = new FileReader("properties.properties");

            properties.load(reader);
            String chromePath = properties.getProperty("chromePath");
            System.setProperty("webdriver.chrome.driver", chromePath);
            webDriver = new ChromeDriver();
        } catch (Exception e) {
            String ex = e.getMessage();
        }
    }

    public static WebDriverHelper getInstance() {
        return WebDriverHelperHolder.INSTANCE;
    }

    private static class WebDriverHelperHolder {

        private static final WebDriverHelper INSTANCE = new WebDriverHelper();
    }
}
