package com.web.dao;


import com.common.BaseDao;
import com.common.SearchTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyang on 16/2/29.
 */
@Repository
public class CaseDao extends BaseDao{

    /**
     * 查询
     * @param map
     * @return
     */
    public SearchTemplate searchCase(Map map){
        StringBuffer sql =new StringBuffer();
        sql.append("select  a.id,a.name,a.type,a.status,a.city,a.country,c.name as username,a.note, ");
        sql.append("case(a.type) when '1' then 'Expert Medical Report' ");
        sql.append("when '2' then 'Personal Healthy Advisory' ");
        sql.append("when '3' then 'Stress Management' ");
        sql.append("when '4' then 'Oientation And Navigation' ");
        sql.append("else '' end as typename,date_format(a.birthday,'%Y-%m-%d') as birthday, ");
        sql.append("case(a.sex) when '0' then '男' ");
        sql.append("when '1' then '女' ");
        sql.append("else '' end as sexname,a.relation,a.createname,a.province,a.address, ");
        sql.append("a.phone1,a.phone2,a.email,a.phonetime,a.remark,a.doctor_name,a.doctor_hospital,a.doctor_major from casemanage a ");
        sql.append("left join patient b on a.createempid=b.id  ");
        sql.append("left join medical.user c on a.userid=c.id  ");
        sql.append("where 1=1 ");
        sql.append("order by a.createtime desc   ");
        Map p=new HashMap();
        return super.search(sql.toString(),p);
    }
}
