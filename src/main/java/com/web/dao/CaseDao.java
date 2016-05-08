package com.web.dao;


import com.common.BaseDao;
import com.common.SearchTemplate;
import com.utils.ConvertUtil;
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
        Map p=new HashMap();
        sql.append("select  a.id,a.name,a.type,a.status,a.city,a.country,c.name as username,a.note, ");
        sql.append("case(a.type) when '1' then 'Expert Medical Report' ");
        sql.append("when '2' then 'Personal Healthy Advisory' ");
        sql.append("when '3' then 'Stress Management' ");
        sql.append("when '4' then 'Oientation And Navigation' ");
        sql.append("else '' end as typename,date_format(a.birthday,'%Y-%m-%d') as birthday, ");
        sql.append("case(a.sex) when '0' then '男' ");
        sql.append("when '1' then '女' ");
        sql.append("else '' end as sexname,a.relation,date_format(a.createtime,'%Y-%m-%d %H:%i:%s') as createtime,a.province,a.address, ");
        sql.append("a.createname,a.phone1,a.phone2,a.email,a.phonetime,a.remark,a.doctor_name,a.doctor_hospital,a.doctor_major from casemanage a ");
        sql.append("left join patient b on a.createempid=b.id  ");
        sql.append("left join medical.user c on a.userid=c.id  ");
        sql.append("where 1=1 ");
        if (map.containsKey("queryname")){
            sql.append(" and a.name like :queryname ");
            p.put("queryname", "%" + map.get("queryname") + "%");
        }
        if (map.containsKey("queryphone1")){
            sql.append(" and a.phone1 like :queryphone1 ");
            p.put("queryphone1", "%" + map.get("queryphone1") + "%");
        }
        if (map.containsKey("querycountry")){
            sql.append(" and a.country like :querycountry ");
            p.put("querycountry", "%" + map.get("querycountry") + "%");
        }
        if (map.containsKey("querycity")){
            sql.append(" and a.province like :querycity ");
            p.put("querycity", "%" + map.get("querycity") + "%");
        }
        if (map.containsKey("querystatus") && 0!= ConvertUtil.safeToInteger(map.get("querystatus"),0)){
            sql.append(" and a.status =:querystatus ");
            p.put("querystatus", map.get("querystatus"));
        }
        if (map.containsKey("queryservicetype") && 0!= ConvertUtil.safeToInteger(map.get("queryservicetype"),0)){
            sql.append(" and a.type = :queryservicetype ");
            p.put("queryservicetype", map.get("queryservicetype"));
        }
        if (map.containsKey("userid")){
            sql.append(" and a.userid =:userid ");
            p.put("userid",map.get("userid"));
        }
        sql.append("order by a.createtime desc   ");
//        sql.append("order by FIELD(a.status,'3') asc, a.createtime desc   ");
        p.put("pageSize",map.get("pageSize"));
        p.put("page",map.get("page"));
        return super.search(sql.toString(),p);
    }
}
