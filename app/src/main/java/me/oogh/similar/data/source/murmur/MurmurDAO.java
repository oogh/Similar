package me.oogh.similar.data.source.murmur;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import me.oogh.similar.data.entry.Murmur;
import me.oogh.similar.data.entry.User;

import static me.oogh.similar.data.source.murmur.MurmurDBContract.MurmurEntry.COLUMN_CONTENT;
import static me.oogh.similar.data.source.murmur.MurmurDBContract.MurmurEntry.COLUMN_DATE;
import static me.oogh.similar.data.source.murmur.MurmurDBContract.MurmurEntry.COLUMN_TAG;
import static me.oogh.similar.data.source.murmur.MurmurDBContract.MurmurEntry.COLUMN_USER_ID;
import static me.oogh.similar.data.source.murmur.MurmurDBContract.MurmurEntry.TABLE_NAME;

/**
 * Created by oogh on 18-3-19.
 */

public class MurmurDAO {
    private static final String TAG = MurmurDAO.class.getSimpleName();

    private MurmurDBHelper mDBHelper;

    public MurmurDAO(MurmurDBHelper DBHelper) {
        mDBHelper = DBHelper;
    }

    /**
     * 插入 murmur 数据
     *
     * @param murmur
     * @return
     */
    public long insert(Murmur murmur) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, murmur.getUser().getObjectId());
        values.put(COLUMN_DATE, murmur.getDate().getTimeStamp(murmur.getDate().getDate()));
        values.put(COLUMN_CONTENT, murmur.getContent());
        values.put(COLUMN_TAG, murmur.getTag());
        return db.insert(TABLE_NAME, null, values);
    }


    /**
     * 根据用户查询所有
     *
     * @param userId
     * @return
     */
    public List<Murmur> findAll(String userId) {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        List<Murmur> murmurs = new ArrayList<>();
        String[] columns = {
                COLUMN_CONTENT,
                COLUMN_DATE,
                COLUMN_TAG
        };

        String selection = COLUMN_USER_ID + " = ?";
        String[] selectionArgs = {userId};

        String orderBy = COLUMN_DATE + " DESC";
        Cursor cursor = db.query(
                TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                orderBy);
        while (cursor.moveToNext()) {
            String content = cursor.getString(cursor.getColumnIndex(COLUMN_CONTENT));
            Long date = cursor.getLong(cursor.getColumnIndex(COLUMN_DATE));
            String tag = cursor.getString(cursor.getColumnIndex(COLUMN_TAG));
            murmurs.add(new Murmur(null, new BmobDate(new Date(date)), content, tag));
        }
        return murmurs;
    }


    public int update(Murmur murmur) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_CONTENT, murmur.getContent());

        String whereClause = COLUMN_USER_ID + " = ? and " + COLUMN_DATE + " = ?";
        String[] whereArgs = {
                BmobUser.getCurrentUser(User.class).getObjectId(),
                String.valueOf(murmur.getDate().getTimeStamp(murmur.getDate().getDate()))};

        return db.update(
                TABLE_NAME,
                values,
                whereClause,
                whereArgs
        );

    }

    /**
     * 删除一条数据
     *
     * @param murmur
     */
    public int deleteMurmur(Murmur murmur) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        String whereClause = COLUMN_USER_ID + "= ? and " + COLUMN_DATE + " = ?";
        String[] whereArgs = {
                BmobUser.getCurrentUser(User.class).getObjectId(),
                String.valueOf(BmobDate.getTimeStamp(murmur.getDate().getDate()))};
        return db.delete(TABLE_NAME, whereClause, whereArgs);
    }
}
