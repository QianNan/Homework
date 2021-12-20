package webui;

import com.google.gson.internal.$Gson$Types;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class uiTest extends baseClass  {


    @Test
    public void topMenuTest() throws InterruptedException,NoSuchElementException {

//        #1 #2 check element has displayed
        WebElement topTab1 = FindElement(getDriver(),By.linkText("ABOUT"),1000);
        WebElement topTab2 = FindElement(getDriver(),By.linkText("SUBJECTS"),1000);
        WebElement topTab3 = FindElement(getDriver(),By.linkText("WHO WE SERVE"),1000);

        Actions action = new Actions(getDriver());
        action.moveToElement(topTab1).perform();
        Thread.sleep(1000);
        action.moveToElement(topTab2).perform();
        Thread.sleep(1000);
        action.moveToElement(topTab3).perform();
        Thread.sleep(1000);

//        List<WebElement> elements = getDriver().findElements(By.id("ec810e00-cd98-cf2d-1023-8e72a743f661"));
//        for (int i=0;i<elements.size();i++) {
//            System.out.println("当前元素的名字是："+elements.get(i)+"索引位置是："+i);
//        }
       
       
        WebElement stuEle = getDriver().findElement(By.linkText("Students"));
        stuEle.click();
//       #3  Check that https://www.wiley.com/en-us/students url is opened
        Assert.assertEquals("https://www.wiley.com/en-sg/students", getDriver().getCurrentUrl());

        //        Check that “Students” header is displayed
        String stuHeaderPath = "//ul[@id=\"breadcrumbStyle\"]/li[2]";
        WebElement stuHeader = getDriver().findElement(By.xpath(stuHeaderPath));
        if(byElementIsExist(By.xpath(stuHeaderPath)))
        {
            System.out.println("Element is exisit and value = "+stuHeader.getText());
        }
        Assert.assertEquals("Students",stuHeader.getText());


//        Check that “Learn More” links are present on the page and direct to  www.wileyplus.com site
        String learnMorePath = "//a[contains(@href,'http://www.wileyplus.com/')]";
        WebElement  learMoreLink = scrollToSpecificElement(By.xpath(learnMorePath),"\"http://www.wileyplus.com/\"");
        System.out.println("Element is exisit and value = "+learMoreLink.getText());
        learMoreLink.click();
        Thread.sleep(3000);

//     #4   Click on the Wiley logo at the top menu
        getDriver().navigate().back();
        Thread.sleep(3000);
        WebElement wileyLogoEle = getDriver().findElement(By.xpath("//*[@id=\"wileyLogo\"]//a/img"));
        wileyLogoEle.click();

    }

    @Test
    public void searchRelatedTest() throws InterruptedException {
        //     #5   Do not enter anything in the search input and press search button
        WebElement searchEle = getDriver().findElement(By.xpath("//button[@type='submit']"));
        searchEle.click();
        System.out.println("I was clicked："+searchEle.getText());

//      #6 Enter “Java” and do not press search button
        WebElement inputEle = getDriver().findElement(By.cssSelector("#js-site-search-input"));
        inputEle.sendKeys("java");
        getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//        display=none need change it;
//        List<WebElement> searchResultEles =  getDriver().findElements(By.xpath("//*[@id=\"ui-id-2\"]//section[1]//a[contains(@href,'/en-sg/search?')]"));
//        for (WebElement resultEle:searchResultEles) {
//            String  resultStr = resultEle.getText();
//            System.out.println("当前字符串是："+resultStr);
//            Assert.assertTrue(resultStr.contains("java"),"Search Result is correct！");
//        }

        searchEle.click();

    }

}
