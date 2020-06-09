package com.um.psystem.service.mtService;

import com.um.psystem.entity.mtEntity.AssetsType;
import com.um.psystem.model.vo.JsonResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: zhenjin.zheng
 * @Description: 物资分类
 * @Date: 2020/5/27
 */
public interface IAssetsTypeService {

    @Transactional
    public JsonResult<Integer> save(AssetsType assetsType);

    @Transactional
    public JsonResult<Integer> update(AssetsType assetsType);

    @Transactional
    public JsonResult<Integer> del(Integer id);

    public JsonResult<AssetsType> get(Integer id);

    public List<AssetsType> getAssetsTypes(Map<String,Object> columnMap);


}
