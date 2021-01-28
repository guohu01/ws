package cn.yoyo.ws.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TempUser {
    private String userId;
    private String name;
    private String phnone;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm",timezone = "GMT+8")
    private Date effectTime;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm",timezone = "GMT+8")
    private Date idValid;
}
