package com.um.psystem.service.mtService.impl;

import cn.hutool.core.util.StrUtil;
import com.um.psystem.enums.ResponseEnum;
import com.um.psystem.mapper.platform.PublicMapper;
import com.um.psystem.model.vo.JsonResult;
import com.um.psystem.service.mtService.IAssetsStockinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: qy
 * @Date: 2020/8/22 - 11:24
 * @Description: com.um.psystem.service.mtService.impl
 * @version: 1.0
 */
@Service
public class AssetsStockinService implements IAssetsStockinService {

    @Autowired
    PublicMapper publicMapper;

    @Override
    public List<Map<String, Object>> getStockinList(Map map) {
        String k3_code="";
        String sqltmp="";
        String stockin_man="";
        Map paramsMap = new HashMap();
        Integer page = Integer.parseInt((map.get("page")!=null?map.get("page").toString():"0"));
        Integer rows = Integer.parseInt((map.get("rows")!=null?map.get("rows").toString():"0"));
        if(StrUtil.isNotBlank(map.get("k3_code")!=null?map.get("k3_code").toString():null)){
            sqltmp+=" and a.k3_code=#{k3_code}";
            k3_code =map.get("k3_code").toString();
            paramsMap.put("k3_code",k3_code);
        }
        if(StrUtil.isNotBlank(map.get("stockin_man")!=null?map.get("stockin_man").toString():null)){
            sqltmp+=" and a.username=#{stockin_man}";
            stockin_man =map.get("stockin_man").toString();
            paramsMap.put("stockin_man",stockin_man);
        }
        String  sql = " select count(*) as totals from ws_eng_assets_stockin_record a where 1=1 "+sqltmp;
        System.out.println("--------------------------------------");
        System.out.println("sql1:"+sql);
        paramsMap.put("sqlStr",sql);
        int totals=0;
        List<Map<String,Object>> list_count = publicMapper.getPublicItems(paramsMap);
        if(list_count.size()>0){
            totals = Integer.parseInt(list_count.get(0).get("totals").toString());
        }
        sql = " select a.*,"+totals+ " as totals from ws_eng_assets_stockin_record a where 1=1 "+sqltmp+" order by id desc";
        if(page!=0 && rows!=0){
            sql+= " limit #{startIndex},#{rows} ";
            paramsMap.put("startIndex",(page-1)*rows);
            paramsMap.put("rows",rows);
        }
        paramsMap.put("sqlStr",sql);
        System.out.println("--------------------------------------");
        System.out.println("sql2:"+sql);
        return publicMapper.getPublicItems(paramsMap);
    }

    @Override
    public List<Map<String,Object>> getMtInfo(Map map) {
        Map paramsMap = new HashMap();
        String k3Code = (String) map.get("k3_code");
        String sql = "SELECT" +
                " a.k3_code AS k3Code," +
                " a.type_dtl_no AS mtCode," +
                " b.type_main_name AS typeMain," +
                " a.type_dtl_name AS mtName," +
                " a.model AS model," +
                " a.sizes AS sizes," +
                " a.unit AS unit " +
                " FROM" +
                " ws_eng_assets_type_dtl a" +
                " JOIN ws_eng_assets_type_main b ON a.type_main_id = b.type_main_id " +
                " WHERE" +
                " a.k3_code ='"+k3Code+"'";
        paramsMap.put("sqlStr",sql);
        return publicMapper.getPublicItems(paramsMap);
    }


    @Override
    public JsonResult addMtRecord(Map map) {
        Map paramsMap = new HashMap();
        String sql = "INSERT INTO ws_eng_assets_stockin_record" +
                " VALUES" +
                " (NULL, #{k3Code}, #{mtCode}, #{typeMain}, #{mtName}," +
                " #{model}, #{sizes}, #{unit}, #{num}, #{supplier}, #{remarks}," +
                " #{username},now() )";
        paramsMap.put("isqlStr",sql);
        paramsMap.put("k3Code",map.get("k3Code"));
        paramsMap.put("mtCode",map.get("mtCode"));
        paramsMap.put("typeMain",map.get("typeMain"));
        paramsMap.put("mtName",map.get("mtName"));
        paramsMap.put("model",map.get("model"));
        paramsMap.put("sizes",map.get("sizes"));
        paramsMap.put("unit",map.get("unit"));
        paramsMap.put("num",Integer.parseInt((String) map.get("num")));
        paramsMap.put("supplier",map.get("supplier"));
        paramsMap.put("remarks",map.get("remarks"));
        paramsMap.put("username",map.get("stockin_username"));
       // paramsMap.put("datetime",new Date());
        return JsonResult.success(publicMapper.saveItems(paramsMap));
    }

    @Override
    public List<Map<String,Object>> getRecordInfo(Map map) {
        Map paramsMap = new HashMap();
        String id = (String) map.get("id");
        String sql = "select id,k3_code as k3Code,mt_code as mtCode," +
                "type_main as typeMain,mt_name as mtName,model,sizes,unit," +
                "num,supplier,remarks,username as stockin_username from ws_eng_assets_stockin_record where id ="+id;
        paramsMap.put("sqlStr",sql);
        return publicMapper.getPublicItems(paramsMap);
    }

    @Override
    public JsonResult<Integer> updateMtRecord(Map map) {
        Map paramsMap = new HashMap();
        String sql = "UPDATE ws_eng_assets_stockin_record" +
                " SET" +
                " k3_code=#{k3Code}, mt_code=#{mtCode}, type_main=#{typeMain}, " +
                "mt_name=#{mtName}, model=#{model}, sizes=#{sizes}, unit=#{unit}," +
                "num=#{num}, supplier=#{supplier}, remarks=#{remarks} ,datetime=now()" +
                " WHERE id=#{id} ";
        paramsMap.put("usqlStr",sql);
        paramsMap.put("k3Code",map.get("k3Code"));
        paramsMap.put("id",map.get("id"));
        paramsMap.put("mtCode",map.get("mtCode"));
        paramsMap.put("typeMain",map.get("typeMain"));
        paramsMap.put("mtName",map.get("mtName"));
        paramsMap.put("model",map.get("model"));
        paramsMap.put("sizes",map.get("sizes"));
        paramsMap.put("unit",map.get("unit"));
        paramsMap.put("num",Integer.parseInt((String) map.get("num")));
        paramsMap.put("supplier",map.get("supplier"));
        paramsMap.put("remarks",map.get("remarks"));
        Integer updateNum = publicMapper.updateItems(paramsMap);
        return JsonResult.success(updateNum);
    }

    @Override
    public JsonResult<Integer> deleMtRecord(Integer id) {
        Map paramsMap = new HashMap();
        String sql = "DELETE FROM ws_eng_assets_stockin_record" +
                " WHERE" +
                " ID=#{id}";
        paramsMap.put("dsqlStr",sql);
        paramsMap.put("id",id);
        Integer updateNum = publicMapper.delItems(paramsMap);
        return JsonResult.success(updateNum);
    }

}
