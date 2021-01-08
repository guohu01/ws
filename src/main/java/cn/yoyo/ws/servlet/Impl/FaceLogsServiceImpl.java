package cn.yoyo.ws.servlet.Impl;

import cn.yoyo.ws.dao.FaceLogsDao;
import cn.yoyo.ws.dao.BaseDao;
import cn.yoyo.ws.model.FaceLogs;
import cn.yoyo.ws.servlet.FaceLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FaceLogsServiceImpl extends BaseServiceImpl<FaceLogs> implements FaceLogsService {

    @Autowired
    private FaceLogsDao faceLogsDao;

    @Override
    public BaseDao getBaseDao() {
        return faceLogsDao;
    }
}
