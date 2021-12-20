package webui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class baseClass {
    private  static WebDriver driver = null;
    public static WebDriver getDriver() {
        return driver;
    }

    private  static  JavascriptExecutor js = (JavascriptExecutor)driver;

    @BeforeTest
    public void setUp() {
        System.setProperty("webdriver.chrome.driver","/Users/winkynan/Desktop/Homework/src/Driver/chromedriver");
        WebDriverManager.chromedriver().version("96.0.4664.110").setup();
        driver = new ChromeDriver();
        driver.get("https://www.wiley.com/en-sg");

//        WebElement driverEle = driver.findElement(By.xpath("//button[contains(@class,'osano-cm-denyAll')]"));
//        WebElement driverEle = driver.findElement(By.xpath("//div[@id='caa1b591-450a-46a4-a10e-4b487d3b139e']"));
//        System.out.println("----------- 当前偏好设置的 ----"+driverEle.getText());
//        js.executeScript("return document.getElementsByClassName(\"\"osano-cm-denyAll osano-cm-buttons__button osano-cm-button osano-cm-button--type_denyAll\").click()");

        System.out.println("CurrentTitle is："+driver.getTitle());
    }

    // 判断某个元素对象是否存在
    public static boolean byElementIsExist(By by) {
        try {
            getDriver().findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public static WebElement FindElement(WebDriver driver, By by, int timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.until( ExpectedConditions.presenceOfElementLocated(by) );
        // wait.ignoring(org.openqa.selenium.NoSuchElementException.class); 如果想要避免抛出异常，用此方法
        return driver.findElement(by);

    }



    public static   WebElement scrollToSpecificElement(By by,String str){
        WebElement showElement= driver.findElement(by);
        if(showElement !=null && showElement.isDisplayed()){
            System.out.println("I found the element");
            return showElement;
        }else {
            while (!driver.getPageSource().contentEquals(str)){
//                scrollHeight need to optimize
                js.executeScript("window.scrollBy(0,-document.body.scrollHeight-800)");
                getDriver().manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
                try{
                    WebElement element= driver.findElement(by);
                    if(element != null && element.isDisplayed()){
                        System.out.println("Hello I found the element");
                        return  element;
                    }
                }catch (Exception e){
                    return  null;
                }
            }
            return null;
        }
    }

//    @AfterClass
//    public void teardown(){
//        driver.quit();
//
//    }
}
