package com.um.psystem.entity.sysEntity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: zzj
 * @Description: 部门实体
 * @Date: 2020/6/1
 */
@TableName("ws_dept")
@Data
@NoArgsConstructor
public class DeptEntity {

    @TableId(value="id", type= IdType.AUTO)
    private Integer id;

    @TableField(value = "dept_name")
    private String deptName;

    @TableField(value = "dept_no")
    private String deptNo;

    /**上级部门*/
    @TableField(value = "upper_dept")
    private String upperDept;

    @TableField(value = "level")
    private String level;

    @TableField(value = "dept_manager")
    private String deptManager;

    @TableField(value = "dept_addr")
    private String deptAddr;

    @TableField(value = "account_dept")
    private String accountDept;

    @TableField(value = "isdrop")
    private Integer isDrop;

}
