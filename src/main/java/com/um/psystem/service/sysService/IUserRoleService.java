package com.um.psystem.service.sysService;

import com.um.psystem.entity.sysEntity.UserRole;

import java.util.List;

/**
 * Role Service接口
 * @create 2020-05-19 10:45:58
 */
public interface IUserRoleService extends IBaseService<UserRole> {

    public int deleteUserRole(Long userId, Long roleId);

    public Integer changeUserRole(Long userId, List<Long> targetRoles);

}
