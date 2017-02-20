package cn.datawin.dao;

import com.mongodb.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class MongoDB {

	private String db;
	private String hosts;
	private String username;
	private String password;
	private int port;
	private int connectionsPerHost;
	private int threadsAllowedToBlockForConnectionMultiplier;
	private boolean autoConnectRetry;
	private int maxWaitTime;
	
	private Log log = LogFactory.getLog(this.getClass());
	
	private MongoClient client = null;
	
	public MongoDB() {
	}

	public MongoDB(String db, String hosts, int port, int connectionsPerHost, int threadsAllowedToBlockForConnectionMultiplier, boolean autoConnectRetry, int maxWaitTime) {
		this.db = db;
		this.hosts = hosts;
		this.port = port;
		this.connectionsPerHost = connectionsPerHost;
		this.threadsAllowedToBlockForConnectionMultiplier = threadsAllowedToBlockForConnectionMultiplier;
		this.autoConnectRetry = autoConnectRetry;
		this.maxWaitTime = maxWaitTime;
	}
	
	
	public void init() {
		
		// 这里可写入配置文件
		try {
			client = new MongoClient(getServerAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		MongoOptions options = client.getMongoOptions();
        options.setReadPreference(ReadPreference.secondaryPreferred());
		options.setConnectionsPerHost(getConnectionsPerHost()); // 每个线程的连接数
		options.setThreadsAllowedToBlockForConnectionMultiplier(
				getThreadsAllowedToBlockForConnectionMultiplier()); // 默认5个线程
		options.setAutoConnectRetry(isAutoConnectRetry()); // 这个控制是否在一个连接时，系统会自动重试
		log.info("init MongoDb success --> "+ client);

		/**
		 * maxWaitTime:最大等待连接的线程阻塞时间 connectTimeout：连接超时的毫秒。0是默认和无限
		 * socketTimeout：socket超时。0是默认和无限
		 * autoConnectRetry：这个控制是否在一个连接时，系统会自动重
		 */
	}
	
	public void cleanUp(){
		System.out.println("------cleanUp--------");
		
	}
	
	public DB getDB(){
		DB db =  client.getDB(getDb());
		db.authenticate(username, password.toCharArray());
		return db;
	}
	
	public DB getDB(String date){
		DB db =  client.getDB(getDb()+date);
		db.authenticate(username, password.toCharArray());
		return db;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHosts() {
		return hosts;
	}

	public void setHosts(String hosts) {
		this.hosts = hosts;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	public List<ServerAddress> getServerAddress() throws UnknownHostException {
		ArrayList<ServerAddress> list = new ArrayList<ServerAddress>(5);
		for(String host: hosts.split(",")){
			ServerAddress address = new ServerAddress(host, port);
			list.add(address);
		}
		return list;
	}

	public int getConnectionsPerHost() {
		return connectionsPerHost;
	}

	public void setConnectionsPerHost(int connectionsPerHost) {
		this.connectionsPerHost = connectionsPerHost;
	}

	public int getThreadsAllowedToBlockForConnectionMultiplier() {
		return threadsAllowedToBlockForConnectionMultiplier;
	}

	public void setThreadsAllowedToBlockForConnectionMultiplier(int threadsAllowedToBlockForConnectionMultiplier) {
		this.threadsAllowedToBlockForConnectionMultiplier = threadsAllowedToBlockForConnectionMultiplier;
	}

	public boolean isAutoConnectRetry() {
		return autoConnectRetry;
	}

	public void setAutoConnectRetry(boolean autoConnectRetry) {
		this.autoConnectRetry = autoConnectRetry;
	}

	public int getMaxWaitTime() {
		return maxWaitTime;
	}

	public void setMaxWaitTime(int maxWaitTime) {
		this.maxWaitTime = maxWaitTime;
	}

	public void setDb(String db) {
		this.db = db;
	}

	public String getDb() {
		return db;
	}

}
