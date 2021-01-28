package cn.yoyo.ws.servlet.Impl;

import cn.yoyo.ws.dao.BaseDao;
import cn.yoyo.ws.dao.TempUserDao;
import cn.yoyo.ws.model.TempUser;
import cn.yoyo.ws.servlet.TempUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TempUserServiceImpl extends BaseServiceImpl<TempUser> implements TempUserService {
    @Autowired
    private TempUserDao tempUserDao;

    @Override
    public BaseDao getBaseDao() {
        return tempUserDao;
    }
}
