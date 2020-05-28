package com.um.psystem.service.sysService.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.um.psystem.annotation.Log;
import com.um.psystem.entity.sysEntity.RoleResource;
import com.um.psystem.entity.sysEntity.User;
import com.um.psystem.mapper.platform.sysMapper.RoleResourceMapper;
import com.um.psystem.mapper.platform.sysMapper.UserMapper;
import com.um.psystem.service.sysService.IRoleResourceService;
import com.um.psystem.utils.AuthUtils;
import com.um.psystem.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * Resource Service实现类
 * @create 2020-05-18 10:45:58
 */
@Service
public class RoleResourceService extends BaseService<RoleResourceMapper, RoleResource> implements IRoleResourceService {
    @Autowired
    UserMapper userMapper;

    @Transactional
    @Log(module = "系统角色", description = "修改角色权限")
    public Integer changeRolePermission(Long roleId, List<Long> targetResources) {
        if (!Utils.isEmpty(roleId)) {

            super.delete(new EntityWrapper<RoleResource>().eq("role_id", roleId));

            if (!Utils.isEmpty(targetResources)) {
                List<RoleResource> roleResources = new ArrayList<>();

                for (Long resourceId : targetResources) {
                    RoleResource roleResource = new RoleResource();
                    roleResource.setRoleId(roleId);
                    roleResource.setResourceId(resourceId);
                    roleResources.add(roleResource);
                }

                super.insertBatch(roleResources);

                List<User> list_user= userMapper.findUserByRoleCode(String.valueOf(roleId));
                for(User user:list_user){
                    System.out.println(user.getName());
                    AuthUtils.refreshAuthorizing(user.getName());
                    //AuthUtils.refreshAuthorizing(user.getName());
                }
                return 1;
            }
        }
        return -1;
    }
}
