package com.um.psystem.service.sysService;

import com.um.psystem.entity.sysEntity.DictionaryType;
import com.um.psystem.model.sysModel.request.DictionaryTypeRequest;
import com.um.psystem.model.sysModel.response.DictionaryTypeResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * DictionaryType Service接口
 * @create 2020-05-13 17:45:00
 */
public interface IDictionaryTypeService extends IBaseService<DictionaryType> {

    @Transactional
    public DictionaryTypeResponse save(DictionaryTypeRequest request);

    @Transactional
    public DictionaryTypeResponse update(DictionaryTypeRequest request);

    @Transactional
    public Integer del(Long id);

    public DictionaryTypeResponse get(Long id);

    public List<DictionaryTypeResponse> getDictionaryTypes();


}
