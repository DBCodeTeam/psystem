package com.um.psystem.service.sysService;

import com.um.psystem.entity.sysEntity.Role;
import com.um.psystem.model.sysModel.request.RoleRequest;
import com.um.psystem.model.sysModel.response.RoleResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Role Service接口
 * @create 2020-05-19 10:45:58
 */
public interface IRoleService extends IBaseService<Role> {

    @Transactional
    public RoleResponse save(RoleRequest request);

    @Transactional
    public RoleResponse update(RoleRequest request);

    @Transactional
    public Integer del(Long id);

    public RoleResponse get(Long id);

    public List<RoleResponse> getRoles();

    public List<RoleResponse> getRoles(String username);

}
