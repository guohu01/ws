package cn.yoyo.ws.controller;

import cn.yoyo.ws.handle.SpringWebSocketHandler;
import cn.yoyo.ws.model.Face;
import cn.yoyo.ws.model.MessageData;
import cn.yoyo.ws.model.MessageMsg;
import cn.yoyo.ws.model.ReturnMsg;
import cn.yoyo.ws.servlet.FaceLogsService;
import cn.yoyo.ws.servlet.MessageHandler;
import cn.yoyo.ws.tools.BASE64Util;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class InteractiveController {

    @Autowired
    private MessageHandler messageHandler;
    @Autowired
    SpringWebSocketHandler springWebSocketHandler;
    @Autowired
    private FaceLogsService faceLogsService;

    @GetMapping("/addUser")
    public String add(){
        String msg = "{\n" +
                "    \"cmd\":\"to_device\",\n" +
                "    \"form\":\"123\",\n" +
                "    \"to\":\"RLX-00112236\",\n" +
                "    \"data\":{\n" +
                "        \"cmd\":\"addUser\",\n" +
                "        \"user_id\":\"1\",\n" +
                "        \"name\":\"郭虎\",\n" +
                "        \"face_template\":\""+ BASE64Util.compressPicForScale("C:\\Users\\Administrator\\Desktop\\b.jpg",null, 50) +"\",\n" +
                "        \"vlface_template\":\"\",\n" +
                "        \"effect_time\":\"\",\n" +
                "        \"id_valid\":\"\",\n" +
                "        \"Ic\":\"\",\n" +
                "        \"confidence_level\":80,\n" +
                "        \"phone\":\"15800000000\",\n" +
                "        \"valid_cycle\":[\n" +
                "            {\n" +
                "            \"start_time\":\"07:00\",\n" +
                "            \"end_time\":\"10:30\"\n" +
                "            },\n" +
                "            {\n" +
                "            \"start_time\":\"14:00\",\n" +
                "            \"end_time\":\"21:00\"\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";
        JSONObject jsonObject = JSONObject.parseObject(msg);
        JSONObject jsonData = (JSONObject) jsonObject.get("data");
        MessageData messageData = JSON.parseObject(jsonData.toString(), MessageData.class);
        //保存上传数据

        System.out.println(msg);
        springWebSocketHandler.sendMessageToUser("admin",msg);
        return "success";
    }

    /**
     * 同步人员接口
     * @param data
     * @return
     */
    @ResponseBody
    @PostMapping("/api/v1/uploadFaceInfo")
    public ReturnMsg interactive(@RequestBody MessageMsg data){
        System.out.println("同步人员数据接口被调用");
        ReturnMsg returnMsg = new ReturnMsg(0, "", "更新成功");
        System.out.println(data);
        return returnMsg;
    }
    @GetMapping("/synchron")
    public String synchronization(){
        String src = "{\n" +
                "    \"cmd\":\"to_device\",\n" +
                "    \"form\":\"client_id\",\n" +
                "    \"to\":\"RLX-00112236\",\n" +
                "    \"data\":{\n" +
                "        \"cmd\":\"uploadFaceInfo\"\n" +
                "    }\n" +
                "}";
        springWebSocketHandler.sendMessageToUser("admin",src);
        return "success";
    }

    @GetMapping("/readAll")
    public String readAllUserId(){
        String msg = "{\n" +
                "    \"cmd\":\"to_device\",\n" +
                "    \"form\":\"client_id\",\n" +
                "    \"to\":\"RLX-00112236\",\n" +
                "    \"data\":{\n" +
                "        \"cmd\":\"getUserInfo\",\n" +
                "        \"value\":1\n" +
                "    }\n" +
                "}";
        springWebSocketHandler.sendMessageToUser("admin",msg);
        return "success";
    }
    @GetMapping("/readUser")
    public String readUser(){
        String msg = "{\n" +
                "    \"cmd\":\"to_device\",\n" +
                "    \"form\":\"client_id\",\n" +
                "    \"to\":\"RLX-00112236\",\n" +
                "    \"data\":{\n" +
                "        \"cmd\":\"getUserDetails\",\n" +
                "        \"value\":0,\n" +
                "        \"user_id\":\"1\"\n" +
                "    }\n" +
                "}";
        springWebSocketHandler.sendMessageToUser("admin",msg);
        return "success";
    }

    /**
     * 识别成功
     * @param body
     * @return
     */
    @PostMapping("/api/v1/record/face")
    public ReturnMsg face(@RequestBody Face body){
        /*System.out.println("识别成功");
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
                    logs.setPhoto(BASE64Util.decodeBase64ToImage(logs.getPhoto(), "D:\\Download\\vl\\", "vl"+UUID.randomUUID().toString() + ".jpg"));
                    logs.setState(0);
                    //远程开门数据存储
                    faceLogsService.add(logs);
                    return returnMsg;
                }
            }
            FaceLogs byIdAndDate = faceLogsService.getByIdAndDate(logs.getUser_id(), logs.getRecog_time());
            if (byIdAndDate==null){
                logs.setPhoto(BASE64Util.decodeBase64ToImage(logs.getPhoto(), "D:\\Download\\vl\\", "vl"+UUID.randomUUID().toString() + ".jpg"));
                logs.setState(1);
                faceLogsService.add(logs);
            }
        }*/
        ReturnMsg returnMsg = messageHandler.face(body);
        return returnMsg;
    }

    /**
     * 陌生人识别接口
     * @return
     */
    @PostMapping("/api/v1/stranger")
    public ReturnMsg stranger(){
        System.out.println("陌生人识别接口被调用");
        ReturnMsg returnMsg = new ReturnMsg();
        returnMsg.setResult(0);
        returnMsg.setMsg("更新成功");
        return returnMsg;
    }

    /**
     * 开门
     */
    @RequestMapping("/switchOn")
    public String switchOn(){
        /*String msg = "{\n" +
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
        //
        String capture = "{\n" +
                "    \"cmd\":\"to_device\",\n" +
                "    \"form\":\"client_capture\",\n" +
                "    \"to\":\"设备编号\",\n" +
                "    \"data\":{\n" +
                "        \"cmd\":\"onlineAuthorization\"\n" +
                "    }\n" +
                "}";
        //抓拍请求
        springWebSocketHandler.sendMessageToUser("admin",capture);
        //开门
        springWebSocketHandler.sendMessageToUser("admin",msg);
        //修改语音提示
        springWebSocketHandler.sendMessageToUser("admin",msgVoice);*/
        messageHandler.switchOn();
        return "success";
    }

    /**
     * 关门
     * @return
     */
    @RequestMapping("/switchOff")
    public String switchOff(){
        String msg = "{\n" +
                "    \"cmd\":\"to_device\",\n" +
                "    \"form\":\"client_id\",\n" +
                "    \"to\":\"RLX-00112236\",\n" +
                "    \"data\":{\n" +
                "        \"cmd\":\"setDoor\",\n" +
                "        \"user_id\":\"Open00000001\",\n" +
                "        \"value\":\"off\"\n" +
                "    }\n" +
                "}";
        springWebSocketHandler.sendMessageToUser("admin",msg);
        return "success";
    }
}
