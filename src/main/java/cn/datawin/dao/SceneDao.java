package cn.datawin.dao;

import cn.datawin.bean.Scene;
import cn.datawin.bean.SceneSys;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2016/6/3 0003.
 */
@Repository
public class SceneDao {

    private JdbcTemplate jdbcTemplate;
    @Resource
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public void save(Scene scene) {
        jdbcTemplate.update("insert into `zb_scene`(scenename_varchar) values(?)", new Object[]{scene.getName()},
                new int[]{java.sql.Types.VARCHAR});
    }

    public void update(Scene scene) {
        jdbcTemplate.update("update `zb_scene` set scenename_varchar=? where sceneid_bigint=?", new Object[]{scene.getName(), scene.getId()},
                new int[]{java.sql.Types.VARCHAR, java.sql.Types.INTEGER});
    }

    public Scene getScene(int id) {
        return (Scene) jdbcTemplate.queryForObject("select * from `zb_scene` where sceneid_bigint=?", new Object[]{id},
                new int[]{java.sql.Types.INTEGER}, new SceneRowMapper());
    }

    public List<Scene> getScenes() {
        return (List<Scene>) jdbcTemplate.query("select * from `zb_scene` ",
                new SceneRowMapper());
    }
    public List<Scene> getScenesByUserid(int userid) {
        return (List<Scene>) jdbcTemplate.query("select * from `zb_scene` where userid_int = "+userid+" and delete_int = 0",
                new SceneRowMapper());
    }
    public void delete(int sceneid) {
        jdbcTemplate.update("delete from `zb_scene`  where sceneid_bigint=?", new Object[]{sceneid},
                new int[]{java.sql.Types.INTEGER});
    }

    public List<SceneSys> getScenesSys() {
        return (List<SceneSys>) jdbcTemplate.query("select * from `zb_scenepagesys` ",
                new SceneSysRowMapper());
    }


    class SceneRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int index) throws SQLException {
            Scene scene = new Scene();
            scene.setName(rs.getString("scenename_varchar"));
            scene.setId(rs.getInt("sceneid_bigint"));
            scene.setPic(rs.getString("thumbnail_varchar"));
            return scene;
        }

    }

    class SceneSysRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int index) throws SQLException {
            SceneSys sceneSys = new SceneSys();
            sceneSys.setName(rs.getString("pagename_varchar"));
            sceneSys.setId(rs.getInt("pageid_bigint"));
            sceneSys.setPic(rs.getString("thumbsrc_varchar"));
            return sceneSys;
        }

    }
}
