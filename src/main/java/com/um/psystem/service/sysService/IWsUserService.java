package com.um.psystem.service.sysService;

import com.um.psystem.entity.sysEntity.WsUser;

import java.util.List;
import java.util.Map;

/**
 * @Author: zzj
 * @Description: 工作站用户接口
 * @Date: 2020/6/17
 */
public interface IWsUserService {
    List<WsUser> getUserList(Map<String,Object> columnMap);
}
