package cn.yoyo.ws.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FaceLogs {
    private String user_id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date recog_time;
    private String recog_type;
    private String photo;
    private String body_temperature;
    private String confidence;
    private String reflectivity;
    private String room_temperature;
    //进入人员状态 1：自己人  0：陌生人
    private Integer state;

}
