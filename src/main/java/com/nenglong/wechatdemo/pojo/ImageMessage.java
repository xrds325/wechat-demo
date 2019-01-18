package com.nenglong.wechatdemo.pojo;

import lombok.Data;

import java.util.List;

@Data
public class ImageMessage {
    private String ToUserName;
    private String FromUserName;
    private String CreateTime;
    private String MsgType;
    private Image Image;


}
