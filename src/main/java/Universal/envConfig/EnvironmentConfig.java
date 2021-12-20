package Universal.envConfig;

import Universal.JDBC.ConnectionUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;


public class EnvironmentConfig {

    public static String driver;
    public static String url;
    public static String userName;
    public static String password;
    public static String envName;
    public static String host;


    public static HashMap readEnvConfigProperties () {
        HashMap map = new HashMap();
        InputStream inputStream = null;
        Properties p = new Properties();

        try {

            inputStream = ConnectionUtil.class.getResourceAsStream("/CareVoiceStaging.properties");
            p.load(inputStream);

            driver = p.getProperty("driver");
            url = p.getProperty("url");
            userName = p.getProperty("username");
            password = p.getProperty("password");
            envName = p.getProperty("envName");
            host = p.getProperty("host");

            map.put("driver",driver);
            map.put("url",url);
            map.put("userName",userName);
            map.put("password",password);
            map.put("envName",envName);
            map.put("host",host);


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {

            if(inputStream != null){

                try{
                    inputStream.close();

                }catch (Exception e) {

                    e.printStackTrace();

                }
            }
        }
        return map;
    }


}
