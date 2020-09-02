package com.wen.awenboot.test.bean;

import com.wen.awenboot.singleton.Inject;
import com.wen.awenboot.singleton.Singleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wen
 * @version 1.0
 * @date 2020/5/26 19:48
 */
@Singleton
@Service
public class Parent {
    @Inject

    private Car car;

    public Car getCar() {
        return car;
    }

    @Autowired
    public void setCar(Car car) {
        this.car = car;
    }
}
