package com.wen.awenboot.test;


import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author wen
 * @version 1.0
 * @date 2020/9/9 20:20
 */
@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Accessors(fluent = true)
@Slf4j
@Builder
public class LombokBean {
    private String id;
    private String name;
    private Date createTime;

    public static void main(String[] args) {
//        LombokBean bean = new LombokBean();
//        bean.id("111").name("test");
//        System.out.println(bean);
    }
}
