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

    @RequestMapping(value = "/searchlist",method = RequestMethod.POST)
    @ResponseBody
    public Object searchlist(@RequestBody Map<String, String> param){

        return caseService.searchPatient(param).getResult();
    }


    @RequestMapping(value = "/updatestatus",method = RequestMethod.POST)
    @ResponseBody
    public Object updatestatus(HttpServletRequest request,
                               HttpServletResponse response){
        int id= ConvertUtil.safeToInteger(request.getParameter("id"),0);
        int status= ConvertUtil.safeToInteger(request.getParameter("status"),0);
        if (0!=id){
            CaseManage caseManage=caseService.getCaseById(id);
            caseManage.setStatus(status);
            caseService.saveCase(caseManage);
        }

        return "success";
    }

}
