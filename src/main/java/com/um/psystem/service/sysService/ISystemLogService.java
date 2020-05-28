package com.um.psystem.service.sysService;

import com.baomidou.mybatisplus.plugins.Page;
import com.um.psystem.entity.sysEntity.SystemLog;
import com.um.psystem.model.sysModel.request.SystemLogRequest;
import com.um.psystem.model.sysModel.response.SystemLogResponse;

/**
 * SystemLog Service接口
 * @create 2020-05-19 21:34:13
 */
public interface ISystemLogService extends IBaseService<SystemLog> {

    public Page<SystemLogResponse> getPage(Page<SystemLog> page, SystemLogRequest request);
}
