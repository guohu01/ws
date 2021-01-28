package cn.yoyo.ws.servlet.Impl;

import cn.yoyo.ws.dao.BaseDao;
import cn.yoyo.ws.dao.StrangerDao;
import cn.yoyo.ws.model.Stranger;
import cn.yoyo.ws.servlet.StrangerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StrangerServiceImpl extends BaseServiceImpl<Stranger> implements StrangerService {
    @Autowired
    private StrangerDao strangerDao;
    @Override
    public BaseDao getBaseDao() {
        return strangerDao;
    }

    public static String username;
    public static String phone;
    public static String matter;

    /**
     * 开门存储数据
     * @param stranger
     */
    @Override
    public void switchOnSave(Stranger stranger) {
        this.add(stranger);
    }

    @Override
    public void outdata(Stranger stranger) {
        username = stranger.getUsername();
        phone = stranger.getPhone();
        matter = stranger.getMatter();
    }
}
