package com.um.psystem.entity.sysEntity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: zzj
 * @Description: 工作站用户
 * @Date: 2020/6/16
 */
@TableName(value="ws_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WsUser implements Serializable {
    @TableId(value="id", type= IdType.AUTO)
    private Integer id;

    @TableField(value="name")
    private  String name;

    @TableField(value="sex")
    private String gender;

    @TableField(value="employeeNum")
    private String employeeNum;

    /*** 用户名、登录名     */
    @TableField(value="loginname")
    private String username;

    /*** 密码     */
    @TableField(value="password")
    private String password;

    /*** 邮箱     */
    @TableField(value="email")
    private String email;

    /*** 手机号     */
    @TableField(value="mobilno")
    private String mobile;

    /**
     * 状态：1=启用，0=禁用
     */
    @TableField(value="visible")
    private Integer status;
}
