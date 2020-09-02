package com.wen.awenboot.biz.model;

/**
 * @author wen
 * @version 1.0
 * @date 2020/5/28 18:52
 */
public enum ResultCode {
    SUCCESS(1, "成功"),
    PARAM_INVALID(501, "参数错误");

    private Integer code;
    private String msg;

    private ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}