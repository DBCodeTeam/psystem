package com.um.psystem.service.sysService.impl;

import com.um.psystem.entity.sysEntity.WsUser;
import com.um.psystem.mapper.platform.sysMapper.WsUserMapper;
import com.um.psystem.service.sysService.IWsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: zzj
 * @Description: 工作站用户接口实现
 * @Date: 2020/6/17
 */
@Service
public class WsUserService implements IWsUserService {
    @Autowired
    WsUserMapper wsUserMapper;
    @Override
    public List<WsUser> getUserList(Map<String, Object> columnMap) {
        return wsUserMapper.selectByMap(columnMap);
    }
}
