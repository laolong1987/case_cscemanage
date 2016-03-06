package com.web.service;

import com.common.SearchTemplate;
import com.utils.StringUtil;
import com.web.dao.DemoDao;
import com.web.dao.PatientDao;
import com.web.entity.Demo;
import com.web.entity.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Created by gaoyang on 16/2/29.
 */
@Service("patientService")
public class PatientService {

    @Autowired
    public PatientDao  patientDao ;

    /**
     * 查询
     * @param map
     * @return
     */
    public SearchTemplate searchPatient(Map map){
        return patientDao.searchPatient(map);
    }

    public void savePartent(Map map){
        Patient patient=new Patient();
        String id= StringUtil.safeToString("patientid","");
        if(!"".equals(id)){
            patient = (Patient) patientDao.getObjectById(Integer.parseInt(id),Patient.class);
            patient.setUpdatetime(new Date());
        }else{
            patient.setCreatetime(new Date());
        }
        patient.setName(StringUtil.safeToString("name",""));
        patient.setUsername(StringUtil.safeToString("name",""));
        patient.setEmail(StringUtil.safeToString("name",""));
        patient.setAddress(StringUtil.safeToString("name",""));
        patient.setSex(Integer.valueOf(StringUtil.safeToString("name","0")));
        patient.setPwd("123456");
        patient.setPhone(StringUtil.safeToString("phone",""));
        patientDao.save(patient);
    }
}
