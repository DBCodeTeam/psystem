package com.um.psystem.model.sysModel.response;

/**
 * User响应体
 */
public class UserResponse extends BaseResponse {

    /*** 用户名、登录名     */
    private String username;

    /*** 姓名     */
    private String name;

    /*** 邮箱     */
    private String email;

    /*** 手机号     */
    private String mobile;

    /**
     * 性别：0=男，1=女
     */
    private String gender;



    /**
     * 状态：0=启用，1=禁用
     */
    private Integer status;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
