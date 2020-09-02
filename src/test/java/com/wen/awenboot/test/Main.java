package com.wen.awenboot.test;

import com.wen.awenboot.singleton.SingletonCtxUtil;
import com.wen.awenboot.test.aop.BizService;
import com.wen.awenboot.test.bean.Children;
import com.wen.awenboot.test.bean.Parent;
import com.wen.awenboot.test.config.MainConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author wen
 * @version 1.0
 * @date 2020/5/26 19:56
 */
public class Main {
    static AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MainConfig.class);

    public static void main(String[] args) {
//        testIoc();
        testAop();
    }

    private static void testAop() {
        BizService bean = ctx.getBean(BizService.class);
        bean.test();
//        System.out.println(bean);
    }

    private static void testIoc() {
        Children c1 = SingletonCtxUtil.getBean(Children.class);
        Parent p1 = SingletonCtxUtil.getBean(Parent.class);
        System.out.println("children" + c1);
        System.out.println("parent" + p1);

        Children children = ctx.getBean(Children.class);
        Parent parent = ctx.getBean("parent", Parent.class);
        System.out.println("children" + children);
        System.out.println("parent" + parent);

    }
}
