package com.wen.awenboot.biz.service.validation.constraint;

import com.wen.awenboot.common.DateUtils;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 日期格式校验
 *
 * @author wen
 * @version 1.0
 * @date 2020/4/3 19:04
 */
public class DatetimeCheckerImpl implements ConstraintValidator<DatetimeChecker, String> {
    private DatetimeChecker checker;

    @Override
    public void initialize(DatetimeChecker checker) {
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

        String msg = null;
        if ("date".equalsIgnoreCase(checker.type())) {
            if (!isLegalDate(value)) {
                msg = "date=yyyy-MM-dd";
            }
        } else if ("hm".equalsIgnoreCase(checker.type())) {
            if (!isLegalHm(value)) {
                msg = "date=HH:mm";
            }
        }
        if (msg == null) {
            return true;
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(msg + checker.message()).addConstraintViolation();
        return false;
    }

    /**
     * HH:mm时间格式
     *
     * @param str
     * @return
     */
    private boolean isLegalHm(String str) {
        if (str.length() != 5) {
            return false;
        }
        String[] arr = str.split(":");
        try {
            int hh = Integer.parseInt(arr[0]);
            int mm = Integer.parseInt(arr[1]);
            return (hh >= 0 && hh < 24) && (mm >= 0 && mm < 60);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isLegalDate(String sDate) {
        return DateUtils.isLegalDate(sDate, "yyyy-MM-dd");
    }

}
