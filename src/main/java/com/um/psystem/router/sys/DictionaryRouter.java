package com.um.psystem.router.sys;

import com.um.psystem.router.BaseRouter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by zzj on 2020-05-19.
 */
@Controller
@RequestMapping(value = "/admin/dict")
public class DictionaryRouter extends BaseRouter{

    @Override
    protected String getPrefix() {
        return "/admin/sys/dict";
    }

    /**
     * 跳转到新增页面
     */
    @RequestMapping(value = "/add/{type}", method = RequestMethod.GET)
    public String add(Model model, @PathVariable("type") String type) {
        model.addAttribute("type", type);
        return ADD;
    }

}
