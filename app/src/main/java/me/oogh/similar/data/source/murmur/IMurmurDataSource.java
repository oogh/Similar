package me.oogh.similar.data.source.murmur;

import android.support.annotation.NonNull;

import java.util.List;

import me.oogh.similar.data.entry.Murmur;

/**
 * Created by oogh on 18-3-19.
 */

public interface IMurmurDataSource {
    /**
     * 删除数据
     *
     * @param murmur
     */
    void removeMurmur(Murmur murmur);

    /**
     * 保存数据
     *
     * @param murmur
     */
    void saveMurmur(@NonNull Murmur murmur);

    /**
     * 获取数据
     *
     * @param userId
     * @param callback
     */
    void getMurmurList(String userId, OnMurmurLoadedCallback callback);

    /**
     * 更新数据
     *
     * @param murmur
     */
    void updateMurmur(Murmur murmur);

    void cacheMurmur(Murmur murmur);

    void getCachedMurmurList(String userId, OnMurmurLoadedCallback callback);

    interface OnMurmurLoadedCallback {
        void onMurmurLoaded(List<Murmur> murmurs);
    }
}
