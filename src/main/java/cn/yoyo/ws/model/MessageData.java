package cn.yoyo.ws.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageData {
    private String user_id;
    private String name;
    private String phone;
    private String id_card;
    private String ic_card;
    private String validity_period;
    private float confidence_level;
    private String photo;
    private String inf_photo;
    private String user_type;
    private List<MessageValidCycle> valid_cycle;

}
