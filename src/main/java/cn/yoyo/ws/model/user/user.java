package cn.yoyo.ws.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class user {
    private String cmd;
    private String form;
    private String to;
    private UserData data;
}
