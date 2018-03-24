package me.oogh.similar.message;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import me.oogh.similar.data.entry.Message;

/**
 * Created by oogh on 18-3-2.
 */

public class MessagePresenter implements MessageContract.Presenter {

    private final MessageContract.View mMessageView;

    public MessagePresenter(@NonNull MessageContract.View messageView) {
        mMessageView = messageView;
        mMessageView.setPresenter(this);
    }

    @Override
    public void start() {
        loadMessages(false);
    }

    /**
     * 加载数据
     *
     * @param showLoadingUI
     */
    private void loadMessages(boolean showLoadingUI) {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("J", "江流儿", "Nice to meet you", "17:43"));
        messages.add(new Message("G", "宫本五藏", "一个一个排好队", "前天 00:43"));
        messages.add(new Message("H", "Helen", "Hi~", "更早 8:20"));
        messages.add(new Message("A", "Align", "Welcome to Shanghai", "昨天 8:20"));
        messages.add(new Message("G", "宫本五藏", "一个一个排好队", "前天 00:43"));
        messages.add(new Message("J", "江流儿", "Nice to meet you", "17:43"));
        messages.add(new Message("H", "Helen", "Hi~", "更早 8:20"));
        messages.add(new Message("J", "江流儿", "Nice to meet you", "17:43"));
        messages.add(new Message("G", "宫本五藏", "一个一个排好队", "前天 00:43"));
        messages.add(new Message("H", "Helen", "Hi~", "更早 8:20"));
        messages.add(new Message("A", "Align", "Welcome to Shanghai", "昨天 8:20"));
        messages.add(new Message("G", "宫本五藏", "一个一个排好队", "前天 00:43"));
        messages.add(new Message("J", "江流儿", "Nice to meet you", "17:43"));
        messages.add(new Message("H", "Helen", "Hi~", "更早 8:20"));
        handleMessages(messages);
    }

    /**
     * 处理消息
     * 数据加载成功以后，处理消息的方法
     *
     * @param messages
     */
    private void handleMessages(List<Message> messages) {
        mMessageView.showMessage(messages);
    }
}
