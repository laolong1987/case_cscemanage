package com.web.service;

import com.common.BaseDao;
import com.common.SearchTemplate;
import com.web.dao.CaseDao;
import com.web.dao.PatientDao;
import com.web.entity.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * Created by gaoyang on 16/2/29.
 */
@Service("caseService")
public class CaseService extends BaseDao {

    @Autowired
    public CaseDao caseDao ;

    /**
     * 查询
     * @param map
     * @return
     */
    public SearchTemplate searchPatient(Map map){
        return caseDao.searchCase(map);
    }


}
