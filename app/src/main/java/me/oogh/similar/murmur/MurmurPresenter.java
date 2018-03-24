package me.oogh.similar.murmur;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import me.oogh.similar.data.entry.Murmur;
import me.oogh.similar.data.source.murmur.MurmurRepository;

/**
 * Created by oogh on 18-3-4.
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
        mRepository.saveMurmur(murmur);
    }

    @Override
    public void listMurmur(String userId) {
        Log.d(TAG, "listMurmur: CurrentUserId = " + userId);

        mRepository.getMurmurList(userId, murmurs -> {
            List<Murmur> todayList = new ArrayList<>();
            for (Murmur murmur : murmurs) {
                if ("today".equals(murmur.getTag())) {
                    todayList.add(murmur);
                }
            }
            mMurmurView.showMurmurList(todayList);
        });
    }

    @Override
    public void updateMurmur(Murmur murmur) {
        mRepository.updateMurmur(murmur);
    }
}
