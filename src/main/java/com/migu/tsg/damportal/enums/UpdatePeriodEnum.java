package com.migu.tsg.damportal.enums;

/**
 * @author wen
 * @version 1.0
 * @date 2020/10/25 13:52
 */
public enum UpdatePeriodEnum {
    DAY(1, "天"), WEEK(2, "周"), MON(3, "月"), RANDOM(3, "不定期");

    private Integer code;
    private String val;

    UpdatePeriodEnum(Integer code, String val) {
        this.code = code;
        this.val = val;
    }

    public Integer getCode() {
        return code;
    }

    public String getVal() {
        return val;
    }

    public static UpdatePeriodEnum getValByCode(Integer code) {
        if (code != null) {
            for (UpdatePeriodEnum obj : values()) {
                if (obj.getCode().compareTo(code) == 0) {
                    return obj;
                }
            }
        }
        return null;
    }


}
