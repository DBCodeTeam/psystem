package com.um.psystem.service.sysService.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.um.psystem.utils.BeanCopier;

import java.util.List;

public abstract class BaseService<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {

    public Page convert(Page source, Class destinationClass) {
        List result = BeanCopier.copy(source.getRecords(), destinationClass);
        source.setRecords(result);
        return source;
    }
}
