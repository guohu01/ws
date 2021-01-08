package cn.yoyo.ws;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
class WsApplicationTests {

    @Autowired
    private DataSource dataSource;
    @Test
    void contextLoads() throws SQLException {
        System.out.println("========"+dataSource.getConnection());
    }

}
