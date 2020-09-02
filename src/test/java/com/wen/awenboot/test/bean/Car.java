package com.wen.awenboot.test.bean;

import com.wen.awenboot.singleton.Singleton;
import lombok.Data;
import org.springframework.stereotype.Service;

/**
 * @author wen
 * @version 1.0
 * @date 2020/5/26 19:54
 */
@Data
@Singleton
@Service
public class Car {
    private String name;
}
