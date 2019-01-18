package com.nenglong.wechatdemo.Utils;

public enum MessageType {
    TEXT("text"),IMAGE("image"),VOICE("voice"),VIDEO("video"),LINK("link"),NEWS("news"),MUSIC("music"),
    LOCATION("location"),EVENT("event"),SUBSCRIBE("subscribe"),UNSUBSCRIBE("unsubscribe"),CLICK("CLICK"),VIEW("view");
    private String type;

    private MessageType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }


    public void setType(String type) {
        this.type = type;
    }
}
