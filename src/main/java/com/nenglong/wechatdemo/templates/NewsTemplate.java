package com.nenglong.wechatdemo.templates;

import com.nenglong.wechatdemo.pojo.News;

public class NewsTemplate {

    public static News fireNews(){
        News news = new News();
        news.setDescription("这是第一条图文消息，哈哈哈哈啊哈");
        news.setTitle("第一条图文消息");
        news.setPicUrl("http://xrds325.natapp1.cc/images/123.jpg");
        news.setUrl("http://www.baidu.com");
        return news;
    }

    public static News secondNews(){
        News news = new News();
        news.setDescription("这是第二条图文消息，哈哈哈哈啊哈");
        news.setTitle("第二条图文消息");
        news.setPicUrl("http://xrds325.natapp1.cc/images/123.jpg");
        news.setUrl("http://www.baidu.com");
        return news;
    }
}
