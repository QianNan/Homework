package api;

import Universal.config.APIConfiguration;
import Universal.http.Http_Client;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.testng.annotations.Test;

public class apiService {

    public delayAPIDto getDelayResponse(){
        String  delayAPI = APIConfiguration.APP_BASE_URL + APIConfiguration.delayAPI+"/3" ;
        System.out.println("Current API:"+delayAPI);
        String response = Http_Client.send_Admin_Get(delayAPI,null,null,null,null);
        System.out.println("Response :"+response);
        delayAPIDto  result = JSON.parseObject(response,delayAPIDto.class);
        System.out.println("当前返回的url是："+result.getUrl());
        return result;

    }

    @Test
    public void getImage(){
       String  imageAPI = APIConfiguration.APP_BASE_URL + APIConfiguration.imageAPI+"/png" ;
       System.out.println("Current API:"+imageAPI);
       String response = Http_Client.send_Img_Get(imageAPI,null,null,null,null);
       System.out.println("Response :"+response);


   }

}
