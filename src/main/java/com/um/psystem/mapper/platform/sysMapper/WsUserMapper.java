package com.um.psystem.mapper.platform.sysMapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.um.psystem.entity.sysEntity.WsUser;
import com.um.psystem.mapper.BaseMapper;
import com.um.psystem.model.sysModel.request.UserRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: zzj
 * @Description: 工作站用户
 * @Date: 2020/6/16
 */
public interface WsUserMapper extends BaseMapper<WsUser> {

    public List<WsUser> findUser(Pagination page, @Param("request") UserRequest request);

    public List<WsUser> findUserByRoleCode(Pagination page, @Param("roleCode") String roleCode);

    public List<WsUser> findUserByRoleCode(@Param("roleCode") String roleCode);
}
