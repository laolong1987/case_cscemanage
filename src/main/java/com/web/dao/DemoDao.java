package com.web.dao;


import java.util.List;
import java.util.Map;


import com.common.BaseDao;
import com.web.entity.Demo;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
/**
 * Created by gaoyang on 16/2/29.
 */
@Repository
public class DemoDao extends BaseDao{

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * 查询
     * @param map
     * @return
     */
    public List<Demo> searchDemo(Map map){
        StringBuffer sql =new StringBuffer();
        sql.append(" from Demo where 1=1");
        Query query = sessionFactory.getCurrentSession().createQuery(sql.toString());
        return query.list();
    }
}
