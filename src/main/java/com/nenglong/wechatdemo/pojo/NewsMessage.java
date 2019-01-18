package com.nenglong.wechatdemo.pojo;

import lombok.Data;

import java.util.List;

@Data
public class NewsMessage {
    private String ToUserName;
    private String FromUserName;
    private String CreateTime;
    private String MsgType;
    private Integer ArticleCount;
    private List<News> Articles;


}
