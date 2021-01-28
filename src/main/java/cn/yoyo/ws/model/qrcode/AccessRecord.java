package cn.yoyo.ws.model.qrcode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessRecord {
    private String sn;
    private int[] qrcode_id;
    private long[] create_time;
    private String[] panoramic_picture;
}
