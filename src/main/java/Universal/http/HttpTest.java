package Universal.http;

import Universal.config.APIConfig;
import java.text.ParseException;
import java.util.HashMap;

//import universal.jdbc.JdbcHelper;
//import web.model.nHos.viewStaff.ResultDetail;
//import web.model.nHos.viewStaff.StaffDetail;


public class HttpTest {

    @org.testng.annotations.Test//(enabled = false)
    public void doPostTest() {
//        reqSmsCode();
        String token = login();
//        follow(token);
        reset(token);
//        System.out.println(token);
//        follow(token);
    }




    public String follow(String token) {
        HashMap<String, String> map = new HashMap<>();
        map.put("refId","169353");
        map.put("type","USER2USER");
        String jsonString = Http_Client
                .doPost("http://app.rfuat.nakedhub.cn/nakedhub/constant/user/follow",map,"",token);
//        System.out.println(jsonString);
        return jsonString;
    }

    public String login() {
        HashMap<String, String> map = new HashMap<>();
        map.put("u","315602");
        map.put("p","12345");
        String jsonString = Http_Client
                .doPost("http://app.rfuat.nakedhub.cn/nakedhub/constant/auth/login",map);
        System.out.println(jsonString);
        return jsonString;
    }

    public String reqSmsCode() {
        HashMap<String, String> map = new HashMap<>();
        map.put("mobile","13817168731");
        map.put("phoneCode","86");
        map.put("verifyType","LOGIN");
        String jsonString = Http_Client
                .doPost("http://app.rfuat.nakedhub.cn/nakedhub/constant/auth/reqSmsCode",map);
        System.out.println(jsonString);
        return jsonString;
    }

    public String reset(String token) {
        HashMap<String, String> map = new HashMap<>();
        map.put("","");
        String jsonString = Http_Client
                .send_App_Get("http://app.rfuat.nakedhub.cn/nakedhub/constant/user/resetCurrentUserCardQueue4Tinder","",token);
        return jsonString;
    }


    @org.testng.annotations.Test//(enabled = false)
    public void getShortToken() throws  ParseException {

        String api = APIConfig.APP_BASE_URL + APIConfig.GET_TOKEN;
        String param = "phone=86-15618880106";

        String  jsonString = Http_Client.doShortTokenGET(api,param);
        System.out.println("当前API返回的值为："+jsonString);
    }


}
