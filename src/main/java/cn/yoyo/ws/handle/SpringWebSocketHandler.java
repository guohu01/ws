package cn.yoyo.ws.handle;

import cn.yoyo.ws.model.FaceLogs;
import cn.yoyo.ws.servlet.MessageHandler;
import cn.yoyo.ws.tools.BASE64Util;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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

    /*
    * 存储用户id和其对应的session
    * */
    public static final Map<String, WebSocketSession> users = new HashMap<>();

    @Autowired
    private MessageHandler messageHandler;

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
        System.out.println("成功建立websocket连接thid.session"+this.session);*/

        /*String userId = (String) session.getAttributes().get(USER_ID);//取出在拦截器中存储的username
        System.out.println("当前用户："+userId);*/
        users.put("admin", session);
        System.out.println("当前线上用户数量:" + users.size());
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
        System.out.println("收到消息：" + message);
        if (message.getPayloadLength() > 0){
            receiveTest(session, message.getPayload());
        }

        if (message.getPayload().contains("在吗")) {
            session.sendMessage(new TextMessage("对方不在线！"));
        }

    }

    //接收消息后消息处理
    private void receiveTest(WebSocketSession session, String obj) throws IOException {
        System.out.println(obj);
        String key = "";
        try {
            JSONObject jsonObject = JSONObject.parseObject(obj);
            System.out.println("jsonkey遍历"+jsonObject.keySet());
            for (String jsonkey : jsonObject.keySet()){
                if (jsonkey=="cmd"){
                    key = jsonObject.getString("cmd");
                }
                if (jsonkey=="logs"){
                    System.out.println("=============logs");
//                    messageHandler.faceUser(jsonObject,session);

                    //json转entity实体
                    JSONArray jsonArray = (JSONArray) jsonObject.get("logs");
                    System.out.println("logs:"+jsonArray);
                    String photo = (String) ((JSONObject) jsonArray.get(0)).get("photo");
                    String photo2 = BASE64Util.decodeBase64ToImage(photo, "D:\\Download\\", UUID.randomUUID().toString() + ".jpg");
                    ((JSONObject) jsonArray.get(0)).put("photo",photo2);  //修改json数据属性值
                    FaceLogs faceLogs = JSON.parseObject(jsonArray.get(0).toString(), FaceLogs.class);
                    System.out.println("========"+faceLogs);

                }
            }
            System.out.println(key);
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
//        users.get(userId).sendMessage(new TextMessage(message));
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
