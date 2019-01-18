package com.nenglong.wechatdemo.menu;

import lombok.Data;

@Data
public class Button {
    private String type;
    private String name;
    private Button[] sub_button;
}
