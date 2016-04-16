package com.web.service;

import java.util.List;
import java.util.Map;

import com.common.SearchTemplate;
import com.web.dao.UserDao;
import com.web.entity.Patient;
import com.web.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserService {

    @Autowired
    UserDao userDao;

    public User findUser(User user) {
        List users = userDao.findUser(user);
        if (users.size() == 0) {
            return null;
        } else {
            return (User) users.get(0);
        }
    }

    /**
     * 查询
     *
     * @param map
     *
     * @return
     */
    public SearchTemplate searchUserList(Map map) {
        return userDao.searchUser(map);
    }

    public void removeUser(List<Integer> ids) {
          StringBuffer sql = new StringBuffer("delete from User where id in(:ids)");
          userDao.removeByIds(sql.toString(), ids);
      }

    public User getUserById(int id){
           return (User) userDao.getObjectById(id,User.class);
       }

    public void saveUser(User user){
            userDao.save(user);
        }
}
