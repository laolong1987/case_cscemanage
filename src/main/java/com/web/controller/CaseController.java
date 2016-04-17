package com.web.controller;

import com.utils.ConvertUtil;
import com.web.entity.CaseManage;
import com.web.entity.Patient;
import com.web.service.CaseService;
import com.web.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by gaoyang on 16/2/28.
 */
@Controller
@RequestMapping("/case")
public class CaseController {


    @Autowired
    CaseService caseService;

    @RequestMapping(value = "/showlist")
    public String showlist(HttpServletRequest request,
                           HttpServletResponse response) {
        return "/jsp/manage/listcase";
    }

    @RequestMapping(value = "/searchlist", method = RequestMethod.POST)
    @ResponseBody
    public Object searchlist(@RequestBody Map<String, String> param) {

        return caseService.searchPatient(param).getResult();
    }


    @RequestMapping(value = "/updatestatus",method = RequestMethod.POST)
    @ResponseBody
    public Object updatestatus(HttpServletRequest request,
                               HttpServletResponse response) {
        int id = ConvertUtil.safeToInteger(request.getParameter("id"), 0);
        int status = ConvertUtil.safeToInteger(request.getParameter("status"), 0);
        if (0 != id) {
            CaseManage caseManage = caseService.getCaseById(id);
//            caseManage.setStatus(status);
            caseService.saveCase(caseManage);
        }
        return "success";
    }



    @RequestMapping("/save")
    @ResponseBody
    public String savaCaseInfo(HttpServletRequest request, HttpServletResponse response) throws ParseException {
        try {
            String form_type = request.getParameter("form_type");
            String patient_name =request.getParameter("patient_name");
            String patient_birth = request.getParameter("patient_birth");
            int patient_gender =ConvertUtil.safeToInteger( request.getParameter("patient_gender"),0) ;
            String relation = request.getParameter("relation");
            String applicant_name = request.getParameter("applicant_name");
            String province = request.getParameter("province");
            String city = request.getParameter("city");
            String address_details = request.getParameter("address_details");
            String user_zip = request.getParameter("user_zip");
            String user_first_phone = request.getParameter("user_first_phone");
            String user_second_phone = request.getParameter("user_second_phone");
            String user_email = request.getParameter("user_email");
            String user_time = request.getParameter("user_time");
            String info_details = request.getParameter("info_details");
            String doctor_name = request.getParameter("doctor_name");
            String doctor_hospital = request.getParameter("doctor_hospital");
            String doctor_major = request.getParameter("doctor_major");
            CaseManage caseManage = new CaseManage();
            caseManage.setAddress(address_details);
//            caseManage.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(patient_birth));
            caseManage.setType(Integer.parseInt(form_type));

            caseManage.setName(patient_name);
            caseManage.setSex(patient_gender);
            caseManage.setRelation(relation);
            caseManage.setApply_name(applicant_name);
            caseManage.setCity(city);
            caseManage.setProvince(province);
            caseManage.setAddress(address_details);
            caseManage.setZipcode(user_zip);
            caseManage.setPhone1(user_first_phone);
            caseManage.setPhone2(user_second_phone);
            caseManage.setEmail(user_email);

            caseService.save(caseManage);
            return "ok";
        }catch(Exception e){
            return "failure";
        }

    }

}
