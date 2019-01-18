package com.nenglong.wechatdemo.pojo;

import lombok.Data;

@Data
public class MusicMessage {
    private String ToUserName;
    private String FromUserName;
    private String CreateTime;
    private String MsgType;
    private Music Music;
}
