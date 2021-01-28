package cn.yoyo.ws.servlet;

import cn.yoyo.ws.model.Stranger;

public interface StrangerService extends BaseService<Stranger> {

    public void switchOnSave(Stranger stranger);

    public void outdata(Stranger stranger);
}
