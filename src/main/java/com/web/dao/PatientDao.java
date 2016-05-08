package com.web.dao;


import com.common.BaseDao;
import com.common.SearchTemplate;
import com.web.entity.Demo;
import com.web.entity.Patient;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gaoyang on 16/2/29.
 */
@Repository
public class PatientDao extends BaseDao{

    /**
     * 查询
     * @param map
     * @return
     */
    public SearchTemplate searchPatient(Map map){
        StringBuffer sql =new StringBuffer();
        sql.append("select * from patient where 1=1");
        Map p=new HashMap();
        if (map.containsKey("queryname")){
            sql.append(" and name like :queryname");
            p.put("queryname", "%" + map.get("queryname") + "%");
        }
        if (map.containsKey("queryphone1")){
            sql.append(" and phone1 like :queryphone1");
            p.put("queryphone1", "%" + map.get("queryphone1") + "%");
        }
        p.put("pageSize",map.get("pageSize"));
        p.put("page",map.get("page"));
        return super.search(sql.toString(),p);
    }
}
