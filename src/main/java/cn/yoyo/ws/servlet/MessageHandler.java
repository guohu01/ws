package cn.yoyo.ws.servlet;

import cn.yoyo.ws.handle.SpringWebSocketHandler;
import cn.yoyo.ws.model.*;
import cn.yoyo.ws.servlet.Impl.StrangerServiceImpl;
import cn.yoyo.ws.tools.BASE64Util;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.UUID;

@Service
public class MessageHandler {

    @Autowired
    private SpringWebSocketHandler springWebSocketHandler;
    @Autowired
    private FaceLogsService faceLogsService;
    @Autowired
    private StrangerService strangerService;
    @Autowired
    private TempUserService tempUserService;

    public void faceUser(JSONObject jsonObject, WebSocketSession session){
        //json转entity实体
        JSONArray jsonArray = (JSONArray) jsonObject.get("logs");
        String photo = (String) ((JSONObject) jsonArray.get(0)).get("photo");
        String photo2 = BASE64Util.decodeBase64ToImage(photo, "D:\\Download\\", UUID.randomUUID().toString() + ".jpg");
        ((JSONObject) jsonArray.get(0)).put("photo",photo2);  //修改json数据属性值
        FaceLogs faceLogs = JSON.parseObject(jsonArray.get(0).toString(), FaceLogs.class);
        System.out.println(faceLogs);
    }

    public String switchOn(){
        String msg = "{\n" +
                "    \"cmd\":\"to_device\",\n" +
                "    \"form\":\"client_id\",\n" +
                "    \"to\":\"RLX-00112236\",\n" +
                "    \"data\":{\n" +
                "        \"cmd\":\"setDoor\",\n" +
                "        \"user_id\":\"Open00000001\",\n" +
                "        \"value\":\"on\"\n" +
                "    }\n" +
                "}";
        String msgVoice = "{\n" +
                "    \"cmd\":\"to_device\",\n" +
                "    \"form\":\"client_id\",\n" +
                "    \"to\":\"RLX-00112236\",\n" +
                "    \"data\":{\n" +
                "        \"cmd\":\"setVoice\",\n" +
                "        \"type\":0,\n" +
                "        \"voice_code\":-1,\n" +
                "        \"voice_text\":\"自己人，好好工作加油\"\n" +
                "    }\n" +
                "}";
        //开门
        springWebSocketHandler.sendMessageToUser("admin",msg);
        //修改语音提示
        springWebSocketHandler.sendMessageToUser("admin",msgVoice);
        return "success";
    }

    /**
     * 识别成功
     * @param body
     * @return
     */
    public ReturnMsg face(Face body){
        System.out.println("识别成功");
        ReturnMsg returnMsg = new ReturnMsg(0, "", "");
        if (body!=null){
//            System.out.println(body);
            Face face = new Face();
            face = body;
            FaceLogs logs = body.getLogs().get(0);
            System.out.println(logs);
            if (logs.getUser_id().equals("Open00000001")){
                FaceLogs byIdAndDate = faceLogsService.getByIdAndDate(logs.getUser_id(), logs.getRecog_time());
                if (byIdAndDate==null){
                    System.out.println("远程开门");
                    String photo = BASE64Util.decodeBase64ToImage(logs.getPhoto(), "D:\\Download\\vl\\", "vl" + UUID.randomUUID().toString() + ".jpg");
                    logs.setPhoto(photo);
                    logs.setState(0);
                    //远程开门数据存储
                    faceLogsService.add(logs);

                    Stranger stranger = new Stranger();
                    stranger.setUsername(StrangerServiceImpl.username);
                    stranger.setPhone(StrangerServiceImpl.phone);
                    stranger.setMatter(StrangerServiceImpl.matter);
                    stranger.setAccess_data(logs.getRecog_time());
                    stranger.setVl_photo(photo);
                    stranger.setState(0);
                    System.out.println("--------------------"+StrangerServiceImpl.username);
                    if (StrangerServiceImpl.username == null){
                        stranger.setUsername("访客");
                    }
                    strangerService.switchOnSave(stranger);
//                    System.out.println(stranger);
                    return returnMsg;
                }
            }else{
                TempUser tempUser = null;
                try {
                    tempUser = tempUserService.selectOneByStringId(logs.getUser_id());
                    System.out.println("tempUser:"+tempUser);
                } catch (Exception e) {
                    System.out.println("tempUser查询出错");
                    e.printStackTrace();
                }
                if (tempUser!=null){
                    //访客进入
                    //修改语音提示
                    String msgVoice = "{\n" +
                            "    \"cmd\":\"to_device\",\n" +
                            "    \"form\":\"client_id\",\n" +
                            "    \"to\":\"RLX-00112236\",\n" +
                            "    \"data\":{\n" +
                            "        \"cmd\":\"setVoice\",\n" +
                            "        \"type\":0,\n" +
                            "        \"voice_code\":-1,\n" +
                            "        \"voice_text\":\"欢迎"+tempUser.getName()+"来到奥诺\"\n" +
                            "    }\n" +
                            "}";
                    springWebSocketHandler.sendMessageToUser("admin",msgVoice);
                }
            }
            FaceLogs byIdAndDate = faceLogsService.getByIdAndDate(logs.getUser_id(), logs.getRecog_time());
            if (byIdAndDate==null){
                logs.setPhoto(BASE64Util.decodeBase64ToImage(logs.getPhoto(), "D:\\Download\\vl\\", "vl"+UUID.randomUUID().toString() + ".jpg"));
                logs.setState(1);
                faceLogsService.add(logs);
            }
        }
        return returnMsg;
    }
}
