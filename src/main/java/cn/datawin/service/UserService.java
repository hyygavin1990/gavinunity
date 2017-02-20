package cn.datawin.service;

import cn.datawin.bean.User;
import cn.datawin.dao.UserDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hyygavin on 2016/11/23.
 */
@Service
public class UserService {
    @Resource
    private UserDao userDao;

    public void save(User user) {
        userDao.save(user);
    }

    public List<User> getListByName(String name) {
        return userDao.getListByName(name);
    }

    public User findByName(String name) {
        return userDao.findByName(name);
    }
}
