package com.um.psystem.controller.mtManage;

import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.excel.EasyExcel;
import com.um.psystem.controller.BaseController;
import com.um.psystem.entity.ExcelHeader;
import com.um.psystem.entity.mtEntity.AssetsDetail;
import com.um.psystem.entity.mtEntity.AssetsType;
import com.um.psystem.entity.sysEntity.DeptEntity;
import com.um.psystem.model.vo.DataGrid;
import com.um.psystem.model.vo.JsonResult;
import com.um.psystem.service.mtService.IAssetsDetailService;
import com.um.psystem.service.mtService.IAssetsTypeService;
import com.um.psystem.service.sysService.IDeptAndEmpService;
import com.um.psystem.utils.EasyExcelUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @Author: zhenjin.zheng
 * @Description: 物资分类控制器
 * @Date: 2020/5/29
 */
@Controller
@RequestMapping(value = "/mtManage/assets")
public class AssetsManageController extends BaseController {

    @Autowired
    IAssetsTypeService iMtAssetsService;

    @Autowired
    IAssetsDetailService iAssetsDetailService;

    @Autowired
    IDeptAndEmpService iDeptAndEmpService;

    ////数据已存在
    //  throw new ApplicationException(StatusCode.CONFLICT.getCode(), StatusCode.CONFLICT.getMessage());
    /**
     * 查询列表
     */
    @ResponseBody
    @RequestMapping(value = "/list_main", method = RequestMethod.GET)
    public JsonResult getList() {
       return  JsonResult.success(iMtAssetsService.getAssetsTypes(null));
    }

    @RequestMapping(value="/list_MainGrid",method = RequestMethod.GET)
    @ResponseBody
    public DataGrid getMainGrid(){
      List<AssetsType> assetsTypeList = iMtAssetsService.getAssetsTypes(null);
      return buildDataGrid(assetsTypeList,assetsTypeList.size());
    }

    @RequestMapping(value="/list_DetailGrid",method = RequestMethod.POST)
    @ResponseBody
    public DataGrid getDetailGrid(@RequestParam("typeMainId")String typeMainId){
        Map<String,Object> modelMap = new HashMap();
        modelMap.put("type_main_id",typeMainId);
        List<AssetsDetail> assetsDetailList = iAssetsDetailService.getAssetsDetails(modelMap);
        return buildDataGrid(assetsDetailList,assetsDetailList.size());
    }

