package com.wen.awenboot.enums;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

/**
 * @author wen
 * @version 1.0
 * @date 2021/1/11 15:18
 */
@Getter
public enum ApiDetailCntEnum {

    FIVE_G("5G", "5G"),
    GOTONE("GOTONE", "GOTONE"),
    BRAND("BRAND", "BRAND"),
    MUSIC("MUSIC", "MUSIC"),
    VIDEO("VIDEO", "VIDEO"),
    IMEI("IMEI", "IMEI");

    private String code;
    private String val;

    ApiDetailCntEnum(String code, String val) {
        this.code = code;
        this.val = val;
    }

    public static ApiDetailCntEnum getEnumByCode(String code) {
        if (StrUtil.isNotBlank(code)) {
            for (ApiDetailCntEnum obj : values()) {
                if (obj.getCode().equalsIgnoreCase(code)) {
                    return obj;
                }
            }
        }
        return null;
    }
}
