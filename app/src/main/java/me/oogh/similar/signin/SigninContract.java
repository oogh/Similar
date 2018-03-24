package me.oogh.similar.signin;

import me.oogh.similar.base.BasePresenter;
import me.oogh.similar.base.BaseView;
import me.oogh.similar.data.entry.User;

/**
 * Created by oogh on 18-3-16.
 */

public class SigninContract {

    interface View extends BaseView<Presenter> {
        /**
         * 登录成功
         */
        void showSucceed();

        /**
         * 登录失败
         *
         * @param msg 失败原因
         */
        void showFailed(String msg);
    }

    interface Presenter extends BasePresenter {
        /**
         * 登录
         *
         * @param type 登录类型： qq、微信、微博
         */
        void login(TYPE type, User... user);
    }

    enum TYPE {
        QQ, WECHAT, WEIBO, BMOB
    }
}
