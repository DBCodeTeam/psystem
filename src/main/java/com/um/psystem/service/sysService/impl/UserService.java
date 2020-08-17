package com.um.psystem.service.sysService.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.um.psystem.exception.ApplicationException;
import com.um.psystem.exception.StatusCode;
import com.um.psystem.annotation.Log;
import com.um.psystem.entity.sysEntity.User;
import com.um.psystem.exception.SystemError;
import com.um.psystem.mapper.platform.sysMapper.UserMapper;
import com.um.psystem.model.sysModel.request.UserRequest;
import com.um.psystem.model.sysModel.response.UserResponse;
import com.um.psystem.service.sysService.IUserService;
import com.um.psystem.utils.BeanCopier;
import com.um.psystem.utils.MD5Utils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zzj on 2020-05-19.
 */
@Service
public class UserService extends BaseService<UserMapper, User> implements IUserService {

    @Override
    @Transactional
    @Log(module = "系统用户", description = "添加用户信息")
    public UserResponse save(UserRequest request) {
        User existing = findByUsername(request.getUsername());
        if (existing == null) {
            User user = BeanCopier.copy(request, User.class);

            user.setPassword(MD5Utils.getStringMD5(request.getPassword()));//密码进行MD5加密

            super.insert(user);

            return BeanCopier.copy(user, UserResponse.class);
        } else {
            //用户名已存在
            throw new ApplicationException(StatusCode.CONFLICT.getCode(), StatusCode.CONFLICT.getMessage());
        }
    }

    @Override
    @Transactional
    @Log(module = "系统用户", description = "更新用户信息")
    public UserResponse update(UserRequest request) {
        User existing = findByUsername(request.getUsername());
        if (existing != null) {
            existing.setEmail(request.getEmail());
            existing.setGender(request.getGender());
            existing.setMobile(request.getMobile());
            existing.setName(request.getName());

            super.insertOrUpdate(existing);

            return BeanCopier.copy(existing, UserResponse.class);
        } else {
            //用户不存在
            throw new ApplicationException(StatusCode.NOT_FOUND.getCode(), StatusCode.NOT_FOUND.getMessage());
        }
    }

    @Override
    @Transactional
    @Log(module = "系统用户", description = "删除用户信息")
    public Integer del(Long id) {
        User existing = selectById(id);
        if (existing != null) {
            super.deleteById(id);
            return 1;
        } else {
            // 用户不存在
            throw new ApplicationException(StatusCode.NOT_FOUND.getCode(), StatusCode.NOT_FOUND.getMessage());
        }
    }

    @Override
    public UserResponse get(Long id) {
        User existing = selectById(id);
        if(existing!=null){
            return BeanCopier.copy(existing, UserResponse.class);
        }else{
            //用户不存在
            throw new ApplicationException(StatusCode.NOT_FOUND.getCode(), StatusCode.NOT_FOUND.getMessage());
        }
    }

    @Override
    public UserResponse get(String username) {
        User existing = findByUsername(username);
        if(existing!=null){
            return BeanCopier.copy(existing, UserResponse.class);
        }else{
            //用户不存在
            throw new ApplicationException(StatusCode.NOT_FOUND.getCode(), StatusCode.NOT_FOUND.getMessage());
        }
    }

    @Override
    public User getUser(String username) {
        User user = findByUsername(username);
        return user;
    }

    @Override
    public List<UserResponse> getUsers(UserRequest request) {
        List<User> users = null;
        List<UserResponse> responses = BeanCopier.copy(users, UserResponse.class);
        return responses;
    }

    @Override
    public Page<UserResponse> getPage(Page<User> page, UserRequest request) {
        List<User> users = baseMapper.findUser(page, request);
        page.setRecords(users);
        return convert(page, UserResponse.class);
    }

    public List<User> getUsersByRole(String roleCode) {
        List<User> user_list =baseMapper.findUserByRoleCode(roleCode);
        return user_list;
    }

    @Override
    public List<User> getUserList(Map<String, Object> columnMap) {
        return baseMapper.selectByMap(columnMap);
    }

    @Override
    public Page<UserResponse> getUsers(Page<User> page, String roleCode) {
        List<User> users = baseMapper.findUserByRoleCode(page, roleCode);
        page.setRecords(users);
        return convert(page, UserResponse.class);
    }

    /**
     * 修改密码
     * @param userId 用户ID
     * @param originalPassword 原密码
     * @param newPassword      新密码
     */
    @Override
    @Transactional
    @Log(module = "系统用户", description = "修改密码")
    public Integer modifyPassword(Long userId, String originalPassword, String newPassword) {
        User existing = selectById(userId);
        if (existing != null) {
            String encryData = MD5Utils.getStringMD5(originalPassword);
            if (existing.getPassword().equals(encryData)) {//验证原密码是否正确
                existing.setPassword(MD5Utils.getStringMD5(newPassword));
                super.insertOrUpdate(existing);
                return 1;
            } else {
                //原密码错误
                throw new ApplicationException(SystemError.ORIGINAL_PASSWORD_ERROR.getCode(), SystemError.ORIGINAL_PASSWORD_ERROR.getMessage());
            }
        } else {
            //用户不存在
            throw new ApplicationException(StatusCode.NOT_FOUND.getCode(), StatusCode.NOT_FOUND.getMessage());
        }
    }

    @Override
    public UserResponse auth(String username, String password) {
        User existing = findByUsername(username);
        if (existing != null) {
            String encryData = MD5Utils.getStringMD5(password);
            if (existing.getPassword().equals(encryData)) {//验证密码是否正确
                return BeanCopier.copy(existing, UserResponse.class);
            } else {
                //密码错误
                throw new ApplicationException(SystemError.LOGIN_FAILED.getCode(), SystemError.LOGIN_FAILED.getMessage());
            }
        } else {
            //用户不存在
            throw new ApplicationException(StatusCode.NOT_FOUND.getCode(), StatusCode.NOT_FOUND.getMessage());
        }
    }

    public User findByUsername(String username){
        return super.selectOne(new EntityWrapper<User>().eq("loginname", username));
    }
}
