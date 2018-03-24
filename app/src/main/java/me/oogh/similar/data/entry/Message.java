package me.oogh.similar.data.entry;

/**
 * Created by oogh on 18-3-2.
 */

public class Message {
    private String avatar;
    private String nickname;
    private String content;
    private String datetime;

    public Message() {
    }

    public Message(String avatar, String nickname, String content, String datetime) {
        this.avatar = avatar;
        this.nickname = nickname;
        this.content = content;
        this.datetime = datetime;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    @Override
    public String toString() {
        return "Message{" +
                "avatar='" + avatar + '\'' +
                ", nickname='" + nickname + '\'' +
                ", content='" + content + '\'' +
                ", datetime='" + datetime + '\'' +
                '}';
    }
}
