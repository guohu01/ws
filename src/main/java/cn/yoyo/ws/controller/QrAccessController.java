package cn.yoyo.ws.controller;

import cn.yoyo.ws.handle.SpringWebSocketHandler;
import cn.yoyo.ws.model.Stranger;
import cn.yoyo.ws.model.qrcode.*;
import cn.yoyo.ws.servlet.MessageHandler;
import cn.yoyo.ws.servlet.StrangerService;
import cn.yoyo.ws.tools.QrCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "http://192.168.3.150:8081", maxAge = 3600)
@Controller
public class QrAccessController {
    @Autowired
    private SpringWebSocketHandler springWebSocketHandler;
    @Autowired
    private MessageHandler messageHandler;
    @Autowired
    private StrangerService strangerService;

    @RequestMapping(value = "/qrcodeUrl",produces = "text/json;charset=utf-8")
    public String qrcodeUrl(){
        System.out.println("url被访问");
        return "accessUrl";
    }

    /**
     * url页面提交
     * @param captcha
     * @return
     */
    @ResponseBody
    @PostMapping(value = "visitorSubmit",produces = "text/json;charset=utf-8")
    public String visitorSub(@RequestParam String username, @RequestParam String phone, @RequestParam String matter,@RequestParam String captcha){
        Stranger stranger = new Stranger();
        stranger.setUsername(username);
        stranger.setPhone(phone);
        stranger.setMatter(matter);

        System.out.println(captcha);
        if (captcha.equals("abc123")){
            System.out.println("验证码开门");
            String msg = "{\n" +
                    "    \"cmd\":\"to_device\",\n" +
                    "    \"form\":\"client_id\",\n" +
                    "    \"to\":\"RLX-00112236\",\n" +
                    "    \"data\":{\n" +
                    "        \"cmd\":\"setVoice\",\n" +
                    "        \"type\":0,\n" +
                    "        \"voice_code\":-1,\n" +
                    "        \"voice_text\":\"欢迎来到奥诺\"\n" +
                    "    }\n" +
                    "}";
            springWebSocketHandler.sendMessageToUser("admin",msg);
            //执行开门
            messageHandler.switchOn();
            //开门存储数据
            strangerService.outdata(stranger);
            /*new Thread(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    String url = "http://192.168.3.150:8081/switchOn";
                    HttpClient httpclient = HttpClients.createDefault();
                    HttpGet httpGet = new HttpGet(url);
                    HttpResponse response = httpclient.execute(httpGet);
                }
            }).start();*/
        }
        return "提交成功";
    }

    /**
     * 二维码在线比对
     * @param recognition
     * @return
     */
    @ResponseBody
    @PostMapping("/api/v1/qrcode_recognition")
    public QrRecognitionRet recognition(@RequestBody QrRecognition recognition){
        System.out.println("二维码在线比对接口被调用");
        System.out.println(recognition);

        /*if (recognition.getQrcode_content().equals(QrCreate.create())){
            System.out.println("识别通过");
            return new QrRecognitionRet(0,"识别通过",new QrContent(100,""));
        }*/
        System.out.println(QrCreate.qrmsg);
        return new QrRecognitionRet(0,"识别通过",new QrContent(105,""));

//        System.out.println("出错");
//        return new QrRecognitionRet(6,"识别通过",new QrContent(203,""));
    }

    /**
     * 上传二维码访问记录
     * @param accessRecord
     * @return
     */
    @ResponseBody
    @PostMapping("/client_write_record")
    public QrMsg record(@RequestBody AccessRecord accessRecord){
        System.out.println("上传二维码访问记录");
        if (accessRecord!=null){
            QrMsg qrMsg = new QrMsg();
            qrMsg.setCode(200);
            qrMsg.setMsg("请求成功");
            return qrMsg;
        }
        return null;
    }



}
