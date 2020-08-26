package com.um.psystem.service.mtService.impl;

import cn.hutool.core.util.StrUtil;
import com.um.psystem.common.Const;
import com.um.psystem.entity.ExcelHeader;
import com.um.psystem.enums.ResponseEnum;
import com.um.psystem.mapper.platform.PublicMapper;
import com.um.psystem.model.sysModel.response.UserResponse;
import com.um.psystem.model.vo.JsonResult;
import com.um.psystem.service.mtService.IAssetsApplyService;
import com.um.psystem.utils.EasyExcelUtils;
import com.um.psystem.utils.RTX;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * @Author: zzj
 * @Description: 物资申请实现类
 * @Date: 2020/6/12
 */
@Service
public class AssetsApplyService implements IAssetsApplyService {

    @Autowired
    PublicMapper publicMapper;
    @Override
    public JsonResult<Integer> save(Map map) {
        Map paramsMap = new HashMap();
        String dept_id = map.get("dept_id").toString();
        String dept_name = map.get("dept_name").toString();
        String operator_node = map.get("operator_node").toString();
        String operator_info =map.get("operator_info").toString();
        String sequence = map.get("sequence").toString();
        String user_name = map.get("user_name").toString();
        String sql = " insert into ws_eng_assets_operator_config(dept_id,dept_name,operator_node,operator_info,"
                   + " sequence,user_name) "
                   + " select #{dept_id},#{dept_name},#{operator_node},#{operator_info},#{sequence},#{user_name}";
        paramsMap.put("isqlStr",sql);
        paramsMap.put("dept_id",dept_id);
        paramsMap.put("dept_name",dept_name);
        paramsMap.put("operator_node",operator_node);
        paramsMap.put("operator_info",operator_info);
        paramsMap.put("sequence",sequence);
        paramsMap.put("user_name",user_name);

        return JsonResult.success(publicMapper.saveItems(paramsMap));
    }

    public JsonResult<Integer> update(Map map) {
        Map paramsMap = new HashMap();
        String dept_id = map.get("dept_id").toString();
        String dept_name = map.get("dept_name").toString();
        String operator_node = map.get("operator_node").toString();
        String operator_info =map.get("operator_info").toString();
        String sequence = map.get("sequence").toString();
        String user_name = map.get("user_name").toString();
        String id = map.get("id").toString();
        String sql = " update ws_eng_assets_operator_config "
                   + " set dept_id=#{dept_id},dept_name=#{dept_name},operator_node=#{operator_node},"
                   + " operator_info=#{operator_info},sequence=#{sequence},user_name=#{user_name} "
                   + " where id=#{id}";
        paramsMap.put("usqlStr",sql);
        paramsMap.put("dept_id",dept_id);
        paramsMap.put("dept_name",dept_name);
        paramsMap.put("operator_node",operator_node);
        paramsMap.put("operator_info",operator_info);
        paramsMap.put("sequence",sequence);
        paramsMap.put("user_name",user_name);
        paramsMap.put("id",id);

        return JsonResult.success(publicMapper.updateItems(paramsMap));
    }

    @Override
    public JsonResult<Integer> del(Map map) {
        Integer id = Integer.parseInt(map.get("id").toString());
        Map paramsMap =new HashMap();
        String sql = " delete from ws_eng_assets_operator_config where id=#{id}";
        paramsMap.put("dsqlStr",sql);
        paramsMap.put("id",id);
        return JsonResult.success(publicMapper.delItems(paramsMap));
    }

