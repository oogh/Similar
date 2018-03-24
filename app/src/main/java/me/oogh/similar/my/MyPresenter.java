package me.oogh.similar.my;

import cn.bmob.v3.BmobUser;
import me.oogh.similar.data.entry.User;

/**
 * Created by oogh on 18-3-4.
 */

public class MyPresenter implements MyContract.Presenter {

    private MyContract.View mView;

    public MyPresenter(MyContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void getCurrentUser(OnUserFoundCallback callback) {
        callback.onUserFound(BmobUser.getCurrentUser(User.class));
    }

    interface OnUserFoundCallback {
        void onUserFound(User user);
    }

}
