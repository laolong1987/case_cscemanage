package com.web.dao;


import java.util.*;

import com.common.BaseDao;
import com.common.SearchTemplate;
import com.web.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends BaseDao {

    public List findUser(User user) {
        StringBuffer sql = new StringBuffer();
        sql.append("From User u where 1=1");
        if (user.getUsername() != null && !"".equals(user.getUsername())) {
            sql.append(" and u.username =:username");
        }
        if (user.getPwd() != null && !"".equals(user.getPwd())) {
            sql.append(" and u.pwd =:pwd");
        }
        return super.findObjects(sql.toString(), user);
    }

    public SearchTemplate searchUser(Map map) {
        StringBuffer sql = new StringBuffer();
        sql.append("select * from user where 1=1");
        Map p = new HashMap();
        if (map.containsKey("queryname")) {
            sql.append(" and name like :queryname");
            p.put("queryname", "%" + map.get("queryname") + "%");
        }
        if (map.containsKey("queryphone1")) {
            sql.append(" and phone1 like :queryphone1");
            p.put("queryphone1", "%" + map.get("queryphone1") + "%");
        }
        if (map.containsKey("role")) {
            sql.append(" and role > :role");
            p.put("role",  map.get("role") );
        }
        return super.search(sql.toString(), p);
    }
}
