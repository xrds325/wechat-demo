package com.nenglong.wechatdemo.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class TextMessage {
    private String ToUserName;
    private String FromUserName;
    private String CreateTime;
    private String MsgType;
    private String Content;
    private String MsgId;
}
