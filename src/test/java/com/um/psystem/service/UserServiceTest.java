package com.um.psystem.service;

import com.um.psystem.BaseTest;
import com.um.psystem.entity.sysEntity.User;
import com.um.psystem.service.sysService.impl.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author: zhenjin.zheng
 * @Description: 用户接口单元测试
 * @Date: 2020/5/25
 */
public class UserServiceTest extends BaseTest {

    @Autowired
    UserService userService;

    @Test
    public void testFindUserByRole(){

       List<User> list_user= userService.getUsersByRole("4");
       for(User user:list_user){
           System.out.println(user.getName());
       }
    }
}
