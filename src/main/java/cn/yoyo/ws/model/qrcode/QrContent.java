package cn.yoyo.ws.model.qrcode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QrContent {
    private Integer voice_code;
    private String voice_text;
}
