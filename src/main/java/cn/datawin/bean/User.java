package cn.datawin.bean;

import cn.datawin.dao.ObjectId;

/**
 * Created by hyygavin on 2016/11/11.
 */
public class User {
    @ObjectId
    private String id;
    private String name;
    private String sex;
    private String tel;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
