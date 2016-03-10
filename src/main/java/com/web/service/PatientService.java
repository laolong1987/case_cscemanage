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

    public void savePartent(Patient patient){

        patientDao.save(patient);
    }

    public Patient getPatientById(int id){
        return (Patient) patientDao.getObjectById(id,Patient.class);
    }

    public void removePatient(List<Integer> ids) {
        StringBuffer sql = new StringBuffer("delete from Patient where id in(:ids)");
        patientDao.removeByIds(sql.toString(), ids);
    }

}
