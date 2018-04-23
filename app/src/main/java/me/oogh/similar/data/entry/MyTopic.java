package me.oogh.similar.data.entry;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * @author oogh <oogh216@163.com>
 * @date 2018-04-11
 * @description
 */
public class MyTopic extends BmobObject {
    private String title;
    private BmobRelation user;

    public MyTopic() {
    }

    public MyTopic(String title) {
        this.title = title;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BmobRelation getUser() {
        return user;
    }

    public void setUser(BmobRelation user) {
        this.user = user;
    }
}
