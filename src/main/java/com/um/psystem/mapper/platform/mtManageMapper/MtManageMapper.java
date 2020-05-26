package com.um.psystem.mapper.platform.mtManageMapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by zzj on 2020/5/24.
 */
@Repository
public interface MtManageMapper {
    public List<Map<String,Object>> findMtCategory(@Param("id")String id);
}
