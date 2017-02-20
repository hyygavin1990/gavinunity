package cn.datawin.action;

import cn.datawin.bean.Scene;
import cn.datawin.bean.SceneSys;
import cn.datawin.bean.User;
import cn.datawin.service.CacheService;
import cn.datawin.service.SceneService;
import cn.datawin.service.UserService;
import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/6/2 0002.
 */
@Controller
public class IndexController extends BaseController{
    @Resource
    private SceneService sceneService;
    @Resource
    private CacheService cacheService;
    @Resource
    private UserService userService;

    @RequestMapping("/hello")
    public String hello(Model model, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        List<Scene> scenes = sceneService.getScenesByUserid(2);
        model.addAttribute("scenes",scenes);
        String token = new ObjectId().toString();
        cacheService.set(token, "2");
        model.addAttribute("token",token);
        return "hello";
    }

    @RequestMapping("/hello1")
    public String hello1(Model model, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        List<SceneSys> sceneSyss = sceneService.getSceneSys();
        model.addAttribute("sceneSyss",sceneSyss);
        String token = new ObjectId().toString();
        cacheService.set(token, "2");
        return "hello1";
    }


    @RequestMapping("/create")
    public void create(Model model, HttpServletRequest request,HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        /**
         * name:
         * type:101
         * pageMode:2
         */
        String t = request.getParameter("t");
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://106.75.36.253/index.php?c=scene&a=create&t="+t);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

            nvps.add(new BasicNameValuePair("name", ""));
            nvps.add(new BasicNameValuePair("type", "101"));
            nvps.add(new BasicNameValuePair("pageMode", "2"));

        HttpResponse res = null;
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
            res = httpClient.execute(httpPost);
            HttpEntity entity = res.getEntity();
            String body = EntityUtils.toString(entity);
            System.out.println(body);
            Object o = JSON.parse(body);
            httpPost.releaseConnection();
            response.getWriter().write(body);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (ParseException e) {
            e.printStackTrace();
        }



    }

    @RequestMapping("/save")
    public void save(Model model, HttpServletRequest request,HttpServletResponse response) {
        User u = new User();
        u.setName("小明");
        u.setSex("man");
        u.setTel("15806116068");
        userService.save(u);
        printJSON(response, 1);
    }

    @RequestMapping("/list")
    public void list(Model model, HttpServletRequest request,HttpServletResponse response) {
        printJSON(response, userService.getListByName("小明"));
    }

    @RequestMapping("/one")
    public void one(Model model, HttpServletRequest request,HttpServletResponse response) {
        printJSON(response, userService.findByName("小明"));
    }




}
