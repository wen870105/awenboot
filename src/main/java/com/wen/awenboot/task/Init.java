package com.wen.awenboot.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

/**
 * @author wen
 * @version 1.0
 * @date 2020/9/1 19:57
 */
@Service
@Slf4j
public class Init {

    @Autowired
    private RestTemplate restTemplate;

    @PostConstruct
    private void init() {
        String uri = "http://www.baidu.com";
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//        HttpEntity<String> entity = new HttpEntity<String>(headers);
//        String strbody=restTemplate.exchange(uri, HttpMethod.GET, entity,String.class).getBody();
//        WeatherResponse weatherResponse= JSONObject.parseObject(strbody,WeatherResponse.class);
//        return weatherResponse;
        log.info("================");
        log.info("================");
        log.info("================");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        String body = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class).getBody();
        log.info(body);

    }
}

