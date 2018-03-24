package me.oogh.similar.message;

import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.event.OfflineMessageEvent;
import cn.bmob.newim.listener.BmobIMMessageHandler;

/**
 * Created by oogh on 18-3-16.
 */

public class SimilarMessageHandler extends BmobIMMessageHandler {

    @Override
    public void onMessageReceive(MessageEvent messageEvent) {
        // TODO: 在线消息
    }

    @Override
    public void onOfflineReceive(OfflineMessageEvent offlineMessageEvent) {
        // TODO: 离线消息
    }
}
