package com.wen.awenboot.test.juc;

import com.alibaba.fastjson.JSON;

import java.util.Arrays;
import java.util.List;

/**
 * @author wen
 * @version 1.0
 * @date 2020/9/1 16:54
 */
public class User {
    private String name;
    private List<String> list;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public static void main(String[] args) {
        User user = new User();
        user.setName("test");
        user.setList(Arrays.asList("l1", "l2"));
        System.out.println(JSON.toJSONString(user));
    }
}
