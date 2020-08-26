package com.um.psystem.service.mtService;

import com.um.psystem.model.vo.JsonResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Author: zzj
 * @Description: 物资申请接口
 * @Date: 2020/6/12
 */
public interface IAssetsApplyService {

     @Transactional
     JsonResult<Integer> save(Map map);

     List<Map<String,Object>> getApplyNode(Map map);

     List<Map<String,Object>> getApplyList(Map map);

     List<Map<String,Object>> getOperator(Map map);

     JsonResult<Integer> update(Map map);

     JsonResult<Integer> del(Map map);

     @Transactional
     JsonResult<Integer> save_flow(Map map);

     @Transactional
     JsonResult<Integer> update_flow(Map map);

     JsonResult<Integer> del_flow(Map map);

     List<Map<String, Object>> getAssets(Map map);

     List<Map<String, Object>> getErpNo(Map map);
     @Transactional
     JsonResult<Integer> save_apply(Map map);
     @Transactional
     JsonResult<Integer> check_apply(Map map);

     JsonResult<Boolean> judgePermission(String apply_id,String username,String flowNode);

     JsonResult<Integer> back_apply(HttpServletRequest request, Map map);

     List<Map<String, Object>> getApplyById(String id);

     JsonResult<Integer> issue_apply(HttpServletRequest request, Map map);

     JsonResult<Integer> update_apply(Map map);

     JsonResult<Integer> del_apply(Map map);

     void exportApplyData(HttpServletResponse response, @RequestParam Map map);

}
