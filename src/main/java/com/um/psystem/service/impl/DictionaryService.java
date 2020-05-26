package com.um.psystem.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.um.psystem.exception.ApplicationException;
import com.um.psystem.exception.StatusCode;
import com.um.psystem.annotation.Log;
import com.um.psystem.entity.Dictionary;
import com.um.psystem.mapper.platform.sysMapper.DictionaryMapper;
import com.um.psystem.model.request.DictionaryRequest;
import com.um.psystem.model.response.DictionaryResponse;
import com.um.psystem.service.IDictionaryService;
import com.um.psystem.utils.BeanCopier;
import com.um.psystem.utils.Utils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Dictionary Service实现类
 * @create 2020-05-13 17:45:00
 */
@Service
public class DictionaryService extends BaseService<DictionaryMapper, Dictionary> implements IDictionaryService {

    @Override
    @Transactional
    @Log(module = "系统字典数据", description = "添加字典数据")
    public DictionaryResponse save(DictionaryRequest request) {
        Dictionary existing = findByTypeAndCode(request.getDictType(), request.getCode());
        if (existing == null) {//判断是否已存在
            Dictionary dictionary = BeanCopier.copy(request, Dictionary.class);

            super.insert(dictionary);

            return BeanCopier.copy(dictionary, DictionaryResponse.class);
        } else {
            //数据已存在
            throw new ApplicationException(StatusCode.CONFLICT.getCode(), StatusCode.CONFLICT.getMessage());
        }
    }

    @Override
    @Transactional
    @Log(module = "系统字典数据", description = "修改字典数据")
    public DictionaryResponse update(DictionaryRequest request) {
        Dictionary existing = selectById(request.getId());
        if (existing != null) {
            Dictionary temp = findByTypeAndCode(request.getDictType(), request.getCode());
            if (temp != null && temp.getId().equals(existing.getId())) {
                //数据已存在
                throw new ApplicationException(StatusCode.CONFLICT.getCode(), StatusCode.CONFLICT.getMessage());
            }

            existing.setName(request.getName());
            existing.setDescription(request.getDescription());
            existing.setParentId(request.getParentId());
            existing.setSequence(request.getSequence());
            existing.setUpdateTime(new Date());

            super.updateById(existing);

            return BeanCopier.copy(existing, DictionaryResponse.class);
        } else {
            //数据不存在
            throw new ApplicationException(StatusCode.NOT_FOUND.getCode(), StatusCode.NOT_FOUND.getMessage());
        }
    }

    @Override
    @Transactional
    @Log(module = "系统字典数据", description = "删除字典数据")
    public Integer del(Long id) {
        Dictionary existing = selectById(id);
        if (existing != null) {
            super.deleteById(id);
            return 1;
        } else {
            //数据不存在
            throw new ApplicationException(StatusCode.NOT_FOUND.getCode(), StatusCode.NOT_FOUND.getMessage());
        }
    }

    @Override
    public DictionaryResponse get(Long id) {
        Dictionary existing = selectById(id);
        if(existing!=null){
            return BeanCopier.copy(existing, DictionaryResponse.class);
        }else{
            //数据不存在
            throw new ApplicationException(StatusCode.NOT_FOUND.getCode(), StatusCode.NOT_FOUND.getMessage());
        }
    }

    @Override
    public List<DictionaryResponse> getDictionarys(String type) {
        List<Dictionary> dictionarys = baseMapper.selectList(
                new EntityWrapper<Dictionary>().eq("dict_type", type)
        );

        List<DictionaryResponse> responses = BeanCopier.copy(dictionarys, DictionaryResponse.class);
        sort(responses);
        return responses;
    }

    /**
     * 排序
     *
     * @param list
     */
    private void sort(List<DictionaryResponse> list) {
        if (!Utils.isEmpty(list)) {
            Collections.sort(list, new Comparator<DictionaryResponse>() {
                public int compare(DictionaryResponse arg0, DictionaryResponse arg1) {
                    if (arg0 != null && arg1 != null && arg0.getSequence() != null && arg1.getSequence() != null) {
                        return arg0.getSequence().compareTo(arg1.getSequence());
                    } else {
                        return 0;
                    }
                }
            });
        }
    }

    public Dictionary findByTypeAndCode(String type, String code) {
        Dictionary dictionary = new Dictionary();
        dictionary.setDictType(type);
        dictionary.setCode(code);
        return super.selectOne(new EntityWrapper<Dictionary>(dictionary));
    }
}
