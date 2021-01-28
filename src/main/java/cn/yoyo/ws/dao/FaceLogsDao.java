package cn.yoyo.ws.dao;

import cn.yoyo.ws.model.FaceLogs;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface FaceLogsDao extends BaseDao {
    public FaceLogs getByIdAndDate(@Param("id") String id, @Param("enterDate") Date enterDate );
}
