package com.web.controller;

import com.utils.ConvertUtil;
import com.utils.StringUtil;
import com.web.entity.Demo;
import com.web.entity.Patient;
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
import java.util.*;

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
    public String savepatient(HttpServletRequest request,
                              HttpServletResponse response) {
        int patientid= ConvertUtil.safeToInteger(request.getParameter("patientid"),0);
        String name=ConvertUtil.safeToString(request.getParameter("name"),"");
        String username=ConvertUtil.safeToString(request.getParameter("username"),"");
        String email=ConvertUtil.safeToString(request.getParameter("email"),"");
        String address=ConvertUtil.safeToString(request.getParameter("address"),"");
        String phone=ConvertUtil.safeToString(request.getParameter("phone1"),"");
        int sex=ConvertUtil.safeToInteger(request.getParameter("sex"),0);

        Patient patient=new Patient();
        if(0!=patientid){
            patient = (Patient) patientService.getPatientById(patientid);
            patient.setUpdatetime(new Date());
        }else{
            patient.setCreatetime(new Date());
        }
        patient.setName(name);
        patient.setUsername(username);
        patient.setEmail(email);
        patient.setAddress(address);
        patient.setSex(sex);
        patient.setPwd("123456");
        patient.setPhone1(phone);
        patientService.savePartent(patient);
        return "success";
    }

    @RequestMapping(value = "/remove",method = RequestMethod.POST)
    @ResponseBody
    public Object removeEnum(@RequestBody List<Map> params){
        List<Integer> ids = new ArrayList<Integer>();
        for (Map map : params) {
            ids.add(Integer.parseInt(map.get("id").toString()));
        }
        patientService.removePatient(ids);
        return "success";
    }

}
