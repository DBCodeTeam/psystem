package com.um.psystem.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

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
     * 数据
     */
    private T data;

    public JsonResult() {}

    public JsonResult(T data) {
        this.data = data;
    }

    public static <T> JsonResult<T> success(T data){
        return new JsonResult<T>(data);
    }


}
