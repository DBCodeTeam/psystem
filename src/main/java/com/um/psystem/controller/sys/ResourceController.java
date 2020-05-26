package com.um.psystem.controller.sys;

import com.um.psystem.utils.TreeNodeUtils;
import com.um.psystem.controller.BaseController;
import com.um.psystem.model.vo.DataGrid;
import com.um.psystem.model.vo.TreeNode;
import com.um.psystem.model.request.ResourceRequest;
import com.um.psystem.model.response.ResourceResponse;
import com.um.psystem.model.response.UserResponse;
import com.um.psystem.service.IResourceService;
import com.um.psystem.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Resource控制器
 *
 * @create 2020-05-18 10:45:58
 */
@RestController
@RequestMapping(value = "/sys/resource")
public class ResourceController extends BaseController {

    @Autowired
    private IResourceService resourceService;

    /**
     * 查询单个
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResourceResponse get(@PathVariable("id") Long id) {
        return resourceService.get(id);
    }

    /**
     * 新增
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResourceResponse add(ResourceRequest request) {
        return resourceService.save(request);
    }

    /**
     * 更新
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResourceResponse update(ResourceRequest request) {
        return resourceService.update(request);
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public int delete(@PathVariable("id") Long id) {
        return resourceService.del(id);
    }

    /**
     * 查询列表
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public DataGrid getAll() {
        List<ResourceResponse> responses = resourceService.getResources();
        return buildDataGrid(responses, responses == null ? 0 : responses.size());
    }

    /**
     * 根据角色查询
     */
    @RequestMapping(value = "/role/{roleCode}", method = RequestMethod.GET)
    public List<ResourceResponse> getResourceByRole(@PathVariable("roleCode") String roleCode) {
        List<ResourceResponse> responses = resourceService.getResources(roleCode);
        return responses;
    }

    /**
     * 查询树列表 easyui combotree
     */
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public List<TreeNode> getTree() {
        List<TreeNode> treeNodes = new ArrayList<>();

        TreeNode root = new TreeNode();
        root.setId(-1L);
        root.setPid(null);
        root.setText("顶级菜单");
        treeNodes.add(root);

        List<ResourceResponse> responses = resourceService.getResources();
        for (ResourceResponse resource : responses) {
            if (resource != null && "1".equals(resource.getType())) {//type=1 代表菜单
                TreeNode treeNode = new TreeNode();
                treeNode.setId(resource.getId());
                treeNode.setPid(resource.getParentId());
                treeNode.setText(resource.getName());
                treeNodes.add(treeNode);
            }
        }
        return TreeNodeUtils.buildTreeNode(treeNodes);
    }

    /**
     * 查询菜单权限列表
     */
    @RequestMapping(value = "/permissions", method = RequestMethod.GET)
    public DataGrid getPermission(@RequestParam(required = false, name = "parentId") Long parentId, @RequestParam(required = false, name = "type") String type) {
        List<ResourceResponse> responses = resourceService.getResources(parentId, type);
        return buildDataGrid(responses, responses == null ? 0 : responses.size());
    }

    /**
     * 查询菜单列表
     */
    @RequestMapping(value = "/menus", method = RequestMethod.GET)
    public DataGrid getMenus(@RequestParam(required = false, name = "parentId") Long parentId, @RequestParam(required = false, name = "type") String type) {
        List<ResourceResponse> responses = resourceService.getResources(parentId, type);
        return buildDataGrid(responses, responses == null ? 0 : responses.size());
    }

    /**
     * 查询菜单列表
     */
    @RequestMapping(value = "/menus/my", method = RequestMethod.GET)
    public List<ResourceResponse> getMenus(@RequestParam(required = false, name = "username") String username, HttpServletRequest request) {
        if (Utils.isEmpty(username)) {
            UserResponse user = getSessionUser(request);
            if (user != null && !Utils.isEmpty(user.getUsername())) {
                username = user.getUsername();
            }
        }
        //查询我的菜单
        List<ResourceResponse> responses = resourceService.getResourceByUsername(username, "1");
        return responses;
    }
}
