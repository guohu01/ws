package cn.yoyo.ws.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stranger {
    private int id;
    private String username;
    private String phone;
    private Date access_data;
    private String matter;
    private String vl_photo;
    private String ir_photo;
    private Integer state;

}
