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
        sql.append("select * from casemanage a left join patient b on a.createempid=b.id where 1=1");
        Map p=new HashMap();
        return super.search(sql.toString(),p);
    }
}
