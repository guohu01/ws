package cn.yoyo.ws.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserData {
    private String cmd;
    private int code;
    private UserDetails details;

}
