package cn.yoyo.ws.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TextMessage {
    private String sn;
    private Boolean is_complete;
    private List<MessageData> data;
}
