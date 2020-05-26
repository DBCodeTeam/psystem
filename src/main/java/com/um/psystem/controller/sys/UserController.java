package com.um.psystem.controller.sys;

import com.baomidou.mybatisplus.plugins.Page;
import com.um.psystem.controller.BaseController;
import com.um.psystem.exception.ApplicationException;
import com.um.psystem.exception.StatusCode;
import com.um.psystem.model.request.PageRequest;
import com.um.psystem.model.vo.DataGrid;
import com.um.psystem.model.request.UserRequest;
import com.um.psystem.model.response.UserResponse;
import com.um.psystem.service.IUserRoleService;
import com.um.psystem.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Hao
 * @create 2017-03-26
 */
@RestController
@RequestMapping(value = "/sys/user")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IUserRoleService userRoleService;

    /**
     * 查询单个
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public UserResponse get(@PathVariable("id") Long id) {
        return userService.get(id);
    }

    /**
     * 查询单个用户
     *
     * @param username
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public UserResponse get(@RequestParam("username") String username) {
        return userService.get(username);
    }

    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public UserResponse add(@Valid UserRequest user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ApplicationException(StatusCode.BAD_REQUEST.getCode(), getValidateErrorMessage(bindingResult));
        }
        return userService.save(user);
    }

    /**
     * 更新用户
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public UserResponse update(UserRequest user) {
        return userService.update(user);
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public int delete(@RequestParam("id") Long id) {
        return userService.del(id);
    }

    /**
     * 查询分页
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public DataGrid getPage(UserRequest request) {
        Page<UserResponse> page = userService.getPage(getPagination(request), request);
        return super.buildDataGrid(page);
    }

    /**
     * 查询列表
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<UserResponse> getList(UserRequest user) {
        return null;
    }

    /**
     * 修改用户拥有的角色
     *
     * @return
     */
    @RequestMapping(value = "{id}/role/modify", method = RequestMethod.POST)
    public Integer changeUserRole(@PathVariable("id") Long userId, @RequestParam(required = false, name = "roles[]") List<Long> roles) {
        return userRoleService.changeUserRole(userId, roles);
    }

    /**
     * 查询角色下的用户列表
     * @param roleCode
     * @return
     */
    @RequestMapping(value = "/role/{roleCode}", method = RequestMethod.GET)
    public DataGrid getUserByRoleCode(@PathVariable("roleCode") String roleCode, PageRequest pageRequest) {
        Page<UserResponse> page = userService.getUsers(getPagination(pageRequest), roleCode);
        return buildDataGrid(page);
    }

    @RequestMapping(value = "{id}/modifypwd", method = RequestMethod.POST)
    public Integer modifyPassword(@PathVariable("id") Long id, @RequestParam("originalPassword") String originalPassword, @RequestParam("newPassword") String newPassword) {
        return userService.modifyPassword(id, originalPassword, newPassword);
    }
}
