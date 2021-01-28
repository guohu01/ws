package cn.yoyo.ws;

import cn.yoyo.ws.servlet.TempUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;

@SpringBootTest
class WsApplicationTests {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private TempUserService tempUserService;

    @Test
    void contextLoads() throws ParseException {
        byte[] data = null;
        InputStream in = null;

        try {
            URL url = new URL("blob:http://localhost:8080/618c24d2-5267-4c87-8ea0-6b68697b1711");
            //创建连接对象
            URLConnection urlConnection = url.openConnection();
            urlConnection.setConnectTimeout(1000);
            urlConnection.setReadTimeout(5000);
            urlConnection.connect();
            //获取流
            in = urlConnection.getInputStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //赋值，关闭流
        try {
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
