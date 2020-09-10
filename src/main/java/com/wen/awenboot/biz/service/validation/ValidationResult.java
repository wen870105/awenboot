package com.wen.awenboot.biz.service.validation;

import java.util.Map;

/**
 * 验证结果
 *
 * @author wen
 * @version 1.0
 * @date 2020/4/3 16:14
 */
public class ValidationResult<T> {
    /**
     * 待验证对象
     */
    private T request;

    /**
     * 验证结果
     */
    private Map<String, String> validateMap;

    public static <T> ValidationResult<T> build(T request, Map<String, String> validateMap) {
        ValidationResult<T> obj = new ValidationResult<>();
        obj.setRequest(request);
        obj.setValidateMap(validateMap);
        return obj;
    }

    /**
     * 验证成功
     *
     * @return
     */
    public boolean isSuccess() {
        return validateMap == null || validateMap.isEmpty();
    }

    public T getRequest() {
        return request;
    }

    public void setRequest(T request) {
        this.request = request;
    }

    public Map<String, String> getValidateMap() {
        return validateMap;
    }

    public void setValidateMap(Map<String, String> validateMap) {
        this.validateMap = validateMap;
    }
}
