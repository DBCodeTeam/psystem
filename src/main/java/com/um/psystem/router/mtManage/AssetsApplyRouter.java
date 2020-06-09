package com.um.psystem.router.mtManage;

import com.um.psystem.router.BaseRouter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: zhenjin.zheng
 * @Description:
 * @Date: 2020/6/3
 */
@Controller
@RequestMapping(value = "/admin/assetsApply")
public class AssetsApplyRouter extends BaseRouter {

    @Override
    protected String getPrefix() {
        return "/admin/mtManage/assetsApply";
    }
}
