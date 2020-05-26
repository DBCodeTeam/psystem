package com.um.psystem.router.sys;

import com.um.psystem.router.BaseRouter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zzj on 2020-05-18.
 */
@Controller
@RequestMapping(value = "/admin/menu")
public class MenuRouter extends BaseRouter{

    @Override
    protected String getPrefix() {
        return "/admin/sys/menu";
    }
}
