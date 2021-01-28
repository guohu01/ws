package cn.yoyo.ws.servlet;

import cn.yoyo.ws.model.FaceLogs;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public interface FaceLogsService extends BaseService<FaceLogs> {
    public FaceLogs getByIdAndDate(String id, Date enterDate );
}