    public JsonResult<Integer> save_apply(Map map){
        Map paramsMap = new HashMap();
        String erp_no="";
        String apply_dept="";
        String apply_man="";
        String out_type="";
        String apply_num="";
        String return_plandate="";
        String assets_id="";
        String node_seq="";
        String turn_node_seq="";
        String node_name="";
        String flow_id="";
        String operator="";
        String demand_time="";
        String stock_num="";
        String apply_reason="";
        if(StrUtil.isNotBlank(map.get("erpInfo")!=null?map.get("erpInfo").toString():null)){
            erp_no = map.get("erpInfo").toString();
        }
        if(StrUtil.isNotBlank(map.get("conDept")!=null?map.get("conDept").toString():null)){
            apply_dept = map.get("conDept").toString();
        }
        if(StrUtil.isNotBlank(map.get("apply_man")!=null?map.get("apply_man").toString():null)){
            apply_man = map.get("apply_man").toString();
        }
        if(StrUtil.isNotBlank(map.get("out_type")!=null?map.get("out_type").toString():null)){
            out_type = map.get("out_type").toString();
        }
        if(StrUtil.isNotBlank(map.get("apply_num")!=null?map.get("apply_num").toString():null)){
            apply_num = map.get("apply_num").toString();
        }
        if(StrUtil.isNotBlank(map.get("return_plandate")!=null?map.get("return_plandate").toString():null)){
            return_plandate = map.get("return_plandate").toString();
        }
        if(StrUtil.isNotBlank(map.get("assets_id")!=null?map.get("assets_id").toString():null)){
            assets_id = map.get("assets_id").toString();
        }
        if(StrUtil.isNotBlank(map.get("stock_num")!=null?map.get("stock_num").toString():null)){
            stock_num = map.get("stock_num").toString();
        }
        if(StrUtil.isNotBlank(map.get("demand_time")!=null?map.get("demand_time").toString():null)){
            demand_time = map.get("demand_time").toString();
        }
        if(StrUtil.isNotBlank(map.get("apply_reason")!=null?map.get("apply_reason").toString():null)){
            apply_reason = map.get("apply_reason").toString();
        }

        String sql = " select id,node_seq,turn_node_seq,node_name from ws_eng_assets_apply_flow "
                   + " where model_name=#{model_name} order by id asc limit 1 ";
        paramsMap.put("model_name","工程物资申请");
        paramsMap.put("sqlStr",sql);
        List<LinkedHashMap<String,Object>> nodeList= publicMapper.getPublicItems(paramsMap);
        if(nodeList.size()>0){
            flow_id=nodeList.get(0).get("id").toString();
            node_seq= nodeList.get(0).get("node_seq").toString();
            turn_node_seq = nodeList.get(0).get("turn_node_seq").toString();
            node_name = nodeList.get(0).get("node_name").toString();
        }
        paramsMap.clear();
        sql = " select a.user_name,a.sequence from ws_eng_assets_operator_config a "
            + " where a.operator_node=#{flow_id} "
            + " order by a.sequence asc limit 1 ";
        paramsMap.put("sqlStr",sql);
        paramsMap.put("flow_id",flow_id);
        List<LinkedHashMap<String,Object>> operatorList= publicMapper.getPublicItems(paramsMap);
        if(operatorList.size()>0){
            operator = operatorList.get(0).get("user_name").toString();
        }
        paramsMap.clear();

        apply_man = SecurityUtils.getSubject().getPrincipal().toString();
        sql ="select a.dept_name from ws_department a "
            + "left join( "
            + "select b.dept_name,dept_group_id from ws_employee a "
            + "join ws_department b "
            + "on a.depart_no = b.dept_no "
            + "where a.emp_name=#{emp_name} and a.is_work='1')b "
            + "on FIND_IN_SET(a.dept_id,b.dept_group_id) "
            + "where b.dept_group_id is not null and a.dept_grade='2'";
        paramsMap.put("sqlStr",sql);
        paramsMap.put("emp_name",apply_man);
        List<LinkedHashMap<String,Object>> dept_list= publicMapper.getPublicItems(paramsMap);
        if(dept_list.size()>0){
            apply_dept = dept_list.get(0).get("dept_name").toString();
        }
        paramsMap.clear();
        sql="  insert into ws_eng_assets_apply_info(erp_no,apply_dept,apply_man,out_type,"
                  + " apply_num,return_plandate,apply_time,state,currentuser,operator_record,"
                  + "assets_id,node_seq,turn_node_seq,demand_time,stock_num,apply_reason) "
                  + " select #{erp_no},#{apply_dept},#{apply_man},#{out_type},#{apply_num},#{return_plandate},now(),"
                  + " #{state},#{currentuser},#{operator_record},#{assets_id},#{node_seq},#{turn_node_seq},#{demand_time},#{stock_num},#{apply_reason} ";
        paramsMap.put("isqlStr",sql);
        paramsMap.put("erp_no",erp_no);
        paramsMap.put("apply_dept",apply_dept);
        paramsMap.put("apply_man",apply_man);
        paramsMap.put("out_type",out_type);
        paramsMap.put("apply_num",apply_num);
        paramsMap.put("return_plandate",return_plandate);
        paramsMap.put("state",node_name);
        paramsMap.put("currentuser",operator);
        paramsMap.put("operator_record","申请("+apply_man+")");
        paramsMap.put("assets_id",assets_id);
        paramsMap.put("node_seq",node_seq);
        paramsMap.put("turn_node_seq",turn_node_seq);
        paramsMap.put("demand_time",demand_time);
        paramsMap.put("stock_num",stock_num);
        paramsMap.put("apply_reason",apply_reason);
        int saveNum = publicMapper.saveItems(paramsMap);
        if(saveNum>0){
            RTX.sendMsg("物资申请消息","物资申请审核",operator);
        }
        return JsonResult.success(saveNum);
    }

