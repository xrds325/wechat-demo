package com.nenglong.wechatdemo.Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nenglong.wechatdemo.menu.Button;
import com.nenglong.wechatdemo.menu.ClickButton;
import com.nenglong.wechatdemo.menu.Menu;
import com.nenglong.wechatdemo.menu.ViewButton;
import com.nenglong.wechatdemo.pojo.AccessToken;
import com.nenglong.wechatdemo.tm.Data;
import com.nenglong.wechatdemo.tm.DataMsg;
import com.nenglong.wechatdemo.tm.TM;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.util.Map;

public class WeChatUtil {
    private static final String APPID = "wx6091c48473e4b4ee";
    private static final String APPSECRET = "671fcee01326428525989e0679882d48";
    //获取access_tokenurl
    private static final String URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    //上次临时素材接口
    private static final String UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
    private static final String MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    private static final String PUSH_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
    private static ObjectMapper objectMapper = new ObjectMapper();
    /**
     * 请求接口的get方法
     * @param url 请求路径
     * @return 响应字符串
     * @throws IOException
     */
    public static String doGet(String url) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = client.execute(httpGet);
        HttpEntity entity = response.getEntity();
        String str = "";
        if(entity!=null){
            str = EntityUtils.toString(entity,"UTF-8");
        }
        return str;
    }

    /**
     * 请求接口的post方法
     * @param url 请求路径
     * @param param 请求参数
     * @return 响应字符串
     * @throws IOException
     */
    public static String doPost(String url,String param) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(param,"UTF-8"));
        HttpResponse response = client.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String str = "";
        if(entity!=null){
            str = EntityUtils.toString(entity,"UTF-8");
        }
        return str;
    }

    /**
     * 获取access_token
     * @return
     */
    public static AccessToken getAccessToken(){
        AccessToken accessToken = null;
        String url = URL.replace("APPID",APPID).replace("APPSECRET",APPSECRET);
        try {
            String result = doGet(url);
            if(StringUtils.isNotBlank(result)){
                accessToken = objectMapper.readValue(result,AccessToken.class);
                System.out.println(accessToken);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accessToken;
    }

    /**
     * 获取临时素材上传url
     * @param type 临时素材类型
     * @return
     */
    public static String getUploadURL(String type){
        String accessToken = getAccessToken().getAccessToken();
        String uploadUrl = UPLOAD_URL.replace("ACCESS_TOKEN",accessToken).replace("TYPE",type);
        return uploadUrl;
    }

    /**
     * 微信临时素材上传
     * @param fileType 文件类型
     * @param filePath 文件路径
     * @return mediaID
     * @throws IOException
     */
    public static String uploadMedia(String fileType,String filePath) throws IOException {
        String result=null;
        File file=new File(filePath);
        if(!file.exists()||!file.isFile()){
            throw new IOException("文件不存在");
        }
        String token=WeChatUtil.getAccessToken().getAccessToken();
        String urlString="https://api.weixin.qq.com/cgi-bin/media/upload?access_token="+token+"&type="+fileType;
        java.net.URL url=new URL(urlString);
        HttpsURLConnection conn=(HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("POST");//以POST方式提交表单
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);//POST方式不能使用缓存
        //设置请求头信息
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Charset", "UTF-8");
        //设置边界
        String BOUNDARY="----------"+System.currentTimeMillis();
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
        //请求正文信息
        //第一部分
        StringBuilder sb=new StringBuilder();
        sb.append("--");//必须多两条道
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"media\"; filename=\"" + file.getName()+"\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");
        System.out.println("sb:"+sb);

        //获得输出流
        OutputStream out=new DataOutputStream(conn.getOutputStream());
        //输出表头
        out.write(sb.toString().getBytes("UTF-8"));
        //文件正文部分
        //把文件以流的方式 推送道URL中
        DataInputStream din=new DataInputStream(new FileInputStream(file));
        int bytes=0;
        byte[] buffer=new byte[1024];
        while((bytes=din.read(buffer))!=-1){
            out.write(buffer,0,bytes);
        }
        din.close();
        //结尾部分
        byte[] foot=("\r\n--" + BOUNDARY + "--\r\n").getBytes("UTF-8");//定义数据最后分割线
        out.write(foot);
        out.flush();
        out.close();
        if(HttpsURLConnection.HTTP_OK==conn.getResponseCode()){

            StringBuffer strbuffer=null;
            BufferedReader reader=null;
            try {
                strbuffer=new StringBuffer();
                reader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String lineString=null;
                while((lineString=reader.readLine())!=null){
                    strbuffer.append(lineString);

                }
                if(result==null){
                    result=strbuffer.toString();
                    System.out.println("result:"+result);
                }
                Map<String,String> map = objectMapper.readValue(result,Map.class);
                System.out.println(map);
                return map.get("media_id");

            } catch (IOException e) {
                System.out.println("发送POST请求出现异常！"+e);
                e.printStackTrace();
            }finally{
                if(reader!=null){
                    reader.close();
                }
            }

        }
        return "";
    }

    public static void createMenu(){
        String accessToken = getAccessToken().getAccessToken();
        String url = MENU_URL.replace("ACCESS_TOKEN",accessToken);
        Menu menu = initMenu();
        try {
            String param = objectMapper.writeValueAsString(menu);
            System.out.println(param);
            String result = doPost(url,param);
            Map<String,String> resMap = objectMapper.readValue(result,Map.class);
            System.out.println(resMap);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 初始化菜单
     * @return
     */
    public static Menu initMenu(){
        Menu menu = new Menu();
        ClickButton button11 = new ClickButton();
        button11.setName("点击菜单");
        button11.setType("click");
        button11.setKey("11");

        ViewButton button21 = new ViewButton();
        button21.setName("视图菜单");
        button21.setType("view");
        button21.setUrl("http://www.baidu.com");

        ClickButton button31 = new ClickButton();
        button31.setName("扫码");
        button31.setType("scancode_push");
        button31.setKey("31");

        ClickButton button32 = new ClickButton();
        button32.setName("地理位置");
        button32.setType("location_select");
        button32.setKey("32");

        Button button = new Button();
        button.setName("菜单");
        button.setSub_button(new Button[]{button31,button32});

        menu.setButton(new Button[]{button11,button21,button});

        return menu;
    }

    /**
     * 初始化模板消息
     * @return
     */
    public static TM initTM(){
        TM tm = new TM();
        tm.setTemplate_id("V77SsBfVanoYk_C8m2iefSVMmCD0vT2StmjqjwIZDEQ");
        tm.setTouser("o-SKh0bMnoElJfRYQBSdo_Z9-3iY");
        tm.setUrl("http://www.baidu.com");
        Data data = new Data();
        data.setFirst(new DataMsg("欢迎购买商品","#173177"));
        data.setKeyword1(new DataMsg("小熊","#173177"));
        data.setKeyword2(new DataMsg("22","#173177"));
        data.setKeyword3(new DataMsg("￥40","#173177"));
        data.setRemark(new DataMsg("欢迎下次惠顾","#173177"));
        tm.setData(data);
        return tm;
    }

    public static Map pushTM(){
        String url = PUSH_URL.replace("ACCESS_TOKEN",getAccessToken().getAccessToken());
        try {
            String param = objectMapper.writeValueAsString(initTM());
            System.out.println(param);
            String result = doPost(url,param);
            return objectMapper.readValue(result,Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
