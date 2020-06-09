package com.um.psystem.controller.sys;

import com.um.psystem.controller.BaseController;
import com.um.psystem.model.Page;
import com.um.psystem.model.vo.DataGrid;
import com.um.psystem.model.sysModel.request.DictionaryTypeRequest;
import com.um.psystem.model.sysModel.response.DictionaryTypeResponse;
import com.um.psystem.service.sysService.IDictionaryTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * DictionaryType控制器
 * @create 2017-4-3 17:45:00
 */
@RestController
@RequestMapping(value = "/sys/dict/type")
public class DictionaryTypeController extends BaseController {

    @Autowired
    private IDictionaryTypeService dictionaryTypeService;

    /**
     * 查询单个
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public DictionaryTypeResponse get(@PathVariable("id") Long id) {
        return dictionaryTypeService.get(id);
    }

    /**
     * 新增
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public DictionaryTypeResponse add(DictionaryTypeRequest request) {
        return dictionaryTypeService.save(request);
    }

    /**
     * 更新
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public DictionaryTypeResponse update(DictionaryTypeRequest request) {
        return dictionaryTypeService.update(request);
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public Integer delete(@PathVariable("id") Long id) {
        return dictionaryTypeService.del(id);
    }

    /**
     * 查询分页
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Page<DictionaryTypeResponse> getPage(DictionaryTypeRequest request) {
        return null;
    }

    /**
     * 查询列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public DataGrid getList() {
        List<DictionaryTypeResponse> responses = dictionaryTypeService.getDictionaryTypes();
        return buildDataGrid(responses, responses == null ? 0 : responses.size());
    }


}
