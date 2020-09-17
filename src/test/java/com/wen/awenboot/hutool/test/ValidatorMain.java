package com.wen.awenboot.hutool.test;

import cn.hutool.core.lang.Validator;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wen
 * @version 1.0
 * @date 2020/9/9 19:35
 */
@Slf4j
public class ValidatorMain {
    public static void main(String[] args) {
        boolean isEmail = Validator.isEmail("wenshenyong@migu.cn");
        log.info("isEmail={}", isEmail);
        boolean isMobile = Validator.isMobile("18382471234");
        log.info("isMobile={}", isMobile);
        // 会校验:城市 生日 校验位等
        boolean isId = Validator.isCitizenId("110101199003078232");
        log.info("身份证校验isId={}", isId);
    }


}
