package me.oogh.similar.data.entry;

import cn.bmob.v3.BmobObject;

/**
 * @author oogh <oogh216@163.com>
 * @date 2018-04-11
 * @description
 */
public class Topic extends BmobObject {

    private String title;

    public Topic() {
    }

    public Topic(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public String toString() {
        return "Topic{" +
                "title='" + title +
                '}';
    }
}
