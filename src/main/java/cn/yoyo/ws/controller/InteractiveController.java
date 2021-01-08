package cn.yoyo.ws.controller;

import cn.yoyo.ws.handle.SpringWebSocketHandler;
import cn.yoyo.ws.model.*;
import cn.yoyo.ws.servlet.FaceLogsService;
import cn.yoyo.ws.tools.BASE64Util;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class InteractiveController {

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
                "        \"face_template\":\"data:image/jpeg;base64,"+ BASE64Util.compressPicForScale("C:\\Users\\Administrator\\Desktop\\b.jpg", 50) +"\",\n" +
                "        \"vlface_template\":\"\",\n" +
                "        \"effect_time\":\"\",\n" +
                "        \"id_valid\":\"\",\n" +
                "        \"Ic\":\"\",\n" +
                "        \"confidence_level\":80,\n" +
                "        \"phone\":\"15800000000\",\n" +
                "        \"valid_cycle\":[\n" +
                "            {\n" +
                "            \"start_time\":\"00:00\",\n" +
                "            \"end_time\":\"00:00\"\n" +
                "            },\n" +
                "            {\n" +
                "            \"start_time\":\"00:00\",\n" +
                "            \"end_time\":\"00:00\"\n" +
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

    /**
     * 识别成功
     * @param body
     * @return
     */
    @PostMapping("/api/v1/record/face")
    public ReturnMsg face(@RequestBody Face body){
        System.out.println("识别成功");
        ReturnMsg returnMsg = new ReturnMsg(0, "", "");
        if (body!=null){
            System.out.println(body);
            Face face = new Face();
            face = body;
            FaceLogs logs = body.getLogs().get(0);
            if (logs.getUser_id().equals("Open00000001")){
                System.out.println("远程开门");
                return returnMsg;
            }
            logs.setPhoto(BASE64Util.decodeBase64ToImage(logs.getPhoto(), "D:\\Download\\", UUID.randomUUID().toString() + ".jpg"));
            logs.setState(1);
//            faceLogsService.add(logs);
            System.out.println(logs);
        }
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
    @GetMapping("/switchOn")
    public String switchOn(){
        String msg = "{\n" +
                "    \"cmd\":\"to_device\",\n" +
                "    \"form\":\"client_id\",\n" +
                "    \"to\":\"RLX-00112236\",\n" +
                "    \"data\":{\n" +
                "        \"cmd\":\"setDoor\",\n" +
                "        \"user_id\":\"Open00000001\"\n" +
                "        \"value\":\"on\",\n" +
                "    }\n" +
                "}";
        springWebSocketHandler.sendMessageToUser("admin",msg);
        return "success";
    }

    /**
     * 关门
     * @return
     */
    @GetMapping("/switchOff")
    public String switchOff(){
        String msg = "{\n" +
                "    \"cmd\":\"to_device\",\n" +
                "    \"form\":\"client_id\",\n" +
                "    \"to\":\"RLX-00112236\",\n" +
                "    \"data\":{\n" +
                "        \"cmd\":\"setDoor\",\n" +
                "        \"user_id\":\"\"\n" +
                "        \"value\":\"off\",\n" +
                "    }\n" +
                "}";
        springWebSocketHandler.sendMessageToUser("admin",msg);
        return "success";
    }
}
