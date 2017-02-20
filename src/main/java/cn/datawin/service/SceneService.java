package cn.datawin.service;

import cn.datawin.bean.Scene;
import cn.datawin.bean.SceneSys;
import cn.datawin.dao.SceneDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/6/3 0003.
 */
@Service
public class SceneService {
    @Resource
    public SceneDao sceneDao;

    public List<Scene> getScenes(){
        return sceneDao.getScenes();
    }


    public List<Scene> getScenesByUserid(int userid){
        return sceneDao.getScenesByUserid(userid);
    }


    public List<SceneSys> getSceneSys() {
        return sceneDao.getScenesSys();
    }
}
