package me.oogh.similar.data.source.murmur;

import android.content.Context;
import android.support.annotation.NonNull;

import me.oogh.similar.data.entry.Murmur;

/**
 * Created by oogh on 18-3-19.
 */

public class MurmurLocalDataSource implements IMurmurDataSource {

    private MurmurDAO mDAO;

    public MurmurLocalDataSource(Context context) {
        mDAO = new MurmurDAO(MurmurDBHelper.getInstance(context));
    }


    @Override
    public void removeMurmur(Murmur murmur) {
        mDAO.deleteMurmur(murmur);
    }

    @Override
    public void saveMurmur(@NonNull Murmur murmur) {
        mDAO.insert(murmur);
    }

    @Override
    public void getMurmurList(String userId, OnMurmurLoadedCallback callback) {
        callback.onMurmurLoaded(mDAO.findAll(userId));
    }

    @Override
    public void updateMurmur(Murmur murmur) {
        mDAO.update(murmur);
    }
}
