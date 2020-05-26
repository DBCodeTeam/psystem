package com.um.psystem.annotation;

import java.lang.annotation.*;

/**
 * @author zzj
 * @create 2020-05-19
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**模块*/
    String module() default "";

    /**描述*/
    String description() default "";
}
