package me.oogh.similar.data.source.murmur;

import android.support.annotation.NonNull;

import me.oogh.similar.data.entry.Murmur;

/**
 * Created by oogh on 18-3-19.
 */

public class MurmurRepository implements IMurmurDataSource {

    private static MurmurRepository INSTANCE = null;

    private final IMurmurDataSource mLocalDataSource;
    private final IMurmurDataSource mRemoteDataSource;

    private MurmurRepository(@NonNull IMurmurDataSource localDataSource,
                             @NonNull IMurmurDataSource remoteDataSource) {
        mLocalDataSource = localDataSource;
        mRemoteDataSource = remoteDataSource;
    }

    public static MurmurRepository getInstance(@NonNull IMurmurDataSource localDataSource,
                                               @NonNull IMurmurDataSource remoteDataSource) {
        if (null == INSTANCE) {
            INSTANCE = new MurmurRepository(localDataSource, remoteDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void removeMurmur(Murmur murmur) {
        mLocalDataSource.removeMurmur(murmur);
    }

    @Override
    public void saveMurmur(@NonNull Murmur murmur) {
        mLocalDataSource.saveMurmur(murmur);
    }

    @Override
    public void getMurmurList(String userId, OnMurmurLoadedCallback callback) {
        mLocalDataSource.getMurmurList(userId, callback);
    }

    @Override
    public void updateMurmur(Murmur murmur) {
        mLocalDataSource.updateMurmur(murmur);

    }

}