    public JsonResult<Integer> update_apply(Map map){
        Map paramsMap = new HashMap();
        String out_type="";
        String apply_num="";
        String return_plandate="";
        String operator="";
        String demand_time="";
        String stock_num="";
        String apply_id="";
        String apply_reason="";
        String flow_id="";
        if(StrUtil.isNotBlank(map.get("out_type")!=null?map.get("out_type").toString():null)){
            out_type = map.get("out_type").toString();
        }
        if(StrUtil.isNotBlank(map.get("apply_num")!=null?map.get("apply_num").toString():null)){
            apply_num = map.get("apply_num").toString();
        }
        if(StrUtil.isNotBlank(map.get("return_plandate")!=null?map.get("return_plandate").toString():null)){
            return_plandate = map.get("return_plandate").toString();
        }
        if(StrUtil.isNotBlank(map.get("stock_num")!=null?map.get("stock_num").toString():null)){
            stock_num = map.get("stock_num").toString();
        }
        if(StrUtil.isNotBlank(map.get("demand_time")!=null?map.get("demand_time").toString():null)){
            demand_time = map.get("demand_time").toString();
        }

        if(StrUtil.isNotBlank(map.get("apply_id")!=null?map.get("apply_id").toString():null)){
            apply_id = map.get("apply_id").toString();
        }
        if(StrUtil.isNotBlank(map.get("apply_reason")!=null?map.get("apply_reason").toString():null)){
            apply_reason = map.get("apply_reason").toString();
        }

        String sql = " select id,node_seq,turn_node_seq,node_name from ws_eng_assets_apply_flow "
                + " where model_name=#{model_name} order by id asc limit 1 ";
        paramsMap.put("model_name","工程物资申请");
        paramsMap.put("sqlStr",sql);
        List<LinkedHashMap<String,Object>> nodeList= publicMapper.getPublicItems(paramsMap);
        if(nodeList.size()>0){
            flow_id=nodeList.get(0).get("id").toString();
        }
        paramsMap.clear();
        sql = " select a.user_name,a.sequence from ws_eng_assets_operator_config a "
                + " where a.operator_node=#{flow_id} "
                + " order by a.sequence asc limit 1 ";
        paramsMap.put("sqlStr",sql);
        paramsMap.put("flow_id",flow_id);
        List<LinkedHashMap<String,Object>> operatorList= publicMapper.getPublicItems(paramsMap);
        if(operatorList.size()>0){
            operator = operatorList.get(0).get("user_name").toString();
        }
        paramsMap.clear();
        String apply_man = SecurityUtils.getSubject().getPrincipal().toString();
        sql=" update ws_eng_assets_apply_info"
            +" set out_type=#{out_type},"
            +" apply_num=#{apply_num},return_plandate=#{return_plandate},"
            +" demand_time=#{demand_time},stock_num=#{stock_num},apply_reason=#{apply_reason},state=#{state},"
            + "operator_record=concat(operator_record,'==>申请(',#{apply_man},')')"
            +" where apply_id=#{apply_id}";
        paramsMap.put("usqlStr",sql);
        paramsMap.put("out_type",out_type);
        paramsMap.put("apply_num",apply_num);
        paramsMap.put("return_plandate",return_plandate);
        paramsMap.put("demand_time",demand_time);
        paramsMap.put("stock_num",stock_num);
        paramsMap.put("apply_id",apply_id);
        paramsMap.put("apply_reason",apply_reason);
        paramsMap.put("apply_man",apply_man);
        paramsMap.put("state","审核");
        paramsMap.put("node_seq","1");
        paramsMap.put("turn_node_seq","2");
        RTX.sendMsg("物资申请消息","物资申请审核",operator);

        return JsonResult.success(publicMapper.updateItems(paramsMap));
    }

