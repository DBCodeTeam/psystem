package com.um.psystem.service.mtService.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.um.psystem.entity.mtEntity.AssetsType;
import com.um.psystem.mapper.platform.mtManageMapper.AssetsTypeMapper;
import com.um.psystem.model.vo.JsonResult;
import com.um.psystem.service.mtService.IAssetsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zzj
 * @Description: 物资分类实现类
 * @Date: 2020/5/29
 */
@Service
public class AssetsTypeService implements IAssetsTypeService {

    @Autowired
    AssetsTypeMapper manageMapper;
    @Override
    public JsonResult<Integer> save(AssetsType assetsType) {
        Integer insertNum =  manageMapper.insert(assetsType);
        return JsonResult.success(insertNum);
    }

    @Override
    public JsonResult<Integer> update(AssetsType assetsType) {
        Integer updateNum =  manageMapper.updateById(assetsType);
        return JsonResult.success(updateNum);
    }

    @Override
    public JsonResult<Integer> del(Integer id) {
        Integer delNum = manageMapper.delete(new EntityWrapper<AssetsType>()
                                   .eq("type_main_id",id));
        return JsonResult.success(delNum);
    }

    @Override
    public JsonResult<AssetsType> get(Integer id) {
        AssetsType assetsType =  manageMapper.selectById(id);
        return JsonResult.success(assetsType);
    }

    @Override
    public List<AssetsType> getAssetsTypes(Map<String,Object> columnMap) {
        EntityWrapper<AssetsType> ew = new EntityWrapper<AssetsType>();
        if(StrUtil.isNotBlank(columnMap.get("typeMainName")!=null?columnMap.get("typeMainName").toString():null)){
            ew.like("type_main_name",columnMap.get("typeMainName").toString());
        }
        List<AssetsType> assetsTypeList =  manageMapper.selectList(ew);
        return assetsTypeList;
    }



}
