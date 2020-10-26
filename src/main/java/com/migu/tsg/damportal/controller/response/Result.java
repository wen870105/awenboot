package com.migu.tsg.damportal.controller.response;


import com.migu.tsg.damportal.exception.SystemErrorType;

/**
 * @author wen
 * @version 1.0
 * @date 2020/8/31 11:35
 */
public class Result<T> {

    public static final String SUCCESSFUL_CODE = "200";
    public static final String SUCCESSFUL_MESG = "处理成功";

    private String code;
    private String msg;
    private Long time;
    private T data;

    public Result() {
        this.time = System.currentTimeMillis();
    }

    public Result(T data) {
        this.data = data;
    }

    public Result(String code, String msg) {
        this.code = code;
        this.msg = msg;
        this.time = System.currentTimeMillis();
    }

    public Result(SystemErrorType type) {
        this.code = type.getCode();
        this.msg = type.getMsg();
        this.time = System.currentTimeMillis();
    }

    public Result(SystemErrorType type, T obj) {
        this.code = type.getCode();
        this.msg = type.getMsg();
        this.data = obj;
        this.time = System.currentTimeMillis();
    }

    public Result(String code, String desc, T data) {
        this(code, desc);
        this.data = data;
    }


    /**
     * 快速创建成功结果并返回结果数据
     *
     * @param data
     * @return Result
     */
    public static Result success(Object data) {
        return new Result<>(SUCCESSFUL_CODE, SUCCESSFUL_MESG, data);
    }

    /**
     * 快速创建成功结果
     *
     * @return Result
     */
    public static Result success() {
        return success(null);
    }

    /**
     * 系统异常类没有返回数据
     *
     * @return Result
     */
    public static Result fail() {
        return new Result(SystemErrorType.SYSTEM_ERROR);
    }

    public static Result fail(String msg) {
        Result ret = Result.fail();
        ret.setMsg(msg);
        return ret;
    }

    /**
     * 系统异常类并返回结果数据
     *
     * @param errorType
     * @param data
     * @return Result
     */
    public static Result fail(SystemErrorType errorType, Object data) {
        return new Result(errorType, data);
    }

    /**
     * 系统异常类并返回结果数据
     *
     * @param errorType
     * @return Result
     */
    public static Result fail(SystemErrorType errorType) {
        return Result.fail(errorType, null);
    }


    /**
     * 成功code=000000
     *
     * @return true/false
     */
    public boolean isSuccess() {
        return SUCCESSFUL_CODE.equals(this.code);
    }

    public static String getSuccessfulCode() {
        return SUCCESSFUL_CODE;
    }

    public static String getSuccessfulMesg() {
        return SUCCESSFUL_MESG;
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

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
