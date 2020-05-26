package com.um.psystem.mapper;

import com.um.psystem.BaseTest;
import com.um.psystem.mapper.platform.mtManageMapper.MtManageMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Created by zzj on 2020/5/24.
 */

public class mtMapper extends BaseTest {

    @Autowired
    MtManageMapper mtMapper;

    @Test
    public void testSelectMt(){
      List<Map<String,Object>> listMap=mtMapper.findMtCategory("1");
      for(Map<String,Object> modelMap:listMap){
          String name = modelMap.get("name").toString();
          System.out.println(name);
      }
    }
}
