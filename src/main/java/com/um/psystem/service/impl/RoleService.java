package com.um.psystem.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.um.psystem.exception.ApplicationException;
import com.um.psystem.exception.StatusCode;
import com.um.psystem.annotation.Log;
import com.um.psystem.entity.Role;
import com.um.psystem.mapper.platform.sysMapper.RoleMapper;
import com.um.psystem.model.request.RoleRequest;
import com.um.psystem.model.response.RoleResponse;
import com.um.psystem.service.IRoleService;
import com.um.psystem.utils.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Role Service实现类
 * 
 * @create 2020-05-18 10:45:58
 */
@Service
public class RoleService extends BaseService<RoleMapper, Role> implements IRoleService {
    
    @Override
    @Transactional
    @Log(module = "系统角色", description = "添加角色信息")
    public RoleResponse save(RoleRequest request) {
        Role existing = findByCode(request.getCode());
        if (existing == null) {// 判断是否已存在
            Role role = BeanCopier.copy(request, Role.class);
            
            super.insert(role);
            
            return BeanCopier.copy(role, RoleResponse.class);
        }
        else {
            // 数据已存在
            throw new ApplicationException(StatusCode.CONFLICT.getCode(), StatusCode.CONFLICT.getMessage());
        }
    }
    
    @Override
    @Transactional
    @Log(module = "系统角色", description = "修改角色信息")
    public RoleResponse update(RoleRequest request) {
        Role existing = selectById(request.getId());
        if (existing != null) {
            Role temp = findByCode(request.getCode());
            if(temp!=null && !temp.getId().equals(request.getId())){
                //数据已存在
                throw new ApplicationException(StatusCode.CONFLICT.getCode(), StatusCode.CONFLICT.getMessage());
            }

            // 设置要更新的字段
            existing.setName(request.getName());
            existing.setDescription(request.getDescription());
            existing.setUpdateTime(new Date());
            
            super.insertOrUpdate(existing);
            
            return BeanCopier.copy(existing, RoleResponse.class);
        }
        else {
            // 数据不存在
            throw new ApplicationException(StatusCode.NOT_FOUND.getCode(), StatusCode.NOT_FOUND.getMessage());
        }
    }
    
    @Override
    @Transactional
    @Log(module = "系统角色", description = "删除角色信息")
    public Integer del(Long id) {
        Role existing = selectById(id);
        if (existing != null) {
            super.deleteById(id);
            return 1;
        } else {
            // 数据不存在
            throw new ApplicationException(StatusCode.NOT_FOUND.getCode(), StatusCode.NOT_FOUND.getMessage());
        }
    }
    
    @Override
    public RoleResponse get(Long id) {
        Role existing = selectById(id);
        if (existing != null) {
            return BeanCopier.copy(existing, RoleResponse.class);
        }
        else {
            // 数据不存在
            throw new ApplicationException(StatusCode.NOT_FOUND.getCode(), StatusCode.NOT_FOUND.getMessage());
        }
    }
    
    @Override
    public List<RoleResponse> getRoles() {
        List<Role> roles = selectList(new EntityWrapper<Role>());
        List<RoleResponse> responses = BeanCopier.copy(roles, RoleResponse.class);
        return responses;
    }
    
    @Override
    public List<RoleResponse> getRoles(String username) {
        List<Role> roles = baseMapper.findRoleByUsername(username);
        List<RoleResponse> responses = BeanCopier.copy(roles, RoleResponse.class);
        return responses;
    }
    
    public Role findByCode(String code) {
        return super.selectOne(new EntityWrapper<Role>().eq("code", code));
    }
}
