package com.um.psystem.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.um.psystem.service.IBaseService;
import com.um.psystem.entity.SystemLog;
import com.um.psystem.model.request.SystemLogRequest;
import com.um.psystem.model.response.SystemLogResponse;

/**
 * SystemLog Service接口
 * @create 2020-05-19 21:34:13
 */
public interface ISystemLogService extends IBaseService<SystemLog> {

    public Page<SystemLogResponse> getPage(Page<SystemLog> page, SystemLogRequest request);
}
