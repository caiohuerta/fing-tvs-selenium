/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seleniumtst;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author chuer
 */
public class WebDriverHelper {
    
    private WebDriver webDriver;
    private WebDriverHelper() {
        webDriver = new ChromeDriver();
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }
    
    public static WebDriverHelper getInstance() {
        return WebDriverHelperHolder.INSTANCE;
    }
    
    private static class WebDriverHelperHolder {

        private static final WebDriverHelper INSTANCE = new WebDriverHelper();
    }
}
