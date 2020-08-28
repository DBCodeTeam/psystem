package com.um.psystem.service.mtService;

import com.um.psystem.model.vo.JsonResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Auther: qy
 * @Date: 2020/8/22 - 11:23
 * @Description: com.um.psystem.service.mtService
 * @version: 1.0
 */
public interface IAssetsStockinService {
    List<Map<String,Object>> getStockinList(Map map);
    List<Map<String,Object>> getMtInfo(Map map);
    List<Map<String,Object>> getRecordInfo(Map map);

    @Transactional
    JsonResult<Integer> updateMtRecord(Map map);
    @Transactional
    JsonResult<Integer> addMtRecord(Map map);
    @Transactional
    JsonResult<Integer> deleMtRecord(Integer id);
}
