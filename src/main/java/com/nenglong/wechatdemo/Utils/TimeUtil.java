package com.nenglong.wechatdemo.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
    public static String format(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return sdf.format(date);
    }
}
