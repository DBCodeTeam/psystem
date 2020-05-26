package com.um.psystem.controller.sys;

import com.um.psystem.controller.BaseController;
import com.um.psystem.model.vo.DataGrid;
import com.um.psystem.model.request.RoleRequest;
import com.um.psystem.model.response.RoleResponse;
import com.um.psystem.service.IRoleResourceService;
import com.um.psystem.service.IRoleService;
import com.um.psystem.service.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Role控制器
 * @create 2020-05-18 10:45:58
 */
@RestController
@RequestMapping(value = "/sys/role")
public class RoleController extends BaseController {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private IRoleResourceService roleResourceService;

    /**
     * 查询单个
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public RoleResponse get(@PathVariable("id") Long id) {
        return roleService.get(id);
    }

    /**
     * 新增
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public RoleResponse add(RoleRequest request) {
        return roleService.save(request);
    }

    /**
     * 更新
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public RoleResponse update(RoleRequest request) {
        return roleService.update(request);
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public int delete(@RequestParam ("id") Long id) {
        return roleService.del(id);
    }

    /**
     * 查询列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public DataGrid getList() {
        List<RoleResponse> roles = roleService.getRoles();
        return buildDataGrid(roles, roles.size());
    }

    /**
     * 查询用户拥有的角色列表
     * @param username
     * @return
     */
    @RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
    public List<RoleResponse> getRoleByUsername(@PathVariable("username") String username) {
        List<RoleResponse> roles = roleService.getRoles(username);
        return roles;
    }

    /**
     * 删除角色下的用户
     * @param userId
     * @return
     */
    @RequestMapping(value = "{roleId}/user/{userId}/delete", method = RequestMethod.POST)
    public int deleteUserRole(@PathVariable("roleId") Long roleId, @PathVariable("userId") Long userId) {
        return userRoleService.deleteUserRole(userId, roleId);
    }

    /**
     * 修改角色拥有的权限
     *
     * @return
     */
    @RequestMapping(value = "{id}/resource/modify", method = RequestMethod.POST)
    public Integer changeUserRole(@PathVariable("id") Long roleId, @RequestParam(required = false, name = "resources[]") List<Long> resources) {
        return roleResourceService.changeRolePermission(roleId, resources);
    }
}
