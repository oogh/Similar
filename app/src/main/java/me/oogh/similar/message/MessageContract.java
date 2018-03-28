package me.oogh.similar.message;

import java.util.List;

import me.oogh.similar.base.BasePresenter;
import me.oogh.similar.base.BaseView;
import me.oogh.similar.data.entry.Message;

/**
 * @author oogh <oogh216@163.com>
 * @date 2018-03-02
 * @description
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
