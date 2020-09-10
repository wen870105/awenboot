package com.wen.awenboot.hutool.date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author wen
 * @version 1.0
 * @date 2020/9/9 20:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class LombokBean<T> {
    private String id;
    private String name;
    private Date createTime;
    private T data;
}
