package com.um.psystem.service;

import com.um.psystem.service.IBaseService;
import com.um.psystem.entity.Role;
import com.um.psystem.model.request.RoleRequest;
import com.um.psystem.model.response.RoleResponse;
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
