package me.oogh.similar.data.source.murmur;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static me.oogh.similar.data.source.murmur.MurmurDBContract.MurmurEntry.*;


/**
 * Created by oogh on 18-3-19.
 */

public class MurmurDBHelper extends SQLiteOpenHelper {

    private static MurmurDBHelper INSTANCE = null;
    private static final String DB_NAME = "similar.db";
    private static final int DB_VERSION = 1;


    public static synchronized MurmurDBHelper getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new MurmurDBHelper(context);
        }
        return INSTANCE;
    }

    private MurmurDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    private static final String CREATE_TABLE_MURMUR =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USER_ID + " TEXT NOT NULL, " +
                    COLUMN_CONTENT + " TEXT NOT NULL, " +
                    COLUMN_DATE + " INTEGER, " +
                    COLUMN_TAG + " TEXT NOT NULL );";

    private static final String DROP_TABLE_MURMUR =
            "DROP TABLE IF EXISTS " + TABLE_NAME;


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MURMUR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_MURMUR);
        onCreate(db);
    }
}
