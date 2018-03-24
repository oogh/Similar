package me.oogh.similar.data.entry;

import java.util.Date;

import cn.bmob.v3.BmobObject;
import me.oogh.similar.utils.DateUtils;

/**
 * Created by oogh on 18-3-19.
 */

public class Murmur extends BmobObject {
    private User user;
    private Date date;
    private String content;
    private String tag;

    public Murmur() {
    }

    public Murmur(User user, Date date, String content, String tag) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
                ", date=" + DateUtils.format(date) +
                ", content='" + content + '\'' +
                ", tag='" + tag + '\'' +
                '}';
    }
}
