package com.wen.awenboot.biz.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wen
 * @version 1.0
 * @date 2020/9/14 15:32
 */
@Service
public class MapService {

    private Map<String, String> map = new ConcurrentHashMap<>(1024);

    public void put(String key, String val) {
        map.putIfAbsent(key, val);
    }

    public boolean exist(String key) {
        return map.containsKey(key);
    }
}
