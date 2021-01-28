package cn.yoyo.ws.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageValidCycle {
    private String start_time;
    private String end_time;
}
