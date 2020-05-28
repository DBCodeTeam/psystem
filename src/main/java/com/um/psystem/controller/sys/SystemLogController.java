package com.um.psystem.controller.sys;

import com.um.psystem.controller.BaseController;
import com.um.psystem.model.vo.DataGrid;
import com.um.psystem.model.sysModel.request.SystemLogRequest;
import com.um.psystem.model.sysModel.response.SystemLogResponse;
import com.um.psystem.service.sysService.ISystemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * SystemLog控制器
 * @create 2020-05-19 21:34:13
 */
@RestController
public class SystemLogController extends BaseController {

    @Autowired
    private ISystemLogService systemLogService;

    /**
     * 查询单个
     */
    @RequestMapping(value = "/sys/logs/{id}", method = RequestMethod.GET)
    public SystemLogResponse get(@PathVariable("id") Integer id) {
        return null;
    }

    /**
     * 新增
     */
    @RequestMapping(value = "/sys/logs", method = RequestMethod.POST)
    public SystemLogResponse add(SystemLogRequest request) {
        return null;
    }

    /**
     * 更新
     */
    @RequestMapping(value = "/sys/logs", method = RequestMethod.PUT)
    @ResponseBody
    public SystemLogResponse update(SystemLogRequest request) {
        return null;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/sys/logs/{id}", method = RequestMethod.DELETE)
    public Integer delete(@PathVariable("id") int id) {
        return null;
    }

    /**
     * 查询分页
     */
    @RequestMapping(value = "/sys/logs/page", method = RequestMethod.GET)
    public DataGrid getPage(SystemLogRequest request) {
        return buildDataGrid(systemLogService.getPage(getPagination(request),request));
    }

    /**
     * 查询列表
     */
    @RequestMapping(value = "/sys/logs", method = RequestMethod.GET)
    public List<SystemLogResponse> getList(SystemLogRequest request) {
        return null;
    }
}
