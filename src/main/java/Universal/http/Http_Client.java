package Universal.http;

import Universal.config.AdminConfig;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.apache.http.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.*;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import Universal.config.AdminConfig;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Http_Client {
    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @token token
     *
     * @return URL 所代表远程资源的响应结果
     */

    public static String token;
    public static String sessionID;
    static CookieStore cookieStore = null;
    private static OkHttpClient okHttpClient;
    private static  String shorToken;

//    向指定URL发送GET请求
    public static String send_App_Get(String url, String param,String token) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            //设置token属性
            conn.setRequestProperty("HEADER_SECURITY_TOKEN",token);

            // 建立实际的连接
            conn.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = conn.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
// 获取需要和手机号拼接的token
    public static String doShortTokenGET(String url, String param) {
        StringBuilder sb = new StringBuilder();
        BufferedReader in = null;
        String urlNameString = "";
        try {
            if (param == null || param == ""){
                urlNameString = url;
            }else {
                urlNameString = url + "?" + param;
                System.out.println(urlNameString);
            }
            CloseableHttpClient c = HttpClients.createDefault();
            HttpGet get = new HttpGet(urlNameString);
            get.addHeader("city_id","4133");
            get.addHeader("locale", "zh-CN") ;
            get.addHeader("Client-Type", "carevoice-iOS") ;
            get.addHeader("User-Agent", "careVoice") ;

            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
            if(cookieStore!=null){
                c = httpClientBuilder.setDefaultCookieStore(cookieStore).build();
            }else{
                c = httpClientBuilder.build();
            }
            CloseableHttpResponse response = c.execute(get);
            setCookieStore(response);

            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            char[] chars = new char[1024];
            int len = 0;
            while ((len = in.read(chars, 0, chars.length)) != -1) {
                sb.append(chars, 0, len);
            }

        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return sb.toString();
    }


    public static String send_Admin_Get(String url) {
        return send_Admin_Get(url, (String) null, null);
    }

    public static String send_Admin_Get(String url, String param) {
        return send_Admin_Get(url,param,null);
    }

    public static String send_Admin_Get(String url, HashMap<String, String> paramMap) {
        return send_Admin_Get(url,paramMap,null);
    }

    public static String send_Admin_Get(String url, HashMap<String, String> paramMap, String timeZone) {

            String param = "";
            for(Map.Entry<String, String> entry:paramMap.entrySet()){
                try {
                    param = param + entry.getKey() + URLEncoder.encode(entry.getValue(),"UTF-8") + "&";
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            param = param.substring(0,param.length()-1);
            return send_Admin_Get(url,param,timeZone);
    }

    public static String send_Admin_Get(String url, String param, String timeZone) {
        String defaultKey = "HEADER_SECURITY_TOKEN";
        String defaultValue = null;
        AdminConfig adminConfig = new AdminConfig();
        try {
            defaultValue = Universal.http.AdminUserToken.genToken(adminConfig.getMainland_phone(),shorToken);
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        return send_Admin_Get(url,param,defaultKey,defaultValue,timeZone);
    }

    public static String send_Admin_Get(String url, String param, String tokenKey,String tokenValue, String timeZone) {
        StringBuilder sb = new StringBuilder();
        BufferedReader in = null;
        String urlNameString = "";
        try {
            if (param == null || param == ""){
                urlNameString = url;
            }else {
                urlNameString = url + "?" + param;
                System.out.println(urlNameString);
            }
            CloseableHttpClient c = HttpClients.createDefault();
            HttpGet get = new HttpGet(urlNameString);
            token = tokenValue;
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
            if(cookieStore!=null){
                c = httpClientBuilder.setDefaultCookieStore(cookieStore).build();
            }else{
                c = httpClientBuilder.build();
            }
            CloseableHttpResponse response = c.execute(get);
            setCookieStore(response);

            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            char[] chars = new char[1024];
            int len = 0;
            while ((len = in.read(chars, 0, chars.length)) != -1) {
                sb.append(chars, 0, len);
            }

        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return sb.toString();
    }

    // 请求返回 image
    public static String send_Img_Get(String url, String param, String tokenKey,String tokenValue, String timeZone) {
        StringBuilder sb = new StringBuilder();
        BufferedReader in = null;
        String urlNameString = "";
        try {
            if (param == null || param == ""){
                urlNameString = url;
            }else {
                urlNameString = url + "?" + param;
                System.out.println(urlNameString);
            }
            CloseableHttpClient c = HttpClients.createDefault();
            HttpGet get = new HttpGet(urlNameString);
            get.addHeader("Accept", "image/png") ;
            token = tokenValue;
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
            if(cookieStore!=null){
                c = httpClientBuilder.setDefaultCookieStore(cookieStore).build();
            }else{
                c = httpClientBuilder.build();
            }
            CloseableHttpResponse response = c.execute(get);
            setCookieStore(response);

            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            char[] chars = new char[1024];
            int len = 0;
            while ((len = in.read(chars, 0, chars.length)) != -1) {
                sb.append(chars, 0, len);
            }

        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static String sendPost(String url) {
        return doPost(url);
    }

    public static String sendPost(String url,String param,String shortToken) {
        String defaultKey = "HEADER_SECURITY_TOKEN";
        AdminConfig adminConfig = new AdminConfig();
        String defaultValue = null;
        try {
            defaultValue = AdminUserToken.genToken(adminConfig.getMainland_phone(),shortToken);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String result = sendPost(url,param,defaultKey,defaultValue);
        return result;
    }

    public static String sendPost(String url, String param, String tokenKey,String tokenValue) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            if (sessionID != null) {
                conn.setRequestProperty("cookie",sessionID);
            }
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            //设置token属性
            conn.setRequestProperty(tokenKey,tokenValue);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            Iterator iter = conn.getHeaderFields().entrySet().iterator();
            while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
                if (key != null && key.toString().toUpperCase().contains("TOKEN")) {
                    token = conn.getHeaderField((String) key);
                }else if (key != null && key.toString().toUpperCase().contains("COOKIE")) {
                    sessionID = conn.getHeaderField((String) key);
                }
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static String sendPost(String url,HashMap param, String tokenKey,String tokenValue,String tmp) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost uploadFile = new HttpPost(url);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        uploadFile.addHeader(tokenKey,tokenValue);

        // 把文件加到HTTP的post请求中
        for (Object i:param.keySet()
             ) {
            if(param.get(i).getClass() == File.class){
                try {
                    builder.addBinaryBody(
                            i.toString(),
                            new FileInputStream((File)param.get(i)),
                            ContentType.MULTIPART_FORM_DATA,
                            ((File)param.get(i)).getName()
                    );
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }else{
                builder.addTextBody(i.toString(), param.get(i).toString(), ContentType.TEXT_PLAIN.withCharset(Consts.UTF_8));
            }
        }

        HttpEntity multipart = builder.build();
        uploadFile.setEntity(multipart);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(uploadFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpEntity responseEntity = response.getEntity();
        String sResponse= null;
        try {
            sResponse = EntityUtils.toString(responseEntity, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sResponse;
    }

    public static String doPost(String url, Map<String, String> map) {
        String result = "";
        result = doPost(url,map,null,null);
        return result;
    }

    public static String doPost(String url) {
        String result = "";
        Map map = new HashMap();
        map = null;
        result = doPost(url,map);
        return result;
    }

    public static String doPost(String url, Map<String, String> map,String tokenKey,String tokenValue) {
        OkHttpClient client = getOkHttpClient();
        System.out.println(url);
        System.out.println(map);
        FormBody.Builder urlBuilder = new FormBody.Builder();
        if (map != null) {
            for (String key : map.keySet()) {
                if (map.get(key)!=null){
                    urlBuilder.add(key, map.get(key));
                }
            }
        }

        Request.Builder builder = new Request.Builder().url(url);
        if(map != null) {
            builder.post(urlBuilder.build());
        }
        builder.addHeader("locale", "zh-CN") ;
        builder.addHeader("Content-Type", "application/json") ;
        if(tokenValue != null){
            builder.addHeader(tokenKey, tokenValue) ;
        }

        String result = null;
        try {
            Response response = client.newCall(builder.build()).execute();
            result = response.body().string();

            // 读取token
            if(tokenKey != null && response.header(tokenKey) != null ){
                token = response.header(tokenKey);
//                System.out.println(token);
            }else{
                token = response.header("HEADER_SECURITY_TOKEN");//Default token key
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);

            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
            okHttpClient = builder.build();
        }
        return okHttpClient;
    }

    public static String doPost(String url,JSONObject json) {
        String result = "";
        result = doPost(url,json,null, (String) null);
        return result;
    }

    public static String doPost(String url,JSONObject json,String timeZone,String shortToken) {

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        String result = "";
//        System.out.println(json);
        try {
            AdminConfig adminConfig = new AdminConfig();
            String token = AdminUserToken.genToken(adminConfig.getMainland_phone(),shortToken);
            post.setHeader("Content-Type", "application/json");
            post.addHeader("HEADER_SECURITY_TOKEN",token);
            post.addHeader("timeZone",timeZone);

            StringEntity s = new StringEntity(json.toString(), "utf-8");
            s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
                    "application/json; charset=utf-8"));
            post.setEntity(s);

            // 发送请求
            HttpResponse httpResponse = client.execute(post);

            // 获取响应输入流
            InputStream inStream = httpResponse.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inStream, "utf-8"));
            StringBuilder strber = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                strber.append(line + "\n");
            }

            inStream.close();

            result = strber.toString();
//            System.out.println(result);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                System.out.println("请求服务器成功，做相应处理");
            } else {
                System.out.println("请求服务端失败");
            }

        } catch (Exception e) {
            System.out.println("请求异常");
            throw new RuntimeException(e);
        }

        return result;
    }

    public static String doPost(String url,JSONObject json,String tokenKey,StringBuffer tokenValue) {

        OkHttpClient client = getOkHttpClient();
        FormBody.Builder urlBuilder = new FormBody.Builder();
        Request.Builder builder = new Request.Builder().url(url);
        System.out.println(json.toJSONString());
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), json.toJSONString());
        builder.post(urlBuilder.build()).post(requestBody);

        builder.addHeader("locale", "zh-CN") ;
        builder.addHeader("Client-Type", "carevoice-iOS") ;
        builder.addHeader("User-Agent", "careVoice") ;
        if(tokenValue != null){
            builder.addHeader(tokenKey, tokenValue.toString()) ;
        }

        String result = null;
        try {
            Response response = client.newCall(builder.build()).execute();
            result = response.body().string();
//            System.out.println("token : " + response.header(tokenKey));
            token = response.header(tokenKey);
            // 读取token
            if(response.header("HEADER_SECURITY_TOKEN") != null){
                tokenValue.setLength(0);
                tokenValue.append(response.header("HEADER_SECURITY_TOKEN"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }




    /**
     * 从响应中读取Session信息并生成cookie保存到静态变量中供后续调用
     * @param httpResponse
     */
    public static void setCookieStore(CloseableHttpResponse httpResponse) {
        cookieStore = new BasicCookieStore();
        String setCookie = "";
        for (Header i:httpResponse.getHeaders("Set-Cookie")
                ) {
            if(i.getValue().contains("JSESSIONID")){
                setCookie = i.getValue();
            }
        }

        if(setCookie.equals("")){
            return;
        }
        String JSESSIONID = setCookie.split("JSESSIONID=")[1].split(";")[0];
        System.out.println("JSESSIONID:" + JSESSIONID);
        // new cookie
        BasicClientCookie cookie = new BasicClientCookie("JSESSIONID",JSESSIONID);
        cookie.setVersion(0);
        cookie.setDomain("caas-api.nakedhub.com");
        cookie.setPath("/");
        cookieStore.addCookie(cookie);
    }

    // 多点登录 特加Header 方法 1
    public static String doPostaddHeader(String url,JSONObject json,String tokenKey,StringBuffer tokenValue) {

        OkHttpClient client = getOkHttpClient();
        FormBody.Builder urlBuilder = new FormBody.Builder();
        Request.Builder builder = new Request.Builder().url(url);
        System.out.println(json.toJSONString());
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), json.toJSONString());
        builder.post(urlBuilder.build()).post(requestBody);

        builder.addHeader("locale", "zh-CN") ;
        builder.addHeader("Client-Type", "carevoice-iOS") ;
        builder.addHeader("User-Agent", "careVoice") ;
        //多点登录记录
        builder.addHeader("device-id", "samsung SM-G1500 13bd0e0f-a38a-4557-a52d-a4a344b30a5b") ;
        builder.addHeader("device-model", "samsung SM-G1500") ;
        if(tokenValue != null){
            builder.addHeader(tokenKey, tokenValue.toString()) ;
        }

        String result = null;
        try {
            Response response = client.newCall(builder.build()).execute();
            result = response.body().string();
            token = response.header(tokenKey);
            // 读取token
            if(response.header("HEADER_SECURITY_TOKEN") != null){
                tokenValue.setLength(0);
                tokenValue.append(response.header("HEADER_SECURITY_TOKEN"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    // 多点登录 特加Header 方法 2
    public static String doPostaddHeaderMore(String url,JSONObject json,String tokenKey,StringBuffer tokenValue) {

        OkHttpClient client = getOkHttpClient();
        FormBody.Builder urlBuilder = new FormBody.Builder();
        Request.Builder builder = new Request.Builder().url(url);
        System.out.println(json.toJSONString());
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), json.toJSONString());
        builder.post(urlBuilder.build()).post(requestBody);

        builder.addHeader("locale", "zh-CN") ;
        builder.addHeader("Client-Type", "carevoice-iOS") ;
        builder.addHeader("User-Agent", "careVoice") ;
        //多点登录记录
        builder.addHeader("device-id", "samsung SM-G9103 13bd0e0f-a38a-4557-a52d-a4a344b30a5b") ;
        builder.addHeader("device-model", "samsung SM-G9103") ;
        if(tokenValue != null){
            builder.addHeader(tokenKey, tokenValue.toString()) ;
        }

        String result = null;
        try {
            Response response = client.newCall(builder.build()).execute();
            result = response.body().string();
            token = response.header(tokenKey);
            // 读取token
            if(response.header("HEADER_SECURITY_TOKEN") != null){
                tokenValue.setLength(0);
                tokenValue.append(response.header("HEADER_SECURITY_TOKEN"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
