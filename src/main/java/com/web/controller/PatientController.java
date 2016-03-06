package com.web.controller;

import com.utils.StringUtil;
import com.web.entity.Demo;
import com.web.service.DemoService;
import com.web.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyang on 16/2/28.
 */
@Controller
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    PatientService patientService;

    @RequestMapping(value = "/showlist")
    public String showlist(HttpServletRequest request,
                                HttpServletResponse response) {
        return "/jsp/manage/listpatient";
    }

    @RequestMapping(value = "/searchlist",method = RequestMethod.POST)
    @ResponseBody
    public Object searchlist(@RequestBody Map<String, String> param){

        return patientService.searchPatient(param).getResult();
    }


    @RequestMapping(value = "/savepatient",method = RequestMethod.POST)
    @ResponseBody
    public String savepatient(@RequestBody Map<String, String> param) {
//        patientService.savePartent(param);
        System.out.println(222222222);
        return "success";
    }
//    @RequestMapping(value = "/savepatient",method = RequestMethod.POST)
//    @ResponseBody
//    public String savepatient(HttpServletRequest request,
//                              HttpServletResponse response) {
////        patientService.savePartent(param);
//        System.out.println(1111111);
//        return "success";
//    }

}
