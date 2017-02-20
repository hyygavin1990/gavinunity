package cn.datawin.dao;

import cn.datawin.entity.Page;
import cn.datawin.util.DateFormat;
import cn.datawin.util.DateUtil;
import cn.datawin.util.ReflectUtil;
import com.mongodb.*;
import org.bson.types.ObjectId;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

/**
 * http://api.mongodb.org/java/2.11.3/ mongodb 基本操作
 * 
 * @author panmg
 * 
 */
public class BaseDao {

	private MongoDB db;

	public MongoDB getDb() {
		return db;
	}

	public void setDb(MongoDB db) {
		this.db = db;
	}
	/**------------------------------公有方法----------------------------------------**/
	/**
	 * insert 
	 * @param bean 对象
	 * @return _id
	 */
	protected String insertAsBean(Object bean) {
		String col = bean.getClass().getSimpleName().toLowerCase();
		Map<String, Object> map = beanToMap(bean);
		return insert(col, map,"");
	}
	
	/**
	 * insert 日表
	 * @param bean 对象
	 * @param date 例yyyyMMdd
	 * @return _id
	 */
	protected String insertAsBeanToDateCol(Object bean, String date) {
		String col = bean.getClass().getSimpleName().toLowerCase()+date;
		Map<String, Object> map = beanToMap(bean);
		return insert(col, map,"");
	}
	
	/**
	 * insert 日库
	 * @param bean
	 * @param date 例yyyyMMdd
	 * @return _id
	 */
	protected String insertAsBeanToDateDb(Object bean, String date) {
		String col = bean.getClass().getSimpleName().toLowerCase();
		Map<String, Object> map = beanToMap(bean);
		return insert(col, map,date);
	}
	
	/**
	 * update 
	 * @param bean
	 * @return
	 */
	protected String saveAsBean(Object bean) {
		String col = bean.getClass().getSimpleName().toLowerCase();
		DBObject dbobj = new BasicDBObject(beanToMap(bean));
		return save(col, dbobj, "");
	}
	
	/**
	 * update 日表
	 * @param bean
	 * @param date
	 * @return
	 */
	protected String saveAsBeanToDateCol(Object bean, String date) {
		String col = bean.getClass().getSimpleName().toLowerCase()+date;
		DBObject dbobj = new BasicDBObject(beanToMap(bean));
		return save(col, dbobj, "");
	}
	
	/**
	 * update 日库
	 * @param bean
	 * @param date
	 * @return
	 */
	protected String saveAsBeanToDateDb(Object bean, String date) {
		String col = bean.getClass().getSimpleName().toLowerCase();
		DBObject dbobj = new BasicDBObject(beanToMap(bean));
		return save(col, dbobj, date);
	}
	
	/**
	 * selectbyid 
	 * @param id
	 * @param clazz
	 * @return
	 */
	protected <T> T getBeanById(String id, Class<T> clazz) {
		String col = clazz.getSimpleName().toLowerCase().toString();
		DBObject obj = new BasicDBObject();
		obj.put("_id", new ObjectId(id));
		BasicDBObject dbObject = (BasicDBObject) findOne(col, obj,"");
		if (dbObject == null){
			return null;
		}
		return mapToBean(dbObject, clazz);
	}
	/**
	 * selectbyid 日表
	 * @param id
	 * @param clazz
	 * @return
	 */
	protected <T> T getBeanByIdFromDateCol(String id, Class<T> clazz, String date) {
		String col = clazz.getSimpleName().toLowerCase().toString()+date;
		DBObject obj = new BasicDBObject();
		obj.put("_id", new ObjectId(id));
		BasicDBObject dbObject = (BasicDBObject) findOne(col, obj,"");
		if (dbObject == null){
			return null;
		}
		return mapToBean(dbObject, clazz);
	}
	
	
	/**
	 * selectbyid 日库
	 * @param id
	 * @param clazz
	 * @return
	 */
	protected <T> T getBeanByIdFromDateDb(String id, Class<T> clazz, String date) {
		String col = clazz.getSimpleName().toLowerCase().toString();
		DBObject obj = new BasicDBObject();
		obj.put("_id", new ObjectId(id));
		BasicDBObject dbObject = (BasicDBObject) findOne(col, obj,date);
		if (dbObject == null){
			return null;
		}
		return mapToBean(dbObject, clazz);
	}
	
	/**
	 * selectbyquery
	 * @param clazz
	 * @param query
	 * @return
	 */
	protected   <T> T getBean(Class<T> clazz, DBObject query) {
		String col = clazz.getSimpleName().toLowerCase().toString();
		BasicDBObject dbObject = (BasicDBObject) findOne(col, query,"");
		if (dbObject == null) {
			return null;
		}
		return mapToBean(dbObject, clazz);
	}
	
