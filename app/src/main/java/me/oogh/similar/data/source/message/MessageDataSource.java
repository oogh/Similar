package me.oogh.similar.data.source.message;

import java.util.List;

import me.oogh.similar.data.entry.Message;

/**
 * Created by oogh on 18-3-4.
 */

public interface MessageDataSource {
    interface LoadMessageCallback {
        void onMessagesLoaded(List<Message> messages);
    }
}
