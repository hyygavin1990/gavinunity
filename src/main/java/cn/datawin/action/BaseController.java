package cn.datawin.action;

import cn.datawin.service.CacheService;
import com.alibaba.fastjson.JSONObject;
import org.bson.types.ObjectId;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseController {

	@Resource
	CacheService cacheService;

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		CustomDateEditor dateEditor = new CustomDateEditor(format, true);
		binder.registerCustomEditor(Date.class, dateEditor);
	}

	public static void printHTML(HttpServletResponse res, Object html) {
		render(res, html, "text/html;charset=UTF-8");
	};

	public static void printJSON(HttpServletResponse res, Object obj) {
		if (obj instanceof String) {
			render(res, obj, "application/json;charset=UTF-8");
			return;
		}
		try {

			render(res, JSONObject.toJSONString(obj), "application/json;charset=UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * json 失败
	 * 
	 * @param res
	 * @param msg
	 */
	public static void printJsonErr(HttpServletResponse res, String msg) {
		JSONObject obj = new JSONObject();
		obj.put("stat", 0);
		obj.put("msg", msg);
		printJSON(res, obj);
	}

	/**
	 * json 成功
	 * 
	 * @param res
	 * @param msg
	 */
	public static void printJsonSucc(HttpServletResponse res, String msg) {
		JSONObject obj = new JSONObject();
		obj.put("stat", 1);
		obj.put("msg", msg);
		printJSON(res, obj);
	}

	private static void render(HttpServletResponse response, Object text, String contentType) {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache, must-revalidate");
		response.setDateHeader("Expires", 0L);
		response.setContentType(contentType);
		// System.out.println(contentType+"::"+text);
		try {
			response.getWriter().print(text);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void sendPage(HttpServletResponse res, String url) throws IOException {
		res.sendRedirect(res.encodeURL(url));
	}

	// 生成令牌
	public String makeToken() {
		String token = new ObjectId().toString();
		cacheService.set(token, "1", 300);
		return token;
	}

	public boolean checkToken(String token) {
		String value = cacheService.get(token);
		return "1".equals(value);
	}

	/**
	 * 校验完token即删除该token，防止重复提交。
	 * @param token
	 * @return
	 */
	public boolean checkTokenRemove(String token) {
		String value = cacheService.get(token);
		if("1".equals(value)){
			cacheService.del(token);
			return true;
		}
		return false;
	}
}
