package com.um.psystem.service;

import com.um.psystem.service.IBaseService;
import com.um.psystem.entity.UserRole;

import java.util.List;

/**
 * Role Service接口
 * @create 2020-05-19 10:45:58
 */
public interface IUserRoleService extends IBaseService<UserRole> {

    public int deleteUserRole(Long userId, Long roleId);

    public Integer changeUserRole(Long userId, List<Long> targetRoles);

}
