package cn.yoyo.ws.model;

import lombok.Data;

/**
 * 临时存储访客数据表
 */
@Data
public class User {
    private String user_id;
    private String name;
    private String face_template;
    private String vlface_template;
    //0 正常用户、10 黑名单用户，识别后设备会报警，并产生黑名单识别记录、2: 访客（访客到期后会自动从设备中删除）
    private String user_type;
    //生效时间
    private String effect_time;
    //有效时间，这个时间点后无法通行
    private String id_valid;
    private String Ic;
    private String confidence_level;
    private String phone;
    private String start_time;
    private String end_time;

}
