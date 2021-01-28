package cn.yoyo.ws.servlet.Impl;

import cn.yoyo.ws.dao.BaseDao;
import cn.yoyo.ws.dao.UserDao;
import cn.yoyo.ws.model.User;
import cn.yoyo.ws.servlet.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public BaseDao getBaseDao() {
        return userDao;
    }
}
