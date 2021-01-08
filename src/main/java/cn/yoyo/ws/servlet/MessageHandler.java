package cn.yoyo.ws.servlet;

import cn.yoyo.ws.model.FaceLogs;
import cn.yoyo.ws.tools.BASE64Util;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.UUID;

@Service
public class MessageHandler {
    public void faceUser(JSONObject jsonObject, WebSocketSession session){
        //json转entity实体
        JSONArray jsonArray = (JSONArray) jsonObject.get("logs");
        String photo = (String) ((JSONObject) jsonArray.get(0)).get("photo");
        String photo2 = BASE64Util.decodeBase64ToImage(photo, "D:\\Download\\", UUID.randomUUID().toString() + ".jpg");
        ((JSONObject) jsonArray.get(0)).put("photo",photo2);  //修改json数据属性值
        FaceLogs faceLogs = JSON.parseObject(jsonArray.get(0).toString(), FaceLogs.class);
        System.out.println(faceLogs);
    }
}
