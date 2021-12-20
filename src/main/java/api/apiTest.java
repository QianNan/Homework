package api;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Develop automation tests to check status and response using Java and any library of your choice.
 *
 * There is a simple HTTP Request & Response Service https://httpbin.org
 *
 * GET/delay/{delay}
 * Returns a delayed response (max of 10 seconds).
 *
 * GET/image/png
 * Returns a simple PNG image.
 * */

public class apiTest {

    private apiService serviceData = new apiService();

    @Test
    public void getDelayRespons(){
        delayAPIDto result = serviceData.getDelayResponse();
        Assert.assertEquals("https://httpbin.org/delay/3",result.getUrl());
        System.out.println("This test return a dealyed response");
    }

    @Test
    public void getPngImage(){
        System.out.println("Returns a simple PNG image.");
    }
}

