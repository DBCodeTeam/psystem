package com.um.psystem.mapper.platform.sysMapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.um.psystem.mapper.BaseMapper;
import com.um.psystem.entity.sysEntity.SystemLog;
import com.um.psystem.model.sysModel.request.SystemLogRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author zzj
 * @since 2020-05-18
 */
public interface SystemLogMapper extends BaseMapper<SystemLog> {
    public List<SystemLog> findSystemLog(Pagination page, @Param("request") SystemLogRequest request);


}