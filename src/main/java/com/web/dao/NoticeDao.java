package com.web.dao;

import java.util.HashMap;
import java.util.Map;

import com.common.BaseDao;
import com.common.SearchTemplate;
import org.springframework.stereotype.Repository;

/**
 * <p>TODO</p>
 *
 * @author <a href="mailto:lctang@foxmail.com">Sukey</a>
 * @version 1.0
 */
@Repository
public class NoticeDao extends BaseDao {

    public SearchTemplate searchNotice(Map map) {
        StringBuffer sql = new StringBuffer();
        sql.append("select * from notice where 1=1");
        Map p = new HashMap();
        if (map.containsKey("userid")) {
            sql.append(" and userid :userid");
            p.put("userid", map.get("userid"));
        }
        if (map.containsKey("state")) {
            sql.append(" and state :state");
            p.put("state", map.get("state"));
        }
        return search(sql.toString(), p);
    }
}
