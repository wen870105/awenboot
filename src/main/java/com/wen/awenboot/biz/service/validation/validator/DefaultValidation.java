package com.wen.awenboot.biz.service.validation.validator;

import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author wen
 * @version 1.0
 * @date 2020/3/25 16:18
 */
public class DefaultValidation implements IValidator<Object> {
    /**
     * 开启快速结束模式 failFast (true)
     */
    private static Validator validator = Validation.byProvider(HibernateValidator.class).configure().failFast(false).buildValidatorFactory().getValidator();

    /**
     * 校验
     *
     * @param t
     * @return
     */
    public static Map<String, String> validateBean(Object t) {
        Set<ConstraintViolation<Object>> ret = validator.validate(t);
        boolean hasError = ret != null && ret.size() > 0;
        if (hasError) {
            Map<String, String> map = new HashMap<>((int) (ret.size() * 1.5));
            ret.forEach((a) -> {
                map.put(a.getPropertyPath().toString(), a.getMessage());
            });
            return map;
        }
        return null;
    }


    @Override
    public Map<String, String> validate(Object obj) {
        return validateBean(obj);
    }

    @Override
    public Class<?> getValidatorKey() {
        return null;
    }
}
