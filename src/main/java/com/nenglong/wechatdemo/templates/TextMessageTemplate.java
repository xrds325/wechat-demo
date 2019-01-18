package com.nenglong.wechatdemo.templates;

import com.nenglong.wechatdemo.Utils.MessageType;
import com.nenglong.wechatdemo.Utils.MessageUtil;
import com.nenglong.wechatdemo.Utils.TimeUtil;
import com.nenglong.wechatdemo.pojo.TextMessage;

import java.util.Date;

/**
 * 消息模板
 */
public class TextMessageTemplate {


    public static String menu(){
        StringBuilder builder = new StringBuilder();
        builder.append("菜单页:\n");
        builder.append("1: 消息1\n");
        builder.append("2: 消息2\n");
        builder.append("其它:返回本菜单\n");
        return builder.toString();
    }

    public static String FirstTemplate(){
        StringBuilder builder = new StringBuilder();
        builder.append("消息模板板消息模板板消息模板板消息模板板消息模板板消息模板板消息模板板消息模板板消息模板板消息模板板消息模板板");
        builder.append("消息模板板消息模板板消息模板板消息模板板消息模板板消息模板板消息模板板消息模板板消息模板板消息模板板消息模板板");
        builder.append("消息模板板消息模板板消息模板板消息模板板消息模板板消息模板板消息模板板消息模板板消息模板板消息模板板消息模板板");
        builder.append("消息模板板消息模板板消息模板板消息模板板消息模板板消息模板板消息模板板消息模板板消息模板板消息模板板消息模板板");
        builder.append("消息模板板消息模板板消息模板板消息模板板消息模板板消息模板板消息模板板消息模板板消息模板板消息模板板消息模板板");
        builder.append("消息模板板消息模板板消息模板板消息模板板消息模板板消息模板板消息模板板消息模板板消息模板板消息模板板消息模板板");
        builder.append("消息模板板消息模板板消息模板板消息模板板消息模板板消息模板板消息模板板消息模板板消息模板板消息模板板消息模板板");
        return builder.toString();
    }

    public static String secondTemplate(){
        return "消息模板2";
    }
}
