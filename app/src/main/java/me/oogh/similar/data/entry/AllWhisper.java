package me.oogh.similar.data.entry;

import cn.bmob.v3.BmobObject;

/**
 * @author oogh <oogh216@163.com>
 * @date 2018-05-13
 * @description
 */
public class AllWhisper extends BmobObject {
    private Whisper whisper;

    public AllWhisper() {
    }

    public AllWhisper(Whisper whisper) {
        this.whisper = whisper;
    }

    public Whisper getWhisper() {
        return whisper;
    }

    public void setWhisper(Whisper whisper) {
        this.whisper = whisper;
    }
}
