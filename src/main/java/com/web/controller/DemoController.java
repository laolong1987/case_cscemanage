package com.web.controller;

import com.utils.StringUtil;
import com.web.entity.Demo;
import com.web.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * Created by gaoyang on 16/2/28.
 */
@Controller
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    DemoService demoService;

    @RequestMapping(value = "/show")
    public String show(HttpServletRequest request,
                                HttpServletResponse response) {

        request.setAttribute("list",demoService.searchList(new HashMap()));

        return "/jsp/demo";
    }

    @RequestMapping(value = "/add")
    public String add(HttpServletRequest request,
                                         HttpServletResponse response) {

        String id= StringUtil.safeToString(request.getParameter("id"),"");
        String name= StringUtil.safeToString(request.getParameter("name"),"");

        Demo demo=new Demo();
        if(!"".equals(id)){
            demo=demoService.get(Integer.parseInt(id));
        }
        demo.setName(name);
        demoService.save(demo);
        return show(request,response);
    }

    @RequestMapping(value = "/del")
    public void del(HttpServletRequest request,
                      HttpServletResponse response) {

        String id= StringUtil.safeToString(request.getParameter("id"),"");

        if(!"".equals(id)){
            demoService.del(Integer.parseInt(id));
        }

    }

}
