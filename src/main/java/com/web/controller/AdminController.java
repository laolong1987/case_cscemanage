package com.web.controller;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.utils.SettingUtil;
import com.utils.StringUtil;

/**
 * Created by gaoyang on 16/3/1.
 */

@Controller
@RequestMapping("/admin")
public class AdminController {


    @RequestMapping(value = "/login")
    public String login(HttpServletRequest request,
                                HttpServletResponse response) {


        return "/jsp/manage/login";
    }



    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request,
                        HttpServletResponse response) {


        return "/jsp/manage/index";
    }
}
