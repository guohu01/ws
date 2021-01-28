package cn.yoyo.ws.model.qrcode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QrRecognitionRet {
    private Integer Result;
    private String Msg;
    private QrContent Content;
}
