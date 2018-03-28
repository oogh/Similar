package me.oogh.similar.data.source.murmur;

import android.support.annotation.NonNull;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import me.oogh.similar.data.entry.Murmur;

/**
 * @author oogh <oogh216@163.com>
 * @date 2018-03-19
 * @description 同步至云端
 */

public class MurmurRemoteDataSource implements IMurmurDataSource {
    @Override
    public void removeMurmur(Murmur murmur) {
    }

    @Override
    public void saveMurmur(@NonNull Murmur murmur) {
        murmur.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e != null) {
                }
            }
        });
    }

    @Override
    public void getMurmurList(String userId, OnMurmurLoadedCallback callback) {

    }

    @Override
    public void updateMurmur(Murmur murmur) {

    }
}