	/**
	 * selectbyquery 日表
	 * @param clazz
	 * @param query
	 * @return
	 */
	protected <T> T getBeanFromDateCol(Class<T> clazz, DBObject query, String date) {
		String col = clazz.getSimpleName().toLowerCase().toString()+date;
		BasicDBObject dbObject = (BasicDBObject) findOne(col, query,"");
		if (dbObject == null) {
			return null;
		}
		return mapToBean(dbObject, clazz);
	}
	
	/**
	 * selectbyquery  日库
	 * @param clazz
	 * @param query
	 * @return
	 */
	protected <T> T getBeanFromDateDb(Class<T> clazz, DBObject query, String date) {
		String col = clazz.getSimpleName().toLowerCase().toString();
		BasicDBObject dbObject = (BasicDBObject) findOne(col, query,date);
		if (dbObject == null) {
			return null;
		}
		return mapToBean(dbObject, clazz);
	}
	
	/**
	 * selectlist 
	 * @param clazz
	 * @param query
	 * @param sort
	 * @param page
	 * @return
	 */
	protected <T> List<T> getList(Class<T> clazz, DBObject query, DBObject sort, Page page) {
		String col = clazz.getSimpleName().toLowerCase().toString();
		List<DBObject> list = findList(col, query, sort, page,"");
		return toBeans(list, clazz);
	}
	
	/**
	 * selectlist 日表
	 * @param clazz
	 * @param query
	 * @param sort
	 * @param page
	 * @return
	 */
	protected <T> List<T> getListFromDateCol(Class<T> clazz, DBObject query, DBObject sort, Page page, String date) {
		String col = clazz.getSimpleName().toLowerCase().toString()+date;
		List<DBObject> list = findList(col, query, sort, page,"");
		return toBeans(list, clazz);
	}
	
	/**
	 * selectlist 日库
	 * @param clazz
	 * @param query
	 * @param sort
	 * @param page
	 * @return
	 */
	protected <T> List<T> getListFromDateDb(Class<T> clazz, DBObject query, DBObject sort, Page page, String date) {
		String col = clazz.getSimpleName().toLowerCase().toString();
		List<DBObject> list = findList(col, query, sort, page,date);
		return toBeans(list, clazz);
	}
	
	/**-------------------------advice方法---------------------------------×/
	/**
	 * distinct
	 * @return
	 */
	protected List<?> distinct(Class<?> clazz, String key, DBObject query, String date) {
		String col = clazz.getSimpleName().toLowerCase();
		DBCollection collection = getCollection(col,date);
		return collection.distinct(key, query);
	}
	
	/**
	 * count
	 * @param clazz
	 * @param query
	 * @param date
	 * @return
	 */
	protected long count(Class<?> clazz, DBObject query, String date) {
		String col = clazz.getSimpleName().toLowerCase();
		DBCollection collection = getCollection(col,date);
		return collection.count(query);
	}
	
	/**------------------------------私有方法----------------------------------------**/
	/**
	 * insert
	 * @param col
	 * @param data
	 * @return
	 */
	private String insert(String col, Map<String, ? extends Object> data, String date) {
		DBCollection collection = getCollection(col,date);
		DBObject dbObj = new BasicDBObject(data);
		collection.insert(dbObj, WriteConcern.SAFE);
		return dbObj.get("_id").toString();
	}

	/**
	 * update
	 * @param col
	 * @param obj
	 * @param date
	 * @return
	 */
	private String save(String col, DBObject obj, String date) {
		DBCollection collection = getCollection(col, date);
		collection.save(obj);
		return obj.get("_id").toString();
	}
	
	/**
	 * select one
	 * @param col
	 * @param o
	 * @param date
	 * @return
	 */
	private DBObject findOne(String col, DBObject o, String date) {
		DBCollection collection = getCollection(col,date);
		return collection.findOne(o);
	}

	/**
	 * select list
	 * @param col
	 * @param query
	 * @param sort
	 * @param page
	 * @param date
	 * @return
	 */
	private List<DBObject> findList(String col, DBObject query, DBObject sort, Page page, String date) {
		if (query == null) {
			query = new BasicDBObject();
		}
		DBCursor cursor = find(col, query,date);
		//设置排序
		if (sort != null) {
			cursor.sort(sort);
		}
		//设置分页查询
		if(page!=null){
			//设置总数和分页数
			long totalCount = cursor.count();
			page.setTotalCount(totalCount);
			long totalPage = Math.round(Math.ceil((double)totalCount/(double)page.getPageSize()));
			page.setTotalPage(totalPage);
			int start = (page.getPageNumber() - 1) * page.getPageSize();
			int limit = page.getPageSize();
			if (start == 0) {
				cursor.limit(limit);
			} else {
				cursor.skip(start).limit(limit);
			}
		}
		return DBCursor2list(cursor);
	}
	
