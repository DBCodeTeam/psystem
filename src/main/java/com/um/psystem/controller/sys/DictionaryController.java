package com.um.psystem.controller.sys;

import com.um.psystem.controller.BaseController;
import com.um.psystem.model.sysModel.response.Page;
import com.um.psystem.model.vo.DataGrid;
import com.um.psystem.model.sysModel.request.DictionaryRequest;
import com.um.psystem.model.sysModel.response.DictionaryResponse;
import com.um.psystem.service.sysService.IDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Dictionary控制器
 * @create 2020-05-13 17:45:00
 */
@RestController
@RequestMapping(value = "/sys/dict")
public class DictionaryController extends BaseController {

    @Autowired
    private IDictionaryService dictionaryService;

    /**
     * 查询单个
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public DictionaryResponse get(@PathVariable("id") Long id) {
        return dictionaryService.get(id);
    }

    /**
     * 新增
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public DictionaryResponse add(DictionaryRequest request) {
        return dictionaryService.save(request);
    }

    /**
     * 更新
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public DictionaryResponse update(DictionaryRequest request) {
        return dictionaryService.update(request);
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public Integer delete(@PathVariable("id") Long id) {
        return dictionaryService.del(id);
    }

    /**
     * 查询分页
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Page<DictionaryResponse> getPage(DictionaryRequest request) {
        return null;
    }

    /**
     * 查询列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public DataGrid getList(@RequestParam(name = "type") String type) {
        List<DictionaryResponse> responses = dictionaryService.getDictionarys(type);
        return buildDataGrid(responses, responses == null ? 0 : responses.size());
    }
}
