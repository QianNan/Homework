package Universal.config;

import Universal.envConfig.EnvironmentConfig;

//此处注释暂时勿删除，作为调试API使用

public class APIConfig {
//    private static String env = EnvironmentConfig.readEnvConfigProperties().get("host").toString();
    public static String APP_BASE_URL = "http://api-staging.kangyu.co";
    public static String DASHBOARD_BASE_URL = "https://staging.kangyu.co/api";


   public static String GET_TOKEN = "/v3/apps/token";

   public static String GET_SMS_CODE="/v3/sms_tokens/sign_in";

   public static String CREATE_INSURANCE_MEMBER="";


//     Dashboard 创建User
   public static String CREATE_DEMO_MEMBER="/admin/users?locale=en";
   public static String LOGIN = "/admin/sign_in?locale=en";

}
