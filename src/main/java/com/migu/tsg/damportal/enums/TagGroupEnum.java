package com.migu.tsg.damportal.enums;

/**
 * @author wen
 * @version 1.0
 * @date 2020/10/25 13:52
 */
public enum TagGroupEnum {
    BASIC("basic", "基础属性"),
    CONSUME("consume", "消费属性"),
    OFFBEHAVIRO("offbehaviro", "行为属性"),
    OPERATE("operate", "运营属性"),
    SPECIAL("special", "专区属性");

    private String code;
    private String val;

    TagGroupEnum(String code, String val) {
        this.code = code;
        this.val = val;
    }

    public String getCode() {
        return code;
    }

    public String getVal() {
        return val;
    }

    public static TagGroupEnum getValByCode(String code) {
        if (code != null) {
            for (TagGroupEnum obj : values()) {
                if (obj.getCode().equalsIgnoreCase(code)) {
                    return obj;
                }
            }
        }
        return null;
    }


}
