package me.oogh.similar.message;

import java.util.List;

import me.oogh.similar.base.BasePresenter;
import me.oogh.similar.base.BaseView;
import me.oogh.similar.data.Message;

/**
 * Created by oogh on 18-3-2.
 */

public class MessageContract {

    interface View extends BaseView<Presenter> {
        /**
         * 有消息内容时显示
         */
        void showMessage(List<Message> messages);

        /**
         * 无消息内容时显示
         */
        void showNoMessage();
    }

    interface Presenter extends BasePresenter {

    }
}
