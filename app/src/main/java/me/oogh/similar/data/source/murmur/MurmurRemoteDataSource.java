package me.oogh.similar.data.source.murmur;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import me.oogh.similar.data.entry.Murmur;
import me.oogh.similar.data.entry.User;

/**
 * @author oogh <oogh216@163.com>
 * @date 2018-03-19
 * @description 同步至云端
 */

public class MurmurRemoteDataSource implements IMurmurDataSource {
    private static final String TAG = MurmurRemoteDataSource.class.getSimpleName();

    @Override
    public void removeMurmur(Murmur murmur) {
//        murmur.setObjectId(BmobUser.getCurrentUser(User.class).getObjectId());
//        Log.i(TAG, "removeMurmur: objectId=" + murmur.getObjectId());
        murmur.setUser(BmobUser.getCurrentUser(User.class));
        murmur.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.i(TAG, "done: 删除成功...");
                } else {
                    Log.i(TAG, "done: 删除失败: " + e.getMessage());
                }
            }
        });
    }

    @Override
    public void saveMurmur(@NonNull Murmur murmur) {

        murmur.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e != null) {
                    Log.i(TAG, "done: 保存成功");
                } else {
                    Log.i(TAG, "done: 同步失败" + e.getLocalizedMessage());
                }
            }
        });
    }

    @Override
    public void getMurmurList(String userId, OnMurmurLoadedCallback callback) {
        BmobQuery<Murmur> query = new BmobQuery<>();

        // 构造BmobPointer
        User user = new User();
        user.setObjectId(userId);
        // 添加查询条件
        query.addWhereEqualTo("user", new BmobPointer(user));
        query.findObjects(new FindListener<Murmur>() {
            @Override
            public void done(List<Murmur> list, BmobException e) {
                if (e == null) {
                    Log.i(TAG, "done: list.size = " + list.size());
                    callback.onMurmurLoaded(list);
                } else {
                    Log.i(TAG, "done: 查询失败: " + e.getMessage());
                }
            }
        });
    }

    @Override
    public void updateMurmur(Murmur murmur) {
        murmur.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.i(TAG, "done: 更新成功..");
                } else {
                    Log.i(TAG, "done: 更新失败: " + e.getMessage());
                }
            }
        });
    }

    @Override
    public void cacheMurmur(Murmur murmur) {
        // 不实现
    }

    @Override
    public void getCachedMurmurList(String userId, OnMurmurLoadedCallback callback) {
        // 不实现
    }
}
