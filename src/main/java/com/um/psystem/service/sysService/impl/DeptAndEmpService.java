package com.um.psystem.service.sysService.impl;

import com.um.psystem.entity.sysEntity.DeptEntity;
import com.um.psystem.mapper.platform.mtManageMapper.DeptAndEmpMapper;
import com.um.psystem.service.sysService.IDeptAndEmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: zzj
 * @Description:
 * @Date: 2020/6/2
 */
@Service
public class DeptAndEmpService implements IDeptAndEmpService {

    @Autowired
    DeptAndEmpMapper deptAndEmpMapper;
    @Override
    public List<DeptEntity> getDeptList(Map<String, Object> columnMap) {
        return deptAndEmpMapper.selectByMap(columnMap);
    }
}
