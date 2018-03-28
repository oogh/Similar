package me.oogh.similar.message;

import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.event.OfflineMessageEvent;
import cn.bmob.newim.listener.BmobIMMessageHandler;

/**
 * @author oogh <oogh216@163.com>
 * @date 2018-03-16
 * @description
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
