package me.oogh.similar.my;

import me.oogh.similar.base.BasePresenter;
import me.oogh.similar.base.BaseView;

/**
 * Created by oogh on 18-3-4.
 */

public class MyContract {
    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {
        void getCurrentUser(MyPresenter.OnUserFoundCallback callback);
    }
}
