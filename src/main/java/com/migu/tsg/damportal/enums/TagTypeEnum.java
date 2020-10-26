package com.migu.tsg.damportal.enums;

/**
 * @author wen
 * @version 1.0
 * @date 2020/10/25 13:52
 */
public enum TagTypeEnum {
    ENUM(1, "枚举", "enum"), STR(2, "字符串", "value"), NUM(3, "数值", "number");

    private Integer code;
    private String val;
    private String str;

    TagTypeEnum(Integer code, String val, String str) {
        this.code = code;
        this.val = val;
        this.str = str;
    }

    public Integer getCode() {
        return code;
    }

    public String getVal() {
        return val;
    }

    public String getStr() {
        return str;
    }

    public static TagTypeEnum getValByStr(String str) {
        if (str != null) {
            for (TagTypeEnum obj : values()) {
                if (obj.getStr().compareTo(str) == 0) {
                    return obj;
                }
            }
        }
        return null;
    }

    public static TagTypeEnum getValByCode(Integer code) {
        if (code != null) {
            for (TagTypeEnum obj : values()) {
                if (obj.getCode().compareTo(code) == 0) {
                    return obj;
                }
            }
        }
        return null;
    }


}
