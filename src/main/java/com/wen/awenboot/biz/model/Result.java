package com.wen.awenboot.biz.model;

/**
 * @author wen
 * @version 1.0
 * @date 2020/5/28 18:57
 */
public class Result {
    private Integer code;
    private String msg;
    private Object obj;

    public Result(ResultCode rc, Object obj) {
        this.code = rc.getCode();
        this.msg = rc.getMsg();
        this.obj = obj;
    }

    public static void main(String[] args) {
        System.out.println(new Result(null, null));
    }
}
