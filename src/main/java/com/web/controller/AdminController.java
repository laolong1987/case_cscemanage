package com.web.controller;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.utils.*;
import com.web.entity.User;
import com.web.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * Created by gaoyang on 16/3/1.
 */

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "/jsp/manage/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(User user, HttpServletRequest request) {
        if (user.getUsername() != null && !"".equals(user.getUsername()) && user.getPwd() != null && !"".equals(user.getPwd())) {
            String pwd = MD5Util.string2MD5(user.getPwd() + user.getUsername());
            user.setPwd(pwd);
            User admin = userService.findUser(user);
            if (admin != null) {
                request.getSession().setAttribute("user", admin);
                return "redirect:index";
            } else {
                request.setAttribute("tip", "用户名或密码错误");
                return "/jsp/manage/login";
            }
        }

        return "/jsp/manage/login";
    }


    @RequestMapping(value = "/savepassword", method = RequestMethod.POST)
    @ResponseBody
    public String save_password( HttpServletRequest request,HttpServletResponse response) {
        String result="";
        User user= (User) request.getSession().getAttribute("user");
        String password = request.getParameter("password");
        String confirmPwd =request.getParameter("confirmPwd");
        if( !"".equals(confirmPwd) && !"".equals(password) && password!=null && confirmPwd!=null ){
            if(password.equals(confirmPwd)){
                String pwd = MD5Util.string2MD5(password + user.getUsername());
                user.setPwd(pwd);
                userService.saveUser(user);
                result="success";
            }else{
                result="The passwords you typed do not match. Type the same password in both text boxes.";
            }
        }else{
            result="password can't be null";
        }
        return result;
    }


    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request,
                        HttpServletResponse response) {
        return "/jsp/manage/index";
    }

    /**
     * 退出清空session
     *
     * @param request
     * @param response
     *
     * @return
     */
    @RequestMapping(value = "/logout")
    public String removeuser(HttpServletRequest request,
                             HttpServletResponse response) {
        request.getSession().removeAttribute("user");
        return "redirect:/admin/login";
    }

}
