package com.um.psystem.entity.mtEntity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: zhenjin.zheng
 * @Description: 物资分类实体
 * @Date: 2020/5/28
 */
@TableName("ws_eng_assets_type_main")
@Data
@NoArgsConstructor
public class AssetsType {
    /** 物资分类id */
    @TableId(value="type_main_id", type= IdType.AUTO)
    private Integer typeMainId;

    /** 物资分类名称 */
    @TableField(value = "type_main_name")
    private String typeMainName;

    /** 备注 */
    private String remark;

    /** 物资所属部门ID */
    @TableField(value = "dept_id")
    private String deptId;

    /** 物资所属部门 */
    @TableField(value = "dept_name")
    private String deptName;
}
