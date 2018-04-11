package me.oogh.similar.murmur;

import android.support.annotation.NonNull;
import android.util.Log;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import me.oogh.similar.data.entry.Murmur;
import me.oogh.similar.data.source.murmur.MurmurRepository;

/**
 * @author oogh <oogh216@163.com>
 * @date 2018-03-04
 * @description
 */

public class MurmurPresenter implements MurmurContract.Presenter {
    private static final String TAG = MurmurPresenter.class.getSimpleName();

    private MurmurRepository mRepository;
    private MurmurContract.View mMurmurView;

    public MurmurPresenter(@NonNull MurmurRepository repository, @NonNull MurmurContract.View murmurView) {
        mRepository = repository;
        mMurmurView = murmurView;
        mMurmurView.setPresenter(this);
    }


    @Override
    public void start() {
    }

    @Override
    public void saveMurmur(@NonNull Murmur murmur) {
//        mRepository.saveMurmur(murmur);
        murmur.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Log.i(TAG, "done: 保存成功");
                } else {
                    cacheMurmur(murmur);
                    mMurmurView.showFailed();
                }
            }
        });
    }

    private void cacheMurmur(@NonNull Murmur murmur) {
        mRepository.cacheMurmur(murmur);
    }


    @Override
    public void listMurmur(String userId) {
        mRepository.getMurmurList(userId, murmurs -> {
            mMurmurView.showMurmurList(murmurs);
        });
    }


    @Override
    public void updateMurmur(Murmur murmur) {
        mRepository.updateMurmur(murmur);
    }

    @Override
    public void removeMurmur(Murmur murmur) {
        mRepository.removeMurmur(murmur);
    }

    @Override
    public void listCachedMurmur(String userId) {
        mRepository.getCachedMurmurList(userId, murmurs -> {
            mMurmurView.showCachedMurmurList(murmurs);
        });
    }
}