    @RequestMapping(value="/add_DtlGrid",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult addDtlGrid(@RequestParam Map map){
        String type_main_name = map.get("type_main_name").toString();
        Integer type_main_id = Integer.parseInt(map.get("type_main_id").toString());
        String type_dtl_name = map.get("type_dtl_name").toString();
        String type_dtl_no = map.get("type_dtl_no").toString();
        String model = map.get("model").toString();
        String sizes = map.get("sizes").toString();
        String remark = map.get("remark_dtl").toString();
        AssetsDetail assetsDetail = new AssetsDetail();
        assetsDetail.setModel(model);
        assetsDetail.setRemark(remark);
        assetsDetail.setSizes(sizes);
        assetsDetail.setTypeDtlName(type_dtl_name);
        assetsDetail.setTypeDtlNo(type_dtl_no);
        assetsDetail.setTypeMainId(type_main_id);
        assetsDetail.setTypeMainName(type_main_name);
        return iAssetsDetailService.save(assetsDetail);
    }

    @RequestMapping(value="/add_MainGrid",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult addMainGrid(@RequestParam Map map){
        String type_main_name = map.get("type_main_name").toString();
        String dept_name = map.get("dept_name").toString();
        String dept_id = map.get("dept_id").toString();
        String remark = map.get("remark").toString();
        AssetsType assetsType = new AssetsType();
        assetsType.setDeptId(dept_id);
        assetsType.setDeptName(dept_name);
        assetsType.setRemark(remark);
        assetsType.setTypeMainName(type_main_name);
        System.out.println(type_main_name+"&&&&"+dept_name);
        return iMtAssetsService.save(assetsType);
    }

    @RequestMapping(value="/update_MainGrid",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult updateMainGrid(@RequestParam Map map){
        String type_main_name = map.get("type_main_name").toString();
        Integer type_main_id = Integer.parseInt(map.get("type_main_id").toString());
        String dept_name = map.get("dept_name").toString();
        String dept_id = map.get("dept_id").toString();
        String remark = map.get("remark").toString();
        AssetsType assetsType = new AssetsType();
        assetsType.setDeptId(dept_id);
        assetsType.setDeptName(dept_name);
        assetsType.setRemark(remark);
        assetsType.setTypeMainName(type_main_name);
        assetsType.setTypeMainId(type_main_id);
        return iMtAssetsService.update(assetsType);
    }

    @RequestMapping(value="/update_DtlGrid",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult updateDtlGrid(@RequestParam Map map){
        String type_main_name = map.get("type_main_name").toString();
        Integer type_main_id = Integer.parseInt(map.get("type_main_id").toString());
        Integer type_dtl_id = Integer.parseInt(map.get("type_dtl_id").toString());
        String type_dtl_name = map.get("type_dtl_name").toString();
        String type_dtl_no = map.get("type_dtl_no").toString();
        String model = map.get("model").toString();
        String sizes = map.get("sizes").toString();
        String remark = map.get("remark_dtl").toString();
        AssetsDetail assetsDetail = new AssetsDetail();
        assetsDetail.setModel(model);
        assetsDetail.setRemark(remark);
        assetsDetail.setSizes(sizes);
        assetsDetail.setTypeDtlName(type_dtl_name);
        assetsDetail.setTypeDtlNo(type_dtl_no);
        assetsDetail.setTypeMainId(type_main_id);
        assetsDetail.setTypeMainName(type_main_name);
        assetsDetail.setTypeDtlId(type_dtl_id);
        //StringUtils.isNotBlank();
        return iAssetsDetailService.update(assetsDetail);
    }


    @RequestMapping(value="/list_dept",method = RequestMethod.GET)
    @ResponseBody
    public JsonResult getDept(){
        List<DeptEntity> getDeptList = iDeptAndEmpService.getDeptList(null);
        //lamda表达式过滤掉无效数据
        getDeptList = getDeptList.stream().filter(o ->o.getDeptManager()!=null)
                .filter(o->o.getId()!=207).filter(o->o.getIsDrop()!=1)
                .collect(Collectors.toList());
        //list存放map，map存放kv值（json），list取需要的字段
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for(DeptEntity deptEntity:getDeptList){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("dept_id",deptEntity.getId());
            map.put("dept_name",deptEntity.getDeptName());
            list.add(map);
        }
        return JsonResult.success(list);
    }

    /**
     * 查询指定分类
     */
    @RequestMapping(value = "/getType/{typeMainId}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult getType(@PathVariable Integer typeMainId) {
        return  iMtAssetsService.get(typeMainId);
    }

    /**
     * 删除指定分类
     */
    @RequestMapping(value = "/delType/{typeMainId}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult delType(@PathVariable Integer typeMainId) {
        return  iMtAssetsService.del(typeMainId);
    }

    /**
     * 查询指定物资
     */
    @RequestMapping(value = "/delDtl/{typeDtlId}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult delDtl(@PathVariable Integer typeDtlId) {
        return  iAssetsDetailService.del(typeDtlId);
    }

    @GetMapping("/easyexceltest")
    public void download(HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("测试", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        List<ExcelHeader> headerList = new ArrayList<>();
        headerList.add(new ExcelHeader("text","文字"));
        ExcelHeader dateHeader =new ExcelHeader("createTime","时间");
        dateHeader.setDataType(ExcelHeader.DATE);
        dateHeader.setFormat("yyyy-MM-dd");
        headerList.add(dateHeader);
        List<Map<String,Object>> dataList = new ArrayList<>();
        for(int i=0;i<3;i++) {
            Map<String, Object> modelMap = new HashMap<>();
            modelMap.put("text", "excel导出测试");
            modelMap.put("createTime", new Date());
            dataList.add(modelMap);
        }

        OutputStream excelOutputStream = EasyExcelUtils.exportDataToExcel(headerList,dataList);
        response.getOutputStream().write(((ByteArrayOutputStream) excelOutputStream).toByteArray());
    }
}
