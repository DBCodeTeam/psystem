package com.um.psystem.router.sys;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.um.psystem.router.BaseRouter;

/**
 * Created by zzj on 2020-05-20.
 */
@Controller
@RequestMapping(value = "/admin/permission")
public class PermissionRouter extends BaseRouter{

    @Override
    protected String getPrefix() {
        return "/admin/sys/permission";
    }

    /**
     * 跳转到新增页面
     */
    @RequestMapping(value = "/add/{menuId}", method = RequestMethod.GET)
    public String add(Model model, @PathVariable("menuId") Long menuId) {
        model.addAttribute("menuId", menuId);
        return ADD;
    }
}
