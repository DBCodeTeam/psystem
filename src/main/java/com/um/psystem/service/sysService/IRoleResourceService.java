package com.um.psystem.service.sysService;

import com.um.psystem.entity.sysEntity.RoleResource;

import java.util.List;

/**
 * Resource Service接口
 * @create 2020-05-19 10:45:58
 */
public interface IRoleResourceService extends IBaseService<RoleResource> {
    public Integer changeRolePermission(Long roleId, List<Long> targetResources);
}
