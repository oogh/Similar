package me.oogh.similar.murmur;

import android.support.annotation.NonNull;

/**
 * Created by oogh on 18-3-4.
 */

public class MurmurPresenter implements MurmurContract.Presenter {

    private MurmurContract.View mMurmurView;

    public MurmurPresenter(@NonNull MurmurContract.View murmurView) {
        mMurmurView = murmurView;
        mMurmurView.setPresenter(this);
    }


    @Override
    public void start() {

    }
}
