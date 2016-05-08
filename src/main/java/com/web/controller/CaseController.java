package com.web.controller;

import com.utils.ConvertUtil;
import com.utils.mail.SentEmailUtils;
import com.web.entity.CaseManage;
import com.web.entity.Patient;
import com.web.entity.User;
import com.web.service.CaseService;
import com.web.service.PatientService;
import com.web.service.UserService;
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

    @Autowired
    UserService userService;

    @RequestMapping(value = "/showlist")
    public String showlist(HttpServletRequest request,
                           HttpServletResponse response) {
        return "/jsp/manage/listcase";
    }

    @RequestMapping(value = "/searchlist", method = RequestMethod.POST)
    @ResponseBody
    public Object searchlist(@RequestBody Map<String, String> param,HttpServletRequest request) {

        User user= (User)request.getSession().getAttribute("user");
        int role=0;
        if(null!=user){
            role=user.getRole();
        }
        if(4==role){
            param.put("userid",ConvertUtil.safeToString(user.getId(),""));
        }
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
                               HttpServletResponse response) throws Exception {
        int id = ConvertUtil.safeToInteger(request.getParameter("id"), 0);
        int userid = ConvertUtil.safeToInteger(request.getParameter("userid"), 0);
        if (0 != id) {
            CaseManage caseManage = caseService.getCaseById(id);
            caseManage.setUserid(userid);
            caseManage.setStatus(2);
            caseService.saveCase(caseManage);

            User user= userService.getUserById(userid);
            if(null!=user){
                SentEmailUtils.sentEmailNullFile(user.getEmail(),user.getName(),caseManage);

            }
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



    @RequestMapping(value="/save",method = RequestMethod.POST)
    @ResponseBody
    public String savaCaseInfo(HttpServletRequest request, HttpServletResponse response) throws ParseException {
        try {
            String form_type = request.getParameter("form_type");

            String patient_name =request.getParameter("patient_name");
            String patient_birth = request.getParameter("patient_birth");
            String gender=request.getParameter("patient_gender");
            int patient_gender =0;
            if(gender.equals("female")){
                patient_gender=1;
            }
            String relation = request.getParameter("relation");
            if(relation.equals("no")){
                String other_relation=request.getParameter("other_relation");
                if(other_relation.equals("1")){
                    relation="父母";
                }else if(other_relation.equals("2")){
                    relation="子女";
                }else if(other_relation.equals("3")){
                    relation="配偶";
                }else {
                    relation="其他";
                }
            }else{
                relation="本人";
            }
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
            String country = request.getParameter("country");
            String doctor_name = request.getParameter("doctor_name");
            String doctor_hospital = request.getParameter("doctor_hospital");
            String doctor_major = request.getParameter("doctor_major");
            CaseManage caseManage = new CaseManage();
            caseManage.setAddress(address_details);
            caseManage.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(patient_birth));
            caseManage.setType(1);

            caseManage.setName(patient_name);
            caseManage.setSex(patient_gender);
            caseManage.setRelation(relation);
            caseManage.setCreatename(applicant_name);
            caseManage.setCity(city);
            caseManage.setProvince(province);
            caseManage.setAddress(address_details);
            caseManage.setZipcode(user_zip);
            caseManage.setPhone1(user_first_phone);
            caseManage.setPhone2(user_second_phone);
            caseManage.setEmail(user_email);
            caseManage.setRemark(info_details);
            caseManage.setPhonetime(user_time);
            caseManage.setDoctor_hospital(doctor_hospital);
            caseManage.setDoctor_name(doctor_name);
            caseManage.setDoctor_major(doctor_major);
            caseManage.setCreatetime(new Date());
            caseManage.setCountry(country);
            caseService.save(caseManage);
            return "ok";
        }catch(Exception e){
            e.printStackTrace();
            return "failure";
        }

    }

    @RequestMapping(value = "/remove",method = RequestMethod.POST)
    @ResponseBody
    public Object removeEnum(@RequestBody List<Map> params){
        List<Integer> ids = new ArrayList<Integer>();
        for (Map map : params) {
            ids.add(Integer.parseInt(map.get("id").toString()));
        }
        caseService.removeCase(ids);
        return "success";
    }

}
