package cn.datawin.service;

import cn.datawin.dao.RedisDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 使用 redis 作为缓存的问题
 * @author tomsun
 * 
 */
@Service
public class CacheService {

	private Log log = LogFactory.getLog(this.getClass());
	
	@Resource
	RedisDao redisDao;
	


	public String get(String key) {
		Jedis jedis = null;
		String value = null;
		try {
			jedis = redisDao.getResource();
			value = jedis.get(key);
		} catch (Exception e) {
			redisDao.returnBrokenResource(jedis);
			log.error("get", e);
		} finally {
			redisDao.returnResource(jedis);
		}
		return value;
	}

	public String set(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = redisDao.getResource();
			return jedis.set(key, value);
		} catch (Exception e) {
			redisDao.returnBrokenResource(jedis);
			log.error("set", e);
			return "0";
		} finally {
			redisDao.returnResource(jedis);
		}
	}
	
	public String set(String key, String value, int seconds) {
		Jedis jedis = null;
		try {
			jedis = redisDao.getResource();
			return jedis.setex(key, seconds, value);
		} catch (Exception e) {
			redisDao.returnBrokenResource(jedis);
			log.error("set", e);
			return "0";
		} finally {
			redisDao.returnResource(jedis);
		}
	}

	public Long del(String... keys) {
		Jedis jedis = null;
		try {
			jedis = redisDao.getResource();
			return jedis.del(keys);
		} catch (Exception e) {
			redisDao.returnBrokenResource(jedis);
			log.error("del", e);
			return 0L;
		} finally {
			redisDao.returnResource(jedis);
		}
	}

	public boolean exists(String key){
		Jedis jedis = null;
		try {
			jedis = redisDao.getResource();
			return jedis.exists(key);
		} catch (Exception e) {
			redisDao.returnBrokenResource(jedis);
			log.error("exists", e);
			return false;
		} finally {
			redisDao.returnResource(jedis);
		}
	}
	
	public String sethash(String key,Map<String, String> hash){
		Jedis jedis = null;
		String res = null;
		try {
			jedis = redisDao.getResource();
			res = jedis.hmset(key, hash);
		} catch (Exception e) {
			redisDao.returnBrokenResource(jedis);
			log.error("sethash", e);
		} finally {
			redisDao.returnResource(jedis);
		}
		return res;
	}
	
	public String sethash(String key,Map<String, String> hash, int seconds){
		Jedis jedis = null;
		String res = null;
		try {
			jedis = redisDao.getResource();
			res = jedis.hmset(key, hash);
			jedis.expire(key, seconds);
		} catch (Exception e) {
			redisDao.returnBrokenResource(jedis);
			log.error("sethash", e);
		} finally {
			redisDao.returnResource(jedis);
		}
		return res;
	}
	

	
	public Map<String, String> gethash(String key){
		Jedis jedis = null;
		Map<String, String> res = null;
		try {
			jedis = redisDao.getResource();
			res = jedis.hgetAll(key);
		} catch (Exception e) {
			redisDao.returnBrokenResource(jedis);
			log.error("gethash", e);
		} finally {
			redisDao.returnResource(jedis);
		}
		return res;
	}
	
	public void set(String key, String... values){
		Jedis jedis = null;
		try {
			jedis = redisDao.getResource();
			jedis.lpush(key, values);
		} catch (Exception e) {
			redisDao.returnBrokenResource(jedis);
			log.error("gethash", e);
		} finally {
			redisDao.returnResource(jedis);
		}
	}
	
	public void set(String key, int seconds, String... values){
		Jedis jedis = null;
		try {
			jedis = redisDao.getResource();
			jedis.lpush(key, values);
			jedis.expire(key, seconds);
		} catch (Exception e) {
			redisDao.returnBrokenResource(jedis);
			log.error("gethash", e);
		} finally {
			redisDao.returnResource(jedis);
		}
	}
	
	public List<String> getlist(String key){
		Jedis jedis = null;
		try {
			jedis = redisDao.getResource();
			return jedis.lrange(key, 0, jedis.hlen(key));
		} catch (Exception e) {
			redisDao.returnBrokenResource(jedis);
			log.error("gethash", e);
			return Collections.emptyList();
		} finally {
			redisDao.returnResource(jedis);
		}
	}
	
	/**
	 * 转化date方法
	 * @param time
	 * @return
	 */
	public Date parseToDate(String time){
		try{
			Date date = new Date(Long.valueOf(time));
			return date;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
