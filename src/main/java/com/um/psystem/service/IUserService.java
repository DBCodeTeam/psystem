package com.um.psystem.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.um.psystem.service.IBaseService;
import com.um.psystem.entity.User;
import com.um.psystem.model.request.UserRequest;
import com.um.psystem.model.response.UserResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zzj on 2020-05-19.
 */
public interface IUserService extends IBaseService<User> {

    @Transactional
    public UserResponse save(UserRequest request);

    @Transactional
    public UserResponse update(UserRequest request);

    @Transactional
    public Integer del(Long id);

    public UserResponse get(Long id);

    public UserResponse get(String username);

    public User getUser(String username);

    public List<UserResponse> getUsers(UserRequest request);

    public Page<UserResponse> getPage(Page<User> page, UserRequest request);

    public Page<UserResponse> getUsers(Page<User> page, String roleCode);

    public Integer modifyPassword(Long userId, String originalPassword, String newPassword);

    public UserResponse auth(String username, String password);

    public List<User> getUsersByRole(String roleCode);
}
