package me.oogh.similar.data.source.murmur;

import android.support.annotation.NonNull;

import java.util.List;

import me.oogh.similar.data.entry.Murmur;

/**
 * Created by oogh on 18-3-19.
 */

public interface IMurmurDataSource {
    interface OnMurmurLoadedCallback {
        void onMurmurLoaded(List<Murmur> murmurs);
    }

    void saveMurmur(@NonNull Murmur murmur);

    void getMurmurList(String userId, OnMurmurLoadedCallback callback);

    void updateMurmur(Murmur murmur);
}
