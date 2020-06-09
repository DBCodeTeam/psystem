package com.um.psystem.service.sysService;

import com.um.psystem.entity.sysEntity.DeptEntity;

import java.util.List;
import java.util.Map;

/**
 * @Author: zhenjin.zheng
 * @Description:
 * @Date: 2020/6/2
 */
public interface IDeptAndEmpService {

    public List<DeptEntity> getDeptList(Map<String,Object> columnMap);
}
