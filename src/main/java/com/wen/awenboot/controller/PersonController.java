package com.wen.awenboot.controller;

import com.alibaba.fastjson.JSON;
import com.wen.awenboot.biz.service.PersonService;
import com.wen.awenboot.dal.dataobject.Person;
import com.wen.awenboot.dal.dataobject.base.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@RequestMapping("/demo")
@RestController
@Slf4j
public class PersonController {
    @Autowired
    private PersonService service;

    private static int counter = 1;

    @Autowired
    private Environment env;

    public static void main(String[] args) {
        try {
            String fff = FileUtils.readFileToString(new File("D:\\checkout\\awenboot\\target\\classes\\application.yml"), "UTF-8");
            log.info(fff);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("")
    @ResponseBody
    public Object index() {

        log.info("CRUD demo===============");
        Person person = new Person();
        person.setName("test wen");
        person.setAge(++counter);
        person.setAddress("address 11000");

        log.info("CRUD demo:add");
        service.add(person);

        Person person1 = service.selectById(1);
        log.info("CRUD demo:selectById" + JSON.toJSONString(person1));


        Person query = new Person();
        query.setName("test wen");
        Page<Person> page = new Page<Person>();
        page.setPageSize(10);
        page.setCurrentPage(1);
        Page<Person> ret = service.selectPage(query, page);
        log.info("CRUD demo:selectPage" + JSON.toJSONString(ret));
        return ret;
    }

}
