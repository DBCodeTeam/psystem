package com.um.psystem.mapper.platform.sysMapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.um.psystem.mapper.BaseMapper;
import com.um.psystem.entity.User;
import com.um.psystem.model.request.UserRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author zzj
 * @since 2020-05-18
 */
public interface UserMapper extends BaseMapper<User> {
    public List<User> findUser(Pagination page, @Param("request") UserRequest request);

    public List<User> findUserByRoleCode(Pagination page, @Param("roleCode") String roleCode);

    public List<User> findUserByRoleCode(@Param("roleCode") String roleCode);
}