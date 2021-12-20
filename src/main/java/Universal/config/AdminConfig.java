package Universal.config;

import java.io.*;
import java.util.Properties;

/**
 * 数据库配置文件读取方法
 */
public final class AdminConfig {

    private static String mainland_phone;
    private static String dashboard_email;
    private static String dashboard_pwd;

    public AdminConfig(){

        InputStream inputStream = getClass().getResourceAsStream("/CareVoiceStating.properties");
        Properties p = new Properties();
        try {
            p.load(inputStream);
            this.mainland_phone = p.getProperty("mainland_phone").trim();
            this.dashboard_email = p.getProperty("dashboard_email").trim();
            this.dashboard_pwd = p.getProperty("dashboard_pwd").trim();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static String getMainland_phone() {
        return mainland_phone;
    }

    public static String getDashboard_email() {
        return dashboard_email;
    }

    public static String getDashboard_pwd() {
        return dashboard_pwd;
    }


}