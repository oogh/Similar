package me.oogh.similar.data.entry;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

/**
 * Created by oogh on 18-3-19.
 */

public class Murmur extends BmobObject {
    private User user;
    private BmobDate date;
    private String content;
    private String tag;

    public Murmur() {
    }

    public Murmur(User user, BmobDate date, String content, String tag) {
        this.user = user;
        this.date = date;
        this.content = content;
        this.tag = tag;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BmobDate getDate() {
        return date;
    }

    public void setDate(BmobDate date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "Murmur{" +
                "user=" + user +
                ", date=" + date.getDate() +
                ", content='" + content + '\'' +
                ", tag='" + tag + '\'' +
                '}';
    }
}
