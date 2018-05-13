package me.oogh.similar.data.entry;

import cn.bmob.v3.BmobObject;

/**
 * @author oogh <oogh216@163.com>
 * @date 2018-04-16
 * @description
 */
public class Whisper extends BmobObject {
    private String content;
    private MyTopic topic;

    private Boolean added = false;

    public Whisper() {
    }

    public Whisper(String content, MyTopic topic, Boolean added) {
        this.content = content;
        this.topic = topic;
        this.added = added;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MyTopic getTopic() {
        return topic;
    }

    public void setTopic(MyTopic topic) {
        this.topic = topic;
    }

    public Boolean getAdded() {
        return added;
    }

    public void setAdded(Boolean added) {
        this.added = added;
    }
}
