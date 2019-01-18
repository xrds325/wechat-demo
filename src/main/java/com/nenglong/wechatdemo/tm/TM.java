package com.nenglong.wechatdemo.tm;

@lombok.Data
public class TM {
    private String touser;
    private String template_id;
    private String url;
    private Data data;
}
