package com.um.psystem.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.um.psystem.exception.ApplicationException;
import com.um.psystem.exception.StatusCode;
import com.um.psystem.annotation.Log;
import com.um.psystem.entity.Resource;
import com.um.psystem.mapper.platform.sysMapper.ResourceMapper;
import com.um.psystem.model.request.ResourceRequest;
import com.um.psystem.model.response.ResourceResponse;
import com.um.psystem.service.IResourceService;
import com.um.psystem.utils.BeanCopier;
import com.um.psystem.utils.Utils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Resource Service实现类
 * @create 2020-05-18 10:45:58
 */
@Service
public class ResourceService extends BaseService<ResourceMapper, Resource> implements IResourceService {

    @Override
    @Transactional
    @Log(module = "系统资源", description = "添加资源信息")
    public ResourceResponse save(ResourceRequest request) {
        Resource existing = findByCode(request.getCode());//权限编码不能一致
        if (existing == null) {//判断是否已存在
            Resource resource = BeanCopier.copy(request, Resource.class);

            if(Utils.notEmpty(request.getParentId())){
                resource.setParentId(request.getParentId());
            }else{
                resource.setParentId(null);
            }

            super.insert(resource);

            return BeanCopier.copy(resource, ResourceResponse.class);
        } else {
            //数据已存在
            throw new ApplicationException(StatusCode.CONFLICT.getCode(), StatusCode.CONFLICT.getMessage());
        }
    }

    @Override
    @Transactional
    @Log(module = "系统资源", description = "修改资源信息")
    public ResourceResponse update(ResourceRequest request) {
        Resource existing = selectById(request.getId());
        if (existing != null) {
            //设置要更新的字段
            existing.setName(request.getName());
            existing.setUrl(request.getUrl());
            existing.setCode(request.getCode());
            existing.setIcon(request.getIcon());
            existing.setDescription(request.getDescription());
            existing.setSequence(request.getSequence());
            if(Utils.notEmpty(request.getParentId())){
                existing.setParentId(request.getParentId());
            }else{
                existing.setParentId(null);
            }
            existing.setUpdateTime(new Date());

            super.insertOrUpdate(existing);

            return BeanCopier.copy(existing, ResourceResponse.class);
        } else {
            //数据不存在
            throw new ApplicationException(StatusCode.NOT_FOUND.getCode(), StatusCode.NOT_FOUND.getMessage());
        }
    }

    @Override
    @Transactional
    @Log(module = "系统资源", description = "删除资源信息")
    public Integer del(Long id) {
        Resource existing = selectById(id);
        if (existing != null) {
            super.deleteById(id);
        } else {
            //数据不存在
            throw new ApplicationException(StatusCode.NOT_FOUND.getCode(), StatusCode.NOT_FOUND.getMessage());
        }
        return 1;
    }

    @Override
    public ResourceResponse get(Long id) {
        Resource existing = selectById(id);
        if(existing!=null){
            return BeanCopier.copy(existing, ResourceResponse.class);
        }else{
            //数据不存在
            throw new ApplicationException(StatusCode.NOT_FOUND.getCode(), StatusCode.NOT_FOUND.getMessage());
        }
    }

    @Cacheable(value ="resourceCache")//这里的名称要跟ehcache.xml中保持一致
    @Override
    public List<ResourceResponse> getResources() {
        List<Resource> resources = selectList(new EntityWrapper<Resource>());
        List<ResourceResponse> responses = BeanCopier.copy(resources, ResourceResponse.class);
        sort(responses);
        return responses;
    }

    @Override
    public List<ResourceResponse> getResources(String roleCode) {
        List<Resource> resources = baseMapper.findResourceByRoleCode(roleCode);
        List<ResourceResponse> responses = BeanCopier.copy(resources, ResourceResponse.class);
        sort(responses);
        return responses;
    }

    @Cacheable(value ="resourceCache")//这里的名称要跟ehcache.xml中保持一致
    @Override
    public List<ResourceResponse> getResources(Long parentId, String type) {
        List<Resource> resources = baseMapper.findResource(parentId, type);
        List<ResourceResponse> responses = BeanCopier.copy(resources, ResourceResponse.class);
        sort(responses);
        return responses;
    }

    @Override
    public List<ResourceResponse> getResourceByUsername(String username, String type) {
        List<Resource> resources = baseMapper.findResourceByUsernameAndType(username, type);
        List<ResourceResponse> responses = BeanCopier.copy(resources, ResourceResponse.class);
        sort(responses);
        return responses;
    }

    /**
     * 排序
     *
     * @param list
     */
    private void sort(List<ResourceResponse> list) {
        if (!Utils.isEmpty(list)) {
            Collections.sort(list, new Comparator<ResourceResponse>() {
                public int compare(ResourceResponse arg0, ResourceResponse arg1) {
                    if (arg0 != null && arg1 != null && arg0.getSequence() != null && arg1.getSequence() != null) {
                        return arg0.getSequence().compareTo(arg1.getSequence());
                    } else {
                        return 0;
                    }
                }
            });
        }
    }

    public Resource findByCode(String code) {
        return super.selectOne(new EntityWrapper<Resource>().eq("code", code));
    }
}
