package com.nenglong.wechatdemo.Utils;

import com.nenglong.wechatdemo.pojo.*;
import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageUtil {

    /**
     * 将微信请求xml格式参数封装到map中
     * @param request 请求对象
     * @return 封装了请求参数的map
     * @throws IOException
     * @throws DocumentException
     */
    public static Map<String,String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException {
        Map<String,String> map = new HashMap<String,String>();
        InputStream inputStream = request.getInputStream();
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        Element root = document.getRootElement();
        List<Element> elements = root.elements();
        for (Element element : elements) {
            map.put(element.getName(),element.getText());
        }
        return map;
    }

    /**
     * 对象类型转xml格式字符串
     * @param obj
     * @return
     */
    public static String objToXml(Object obj){
        XStream xStream = new XStream();
        xStream.alias("xml",obj.getClass());
        return xStream.toXML(obj);
    }

    public static String newsMsgToXml(NewsMessage newsMessage){
        XStream xStream = new XStream();
        xStream.alias("xml",NewsMessage.class);
        xStream.alias("item",News.class);
        return xStream.toXML(newsMessage);
    }


    /**
     * 初始化回复文本消息内容
     * @param toUerName 请求接收人
     * @param fromUserName 请求发送人
     * @param template 回复正文
     * @return xml格式响应内容
     */
    public static String initMessage(String toUerName,String fromUserName,String template){
        TextMessage textMessage = new TextMessage();
        textMessage.setToUserName(fromUserName);
        textMessage.setFromUserName(toUerName);
        textMessage.setCreateTime(TimeUtil.format(new Date()));
        textMessage.setMsgType(MessageType.TEXT.getType());
        textMessage.setContent(template);
        String str = MessageUtil.objToXml(textMessage);
        return str;
    }

    /**
     * 初始化图文消息格式
     * @param toUerName 请求接收人
     * @param fromUserName 请求发送人
     * @param news 消息正文
     * @return xml格式响应内容
     */
    public static String initNewsMessage(String toUerName, String fromUserName, List<News> news){
        NewsMessage newsMessage = new NewsMessage();
        newsMessage.setFromUserName(toUerName);
        newsMessage.setToUserName(fromUserName);
        newsMessage.setCreateTime(TimeUtil.format(new Date()));
        newsMessage.setMsgType(MessageType.NEWS.getType());
        newsMessage.setArticles(news);
        newsMessage.setArticleCount(news.size());
        return newsMsgToXml(newsMessage);
    }

    public static String initImageMessage(String toUserName,String fromUserName){
        ImageMessage imageMessage = new ImageMessage();
        imageMessage.setFromUserName(toUserName);
        imageMessage.setToUserName(fromUserName);
        imageMessage.setCreateTime(TimeUtil.format(new Date()));
        imageMessage.setMsgType(MessageType.IMAGE.getType());
        imageMessage.setImage(new Image());
        return objToXml(imageMessage);
    }

    public static String initMusicMessage(String toUserName,String fromUserName){
        MusicMessage musicMessage = new MusicMessage();
        musicMessage.setFromUserName(toUserName);
        musicMessage.setToUserName(fromUserName);
        musicMessage.setCreateTime(TimeUtil.format(new Date()));
        musicMessage.setMsgType(MessageType.MUSIC.getType());
        Music music = new Music();
        music.setTitle("音乐");
        music.setDescription("这是一首音乐");
        music.setMusicUrl("http://xrds325.natapp1.cc/static/music/some.mp3");
        music.setHQMusicUrl("http://xrds325.natapp1.cc/static/music/some.mp3");
        musicMessage.setMusic(music);

        return objToXml(musicMessage);
    }

}
