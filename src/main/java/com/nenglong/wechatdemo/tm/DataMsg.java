package com.nenglong.wechatdemo.tm;

import lombok.Data;

@Data
public class DataMsg {
    private String value;
    private String color;

    public DataMsg(String value, String color) {
        this.value = value;
        this.color = color;
    }
}
