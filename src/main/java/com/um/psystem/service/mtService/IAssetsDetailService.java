package com.um.psystem.service.mtService;

import com.um.psystem.entity.mtEntity.AssetsDetail;
import com.um.psystem.entity.mtEntity.AssetsType;
import com.um.psystem.model.vo.JsonResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: zhenjin.zheng
 * @Description: 物资明细接口
 * @Date: 2020/5/29
 */
public interface IAssetsDetailService {

    @Transactional
    public JsonResult<Integer> save(AssetsDetail assetsDtail);

    @Transactional
    public JsonResult<Integer> update(AssetsDetail assetsDtail);

    @Transactional
    public JsonResult<Integer> del(Integer id);

    public JsonResult<AssetsDetail> get(Integer id);

    public List<AssetsDetail> getAssetsDetails(Map<String,Object> columnMap);

}
