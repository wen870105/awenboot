package com.migu.tsg.damportal.enums;

/**
 * @author wen
 * @version 1.0
 * @date 2020/10/25 13:52
 */
public enum TagStatusEnum {
    OPEN(1, "启用"),
    CLOSE(0, "停用");
    private Integer code;
    private String val;

    TagStatusEnum(Integer code, String val) {
        this.code = code;
        this.val = val;
    }

    public Integer getCode() {
        return code;
    }

    public String getVal() {
        return val;
    }


    public static TagStatusEnum getValByCode(Integer code) {
        if (code != null) {
            for (TagStatusEnum obj : values()) {
                if (obj.getCode().compareTo(code) == 0) {
                    return obj;
                }
            }
        }
        return null;
    }


}
