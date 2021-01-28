package cn.yoyo.ws.tools;

import cn.yoyo.ws.enums.PeopleState;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * map转entity类
 */
public class MapToEntityTool {

    private static Map<String,EntityCacheItem> convertItemCache = new HashMap<>();

    public static <T> T map2Entity(Map<Object,Object> map,Class<?> entityClass){
        if (map==null){
            return null;
        }
        //尝试从缓存中获取entityCacheItem对象
        EntityCacheItem eci = convertItemCache.get(entityClass.getName());
        if (eci==null){
            eci = EntityCacheItem.createConvertItem(entityClass);
            convertItemCache.put(entityClass.getName(),eci);
        }

        List<String> allFieldList = eci.getAllFieldList();
        Map<String, Method> setMethodMap = eci.getSetMethodMap();

        String key;
        String key1;
        String key2;
        Map<Object,Object> targetMap = new HashMap<>();

        for(Map.Entry<Object, Object> entry:map.entrySet()) {
            key = entry.getKey().toString();
            while(key.contains("_")) {
                key1 = key.substring(0,key.indexOf('_'));
                key2 = key.substring(key.indexOf('_')+1);
                key2 = key2.substring(0,1).toUpperCase() + key2.substring(1);
                key = key1 + key2;
            }
            targetMap.put(key, entry.getValue());
        }

        //把Map里的值放到entity对象里
        T entity = null;
        try {
            //获得该对象的实例
            entity = (T) entityClass.newInstance();
        } catch (Exception e) {
            System.out.println("获取对象实例出错");
            e.printStackTrace();
            return null;
        }

        //字段对应的值
        Object fieldValue = null;
        //setMethodMap中取出的value值(方法名)
        Method method = null;
        //方法中参数的类型
        Class<?>[] paramTypes = null;
        for (String strFieldName : allFieldList) {
//            fieldValue = map.get(strFieldName);
            fieldValue = targetMap.get(strFieldName);

            if (fieldValue == null) continue;
            method = setMethodMap.get(strFieldName);
            if (method == null) continue;
            paramTypes = method.getParameterTypes();

//            System.out.println("paramType======="+paramTypes[0]+"========"+fieldValue.getClass());
            if (paramTypes == null || paramTypes.length>1) continue;
            if (paramTypes[0].isAssignableFrom(PeopleState.class) && fieldValue.getClass().isAssignableFrom(Integer.class))
                fieldValue = PeopleState.getPeopleStateByCode((Integer) fieldValue);
            if (paramTypes[0].isAssignableFrom(fieldValue.getClass())){
                //方法中参数类型与数据库中字段类型相同
                /*System.out.println("method:"+method);
                System.out.println("entity:"+entity);
                System.out.println("fieldValue:"+fieldValue);*/
                try {
                    method.invoke(entity,fieldValue);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return entity;
    }

    /**
     * 内部类用于缓存Entity类，提高代码效率
     */
    static class EntityCacheItem{
        private List<String> allFieldList = new ArrayList<>();
        private Map<String,Method> setMethodMap = new HashMap<>();

        public List<String> getAllFieldList() {
            return allFieldList;
        }
        public Map<String, Method> getSetMethodMap() {
            return setMethodMap;
        }

        private EntityCacheItem() {
        }

        /**
         * 拿到entityClass的属性及set方法
         * @param entityClass
         */
        public void parseEntity(Class<?> entityClass){
            Field[] fields = entityClass.getDeclaredFields();
            System.out.println("====="+fields);
            String fieldName;
            String setMethodName;
            Method setMethod = null;
            for (Field field : fields){
                field.setAccessible(true);   //获得能够操作private字段的权限
                fieldName = field.getName();
                allFieldList.add(fieldName);
                setMethodName = "set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
                try {
                    setMethod = entityClass.getDeclaredMethod(setMethodName,field.getType());
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                setMethodMap.put(fieldName,setMethod);
            }
        }

        public static EntityCacheItem createConvertItem(Class<?> entityClass){
            EntityCacheItem eci = new EntityCacheItem();
            eci.parseEntity(entityClass);
            return eci;
        }
    }
}

