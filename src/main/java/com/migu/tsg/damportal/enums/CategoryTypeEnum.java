package com.migu.tsg.damportal.enums;

/**
 * @author wen
 * @version 1.0
 * @date 2020/10/25 13:52
 */
public enum CategoryTypeEnum {
    STATS(1, "统计"), RULE(2, "规则"), DIG(3, "挖掘");

    private Integer code;
    private String val;

    CategoryTypeEnum(Integer code, String val) {
        this.code = code;
        this.val = val;
    }

    public Integer getCode() {
        return code;
    }

    public String getVal() {
        return val;
    }

    public static CategoryTypeEnum getValByCode(Integer code) {
        if (code != null) {
            for (CategoryTypeEnum obj : values()) {
                if (obj.getCode().compareTo(code) == 0) {
                    return obj;
                }
            }
        }
        return null;
    }


}
