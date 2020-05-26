package com.um.psystem.mapper.platform.sysMapper;

import com.um.psystem.mapper.BaseMapper;
import com.um.psystem.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author zzj
 * @since 2020-05-20
 */
public interface RoleMapper extends BaseMapper<Role> {
    public List<Role> findRoleByUsername(@Param("username") String username);
}