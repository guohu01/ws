package cn.yoyo.ws.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor                 //无参构造
@AllArgsConstructor                //有参构造
@Data
public class Face {
    private String sn;
    private String count;
    private List<FaceLogs> logs;
}
