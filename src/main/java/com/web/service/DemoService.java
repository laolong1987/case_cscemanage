package com.web.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.web.dao.DemoDao;
import com.web.entity.Demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by gaoyang on 16/2/29.
 */
@Service("demoService")
public class DemoService {

    @Autowired
    public DemoDao demoDao;

    public List<Demo> searchList(Map map){
        return demoDao.searchDemo(map);
    }

    public void add(Demo demo){
        demoDao.save(demo);
    }

    public Demo get(int id){
        return (Demo) demoDao.getObjectById(id,Demo.class);
    }

    public void del(int id){
        Object o=get(id);
        demoDao.remove(o);
    }

    public void save(Demo demo){
        demoDao.save(demo);
    }
}
