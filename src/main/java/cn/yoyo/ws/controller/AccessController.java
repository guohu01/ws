package cn.yoyo.ws.controller;

import cn.yoyo.ws.handle.SpringWebSocketHandler;
import cn.yoyo.ws.model.TempUser;
import cn.yoyo.ws.servlet.TempUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.UUID;

@CrossOrigin
@RestController
public class AccessController {

    @Autowired
    private TempUserService tempUserService;
    @Autowired
    private SpringWebSocketHandler springWebSocketHandler;

    @RequestMapping("/accessPage")
    public String indexPage(){
        return "access";
    }

    /**
     * 用户访客表单提交成功，下发访客人员到设备
     * @param map
     * @return
     * @throws UnsupportedEncodingException
     */
    @ResponseBody
    @PostMapping(value = "/accessPageRe",produces = "text/json;charset=utf-8")
    public String indexPageRe(@RequestBody Map map) throws UnsupportedEncodingException {
        String name = (String) map.get("name");
        String phone = (String) map.get("phone");
        String face_template = (String) map.get("face_template");
        String uid = (String) map.get("uid");
        TempUser tempUser = null;

        System.out.println(name);
        System.out.println(phone);
        System.out.println(face_template);
        System.out.println(uid);

        if (uid!=null && !"".equals(uid)){
            tempUser = tempUserService.selectOneByStringId(uid);
            System.out.println("查询结果："+tempUser);
            if (tempUser!=null){
                String msg = "{\n" +
                        "    \"cmd\":\"to_device\",\n" +
                        "    \"form\":\"123\",\n" +
                        "    \"to\":\"RLX-00112236\",\n" +
                        "    \"data\":{\n" +
                        "        \"cmd\":\"addUser\",\n" +
                        "        \"user_id\":\""+tempUser.getUserId()+"\",\n" +
                        "        \"name\":\""+tempUser.getName()+"\",\n" +
                        "        \"user_type\":\"2\",\n" +
                        "        \"face_template\":\""+ URLEncoder.encode(face_template,"utf-8") +"\",\n" +
                        "        \"vlface_template\":\"\",\n" +
                        "        \"effect_time\":\""+tempUser.getEffectTime()+"\",\n" +
                        "        \"id_valid\":\""+tempUser.getIdValid()+"\",\n" +
                        "        \"Ic\":\"\",\n" +
                        "        \"confidence_level\":80,\n" +
                        "        \"phone\":\""+phone+"\",\n" +
                        "        \"valid_cycle\":[\n" +
                        "            {\n" +
                        "            \"start_time\":\"07:00\",\n" +
                        "            \"end_time\":\"22:59\"\n" +
                        "            }\n" +
                        "        ]\n" +
                        "    }\n" +
                        "}";
                //下发人员到设备
                springWebSocketHandler.sendMessageToUser("admin",msg);
            }

        }



        return "success";
    }

    /**
     * 返回访客url表单链接
     * @param tempUser
     * @return
     */
    @PostMapping(value = "/back",produces = {"application/json;charset=utf-8"})
    public String bk(@RequestBody TempUser tempUser){
        System.out.println("请求成功");
        String uid = UUID.randomUUID().toString();
        tempUser.setUserId(uid);
        System.out.println(tempUser);
        tempUserService.add(tempUser);
        return "http://localhost:8080/#?uid="+uid;
    }

}
