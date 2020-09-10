package com.wen.awenboot.biz.service.validation.custom;

import com.wen.awenboot.common.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wen
 * @version 1.0
 * @date 2020/4/3 21:08
 */
public class QueryChecker implements ICustomChecker {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueryChecker.class);

    @Override
    public String check(String str) {
        String pattern = "accessTime='";
        int i = str.indexOf(pattern);
        int idx = i + pattern.length();
        int idx2 = str.indexOf("'", idx);
        if (i > -1 && idx2 > -1) {
            String accessTime = str.substring(idx, idx2);
            if (DateUtils.isLegalDate(accessTime, "yyyy-MM-dd")) {
                return null;
            }
        }
        return "必须包含accessTime='yyyy-MM-dd'格式,=不能有空格";
    }

    public static void main(String[] args) {
        String str = "dsaf accessTime='2001-02-13 fsdaf";
        String pattern = "accessTime='";
        int i = str.indexOf(pattern);
        int idx = i + pattern.length();
        if (i > -1 && str.indexOf("'", idx) > -1) {
            String accessTime = str.substring(idx, str.indexOf("'", idx));
            System.out.println(accessTime);
            System.out.println(DateUtils.isLegalDate(accessTime, "yyyy-MM-dd"));
        }
        System.out.println("end");
    }
}
