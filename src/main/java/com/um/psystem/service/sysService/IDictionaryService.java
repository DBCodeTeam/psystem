package com.um.psystem.service.sysService;

import com.um.psystem.entity.sysEntity.Dictionary;
import com.um.psystem.model.sysModel.request.DictionaryRequest;
import com.um.psystem.model.sysModel.response.DictionaryResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Dictionary Service接口
 * @create 2020-05-13 17:45:00
 */
public interface IDictionaryService extends IBaseService<Dictionary> {

    @Transactional
    public DictionaryResponse save(DictionaryRequest request);

    @Transactional
    public DictionaryResponse update(DictionaryRequest request);

    @Transactional
    public Integer del(Long id);

    public DictionaryResponse get(Long id);

    public List<DictionaryResponse> getDictionarys(String type);

}
