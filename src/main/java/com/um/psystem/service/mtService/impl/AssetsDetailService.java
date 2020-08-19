package com.um.psystem.service.mtService.impl;

import cn.hutool.core.util.StrUtil;
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
        EntityWrapper<AssetsDetail> ew = new EntityWrapper<AssetsDetail>();
        if(StrUtil.isNotBlank(columnMap.get("type_main_id")!=null?columnMap.get("type_main_id").toString():null)){
            ew.eq("type_main_id",columnMap.get("type_main_id").toString());
        }
        if(StrUtil.isNotBlank(columnMap.get("type_dtl_name")!=null?columnMap.get("type_dtl_name").toString():null)){
            ew.like("type_dtl_name",columnMap.get("type_dtl_name").toString());
        }
        List<AssetsDetail> assetsDetailList =  assetsDetailMapper.selectList(ew);
        return assetsDetailList;
    }
}
