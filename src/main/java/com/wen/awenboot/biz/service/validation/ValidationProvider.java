package com.wen.awenboot.biz.service.validation;

import com.wen.awenboot.biz.service.validation.validator.IValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author wen
 * @version 1.0
 * @date 2020/3/25 17:22
 */
@Service
public class ValidationProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationProvider.class);
//    private Map<Class<Object>, IValidator> map = new HashMap<>();

//    @Override
//    public void onEvent(SingletonContainer ctx) {
//        Collection<IValidator> ret = ctx.getBeansOfType(IValidator.class).values();
//        ret.forEach(clazz -> {
//            try {
//                IValidator val = clazz;
//                map.put(val.getValidatorKey(), val);
//            } catch (Exception e) {
//                LOGGER.error("", e);
//            }
//        });
//        LOGGER.info("初始化验证组件:数量={},keys={}", map.size(), map.keySet().toString());
//    }

    public <T> ValidationResult<T> validate(T obj) {
        Map<String, String> validate = retrieveValidator(obj).validate(obj);
        return ValidationResult.build(obj, validate);
    }

    public IValidator retrieveValidator(Object obj) {
//        IValidator val = map.get(obj.getClass());
//        if (val == null) {
//            val = map.get(null);
//        }
        return null;
    }


}
