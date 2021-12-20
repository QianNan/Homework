package Universal.http;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class AdminUserToken {

   public static String genToken(String phone, String shortToken) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        StringBuilder stringBuilder = new StringBuilder(phone);
        String data = stringBuilder.append(shortToken).toString();
        byte[] digest=null;

        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            digest  = md5.digest(data.getBytes("utf-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String md5Str = new BigInteger(1, digest).toString(16);
        return md5Str;
    }

}
