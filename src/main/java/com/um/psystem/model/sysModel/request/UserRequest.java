package com.um.psystem.model.sysModel.request;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class UserRequest extends BaseRequest {

    /*** 用户名、登录名     */
    @NotEmpty(message = "用户名不能为空")
    private String username;

    /*** 密码     */
    @NotEmpty(message = "密码不能为空")
    private String password;

    /*** 姓名     */
    private String name;

    /*** 邮箱     */
    private String email;

    /*** 手机号     */
    private String mobile;

    private String gender;

    private String employeeNum;

    /**
     * 状态：1=启用，0=禁用
     */
    private Integer status;

}
