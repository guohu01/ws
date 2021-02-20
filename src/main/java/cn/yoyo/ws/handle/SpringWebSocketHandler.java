package cn.yoyo.ws.handle;

import cn.yoyo.ws.model.FaceLogs;
import cn.yoyo.ws.tools.BASE64Util;
import cn.yoyo.ws.tools.QrCreate;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Repository;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class SpringWebSocketHandler extends TextWebSocketHandler {

/*    public static String ir_image;
    public static String vl_image;*/

    /*
    * 存储用户id和其对应的session
    * */
    public static final Map<String, WebSocketSession> users = new HashMap<>();

    /*@Autowired
    private MessageHandler messageHandler;*/

    /*
    * 用户key值
    * */
    public static final String USER_ID = "WEBSOCKET_USERID";

    /**
     * 连接建立后触发
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("成功建立websocket连接!");
        /*this.session = session;
        System.out.println("成功建立websocket连接thid.session"+this.session);
        String userId = (String) session.getAttributes().get(USER_ID);//取出在拦截器中存储的username
        System.out.println("当前用户："+userId);*/
        users.put("admin", session);
        System.out.println("当前线上用户数量:" + users.size());
        try {
            QrCreate.generateQRCodeImage(QrCreate.create(),"C:\\Users\\Administrator\\Desktop\\MyQRCode.png");
        }  catch (Exception e) {
            e.printStackTrace();
        }
        String msg = "{\n" +
                "    \"cmd\":\"to_device\",\n" +
                "    \"form\":\"client_id\",\n" +
                "    \"to\":\"RLX-00112236\",\n" +
                "    \"data\":{\n" +
                "        \"cmd\":\"setVisitorApplyValue\",\n" +
                "        \"url\":\"http://192.168.3.150:8081/qrcodeUrl?estate_id=1\",\n" +
                "        \"photo\":\""+ BASE64Util.compressPicForScale("C:\\Users\\Administrator\\Desktop\\MyQRCode.png",null, 50) +"\"\n" +
                "    }\n" +
                "}";
//        System.out.println(msg);
        sendMessageToUser("admin",msg);
    }

    /**
     * 关闭连接时触发
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        System.out.println("关闭session："+session);
        String userId = (String) session.getAttributes().get(USER_ID);
        System.out.println("用户" + userId + "已退出！");
        users.remove(userId);
        System.out.println("剩余在线用户" + users.size());
    }

    /*
    * 接收消息
    * */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
//        System.out.println("收到消息：" + message);
        if (message.getPayloadLength() > 0){
            receiveTest(session, message.getPayload());
        }

        if (message.getPayload().contains("在吗")) {
            session.sendMessage(new TextMessage("对方不在线！"));
        }

    }

    //接收消息后消息处理
    private void receiveTest(WebSocketSession session, String obj) throws IOException {
        System.out.println("收到消息"+obj);
        String key = "";
        String keyTo = "";
        try {
            JSONObject jsonObject = JSONObject.parseObject(obj);
//            System.out.println("jsonkey遍历"+jsonObject.keySet());
            for (String jsonkey : jsonObject.keySet()){
                if (jsonkey=="cmd"){
                    key = jsonObject.getString("cmd");
                }
                if(jsonkey=="to"){
                    keyTo = jsonObject.getString("to");
                }
                if (jsonkey=="logs"){
                    System.out.println("=============logs");
                    //json转entity实体
                    JSONArray jsonArray = (JSONArray) jsonObject.get("logs");
                    System.out.println("logs:"+jsonArray);
                    String photo = (String) ((JSONObject) jsonArray.get(0)).get("photo");
                    String photo2 = BASE64Util.decodeBase64ToImage(photo, "D:\\Download\\vl\\", "vl_"+UUID.randomUUID().toString() + ".jpg");
                    ((JSONObject) jsonArray.get(0)).put("photo",photo2);  //修改json数据属性值
                    FaceLogs faceLogs = JSON.parseObject(jsonArray.get(0).toString(), FaceLogs.class);
                    System.out.println("========"+faceLogs);
                }
            }
            System.out.println(key);
            switch (keyTo){
                case "识别成功语音" : {
                    //获取设备识别成功语音提示
                    String voice_text = ((JSONObject) ((JSONObject) ((JSONObject) jsonObject.get("data")).get("settings")).get("recognize_voice")).getString("voice_text");
                    System.out.println("设备识别成功语音提示=============="+voice_text);
                    String msgVoice = "{\n" +
                            "    \"cmd\":\"to_device\",\n" +
                            "    \"form\":\"client_id\",\n" +
                            "    \"to\":\"RLX-00112236\",\n" +
                            "    \"data\":{\n" +
                            "        \"cmd\":\"setVoice\",\n" +
                            "        \"type\":0,\n" +
                            "        \"voice_code\":-1,\n" +
                            "        \"voice_text\":\""+voice_text+"\"\n" +
                            "    }\n" +
                            "}";
                    //修改当前语音为设备识别成功语音
                    sendMessageToUser("admin",msgVoice);
                }
            }
            switch (key){
                //客户端声明
                case "declare" :{
                    String msg = "{\n" +
                            "    \"cmd\": \"declare\",\n" +
                            "    \"client_id\": \"RLX-00112236\",\n" +
                            "  }";
                    session.sendMessage(new TextMessage(msg));
                    break;
                }
                case "ping" : {
                    String msg = "{\n" +
                            "    \"cmd\": \"pong\"\n" +
                            "  }";
                    session.sendMessage(new TextMessage(msg));
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        System.out.println("传输出现异常，关闭websocket连接... ");
        String userId = (String) session.getAttributes().get(USER_ID);
        users.remove(userId);
    }

    public boolean supportsPartialMessages() {
        return false;
    }

    /*
    * 给某个用户发送消息
    * String userId,
    * */
    public void sendMessageToUser(String userId, String message) {
        System.out.println(message);
        for (String id : users.keySet()) {
            if (id.equals(userId)) {
                try {
                    if (users.get(id).isOpen()) {
                        System.out.println("给"+users.get(id)+"发送消息");
                        users.get(id).sendMessage(new TextMessage(message));
                    }
                } catch (IOException e) {
                    System.out.println("发送消息异常");
                    e.printStackTrace();
                }
                break;
            }
        }
    }
    /*
    * 给所有用户发送消息
    * */
    public void sendMessageToUsers(TextMessage message) {
        for (String userId : users.keySet()) {
            try {
                if (users.get(userId).isOpen()) {
                    users.get(userId).sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
