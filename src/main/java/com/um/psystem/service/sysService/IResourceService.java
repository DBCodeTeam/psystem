package com.um.psystem.service.sysService;

import com.um.psystem.entity.sysEntity.Resource;
import com.um.psystem.model.sysModel.request.ResourceRequest;
import com.um.psystem.model.sysModel.response.ResourceResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Resource Service接口
 * @create 2020-05-19 10:45:58
 */
public interface IResourceService extends IBaseService<Resource> {

    @Transactional
    public ResourceResponse save(ResourceRequest request);

    @Transactional
    public ResourceResponse update(ResourceRequest request);

    @Transactional
    public Integer del(Long id);

    public ResourceResponse get(Long id);

    public List<ResourceResponse> getResources();

    public List<ResourceResponse> getResources(String roleCode);

    public List<ResourceResponse> getResources(Long parentId, String type);

    public List<ResourceResponse> getResourceByUsername(String username, String type);
}
