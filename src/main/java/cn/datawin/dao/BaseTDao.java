package cn.datawin.dao;

import cn.datawin.entity.Page;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * BaseDao template泛型类
 * Created by hyygavin on 2017/1/23.
 */
public class BaseTDao<T> extends BaseDao {
    private Class<T> cls = null;

    BaseTDao() {
        Class clz = this.getClass();
        ParameterizedType type = (ParameterizedType) clz.getGenericSuperclass();
        Type[] types = type.getActualTypeArguments();
        cls = (Class<T>) types[0];
    }
    public T findByQuery(DBObject query) {
        return super.getBean(cls, query);
    }

    public T findById(String id) {
        DBObject query = new BasicDBObject("_id", new ObjectId(id));
        return findByQuery(query);
    }

    public List<T> getListByQuery(BasicDBObject query, BasicDBObject sort, Page page) {
        return super.getList(cls,query,sort,page);
    }

    private List<T> GL(BasicDBObject q, BasicDBObject s, Page p) {
        return getListByQuery(q,s,p);
    }

    public List<T> getListByQuery(BasicDBObject query, BasicDBObject sort) {
        return GL(query,sort,null);
    }

    public List<T> getListByQuery(BasicDBObject q) {
        return GL(q,null,null);
    }


    public List<T> getListByIds(BasicDBList idList, BasicDBObject sort, Page page) {
        return GL(iiiq(idList),sort,page);
    }

    public List<T> getListByIds(BasicDBList idList, BasicDBObject sort) {
        return GL(iiiq(idList),sort,null);
    }

    public List<T> getListByIds(BasicDBList idList) {
        return GL(iiiq(idList),null,null);
    }

    public String save(T t) {
        return saveAsBean(t);
    }

    public String insert(T t) {
        return insertAsBean(t);
    }

}
