package com.um.psystem.mapper.platform;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zzj
 * @Description: 通用mapper 直接传sql
 * @Date: 2020/6/10
 * 建议写法:select * from sys_user where name=#{name} 这样比直接传sql可以防止sql注入隐患
 * 该接口基本满足sql的各种操作
 * #{name}参数可以通过map传递
 */

@Mapper
@Repository
public interface PublicMapper {
    @Select("${sqlStr}")
    List<LinkedHashMap<String,Object>> getPublicItems(Map<String,Object> modelMap);

    @Update("${usqlStr}")
    Integer updateItems(Map<String,Object> modelMap);

    @Insert("${isqlStr}")
    Integer saveItems(Map<String,Object> modelMap);

    @Delete("${dsqlStr}")
    Integer delItems(Map<String,Object> modelMap);

    /**
     * 存储过程调用
     * @param map
     * @return 返回值建议为List<Map<String,Object>> 可以读取存储过程的返回内容
     * 用法 将sql以及参数赋值到map 入参调用
     * 例：map.put("proSql",proSql);
     *     map.put("a","a");
     *     map.put("b","b");
     */
    @Select("${proSql}")
    @Options(statementType = StatementType.CALLABLE)
    List<Map<String,Object>> callProcedure(Map map);

}
