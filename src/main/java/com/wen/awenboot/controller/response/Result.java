package com.wen.awenboot.controller.response;

import lombok.Data;

/**
 * @author wen
 * @version 1.0
 * @date 2020/5/28 18:57
 */
@Data
public class Result {
    private String code;
    private String msg;
    private Object obj;

    public Result(String code, String msg, Object obj) {
        this.code = code;
        this.msg = msg;
        this.obj = obj;
    }

    public static Result msg(String msg) {
        return new Result("200", msg, null);
    }

    public static Result success() {
        return new Result("200", "success", null);
    }

    public static Result success(Object obj) {
        return new Result("200", "success", obj);
    }

    public static Result error(String msg) {
        return new Result("500", msg, null);
    }
}
