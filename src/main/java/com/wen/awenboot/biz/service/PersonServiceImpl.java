/**
 * LY.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.wen.awenboot.biz.service;

import com.wen.awenboot.biz.service.base.BaseServiceImpl;
import com.wen.awenboot.dal.dao.PersonDao;
import com.wen.awenboot.dal.dao.base.BaseDao;
import com.wen.awenboot.dal.dataobject.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * person memo
 *
 * @author Wen
 * @since 2020-05-25
 */
@Service("personService")
public class PersonServiceImpl extends BaseServiceImpl<Person> implements PersonService {

    @Autowired
    private PersonDao personDao;

    public BaseDao<Person> getDao() {
        return personDao;
    }

}
