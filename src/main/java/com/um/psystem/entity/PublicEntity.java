package com.um.psystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author: zzj
 * @Description: 公用实体
 * @Date: 2020/6/11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true)
public class PublicEntity {
    private String a;
    private String b;
    private String d;
}
