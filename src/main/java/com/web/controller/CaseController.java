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

        return caseService.searchCase(param).getResult();
    }


    @RequestMapping(value = "/updatestatus",method = RequestMethod.POST)
    @ResponseBody
    public Object updatestatus(HttpServletRequest request,
                               HttpServletResponse response) {
        int id = ConvertUtil.safeToInteger(request.getParameter("id"), 0);
        int status = ConvertUtil.safeToInteger(request.getParameter("status"), 0);
        if (0 != id) {
            CaseManage caseManage = caseService.getCaseById(id);
            caseManage.setStatus(status);
            caseService.saveCase(caseManage);
        }
        return "success";
    }

    @RequestMapping(value = "/updateuserid",method = RequestMethod.POST)
    @ResponseBody
    public Object updateuserid(HttpServletRequest request,
                               HttpServletResponse response) {
        int id = ConvertUtil.safeToInteger(request.getParameter("id"), 0);
        int userid = ConvertUtil.safeToInteger(request.getParameter("userid"), 0);
        if (0 != id) {
            CaseManage caseManage = caseService.getCaseById(id);
            caseManage.setUserid(userid);
            caseManage.setStatus(2);
            caseService.saveCase(caseManage);
        }
        return "success";
    }


    @RequestMapping(value = "/updatenote",method = RequestMethod.POST)
    @ResponseBody
    public Object updatenote(HttpServletRequest request,
                               HttpServletResponse response) {
        int id = ConvertUtil.safeToInteger(request.getParameter("id"), 0);
        String note = ConvertUtil.safeToString(request.getParameter("note"), "");
        if (0 != id) {
            CaseManage caseManage = caseService.getCaseById(id);
            caseManage.setNote(note);
            caseService.saveCase(caseManage);
        }
        return "success";
    }



    @RequestMapping("/save")
    @ResponseBody
    public String savaCaseInfo(HttpServletRequest request, HttpServletResponse response) throws ParseException {
        try {
            String form_type = request.getParameter("form-type");
            String patient_name = request.getParameter("patient-name");
            String patient_birth = request.getParameter("patient-birth");
            String patient_gender = request.getParameter("patient-gender");
            String relation = request.getParameter("relation");
            String applicant_name = request.getParameter("applicant-name");
            String province = request.getParameter("province");
            String city = request.getParameter("city");
            String address_details = request.getParameter("address-details");
            String user_zip = request.getParameter("user-zip");
            String user_first_phone = request.getParameter("user-first-phone");
            String user_second_phone = request.getParameter("user-second-phone");
            String user_email = request.getParameter("user-email");
            String user_time = request.getParameter("user-time");
            String info_details = request.getParameter("info-details");
            String doctor_name = request.getParameter("doctor-name");
            String doctor_hospital = request.getParameter("doctor-hospital");
            String doctor_major = request.getParameter("doctor-major");
            CaseManage caseManage = new CaseManage();
            caseManage.setAddress(address_details);
            caseManage.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(patient_birth));
            caseManage.setType(Integer.parseInt(form_type));
            caseService.save(caseManage);
            return "ok";
        }catch(Exception e){
            return "failure";
        }

    }

}
