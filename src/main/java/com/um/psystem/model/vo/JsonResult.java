package com.um.psystem.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.um.psystem.enums.ResponseEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description:  数据格式返回统一
 * @author zzj
 * @date 2019/02/19 16:00:22
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonResult <T> implements Serializable {

    private static final long serialVersionUID = -437839076132402939L;

    /**
     * 异常码
     */
    private Integer code;

    /**
     * 描述
     */
    private String message;

    /**
     * 数据
     */
    private T data;

    public JsonResult() {}

    public JsonResult(Integer code,String msg){
        this.code =code;
        this.message =msg;
    }

    public JsonResult(Integer code,String msg,T data){
        this.code = code;
        this.message = msg;
        this.data = data;
    }

    public JsonResult(ResponseEnum responseEnum){
        this.code = responseEnum.getCode();
        this.message = responseEnum.getMessage();
    }

    public JsonResult(ResponseEnum responseEnum,T data){
        this.code = responseEnum.getCode();
        this.message = responseEnum.getMessage();
        this.data = data;
    }

    public JsonResult(T data) {
        this.data = data;
    }

    public static JsonResult success() {
        return new JsonResult(ResponseEnum.SUCCESS);
    }

    public static <T> JsonResult<T> success(T data) {
        return new JsonResult<T>(ResponseEnum.SUCCESS, data);
    }

    public static <T> JsonResult<T> success(int code, String msg) {
        return new JsonResult<T>(code, msg);
    }

    public static JsonResult error(int code, String msg) {
        return new JsonResult(code, msg);
    }

    public static JsonResult error(ResponseEnum responseEnum) {
        return new JsonResult(responseEnum);
    }

    public static JsonResult error(ResponseEnum responseEnum, Object data) {
        return new JsonResult<Object>(responseEnum, data);
    }


}
