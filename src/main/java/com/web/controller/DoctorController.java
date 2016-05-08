package com.web.controller;


import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.utils.ConvertUtil;
import com.utils.MD5Util;
import com.web.entity.Patient;
import com.web.entity.User;
import com.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("doctor")
public class DoctorController {

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/showlist")
    public String showlist(HttpServletRequest request, HttpServletResponse response) {
        User admin = (User) request.getSession().getAttribute("user");
        String role = "";
        if (admin.getRole() == 1) {
            role = "{\"text\":\"case director\",\"id\":2},{\"text\":\"case coordinator\",\"id\":3},{\"text\":\"case manager\",\"id\":4}";
        } else if (admin.getRole() == 2) {
            role = "{\"text\":\"case coordinator\",\"id\":3},{\"text\":\"case manager\",\"id\":4}";
        } else if (admin.getRole() == 3) {
            role = "{\"text\":\"case manager\",\"id\":4}";
        }
        request.setAttribute("role", role);
        return "/jsp/manage/listdoctor";
    }

    @RequestMapping(value = "/searchlist", method = RequestMethod.POST)
    @ResponseBody
    public Object searchlist(@RequestBody Map<String, String> param, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        param.put("role",String.valueOf(user.getRole()));
        return userService.searchUserList(param).getResult();
    }


    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public Object removeEnum(@RequestBody List<Map> params) {
        List<Integer> ids = new ArrayList<Integer>();
        for (Map map : params) {
            ids.add(Integer.parseInt(map.get("id").toString()));
        }
        userService.removeUser(ids);
        return "success";
    }

    @RequestMapping(value = "/adduser", method = RequestMethod.POST)
    @ResponseBody
    public String adduser(HttpServletRequest request,
                          HttpServletResponse response) {
        int keyid = ConvertUtil.safeToInteger(request.getParameter("keyid"), 0);
        String name = ConvertUtil.safeToString(request.getParameter("name"), "");
        String username = ConvertUtil.safeToString(request.getParameter("username"), "");
//        String email = ConvertUtil.safeToString(request.getParameter("email"), "");
//        String domain = ConvertUtil.safeToString(request.getParameter("domain"), "");
//        String company = ConvertUtil.safeToString(request.getParameter("company"), "");
        String domain = "advance-medical.com.cn";
        String company = "Advance Medical";
        int role = ConvertUtil.safeToInteger(request.getParameter("role_"), 0);

        User user = new User();
        if (0 != keyid) {
            user = userService.getUserById(keyid);
            user.setUpdatetime(new Date());
        } else {
            user.setCreatetime(new Date());
        }
        user.setName(name);
        if (!username.contains("@")) {
            user.setUsername(username + "@" + domain);
            user.setEmail(username + "@" + domain);

        } else {
            user.setUsername(username);
            user.setEmail(username);
        }
        user.setDomain(domain);

        String pwd = MD5Util.string2MD5("111111" + user.getUsername());
        user.setPwd(pwd);
        user.setCompany(company);
        user.setRole(role);
        user.setState(1);
        userService.saveUser(user);
        return "success";
    }

    @RequestMapping(value = "/resetpwd", method = RequestMethod.POST)
    @ResponseBody
    public String reSetPwd(HttpServletRequest request, HttpServletResponse response) {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            User user = userService.getUserById(id);
            String pwd = MD5Util.string2MD5("111111" + user.getUsername());
            user.setPwd(pwd);
            user.setUpdatetime(new Date());
            userService.saveUser(user);
            return "success";
        } catch (Exception e) {
            return "failure";
        }
    }

    @RequestMapping(value = "/resetstate", method = RequestMethod.POST)
    @ResponseBody
    public String reSetState(HttpServletRequest request, HttpServletResponse response) {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            int state = Integer.parseInt(request.getParameter("state"));
            User user = userService.getUserById(id);
            user.setState(state);
            userService.saveUser(user);
            return "success";
        } catch (Exception e) {
            return "failure";
        }

    }
}
