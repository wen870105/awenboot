package com.migu.tsg.damportal.exception;

/**
 * @author wen
 * @version 1.0
 * @date 2020/8/31 11:41
 */
public enum SystemErrorType {

    SYSTEM_ERROR("500", "系统异常"),
    SYSTEM_BUSY("501", "系统繁忙,请稍候再试"),

    ARGUMENT_NOT_VALID("401", "请求参数校验不通过"),
    INVALID_TOKEN("402", "无效token"),
    NOT_FOUND("404", "没有相关数据");

    /**
     * 错误类型码
     */
    private String code;
    /**
     * 错误类型描述信息
     */
    private String msg;

    SystemErrorType(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
