package com.um.psystem.mapper.platform.mtManageMapper;

import com.um.psystem.entity.mtEntity.AssetsType;
import com.um.psystem.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by zzj on 2020/5/24.
 */
@Repository
public interface AssetsTypeMapper extends BaseMapper<AssetsType> {
    public List<Map<String,Object>> findMtCategory(@Param("id")String id);

    public List<Map<String,Object>> findUserByDept(@Param("deptName") String deptName);

    public void callProcedure(Map map);
}
