package cn.yoyo.ws.model.qrcode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QrMsg {
    private Integer code;
    private String msg;
    private QrData data;
}
