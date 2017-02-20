package cn.datawin.entity;

import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Constants {
	public static Map<String, String> apps = new HashMap<String, String>();
	public static Map<String, String> imgs = new HashMap<String, String>(); 
	
	{
		apps.put("msgtemp", "您正在使用微推联盟手机校验, 请确定本人操作, 校验码:${code}, 拒收消息回N【微推联盟】");
	}
	
	public static final int QUERY_ALL=-1000;
	

	//任务
	public static interface Task{
		public static final int STARTED=3;//已开始
		//status只存以下3个值
		public static final int NOT_STARTED=0;//未开始	
		public static final int UNDERWAY=1;//进行中
		public static final int FINISHED=2;//已结束
		
		public static final int TYPE_CPC=0;//CPC
		public static final int TYPE_CPA=1;//CPA
		public static final int TYPE_CPC_CPA=2;//CPC加CPA
		
		public static final int CREAT_PM=0;//pm创建
		public static final int CREAT_USER=1;//user创建
		
		public static final int SCENE_EQX = 0;//易企秀
		public static final int SCENE_P = 1;//自己制作
		public static final int SCENE_WX = 2;//微信文章
		public static final int SCENE_URL = 3;//外链
		
		public static final int SHOW = 0;//首页显示
		public static final int HIDE = 1;//首页显示
	}



	
	/**
	 * 取得上传地址
	 * @return
	 */
	public static String getUploadPath(){
		String upath =  apps.get("uploadpath");
		if(StringUtils.isBlank(upath)){
			return "";
		}
		File file = new File(upath);
		if(file.isAbsolute()){
			if(!file.exists()) file.mkdirs();
			return file.getAbsolutePath();
		}
		file = new File(apps.get("webroot")+ file.getPath());
		if(!file.exists()) file.mkdirs();
		return file.getAbsolutePath();
	}
	
	/**
	 * 取得文件位置
	 * @return
	 */
	public static String getFilePath(){
		String upath =  apps.get("uploadpath");
		File file = new File(upath);
//		return apps.get("ctx");
		if(file.isAbsolute()){
			return apps.get("ctx");
		}
		return apps.get("ctx") + upath;
	}
	
}
