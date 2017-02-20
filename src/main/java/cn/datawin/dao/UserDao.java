package cn.datawin.dao;

import cn.datawin.bean.User;
import com.mongodb.BasicDBObject;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hyygavin on 2016/11/11.
 */
@Repository
public class UserDao extends BaseTDao<User> {

    @Override
    @Resource(name = "mongoDB")
    public void setDb(MongoDB db) {
        super.setDb(db);
    }

    public List<User> getListByName(String name) {
        BasicDBObject q = new BasicDBObject("name",name);
        return getListByQuery(q);
    }

    public User findByName(String name) {
        BasicDBObject q = new BasicDBObject("name",name);
        return findByQuery(q);
    }
}
