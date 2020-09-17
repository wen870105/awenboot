package com.wen.awenboot.hutool.test;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.TypeUtil;
import com.wen.awenboot.hutool.test.bean.Holder;
import com.wen.awenboot.hutool.test.bean.UserTest;

import java.util.List;

/**
 * @author wen
 * @version 1.0
 * @date 2020/9/9 20:06
 */
public class ConvertMain {

    public static void main(String[] args) {

        test0();
        test1();

    }

    private static void test0() {
        int a = 1;
//aStr为"1"
        String aStr = Convert.toStr(a);
        System.out.println(aStr);
        long[] b = {1, 2, 3, 4, 5};
//bStr为："[1, 2, 3, 4, 5]"
        String bStr = Convert.toStr(b);
        System.out.println(bStr);
        Holder<String> h1 = new Holder<>();
        Holder<UserTest> h2 = new Holder<>();
        Class<?> clazz1 = TypeUtil.getClass(h1.getClass());
        Class<?> clazz2 = TypeUtil.getClass(h2.getClass());
        System.out.println("clazz1:" + clazz1);
        System.out.println("clazz2:" + clazz2);
    }

    private static void test1() {
        Object[] a = {"a", "你", "好", "", 1};
        List<String> list = Convert.convert(new TypeReference<List<String>>() {
        }, a);
        List l2 = Convert.convert(List.class, a);
        System.out.println(list);
        System.out.println(l2);
    }
}
