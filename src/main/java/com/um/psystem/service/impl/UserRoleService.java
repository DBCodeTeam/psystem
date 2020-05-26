package com.um.psystem.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.um.psystem.annotation.Log;
import com.um.psystem.entity.UserRole;
import com.um.psystem.mapper.platform.sysMapper.UserRoleMapper;
import com.um.psystem.service.IUserRoleService;
import com.um.psystem.utils.Utils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Role Service实现类
 * 
 * @create 2020-05-18 10:45:58
 */
@Service
public class UserRoleService extends BaseService<UserRoleMapper, UserRole> implements IUserRoleService {

    @Override
    @Transactional
    @Log(module = "系统角色", description = "删除角色下指定的用户")
    public int deleteUserRole(Long userId, Long roleId) {
        super.delete(new EntityWrapper<UserRole>().eq("role_id", roleId).eq("user_id", userId));
        return 1;
    }

    @Override
    @Transactional
    @Log(module = "系统用户", description = "修改用户角色")
    public Integer changeUserRole(Long userId, List<Long> targetRoles) {
        if (!Utils.isEmpty(userId)) {
            super.delete(new EntityWrapper<UserRole>().eq("user_id", userId));

            if (!Utils.isEmpty(targetRoles)) {
                List<UserRole> userRoles = new ArrayList<>();
                for (Long roleId : targetRoles) {
                    UserRole userRole = new UserRole();
                    userRole.setRoleId(roleId);
                    userRole.setUserId(userId);
                    userRoles.add(userRole);
                }
                super.insertBatch(userRoles);
                return 1;
            }
        }
        return -1;
    }
}
