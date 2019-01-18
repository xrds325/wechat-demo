package com.nenglong.wechatdemo.controller;

import com.nenglong.wechatdemo.Utils.CheckUtil;
import com.nenglong.wechatdemo.Utils.MessageType;
import com.nenglong.wechatdemo.Utils.MessageUtil;
import com.nenglong.wechatdemo.Utils.WeChatUtil;
import com.nenglong.wechatdemo.pojo.News;
import com.nenglong.wechatdemo.pojo.ResponseResult;
import com.nenglong.wechatdemo.templates.NewsTemplate;
import com.nenglong.wechatdemo.templates.TextMessageTemplate;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/wechat")
public class WechatController {

    @GetMapping("/portal")
    @ResponseBody
    public String auth(String signature,String timestamp,String nonce,String echostr){
        if(CheckUtil.checkSignature(signature,timestamp,nonce)){
            return echostr;
        }
        return "非法请求";
    }

    @PostMapping("/portal")
    @ResponseBody
    public void reply(HttpServletRequest request, HttpServletResponse response){

        try {
            PrintWriter writer = response.getWriter();
            Map<String,String> map = MessageUtil.xmlToMap(request);
            String toUserName = map.get("ToUserName");
            String fromUserName = map.get("FromUserName");
            String msgType = map.get("MsgType");
            String msgId = map.get("MsgId");
            String content = map.get("Content");
            if(MessageType.TEXT.getType().equals(msgType.toLowerCase())){
                String message = "";
                if("1".equals(content)){
                    message = MessageUtil.initMessage(toUserName,fromUserName,TextMessageTemplate.FirstTemplate());
                }else if("2".equals(content)){
                    List<News> news = new ArrayList<>();
                    news.add(NewsTemplate.fireNews());
                    news.add(NewsTemplate.secondNews());
                    message = MessageUtil.initNewsMessage(toUserName,fromUserName,news);
                }else if("3".equals(content)){
                    message = MessageUtil.initImageMessage(toUserName,fromUserName);
                }else if("4".equals(content)){
                    message = MessageUtil.initMusicMessage(toUserName,fromUserName);
                }else{
                    message = MessageUtil.initMessage(toUserName,fromUserName,TextMessageTemplate.menu());
                }
                System.out.println(message);
                writer.write(message);
            }else if(MessageType.EVENT.getType().equals(msgType.toLowerCase())){
                String message = "";
                if(MessageType.SUBSCRIBE.getType().equals(map.get("Event"))){
                    message = MessageUtil.initMessage(toUserName,fromUserName,TextMessageTemplate.menu());
                }else if(MessageType.CLICK.getType().equals(map.get("Event"))){
                    message = MessageUtil.initMessage(toUserName,fromUserName,TextMessageTemplate.menu());
                }
                writer.write(message);
            }else if(MessageType.LOCATION.getType().equals(msgType.toLowerCase())){
                writer.write(MessageUtil.initMessage(toUserName,fromUserName,map.get("Label")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/getUrl.do")
    @ResponseBody
    public ResponseResult<String> getUploadUrl(String type){
        ResponseResult<String> rr = new ResponseResult<>();
        rr.setData(WeChatUtil.getUploadURL(type));
        return rr;
    }

    @GetMapping("/upload.do")
    public String showUpload(){
        return "upload";
    }
}
