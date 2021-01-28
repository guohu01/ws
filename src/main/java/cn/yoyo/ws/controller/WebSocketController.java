package cn.yoyo.ws.controller;

import cn.yoyo.ws.handle.SpringWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.TextMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/websocket",method = {RequestMethod.POST,RequestMethod.GET})   //get方法仅用于测试
public class WebSocketController {
    @Autowired
    SpringWebSocketHandler springWebSocketHandler;

    @GetMapping("/index")
    public String index(){
        return "index";
    }

    /**
     * 登录将username放入session中，然后在拦截器HandshakeInterceptor中取出
     */
    @ResponseBody
    @RequestMapping("/login")
    public String login(HttpServletRequest request, @RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
        System.out.println("登录：" + username + "：" + password);
        HttpSession session = request.getSession();
        if (null != session) {
            session.setAttribute("SESSION_USERNAME", username);
            System.out.println(session);
            return "success";
        } else {
            return "fail";
        }
    }

    /**
     * 指定发送
     */
    @ResponseBody
    @RequestMapping("/sendToUser")
    public String send() {
        String msg = "{\"cmd\":\"to_device\",\"form\":\"client_id\",\"to\":\"RLX-00112236\",\"data\":{\"cmd\":\"uploadFaceInfo\"}}";
        springWebSocketHandler.sendMessageToUser("admin", msg);
        return "success";
    }

    /**
     * 广播
     */
    @ResponseBody
    @RequestMapping("/broadcast")
    public String broadcast(@RequestParam(value = "info") String info) {
        springWebSocketHandler.sendMessageToUsers(new TextMessage("广播消息：" + info));
        System.out.println("广播成功");
        return "success";
    }
}
