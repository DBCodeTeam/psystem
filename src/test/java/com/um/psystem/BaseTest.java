package com.um.psystem;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
/**
 * Created by zzj on 2020/5/24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:spring-context.xml","classpath*:spring-mybatis.xml"})
public class BaseTest {

    @Test
    public void test(){
        System.out.println("666");
    }
}

