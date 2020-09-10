package com.wen.awenboot.biz.service.validation.constraint;

import com.wen.awenboot.biz.service.validation.custom.ICustomChecker;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 自定义校验
 *
 * @author wen
 * @version 1.0
 * @date 2020/4/3 19:04
 */
public class CustomCheckerImpl implements ConstraintValidator<CustomChecker, String> {
    private CustomChecker checker;

    @Override
    public void initialize(CustomChecker checker) {
        this.checker = checker;
    }

    /**
     * 检验逻辑
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        StringBuilder sb = new StringBuilder();
        int i = 0;
        Class<? extends ICustomChecker>[] classes = checker.classes();
        for (Class<? extends ICustomChecker> clz : classes) {
//            ICustomChecker checker = SingletonCtxUtil.getBean(clz);
//            String check = checker.check(value);
//            if (StringUtils.isNotEmpty(check)) {
//                sb.append(check);
//                if (i < classes.length - 1) {
//                    sb.append(",");
//                }
//            }
        }
        if (sb.length() == 0) {
            return true;
        }

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(sb.toString()).addConstraintViolation();
        return false;
    }
}
