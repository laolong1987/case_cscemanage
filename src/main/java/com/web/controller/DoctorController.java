package com.web.controller;


import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.utils.ConvertUtil;
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
        return "/jsp/manage/listdoctor";
    }

    @RequestMapping(value = "/searchlist", method = RequestMethod.POST)
    @ResponseBody
    public Object searchlist(@RequestBody Map<String, String> param) {

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
        int patientid = ConvertUtil.safeToInteger(request.getParameter("patientid"), 0);
        String name = ConvertUtil.safeToString(request.getParameter("name"), "");
        String username = ConvertUtil.safeToString(request.getParameter("username"), "");
        String email = ConvertUtil.safeToString(request.getParameter("email"), "");
        String address = ConvertUtil.safeToString(request.getParameter("address"), "");
        String phone = ConvertUtil.safeToString(request.getParameter("phone1"), "");
        int sex = ConvertUtil.safeToInteger(request.getParameter("sex"), 0);

        User user = new User();
        if (0 != patientid) {
            user = (User) userService.getPatientById(patientid);
            user.setUpdatetime(new Date());
        } else {
            user.setCreatetime(new Date());
        }
        user.setName(name);
        user.setUsername(username);
        user.setEmail(email);
        user.setAddress(address);
        user.setGender(sex);
        user.setPwd("111111");
        user.setPhone(phone);
        userService.saveUser(user);
        return "success";
    }
}