    @Override
    public JsonResult<Integer> del_apply(Map map) {
        Integer apply_id = Integer.parseInt(map.get("apply_id").toString());
        Map paramsMap =new HashMap();
        String sql = " delete from ws_eng_assets_apply_info where apply_id=#{apply_id}";
        paramsMap.put("dsqlStr",sql);
        paramsMap.put("id",apply_id);
        return JsonResult.success(publicMapper.delItems(paramsMap));
    }

    @Override
    public void exportApplyData(HttpServletResponse response, Map map) {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码
        String fileName = null;
        try {
            fileName = URLEncoder.encode("物资申请数据", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
        List<ExcelHeader> headerList = new ArrayList<>();
        headerList.add(new ExcelHeader("erp_no","ERP编号"));
        headerList.add(new ExcelHeader("apply_dept","申请部门"));
        headerList.add(new ExcelHeader("apply_man","申请人"));
        ExcelHeader dateHeader =new ExcelHeader("return_plandate","预计归还");
        dateHeader.setDataType(ExcelHeader.DATE);
        dateHeader.setFormat("yyyy-MM-dd");
        headerList.add(dateHeader);
        ExcelHeader dateApplyHeader =new ExcelHeader("apply_time","申请时间");
        dateApplyHeader.setDataType(ExcelHeader.DATE);
        dateApplyHeader.setFormat("yyyy-MM-dd");
        headerList.add(dateApplyHeader);
        headerList.add(new ExcelHeader("state","状态"));
        headerList.add(new ExcelHeader("currentuser","当前责任人"));
        headerList.add(new ExcelHeader("dept_name","物资部门"));
        headerList.add(new ExcelHeader("type_main_name","物资类别"));
        headerList.add(new ExcelHeader("type_dtl_name","物资名称"));
        headerList.add(new ExcelHeader("type_dtl_no","物资编号"));
        headerList.add(new ExcelHeader("model","型号"));
        headerList.add(new ExcelHeader("sizes","规格"));
        headerList.add(new ExcelHeader("out_type","出库类型"));
        headerList.add(new ExcelHeader("apply_num","申请数量"));
        headerList.add(new ExcelHeader("backto_user","打回人"));
        ExcelHeader dateBackHeader =new ExcelHeader("backto_time","打回时间");
        dateBackHeader.setDataType(ExcelHeader.DATE);
        dateBackHeader.setFormat("yyyy-MM-dd HH:mm:ss");
        headerList.add(dateBackHeader);
        headerList.add(new ExcelHeader("apply_reason","申请原因"));
        headerList.add(new ExcelHeader("stock_num","库存数量"));
        ExcelHeader dateNeedHeader =new ExcelHeader("demand_time","需求日期");
        dateNeedHeader.setDataType(ExcelHeader.DATE);
        dateNeedHeader.setFormat("yyyy-MM-dd HH:mm:ss");
        headerList.add(dateNeedHeader);

        List<Map<String,Object>> dataList = getApplyList(map);
        OutputStream excelOutputStream = EasyExcelUtils.exportDataToExcel(headerList,dataList);
        try {
            response.getOutputStream().write(((ByteArrayOutputStream) excelOutputStream).toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JsonResult<Integer> check_apply(Map map){
        Map paramsMap = new HashMap();
        String apply_id="";
        String node_seq="";
        String operator="";
        String flow_id="";
        String turn_node_seq="";
        String state="";
        String sqltmp="";
        String nodeOperator="";
        String sendInfo="";
        if(StrUtil.isNotBlank(map.get("apply_id")!=null?map.get("apply_id").toString():null)){
            apply_id = map.get("apply_id").toString();
        }
        if(StrUtil.isNotBlank(map.get("node_seq")!=null?map.get("node_seq").toString():null)){
            node_seq = map.get("node_seq").toString();
        }

        if(StrUtil.isNotBlank(map.get("currentuser")!=null?map.get("currentuser").toString():null)){
            operator = map.get("currentuser").toString();
        }

        String sql = " select id,node_seq,turn_node_seq,node_name from ws_eng_assets_apply_flow "
                + " where model_name=#{model_name} and node_seq=#{node_seq} order by id asc limit 1 ";
        paramsMap.put("model_name","工程物资申请");
        paramsMap.put("node_seq",node_seq);
        paramsMap.put("sqlStr",sql);
        List<LinkedHashMap<String,Object>> nodeList= publicMapper.getPublicItems(paramsMap);
        if(nodeList.size()>0){
            flow_id =  nodeList.get(0).get("id").toString();
            turn_node_seq = nodeList.get(0).get("turn_node_seq").toString();
        }
        sql = "select b.user_name,b.sequence from ws_eng_assets_operator_config a "
            + "left join ws_eng_assets_operator_config b on a.operator_node = b.operator_node "
            + "and b.sequence>a.sequence "
            + "where a.operator_node=#{flow_id} and a.user_name=#{operator} "
            + "order by b.sequence asc limit 1 ";
        /*sql = " select a.user_name,a.sequence from ws_eng_assets_operator_config a "
            + " where a.operator_node=#{flow_id} "
            + " order by a.sequence asc limit 1 ";*/
        paramsMap.put("sqlStr",sql);
        paramsMap.put("operator",operator);
        paramsMap.put("flow_id",flow_id);
        List<LinkedHashMap<String,Object>> operatorList= publicMapper.getPublicItems(paramsMap);
        if(operatorList.size()>0){
            nodeOperator = operatorList.get(0).get("user_name").toString();
            state = "审核";
            sendInfo="请到工作站审核物资申请单";
        }else{
           sql = " select user_name from ws_eng_assets_operator_config "
               + " where operator_node=#{turn_node_seq}";
           paramsMap.put("sqlStr",sql);
           paramsMap.put("turn_node_seq",turn_node_seq);
            List<LinkedHashMap<String,Object>> nextNodeOperList= publicMapper.getPublicItems(paramsMap);
            if(nextNodeOperList.size()>0){
                nodeOperator = nextNodeOperList.get(0).get("user_name").toString();
                state = "发放";
                sendInfo="请为审核过的订单发放物资";
            }
        }
        sqltmp = "operator_record=concat(operator_record,'==>审核(',#{operator},')')";
        sql = " update ws_eng_assets_apply_info set currentuser=#{nodeoperator},"
            + " node_seq=#{node_seq},turn_node_seq=#{turn_node_seq},state=#{state}, "
            + sqltmp +" where apply_id=#{apply_id}";
        paramsMap.put("usqlStr",sql);
        paramsMap.put("apply_id",apply_id);
        paramsMap.put("operator",operator);
        paramsMap.put("nodeoperator",nodeOperator);
        paramsMap.put("node_seq",node_seq);
        paramsMap.put("turn_node_seq",turn_node_seq);
        paramsMap.put("state",state);
        int updateNum = publicMapper.updateItems(paramsMap);
        if(updateNum>0){
            RTX.sendMsg("物资申请消息",sendInfo,nodeOperator);
            return JsonResult.success(updateNum);
        }else{
            return JsonResult.error(ResponseEnum.SERVER_ERROR);
        }
    }

    public JsonResult<Integer> back_apply(HttpServletRequest request, Map map){
        Map paramsMap = new HashMap();
        String apply_id="";
        String sqltmp="";
        String sendInfo="";
        String apply_man="";
        String username="";
        if(StrUtil.isNotBlank(map.get("apply_id")!=null?map.get("apply_id").toString():null)){
            apply_id = map.get("apply_id").toString();
        }
        if(StrUtil.isNotBlank(map.get("apply_man")!=null?map.get("apply_man").toString():null)){
            apply_man = map.get("apply_man").toString();
        }

        //获取当前操作用户
        if (request.getSession().getAttribute(Const.SESSION_USER) != null) {
            UserResponse user = (UserResponse) request.getSession().getAttribute(Const.SESSION_USER);
            username = user != null ? user.getUsername() : null;
        }
        sqltmp = "operator_record=concat(operator_record,'==>打回(',#{operator},')')";
        String sql = " update ws_eng_assets_apply_info set currentuser=#{nodeoperator},"
                + " node_seq='-1',turn_node_seq='1',state=#{state},backto_user=#{backto_user},backto_time=now() "
                + sqltmp +" where apply_id=#{apply_id}";
        paramsMap.put("usqlStr",sql);
        paramsMap.put("apply_id",apply_id);
        paramsMap.put("operator",username);
        paramsMap.put("backto_user",username);
        paramsMap.put("nodeoperator",apply_man);
        paramsMap.put("state","打回");
        int updateNum = publicMapper.updateItems(paramsMap);
        if(updateNum>0){
            sendInfo="您的物资申请被打回";
            RTX.sendMsg("物资申请消息",sendInfo,apply_man);
            return JsonResult.success(updateNum);
        }else{
            return JsonResult.error(ResponseEnum.SERVER_ERROR);
        }
    }

    public JsonResult<Integer> issue_apply(HttpServletRequest request, Map map){
        Map paramsMap = new HashMap();
        String apply_id="";
        String sqltmp="";
        String sendInfo="";
        String apply_man="";
        String username="";
        if(StrUtil.isNotBlank(map.get("apply_id")!=null?map.get("apply_id").toString():null)){
            apply_id = map.get("apply_id").toString();
        }
        if(StrUtil.isNotBlank(map.get("apply_man")!=null?map.get("apply_man").toString():null)){
            apply_man = map.get("apply_man").toString();
        }
        //获取当前操作用户
        if (request.getSession().getAttribute(Const.SESSION_USER) != null) {
            UserResponse user = (UserResponse) request.getSession().getAttribute(Const.SESSION_USER);
            username = user != null ? user.getUsername() : null;
        }
        sqltmp = "operator_record=concat(operator_record,'==>发放(',#{operator},')')";
        String sql = " update ws_eng_assets_apply_info set currentuser=#{nodeoperator},"
                + " node_seq='2',turn_node_seq='999',state=#{state}, "
                + sqltmp +" where apply_id=#{apply_id}";
        paramsMap.put("usqlStr",sql);
        paramsMap.put("apply_id",apply_id);
        paramsMap.put("operator",username);
        paramsMap.put("nodeoperator",apply_man);
        paramsMap.put("state","已发放");
        int updateNum = publicMapper.updateItems(paramsMap);
        if(updateNum>0){
            sendInfo="您的物资申请已发放";
            RTX.sendMsg("物资申请消息",sendInfo,apply_man);
            return JsonResult.success(updateNum);
        }else{
            return JsonResult.error(ResponseEnum.SERVER_ERROR);
        }
    }



    @Override
    public JsonResult<Integer> save_flow(Map map) {
        Map paramsMap = new HashMap();
        String model_name = map.get("model_name").toString();
        String node_name = map.get("node_name").toString();
        String node_seq = map.get("node_seq").toString();
        String turn_node_seq =map.get("turn_node_seq").toString();
        String serial_flag = map.get("serial_flag").toString();
        String sql = " insert into ws_eng_assets_apply_flow(model_name,node_seq,node_name,turn_node_seq,"
                + " serial_flag) "
                + " select #{model_name},#{node_seq},#{node_name},#{turn_node_seq},#{serial_flag}";
        paramsMap.put("isqlStr",sql);
        paramsMap.put("model_name",model_name);
        paramsMap.put("node_seq",node_seq);
        paramsMap.put("node_name",node_name);
        paramsMap.put("turn_node_seq",turn_node_seq);
        paramsMap.put("serial_flag",serial_flag);

        return JsonResult.success(publicMapper.saveItems(paramsMap));
    }

    @Override
    public JsonResult<Integer> update_flow(Map map) {
        Map paramsMap = new HashMap();
        String model_name = map.get("model_name").toString();
        String node_name = map.get("node_name").toString();
        String node_seq = map.get("node_seq").toString();
        String turn_node_seq =map.get("turn_node_seq").toString();
        String serial_flag = map.get("serial_flag").toString();
        String id = map.get("id").toString();
        String sql = " update ws_eng_assets_apply_flow "
                + " set model_name=#{model_name},node_name=#{node_name},node_seq=#{node_seq},"
                + " turn_node_seq=#{turn_node_seq},serial_flag=#{serial_flag} "
                + " where id=#{id}";
        paramsMap.put("usqlStr",sql);
        paramsMap.put("model_name",model_name);
        paramsMap.put("node_name",node_name);
        paramsMap.put("turn_node_seq",turn_node_seq);
        paramsMap.put("serial_flag",serial_flag);
        paramsMap.put("node_seq",node_seq);
        paramsMap.put("id",id);

        return JsonResult.success(publicMapper.updateItems(paramsMap));
    }

    @Override
    public JsonResult<Integer> del_flow(Map map) {
        Integer id = Integer.parseInt(map.get("id").toString());
        Map paramsMap =new HashMap();
        String sql = " delete from ws_eng_assets_apply_flow where id=#{id}";
        paramsMap.put("dsqlStr",sql);
        paramsMap.put("id",id);
        return JsonResult.success(publicMapper.delItems(paramsMap));
    }

    @Override
    public List<Map<String, Object>> getApplyNode(Map map) {
        String sql= " select * from ws_eng_assets_apply_flow where 1=1";
        Map paramsMap = new HashMap();
        String model_name="";
        if(StrUtil.isNotBlank(map.get("model_name")!=null?map.get("model_name").toString():null)){
            sql+=" and model_name=#{model_name}";
            model_name =map.get("model_name").toString();
            paramsMap.put("model_name",model_name);
        }
        paramsMap.put("sqlStr",sql);
        return publicMapper.getPublicItems(paramsMap);
    }

    @Override
    public List<Map<String, Object>> getApplyList(Map map) {
        String erp_no="";
        String sqltmp="";
        String apply_man="";
        Map paramsMap = new HashMap();
        Integer page = Integer.parseInt((map.get("page")!=null?map.get("page").toString():"0"));
        Integer rows = Integer.parseInt((map.get("rows")!=null?map.get("rows").toString():"0"));

        if(StrUtil.isNotBlank(map.get("erp_no")!=null?map.get("erp_no").toString():null)){
            sqltmp+=" and a.erp_no=#{erp_no}";
            erp_no =map.get("erp_no").toString();
            paramsMap.put("erp_no",erp_no);
        }
        if(StrUtil.isNotBlank(map.get("apply_man")!=null?map.get("apply_man").toString():null)){
            sqltmp+=" and a.apply_man=#{apply_man}";
            apply_man =map.get("apply_man").toString();
            paramsMap.put("apply_man",apply_man);
        }
        String  sql = " select count(*) as totals from ws_eng_assets_apply_info a where 1=1 "+sqltmp;
        paramsMap.put("sqlStr",sql);
        int totals=0;
        List<Map<String,Object>> list_count = publicMapper.getPublicItems(paramsMap);
        if(list_count.size()>0){
            totals = Integer.parseInt(list_count.get(0).get("totals").toString());
        }
        sql = " select a.erp_no,a.apply_dept,a.apply_man,a.return_plandate,a.apply_time,a.state,a.currentuser, "
                    + "a.state,a.currentuser,b.dept_name,b.type_main_name,c.type_dtl_name,c.type_dtl_no,c.model,c.sizes,a.out_type, "
                    + "a.apply_num,a.backto_user,a.backto_time,a.apply_reason,a.stock_num,a.demand_time,a.operator_record,"
                    + "a.besureout_user,a.besureout_time,a.refusereason,a.refusereason_user,a.refusereason_time, "
                    + "a.node_seq,a.turn_node_seq,a.assets_id,c.use_to,c.k3_code,c.brand_name,a.apply_id,"
                    + "d.conErpNo,d.conProductNum,d.conDept,d.sdOrderAmount,d.cusSentToName,"+totals+ " as totals from ws_eng_assets_apply_info a "
                    + "left join ws_eng_assets_type_dtl c "
                    + "on a.assets_id=c.type_dtl_id "
                    + "left join "
                    + "ws_eng_assets_type_main b on b.type_main_id=c.type_main_id "
                    + "left join uws_produceorder_confirmation_query d "
                    + "on a.erp_no = d.conErpNo "
                    + "where 1=1 "+sqltmp;
        if(page!=0 && rows!=0){
            sql+= " limit #{startIndex},#{rows} ";
            paramsMap.put("startIndex",(page-1)*rows);
            paramsMap.put("rows",rows);
        }
        paramsMap.put("sqlStr",sql);
        return publicMapper.getPublicItems(paramsMap);
    }

    @Override
    public List<Map<String, Object>> getApplyById(String id) {
        Map paramsMap = new HashMap();
        String sql = " select a.*,b.*,c.conErpNo,c.conProductNum,c.conDept,c.sdOrderAmount,c.cusSentToName  from ws_eng_assets_apply_info a "
                + "left join ws_eng_assets_type_dtl b "
                + "on a.assets_id=b.type_dtl_id "
                + "left join uws_produceorder_confirmation_query c "
                + "on a.erp_no = c.conErpNo "
                + "where a.apply_id=#{apply_id}";
        paramsMap.put("sqlStr",sql);
        paramsMap.put("apply_id",id);
        return publicMapper.getPublicItems(paramsMap);
    }

    @Override
    public List<Map<String, Object>> getOperator(Map map) {
        String operator_node ="";
        Map paramsMap = new HashMap();
        String sql = " select * from ws_eng_assets_operator_config where 1=1 ";
        if(StrUtil.isNotBlank(map.get("id")!=null?map.get("id").toString():null)){
            sql+=" and operator_node=#{operator_node} order by sequence asc ";
            operator_node =map.get("id").toString();
            paramsMap.put("operator_node",operator_node);
        }
        paramsMap.put("sqlStr",sql);
        return publicMapper.getPublicItems(paramsMap);
    }

    @Override
    public List<Map<String, Object>> getErpNo(Map map) {
        Map paramsMap = new HashMap();
        String conErpNo="";
        String sql = " select conErpNo,cusSentToName,conProductNum,sdOrderAmount,conDept from uws_produceorder_confirmation_query where conErpNo is not null ";
        if(map!=null){
            if(StrUtil.isNotBlank(map.get("conErpNo")!=null?map.get("conErpNo").toString():null)){
                sql+=" and conErpNo=#{conErpNo} ";
                conErpNo =map.get("conErpNo").toString();
                paramsMap.put("conErpNo",conErpNo);
            }
        }
        paramsMap.put("sqlStr",sql);
        return publicMapper.getPublicItems(paramsMap);
    }

    public List<Map<String, Object>> getAssets(Map map) {
        String sql = " select * from ws_eng_assets_type_dtl a "
                   + " left join ws_eng_assets_type_main b on a.type_main_id = b.type_main_id "
                   + " where 1=1 ";
        String type_main_id = "";
        Map paramsMap = new HashMap();
        if(StrUtil.isNotBlank(map.get("type_main_id")!=null?map.get("type_main_id").toString():null)){
            sql+=" and type_main_id=#{type_main_id} ";
            type_main_id =map.get("type_main_id").toString();
            paramsMap.put("type_main_id",type_main_id);
        }
        paramsMap.put("sqlStr",sql);
        return publicMapper.getPublicItems(paramsMap);
    }

    public JsonResult<Boolean> judgePermission(String apply_id,String username,String flowNode){
        String sql = " select a.* from ws_eng_assets_operator_config a "
                   + " join ws_eng_assets_apply_info b on a.user_name = b.currentuser "
                   +"  where a.operator_info=#{flowNode} and a.user_name=#{user_name} and apply_id=#{apply_id}";
        Map paramsMap = new HashMap();
        paramsMap.put("user_name",username);
        paramsMap.put("flowNode",flowNode);
        paramsMap.put("apply_id",apply_id);
        paramsMap.put("sqlStr",sql);
        List<Map<String, Object>> list_name  = publicMapper.getPublicItems(paramsMap);
        if(list_name.size()==0){
            return JsonResult.success(false);
        }
        return JsonResult.success(true);
    }
}
