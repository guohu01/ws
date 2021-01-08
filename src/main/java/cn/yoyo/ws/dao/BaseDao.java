package cn.yoyo.ws.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BaseDao {
    public void add(@Param("tableName") String tableName, @Param("objects") Object[] objects);

    public void delete(@Param("tableName")String tableName, @Param("id") Integer id);

    public void update(@Param("tableName")String tableName, @Param("id") Integer id,@Param("objects")Object[] objects);

    public Map<Object,Object> selectOneById(@Param("tableName")String tableName, @Param("id") Integer id);

    public List<Map<Object,Object>> selectAll(@Param("tableName") String tableName);

    void addForNotMatch(@Param("tableName") String tableName, @Param("fieldNames") Object[] fieldNames, @Param("fieldValues") Object[] fieldValues);
}
