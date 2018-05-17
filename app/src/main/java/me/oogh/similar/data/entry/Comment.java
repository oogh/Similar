package me.oogh.similar.data.entry;

import cn.bmob.v3.BmobObject;

/**
 * @author oogh <oogh216@163.com>
 * @date 2018-05-17
 * @description
 */
public class Comment extends BmobObject {
    private String content;
    private Whisper whisper;

    public Comment() {
    }

    public Comment(String content, Whisper whisper) {
        this.content = content;
        this.whisper = whisper;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Whisper getWhisper() {
        return whisper;
    }

    public void setWhisper(Whisper whisper) {
        this.whisper = whisper;
    }
}
