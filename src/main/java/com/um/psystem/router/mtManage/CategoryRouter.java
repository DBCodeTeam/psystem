package com.um.psystem.router.mtManage;

import com.um.psystem.router.BaseRouter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: zzj
 * @Description: 物资分类路由
 * @Date: 2020/5/27
 */
@Controller
@RequestMapping(value = "/admin/mtCategory")
public class CategoryRouter extends BaseRouter {

    @Override
    protected String getPrefix() {
        return "/admin/mtManage/mtCategory";
    }


}
