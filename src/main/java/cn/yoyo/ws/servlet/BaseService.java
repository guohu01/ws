package cn.yoyo.ws.servlet;

import java.util.List;

public interface BaseService<T> {
    /**
     * 这个添加方法有局限性，只能是entity类的属性个数和数据库的字段数一一对应关系才能使用
     * @param t
     */
    public void add(T t);

    public void addForNotMatch(Object[] fieldNames,Object[] fieldValues);

    public void delete(Integer id);

    public void update(T t);

    public T selectOneById(Integer id);

    public List<T> selectAll();
}
