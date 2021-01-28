package cn.yoyo.ws.model.qrcode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QrRecognition {
    private String sn;
    private String qrcode_content;
    private float temperature;
    private String user_id;
}
