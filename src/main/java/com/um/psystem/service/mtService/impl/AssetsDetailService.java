package com.um.psystem.service.mtService.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.um.psystem.entity.mtEntity.AssetsDetail;
import com.um.psystem.entity.mtEntity.AssetsType;
import com.um.psystem.mapper.platform.mtManageMapper.AssetsDetailMapper;
import com.um.psystem.model.vo.JsonResult;
import com.um.psystem.service.mtService.IAssetsDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: zzj
 * @Description: 物资明细实现类
 * @Date: 2020/5/29
 */
@Service
public class AssetsDetailService implements IAssetsDetailService {

    @Autowired
    AssetsDetailMapper assetsDetailMapper;

    @Override
    public JsonResult<Integer> save(AssetsDetail assetsDtail) {
        return JsonResult.success(assetsDetailMapper.insert(assetsDtail));
    }

    @Override
    public JsonResult<Integer> update(AssetsDetail assetsDtail) {
        return JsonResult.success(assetsDetailMapper.updateById(assetsDtail));
    }

    @Override
    public JsonResult<Integer> del(Integer id) {
        Integer delNum = assetsDetailMapper.delete(new EntityWrapper<AssetsDetail>()
                .eq("type_dtl_id",id));
        return JsonResult.success(delNum);
    }

    @Override
    public JsonResult<AssetsDetail> get(Integer id) {
        AssetsDetail assetsDtail =  assetsDetailMapper.selectById(id);
        return JsonResult.success(assetsDtail);
    }

    @Override
    public List<AssetsDetail> getAssetsDetails(Map<String, Object> columnMap) {
        List<AssetsDetail> assetsDetailList =  assetsDetailMapper.selectByMap(columnMap);
        return assetsDetailList;
    }
}