	/**
	 * find DBCursor
	 * @param col
	 * @param o
	 * @param date
	 * @return
	 */
	private DBCursor find(String col, DBObject o, String date) {
		DBCollection collection = getCollection(col,date);
		return collection.find(o);
	}


	private List<DBObject> DBCursor2list(DBCursor cur) {
		if (cur != null) {
			ArrayList<DBObject> list = new ArrayList<DBObject>();
			while (cur.hasNext()) {
				DBObject dbo = cur.next();
				list.add(dbo);
			}
			return list;
		}
		return Collections.emptyList();
	}
	/**
	 * 
	 * @param col
	 * @param date
	 * @return
	 */
	private DBCollection getCollection(String col, String date) {
		//如果日库的日期为空，则选为当天
		if(date==null){
			Date now = DateUtil.getTheDay(new Date());
			date = DateFormat.dateFormat(now, "yyyyMMdd");
		}
		return db.getDB(date).getCollection(col);
	}

	private <T> List<T> toBeans(List<DBObject> list, Class<T> clazz) {
		List<T> relist = new ArrayList<T>();
		for (DBObject dbObj : list) {
			BasicDBObject b = (BasicDBObject) dbObj;
			T t = mapToBean(b, clazz);
			relist.add(t);
		}
		return relist;
	}

	protected static class Query extends BasicDBObject {
		private static final long serialVersionUID = 1L;

		protected Query() {
		}

		protected Query(String key, Object value) {
			super(key, value);
		}

		protected Query(Map<?, ?> m) {
			super(m);
		}
	}

	protected Map<String, Object> beanToMap(Object bean) {
		BeanInfo info = null;
		Map<String, Object> map = new HashMap<String, Object>();
		Class<?> _class = bean.getClass();
		try {
			info = Introspector.getBeanInfo(_class);
		} catch (IntrospectionException e) {
			e.printStackTrace();
			return null;
		}
		PropertyDescriptor[] pros = info.getPropertyDescriptors();
		for (PropertyDescriptor pro : pros) {
			String name = pro.getName();
			if (!"class".equals(name)) {
				Method method = pro.getReadMethod();
				try {
					cn.datawin.dao.ObjectId objectId = _class.getDeclaredField(name).getAnnotation(cn.datawin.dao.ObjectId.class);
					Object result = method.invoke(bean);
					if (objectId != null && result != null && ObjectId.isValid(result.toString())) {
						result = new ObjectId((String) result);
					}
					if (result != null) {
						if ("id".equals(name)) {
							name = "_id";
						}
						if(result instanceof BigDecimal){
							map.put(name, ((BigDecimal) result).doubleValue());
						}else{
							map.put(name, result);
						}
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return map;
	}

	protected  <T> T mapToBean(Map<String, ?> map, Class<T> _class) {
		T t;
		try {
			t = _class.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		for (String key : map.keySet()) {
			Object value = map.get(key);
			if ("_id".equals(key)) {
				key = "id";
			}
			String methodName = ReflectUtil.toSetMethod(key);
			Method method = ReflectUtil.getDeclaredMethod(_class, methodName);
			if (method == null || value == null) {
				continue;
			}
			ReflectUtil.invokeMethod(t, method, value);
		}
		return t;
	}
	/** 单条单列,其他列如果没有赋值不修改 */
	protected WriteResult updateSingle(String col, DBObject q, DBObject o) {
		return this.update(col, q, new BasicDBObject("$set", o), false, false);
	}

	/** 批量修改 */
	protected WriteResult updateMulti(String col, DBObject q, DBObject o) {
		return this.update(col, q, o, false, true);
	}
	/** 修改 */
	protected WriteResult update(String col, DBObject q, DBObject o, boolean upsert, boolean multi) {
		DBCollection collection = getCollection(col);
		return collection.update(q, o, upsert, multi);
	}

	/** 单条修改 */
	protected WriteResult update(String col, DBObject q, DBObject o) {
		return this.update(col, q, o, false, false);
	}
	protected DBCollection getCollection(String col){
		return db.getDB().getCollection(col);
	}
	protected DBObject findOne(String col, DBObject o) {
		DBCollection collection = getCollection(col);
		return collection.findOne(o);
}
	protected WriteResult updateByid(String col, String id, DBObject o) {
		DBObject _id = new BasicDBObject("_id", new ObjectId(id));
		BasicDBObject ob = (BasicDBObject) o;
		if (ob.containsField("_id")){
			ob.remove("_id");
		}
		return this.updateSingle(col, _id, o);
	}

	private BasicDBObject initIdsInQuery (BasicDBList idList){
		return new BasicDBObject("_id",new BasicDBObject(QueryOperators.IN,idList));
	}

	protected BasicDBObject iiiq (BasicDBList idList){
		return initIdsInQuery(idList);
	}

}