package com.nenglong.wechatdemo.Utils;

import com.nenglong.wechatdemo.config.WechatConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.util.Arrays;


public class CheckUtil {
   private static String token = "123456";

   public static boolean checkSignature(String signature,String timestamp,String nonce){
       String[] array = new String[]{nonce,timestamp,token};
       Arrays.sort(array);
       StringBuilder builder = new StringBuilder();
       for (String s : array) {
           builder.append(s);
       }
       String str = getSha1(builder.toString());
       if(str.equals(signature)){
            return true;
       }
       return false;

   }

    /**
     * sha1加密
     * @param str
     * @return
     */
    public static String getSha1(String str) {

        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));
            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            return null;
        }

    }
}
