package com.nenglong.wechatdemo.test;

import com.nenglong.wechatdemo.Utils.MessageType;
import com.nenglong.wechatdemo.Utils.MessageUtil;
import com.nenglong.wechatdemo.Utils.WeChatUtil;
import com.nenglong.wechatdemo.pojo.AccessToken;
import com.nenglong.wechatdemo.pojo.TextMessage;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

public class UtilTest {
    @Test
    public void test(){
        try {
            String mediaId = WeChatUtil.uploadMedia("thumb","F:\\wechat-demo\\src\\main\\resources\\static\\images\\321.jpg");
            System.out.println(mediaId);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void test1(){
         WeChatUtil.createMenu();

    }

    @Test
    public void test2(){
        Map map = WeChatUtil.pushTM();
        Set<Map.Entry> entrySet = map.entrySet();
        for(Map.Entry entry:entrySet){
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }

    }
}
