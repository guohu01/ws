package cn.yoyo.ws.servlet.Impl;

import cn.yoyo.ws.dao.BaseDao;
import cn.yoyo.ws.servlet.BaseService;
import cn.yoyo.ws.tools.MapToEntityTool;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public abstract class BaseServiceImpl<T> implements BaseService<T> {

    private T t;

    //提供一个抽象方法当前类的子类需要提供具体实现类的Dao
    public abstract BaseDao getBaseDao();

    //数据库表名
    private String tableName;
    //获取泛型参数T的实际类型
    private Class<?> clazz;
    public BaseServiceImpl(){
        clazz = (Class<?>) ((ParameterizedType)(this.getClass().getGenericSuperclass())).getActualTypeArguments()[0];
        //getSimpleName():获取到该类的底层类简介
        tableName = "t_"+clazz.getSimpleName().toLowerCase();
    }

    @Override
    public void add(T t) {

        List<Object> list = new ArrayList<>();
        for (Field field : t.getClass().getDeclaredFields()){   //getDeclaredFields拿到该类中的所有字段
            field.setAccessible(true);//让我们能够访问私有字段方法
            try {
                list.add(field.get(t));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        getBaseDao().add(tableName,list.toArray());
    }

    @Override
    public void delete(Integer id) {
        getBaseDao().delete(tableName,id);
    }

    @Override
    public void update(T t) {
        List<Object> list = new ArrayList<>();
        int id = 0;
        for (Field field : t.getClass().getDeclaredFields()){
            field.setAccessible(true);
            try {
                if (field.get(t)==null){
                    continue;
                }
                if ("id".equals(field.getName())){
                    id = (int) field.get(t);
                    continue;
                }
                list.add(field.getName()+"='"+field.get(t)+"'");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        getBaseDao().update(tableName,id,list.toArray());
    }

    @Override
    public T selectOneById(Integer id) {
        Map<Object, Object> rsMap = getBaseDao().selectOneById(tableName, id);
        T t = MapToEntityTool.map2Entity(rsMap, clazz);
        return t;
    }

    @Override
    public T selectOneByStringId(String uid) {
        Map<Object, Object> rsMap = getBaseDao().selectOneByStringId(tableName, uid);
        T t = MapToEntityTool.map2Entity(rsMap, clazz);
        return t;
    }

    @Override
    public List<T> selectAll() {
        List<Map<Object, Object>> rsList = getBaseDao().selectAll(tableName);
        List<T> list = new ArrayList<>();
        for (Map<Object,Object> map : rsList){
            list.add(MapToEntityTool.map2Entity(map,clazz));
        }
        return list;
    }

    @Override
    public void addForNotMatch(Object[] fieldNames, Object[] fieldValues) {
        System.out.println("tableName======"+tableName);
        System.out.println("fieldNames====="+ Arrays.toString(fieldNames));
        System.out.println("fieldValues======="+Arrays.toString(fieldValues));
        getBaseDao().addForNotMatch(tableName,fieldNames,fieldValues);
    }
}
