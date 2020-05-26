package com.um.psystem.mapper.platform.sysMapper;

import com.um.psystem.mapper.BaseMapper;
import com.um.psystem.entity.Resource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author zzj
 * @since 2020-05-19
 */
public interface ResourceMapper extends BaseMapper<Resource> {

    public List<Resource> findResourceByRoleCode(@Param("roleCode") String roleCode);

    public List<Resource> findResource(@Param("parentId") Long parentId, @Param("type") String type);

    public List<Resource> findResourceByUsernameAndType(@Param("username") String username, @Param("type") String type);
}